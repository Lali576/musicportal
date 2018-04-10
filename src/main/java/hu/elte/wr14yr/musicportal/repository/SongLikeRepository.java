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

    @Query(value = "SELECT COUNT(id) FROM SONG_LIKES WHERE SONG_ID = :SONG_ID AND ROLE LIKE 'LIKE'", nativeQuery = true)
    int countAllBySongAndRoleLike(@Param("SONG_ID") int songId);

    @Query(value = "SELECT COUNT(id) FROM SONG_LIKES WHERE songId = :SONG_ID AND ROLE LIKE 'DISLIKE'", nativeQuery = true)
	int countAllBySongAndRoleDislike(@Param("SONG_ID") int songId);

    @Override
    void deleteById(Long id);

    void deleteAllBySong(Song song);
}
