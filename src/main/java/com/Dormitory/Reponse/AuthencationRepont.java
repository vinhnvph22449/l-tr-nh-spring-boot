package com.Dormitory.Reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthencationRepont {

    // trả về cho user
    String token;

    boolean authecatidated;
}
