package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistKeywordRepository extends CrudRepository<PlaylistKeyword, Long> {
    @Override
    PlaylistKeyword save(PlaylistKeyword playlistKeyword);

    List<PlaylistKeyword> findAllByWordContainsAllIgnoreCase(String keyword);

    @Override
    void deleteById(Long id);
}
