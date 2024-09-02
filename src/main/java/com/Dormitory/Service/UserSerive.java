package com.Dormitory.Service;

import com.Dormitory.Dto.UsercareteRequest;
import com.Dormitory.Entity.User;
import com.Dormitory.Enums.Role;
import com.Dormitory.Exception.AppException;
import com.Dormitory.Exception.ErrorCode;
import com.Dormitory.Reponse.UserReponse;
import com.Dormitory.Repository.UserRepository;
import com.Dormitory.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserSerive {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // post
    public UserReponse createRequest(UsercareteRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
            User user = mapper.toUser(request);
            System.out.println(user);
        // đô mạnh của mã hóa là 10
            user.setPassword(passwordEncoder.encode(request.getPassword()));

          HashSet<String> roles = new HashSet<>();
           roles.add(Role.USER.name());
        //    user.setRoles(roles);
        return  mapper.toUserResponse(userRepository.save(user));
    }


    //get
   @PreAuthorize("hasRole('ADMIN')")
    public List<UserReponse> getuser() {
        log.info("In Method get User");
        return userRepository.findAll().stream().map(user -> mapper.toUserResponse(user)).toList();
    }

    // get user theo id
     @PostAuthorize("retrunObject.username == authentication.name")
    public UserReponse getuserid(String id) {
        log.info("In Method get User");
        return  mapper.toUserResponse(userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_Not_EXISTED)));
    }


    public UserReponse getmyInFo() {
      var context= SecurityContextHolder.getContext();
      String name= context.getAuthentication().getName();
      User user =  userRepository.findByUsername(name).orElseThrow(() ->new AppException(ErrorCode.USER_Not_EXISTED));
      return mapper.toUserResponse(user);
    }

    // delete
    public Boolean delete(String id) {
       if(userRepository.existsById(id)){
           userRepository.deleteById(id);
           return true;
       }else {
           return false;
       }
    }


}
