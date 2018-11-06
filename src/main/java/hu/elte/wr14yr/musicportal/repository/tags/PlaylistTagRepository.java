package hu.elte.wr14yr.musicportal.repository.tags;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistTagRepository extends CrudRepository<PlaylistTag, Long> {
    @Override
    PlaylistTag save(PlaylistTag playlistTag);

    List<PlaylistTag> findAllByPlaylist(Playlist playlist);

    @Override
    void deleteById(Long id);
}
