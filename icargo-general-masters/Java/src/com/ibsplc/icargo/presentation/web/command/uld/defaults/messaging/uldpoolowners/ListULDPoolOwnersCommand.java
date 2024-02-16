/*
 * ListULDPoolOwnersCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.uldpoolowners;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDPoolOwnersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class ListULDPoolOwnersCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand", "UCM IN OUT");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDPoolOwnerFilterVO poolOwnerFilterVO = new ULDPoolOwnerFilterVO();
		log.log(Log.FINE, "flight number--------->", form.getFlightNumber());
		errors = validateForm(form, poolOwnerFilterVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		/*
		 * Collection<FlightValidationVO> flightValidationVOs = null;
		 * FlightValidationVO flightValidationVO = new FlightValidationVO();
		 *
		 *
		 * FlightFilterVO flightFilterVO = null; flightFilterVO =
		 * getFlightFilterVO(logonAttributes.getCompanyCode(), form,
		 * airlineValidationVO); flightValidationVOs =
		 * getFlightDetails(flightFilterVO); if (flightValidationVOs == null ||
		 * flightValidationVOs.size() <= 0) { log.log(Log.FINE,
		 * "flightValidationVOs is NULL"); ErrorVO errorVO = new ErrorVO(
		 * "uld.defaults.uldpoolowners.msg.err.noflightdetails");
		 * errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		 * errors.add(errorVO); invocationContext.addAllError(errors);
		 * invocationContext.target = LIST_FAILURE; return; } else if
		 * (flightValidationVOs != null && flightValidationVOs.size() == 1) {
		 * log.log(Log.FINE, "\n\n\n********* No Duplicate
		 * flights**************"); flightValidationVO = ((ArrayList<FlightValidationVO>)
		 * flightValidationVOs) .get(0); } else { log.log(Log.FINE,
		 * "\n\n\n*********Duplicate flights**************");
		 * duplicateFlightSession .setFlightValidationVOs((ArrayList<FlightValidationVO>)
		 * flightValidationVOs);
		 * duplicateFlightSession.setParentScreenId(SCREEN_ID);
		 * duplicateFlightSession.setFlightFilterVO(flightFilterVO);
		 * form.setDuplicateFlightStatus(FLAG_YES); invocationContext.target =
		 * DUPLICATE_SUCCESS; return; }
		 */

		poolOwnerFilterVO.setCompanyCode(companyCode);
		poolOwnerFilterVO.setDestination(form.getDestination().toUpperCase());
		// poolOwnerFilterVO.setFlightCarrierIdentifier(airlineValidationVO
		// .getAirlineIdentifier());
		poolOwnerFilterVO.setFlightNumber(form.getFlightNumber().toUpperCase());
		poolOwnerFilterVO.setOrigin(form.getOrigin().toUpperCase());
		log.log(Log.FINE, "Filter VO To Server------------->",
				poolOwnerFilterVO);
		ULDDefaultsDelegate uldDelegate = new ULDDefaultsDelegate();
		ULDPoolOwnerVO poolOwnerVO = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		/*try {
			poolOwnerVO = uldDelegate.listULDPoolOwner(poolOwnerFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			exception = handleDelegateException(businessDelegateException);
		}*/

		log.log(Log.FINE, "\n\n\nPOOL OWNER VO------------>", poolOwnerVO);
		if (poolOwnerVO != null) {

			session.setUldPoolOwnerVO(poolOwnerVO);
			form.setLinkStatus("Y");

		} else {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.norecords");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			form.setLinkStatus("Y");
			invocationContext.target = LIST_SUCCESS;
			form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			return;
		} 
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = LIST_SUCCESS;

	}

	/**
	 *
	 * @param form
	 * @param poolOwnerFilterVO
	 * @return
	 */

	private Collection<ErrorVO> validateForm(ULDPoolOwnersForm form,
			ULDPoolOwnerFilterVO poolOwnerFilterVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		if (form.getAirline() == null || form.getAirline().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.enterairline");
			errors.add(error);
		} else if (validateAirlineCodes(form, getApplicationSession()
				.getLogonVO().getCompanyCode(), poolOwnerFilterVO) != null) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.invalidairline",
					new Object[] { form.getAirline().toUpperCase() });
			errors.add(error);
		}

		if (form.getFlightNumber() == null
				|| form.getFlightNumber().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.enterflightno");
			errors.add(error);
		}

		if (form.getOrigin() == null || form.getOrigin().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.enterorigin");
			errors.add(error);
		} else if (validateAirportCodes(form.getOrigin().toUpperCase(),
				companyCode) != null) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.invalidorigin");
			errors.add(error);
		}

		if (form.getDestination() == null
				|| form.getDestination().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.uldpoolowners.msg.err.enterdestn");
			errors.add(error);
		} else if (validateAirportCodes(form.getDestination().toUpperCase(),
				companyCode) != null) {
			error = new ErrorVO(
					"uld.defaults.uldpoolowners.msg.err.invaliddestn");
			errors.add(error);

		}
		/*
		 * if (form.getFlightDate() == null ||
		 * form.getFlightDate().trim().length() == 0) { error = new
		 * ErrorVO("uld.defaults.ucminout.msg.err.enterflightdate");
		 * errors.add(error); }
		 */
		return errors;

	}

	/**
	 *
	 * @param station
	 * @param companyCode
	 * @return
	 */
	public Collection<ErrorVO> validateAirportCodes(String station,
			String companyCode) {
		log.entering("ListCommand", "validateAirportCodes");
		Collection<ErrorVO> errors = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(companyCode, station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		log.exiting("ListCommand", "validateAirportCodes");
		return errors;
	}

	/**
	 *
	 * @param form
	 * @param companyCode
	 * @param poolOwnerFilterVO
	 * @return
	 */

	public Collection<ErrorVO> validateAirlineCodes(ULDPoolOwnersForm form,
			String companyCode, ULDPoolOwnerFilterVO poolOwnerFilterVO) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, form.getAirline().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				poolOwnerFilterVO
						.setFlightCarrierIdentifier(airlineValidationVO
								.getAirlineIdentifier());
			}
		}
		return errors;
	}

}
