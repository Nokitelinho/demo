/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.ScreenLoadBookedFlightsCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	08-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm;


import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.ScreenLoadBookedFlightsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-Aug-2017	:	Draft
 */
public class ScreenLoadBookedFlightsCommand extends BaseCommand{

	
	 private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   private static final String TARGET = "bookingpopup_success";
	   
	   private static final String MODULE_NAME = "bsmail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";	
	   
	  
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 08-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */

		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {
			
			log.entering("ScreenLoadBookedFlightsCommand","execute");
			MailbookingPopupForm form = 
		    		(MailbookingPopupForm)invocationContext.screenModel;
			SearchConsignmentSession mailSession= getScreenSession(MODULE_NAME,SCREEN_ID);

			ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
				LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				String companyCode=null;
				String shipmentPrefix=null;
				String masterDocumentNumber=null;
				
			    	Collection<MailbagVO> mailbagVOs = mailSession.getMailbagVOsCollection();
			    	BaseMailOperationsDelegate delegate=new BaseMailOperationsDelegate();
			    	
			    	Collection<MailbagVO> selectedMailBagVO=new ArrayList<MailbagVO>();
			    	
			    	//added a-8061 for  ICRD-229573 begin 
			     	String[] selectedMailIds=null;
			    	if(form.getSelectedMailbagId()!=null){	    		
			    		 selectedMailIds =form.getSelectedMailbagId()[0].split(",");	    		
			    	}
			    	//added a-8061 for  ICRD-229573 end 
			    	
			    	if(selectedMailIds != null && mailbagVOs != null){
			    		
								
								for(int i=0; i < selectedMailIds.length; i++){
									String selectedId = selectedMailIds[i];
									for(MailbagVO mailbagVO:mailbagVOs){
								if(mailbagVO.getMailbagId().equals(selectedId)){
									companyCode=mailbagVO.getCompanyCode();
									shipmentPrefix= mailbagVO.getShipmentPrefix();
									masterDocumentNumber=mailbagVO.getDocumentNumber();
									form.setMasterDocumentNumber(masterDocumentNumber);
									form.setShipmentPrefix(shipmentPrefix);
									
									//added by A-8061 as part of ICRD-229572
									selectedMailBagVO.add(mailbagVO);
									
								}
							}
						}
							
						
			    	}
			    	Collection<MailBookingDetailVO>mailBookingDetailVOs =new ArrayList<MailBookingDetailVO>();
			    	
			    	try {
			    		mailBookingDetailVOs = delegate.fetchBookedFlightDetails(companyCode,shipmentPrefix,masterDocumentNumber);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
						businessDelegateException.getMessage();
					}
				
			    	mailSession.setMailBookingDetailsVO(mailBookingDetailVOs);
			    	//added by A-8061 as part of ICRD-229572
			    	mailSession.setselectedMailbagVOs(selectedMailBagVO);
					
				invocationContext.target = TARGET;
		        log.exiting("ScreenLoadBookedFlightsCommand","execute");
			
		}
}
