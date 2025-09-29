package br.com.alura.AluraFake.task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.AluraFake.course.Course;

public interface JpaTaskRepository extends JpaRepository<Task, Long> {
    boolean existsByCourseAndStatement(Course course, String statement);
    List<Task> findByCourseAndOrderGreaterThanEqual(Course course, Integer order);
    @Query("SELECT MAX(t.order) FROM Task t WHERE t.course = :course")
    Optional<Integer> findMaxOrderByCourse(Course course);
}
