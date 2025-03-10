package com.example.domparser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationResponse {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationResponse.class);

    private String jwtToken;

    public AuthenticationResponse( String jwtToken ){
        logger.debug("We are initializing the AuthenticationResponse()");
        this.jwtToken = jwtToken;
    }

    public String getJwtToken(){
        logger.debug("We are retrieving the JWT Token at AuthenticationResponse");
        return jwtToken;
    }

    public void setJwtToken( String jwtToken ){
        logger.debug("We are setting the JWT Token at AuthenticationResponse");
        this.jwtToken = jwtToken;
    }

}
