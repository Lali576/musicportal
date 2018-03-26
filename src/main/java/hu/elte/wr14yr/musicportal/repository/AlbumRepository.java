package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    Optional<Set<Album>> findAllByNameContaining(String name);

    Optional<Set<Album>> findAllByKeywordsContaining(String keyword);

    Album findAlbumById(Long id);

    @Override
    void deleteById(Long id);
}
