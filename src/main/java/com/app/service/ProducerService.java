package com.app.service;

import com.app.dto.ModelMapper;
import com.app.dto.ProducerDto;
import com.app.exceptions.ProducerNotFoundException;
import com.app.model.Producer;
import com.app.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Producer getProducer(Long id) {
        return producerRepository.findById(id).orElseThrow(() -> new ProducerNotFoundException("Producer with id = [" + id + "] doesn't exist"));
    }

    public void addProducer(ProducerDto producerDto) {
        Producer producer = modelMapper.fromProducerDtoToProducer(producerDto);
        producerRepository.save(producer);
    }

    public List<ProducerDto> getAllProducers() {
        return producerRepository.findAll()
                .stream()
                .map(p -> modelMapper.fromProducerToProducerDto(p))
                .collect(Collectors.toList());
    }
}
