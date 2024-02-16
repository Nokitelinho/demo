/*
 * ScreenLoadCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ListCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

	/**
	 * Target action
	 */
	private static final String SCREEN_SUCCESS = "screenload_success";

	/**
	 * For Onetime values
	 */
	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";


	/**
	 * For Onetime values
	 */
	private static final String CCABILSTA_ONETIME = "mra.airlinebilling.billingstatus";
	/**
	 * For Onetime values
	 */
	private static final String CCABILLSTA_ONETIME = "mra.gpabilling.billingstatus";

	/**
	 * For Onetime values
	 */
	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String MCACREATION_ONETIME = "mailtracking.mra.defaults.mcacreationtype";
	/*
	 * Parameter code for system parameter - level for data import to mra
	 */
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mra.defaults.levelfordataimporttomra";

	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";//Added by A-6991 for ICRD-208114
	private static final String BASED_ON_RULES = "R";//Added for IASCB-2373
	private static final String KEY_WEIGHT_UNIT="mail.mra.defaults.weightunit"; // Added by A-9002
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListCCAForm listCCAForm = (ListCCAForm) invocationContext.screenModel;
		ListCCASession listCCASession = getScreenSession(MODULE_NAME, SCREEN_ID);
		
		Map<String, String> systemParameterValues = null;
		
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes
				.getCompanyCode());
		try {
			if(checkWorkFlowEnabled()){
				listCCAForm.setComboFlag(YES);
			}
		} catch (SystemException e) {

			log.log(Log.FINE,  "Sys.Excptn ");
		}
		try {
			/** getting collections of OneTimeVOs */
			
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
			
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		
		for (Map.Entry<String, String> entry : systemParameterValues.entrySet()) { 
			listCCAForm.setParameterValue(entry.getValue());
			
    		}
		ArrayList<OneTimeVO> billstat = (ArrayList<OneTimeVO>) oneTimeValues.get(CCABILLSTA_ONETIME);
		oneTimeValues.remove(CCABILLSTA_ONETIME);
		int cnt = 0 ;
		int flg = 0;
		for(OneTimeVO oneTimeVO : billstat){			
			if("OH".equals(oneTimeVO.getFieldValue())){
				flg=1;
				break;			 
			}
			cnt=cnt+1;
		}
		if(flg==1){
			billstat.remove(cnt);
		}
		oneTimeValues.put(CCABILLSTA_ONETIME, billstat);
		listCCASession
		.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		listCCASession.setSystemparametres((HashMap<String, String>)systemParameterValues);
		// listBillingMatrixForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		LocalDate dateTo = new LocalDate(getApplicationSession().getLogonVO()
				.getAirportCode(), Location.ARP, true);
		String toDateToString = TimeConvertor.toStringFormat(dateTo
				.toCalendar(), "dd-MMM-yyyy");
		listCCAForm.setToDate(toDateToString);
		log.log(Log.INFO, "Todate====>", listCCAForm.getToDate());
		listCCASession.setCCADetailsVOs(null);
		listCCASession.setCCAFilterVO(null);
		listCCASession.setListStatus(null);
		listCCAForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {
		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CCATYPE_ONETIME);
		oneTimeList.add(CCABILSTA_ONETIME);
		oneTimeList.add(CCABILLSTA_ONETIME);
		oneTimeList.add(ISSUINGPARTY_ONETIME);
		oneTimeList.add(CCASTATUS_ONETIME);
		oneTimeList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeList.add(KEY_CATEGORY_ONETIME);
		oneTimeList.add(MCACREATION_ONETIME);
		oneTimeList.add(KEY_WEIGHT_UNIT);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private boolean checkWorkFlowEnabled() throws SystemException {
		Boolean workFlowEnabled=true;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
		
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			if(!(systemParameters.containsValue(YES) || systemParameters.containsValue(BASED_ON_RULES))){//Modified for IASCB-2373
				workFlowEnabled=false;
			}
		}
		return workFlowEnabled;
	}
	private Collection<String> getSystemParameterTypes(){
    	log.entering("RefreshCommand", "getSystemParameterTypes");
    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
    	
    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
    	
    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
    	return systemparameterTypes;
      }
}
