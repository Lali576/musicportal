package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    List<Album> findAll();

    List<Album> findAllByTitleContainsAllIgnoreCase(String name);

    List<Album> findFirst5ByOrderByDateAsc();

    List<Album> findAllByUserId(long id);

    List<Album> findAllByGenre(Genre genre);

    List<Album> findAllByAlbumKeyword(AlbumKeyword albumKeyword);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);
}
