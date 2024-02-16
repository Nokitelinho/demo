/*
 * ListMailbagsCommand.java 
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmailbag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailbagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227
 *
 */
public class ListMailbagsCommand  extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET = "success";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.reassignmailbag";
	private static final String MAILEXPORTLIST_SCREEN_ID = "mailtracking.defaults.mailexportlist";	
	private static final String CURRENTSTATUS = "mailtracking.defaults.mailstatus";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("ListMailbagsCommand","execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();  
		ReassignMailbagForm reassignMailbagForm = (ReassignMailbagForm)invocationContext.screenModel;	    	
		ReassignMailbagSession reassignMailbagSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,MAILEXPORTLIST_SCREEN_ID);	 

		Collection<ErrorVO> errors = null;

		Page<MailbagVO> mailbagVOPage = null;
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailExportListSession.getMailbagEnquiryFilterVO();
		mailbagEnquiryFilterVO.setFromScreen(reassignMailbagForm.getFromScreen());
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();		
		int displayPage = reassignMailbagForm.getDisplayPage();		
		try {

			mailbagVOPage = mailTrackingDefaultsDelegate.findMailbags(mailbagEnquiryFilterVO,displayPage);
             if(mailbagVOPage !=  null){
			for(MailbagVO mailbagVO : mailbagVOPage){
				/*Commented as part of ICRD-260453
				 * Domestic mail tags will not be 29 character.Weight already set in VO is measure object
				String  maibagid = mailbagVO.getMailbagId();
				Double weight = Double.parseDouble(maibagid.substring(25,29));
				//mailbagVO.setWeight(weight/10);
				mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,weight/10));//added by A-7371*/
				if(mailbagVO.getScannedDate() != null) {
					LocalDate sd = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
					String scanDate = mailbagVO.getScannedDate().toDisplayDateOnlyFormat();
					String scanTime = mailbagVO.getScannedDate().toDisplayTimeOnlyFormat();
					String scanDT = new StringBuilder(scanDate).append(" ").append(scanTime).toString();
					mailbagVO.setScannedDate(sd.setDateAndTime(scanDT));
				}
			}	    	
           }
			log.log(Log.FINE, "Mailbags :::", mailbagVOPage);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> currentStatus = oneTimes.get(CURRENTSTATUS);
			reassignMailbagSession.setCurrentStatus(currentStatus);
		}
		
		if (errors != null && errors.size() > 0) {
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.nomailbags");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);	
			invocationContext.target = TARGET;
			return;
		}			
		
		reassignMailbagSession.setMailbagVOs(mailbagVOPage);	
		if((reassignMailbagForm.getDisplayPage()>MailConstantsVO.ZERO)
				&& ((reassignMailbagForm.getFlightCarrierCode()!=null && reassignMailbagForm.getFlightCarrierCode().length()>0)
						|| (reassignMailbagForm.getCarrierCode() !=null && reassignMailbagForm.getCarrierCode().length()>0 )
						|| (reassignMailbagForm.getFlightNumber() !=null && reassignMailbagForm.getFlightNumber().length()>0))){
			reassignMailbagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			reassignMailbagForm.setNext(MailConstantsVO.FLAG_YES);
		}else{
			if(MailConstantsVO.FLAG_YES.equals(reassignMailbagForm.getNext())
					&& ((reassignMailbagForm.getFlightCarrierCode()!=null && reassignMailbagForm.getFlightCarrierCode().length()>0)
							|| (reassignMailbagForm.getCarrierCode() !=null && reassignMailbagForm.getCarrierCode().length()>0 )
							|| (reassignMailbagForm.getFlightNumber() !=null && reassignMailbagForm.getFlightNumber().length()>0) )){
				reassignMailbagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);				
			}else{
				reassignMailbagForm.setNext(MailConstantsVO.FLAG_NO);				
				if(reassignMailbagForm.getFlightCarrierCode()== null 
						|| reassignMailbagForm.getCarrierCode() == null 
						|| reassignMailbagForm.getFlightNumber() == null
						|| reassignMailbagForm.getDepDate() == null){					
					reassignMailbagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);					
				}
			}
		}    		
		invocationContext.target = TARGET;
		log.exiting("ListMailbagsCommand","execute");
	}
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(CURRENTSTATUS);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
