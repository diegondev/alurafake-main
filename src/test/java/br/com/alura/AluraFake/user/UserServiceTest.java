package br.com.alura.AluraFake.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.CourseService;
import br.com.alura.AluraFake.util.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    @Test
    void existsByEmail__should_return_true_when_user_exists() {
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(true);

        boolean result = service.existsByEmail(email);

        assertTrue(result);
        verify(repository).existsByEmail(email);
    }

    @Test
    void saveUser__should_return_user_when_user_is_valid() {
        User user = new User("Test User", "test@example.com", Role.STUDENT);
        when(repository.save(user)).thenReturn(user);

        User result = service.create(user);

        assertTrue(result != null);
        verify(repository).save(user);
    }

    @Test
    void generateCourseReportByInstructor__should_throw_exception_when_user_not_found() {
        Long instructorId = 1L;
        when(repository.findById(instructorId)).thenReturn(Optional.empty());

        try {
            service.generateCourseReportByInstructor(instructorId);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(UserService.MSG_USER_NOT_FOUND));
            assertTrue(e instanceof ResourceNotFoundException);
        }

        verify(repository).findById(instructorId);
    }

    @Test
    void generateCourseReportByInstructor__should_throw_exception_when_user_not_instructor() {
        Long instructorId = 1L;
        User user = new User("Test User", "test@example.com", Role.STUDENT);
        when(repository.findById(instructorId)).thenReturn(Optional.of(user));

        try {
            service.generateCourseReportByInstructor(instructorId);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(UserService.MSG_USER_NOT_INSTRUCTOR));
        }

        verify(repository).findById(instructorId);
    }

    @Test
    void generateCourseReportByInstructor__should_return_report_when_user_is_instructor() {
        Long instructorId = 1L;
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        when(repository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.findByInstructor(instructor)).thenReturn(getListOfCourses(instructor));

        InstructorCoursesReportDTO report = service.generateCourseReportByInstructor(instructorId);

        assertTrue(report != null);
        assertTrue(report.courses().size() == 4);
        assertTrue(report.publishedCount() == 0);
    }

    private List<Course> getListOfCourses(User instructor) {
        // gere uma lista de cursos para o instrutor com valores fictícios preenchidos com valores elaborados em portugues
        return List.of(
            new Course("Curso de Testes", "Descrição do curso de testes", instructor),
            new Course("Curso de Java", "Descrição do curso de Java", instructor),
            new Course("Curso de Spring", "Descrição do curso de Spring", instructor),
            new Course("Curso de Hibernate", "Descrição do curso de Hibernate", instructor)
        );
    }

}
