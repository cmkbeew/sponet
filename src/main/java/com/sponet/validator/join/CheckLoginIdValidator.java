package com.sponet.validator.join;

import com.sponet.domain.user.request.JoinRequest;
import com.sponet.repository.UserRepository;
import com.sponet.validator.AbstractValidator;
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
