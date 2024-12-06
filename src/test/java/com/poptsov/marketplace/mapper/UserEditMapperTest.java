package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.UserEditDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserEditMapperTest {

    @InjectMocks
    private UserEditMapper userEditMapper;

    private User user;

    private Integer id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = MockEntityUtil.getTestUser();
        id = 1;
    }

    @Test
    void map() {

        UserEditDto userEditDto = UserEditDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();

        User result = userEditMapper.map(id, userEditDto);

        assertNotNull(result);
        assertEquals(userEditDto.getFirstname(), result.getFirstname());
        assertEquals(userEditDto.getLastname(), result.getLastname());
    }
}