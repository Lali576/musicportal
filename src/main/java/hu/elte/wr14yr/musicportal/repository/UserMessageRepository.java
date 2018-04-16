package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {
    @Override
    UserMessage save(UserMessage userMessage);

    Set<UserMessage> findAllByUserTo(User user);

    void deleteAllByUserFrom(User userFrom);
}
