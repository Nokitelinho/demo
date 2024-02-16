/*
 * AddRowCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1747
 *
 */
public class AddRowCommand extends BaseCommand {

	private static final String ERROR_FWD = "screenload_failure";

	/**
	 * execute method for handling the addrow action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ReserveAWBSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.reservestock");
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errorvos = new ArrayList<ErrorVO>();
		//Collection<String> awbTypes = session.getAWBTypes();
		//String expDate = form.getExpiryDate();
		//ReserveAWBVO reserveAWBVO = new ReserveAWBVO();
		//String airline = form.getAirline();
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			try {
				AirlineValidationVO airlineVO = airlineDelegate
						.validateAlphaCode(logonAttributes.getCompanyCode(),
								form.getAirline().toUpperCase());
				form.setShpPrefix(airlineVO.getNumericCode());
				//reserveAWBVO.setShipmentPrefix(airlineVO.getNumericCode());
			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				errorvos = handleDelegateException(e);
			}
		}
		if (errorvos != null && errorvos.size() > 0) {
			invocationContext.addAllError(errorvos);
			invocationContext.target = ERROR_FWD;
			return;
		} 
		/*else {
			Collection<String> awbnum = new ArrayList<String>();
			if (form.getAwbNumber() != null) {
				String[] awbNumbers = form.getAwbNumber();
				for (String awbNum : awbNumbers) {
					awbnum.add(awbNum);
				}
				awbnum.add(new String(""));
			} else {
				awbnum.add(new String(""));
			}
			reserveAWBVO.setSpecificDocNumbers(awbnum);
			}*/
			//session.setReserveAWBVO(reserveAWBVO);
			
			//session.setAWBTypes(awbTypes);
			//form.setExpiryDate(expDate);
			invocationContext.target = "addrow_success";		
	}

}
