package com.app.controller;

import com.app.dto.ProducerDto;
import com.app.dto.ProductDto;
import com.app.model.Category;
import com.app.service.ProducerService;
import com.app.utils.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProducerService producerService;

    public ProductController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/add")
    public String producerAddGet(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("producers", producerService.getAllProducers());
        model.addAttribute("categories", Category.values());
        return "product/add";
    }

    @PostMapping("/add")
    public String producerAddPost(@ModelAttribute ProductDto productDto) {
        System.out.println(productDto);
        return "redirect:/producers";
    }
}
