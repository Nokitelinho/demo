/*
 * CRAAirlineBillingProxy.java Created on Jul 30, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-2391
 *
 */
@Module("cra")
@SubModule("airlinebilling")
public class CRAAirlineBillingProxy extends ProductProxy{
	private static final String SERVICE_NAME = "CRA_AIRLINEBILLING";





	/**
	 * Added by Sandeep for defaulting the open clearance period as per AirNz req:
	 * @Author :- A-2270
	 * @param companyCode
	 * @param currentDate
	 * @return String
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public String findCurrentIchClearencePeriod(String companyCode,
			LocalDate currentDate) throws SystemException,ProxyException{

		return despatchRequest("findCurrentIchClearencePeriod",companyCode,currentDate);
	}



}



