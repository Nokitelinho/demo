/*
 * TenantMDCFilterRegistrar.java Created on 31/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.mdc;

import com.ibsplc.neoicargo.framework.core.context.tenant.servlet.TenantFilterRegistrar;

import static java.lang.String.format;

/**
 * @author jens
 */
public class TenantMDCFilterRegistrar extends TenantFilterRegistrar<TenantMDCFilterRegistrationBean> {


    @Override
    public Class<TenantMDCFilterRegistrationBean> filterRegistrationBeanClass(String s) {
        return TenantMDCFilterRegistrationBean.class;
    }

    @Override
    public String filterRegistrationBeanName(String tenant) {
        return format("txProbeMDCCorrelationFilterRegistration-%s", tenant);
    }
}
