package hu.elte.wr14yr.musicportal.repository;

import hu.elte.wr14yr.musicportal.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContainsAllIgnoreCase(String username);

    //List<User> findAllByKeywords(Keyword keyword);

    User findUserById(Long id);

    /*
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE USERS SET SALT_CODE = :SALT_CODE WHERE ID := ID", nativeQuery = true)
    void updateSaltCode(@Param("ID") long id, @Param("SALT_CODE") String saltCode);
    */

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE USERS SET HASH_PASSWORD = :HASH_PASSWORD WHERE ID = :ID", nativeQuery = true)
    void updateHashPassword(@Param("ID") long id, @Param("HASH_PASSWORD") String hashPassword);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE USERS SET USER_FOLDER_GDA_ID = :USER_FOLDER_GDA_ID WHERE ID = :ID", nativeQuery = true)
    void updateFolderGdaId(@Param("ID") long id, @Param("USER_FOLDER_GDA_ID") String folderGdaId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE USERS SET ICON_FILE_GDA_ID = :ICON_FILE_GDA_ID WHERE ID = :ID", nativeQuery = true)
    void updateFileGdaId(@Param("ID") long id, @Param("ICON_FILE_GDA_ID") String fileGdaId);

    @Override
    void deleteById(Long id);
}
