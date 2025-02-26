package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Banned;
import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.BannedRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.BanCreateDto;
import com.poptsov.marketplace.dto.BanReadDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.exceptions.EntityNotFoundException;
import com.poptsov.marketplace.mapper.BannedReadMapper;
import com.poptsov.marketplace.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannedServiceImpl implements BannedService {

    private final BannedReadMapper bannedReadMapper;
    private final BannedRepository bannedRepository;
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;


    //CRUD start
    @Transactional
    public BanReadDto create(Integer userId, BanCreateDto banCreateDto) { // Admin

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setIsBanned(true);
        user.setIsAdmin(false);
        user.setRole(Role.banned);
        userRepository.save(user);

        Banned banned = bannedRepository.save(Banned.builder()
                .descriptionOfBan(banCreateDto.getDescriptionOfBan())
                .user(user)
                .banDate(new Date())
                .build());

        return bannedReadMapper.map(banned);
    }

    @Transactional
    public UserReadDto delete(Integer id) { // Admin
        Banned banned = bannedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Banned not found"));

        User user = banned.getUser();
        if (user != null) {
            user.setIsBanned(false);
            user.setIsAdmin(false);
            user.setRole(Role.user);
        } else {
            throw new EntityNotFoundException("User associated with banned not found");
        }

        bannedRepository.deleteBannedById(id);

        return userReadMapper.map(user);
    }

    public List<BanReadDto> findAll() { // Admin
        return bannedRepository.findAll().stream().map(bannedReadMapper::map).toList();
    }

    public BanReadDto findById(Integer id) { // Admin
        return bannedReadMapper.map(bannedRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Banned not found")));
    }
    // CRUD end
}
