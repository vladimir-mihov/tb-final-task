package org.talentboost.finaltask.backend.exceptions;

public abstract class VladoException extends RuntimeException {
    public VladoException() {
    }

    public VladoException(String s) {
        super(s);
    }
}
