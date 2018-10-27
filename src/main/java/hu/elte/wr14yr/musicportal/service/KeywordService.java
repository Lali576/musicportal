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
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
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

    void createUserKeywords(List<UserKeyword> userKeywords, User user) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Keyword service: user keywords for user named %s" +
                " are going to be saved in database MusicPortal", user.getUsername()));

        userKeywords.stream().forEach(k -> {
            k.setUser(user);
            userKeywordRepository.save(k);
        });

        logger.info(String.format("Keyword service: user keywords for user named %s" +
                " have been successfully saved in database MusicPortal", user.getUsername()));
    }

    void createAlbumKeywords(List<AlbumKeyword> albumKeywords, Album album) {
        logger.info(String.format("Keyword service: album keywords for album titled %s" +
                " are going to be saved in database MusicPortal", album.getTitle()));

        albumKeywords.stream().forEach(k -> {
            k.setAlbum(album);
            albumKeywordRepository.save(k);
        });

        logger.info(String.format("Keyword service: album keywords for album titled %s" +
                " have been successfully saved in database MusicPortal", album.getTitle()));
    }

    void createSongKeywords(List<SongKeyword> songKeywords, Song song) {
        logger.info(String.format("Keyword service: song keywords for song titled %s" +
                " are going to be saved in database MusicPortal", song.getTitle()));

        songKeywords.stream().forEach(k -> {
            k.setSong(song);
            songKeywordRepository.save(k);
        });

        logger.info(String.format("Keyword service: song keywords for song titled %s" +
                " have been successfully saved in database MusicPortal", song.getTitle()));
    }

    void createPlaylistKeywords(List<PlaylistKeyword> playlistKeywords, Playlist playlist) {
        logger.info(String.format("Keyword service: playlist keywords for playlist named %s" +
                " are going to be saved in database MusicPortal", playlist.getName()));

        playlistKeywords.stream().forEach(k -> {
            k.setPlaylist(playlist);
            playlistKeywordRepository.save(k);
        });

        logger.info(String.format("Keyword service: playlist keywords for playlist named %s" +
                " have been successfully saved in database MusicPortal", playlist.getName()));
    }

    void deleteAllUserKeywordsByUser(User user) {
        logger.info(String.format("Keyword service: playlist keywords for user named %s" +
                " are going to be deleted from database MusicPortal", user.getUsername()));

        List<UserKeyword> userKeywords = userKeywordRepository.findByUser(user);
        userKeywords.stream().forEach(k ->
            userKeywordRepository.deleteById(k.getId())
        );

        logger.info(String.format("Keyword service: playlist keywords for user named %s" +
                " have been successfully deleted from database MusicPortal", user.getUsername()));
    }

    void deleteAllAlbumKeywordsByAlbum(Album album) {
        logger.info(String.format("Keyword service: album keywords for album titled %s" +
                " are going to be deleted from database MusicPortal", album.getTitle()));

        List<AlbumKeyword> albumKeywords = albumKeywordRepository.findAllByAlbum(album);
        albumKeywords.stream().forEach(k ->
            albumKeywordRepository.deleteById(k.getId())
        );

        logger.info(String.format("Keyword service: album keywords for album titled %s" +
                " have been successfully deleted from database MusicPortal", album.getTitle()));
    }

    void deleteAllSongKeywordsBySong(Song song) {
        logger.info(String.format("Keyword service: song keywords for song titled %s" +
                " are going to be deleted from database MusicPortal", song.getTitle()));

        List<SongKeyword> songKeywords = songKeywordRepository.findAllBySong(song);
        songKeywords.stream().forEach(k ->
            songKeywordRepository.deleteById(k.getId())
        );

        logger.info(String.format("Keyword service: song keywords for song titled %s" +
                " have been successfully deleted from database MusicPortal", song.getTitle()));
    }

    void deleteAllPlaylistKeywordsByPlaylist(Playlist playlist) {
        logger.info(String.format("Keyword service: playlist keywords for playlist named %s" +
                " are going to be deleted from database MusicPortal", playlist.getName()));

        List<PlaylistKeyword> playlistKeywords = playlistKeywordRepository.findAllByPlaylist(playlist);
        playlistKeywords.stream().forEach(k ->
            playlistKeywordRepository.deleteById(k.getId())
        );

        logger.info(String.format("Keyword service: playlist keywords for playlist named %s" +
                " have been successfully deleted from database MusicPortal", playlist.getName()));
    }
}