package br.com.alura.AluraFake.course;

import org.springframework.stereotype.Repository;

import br.com.alura.AluraFake.shared.base.BaseRepository;

@Repository
public class CourseRepository implements BaseRepository<Course, Long> {

    private final JpaCourseRepository repository;

    public CourseRepository(JpaCourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Course save(Course entity) {
        return repository.save(entity);
    }

    @Override
    public java.util.List<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public java.util.Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}