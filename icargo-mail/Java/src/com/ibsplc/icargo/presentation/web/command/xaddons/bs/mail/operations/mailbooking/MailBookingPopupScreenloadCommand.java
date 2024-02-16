/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking.MailBookingPopupScreenloadCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	03-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm;



import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.MailBookingPopupScreenloadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	03-Aug-2017	:	Draft
 */
public class MailBookingPopupScreenloadCommand extends BaseCommand{
	
	 private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	   private static final String TARGET = "mailpopup_success";
	   
	   private static final String MODULE_NAME = "bsmail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";	
	   
	   private static final String KEY_SHIPMENT_ONETIME = "capacity.booking.shipmentstatus";
	   

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 03-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("MailBookingPopupScreenloadCommand","execute");
		MailbookingPopupForm carditEnquiryForm = 
	    		(MailbookingPopupForm)invocationContext.screenModel;
		SearchConsignmentSession mailSession= getScreenSession(MODULE_NAME,SCREEN_ID);
		mailSession.setMailBookingDetailsVOs(null);   
		Map<String, Collection<OneTimeVO>>oneTimeValues = null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			String companyCode = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			
			companyCode = logonAttributes.getCompanyCode();
			
			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(KEY_SHIPMENT_ONETIME);
			
			try {
				oneTimeValues = new SharedDefaultsDelegate()
										.findOneTimeValues(companyCode, oneTimeList);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			if(oneTimeValues != null){
				mailSession.setBookingStatus( 
						(ArrayList<OneTimeVO>)oneTimeValues.get(KEY_SHIPMENT_ONETIME));
			}
			Collection<MailbagVO> mailbagVOs = mailSession.getMailbagVOsCollection();
	    	
	    	
	    	Collection<MailbagVO> selectedMailBagVO=new ArrayList<MailbagVO>();
	    	
	    	//added by  a-8061 for ICRD-229330 begin
	    	String[] selectedMailIds=null;
	    	if(carditEnquiryForm.getSelectedMailbagId()!=null){	    		
	    		 selectedMailIds =carditEnquiryForm.getSelectedMailbagId()[0].split(",");	    		
	    	}
	    	if(selectedMailIds!=null && selectedMailIds.length==1 && selectedMailIds[0].isEmpty()){
	    		selectedMailIds=null;    
	    	}
	    	//added by  a-8061 for ICRD-229330 end
	    	
	    	
	    	/*if(selectedMailIds != null && mailbagVOs != null){
	    		
					for(MailbagVO mailbagVO:mailbagVOs){
						
						for(int i=0; i < selectedMailIds.length; i++){
							String selectedId = selectedMailIds[i];
						
						if(mailbagVO.getMailbagId().equals(selectedId)){
							selectedMailBagVO.add(mailbagVO);
						}
					}
				}
				
				
	    	}*/
	    
	    	if(selectedMailIds != null && mailbagVOs != null){
                
                
                
                for(int i=0; i < selectedMailIds.length; i++){
                                String selectedId = selectedMailIds[i];
                for(MailbagVO mailbagVO:mailbagVOs){
                if(mailbagVO.getMailbagId().equals(selectedId)){
                                selectedMailBagVO.add(mailbagVO);
                                break;
                               
                }
               
                }

} 
	    	}/*else{    
	    		CarditEnquiryFilterVO carditEnquiryFilterVO=carditEnquirySession.getCarditEnquiryFilterVO();
	    		carditEnquiryFilterVO.setConsignmentLevelAWbAttachRequired(MailConstantsVO.FLAG_YES);
	    		try {
					mailbagVOs=delegate.findCarditMails(carditEnquiryFilterVO,0);
				} catch (BusinessDelegateException e) {       
					errors = handleDelegateException(e);
					
				}
	    		selectedMailBagVO.addAll(mailbagVOs);
	    	}*/
                mailSession.setselectedMailbagVOs
(selectedMailBagVO);

	    	 	
			
	    	
			invocationContext.target = TARGET;
	        log.exiting("MailBookingPopupScreenloadCommand","execute");
		
	}

}
