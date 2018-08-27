package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;
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
        album.setUser(user);
        album.setGenres(genres);

        Album savedAlbum = albumRepository.save(album);

        keywordService.createAlbumKeywords(albumKeywords, savedAlbum);

        String albumFolderGdaId = fileService.uploadFolder(savedAlbum.getTitle(), user.getUserFolderGdaId());
        String coverFileGdaId = fileService.uploadFile(coverFile, albumFolderGdaId);

        savedAlbum.setAlbumFolderGdaId(albumFolderGdaId);
        savedAlbum.setCoverFileGdaId(coverFileGdaId);

        savedAlbum = albumRepository.save(savedAlbum);

        return savedAlbum;
    }

    public Iterable<Album> list(User loggedInUser) {
        if(loggedInUser.getRole() == User.Role.ARTIST) {
            return albumRepository.findAllByUser(loggedInUser);
        } else {
            return Collections.emptyList();
        }
    }

    public Album find(long id) {
       Album album = albumRepository.findAlbumById(id);

       return album;
    }

    public Album updateDetails(Album album, List<Genre> genres, List<AlbumKeyword> albumKeywords) {
        album.setGenres(genres);
        album.setAlbumKeywords(albumKeywords);
        album = albumRepository.save(album);

        return album;
    }

    public Album updateCoverFile(Album album, File coverFile) {
        String newCoverFileGdaId = fileService.updateFile(album.getCoverFileGdaId(), coverFile);
        album.setCoverFileGdaId(newCoverFileGdaId);
        album = albumRepository.save(album);

        return album;
    }

    public void deleteAllByUser(User user) {
        for(Album album : user.getAlbums()) {
            delete(album);
        }
    }

    public void delete(Album album) {
        songService.deleteAllByAlbum(album);

        keywordService.deleteAllAlbumKeywordsByAlbum(album);

        fileService.delete(album.getCoverFileGdaId());

        fileService.delete(album.getAlbumFolderGdaId());

        albumRepository.deleteById(album.getId());
    }
}
