/*
 * WithdrowMailCommand.java Created on Dec 03, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-8061
 *
 */
public class WithdrawMailCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ExceptionCommand");

	private static final String CLASS_NAME = "ExceptionCommand";
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "action_success";
	private static final String ACTION_FAILURE = "action_failure";
	private static final String SELECT_ALLMAILBAGS_INDSN = "mailtracking.mra.airlinebilling.msg.err.selectallmailbagsindsn";
	private static final String NO_ROWS_SELECTED = "mailtracking.mra.airlinebilling.msg.err.norowsselected";
	
	
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		Page<AirlineCN66DetailsVO> airlineCN66DetailsVOs =null;
		airlineCN66DetailsVOs=session.getAirlineCN66DetailsVOs();
		Collection<AirlineCN66DetailsVO> selectedAirlineCN66DetailsVOs = new ArrayList<AirlineCN66DetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		Map<String,String> selectedDSNMap=new HashMap<String,String>();
		Map<Long,String> selectedMailBagMap=new HashMap<Long,String>();
		AirlineCN66DetailsVO airlineCN66DetVO = session.getAirlineCN66DetailsVO();

		
		if(form.getCheck()!=null){
		if(!"N".equals(airlineCN66DetailsVOs.get(0).getCn51Status())){
			ErrorVO errorvo=new ErrorVO("mailtracking.mra.airlinebilling.msg.err.invalidinvoicestatus");
			errors.add(errorvo);
		}

	   	String[] selectedRows = form.getCheck();  
    	int size = selectedRows.length;
    	int row = 0;
    	for (AirlineCN66DetailsVO airlineCN66DetailsVO : airlineCN66DetailsVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				selectedAirlineCN66DetailsVOs.add(airlineCN66DetailsVO);
    				selectedMailBagMap.put(airlineCN66DetailsVO.getMalSeqNum(), airlineCN66DetailsVO.getDespatchSerialNo());
    				if(!selectedDSNMap.containsKey(airlineCN66DetailsVO.getDespatchSerialNo())){
    					selectedDSNMap.put(airlineCN66DetailsVO.getDespatchSerialNo(), airlineCN66DetailsVO.getDespatchSerialNo());
    				}
    			}    			
			}
    		row++;
    	}

    	for (AirlineCN66DetailsVO airlineCN66DetailsVO : airlineCN66DetailsVOs) {
    		if(selectedDSNMap.containsKey(airlineCN66DetailsVO.getDespatchSerialNo()) && !selectedMailBagMap.containsKey(airlineCN66DetailsVO.getMalSeqNum())){
    			ErrorVO errorvo=new ErrorVO(SELECT_ALLMAILBAGS_INDSN);
    			errors.add(errorvo);
    			break;
    		}
    	}
    	
		if(errors.isEmpty()){

		try{
				 delegate.withdrawMailBags(selectedAirlineCN66DetailsVOs);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors=handleDelegateException(businessDelegateException);
		}

		}
		
		}else{
			ErrorVO errorvo=new ErrorVO(NO_ROWS_SELECTED);
			errors.add(errorvo);
		}

		if(errors!=null&& !errors.isEmpty()){
		invocationContext.addAllError(errors);	
		invocationContext.target = ACTION_FAILURE;
		}else{
			invocationContext.target = ACTION_SUCCESS;
		}
		
		log.exiting(CLASS_NAME,"execute");
	}


}
