package com.szmengran.cola.extension.test.customer.app.extensionpoint;

import com.szmengran.cola.extension.ExtensionPointI;
import com.szmengran.cola.extension.test.customer.client.AddCustomerCmd;
import com.szmengran.cola.extension.test.customer.domain.CustomerEntity;

/**
 * CustomerConvertorExtPt
 *
 * @author Frank Zhang
 * @date 2018-01-07 2:37 AM
 */
public interface CustomerConvertorExtPt extends ExtensionPointI {

    public CustomerEntity clientToEntity(AddCustomerCmd addCustomerCmd);
}
