package com.Dormitory.mapper;

import com.Dormitory.Dto.PermissionRequesst;
import com.Dormitory.Entity.Permission;
import com.Dormitory.Reponse.PermissionReponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequesst permissionRequesst);

    PermissionReponse toPermissionReponse(Permission permission);

}
