package org.talentboost.finaltask.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;

@RestController
public class PublicAPIController {

    @Autowired
    ImageRepository repository;

    @GetMapping("/meme")
    public Iterable<Image> getAllImages() {
        return repository.findAll();
    }

}
