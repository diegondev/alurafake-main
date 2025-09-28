package br.com.alura.AluraFake.course;

import org.springframework.stereotype.Component;
import br.com.alura.AluraFake.shared.base.BaseMapper;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.ValidationException;

@Component
public class NewCourseDTOMapper implements BaseMapper<Course, NewCourseDTO> {
    private final UserRepository userRepository;

    public NewCourseDTOMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Course toEntity(NewCourseDTO dto) {
        User instructor = userRepository.findByEmail(dto.emailInstructor())
            .filter(User::isInstructor)
            .orElseThrow(() -> new ValidationException("emailInstructor", "Usuário não é um instrutor"));
        return new Course(dto.title(), dto.description(), instructor);
    }

    @Override
    public NewCourseDTO toDTO(Course entity) {
        return new NewCourseDTO(entity.getTitle(), entity.getDescription(), entity.getInstructor().getEmail());
    }
}
