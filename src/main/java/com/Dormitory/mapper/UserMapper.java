package com.Dormitory.mapper;

import com.Dormitory.Dto.PermissionRequesst;
import com.Dormitory.Dto.UsercareteRequest;
import com.Dormitory.Entity.Permission;
import com.Dormitory.Entity.User;
import com.Dormitory.Reponse.UserReponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UsercareteRequest request);

    UserReponse toUserResponse(User user);


}
