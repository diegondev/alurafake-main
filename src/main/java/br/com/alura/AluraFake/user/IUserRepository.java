package br.com.alura.AluraFake.user;

import java.util.Optional;

import br.com.alura.AluraFake.shared.base.BaseRepository;

public interface IUserRepository extends BaseRepository<User, Long> {
    long count();
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
