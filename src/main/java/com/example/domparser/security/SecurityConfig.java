package com.example.domparser.security;

import com.example.domparser.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import com.example.domparser.filter.JwtRequestFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.annotation.web.configurers.oauth2.OAuth2LoginConfigurer;



@Configuration
public class SecurityConfig {
    
    private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.debug("The http request has reached filterChain() method in SecurityConfig");

        http.csrf(csrf -> csrf.disable())
                .sessionManagement( sesssion -> sesssion.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth ->
                    auth.requestMatchers("/dom-parser/authenticate/**").permitAll().
                            anyRequest().authenticated()
            ).oauth2Login(Customizer.withDefaults());

//            .httpBasic();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        logger.debug("The http request has left the filterChain() method in SecurityConfig");

        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        logger.debug("We have reached the webSecurityCustomizer() method in SecurityConfig");

        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}

