/*
 * ScreenLoadCommand.java Created on Jan 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ScreenloadCommand");

	private static final String CLASS_NAME = "CaptureCN66ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.billingtype";
	private static final String ONE_TIME_FIELDTYPE_BLGTYP = "mailtracking.mra.invoicetype";//added by a-7929 as part of ICRD-265471
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String KEY_DESPATCH_STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	private static final String SCREENSTATUS_SCREENLOAD="screenload";
	private static final String SCREENSTATUS_CN51SCREEN="cn51screen";
	private static final String SCREENSTATUS_INVEXP="invoiceexception";
	private static final String SCREENSTATUS_CN66SCREEN="cn66screen";
	private static final String KEY_ONETIMERI ="mailtracking.defaults.registeredorinsuredcode";
	private static final String KEY_ONETIMEHNI ="mailtracking.defaults.highestnumbermail";
	/*
	 * Parameter code for system parameter - level for data import to mra
	 */
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		String dou="0.00";
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	String stationCode=getApplicationSession().getLogonVO().getStationCode();
    	//Added for Unit Components weight,volume,Modified as part of ICRD-101112
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		session.setWeightRoundingVO(unitRoundingVO);
		session.setVolumeRoundingVO(unitRoundingVO);		
		setUnitComponent(stationCode,session);	
		Map oneTimeHashMap 								= null;
		Map<String, String> systemParameterValues = null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(KEY_DESPATCH_STATUS_ONETIME);
		oneTimeActiveStatusList.add(ONE_TIME_FIELDTYPE_BLGTYP);  //Added by A-7929 s part of ICRD-265471
		AirlineCN66DetailsFilterVO filterVO=new AirlineCN66DetailsFilterVO();
		//Added as part of bug ICRD-101743 by A-5526 starts
		Page<AirlineCN66DetailsVO> vos =null;
		double wt=0.0;
		Money summaryChg=null;
		//Added as part of bug ICRD-101743 by A-5526 ends
		if(SCREENSTATUS_CN51SCREEN.equals(form.getFromScreenStatus())||SCREENSTATUS_INVEXP.equals(form.getFromScreenStatus())){
			if(session.getCn66FilterVO()!=null){
				filterVO=session.getCn66FilterVO();
			}
			//Added by A-5945 for ICRD-101867 starts
			if (SCREENSTATUS_INVEXP.equals(form.getFromScreenStatus()))   {
				form.setCn51Status("E")	;
			} 
			//Added by A-5945 for ICRD-101867 ends
			//Added as part of bug ICRD-101743 by A-5526 starts
			if(session.getAirlineCN66DetailsVOs()!=null && session.getAirlineCN66DetailsVOs().size()>0){      
				vos=session.getAirlineCN66DetailsVOs();
				form.setBlgCurCode(vos.get(0).getCurCod());
				for (AirlineCN66DetailsVO vo : vos) {
					if(vo.getTotalSummaryWeight()!=0.0){
						wt=vo.getTotalSummaryWeight();
					}
					if(vo.getSummaryAmount()!=null){
						summaryChg=vo.getSummaryAmount();

					}
					try
					{
					form.setNetSummaryWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, wt)));
					}catch(UnitException unitException) {
						unitException.getErrorCode();
					 }
					if(summaryChg!=null){
						form.setNetChargeMoneyDisp(String.valueOf(summaryChg.getAmount()));
					}
					form.setNetSummaryAmount(summaryChg);
					
				}
			}
			//Added as part of bug ICRD-101743 by A-5526 ends
			form.setInvoiceRefNo(filterVO.getInvoiceRefNumber());
			form.setClearancePeriod(filterVO.getClearancePeriod());
			form.setAirlineCode(filterVO.getAirlineCode());
			//Added by A-5945 for ICRD-100132 starts
			form.setAirlineIdentifier(filterVO.getAirlineId());
			//Added by A-5945 for ICRD-100132 ends
			form.setCategory(filterVO.getCategory());
			form.setCarriageFromFilter(filterVO.getCarriageFrom());
			form.setCarriageToFilter(filterVO.getCarriageTo());
			form.setDespatchStatusFilter(filterVO.getDespatchStatus());
			form.setBillingType(filterVO.getInterlineBillingType());
			form.setFromScreenStatus("");
			if(session.getPresentScreenStatus()!=null){
				form.setScreenStatus(session.getPresentScreenStatus());
			}
			else{
				form.setScreenStatus("");
			}
		}
		else{
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
			
			
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		if(oneTimeHashMap!=null){
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
		log.log(Log.INFO, "oneTimeHashMap", oneTimeHashMap);
		}
		session.removeCn66Details();
		session.removeCn66DetailsMap();
		session.removeAirlineCN66DetailsVOs();
		form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
		form.setFromScreenStatus(SCREENSTATUS_CN66SCREEN);
		try{
   			Money money = CurrencyHelper.getMoney("USD");
   			money.setAmount(0.0D);
   		
   			form.setNetChargeMoney(money);
   			form.setNetSummaryWeight(dou);
   			form.setNetCPWeight(dou);
   			form.setNetLCWeight(dou);
   			form.setNetUldWeight(dou);
   			//form.setNetSalWeight(dou);
   			form.setNetSVWeight(dou);
   			form.setNetEMSWeight(dou);
   			}
   			catch(CurrencyException currencyException){
   				log.entering(CLASS_NAME,"CurrencyException");
   			}
   			Map oneTimes = findOneTimeDescription(companyCode);
   		    if (oneTimes != null) {
   		      Collection hniVOs = (Collection)oneTimes.get(KEY_ONETIMEHNI);
   		      Collection riVOs = (Collection)oneTimes.get(KEY_ONETIMERI);
   		      session.setOneTimeRI(riVOs);
   		      session.setOneTimeHNI(hniVOs);
   		    }
		if(errors!=null && errors.size()>0){
	    	invocationContext.addAllError(errors);
	    	}
		form.setLinkStatus("");
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering("RefreshCommand", "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
	    	
	    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
	    	
	    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
	    	return systemparameterTypes;
	      }
	 
	 public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode)
	  {
	    Map oneTimes = null;
	    Collection errors = null;
	    try {
	      Collection fieldValues = new ArrayList();
	      fieldValues.add(KEY_ONETIMERI);
	      fieldValues.add(KEY_ONETIMEHNI);
	      oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldValues);
	    } catch (BusinessDelegateException businessDelegateException) {
	      errors = handleDelegateException(businessDelegateException);
	    }
	    return oneTimes;
	  }
	//Added as part of ICRD-101112
	 private void setUnitComponent(String stationCode,
			 CaptureCN66Session session){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT);		
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			session.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			session.setVolumeRoundingVO(unitRoundingVO);
		   }catch(UnitException unitException) {
	//printStackTrrace()();
		   }
	}
}
