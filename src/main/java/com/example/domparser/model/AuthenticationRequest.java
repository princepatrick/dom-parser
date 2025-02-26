package com.example.domparser.model;

public class AuthenticationRequest {

    private String username;

    private String password;

    public AuthenticationRequest(){

    }

    public AuthenticationRequest( String username, String password ){
        System.out.println("We are initializing the AuthenticationRequest()");
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
