package org.talentboost.finaltask.backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {

    private Map<String, String> allowedMimeTypes;

    public ValidationService() {
        Map<String, String> tmpMap = new HashMap<>();

        tmpMap.put("image/jpeg", "jpg");
        tmpMap.put("image/png", "png");
        tmpMap.put("image/gif", "gif");

        allowedMimeTypes = Collections.unmodifiableMap(tmpMap);
    }

    public void validateImageTitle(String imageTitle) {
        if (imageTitle == null || imageTitle.length() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image title should be at least 1 character long."
            );
        }
    }

    public void requireFiles(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No file."
            );
        }

        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (allowedMimeTypes.get(contentType) == null) {
                throw new ResponseStatusException(
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        String.format("Mime type %s is not supported.", contentType)
                );
            }
        }

    }

    public Map<String, String> getAllowedMimeTypes() {
        return allowedMimeTypes;
    }

}
