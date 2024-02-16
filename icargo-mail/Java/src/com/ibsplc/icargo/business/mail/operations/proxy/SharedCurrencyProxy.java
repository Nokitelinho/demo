/*
 * SharedCurrencyProxy.java Created on OCT 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.proxy;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-3227  RENO K ABRAHAM
 */
@Module("shared")
@SubModule("currency")
public class SharedCurrencyProxy extends ProductProxy{

	/**
	 * @author A-3227  RENO K ABRAHAM on 01-OCT-08
	 * @param currencyConvertorVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Double findConversionRate( CurrencyConvertorVO currencyConvertorVO )
	throws SystemException , ProxyException{		
		return despatchRequest("findConversionRate",currencyConvertorVO);				
	}

}
