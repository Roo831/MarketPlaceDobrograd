package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserReadMapperTest {

    @InjectMocks
    private UserReadMapper userReadMapper;

    private User user;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = MockEntityUtil.getTestUser();
    }

    @Test
    void map() {

        UserReadDto result = userReadMapper.map(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getSteamId(), result.getSteamId());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getIsAdmin(), result.getIsAdmin());
        assertEquals(user.getIsBanned(), result.getIsBanned());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());

    }
}