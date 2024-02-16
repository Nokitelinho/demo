/**
 * OffloadCommand.java Created on January 12, 2012
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.OffloadDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class OffloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String SCREEN_ID_OFFLOAD = "mailtracking.defaults.national.offload";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String OFFLOAD = "OFFLOAD";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 *  
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationcontext.screenModel;
		OffloadDispatchSession offloadSession  = getScreenSession(MODULE_NAME, SCREEN_ID_OFFLOAD);

		MailManifestVO mailManifestVO = assignMailBagSession.getMailManifestVO();		
		String [] selectedRows  = assignMailBagForm.getRowId();
		Collection<DSNVO> dsnVOSToOffload = new ArrayList<DSNVO>();
		List<DSNVO> dsnList = (List<DSNVO>)mailManifestVO.getContainerDetails().iterator().next().getDsnVOs();
		ContainerDetailsVO containerDetailsVO = mailManifestVO.getContainerDetails().iterator().next();
		DSNVO dsnvo = null;
		if(selectedRows != null && selectedRows.length >0){
			for(String row : selectedRows){
				if(row != null && row.trim().length() >0){
					dsnvo = dsnList.get(Integer.parseInt(row));
					dsnvo.setFlightNumber(containerDetailsVO.getFlightNumber());
					dsnvo.setCarrierId(containerDetailsVO.getCarrierId());
					dsnvo.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
					dsnvo.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
					dsnvo.setPou(containerDetailsVO.getPou());
					dsnvo.setPol(containerDetailsVO.getPol());

					dsnVOSToOffload.add(dsnList.get(Integer.parseInt(row)));
				}

			}
			offloadSession.setSelectedDSNVO(dsnVOSToOffload);

		}
		assignMailBagForm.setPopupFlag(OFFLOAD);		
		log.log(Log.FINE, "getPopupFlag...", assignMailBagForm.getPopupFlag());
		invocationcontext.target = SCREENLOAD_SUCCESS;
	}
}
