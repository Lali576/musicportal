package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.util.Collections;
import java.util.Date;
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
        album.setDate(new Date());
        album.setGenres(genres);

        Album savedAlbum = albumRepository.save(album);
        logger.log(Level.INFO, "Album service: new album has been successfully saved in database MusicPortal");

        if(albumKeywords != null) {
            keywordService.createAlbumKeywords(albumKeywords, savedAlbum);
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
        logger.log(Level.INFO, "Album service: new album has been updated with folder and file id's");

        return savedAlbum;
    }

    public Iterable<Album> listAll() {
        logger.log(Level.INFO, "Album service: every album in database MusicPortal are going to be listed");

        return albumRepository.findAll();
    }

    public Iterable<Album> listByUser(long id) {
        logger.log(Level.INFO, "Album service: user with id " +
                id + "'s albums are going to be listed");

        return albumRepository.findAllByUserId(id);
    }

    public Iterable<Album> listFirstFive() {
        logger.log(Level.INFO, "Album service: first five albums ordered " +
                "by their dates are going to be listed");

        return albumRepository.findFirst5ByOrderByDateAsc();
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
        Iterable<Album> userAlbums = listByUser(user.getId());
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

        fileService.delete(album.getAlbumFolderGdaId());
        albumRepository.deleteById(album.getId());
        logger.log(Level.INFO, "Album service: album titled " +
                album.getTitle() + " has been successfully deleted from database MusicPortal");
    }
}
