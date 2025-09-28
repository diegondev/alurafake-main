package br.com.alura.AluraFake.shared.base;

public interface BaseMapper<T extends BaseEntity, R> {
    T toEntity(R dto);
    R toDTO(T entity);
}