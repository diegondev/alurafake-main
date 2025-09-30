package br.com.alura.AluraFake.course;

import java.util.List;

import br.com.alura.AluraFake.shared.base.BaseRepository;
import br.com.alura.AluraFake.user.User;

public interface ICourseRepository extends BaseRepository<Course, Long> {
    public List<Course> findByInstructor(User instructor);
}
