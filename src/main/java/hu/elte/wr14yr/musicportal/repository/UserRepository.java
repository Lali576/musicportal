package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Keyword;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.security.Key;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    User findByUsername(String username);

    Set<User> findAllByUsernameContainsAllIgnoreCase(String username);

    Set<User> findAllByKeywords(Keyword keyword);

    @Override
    void deleteById(Long id);
}
