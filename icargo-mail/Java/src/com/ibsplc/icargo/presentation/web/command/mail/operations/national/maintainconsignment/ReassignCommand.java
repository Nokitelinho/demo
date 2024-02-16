/**
 *  ReassignCommand.java Created on June 20, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReassignDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4810
 * This command class is created to implement reassign functionlity for flights under acceptancedetailsscection in
 * capturemaildocumentscreen.
 *
 */
public class ReassignCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.national.reassign";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String REASSIGN = "REASSIGN";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
		
		 ReassignDispatchSession reassignSession  = getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
		 Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		 String [] selectedRows  = consignmentForm.getSelectAcceptance();
		 Collection<RoutingInConsignmentVO> acceptancevos = consignmentDocumentVO.getAcceptanceInfo();
		RoutingInConsignmentVO routingvo = null;
		ArrayList<RoutingInConsignmentVO> routingList = (ArrayList<RoutingInConsignmentVO>)acceptancevos;
		Collection<RoutingInConsignmentVO> newacceptancevos = new ArrayList<RoutingInConsignmentVO>();
			
		
		if(selectedRows != null && selectedRows.length >0){
			for(String row : selectedRows){
				if(row != null && row.trim().length() >0){
					routingvo = routingList.get(Integer.parseInt(row));
					newacceptancevos.add(routingvo);
					
				}

			}
		validateConsignmentDetailsForClosedFlight(newacceptancevos, errorVOs);
		if(errorVOs != null && errorVOs.size() >0){
			invocationContext.addAllError(errorVOs);
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;
			
		}

			
			reassignSession.setRoutingInConsignmentVOs(newacceptancevos);
			reassignSession.setConsignmentDocumentVO(consignmentDocumentVO);

		}
		consignmentForm.setPopupFlag(REASSIGN);		
		log.log(Log.FINE, "getPopupFlag...", consignmentForm.getPopupFlag());
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
	
	private void validateConsignmentDetailsForClosedFlight(Collection<RoutingInConsignmentVO> newacceptancevos,Collection<ErrorVO> errorVOs){
		
			for(RoutingInConsignmentVO routingInConsignmentVO : newacceptancevos){
				if(routingInConsignmentVO.isFlightClosed()){
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.flightClosedCannotReassign");
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);

				}

			}

		
	}

}
