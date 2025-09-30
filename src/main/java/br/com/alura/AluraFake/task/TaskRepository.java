package br.com.alura.AluraFake.task;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.alura.AluraFake.course.Course;

@Repository
public class TaskRepository implements ITaskRepository {

    private final JpaTaskRepository repository;

    public TaskRepository(JpaTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(Task entity) {
        beforeSave(entity);
        return repository.save(entity);
    }
    
    @Override
    public List<Task> saveAll(List<Task> tasks) {
        tasks.forEach(this::beforeSave);
        return repository.saveAll(tasks);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsByCourseAndStatement(Course course, String statement) {
        return repository.existsByCourseAndStatement(course, statement);
    }

    public List<Task> findByCourseAndOrderGreaterThanEqual(Course course, Integer order) {
        return repository.findByCourseAndOrderGreaterThanEqual(course, order);
    }

    public Optional<Integer> findMaxOrderByCourse(Course course) {
        return repository.findMaxOrderByCourse(course);
    }

    private void beforeSave(Task entity) {
        entity.getOptions().forEach(option -> option.setTask(entity));
    }
    
}
