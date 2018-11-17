package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.tags.AlbumTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    List<Album> findAll();

    List<Album> findAllByTitleContainsAllIgnoreCase(String name);

    List<Album> findFirst5ByOrderByDateAsc();

    List<Album> findAllByUserId(long id);

    List<Album> findAllByGenres(Genre genre);

    List<Album> findAllByAlbumTagsWordContainsAllIgnoreCase(String albumTagWord);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);
}
