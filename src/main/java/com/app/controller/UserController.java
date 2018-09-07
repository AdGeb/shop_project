package com.app.controller;

import com.app.exceptions.PasswordConfirmationException;
import com.app.model.Role;
import com.app.model.security.User;
import com.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", Role.values());
        return "security/register";
    }

    @PostMapping("/register")
    public String productAddPost(@ModelAttribute User user) {
        try {
            verifyPasswordConfirmation(user);
        } catch (PasswordConfirmationException e) {
            System.err.println(e.getMessage());
            return "redirect:/security/register";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("error", "");
        return "security/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("error", "NIEPRAWIDLOWE DANE LOGOWANIA");
        return "security/login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(@RequestParam String message, Model model) {
        model.addAttribute("message", message);
        return "security/accessDenied";
    }

    // ZROBIC OSOBNY VALIDATOR !!!!!!!!
    private void verifyPasswordConfirmation(final User user) throws PasswordConfirmationException {
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new PasswordConfirmationException("WRONG PASSWORD CONFIRMATION!");
        }
    }


}
