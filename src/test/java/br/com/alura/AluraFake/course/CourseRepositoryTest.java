package br.com.alura.AluraFake.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
@Import({CourseRepository.class})
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findById__should_return_course_when_exists() {
        User instructor = userRepository.save(getInstructor());
        Course course = repository.save(getEmptyCourse(instructor));

        Optional<Course> result = repository.findById(course.getId());
        assertTrue(result.isPresent());
        assertEquals(course, result.get());
    }

    @Test
    void findById__should_return_empty_when_not_exists() {
        Optional<Course> result = repository.findById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByInstructor__should_return_courses_when_exists() {
        User instructor = userRepository.save(getInstructor());
        Course course = repository.save(getEmptyCourse(instructor));

        List<Course> result = repository.findByInstructor(instructor);
        assertFalse(result.isEmpty());
        assertEquals(course, result.get(0));
    }

    private User getInstructor() {
        return new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
    }

    private Course getEmptyCourse(User instructor) {
        return new Course("Java", "Curso de Java", instructor);
    }

}
