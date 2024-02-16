/*
 * AddULDPoolOwnersCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.uldpoolowners;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
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
public class AddULDPoolOwnersCommand extends BaseCommand {

	private static final String ADD_SUCCESS = "add_success";

	private static final String ADD_FAILURE = "add_failure";

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

		log.entering("Add Command", "ULD POOL OWNERS");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ULDPoolOwnerVO poolOwnerVO = session.getUldPoolOwnerVO();
		Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs = poolOwnerVO
				.getPoolDetailVOs();
		if (poolOwnerDetailsVOs != null && poolOwnerDetailsVOs.size() > 0) {
			updateULDPoolOwnerDetails(poolOwnerDetailsVOs, form,poolOwnerVO);
			log.log(Log.FINE, "details vos after update----------------->",
					poolOwnerDetailsVOs);
			errors=	validateAirlineCode(poolOwnerDetailsVOs, form, companyCode);
			if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target=ADD_FAILURE;
				return;
			}
			errors=validateDateFields(poolOwnerDetailsVOs, form,applicationSession);
			if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target=ADD_FAILURE;
				return;
			}
			errors=validateEndDates(poolOwnerDetailsVOs);
			if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target=ADD_FAILURE;
				return;
			}

		} else {
			poolOwnerDetailsVOs = new ArrayList<ULDPoolOwnerDetailsVO>();

		}
		ULDPoolOwnerDetailsVO poolOwnerDetailsVO = new ULDPoolOwnerDetailsVO();
		poolOwnerDetailsVO
				.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_INSERT);
		poolOwnerDetailsVO.setCompanyCode(companyCode);
		poolOwnerDetailsVO.setPolAirlineCode("");
		poolOwnerDetailsVO.setPolFligthNumber("");
		
		poolOwnerDetailsVOs.add(poolOwnerDetailsVO);
		poolOwnerVO.setPoolDetailVOs(poolOwnerDetailsVOs);
		session.setUldPoolOwnerVO(poolOwnerVO);
		log.log(Log.FINE, "ULD Pool Owner VO after addition", poolOwnerVO);
		invocationContext.target = ADD_SUCCESS;
		return;

	}

/**
 * 
 * @param poolOwnerDetailsVOs
 * @param form
 * @param poolOwnerVO
 */
	public void updateULDPoolOwnerDetails(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form,ULDPoolOwnerVO poolOwnerVO) {
		String[] polAirline = form.getAirlineOwn();
		String[] polFlights = form.getFlightOwn();
		String[] fromDate = form.getFromDate();
		String[] toDate = form.getToDate();
		log.log(Log.FINE, "airline from form-------------->", polAirline);
		log.log(Log.FINE, "flight from form-------------->", polFlights);
		log.log(Log.FINE, "from date from form-------------->", fromDate);
		log.log(Log.FINE, "to date from form-------------->", toDate);
		int index = 0;
		for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
			if (detailsVO.getOperationFlag() == null) {
				if (hasValueChanged(detailsVO.getPolAirlineCode(),
						polAirline[index].toUpperCase())
						|| hasValueChanged(detailsVO.getPolFligthNumber(),
								polFlights[index].toUpperCase())
						|| hasValueChanged(detailsVO.getFromDate()
								.toDisplayDateOnlyFormat(), fromDate[index])
						|| hasValueChanged(detailsVO.getToDate()
								.toDisplayDateOnlyFormat(), toDate[index])) {
					detailsVO
							.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
					poolOwnerVO.setOperationFlag(ULDPoolOwnerDetailsVO.OPERATION_FLAG_UPDATE);
				}
			}
				if (!ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE
						.equals(detailsVO.getOperationFlag())) {
					
					log.log(Log.FINE,"\n\n\nInside Update");
					log.log(Log.FINE, "polAirline[index]----------------->",
							polAirline[index].toUpperCase());
					detailsVO
							.setPolAirlineCode(polAirline[index].toUpperCase());
					detailsVO.setPolFligthNumber(polFlights[index]
							.toUpperCase());
					if (fromDate[index] != null
							&& fromDate[index].trim().length() > 0) {
						LocalDate polFromDate = new LocalDate(
								getApplicationSession().getLogonVO()
										.getAirportCode(), Location.ARP, false);
						
						polFromDate.setDate(fromDate[index]);
						log.log(Log.FINE, "pol from date--------------->",
								polFromDate);
						detailsVO.setFromDate(polFromDate);
					}

					if (toDate[index] != null
							&& toDate[index].trim().length() > 0) {
						LocalDate polToDate = new LocalDate(
								getApplicationSession().getLogonVO()
										.getAirportCode(), Location.ARP, false);
					
						polToDate.setDate(toDate[index]);
						detailsVO.setToDate(polToDate);

					}
				}
			
			index++;
		}

	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	private boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue != null) {
			if (originalValue.equalsIgnoreCase(formValue)) {
				return false;
			}

		}
		return true;
	}

/**
 * 
 * @param poolOwnerDetailsVOs
 * @param form
 * @param companyCode
 * @return
 */
	private Collection<ErrorVO> validateAirlineCode(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] airlineCodes = form.getAirlineOwn();
		int index = airlineCodes.length - 1;
		ULDPoolOwnerDetailsVO currentDetailsVO = ((ArrayList<ULDPoolOwnerDetailsVO>) poolOwnerDetailsVOs)
				.get(index);
		log.log(Log.FINE, "Selected airline code---------------->",
				currentDetailsVO.getPolAirlineCode());
		if (!ULDPoolOwnerDetailsVO.OPERATION_FLAG_DELETE
				.equals(currentDetailsVO.getOperationFlag())) {
			String currentAirlineCode = airlineCodes[index].toUpperCase();
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, currentAirlineCode);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (airlineValidationVO != null) {
				currentDetailsVO.setPolAirlineIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}

/**
 * 
 * @param poolOwnerDetailsVOs
 * @param form
 * @param appSession
 * @return
 */
	private Collection<ErrorVO> validateDateFields(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs,
			ULDPoolOwnersForm form, ApplicationSession appSession) {
		String[] airlineCodes = form.getAirlineOwn();
		String[] flightNumbers = form.getFlightOwn();
		String[] fromDates = form.getFromDate();
		String[] toDates = form.getToDate();
		String[] opFlag = form.getOperationFlag();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		for (int i = 0; i < airlineCodes.length; i++) {
			int index = 0;
			for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
				if (index != i) {
					LocalDate fromDateForm = (new LocalDate(appSession
							.getLogonVO().getAirportCode(), Location.ARP, false))
							.setDate(fromDates[i]);
					LocalDate toDateForm = (new LocalDate(appSession
							.getLogonVO().getAirportCode(), Location.ARP, false))
							.setDate(toDates[i]);
					log.log(Log.FINE, "fromDateForm------------------>",
							fromDateForm);
					log.log(Log.FINE, "toDateForm-------------->", toDateForm);
					if (!OPERATION_FLAG_DELETE.equals(opFlag[i])
							&& !OPERATION_FLAG_DELETE.equals(detailsVO
									.getOperationFlag())) {
						if (detailsVO.getPolAirlineCode().equals(
								airlineCodes[i].toUpperCase())
								&& detailsVO.getPolFligthNumber().equals(
										flightNumbers[i].toUpperCase())) {

							if (!detailsVO.getFromDate().isGreaterThan(
									toDateForm)
									&& !detailsVO.getToDate().isLesserThan(
											fromDateForm)) {
								error = new ErrorVO(
										"uld.defaults.uldpoolowners.daterangeexists");
								errors.add(error);
								return errors;
							}

						}

					}

				}
				index++;
			}
			
		}
		return errors;
	}

	/**
	 * 
	 * @param poolOwnerDetailsVOs
	 * @return
	 */
	private Collection<ErrorVO> validateEndDates(
			Collection<ULDPoolOwnerDetailsVO> poolOwnerDetailsVOs) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		for (ULDPoolOwnerDetailsVO detailsVO : poolOwnerDetailsVOs) {
			if (detailsVO.getFromDate().isGreaterThan(detailsVO.getToDate())) {
				error = new ErrorVO(
						"uld.defaults.poolowners.msg.err.invalidenddates");
				errors.add(error);
			}
		}
		return errors;
	}

}
