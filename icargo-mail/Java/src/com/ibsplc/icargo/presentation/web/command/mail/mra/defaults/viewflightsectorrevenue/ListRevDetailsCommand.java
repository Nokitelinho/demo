/*
 * ListRevDetailsCommand.java Created on Aug 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3429
 * 
 */
public class ListRevDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ListRevDetailsCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREENID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	/**
	 * Target action
	 */
	private static final String LIST_SUCCESS = "list_success";

	/**
	 * Target action
	 */
	private static final String LIST_FAILURE = "list_failure";

	/**
	 * For Error Tags
	 */
	private static final String NO_RESULTS_FOUND = "mra.defaults.msg.err.noresultsfound";

	/**
	 * For Error Tags
	 */
	private static final String PAYFLAG = "T";

	private static final String CLOSED = "CLOSED";

	private static final String OPEN = "OPEN";

	private static final String ACCOUNTED = "ACCOUNTED";

	private static final String PARTIALLY_ACCOUNTED = "PARTIALLY ACCOUNTED";

	private static final String SECTOR_MANDATORY = "mra.defaults.msg.err.sectormandatory";
	private static final String CURRENCY = "NZD";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ViewFlightSectorRevenueSession session = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREENID);
		ViewMailFlightSectorRevenueForm form = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		double totalGrossWeight = 0.0D;
		double totalWeightCharge = 0.0D;
		double totalNetRevenue = 0.0D;
		boolean flag = true;
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			log.log(Log.INFO, "errors-------->", invocationContext.getErrors());
			invocationContext.target = LIST_FAILURE;
			return;
			
		} else if (("Y").equals(form.getDuplicateFlightFlag())) {
			invocationContext.target = LIST_FAILURE;
			return;
		} else {
			log.log(Log.INFO, "duplicateFlightFlag----->", form.getDuplicateFlightFlag());
			log.log(Log.INFO, "Entering Listing ...");
			Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();

			if (!(form.getSector() != null && form.getSector().trim().length() > 0)) {
				log.log(Log.FINE, "!!!sector mandatory check");
				ErrorVO errorVO = new ErrorVO(SECTOR_MANDATORY);
				errorVos.add(errorVO);
			}
			if (errorVos != null && errorVos.size() > 0) {
				log.log(Log.FINE, "!!!inside errors!= null");
				invocationContext.addAllError(errorVos);
				invocationContext.target = LIST_FAILURE;
				return;
			} else {

				FlightValidationVO flightValidationVO = session
						.getFlightDetails();
				log
						.log(
								Log.INFO,
								"flightValidationVO in ListRevenueDetailsCommand class---->",
								flightValidationVO);
				ErrorVO error = null;
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				Collection<SectorRevenueDetailsVO> revenueDetailsVOs = new ArrayList<SectorRevenueDetailsVO>();

				FlightSectorRevenueFilterVO filterVO = new FlightSectorRevenueFilterVO();
				log.log(Log.INFO, "Flight Number from form------>", form.getFlightNo());
				log.log(Log.INFO, "Flight Date from form------->", form.getFlightDate());
				log.log(Log.INFO, "Sector---->", form.getSector());
				String sector = form.getSector();
				String[] sectors = sector.split("-");
				filterVO.setFlightCarrierCode(form.getCarrierCode());
				filterVO.setFlightNumber(form.getFlightNo());
				filterVO.setSegmentOrigin(sectors[0]);
				filterVO.setSegmentDestination(sectors[1]);
				filterVO.setPayFlag(PAYFLAG);
				LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				flightDate.setDate(form.getFlightDate());
				filterVO.setFlightDate(flightDate);

				filterVO.setFlightCarrierId(flightValidationVO
						.getFlightCarrierId());
				filterVO.setFlightSequenceNumber((int) flightValidationVO
						.getFlightSequenceNumber());
				filterVO.setCompanyCode(flightValidationVO.getCompanyCode());
				session.setRevenueDetailsFilterVO(filterVO);
				try {
					revenueDetailsVOs = new MailTrackingMRADelegate()
							.findFlightRevenueForSector(filterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);

				}
				log.log(Log.INFO, "revenueDetailsVOs.size()===>",
						revenueDetailsVOs.size());
				Money totWeightCharge=null;				 
				 Money totnetreven=null;
				 try {
					 totWeightCharge = CurrencyHelper.getMoney(CURRENCY);			 
					 totnetreven = CurrencyHelper.getMoney(CURRENCY);
				 } catch (CurrencyException e) {
						// TODO Auto-generated catch block
						e.getErrorCode();
					}
				 
				if (revenueDetailsVOs != null && revenueDetailsVOs.size() > 0) {
					for (SectorRevenueDetailsVO detailsVO : revenueDetailsVOs) {
							totalGrossWeight += detailsVO.getGrossWeight();
							totalWeightCharge += detailsVO.getWeightChargeBase().getAmount();
							totalNetRevenue += detailsVO.getNetRevenue().getAmount();
							totWeightCharge.setAmount(totalWeightCharge);			 
							totnetreven.setAmount(totalNetRevenue);
						
				}
					for (SectorRevenueDetailsVO detailsVO : revenueDetailsVOs) {
						log
								.log(Log.INFO, "detailsVO", detailsVO.getAccStatus());
						if (!("A".equals(detailsVO.getAccStatus()))) {
							flag = false;
							break;
						}
					}
					if (flag) {
						log.log(Log.INFO, "inside falg true===>");
						form.setFlightStatus(CLOSED);
						form.setFlightSectorStatus(ACCOUNTED);
					} else {
						log.log(Log.INFO, "inside falg false===>");
						form.setFlightStatus(OPEN);
						form.setFlightSectorStatus(PARTIALLY_ACCOUNTED);
					}
					log.log(Log.INFO, "weight charge base===>", totWeightCharge.getAmount());
					form.setTotGrossWeight(round(totalGrossWeight, 3));
					form.setTotWeightCharge(totWeightCharge.getAmount());
					form.setTotNetRevenue(totnetreven.getAmount());
					log.log(Log.INFO, "weight ccccccccharge", form.getTotWeightCharge());
					session.setFlightSectorRevenueDetails(revenueDetailsVOs);
					log.log(Log.INFO,
							"revenueDetailsVOs from session-------->", session.getFlightSectorRevenueDetails());
					log.log(Log.INFO, "outside no results found",
							revenueDetailsVOs);
				} else {
					log.log(Log.INFO, "inside no results found");
					session.setFlightSectorRevenueDetails(null);
					error = new ErrorVO(NO_RESULTS_FOUND);
					form.setFlightStatus(null);
					form.setFlightSectorStatus(null);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

				if (errors != null && errors.size() > 0) {
					log.log(Log.FINE, "!!!inside errors!= null");
					invocationContext.addAllError(errors);
					form.setListSegmentsFlag("Y");
					invocationContext.target = LIST_FAILURE;
					return;
				}
				form.setListSegmentsFlag("Y");
				invocationContext.target = LIST_SUCCESS;
			}
		}
	}

	/**
	 * Round a double value to a specified number of decimal places.
	 * 
	 * @Author A-3429
	 * @param val
	 *            the value to be rounded.
	 * @param places
	 *            the number of decimal places to round to.
	 * @return val rounded to places decimal places.
	 */
	private double round(double val, int places) {
		long factor = (long) Math.pow(10, places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double) tmp / factor;
	}
}
