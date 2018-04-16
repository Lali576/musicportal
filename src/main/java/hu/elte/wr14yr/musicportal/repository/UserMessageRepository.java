package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import org.springframework.data.repository.CrudRepository;

public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {
    @Override
    UserMessage save(UserMessage userMessage);

    Iterable<UserMessage> findAllByUserTo(User user);

    void deleteByUserFrom(User userFrom);
}
