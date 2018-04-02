package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongLike;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SongLikeRepository extends CrudRepository<SongLike, Long> {
    @Override
    SongLike save(SongLike songLike);

    @Query(value = "SELECT COUNT(id) FROM WHERE songId = :songId AND role LIKE 'LIKE'", nativeQuery = true)
    int countAllBySongAndRoleLike(@Param("songId") int songId);

    @Query(value = "SELECT COUNT(id) FROM WHERE songId = :songId AND role LIKE 'DISLIKE'", nativeQuery = true)
	int countAllBySongAndRoleDislike(@Param("songId") int songId);

    @Override
    void deleteById(Long id);

    void deleteAllBySong(Song song);

    void deleteAllByUser(User user);

}
