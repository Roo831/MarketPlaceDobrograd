package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Banned;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.BanReadDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BannedReadMapperTest {

    @InjectMocks
    private BannedReadMapper bannedReadMapper;

    private Banned banned;
    private User userBanned;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        banned = MockEntityUtil.getTestBanned();
        userBanned = MockEntityUtil.getTestUserBanned();
    }

    @Test
    void map() {

        BanReadDto result = bannedReadMapper.map(banned);

        assertNotNull(result);
        assertEquals(banned.getId(), result.getId());
        assertEquals(banned.getBanDate(), result.getBanDate());
        assertEquals(banned.getDescriptionOfBan(), result.getDescriptionOfBan());
        assertEquals(banned.getUser().getId(), userBanned.getId());

    }
}