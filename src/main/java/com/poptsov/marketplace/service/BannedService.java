package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Banned;
import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.BannedRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.BanCreateDto;
import com.poptsov.marketplace.dto.BanReadDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.mapper.BannedReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannedService {

    private final BannedReadMapper bannedReadMapper;
    private final BannedRepository bannedRepository;
    private final UserRepository userRepository;

    @Transactional
    public BanReadDto createBanned(Integer userId, BanCreateDto banCreateDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityGetException("User not found"));
        user.setIsBanned(true);
        user.setIsAdmin(false);
        user.setRole(Role.banned);
        userRepository.save(user);

        Banned banned = bannedRepository.save(Banned.builder()
                .descriptionOfBan(banCreateDto.getDescriptionOfBan())
                .banDate(new Date())
                .build());

        return bannedReadMapper.map(banned);
    }

    public UserReadDto deleteBanned(Integer id) {
        return null;
    }

    public List<BanReadDto> getAllBanned() {
        return null;
    }

    public BanReadDto getBannedById(Integer id) {
        return null;
    }
}
