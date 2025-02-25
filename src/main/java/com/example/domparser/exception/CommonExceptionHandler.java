package com.example.domparser.exception;

import com.example.domparser.model.ParserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(DomParserException.class)
    public List<ParserResponse> handleDomParserException(DomParserException ex ){
        System.out.println("Entering into the handleDomParserException() block");

        List<ParserResponse> response = new ArrayList<>();

        ParserResponse parserResponse = new ParserResponse();
        parserResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        parserResponse.setParsedResponse("A Dom Parser Exception has occurred " + ex.getMessage());

        System.out.println("A Dom Parser Exception has occurred " + ex.getMessage());

        return response;
    }

    @ExceptionHandler(Exception.class)
    public List<ParserResponse> handleException(Exception ex ){
        System.out.println("Entering into the handleException() block ");

        List<ParserResponse> response = new ArrayList<>();

        ParserResponse parserResponse = new ParserResponse();
        parserResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        parserResponse.setParsedResponse("An Exception has occurred " + ex.getMessage());

        System.out.println("An Exception has occurred " + ex.getMessage());

        return response;
    }

}
