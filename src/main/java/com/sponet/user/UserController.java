package com.sponet.user;

import com.sponet.user.request.JoinRequest;
import com.sponet.user.request.LoginRequest;
import com.sponet.user.request.UpdatePasswordRequest;
import com.sponet.user.request.UpdateRequest;
import com.sponet.user.validator.CheckLoginIdValidator;
import com.sponet.user.validator.CheckPasswordEqualValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CheckLoginIdValidator checkLoginIdValidator;
    private final CheckPasswordEqualValidator checkPasswordEqualValidator;

    @InitBinder // @Valid 어노테이션으로 검증이 필요한 객체를 가져오기 전에 수행할 method를 지정해준다.
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkLoginIdValidator);
        binder.addValidators(checkPasswordEqualValidator);
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());

        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, Errors errors, Model model) throws Exception {
        if (errors.hasErrors()) {
            model.addAttribute("joinRequest", joinRequest);

            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 리턴
            return "user/joinForm";
        }
        userService.join(joinRequest);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("loginRequest", new LoginRequest());

        return "user/loginForm";
    }

    @GetMapping("info")
    public String userInfo(Model model, Authentication auth) {
        UserEntity user = userService.getLoginUserByLoginId(auth.getName());

        model.addAttribute("loginUser", user);

        model.addAttribute("updateRequest", new UpdateRequest());

        return "user/info";
    }

    @PostMapping("updateInfo")
    public String updateInfo(@ModelAttribute UpdateRequest updateRequest, Long id) {
        userService.updateInfo(id, updateRequest);

        return "redirect:/";
    }

    @GetMapping("updatePassword")
    public String udpatePasswordForm(Model model, Authentication auth) {
        UserEntity user = userService.getLoginUserByLoginId(auth.getName());

        model.addAttribute("loginUser", user);

        return "user/updatePasswordForm";
    }

    @PostMapping("updatePassword")
    @ResponseBody // 리턴 값을 html로 response 전송하기 위해서 자바 객체로 변환시켜줌 (@RestController와 같은 역할)
    public int updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        int check = userService.updatePassword(id, updatePasswordRequest);

        return check;
    }
}
