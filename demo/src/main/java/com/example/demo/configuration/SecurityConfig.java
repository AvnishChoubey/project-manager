package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired JwtAuthFilter jwtAuthFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                // .requestMatchers("/api/v1/users/**").permitAll()
                /* PROJECT ENDPOINTS */
                .requestMatchers(HttpMethod.GET, "/api/v1/project/**").hasAnyRole("ADMIN", "PM", "USER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/v1/project/**").hasAnyRole("PM")
                .requestMatchers(HttpMethod.PUT, "/api/v1/project/**").hasAnyRole("PM")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/**").hasAnyRole("PM")
                /* TASK ENDPOINTS */
                .requestMatchers(HttpMethod.GET, "/api/v1/project/*/task/**").hasAnyRole("ADMIN", "PM", "USER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/v1/project/*/task/**").hasAnyRole("PM", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/project/*/task/**").hasAnyRole("PM", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/*/task/**").hasAnyRole("PM")
                /* COMMENT ENDPOINTS */
                .requestMatchers(HttpMethod.GET, "/api/v1/project/*/task/*/comment/**").hasAnyRole("ADMIN", "PM", "USER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/v1/project/*/task/*/comment/**").hasAnyRole("PM", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/project/*/task/*/comment/**").hasAnyRole("PM", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/*/task/*/comment/**").hasAnyRole("PM")
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
