package com.app.model.security;

import com.app.model.Role;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String email;
    private String password;
    @Transient
    private String passwordConfirmation;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabled;
}
