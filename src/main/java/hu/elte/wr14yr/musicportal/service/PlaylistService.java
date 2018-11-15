package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.repository.PlaylistRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TagService tagService;

    private Logger logger = Logger.getLogger(PlaylistService.class.getName());

    public Playlist create(Playlist playlist, User user, List<Song> songs, List<PlaylistTag> playlistTags) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info("Playlist service: new playlist is going to be saved in database MusicPortal");

        playlist.setUser(user);
        playlist.setDate(new Date());
        playlist.setSongs(songs);
        Playlist savedPlaylist = playlistRepository.save(playlist);

        if(playlistTags != null) {
            tagService.createPlaylistTags(playlistTags, savedPlaylist);
        }

        logger.info("Playlist service: new playlist has been successfully saved in database MusicPortal");

        return savedPlaylist;
    }

    public Iterable<Playlist> listAll() {
        logger.info("Playlist service: every playlist in database MusicPortal are going to be listed");

        return playlistRepository.findAll();
    }

    public Iterable<Playlist> listFirstFive() {
        logger.info("Playlist service: first five playlists ordered " +
                "by their dates are going to be listed");

        return playlistRepository.findFirst5ByOrderByDateAsc();
    }

    public Iterable<Playlist> listByUser(long id) {
        logger.info(String.format("Playlist service: user with id %s's playlist are going to be listed", id));

        return playlistRepository.findAllByUserId(id);
    }

    public Playlist find(long id) {
        logger.info(String.format("Playlist service: playlist with id %s is going to be searched", id));

        Playlist playlist = playlistRepository.findPlaylistById(id);

        logger.info(String.format("Playlist service: playlist with id %s has been found successfully", id));

        return playlist;
    }

    public Playlist update(Playlist playlist, List<Song> songs, User user, List<PlaylistTag> playlistTags) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Playlist service: playlist named %s is going to be updated", playlist.getName()));

        playlist.setUser(user);
        playlist.setSongs(songs);
        playlist = playlistRepository.save(playlist);

        tagService.deleteAllPlaylistTagsByPlaylist(playlist);

        if(playlistTags != null) {
            tagService.createPlaylistTags(playlistTags, playlist);
        }

        logger.info(String.format("Playlist service: playlist named %s has been updated successfully", playlist.getName()));

        return playlist;
    }

    public void deleteAllByUser(User user) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Playlist service: user named %s's playlist are going to be deleted", user.getUsername()));

        Iterable<Playlist> userPlaylist = listByUser(user.getId());

        for(Playlist playlist : userPlaylist) {
            delete(playlist);
        }

        logger.info(String.format("Playlist service: user named %s's playlist have been deleted successfully", user.getUsername()));
    }

    public void delete(Playlist playlist) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Playlist service: playlist named %s" +
                " is going to be deleted from database MusicPortal", playlist.getName()));

        tagService.deleteAllPlaylistTagsByPlaylist(playlist);
        playlistRepository.deleteById(playlist.getId());

        logger.info(String.format("Playlist service: playlist named %s" +
                " has been successfully from database MusicPortal", playlist.getName()));
    }
}
