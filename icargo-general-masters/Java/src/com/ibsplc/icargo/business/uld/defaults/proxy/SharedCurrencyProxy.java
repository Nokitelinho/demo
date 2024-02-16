/*
 * SharedCurrencyProxy.java Created on Oct 17, 2005 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.currency.CurrencyBI;
import com.ibsplc.icargo.business.shared.currency.CurrencyBusinessException;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyRateVO;
import com.ibsplc.icargo.business.uld.defaults.CurrencyConversionException;

import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class represents the product proxy for shared currency subsystem
 *
 * @author A-1958
 *
 *
 **/



public class SharedCurrencyProxy extends SubSystemProxy {
   
	
	private Log log =LogFactory.getLogger("ULD_MANAGEMENT");
	private static final String INVALID_ROUNDINGUNIT = "shared.currency.invalidroundingunit";
	private static final String INVALID_ROUNDBASIS = "shared.currency.invalidroundbasis";
	private static final String CURCFGDFORSTN = "shared.currency.currencyconfiguredforstation";
	private static final String EXCHANGE_RATE_NOT_OBTIANED = "shared.currency.exgRateNotObtained";
	
  
  /**
   * This method converts the Currency to the required Airline specific currencyRates
   * @param companyCode
   * @param rateType
   * @param fromCurrencyCode
   * @param fromValue
   * @param airlineIdentifier
   * @return
   * @throws ProxyException
   * @throws SystemException
   */
  @Action("findCurrencyConvertedRates")
  public CurrencyRateVO findCurrencyConvertedRates(CurrencyConvertorVO currencyConvertorVO)
		throws SystemException,CurrencyConversionException {
	  	log.entering("INSIDE THE CURRENCYPROXY","findCurrencyConvertedRates");
	    CurrencyRateVO currencyRateVO = null;
		try {
			CurrencyBI currencyBI = (CurrencyBI)getService("SHARED_CURRENCY");
			currencyRateVO =  currencyBI.findCurrencyConvertedRates(currencyConvertorVO);
			
		}catch(com.ibsplc.icargo.business.shared.currency.CurrencyBusinessException e) {
			Collection<ErrorVO> errors = e.getErrors();
			for(ErrorVO errorVO:errors)
			{
				if(INVALID_ROUNDINGUNIT.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.INVALID_ROUNDINGUNIT);
				}
				else if(INVALID_ROUNDBASIS.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.INVALID_ROUNDBASIS);
				}
				else if(CURCFGDFORSTN.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.CURCFGDFORSTN);
				}
				else if(EXCHANGE_RATE_NOT_OBTIANED.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.EXCHANGE_RATE_NOT_OBTIANED);
				}
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
		return currencyRateVO;
  	}

  /**
   * This method gives conversionRates
   * @param currencyConvertorVO
   * @return
   * @throws CurrencyConversionException
   * @throws SystemException
   */
  @Action("findExchangeRate")
  public double findExchangeRate(CurrencyConvertorVO currencyConvertorVO)
  throws SystemException,CurrencyConversionException {
	  double conversionrate = 0;
	  Collection<ErrorVO> errors = null;
	 		
			try {
				CurrencyBI currencyBI = (CurrencyBI)getService("SHARED_CURRENCY");
				conversionrate=currencyBI.findExchangeRate(currencyConvertorVO);
			} catch (SystemException e) {
				e.getMessage();
			}catch(CurrencyBusinessException ex) {
			 errors = ex.getErrors();
			for(ErrorVO errorVO:errors)
			{
				if(INVALID_ROUNDINGUNIT.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.INVALID_ROUNDINGUNIT);
				}
				else if(INVALID_ROUNDBASIS.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.INVALID_ROUNDBASIS);
				}
				else if(CURCFGDFORSTN.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.CURCFGDFORSTN);
				}
				else if(EXCHANGE_RATE_NOT_OBTIANED.equals(errorVO.getErrorCode()))
				{
					throw new CurrencyConversionException(CurrencyConversionException.EXCHANGE_RATE_NOT_OBTIANED);
				}
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}

	return conversionrate;  
  }
}
