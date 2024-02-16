/*
 * AddFlightDetailsCommand.java Created on Feb 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Feb 27, 2007			a-2257		Created
 */
public class AddFlightDetailsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "AddFlightDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		CaptureGPAReportSession session =
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);		
		
		GPAReportingDetailsVO gpaReportingDetailsVO = session.getSelectedGPAReportingDetailsVO();

		GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO= new GPAReportingFlightDetailsVO();
		
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			
			log.log(Log.FINE,"Inside errors");	
			invocationContext.target = ACTION_SUCCESS;		
			log.exiting(CLASS_NAME, "execute");			
			return;
		}

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		gpaReportingFlightDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		gpaReportingFlightDetailsVO.setSequenceNumber(0);
		gpaReportingFlightDetailsVO.setPoaCode(gpaReportingDetailsVO.getPoaCode());
		gpaReportingFlightDetailsVO.setOperationFlag(GPAReportingFlightDetailsVO.OPERATION_FLAG_INSERT);
		gpaReportingFlightDetailsVO.setCarriageFrom("");
		gpaReportingFlightDetailsVO.setCarriageTo("");
		gpaReportingFlightDetailsVO.setFlightCarrierCode("");
		gpaReportingFlightDetailsVO.setFlightNumber("");
		gpaReportingFlightDetailsVO.setFlightSequenceNumber(0);
		gpaReportingFlightDetailsVO.setReportingFrom(gpaReportingDetailsVO.getReportingFrom());
		gpaReportingFlightDetailsVO.setReportingTo(gpaReportingDetailsVO.getReportingTo());
		gpaReportingFlightDetailsVO.setBillingBasis(gpaReportingDetailsVO.getBillingBasis());

		if(gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs()!=null
				&& gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs().size()>0){

			log.log(Log.FINE, "getGpaReportingFlightDetailsVOs is not null==");
			gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs().add(gpaReportingFlightDetailsVO);
		}else{

			log.log(Log.FINE, "getGpaReportingFlightDetailsVOs is null==");

			Collection<GPAReportingFlightDetailsVO> gpaReportingFlightDetailsVOs
				= new ArrayList<GPAReportingFlightDetailsVO>();

			gpaReportingFlightDetailsVOs.add(gpaReportingFlightDetailsVO);

			gpaReportingDetailsVO.setGpaReportingFlightDetailsVOs(gpaReportingFlightDetailsVOs);
		}

		invocationContext.target =ACTION_SUCCESS;   
		log.exiting(CLASS_NAME, "execute");

	}

}
