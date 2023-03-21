package com.szmengran.cola.extension.test.customer.client;


import com.szmengran.cola.dto.Command;
import com.szmengran.cola.extension.BizScenario;
import lombok.Data;

/**
 * AddCustomerCmd
 *
 * @author Frank Zhang 2018-01-06 7:28 PM
 */
@Data
public class AddCustomerCmd extends Command {

    private CustomerDTO customerDTO;

    private String biz;

    private BizScenario bizScenario;
}
