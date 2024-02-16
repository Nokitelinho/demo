/*
 * CloseListCN51sScreenCommand.java Created on Aug 17,2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51.FinalizeInvoiceCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7929	:	17-Aug-2018	:	Draft
 */
public class FinalizeInvoiceCommand extends BaseCommand{
	
    private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String ACTION_FAILURE = "finalize_failure";
	private static final String ACTION_SUCCESS = "finalize_success";
	private static final String NOT_NEWSTATUS_CANNOT_FINALIZE = "mailtracking.mra.airlinebilling.msg.error.newcannotfinalize";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	public void execute(InvocationContext invContext) throws CommandInvocationException {
		
		log.entering("FinalizeInvoiceCommand","execute");
		ListCN51ScreenSession cn51ScreenSession
				= (ListCN51ScreenSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		ListCN51ScreenForm listCN51ScreenForm 
		= (ListCN51ScreenForm)invContext.screenModel;
		Page<AirlineCN51SummaryVO> listedDetails = cn51ScreenSession.getAirlineCN51SummaryVOs();
		Collection<AirlineCN51SummaryVO> selectedairlineCN51SummaryVO = new ArrayList<AirlineCN51SummaryVO>();
		
		String[] selectedRows = listCN51ScreenForm.getTableRowId();
          int size = selectedRows.length;
		
    	   int row = 0;
		
		for(AirlineCN51SummaryVO airlineCN51SummaryVO :listedDetails ){
			for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				selectedairlineCN51SummaryVO.add(airlineCN51SummaryVO);
    			}    			
			}
			row++;
			
		}
		
		Collection<ErrorVO> errors = null;
	       
		errors = validateFrom(selectedairlineCN51SummaryVO,selectedRows,size);
	
	if (errors != null && errors.size() > 0) {
		 log.log(Log.FINE,"!!!inside errors!= null");
		 invContext.addAllError(errors);
		 invContext.target =  ACTION_FAILURE;
		return;
	}
	
	try {
		new MailTrackingMRADelegate().finalizeInvoice(selectedairlineCN51SummaryVO,null);
	} catch (BusinessDelegateException e) {
		
	}
		//cn51ScreenSession.removeAllAttributes();
		//invContext.target = CLOSE_SUCCESS;
	listCN51ScreenForm.setScreenStatus("finalizeList");
        invContext.target = ACTION_SUCCESS;
		log.exiting("FinalizeInvoiceCommand","execute");
		
		
	}

	/**
	 * 
	 * 	Method		:	FinalizeInvoiceCommand.validateFrom
	 *	Added by 	:	a-7929 on 17-Aug-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	ErrorVO
	 */
	private Collection<ErrorVO> validateFrom(Collection<AirlineCN51SummaryVO> selectedairlineCN51SummaryVO,
			String[] selectedRows, int size) {
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		Boolean isNotNewInvoiceStatus = false;
		//String invoiceStatus = "";
		
		for (AirlineCN51SummaryVO airlineCN51SummaryVO :selectedairlineCN51SummaryVO) {
			
    		/*if(invoiceStatus.length() == 0) {
    			invoiceStatus = airlineCN51SummaryVO.getInvStatus();
    		}
    		else if(!invoiceStatus.equals(airlineCN51SummaryVO.getInvStatus())){
    			isDifferentInvoiceStatus = true;	
			}*/
			
			if(!("New".equals(airlineCN51SummaryVO.getInvStatus()))){
				isNotNewInvoiceStatus=true;
			}
		}
		if(isNotNewInvoiceStatus){
		ErrorVO errorVO = new ErrorVO(NOT_NEWSTATUS_CANNOT_FINALIZE);
		 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		 formErrors.add(errorVO);
		}
		return formErrors;
		
	}

}
