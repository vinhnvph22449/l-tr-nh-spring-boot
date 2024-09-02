package com.Dormitory.Service;

import com.Dormitory.Dto.PermissionRequesst;
import com.Dormitory.Entity.Permission;
import com.Dormitory.Reponse.PermissionReponse;
import com.Dormitory.Repository.PermissionRepository;
import com.Dormitory.mapper.PermissionMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

   public PermissionReponse create(PermissionRequesst request){
        Permission permission = permissionMapper.toPermission(request);
        permission =  permissionRepository.save(permission);
        return permissionMapper.toPermissionReponse(permission);
    }


     public List<PermissionReponse> getall() {
        var permisstion  =  permissionRepository.findAll();
      return   permisstion.stream().map(permissionMapper::toPermissionReponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
