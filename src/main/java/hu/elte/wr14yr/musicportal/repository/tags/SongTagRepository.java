package hu.elte.wr14yr.musicportal.repository.tags;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.tags.SongTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongTagRepository extends CrudRepository<SongTag, Long> {
    @Override
    SongTag save(SongTag songTag);

    List<SongTag> findAllBySong(Song song);

    @Override
    void deleteById(Long id);
}
