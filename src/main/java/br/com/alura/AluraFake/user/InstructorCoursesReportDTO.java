package br.com.alura.AluraFake.user;

import java.util.List;

public record InstructorCoursesReportDTO(List<CourseReportDTO> courses, long publishedCount) {

}
