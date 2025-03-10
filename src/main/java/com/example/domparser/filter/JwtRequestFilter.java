package com.example.domparser.filter;

import com.example.domparser.service.CustomUserDetailsService;
import com.example.domparser.util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component()
public class JwtRequestFilter extends OncePerRequestFilter {

    private static  final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
    throws ServletException, IOException {

        logger.info("We are reaching the doFilterInternal()");

        logger.info("The servlet path is " + request.getServletPath() );

        if ("/dom-parser/authenticate/".equals(request.getServletPath())) {
            logger.info("Bypassing the filter check!!");
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            jwt = authorizationHeader.substring(7);
            logger.info("Extracted JWT successfully!");

            username = jwtUtil.extractUsername(jwt);
            logger.info("Extracted Username!");

        }

        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if( jwtUtil.validateToken(jwt, userDetails)){
                logger.info("The user details are validated successfully!");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        logger.info("We are reaching the end of the doFilterInternal()");

        filterChain.doFilter(request, response);
    }

}
