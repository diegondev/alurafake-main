package br.com.alura.AluraFake.course;

public record CourseListItemDTO(
    Long id,
    String title,
    String description,
    Status status
) {}
