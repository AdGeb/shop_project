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

    private String userName;
    private String password;
    private String passwordConfirmation;
    private Role role;
    private boolean enabled;
}
