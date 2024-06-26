package com.alibaba.craftsman.dto;

import com.alibaba.craftsman.dto.clientobject.UserProfileCO;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UserProfileUpdateCmd extends CommonCommand {

    @NotNull
    private UserProfileCO userProfileCO;
}
