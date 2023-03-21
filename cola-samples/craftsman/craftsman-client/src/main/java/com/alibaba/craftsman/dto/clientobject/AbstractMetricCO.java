package com.alibaba.craftsman.dto.clientobject;

import com.szmengran.cola.dto.ClientObject;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

/**
 * AbstractMetricCO
 *
 * @author Frank Zhang
 * @date 2019-03-04 11:32 AM
 */
@Data
public abstract class AbstractMetricCO extends ClientObject{
    /**
     * The ownerId of this Metric Item
     */
    @NotEmpty
    private String ownerId;
}
