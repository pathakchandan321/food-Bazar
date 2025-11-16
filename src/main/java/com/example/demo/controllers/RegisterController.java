package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserServices userService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("userRegistration", new User());
        return "register"; // Name of your HTML template (register.html)
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("userRegistration") User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/login";  // after successful registration
        } catch (Exception e) {
            model.addAttribute("error", "Email already exists or invalid input!");
            return "register";
        }
    }
}

