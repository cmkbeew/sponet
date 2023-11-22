package com.sponet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sponet.domain.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByUserName(String userName);

    UserEntity findByLoginId(String loginId);
}
