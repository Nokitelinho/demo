/*
 * ClearProrationCommand.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm;


/**
 * @author A-2554
 *
 */
public class ClearProrationCommand extends BaseCommand {
	

	private static final String SCREENID_MAILPRO ="mailtracking.mra.defaults.mailproration";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		// TODO Auto-generated method stub
		
		MRAViewProrationSession session = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);
		session.setProrationVOs(null);
		session.setPrimaryProrationVOs(null);
		session.setSecondaryProrationVOs(null);
		session.setOneTimeVOs(null);
		session.setSurchargeProrationVOs(null);
		session.setProrationFilterVO(null);
		MRAViewProrationForm form=(MRAViewProrationForm)invocationContext.screenModel;
		form.setDispatch("");
		form.setConDocNo("");
		form.setCarrierCode("");
		form.setFlightNo("");
		form.setFlightDate("");
		form.setOrigin("");
		form.setDest("");
		form.setGpa("");
		form.setGpaName("");
		form.setCategory("");
		form.setTotWt("");
		form.setCategory("");
		form.setFromScreen("");
		form.setSubClass("");
		form.setRsn("");
		form.setDsn("");
		form.setFormStatusFlag("");
		
		form.setTotalInUsdForPri("");
		form.setTotalInBasForPri("");
		form.setTotalInSdrForPri("");
		form.setTotalInCurForPri("");
		form.setTotalInUsdForSec("");
		form.setTotalInBasForSec("");
		form.setTotalInSdrForSec("");
		form.setTotalInCurForSec("");
		form.setTotalSurchgInBasForSec("");
		form.setTotalSurchgInBasForPri("");
		form.setTotalSurchgInUsdForPri("");//added by A-8464 for ICRD-278833
		form.setTotalSurchgInBasForPri("");
		form.setTotalSurchgInSdrForPri("");
		form.setTotalSurchgInCurForPri("");
		form.setTotalSurchgInUsdForSec("");
		form.setTotalSurchgInBasForSec("");
		form.setTotalSurchgInSdrForSec("");

		invocationContext.target=CLEAR_SUCCESS;
		
		
		
	}

}
