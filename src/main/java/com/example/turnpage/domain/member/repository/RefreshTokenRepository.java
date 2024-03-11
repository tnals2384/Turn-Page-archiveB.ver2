package com.example.turnpage.domain.member.repository;

import com.example.turnpage.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
