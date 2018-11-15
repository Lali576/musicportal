package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.tags.*;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private Logger logger = Logger.getLogger(SearchService.class.getName());

    public Iterable<Album> findAlbumsByTitle(String title) {
        logger.info(String.format("Search service: albums with title %s" +
                " are going to be searched", title));

        return albumRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Album> findAlbumByAlbumTag(AlbumTag albumTag) {
        logger.info("Search service: albums with current album tag are going to be searched");

        return albumRepository.findAllByAlbumTags(albumTag);
    }

    public Iterable<Album> findAlbumsByGenre(Genre genre) {
        logger.info("Search service: albums with current genres are going to be searched");

        return albumRepository.findAllByGenres(genre);
    }

    public Iterable<Song> findSongsByTitle(String title) {
        logger.info(String.format("Search service: songs with title %s are going to be searched", title));

        return songRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Playlist> findPlaylistByName(String name) {
        logger.info(String.format("Search service: playlists with name %s are going to be searched", name));

        return playlistRepository.findAllByNameContainsAllIgnoreCase(name);
    }
    public Iterable<Playlist> findPlaylistByPlaylistTag(PlaylistTag playlistTag) {
        logger.info("Search service: playlists with current playlist tag are going to be searched");

        return playlistRepository.findAllByPlaylistTags(playlistTag);
    }

    public Iterable<User> findUsersByUsername(String username) {
        logger.info(String.format("Search service: users with username %s are going to be searched", username));

        return userRepository.findAllByUsernameContainsAllIgnoreCase(username);
    }

    public Iterable<User> findUsersByUserTag(UserTag userTag) {
        logger.info("Search service: users with current user tag are going to be searched");

        return userRepository.findAllByUserTags(userTag);
    }
}