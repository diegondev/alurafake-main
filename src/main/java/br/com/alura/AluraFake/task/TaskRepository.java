package br.com.alura.AluraFake.task;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.shared.base.BaseRepository;

@Repository
public class TaskRepository implements BaseRepository<Task, Long>, ITaskRepository {

    private final JpaTaskRepository repository;

    public TaskRepository(JpaTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(Task entity) {
        return repository.save(entity);
    }
    
    @Override
    public List<Task> saveAll(List<Task> tasks) {
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
    
}
