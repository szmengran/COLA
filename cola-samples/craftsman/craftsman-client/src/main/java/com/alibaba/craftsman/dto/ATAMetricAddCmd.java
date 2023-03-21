package com.alibaba.craftsman.dto;

import com.alibaba.craftsman.dto.clientobject.ATAMetricCO;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * ATAMetricAddCmd
 *
 * @author Frank Zhang
 * @date 2019-03-01 10:12 AM
 */
@Data
public class ATAMetricAddCmd extends CommonCommand{
    @NotNull
    private ATAMetricCO ataMetricCO;
}
