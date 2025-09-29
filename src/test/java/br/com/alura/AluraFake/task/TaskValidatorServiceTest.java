package br.com.alura.AluraFake.task;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;

@ExtendWith(MockitoExtension.class)
public class TaskValidatorServiceTest {

    @Mock
    private TaskRepository repository;
    @InjectMocks
    private TaskService service;
    @InjectMocks
    private TaskValidatorService validatorService;

    @BeforeEach
    void setup() {
        doReturn(false).when(repository).existsByCourseAndStatement(any(), any());
        doReturn(Optional.of(0)).when(repository).findMaxOrderByCourse(any());
    }
    
    @Test
    void createOpenTextTask__should_create_open_text_task() {
        Task task = getSuccessOpenTextTask();
        validatorService.validateOpenTextTask(task);

    }

    @Test
    void createSingleChoiceTask__should_create_single_choice_task() {
        Task task = getSuccessSingleChoiceTask();
        validatorService.validateSingleChoiceTask(task);
    }

    @Test
    void createMultipleChoiceTask__should_create_multiple_choice_task() {
        Task task = getSuccessMultipleChoiceTask();
        validatorService.validateMultipleChoiceTask(task);
    }

    private Task getSuccessOpenTextTask() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        return new Task(course, "O que aprendemos hoje?", 1, TaskType.OPEN_TEXT, new ArrayList<>());
    }

    private Task getSuccessSingleChoiceTask() {
        Task task = getSuccessOpenTextTask();
        task.getOptions().add(new TaskOption("Java", true));
        task.getOptions().add(new TaskOption("Python", false));
        task.getOptions().add(new TaskOption("Ruby", false));
        return task;
    }

    private Task getSuccessMultipleChoiceTask() {
        Task task = getSuccessOpenTextTask();
        task.getOptions().add(new TaskOption("Java", true));
        task.getOptions().add(new TaskOption("Python", true));
        task.getOptions().add(new TaskOption("Ruby", false));
        return task;
    }

    public Course createMockCourseWithTwoTasks() {
    User instructor = new User("Maria", "maria@alura.com.br", Role.INSTRUCTOR);
    Course course = new Course("Spring Boot", "Curso completo de Spring Boot", instructor);

    Task task1 = new Task(course, "Explique o ciclo de vida do Spring", 1, TaskType.OPEN_TEXT, null);
    Task task2 = new Task(course, "Escolha as tecnologias utilizadas", 2, TaskType.SINGLE_CHOICE, List.of(
            new TaskOption("Spring Boot", true),
            new TaskOption("Django", false),
            new TaskOption("Rails", false)
    ));

    course.setTasks(List.of(task1, task2));

    course.setId(100L);
    task1.setId(101L);
    task2.setId(102L);

    return course;
}
    
}
