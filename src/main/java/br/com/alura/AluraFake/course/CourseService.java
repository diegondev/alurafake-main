package br.com.alura.AluraFake.course;

import org.springframework.stereotype.Service;
import br.com.alura.AluraFake.shared.base.BaseService;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.ValidationException;

@Service
public class CourseService extends BaseService<Course, CourseRepository, Long> {

    CourseService (
        CourseRepository courseRepository, 
        UserRepository userRepository
    ) {
        super(courseRepository);
    }

    public Course publish(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ValidationException("id", "Curso não encontrado"));

        course.setStatus(Status.PUBLISHED);
        return repository.save(course);
    }
}
