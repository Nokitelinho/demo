/*
 * ScreenloadCommand.java Created on Feb 12, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-2122
 *
 */
public class ScreenloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "ScreenloadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String BILLINGTYPE_ONETIME = "mailtracking.mra.billingtype";
	private static final String STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		Double dou=0.00;
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		  LogonAttributes logonAttributes = applicationSession.getLogonVO();
		  	//Added for Unit Components weight,volume
			UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
			captureCN51Session.setWeightRoundingVO(unitRoundingVO);
			captureCN51Session.setVolumeRoundingVO(unitRoundingVO);		
			setUnitComponent(logonAttributes.getStationCode(),captureCN51Session);	

		  SharedDefaultsDelegate sharedDefaultsDelegate = new
		  SharedDefaultsDelegate(); 
		  Map<String, Collection<OneTimeVO>>
		  oneTimeValues = null; 
		  try {
		 
		  oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
		  logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
		   } catch (BusinessDelegateException businessDelegateException) {
			  log.log(Log.FINE, "*****in the exception");
//printStackTrrace()(); 
			  handleDelegateException(businessDelegateException);
		  }
		   captureCN51Session .setOneTimeValues( (HashMap<String,
		  Collection<OneTimeVO>>) oneTimeValues);
		captureCN51Form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		captureCN51Form.setLinkStatusFlag("disable");
		captureCN51Session.setCn51Details(null);
		captureCN51Session.setFilterDetails(null);
		captureCN51Form.setScreenFlg("");
		
		try{
   			Money money = CurrencyHelper.getMoney("USD");
   			money.setAmount(0.0D);
   		
   			captureCN51Form.setNetChargeMoney(money);
   			captureCN51Form.setNetSummaryMoney(money);
   			captureCN51Form.setNetWeight(dou);
   			captureCN51Form.setNetCP(dou);
   		    captureCN51Form.setNetLC(dou);
   			captureCN51Form.setNetUld(dou);
   			captureCN51Form.setNetSal(dou);
   			captureCN51Form.setNetSV(dou);
   			captureCN51Form.setNetEMS(dou);
   			}
   			catch(CurrencyException currencyException){
   				log.entering(CLASS_NAME,"CurrencyException");
   			}
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}
	/**
	 * 
	 * @return Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(CATEGORY_ONETIME);
		parameterTypes.add(BILLINGTYPE_ONETIME);
		parameterTypes.add(STATUS_ONETIME);
		return parameterTypes;
	}
	
	 private void setUnitComponent(String stationCode,
   		  CaptureCN51Session captureCN51Session){
	UnitRoundingVO unitRoundingVO = null;
	try{
		log.log(Log.FINE, "station code is ----------->>", stationCode);
		unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT);		
		log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
		captureCN51Session.setWeightRoundingVO(unitRoundingVO);
		unitRoundingVO = UnitFormatter.getStationDefaultUnit(
				stationCode, UnitConstants.VOLUME);
		captureCN51Session.setVolumeRoundingVO(unitRoundingVO);
		
	   }catch(UnitException unitException) {
//printStackTrrace()();
	   }
	
}
	
}
