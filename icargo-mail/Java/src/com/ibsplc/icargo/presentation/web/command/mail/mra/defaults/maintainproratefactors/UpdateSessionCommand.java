/*
 * UpdateSessionCommand.java Created on Nov 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.citypair.CityPairDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * Command class for loading captureform3
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Nov 28, 2006 Rani Rose John Initial draft
 */
public class UpdateSessionCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "UpdateSessionCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String UPDATE_SUCCESS = "update_success";

	private static final String UPDATE_FAILURE = "update_failure";

	private static final String DATEFORMAT = "dd-MMM-yyyy";

	private static final String DATERANGEERR = "mailtracking.mra.defaults.maintainproratefactors.msg.err.invalidDateRange";

	private static final String EMPTY = "";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainMraProrateFactorsForm maintainProrateFactorsForm = (MaintainMraProrateFactorsForm) invocationContext.screenModel;
		MaintainMraProrateFactorsSession maintainProrateFactorsSession = (MaintainMraProrateFactorsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		updateDetails(maintainProrateFactorsForm, maintainProrateFactorsSession);
		Collection<ErrorVO> errors = null;
		errors = validateForm(maintainProrateFactorsForm);
		if (errors != null && errors.size() > 0) {
			log.log(Log.INFO, "mandatiry fields missing!!!!!");
			invocationContext.addAllError(errors);
			invocationContext.target = UPDATE_FAILURE;
		} else {
			errors = validateCities(maintainProrateFactorsForm,
					maintainProrateFactorsSession);
			if (errors != null && errors.size() > 0) {
				log.log(Log.INFO, "Invalid city exists!!!!!");
				invocationContext.addAllError(errors);
				invocationContext.target = UPDATE_FAILURE;
			} else {
				errors = validateCityPair(maintainProrateFactorsForm,
						maintainProrateFactorsSession);
				if (errors != null && errors.size() > 0) {
					log.log(Log.INFO, "Invalid city pair exists!!!!!");
					invocationContext.addAllError(errors);
					invocationContext.target = UPDATE_FAILURE;
				} else {
					invocationContext.target = UPDATE_SUCCESS;
				}
			}
		}
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * Method to validate citypair
	 * 
	 * @param maintainProrateFactorsForm
	 * @param maintainProrateFactorsSession
	 * @return
	 */
	private Collection<ErrorVO> validateCityPair(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm,
			MaintainMraProrateFactorsSession maintainProrateFactorsSession) {
		log.entering(CLASS_NAME, "validateCityPair");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (maintainProrateFactorsForm.getDestinationCityCode() != null
				&& maintainProrateFactorsForm.getDestinationCityCode().length > 0) {
			CityPairDelegate cityPairDelegate = new CityPairDelegate();
			String[] cityCodes = maintainProrateFactorsForm
					.getDestinationCityCode();
			String[] operationFlags = maintainProrateFactorsForm
					.getOperationFlag();
			String origin = maintainProrateFactorsForm.getOriginCityCode()
					.toUpperCase();
			String companycode = getApplicationSession().getLogonVO()
					.getCompanyCode();
			Set<String> codes = new HashSet<String>();
			int index = 0;
			for (String code : cityCodes) {
				if (index != cityCodes.length - 1) {
					if (OPERATION_FLAG_INSERT.equals(operationFlags[index])
							|| OPERATION_FLAG_UPDATE
									.equals(operationFlags[index])) {
						String param = null;
						StringBuilder builder = new StringBuilder(origin);
						param = builder.append("-").append(code.toUpperCase())
								.toString();
						codes.add(param);
					}
				}
				++index;
			}
			try {
				cityPairDelegate.findCityPair(companycode, codes);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}

		}
		return errors;
	}

	/**
	 * Method to validate Cities
	 * 
	 * @param maintainProrateFactorsForm
	 * @param maintainProrateFactorsSession
	 * @return
	 */
	private Collection<ErrorVO> validateCities(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm,
			MaintainMraProrateFactorsSession maintainProrateFactorsSession) {
		log.entering(CLASS_NAME, "validateCities");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (maintainProrateFactorsForm.getDestinationCityCode() != null
				&& maintainProrateFactorsForm.getDestinationCityCode().length > 0) {
			String[] cityCodes = maintainProrateFactorsForm
					.getDestinationCityCode();
			String[] cityNames = maintainProrateFactorsForm
					.getDestinationCityName();
			String[] operationFlags = maintainProrateFactorsForm
					.getOperationFlag();
			StringBuffer invalidCodes = new StringBuffer("");
			StringBuffer invalidCodeandName = new StringBuffer("");
			Set<String> codes = new HashSet<String>();
			int index = 0;
			for (String code : cityCodes) {
				if (index != cityCodes.length - 1) {
					if (OPERATION_FLAG_INSERT.equals(operationFlags[index])
							|| OPERATION_FLAG_UPDATE
									.equals(operationFlags[index])) {
						codes.add(code.toUpperCase());
						++index;
					}
				}
			}
			AreaDelegate areaDelegate = new AreaDelegate();

			String companycode = getApplicationSession().getLogonVO()
					.getCompanyCode();
			CityVO cityvo = null;
			for (int i = 0; i < cityCodes.length; i++) {
				if (cityCodes[i] != null && cityCodes[i].trim().length() > 0) {
					Map<String, CityVO> cityMap = null;

					try {
						cityMap = areaDelegate.validateCityCodes(companycode,
								codes);
					} catch (BusinessDelegateException e) {
						errors = handleDelegateException(e);
					}
					if (cityMap == null) {
						cityMap = new HashMap<String, CityVO>();
					}
					int j = 0;
					for (String code : codes) {
						if (cityMap.get(code.toUpperCase()) == null) {
							if (!("").equals(invalidCodes.toString())) {
								invalidCodes.append(", ");
							}
							invalidCodes.append(code.toUpperCase());
						} else {
							cityvo = cityMap.get(code);
							if (!((cityNames[j].toUpperCase()).equals(cityvo
									.getCityName().toUpperCase()))) {
								log.log(Log.INFO,
										"Inside IF mismatch exists >>>>",
										cityNames, j);
								if (cityMap.get(code.toUpperCase()) == null) {
									if (!("").equals(invalidCodeandName
											.toString())) {
										invalidCodeandName.append(", ");
									}
									invalidCodeandName.append(code
											.toUpperCase());
									ErrorVO errorVO = new ErrorVO(
											"mailtracking.mra.defaults.maintainproratefactors.msg.err.codeandnamemismatch");
									errorVO
											.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(errorVO);
								}
							}
							j++;
						}
						if (!("").equals(invalidCodeandName.toString())) {
							log.log(Log.INFO,
									"Inside invalid name not empty >>>>",
									invalidCodeandName);
							ErrorVO errorVO = new ErrorVO(
									"mailtracking.mra.defaults.maintainproratefactors.msg.err.codeandnamemismatch",
									new Object[] { invalidCodeandName
											.toString() });
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
						}
					}
				}
			}
		}
		return errors;
	}

	/**
	 * Method to validate form variables
	 * 
	 * @param maintainProrateFactorsForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm) {
		log.entering(CLASS_NAME, "validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (maintainProrateFactorsForm.getDestinationCityCode() != null
				&& maintainProrateFactorsForm.getDestinationCityCode().length > 0) {
			/*
			 * String[] cityCodes = maintainProrateFactorsForm
			 * .getDestinationCityCode();
			 */
			String[] fromdate = maintainProrateFactorsForm.getFromDate();
			String[] toDate = maintainProrateFactorsForm.getToDate();
			double[] factors = maintainProrateFactorsForm.getProrationFactor();
			/*
			 * for (int i = 0; i < cityCodes.length; i++) { if ((cityCodes[i] ==
			 * null || cityCodes[i].trim().length() == 0)) { ErrorVO errorVO =
			 * new ErrorVO(
			 * "mailtracking.mra.defaults.maintainproratefactors.msg.err.citycodesmandatory");
			 * errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 * errors.add(errorVO); break; } }
			 */
			/*
			 * for (int i = 0; i < factors.length; i++) { if (factors[i] == 0.0) {
			 * log.log(Log.INFO, "Row with no factor found"); ErrorVO errorVO =
			 * new ErrorVO(
			 * "mailtracking.mra.defaults.maintainproratefactors.msg.err.factormandatory");
			 * errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 * errors.add(errorVO); break; } }
			 */
			for (int i = 0; i < factors.length; i++) {
				if ((!(EMPTY).equals(fromdate[i]))
						&& (!(EMPTY).equals(toDate[i]))) {
					if (fromdate[i] != null && fromdate[i].trim().length() > 0) {
						if (!fromdate[i].equals(toDate[i])) {
							if (DateUtilities.isGreaterThan(fromdate[i],
									toDate[i], DATEFORMAT)) {
								ErrorVO errorVO = new ErrorVO(DATERANGEERR);
								errorVO
										.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
							}
						}
					}
				}
			}
		}
		log.exiting(CLASS_NAME, "validateForm");
		return errors;
	}

	/**
	 * Method to update form
	 * 
	 * @param maintainProrateFactorsForm
	 * @param maintainProrateFactorsSession
	 */
	private void updateDetails(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm,
			MaintainMraProrateFactorsSession maintainProrateFactorsSession) {
		log.entering(CLASS_NAME, "updateDetails");
		log.log(Log.INFO, "Session before updation ",
				maintainProrateFactorsSession.getFactors());
		String[] destinationCityCodes = maintainProrateFactorsForm
				.getDestinationCityCode();
		double[] factors = maintainProrateFactorsForm.getProrationFactor();
		String[] sources = maintainProrateFactorsForm
				.getProrationFactorSource();
		String[] status = maintainProrateFactorsForm.getProrationFactorStatus();
		String[] fromDates = maintainProrateFactorsForm.getFromDate();
		String[] toDates = maintainProrateFactorsForm.getToDate();
		String[] operationFlags = maintainProrateFactorsForm.getOperationFlag();
		int[] sequenceNumbers = maintainProrateFactorsForm.getSequenceNumber();
		ArrayList<ProrationFactorVO> prorationFactorVOs = new ArrayList<ProrationFactorVO>();
		if (operationFlags != null) {
			int index = 0;
			log.log(Log.FINEST, "OPERATION FLAGS LENGTH-->",
					operationFlags.length);
			for (String operationFlag : operationFlags) {
				if (index != operationFlags.length - 1) {
					log.log(Log.FINEST, "OPERATION FLAG-->", operationFlag);
					ProrationFactorVO prorationFactorVO = new ProrationFactorVO();
					if (OPERATION_FLAG_INSERT.equals(operationFlag)
							|| OPERATION_FLAG_DELETE.equals(operationFlag)) {
						prorationFactorVO.setOperationFlag(operationFlag);
					} else if (!"NOOP".equals(operationFlag)
							&& !"N".equals(operationFlag)) {
						prorationFactorVO
								.setOperationFlag(OPERATION_FLAG_UPDATE);
					}
					LogonAttributes logonAttributes = getApplicationSession()
							.getLogonVO();
					prorationFactorVO.setCompanyCode(logonAttributes
							.getCompanyCode());
					prorationFactorVO
							.setDestinationCityCode(destinationCityCodes[index]
									.toUpperCase());
					prorationFactorVO.setLastUpdatedUser(logonAttributes
							.getUserId());
					prorationFactorVO
							.setOriginCityCode(maintainProrateFactorsForm
									.getOriginCityCode().toUpperCase());
					prorationFactorVO.setProrationFactor(factors[index]);
					prorationFactorVO.setProrationFactorSource(sources[index]
							.toUpperCase());
					prorationFactorVO.setProrationFactorStatus(status[index]
							.toUpperCase());
					prorationFactorVO.setSequenceNumber(sequenceNumbers[index]);
					if (fromDates[index] != null
							&& fromDates[index].trim().length() > 0) {
						prorationFactorVO.setFromDate(new LocalDate(NO_STATION,
								NONE, false).setDate(fromDates[index]));
					}
					if (toDates[index] != null
							&& toDates[index].trim().length() > 0) {
						prorationFactorVO.setToDate(new LocalDate(NO_STATION,
								NONE, false).setDate(toDates[index]));
					}
					prorationFactorVOs.add(prorationFactorVO);
					++index;
				}
			}
			maintainProrateFactorsSession.setFactors(prorationFactorVOs);
		}
	}
}
