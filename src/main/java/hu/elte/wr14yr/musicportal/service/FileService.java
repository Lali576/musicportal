package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.util.logging.PlatformLogger;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileService {

    private Logger logger = Logger.getLogger(FileService.class.getName());

    public String uploadFile(java.io.File file, String userFolderGdaId) {
        logger.info(String.format("File service: trying to upload file named %s within folder with id %s", file.getName(), userFolderGdaId));

        try {
            String gdaId = GoogleDriveApi.uploadFile(file, userFolderGdaId);

            logger.info(String.format("File service: upload file named %s was successful", file.getName()));

            return gdaId;
        } catch(GeneralSecurityException | IOException e) {
            logger.severe(String.format("File service: uploading file named %s was unsuccessful", file.getName()));
            e.printStackTrace();
        }

        return null;
    }

    public OutputStream downloadFile(String fileGdaId) {
        logger.info(String.format("File service: downloading file with gda id %s", fileGdaId));
        try {
            return GoogleDriveApi.downloadFile(fileGdaId);
        } catch (GeneralSecurityException | IOException e) {
            logger.severe(String.format("File service: downloading file with gda id %s was unsuccessful", fileGdaId));
            e.printStackTrace();
        }
        return null;
    }

    public String uploadFolder(String username, String folderGdaId) {
        logger.info(String.format("File service: trying to create folder within folder with id %s", folderGdaId));
        try {
            String gdaId = GoogleDriveApi.uploadFolder(username, folderGdaId);

            logger.info("File service: creating folder was successful");

            return gdaId;
        } catch (GeneralSecurityException | IOException e) {
            logger.severe("File service: creating folder was unsuccessful");
            e.printStackTrace();
        }

        return null;
    }

    public String updateFile(String fileGdaId, java.io.File newFile) {
        logger.info(String.format("File service: trying to update file with id %s", fileGdaId));

        try {
            String newFileGdaId = GoogleDriveApi.updateFile(fileGdaId, newFile);

            logger.info(String.format("File service: updating file with id %s was successful", fileGdaId));

            return newFileGdaId;
        } catch(GeneralSecurityException | IOException e) {
            logger.severe(String.format("File service: updating file with id %s was unsuccessful", fileGdaId));
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String id) {
        logger.info(String.format("File service: trying to delete file with id %s from Google Drive", id));

        try {
            GoogleDriveApi.delete(id);

            logger.info(String.format("File service: deleting file with id %s from Google Drive was successful", id));
        } catch (GeneralSecurityException | IOException e) {
            logger.severe(String.format("File service: deleting file with id %s from Google Drive was unsuccessful", id));
            e.printStackTrace();
        }

        logger.info(String.format("Deleting file with id %s from Google Drive was successful", id));
    }

    public File convertToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
