/*
 * ScreenloadULDAuditEnquiryCommand.java Created on 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDAuditEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2883
 */

public class ScreenloadULDAuditEnquiryCommand extends BaseCommand{
	
	private static final String MODULE_NAME = "shared.audit";
	private static final String SCREEN_ID = "shared.audit.auditenquiry";
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.generatescm";	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";
	private static final String BLANK = "";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	   /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.log(Log.FINE,"\n -*--*-*-*-*****");
		 
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			GenerateSCMSession generateSCMSession = 
				(GenerateSCMSession)getScreenSession(MODULE,SCREENID);
			
			Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes.getCompanyCode());
			ArrayList<OneTimeVO> facilityTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);
			generateSCMSession.setFacilityType(facilityTypes);
			AuditEnquirySession auditEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
			auditEnquirySession.removeAllAttributes();
			ULDAuditEnquiryForm uldAuditEnquiryForm = 
				(ULDAuditEnquiryForm) invocationContext.screenModel;
			uldAuditEnquiryForm.setUldNumber(BLANK);
			invocationContext.target = SCREENLOAD_SUCCESS;
			
	 }
	 
	 /**
	  * Fetching oneTime values
	  * @author A-2667
	  * @param companyCode
	  * @return
	  */
	 private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
				String companyCode) {
			Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(FACILITY_TYPE);
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
						oneTimeList);

			} catch (BusinessDelegateException exception) {
				exception.getMessage();
				errors = handleDelegateException(exception);
			}
			return hashMap;
		}

}
