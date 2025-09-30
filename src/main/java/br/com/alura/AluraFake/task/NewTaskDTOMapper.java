package br.com.alura.AluraFake.task;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseService;
import br.com.alura.AluraFake.shared.base.BaseMapper;
import br.com.alura.AluraFake.util.ValidationException;

@Component
public class NewTaskDTOMapper implements BaseMapper<Task, NewTaskDTO> {

    private final CourseService courseService;

    public NewTaskDTOMapper(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public Task toEntity(NewTaskDTO dto) {
        Course course = getCourse(dto.courseId());
        Task task = new Task(course, dto.statement(), dto.order(), null, toOptions(dto));
        return task;
    }

    @Override
    public NewTaskDTO toDTO(Task entity) {
        throw new UnsupportedOperationException("Unimplemented method 'toDTO'");
    }

    private Course getCourse(Long courseId) {
        Course course = courseService.getById(courseId);
        if (course == null) {
            throw new ValidationException("courseId", "Curso com id " + courseId + " não encontrado");
        }
        return course;
    }

    private List<TaskOption> toOptions(NewTaskDTO newTaskDTO) {
        List<NewTaskOptionDTO> optionsDTO = newTaskDTO.options();

        if (optionsDTO == null) {
            return List.of();
        }

        return optionsDTO.stream()
                .map(dto -> new TaskOption(dto.option(), dto.isCorrect()))
                .toList();
    }
    
}
