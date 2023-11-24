package com.experis.course.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/photos", "/photos/show/**").hasAnyAuthority("ADMIN","USER")                // Index/Show   PHOTO
                .requestMatchers("/users").hasAuthority("ADMIN")                                    // Index        USERS
                .requestMatchers("/photos/create").hasAuthority("ADMIN")                            // Create       PHOTO
                .requestMatchers("/photos/edit/**").hasAuthority("ADMIN")                           // Edit         PHOTO
                .requestMatchers("/photos/delete/**").hasAuthority("ADMIN")                         // Delete       PHOTO
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")               // Metodi POST  PHOTO
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }

}
