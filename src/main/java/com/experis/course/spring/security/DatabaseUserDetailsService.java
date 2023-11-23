package com.experis.course.spring.security;

import com.experis.course.spring.model.User;
import com.experis.course.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Override dei metodi (visto che ho fatto implements)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> loggedUser = userRepository.findByEmail(username);

        if (loggedUser.isPresent()) {

            return new DatabaseUserDetails(loggedUser.get());
        } else {

            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
    }
}
