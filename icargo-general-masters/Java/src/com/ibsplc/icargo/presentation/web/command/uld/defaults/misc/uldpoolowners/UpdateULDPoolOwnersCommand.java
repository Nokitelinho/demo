/*
 * UpdateULDPoolOwnersCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldpoolowners;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author
 * 
 */
public class UpdateULDPoolOwnersCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String UPDATE_SUCCESS = "update_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String ADD_SUCCESS = "add_success";

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String SEGMENTS_SUCCESS = "segments_success";

	private static final String POPUP_SUCCESS = "popup_success";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("UPDATE", "RECORD ULD MOVEMENT FORM");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		// String companyCode = logonAttributes.getCompanyCode();
		// AirlineValidationVO airlineValidationVO = null;
		// AirlineDelegate airlineDelegate = new AirlineDelegate();
		int count = 0;
		String[] airlineOne = form.getAirlineOne();
		String[] airlineTwo = form.getAirlineTwo();
		String[] airport = form.getAirport();
		String[] remarks = form.getRemarks();
		String[] selectedRows = null;
		String flag = form.getFlag();
		Collection<ULDPoolOwnerVO> uldPoolVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		// int selectedRow = Integer.parseInt(form.getSelectedRow());
		if (form.getSelectedRow() != null) {
			selectedRows = form.getSelectedRows();
		}

		uldPoolVOs = session.getUldPoolOwnerVO();

		log.log(Log.INFO, "before updateeeee", uldPoolVOs);
		boolean isCheckReq = true;
		if (uldPoolVOs != null) {
			for (ULDPoolOwnerVO uldPoolVO : uldPoolVOs) {
				ErrorVO error = null;
				log.log(Log.INFO, "iteration count", count);
				if (!"D".equals(uldPoolVO.getOperationFlag())) {
					if ("delete".equals(flag)) {
						if (selectedRows != null && selectedRows.length > 0) {
							for (String str : selectedRows) {
								if (count == Integer.parseInt(str)) {
									isCheckReq = false;
									break;
								}
							}
						}
					}
					if (isCheckReq) {
						if (airlineOne[count] != null
								&& airlineOne[count].trim().length() != 0) {
							log
									.log(
											Log.FINE,
											"inside airlineOne first if--------------->",
											airlineOne, count);
							if (validateAirlineCodeOne(form,
									getApplicationSession().getLogonVO()
											.getCompanyCode(),
									airlineOne[count], uldPoolVO) == null) {
								log
										.log(
												Log.FINE,
												"inside airlineOne second if--------------->",
												airlineOne, count);
								uldPoolVO.setAirlineOne(airlineOne[count]
										.toUpperCase());
							} else {
								uldPoolVO.setAirlineOne(airlineOne[count]
										.toUpperCase());
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.msg.err.invalidairline",
										new Object[] { airlineOne[count]
												.toUpperCase() });
								errors.add(error);
							}

						} else {
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.airlineonemandatory");
							errors.add(error);
						}
						if (airlineTwo[count] != null
								&& airlineTwo[count].trim().length() != 0) {
							log.log(Log.INFO, "inside airline2 if====>");
							if (validateAirlineCodeTwo(form,
									getApplicationSession().getLogonVO()
											.getCompanyCode(),
									airlineTwo[count], uldPoolVO) == null) {
								log.log(Log.INFO,
										"inside airline2 second if====>");
								uldPoolVO.setAirlineTwo(airlineTwo[count]
										.toUpperCase());
							} else {
								uldPoolVO.setAirlineTwo(airlineTwo[count]
										.toUpperCase());
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.msg.err.invalidairline",
										new Object[] { airlineTwo[count]
												.toUpperCase() });
								errors.add(error);
							}
						} else {
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.airlinetwomandatory");
							errors.add(error);
						}
						if (airport[count] != null
								&& airport[count].trim().length() != 0 && !"add".equals(flag) && !"delete".equals(flag) /*&& !("NA".equals(airport[count]))*/) {
							if (validateAirportCode(form,
									getApplicationSession().getLogonVO()
											.getCompanyCode(), airport[count]) == null) {
								log.log(Log.INFO,
										"inside airport second if====>",
										airport, count);
								uldPoolVO.setAirport(airport[count]
										.toUpperCase());
							} else {
								uldPoolVO.setAirport(airport[count]
										.toUpperCase());
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.msg.err.invalidairport",
										new Object[] { airport[count]
												.toUpperCase() });
								errors.add(error);

							}
						} /*else {
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.airportmandatory");
							errors.add(error);
						}*/

						if (!(remarks[count].equals(uldPoolVO.getRemarks()))) {

							uldPoolVO.setRemarks(remarks[count]);
							if (!("D".equals(uldPoolVO.getOperationFlag()) || "I"
									.equals(uldPoolVO.getOperationFlag()))) {
								uldPoolVO.setOperationFlag("U");
							}
						}
						if (!(airport[count].equals(uldPoolVO.getAirport()))) {
							log
									.log(
											Log.FINE,
											"inside Airport for Updation---------Session------>",
											uldPoolVO.getAirport());
							log
									.log(
											Log.FINE,
											"inside Airport for Updation---------Form------>",
											airport, count);
							uldPoolVO.setAirport(airport[count]);
							if (!("D".equals(uldPoolVO.getOperationFlag()) || "I"
									.equals(uldPoolVO.getOperationFlag()))) {
								uldPoolVO.setOperationFlag("U");
							}
						}
						count++;
					}
				} else if ("D".equals(uldPoolVO.getOperationFlag())) {
					count++;
				}

			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;

		} else {
			log.log(Log.INFO, "flag value", flag);
			if (("save").equals(flag)) {
				invocationContext.target = SAVE_SUCCESS;
			} else if (("add").equals(flag)) {
				invocationContext.target = ADD_SUCCESS;
			} else if (("delete").equals(flag)) {
				invocationContext.target = DELETE_SUCCESS;
			} else if (("exceptions").equals(flag)) {
				form.setPopupFlag("Y");
				invocationContext.target = POPUP_SUCCESS;

			}

		}
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
		log.log(Log.FINE, "inside validateAirlineCodeOne--------------->>>>",
				airlineOne);
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineOne != null && airlineOne.trim().length() > 0) {
			log.log(Log.FINE,
					"inside validateAirlineCodeOne first --------------->>>>",
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
			log.log(Log.FINE,
					"inside validateAirlineCodeTwo--------------->>>>",
					airlineTwo);
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

	private Collection<ErrorVO> validateAirportCode(ULDPoolOwnersForm form,
			String companyCode, String airport) {
		log.log(Log.INFO, "Airport inside airport validation", airport);
		Collection<ErrorVO> errors = null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;

		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), airport.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			log.log(Log.INFO, "Errors", errors);
		}
		return errors;

	}

}
