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
import java.util.Set;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    public Playlist create(Playlist playlist, User user, Set<Song> songs, Set<Keyword> keywords) {

        for(Song song : songs) {
            songRepository.saveSongPlaylist(song.getId(), playlist.getId());
        }

        for(Keyword keyword : keywords) {
            playlistRepository.savePlaylistKeyword(playlist.getId(), keyword.getId());
        }

        return playlistRepository.save(playlist);
    }

    public Playlist find(long id) {
        Playlist playlist = playlistRepository.findPlaylistById(id);
        //playlist.getSongs(songRepository.findAllByPlaylists(playlist));
        return null;
    }

    public Iterable<Playlist> list(User user) {
        if(user.getRole() == User.Role.ARTIST || user.getRole() == User.Role.USER) {
            return playlistRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Playlist update(Playlist playlist) {
        return create(playlist, playlist.getUser(), playlist.getSongs(), playlist.getKeywords());
    }

    public void delete() {

    }
}
