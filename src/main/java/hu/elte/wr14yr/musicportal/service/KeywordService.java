package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.UserKeyword;
import hu.elte.wr14yr.musicportal.repository.keywords.AlbumKeywordRepository;
import hu.elte.wr14yr.musicportal.repository.keywords.PlaylistKeywordRepository;
import hu.elte.wr14yr.musicportal.repository.keywords.SongKeywordRepository;
import hu.elte.wr14yr.musicportal.repository.keywords.UserKeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class KeywordService {

    @Autowired
    private UserKeywordRepository userKeywordRepository;

    @Autowired
    private AlbumKeywordRepository albumKeywordRepository;

    @Autowired
    private SongKeywordRepository songKeywordRepository;

    @Autowired
    private PlaylistKeywordRepository playlistKeywordRepository;

    private Logger logger = Logger.getLogger(KeywordService.class.getName());

    public void createUserKeywords(List<UserKeyword> userKeywords, User user) {
        for (UserKeyword userKeyword : userKeywords) {
            userKeyword.setUser(user);
            userKeywordRepository.save(userKeyword);
        }
    }

    public void createAlbumKeywords(List<AlbumKeyword> albumKeywords, Album album) {
        for (AlbumKeyword albumKeyword : albumKeywords) {
            albumKeyword.setAlbum(album);
            albumKeywordRepository.save(albumKeyword);
        }
    }

    public void createSongKeywords(List<SongKeyword> songKeywords, Song song) {
        for (SongKeyword songKeyword : songKeywords) {
            songKeyword.setSong(song);
            songKeywordRepository.save(songKeyword);
        }
    }

    public void createPlaylistKeywords(List<PlaylistKeyword> playlistKeywords, Playlist playlist) {
        for (PlaylistKeyword playlistKeyword : playlistKeywords) {
            playlistKeyword.setPlaylist(playlist);
            playlistKeywordRepository.save(playlistKeyword);
        }
    }

    public void deleteAllUserKeywordsByUser(User user) {
        for (UserKeyword userKeyword: user.getUserKeywords()) {
            userKeywordRepository.deleteById(userKeyword.getId());
        }
    }

    public void deleteAllAlbumKeywordsByAlbum(Album album) {
        for (AlbumKeyword albumKeyword : album.getAlbumKeywords()) {
            albumKeywordRepository.deleteById(albumKeyword.getId());
        }
    }

    public void deleteAllSongKeywordsBySong(Song song) {
        for (SongKeyword songKeyword : song.getSongKeywords()) {
            songKeywordRepository.deleteById(songKeyword.getId());
        }
    }

    public void deleteAllPlaylistKeywordsByPlaylist(Playlist playlist) {
        for (PlaylistKeyword playlistKeyword : playlist.getPlaylistKeywords()) {
            playlistKeywordRepository.deleteById(playlistKeyword.getId());
        }
    }
}
