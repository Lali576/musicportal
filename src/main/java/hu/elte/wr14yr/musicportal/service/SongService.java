package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.SongCommentRepository;
import hu.elte.wr14yr.musicportal.repository.SongCounterRepository;
import hu.elte.wr14yr.musicportal.repository.SongLikeRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongCommentRepository songCommentRepository;

    @Autowired
    private SongCounterRepository songCounterRepository;

    @Autowired
    private SongLikeRepository songLikeRepository;

    @Autowired
    private FileService fileService;

    public Song create(Song song, User user, Album album, File audioFile, List<Genre> genres, List<Keyword> keywords) {
        song.setUser(user);
        song.setAlbum(album);

        Song savedSong = songRepository.save(song);

        String audioFileGdaId = fileService.uploadFile(audioFile, album.getAlbumFolderGdaId());

        songRepository.updateAudioFileGdaId(savedSong.getId(), audioFileGdaId);

        savedSong.setAudioFileGdaId(audioFileGdaId);

        return savedSong;
    }

    public Iterable<SongComment> createSongComment(SongComment songComment) {
        SongComment savedSongComment = songCommentRepository.save(songComment);
        return songCommentRepository.findAllBySong(savedSongComment.getSong());
    }

    public Iterable<SongComment> listSongComments(Song song) {
        return songCommentRepository.findAllBySong(song);
    }

    public int saveSongLike(SongLike songLike) {
        SongLike savedSongLike = songLikeRepository.save(songLike);
        if(savedSongLike.getRole().equals(SongLike.Role.LIKE)) {
            return songLikeRepository.countAllBySongAndRoleLike(savedSongLike.getId());
        } else {
            return songLikeRepository.countAllBySongAndRoleDislike(savedSongLike.getId());
        }
    }

    public int saveSongCounter(SongCounter songCounter) {
        SongCounter savedSongCounter = songCounterRepository.save(songCounter);
        return songCounterRepository.countAllBySong(songCounter.getSong());
    }

    public Song find(long id) {
        Song song = songRepository.findSongById(id);

        return song;
    }

    public Iterable<Song> listAll() {
        return songRepository.findAll();
    }

    public Iterable<Song> list(User user) {
        if(user.getRole() == User.Role.ARTIST) {
            return songRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Iterable<Song> listByAlbum(Album album) {
        return songRepository.findAllByAlbum(album);
    }

    public Iterable<Song> listByPlaylist(Playlist playlist) {
        return songRepository.findAllByPlaylist(playlist);
    }

    public Song update(Song song, Album album, User user, File file) {
        song.setUser(user);
        song.setAlbum(album);

        return songRepository.save(song);
    }

    public void deleteAllByAlbum(Album album) {
        for (Song song : album.getSongs()) {
            delete(song);
        }
    }

    public void delete(Song song) {
        songCommentRepository.deleteAllBySong(song);
        songCounterRepository.deleteAllBySong(song);
        songLikeRepository.deleteAllBySong(song);

        songRepository.deleteById(song.getId());
    }
}
