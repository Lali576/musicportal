package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SongLikeRepository extends CrudRepository<SongLike, Long> {
    @Override
    SongLike save(SongLike songLike);

    @Query(value = "SELECT COUNT(ID) FROM SONG_LIKES WHERE SONG_ID = :SONG_ID AND ROLE LIKE 'LIKE'", nativeQuery = true)
    int countAllBySongAndRoleLike(@Param("SONG_ID") long songId);

    @Query(value = "SELECT COUNT(ID) FROM SONG_LIKES WHERE songId = :SONG_ID AND ROLE LIKE 'DISLIKE'", nativeQuery = true)
	int countAllBySongAndRoleDislike(@Param("SONG_ID") long songId);

    @Override
    void deleteById(Long id);

    void deleteAllBySong(Song song);
}
