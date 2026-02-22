package com.mindingmybookness.Config;

import com.mindingmybookness.Entity.Role;
import com.mindingmybookness.Entity.User;
import com.mindingmybookness.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppConfig {

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AppConfig(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }




    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (userRepository.findUsersByRole(Role.ADMIN).isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); //temporary//use EV
                admin.setEmail("admin@MMB");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
        }
    };

}

}

//BEAN:
//What Spring Is Really Doing Internally.
//
//Conceptually something like:
//
 //UserRepository repo = applicationContext.getBean(UserRepository.class);
//PasswordEncoder encoder = applicationContext.getBean(PasswordEncoder.class);
//
//createAdmin(repo, encoder);
