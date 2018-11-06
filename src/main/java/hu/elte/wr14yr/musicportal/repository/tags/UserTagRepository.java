package hu.elte.wr14yr.musicportal.repository.tags;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserTagRepository extends CrudRepository<UserTag, Long> {
    @Override
    UserTag save(UserTag userTag);

    List<UserTag> findAllByUserId(long id);

    @Override
    void deleteById(Long id);
}
