package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "producers")
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String country;
    private String email;
    private String photoName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producer", fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(country, producer.country) &&
                Objects.equals(email, producer.email) &&
                Objects.equals(photoName, producer.photoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, email, photoName);
    }
}
