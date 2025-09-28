package br.com.alura.AluraFake.course;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    private final CourseService courseService;
    private final NewCourseDTOMapper newCourseDTOMapper;
    private final CourseListItemDTOMapper courseListItemDTOMapper;

    public CourseController(
        CourseService courseService,
        NewCourseDTOMapper newCourseDTOMapper,
        CourseListItemDTOMapper courseListItemDTOMapper
    ) {
        this.courseService = courseService;
        this.newCourseDTOMapper = newCourseDTOMapper;
        this.courseListItemDTOMapper = courseListItemDTOMapper;
    }

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {
        Course course = newCourseDTOMapper.toEntity(newCourse);
        courseService.create(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/course/all")
    public ResponseEntity<List<CourseListItemDTO>> createCourse() {
        List<CourseListItemDTO> courses = courseService.listAll().stream()
                .map(courseListItemDTOMapper::toDTO)
                .toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/course/{id}/publish")
    public ResponseEntity createCourse(@PathVariable("id") Long id) {
        return ResponseEntity.ok().build();
    }
}
