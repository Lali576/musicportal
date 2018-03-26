package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {
    @Override
    UserMessage save(UserMessage userMessage);

    Optional<Set<UserMessage>> findAllByUserFrom(User user);

    Optional<Set<UserMessage>> findAllByUserTo(User user);

    @Override
    void deleteById(Long id);
}
