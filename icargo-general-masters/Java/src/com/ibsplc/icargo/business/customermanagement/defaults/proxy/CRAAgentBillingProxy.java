/*
 * CRADefaultsProxy.java Created on Sep 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;



import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.cra.agentbilling.cass.vo.CASSFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CCADetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerBillingInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.PaymentDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteDetailsVO;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteFilterVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("cra")
@SubModule("agentbilling")
public class CRAAgentBillingProxy
  extends ProductProxy
{
  
	/**
	 *	Method	    :	CRADefaultsProxy.getBillingInvoiceDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param filterVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	CustomerBillingInvoiceDetailsVO
	 */
	 public CustomerBillingInvoiceDetailsVO getBillingInvoiceDetails(CustomerFilterVO filterVO)
	   throws SystemException, ProxyException
	 {
	   return despatchRequest("getBillingInvoiceDetails",  filterVO );
	 }
 /**
	 *	Method	    :	CRADefaultsProxy.getCCADetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	List<CCADetailsVO>
	 */
	 public List<CCADetailsVO> getCCADetails(String invoiceNumber,String companyCode) throws SystemException, ProxyException{
			return despatchRequest("getCCADetails",  invoiceNumber,companyCode );
		}
	/**
	 *	Method	    :	CRADefaultsProxy.getPaymentDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	Collection<PaymentDetailsVO>
	 */
	public Collection<PaymentDetailsVO> getPaymentDetails(CustomerInvoiceDetailsVO customerInvoiceDetailsVO) throws SystemException, ProxyException{
		return despatchRequest("getPaymentDetails",  customerInvoiceDetailsVO );
	}
	
	/**
	 *	Method	    :	CRADefaultsProxy.findAccountStatementForPrint
	 *	Added by 	:	A-8169 on 23-Nov-2018 
	 *	Used for	: 	CR ICRD-236527
	 */
	public List<CustomerInvoiceAWBDetailsVO> findAccountStatementForPrint(
			CustomerFilterVO customerFilterVO) throws SystemException, ProxyException {
		return despatchRequest("findAccountStatementForPrint", customerFilterVO);
	}
	
	/**
	 *	Method	    :	CRADefaultsProxy.findAccountStatementForPrint
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *	Parameters	:	@param ScribbleNoteFilterVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	List<ScribbleNoteDetailsVO>
	 */
	public List<ScribbleNoteDetailsVO> getCustomerScribbleDetails(
			ScribbleNoteFilterVO scribbleNoteFilterVO) throws SystemException, ProxyException {
		return despatchRequest("getCustomerScribbleDetails", scribbleNoteFilterVO);
	}
	/**
	 *	Method	    :	CRADefaultsProxy.saveCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *	Parameters	:	@param ScribbleNoteFilterVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	void
	 */
	public void saveCustomerScribbleDetails(
			ScribbleNoteDetailsVO scribbleNoteDetailsVO) throws SystemException, ProxyException {
		despatchRequest("saveCustomerScribbleDetails", scribbleNoteDetailsVO);
	}
	
	public List<CustomerInvoiceAWBDetailsVO> findCassInvoiceAccountStatementForPrint(CASSFilterVO cassVO)
			throws ProxyException, SystemException {
		return despatchRequest("findCassInvoiceAccountStatementForPrint", cassVO);
	}
 
}
