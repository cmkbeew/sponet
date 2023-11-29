package com.sponet.competition;

import com.sponet.competition.request.CompetitionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public List<CompetitionEntity> competitionList() {
        List<CompetitionEntity> competitionList = competitionRepository.findAll();

        return competitionList;
    }

    // 대회 등록
    public int save(CompetitionRequest request) {
        int saveCheck = 0;
        String name = request.getCompetitionName();
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();
        LocalDate registerStart = request.getRegisterStartDate();
        LocalDate registerEnd = request.getRegisterEndDate();

        // 일정 선택 유효성 검사 TODO: 더 확실한 유효성 검사가 있을 것 같다.
        if (name != "") {
            if (start.compareTo(end) <= 0) { // start > end: 양수, start < end: 음수, start = end: 0
                if (registerStart.compareTo(registerEnd) <= 0) {
                    // 등록 성공
                    saveCheck = 1;

                    competitionRepository.save(request.toEntity());
                } else { // 접수 일정 오류
                    saveCheck = -3;
                }
            } else { // 대회 일정 오류
                saveCheck = -2;
            }
        } else { // 대회명 오류
            saveCheck = -1;
        }

        return saveCheck;
    }
}
