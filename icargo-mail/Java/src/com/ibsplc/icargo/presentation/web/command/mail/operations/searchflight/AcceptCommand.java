/*
 * AcceptCommand.java Created on JULY 24, 2009
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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3817
 * 
 */
public class AcceptCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	private static final String SCREEN_ID = "mailtracking.defaults.flightreconcilation";

	private static final String MODULE_NAME = "mail.operations";

	private static final String ACCEPT_SUCCESS = "accept_success";
	private static final String ACCEPT_FAILURE = "accept_failure";

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		FlightReconciliationForm flightReconciliationForm = (FlightReconciliationForm) invocationContext.screenModel;
		FlightReconciliationSession flightReconciliationSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		MailReconciliationVO mailReconciliationVO = null;
		ArrayList<MailReconciliationVO> mailReconciliationVOs = (ArrayList<MailReconciliationVO>) flightReconciliationSession
		.getMailReconciliationVOs();
		OperationalFlightVO operationalFlightVO = flightReconciliationSession
		.getOperationalFlightVO();
		
		boolean infoflag = true;
		boolean acceptListflag=true;
		Collection<ErrorVO> errorVOs = null;
		ErrorVO errorVO = null;

		if (mailReconciliationVOs != null && mailReconciliationVOs.size() > 0) {
			String[] selectedRows = flightReconciliationForm.getSelectedRow()
			.split("-");
			for (int i = 0; i < selectedRows.length; i++) {
				// mailReconciliationVO=mailReconciliationVOs.get(Integer.parseInt(selectedRows[i]));
				mailReconciliationVO = mailReconciliationVOs.get(Integer
						.parseInt(selectedRows[i]));
				mailReconciliationVO
				.setMailStatus(MailConstantsVO.EXC_ACCEPTED);
				// acceptedMailVOs.add(mailReconciliationVO);
			}
			try {
				new MailTrackingDefaultsDelegate()
				.acceptMailsForReconciliation(mailReconciliationVOs,
						operationalFlightVO);
				
			} catch (BusinessDelegateException businessDelegateException) {
				infoflag = false;
			}
			
		}
		if (infoflag) {
			           for(MailReconciliationVO reconciliationVO:mailReconciliationVOs){
			        	   if(!MailConstantsVO.EXC_ACCEPTED.equals(reconciliationVO.getMailStatus())) {
			        		   acceptListflag=false;
							}
			           }
			           
			           if(acceptListflag){
			        
						 if((MailConstantsVO.OPERATION_OUTBOUND.equals(flightReconciliationForm.getFromScreen())) && (!MailConstantsVO.EXC_RECONCILE.equals(operationalFlightVO.getMailStatus()))){
								try{
									operationalFlightVO=new MailTrackingDefaultsDelegate().updateOperationalFlightVO(operationalFlightVO);
									flightReconciliationSession.setOperationalFlightVO(operationalFlightVO);
								}
								catch (BusinessDelegateException businessDelegateException) {
									log.log(Log.FINE,  "BusinessDelegateException");
								}
						 }
			           } 
						 
			 flightReconciliationForm.setListflag("ON");
//			 if(mailReconciliationVOs!=null &&mailReconciliationVOs.size()>0){
//					 for(MailReconciliationVO reconciliationVO:mailReconciliationVOs){
//						if(!MailConstantsVO.EXC_ACCEPTED.equals(reconciliationVO.getMailStatus())) {
//							acceptListflag=false;
//						}
//					 }
//			 }
			errorVO = new ErrorVO(
			"mailtracking.defaults.flightreconciliation.accept");
			errorVOs = new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			//invocationContext.addAllError(errorVOs);
			invocationContext.target = ACCEPT_SUCCESS;
			return;
		}
		
		invocationContext.target = ACCEPT_FAILURE;
		
	}

}
