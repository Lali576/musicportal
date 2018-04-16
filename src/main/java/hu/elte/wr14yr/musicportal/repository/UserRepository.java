package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    Optional<User> findByUsername(String username);

    @Override
    void deleteById(Long id);
}
