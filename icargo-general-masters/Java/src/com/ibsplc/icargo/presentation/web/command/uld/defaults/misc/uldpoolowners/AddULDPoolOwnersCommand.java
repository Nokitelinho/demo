/*
 * AddULDPoolOwnersCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldpoolowners;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3429
 * 
 */

public class AddULDPoolOwnersCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Add ULD Pool Owners Command");

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String OPERATION_FLAG_UPDATE = "U";

	private static final String SAVE_FAILURE = "save_failure";

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AddRowCommand", "execute");

		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		//Commented by Manaf for INT ULD510
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ULDPoolOwnerVO> uldPoolVOs = null;
		String[] airlineOne = form.getAirlineOne();
		String[] airlineTwo = form.getAirlineTwo();
		String[] airport = form.getAirport();
		String[] remarks = form.getRemarks();
		int count = 0;
		ULDPoolOwnerVO uldPoolOwnerVO = new ULDPoolOwnerVO();
		uldPoolOwnerVO.setCompanyCode(logonAttributes.getCompanyCode());
		uldPoolOwnerVO.setAirlineOne("");
		uldPoolOwnerVO.setAirlineTwo("");
		uldPoolOwnerVO.setAirport("");
		uldPoolOwnerVO.setRemarks("");
		uldPoolOwnerVO.setOperationFlag("I");

		if (session.getUldPoolOwnerVO() == null) {
			log.log(Log.FINE, "...........There are no entries.......");
			uldPoolVOs = new ArrayList<ULDPoolOwnerVO>();
			uldPoolVOs.add(uldPoolOwnerVO);
			session.setUldPoolOwnerVO(uldPoolVOs);
		} else {
			log.log(Log.FINE, "...........Else part.......");
			ErrorVO error = null;
			count = 0;
			uldPoolVOs = session.getUldPoolOwnerVO();
			uldPoolVOs.add(uldPoolOwnerVO);

		}

		log.exiting("AddRowCommand", "execute");
		invocationContext.target = "save_success";

	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param poolOwnerVO
	 * @param airlineOne
	 * @return
	 */

	public Collection<ErrorVO> validateAirlineCodeOne(ULDPoolOwnersForm form,
			String companyCode, String airlineOne, ULDPoolOwnerVO poolOwnerVO) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		log.log(Log.FINE, "inside validateAirlineCodeOne--------------->>>>");
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineOne != null && airlineOne.trim().length() > 0) {
			log
					.log(Log.FINE,
							"inside validateAirlineCodeOne first if--------------->>>>");
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, airlineOne.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				poolOwnerVO.setAirlineIdentifierOne(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param poolOwnerVO
	 * @param airlineTwo
	 * @return
	 */

	public Collection<ErrorVO> validateAirlineCodeTwo(ULDPoolOwnersForm form,
			String companyCode, String airlineTwo, ULDPoolOwnerVO poolOwnerVO) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineTwo != null && airlineTwo.trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, airlineTwo.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				poolOwnerVO.setAirlineIdentifierTwo(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}

}
