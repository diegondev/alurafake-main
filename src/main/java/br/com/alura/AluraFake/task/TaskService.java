package br.com.alura.AluraFake.task;

import java.util.List;

import br.com.alura.AluraFake.shared.base.BaseService;

public class TaskService  extends BaseService<Task, TaskRepository, Long> {
    private final TaskValidatorService validatorService;

    public TaskService(TaskRepository repository, TaskValidatorService validatorService) {
        super(repository);
        this.validatorService = validatorService;
    }

    @Override
    public Task create(Task task) {
        shiftTasksOrderIfNecessary(task);
        return super.create(task);
    }

    public Task createOpenTextTask(Task task) {
        task.setType(TaskType.OPEN_TEXT);
        validatorService.validateTask(task);
        return create(task);
    }

    public Task createSingleChoiceTask(Task task) {
        task.setType(TaskType.SINGLE_CHOICE);
        validatorService.validateSingleChoiceTask(task);
        return create(task);
    }

    public Task createMultipleChoiceTask(Task task) {
        task.setType(TaskType.MULTIPLE_CHOICE);
        validatorService.validateMultipleChoiceTask(task);
        return create(task);
    }

    private void shiftTasksOrderIfNecessary(Task newTask) {
        List<Task> tasksToShift = repository.findByCourseAndOrderGreaterThanEqual(
                newTask.getCourse(), newTask.getOrder()
        );

        tasksToShift.forEach(task -> task.setOrder(task.getOrder() + 1));

        repository.saveAll(tasksToShift);
    }
}
