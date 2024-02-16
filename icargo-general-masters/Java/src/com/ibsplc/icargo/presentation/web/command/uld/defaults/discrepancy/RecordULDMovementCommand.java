/*
 * RecordULDMovementCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2052
 * 
 */
public class RecordULDMovementCommand extends BaseCommand {

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private static final String NAVIGATE_SUCCESS = "navigate_success";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("NavigateCommand");
		log.entering("NavigateCommand", "Navigate Command");

		ListULDDiscrepanciesForm form = (ListULDDiscrepanciesForm) invocationContext.screenModel;
		ListUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		ArrayList<ULDDiscrepancyVO> uldDiscrepancyVOs = session
				.getULDDiscrepancyVOs();
		ULDDiscrepancyVO vo = new ULDDiscrepancyVO();
		if (uldDiscrepancyVOs != null) {
			vo = uldDiscrepancyVOs.get(Integer.parseInt(form
					.getUldDisplayPage()) - 1);
		}
		form.setCloseFlag(session.getPageURL());
		form.setPageURL(session.getPageURL());
		form.setNavigate(true);
		form.setSaveFlag(false);
		log.log(Log.FINE, "vo-------------->>>" + vo);
		if (vo != null) {
			log.log(Log.FINE, "vo not equal to null-------------->>>");
			form.setUldNoChild(vo.getUldNumber());
			form.setDiscrepancyCode(vo.getDiscrepencyCode());
			form.setDiscrepancyDate(vo.getDiscrepencyDate()
					.toDisplayDateOnlyFormat());
			form.setReportingStationChild(vo.getReportingStation());
			form.setRemarks(vo.getRemarks());
		}
		invocationContext.target = NAVIGATE_SUCCESS;
	}
}
