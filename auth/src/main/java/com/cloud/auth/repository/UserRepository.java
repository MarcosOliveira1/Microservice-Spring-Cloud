package com.cloud.auth.repository;

import com.cloud.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u where u.userName = userName")
    User findByUserName(@Param("userName") String userName);
}
