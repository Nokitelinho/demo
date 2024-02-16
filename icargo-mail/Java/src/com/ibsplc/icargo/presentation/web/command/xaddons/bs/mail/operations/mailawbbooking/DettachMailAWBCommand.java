/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailawbbooking.DettachMailAWBCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	08-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailawbbooking.DettachMailAWBCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-Sep-2017	:	Draft
 */
public class DettachMailAWBCommand extends BaseCommand{

	
	private Log log = LogFactory.getLogger("DettachMailAWBCommand");
	 private static final String MODULE_NAME = "bsmail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	   private static final String TARGET = "dettachmail_success";
	   private static final String STATUS_DETACH="xaddons.bs.mail.operations.awb.status.dettach";
	   public static final String STATUS_ACCEPTED = "Y";
	   private static final String TARGET_FAILURE = "dettachmail_failure";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		
		SearchConsignmentForm consignmentform = 
				(SearchConsignmentForm)invocationContext.screenModel;
	    	SearchConsignmentSession consignmentSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	String[] awb = consignmentform.getAwbNumber();
	    	Collection<MailbagVO> mailbagVOs = consignmentSession.getMailbagVOsCollection();
	    	BaseMailOperationsDelegate delegate=new BaseMailOperationsDelegate();
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	Collection<MailbagVO> selectedMailBagVO=new ArrayList<MailbagVO>();
	    	
	    	String[] selectedMailIds=null;
	    	if(consignmentform.getSelectedMailbagId()!=null){	    		
	    		 selectedMailIds =consignmentform.getSelectedMailbagId()[0].split(",");	    		
	    	}
	    	if(selectedMailIds!=null && selectedMailIds.length==1 && selectedMailIds[0].isEmpty()){
	    		selectedMailIds=null;    
	    	}
	    	
	    	
	    	//String[] selectedMailIds = consignmentform.getSelectedMailbagId()[0].split(",");
	    	if(selectedMailIds != null && mailbagVOs != null){
	    		
						for(int i=0; i < selectedMailIds.length; i++){
							String selectedId = selectedMailIds[i];
	    			for(MailbagVO mailbagVO:mailbagVOs){
							
						//consignmentform.setDocumentNumber(awb[i]);
						if(mailbagVO.getMailbagId().equals(selectedId)){
							selectedMailBagVO.add(mailbagVO);
						}
					}
				}
					
				

	    	}
	    	 consignmentform.setDisableButton("Y");  
	    	//added by a-8061 for ICRD-229273 starts
			if (selectedMailBagVO != null && selectedMailBagVO.size() > 0) {
				for (MailbagVO mailbagVO : selectedMailBagVO) {
					if(STATUS_ACCEPTED.equals(mailbagVO.getAccepted())){
						ErrorVO error = new ErrorVO(STATUS_DETACH);
						errors.add(error);
						invocationContext.addAllError(errors);   
			            invocationContext.target = TARGET_FAILURE;
			            log.exiting("DettachMailAWBCommand","isacceptedcondition");
						return;
					}
				}
			}
			//added by a-8061 for ICRD-229273 end
			CarditEnquiryFilterVO carditEnquiryFilterVO=consignmentSession.getCarditEnquiryFilterVO();
	    	try {
				delegate.dettachMailBookingDetails (selectedMailBagVO,carditEnquiryFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
	    	
	    	
	   
		if(errors!=null&&errors.size()>0)
		{
	        for (ErrorVO err : errors) {
	            if ("mailtracking.defaults.searchconsignment.selectedmailcountexceeded".equalsIgnoreCase(err.getErrorCode())) {
	            	 Object[] obj = { carditEnquiryFilterVO.getMailCount() };
	              ErrorVO error = new ErrorVO("xaddons.mail.operations.searchconsignment.detach.selectedmailcountexceeded",obj);
	            	 
	              errors= new ArrayList<ErrorVO>();
	                errors.add(error);
			invocationContext.addAllError(errors);   
            invocationContext.target = TARGET_FAILURE;
            return;
	            }else{
	            	invocationContext.addAllError(errors);   
	                invocationContext.target = TARGET_FAILURE;
	                return;
	            }
	        }
			
		}
		invocationContext.target = TARGET;
    	log.exiting("DettachMailAWBCommand","execute");
	}

}
