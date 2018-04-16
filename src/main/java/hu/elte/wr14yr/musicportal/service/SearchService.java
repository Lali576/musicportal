package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import hu.elte.wr14yr.musicportal.repository.PlaylistRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import hu.elte.wr14yr.musicportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Album> findAlbumByName(String name) {
        return albumRepository.findAllByNameContainsAllIgnoreCase(name);
    }

    public Iterable<Song> findSongByTitle(String title) {
        return songRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Playlist> findPlaylistByName(String name) {
        return playlistRepository.findAllByNameContainsAllIgnoreCase(name);
    }

    public Iterable<User> findUserByUsername(String username) {
        return userRepository.findAllByUsernameContainsAllIgnoreCase(username);
    }
}
