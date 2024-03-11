package com.example.turnpage.domain.member.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class RefreshToken  implements Serializable {

    @Id
    @Indexed
    private String refreshToken;

    @Indexed
    private Long memberId;


    public RefreshToken(final Long memberId,final String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String refreshToken) {
        this.refreshToken = refreshToken;

        return this;
    }
}
