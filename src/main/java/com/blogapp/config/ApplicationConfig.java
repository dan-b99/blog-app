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
import org.springframework.web.cors.CorsConfiguration;
import java.io.IOException;
import java.security.SecureRandom;

@Configuration
public class ApplicationConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JWTUtil jwtUtil, ObjectMapper objectMapper, UtenteRepository utenteRepository) throws Exception {
        httpSecurity.csrf(csrfConfigurer -> csrfConfigurer.disable());
        httpSecurity.cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("http://localhost:4200/");
            configuration.addAllowedMethod("*");
            configuration.addAllowedHeader("*");
            return configuration;
        }));
        httpSecurity.sessionManagement(sessionManagementConfigurer ->
           sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.authorizeHttpRequests(requestMatcherRegistry -> {
           requestMatcherRegistry.requestMatchers("/error").permitAll();
           requestMatcherRegistry.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
           requestMatcherRegistry.requestMatchers("/users/all").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers(HttpMethod.GET, "/users/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.PATCH, "/users/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers("/articles/not-approved/**").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers(HttpMethod.GET, "/articles/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.POST, "/articles/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.PUT, "/articles/**").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers(HttpMethod.DELETE, "/articles/**").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers(HttpMethod.GET, "/categories/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN");
           requestMatcherRegistry.requestMatchers(HttpMethod.GET, "/comments/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.POST, "/comments/**").authenticated();
           requestMatcherRegistry.requestMatchers(HttpMethod.GET, "/votes/**").authenticated();
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
