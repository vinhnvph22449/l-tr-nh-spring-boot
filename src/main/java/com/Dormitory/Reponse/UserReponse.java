package com.Dormitory.Reponse;

import com.Dormitory.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReponse {

    private String id;
    private String username;
    private String password;
    private String firtName;
    private String lastName;
    private LocalDate dob;
    Set<Role> roles;

}
