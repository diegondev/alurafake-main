package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NewCourseDTOMapperTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NewCourseDTOMapper newCourseDTOMapper;

    @Test
    void toEntity__should_map_dto_to_entity_when_instructor_is_valid() {
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        when(userRepository.findByEmail("paulo@alura.com.br")).thenReturn(Optional.of(instructor));

        NewCourseDTO dto = new NewCourseDTO("Java", "Curso de Java", "paulo@alura.com.br");

        Course course = newCourseDTOMapper.toEntity(dto);
        assertEquals("Java", course.getTitle());
        assertEquals("Curso de Java", course.getDescription());
        assertEquals(instructor, course.getInstructor());
    }

    @Test
    void toEntity__should_throw_exception_when_instructor_email_not_found() {
        NewCourseDTO dto = new NewCourseDTO("Java", "Curso de Java", "notfound@alura.com.br");

        when(userRepository.findByEmail("notfound@alura.com.br")).thenReturn(Optional.empty());

        ValidationException ex = assertThrows(ValidationException.class, () -> newCourseDTOMapper.toEntity(dto));
        assertEquals("emailInstructor", ex.getField());
    }

    @Test
    void toEntity__should_throw_exception_when_user_is_not_instructor() {
        User user = new User("Joao", "joao@alura.com.br", Role.STUDENT);
        when(userRepository.findByEmail("joao@alura.com.br")).thenReturn(Optional.of(user));

        NewCourseDTO dto = new NewCourseDTO("Java", "Curso de Java", "joao@alura.com.br");

        ValidationException ex = assertThrows(ValidationException.class, () -> newCourseDTOMapper.toEntity(dto));
        assertEquals("emailInstructor", ex.getField());
    }
}