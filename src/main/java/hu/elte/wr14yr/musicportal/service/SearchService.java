package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private KeywordRepository keywordRepository;

    public Iterable<Genre> findGenreByName(String name) {
        return genreRepository.findAllByNameContainsAllIgnoreCase(name);
    }

    public Iterable<Keyword> findKeywordByWord(String word) {
        return keywordRepository.findAllByWordContainsAllIgnoreCase(word);
    }

    public Iterable<Album> findAlbumByName(String name) {
        return albumRepository.findAllByTitleContainsAllIgnoreCase(name);
    }

    public Iterable<Album> findAlbumByKeyword(Keyword keyword) {
        return albumRepository.findAllByKeywords(keyword);
    }

    public Iterable<Album> findAlbumByGenre(Genre genre) {
        return albumRepository.findAllByGenres(genre);
    }

    public Iterable<Song> findSongByTitle(String title) {
        return songRepository.findAllByTitleContainsAllIgnoreCase(title);
    }

    public Iterable<Song> findSongByKeyword(Keyword keyword) {
        return songRepository.findAllByKeywords(keyword);
    }

    public Iterable<Song> findSongByGenre(Genre genre) {
        return songRepository.findAllByGenres(genre);
    }

    public Iterable<Playlist> findPlaylistByName(String name) {
        return playlistRepository.findAllByNameContainsAllIgnoreCase(name);
    }
    public Iterable<Playlist> findPlaylistByKeyword(Keyword keyword) {
        return playlistRepository.findAllByKeywords(keyword);
    }

    public Iterable<User> findUserByUsername(String username) {
        return userRepository.findAllByUsernameContainsAllIgnoreCase(username);
    }

    public Iterable<User> findUserByKeyword(Keyword keyword) {
        return userRepository.findAllByKeywords(keyword);
    }
}
