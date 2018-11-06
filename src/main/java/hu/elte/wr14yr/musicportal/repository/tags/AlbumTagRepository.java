package hu.elte.wr14yr.musicportal.repository.tags;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.tags.AlbumTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumTagRepository extends CrudRepository<AlbumTag, Long> {
    @Override
    AlbumTag save(AlbumTag albumTag);

    List<AlbumTag> findAllByAlbum(Album album);

    @Override
    void deleteById(Long id);
}
