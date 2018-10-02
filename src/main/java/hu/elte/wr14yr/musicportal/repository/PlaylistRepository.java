package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    List<Playlist> findAll();

    List<Playlist> findAllByNameContainsAllIgnoreCase(String name);

    List<Playlist> findAllByUserId(Long id);

    List<Playlist> findFirst5ByOrderByDateAsc();

    List<Playlist> findAllByPlaylistKeywords(List<PlaylistKeyword> playlistKeywords);

    Playlist findPlaylistById(Long id);


    @Override
    void deleteById(Long id);
}
