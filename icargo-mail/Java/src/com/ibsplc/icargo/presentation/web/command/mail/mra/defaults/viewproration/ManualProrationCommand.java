/*
 * ManualProrationCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm;

/**
 * @author A-3251
 *
 */
public class ManualProrationCommand extends BaseCommand {
	

	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String MANUAL_PRORATION_SUCCESS = "manual_proration_success";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		// TODO Auto-generated method stub
		
		MRAViewProrationSession mRAViewProrationSession = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);
		ManualProrationSession manualProrationSession = (ManualProrationSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		
		
		MRAViewProrationForm form=(MRAViewProrationForm)invocationContext.screenModel;
		
		ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
		
		prorationFilterVO = mRAViewProrationSession.getProrationFilterVO();
		
		manualProrationSession.setProrationFilterVO(prorationFilterVO);

		invocationContext.target=MANUAL_PRORATION_SUCCESS;
		
		
		
	}

}