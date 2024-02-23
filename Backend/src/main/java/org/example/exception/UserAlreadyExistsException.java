package org.example.exception;

public class UserAlreadyExistsException extends Exception{
    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
