package com.sponet.validator.login;

import com.sponet.domain.user.request.LoginRequest;
import com.sponet.repository.UserRepository;
import com.sponet.validator.AbstractValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckLoginValidator extends AbstractValidator<LoginRequest> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(LoginRequest dto, Errors errors) {
        if (!userRepository.existsByLoginId(dto.getLoginId())) {
            errors.rejectValue("loginId", "아이디 존재하지 않음", "존재하지 않은 아이디 입니다.");
        }
    }
}
