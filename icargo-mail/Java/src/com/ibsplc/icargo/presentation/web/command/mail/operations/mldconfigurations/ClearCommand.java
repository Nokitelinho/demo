/*
 * ClearCommand Created on DEC 17, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mldconfigurations;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MLDConfigurationSession;

import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MLDConfigurationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5526
 * 
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS = "clear_success";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mldconfiguration";

	/**
	 * @author A-5526 This method overrides the executre method of BaseComand
	 *         class
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ListCommand", "execute");

		MLDConfigurationForm mLDConfigurationForm = (MLDConfigurationForm) invocationContext.screenModel;
		MLDConfigurationSession mLDConfigurationSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		mLDConfigurationForm.setAirport("");
		mLDConfigurationForm.setCarrier("");
		mLDConfigurationForm.setAirportCode(null);
		mLDConfigurationForm.setAllocatedRequired(null);
		mLDConfigurationForm.setCarrierCode(null);
		mLDConfigurationForm.setDeliveredRequired(null);
		mLDConfigurationForm.setHndRequired(null);
		mLDConfigurationForm.setReceivedRequired(null);
		mLDConfigurationForm.setRowId(null);
		mLDConfigurationForm.setOperationFlag(null);
		mLDConfigurationForm.setUpliftedRequired(null);
		mLDConfigurationForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mLDConfigurationSession.setMLDConfigurationVOs(null);
		
		
		/*  private String mldversion;
		  private String[] stagedRequired;
		  private String[] nestedRequired;
		  private String[] receivedFromFightRequired;
		  private String[] transferredFromOALRequired;
		  private String[] receivedFromOALRequired;
		  private String[] returnedRequired;
	
		   private String listFlag;*/
		   
		//Added for CRQ ICRD-135130 by A-8061 starts
		mLDConfigurationForm.setMldversion(null);
		mLDConfigurationForm.setStagedRequired(null);
		mLDConfigurationForm.setNestedRequired(null);
		mLDConfigurationForm.setReceivedFromFightRequired(null);
		mLDConfigurationForm.setTransferredFromOALRequired(null);
		mLDConfigurationForm.setReceivedFromOALRequired(null);
		mLDConfigurationForm.setReturnedRequired(null);
		mLDConfigurationForm.setListFlag(null);
		//Added for CRQ ICRD-135130 by A-8061 end

		invocationContext.target = TARGET_SUCCESS;

		log.exiting("ClearCommand", "execute");

	}

}
