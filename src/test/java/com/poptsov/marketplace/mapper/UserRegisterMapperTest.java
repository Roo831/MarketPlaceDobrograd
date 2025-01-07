package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterMapperTest {

    @InjectMocks
    private UserRegisterMapper userRegisterMapper;

    private User user;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String encodedPassword;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = MockEntityUtil.getTestUser();
        encodedPassword = passwordEncoder.encode(user.getPassword());
    }

    @Test
    void map() {
        RegisterDto registerDto = RegisterDto.builder()
                .steamId(user.getSteamId())
                .username(user.getUsername())
                .build();

        User actualResult = userRegisterMapper.map(registerDto);

        assertNotNull(actualResult);
        assertEquals(user.getUsername(), actualResult.getUsername());
        assertEquals("John", actualResult.getFirstname());
        assertEquals("Doe", actualResult.getLastname());
        assertEquals(encodedPassword, actualResult.getPassword());
        assertEquals(user.getRole(), actualResult.getRole());
        assertEquals(user.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(user.getIsBanned(), actualResult.getIsBanned());
    }
}