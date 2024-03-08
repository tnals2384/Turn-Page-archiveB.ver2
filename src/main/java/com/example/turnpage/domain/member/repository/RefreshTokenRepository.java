package com.example.turnpage.domain.member.repository;

import com.example.turnpage.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}
