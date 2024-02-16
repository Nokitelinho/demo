/**
 * ScreenLoadCommand.java Created on January 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.reassign;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReassignDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ReassignDispatchForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ScreenLoadCommand  extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE_NAME = "mail.operations";
	private static final String ASG_MAIL_BAG = "ASG_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
	private static final String CAPTURE_MAIL_BAG = "CAPTURE_MAIL_BAG";
	private static final String SCREEN_ID = "mailtracking.defaults.national.reassign";
	private static final String SCREEN_ID_ASSIGN = "mailtracking.defaults.national.assignmailbag";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadReassignCommand", "execute");

		ReassignDispatchSession reassignSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		ReassignDispatchForm reassignDispatchForm = (ReassignDispatchForm)invocationContext.screenModel;
		AssignMailBagForm assignMailBagForm = new AssignMailBagForm();
		AssignMailBagSession assignMailBagSession = getScreenSession(MODULE_NAME, SCREEN_ID_ASSIGN);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String fromScreen =  reassignDispatchForm.getFromScreen();
		reassignDispatchForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());

		// assignMailBagForm.setDisableStatus("Y");
		if(ASG_MAIL_BAG.equals(fromScreen)){
			Collection<DSNVO> dsnvos = reassignSession.getSelectedDSNVO();
			for( DSNVO dsnvo: dsnvos){
				reassignDispatchForm.setPieces(String.valueOf(dsnvo.getBags()));
				reassignDispatchForm.setWeight(String.valueOf(dsnvo.getWeight()));
				//reassignDispatchForm.setRemarks(dsnvo.getRemarks());

			}
		}
		else if(DSN_ENQUIRY.equals(fromScreen)){
			Collection<DespatchDetailsVO> despatchDetailsVOs =  reassignSession.getDespatchDetailsVOs();
			for(DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs){
				reassignDispatchForm.setPieces(String.valueOf(despatchDetailsVO.getAcceptedBags()));
				reassignDispatchForm.setWeight(String.valueOf(despatchDetailsVO.getAcceptedWeight()));
				//reassignDispatchForm.setRemarks(despatchDetailsVO.getRemarks());				
			}
		}
		
		//Added as part of bug-fix -icrd-13564 by A-4810 to enable reassign from capture consignment document
		else if(CAPTURE_MAIL_BAG.equals(fromScreen)){
			Collection<RoutingInConsignmentVO> routingInConsignmentVOs =  reassignSession.getRoutingInConsignmentVOs();
			for(RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs){
				reassignDispatchForm.setPieces(String.valueOf(routingInConsignmentVO.getNoOfPieces()));
				reassignDispatchForm.setWeight(String.valueOf(routingInConsignmentVO.getWeight()));
				//reassignDispatchForm.setRemarks(routingInConsignmentVO.getRemarks());	
				
				
			}
		}
		reassignDispatchForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//	  	   assignMailBagSession.removeFilterVO(KEY_FILTER_ASSIGNMAILBAG);
		//	  	   assignMailBagSession.removeAssignMagVOS(ASSIGNMAILBAGVOS);

		reassignSession.setFlightValidationVO(null);

		invocationContext.target = SCREENLOAD_SUCCESS;	
		log.exiting("ScreenLoadCommand", "execute");


	}



}
