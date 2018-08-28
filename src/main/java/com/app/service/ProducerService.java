package com.app.service;

import com.app.dto.ModelMapper;
import com.app.dto.ProducerDto;
import com.app.exceptions.MyException;
import com.app.exceptions.ProducerNotFoundException;
import com.app.model.Producer;
import com.app.repository.ProducerRepository;
import com.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProducerDto getProducer(Long id) {
        return producerRepository.findById(id).map(modelMapper::fromProducerToProducerDto).orElseThrow(() -> new ProducerNotFoundException("Producer with id = [" + id + "] doesn't exist"));
    }

    public void addOrUpdate(ProducerDto producerDto) {
        Producer producer = modelMapper.fromProducerDtoToProducer(producerDto);
        producerRepository.save(producer);
    }

    public List<ProducerDto> getAllProducers() {
        return producerRepository.findAll()
                .stream()
                .map(p -> modelMapper.fromProducerToProducerDto(p))
                .collect(Collectors.toList());
    }

    public void deleteProducer(Long id) {
        try {

            Producer producer = producerRepository.findById(id).orElseThrow(NullPointerException::new);
            productRepository.deleteAll(producer.getProducts());
            producerRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("SERVICE DELETE PRODUCER", LocalDateTime.now());
        }
    }
}
