package com.app.controller;

import com.app.dto.ProducerDto;
import com.app.service.ProducerService;
import com.app.utils.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/producers")
public class ProducerController {

    private ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/home")
    public String homePage() {
        return "main";
    }

    @GetMapping("/add")
    public String producerAddGet(Model model) {
        model.addAttribute("producer", new ProducerDto());
        return "producer/add";
    }

    @PostMapping("/add")
    public String producerAddPost(@ModelAttribute ProducerDto producerDto) {
        String filename = FileManager.addFileToResources(producerDto.getMultipartFile());
        producerDto.setPhotoName(filename);
        producerService.addOrUpdate(producerDto);
        return "redirect:/producers";
    }

    @GetMapping("/edit/{id}")
    public String producerEditGet(@PathVariable Long id,  Model model) {
        model.addAttribute("producer", producerService.getProducer(id));
        return "producer/edit";
    }

    @PostMapping("/edit")
    public String producerEditPost(@ModelAttribute ProducerDto producerDto) {
        FileManager.updateFileInResources(producerDto.getMultipartFile(), producerDto.getPhotoName());
        producerService.addOrUpdate(producerDto);
        return "redirect:/producers";
    }

    @GetMapping
    public String getAllProducers(Model model) {
        model.addAttribute("producers", producerService.getAllProducers());
        return "producer/all";
    }

    @PostMapping("/remove")
    public String teamRemovePost(@RequestParam Long id) {
        ProducerDto producerDto = producerService.getProducer(id);
        FileManager.removeFileFromResources(producerDto.getPhotoName());
        producerService.deleteProducer(id);
        return "redirect:/producers";
    }

    @GetMapping("/details/{id}")
    public String producerDetailsGet(@PathVariable Long id,  Model model) {
        model.addAttribute("producer", producerService.getProducer(id));
        return "producer/details";
    }
}
