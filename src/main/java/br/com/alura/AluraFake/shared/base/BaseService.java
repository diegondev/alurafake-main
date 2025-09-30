package br.com.alura.AluraFake.shared.base;

import java.util.List;

public class BaseService<T extends BaseEntity<ID>, R extends BaseRepository<T, ID>, ID> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public T getById(ID id) {
        return repository.findById(id).orElse(null);
    }

    public List<T> listAll() {
        return repository.findAll();
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
