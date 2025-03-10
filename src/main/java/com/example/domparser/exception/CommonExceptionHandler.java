package com.example.domparser.exception;

import com.example.domparser.model.ParserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CommonExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(DomParserException.class)
    public List<ParserResponse> handleDomParserException(DomParserException ex ){
        logger.debug("Entering into the handleDomParserException() block");

        List<ParserResponse> response = new ArrayList<>();

        ParserResponse parserResponse = new ParserResponse();
        parserResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        parserResponse.setParsedResponse("A Dom Parser Exception has occurred " + ex.getMessage());

        logger.debug("A Dom Parser Exception has occurred " + ex.getMessage());

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ParserResponse>> handleException(Exception ex) {
        logger.error("Entering into the handleException() block");

        List<ParserResponse> response = new ArrayList<>();

        ParserResponse parserResponse = new ParserResponse();
        parserResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        parserResponse.setParsedResponse("An Exception has occurred: " + ex.getMessage());

        // Add the parserResponse to the list so that it is returned
        response.add(parserResponse);

        logger.error("An Exception has occurred: " + ex.getMessage());

        // Return a ResponseEntity with the error list and appropriate status
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }


}
