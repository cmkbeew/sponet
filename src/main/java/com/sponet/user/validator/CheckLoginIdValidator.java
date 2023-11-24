package com.sponet.user.validator;

import com.sponet.user.request.JoinRequest;
import com.sponet.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckLoginIdValidator extends AbstractValidator<JoinRequest> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(JoinRequest dto, Errors errors) {
        if (userRepository.existsByLoginId(dto.getLoginId())) {
            errors.rejectValue("loginId", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }
}
