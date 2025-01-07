package com.poptsov.marketplace.integration.service;

import com.poptsov.marketplace.annotations.IT;
import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.UserEditDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.exceptions.EntityAlreadyExistsException;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.mapper.UserReadMapper;
import com.poptsov.marketplace.service.UserService;
import com.poptsov.marketplace.util.MockEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@IT
@Slf4j
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserService userService;

    private User userToCreate;

    private final UserReadMapper userReadMapper;


    @BeforeEach
    public void setUp() {

            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("Roo"); // текущий пользователь
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

    }


//    @Test
//    @Sql(scripts = "classpath:db/changelog/db.changelog-2.0.1.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    void findById_success() {
//        UserReadDto actualResult = userService.findById(1);
//        UserReadDto exceptedUser = userReadMapper.map(MockEntityUtil.getTestUserIT());
//
//        assertEquals(exceptedUser.getFirstname(), actualResult.getFirstname());
//        assertEquals(exceptedUser.getLastname(), actualResult.getLastname());
//        assertEquals(exceptedUser.getEmail(), actualResult.getEmail());
//        assertEquals(exceptedUser.getIsAdmin(), actualResult.getIsAdmin());
//    }

//    @Test
//    void findById_fail() {
//        assertThrows(EntityGetException.class, () -> userService.findById(999999));
//    }

//    @Test
//    void findAll_success() {
//
//        List<UserReadDto> actualResult = userService.findAll();
//        for(UserReadDto userReadDto : actualResult) {
//            log.info(userReadDto.toString());
//        }
//        assertEquals(1, actualResult.size());
//    }

//    @Test
//    void create_success() {
//        userToCreate = MockEntityUtil.getTestUserIT();
//        userToCreate.setUsername("New Test User Roo831");
//        userToCreate.setEmail("New Test Email Of User Roo831");
//       User actualResult = userService.create(userToCreate);
//        assertEquals(userToCreate.getFirstname(), actualResult.getFirstname());
//    }

//    @Test
//    void create_fail_usernameExists() {
//        userToCreate = MockEntityUtil.getTestUserIT();
//        userToCreate.setId(2);
//      assertThrows(EntityAlreadyExistsException.class, () -> userService.create(userToCreate));
//    }

//    @Test
//    void create_fail_emailExists() {
//        userToCreate = MockEntityUtil.getTestUserIT();
//        userToCreate.setId(2);
//        assertThrows(EntityAlreadyExistsException.class, () -> userService.create(userToCreate));
//    }

//    @Test
//    void update_success() {
//        String updatedFirstname = "updatedFirstname";
//        String updatedLastname = "updatedLastname";
//        UserEditDto userEditDto = UserEditDto.builder()
//                .firstname(updatedFirstname)
//                .lastname(updatedLastname)
//                .build();
//
//        UserReadDto actualResult = userService.update(userEditDto);
//
//        assertEquals(updatedFirstname, actualResult.getFirstname());
//    }

//    @Test
//    void findByEmail_success() {
//        User exceptedResult = MockEntityUtil.getTestUserIT();
//        String emailToFind = exceptedResult.getEmail();
//        User actualResult = userService.findByEmail(emailToFind);
//        assertEquals(exceptedResult.getFirstname(), actualResult.getFirstname());
//
//    }

}
