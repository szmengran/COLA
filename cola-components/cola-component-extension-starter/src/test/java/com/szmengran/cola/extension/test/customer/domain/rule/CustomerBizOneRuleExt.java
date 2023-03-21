package com.szmengran.cola.extension.test.customer.domain.rule;

import com.szmengran.cola.exception.BizException;
import com.szmengran.cola.extension.Extension;
import com.szmengran.cola.extension.test.customer.client.Constants;
import com.szmengran.cola.extension.test.customer.domain.CustomerEntity;
import com.szmengran.cola.extension.test.customer.domain.SourceType;

/**
 * CustomerBizOneRuleExt
 *
 * @author Frank Zhang
 * @date 2018-01-07 12:10 PM
 */
@Extension(bizId = Constants.BIZ_1)
public class CustomerBizOneRuleExt implements CustomerRuleExtPt{

    @Override
    public boolean addCustomerCheck(CustomerEntity customerEntity) {
        if(SourceType.AD == customerEntity.getSourceType()){
            throw new BizException("Sorry, Customer from advertisement can not be added in this period");
        }
        return true;
    }
}
