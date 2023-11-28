package com.sponet.user;

import com.sponet.user.request.JoinRequest;
import com.sponet.user.request.LoginRequest;
import com.sponet.user.request.UpdatePasswordRequest;
import com.sponet.user.request.UpdateRequest;
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
    public void updateInfo(Long id, UpdateRequest updateRequest) {
        // 회원 정보 찾아오기
        UserEntity loginUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id= " + id));

        loginUser.update(updateRequest.getBirth(), updateRequest.getGrade());

        String enteredBirth = updateRequest.getBirth();

        // 생년월일(6자리) 형식에 맞지 않으면 유저 정보 업데이트를 하지 않음. TODO: 체크 - 문자 6자리 입력해도 저장됨
        if (enteredBirth.length() == 6) {
            userRepository.save(loginUser);
        }
    }

    // 비밀번호 변경
    public int updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        // 회원 정보 찾아오기
        Long finalId = id;
        UserEntity loginUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id= " + finalId));

        // 현재 비밀번호 일치 확인
        String enteredPassword = updatePasswordRequest.getOldPassword(); // 입력한 비밀번호
        String oldPassword = loginUser.getPassword(); // 저장되어있는 비밀번호
        
        // matches ==> 암호화 되지 않은 입력된 비밀번호와 저장되어있는 암호화 비밀번호를 비교하는 method
        boolean matchPassword = passwordEncoder().matches(enteredPassword, oldPassword);


        // updatePasswordForm 에서 ajax 처리
        // 현재 비밀번호 불일치
        if(!matchPassword) {
            return -1;
        }

        // 새 비밀번호와 새 비밀번호 확인 불일치
        if(!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getNewPasswordCheck())) {
            return -2;
        }

        // 새 비밀번호 암호화 후 변경, 저장
        loginUser.updatePassword(passwordEncoder().encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(loginUser);

        return 1;
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
