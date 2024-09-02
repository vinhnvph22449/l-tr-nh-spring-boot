package com.Dormitory.Controller;

import com.Dormitory.Dto.ApiResponse;
import com.Dormitory.Dto.UsercareteRequest;
import com.Dormitory.Entity.User;
import com.Dormitory.Reponse.UserReponse;
import com.Dormitory.Service.UserSerive;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private UserSerive userSerive;

    // su ly null
    @GetMapping
    public ApiResponse<List<UserReponse>> getuser() {
       // dùng để lấy thông tin về người dùng hiện tại trong Spring Security.
        // SecurityContextHolder truy cập vào getContext đẻ lấy trhoong tin ng dùng
        //SecurityContext, chứa thông tin bảo mật của người dùng hiện tại.
        //getAuthentication đại diện cho thông tin xác thực của người dùng hiện tại.
       var aunthencation=  SecurityContextHolder.getContext().getAuthentication();
         log.info("Username: {}",aunthencation.getName());

        log.info("Number of authorities: {}", aunthencation.getAuthorities().size());

        aunthencation.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserReponse>>builder()
                .result(userSerive.getuser())
                .build();
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteuser(@PathVariable String id) {
        Boolean user = userSerive.delete(id);
        if(user) {
            return ResponseEntity.ok("Xoa Thanh Cong");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nguoi dung khong ton tai");
        }
    }

    @GetMapping("/{id}")
    public UserReponse getuserid(@PathVariable String id) {
        return userSerive.getuserid(id);
    }
    
    @GetMapping("/myinfo")
    public ApiResponse<UserReponse> getmyinfo() {
        return ApiResponse.<UserReponse>builder().result(userSerive.getmyInFo()).build();
    }

    @PostMapping
    public ApiResponse<UserReponse> postuser(@RequestBody @Valid UsercareteRequest request) {
          return ApiResponse.<UserReponse>builder()
                  .result(userSerive.createRequest(request))
                  .build();
    }
}
