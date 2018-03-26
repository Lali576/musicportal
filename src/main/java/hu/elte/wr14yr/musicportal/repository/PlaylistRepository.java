package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Override
    Playlist save(Playlist playlist);

    Optional<Set<Playlist>> findAllByNameContaining(String name);

    Optional<Set<Playlist>> findAllByUser(User user);

    Playlist findPlaylistById(Long id);

    Optional<Set<Playlist>> findAllByKeywordsContaining(String keyword);

    @Override
    void deleteById(Long id);
}
