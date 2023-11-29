package com.sponet.competition.request;

import com.sponet.competition.CompetitionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CompetitionRequest {

    @NotBlank(message = "대회명을 입력해주세요.")
    private String competitionName;

    // LocalDate 타입은 NotBlank 사용 불가능
    @NotNull(message = "대회 시작 일자를 선택해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "대회 종료 일자를 선택해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerEndDate;

    private String uploadFileName;

    private String storedFileName;

    private Long size;

    public CompetitionEntity toEntity() {
        return CompetitionEntity.builder()
                .competitionName(this.competitionName)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .registerStartDate(this.registerStartDate)
                .registerEndDate(this.registerEndDate)
                .uploadFileName(this.uploadFileName)
                .storedFileName(this.storedFileName)
                .size(this.size)
                .build();
    }
}
