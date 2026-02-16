package com.mindingmybookness.Config;

import com.mindingmybookness.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor //this does dependency injection for me?

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")); //UserNameNotFoundExeception is in the loadUsername method so i have to use it
    }



}
