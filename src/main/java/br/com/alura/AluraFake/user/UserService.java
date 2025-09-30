package br.com.alura.AluraFake.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.shared.base.BaseService;
import br.com.alura.AluraFake.util.BusinessException;

@Service
public class UserService extends BaseService<User, UserRepository, Long> {
    public static final String MSG_USER_NOT_FOUND = "Usuário não encontrado";
    public static final String MSG_USER_NOT_INSTRUCTOR = "Usuário não é instrutor";

    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        super(userRepository);
        this.courseRepository = courseRepository;
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public InstructorCoursesReportDTO generateCourseReportByInstructor(Long instructorId) {
        User user = validateUserToGenerateReport(instructorId);
        List<Course> courses = courseRepository.findByInstructor(user);
        List<CourseReportDTO> report = courses.stream()
            .map(course -> new CourseReportDTO(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                course.getPublishedAt(),
                course.getTasks().size()
            )).toList();
        long publishedCount = courses.stream()
            .filter(c -> c.getStatus() == Status.PUBLISHED)
            .count();

        return new InstructorCoursesReportDTO(report, publishedCount);
    }

    private User validateUserToGenerateReport(Long instructorId) {
        Optional<User> userOpt = repository.findById(instructorId);
        if (userOpt.isEmpty()) {
            throw new BusinessException(MSG_USER_NOT_FOUND);
        }
        User user = userOpt.get();
        if (user.getRole() != Role.INSTRUCTOR) {
            throw new BusinessException(MSG_USER_NOT_INSTRUCTOR);
        }
        return user;
    }
}
