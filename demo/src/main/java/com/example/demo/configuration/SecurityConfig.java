package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.repository.UserRepository;

@Configuration
public class SecurityConfig {

    @Autowired UserRepository authRepository;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/*").permitAll()
                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                /* PROJECT ENDPOINTS */
                .requestMatchers(HttpMethod.GET, "/api/v1/project/**").hasAnyRole("ADMIN", "PM", "USER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/v1/project/**").hasAnyRole("ADMIN", "PM")
                .requestMatchers(HttpMethod.PUT, "/api/v1/project/**").hasAnyRole("ADMIN", "PM")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/**").hasAnyRole("ADMIN", "PM")
                /* TASK ENDPOINTS */
                .requestMatchers(HttpMethod.GET, "/api/v1/project/*/task/**").hasAnyRole("ADMIN", "PM", "USER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/v1/project/*/task/**").hasAnyRole("ADMIN", "PM", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/project/*/task/**").hasAnyRole("ADMIN", "PM", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/*/task/**").hasAnyRole("ADMIN", "PM")
                /* COMMENT ENDPOINTS */
                .requestMatchers(HttpMethod.GET, "/api/v1/project/*/task/*/comment/**").hasAnyRole("ADMIN", "PM", "USER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/api/v1/project/*/task/*/comment/**").hasAnyRole("ADMIN", "PM", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/project/*/task/*/comment/**").hasAnyRole("ADMIN", "PM", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/*/task/*/comment/**").hasAnyRole("ADMIN", "PM")
                .anyRequest().authenticated());
            return httpSecurity.build();
        }
        
        @Bean
        public UserDetailsService userDetailsService() {
            return new CustomUserDetailsService();
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
