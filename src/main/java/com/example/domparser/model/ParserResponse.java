package com.example.domparser.model;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ParserResponse {

    private HttpStatus status;
    private String parsedResponse;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getParsedResponse() {
        return parsedResponse;
    }

    public void setParsedResponse(String parsedResponse) {
        this.parsedResponse = parsedResponse;
    }

    public void setAttributes(Map<String, String> map){

    }
}
