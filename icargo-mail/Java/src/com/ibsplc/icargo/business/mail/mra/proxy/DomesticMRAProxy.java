/*
 * DomesticMRAProxy.java Created on NOV 18,2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.icargo.framework.proxy.SubSystemProxy;

/**
 * ->@author a-3447
 */
import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3447 Muralee
 * Revision History:-
 *
 * Version   Date                 Author            Description
 * ***************************************************************************        
 * 0.1		 Nov 18,2009 		 Muralee Krishnan		 Initial draft 
 *
 *
 */
public class DomesticMRAProxy extends SubSystemProxy {
	private Log log = LogFactory.getLogger("MailtrackingMRAProxy");

	private static final String SERVICE_NAME = "MAILTRACKING_DOMMRA";
	

   

	/**
	 * @author a-3447
	 * @param companyCode 
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void importDomesticMails(String companyCode) throws SystemException, ProxyException {
		/*log.entering("DomesticMRAProxy", "importDomesticMails");
		try {
			constructService().importDomesticMails(companyCode);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		log.exiting("DomesticMRAProxy", "importDomesticMails");*/
	}

	/**
	 * @author a-3447
	 * @param domFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 * @throws RemoteException
	 * @throws ServiceNotAccessibleException
	 */

	/*public Page<MailInvoicClaimsEnquiryVO> findDomClaimEnquiryDetails(DomesticMailInvoicClaimFilterVO domFilterVO)throws SystemException, ProxyException { 
		log.entering("DomesticMRAProxy", "findDomClaimEnquiryDetails");
		Page<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOs=null;
		/*try {
			mailInvoicClaimsEnquiryVOs=	constructService().findDomClaimEnquiryDetails(domFilterVO);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
			
		} catch (SystemException e) {
			throw new ProxyException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}*/
		//return  mailInvoicClaimsEnquiryVOs;
		
	//}
	
	/**
	 * @author a-2596
	 * @param domFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 * @throws RemoteException
	 * @throws ServiceNotAccessibleException
	 */

	/*public Page<MailInvoicEnquiryDetailsVO> findDomInvoiceEnquiryDetails(DomesticMailInvoicEnquiryFilterVO domFilterVO)throws SystemException, ProxyException { 
		log.entering("DomesticMRAProxy", "findDomInvoiceEnquiryDetails");
		Page<MailInvoicEnquiryDetailsVO> invoiceEnquiryDetails=null;*/
		/*try {
			invoiceEnquiryDetails=	constructService().findDomInvoiceEnquiryDetails(domFilterVO);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
			
		} catch (SystemException e) {
			throw new ProxyException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}*/
		//return  invoiceEnquiryDetails;
		
	//}

	public void reconcileProcessDomestic(String companyCode, String mode) throws SystemException, ProxyException{
		log.entering("DomesticMRAProxy", "reconcileProcessDomestic");
		/*try{
			constructService().reconcileProcessDomestic(companyCode,mode);
		}catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
			
		} catch (SystemException e) {
			throw new ProxyException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}*/
		
	}
	
	

	  
/**
	 * @param mailInvoicClaimsEnquiryVOsForDel
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void deleteInvoicClaimsEnquiryDetails 
	(Collection<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOsForDel)throws SystemException, ProxyException { 
		log.entering("DomesticMRAProxy", "findDomClaimEnquiryDetails");
		
		/*try {
				constructService().deleteInvoicClaimsEnquiryDetails(mailInvoicClaimsEnquiryVOsForDel);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
			
		} catch (SystemException e) {
			throw new ProxyException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}*/
	
		
	}


}
