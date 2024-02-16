/**
 *  ReassignCommand.java Created on January 16, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReassignDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ReassignCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.national.reassign";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String REASSIGN = "REASSIGN";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationContext.screenModel;
		ReassignDispatchSession reassignSession  = getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
		MailManifestVO mailManifestVO = assignMailBagSession.getMailManifestVO();		
		String [] selectedRows  = assignMailBagForm.getRowId();
		Collection<DSNVO> dsnVOSToReassign = new ArrayList<DSNVO>();
		ContainerDetailsVO containerDetailsVO = mailManifestVO.getContainerDetails().iterator().next();
		List<DSNVO> dsnList = (List<DSNVO>)mailManifestVO.getContainerDetails().iterator().next().getDsnVOs();
		DSNVO dsnvo = null;
		if(selectedRows != null && selectedRows.length >0){
			for(String row : selectedRows){
				if(row != null && row.trim().length() >0){
					dsnvo = dsnList.get(Integer.parseInt(row));
					dsnvo.setFlightNumber(containerDetailsVO.getFlightNumber());
					dsnvo.setCarrierId(containerDetailsVO.getCarrierId());
					dsnvo.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
					dsnvo.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
					dsnvo.setPou(containerDetailsVO.getPou().toUpperCase());
					dsnvo.setPol(containerDetailsVO.getPol().toUpperCase());

					dsnVOSToReassign.add(dsnList.get(Integer.parseInt(row)));
				}

			}

			reassignSession.setSelectedDSNVO(dsnVOSToReassign);

		}
		assignMailBagForm.setPopupFlag(REASSIGN);		
		log.log(Log.FINE, "getPopupFlag...", assignMailBagForm.getPopupFlag());
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

}
