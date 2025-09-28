package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void should_create_course() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);

        when(courseRepository.save(course)).thenReturn(course);

        courseService.create(course);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void should_list_all_courses() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course c1 = new Course("Java", "Curso de Java", instructor);
        Course c2 = new Course("Spring", "Curso de Spring", instructor);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Course> courses = courseService.listAll();
        assertEquals(2, courses.size());
        assertTrue(courses.contains(c1));
        assertTrue(courses.contains(c2));
    }
}