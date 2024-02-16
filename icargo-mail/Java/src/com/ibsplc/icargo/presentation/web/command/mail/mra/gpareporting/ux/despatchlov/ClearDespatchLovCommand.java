/**
 * ClearDSNCommand.java Created on May 25-2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms. 
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.despatchlov;


import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.DespatchLOVForm;

/**
 * @author a-4823
 *
 */
public class ClearDespatchLovCommand extends BaseCommand{
	private static final String CLASS_NAME = "ClearDespatchLovCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpareporting";

	private static final String SCREEN_ID = "mail.mra.gpareporting.ux";

	private static final String SCREEN_SUCCESS = "screenload_success";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		DespatchLOVForm dsnLovForm = (DespatchLOVForm) invocationContext.screenModel;		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		dsnLovForm.setDsnLovPage(null);
		dsnLovForm.setCategory(null);
		dsnLovForm.setCondocno(null);
		dsnLovForm.setDestination(null);
		dsnLovForm.setDsnNumber(null);
		dsnLovForm.setFromDate(null);
		dsnLovForm.setOrigin(null);
		dsnLovForm.setSubclass(null);
		dsnLovForm.setYear(null);
		dsnLovForm.setToDate(null);
		dsnLovForm.setLovDescriptionTxtFieldName("");
		dsnLovForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		   dsnLovForm.setRecepatableSerialNumber(null);
		dsnLovForm.setHighestNumberIndicator(null);
		dsnLovForm.setRegisteredIndicator(null);
		invocationContext.target=SCREEN_SUCCESS;
		
	}

}

