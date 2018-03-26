package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongCounter;
import org.springframework.data.repository.CrudRepository;

public interface SongCounterRepository extends CrudRepository<SongCounter, Long> {
    @Override
    SongCounter save(SongCounter songCounter);

    int countAllBySongAndIpAddress(Song song, String ipAddress);

    @Override
    void deleteById(Long id);
}
