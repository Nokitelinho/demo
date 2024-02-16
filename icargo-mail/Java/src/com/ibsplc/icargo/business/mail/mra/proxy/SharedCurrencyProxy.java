/*
 * SharedCurrencyProxy.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.business.shared.currency.vo.ExchangeRateFilterVO;
import com.ibsplc.icargo.business.shared.currency.vo.ExchangeRateParameterMasterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-2408
 *
 */
@Module("shared")
@SubModule("currency")
public class SharedCurrencyProxy extends ProductProxy{


		
	/**
	 * @param currencyConvertorVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Double findConversionRate( CurrencyConvertorVO currencyConvertorVO )
	throws SystemException , ProxyException{
		
		return despatchRequest("findConversionRate",currencyConvertorVO);				
	}
/**
 * 
 * @param companyCode
 * @param baseCurrency
 * @return
 * @throws SystemException 
 * @throws ProxyException 
 */
	public CurrencyValidationVO validateCurrency(String companyCode, String baseCurrency) 
			throws ProxyException, SystemException {
		return despatchRequest("validateCurrency",companyCode,baseCurrency);
	}
	
	
    /**
     * 
     * @param currencyConvertorVOs
     * @return Collection<CurrencyConvertorVO>
     * @throws ProxyException
     * @throws SystemException
     */
 	public Collection<CurrencyConvertorVO> convertCurrency(
 			Collection<CurrencyConvertorVO> currencyConvertorVOs)throws ProxyException, SystemException {
 		
 		return  despatchRequest("convertCurrency",currencyConvertorVOs);		

 	}
	/**
	 * 
	 * @param companyCode
	 * @param baseCurrency
	 * @return
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
		public Collection<ExchangeRateParameterMasterVO> findExchangeRateParameters(ExchangeRateFilterVO exchangeRateFilterVO) 
				throws ProxyException, SystemException {
			return despatchRequest("findExchangeRateParameters",exchangeRateFilterVO);
	}
	

}



