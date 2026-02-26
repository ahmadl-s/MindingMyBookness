package com.mindingmybookness;

import com.mindingmybookness.Entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@SpringBootApplication
public class MindingMyBooknessApplication {

    public static void main(String[] args) {

        SpringApplication.run(MindingMyBooknessApplication.class, args);


    }
}
