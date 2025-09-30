package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;
    @Mock
    private TaskValidatorService validatorService;
    @InjectMocks
    private TaskService service;

    @Test
    void create__should_create_task() {
        Course course = getEmptyCourse();
        Task task = getOpenTextTask(course, 1);

        when(repository.save(task)).thenReturn(task);

        service.create(task);
        verify(repository, times(1)).save(task);
    }

    @Test
    void createOpenTextTask__should_create_open_text_task() {
        Course course = getEmptyCourse();
        Task task = getOpenTextTask(course, 1);

        when(repository.save(task)).thenReturn(task);

        Task createdTask = service.createOpenTextTask(task);
        assertEquals(TaskType.OPEN_TEXT, createdTask.getType());
        verify(repository, times(1)).save(task);
    }

    @Test
    void createSingleChoiceTask__should_create_single_choice_task() {
        Course course = getEmptyCourse();
        Task task = getSingleChoiceTask(course, 1);

        when(repository.save(task)).thenReturn(task);

        Task createdTask = service.createSingleChoiceTask(task);
        assertEquals(TaskType.SINGLE_CHOICE, createdTask.getType());
        verify(repository, times(1)).save(task);
    }

    @Test
    void createMultipleChoiceTask__should_create_multiple_choice_task() {
        Course course = getEmptyCourse();
        Task task = getMultipleChoiceTask(course, 1);

        when(repository.save(task)).thenReturn(task);

        Task createdTask = service.createMultipleChoiceTask(task);
        assertEquals(TaskType.MULTIPLE_CHOICE, createdTask.getType());
        verify(repository, times(1)).save(task);
    }

    @Test
    void listAll__should_list_all_tasks() {
        Course course = getEmptyCourse();
        Task task1 = getOpenTextTask(course, 1);
        Task task2 = getSingleChoiceTask(course, 2);

        when(repository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = service.listAll();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertEquals(tasks.get(1).getOptions().size(), 3);
    }

    @Test
    void shiftTasksOrderIfNecessary__should_shift_tasks_order() {
        Course course = getEmptyCourse();
        Task existingTask1 = getOpenTextTask(course, 1);
        Task existingTask2 = getSingleChoiceTask(course, 2);
        Task newTask = getMultipleChoiceTask(course, 1);

        when(repository.findByCourseAndOrderGreaterThanEqual(course, 1))
                .thenReturn(Arrays.asList(existingTask1, existingTask2));
        when(repository.save(any(Task.class))).thenReturn(newTask);
        service.create(newTask);
        assertEquals(2, existingTask1.getOrder());
        assertEquals(3, existingTask2.getOrder());
        assertEquals(1, newTask.getOrder());
        verify(repository, times(1)).saveAll(anyList());
        verify(repository, times(1)).save(newTask);
    }

    private Course getEmptyCourse() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        course.setId(1L);
        return course;
    }

    private Task getOpenTextTask(Course course, int order) {
        return new Task(course, "Explique o ciclo de vida do Spring", order, TaskType.OPEN_TEXT, null);
    }

    private Task getSingleChoiceTask(Course course, int order) {
        return new Task(course, "Escolha as tecnologias utilizada", order, TaskType.SINGLE_CHOICE, List.of(
                new TaskOption("Spring Boot", false),
                new TaskOption("Django", true),
                new TaskOption("Rails", false)
        ));
    }

    private Task getMultipleChoiceTask(Course course, int order) {
        return new Task(course, "Quais linguagens são tipadas estaticamente?", order, TaskType.MULTIPLE_CHOICE, List.of(
                new TaskOption("Java", true),
                new TaskOption("Python", false),
                new TaskOption("C#", true),
                new TaskOption("Ruby", false)
        ));
    }

}