package com.sponet.controller.competition;

import com.sponet.domain.user.UserEntity;
import com.sponet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/competition")
public class CompetitionController {

    private final UserService userService;

    @GetMapping
    public String competitionList(Model model, Authentication auth) {
        if(auth != null) {
            UserEntity loginUser = userService.getLoginUserByLoginId(auth.getName());
            if(loginUser != null){
                model.addAttribute("loginUser", loginUser); // UserEntity 전체를 넘겨줘서 원하는 값을 index에서 사용 가능
            }
        }

        return "/competition/competitionList";
    }
}
