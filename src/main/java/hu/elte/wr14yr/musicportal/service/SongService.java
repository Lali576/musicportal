package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.SongCommentRepository;
import hu.elte.wr14yr.musicportal.repository.SongCounterRepository;
import hu.elte.wr14yr.musicportal.repository.SongLikeRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.Key;
import java.util.Collections;
import java.util.Set;

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

    public Song create(Song song, User user, Album album, Set<Genre> genres, Set<Keyword> keywords) {
        song.setUser(user);
        song.setAlbum(album);
        song.setGenres(genres);
        song.setKeywords(keywords);

        saveAudioFile(new File(song.getAudioPath()));

        return songRepository.save(song);
    }

    public Song find(long id) {
        Song song = songRepository.findSongById(id);
        song.setSongComments(songCommentRepository.findAllBySong(song));
        song.setSongCounterNumber(songCounterRepository.countAllBySong(song));
        song.setSongLikeNumber(songLikeRepository.countAllBySongAndRoleLike(id));
        song.setSongDislikeNumber(songLikeRepository.countAllBySongAndRoleDislike(id));
        song.setTempAudioFile(new File(song.getAudioPath()));

        return song;
    }

    public Iterable<Song> list(User user) {
        if(user.getRole() == User.Role.ARTIST) {
            return songRepository.findAllByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Song update(Song song) {
        saveAudioFile(song.getTempAudioFile());
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

    private void saveAudioFile(File file) {

    }

    private void deleteAudioFile(File file) {

    }
}
