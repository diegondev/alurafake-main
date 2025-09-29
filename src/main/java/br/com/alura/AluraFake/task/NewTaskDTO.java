package br.com.alura.AluraFake.task;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Positive;

public record NewTaskDTO(
    Long courseId,
    @Length(min = 4, max = 255)
    String statement, 
    @Positive
    Integer order,
    List<NewTaskOptionDTO> options
) {}