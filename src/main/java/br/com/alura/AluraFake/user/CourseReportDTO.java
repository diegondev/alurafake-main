package br.com.alura.AluraFake.user;

import java.time.LocalDateTime;

import br.com.alura.AluraFake.course.Status;

public record CourseReportDTO(Long id, String title, Status status, LocalDateTime publishedAt, int taskCount) {}
