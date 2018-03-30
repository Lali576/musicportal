package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.SongLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SongLikeRepository extends CrudRepository<SongLike, Long> {
    @Override
    SongLike save(SongLike songLike);

    int countAllBySongAndRole(Song song, SongLike.Role role);

    @Override
    void deleteById(Long id);

    void deleteAllBySong(Song song);
}
