package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    Iterable<Playlist> findAllByNameContaining(String name);

    Iterable<Playlist> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    Iterable<Playlist> findAllByKeywordsContaining(String keyword);

    @Override
    void deleteById(Long id);
}
