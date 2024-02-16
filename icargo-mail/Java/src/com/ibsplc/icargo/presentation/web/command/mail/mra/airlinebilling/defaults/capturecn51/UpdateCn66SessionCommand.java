/*
 * UpdateCn66SessionCommand.java Created on Feb 21, 2007
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

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 *
 */
public class UpdateCn66SessionCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "UpdateCn66SessionCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String MODULENAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.billingtype";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String KEY_DESPATCH_STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	private static final String UPDATE_SUCCESS="update_success";
	//Added for Bug ICRD-100226 by A-5526
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";
	//Added for Bug ICRD-101111
	private static final String KEY_ONETIMERI ="mailtracking.defaults.registeredorinsuredcode";
	private static final String KEY_ONETIMEHNI ="mailtracking.defaults.highestnumbermail";
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULENAME, SCREENID);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		Map oneTimeHashMap 								= null;
		Map<String, String> systemParameterValues = null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(KEY_DESPATCH_STATUS_ONETIME);
		
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
//Added for bug ICRD-100226 by A-5526
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
			handleDelegateException( e );
		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		//Added for bug ICRD-100226 by A-5526
		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
		//Added for Bug ICRD-101111
		Map oneTimes = findOneTimeDescription(companyCode);
		 if (oneTimes != null) {
  		      Collection hniVOs = (Collection)oneTimes.get(KEY_ONETIMEHNI);
  		      Collection riVOs = (Collection)oneTimes.get(KEY_ONETIMERI);
  		      session.setOneTimeRI(riVOs);
  		      session.setOneTimeHNI(hniVOs);
  		    }
		String[] rows = captureCN51Form.getSelect();
		int index = Integer.parseInt(rows[0]);
		AirlineCN51DetailsVO airlineCN51DetailsVO 
							=(captureCN51Session.getCn51Details().getCn51DetailsPageVOs()).get(index);
		log.log(Log.INFO, "Selected vo --->>>", airlineCN51DetailsVO);
		AirlineCN51FilterVO airlineCN51FilterVO = captureCN51Session.getFilterDetails();
		AirlineCN66DetailsFilterVO airlineCN66DetailsFilterVO = new AirlineCN66DetailsFilterVO();
		airlineCN66DetailsFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineCN66DetailsFilterVO.setClearancePeriod(airlineCN51DetailsVO.getClearanceperiod());
		airlineCN66DetailsFilterVO.setInterlineBillingType(airlineCN51DetailsVO.getInterlinebillingtype());
		airlineCN66DetailsFilterVO.setCarriageFrom(airlineCN51DetailsVO.getCarriagefrom());
		airlineCN66DetailsFilterVO.setCarriageTo(airlineCN51DetailsVO.getCarriageto());
		airlineCN66DetailsFilterVO.setCategory(airlineCN51DetailsVO.getMailcategory());
		airlineCN66DetailsFilterVO.setAirlineId(airlineCN51FilterVO.getAirlineIdentifier());
		airlineCN66DetailsFilterVO.setAirlineCode(airlineCN51FilterVO.getAirlineCode());
		airlineCN66DetailsFilterVO.setInvoiceRefNumber(airlineCN51FilterVO.getInvoiceReferenceNumber());
		session.setCn66FilterVO(airlineCN66DetailsFilterVO);
		session.setParentId(SCREEN_ID);
		log.log(Log.INFO, "Updated cn66session ------>>>", session.getCn66FilterVO());
		invocationContext.target=UPDATE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}
	//This method getSystemParameterTypes is added for Bug ICRD-100226 by A-5526
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering("RefreshCommand", "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
	    	
	    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
	    	
	    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
	    	return systemparameterTypes;
	      }
	//Added for Bug ICRD-101111
	 public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode){
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
}
