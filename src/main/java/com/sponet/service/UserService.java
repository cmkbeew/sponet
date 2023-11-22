package com.sponet.service;

import com.sponet.domain.user.UserEntity;
import com.sponet.domain.user.request.JoinRequest;
import com.sponet.domain.user.request.LoginRequest;
import com.sponet.domain.user.request.UpdatePasswordRequest;
import com.sponet.domain.user.request.UpdateRequest;
import com.sponet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 회원가입 시 로그인 아이디 중복확인
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());

            log.info("validatorResult: " + validatorResult.toString());
        }

        return validatorResult;
    }

    // 회원가입
    public void join(JoinRequest request) {
        userRepository.save(request.toEntity(passwordEncoder().encode(request.getPassword())));
    }

    // 로그인
    public UserEntity login(LoginRequest request) {
        UserEntity loginUser = userRepository.findByLoginId(request.getLoginId());

        if (loginUser.equals(null)) {
            return null;
        }

        if (loginUser.getPassword().equals(request.getPassword())) {
            return null;
        }

        return loginUser;
    }

    // 유저 정보 수정
    public Long updateInfo(Long id, UpdateRequest updateRequest) {
        // 회원 정보 찾아오기
        UserEntity loginUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id= " + id));

        loginUser.update(updateRequest.getBirth(), updateRequest.getGrade());

        userRepository.save(loginUser);

        return id;
    }

    // 비밀번호 변경
    public Long updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        // 회원 정보 찾아오기
        Long finalId = id;
        UserEntity loginUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id= " + finalId));

        //TODO: 에러 발생 상황에 따라 다른 처리 필요

        // 현재 비밀번호 일치 확인
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = loginUser.getPassword();

        // 암호화 되지않은 입력된 비밀번호와 암호화된 비밀번호를 비교하는 method
        boolean matchPassword = passwordEncoder().matches(oldPassword, newPassword);

        if(!matchPassword) {
            id = -1L;

            return id;
        }

        // 새 비밀번호와 새 비밀번호 확인 일치 확인
        if(!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getNewPasswordCheck())) {
            id = -1L;

            return id;
        }

        // 새 비밀번호 암호화 후 변경, 저장
        loginUser.updatePassword(passwordEncoder().encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(loginUser);

        return id;
    }

    // id를 입력받아 User 리턴
    public UserEntity getLoginUserById(Long id) {
        if(id == null) return null;


        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    // loginId를 입력받아 User 리턴
    public UserEntity getLoginUserByLoginId(String loginId) {
        if(loginId == null) return null;

        UserEntity loginUser = userRepository.findByLoginId(loginId);
        if(loginUser.equals(null)) return null;

        return loginUser;
    }
}
