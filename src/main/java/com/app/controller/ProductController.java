package com.app.controller;

import com.app.dto.ProducerDto;
import com.app.dto.ProductDto;
import com.app.model.Category;
import com.app.model.Service;
import com.app.service.ProducerService;
import com.app.service.ProductService;
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

    private ProductService productService;
    private ProducerService producerService;

    public ProductController(final ProductService productService, final ProducerService producerService) {
        this.productService = productService;
        this.producerService = producerService;
    }

    @GetMapping("/add")
    public String producerAddGet(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("producers", producerService.getAllProducers());
        model.addAttribute("categories", Category.values());
        model.addAttribute("services", Service.values());
        return "product/add";
    }

    @PostMapping("/add")
    public String productAddPost(@ModelAttribute ProductDto productDto) {
        String fileName = FileManager.addFileToResources(productDto.getMultipartFile());
        productDto.setPhotoName(fileName);
        productService.addOrUpdate(productDto);
        return "redirect:/producers";
    }
}
