package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import org.springframework.data.repository.CrudRepository;

public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {
    @Override
    UserMessage save(UserMessage userMessage);

    Iterable<UserMessage> findAllByUserFrom(User user);

    Iterable<UserMessage> findAllByUserTo(User user);

    @Override
    void deleteById(Long id);

    void deleteByUserFrom(User userFrom);
}
