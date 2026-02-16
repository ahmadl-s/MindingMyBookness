package com.mindingmybookness.Service;

import com.mindingmybookness.Entity.Login;
import com.mindingmybookness.Entity.Role;
import com.mindingmybookness.Entity.User;
import com.mindingmybookness.Repository.UserRepository;
import com.mindingmybookness.auth.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }



    public List<User> searchUserByName(String username){
        return userRepository.findAllByUsernameContainingIgnoreCase(username);
    }


    public void login(Login userLogin){

    }


    public ResponseEntity<String> signup(SignupRequest signupRequest){

        if (signupRequest.getEmail() == null || signupRequest.getUsername() == null || signupRequest.getPassword() == null){
            return new ResponseEntity<>("Fill in appropriately", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(signupRequest.getUsername()) || userRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("Username or email already exists", HttpStatus.CONFLICT);
        }


       String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User userToStore = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();


        userRepository.save(userToStore);
        return new ResponseEntity<>("User saved Succesfully", HttpStatus.CREATED);

    }




}
