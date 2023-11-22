package com.sponet.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sponet.domain.type.UserRole;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(length = 5, unique = true)
    private String userName;

    @Column(length = 5)
    private String gender;

    @Column(length = 10)
    private String birth;

    @Column(length = 5)
    private String grade;

    private UserRole role;

    public void update(String birth, String grade) {
        this.birth = birth;
        this.grade = grade;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
