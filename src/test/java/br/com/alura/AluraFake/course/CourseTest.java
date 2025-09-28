package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void should_create_course_with_valid_data() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);

        assertEquals("Java", course.getTitle());
        assertEquals("Curso de Java", course.getDescription());
        assertEquals(instructor, course.getInstructor());
    }
}