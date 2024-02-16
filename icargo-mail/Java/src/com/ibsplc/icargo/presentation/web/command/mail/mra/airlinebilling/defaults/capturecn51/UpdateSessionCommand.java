/*
 * UpdateSessionCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 * 
 */
public class UpdateSessionCommand extends BaseCommand {

	private Log log = LogFactory
			.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "UpdateSessionCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	private static final String UPDATE_SUCCESS = "update_success";

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
		if (invocationContext.getErrors() == null
				|| invocationContext.getErrors().size() == 0) {
			CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
					MODULE_NAME, SCREEN_ID);
			CaptureCN51Form captureCN51Form = (CaptureCN51Form) invocationContext.screenModel;
			updateDetails(captureCN51Form, captureCN51Session);
		}
		invocationContext.target = UPDATE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

	private void updateDetails(CaptureCN51Form captureCN51Form,
			CaptureCN51Session captureCN51Session) {
		if (captureCN51Form.getCarriagesFrom() != null
				&& captureCN51Form.getCarriagesFrom().trim().length() > 0) {
			String carriagesFrom = captureCN51Form.getCarriagesFrom();
			String carriagesTo = captureCN51Form.getCarriagesTo();
			String categories = captureCN51Form.getCategories();
			String operationFlag = captureCN51Form.getOperationFlag();
			String wtLc = captureCN51Form.getWtLCAO();
			String rate = captureCN51Form.getRate();
			String amt = captureCN51Form.getTotalAmount();
			String wtCp = captureCN51Form.getWtCP();
			
			String wtSal = captureCN51Form.getWtSal();
			String wtUld = captureCN51Form.getWtUld();
			String wtSV = captureCN51Form.getWtSv();
			String wtEMS = captureCN51Form.getWtEms();
			double amount = 0.0;
			double c51Amount= 0.0;
			
			//double[] rtCp = captureCN51Form.getRateCP();
			//double[] atCp = captureCN51Form.getAmountCP();
			String sequenceNumbers = captureCN51Form.getSequenceNumber();
			AirlineCN51SummaryVO airlineCn51SummaryVo = captureCN51Session
					.getCn51Details();
			if (airlineCn51SummaryVo == null) {
				airlineCn51SummaryVo = new AirlineCN51SummaryVO();
				airlineCn51SummaryVo.setCompanycode(getApplicationSession()
						.getLogonVO().getCompanyCode());
				
			}
			AirlineCN51DetailsVO airlineCN51DetailsVO = null;
			Page<AirlineCN51DetailsVO> airlineCn51DetailsVos =new Page<AirlineCN51DetailsVO>(
					new ArrayList<AirlineCN51DetailsVO>(), 0, 0, 0, 0, 0,false);
			log.log(Log.FINEST, "OPERATION FLAGS LENGTH-->", operationFlag);
			int index = 0;
			try{
					log.log(Log.FINEST, "OPERATION FLAG-->", operationFlag);
					if(!(("NOOP").equals(operationFlag))){
					if (!OPERATION_FLAG_DELETE.equals(operationFlag)) {
						airlineCN51DetailsVO = new AirlineCN51DetailsVO();
						airlineCN51DetailsVO.setOperationFlag(operationFlag);
						airlineCN51DetailsVO
								.setCompanycode(airlineCn51SummaryVo
										.getCompanycode());
						airlineCN51DetailsVO
								.setInterlinebillingtype(airlineCn51SummaryVo
										.getInterlinebillingtype());
						airlineCN51DetailsVO.setAirlineidr(airlineCn51SummaryVo
								.getAirlineidr());
						airlineCN51DetailsVO
								.setInvoicenumber(airlineCn51SummaryVo
										.getInvoicenumber());
						airlineCN51DetailsVO
								.setClearanceperiod(airlineCn51SummaryVo
										.getClearanceperiod());
						airlineCN51DetailsVO.setAirlineCode(captureCN51Form
								.getAirlineCode());
						airlineCN51DetailsVO
								.setCarriagefrom(carriagesFrom
										.toUpperCase());
						airlineCN51DetailsVO.setCarriageto(carriagesTo
								.toUpperCase());
						airlineCN51DetailsVO.setMailcategory(categories
								.toUpperCase());
						airlineCN51DetailsVO.setSequenceNumber(Integer
								.parseInt(sequenceNumbers));
						if(wtLc!=null && wtLc.length() > 0){
						if (Double.parseDouble(wtLc) !=0.0) {
							airlineCN51DetailsVO.setMailsubclass("LC");
							airlineCN51DetailsVO.setTotalweight(Double.parseDouble(wtLc));
							log.log(Log.INFO, "wtlc not null ", Double.parseDouble(wtLc));
							
						} 
						}
						if(wtCp!=null && wtCp.length() > 0){
						if (Double.parseDouble(wtCp) !=0.0)
                        {
							airlineCN51DetailsVO.setMailsubclass("CP");
							airlineCN51DetailsVO.setTotalweight(Double.parseDouble(wtCp));
							
							//airlineCN51DetailsVO.setApplicablerate(Double.parseDouble(rate));
						}
						}
						/*if(wtSal[index]!=null && wtSal[index].length() > 0){
						if (Double.parseDouble(wtSal[index]) !=0.0)
                        {
							airlineCN51DetailsVO.setMailsubclass("SL");
							airlineCN51DetailsVO.setTotalweight(Double.parseDouble(wtSal));
							
							//airlineCN51DetailsVO.setApplicablerate(Double.parseDouble(rate));
						}
						}*/
						if(wtUld!=null && wtUld.length() > 0){
						if (Double.parseDouble(wtUld) !=0.0)
                        {
							airlineCN51DetailsVO.setMailsubclass("UL");
							airlineCN51DetailsVO.setTotalweight(Double.parseDouble(wtUld));
							
							//airlineCN51DetailsVO.setApplicablerate(Double.parseDouble(rate));
						}
						}
						if(wtSV!=null && wtSV.length() > 0){
						if (Double.parseDouble(wtSV) !=0.0)
                        {
							airlineCN51DetailsVO.setMailsubclass("SV");
							airlineCN51DetailsVO.setTotalweight(Double.parseDouble(wtSV));
							
							//airlineCN51DetailsVO.setApplicablerate(Double.parseDouble(rate));
						}
						}
						//Added by A-4809 for EMS as SUBCLSGRP ...Starts
						if(wtEMS!=null && wtEMS.length()>0){
							if (Double.parseDouble(wtEMS) !=0.0){
								airlineCN51DetailsVO.setMailsubclass("EMS");
								airlineCN51DetailsVO.setTotalweight(Double.parseDouble(wtEMS));								
							}
						}
						//Added by A-4809 for EMS as SUBCLSGRP ...Ends
						airlineCN51DetailsVO.setApplicablerate(Double.parseDouble(rate));
						if (amt != null
								&& amt.length() > 0) {
							log.log(Log.INFO, "amt.... ", amt);
								//if (amount != 0.0) {
								//Money amts=CurrencyHelper.getMoney(captureCN51Form.getBlgCurCode());
								//amts.setAmount(amount);
							//airlineCN51DetailsVO.setTotalamountincontractcurrency(amts);
								Double weight=airlineCN51DetailsVO.getTotalweight();
								Double rates=airlineCN51DetailsVO.getApplicablerate();
								amount = weight*rates;
								if (amount != 0.0) {
									Money amts=CurrencyHelper.getMoney(captureCN51Form.getBlgCurCode());
									amts.setAmount(amount);
								airlineCN51DetailsVO.setTotalamountincontractcurrency(amts);
								}
								 c51Amount+=airlineCN51DetailsVO.getTotalamountincontractcurrency().getAmount();
								 log.log(Log.INFO, "c51Amount.... ", c51Amount);
						}
						airlineCn51DetailsVos.add(airlineCN51DetailsVO);
					}
					++index;
				}
			}
			catch(CurrencyException currencyException){
				log.log(Log.INFO,"CurrencyException found");
			}
			airlineCn51SummaryVo.setC51Amount(c51Amount);
			airlineCn51SummaryVo.setCn51DetailsPageVOs(airlineCn51DetailsVos);
		}
		if(captureCN51Session.getCn51Details()!=null){
		captureCN51Session.getCn51Details().setAirlinecode(
				captureCN51Form.getAirlineCode());
		
		log.log(Log.INFO, "updated Session", captureCN51Session.getCn51Details().getCn51DetailsPageVOs());
		log.log(Log.INFO, "updated Session....", captureCN51Session.getCn51Details());
		}
	}
}
