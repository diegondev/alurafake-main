package br.com.alura.AluraFake.course;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import br.com.alura.AluraFake.shared.base.BaseService;
import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskType;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.BusinessException;
import br.com.alura.AluraFake.util.ValidationException;

@Service
public class CourseService extends BaseService<Course, CourseRepository, Long> {

    CourseService (
        CourseRepository courseRepository, 
        UserRepository userRepository
    ) {
        super(courseRepository);
    }

    public Course publish(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ValidationException("id", "Curso não encontrado"));
        List<Task> tasks = course.getTasks();

        if (tasks.isEmpty()) {
            throw new BusinessException("O curso não tem atividades.");
        }

        boolean hasOpenText = tasks.stream().anyMatch(t -> t.getType() == TaskType.OPEN_TEXT);
        boolean hasSingleChoice = tasks.stream().anyMatch(t -> t.getType() == TaskType.SINGLE_CHOICE);
        boolean hasMultipleChoice = tasks.stream().anyMatch(t -> t.getType() == TaskType.MULTIPLE_CHOICE);

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            throw new BusinessException("O curso deve conter ao menos uma atividade de cada tipo.");
        }

        List<Integer> orders = tasks.stream().map(Task::getOrder).sorted().toList();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i) != i + 1) {
                throw new BusinessException("As atividades devem ter ordem contínua (1, 2, 3...).");
            }
        }

        if (course.getStatus() != Status.BUILDING) {
            throw new BusinessException("O curso só pode ser publicado se o status for BUILDING.");
        }

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        course.setStatus(Status.PUBLISHED);
        return repository.save(course);
    }
}
