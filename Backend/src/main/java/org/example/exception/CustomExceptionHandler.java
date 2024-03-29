package org.example.exception;

import org.example.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserException(UserAlreadyExistsException exception) {
        ErrorDetails error = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailVerificationException.class)
    public ResponseEntity<?> handelEmailVerificationException(EmailVerificationException exception) {
        ErrorDetails error = new ErrorDetails(exception.getMessage(), HttpStatus.REQUEST_TIMEOUT);
        return new ResponseEntity(error, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> handleGeneralExceptions(GeneralException exception) {
        ErrorDetails error = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidItinerary.class)
    public ResponseEntity<?> handleInvalidItineraryException(InvalidItinerary exception) {
        ErrorDetails error = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyPaid.class)
    public ResponseEntity<?> handleUserAlreadyPaidException(UserAlreadyPaid exception) {
        ErrorDetails error = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
