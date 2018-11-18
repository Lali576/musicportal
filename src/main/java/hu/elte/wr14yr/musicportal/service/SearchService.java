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

    public Iterable<Album> findAlbumsByTitle(String searchWord) {
        logger.info(String.format("Search service: albums with title %s" +
                " are going to be searched", searchWord));

        return albumRepository.findAllByTitleContainsAllIgnoreCase(searchWord);
    }

    public Iterable<Album> findAlbumByAlbumTag(String tagWord) {
        logger.info("Search service: albums with current album tag are going to be searched");

        return albumRepository.findAllByAlbumTagsWordContainsAllIgnoreCase(tagWord);
    }

    public Iterable<Album> findAlbumsByGenre(String genreWord) {
        logger.info("Search service: albums with current genres are going to be searched");

        return albumRepository.findAllByGenresNameEquals(genreWord);
    }

    public Iterable<Song> findSongsByTitle(String searchWord) {
        logger.info(String.format("Search service: songs with title %s are going to be searched", searchWord));

        return songRepository.findAllByTitleContainsAllIgnoreCase(searchWord);
    }

    public Iterable<Playlist> findPlaylistByName(String searchWord) {
        logger.info(String.format("Search service: playlists with name %s are going to be searched", searchWord));

        return playlistRepository.findAllByNameContainsAllIgnoreCase(searchWord);
    }
    public Iterable<Playlist> findPlaylistByPlaylistTag(String tagWord) {
        logger.info("Search service: playlists with current playlist tag are going to be searched");

        return playlistRepository.findAllByPlaylistTagsWordContainsAllIgnoreCase(tagWord);
    }

    public Iterable<User> findUsersByUserTag(String tagWord) {
        logger.info("Search service: users with current user tag are going to be searched");

        return userRepository.findAllByUserTagsWordContainsAllIgnoreCase(tagWord);
    }

    public Iterable<User> findUsersByGenre(String genreWord) {
        logger.info("Search service: users with current favourite genre are going to be searched");

        return userRepository.findAllByFavGenreIdNameEquals(genreWord);
    }
}