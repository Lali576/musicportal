package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.Song;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.tags.*;
import hu.elte.wr14yr.musicportal.model.tags.PlaylistTag;
import hu.elte.wr14yr.musicportal.model.tags.UserTag;
import hu.elte.wr14yr.musicportal.repository.tags.AlbumTagRepository;
import hu.elte.wr14yr.musicportal.repository.tags.PlaylistTagRepository;
import hu.elte.wr14yr.musicportal.repository.tags.UserTagRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TagService {

    @Autowired
    private UserTagRepository userTagRepository;

    @Autowired
    private AlbumTagRepository albumTagRepository;

    @Autowired
    private PlaylistTagRepository playlistTagRepository;

    private Logger logger = Logger.getLogger(TagService.class.getName());

    void createUserTags(List<UserTag> userTags, User user) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Tag service: user tags for user named %s" +
                " are going to be saved in database MusicPortal", user.getUsername()));

        userTags.stream().forEach(t -> {
            t.setUser(user);
            userTagRepository.save(t);
        });

        logger.info(String.format("Tag service: user tags for user named %s" +
                " have been successfully saved in database MusicPortal", user.getUsername()));
    }

    void createAlbumTags(List<AlbumTag> albumTags, Album album) {
        logger.info(String.format("Tag service: album tags for album titled %s" +
                " are going to be saved in database MusicPortal", album.getTitle()));

        albumTags.stream().forEach(t -> {
            t.setAlbum(album);
            albumTagRepository.save(t);
        });

        logger.info(String.format("Tag service: album tags for album titled %s" +
                " have been successfully saved in database MusicPortal", album.getTitle()));
    }

    void createPlaylistTags(List<PlaylistTag> playlistTags, Playlist playlist) {
        logger.info(String.format("Tag service: playlist tags for playlist named %s" +
                " are going to be saved in database MusicPortal", playlist.getName()));

        playlistTags.stream().forEach(t -> {
            t.setPlaylist(playlist);
            playlistTagRepository.save(t);
        });

        logger.info(String.format("Tag service: playlist tags for playlist named %s" +
                " have been successfully saved in database MusicPortal", playlist.getName()));
    }

    public List<UserTag> listUserTagsByUser(final long id) {
        logger.info(String.format("Tag service: user with id %s's tags are going to be listed", id));

        return userTagRepository.findAllByUserId(id);
    }

    public List<AlbumTag> listAlbumTagsByAlbum(final Album album) {
        logger.info(String.format("Tag service: album titled %s's tags are going to be listed", album.getTitle()));

        return albumTagRepository.findAllByAlbum(album);
    }

    public List<PlaylistTag> listPlaylistTagsByPlaylist(final Playlist playlist) {
        logger.info(String.format("Tag service: playlist named %s's tags are going to be listed", playlist.getName()));

        return playlistTagRepository.findAllByPlaylist(playlist);}

    void deleteAllUserTagsByUser(long id) {
        logger.info(String.format("Tag service: user tags for user with id %s" +
                " are going to be deleted from database MusicPortal", id));

        List<UserTag> userTags = userTagRepository.findAllByUserId(id);
        userTags.stream().forEach(t->
            userTagRepository.deleteById(t.getId())
        );

        logger.info(String.format("Tag service: user tags for user with id %s" +
                " have been successfully deleted from database MusicPortal",id));
    }

    void deleteAllAlbumTagsByAlbum(Album album) {
        logger.info(String.format("Tag service: album tags for album titled %s" +
                " are going to be deleted from database MusicPortal", album.getTitle()));

        List<AlbumTag> albumTags = albumTagRepository.findAllByAlbum(album);
        albumTags.stream().forEach(t ->
            albumTagRepository.deleteById(t.getId())
        );

        logger.info(String.format("Tag service: album tags for album titled %s" +
                " have been successfully deleted from database MusicPortal", album.getTitle()));
    }

    void deleteAllPlaylistTagsByPlaylist(Playlist playlist) {
        logger.info(String.format("Tag service: playlist tags for playlist named %s" +
                " are going to be deleted from database MusicPortal", playlist.getName()));

        List<PlaylistTag> playlistTags = playlistTagRepository.findAllByPlaylist(playlist);
        playlistTags.stream().forEach(t ->
            playlistTagRepository.deleteById(t.getId())
        );

        logger.info(String.format("Tag service: playlist tags for playlist named %s" +
                " have been successfully deleted from database MusicPortal", playlist.getName()));
    }
}