package org.talentboost.finaltask.backend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;
import org.talentboost.finaltask.backend.util.Env;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PublicAPIControllerTest {

    private PublicAPIController api;

    @Mock
    private ImageRepository repo;
    @Mock
    private Env env;

    @Before
    public void setUp() {
        when(env.get(anyString())).thenReturn("localhost");

        api = new PublicAPIController(repo, env);
    }

    @Test
    public void shouldReturnCorrectAmount() {
        Image image = new Image("title", "image");
        when(repo.findAll()).thenReturn(Collections.singletonList(image));

        Collection<Image> returnedFromApi = (Collection<Image>) api.getAllImages();
        assertEquals(1, returnedFromApi.size());
    }

    @Test
    public void shouldReturnCorrectImage() {
        String imageImage = "image";
        Image image = new Image("title", imageImage);
        when(repo.findAll()).thenReturn(Collections.singletonList(image));

        Image returnedFromApi = api.getAllImages().iterator().next();
        assertEquals("http://localhost:8090/" + imageImage, returnedFromApi.getImage());
    }

    @Test
    public void shouldReturnCorrectTitle() {
        String imageTitle = "image";
        Image image = new Image(imageTitle, "image");
        when(repo.findAll()).thenReturn(Collections.singletonList(image));

        Image returnedFromApi = api.getAllImages().iterator().next();
        assertEquals(imageTitle, returnedFromApi.getTitle());
    }
}