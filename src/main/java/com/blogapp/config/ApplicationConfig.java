package com.blogapp.config;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blogapp.entities.auth.Utente;
import com.blogapp.repositories.auth.UtenteRepository;
import com.blogapp.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.security.SecureRandom;

@Configuration
public class ApplicationConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JWTUtil jwtUtil, ObjectMapper objectMapper, UtenteRepository utenteRepository) throws Exception {
        httpSecurity.csrf(csrfConfigurer -> csrfConfigurer.disable());
        httpSecurity.cors(corsConfigurer -> corsConfigurer.disable());
        httpSecurity.sessionManagement(sessionManagementConfigurer ->
           sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.authorizeHttpRequests(requestMatcherRegistry -> {
           requestMatcherRegistry.requestMatchers(HttpMethod.GET, "/**").permitAll();
        });
        httpSecurity.addFilterBefore(new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest httpReq =  (HttpServletRequest)servletRequest;
                String header = httpReq.getHeader(HttpHeaders.AUTHORIZATION);
                if(header != null && header.startsWith(jwtUtil.getPrefix())) {
                    try {
                        String jwt = header.replaceFirst(jwtUtil.getPrefix(), "");
                        DecodedJWT decoded = jwtUtil.decode(jwt);
                        Claim userClaim = decoded.getClaim("user");
                        Utente utente = objectMapper.readValue(userClaim.asString(), Utente.class);
                        utenteRepository.findById(utente.getId()).ifPresent(u -> {
                            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                                    u.getEmail(), null, u.getRuoli()
                            ));
                        });
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }
}