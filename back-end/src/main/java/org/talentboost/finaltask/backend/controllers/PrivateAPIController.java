package org.talentboost.finaltask.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.exceptions.UnsupportedMimeTypeException;
import org.talentboost.finaltask.backend.exceptions.VladoException;
import org.talentboost.finaltask.backend.repository.ImageRepository;
import org.talentboost.finaltask.backend.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class PrivateAPIController {

    @Autowired
    private ImageRepository repository;

    private final FileUtils fileUtils;
    private final String BASE_URL = "http://localhost:3333/";

    public PrivateAPIController() {
        Map<String, String> tmpMap = new HashMap<>();
        String staticFolder = "../static";

        tmpMap.put("image/jpeg", "jpg");
        tmpMap.put("image/png", "png");
        tmpMap.put("image/gif", "gif");

        this.fileUtils = new FileUtils(staticFolder, Collections.unmodifiableMap(tmpMap));
    }

    @ExceptionHandler
    public void handleUnsupportedMimeType(
            VladoException e,
            HttpServletResponse response
    ) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @PostMapping("/meme")
    public ResponseEntity<Image> createImage(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files
    ) throws IOException {
        String fileName = fileUtils.createFile(files[0]);

        Image toBeSaved = new Image(title, BASE_URL + fileName);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(toBeSaved));
    }

    @PutMapping("/meme/{id}")
    public Image alterImage(
            @PathVariable("id") int id,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files
    ) throws IOException {
        Optional<Image> toAlter = repository.findById(id);
        if(toAlter.isPresent()) {
            Image image = toAlter.get();

            image.setTitle(Objects.requireNonNull(title));

            if(files != null && files.length > 0) {
                String oldFileName = fileUtils.getFileNameFromUrl(image.getUrl());
                String newFileName = fileUtils.createFile(files[0]);

                fileUtils.deleteFile(oldFileName);
                image.setUrl(BASE_URL + newFileName);
            }

            repository.save(image);
        }
        return toAlter.orElse(null);
    }

    @DeleteMapping("/meme/{id}")
    public void deleteImage(
            @PathVariable("id") int id
    ) throws IOException {
        Optional<Image> toBeDeleted = repository.findById(id);
        if(toBeDeleted.isPresent()) {
            String url = toBeDeleted.get().getUrl();

            fileUtils.deleteFile(fileUtils.getFileNameFromUrl(url));
            repository.deleteById(id);
        }
    }

}
