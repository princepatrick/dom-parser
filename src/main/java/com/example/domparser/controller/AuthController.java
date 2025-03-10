package com.example.domparser.controller;

import com.example.domparser.model.AuthenticationRequest;
import com.example.domparser.model.AuthenticationResponse;
import com.example.domparser.model.ParserResponse;
import com.example.domparser.service.CustomUserDetailsService;
import com.example.domparser.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RequestMapping("/dom-parser/authenticate")
@RestController
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest ) throws Exception{

        logger.info("We have entered into the createAuthenticationToken()");

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            logger.info("Successful Authentication at AuthController");
        } catch ( BadCredentialsException ex ){
//            throw new Exception("Incorrect user name or password");
            logger.error("Incorrect Username or password at AuthController");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");

        }

        logger.info("We have exited from the createAuthenticationToken() and userDetails are set");

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        logger.info("The JWT token is generated successfully");

        return ResponseEntity.ok( new AuthenticationResponse(jwt));
    }

}
