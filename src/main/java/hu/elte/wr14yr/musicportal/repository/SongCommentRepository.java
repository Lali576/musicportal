package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongComment;
import org.springframework.data.repository.CrudRepository;

public interface SongCommentRepository extends CrudRepository<SongComment, Long> {
    @Override
    SongComment save(SongComment songComment);

    Iterable<SongComment> findAllBySong(Song song);

    @Override
    void deleteById(Long id);

    void deleteAllBySong(Song song);
}
