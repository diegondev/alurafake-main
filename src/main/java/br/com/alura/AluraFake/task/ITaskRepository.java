package br.com.alura.AluraFake.task;

import java.util.List;
import java.util.Optional;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.shared.base.BaseRepository;

public interface ITaskRepository extends BaseRepository<Task, Long> {
    public Task save(Task task);
    public List<Task> saveAll(List<Task> tasks);
    public Optional<Task> findById(Long id);
    public List<Task> findAll();
    public void deleteById(Long id);
    public boolean existsByCourseAndStatement(Course course, String statement);
    public List<Task> findByCourseAndOrderGreaterThanEqual(Course course, Integer order);
    public Optional<Integer> findMaxOrderByCourse(Course course);
}
