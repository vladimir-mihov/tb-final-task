package org.talentboost.finaltask.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;

import java.util.Objects;

@RestController
@CrossOrigin
public class PublicAPIController {

    @Autowired
    private ImageRepository repository;

    private final String BASE_URL = "http://" +
            Objects.requireNonNull(System.getenv("STATIC_SERVER_IP")) +
            ":8090/";

    @GetMapping("/meme")
    public Iterable<Image> getAllImages() {
        Iterable<Image> images = repository.findAll();

        for(Image image : images) {
            image.setUrl(BASE_URL + image.getUrl());
        }

        return images;
    }

}
