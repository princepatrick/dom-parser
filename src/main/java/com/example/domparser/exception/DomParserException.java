package com.example.domparser.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomParserException extends RuntimeException{

    private static Logger logger = LoggerFactory.getLogger(DomParserException.class);

    public DomParserException( String message ){
        super(message);
        logger.error("We are facing errors at the DomParserException class with the error message " + message );
    }

}
