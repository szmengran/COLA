package com.szmengran.cola.extension.test.customer.app;

import com.szmengran.cola.dto.Response;
import com.szmengran.cola.extension.test.customer.client.CustomerCreatedEvent;

/**
 * CustomerCreatedEventHandler
 *
 * @author Frank Zhang
 * @date 2020-06-22 7:00 PM
 */
public class CustomerCreatedEventHandler {

    public Response execute(CustomerCreatedEvent customerCreatedEvent) {
        System.out.println("customerCreatedEvent processed");
        return null;
    }
}
