package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Getter
@Setter
@Transactional
public class AuthenticationService {

    private final UserRegisterMapper userRegisterMapper;
    private final UserRepository userRepository;


    public User findOrCreateUserBySteamId(String steamId, String username) {
        Optional<User> userToFind = userRepository.findUserBySteamId(steamId);

        if (userToFind.isPresent()) {
            return userToFind.get();
        }

        RegisterDto registerDto = RegisterDto.builder()
                .steamId(steamId)
                .username(username)
                .build();

        User newUser  = userRegisterMapper.map(registerDto);
        return userRepository.save(newUser);
    }
}