package com.ecommerce.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank @NotNull(message = "Username cannot be empty! ")
    String username;

    @NotBlank @NotNull(message = "Username cannot be empty! ")
    String fullName;

    @NotNull String password;

    @Email @NotNull @Size(min = 7, max = 40)
    @Column(unique = true)
    String email;

    boolean isAccountNonExpired;
    boolean isAccountNonLocked;
    boolean isCredentialsNonExpired;
    boolean isEnabled;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Role> authorities;

    @PrePersist // READ ABOUT
    private void prePersist() {
        isEnabled = true;
        isAccountNonExpired = true;
        isAccountNonLocked = true;
        isCredentialsNonExpired = true;
    }
}