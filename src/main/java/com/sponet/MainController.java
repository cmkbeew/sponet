package com.sponet;

import com.sponet.user.UserEntity;
import com.sponet.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String root(Model model, Authentication auth) {
        if(auth != null) {
            UserEntity loginUser = userService.getLoginUserByLoginId(auth.getName());
            if(loginUser != null){
                model.addAttribute("loginUser", loginUser); // UserEntity 전체를 넘겨줘서 원하는 값을 index에서 사용 가능
            }
        }

        return "index";
    }

    @GetMapping("/player")
    public String playerSearch() {
        return "player/search";
    }
}
