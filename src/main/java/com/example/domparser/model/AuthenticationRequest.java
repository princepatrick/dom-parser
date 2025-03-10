package com.example.domparser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationRequest {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationRequest.class);

    private String username;

    private String password;

    public AuthenticationRequest(){

    }

    public AuthenticationRequest( String username, String password ){
        logger.debug("We are initializing the AuthenticationRequest()");
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername( String username ){
        this.username = username;
    }

    public void setPassword( String password ){
        this.password = password;
    }

}
