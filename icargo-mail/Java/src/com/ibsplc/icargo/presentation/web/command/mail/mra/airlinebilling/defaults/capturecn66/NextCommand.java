/*
 * NextCommand.java Created on Jan 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */
public class NextCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling NextCommand");

	private static final String CLASS_NAME = "Next";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String ACTION_FAILURE = "screenload_failure";

	private static final String SCREENSTATUS_NEXT = "next";

	private static final String SCREENSTATUS_ONNEXT = "nonext";

	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";
	
	private static final String MAILSUBCLASS_SAL = "SAL";
	
	private static final String MAILSUBCLASS_ULD = "UL";
	
	private static final String MAILSUBCLASS_SV = "SV";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CN66DetailsForm form = (CN66DetailsForm) invocationContext.screenModel;
		HashMap<String, Collection<AirlineCN66DetailsVO>> modifiedcn66map = new HashMap<String, Collection<AirlineCN66DetailsVO>>();
		ArrayList<String> keyvals = null;
		ArrayList<AirlineCN66DetailsVO> cn66s = null;
		ArrayList<AirlineCN66DetailsVO> newcn66s = new ArrayList<AirlineCN66DetailsVO>();
		int index = 0;
		String carriageFrom = form.getCarriageFrom();
		String carriageTo = form.getCarriageTo();
		String[] category = form.getMailCategoryCode();
		String[] origin = form.getOrigin();
		String[] destination = form.getDestination();
		String[] flightNumber = form.getFlightNumber();
		String[] carrierCode = form.getCarrierCode();
		String[] despatchDate = form.getDespatchDate();
		String[] despatchNumber = form.getDespatchNumber();
		String[] totalCpWeights = form.getTotalCpWeight();
		String[] totalLcWeights = form.getTotalLcWeight();
		String[] operationFlags = form.getOperationFlag();
		String[] sequenceNumber = form.getSequenceNumber();
		String[] totalSalWeights=form.getTotalSalWeight();
    	String[] totalUldWeights=form.getTotalUldWeight();
    	String[] totalSvWeights=form.getTotalSvWeight();
    	String[] rate=form.getRate();
    	String[] amounts=form.getAmount();
		double totalCpWeight = 0.0;
		double totalLcWeight = 0.0;
		double totalSalWeight = 0.0;
		double totalUldWeight = 0.0;
		double totalSvWeight = 0.0;
		double amount = 0.0;
		
		Collection<String> origins = Arrays.asList(form.getOrigin());
		Collection<String> destinations = Arrays.asList(form.getDestination());
		Collection<String> checkorigins = new ArrayList<String>();
		Collection<String> checkdests = new ArrayList<String>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> orgerrors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> desterrors = new ArrayList<ErrorVO>();
		Map<String, AirlineValidationVO> airlinevalidationVOs = null;
		for (String org : origins) {
			if (index != origins.size() - 1) {
				if (!checkorigins.contains(org)) {
					checkorigins.add(org);
				}
			}
			++index;
		}
		index = 0;
		for (String des : destinations) {
			if (index != destinations.size() - 1) {
				if (!checkdests.contains(des)) {
					checkdests.add(des);
				}
			}
			++index;
		}
		// origin - validation against station
		try {
			new AreaDelegate().validateStationCodes(getApplicationSession()
					.getLogonVO().getCompanyCode(), checkorigins);
		} catch (BusinessDelegateException e) {
			orgerrors = handleDelegateException(e);
		}
		if (orgerrors != null && orgerrors.size() > 0) {
			StringBuilder codeArray = new StringBuilder();
			String errorString = "";
			for (ErrorVO error : orgerrors) {
				log.log(Log.INFO, "ErrorVO---->>>", error);
				if (("shared.station.invalidstation").equals
						(error.getErrorCode())) {
					Object[] codes = error.getErrorData();
					for (int count = 0; count < codes.length; count++) {
						if (("").equals(errorString)) {
							errorString = String.valueOf(codes[count]);
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(
									String.valueOf(codes[count])).toString();
						}
						log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
						log.log(Log.FINE, "\n\n\nErrorString-->", errorString);
					}
					Object[] errorArray = { errorString };
					ErrorVO errorVO = new ErrorVO(
							"mra.airlinebilling.defaults.capturecn66.msg.err.invalidorigin",
							errorArray);
					errors.add(errorVO);
				}
			}
		}
		// destination - validation against station
		try {
			new AreaDelegate().validateStationCodes(getApplicationSession()
					.getLogonVO().getCompanyCode(), checkdests);
		} catch (BusinessDelegateException e) {
			desterrors = handleDelegateException(e);
		}
		if (desterrors != null && desterrors.size() > 0) {
			StringBuilder codeArray = new StringBuilder();
			String errorString = "";
			for (ErrorVO error : desterrors) {
				log.log(Log.INFO, "ErrorVO---->>>", error);
				if (("shared.station.invalidstation").equals
						(error.getErrorCode())) {
					Object[] codes = error.getErrorData();
					for (int count = 0; count < codes.length; count++) {
						if (("").equals(errorString)) {
							errorString = String.valueOf(codes[count]);
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(
									String.valueOf(codes[count])).toString();
						}
						log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
					}
					Object[] errorArray = { errorString };
					ErrorVO errorVO = new ErrorVO(
							"mra.airlinebilling.defaults.capturecn66.msg.err.invaliddest",
							errorArray);
					errors.add(errorVO);
				}
			}
		}
		if (carrierCode != null && carrierCode.length > 0) {
			Collection<String> carrierCodes = new ArrayList<String>();
			index = 0;
			for (String code : carrierCode) {
				if (index != carrierCode.length - 1) {
					if (!carrierCodes.contains(code)) {
						carrierCodes.add(code);
					}
				}
				++index;
			}
			// carrier code - validation against airline
			try {
				airlinevalidationVOs = new AirlineDelegate()
						.validateAlphaCodes(getApplicationSession()
								.getLogonVO().getCompanyCode(), carrierCodes);
			} catch (BusinessDelegateException businessDelegateException) {
				errors
						.addAll(handleDelegateException(businessDelegateException));
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			log.exiting("MAILTRACKING_LIST", "OkCommand exit");
			return;
		}
		// populating VOs from form bean
		ArrayList<AirlineCN66DetailsVO> cn66details = new ArrayList<AirlineCN66DetailsVO>();
		AirlineCN66DetailsVO cn66DetailsVo = null;
		log.log(Log.FINEST, "OPERATION FLAGS LENGTH-->", operationFlags.length);
		index = 0;
		// populating VOs from form bean
		try{
		for (String operationFlag : operationFlags) {
			if (index != operationFlags.length - 1) {
				log.log(Log.FINEST, "OPERATION FLAG-->", operationFlag);
				cn66DetailsVo = new AirlineCN66DetailsVO();
				if (OPERATION_FLAG_INSERT.equals(operationFlag)
						|| OPERATION_FLAG_DELETE.equals(operationFlag)) {
					cn66DetailsVo.setOperationFlag(operationFlag);
				} else if (!"NOOP".equals(operationFlag)
						&& !"N".equals(operationFlag)) {
					cn66DetailsVo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
				cn66DetailsVo.setSequenceNumber(Integer
						.parseInt(sequenceNumber[index]));
				cn66DetailsVo.setCarriageFrom(form.getCarriageFrom());
				cn66DetailsVo.setCarriageTo(form.getCarriageTo());
				cn66DetailsVo.setMailCategoryCode(category[index]);
				cn66DetailsVo.setOrigin(origin[index]);
				cn66DetailsVo.setDestination(destination[index]);
				//cn66DetailsVo.setFlightNumber(flightNumber[index]);
				//cn66DetailsVo.setCarrierCode(carrierCode[index]);
				cn66DetailsVo.setDespatchDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, false)
						.setDate(despatchDate[index]));
				cn66DetailsVo.setDespatchSerialNo(despatchNumber[index]);
				if (totalCpWeights[index] != null
						&& totalCpWeights[index].length() > 0) {
					totalCpWeight = Double.parseDouble(totalCpWeights[index]);
					if (totalCpWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalCpWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_CP);
					}
				}
				if (totalLcWeights[index] != null
						&& totalLcWeights[index].length() > 0) {
					totalLcWeight = Double.parseDouble(totalLcWeights[index]);
					if (totalLcWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalLcWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_LC);
					}
				}
				/*if (totalSalWeights[index] != null
						&& totalSalWeights[index].length() > 0) {
					totalSalWeight = Double.parseDouble(totalSalWeights[index]);
					if (totalSalWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalSalWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_SAL);
					}
				}*/
				if (totalUldWeights[index] != null
						&& totalUldWeights[index].length() > 0) {
					totalUldWeight = Double.parseDouble(totalUldWeights[index]);
					if (totalUldWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalUldWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_ULD);
					}
				}
				if (totalSvWeights[index] != null
						&& totalSvWeights[index].length() > 0) {
					totalSvWeight = Double.parseDouble(totalSvWeights[index]);
					if (totalSvWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalSvWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_SV);
					}
				}
				cn66DetailsVo.setRate(Double.parseDouble(rate[index]));
				if (amounts[index] != null
						&& amounts[index].length() > 0) {
					amount = Double.parseDouble(amounts[index]);
					if (amount != 0.0) {
						Money amt=CurrencyHelper.getMoney(form.getBlgCurCode());
						amt.setAmount(amount);
						cn66DetailsVo.setAmount(amt);
						
					}
				}
				/*cn66DetailsVo.setFlightCarrierIdentifier(airlinevalidationVOs
						.get(carrierCode[index]).getAirlineIdentifier());*/
				cn66details.add(cn66DetailsVo);
			}
			++index;
		}
		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		session.setCn66Details(cn66details);
		newcn66s = session.getCn66Details();
		String key = "";
		if (carriageFrom != null && carriageFrom.trim().length() > 0
				&& carriageTo != null && carriageTo.trim().length() > 0) {
			key = new StringBuilder().append(carriageFrom.trim()).append("-")
					.append(carriageTo.trim()).toString();
		}
		if (session.getCn66DetailsModifiedMap() != null
				&& session.getCn66DetailsModifiedMap().size() > 0) {
			modifiedcn66map = session.getCn66DetailsModifiedMap();
			cn66s = new ArrayList<AirlineCN66DetailsVO>(modifiedcn66map
					.get(key));
			modifiedcn66map.remove(key);
			modifiedcn66map.put(key, newcn66s);
			if (session.getKeyValues() != null
					&& session.getKeyValues().size() > 0) {
				keyvals = new ArrayList<String>(session.getKeyValues());
				index = keyvals.indexOf(key);
				if (index != keyvals.size() - 1) {
					key = keyvals.get(index + 1);
				}
				if (index + 1 == keyvals.size() - 1) {
					form.setScreenStatus(SCREENSTATUS_ONNEXT);
				} else {
					form.setScreenStatus(SCREENSTATUS_NEXT);
				}
			}
			if (modifiedcn66map.containsKey(key)) {
				cn66s = new ArrayList<AirlineCN66DetailsVO>(modifiedcn66map
						.get(key));
				if (cn66s != null && cn66s.size() > 0) {
					form.setCarriageFrom(cn66s.get(0).getCarriageFrom());
					form.setCarriageTo(cn66s.get(0).getCarriageTo());
				}
				if (modifiedcn66map.get(key) != null) {
					session
							.setCn66Details((ArrayList<AirlineCN66DetailsVO>) modifiedcn66map
									.get(key));
				}
			} else {
				session.removeCn66Details();
			}
		}
		session.setCn66DetailsModifiedMap(modifiedcn66map);
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
