package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongComment;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SongCommentRepository extends CrudRepository<SongComment, Long> {
    @Override
    SongComment save(SongComment songComment);

    Set<SongComment> findAllBySong(Song song);

    void deleteAllBySong(Song song);
}
