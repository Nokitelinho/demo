/*
 * SaveExceptionsCommand.java Created on Jul 20, 2006
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldpoolowners;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.SegmentExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3429
 * 
 */

public class SaveExceptionsCommand extends BaseCommand {

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String OPERATION_FLAG_NOOP = "NOOP";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Save Command", "SEGMENT EXCEPTIONS");
		SegmentExceptionsForm form = (SegmentExceptionsForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		int selectedRow = Integer.parseInt(form.getSelectedRow());

		ArrayList<ULDPoolOwnerVO> poolOwnerVOs = session.getUldPoolOwnerVO() != null ? new ArrayList<ULDPoolOwnerVO>(
				session.getUldPoolOwnerVO())
				: new ArrayList<ULDPoolOwnerVO>();
		ULDPoolOwnerVO poolOwnerVO = poolOwnerVOs.get(selectedRow);

		ArrayList<ULDPoolSegmentExceptionsVO> exceptionsVOs = poolOwnerVO
				.getPoolSegmentsExceptionsVOs() != null ? new ArrayList<ULDPoolSegmentExceptionsVO>(
				poolOwnerVO.getPoolSegmentsExceptionsVOs())
				: new ArrayList<ULDPoolSegmentExceptionsVO>();
		ArrayList<ULDPoolSegmentExceptionsVO> poolExceptionVos 
							=new ArrayList<ULDPoolSegmentExceptionsVO>();
		
		String[] origin = form.getOrigin();
		String[] destination = form.getDestination();
		String[] opFlags = form.getHiddenOperationFlag();
		String airlineOne = poolOwnerVO.getAirlineOne();
		String airlineTwo = poolOwnerVO.getAirlineTwo();
		String airport = poolOwnerVO.getAirport();
		int count = 0;
		if (exceptionsVOs != null && exceptionsVOs.size() > 0) {
			count = exceptionsVOs.size();
			log.log(Log.INFO, "Count", count);
			log.log(Log.INFO, "Count", exceptionsVOs);
			for (int i = count - 1; i >= 0; i--) {
				if (OPERATION_FLAG_NOOP.equals(opFlags[i])) {
					exceptionsVOs.remove(i);
					log.log(Log.FINE, "exceptionsVO--------------->",
							exceptionsVOs);
					
				} else {
					ErrorVO error = null;
					ULDPoolSegmentExceptionsVO exceptionsVO = exceptionsVOs
							.get(i);
					ULDPoolSegmentExceptionsVO poolExceptionVO = exceptionsVOs
							.get(i);
					log.log(Log.FINE,
							" poolExceptionVO first if123!@#--------------->",
							poolExceptionVO);
					if (!("D".equals(opFlags[i]))) {

						if (origin[i] != null && origin[i].trim().length() != 0) {

							log.log(Log.FINE,
									"inside Origin first if--------------->",
									origin, i);
							if (validateAirportCode(form,
									getApplicationSession().getLogonVO()
											.getCompanyCode(), origin[i]) == null) {

								exceptionsVO.setOrigin(origin[i].toUpperCase());
								poolExceptionVO.setOrigin(origin[i]
										.toUpperCase());
							} else {
								poolExceptionVO.setOrigin(origin[i]
										.toUpperCase());
								log
										.log(
												Log.FINE,
												"exceptionsVO.getOrigin()------------>",
												exceptionsVO.getOrigin());
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.msg.err.invalidairport",
										new Object[] { origin[i].toUpperCase() });
								errors.add(error);

							}

						} else {

							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.originmandatory");
							errors.add(error);
						}

						if (destination[i] != null
								&& destination[i].trim().length() != 0) {

							log
									.log(
											Log.FINE,
											"inside destination first if--------------->",
											destination, i);
							if (validateAirportCode(form,
									getApplicationSession().getLogonVO()
											.getCompanyCode(), destination[i]) == null) {
								log
										.log(
												Log.FINE,
												"inside Destination validate loop123--------------->",
												destination, i);
								exceptionsVO.setDestination(destination[i]
										.toUpperCase());
								poolExceptionVO.setDestination(destination[i]
										.toUpperCase());
							} else {
								log.log(Log.INFO,
										"exceptionsVO.getDestination",
										exceptionsVO.getDestination());
								poolExceptionVO.setDestination(destination[i]
										.toUpperCase());
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.msg.err.invalidairport",
										new Object[] { destination[i]
												.toUpperCase() });
								errors.add(error);

							}

						} else {
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.destinationmandatory");
							errors.add(error);
						}
					}
					log.log(Log.INFO, "SAVE EXCEPTIONS COMMAND1234",
							exceptionsVO);
					exceptionsVO.setOperationFlag(opFlags[i]);
					poolExceptionVO.setOperationFlag(opFlags[i]);
					log.log(Log.INFO, "Operation Flag", exceptionsVO.getOperationFlag());
					log.log(Log.INFO, "SAVE EXCEPTIONS COMMAND", exceptionsVO);
					poolExceptionVos.add(poolExceptionVO);
				}

			}
			ErrorVO error = null;
			log.log(Log.INFO, "OpFlags inside save exceptions command",
					opFlags.length);
			for (int i = count; i < opFlags.length - 1; i++) {

				log.log(Log.FINE, "Inside second for opFlags--------------->",
						opFlags, i);
				if (!OPERATION_FLAG_NOOP.equals(opFlags[i])) {
					log.log(Log.FINE, "inside if------------");
					ULDPoolSegmentExceptionsVO exceptionsVO = new ULDPoolSegmentExceptionsVO();
					ULDPoolSegmentExceptionsVO poolExceptionVO = new ULDPoolSegmentExceptionsVO();
					if (origin[i] != null && origin[i].trim().length() != 0) {

						log.log(Log.FINE,
								"inside Origin first if--------------->",
								origin, i);
						if (validateAirportCode(form, getApplicationSession()
								.getLogonVO().getCompanyCode(), origin[i]) == null) {
							exceptionsVO.setOrigin(origin[i].toUpperCase());
							poolExceptionVO.setOrigin(origin[i].toUpperCase());
						} else {

							poolExceptionVO.setOrigin(origin[i].toUpperCase());

							log.log(Log.FINE,
									"inside Else exceptionsVO.orgin  ",
									exceptionsVO.getOrigin());
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.invalidairport",
									new Object[] { origin[i].toUpperCase() });
							errors.add(error);

						}

					} else {
						error = new ErrorVO(
								"uld.defaults.uldpoolowners.msg.err.originmandatory");
						errors.add(error);
					}

					if (destination[i] != null
							&& destination[i].trim().length() != 0) {

						log.log(Log.FINE,
								"inside destination first if--------------->",
								destination, i);
						if (validateAirportCode(form, getApplicationSession()
								.getLogonVO().getCompanyCode(), destination[i]) == null) {
							exceptionsVO.setDestination(destination[i]
									.toUpperCase());
							poolExceptionVO.setDestination(destination[i]
									.toUpperCase());
						} else {
							poolExceptionVO.setDestination(destination[i]
									.toUpperCase());
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.invalidairport",
									new Object[] { destination[i].toUpperCase() });
							errors.add(error);

						}

					} else {
						error = new ErrorVO(
								"uld.defaults.uldpoolowners.msg.err.destinationmandatory");
						errors.add(error);
					}

					if (validateAirlineCodeTwo(form, getApplicationSession()
							.getLogonVO().getCompanyCode(), airlineTwo,
							exceptionsVO) == null) {
						exceptionsVO.setAirlineTwo(airlineTwo.toUpperCase());
						poolExceptionVO.setAirlineTwo(airlineTwo.toUpperCase());
					}
					if (validateAirlineCodeOne(form, getApplicationSession()
							.getLogonVO().getCompanyCode(), airlineOne,
							exceptionsVO) == null) {
						exceptionsVO.setAirlineOne(airlineOne.toUpperCase());
						poolExceptionVO.setAirlineOne(airlineOne.toUpperCase());
					}
					exceptionsVO.setAirport(airport.toUpperCase());
					poolExceptionVO.setAirport(airport.toUpperCase());
					exceptionsVO.setCompanyCode(companyCode);
					poolExceptionVO.setCompanyCode(companyCode);
					exceptionsVO
							.setOperationFlag(ULDPoolSegmentExceptionsVO.OPERATION_FLAG_INSERT);
					poolExceptionVO
							.setOperationFlag(ULDPoolSegmentExceptionsVO.OPERATION_FLAG_INSERT);
					log.log(Log.FINE, "exceptionsVO--------------->",
							exceptionsVO);
					exceptionsVOs.add(exceptionsVO);
					poolExceptionVos.add(poolExceptionVO);
					log.log(Log.FINE, "poolExceptionVos123!@#--------------->",
							poolExceptionVos);
				} else {
					if(poolExceptionVos.size()>i){
					poolExceptionVos.remove(i);
					}
				}
			}
			session.setUldPoolSegmentVos(poolExceptionVos);
			//poolOwnerVO.setPoolSegmentsExceptionsVOs(exceptionsVOs);
			if (!("I".equals(poolOwnerVO.getOperationFlag()))) {
				poolOwnerVO
						.setOperationFlag(ULDPoolOwnerVO.OPERATION_FLAG_UPDATE);
			}
			log.log(Log.INFO, "Operation Flag========>>>>>>>..", poolOwnerVO.getOperationFlag());
			
log.log(Log.INFO, "PoolOwnerVO=======>>>>>>>..", poolOwnerVO);

		}

		else {
			log.log(Log.INFO, "INSIDE SAVE EXCEPTIONS ELSE LOOP");
			log.log(Log.INFO, "session.getUldPoolSegmentVos>>>>>>>..", session.getUldPoolSegmentVos());
			ErrorVO error = null;
			for (int i = 0; i < opFlags.length - 1; i++) {
				if (!OPERATION_FLAG_NOOP.equals(opFlags[i])) {
					log.log(Log.FINE, "inside if------------");
					ULDPoolSegmentExceptionsVO exceptionsVO = new ULDPoolSegmentExceptionsVO();
					ULDPoolSegmentExceptionsVO poolExceptionVO = new ULDPoolSegmentExceptionsVO();
					if (origin[i] != null && origin[i].trim().length() != 0) {

						log.log(Log.FINE,
								"inside Origin first if--------------->",
								origin, i);
						if (validateAirportCode(form, getApplicationSession()
								.getLogonVO().getCompanyCode(), origin[i]) == null) {
							log.log(Log.FINE, "no error for origin-----------");
							exceptionsVO.setOrigin(origin[i].toUpperCase());
							poolExceptionVO.setOrigin(origin[i].toUpperCase());
						} else {
							log.log(Log.FINE,
									"error for origin-else loop----------");
							poolExceptionVO.setOrigin(origin[i].toUpperCase());
							log.log(Log.FINE,
									"inside exceptionsVO Origin ----------->",
									exceptionsVO.getOrigin());
							log.log(Log.FINE, "inside origin else------------");
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.invalidairport",
									new Object[] { origin[i].toUpperCase() });
							errors.add(error);

						}

					} else {
						error = new ErrorVO(
								"uld.defaults.uldpoolowners.msg.err.originmandatory");
						errors.add(error);
					}

					if (destination[i] != null
							&& destination[i].trim().length() != 0) {

						log.log(Log.FINE,
								"inside destination first if--------------->",
								destination, i);
						if (validateAirportCode(form, getApplicationSession()
								.getLogonVO().getCompanyCode(), destination[i]) == null) {
							exceptionsVO.setDestination(destination[i]
									.toUpperCase());
							poolExceptionVO.setDestination(destination[i]
									.toUpperCase());
						} else {
							poolExceptionVO.setDestination(destination[i]
									.toUpperCase());
							error = new ErrorVO(
									"uld.defaults.uldpoolowners.msg.err.invalidairport",
									new Object[] { destination[i].toUpperCase() });
							errors.add(error);

						}

					} else {
						error = new ErrorVO(
								"uld.defaults.uldpoolowners.msg.err.destinationmandatory");
						errors.add(error);
					}
					log.log(Log.INFO, "Airline2====>>>>>>>>>>", airlineTwo);
					if (validateAirlineCodeTwo(form, getApplicationSession()
							.getLogonVO().getCompanyCode(), airlineTwo,
							exceptionsVO) == null) {
						log
								.log(Log.INFO, "inside if Airline2===>>",
										airlineTwo);
						exceptionsVO.setAirlineTwo(airlineTwo.toUpperCase());
						poolExceptionVO.setAirlineTwo(airlineTwo.toUpperCase());
					}

					if (validateAirlineCodeOne(form, getApplicationSession()
							.getLogonVO().getCompanyCode(), airlineOne,
							exceptionsVO) == null) {
						exceptionsVO.setAirlineOne(airlineOne.toUpperCase());
						poolExceptionVO.setAirlineOne(airlineOne.toUpperCase());
					}

					exceptionsVO.setAirport(airport.toUpperCase());
					poolExceptionVO.setAirport(airport.toUpperCase());
					exceptionsVO.setCompanyCode(companyCode);
					poolExceptionVO.setCompanyCode(companyCode);
					exceptionsVO
							.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT);
					poolExceptionVO.setOperationFlag(opFlags[i]);
					log
							.log(Log.FINE, "ExceptionVOS------------>",
									exceptionsVO);
					log.log(Log.FINE, "inside not equals NOOP Loop");
					
					exceptionsVOs.add(exceptionsVO);
					poolExceptionVos.add(poolExceptionVO);
				} else {
					log.log(Log.FINE, "inside  equals NOOP Loop");
					if(poolExceptionVos!=null&&poolExceptionVos.size()>0){
					if(poolExceptionVos.size()>i){
					poolExceptionVos.remove(i);
					}
					}
				}

			}
			//session.setUldPoolSegmentVos(poolExceptionVos);
			//poolOwnerVO.setPoolSegmentsExceptionsVOs(exceptionsVOs);

			if (!("I".equals(poolOwnerVO.getOperationFlag()))) {
				poolOwnerVO
						.setOperationFlag(ULDPoolOwnerVO.OPERATION_FLAG_UPDATE);
			}

		}
		log.log(Log.INFO, "\n errors -----------> ", errors);
		log.log(Log.INFO, "\n errors.size() -----------> ", errors.size());
		session.setUldPoolSegmentVos(poolExceptionVos);
		if (errors != null && errors.size() > 0) {
			log.log(Log.INFO, "Inside ERRORS");
			
			log.log(Log.FINE, "getpoolExceptionVossssssss****------------>",
					session.getUldPoolSegmentVos());
			invocationContext.addAllError(errors);
			form.setErrorFlag("N");
			invocationContext.target = SAVE_FAILURE;
			return;
		} else {
			poolOwnerVO.setPoolSegmentsExceptionsVOs(exceptionsVOs);

			form.setErrorFlag("Y");
			invocationContext.target = SAVE_SUCCESS;
		}
	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param exceptionsVO
	 * @param airlineOne
	 * @return
	 */
	public Collection<ErrorVO> validateAirlineCodeOne(
			SegmentExceptionsForm form, String companyCode, String airlineOne,
			ULDPoolSegmentExceptionsVO exceptionsVO) {
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
				exceptionsVO.setAirlineIdentifierOne(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param airlineTwo
	 * @param exceptionsVO
	 * @return
	 */
	public Collection<ErrorVO> validateAirlineCodeTwo(
			SegmentExceptionsForm form, String companyCode, String airlineTwo,
			ULDPoolSegmentExceptionsVO exceptionsVO) {
		log.log(Log.INFO, "inside validateAirlineCodeTwo Airline2===>>",
				airlineTwo);
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineTwo != null && airlineTwo.trim().length() > 0) {
			try {
				log
						.log(
								Log.INFO,
								"inside validateAirlineCodeTwo try block Airline2===>>",
								airlineTwo);
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, airlineTwo.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				log.log(Log.INFO,
						"inside validateAirlineCodeTwo if loop Airline2===>>",
						airlineValidationVO.getAirlineIdentifier());
				exceptionsVO.setAirlineIdentifierTwo(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param airport
	 * @return
	 */
	private Collection<ErrorVO> validateAirportCode(SegmentExceptionsForm form,
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
