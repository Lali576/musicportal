package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private FileService fileService;

    @Autowired
    private KeywordService keywordService;

    private Logger logger = Logger.getLogger(AlbumService.class.getName());

    public Album create(Album album, User user, File coverFile, List<Genre> genres, List<AlbumKeyword> albumKeywords) {
        logger.log(Level.INFO, "Album service: new album is going to be saved in database MusicPortal");
        album.setUser(user);
        album.setGenres(genres);

        Album savedAlbum = albumRepository.save(album);
        logger.log(Level.INFO, "Album service: new album has been successfully saved in database MusicPortal");

        if(albumKeywords != null) {
            keywordService.createAlbumKeywords(albumKeywords, savedAlbum);
        }

        String albumFolderGdaId = fileService.uploadFolder(savedAlbum.getTitle(), user.getUserFolderGdaId());
        savedAlbum.setAlbumFolderGdaId(albumFolderGdaId);

        if(coverFile != null) {
            String coverFileGdaId = fileService.uploadFile(coverFile, albumFolderGdaId);
            savedAlbum.setCoverFileGdaId(coverFileGdaId);
        }

        savedAlbum = albumRepository.save(savedAlbum);
        logger.log(Level.INFO, "Album service: new album has been updated with folder and file id's");

        return savedAlbum;
    }

    public Iterable<Album> listAll() {
        logger.log(Level.INFO, "Every album in database MusicPortal are going to be listed");

        return albumRepository.findAll();
    }

    public Iterable<Album> list(User loggedInUser) {
        logger.log(Level.INFO, "Album service: user named " +
                loggedInUser.getUsername() + "'s albums are going to be listed");

        return albumRepository.findAllByUser(loggedInUser);
    }

    public Album find(long id) {
        logger.log(Level.INFO, "Album service: album with id " +
                id + " is going to be searched");
        Album album = albumRepository.findAlbumById(id);
        logger.log(Level.INFO, "Album service: album with id "
                + id + " has been found successfully");

        return album;
    }

    public Album updateDetails(Album album, List<Genre> genres, List<AlbumKeyword> albumKeywords) {
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + " is going to be updated");
        album.setGenres(genres);
        album = albumRepository.save(album);
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + " has been updated successfully");

        keywordService.deleteAllAlbumKeywordsByAlbum(album);

        if(albumKeywords != null) {
            keywordService.createAlbumKeywords(albumKeywords, album);
        }

        return album;
    }

    public Album changeCoverFile(Album album, File coverFile) {
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + "'s cover image is going to be changed");
        if(coverFile != null) {
            String newCoverFileGdaId = fileService.updateFile(album.getCoverFileGdaId(), coverFile);
            album.setCoverFileGdaId(newCoverFileGdaId);
        } else {
            fileService.delete(album.getCoverFileGdaId());
            album.setCoverFileGdaId(null);
        }
        album = albumRepository.save(album);
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + "'s cover image has been changed successfully");

        return album;
    }

    public void deleteAllByUser(User user) {
        logger.log(Level.INFO, "Album service: user named " +
                user.getUsername() + "'s albums are going to be deleted");
        Iterable<Album> userAlbums = list(user);
        for(Album album : userAlbums) {
            delete(album);
        }
        logger.log(Level.INFO, "Album service: user named " +
                user.getUsername() + "'s albums have been deleted successfully");
    }

    public void delete(Album album) {
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + " is going to be deleted from database MusicPortal");
        songService.deleteAllByAlbum(album);
        keywordService.deleteAllAlbumKeywordsByAlbum(album);

        if(!(album.getCoverFileGdaId().equals(""))) {
            fileService.delete(album.getCoverFileGdaId());
        }
        fileService.delete(album.getAlbumFolderGdaId());
        albumRepository.deleteById(album.getId());
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + " has been successfully deleted from database MusicPortal");
    }
}
