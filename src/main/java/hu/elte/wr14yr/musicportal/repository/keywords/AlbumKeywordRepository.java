package hu.elte.wr14yr.musicportal.repository.keywords;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumKeywordRepository extends CrudRepository<AlbumKeyword, Long> {
    @Override
    AlbumKeyword save(AlbumKeyword albumKeyword);

    List<AlbumKeyword> findAllByWordContainsAllIgnoreCase(String keyword);

    List<AlbumKeyword> findAllByAlbum(Album album);

    @Override
    void deleteById(Long id);
}
