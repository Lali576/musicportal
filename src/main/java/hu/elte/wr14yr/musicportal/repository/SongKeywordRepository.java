package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongKeywordRepository extends CrudRepository<SongKeyword, Long> {
    @Override
    SongKeyword save(SongKeyword songKeyword);

    List<SongKeyword> findAllByWordContainsAllIgnoreCase(String keyword);

    @Override
    void deleteById(Long id);
}
