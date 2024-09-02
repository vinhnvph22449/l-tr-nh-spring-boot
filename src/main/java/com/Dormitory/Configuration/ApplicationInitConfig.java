package com.Dormitory.Configuration;

import com.Dormitory.Dto.ApiResponse;
import com.Dormitory.Entity.User;
import com.Dormitory.Enums.Role;
import com.Dormitory.Reponse.UserReponse;
import com.Dormitory.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var role = new HashSet<String>();
                role.add(Role.ADMIN.name());
                User user = User.builder().username("admin").password(passwordEncoder.encode("admin"))
                      //  .roles(role)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been crated with default");
            }
        };
    }
}
