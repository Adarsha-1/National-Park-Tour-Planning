package org.example.exception;

public class EmailVerificationException extends Exception{

    private static final long serialVersionUID = 5861310537366287163L;

    public EmailVerificationException(String message) {
        super(message);
    }
}
