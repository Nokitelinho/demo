/*
 * ReconcileFlightCommand.java Created on JULY 24, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-3817
 *
 */
public class ReconcileFlightCommand extends BaseCommand {
	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String MODULENAME = "mail.operations";
	
	 private static final String RECONCILE_SUCCESS="reconcile_success";
	 private static final String RECONCILE_FAILURE="reconcile_failure";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		SearchFlightForm searchFlightForm=(SearchFlightForm)invocationContext.screenModel;
		SearchFlightSession searchFlightSession=(SearchFlightSession)getScreenSession(MODULENAME, SCREENID);
		String selectedRow=searchFlightForm.getSelectedRow();
		Collection<ErrorVO>errorVOs=null;
		ErrorVO errorVO=null;
		OperationalFlightVO operationalFlightVO=(OperationalFlightVO)(searchFlightSession.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));
		if(MailConstantsVO.EXC_FINALISE.equals(operationalFlightVO.getMailStatus())){
			errorVO=new ErrorVO("mailtracking.defaults.searchflight.flightnotfinalised");
			errorVOs=new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			searchFlightForm.setFinaliseFlag("OFF");
			invocationContext.addAllError(errorVOs);
			invocationContext.target=RECONCILE_FAILURE;
			return;
		}
		if(MailConstantsVO.FLIGHT_STATUS_OPEN.equals(operationalFlightVO.getMailStatus())){
			errorVO=new ErrorVO("mailtracking.defaults.searchflight.flightopened");
			errorVOs=new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			searchFlightForm.setFinaliseFlag("OFF");
			invocationContext.addAllError(errorVOs);
			invocationContext.target=RECONCILE_FAILURE;
			return;
		}
		searchFlightForm.setFinaliseFlag("ON");
		if(MailConstantsVO.OPERATION_INBOUND.equals(searchFlightForm.getFromScreen())){
			searchFlightForm.setPort(searchFlightForm.getArrivalPort());
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(searchFlightForm.getFromScreen())){
			searchFlightForm.setPort(searchFlightForm.getDepartingPort());
		}
		invocationContext.target=RECONCILE_SUCCESS;
	}

}
