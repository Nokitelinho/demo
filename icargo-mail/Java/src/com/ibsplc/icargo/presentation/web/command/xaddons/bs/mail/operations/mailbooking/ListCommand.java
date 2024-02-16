/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.ListCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	09-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;






import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;


import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm;



import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	09-Aug-2017	:	Draft
 */
public class ListCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListCommand");
	 
	   private static final String MODULE_NAME = "bsmail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	   private static final String TARGET = "listmail_success";
	   public static final String NAV_MOD_PAGINATION = "NAVIGATION";
	   public static final String NAV_MOD_PAGINATION_LIST = "LIST";//added by A-7371 for ICRD-228233  
       private static final String FAILED="listmail_failure";
	   private static final String NO_REULTS_FOUND= "xaddons.bs.mail.operations.msg.status.noresultsfound";
	   //added by  a-8061 for ICRD-229353
	   private static final String SHIPMENT_STATUS = "operations.shipment.shipmentstatus";
	   private static final String MAIL_SCC_CODES_SYSPAR = "operations.shipment.mailsccs";
	   private static final String INVALID_MAIl_SCC= "xaddons.bs.mail.operations.msg.status.notmailscc";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 09-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("ListCommand","executeee");
		// TODO Auto-generated method stub
		Collection<ErrorVO> errors = null;
		MailbookingPopupForm mailPopupForm = 
	    		(MailbookingPopupForm)invocationContext.screenModel;
	    	SearchConsignmentSession carditEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	MailBookingFilterVO mailBookingFilterVO = new MailBookingFilterVO();
	    	
	    	LocalDate date = null;//modified by A-7371 as part of ICRD-229099
	    	
	    			
	    			
	    	mailBookingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	mailBookingFilterVO.setAgentCode(mailPopupForm.getAgentCode());
	    	mailBookingFilterVO.setBookingCarrierCode(mailPopupForm.getBookingCarrierCode());
	    	mailBookingFilterVO.setBookingFlightFrom(mailPopupForm.getBookingFlightFrom());
	    	mailBookingFilterVO.setBookingFlightNumber(mailPopupForm.getBookingFlightNumber());
	    	mailBookingFilterVO.setBookingFlightTo(mailPopupForm.getBookingFlightTo());
	    	
	    	
	    	//Added by A-8893 for ICRD-332006 begins
	    	if(mailPopupForm.getBookingFrom()==null){
	    		mailPopupForm.setBookingFrom
				((new LocalDate(
						logonAttributes.getAirportCode(),Location.ARP,true).
								addDays(-30)).toDisplayDateOnlyFormat());
	    	}
	    	
	    	if(mailPopupForm.getBookingTo()==null){
	    		mailPopupForm.setBookingTo(
				new LocalDate(
						logonAttributes.getAirportCode(),Location.ARP,true).toDisplayDateOnlyFormat());
	    	}
	    	
	    	//Added by A-8893 for ICRD-332006 ends
	    	
	    	if(mailPopupForm.getBookingFrom()!=null &&  mailPopupForm.getBookingFrom().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    	mailBookingFilterVO.setBookingFrom(date.setDate(mailPopupForm.getBookingFrom()));
	    	}
	    	mailBookingFilterVO.setBookingStatus(mailPopupForm.getBookingStatus());
	    	if(mailPopupForm.getBookingTo()!=null&& mailPopupForm.getBookingTo().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    	mailBookingFilterVO.setBookingTo(date.setDate(mailPopupForm.getBookingTo()));
	    	}
	    	mailBookingFilterVO.setBookingUserId(mailPopupForm.getBookingUserId());
	    	mailBookingFilterVO.setCustomerCode(mailPopupForm.getCustomerCode());
	    	mailBookingFilterVO.setDestinationOfBooking(mailPopupForm.getDestinationOfBooking());
	    	mailBookingFilterVO.setMailScc(mailPopupForm.getMailScc());
	    	mailBookingFilterVO.setMasterDocumentNumber(mailPopupForm.getMasterDocumentNumber());
	    	mailBookingFilterVO.setOrginOfBooking(mailPopupForm.getOrginOfBooking());
	    	mailBookingFilterVO.setProduct(mailPopupForm.getMailProduct());
	    	if(mailPopupForm.getShipmentDate()!=null && mailPopupForm.getShipmentDate().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setShipmentDate(date.setDate(mailPopupForm.getShipmentDate()));
	    	}
	    	mailBookingFilterVO.setShipmentPrefix((mailPopupForm.getShipmentPrefix()));
	    	mailBookingFilterVO.setStationOfBooking(mailPopupForm.getStationOfBooking());
	    	mailBookingFilterVO.setViaPointOfBooking(mailPopupForm.getViaPointOfBooking());

	        if (NAV_MOD_PAGINATION.equalsIgnoreCase(mailPopupForm.getActionName())) {
	          mailBookingFilterVO.setTotalRecordsCount(carditEnquirySession.getTotalRecordCount());
	          mailBookingFilterVO.setPageNumber(Integer.parseInt(mailPopupForm.getDisplayPage()));
	        } else if (NAV_MOD_PAGINATION_LIST.equalsIgnoreCase(mailPopupForm.getActionName())) {
	          mailBookingFilterVO.setTotalRecordsCount(carditEnquirySession.getTotalRecordCount());
	          mailBookingFilterVO.setPageNumber(1);
	          mailPopupForm.setDisplayPage(String.valueOf(1));
	        }
	        
	    	String displayPg=mailPopupForm.getDisplayPage();
	    	Collection<String> systemParameterCodes = new ArrayList<String>();   
	    	systemParameterCodes.add(MAIL_SCC_CODES_SYSPAR);		
			String mailSccCode = findSystemParameterByCodes(systemParameterCodes).get(MAIL_SCC_CODES_SYSPAR);
			mailBookingFilterVO.setMailSccFromSyspar(mailSccCode); 
		if (mailBookingFilterVO.getMailScc() != null && mailBookingFilterVO.getMailScc().trim().length() >0) {
			boolean sccValid = true;
			int count=0;
			int sccCount=0;
			String[] mailSccs = null;
			String[] Sccs = null;
			if (mailSccCode != null)   
				mailSccs = mailSccCode.split(",");
			if (mailBookingFilterVO.getMailScc() != null && mailBookingFilterVO.getMailScc().contains(",")){
				Sccs = mailBookingFilterVO.getMailScc().split(",");
				sccCount=Sccs.length;
			
			for (int i = 0; i < Sccs.length; i++) {
				for (int j = 0; j < mailSccs.length; j++) {           
					if (Sccs[i].equals(mailSccs[j])) {
						sccValid = true; 
						count++;
					}
				}
			} 
			if ( count<sccCount) {        
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				error = new ErrorVO(INVALID_MAIl_SCC);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = FAILED;
				return;
			}
			}else{ sccCount=1;      
				for (int j = 0; j < mailSccs.length; j++) {
					if (mailBookingFilterVO.getMailScc().equals(mailSccs[j])) {
						sccValid = true;
						break;
					}
					else{
						sccValid = false;    
					}
				}
				if (!sccValid ) {        
					errors = new ArrayList<ErrorVO>();
					ErrorVO error = null;
					error = new ErrorVO(INVALID_MAIl_SCC);
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = FAILED;
					return;
				}
			}
			
		}
	         Page<MailBookingDetailVO> mailbookingVO=null;
	    	 BaseMailOperationsDelegate delegate = new BaseMailOperationsDelegate();
	    	try
	    	{
	    		mailbookingVO = delegate.findMailBookingAWBs(mailBookingFilterVO,Integer.parseInt(displayPg));
	    	}
	    	catch(BusinessDelegateException businessDelegateException) {
	    		errors = new ArrayList<ErrorVO>();
				errors = handleDelegateException(businessDelegateException);
				//businessDelegateException.getMessage();
			}
	    	if (mailbookingVO == null || mailbookingVO.size() == 0) {
	    		carditEnquirySession.setMailBookingDetailsVOs(null);
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				error = new ErrorVO(
						NO_REULTS_FOUND);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = FAILED;
				return;
			} 
	    	if(errors!=null && errors.size()>0){
	    		invocationContext.addAllError(errors);
	    		invocationContext.target = FAILED;
	    	}
	    	Collection<MailBookingDetailVO> coll=new ArrayList<MailBookingDetailVO>(); 
	    	
	    	if(mailbookingVO!=null){
	           int  size1=mailbookingVO.size();
		    	
	            for(int i=0;i<size1;i++){
	                   coll.add(mailbookingVO.get(i)) ;
	                   
	            }
	    	}


	    	//added by  a-8061 for ICRD-229353 begin
	    	if(mailbookingVO!=null){
		    	Collection<String> oneTimeList = new ArrayList<String>();
		    	Map<String, Collection<OneTimeVO>> oneTimeVOsMap = new HashMap<String, Collection<OneTimeVO>>();
		    	try{
					oneTimeList.add(SHIPMENT_STATUS);
					SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
					oneTimeVOsMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), oneTimeList);
					if(carditEnquirySession.getOneTimeVOs().isEmpty()){
					carditEnquirySession.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeVOsMap);
					}else{
						carditEnquirySession.getOneTimeVOs().put(SHIPMENT_STATUS, oneTimeVOsMap.get(SHIPMENT_STATUS));
					}
		    	}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
	    	}
	    	
	    	//added by  a-8061 for ICRD-229353 end

	    	if(coll!=null && coll.size()>0)
	    	{
	    	carditEnquirySession.setMailBookingDetailsVOs(mailbookingVO);
	    	//mailPopupForm.setTotalViewRecords(String.valueOf(carditEnquirySession.getMailBookingDetailsVOs().size()));
	    	//mailPopupForm.setLastPageNum(mailPopupForm.getTotalViewRecords());
	    	 carditEnquirySession.setTotalRecordCount(mailbookingVO.getTotalRecordCount());

	    	}
		invocationContext.target = TARGET;
    	log.exiting("ListCommand","execute");
		
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


