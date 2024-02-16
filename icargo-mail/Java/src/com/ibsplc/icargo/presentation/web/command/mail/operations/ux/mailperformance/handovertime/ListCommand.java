/*
 * ListCommand.java Created on Jul 05, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.handovertime;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
public class ListCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String NO_RESULTS_FOUND = "mailtracking.defaults.ux.mailperformance.msg.err.noresultsfound";
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the list command----------> \n\n");
		
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		String airport = mailPerformanceForm.getHoAirport().toUpperCase().trim();
		MailHandoverFilterVO handoverFilterVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
		int displayPage=Integer.parseInt(mailPerformanceForm.getDisplayPage());
		
	    handoverFilterVO = new MailHandoverFilterVO();
	 	handoverFilterVO.setPageNumber(displayPage);
		handoverFilterVO.setCompanyCode(companyCode);
		handoverFilterVO.setAirportCode(airport);
		handoverFilterVO.setGpaCode(mailPerformanceForm.getHoPaCode());
		handoverFilterVO.setMailClass(mailPerformanceForm.getHoMailClass());
		handoverFilterVO.setMailSubClass(mailPerformanceForm.getHoMailSubClass());
		if(mailPerformanceForm.getHoExchangeOffice() !=null && mailPerformanceForm.getHoExchangeOffice().length()==6){
			handoverFilterVO.setExchangeOffice(mailPerformanceForm.getHoExchangeOffice().substring(0, 5));
		}else if(mailPerformanceForm.getHoExchangeOffice() !=null && mailPerformanceForm.getHoExchangeOffice().length()<6){
			handoverFilterVO.setExchangeOffice(mailPerformanceForm.getHoExchangeOffice());
		}
		//Added by A-8527 for ICRD-352718
		if(mailPerformanceForm.getDefaultPageSize()!=null && mailPerformanceForm.getDefaultPageSize().trim().length()>0){
		handoverFilterVO.setDefaultPageSize(Integer.parseInt(mailPerformanceForm.getDefaultPageSize()));
		}
		/*if("LIST".equals(mailPerformanceForm.getPagination())){
			handoverFilterVO.setTotalRecords(-1);
		}else{
			handoverFilterVO.setTotalRecords(mailPerformanceSession.getTotalRecords());
		}*/
			
		Page<MailHandoverVO> mailHandoverVOs = null;
		
		if(mailPerformanceForm.getHoPaCode() == null || ("").equals(mailPerformanceForm.getHoPaCode())){
			log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceSession.setMailHandoverVOs(mailHandoverVOs);
	    	mailPerformanceForm.setScreenFlag("hoRadiobtn");
	    	mailPerformanceForm.setStatusFlag("List_failure");
	    	invocationContext.target = FAILURE;
	    	
	    	return;
			
		}
		
		try {
			
			mailHandoverVOs = mailTrackingDefaultsDelegate.
					findMailHandoverDetails(handoverFilterVO,displayPage);
			
			if(mailHandoverVOs == null ||  mailHandoverVOs.size()== 0){
			ErrorVO error = new ErrorVO(NO_RESULTS_FOUND,new Object[]{""});
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			mailPerformanceForm.setScreenFlag("hoRadiobtn");
			mailPerformanceForm.setStatusFlag("List_failure_NoRecords"); //Modified by A-8399 as part of ICRD-293432
			invocationContext.target = FAILURE; 
			
			return;		
		}
			
		}catch(BusinessDelegateException businessDelegateException) {
	    	
	    	errors = handleDelegateException(businessDelegateException);
	    }
		
		if(mailHandoverVOs != null && mailHandoverVOs.size()>0){
			//Added by A-8527 for ICRD-352718 starts
			for (MailHandoverVO mailho:mailHandoverVOs){
				if(mailho.getHandoverTimes()!=null && mailho.getHandoverTimes().trim().length()>0){
					String convertedval= new String();
					 int index=2;
					String timevalue=mailho.getHandoverTimes();
					for (int i = 0; i < timevalue.length(); i++) {
						 if (i == index) { 
				            	convertedval += ":"; 
				            } 
						convertedval += timevalue.charAt(i); 
				        } 
					mailho.setHandoverTimes(convertedval);
				}
			}
			//Added by A-8527 for ICRD-352718 Ends
			mailPerformanceSession.setTotalRecords(mailHandoverVOs.size());
		}
		
		mailPerformanceForm.setHandoverPage(mailHandoverVOs);
		mailPerformanceSession.setMailHandoverVOs(mailHandoverVOs);
		
	    mailPerformanceForm.setScreenFlag("hoRadiobtn");
	    mailPerformanceForm.setStatusFlag("List_success");
	    ErrorVO error = new ErrorVO("mailtracking.defaults.ux.mailperformance.msg.info.savesuccess");
	    
		errors.add(error);
		 mailPerformanceForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;
	}
}
