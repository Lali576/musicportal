package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    Iterable<Album> findAllById(Long iterable);

    Iterable<Album> findAllByNameContainsAllIgnoreCase(String name);

    Iterable<Album> findAllByKeywordsContainsAllIgnoreCase(String keyword);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);
}
