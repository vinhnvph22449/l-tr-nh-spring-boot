package com.Dormitory.Dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsercareteRequest {

    @Size(min = 3, message  = "USERNAME_INVALID")
    private String username;
    @Size(min = 8 , message = "PASSWORD_INVALID")
    private String password;
    private String firtName;
    private String lastName;
    private LocalDate dob;

}
