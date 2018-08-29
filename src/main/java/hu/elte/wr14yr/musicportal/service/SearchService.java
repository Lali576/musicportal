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

    @Autowired
    private KeywordService keywordService;

    private Logger logger = Logger.getLogger(SearchService.class.getName());

    public Iterable<Genre> findGenreByName(String name) {
        return genreRepository.findAllByNameContainsAllIgnoreCase(name);
    }

    /*
    public Iterable<Keyword> findKeywordByWord(String word) {
        return keywordRepository.findAllByWordContainsAllIgnoreCase(word);
    }
    */

    public Iterable<Album> findAlbumByName(String name) {
        return albumRepository.findAllByTitleContainsAllIgnoreCase(name);
    }

    public Iterable<Album> findAlbumByAlbumKeywords(List<AlbumKeyword> albumKeywords) {
        return albumRepository.findAllByAlbumKeywords(albumKeywords);
    }

    public Iterable<Album> findAlbumByGenre(Genre genre) {
        return albumRepository.findAllByGenres(genre);
    }

    public Iterable<Song> findSongByTitle(String title) {
        return songRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Song> findSongBySongKeywords(List<SongKeyword> songKeywords) {
        return songRepository.findAllBySongKeywords(songKeywords);
    }

    public Iterable<Song> findSongByGenre(Genre genre) {
        return songRepository.findAllByGenres(genre);
    }

    public Iterable<Playlist> findPlaylistByName(String name) {
        return playlistRepository.findAllByNameContainsAllIgnoreCase(name);
    }
    public Iterable<Playlist> findPlaylistByPlaylistKeywords(List<PlaylistKeyword> playlistKeywords) {
        return playlistRepository.findAllByPlaylistKeywords(playlistKeywords);
    }

    public Iterable<User> findUserByUsername(String username) {
        return userRepository.findAllByUsernameContainsAllIgnoreCase(username);
    }

    public Iterable<User> findUsersByUserKeywords(List<UserKeyword> userKeywords) {
        return userRepository.findAllByUserKeywords(userKeywords);
    }
}
