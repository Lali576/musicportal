package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    List<Playlist> findAll();

    List<Playlist> findAllByNameContainsAllIgnoreCase(String name);

    List<Playlist> findAllByPlaylistKeywords(List<PlaylistKeyword> playlistKeywords);

    List<Playlist> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    @Override
    void deleteById(Long id);
}
