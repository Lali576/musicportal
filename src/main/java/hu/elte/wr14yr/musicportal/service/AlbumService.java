package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.AlbumRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private FileService fileService;

    public Album create(Album album, User user, File coverFile) throws URISyntaxException, IOException {
        album.setUser(user);

        Album savedAlbum = albumRepository.save(album);

        String albumFolderGdaId = fileService.uploadFolder(savedAlbum.getTitle(), user.getUserFolderGdaId());
        String coverFileGdaId = fileService.uploadFile(coverFile, albumFolderGdaId);

        albumRepository.updateFolderGdaId(savedAlbum.getId(), albumFolderGdaId);
        albumRepository.updateFileGdaId(savedAlbum.getId(), coverFileGdaId);

        savedAlbum.setAlbumFolderGdaId(albumFolderGdaId);
        savedAlbum.setCoverFileGdaId(coverFileGdaId);

        return savedAlbum;
    }

    public Iterable<Album> list(User user) {
        if(user.getRole() == User.Role.ARTIST) {
            return albumRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Album find(long id) {
       Album album = albumRepository.findAlbumById(id);

       return album;
    }

    public Album update(Album album, File file) throws URISyntaxException, IOException {

        Album updatedAlbum = albumRepository.save(album);



        fileService.delete(updatedAlbum.getCoverFileGdaId());

        String coverFileGdaId = fileService.uploadFile(file, album.getCoverFileGdaId());

        albumRepository.updateFileGdaId(updatedAlbum.getId(), coverFileGdaId);

        updatedAlbum.setCoverFileGdaId(coverFileGdaId);

        //Update possible changed songs

        return albumRepository.save(album);
    }

    public void deleteAllByUser(User user) throws URISyntaxException, IOException {
        for(Album album : user.getAlbums()) {
            delete(album);
        }
    }

    public void delete(Album album) throws URISyntaxException, IOException {
        songService.deleteAllByAlbum(album);

        //Delete keywords and genres

        fileService.delete(album.getCoverFileGdaId());

        fileService.delete(album.getAlbumFolderGdaId());

        albumRepository.deleteById(album.getId());
    }
}
