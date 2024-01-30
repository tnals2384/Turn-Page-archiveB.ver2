package com.archiveB.service;

import com.archiveB.domain.User;
import com.archiveB.repository.UserRepository;
import com.archiveB.web.dto.User.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(UserRequestDTO.AddUserRequest dto) {
        return userRepository.save(User.builder()
                    .email(dto.getEmail())
                    .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                    .build())
                .getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
