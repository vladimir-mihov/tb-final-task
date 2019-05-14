package org.talentboost.finaltask.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;
import org.talentboost.finaltask.backend.util.FileService;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
public class PrivateAPIController {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private FileService fileService;

    @PostMapping("/meme")
    public ResponseEntity<Image> createImage(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files
    ) throws IOException {
        String fileName = fileService.createFile(files[0]);

        Image toBeSaved = new Image(title, fileName);

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
                String oldFileName = fileService.getFileNameFromUrl(image.getUrl());
                String newFileName = fileService.createFile(files[0]);

                fileService.deleteFile(oldFileName);
                image.setUrl(newFileName);
            }

            repository.save(image);
        }
        return toAlter.orElse(null);
    }

    @DeleteMapping("/meme/{id}")
    public Image deleteImage(
            @PathVariable("id") int id
    ) throws IOException {
        Optional<Image> toBeDeleted = repository.findById(id);

        if(toBeDeleted.isPresent()) {
            String url = toBeDeleted.get().getUrl();

            fileService.deleteFile(fileService.getFileNameFromUrl(url));
            repository.deleteById(id);
        }

        return toBeDeleted.orElse(null);
    }

}
