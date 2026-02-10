package com.mindingmybookness.Service;

import com.mindingmybookness.Entity.Login;
import com.mindingmybookness.Entity.User;
import com.mindingmybookness.Repository.UserRepository;
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


    public ResponseEntity<String> login(Login userLogin){

        if (userRepository.existsByUsername(userLogin.getUsername())){
            User user = userRepository.findUsersByUsername (userLogin.getUsername());

            if (passwordEncoder.matches(userLogin.getPassword(), user.getPassword())){


                return new ResponseEntity<>("Username and Password correct! Successfully logged in! \n token: " + jwtService.generateToken(user), HttpStatus.OK);

            }else{
               return new ResponseEntity<>("Correct Username, but Wrong Password", HttpStatus.UNAUTHORIZED);
            }

        }

            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> signup(User user){

        if (user.getEmail() == null || user.getUsername() == null || user.getPassword() == null){
            return new ResponseEntity<>("Fill in appropriately", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

       String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return new ResponseEntity<>("User saved Succesfully", HttpStatus.OK);

    }




}
