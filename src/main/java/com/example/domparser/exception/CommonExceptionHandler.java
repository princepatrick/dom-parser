package com.example.domparser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(DomParserException.class)
    public ResponseEntity<HttpStatus> handleDomParserException(DomParserException ex ){
        ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        System.out.println("A Dom Parser Exception has occurred" + ex.getMessage());

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpStatus> handleException(Exception ex ){
        ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        System.out.println("An Exception has occurred" + ex.getMessage());

        return response;
    }

}
