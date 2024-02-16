/*
 * ScreenLoadCommand.java Created on Apr02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermailmanifest;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3353
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	 private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * declaring
	    * TARGET , MODULE & SCREENID
	    */
	   private static final String TARGET = "screenload_success";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
		public static final String MAIL_OPERATIONS_TRANSFER_TRANSACTION="mail.operation.transferoutinonetransaction";
	   /**
	    * @param invocationContext
	    * @throws CommandInvocationException
	    */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("Screenloadtransfermanifest","execute");
		TransferMailManifestForm transferMailManifest = (TransferMailManifestForm)invocationContext.screenModel;
		invocationContext.target = TARGET;
		TransferMailManifestSession transferMailManifestSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Boolean transfer = false;
	    Map<String, String> systemParameters = null;
	    SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add(MAIL_OPERATIONS_TRANSFER_TRANSACTION);
		     systemParameters = findSystemParameterValues(systemParameters, sharedDefaultsDelegate, parameterTypes);
			 if(systemParameters!=null && systemParameters.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION)!=null &&
					 !systemParameters.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION).isEmpty()){
				 if("Y".equals(systemParameters.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION))){
						transfer=true;
					}
					else{
						transfer=false;		 		
	   		 }
	   		 }
			transferMailManifest.setTransferParameter(transfer);
		
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		transferMailManifest.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		transferMailManifest.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		transferMailManifestSession.setTransferManifestVOs(null);
    	log.exiting("ScreenloadMailHandedOverReport","execute");
		
		
	}
	private Map<String, String> findSystemParameterValues(Map<String, String> systemParameters,
			SharedDefaultsDelegate sharedDefaultsDelegate, Collection<String> parameterTypes) {
		try{
		     systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
		 }catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
				}
		return systemParameters;
	}
	

}
