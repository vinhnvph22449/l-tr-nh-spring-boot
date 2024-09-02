package com.Dormitory.Service;

import com.Dormitory.Dto.AuthenticationRequest;
import com.Dormitory.Dto.IntrospectRequest;
import com.Dormitory.Entity.User;
import com.Dormitory.Exception.AppException;
import com.Dormitory.Exception.ErrorCode;
import com.Dormitory.Reponse.AuthencationRepont;
import com.Dormitory.Reponse.IntrospectReponse;
import com.Dormitory.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthencationService {

    @NonFinal
    @Value("${security.jwt.secret-key}")
    private String key;

    @Autowired
    UserRepository userRepository;



    // để no ko inrectvao constrctor
//    @NonFinal
//    protected static final String key= "cIulBG4XJ21QNzfOU5XmEFVGxeAbIIRWgkewRMpzUOLLg+6VERLRSn4ckIzBS10+";

    public AuthencationRepont authencite(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // để xác thucew user phải lấy user ra
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_Not_EXISTED));
        boolean  resul = passwordEncoder.matches(authenticationRequest.getPassword(),user.getPassword());
      // kiem tra xem đúng mật khẩu
        if(!resul) {
            throw new AppException(ErrorCode.UANTHENTICATED);
        }

        var token = generatetoken(user);

        return AuthencationRepont.builder()
                .token(token).authecatidated(true)
                .build();
    }



    public IntrospectReponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var token = introspectRequest.getToken();

        //JWSVerifier jwsVerifier = new MACVerifier(key.getBytes());: Tạo một JWSVerifier để kiểm tra chữ ký của token, sử dụng khóa bí mật.
        JWSVerifier jwsVerifier = new MACVerifier(key.getBytes());

        //Khi parse, nó tách và phân tích các phần này, cho phép bạn truy cập và xử lý các thông tin bên trong như claims (ví dụ: thời gian hết hạn, thông tin người dùng).
        SignedJWT signedJWT = SignedJWT.parse(token);

        // kiem tra xem token đã hết hạn hay chưa
        Date exptytime = signedJWT.getJWTClaimsSet().getExpirationTime();
        //   Phương thức verify kiểm tra xem chữ ký của token có khớp với signature được tạo bằng khóa bí mật hay không.

        var verified = signedJWT.verify(jwsVerifier);

        return IntrospectReponse.builder()
                .valid(verified && exptytime.after(new Date()))
                .build();
    }



    // tao token
    // co 3 buoc de tao token
    // 1 JWSHeader 2 payload 3 ki token de ko bi thay doi bao mat
     private String generatetoken(User user) {
         JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
         // ĐÊ BILL 1 PAYLOAD CAN KHAI NIEM LA CLIEN;
         // đây là body gửi đi cùng token
         JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                 // DAI DIEN CHO USER DANG NHAP
                 .subject(user.getUsername())
                 // token là issuer từ ai phat hanh tu ai
                 .issuer("Vinhdeptrai")
                 // thoi gian tao
                 .issueTime(new Date())
                 // token co thoi han (sau1 h)
                 .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                 .claim("scope",buildScope(user))
                 .build();
          // sau khi co JWTClaimsSet bây h tao payload cho token

         Payload payload = new Payload(jwtClaimsSet.toJSONObject());


        // ddeeer tao token nimbous se dung
         // insstal 2 pram header , payload
         JWSObject jwsObject = new JWSObject(header,payload);

         // de ki token ta dung
         // thuat toan MACSigner dùng để kí token
         try {
             jwsObject.sign(new MACSigner(key.getBytes()));
             return jwsObject.serialize();
         } catch (JOSEException e) {
             log.info("cannot create toke",e);
              throw new RuntimeException(e);
         }
     }


     private String buildScope(User user) {
         StringJoiner stringJoiner = new StringJoiner(" ");
//         if(!CollectionUtils.isEmpty(user.getRoles())){
//             user.getRoles().forEach(s-> stringJoiner.add(s));
//         }
         return stringJoiner.toString();
     }
}
