package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanReadDto implements ReadDto {

    @NotBlank(message = "Id of ban cannot be Blank")
    private Integer id;
    @NotBlank(message = "Description of ban cannot be Blank")
    private String descriptionOfBan;
    @NotBlank(message = "Ban Date cannot be Blank")
    private Date BanDate;
}
