package com.alibaba.craftsman.command.query;

import com.szmengran.cola.dto.SingleResponse;
import com.alibaba.craftsman.dto.UserProfileGetQry;
import com.alibaba.craftsman.dto.clientobject.UserProfileCO;
import com.alibaba.craftsman.gatewayimpl.database.UserProfileMapper;
import com.alibaba.craftsman.gatewayimpl.database.dataobject.UserProfileDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class UserProfileGetQryExe {

    @Resource
    private UserProfileMapper userProfileMapper;

    public SingleResponse<UserProfileCO> execute(UserProfileGetQry qry) {
        UserProfileDO userProfileDO = userProfileMapper.getByUserId(qry.getUserId());
        UserProfileCO userProfileCO = new UserProfileCO();
        BeanUtils.copyProperties(userProfileDO, userProfileCO);
        return SingleResponse.of(userProfileCO);
    }

}
