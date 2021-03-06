package com.app.dto;

import com.app.model.Category;
import com.app.model.Service;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Category category;
    private Set<Service> services;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate release;
    private ProducerDto producerDto;
    private String photoName;
    private MultipartFile multipartFile;
}
