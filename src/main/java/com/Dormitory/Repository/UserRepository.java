package com.Dormitory.Repository;

import com.Dormitory.Entity.User;
import com.Dormitory.Reponse.UserReponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
