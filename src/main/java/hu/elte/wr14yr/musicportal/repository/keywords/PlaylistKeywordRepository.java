package hu.elte.wr14yr.musicportal.repository.keywords;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistKeywordRepository extends CrudRepository<PlaylistKeyword, Long> {
    @Override
    PlaylistKeyword save(PlaylistKeyword playlistKeyword);

    List<PlaylistKeyword> findAllByWordContainsAllIgnoreCase(String keyword);

    List<PlaylistKeyword> findAllByPlaylist(Playlist playlist);

    @Override
    void deleteById(Long id);
}
