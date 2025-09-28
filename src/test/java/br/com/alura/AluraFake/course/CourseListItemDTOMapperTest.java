package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseListItemDTOMapperTest {

    @Test
    void should_map_course_to_dto() {
        CourseListItemDTOMapper mapper = new CourseListItemDTOMapper();
        User instructor = new User("Paulo", "paulo@alura.com.br", Role.INSTRUCTOR);
        Course course = new Course("Java", "Curso de Java", instructor);

        CourseListItemDTO dto = mapper.toDTO(course);
        assertEquals("Java", dto.title());
        assertEquals("Curso de Java", dto.description());
    }
}