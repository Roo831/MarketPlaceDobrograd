package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Banned;
import com.poptsov.marketplace.dto.BanReadDto;
import org.springframework.stereotype.Component;

@Component
public class BannedReadMapper implements Mapper<Banned, BanReadDto> {
    @Override
    public BanReadDto map(Banned object) {
        BanReadDto result = new BanReadDto();
        result.setId(object.getId());
        result.setBanDate(object.getBanDate());
        result.setDescriptionOfBan(object.getDescriptionOfBan());
        return result;
    }
}
