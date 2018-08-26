package com.app.controller;

import com.app.dto.ProductDto;
import com.app.model.Category;
import com.app.model.Service;
import com.app.service.ProducerService;
import com.app.service.ProductService;
import com.app.utils.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String productAddGet(Model model) {
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
        return "redirect:/products";
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/all";
    }

    @GetMapping("/details/{id}")
    public String productDetailsGet(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "product/details";
    }

    @PostMapping("/remove")
    public String productRemovePost(@RequestParam Long id) {
        ProductDto productDto = productService.getProduct(id);
        FileManager.removeFileFromResources(productDto.getPhotoName());
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String productEditGet(@PathVariable Long id,  Model model) {
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("categories", Category.values());
        model.addAttribute("producers", producerService.getAllProducers());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String productEditPost(@ModelAttribute ProductDto productDto) {
        FileManager.updateFileInResources(productDto.getMultipartFile(), productDto.getPhotoName());
        productService.addOrUpdate(productDto);
        return "redirect:/products";
    }
}
