/*
 * FinaliseCommand.java Created on JULY 24, 2009
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
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.FlightReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.FlightReconciliationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-3817
 *
 */
public class FinaliseCommand extends BaseCommand {
	private static final String SCREEN_ID = "mailtracking.defaults.flightreconcilation";

	private static final String MODULE_NAME = "mail.operations";
	private static final String FINALISE_SUCCESS = "finalise_success";
	private static final String FINALISE_FAILURE = "finalise_failure";
	private static final String BNK = " ";
	/**
	 * 
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		FlightReconciliationForm flightReconciliationForm=(FlightReconciliationForm)invocationContext.screenModel;
		FlightReconciliationSession flightReconciliationSession=getScreenSession(MODULE_NAME, SCREEN_ID);
		ArrayList<MailReconciliationVO>mailReconciliationVOs=(ArrayList<MailReconciliationVO>)flightReconciliationSession.getMailReconciliationVOs();
		OperationalFlightVO operationalFlightVO=flightReconciliationSession.getOperationalFlightVO();
		Collection<ErrorVO>errorVOs=null;
		ErrorVO errorVO=null;
		boolean infoFlag=true;
		if(mailReconciliationVOs!=null && mailReconciliationVOs.size()>0){
				for (MailReconciliationVO mailReconciliationVO : mailReconciliationVOs) {
					if(!MailConstantsVO.EXC_ACCEPTED.equals(mailReconciliationVO.getMailStatus())){
						errorVO=new ErrorVO("mailtracking.defaults.flightreconciliation.notfinalise");
						errorVOs=new ArrayList<ErrorVO>();
						errorVOs.add(errorVO);
						invocationContext.addAllError(errorVOs);
						invocationContext.target=FINALISE_FAILURE;
						return;
					}
				}
		}		
		String flag=MailConstantsVO.EXC_FINALISE;
		Collection<ErrorVO> errors = null;
		try{
			new MailTrackingDefaultsDelegate().finaliseMailsForReconciliation(operationalFlightVO);
		
		}catch (BusinessDelegateException  businessDelegateException) {
			infoFlag=false;
			errors = handleDelegateException(businessDelegateException);
		}
		if(infoFlag){
			flightReconciliationForm.setFinaliseBtnFlag("OFF");
			StringBuilder flightInfo = new StringBuilder().append(operationalFlightVO.getCarrierCode())
				.append(BNK).append(operationalFlightVO.getFlightNumber());
			/*errorVO=new ErrorVO("mailtracking.defaults.flightreconciliation.finalise");
			errorVOs=new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			invocationContext.addAllError(errorVOs);*/
			/*invocationContext.addError(new ErrorVO("mailtracking.defaults.flightreconciliation.finalise",
	   				new Object[]{flightInfo.toString()}));*/
			flightReconciliationForm.setFlightInfo(flightInfo.toString());
			flightReconciliationForm.setCloseFlag("mailtracking.defaults.flightreconciliation.finalise");
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target=FINALISE_SUCCESS;
			return;
		}
		flightReconciliationForm.setFinaliseBtnFlag("OFF");
		invocationContext.target=FINALISE_SUCCESS;
		
	}

}
