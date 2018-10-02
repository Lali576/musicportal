package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.*;
import hu.elte.wr14yr.musicportal.model.keywords.SongKeyword;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>{
    @Override
    Song save(Song song);

    List<Song> findAll();

    List<Song> findAllByTitleContainsAllIgnoreCase(String title);

    List<Song> findAllByAlbum(Album album);

    List<Song> findAllByUserId(long id);

    List<Song> findFirst5ByCountBySongCounter();

    List<Song> findAllByPlaylist(Playlist playlist);

    List<Song> findAllByGenres(Genre genre);

    List<Song> findAllBySongKeywords(List<SongKeyword> songKeywords);

    Song findSongById(long id);

    /*
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE SONGS SET AUDIO_FILE_GDA_ID = :AUDIO_FILE_GDA_ID WHERE ID = :ID", nativeQuery = true)
    void updateAudioFileGdaId(@Param("ID") long id, @Param("AUDIO_FILE_GDA_ID") String audioFileGdaId);
    */

    @Override
    void deleteById(Long id);
}
