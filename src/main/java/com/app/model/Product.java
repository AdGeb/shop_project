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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "producer_id")
    private Producer producer;
    private String photoName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(releaseDate, product.releaseDate) &&
                category == product.category &&
                Objects.equals(services, product.services) &&
                Objects.equals(photoName, product.photoName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, price, releaseDate, category, services, photoName);
    }
}
