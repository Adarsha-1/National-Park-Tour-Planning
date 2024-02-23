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

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> handleGeneralException(GeneralException exception) {
        ErrorDetails error = new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
