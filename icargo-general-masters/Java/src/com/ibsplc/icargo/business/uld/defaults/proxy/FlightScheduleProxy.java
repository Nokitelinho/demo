/*
 * FlightScheduleProxy.java Created on Jun 03, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2883
 *
 */
@Module("flight")
@SubModule("schedule")
public class FlightScheduleProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("ULD DEFAULTS");
    

}
