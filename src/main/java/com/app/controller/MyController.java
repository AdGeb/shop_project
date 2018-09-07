package com.app.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class MyController {
    @GetMapping("/")
    public String home() {
        return "main";
    }

    @GetMapping("/user")
    public String userPage() {
        return "users/user";
    }

    // http:localhost:8080/params1?imie=ADAM&age=20
    // http:localhost:8080/params1?imie=ADAM  -- age domyslnie = 0
    @GetMapping("/params1")
    public String params1(@RequestParam(name = "imie", required = true) String name, @RequestParam(defaultValue = "0") Integer age, Model model) {
        System.out.println("NAME: " + name);
        System.out.println("AGE: " + age);
        model.addAttribute("name", name); // pierwszy argument mowi pod jaka nazwa bedzie widoczna zmienna w szablonie html
        model.addAttribute("age", age);
        return "params1";
    }

    @GetMapping("/home")
    public String homePage() {return "main";}

    // http:localhost:8080/params1/ADAM/20
    @GetMapping("/params2/{name}/{age}")
    public String params2(Model model, @PathVariable String name, @PathVariable Integer age) {
        System.out.println("NAME: " + name);
        System.out.println("AGE: " + age);
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "params2";
    }
}
