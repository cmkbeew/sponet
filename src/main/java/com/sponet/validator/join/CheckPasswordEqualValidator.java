package com.sponet.validator.join;

import com.sponet.domain.user.request.JoinRequest;
import com.sponet.validator.AbstractValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckPasswordEqualValidator extends AbstractValidator<JoinRequest> {

    @Override
    protected void doValidate(JoinRequest dto, Errors errors) {
        if (!dto.getPassword().equals(null)) { // 정보 수정 시 파라미터를 updateRequest => joinRequest 바꿨을 경우
            if (!dto.getPassword().equals(dto.getPasswordCheck())) {
                errors.rejectValue("passwordCheck", "비밀번호 일치 오류", "비밀번호가 일치하지 않습니다.");
            }
        }
    }
}
