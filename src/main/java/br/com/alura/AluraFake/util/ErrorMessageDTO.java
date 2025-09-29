package br.com.alura.AluraFake.util;

import org.springframework.util.Assert;

public record ErrorMessageDTO(
    String message
) {

    public ErrorMessageDTO(String message) {
        Assert.notNull(message, "message description must not be null");
        Assert.isTrue(!message.isEmpty(), "message description must not be null");
        this.message = message;
    }
}
