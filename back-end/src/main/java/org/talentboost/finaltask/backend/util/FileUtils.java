package org.talentboost.finaltask.backend.util;

import org.springframework.web.multipart.MultipartFile;
import org.talentboost.finaltask.backend.exceptions.UnsupportedMimeTypeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FileUtils {

    private String staticDirectory;
    private Map<String,String> allowedMimeTypes;

    public FileUtils(String staticDirectory, Map<String, String> allowedMimeTypes) {
        this.staticDirectory = staticDirectory;
        this.allowedMimeTypes = allowedMimeTypes;
    }

    /**
     * Return file extension of file or throw if it is unsupported.
     */
    private String getFileExtension(MultipartFile file) {
        String fileMimeType = Objects.requireNonNull(file.getContentType());
        String fileExtension = allowedMimeTypes.get(fileMimeType);

        if (fileExtension == null) {
            throw new UnsupportedMimeTypeException("Mime type is not supported.");
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

    public String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public void deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(staticDirectory + "/" + fileName));
    }
}
