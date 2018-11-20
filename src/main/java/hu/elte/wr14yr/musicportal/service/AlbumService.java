package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.tags.AlbumTag;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private FileService fileService;

    @Autowired
    private TagService tagService;

    private Logger logger = Logger.getLogger(AlbumService.class.getName());

    public Album create(Album album, User user, File coverFile, List<Genre> genres, List<AlbumTag> albumTags) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info("Album service: new album is going to be saved in database MusicPortal");

        album.setUser(user);
        album.setDate(new Date());
        album.setGenres(genres);

        Album savedAlbum = albumRepository.save(album);

        logger.info("Album service: new album has been successfully saved in database MusicPortal");

        if(!(albumTags.isEmpty())) {
            tagService.createAlbumTags(albumTags, savedAlbum);
        }

        String albumFolderGdaId = fileService.uploadFolder(savedAlbum.getTitle(), user.getUserAlbumsFolderGdaId());
        savedAlbum.setAlbumFolderGdaId(albumFolderGdaId);

        String albumSongsFolderGdaId = fileService.uploadFolder("songs", albumFolderGdaId);
        savedAlbum.setAlbumSongsFolderGdaId(albumSongsFolderGdaId);

        String albumCoverFolderGdaId = fileService.uploadFolder("cover", albumFolderGdaId);
        savedAlbum.setAlbumCoverFolderGdaId(albumCoverFolderGdaId);

        if(coverFile != null) {
            String coverFileGdaId = fileService.uploadFile(coverFile, albumCoverFolderGdaId);
            savedAlbum.setCoverFileGdaId(coverFileGdaId);
        }

        savedAlbum = albumRepository.save(savedAlbum);

        logger.info("Album service: new album has been updated with folder and file id's");

        return savedAlbum;
    }

    public Iterable<Album> listAll() {
        logger.info("Album service: every album in database MusicPortal are going to be listed");

        return albumRepository.findAll();
    }

    public Iterable<Album> listFirstFive() {
        logger.info("Album service: first five albums ordered by their dates are going to be listed");

        return albumRepository.findFirst5ByOrderByDateDesc();
    }

    public Iterable<Album> listByUser(long id) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Album service: user with id %d's albums are going to be listed", id));

        return albumRepository.findAllByUserId(id);
    }

    public Album find(long id) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Album service: album with id %d is going to be searched", id));

        Album album = albumRepository.findAlbumById(id);

        logger.info(String.format("Album service: album with id %d has been found successfully", id));

        return album;
    }

    public Album updateDetails(Album album, List<Genre> genres, List<AlbumTag> albumTags) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Album service: album titled %s is going to be updated", album.getTitle()));

        album.setGenres(genres);

        album = albumRepository.save(album);

        tagService.deleteAllAlbumTagsByAlbum(album);

        if(!(albumTags.isEmpty())) {
            tagService.createAlbumTags(albumTags, album);
        }

        logger.info(String.format("Album service: album titled %s has been updated successfully", album.getTitle()));

        return album;
    }

    public Album changeCoverFile(Album album, File coverFile) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Album service: album titled %s's cover image is going to be changed",album.getTitle()));

        if(album.getCoverFileGdaId().equals("")) {
            if(coverFile != null) {
                String coverFileGdaId = fileService.uploadFile(coverFile, album.getAlbumCoverFolderGdaId());
                album.setCoverFileGdaId(coverFileGdaId);
            }
        } else {
            if(coverFile != null) {
                String coverFileGdaId =fileService.updateFile(album.getCoverFileGdaId(), coverFile);
                album.setCoverFileGdaId(coverFileGdaId);
            } else {
                fileService.delete(album.getCoverFileGdaId());
                album.setCoverFileGdaId("");
            }
        }

        album = albumRepository.save(album);

        logger.info(String.format("Album service: album titled %s's cover image has been changed successfully", album.getTitle()));

        return album;
    }

    public Album updateType(Album album) {
        logger.info(String.format("Album service: album titled %s' type is going to be updated", album.getTitle()));

        Album savedAlbum = albumRepository.save(album);

        logger.info(String.format("Album service: album titled %s's type is going to be updated", album.getTitle()));

        return savedAlbum;
    }

    void deleteAllByUser(User user) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Album service: user named %s's albums are going to be deleted", user.getUsername()));

        Iterable<Album> userAlbums = listByUser(user.getId());

        for(Album album : userAlbums) {
            delete(album);
        }

        logger.info(String.format("Album service: user named %s's albums has been deleted successfully", user.getUsername()));
    }

    public void delete(Album album) throws DataAccessException, ConstraintViolationException, DataException {
        logger.info(String.format("Album service: album titled %s is going to be deleted from database MusicPortal", album.getTitle()));

        songService.deleteAllByAlbum(album);

        tagService.deleteAllAlbumTagsByAlbum(album);

        fileService.delete(album.getAlbumFolderGdaId());

        albumRepository.deleteById(album.getId());

        logger.info(String.format("Album service: album titled %s has been successfully deleted from database MusicPortal", album.getTitle()));
    }
}
