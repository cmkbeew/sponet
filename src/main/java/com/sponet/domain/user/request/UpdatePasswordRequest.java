package com.sponet.domain.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequest {

    private String oldPassword;

    private String newPassword;

    private String newPasswordCheck;
}
