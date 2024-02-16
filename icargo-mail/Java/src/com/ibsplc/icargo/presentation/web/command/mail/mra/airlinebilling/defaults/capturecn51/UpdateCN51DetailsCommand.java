/*
 * UpdateCN51DetailsCommand.java Created on DEC 04, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 */
public class UpdateCN51DetailsCommand   extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "UpdateCN51DetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String UPDATE_SUCCESS="update_success";
	private static final String UPDATE_FAILURE="update_failure";
	
	/**
	 * Execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = 
			(CaptureCN51Session) getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		AirlineCN51DetailsVO currentCN51VO = new AirlineCN51DetailsVO();
		AirlineCN51SummaryVO airlineCN51SummaryVO = new AirlineCN51SummaryVO();
		airlineCN51SummaryVO = captureCN51Session.getCn51Details();
		currentCN51VO = updateCN51Detail(airlineCN51SummaryVO,
				captureCN51Session.getCurrentCn51Detail(),captureCN51Form);
		captureCN51Session.setCurrentCn51Detail(currentCN51VO);
		log.log(Log.FINE, "currentCN51VO-", currentCN51VO);
		invocationContext.target=UPDATE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	
	/**
	 * updateCN51Detail
	 * @param currentCN51VO
	 * @param captureCN51Form
	 * @return
	 */
	private AirlineCN51DetailsVO updateCN51Detail(AirlineCN51SummaryVO airlineCN51SummaryVO,
			AirlineCN51DetailsVO currentCN51VO,CaptureCN51Form captureCN51Form) {

		double weight = 0.00;	
		double rate	= 0.00;
		double totalAmount = 0;
		AirlineCN51DetailsVO currentCN51DetailVO = new AirlineCN51DetailsVO();
		currentCN51DetailVO = currentCN51VO;
		if(airlineCN51SummaryVO != null) {
			currentCN51DetailVO.setCompanycode(airlineCN51SummaryVO.getCompanycode());
			currentCN51DetailVO.setAirlineidr(airlineCN51SummaryVO.getAirlineidr());
			currentCN51DetailVO.setInvoicenumber(airlineCN51SummaryVO.getInvoicenumber());
			currentCN51DetailVO.setClearanceperiod(airlineCN51SummaryVO.getClearanceperiod());
			currentCN51DetailVO.setListingcurrencycode(airlineCN51SummaryVO.getListingCurrency());
			currentCN51DetailVO.setContractcurrencycode(airlineCN51SummaryVO.getContractCurrencycode());
		}
		if(captureCN51Form.getCarriagesFrom() != null && 
				captureCN51Form.getCarriagesFrom().trim().length() > 0) {
			currentCN51DetailVO.setCarriagefrom(captureCN51Form.getCarriagesFrom());
		}
		if(captureCN51Form.getCarriagesTo() != null && 
				captureCN51Form.getCarriagesTo().trim().length() > 0) {
			currentCN51DetailVO.setCarriageto(captureCN51Form.getCarriagesTo());
		}
		if(captureCN51Form.getCategories() != null && 
				captureCN51Form.getCategories().trim().length() > 0) {
			currentCN51DetailVO.setMailcategory(captureCN51Form.getCategories());
		}
		if(captureCN51Form.getWtLCAO() != null && 
				captureCN51Form.getWtLCAO().trim().length() > 0) {
			if(Double.parseDouble(captureCN51Form.getWtLCAO()) != 0) {
				weight = Double.parseDouble(captureCN51Form.getWtLCAO());
				currentCN51DetailVO.setMailsubclass("LC");
			}
			currentCN51DetailVO.setTotalweight(weight);
		}
		if(captureCN51Form.getWtCP() != null && 
				captureCN51Form.getWtCP().trim().length() > 0) {
			if(Double.parseDouble(captureCN51Form.getWtCP()) != 0) {
				weight = Double.parseDouble(captureCN51Form.getWtCP());
				currentCN51DetailVO.setMailsubclass("CP");
			}
			currentCN51DetailVO.setTotalweight(weight);
		}
		if(captureCN51Form.getWtUld() != null && 
				captureCN51Form.getWtUld().trim().length() > 0) {
			if(Double.parseDouble(captureCN51Form.getWtUld()) != 0) {
				weight = Double.parseDouble(captureCN51Form.getWtUld());
				currentCN51DetailVO.setMailsubclass("UL");
			}
			currentCN51DetailVO.setTotalweight(weight);
		}
		if(captureCN51Form.getWtSv() != null && 
				captureCN51Form.getWtSv().trim().length() > 0) {
			if(Double.parseDouble(captureCN51Form.getWtSv()) != 0) {
				weight = Double.parseDouble(captureCN51Form.getWtSv());
				currentCN51DetailVO.setMailsubclass("SV");
			}
			currentCN51DetailVO.setTotalweight(weight);
		}
		//Added by A-4809 for EMS as SUBCLSGRP ...Starts
		if(captureCN51Form.getWtEms()!=null && captureCN51Form.getWtEms().trim().length()>0){
			if(Double.parseDouble(captureCN51Form.getWtEms()) != 0){
				weight = Double.parseDouble(captureCN51Form.getWtEms());
				currentCN51DetailVO.setMailsubclass("EMS");
			}
			currentCN51DetailVO.setTotalweight(weight);
		}
		//Added by A-4809 for EMS as SUBCLSGRP ...Ends		
		if(captureCN51Form.getRate() != null && 
				captureCN51Form.getRate().trim().length() > 0){
			rate = Double.parseDouble(captureCN51Form.getRate());
			currentCN51DetailVO.setApplicablerate(rate);			
		}
		if(rate != 0 && weight != 0) {
			totalAmount = getScaledValue((rate * weight),2);
			Money totalAmountInContractCurrency;
			try {
				if(currentCN51DetailVO.getContractcurrencycode() != null && 
						currentCN51DetailVO.getContractcurrencycode().trim().length() > 0) {
					totalAmountInContractCurrency = CurrencyHelper.getMoney(currentCN51DetailVO.getContractcurrencycode());
					totalAmountInContractCurrency.setAmount(totalAmount);						
					currentCN51DetailVO.setTotalamountincontractcurrency(totalAmountInContractCurrency);
				}
			} catch (CurrencyException e) {
				log.log(Log.INFO,"!!!!!!!!!!!!!! CurrencyException !!!!!!!!!!!!!!!!");
			}
		}
		
		return currentCN51DetailVO;
	}

	/**
	 * @author A-3227 RENO K ABRAHAM
	 * getScaledValue
	 * @param value
	 * @param precision
	 * @return
	 */
	private double getScaledValue(double value, int precision) {

		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
		return bigDecimal.setScale(precision,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
