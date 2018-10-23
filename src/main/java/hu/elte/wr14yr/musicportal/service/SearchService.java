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

    public Iterable<Album> findAlbumsByTitle(String title) {
        logger.log(Level.INFO, "Search service: albums with title " +
                title + " are going to be searched");
        return albumRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Album> findAlbumByAlbumKeyword(AlbumKeyword albumKeyword) {
        logger.log(Level.INFO, "Search service: albums with current album keywords are going to be searched", albumKeyword);

        return albumRepository.findAllByAlbumKeyword(albumKeyword);
    }

    public Iterable<Album> findAlbumsByGenre(Genre genre) {
        logger.log(Level.INFO, "Search service: albums with current genres are going to be searched", genre);

        return albumRepository.findAllByGenre(genre);
    }

    public Iterable<Song> findSongsByTitle(String title) {
        logger.log(Level.INFO, "Search service: songs with title " +
                title + " are going to be searched");

        return songRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Song> findSongsBySongKeywords(SongKeyword songKeyword) {
        logger.log(Level.INFO, "Search service: users with current user keywords are going to be searched", songKeyword);

        return songRepository.findAllBySongKeyword(songKeyword);
    }

    public Iterable<Song> findSongsByGenre(Genre genre) {
        logger.log(Level.INFO, "Search service: songs with current genres are going to be searched", genre);

        return songRepository.findAllByGenre(genre);
    }

    public Iterable<Playlist> findPlaylistByName(String name) {
        logger.log(Level.INFO, "Search service: playlists with name " +
                name + " are going to be searched");

        return playlistRepository.findAllByNameContainsAllIgnoreCase(name);
    }
    public Iterable<Playlist> findPlaylistByPlaylistKeyword(PlaylistKeyword playlistKeyword) {
        logger.log(Level.INFO, "Search service: playlists with current playlist keywords are going to be searched", playlistKeyword);

        return playlistRepository.findAllByPlaylistKeyword(playlistKeyword);
    }

    public Iterable<User> findUsersByUsername(String username) {
        logger.log(Level.INFO, "Search service: users with username " +
                username + " are going to be searched");

        return userRepository.findAllByUsernameContainsAllIgnoreCase(username);
    }

    public Iterable<User> findUsersByUserKeyword(UserKeyword userKeyword) {
        logger.log(Level.INFO, "Search service: users with current user keywords are going to be searched", userKeyword);

        return userRepository.findAllByUserKeyword(userKeyword);
    }
}