package org.talentboost.finaltask.backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FileService {

    private String staticDirectory = "/static";
    private Map<String,String> allowedMimeTypes;

    public FileService() {
        Map<String, String> tmpMap = new HashMap<>();

        tmpMap.put("image/jpeg", "jpg");
        tmpMap.put("image/png", "png");
        tmpMap.put("image/gif", "gif");

        this.allowedMimeTypes = Collections.unmodifiableMap(tmpMap);
    }

    /**
     * Return file extension of file or throw if it is unsupported.
     */
    private String getFileExtension(MultipartFile file) {
        String fileMimeType = Objects.requireNonNull(file.getContentType());
        String fileExtension = allowedMimeTypes.get(fileMimeType);

        if (fileExtension == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Mime type is not supported."
            );
        }

        return fileExtension;
    }

    /**
     * Create file into the static directory and return the filename
     */
    public String createFile(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();

        String fileName = uuid + "." + getFileExtension(file);

        Files.copy(
                file.getInputStream(),
                Paths.get(staticDirectory + "/" + fileName)
        );

        return fileName;
    }

    public void deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(staticDirectory + "/" + fileName));
    }
}
