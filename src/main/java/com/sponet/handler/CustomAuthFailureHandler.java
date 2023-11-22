package com.sponet.handler;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof BadCredentialsException) { // 비밀번호 일치 오류
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (exception instanceof UsernameNotFoundException) { // 존재하지 않는 아이디
            errorMessage = "존재하지 않은 아이디입니다. 아이디를 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) { // 존재하지 않는 아이디
            errorMessage = "존재하지 않은 아이디입니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) { // 인증 요구 거부
            errorMessage = "인층 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage = "로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }
        // LockedException, DisabledException, AccountExpiredException, CredentialExpiredException 도 있지만 PrincipalDetails 에서 true 세팅함.

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); // 한글 깨짐 방지
        setDefaultFailureUrl("/user/login?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
