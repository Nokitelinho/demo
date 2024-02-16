/*
 * ProcessMailCommand.java Created on Mar 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2408
 *
 */
public class ProcessMailCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ExceptionCommand");

	private static final String CLASS_NAME = "ExceptionCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirlineCN66DetailsFilterVO filtervo=null;
		
		filtervo=session.getCn66FilterVO();
		if(filtervo!=null){
		filtervo.setLastUpdatedUser(logonAttributes.getUserId());
		log.log(Log.INFO, "filter vo----->", filtervo);
		form.setFromScreenStatus("processed");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{
			new MailTrackingMRADelegate().processMail(filtervo);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors=handleDelegateException(businessDelegateException);
		}
	
		if(errors.size()==0){
			ErrorVO errorvo=new ErrorVO("mailtracking.mra.airlinebilling.defaults.msg.info.proceduresuccess");
			errors.add(errorvo);
		}
		
		invocationContext.addAllError(errors);
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
}
