package org.talentboost.finaltask.backend.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class PrivateAPIController {

    @Autowired
    private ImageRepository repository;

    private final String STATIC_FOLDER = "../static";
    private final Map<String, String> mimeTypeMappings;

    public PrivateAPIController() {
        Map<String, String> tmpMap = new HashMap<>();

        tmpMap.put("image/jpeg", "jpg");
        tmpMap.put("image/png", "png");
        tmpMap.put("image/gif", "gif");

        mimeTypeMappings = Collections.unmodifiableMap(tmpMap);
    }

    @PostMapping("/meme")
    public ResponseEntity<Image> createImage(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files
    ) {
        String uuid = UUID.randomUUID().toString();

        MultipartFile file = files[0];
        String fileMimeType = Objects.requireNonNull(file.getContentType());
        String fileExtension = mimeTypeMappings.get(fileMimeType);

        if (fileExtension == null) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }

        String fileName = uuid + "." + fileExtension;

        try (
                InputStream fileInputStream = file.getInputStream()
        ) {
            Files.copy(
                    fileInputStream,
                    Paths.get(STATIC_FOLDER + "/" + fileName)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(new Image(title, "http://localhost:3333/" + fileName)));
    }

    @PutMapping("/meme/{id}")
    public Image alterImage(
            @PathVariable("id") int id,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile[] files

    ) {
        Optional<Image> toAlter = repository.findById(id);
        toAlter.ifPresent(image -> {
            // Logic
            repository.save(image);
        });
        return toAlter.orElse(null);
    }

    @DeleteMapping("/meme/{id}")
    public void deleteImage(
            @PathVariable("id") int id
    ) {
        repository.deleteById(id);
    }

}
