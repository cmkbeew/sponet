package com.sponet.validator.login;

import com.sponet.domain.user.UserEntity;
import com.sponet.domain.user.request.LoginRequest;
import com.sponet.repository.UserRepository;
import com.sponet.validator.AbstractValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CheckPasswordValidator extends AbstractValidator<LoginRequest> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(LoginRequest dto, Errors errors) {
        UserEntity loginUser = userRepository.findByLoginId(dto.getLoginId());

        boolean matchPassword = passwordEncoder().matches(dto.getPassword(), loginUser.getPassword());

        if (!matchPassword) {
            errors.rejectValue("password", "비밀번호 틀림", "틀린 비밀번호 입니다.");
        }
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
