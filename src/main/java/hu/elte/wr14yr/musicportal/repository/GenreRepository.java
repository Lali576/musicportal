package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    @Override
    Genre save(Genre genre);

    List<Genre> findAll();

    List<Genre> findAllByAlbums(Album album);
}