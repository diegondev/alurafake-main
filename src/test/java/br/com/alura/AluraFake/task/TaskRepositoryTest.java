package br.com.alura.AluraFake.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
@Import({TaskRepository.class, CourseRepository.class})
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByCourseAndStatement_should_return_true_when_task_exists() {
        Course course = createCourseForTest();
        Task task = new Task(course, "O que aprendemos hoje?", 1, TaskType.OPEN_TEXT, null);
        taskRepository.save(task);

        boolean exists = taskRepository.existsByCourseAndStatement(course, "O que aprendemos hoje?");
        assert(exists);
        boolean notExists = taskRepository.existsByCourseAndStatement(course, "Questão inexistente");
        assert(!notExists);
    }

        @Test
    void existsByCourseAndStatement_should_return_false_when_task_exists() {
        Course course = createCourseForTest();
        Task task = new Task(course, "O que aprendemos hoje?", 1, TaskType.OPEN_TEXT, null);
        taskRepository.save(task);

        boolean notExists = taskRepository.existsByCourseAndStatement(course, "Questão inexistente");
        assert(!notExists);
    }

    @Test
    void findByCourseIdAndOrderGreaterThanEqual_should_return_tasks_with_order_greater_or_equal() {
        Course course = createCourseForTest();
        Task task = new Task(course, "O que aprendemos hoje?", 1, TaskType.OPEN_TEXT, null);
        taskRepository.save(task);

        var tasks = taskRepository.findByCourseAndOrderGreaterThanEqual(course, 1);
        assert(tasks.size() == 1);
        tasks = taskRepository.findByCourseAndOrderGreaterThanEqual(course, 2);
        assert(tasks.isEmpty());
    }

    @Test
    void findMaxOrderByCourseId_should_return_max_order_when_tasks_exist() {
        Course course = createCourseForTest();
        Task task1 = new Task(course, "O que aprendemos hoje?", 1, TaskType.OPEN_TEXT, null);
        Task task2 = new Task(course, "O que vamos aprender amanhã?", 2, TaskType.OPEN_TEXT, null);
        taskRepository.save(task1);
        taskRepository.save(task2);

        var maxOrder = taskRepository.findMaxOrderByCourse(course);
        assert(maxOrder.isPresent());
        assert(maxOrder.get() == 2);
    }

    private Course createCourseForTest() {
        User instructor = userRepository.save(new User("Caio", "caio@alura.com.br", Role.INSTRUCTOR));
        Course course = new Course("Java", "Curso de Java", instructor);

        return courseRepository.save(course);
    }

}
