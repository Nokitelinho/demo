/*
 * ValidateCommand.java Created on Feb 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 * 
 */
public class ValidateCommand extends BaseCommand {

	private Log log = LogFactory
			.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "ValidateCommand";

	private static final String VALIDATE_SUCCESS = "validate_success";

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
		CaptureCN51Form captureCN51Form = (CaptureCN51Form) invocationContext.screenModel;
		Collection<ErrorVO> errors = null;
		errors = validateStations(captureCN51Form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		invocationContext.target = VALIDATE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

	private Collection<ErrorVO> validateStations(CaptureCN51Form captureCN51Form) {
		log.entering(CLASS_NAME, "validateStations");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> frmerrors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> toerrors = new ArrayList<ErrorVO>();
		String operationFlag = captureCN51Form.getOperationFlag();
		if (captureCN51Form.getCarriagesFrom() != null
				&& captureCN51Form.getCarriagesFrom().length() > 0) {
			AreaDelegate areaDelegate = new AreaDelegate();
			String companyCode = getApplicationSession().getLogonVO()
					.getCompanyCode();
			Collection<String> fromStations = Arrays.asList(captureCN51Form
					.getCarriagesFrom());
			Collection<String> newFromStations = new ArrayList<String>();
			Collection<String> toStations = Arrays.asList(captureCN51Form
					.getCarriagesTo());
			Collection<String> newToStations = new ArrayList<String>();
			log.log(Log.INFO, "OLDFromStations---.>>", fromStations);
			int index = 0;
			for (String frmstn : fromStations) {
				if (index != fromStations.size() - 1) {
					if(!(("NOOP").equals(operationFlag))){
					if (!newFromStations.contains(frmstn.toUpperCase())) {
						newFromStations.add(frmstn.toUpperCase());
					}
				}
				}
				++index;
			}
			log.log(Log.INFO, "newFromStations---.>>", newFromStations);
			log.log(Log.INFO, "oldToStations---.>>", toStations);
			index = 0;
			for (String tostn : toStations) {
				if (index != toStations.size() - 1) {
					if(!(("NOOP").equals(operationFlag))){
					if (!newToStations.contains(tostn.toUpperCase())) {
						newToStations.add(tostn.toUpperCase());
					}
				}
				}
				++index;
			}
			log.log(Log.INFO, "newToStations---.>>", newToStations);
			try {
				areaDelegate.validateStationCodes(companyCode, newFromStations);
			} catch (BusinessDelegateException e) {
				frmerrors = handleDelegateException(e);
			}
			if (frmerrors != null && frmerrors.size() > 0) {
				String errorString = "";
				StringBuilder codeArray = new StringBuilder();
				for (ErrorVO error : frmerrors) {
					if (("shared.station.invalidstation").equals
							(error.getErrorCode())) {
						Object[] codes = error.getErrorData();
						for (int count = 0; count < codes.length; count++) {
							if (("").equals(errorString)) {
								errorString = String.valueOf(codes[count]);
								codeArray = codeArray.append(String
										.valueOf(codes[count]));
							} else {
								errorString = codeArray.append(",").append(
										String.valueOf(codes[count]))
										.toString();
							}
							log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
						}
						Object[] errorArray = { errorString };
						ErrorVO errorVO = new ErrorVO(
								"mra.airlinebilling.defaults.capturecn51.msg.err.invalidfromstaion",
								errorArray);
						errors.add(errorVO);
					}
				}
			}
			try {
				areaDelegate.validateStationCodes(companyCode, newToStations);
			} catch (BusinessDelegateException e) {
				toerrors = handleDelegateException(e);
			}
			if (toerrors != null && toerrors.size() > 0) {
				for (ErrorVO error : toerrors) {
					log.log(Log.INFO, "ErrorVO---->>>", error);
					if (("shared.station.invalidstation").equals
							(error.getErrorCode())) {
						Object[] codes = error.getErrorData();
						StringBuilder codeArray = new StringBuilder();
						String errorString = "";
						for (int count = 0; count < codes.length; count++) {
							if (("").equals(errorString)) {
								errorString = String.valueOf(codes[count]);
								codeArray = codeArray.append(String
										.valueOf(codes[count]));
							} else {
								errorString = codeArray.append(",").append(
										String.valueOf(codes[count]))
										.toString();
							}
							log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
						}
						Object[] errorArray = { errorString };
						ErrorVO errorVO = new ErrorVO(
								"mra.airlinebilling.defaults.capturecn51.msg.err.invalidtostaion",
								errorArray);
						errors.add(errorVO);
					}
				}
			}
		}
		log.exiting(CLASS_NAME, "validateStations");
		return errors;
	}
}
