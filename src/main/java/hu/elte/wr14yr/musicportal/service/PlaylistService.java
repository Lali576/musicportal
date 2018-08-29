package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.PlaylistKeyword;
import hu.elte.wr14yr.musicportal.repository.PlaylistRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private KeywordService keywordService;

    private Logger logger = Logger.getLogger(PlaylistService.class.getName());

    public Playlist create(Playlist playlist, User user, List<Song> songs, List<PlaylistKeyword> playlistKeywords) {
        logger.log(Level.INFO, "Playlist service: new playlist is going to be saved in database MusicPortal");
        playlist.setUser(user);
        playlist.setSongs(songs);
        Playlist savedPlaylist = playlistRepository.save(playlist);
        logger.log(Level.INFO, "Playlist service: new playlist has been successfully saved in database MusicPortal");
        keywordService.createPlaylistKeywords(playlistKeywords, savedPlaylist);

        return savedPlaylist;
    }

    public Iterable<Playlist> listAll() {
        logger.log(Level.INFO, "Every playlist in database MusicPortal are going to be listed");

        return playlistRepository.findAll();
    }

    public Iterable<Playlist> list(User user) {
        logger.log(Level.INFO, "Playlist service: user named " +
                user.getUsername() + "'s playlist are going to be listed");

        return playlistRepository.findAllByUser(user);
    }

    public Playlist find(long id) {
        logger.log(Level.INFO, "Playlist service: playlist with id " +
                id + " is going to be searched");
        Playlist playlist = playlistRepository.findPlaylistById(id);
        logger.log(Level.INFO, "Playlist service: playlist with id " +
                id + " has been found successfully");

        return playlist;
    }

    public Playlist update(Playlist playlist, List<Song> songs, User user, List<PlaylistKeyword> keywordList) {
        logger.log(Level.INFO, "Playlist service: playlist named " +
                playlist.getName() + " is going to be updated");
        playlist.setUser(user);
        playlist.setSongs(songs);
        playlist.setPlaylistKeywords(keywordList);
        playlist = playlistRepository.save(playlist);
        logger.log(Level.INFO, "Playlist service: playlist named " +
                playlist.getName() + " has been updated successfully");

        return playlist;
    }

    public void deleteAllByUser(User user) {
        logger.log(Level.INFO, "Playlist service: user named " +
                user.getUsername() + "'s playlist are going to be deleted");
        for(Playlist playlist : user.getPlaylist()) {
            delete(playlist);
        }
        logger.log(Level.INFO, "Playlist service: user named " +
                user.getUsername() + "'s playlist have been deleted successfully");
    }

    public void delete(Playlist playlist) {
        logger.log(Level.INFO, "Playlist service: playlist named " +
                playlist.getName() + " is going to be deleted from database MusicPortal");
        keywordService.deleteAllPlaylistKeywordsByPlaylist(playlist);
        playlistRepository.deleteById(playlist.getId());
        logger.log(Level.INFO, "Playlist service: playlist named " +
                playlist.getName() + " has been successfully from database MusicPortal");
    }
}
