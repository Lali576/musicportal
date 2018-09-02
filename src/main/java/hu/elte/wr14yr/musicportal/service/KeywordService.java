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
import java.util.logging.Level;
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
        logger.log(Level.INFO, " Keyword service: user keywords for user named " +
                user.getUsername() + " are going to be saved in database MusicPortal");
        for (UserKeyword userKeyword : userKeywords) {
            userKeyword.setUser(user);
            userKeywordRepository.save(userKeyword);
        }
        logger.log(Level.INFO, " Keyword service: user keywords for user named " +
                user.getUsername() + " have been successfully saved in database MusicPortal");
    }

    public void createAlbumKeywords(List<AlbumKeyword> albumKeywords, Album album) {
        logger.log(Level.INFO, " Keyword service: album keywords for album titled " +
                album.getTitle() + " are going to be saved in database MusicPortal");
        for (AlbumKeyword albumKeyword : albumKeywords) {
            albumKeyword.setAlbum(album);
            albumKeywordRepository.save(albumKeyword);
        }
        logger.log(Level.INFO, " Keyword service: album keywords for album titled " +
                album.getTitle() + " have been successfully saved in database MusicPortal");
    }

    public void createSongKeywords(List<SongKeyword> songKeywords, Song song) {
        logger.log(Level.INFO, " Keyword service: song keywords for song titled " +
                song.getTitle() + " are going to be saved in database MusicPortal");
        for (SongKeyword songKeyword : songKeywords) {
            songKeyword.setSong(song);
            songKeywordRepository.save(songKeyword);
        }
        logger.log(Level.INFO, " Keyword service: song keywords for song titled " +
                song.getTitle() + " have been successfully saved in database MusicPortal");
    }

    public void createPlaylistKeywords(List<PlaylistKeyword> playlistKeywords, Playlist playlist) {
        logger.log(Level.INFO, " Keyword service: playlist keywords for playlist named " +
                playlist.getName() + " are going to be saved in database MusicPortal");
        for (PlaylistKeyword playlistKeyword : playlistKeywords) {
            playlistKeyword.setPlaylist(playlist);
            playlistKeywordRepository.save(playlistKeyword);
        }
        logger.log(Level.INFO, " Keyword service: playlist keywords for playlist named " +
                playlist.getName() + " have been successfully saved in database MusicPortal");
    }

    public void deleteAllUserKeywordsByUser(User user) {
        logger.log(Level.INFO, " Keyword service: playlist keywords for user named " +
                user.getUsername() + " are going to be deleted from database MusicPortal");
        List<UserKeyword> userKeywords = userKeywordRepository.findByUser(user);
        for (UserKeyword userKeyword: userKeywords) {
            userKeywordRepository.deleteById(userKeyword.getId());
        }
        logger.log(Level.INFO, " Keyword service: playlist keywords for user named " +
                user.getUsername() + " have been successfully deleted from database MusicPortal");
    }

    public void deleteAllAlbumKeywordsByAlbum(Album album) {
        logger.log(Level.INFO, " Keyword service: album keywords for album titled " +
                album.getTitle() + " are going to be deleted from database MusicPortal");
        List<AlbumKeyword> albumKeywords = albumKeywordRepository.findAllByAlbum(album);
        for (AlbumKeyword albumKeyword : albumKeywords) {
            albumKeywordRepository.deleteById(albumKeyword.getId());
        }
        logger.log(Level.INFO, " Keyword service: album keywords for album titled " +
                album.getTitle() + " have been successfully deleted from database MusicPortal");
    }

    public void deleteAllSongKeywordsBySong(Song song) {
        logger.log(Level.INFO, " Keyword service: song keywords for song titled " +
                song.getTitle() + " are going to be deleted from database MusicPortal");
        List<SongKeyword> songKeywords = songKeywordRepository.findAllBySong(song);
        for (SongKeyword songKeyword : songKeywords) {
            songKeywordRepository.deleteById(songKeyword.getId());
        }
        logger.log(Level.INFO, " Keyword service: song keywords for song titled " +
                song.getTitle() + " have been successfully deleted from database MusicPortal");
    }

    public void deleteAllPlaylistKeywordsByPlaylist(Playlist playlist) {
        logger.log(Level.INFO, " Keyword service: playlist keywords for playlist named " +
                playlist.getName() + " are going to be deleted from database MusicPortal");
        List<PlaylistKeyword> playlistKeywords = playlistKeywordRepository.findAllByPlaylist(playlist);
        for (PlaylistKeyword playlistKeyword : playlistKeywords) {
            playlistKeywordRepository.deleteById(playlistKeyword.getId());
        }
        logger.log(Level.INFO, " Keyword service: playlist keywords for playlist named " +
                playlist.getName() + " have been successfully deleted from database MusicPortal");
    }
}
