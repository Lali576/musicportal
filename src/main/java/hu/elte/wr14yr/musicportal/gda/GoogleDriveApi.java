package hu.elte.wr14yr.musicportal.gda;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import hu.elte.wr14yr.musicportal.MusicportalApplication;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveApi {
    private static final String APPLICATION_NAME = "Google Drive API Java QuickStart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public static final String MAIN_FOLDER_ID = "1QoToG71iVMPyH9XCYI2BgzDJcSvgknSQ";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = MusicportalApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static String uploadFile(java.io.File filePath, String folderGdaId) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        fileMetadata.setName(filePath.getName());
        fileMetadata.setParents(Collections.singletonList(folderGdaId));
        FileContent mediaContent = new FileContent(fileMetadata.getMimeType(), filePath);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
        System.out.println("File ID: " + file.getId());

        return file.getId();
    }

    public static String uploadFolder(String username, String folderGdaId) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        fileMetadata.setName(username);
        fileMetadata.setParents(Collections.singletonList(folderGdaId));
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File folder = service.files().create(fileMetadata)
                .setFields("id, parents")
                .execute();
        System.out.println("File ID: " + folder.getId());

        return folder.getId();
    }

    public static OutputStream downloadFile(String fileGdaId) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        OutputStream outputStream = new ByteArrayOutputStream();
        service.files().get(fileGdaId).executeMediaAndDownloadTo(outputStream);

        return outputStream;
    }

    public static String updateFile(String gdaId, java.io.File newFilePath) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        fileMetadata.setName(newFilePath.getName());
        FileContent mediaContent = new FileContent(fileMetadata.getMimeType(), newFilePath);

        File updatedFile = service.files().update(gdaId, fileMetadata, mediaContent).execute();
        System.out.println("File ID: " + updatedFile.getId());

        return updatedFile.getId();
    }

    public static void delete(String gdaId) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        service.files().delete(gdaId).execute();
    }
}
