package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.keywords.UserKeyword;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserKeywordRepository extends CrudRepository<UserKeyword, Long> {
    @Override
    UserKeyword save(UserKeyword userKeyword);

    List<UserKeyword> findAllByWordContainsAllIgnoreCase(String keyword);

    @Override
    void deleteById(Long id);
}
