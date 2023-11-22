package com.sponet.domain.user.request;

import com.sponet.domain.type.UserRole;
import com.sponet.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRequest {

    // 회원 정보 수정에서 사용
    private Long id;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "YYMMDD 형식에 맞추어 입력해주세요.")
    private String birth;

    private String grade;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .birth(this.birth)
                .grade(this.grade)
                .build();
    }
}
