package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void should_create_task_with_valid_data() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        Task task = new Task(course, "Exercício 1", 1, TaskType.OPEN_TEXT, null);

        assertEquals("Exercício 1", task.getStatement());
        assertEquals(1, task.getOrder());
        assertEquals(TaskType.OPEN_TEXT, task.getType());
        assertEquals(course, task.getCourse());
    }
}