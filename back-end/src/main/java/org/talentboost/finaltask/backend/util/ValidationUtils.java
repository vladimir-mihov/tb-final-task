package org.talentboost.finaltask.backend.util;

import org.talentboost.finaltask.backend.exceptions.InvalidTitleException;

public class ValidationUtils {

    public void validateTitle(String title) {
        if(title.length() < 4) {
            throw new InvalidTitleException("Title length must be at least 4 characters.");
        }
    }

}
