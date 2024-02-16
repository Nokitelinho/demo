/*
 * CRADefaultsProxy.java Created on June 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;

/**
 * @author A-3251
 *
 */
@Module("cra")
@SubModule("defaults")
public class CRADefaultsProxy extends ProductProxy{
	private static final String SERVICE_NAME = "CRA_DEFAULTS";
	
	
	
	
	 /**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public IATACalendarVO  validateClearancePeriod(IATACalendarFilterVO  filterVO) 

	    throws SystemException, ProxyException {

		 return despatchRequest("validateClearancePeriod", filterVO);	
	}
	
	
	/**
	 * Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 * @param invoiceTransactionLogVO
	 * @return invoiceTransactionLogVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	
	public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO )
	
					 throws SystemException, ProxyException {

		 return despatchRequest("initiateTransactionLogForInvoiceGeneration", invoiceTransactionLogVO);	
	}
	
	/**
	 * Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 * @param invoiceTransactionLogVO
	 * 
	 * @throws SystemException
	 * @throws ProxyException
	 */
	
	public void updateTransactionandRemarks(
			InvoiceTransactionLogVO invoiceTransactionLogVO )
	
					 throws SystemException, ProxyException {

		  despatchRequest("updateTransactionandRemarks", invoiceTransactionLogVO);	
	}
	
	 //Added as part IASCB-860 starts
	public Collection<CRAParameterVO> findCRAParameterDetails(String companyCode, String parameterType)  throws SystemException, ProxyException{ 
	
		return despatchRequest("findCRAParameterDetails",companyCode,parameterType);
	}
	
	 //Added as part IASCB-860 ends
}
