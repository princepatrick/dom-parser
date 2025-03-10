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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController()
@RequestMapping("/dom-parser/")
public class DomParserController {

    private static final Logger logger = LoggerFactory.getLogger(DomParserController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    DomParserService domParserService;

    @PostMapping("get-value/")
    public ResponseEntity<String> parseDom(
            @RequestBody() String requestBody,
            @RequestParam(value = "element", required = false, defaultValue = "None") String elementFilter,
            @RequestParam(value = "attribute", required = false, defaultValue = "None") String attribueListFilter,
            @RequestParam(value = "returnAttribute", required = false, defaultValue = "None") String returnAttribute ){

        logger.debug("Entering into parseDom() method");
        
        logger.debug("Entered the parseDom() method in DomParserController");

        List<ParserResponse> responseDom = new ArrayList<>();

        responseDom = domParserService.parseDomTree( requestBody, elementFilter, attribueListFilter, returnAttribute );

        return ResponseEntity.ok("Successful Call");
    }

    @PostMapping("authenticate/")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest ) throws Exception{

        logger.debug("We have entered into the createAuthenticationToken()");

        String userName = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        logger.debug("The username is " + userName +" and passsword is " + password );

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( userName, password ));
            logger.info("The authentication is successful in DomParserController class");
        } catch ( BadCredentialsException ex ){
//            throw new Exception("Incorrect user name or password");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Incorrect username or password");
           logger.error("Throwing an exception for in DomParserController class " + ex.getMessage() );

           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("error", "Incorrect username or password"));


        }

        logger.debug("We have exited from the createAuthenticationToken() and userDetails are set");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        logger.debug("The JWT token is generated at DomParserController class");

        return ResponseEntity.ok( new AuthenticationResponse(jwt));
    }


}
