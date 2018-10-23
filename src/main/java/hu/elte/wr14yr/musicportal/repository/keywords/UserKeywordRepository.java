package hu.elte.wr14yr.musicportal.repository.keywords;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.UserKeyword;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserKeywordRepository extends CrudRepository<UserKeyword, Long> {
    @Override
    UserKeyword save(UserKeyword userKeyword);

    List<UserKeyword> findByUser(User user);

    @Override
    void deleteById(Long id);
}
