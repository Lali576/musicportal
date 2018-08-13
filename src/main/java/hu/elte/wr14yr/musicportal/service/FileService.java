package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.gda.GoogleDriveApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class FileService {

    public String uploadFile(java.io.File file, String userFolderGdaId) {
        try {
            return GoogleDriveApi.uploadFile(file, userFolderGdaId);
        } catch(GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String uploadFolder(String username, String folderGdaId) {
        try {
            return GoogleDriveApi.uploadFolder(username, folderGdaId);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
