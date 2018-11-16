package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongCommentRepository extends CrudRepository<SongComment, Long> {
    @Override
    SongComment save(SongComment songComment);

    List<SongComment> findAllBySong(Song song);

    @Transactional
    void deleteAllBySong(Song song);
}
