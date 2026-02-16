package com.mindingmybookness.Controller;

import com.mindingmybookness.Entity.Login;
import com.mindingmybookness.Entity.User;
import com.mindingmybookness.Repository.UserRepository;
import com.mindingmybookness.Service.UserService;
import com.mindingmybookness.auth.SignupRequest;
import org.hibernate.dialect.function.StringFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;
import java.util.List;

@RestController
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


    @GetMapping("/searchUsername/{username}")
    public List<User> searchUserController(@PathVariable String username){
        return userService.searchUserByName(username);
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signupController(@RequestBody SignupRequest signupRequest){
        return userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginController(@RequestBody Login userLogin){
        return  userService.login(userLogin);
    }

}
