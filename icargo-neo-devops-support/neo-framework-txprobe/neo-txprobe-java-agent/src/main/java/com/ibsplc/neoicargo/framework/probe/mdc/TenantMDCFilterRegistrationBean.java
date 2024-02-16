/*
 * TenantMDCFilterRegistrationBean.java Created on 31/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.mdc;

import com.ibsplc.neoicargo.framework.core.context.tenant.TenantContext;
import com.ibsplc.neoicargo.framework.core.context.tenant.servlet.TenantFilterRegistrationBean;

import java.util.Collections;

import static java.lang.String.format;

/**
 * @author jens
 */
public class TenantMDCFilterRegistrationBean extends TenantFilterRegistrationBean<TxProbeMDCCorrelationFilter> {

    /**
     *
     * @param tenant
     */
    public TenantMDCFilterRegistrationBean(String tenant) {
        super(tenant);
        String urlPattern = format("/%s/*", tenant);
        this.setUrlPatterns(Collections.singletonList(urlPattern));
    }

    @Override
    public String filterName(String tenant) {
        return format("txProbeMDCCorrelationFilter-%s", tenant);
    }

    @Override
    public TxProbeMDCCorrelationFilter filter(TenantContext tenantContext) {
        return new TxProbeMDCCorrelationFilter();
    }
}
