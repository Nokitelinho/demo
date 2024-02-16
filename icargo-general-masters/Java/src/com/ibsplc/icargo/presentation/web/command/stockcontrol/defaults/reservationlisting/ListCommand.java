/*
 * ListCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 * 
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("RESERVATION LISTING");

	private static final String LIST_SUCCESS = "list_reservation_success";

	private static final String STATUS = "stockcontrol.reservation.status";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";

	private static final String LIST_FAILURE = "list_reservation_failure";

	/**
	 * execute method for handling the list action
	 * 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReservationListingForm form = (ReservationListingForm) invocationContext.screenModel;

		/*
		 * Obtain the start session
		 */
		ReservationListingSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);

		StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		/*
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		String stationCode = logonAttributes.getStationCode();
		Collection<ReservationVO> reservationVOs = new ArrayList<ReservationVO>();
		ReservationFilterVO reservationFilterVO = new ReservationFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<DocumentVO> doctype = new ArrayList<DocumentVO>();
		reservationFilterVO.setCompanyCode(companyCode);
		reservationFilterVO.setAirportCode(logonAttributes.getAirportCode());
		doctype = session.getDocumentType();
		log.log(Log.FINE, "<-----------------doctype------------->", doctype);
		if (form.getDocumentFilterType() != null
				&& form.getDocumentFilterType().trim().length() > 0) {
			// reservationFilterVO.setDocumentType(form.getDocumentFilterType().toUpperCase());
			for (DocumentVO vo : doctype) {
				if (vo.getDocumentSubTypeDes().equals(
						form.getDocumentFilterType())) {
					reservationFilterVO
							.setDocumentType(vo.getDocumentSubType());
					log
							.log(
									Log.FINE,
									"<-----------------vo.getDocumentSubType------------->",
									vo.getDocumentSubType());
				}
			}
		}
		if (form.getCustomerFilterCode() != null
				&& form.getCustomerFilterCode().trim().length() > 0) {
			reservationFilterVO.setCustomerCode(form.getCustomerFilterCode()
					.toUpperCase());
		}
		if (form.getAirlineFilterCode() != null
				&& form.getAirlineFilterCode().trim().length() > 0) {

			try {
				AirlineValidationVO airlineVO = airlineDelegate
						.validateAlphaCode(logonAttributes.getCompanyCode(),
								form.getAirlineFilterCode().toUpperCase());
				reservationFilterVO.setAirlineIdentifier(airlineVO
						.getAirlineIdentifier());
			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				errors = handleDelegateException(e);
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
				}
			}
		}
		LocalDate reserveLocalFromDate = new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, true);
		LocalDate reserveLocalToDate = new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, true);
		LocalDate expiryLocalFromDate = new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, true);
		LocalDate expiryLocalToDate = new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, true);

		if (!("").equals(form.getReservationFilterFromDate())) {
			reservationFilterVO.setReservationFromDate(reserveLocalFromDate
					.setDate(form.getReservationFilterFromDate()));
		}
		if (!("").equals(form.getReservationFilterToDate())) {
			reservationFilterVO.setReservationToDate(reserveLocalToDate
					.setDate(form.getReservationFilterToDate()));
		}
		if (!("").equals(form.getExpiryFilterFromDate())) {
			reservationFilterVO.setExpiryFromDate(expiryLocalFromDate
					.setDate(form.getExpiryFilterFromDate()));
		}
		if (!("").equals(form.getExpiryFilterToDate())) {
			reservationFilterVO.setExpiryToDate(expiryLocalToDate.setDate(form
					.getExpiryFilterToDate()));
		}

		log.log(Log.FINE, "reservationFilterVO(sent to delegate) ---> ",
				reservationFilterVO);
		if (("").equals(form.getExpiryFilterFromDate())
				&& ("").equals(form.getExpiryFilterToDate())
				&& ("").equals(form.getReservationFilterFromDate())
				&& ("").equals(form.getReservationFilterToDate())) {

			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.entersearchfilter");
			errors.add(error);
		}
		if (("").equals(form.getExpiryFilterFromDate())
				&& (!("").equals(form.getExpiryFilterToDate())
				|| ((!("").equals(form.getExpiryFilterFromDate()) 
						&& ("").equals(form
						.getExpiryFilterToDate()))))) {

			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.enterfromAndtoExpdate");
			errors.add(error);
		}
		if (("").equals(form.getReservationFilterFromDate())
				&& (!("").equals(form.getReservationFilterToDate())
				|| (!("").equals(form.getReservationFilterFromDate())
						&& ("").equals(form.getReservationFilterToDate())))) {

			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.enterfromAndtoResdate");
			errors.add(error);

		}
		if (errors != null && errors.size() > 0) {
			session.removeReservationVO();
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		try {
			reservationVOs = stockControlDefaultsDelegate
					.findReservationListing(reservationFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;

			}
		}
		if (reservationVOs.size() == 0 || reservationVOs == null) {
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.reservationlisting.noresultsfound");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			if (errors != null && errors.size() > 0) {
				session.removeReservationVO();
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
			}
		} else {
			Map<String, Collection<OneTimeVO>> oneTimes = getScreenLoadDetails(companyCode);
			Collection<OneTimeVO> prtyOnetime = new ArrayList<OneTimeVO>();
			if (oneTimes != null) {
				prtyOnetime = oneTimes.get(STATUS);
				session.setOnetime(prtyOnetime);
			}
			for (ReservationVO vo : reservationVOs) {
				for (OneTimeVO oneTimeVO : prtyOnetime) {
					if (vo.getDocumentStatus() != null
							&& vo.getDocumentStatus().trim().length() > 0) {
						if (vo.getDocumentStatus().trim().equals(
								oneTimeVO.getFieldValue())) {
							vo.setDocumentStatus(oneTimeVO
									.getFieldDescription());
							log
									.log(
											Log.FINE,
											"<-----------------oneTimeVO.getFieldDescription()------------->",
											oneTimeVO.getFieldDescription());
						}
					}
				}
			}
			for (ReservationVO vo : reservationVOs) {
				for (DocumentVO documentVO : doctype) {
					if (documentVO.getDocumentSubType().equals(
							vo.getDocumentType())) {
						vo.setDocumentType(documentVO.getDocumentSubTypeDes());
						log
								.log(
										Log.FINE,
										"<-----------------vo.getDocumentSubType------------->",
										vo.getDocumentSubType());
					}
				}
			}
			session.setReservationVO(reservationVOs);
		}
		log.log(Log.FINE, "reservationVOs(set in session) ---> ",
				reservationVOs);
		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListStockCommand", "execute");

	}

	/**
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add(STATUS);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldTypes);

		} catch (BusinessDelegateException businessDelegateException) {
			log
					.log(Log.FINE,
							"<-----------------BusinessDelegateException------------->");
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
