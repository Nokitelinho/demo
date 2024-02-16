/*
 * FinaliseFlightCommand.java Created on JULY 24, 2009
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
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-3817
 *
 */
public class FinaliseFlightCommand extends BaseCommand{
	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String MODULENAME = "mail.operations";
	private static final String FINALISE_SUCCESS="finalise_success";
	 private static final String FINALISE_FAILURE="finalise_failure";
	 private static final String BLANK=" ";
	 private static final String FINALISEMESSAGE=" is finalised ";

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		SearchFlightForm searchFlightForm=(SearchFlightForm)invocationContext.screenModel;
		SearchFlightSession searchFlightSession=(SearchFlightSession)getScreenSession(MODULENAME, SCREENID);
		ErrorVO errorVO=null;
		Collection<ErrorVO>errorVOs=null;
		boolean finaliseFlag=true;
		StringBuilder finaliseMessage=null;
		OperationalFlightVO operationalFlightVO=null;
		String[] selectedRow=searchFlightForm.getSelectedElements();
		int row=Integer.parseInt(selectedRow[0]);
		operationalFlightVO=searchFlightSession.getOperationalFlightVOs().get(row);
		operationalFlightVO.setDirection(searchFlightForm.getFromScreen());
		if(MailConstantsVO.EXC_RECONCILE.equals(operationalFlightVO.getMailStatus())){
			try{
			new MailTrackingDefaultsDelegate().finaliseMailsForReconciliation(operationalFlightVO) ;
			}
			catch(BusinessDelegateException businessDelegateException){
				finaliseFlag=false;
			}
			if(finaliseFlag){
				finaliseMessage=new StringBuilder("BLANK").append(operationalFlightVO.getCarrierCode()).append(BLANK).append(operationalFlightVO.getFlightNumber()).append(FINALISEMESSAGE);
				errorVO=new ErrorVO("mailtracking.defaults.searchflight.flightfinalised",new Object[]{finaliseMessage.toString()});
				errorVOs=new ArrayList<ErrorVO>();
				errorVOs.add(errorVO);
				invocationContext.addAllError(errorVOs);
			}
			invocationContext.target= FINALISE_SUCCESS;
			return;
		}
		else{
			 errorVO=new ErrorVO("mailtracking.defaults.searchflight.notfinalised");
			errorVOs=new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			invocationContext.addAllError(errorVOs);
			invocationContext.target=FINALISE_FAILURE;
		}
		
		
	}

}
