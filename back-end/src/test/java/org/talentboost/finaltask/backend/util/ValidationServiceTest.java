package org.talentboost.finaltask.backend.util;

import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;

public class ValidationServiceTest {

    ValidationService validationService;

    @Before
    public void setUp() {
        validationService = new ValidationService();
    }

    @Test
    public void shouldNotThrowWhenImageTitleIsValid() {
        validationService.validateImageTitle("vlado");
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenTitleIsNull() {
        validationService.validateImageTitle(null);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenTitleIsEmptyString() {
        validationService.validateImageTitle("");
    }

    @Test
    public void shouldNotThrowWhenFilesAreValid() {
        MultipartFile mockFileJpeg = mock(MultipartFile.class);
        MultipartFile mockFileGif = mock(MultipartFile.class);
        MultipartFile mockFilePng = mock(MultipartFile.class);
        MultipartFile[] files = new MultipartFile[] { mockFileJpeg, mockFileGif, mockFilePng };

        when(mockFileJpeg.getContentType())
                .thenReturn(ContentType.IMAGE_JPEG.toString());
        when(mockFileGif.getContentType())
                .thenReturn(ContentType.IMAGE_GIF.toString());
        when(mockFilePng.getContentType())
                .thenReturn(ContentType.IMAGE_PNG.toString());

        validationService.requireFiles(files);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenFilesIsNull() {
        validationService.requireFiles(null);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowWhenFilesIsEmptyArray() {
        validationService.requireFiles(new MultipartFile[0]);
    }
}

