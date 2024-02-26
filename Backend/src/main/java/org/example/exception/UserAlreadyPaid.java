package org.example.exception;

public class UserAlreadyPaid extends Exception{

    private static final long serialVersionUID = 5861310537366287163L;

    public UserAlreadyPaid(String message) {
        super(message);
    }
}
