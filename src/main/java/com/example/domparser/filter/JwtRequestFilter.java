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

@Component()
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
    throws ServletException, IOException {

        System.out.println("We are reaching the doFilterInternal()");

        System.out.println("The servlet path is " + request.getServletPath() );

        if ("/dom-parser/authenticate/".equals(request.getServletPath())) {
            System.out.println("Bypassing the filter check!!");
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            jwt = authorizationHeader.substring(7);
            System.out.println("Extracted JWT: " + jwt);

            username = jwtUtil.extractUsername(jwt);
            System.out.println("Extracted Username: " + username);

        }

        System.out.println("The username of the user is " + username);

        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if( jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        System.out.println("We are reaching the end of the doFilterInternal()");

        filterChain.doFilter(request, response);
    }

}
