package com.app.dto;

import com.app.model.Producer;
import com.app.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ModelMapper {
    public ProducerDto fromProducerToProducerDto(Producer producer) {
        return producer == null ? null : ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .email(producer.getEmail())
                .country(producer.getCountry())
                .photoName(producer.getPhotoName())
                .build();
    }

    public Producer fromProducerDtoToProducer(ProducerDto producerDto) {
        return producerDto == null ? null : Producer.builder()
                .id(producerDto.getId())
                .name(producerDto.getName())
                .email(producerDto.getEmail())
                .country(producerDto.getCountry())
                .photoName(producerDto.getPhotoName())
                .products(new HashSet<>())
                .build();
    }

    public ProductDto fromProductToProductDto(Product product) {
        return product == null ? null : ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .release(product.getReleaseDate())
                .category(product.getCategory())
                .services(product.getServices())
                .photoName(product.getPhotoName())
                .producerDto(product.getProducer() == null ? null : fromProducerToProducerDto(product.getProducer()))
                .build();
    }

    public Product fromProductDtoToProduct(ProductDto productDto) {
        return productDto == null ? null : Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .category(productDto.getCategory())
                .services(productDto.getServices())
                .releaseDate(productDto.getRelease())
                .photoName(productDto.getPhotoName())
                .producer(productDto.getProducerDto() == null ? null : fromProducerDtoToProducer(productDto.getProducerDto()))
                .build();
    }
}