/*
 * ShowDsnPopUpCommand.java Created on Oct-28-2008 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.s
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;

/**
 * @author A-3434
 * ShowDsnPopUpCommand
 * extends BaseCommand
 */
 /* Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Oct-23-2008 a-3434 Created
 */

public class ShowDsnPopUpCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "ShowDsnPopUpCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String ACTION_SUCCESS = "action_success";
	private static final String DSNPOPUP_MODULE = "mailtracking.mra.defaults";

	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";

	private static final String MAILSUBCLASS_SAL = "SAL";

	private static final String MAILSUBCLASS_ULD = "UL";

	private static final String MAILSUBCLASS_SV = "SV";

	private static final String SCREENSTATUS_ONNEXT="nonext";

	private static final String SCREENSTATUS_NEXT="next";
	/**
	 * *Oct-23-2008,a-3434
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *             Execute Method for showing Dsn Pop UP
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, MODULE_NAME);
		Page<DSNPopUpVO> despatchLovVOs = null;
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		DSNPopUpSession dSNPopUpSession = getScreenSession( DSNPOPUP_MODULE,DSNPOPUP_SCREENID);
		CN66DetailsForm cN66DetailsForm = (CN66DetailsForm) invocationContext.screenModel;

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<DSNPopUpVO> despatchLovVOss = new ArrayList<DSNPopUpVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOsForadding=session.getCn66Details();

		AirlineCN66DetailsVO airlineCN66DetailsVO=airlineCN66DetailsVOsForadding.get(airlineCN66DetailsVOsForadding.size()-1);

		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		String dsnDate="";
		Double  weight=0.0;
		Double  rates=0.0;


		Integer count=cN66DetailsForm.getCount();
		log.log(Log.INFO, "count ---", count);
		String[] dsnNum=cN66DetailsForm.getDespatchNumber();
		String dsnNumber= dsnNum[count-1];
		log.log(Log.FINEST, "despatchNumber---", dsnNumber);
		String carriageFrom = cN66DetailsForm.getCarriageFrom();
		String carriageTo = cN66DetailsForm.getCarriageTo();
		String[] category = cN66DetailsForm.getMailCategoryCode();
		String[] origin = cN66DetailsForm.getOrigin();
		String[] destination = cN66DetailsForm.getDestination();
		String[] flightNumber = cN66DetailsForm.getFlightNumber();
		String[] carrierCode = cN66DetailsForm.getCarrierCode();
		String[] despatchDate = cN66DetailsForm.getDespatchDate();
		String[]totalCpWeights = cN66DetailsForm.getTotalCpWeight();
		String[] totalLcWeights = cN66DetailsForm.getTotalLcWeight();
		String[] totalSalWeights=cN66DetailsForm.getTotalSalWeight();
		String[] totalUldWeights=cN66DetailsForm.getTotalUldWeight();
		String[] totalSvWeights=cN66DetailsForm.getTotalSvWeight();
		String[] rate=cN66DetailsForm.getRate();
		String[] amounts=cN66DetailsForm.getAmount();
		String[] operationFlags = cN66DetailsForm.getOperationFlag();
		String[] sequenceNumber = cN66DetailsForm.getSequenceNumber();
		try{
			airlineCN66DetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			airlineCN66DetailsVO.setCarriageFrom(carriageFrom);
			airlineCN66DetailsVO.setCarriageTo(carriageTo);
			airlineCN66DetailsVO.setMailCategoryCode(category[count-1]);
			airlineCN66DetailsVO.setOrigin(origin[count-1]);
			airlineCN66DetailsVO.setDestination(destination[count-1]);
			airlineCN66DetailsVO.setFlightNumber(flightNumber[count-1]);
			airlineCN66DetailsVO.setCarrierCode(carrierCode[count-1]);
			airlineCN66DetailsVO.setCurCod(cN66DetailsForm.getBlgCurCode());
			airlineCN66DetailsVO.setSequenceNumber(Integer
					.parseInt(sequenceNumber[count-1]));
			airlineCN66DetailsVO.setDespatchSerialNo(dsnNumber);
			airlineCN66DetailsVO.setOperationFlag(operationFlags[count-1]);
			log.log(Log.FINEST, "operationFlags[count-1]---", operationFlags,
					count);
			if((despatchDate[count-1]!=null)&& (despatchDate[count-1].length()>0)){

				airlineCN66DetailsVO.setDespatchDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, false)
				.setDate(despatchDate[count-1]));
			}
			log.log(Log.FINEST, "despatchDate---", airlineCN66DetailsVO.getDespatchDate());
			log.log(Log.FINEST, "despatchNumber---", airlineCN66DetailsVO.getDespatchSerialNo());
			if(totalCpWeights[count-1]!= null && totalCpWeights[count-1].length() > 0){

				Double totalCpWeight=Double.parseDouble(totalCpWeights[count-1]);
				if(totalCpWeight!=0.0){
					airlineCN66DetailsVO.setTotalWeight(totalCpWeight);
					airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_CP);

				}
			}
			if(totalLcWeights[count-1]!= null && totalLcWeights[count-1].length() > 0){
				Double totalLcWeight=Double.parseDouble(totalLcWeights[count-1]);
				if(totalLcWeight!=0.0){
					airlineCN66DetailsVO.setTotalWeight(totalLcWeight);
					airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_LC);
				}

			}
			/*if(totalSalWeights[count-1]!= null && totalSalWeights[count-1].length() > 0){
    			Double totalSalWeight=Double.parseDouble(totalSalWeights[count-1]);
    			if(totalSalWeight!=0.0){
    			airlineCN66DetailsVO.setTotalWeight(totalSalWeight);
    			airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_SAL);
    			}
    		}if(totalUldWeights[count-1]!= null && totalUldWeights[count-1].length() > 0){
				Double totalUldWeight=Double.parseDouble(totalUldWeights[count-1]);
				if(totalUldWeight!=0.0){
					airlineCN66DetailsVO.setTotalWeight(totalUldWeight);
					airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_ULD);
				}
			}*/
			if(totalSvWeights[count-1]!= null && totalSvWeights[count-1].length() > 0){
				Double totalSvWeight=Double.parseDouble(totalSvWeights[count-1]);
				if(totalSvWeight!=0.0){
					airlineCN66DetailsVO.setTotalWeight(totalSvWeight);
					airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_SV);
				}
			}


			weight=airlineCN66DetailsVO.getTotalWeight();
			log.log(Log.FINEST, "TotalWeight -----", airlineCN66DetailsVO.getTotalWeight());
			log.log(Log.FINEST, "subclass -----", airlineCN66DetailsVO.getMailSubClass());
			if(rate[count-1]!= null && rate[count-1].length() > 0){
				airlineCN66DetailsVO.setRate(Double.parseDouble(rate[count-1]));
			}
			rates=airlineCN66DetailsVO.getRate();
			Double amount = weight*rates;
			if (amount != 0.0) {
				Money amt=CurrencyHelper.getMoney(cN66DetailsForm.getBlgCurCode());
				amt.setAmount(amount);
				airlineCN66DetailsVO.setAmount(amt);

			}

		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		log.log(Log.INFO, "airlineCN66DetailsVO -in showCommand--",
				airlineCN66DetailsVO);
		try {
			despatchLovVOs = mailTrackingMRADelegate.findDsnSelectLov(
					logonAttributes.getCompanyCode(), dsnNumber, dsnDate, 1);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		dSNPopUpSession.setDespatchDetails(despatchLovVOs);
		if(despatchLovVOs != null && !despatchLovVOs.isEmpty()){
		for(DSNPopUpVO dSNPopUpVO :despatchLovVOs){

			despatchLovVOss.add(dSNPopUpVO);

		}	
		}

		if(despatchLovVOss==null || despatchLovVOss.size() == 0){

			airlineCN66DetailsVO.setCsgdocnum(null);
			airlineCN66DetailsVO.setCsgseqnum(-1);
			airlineCN66DetailsVO.setPoaCode(null);
			log.log(Log.INFO, "if --despatchLovVOs ---", despatchLovVOss);
			cN66DetailsForm.setShowDsnPopUp("false");

		}
		else {
			if (despatchLovVOss.size() == 1 ) {
				for(DSNPopUpVO dSNPopUpVO :despatchLovVOss){

					airlineCN66DetailsVO.setCsgdocnum(dSNPopUpVO.getCsgdocnum());
					airlineCN66DetailsVO.setCsgseqnum(dSNPopUpVO.getCsgseqnum());
					airlineCN66DetailsVO.setPoaCode(dSNPopUpVO.getGpaCode());
					log.log(Log.INFO, "BillingBasis ---", dSNPopUpVO.getBlgBasis());
					airlineCN66DetailsVO.setBillingBasis(dSNPopUpVO.getBlgBasis());

				}
				log
						.log(Log.INFO, "else despatchLovVOs---->> ",
								despatchLovVOss);
				cN66DetailsForm.setShowDsnPopUp("true");

			}
			else {
				log.log(Log.INFO, "Size >1", despatchLovVOss);
				cN66DetailsForm.setShowDsnPopUp("true");
				cN66DetailsForm.setPopupFlag("popup");
			}

		}

		if(!(SCREENSTATUS_ONNEXT.equals(cN66DetailsForm.getScreenStatus())
				||SCREENSTATUS_NEXT.equals(cN66DetailsForm.getScreenStatus()))){
			session.setAirlineCN66DetailsVO(airlineCN66DetailsVO);
		}


		session.setCn66Details(airlineCN66DetailsVOsForadding);
		log.log(Log.INFO, "ShowDsnPopUp ----->", cN66DetailsForm.getShowDsnPopUp());
		log.log(Log.INFO, "airlineCN66DetailsVOsForadding ---",
				airlineCN66DetailsVOsForadding);
		invocationContext.target = ACTION_SUCCESS;
	}
}


