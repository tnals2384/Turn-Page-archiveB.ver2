package com.example.turnpage.domain.member.entity;

import com.example.turnpage.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE member_id = ?")
@SQLRestriction("deleted_at is NULL")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    // OAuth 적용 전까지 임시로 사용된다. 모든 계정의 비밀번호는 "password"로 설정.
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String image;

    @Column(name = "invite_code", nullable = false)
    private String inviteCode;

    @Column(nullable = false)
    private int point;

    @Builder
    public Member(String name, String email, String password, String role, String image, String inviteCode) {
        this.id = null;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.valueOf(role);
        this.image = image;
        this.inviteCode = inviteCode;
        this.point = 0;
    }

}
