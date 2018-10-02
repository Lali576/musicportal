package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import hu.elte.wr14yr.musicportal.repository.SongCommentRepository;
import hu.elte.wr14yr.musicportal.repository.SongCounterRepository;
import hu.elte.wr14yr.musicportal.repository.SongLikeRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Autowired
    private KeywordService keywordService;

    private Logger logger = Logger.getLogger(SongService.class.getName());

    public Song create(Song song, User user, Album album, File audioFile, List<Genre> genres, List<SongKeyword> songKeywords) {
        logger.log(Level.INFO, "Song service: new song is going to be saved in database MusicPortal");
        song.setUser(user);
        song.setAlbum(album);
        song.setGenres(genres);
        try {
            AudioFile f = AudioFileIO.read(audioFile);
            song.setDuration(f.getAudioHeader().getTrackLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Song savedSong = songRepository.save(song);
        logger.log(Level.INFO, "Song service: new song has been successfully saved in database MusicPortal");

        if(songKeywords != null) {
            keywordService.createSongKeywords(songKeywords, savedSong);
        }

        String audioFileGdaId = fileService.uploadFile(audioFile, album.getAlbumSongsFolderGdaId());

        savedSong.setAudioFileGdaId(audioFileGdaId);
        savedSong = songRepository.save(savedSong);
        logger.log(Level.INFO, "Song service: new song has been updated with file id");

        return savedSong;
    }

    public Iterable<Song> listAll() {
        logger.log(Level.INFO, "Song service: every song in database MusicPortal are going to be listed");

        return songRepository.findAll();
    }

    public Iterable<Song> listByUser(long id) {
        logger.log(Level.INFO, "Song service: user with id " +
                id + "'s songs are going to be listed");

        return songRepository.findAllByUserId(id);
    }

    public Iterable<Song> listFirstFive() {
        logger.log(Level.INFO, "Song service: first five songs ordered " +
                "by their counted song counters are going to be listed");

        return songRepository.findFirst5ByCountBySongCounter();
    }

    public Iterable<Song> listByAlbum(Album album) {
        logger.log(Level.INFO, "Song service: album titled " +
                album.getTitle() + "'s songs are going to be listed");

        return songRepository.findAllByAlbum(album);
    }

    public Iterable<Song> listByPlaylist(Playlist playlist) {
        logger.log(Level.INFO, "Song service: playlist named " +
                playlist.getName() + "'s songs are going to be listed");

        return songRepository.findAllByPlaylist(playlist);
    }

    public Song find(long id) {
        logger.log(Level.INFO, "Song service: song with id " +
                id + " is going to be searched");
        Song song = songRepository.findSongById(id);
        logger.log(Level.INFO, "Song service: song with id " +
                id + " has been found successfully");

        return song;
    }

    public Song update(Song song, Album album, User user, File audioFile) {
        logger.log(Level.INFO, "Song service: song titled " +
                song.getTitle() + " is going to be updated");
        song.setUser(user);
        song.setAlbum(album);
        String newAudioFileGdaId = fileService.updateFile(song.getAudioFileGdaId(), audioFile);
        song.setAudioFileGdaId(newAudioFileGdaId);
        song = songRepository.save(song);
        logger.log(Level.INFO, "Song service: song titled " +
                song.getTitle() + " has been updated successfully");

        return song;
    }

    public void deleteAllByAlbum(Album album) {
        logger.log(Level.INFO, "Song service: album titled " +
                album.getTitle() + "'s songs are going to be deleted");
        Iterable<Song> albumSongs = listByAlbum(album);
        for (Song song : albumSongs) {
            delete(song);
        }
        logger.log(Level.INFO, "Song service: album titled " +
                album.getTitle() + "'s songs have been deleted successfully");
    }

    public void delete(Song song) {
        logger.log(Level.INFO, "Song service: song titled " +
                song.getTitle() + " is going to be deleted from database MusicPortal");
        songCommentRepository.deleteAllBySong(song);
        songCounterRepository.deleteAllBySong(song);
        songLikeRepository.deleteAllBySong(song);
        keywordService.deleteAllSongKeywordsBySong(song);
        fileService.delete(song.getAudioFileGdaId());
        songRepository.deleteById(song.getId());
        logger.log(Level.INFO, "Song service: song titled " +
                song.getTitle() + " has been successfully deleted from database MusicPortal");
    }

    public Iterable<SongComment> createSongComment(SongComment songComment, User user, Song song) {
        logger.log(Level.INFO, "Song service: new song comment for song titled " +
                song.getTitle() + " is going to be saved");
        songComment.setUser(user);
        songComment.setSong(song);
        SongComment savedSongComment = songCommentRepository.save(songComment);
        logger.log(Level.INFO, "Song service: new song comment for song titled " +
                song.getTitle() + " has been saved successfully");

        return songCommentRepository.findAllBySong(savedSongComment.getSong());
    }

    public Iterable<SongComment> listSongComments(Song song) {
        logger.log(Level.INFO, "Song service: song titled " +
                song.getTitle() + "'s comments are going to be listed");

        return songCommentRepository.findAllBySong(song);
    }

    public int saveSongLike(SongLike songLike, User user, Song song) {
        logger.log(Level.INFO, "Song service: new like material for song titled " +
                song.getTitle() + " is going to be saved");
        songLike.setUser(user);
        songLike.setSong(song);
        SongLike savedSongLike = songLikeRepository.save(songLike);
        if(savedSongLike.getType().equals(SongLike.Type.LIKE)) {
            logger.log(Level.INFO, "Song service: new like material for song titled " +
                    song.getTitle() + " has been saved successfully");

            return songLikeRepository.countAllBySongAndRoleLike(savedSongLike.getId());
        } else {
            logger.log(Level.INFO, "Song service: new dislike material for song titled " +
                    song.getTitle() + " has been saved successfully");

            return songLikeRepository.countAllBySongAndRoleDislike(savedSongLike.getId());
        }
    }

    public int[] countLikesDivided(Song song) {
        logger.log(Level.INFO, "Song service: likes for song titled " +
                song.getTitle() + " are going to be counted");
        int[] counts = new int[2];
        counts[0] = songLikeRepository.countAllBySongAndRoleLike(song.getId());
        counts[1] = songLikeRepository.countAllBySongAndRoleDislike(song.getId());

        return counts;
    }

    public int saveSongCounter(SongCounter songCounter, Song song, User user) {
        logger.log(Level.INFO, "Song service: new song counter material for song titled " +
                song.getTitle() + " is going to be saved");
        songCounter.setSong(song);
        if (!(user.getRole().equals(User.Role.USER))) {
            songCounter.setUser(user);
        }
        SongCounter savedSongCounter = songCounterRepository.save(songCounter);
        logger.log(Level.INFO, "Song service: new song counter material for song titled " +
                song.getTitle() + " has been saved successfully");

        return songCounterRepository.countAllBySong(savedSongCounter.getSong());
    }

    public int countSongCounterNumber(Song song) {
        logger.log(Level.INFO, "Song service: counters for song titled " +
                song.getTitle() + " is going to be counted");

        return songCounterRepository.countAllBySong(song);
    }
}