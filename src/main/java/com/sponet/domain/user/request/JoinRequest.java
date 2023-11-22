package com.sponet.domain.user.request;

import com.sponet.domain.type.UserRole;
import com.sponet.domain.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "아이디가 비어있습니다.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하로 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;

    private String passwordCheck;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;
    
    @NotBlank(message = "성별을 선택해주세요")
    private String gender;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "YYMMDD 형식에 맞추어 입력해주세요.")
    private String birth;

    @NotBlank(message = "급수를 선택해주세요.")
    private String grade;

    public UserEntity toEntity(String encodedPassword) {
        return UserEntity.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .userName(this.userName)
                .gender(this.gender)
                .birth(this.birth)
                .grade(this.grade)
                .role(UserRole.USER)
                .build();
    }
}
