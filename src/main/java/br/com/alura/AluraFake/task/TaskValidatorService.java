package br.com.alura.AluraFake.task;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.util.BusinessException;

@Service
public class TaskValidatorService {
    private final TaskRepository repository;

    public TaskValidatorService(TaskRepository repository) {
        this.repository = repository;
    }

    public void validateTask(Task task) {
        validateCourseStatus(task);
        validateStatementExistis(task);
        validateTaskOrderSequence(task);

        List<TaskOption> options = task.getOptions();
        if (options != null && !options.isEmpty()) {
            validateAlternativeLengths(options, 4, 80);
            validateEqualAlternatives(options);
            validateEqualityWithStatement(options, task.getStatement());
        }
    }

    public void validateOpenTextTask(Task task) {
        validateTask(task);
    }

    public void validateSingleChoiceTask(Task task) {
        List<TaskOption> options = task.getOptions();
        validateTask(task);
        validateNumberOfAlternatives(task, 2, 5);
        validateNumberOfCorrectAlternatives(options, task.getType());
    }

    public void validateMultipleChoiceTask(Task task) {
        List<TaskOption> options = task.getOptions();
        validateTask(task);
        validateNumberOfAlternatives(task, 3, 5);
        validateNumberOfCorrectAlternatives(options, task.getType());
    }

    private void validateCourseStatus(Task task) {
        if (Status.PUBLISHED.equals(task.getCourse().getStatus())) {
            throw new BusinessException("O curso já foi publicado e não pode ser editado.");
        }
    }

    private void validateStatementExistis(Task task) {
        boolean alreadyExists = repository.existsByCourseAndStatement(task.getCourse(), task.getStatement());
        if (alreadyExists) {
            throw new BusinessException("Já existe uma tarefa com esse enunciado nesse curso.");
        }
    }

    private void validateNumberOfAlternatives(Task task, int min, int max) {
        List<TaskOption> options = task.getOptions();
        if (options.size() < min || options.size() > max) {
            throw new BusinessException(String.format("A tarefa deve ter entre %d e %d alternativas.", min, max));
        }
    }

    private void validateNumberOfCorrectAlternatives(List<TaskOption> options, TaskType type) {
        long correctCount = options.stream().filter(TaskOption::isCorrect).count();

        if (type == TaskType.SINGLE_CHOICE && correctCount != 1) {
            throw new BusinessException("A tarefa deve ter exatamente uma alternativa correta.");
        }

        if (type == TaskType.MULTIPLE_CHOICE) {
            if (correctCount < 2) {
                throw new BusinessException("A tarefa deve ter pelo menos duas alternativas corretas.");
            }
            if (!(options.size() - correctCount >= 1)) {
                throw new BusinessException("A tarefa deve ter pelo menos uma alternativa incorreta.");
            }
        }
    }

    private void validateAlternativeLengths(List<TaskOption> options, int min, int max) {
        for (TaskOption option : options) {
            validateTextLength(option.getOption(), min, max, "Alternativa");
        }
    }

    private void validateTextLength(String text, int min, int max, String fieldName) {
        if (text.length() < min || text.length() > max) {
            throw new BusinessException(String.format("%s deve ter entre %d e %d caracteres.", fieldName, min, max));
        }
    }

    private void validateEqualAlternatives(List<TaskOption> options) {
        Set<String> uniqueOptions = options.stream()
                .map(o -> o.getOption().trim().toLowerCase())
                .collect(Collectors.toSet());

        if (uniqueOptions.size() != options.size()) {
            throw new BusinessException("As alternativas não podem ser duplicadas.");
        }
    }

    private void validateEqualityWithStatement(List<TaskOption> options, String statement) {
        String normalizedStatement = statement.trim().toLowerCase();
        for (TaskOption option : options) {
            if (option.getOption().trim().toLowerCase().equals(normalizedStatement)) {
                throw new BusinessException("As alternativas não podem ser iguais ao enunciado da atividade.");
            }
        }
    }

    private void validateTaskOrderSequence(Task task) {
        Integer maxOrder = repository.findMaxOrderByCourse(task.getCourse()).orElse(0);

        if (task.getOrder() > maxOrder + 1) {
            throw new BusinessException(
                "A ordem da atividade deve ser contínua. Última ordem existente: " + maxOrder
            );
        }
    }
}
