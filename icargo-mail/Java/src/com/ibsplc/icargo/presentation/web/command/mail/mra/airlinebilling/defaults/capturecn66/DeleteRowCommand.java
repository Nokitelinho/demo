
/*
 *  DeleteRowCommand.java Created on Dec-10-2008 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.s
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DeleteRowCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "DeleteRowCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	
	private static final String ACTION_SUCCESS = "action_success";
	
	/**
	 * *Dec-10,a-3434
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * Execute Method for deleting a row in c66Details popup
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, MODULE_NAME);
		
	CaptureCN66Session captureCN66Session = (CaptureCN66Session) getScreenSession(
			MODULE_NAME, SCREEN_ID);
	CN66DetailsForm cN66DetailsForm = (CN66DetailsForm) invocationContext.screenModel;
	if(captureCN66Session.getCn66Details()!=null){
	Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs = captureCN66Session
	.getCn66Details();
	ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOArraylist = new ArrayList<AirlineCN66DetailsVO>(
			airlineCN66DetailsVOs);
	
	AirlineCN66DetailsVO airlineCN66DetailsVO;
	Integer count=cN66DetailsForm.getCount();	
	log.log(Log.FINE, "Inside DeleteRowCommand  count... >>", count);
	airlineCN66DetailsVO= airlineCN66DetailsVOArraylist.get(count);
	log.log(Log.FINE, "Inside DeleteRowCommand  airlineCN66DetailsVO... >>",
			airlineCN66DetailsVO);
	airlineCN66DetailsVO.setOperationFlag("D");
	captureCN66Session.removeAirlineCN66DetailsVO();
	airlineCN66DetailsVOs.remove(airlineCN66DetailsVO);
	log.log(Log.FINE, "Inside DeleteRowCommand  airlineCN66DetailsVOs... >>",
			airlineCN66DetailsVOs);
	}
	invocationContext.target = ACTION_SUCCESS;
	}
	
}
