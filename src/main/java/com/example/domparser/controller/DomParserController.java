package com.example.domparser.controller;

import com.example.domparser.model.AuthenticationRequest;
import com.example.domparser.model.AuthenticationResponse;
import com.example.domparser.model.ParserResponse;
import com.example.domparser.service.CustomUserDetailsService;
import com.example.domparser.service.DomParserService;
import com.example.domparser.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("/dom-parser/")
public class DomParserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    DomParserService domParserService;

    @PostMapping("get-value/")
    public List<ParserResponse> parseDom(
            @RequestBody() String requestBody,
            @RequestParam(value = "element", required = false, defaultValue = "None") String elementFilter,
            @RequestParam(value = "attribute", required = false, defaultValue = "None") String attribueListFilter,
            @RequestParam(value = "returnAttribute", required = false, defaultValue = "None") String returnAttribute ){

        System.out.println("Entered the parseDom() method in DomParserController ");

        List<ParserResponse> responseDom = new ArrayList<>();

        responseDom = domParserService.parseDomTree( requestBody, elementFilter, attribueListFilter, returnAttribute );

        return responseDom;
    }

    @PostMapping("authenticate/")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest ) throws Exception{

        System.out.println("We have entered into the createAuthenticationToken()");

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch ( BadCredentialsException ex ){
//            throw new Exception("Incorrect user name or password");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Incorrect username or password");
           System.out.println("Throwing an exception for in DomParserController class " + ex.getMessage() );

           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("error", "Incorrect username or password"));


        }

        System.out.println("We have exited from the createAuthenticationToken() and userDetails are set");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok( new AuthenticationResponse(jwt));
    }


}
