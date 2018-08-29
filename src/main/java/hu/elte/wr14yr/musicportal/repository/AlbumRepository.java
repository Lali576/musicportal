package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Genre;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.keywords.AlbumKeyword;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {
    @Override
    Album save(Album album);

    List<Album> findAll();

    List<Album> findAllByTitleContainsAllIgnoreCase(String name);

    List<Album> findAllByUser(User user);

    List<Album> findAllByGenres(Genre genre);

    List<Album> findAllByAlbumKeywords(List<AlbumKeyword> albumKeywords);

    Album findAlbumById(Long id);

    /*
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE ALBUMS SET ALBUM_FOLDER_GDA_ID = :ALBUM_FOLDER_GDA_ID WHERE ID = :ID", nativeQuery = true)
    void updateFolderGdaId(@Param("ID") long id, @Param("ALBUM_FOLDER_GDA_ID") String folderGdaId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE ALBUMS SET COVER_FILE_GDA_ID = :COVER_FILE_GDA_ID WHERE ID = :ID", nativeQuery = true)
    void updateFileGdaId(@Param("ID") long id, @Param("COVER_FILE_GDA_ID") String fileGdaId);
    */

    @Override
    void deleteById(Long id);
}
