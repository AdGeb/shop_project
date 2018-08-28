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
@ToString
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "producers")
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String country;
    private String email;
    private String photoName;
    @OneToMany(mappedBy = "producer", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude @ToString.Exclude private Set<Product> products = new HashSet<>();
}
