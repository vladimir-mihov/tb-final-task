package org.talentboost.finaltask.backend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.talentboost.finaltask.backend.data.Image;
import org.talentboost.finaltask.backend.repository.ImageRepository;
import org.talentboost.finaltask.backend.util.FileService;
import org.talentboost.finaltask.backend.util.ValidationService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PrivateAPIControllerTest {

    PrivateAPIController api;

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private FileService fileService;
    @Mock
    private ValidationService validationService;

    @Before
    public void setUp() {
        api = new PrivateAPIController(
                imageRepository,
                fileService,
                validationService
        );
    }

    @Test
    public void shouldCreateFileAndSaveImage() throws IOException {
        String title = "title";
        MultipartFile fileMock = mock(MultipartFile.class);
        MultipartFile[] files = new MultipartFile[]{fileMock};

        ResponseEntity<Image> response = api.createImage(title, files);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(validationService).requireFiles(files);
        verify(fileService).createFile(fileMock);
        verify(imageRepository).save(any(Image.class));
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenCreatingImageAndFilesIsNull()
            throws IOException {
        String title = "title";
        MultipartFile[] files = new MultipartFile[]{null};

        doThrow(ResponseStatusException.class)
                .when(validationService)
                .requireFiles(null);
        doThrow(ResponseStatusException.class)
                .when(validationService)
                .requireFiles(new MultipartFile[]{null});

        api.createImage(title, files);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenCreatingImageWithEmptyTitleString()
            throws IOException {
        String title = "";
        MultipartFile[] files = new MultipartFile[0];

        doThrow(ResponseStatusException.class)
                .when(validationService)
                .validateImageTitle("");

        api.createImage(title, files);
    }

    @Test
    public void shouldReturnUpdatedImageWhenUpdating() throws IOException {
        String currentTitle = "currentTitle";
        String newTitle = "newTitle";

        MultipartFile mockFile = mock(MultipartFile.class);
        MultipartFile[] files = new MultipartFile[] { mockFile };

        Image mockImage = mock(Image.class);

        when(imageRepository.findById(any()))
                .thenReturn(Optional.of(mockImage));

        Image updated = api.alterImage(1, newTitle, files);

        verify(mockImage).setTitle(newTitle);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenUpdatingIfTitleIsInvalid() throws IOException {
        Image imageMock = mock(Image.class);

        when(imageRepository.findById(any())).thenReturn(Optional.of(imageMock));
        doThrow(ResponseStatusException.class)
                .when(validationService).validateImageTitle("");

        api.alterImage(1, "", new MultipartFile[0]);
    }

    @Test
    public void shouldNotThrowWhenUpdatingIfFilesIsNullOrEmpty() throws IOException {
        when(imageRepository.findById(any()))
                .thenReturn(Optional.of(mock(Image.class)));

        api.alterImage(1, "1", null);
        api.alterImage(1, "1", new MultipartFile[0]);
    }

    @Test
    public void shouldReturnNullWhenUpdatingIfImageNotFound() throws IOException {
        assertNull(api.alterImage(1, "someTitle", new MultipartFile[0]));
    }

    @Test
    public void shouldDeleteImageAndReturnIt() throws IOException {
        Image imageMock = mock(Image.class);
        String image = "image";

        when(imageRepository.findById(any()))
                .thenReturn(Optional.of(imageMock));
        when(imageMock.getImage()).thenReturn(image);

        api.deleteImage(1);

        verify(imageRepository).deleteById(1);
        verify(fileService).deleteFile(image);
    }

    @Test
    public void shouldReturnNullWhenDeletingNonexistentImage() throws IOException {
        assertNull(api.deleteImage(1));
    }

}