package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Keyword;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.repository.PlaylistRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    public Playlist create(Playlist playlist, User user, List<Song> songs, List<Keyword> keywords) {
        playlist.setUser(user);
        playlist.setSongs(songs);
        playlist.setKeywords(keywords);

        return playlistRepository.save(playlist);
    }

    public Iterable<Playlist> list(User user) {
        if(user.getRole() == User.Role.ARTIST || user.getRole() == User.Role.USER) {
            return playlistRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Playlist find(long id) {
        Playlist playlist = playlistRepository.findPlaylistById(id);

        return playlist;
    }

    public Playlist update(Playlist playlist, List<Song> songs, User user) {
        playlist.setUser(user);
        playlist.setSongs(songs);
        return playlistRepository.save(playlist);
    }

    public void deleteAllByUser(User user) {
        for(Playlist playlist : user.getPlaylist()) {
            delete(playlist.getId());
        }
    }

    public void delete(long id) {
        playlistRepository.deleteById(id);
    }
}
