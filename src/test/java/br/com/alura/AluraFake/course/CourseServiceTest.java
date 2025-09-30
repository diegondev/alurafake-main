package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskType;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.util.BusinessException;
import br.com.alura.AluraFake.util.ValidationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Course course = getEmptyCourse();


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

    @Test
    void publish__should_throw_exception_when_course_not_found() {
        Long courseId = 1L;

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.empty());

        ValidationException ex = assertThrows(ValidationException.class, () -> courseService.publish(courseId));
        assertEquals(CourseService.MSG_COURSE_NOT_FOUND, ex.getMessage());
    }

    @Test
    void publish__should_throw_exception_when_course_has_no_tasks() {
        Course course = getEmptyCourse();
        Long courseId = course.getId();

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));

        BusinessException ex = assertThrows(BusinessException.class, () -> courseService.publish(courseId));
        assertEquals(CourseService.MSG_COURSE_HAVE_NO_TASKS, ex.getMessage());
    }

    @Test
    void publish__should_throw_exception_when_course_does_not_have_all_task_types() {
        Course course = getValidCourseWithTasks();
        Long courseId = course.getId(); 

        // Remove one type of task to simulate the error
        course.setTasks(course.getTasks().stream()
                .filter(t -> t.getType() != TaskType.MULTIPLE_CHOICE)
                .toList());

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));
        BusinessException ex = assertThrows(BusinessException.class, () -> courseService.publish(courseId));
        assertEquals(CourseService.MSG_COURSE_MUST_HAVE_AT_LEAST_ONE_TASK_OF_EACH_TYPE, ex.getMessage());
    }

    @Test
    void publish__should_throw_exception_when_task_orders_are_not_continuous() {
        Course course = getEmptyCourse();
        Long courseId = course.getId();

        // Set non-continuous orders to simulate the error
        course.setTasks(Arrays.asList(
            new Task(course, "O que é Java?", 1, TaskType.OPEN_TEXT, null),
            new Task(course, "Escolha a opção correta", 3, TaskType.SINGLE_CHOICE, null), // Order 2 is missing
            new Task(course, "Selecione as opções corretas", 4, TaskType.MULTIPLE_CHOICE, null)
        ));

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));

        BusinessException ex = assertThrows(BusinessException.class, () -> courseService.publish(courseId));
        assertEquals(CourseService.MSG_COURSE_MUST_HAVE_CONTINUOUS_TASK_ORDERS, ex.getMessage());
    }

    @Test
    void publish__should_throw_exception_when_course_is_not_in_building_status() {
        Course course = getValidCourseWithTasks();
        course.setStatus(Status.PUBLISHED);
        Long courseId = course.getId();

        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));
        BusinessException ex = assertThrows(BusinessException.class, () -> courseService.publish(courseId));
        assertEquals(CourseService.MSG_COURSE_MUST_BE_IN_BUILDING_STATUS_TO_BE_PUBLISHED, ex.getMessage());
    }   

    private Course getEmptyCourse() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);
        course.setId(1L);
        return course;
    }

    private Course getValidCourseWithTasks() {
        Course course = getEmptyCourse();
        Task task1 = new Task(course, "O que é Java?", 1, TaskType.OPEN_TEXT, null);
        Task task2 = new Task(course, "Escolha a opção correta", 2, TaskType.SINGLE_CHOICE, null);
        Task task3 = new Task(course, "Selecione as opções corretas", 3, TaskType.MULTIPLE_CHOICE, null);
        course.setTasks(Arrays.asList(task1, task2, task3));    
        return course;
    }
}