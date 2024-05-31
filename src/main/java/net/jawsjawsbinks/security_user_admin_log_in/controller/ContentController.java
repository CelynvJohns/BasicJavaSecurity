package net.jawsjawsbinks.security_user_admin_log_in.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    
    // sets up the homepage
    @GetMapping("/home")
    public String homepage() {
        return "home";
    }

    // sets up admin home page location
    @GetMapping("/admin/home")
    public String adminPage(){
        return "admin/home";
    }

    // sets up the user home page location
    @GetMapping("/user/home")
    public String userPage(){
        return "user/home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "custom_login";
    }

    @GetMapping("/register")
    public String registerUser(){
        return "register";
    }

}
