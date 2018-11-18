package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContainsAllIgnoreCase(String username);

    List<User> findAllByFavGenreIdNameEquals(String genreWord);

    List<User> findAllByUserTagsWordContainsAllIgnoreCase(String tagWord);

    User findUserById(Long id);

    @Override
    void deleteById(Long id);
}
