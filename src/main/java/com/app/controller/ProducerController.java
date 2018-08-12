package com.app.controller;

import com.app.dto.ProducerDto;
import com.app.service.ProducerService;
import com.app.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/add")
    public String producerAddGet(Model model) {
        model.addAttribute("producer", new ProducerDto());
        return "producer/add";
    }

    @PostMapping("/add")
    public String teamAddPost(@ModelAttribute ProducerDto producerDto) {
        String filename = FileManager.addFileToResources(producerDto.getMultipartFile());
        producerDto.setPhotoName(filename);
        producerService.addProducer(producerDto);
        return "redirect:/";
    }
}
