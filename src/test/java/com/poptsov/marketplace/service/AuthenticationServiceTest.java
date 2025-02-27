package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.mapper.JwtAuthenticationDtoMapper;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import com.poptsov.marketplace.security.JwtService;
import com.poptsov.marketplace.util.MockEntityUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserRegisterMapper userRegisterMapper;
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtAuthenticationDtoMapper authenticationDtoMapper;

    private User user;

    private final String userToken = "UserToken";

    private final String code = "123456";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = MockEntityUtil.getTestUser();
    }

//    @Test
//    void createTest() {
//        // Arrange
//        RegisterDto registerDto = RegisterDto.builder()
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .password(user.getPassword())
//                .build();
//        when(userRegisterMapper.map(registerDto)).thenReturn(user);
//        when(userService.create(user)).thenReturn(user);
//
//        mockStatic(VerificationCodeGenerator.class);
//        String mockedCode = code; // Предполагаемый код подтверждения
//        when(VerificationCodeGenerator.generateCode()).thenReturn(mockedCode);
//
//        // Actual
//        OverheadMessageDto result = authenticationService.create(registerDto);
//
//        // Assert
//        assertEquals("Код был отправлен на вашу почту", result.getMessage());
//        assertTrue(authenticationService.getVerificationCodes().containsKey(user.getEmail()));
//        assertEquals(mockedCode, authenticationService.getVerificationCodes().get(user.getEmail()));
//        verify(emailService).sendVerificationCode(user.getEmail(), authenticationService.getVerificationCodes().get(user.getEmail()));
//    }
//
//    @Test
//    void verifyCodeTest() {
//
//        VerificationCodeDto verificationCodeDto = VerificationCodeDto.builder()
//                .email(user.getEmail())
//                .code(code)
//                .build();
//
//        JwtAuthenticationDto jwtAuthenticationDto = JwtAuthenticationDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .steamId(user.getSteamId())
//                .role(user.getRole())
//                .isAdmin(user.getIsAdmin())
//                .isBanned(user.getIsBanned())
//                .createdAt(user.getCreatedAt())
//                .token(userToken)
//                .build();
//
//        // Actual
//        authenticationService.getVerificationCodes().put(user.getEmail(), code);
//
//        // Assert
//        when(userService.findByEmail(user.getEmail())).thenReturn(user);
//        when(jwtService.generateToken(user)).thenReturn(userToken);
//        when(authenticationDtoMapper.map(user, userToken)).thenReturn(jwtAuthenticationDto);
//        JwtAuthenticationDto expectedResult = JwtAuthenticationDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .steamId(user.getSteamId())
//                .role(user.getRole())
//                .isAdmin(user.getIsAdmin())
//                .isBanned(user.getIsBanned())
//                .createdAt(user.getCreatedAt())
//                .token(userToken)
//                .build();
//
//        JwtAuthenticationDto actualResult = authenticationService.verifyCode(verificationCodeDto);
//
//        assertEquals(actualResult, expectedResult);
//        verify(userService).findByEmail(user.getEmail());
//        verify(jwtService).generateToken(user);
//
//    }

//    @Test
//    public void testSignIn_Success() {
//        // Arrange
//        LoginDto loginDto = LoginDto.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();
//
//        JwtAuthenticationDto exceptedResult = JwtAuthenticationDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .steamId(user.getSteamId())
//                .role(user.getRole())
//                .isAdmin(user.getIsAdmin())
//                .isBanned(user.getIsBanned())
//                .createdAt(user.getCreatedAt())
//                .token(userToken)
//                .build();
//
//        when(authenticationDtoMapper.map(user, userToken)).thenReturn(exceptedResult);
//        when(userService.findByUsername(loginDto.getUsername())).thenReturn(user);
//        when(jwtService.generateToken(user)).thenReturn(userToken);
//
//        // Act
//        JwtAuthenticationDto response = authenticationService.signIn(loginDto);
//
//        // Assert
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        assertNotNull(response);
//        assertEquals(userToken, response.getToken());
//
//    }

//    @Test
//    public void testSignIn_UserNotVerified() {
//        // Arrange
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUsername("testUser ");
//        loginDto.setPassword("testPassword");
//
//        user.setIsVerified(false); // Устанавливаем, что пользователь не верифицирован
//        when(userService.findByUsername("testUser ")).thenReturn(user);
//
//        // Act & Assert
//        AuthorizationException exception = assertThrows(AuthorizationException.class, () -> {
//            authenticationService.signIn(loginDto);
//        });
//        assertEquals("Пользователь не верифицирован.", exception.getMessage());
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//    }
}
