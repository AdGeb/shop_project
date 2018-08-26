package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDate releaseDate;
    private Category category;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "services",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "service")
    private Set<Service> services;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_id")
    private Producer producer;
    private String photoName;
}
