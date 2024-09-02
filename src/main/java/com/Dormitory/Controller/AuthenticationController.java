package com.Dormitory.Controller;


import com.Dormitory.Dto.ApiResponse;
import com.Dormitory.Dto.AuthenticationRequest;
import com.Dormitory.Dto.IntrospectRequest;
import com.Dormitory.Reponse.AuthencationRepont;
import com.Dormitory.Reponse.IntrospectReponse;
import com.Dormitory.Service.AuthencationService;
//import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    AuthencationService authencationService;

    @PostMapping("/token")
    ApiResponse<AuthencationRepont> anthencatice(@RequestBody  AuthenticationRequest request) {
        var resultt =  authencationService.authencite(request);
       return ApiResponse.<AuthencationRepont>builder().result(resultt).build();
    }

    @PostMapping("/instropect")
    ApiResponse<IntrospectReponse> anthencatice(@RequestBody IntrospectRequest request) throws ParseException, JOSEException, JOSEException {
        var resultt =  authencationService.introspect(request);
        return ApiResponse.<IntrospectReponse>builder().result(resultt).build();
    }


}
