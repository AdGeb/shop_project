package com.app.service;

import com.app.dto.ModelMapper;
import com.app.dto.ProducerDto;
import com.app.dto.ProductDto;
import com.app.exceptions.MyException;
import com.app.exceptions.ProductNotFoundException;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductService {

    @Autowired
    private EntityManager entityManager;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ProducerService producerService;
    public ProductService(final ProductRepository productRepository, final ModelMapper modelMapper, final ProducerService producerService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.producerService = producerService;
    }

    public ProductDto getProduct(final Long id) {
        return productRepository.findById(id).map(modelMapper::fromProductToProductDto).orElseThrow(() -> new ProductNotFoundException("Product with id = [" + id + "] doesn't exist"));
    }

    public void addOrUpdate(final ProductDto productDto) {
        Product product = modelMapper.fromProductDtoToProduct(productDto);
        Producer producer1 = entityManager.find(Producer.class, productDto.getProducerDto().getId());
        product.setProducer(producer1);
        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> modelMapper.fromProductToProductDto(p))
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("SERVICE DELETE PRODUCT", LocalDateTime.now());
        }
    }
}
