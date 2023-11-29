package com.sponet.competition;

import com.sponet.competition.request.CompetitionRequest;
import com.sponet.user.UserEntity;
import com.sponet.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/competition")
public class CompetitionController {

    private final UserService userService;
    private final CompetitionService competitionService;

    @GetMapping
    public String competitionList(Model model, Authentication auth) {
        if(auth != null) {
            UserEntity loginUser = userService.getLoginUserByLoginId(auth.getName());
            if(loginUser != null){
                model.addAttribute("loginUser", loginUser); // UserEntity 전체를 넘겨줘서 원하는 값을 index에서 사용 가능
            }
        }

        List<CompetitionEntity> competitionList = competitionService.competitionList();
        model.addAttribute("competitionList", competitionList);

        return "/competition/competitionList";
    }

    @GetMapping("saveCompetition")
    public String competitionForm(Model model, Authentication auth) {
        model.addAttribute("competitionRequest", new CompetitionRequest());

        return "/competition/competitionForm";
    }

    @PostMapping("saveCompetition")
    @ResponseBody
    public int saveCompetition(CompetitionRequest competitionRequest) {
        int check = competitionService.save(competitionRequest);

        return check;
    }
}
