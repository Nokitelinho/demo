/*
 * MailTrackingDefaultsProxy.java Created on Mar 9, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.framework.proxy.AsyncProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1945
 */

/*
 *
 * Revision History
 * Version	 	Date      		    Author			Description
 * 0.1			Mar 9, 2007 	  	A-1945			Initial draft
 *
 */
@Module("mail") 
@SubModule("mra")
public class MailTrackingMRAProxy extends AsyncProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA PROXY");

	private static final String SERVICE_NAME = "MAILTRACKING_MRA";
	
	/**
	 * 
	 * @param messageVO  
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void sendEmailforPAs(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException,ProxyException{
		log.entering("MailTrackingMRAProxy", "sendEmailforPAs");	
		dispatchAsyncRequest("sendEmailforPAs",true,postalAdministrationVOs,generateInvoiceFilterVO);
		log.exiting("MailTrackingMRAProxy", "sendEmailforPAs");
	}

	/**
	 * 	Method		:	MailTrackingMRAProxy.importConsignmentDataToMra
	 *	Added by 	:	A-4809 on Nov 20, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentDocumentVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void importConsignmentDataToMRA(MailInConsignmentVO mailInConsignmentVO) 
			throws SystemException,ProxyException{
		log.entering("MailTrackingMRAProxy", "importConsignmentDataToMra");	
		dispatchProductAsyncRequest("importConsignmentDataToMRA",true,mailInConsignmentVO);
		log.exiting("MailTrackingMRAProxy", "importConsignmentDataToMra"); 
	}   
	/**
	 * 	Method		:	MailTrackingMRAProxy.sendEmailforTK
	 *	Added by 	:	A-5526 on Feb 12, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param PostalAdministrationVOs
	 *	Parameters	:	@throws generateInvoiceFilterVO
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void sendEmailforTK(Collection<PostalAdministrationVO> palist,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws ProxyException, SystemException {
		log.entering("MailTrackingMRAProxy", "sendEmailforTK");	
		dispatchAsyncRequest("sendEmailforTK",true,palist,generateInvoiceFilterVO);
		log.exiting("MailTrackingMRAProxy", "sendEmailforTK");
		
	}
	/**
	 * 	Method		:	MailTrackingMRAProxy.sendEmailforAA
	 *	Added by 	:	A-5526 on Feb 12, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param PostalAdministrationVOs
	 *	Parameters	:	@throws generateInvoiceFilterVO
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void sendEmailforAA(Collection<PostalAdministrationVO> palist,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws ProxyException, SystemException {
		log.entering("MailTrackingMRAProxy", "sendEmailforAA");	
		dispatchAsyncRequest("sendEmailforAA",true,palist,generateInvoiceFilterVO);
		log.exiting("MailTrackingMRAProxy", "sendEmailforAA");
		
	}
	/**
	 * 	Method		:	MailTrackingMRAProxy.sendEmailRebillInvoice
	 *	Added by 	:	A-5526 on Mar 04, 2019
	 * 	Used for 	:ICRD-234283-Rebill invoice email sending from CRA209-Reminder report flow
	 *	Parameters	:	@param cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs
	 *	Parameters	:	@throws ProxyException,SystemException
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void sendEmailRebillInvoice(Collection<CN51DetailsVO> cn51DetailsVoS, CN51CN66FilterVO cn51cn66FilterVO,
			Collection<CN66DetailsVO> cn66DetailsVos, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs) throws ProxyException, SystemException {
		log.entering("MailTrackingMRAProxy", "sendEmailRebillInvoice");	
		dispatchAsyncRequest("sendEmailRebillInvoice",true,cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs);
		log.exiting("MailTrackingMRAProxy", "sendEmailRebillInvoice");    
		
		
	}  
	/**
	 * 	Method		:	MailTrackingMRAProxy.sendEmailRebillInvoice
	 *	Added by 	:	A-5526 on Mar 04, 2019
	 * 	Used for 	:ICRD-234283-Rebill invoice email sending from CRA209-GPA rebill flow
	 *	Parameters	:	@param reminderDetailsFilterVO
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void sendEmailRebillInvoice(ReminderDetailsFilterVO reminderDetailsFilterVO) throws ProxyException, SystemException {
		log.entering("MailTrackingMRAProxy", "sendEmailRebillInvoice");	
		dispatchAsyncRequest("sendEmailOnRebillInvoiceGeneration",true,reminderDetailsFilterVO);
		log.exiting("MailTrackingMRAProxy", "sendEmailRebillInvoice");    
		
		
	}   
	
	/**
	 * 	Method		:	MailTrackingMRAProxy.calculateUSPSIncentiveAmount
	 *	Added by 	:	A-5526 on Mar 04, 2019
	 * 	Used for 	:IASCB-28259-calculateUSPSIncentiveAmount
	 *	Parameters	:	@param USPSPostalCalendarVO
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void calculateUSPSIncentiveAmount(USPSPostalCalendarVO uspsPostalCalendarVO,USPSIncentiveVO uspsIncentiveVO) throws ProxyException, SystemException {
		log.entering("MailTrackingMRAProxy", "calculateUSPSIncentive");	
		dispatchAsyncRequest("calculateUSPSIncentiveAmount",true,uspsPostalCalendarVO,uspsIncentiveVO);
		log.exiting("MailTrackingMRAProxy", "calculateUSPSIncentive");    
		
		
	}   
	
}
