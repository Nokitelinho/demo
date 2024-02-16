/*
 * ScreenloadChangeInboundFlightCommand.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm;

/**
 * @author a-3817
 * 
 */
public class ScreenloadChangeInboundFlightCommand extends BaseCommand {
	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";

	private static final String SCREENLOAD_INBOUND_SUCCESS = "screenload_inbound_success";

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		UploadMailDetailsForm uploadMailDetailsForm = (UploadMailDetailsForm) invocationContext.screenModel;
		UploadMailDetailsSession uploadMailDetailsSession = (UploadMailDetailsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		String selectedRow = uploadMailDetailsForm.getSelectedScannedVOIndx();
		ScannedMailDetailsVO scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>) uploadMailDetailsSession
				.getScannedMailDetailsVOs()).get(Integer.parseInt(selectedRow));
		
		uploadMailDetailsForm.setSelectedProcessPoint(scannedMailDetailsVO.getProcessPoint());
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()) || 
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint()) ||
				MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()) ||
				MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())){
			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()) || 
					MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())){
				uploadMailDetailsForm.setExportPort(logonAttributes.getAirportCode());
				if(scannedMailDetailsVO.getFlightDate() != null){
					uploadMailDetailsForm.setOutboundFlightCarrierCode(scannedMailDetailsVO.getCarrierCode());
					uploadMailDetailsForm.setOutboundFlightNumber(scannedMailDetailsVO.getFlightNumber());
					uploadMailDetailsForm.setOutboundFlightDate(scannedMailDetailsVO.getFlightDate().toDisplayDateOnlyFormat());
				}else{
					uploadMailDetailsForm.setOutboundCarrierCode(scannedMailDetailsVO.getCarrierCode());
				}
				if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())){
					if(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
						uploadMailDetailsForm.setToBulkFlag(MailConstantsVO.FLAG_YES);
					}else if(MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())){
						uploadMailDetailsForm.setToBulkFlag(MailConstantsVO.FLAG_NO);
					}
				}
				uploadMailDetailsForm.setToContainer(scannedMailDetailsVO.getContainerNumber());
				uploadMailDetailsForm.setPou(scannedMailDetailsVO.getPou());
				uploadMailDetailsForm.setDest(scannedMailDetailsVO.getDestination());
			}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())){
				//Arrival Details
				uploadMailDetailsForm.setArrivalPort(logonAttributes.getAirportCode());
				uploadMailDetailsForm.setInboundCarrierCode(scannedMailDetailsVO.getCarrierCode());
				uploadMailDetailsForm.setInboundFlightNumber(scannedMailDetailsVO.getFlightNumber());
				if(scannedMailDetailsVO.getFlightDate() != null){
					uploadMailDetailsForm.setInboundFlightDate(scannedMailDetailsVO.getFlightDate().toDisplayDateOnlyFormat());
				}
				if(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
					uploadMailDetailsForm.setFromBulkFlag(MailConstantsVO.FLAG_YES);
				}else if(MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())){
					uploadMailDetailsForm.setFromBulkFlag(MailConstantsVO.FLAG_NO);
				}
				uploadMailDetailsForm.setFromContainer(scannedMailDetailsVO.getContainerNumber());
				uploadMailDetailsForm.setPol(scannedMailDetailsVO.getPol());

			}else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())){
				//Import Details For Transfer
				uploadMailDetailsForm.setArrivalPort(logonAttributes.getAirportCode());
				uploadMailDetailsForm.setInboundCarrierCode(scannedMailDetailsVO.getCarrierCode());
				uploadMailDetailsForm.setInboundFlightNumber(scannedMailDetailsVO.getFlightNumber());
				if(scannedMailDetailsVO.getFlightDate() != null){
					uploadMailDetailsForm.setInboundFlightDate(scannedMailDetailsVO.getFlightDate().toDisplayDateOnlyFormat());
				}
				if(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
					uploadMailDetailsForm.setFromBulkFlag(MailConstantsVO.FLAG_YES);
				}else if(MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())){
					uploadMailDetailsForm.setFromBulkFlag(MailConstantsVO.FLAG_NO);
				}
				uploadMailDetailsForm.setFromContainer(scannedMailDetailsVO.getContainerNumber());
				uploadMailDetailsForm.setPol(scannedMailDetailsVO.getPol());
				//Export Details For Transfer
				uploadMailDetailsForm.setExportPort(logonAttributes.getAirportCode());
				if(scannedMailDetailsVO.getToFlightDate() != null){
					uploadMailDetailsForm.setOutboundFlightCarrierCode(scannedMailDetailsVO.getToCarrierCode());
					uploadMailDetailsForm.setOutboundFlightNumber(scannedMailDetailsVO.getToFlightNumber());
					uploadMailDetailsForm.setOutboundFlightDate(scannedMailDetailsVO.getToFlightDate().toDisplayDateOnlyFormat());
				}else{
					uploadMailDetailsForm.setOutboundCarrierCode(scannedMailDetailsVO.getToCarrierCode());
				}
				if(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
					uploadMailDetailsForm.setToBulkFlag(MailConstantsVO.FLAG_YES);
				}else{
					uploadMailDetailsForm.setToBulkFlag(MailConstantsVO.FLAG_NO);
				}
				uploadMailDetailsForm.setToContainer(scannedMailDetailsVO.getContainerNumber());
				uploadMailDetailsForm.setPou(scannedMailDetailsVO.getPou());
				uploadMailDetailsForm.setDest(scannedMailDetailsVO.getDestination());


			}

		}

		invocationContext.target = SCREENLOAD_INBOUND_SUCCESS;
	}

}
