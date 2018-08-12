package com.app.service;

import com.app.exceptions.ProducerNotFoundException;
import com.app.model.Producer;
import com.app.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    public Producer getProducer(Long id) {
        return producerRepository.findById(id).orElseThrow(() -> new ProducerNotFoundException("Producer with id = [" + id + "] doesn't exist"));
    }
}
