package org.talentboost.finaltask.backend.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PublicAPIController.class)
public class PublicAPIControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PublicAPIController api;

    @Test
    public void getAllImagesTest() throws Exception {
        Image image = new Image("vlado", "vlado");
        Iterable<Image> images = Collections.singletonList(image);

        given(api.getAllImages()).willReturn(images);

        mvc.perform(
            get("/meme")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title", is(image.getTitle())));
    }

}