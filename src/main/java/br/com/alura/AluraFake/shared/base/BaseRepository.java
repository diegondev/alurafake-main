package br.com.alura.AluraFake.shared.base;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<ID>, ID> {
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
    void deleteById(ID id);
}
