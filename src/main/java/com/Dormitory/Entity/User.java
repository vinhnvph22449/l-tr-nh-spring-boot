package com.Dormitory.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "[user]")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private String id;
    private String username;
    private String password;
    private String firtName;
    private String lastName;
    private LocalDate dob;

    @ManyToMany
    Set<Role> roles;
}
