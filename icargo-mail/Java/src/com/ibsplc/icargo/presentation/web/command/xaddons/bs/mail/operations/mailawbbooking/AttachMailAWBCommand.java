/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.mailawbbooking.AttachMailAWBCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	22-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm;




import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.mailawbbooking.AttachMailAWBCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	22-Aug-2017	:	Draft
 */
public class AttachMailAWBCommand extends BaseCommand{


	private Log log = LogFactory.getLogger("AttachMailAWBCommand");
	 private static final String MODULE_NAME = "bsmail.operations";
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	   private static final String TARGET = "attachmail_success";
	   public static final String STATUS_EXCECUTED = "E";
	   public static final String NOT_EXECUTED="xaddons.bs.mail.operations.awb.status.excecuted";
	   public static final String FAILED="listmail_failure";
	   private static final String AWB_DESTINATION_ERRORTYPE = "mail.operations.awbdestination.mismatch.errortype";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 22-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {


		MailbookingPopupForm mailpopForm =
				(MailbookingPopupForm)invocationContext.screenModel;
	    	SearchConsignmentSession consignmentSession =
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	Collection<MailbagVO> selectedMailBagVO = consignmentSession.getMailbagVOsCollection();
	    	BaseMailOperationsDelegate delegate=new BaseMailOperationsDelegate();
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	SearchConsignmentSession carditEnquirySession = 
	        		getScreenSession("mail.operations",SCREEN_ID);
	    	CarditEnquiryFilterVO carditEnquiryFilterVO=carditEnquirySession.getCarditEnquiryFilterVO();
	    	selectedMailBagVO =consignmentSession.getselectedMailbagVOs();
	    	Collection<MailBookingDetailVO> selectedMailBookingVO =new ArrayList<MailBookingDetailVO>();
	    	selectedMailBookingVO=consignmentSession.getMailBookingDetailsVOs();
	    	Collection<String> systemParameterCodes = new ArrayList<String>();   
	    	systemParameterCodes.add(AWB_DESTINATION_ERRORTYPE);
	    	String errorType = findSystemParameterByCodes(systemParameterCodes).get(AWB_DESTINATION_ERRORTYPE);
	    	String[] position = mailpopForm.getSelectedMail();
	    	int exactIndex=0;
	    	if(mailpopForm.getSelectedMail()==null){
	    		exactIndex=mailpopForm.getSelectedAwbIndex();
	    	}else{    
	    	exactIndex=Integer.parseInt(position[0]);
	    	}
	    	int i=1;      
	    	MailBookingDetailVO mailBookingDetailVO=selectedMailBookingVO.iterator().next(); 
	    	
	    	
	    	try {
				delegate.validateMailTags(selectedMailBagVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
	    	
			if(errors!=null&&errors.size()>0){
				invocationContext.addAllError(errors);   
	            invocationContext.target = FAILED;
	            return;
			}


	       for(MailBookingDetailVO mailDetailVO:selectedMailBookingVO){
	    	   if(i==exactIndex){  
	    		   mailBookingDetailVO=mailDetailVO;
	    		  String status= mailBookingDetailVO.getShipmentStatus();
	    		  if(STATUS_EXCECUTED.equals(status))
	    		  {
	    			  errors = new ArrayList<ErrorVO>();
	  				ErrorVO error = null;
	  				error = new ErrorVO(
	  						NOT_EXECUTED);
	  				errors.add(error);
	  				invocationContext.addAllError(errors);
					invocationContext.target = FAILED ;
					return;
	    		  }
	    		   break;
	    	   }
	    	   i++;
	       }
	    	 
	    	//mailBookingDetailVO.setSegementserialNumber(1);
	    	/*FlightValidationVO flightValidationVO=validateFlightForAirport(mailBookingDetailVO);
            if(flightValidationVO!=null){
                   mailBookingDetailVO.setSegementserialNumber(flightValidationVO.getLegSerialNumber())     ;
            }
*/
	       mailBookingDetailVO.setDestinationCheckReq(true);
	       if("N".equals(errorType) || errorType==null){
	    	   mailBookingDetailVO.setDestinationCheckReq(false);
	       }    
	       else if(mailpopForm.getDestMismatchFlag()!=null && "N".equals(mailpopForm.getDestMismatchFlag())){
	    	   mailBookingDetailVO.setDestinationCheckReq(false);
	    	   
	       }
	       int mismatchCount=0;
	    	try {
	    		mismatchCount=delegate.saveMailBookingDetails(selectedMailBagVO,mailBookingDetailVO,carditEnquiryFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
	    	if(errors!=null&&errors.size()>0)
			{
	    	for (ErrorVO err : errors) {
	            if ("mailtracking.defaults.searchconsignment.selectedmailcountexceeded".equalsIgnoreCase(err.getErrorCode())) {
	            	Object[] obj = { carditEnquiryFilterVO.getMailCount() };
		              ErrorVO error = new ErrorVO("xaddons.mail.operations.searchconsignment.attach.selectedmailcountexceeded",obj);
		            	 
		              errors= new ArrayList<ErrorVO>();
		                errors.add(error);
						invocationContext.addAllError(errors); 
		                invocationContext.target = FAILED;            
		              return; 
	            }else{
	            	invocationContext.addAllError(errors);   
	                invocationContext.target = FAILED;
	                return;
	            }
	        }
			}
	    	if(mismatchCount>0){
	    		Object[] obj = { mismatchCount };
	             
	            	 if("E".equals(errorType)){
	            		 ErrorVO error = new ErrorVO("xaddons.mail.operations.searchconsignment.attach.awbdestinationmismatch.error",obj);
	            		 error.setErrorDisplayType(ErrorDisplayType.ERROR);
	            		 errors= new ArrayList<ErrorVO>();
	 	                errors.add(error);
	 					invocationContext.addAllError(errors); 
	 	                invocationContext.target = FAILED;                    
	 	              return;
	            	 }else if("W".equals(errorType)){
	            		 mailpopForm.setDestMismatchFlag("Y");    
	            		 mailpopForm.setSelectedAwbIndex(exactIndex);    
	            		 //String[] ind=new String[1];
	            		 //ind[0]=String.valueOf(exactIndex);    
	            		// mailpopForm.setSelectedMail(ind);
	            		 ErrorVO error = new ErrorVO("xaddons.mail.operations.searchconsignment.attach.awbdestinationmismatch.warning",obj);
	            		 error.setErrorDisplayType(ErrorDisplayType.WARNING);
	            		 errors= new ArrayList<ErrorVO>();    
	 	                errors.add(error);
	 					invocationContext.addAllError(errors);                 
	 	                invocationContext.target = TARGET;                           
	 	              return;   
	            	 }
	              	
	    	}
	    	mailpopForm.setPopUpFlag("N");
	    	invocationContext.target = TARGET;
	    	log.exiting("AttachMailAWBCommand","execute");


	}

	private Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.entering("UploadMailDetailsCommand","findSystemParameterByCodes");
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		log.exiting("UploadMailDetailsCommand","findSystemParameterByCodes");
		return results;
	}

}
