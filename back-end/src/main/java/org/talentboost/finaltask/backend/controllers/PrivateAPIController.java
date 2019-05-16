package org.talentboost.finaltask.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;
import org.talentboost.finaltask.backend.util.FileService;
import org.talentboost.finaltask.backend.util.ValidationService;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
public class PrivateAPIController {

    private final ImageRepository repository;
    private final FileService fileService;
    private final ValidationService validationService;

    @Autowired
    public PrivateAPIController(
            ImageRepository repository,
            FileService fileService,
            ValidationService validationService
    ) {
        this.repository = repository;
        this.fileService = fileService;
        this.validationService = validationService;
    }

    @PostMapping("/meme")
    public ResponseEntity<Image> createImage(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files
    ) throws IOException {
        validationService.validateImageTitle(title);
        validationService.requireFiles(files);

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
        validationService.validateImageTitle(title);

        Optional<Image> toAlter = repository.findById(id);
        if(toAlter.isPresent()) {
            Image image = toAlter.get();

            image.setTitle(title);

            if(files != null && files.length > 0) {
                String oldFileName = image.getImage();
                String newFileName = fileService.createFile(files[0]);

                fileService.deleteFile(oldFileName);
                image.setImage(newFileName);
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
            String image = toBeDeleted.get().getImage();

            fileService.deleteFile(image);
            repository.deleteById(id);
        }

        return toBeDeleted.orElse(null);
    }

}
