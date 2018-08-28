package com.app.controller;

import com.app.dto.ProductDto;
import com.app.model.Role;
import com.app.model.security.User;
import com.app.repository.security.UserRepository;
import com.app.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", Role.values());
        return "security/register";
    }

    @PostMapping("/register")
    public String productAddPost(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/products";
    }
}
