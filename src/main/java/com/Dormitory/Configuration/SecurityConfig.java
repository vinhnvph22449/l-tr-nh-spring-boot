package com.Dormitory.Configuration;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

private final String[] PUBLIC_ENDPOINTS={"/user","/auth/token","/auth/instropect"};

    @Value("${security.jwt.secret-key}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // cấu hình security
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
//                .permitAll().requestMatchers(HttpMethod.GET, "/user").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
        );

        //Khi bạn muốn bảo vệ các API và chỉ cho phép những yêu cầu có token JWT hợp lệ được truy cập.
        // Cấu hình oauth2ResourceServer giúp biến ứng dụng thành một OAuth2 Resource Server, nơi mà các endpoint được bảo vệ bằng cách yêu cầu một token hợp lệ.
        // Thường token này là JWT (JSON Web Token).
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter())).authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
      @Bean
      JwtDecoder jwtDecoder() {
        //Tạo khóa bí mật từ dữ liệu byte có sẵn, phục vụ cho các thuật toán mã hóa/giải mã như AES, DES.
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),"HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
        //  NimbusJwtDecoder.withSecretKey(secretKeySpec): Sử dụng khóa bí mật để giải mã JWT.
        //     macAlgorithm(MacAlgorithm.HS512): Xác định thuật toán HMAC SHA-512 (HS512) được sử dụng cho việc mã hóa và giải mã.
          //      build(): Tạo và trả về một đối tượng JwtDecoder, được sử dụng để giải mã và xác thực JWT trong ứng dụng.
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
