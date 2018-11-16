package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongCounter;
import org.springframework.data.repository.CrudRepository;

public interface SongCounterRepository extends CrudRepository<SongCounter, Long> {
    @Override
    SongCounter save(SongCounter songCounter);

    int countAllBySong(Song song);

    void deleteAllBySong(Song song);
}
