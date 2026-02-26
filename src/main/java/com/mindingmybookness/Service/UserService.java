package com.mindingmybookness.Service;

import com.mindingmybookness.DTOs.LoginRequest;
import com.mindingmybookness.DTOs.RefreshToken;
import com.mindingmybookness.Entity.Role;
import com.mindingmybookness.Entity.User;
import com.mindingmybookness.Repository.UserRepository;
import com.mindingmybookness.DTOs.SignupRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }



    public List<User> searchUserByName(String username){
        return userRepository.findAllByUsernameContainingIgnoreCase(username);
    }




    /// /////// AUTHENTICATIONS

    @Transactional
    public ResponseEntity<?> login(LoginRequest loginRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            //If it reches here it means that user is authentic

            User user = userRepository.findUsersByUsername(loginRequest.getUsername())
                    .orElseThrow();

            String token = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            return new ResponseEntity<>( "token: " + token +"\n refresh token: "+ refreshToken , HttpStatus.ACCEPTED );

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid user or username", HttpStatus.UNAUTHORIZED);
        }
    }



    public ResponseEntity<?> refresh(RefreshToken refreshToken){

        // 1. Validate signature + expiry
        if(jwtService.isTokenExpired(refreshToken.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 2. Extract username from token
        String username = jwtService.extractUsername(refreshToken.getRefreshToken());

        // 3. Load user
        User user = userRepository.findUsersByUsername(username)
                .orElseThrow();

        // 4. OPTIONAL (better): verify refresh token matches stored one
        if(!refreshToken.getRefreshToken().equals(user.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 5. Generate new access token
        String newAccessToken = jwtService.generateToken(user);

        return new ResponseEntity<>("new JWT token: \n" + newAccessToken,HttpStatus.OK);

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
