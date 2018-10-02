package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import hu.elte.wr14yr.musicportal.model.keywords.UserKeyword;
import hu.elte.wr14yr.musicportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Autowired
    private GenreRepository genreRepository;

    private Logger logger = Logger.getLogger(SearchService.class.getName());

    public Iterable<Genre> findGenreByName(String name) {
        logger.log(Level.INFO, "Search service: genres with name " +
                name + " are going to be searched");
        return genreRepository.findAllByNameContainsAllIgnoreCase(name);
    }

    public Iterable<Album> findAlbumsByTitle(String title) {
        logger.log(Level.INFO, "Search service: albums with title " +
                title + " are going to be searched");
        return albumRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Album> findAlbumByAlbumKeywords(List<AlbumKeyword> albumKeywords) {
        logger.log(Level.INFO, "Search service: albums with current album keywords are going to be searched", albumKeywords);

        return albumRepository.findAllByAlbumKeywords(albumKeywords);
    }

    public Iterable<Album> findAlbumsByGenre(List<Genre> genres) {
        logger.log(Level.INFO, "Search service: albums with current genres are going to be searched", genres);

        return albumRepository.findAllByGenres(genres);
    }

    public Iterable<Song> findSongsByTitle(String title) {
        logger.log(Level.INFO, "Search service: songs with title " +
                title + " are going to be searched");

        return songRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Song> findSongsBySongKeywords(List<SongKeyword> songKeywords) {
        logger.log(Level.INFO, "Search service: users with current user keywords are going to be searched", songKeywords);

        return songRepository.findAllBySongKeywords(songKeywords);
    }

    public Iterable<Song> findSongsByGenres(List<Genre> genres) {
        logger.log(Level.INFO, "Search service: songs with current genres are going to be searched", genres);

        return songRepository.findAllByGenres(genres);
    }

    public Iterable<Playlist> findPlaylistByName(String name) {
        logger.log(Level.INFO, "Search service: playlists with name " +
                name + " are going to be searched");

        return playlistRepository.findAllByNameContainsAllIgnoreCase(name);
    }
    public Iterable<Playlist> findPlaylistByPlaylistKeywords(List<PlaylistKeyword> playlistKeywords) {
        logger.log(Level.INFO, "Search service: playlists with current playlist keywords are going to be searched", playlistKeywords);

        return playlistRepository.findAllByPlaylistKeywords(playlistKeywords);
    }

    public Iterable<User> findUsersByUsername(String username) {
        logger.log(Level.INFO, "Search service: users with username " +
                username + " are going to be searched");

        return userRepository.findAllByUsernameContainsAllIgnoreCase(username);
    }

    public Iterable<User> findUsersByUserKeywords(List<UserKeyword> userKeywords) {
        logger.log(Level.INFO, "Search service: users with current user keywords are going to be searched", userKeywords);

        return userRepository.findAllByUserKeywords(userKeywords);
    }
}