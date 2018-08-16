package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import org.springframework.stereotype.Service;
import sun.util.logging.PlatformLogger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileService {

    private Logger logger = Logger.getLogger(FileService.class.getName());

    public String uploadFile(java.io.File file, String userFolderGdaId) {
        logger.log(Level.INFO, "Trying to upload file named " + file.getName() + "within folder with id " + userFolderGdaId, file);
        String gdaId = null;
        try {
            gdaId = GoogleDriveApi.uploadFile(file, userFolderGdaId);

            logger.log(Level.INFO, "Uploading file named " + file.getName() + " was successful");

            return gdaId;
        } catch(GeneralSecurityException | IOException e) {
            logger.log(Level.SEVERE, "Uploading file named " + file.getName() + " was unsuccessful!", file);
            e.printStackTrace();
        }

        return null;
    }

    public String uploadFolder(String username, String folderGdaId) {
        logger.log(Level.INFO, "Trying to create folder within folder with id " + folderGdaId);
        String gdaId = null;
        try {
            gdaId = GoogleDriveApi.uploadFolder(username, folderGdaId);

            logger.log(Level.INFO, "Creating folder was successful");

            return gdaId;
        } catch (GeneralSecurityException | IOException e) {
            logger.log(Level.SEVERE, "Creating folder was unsuccessful!");
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String id) {
        logger.log(Level.INFO, "Trying to delete file with id " + id + " from Google Drive");
        try {
            GoogleDriveApi.delete(id);
        } catch (GeneralSecurityException | IOException e) {
            logger.log(Level.SEVERE, "Deleting file with id " + id + " from Google Drive was unsuccessful!");
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Deleting file with id " + id + " from Google Drive was successful");
    }
}
