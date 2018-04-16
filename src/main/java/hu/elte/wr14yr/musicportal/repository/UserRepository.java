package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    User findByUsername(String username);

    Set<User> findAllByUsernameContainsAllIgnoreCase(String username);

    @Override
    void deleteById(Long id);
}
