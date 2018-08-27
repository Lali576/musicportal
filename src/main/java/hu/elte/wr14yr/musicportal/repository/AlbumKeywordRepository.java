package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumKeywordRepository extends CrudRepository<AlbumKeyword, Long> {
    @Override
    AlbumKeyword save(AlbumKeyword albumKeyword);

    List<AlbumKeyword> findAllByWordContainsAllIgnoreCase(String keyword);

    @Override
    void deleteById(Long id);
}
