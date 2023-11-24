package com.sponet.competition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String competitionName;

    @Column(nullable = false)
    private Date competitionDate;

    @Column(nullable = false)
    private Date registerDate;

    private String uploadFileName;

    private String storedFileName;

    private Long size;

}
