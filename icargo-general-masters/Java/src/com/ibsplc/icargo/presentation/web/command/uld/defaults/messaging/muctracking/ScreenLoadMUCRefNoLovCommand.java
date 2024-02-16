/*
 * ScreenLoadMUCRefNoLovCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ScreenLoadMUCRefNoLovCommand extends BaseCommand {
	
private Log log = LogFactory.getLogger("MUC Ref No Lov");
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.messaging.muctracking";
	private static final String SCREENLOAD_SUCCESS = "lovScreenload_success";
	private static final String SCREENLOAD_FAILURE = "lovScreenload_failure";
    
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MUCTrackingForm mucTrackingForm = (MUCTrackingForm) invocationContext.screenModel;
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE,
				SCREENID);
		Collection<ErrorVO> errors = null;
		Page<String> lovVOs = new Page<String>(
				new ArrayList<String>(), 0, 0, 0, 0, 0, false);		
		try {
			String mucRefNum = mucTrackingForm.getMucReferenceNum() != null 
										? mucTrackingForm.getMucReferenceNum().toUpperCase() : "";
			log.log(Log.FINE, "mucRefNum------------>>>>>>", mucRefNum);
			log.log(Log.FINE, "Display Page------------>>>>>>", mucTrackingForm.getDisplayLovPage());
			String mucDate = null;
			if(mucTrackingForm.getMucLovFilterDate() != null) {
				mucDate = mucTrackingForm.getMucLovFilterDate();
			}
			log.log(Log.FINE, "mucDate------------>>>>>>", mucDate);
			log.log(Log.FINE, "CompanyCode------------>>>>>>", logonAttributes.getCompanyCode());
			lovVOs = new ULDDefaultsDelegate().findMUCRefNumberLov(
					logonAttributes.getCompanyCode(),Integer.parseInt
							(mucTrackingForm.getDisplayLovPage()),mucRefNum,mucDate);			
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}		
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
		}
		log.log(Log.FINE,
				"lovVOs before insert into session------------>>>>>>", lovVOs);
		if(lovVOs != null && lovVOs.size()> 0) {
			mucTrackingSession.setLovVO(lovVOs);
		}
		else {
			mucTrackingSession.setLovVO(null);
			ErrorVO error = new ErrorVO("uld.defaults.listuld.norecordsfound");
	     	error.setErrorDisplayType(ErrorDisplayType.STATUS);
	     	errors = new ArrayList<ErrorVO>();
	     	errors.add(error);
	     	invocationContext.addAllError(errors);
		}		
		log.log(Log.FINE,
				"lovVOs before insert into session------------>>>>>>",
				mucTrackingSession.getLovVO());
		invocationContext.target = SCREENLOAD_SUCCESS;
    }
}

