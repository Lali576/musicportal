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

    @Query(value = "UPDATE SONGCOUNTER SET userId = null WHERE userId = :userId", nativeQuery = true)
    void deleteUserId(@Param("userId") int userId);
}
