package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongCounter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SongCounterRepository extends CrudRepository<SongCounter, Long> {
    @Override
    SongCounter save(SongCounter songCounter);

    int countAllBySong(Song song);

    void deleteAllBySong(Song song);

    @Query(value = "UPDATE SONG_COUNTER SET USER_ID = null WHERE USER_ID = :USER_ID", nativeQuery = true)
    void deleteUserId(@Param("USER_ID") long userId);
}
