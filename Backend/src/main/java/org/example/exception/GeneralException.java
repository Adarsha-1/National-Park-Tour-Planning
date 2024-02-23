package org.example.exception;

public class GeneralException extends RuntimeException{
    private static final long serialVersionUID = 5861310537366287163L;

    public GeneralException(String message) {
        super(message);
    }
}
