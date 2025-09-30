package br.com.alura.AluraFake.course;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import br.com.alura.AluraFake.shared.base.BaseService;
import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.TaskType;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.BusinessException;
import br.com.alura.AluraFake.util.ValidationException;

@Service
public class CourseService extends BaseService<Course, CourseRepository, Long> {
    public static final String MSG_COURSE_NOT_FOUND = "Curso não encontrado";
    public static final String MSG_COURSE_HAVE_NO_TASKS = "O curso não tem atividades.";
    public static final String MSG_COURSE_MUST_HAVE_AT_LEAST_ONE_INSTRUCTOR = "O curso deve ter ao menos um instrutor.";
    public static final String MSG_COURSE_MUST_HAVE_AT_LEAST_ONE_TASK_OF_EACH_TYPE = "O curso deve conter ao menos uma atividade de cada tipo.";
    public static final String MSG_COURSE_MUST_HAVE_CONTINUOUS_TASK_ORDERS = "As atividades devem ter ordem contínua (1, 2, 3...).";
    public static final String MSG_COURSE_MUST_BE_IN_BUILDING_STATUS_TO_BE_PUBLISHED = "O curso só pode ser publicado se o status for BUILDING.";
    public static final String MSG_COURSE_MUST_HAVE_TASKS = "O curso não tem atividades.";

    CourseService (
        CourseRepository courseRepository, 
        UserRepository userRepository
    ) {
        super(courseRepository);
    }

    public Course publish(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ValidationException("id", CourseService.MSG_COURSE_NOT_FOUND));
        List<Task> tasks = course.getTasks();

        if (tasks.isEmpty()) {
            throw new BusinessException(CourseService.MSG_COURSE_HAVE_NO_TASKS);
        }

        boolean hasOpenText = tasks.stream().anyMatch(t -> t.getType() == TaskType.OPEN_TEXT);
        boolean hasSingleChoice = tasks.stream().anyMatch(t -> t.getType() == TaskType.SINGLE_CHOICE);
        boolean hasMultipleChoice = tasks.stream().anyMatch(t -> t.getType() == TaskType.MULTIPLE_CHOICE);

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            throw new BusinessException(CourseService.MSG_COURSE_MUST_HAVE_AT_LEAST_ONE_TASK_OF_EACH_TYPE);
        }

        List<Integer> orders = tasks.stream().map(Task::getOrder).sorted().toList();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i) != i + 1) {
                throw new BusinessException(CourseService.MSG_COURSE_MUST_HAVE_CONTINUOUS_TASK_ORDERS);
            }
        }

        if (course.getStatus() != Status.BUILDING) {
            throw new BusinessException(CourseService.MSG_COURSE_MUST_BE_IN_BUILDING_STATUS_TO_BE_PUBLISHED);
        }

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        course.setStatus(Status.PUBLISHED);
        return repository.save(course);
    }
}
