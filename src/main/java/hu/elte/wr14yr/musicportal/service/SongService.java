package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.repository.SongCommentRepository;
import hu.elte.wr14yr.musicportal.repository.SongCounterRepository;
import hu.elte.wr14yr.musicportal.repository.SongLikeRepository;
import hu.elte.wr14yr.musicportal.repository.SongRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

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
    private TagService tagService;

    private Logger logger = Logger.getLogger(SongService.class.getName());

    public Song create(Song song, User user, Album album, File audioFile, List<Genre> genres) {
        logger.info("Song service: new song is going to be saved in database MusicPortal");

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

        logger.info("Song service: new song has been successfully saved in database MusicPortal");

        String audioFileGdaId = fileService.uploadFile(audioFile, album.getAlbumSongsFolderGdaId());

        savedSong.setAudioFileGdaId(audioFileGdaId);
        savedSong = songRepository.save(savedSong);

        logger.info("Song service: new song has been updated with file id");

        return savedSong;
    }

    public Iterable<Song> listAll() {
        logger.info("Song service: every song in database MusicPortal are going to be listed");

        return songRepository.findAll();
    }

    public Iterable<Song> listFirstFive() {
        logger.info("Song service: first five songs ordered " +
                "by their counted song counters are going to be listed");

        return songRepository.findFirst5ByOrderByTitleAsc();
    }

    public Iterable<Song> listByUser(long id) {
        logger.info(String.format("Song service: user with id %s's songs are going to be listed", id));

        return songRepository.findAllByUserId(id);
    }

    public Iterable<Song> listByAlbum(Album album) {
        logger.info(String.format("Song service: album titled %s's songs are going to be listed", album.getTitle()));

        return songRepository.findAllByAlbum(album);
    }

    public Iterable<Song> listByPlaylist(Playlist playlist) {
        logger.info(String.format("Song service: playlist named %s's songs are going to be listed", playlist.getName()));

        return songRepository.findAllByPlaylist(playlist);
    }

    public Song find(long id) {
        logger.info(String.format("Song service: song with id %s is going to be searched", id));

        Song song = songRepository.findSongById(id);

        logger.info(String.format("Song service: song with id %s has been found successfully", id));

        return song;
    }

    public Song update(Song song, Album album, User user, File audioFile) {
        logger.info(String.format("Song service: song titled %s is going to be updated", song.getTitle()));

        song.setUser(user);
        song.setAlbum(album);
        String newAudioFileGdaId = fileService.updateFile(song.getAudioFileGdaId(), audioFile);
        song.setAudioFileGdaId(newAudioFileGdaId);
        song = songRepository.save(song);

        logger.info(String.format("Song service: song titled %s has been updated successfully", song.getTitle()));

        return song;
    }

    public Song updateLyrics(Song song, String lyrics) {
        song.setLyrics(lyrics);

        song = songRepository.save(song);

        return song;
    }

    public void deleteAllByAlbum(Album album) {
        logger.info(String.format("Song service: album titled %s's songs are going to be deleted", album.getTitle()));

        Iterable<Song> albumSongs = listByAlbum(album);
        StreamSupport.stream(albumSongs.spliterator(), false).forEach(
                s -> delete(s)
            );

        logger.info(String.format("Song service: album titled %s's songs have been deleted successfully", album.getTitle()));
    }

    public void delete(Song song) {
        logger.info(String.format("Song service: song titled %s" +
                " is going to be deleted from database MusicPortal", song.getTitle()));

        songCommentRepository.deleteAllBySong(song);
        songCounterRepository.deleteAllBySong(song);
        songLikeRepository.deleteAllBySong(song);
        fileService.delete(song.getAudioFileGdaId());
        songRepository.deleteById(song.getId());

        logger.info(String.format("Song service: song titled %s" +
                " has been successfully deleted from database MusicPortal", song.getTitle()));
    }

    public Iterable<SongComment> createSongComment(SongComment songComment, User user, Song song) {
        logger.info(String.format("Song service: new song comment for song titled %s" +
                " is going to be saved", song.getTitle()));

        songComment.setUser(user);
        songComment.setSong(song);
        songComment.setDate(new Date());
        SongComment savedSongComment = songCommentRepository.save(songComment);

        logger.info(String.format("Song service: new song comment for song titled %s" +
                " has been saved successfully", song.getTitle()));

        return songCommentRepository.findAllBySong(savedSongComment.getSong());
    }

    public Iterable<SongComment> listSongComments(Song song) {
        logger.info(String.format("Song service: song titled %s's comments are going to be listed", song.getTitle()));

        return songCommentRepository.findAllBySong(song);
    }

    public int saveSongLike(SongLike songLike, User user, Song song) {
        logger.info(String.format("Song service: new like material for song titled %s" +
                " is going to be saved", song.getTitle()));

        songLike.setUser(user);
        songLike.setSong(song);
        SongLike savedSongLike = songLikeRepository.save(songLike);

        if(savedSongLike.getType().equals(SongLike.Type.LIKE)) {
            logger.info(String.format("Song service: new like material for song titled %s" +
                    " has been saved successfully", song.getTitle()));

            return songLikeRepository.countAllBySongAndRoleLike(savedSongLike.getId());
        } else {
            logger.info(String.format("Song service: new dislike material for song titled %s" +
                    " has been saved successfully", song.getTitle()));

            return songLikeRepository.countAllBySongAndRoleDislike(savedSongLike.getId());
        }
    }

    public int[] countLikesDivided(Song song) {
        logger.info(String.format("Song service: likes for song titled %s" +
                " are going to be counted", song.getTitle()));

        int[] counts = new int[2];
        counts[0] = songLikeRepository.countAllBySongAndRoleLike(song.getId());
        counts[1] = songLikeRepository.countAllBySongAndRoleDislike(song.getId());

        return counts;
    }

    public int saveSongCounter(Song song, User user) {
        logger.info(String.format("Song service: new song counter material for song titled %s" +
                " is going to be saved", song.getTitle()));

        SongCounter songCounter = new SongCounter();

        songCounter.setSong(song);

        songCounter.setUser(user);

        SongCounter savedSongCounter = songCounterRepository.save(songCounter);

        logger.info(String.format("Song service: new song counter material for song titled %s" +
                " has been saved successfully", song.getTitle()));

        return songCounterRepository.countAllBySong(savedSongCounter.getSong());
    }

    public int countSongCounterNumber(Song song) {
        logger.info(String.format("Song service: counters for song titled %s" +
                " is going to be counted", song.getTitle()));

        return songCounterRepository.countAllBySong(song);
    }
}