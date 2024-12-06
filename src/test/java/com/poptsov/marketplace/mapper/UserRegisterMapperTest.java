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
import static org.mockito.Mockito.when;

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
        user.setIsVerified(false);
        String decodedPassword = "Test User Password";
        RegisterDto registerDto = RegisterDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(decodedPassword)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();

        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn(encodedPassword);

        User actualResult = userRegisterMapper.map(registerDto);

        assertNotNull(actualResult);
        assertEquals(user.getUsername(), actualResult.getUsername());
        assertEquals(user.getFirstname(), actualResult.getFirstname());
        assertEquals(user.getLastname(), actualResult.getLastname());
        assertEquals(user.getEmail(), actualResult.getEmail());
        assertEquals(encodedPassword, actualResult.getPassword());
        assertEquals(user.getRole(), actualResult.getRole());
        assertEquals(user.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(user.getIsBanned(), actualResult.getIsBanned());
        assertEquals(user.getIsVerified(), actualResult.getIsVerified());
    }
}