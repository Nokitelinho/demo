/*
 * ViewExceptionsCommand.java Created on NOV 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * 
 * Exceptions
 * 
 * @author A-3429
 */
public class ViewExceptionsCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "ViewExceptionsCommand";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String LISTPRORATIONEXCEPTION_SCREEN = "mailtracking.mra.defaults.listmailprorationexceptions";

	/**
	 * Screen ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	/**
	 * target action
	 */
	private static final String EXCEPTION_SUCCESS = "exceptions_success";

	/**
	 * LIST_FAILURE
	 */
	private static final String EXCEPTION_FAILURE = "exceptions_failure";

	/**
	 * target action
	 */
	private static final String SCREEN_STATUS = "listproration";
	
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
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ListProrationExceptionsSession listExceptionSession = getScreenSession(
				MODULE_NAME, LISTPRORATIONEXCEPTION_SCREEN);
		ViewFlightSectorRevenueSession sectorRevenuesession = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ViewMailFlightSectorRevenueForm sectorRevenueform = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		double totalGrossWeight = 0.0D;
		double totalWeightCharge = 0.0D;
		double totalNetRevenue = 0.0D;
		int count = Integer.parseInt(sectorRevenueform.getCount());
		ArrayList<SectorRevenueDetailsVO> sectorRevenueDetailsVos = (ArrayList<SectorRevenueDetailsVO>) sectorRevenuesession
				.getFlightSectorRevenueDetails();
		SectorRevenueDetailsVO sectorRevenueDetailsVo = sectorRevenueDetailsVos
				.get(count);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		if ("D".equalsIgnoreCase(sectorRevenueDetailsVo.getErrorPresent())) {
			log
					.log(1,
							"Inside MequalsIgnoreCase(sectorRevenueDetailsVo.getErrorPresent");
			errorVO = new ErrorVO(
					"mailtracking.mra.defaults.err.selectedrowdoesnothaveexceptions");
			errors.add(errorVO);

		}
		if (errors != null && errors.size() > 0) {
			 Money totWeightCharge=null;				 
			 Money totnetreven=null;
			 try {
				 totWeightCharge = CurrencyHelper.getMoney(CURRENCY);			 
				 totnetreven = CurrencyHelper.getMoney(CURRENCY);
			 } catch (CurrencyException e) {
					// TODO Auto-generated catch block
					e.getErrorCode();
				}
			 if (sectorRevenueDetailsVos != null && sectorRevenueDetailsVos.size() > 0) {
					for (SectorRevenueDetailsVO detailsVO : sectorRevenueDetailsVos) {
							totalGrossWeight += detailsVO.getGrossWeight();
							totalWeightCharge += detailsVO.getWeightChargeBase().getAmount();
							totalNetRevenue += detailsVO.getNetRevenue().getAmount();
							totWeightCharge.setAmount(totalWeightCharge);			 
							totnetreven.setAmount(totalNetRevenue);
						
				}
			 }
			 sectorRevenueform.setTotGrossWeight(round(totalGrossWeight, 3));
			 sectorRevenueform.setTotWeightCharge(totWeightCharge.getAmount());
			 sectorRevenueform.setTotNetRevenue(totnetreven.getAmount());
			log
					.log(1,
							"Inside MequalsIgnoreCase(sectorRevenueDetailsVo.getErrorPresent");
			sectorRevenuesession.setListStatus(SCREEN_STATUS);
			invocationContext.addAllError(errors);
			invocationContext.target = EXCEPTION_FAILURE;
			return;
		}
		log.log(1, "Before inserting in to filter VO");
		ProrationExceptionsFilterVO proExpFilterVO = new ProrationExceptionsFilterVO();

		proExpFilterVO.setCompanyCode(companyCode);
		proExpFilterVO.setDispatchNo(sectorRevenueDetailsVo.getDsn());
		proExpFilterVO.setBlgbas(sectorRevenueDetailsVo.getBlgbas());
		proExpFilterVO.setCsgdocnum(sectorRevenueDetailsVo.getCsgdocnum());
		proExpFilterVO.setCsgseqnum(sectorRevenueDetailsVo.getCsgseqnum());
		proExpFilterVO.setPoaCode(sectorRevenueDetailsVo.getPoaCode());
		LocalDate dateTo = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		LocalDate newDateTo = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		String toDate = dateTo.toDisplayDateOnlyFormat();

		proExpFilterVO.setToDate(newDateTo.setDate(toDate));
		log.log(Log.FINE, "FilterVO=====>>>>>", proExpFilterVO);
		listExceptionSession.setProrationExceptionFilterVO(proExpFilterVO);
		log.log(Log.FINE, "maintainCCAFilterVO----->", proExpFilterVO);
		sectorRevenuesession.setListStatus(SCREEN_STATUS);
		invocationContext.target = EXCEPTION_SUCCESS;
		
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
