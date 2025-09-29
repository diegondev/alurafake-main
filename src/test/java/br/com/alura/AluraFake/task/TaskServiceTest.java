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
    @InjectMocks
    private TaskService service;

    @Test
    void create__should_create_task() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        Task task = new Task(course, "O que aprendemos hoje?", 1, TaskType.OPEN_TEXT,null);

        service.create(task);
        verify(repository, times(1)).save(task);
    }

    @Test
    void listAll__should_list_all_tasks() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        Task task1 = new Task(course, "I) O que aprendemos hoje?", 1, TaskType.OPEN_TEXT, null);
        Task task2 = new Task(course, "II) O que aprendemos hoje?", 2, TaskType.SINGLE_CHOICE, List.of(
                new TaskOption("Alternativa 1", false),
                new TaskOption("Alternativa 2", true)
        ));

        when(repository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = service.listAll();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertEquals(tasks.get(1).getOptions().size(), 2);
    }

    @Test
    void shiftTasksOrderIfNecessary__should_shift_tasks_order() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        Task existingTask1 = new Task(course, "Explique o ciclo de vida do Spring", 1, TaskType.OPEN_TEXT, null);
        Task existingTask2 = new Task(course, "Escolha as tecnologias utilizada", 2, TaskType.SINGLE_CHOICE, List.of(
                new TaskOption("Spring Boot", false),
                new TaskOption("Django", true),
                new TaskOption("Rails", false)
        ));
        Task newTask = new Task(course, "O que aprendemos hoje?", 1, TaskType.MULTIPLE_CHOICE, List.of(
                new TaskOption("Java", false),
                new TaskOption("Python", true),
                new TaskOption("Ruby", true)
        ));

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

}