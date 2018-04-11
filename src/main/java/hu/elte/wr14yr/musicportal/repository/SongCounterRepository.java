package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongCounter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SongCounterRepository extends CrudRepository<SongCounter, Long> {
    @Override
    SongCounter save(SongCounter songCounter);

    int countAllBySongAndIpAddress(Song song, String ipAddress);

    @Override
    void deleteById(Long id);

    void deleteAllBySong(Song song);

    @Query(value = "UPDATE SONG_COUNTER SET userId = null WHERE USER_ID = :USER_ID", nativeQuery = true)
    void deleteUserId(@Param("USER_ID") long userId);
}
