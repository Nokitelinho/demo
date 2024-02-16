/*
 * SaveULDPoolOwnersCommand.java Created on AUG 25, 2006
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class SaveULDPoolOwnersCommand extends BaseCommand {

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String BLANK = "";

	private static final String SKIP_ADD = "NA";

    //Added by A-2412
	private static final String OPERATION_FLAG_NOOP = "NOOP";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
 
		log.entering("Save Command", "ULD POOL OWNERS");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		//Commented by Manaf for INT ULD510
		//AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineValidationVO airlineValidationVO = null;
		ArrayList<ULDPoolOwnerVO> uLDPoolOwnerVOs = 
			session.getUldPoolOwnerVO()!= null ?
			new ArrayList<ULDPoolOwnerVO>(session.getUldPoolOwnerVO()) : 
			new ArrayList<ULDPoolOwnerVO>();
			Collection<ULDPoolSegmentExceptionsVO> exceptionVO=new ArrayList<ULDPoolSegmentExceptionsVO>();	
		if(uLDPoolOwnerVOs!=null){
			for(ULDPoolOwnerVO poolVO:uLDPoolOwnerVOs){
				if("D".equals(poolVO.getOperationFlag())&&poolVO.getPoolSegmentsExceptionsVOs()!=null){
					for(ULDPoolSegmentExceptionsVO exceptionsVO:poolVO.getPoolSegmentsExceptionsVOs()){
						if("I".equals(exceptionsVO.getOperationFlag())){
							exceptionVO.add(exceptionsVO);
						}
					}
					for(ULDPoolSegmentExceptionsVO exceptionsVO:exceptionVO){
						poolVO.getPoolSegmentsExceptionsVOs().remove(exceptionsVO);
					}
				}
			}
		}
		
		
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
						
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		if(uLDPoolOwnerVOs!=null&&uLDPoolOwnerVOs.size()>0){
		try {
			
			delegate.saveULDPoolOwner(uLDPoolOwnerVOs);
			
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			exception = handleDelegateException(ex);
		}

		if(exception !=null && exception.size()>0){
			invocationContext.addAllError(exception);
			invocationContext.target = SAVE_FAILURE;
			return;
		}else{
		//form.setAirline(BLANK);
		form.setSecondAirline(BLANK);
		form.setPolAirport(BLANK);
		form.setFirstAirline(logonAttributes.getOwnAirlineCode());
		form.setLinkStatus(BLANK);
		session.setFlightValidationVOSession(null);
		session.setUldPoolOwnerVO(null);
		ULDPoolOwnerVO newPoolOwnerVO = new ULDPoolOwnerVO();
		newPoolOwnerVO.setOperationFlag(ULDPoolOwnerVO.OPERATION_FLAG_INSERT);
		newPoolOwnerVO.setCompanyCode(companyCode);
		//session.setUldPoolOwnerVO(newPoolOwnerVO);
		
		ErrorVO error = new ErrorVO("uld.defaults.poolowners.savedsuccessfully");
     	error.setErrorDisplayType(ErrorDisplayType.STATUS);
     	errors = new ArrayList<ErrorVO>();
     	errors.add(error);
     	invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
	}
	}else{
		ErrorVO error = new ErrorVO("uld.defaults.poolowners.nodatatosave");
		errors.add(error);
     	invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
		}
	}

	/**
	 *
	 * @param form
	 * @param companyCode
	 * @param poolOwnerVO
	 * @return
	 */

	public Collection<ErrorVO> validateAirlineCodeOne(ULDPoolOwnersForm form,
			String companyCode, String airlineOne, ULDPoolOwnerVO poolOwnerVO) {
		Collection<ErrorVO> errors = null;
		log.log(Log.FINE, "inside validateAirlineCodeOne--------------->>>>",
				airlineOne);
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineOne != null && airlineOne.trim().length() > 0) {
			log
					.log(
							Log.FINE,
							"inside validateAirlineCodeOne first if--------------->>>>",
							airlineOne);
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
