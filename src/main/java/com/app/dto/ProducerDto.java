package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDto {
    private Long id;
    private String name;
    private String country;
    private String email;
    private String photoName;
    private MultipartFile multipartFile;
}
