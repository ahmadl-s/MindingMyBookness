package com.mindingmybookness.Controller;

import com.mindingmybookness.DTOs.LoginRequest;
import com.mindingmybookness.DTOs.RefreshToken;
import com.mindingmybookness.Entity.User;
import com.mindingmybookness.Service.UserService;
import com.mindingmybookness.DTOs.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Tells spring that this should be JSON, while @Controller is for HTML
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping
    public List<User> getAllUsersController(){
        return userService.getAllUsers();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("searchUsername/{username}")
    public List<User> searchUserController(@PathVariable String username){
        return userService.searchUserByName(username);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signupController(@RequestBody SignupRequest signupRequest){
        return userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody LoginRequest loginRequest){
        return  userService.login(loginRequest);

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshToken refreshToken){
        return userService.refresh(refreshToken);
    }

}
