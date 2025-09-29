package br.com.alura.AluraFake.util;

import org.springframework.util.Assert;
import org.springframework.validation.FieldError;

public record ErrorItemDTO(
    String field,
    String message
) {

    public ErrorItemDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

    public ErrorItemDTO(String field, String message) {
        Assert.notNull(field, "field description must not be null");
        Assert.isTrue(!field.isEmpty(), "field description must not be null");
        Assert.notNull(message, "message description must not be null");
        Assert.isTrue(!message.isEmpty(), "message description must not be null");
        this.field = field;
        this.message = message;
    }
}