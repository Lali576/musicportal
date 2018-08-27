package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    List<Playlist> findAllByNameContainsAllIgnoreCase(String name);

    //List<Playlist> findAllByKeywords(Keyword keyword);

    List<Playlist> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    @Override
    void deleteById(Long id);
}
