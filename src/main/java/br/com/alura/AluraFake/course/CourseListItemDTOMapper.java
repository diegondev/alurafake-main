package br.com.alura.AluraFake.course;

import org.springframework.stereotype.Component;
import br.com.alura.AluraFake.shared.base.BaseMapper;

@Component
public class CourseListItemDTOMapper implements BaseMapper<Course, CourseListItemDTO> {

    @Override
    public Course toEntity(CourseListItemDTO dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public CourseListItemDTO toDTO(Course entity) {
        return new CourseListItemDTO(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getStatus());
    }
}
