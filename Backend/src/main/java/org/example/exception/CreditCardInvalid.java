package org.example.exception;

public class CreditCardInvalid extends Exception{

    private static final long serialVersionUID = 5861310537366287163L;

    public CreditCardInvalid(String message) {
        super(message);
    }
}
