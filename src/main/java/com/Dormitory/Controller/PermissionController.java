package com.Dormitory.Controller;


import com.Dormitory.Dto.ApiResponse;
import com.Dormitory.Dto.PermissionRequesst;
import com.Dormitory.Reponse.PermissionReponse;
import com.Dormitory.Service.PermissionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@RequestMapping("/Permissinon")

public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionReponse> create(@RequestBody PermissionRequesst permissionRequesst) {
        return ApiResponse.<PermissionReponse>builder()
                .result(permissionService.create(permissionRequesst))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionReponse>> getall(){
        return ApiResponse.<List<PermissionReponse>>builder()
                .result(permissionService.getall())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}
