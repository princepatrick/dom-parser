package com.example.domparser.model;

public class AuthenticationResponse {

    private String jwtToken;

    public AuthenticationResponse( String jwtToken ){
        System.out.println("We are initializing the AuthenticationRequest()");
        this.jwtToken = jwtToken;
    }

    public AuthenticationResponse(){

    }

    public String getJwtToken(){
        return jwtToken;
    }

    public void setJwtToken( String jwtToken ){
        this.jwtToken = jwtToken;
    }

}
