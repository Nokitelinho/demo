/*
 * TxProbeTenantConfigurator.java Created on 26/11/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe;

import com.ibsplc.neoicargo.framework.core.context.tenant.beans.OrderedPostAutoConfiguration;
import com.ibsplc.neoicargo.framework.core.context.tenant.beans.TenantConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * @author jens
 */
@Import({TxProbeConfigurator.class})
@ConditionalOnProperty(name = "neo.txprobe.enabled")
public class TxProbeTenantConfigurator implements OrderedPostAutoConfiguration, TenantConfiguration {


}
