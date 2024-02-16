/*
 * TariffTaxProxy.java Created on Apr 26, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.tariff.tax.vo.TaxFilterVO;
import com.ibsplc.icargo.business.tariff.tax.vo.TaxVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2147
 *
 */
@Module("tariff")
@SubModule("tax")
public class TariffTaxProxy extends ProductProxy{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	
	/**
	 * @author A-2147
	 * @param taxFilterMap
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public HashMap<String ,HashMap<String ,Collection<TaxVO>>> 
				computeTax( HashMap<String,TaxFilterVO> taxFilterMap) 
				throws SystemException, ProxyException {
		log.entering("TariffTaxProxy","computeTax");
		return despatchRequest("computeTax",taxFilterMap);
		
	}
}
