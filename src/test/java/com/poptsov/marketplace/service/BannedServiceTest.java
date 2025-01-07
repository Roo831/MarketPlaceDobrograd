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
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BannedServiceTest {

    @InjectMocks
    private BannedService bannedService;

    @Mock
    private BannedRepository bannedRepository;

    @Mock
    private BannedReadMapper bannedReadMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;


    private Banned banned;

    private User bannedUser;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        banned = MockEntityUtil.getTestBanned();
        bannedUser = MockEntityUtil.getTestUserBanned();
        banned.setUser(bannedUser);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(bannedUser.getUsername());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void create_get() {

        BanReadDto expectedResult = BanReadDto.builder()
                .id(banned.getId())
                .descriptionOfBan(banned.getDescriptionOfBan())
                .BanDate(banned.getBanDate())
                .build();

        BanCreateDto banCreateDto = BanCreateDto.builder()
                .descriptionOfBan(banned.getDescriptionOfBan())
                .build();


        when(userRepository.findById(bannedUser.getId())).thenReturn(Optional.ofNullable(bannedUser));
        when(userRepository.save(bannedUser)).thenReturn(bannedUser);
        when(bannedRepository.save(banned)).thenReturn(banned);
        when(bannedReadMapper.map(any())).thenReturn(expectedResult);

        BanReadDto actualResult = bannedService.create(bannedUser.getId(), banCreateDto);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getDescriptionOfBan(), actualResult.getDescriptionOfBan());
        assertEquals(expectedResult.getBanDate(), actualResult.getBanDate());


    }

    @Test
    void create_throw() {

        BanCreateDto banCreateDto = BanCreateDto.builder()
                .descriptionOfBan(banned.getDescriptionOfBan())
                .build();

        when(userRepository.findById(bannedUser.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> bannedService.create(bannedUser.getId(), banCreateDto));
    }

    @Test
    void delete_success() {
        when(bannedRepository.findById(banned.getId())).thenReturn(Optional.of(banned));

        bannedUser.setIsBanned(false);
        bannedUser.setIsAdmin(false);
        bannedUser.setRole(Role.user);

        UserReadDto expectedResult = UserReadDto.builder()
                .id(bannedUser.getId())
                .username(bannedUser.getUsername())
                .firstname(bannedUser.getFirstname())
                .lastname(bannedUser.getLastname())
                .steamId(bannedUser.getSteamId())
                .role(bannedUser.getRole())
                .isAdmin(bannedUser.getIsAdmin())
                .isBanned(bannedUser.getIsBanned())
                .createdAt(bannedUser.getCreatedAt())
                .build();

        when(userReadMapper.map(bannedUser)).thenReturn(expectedResult);

        UserReadDto actualResult = bannedService.delete(banned.getId());

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getUsername(), actualResult.getUsername());
        assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        assertEquals(expectedResult.getFirstname(), actualResult.getFirstname());
        assertEquals(expectedResult.getLastname(), actualResult.getLastname());
        assertEquals(expectedResult.getSteamId(), actualResult.getSteamId());
        assertEquals(expectedResult.getRole(), actualResult.getRole());
        assertEquals(expectedResult.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(expectedResult.getIsBanned(), actualResult.getIsBanned());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());

    }

    @Test
    void delete_throw() {
        when(bannedRepository.findById(banned.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> bannedService.delete(banned.getId()));

    }


    @Test
    void findAll_get() {


        Banned exceptedResult = Banned.builder()
                .id(banned.getId())
                .descriptionOfBan(banned.getDescriptionOfBan())
                .banDate(banned.getBanDate())
                .user(bannedUser)
                .build();

        List<Banned> exceptedResultList = List.of(exceptedResult);

        when(bannedRepository.findAll()).thenReturn(exceptedResultList);
        when(bannedReadMapper.map(any())).thenReturn(BanReadDto.builder()
                .id(banned.getId())
                .descriptionOfBan(banned.getDescriptionOfBan())
                .BanDate(banned.getBanDate())
                .build());

        List<BanReadDto> actualResultList = bannedService.findAll();
        BanReadDto actualResult = actualResultList.get(0);

        assertNotNull(actualResultList);
        assertEquals(1, actualResultList.size());
        assertEquals(exceptedResult.getId(), actualResult.getId());
        assertEquals(exceptedResult.getDescriptionOfBan(), actualResult.getDescriptionOfBan());
        assertEquals(exceptedResult.getBanDate(), actualResult.getBanDate());

    }

    @Test
    void findAll_getNull() {


        when(bannedRepository.findAll()).thenReturn(Collections.emptyList());

        List<BanReadDto> actualResultList = bannedService.findAll();

        assertNotNull(actualResultList);
        assertEquals(0, actualResultList.size());

    }

    @Test
    void findById_get() {

        when(bannedRepository.findById(banned.getId())).thenReturn(Optional.of(banned));
        BanReadDto exceptedResult = BanReadDto.builder()
                .id(banned.getId())
                .descriptionOfBan(banned.getDescriptionOfBan())
                .BanDate(banned.getBanDate())
                .build();
        when(bannedReadMapper.map(banned)).thenReturn(exceptedResult);

        BanReadDto actualResult = bannedService.findById(banned.getId());
        assertNotNull(actualResult);
        assertEquals(exceptedResult.getId(), actualResult.getId());
        assertEquals(exceptedResult.getDescriptionOfBan(), actualResult.getDescriptionOfBan());
        assertEquals(exceptedResult.getBanDate(), actualResult.getBanDate());


    }

    @Test
    void findById_throw() {

        when(bannedRepository.findById(banned.getId())).thenThrow(EntityNotFoundException.class);


        assertThrows(EntityNotFoundException.class, () -> bannedService.findById(banned.getId()));

    }
}