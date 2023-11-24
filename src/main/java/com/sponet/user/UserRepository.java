package com.sponet.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponet.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByUserName(String userName);

    UserEntity findByLoginId(String loginId);
}
