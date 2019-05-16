package org.talentboost.finaltask.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final String staticDirectory = "/static";
    private final ValidationService validationService;

    @Autowired
    public FileService(ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * Return file extension of file or throw if it is unsupported.
     */
    private String getFileExtension(MultipartFile file) {
        String fileMimeType = file.getContentType();

        return validationService.getAllowedMimeTypes()
                .get(fileMimeType);
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
