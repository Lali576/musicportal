package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongCounter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SongCounterRepository extends CrudRepository<SongCounter, Long> {
    @Override
    SongCounter save(SongCounter songCounter);

    int countAllBySong(Song song);

    void deleteAllBySong(Song song);
}
