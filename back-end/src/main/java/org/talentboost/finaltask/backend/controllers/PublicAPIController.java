package org.talentboost.finaltask.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;
import org.talentboost.finaltask.backend.util.Env;

import java.util.Objects;

@RestController
@CrossOrigin
public class PublicAPIController {

    private ImageRepository repository;

    private final String STATIC_SERVER_BASE_URL;

    @Autowired
    public PublicAPIController(ImageRepository repository, Env env) {
        this.repository = repository;

        STATIC_SERVER_BASE_URL = String.format("http://%s:8090/",
                Objects.requireNonNull(env.get("HOST_IP"))
        );
    }

    @GetMapping("/meme")
    public Iterable<Image> getAllImages() {
        Iterable<Image> images = repository.findAll();

        for (Image image : images) {
            image.setImage(STATIC_SERVER_BASE_URL + image.getImage());
        }

        return images;
    }

}
