/*
 * ScreenloadListCN51Command.java Created on Mar 14,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 *
 */
public class ScreenloadListCN51Command extends BaseCommand {

	private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String SCREENLOAD_SUCESS = "screenload_success";
	
	private static final String ONE_TIME_FIELDTYPE_INTBLGTYP = "mailtracking.mra.billingtype";
	
	private static final String ONE_TIME_FIELDTYPE_BLGTYP = "mailtracking.mra.invoicetype"; //Added by A-7929 as part of ICRD-265471
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String BLANK_VALUE = "";
	
	private static final String SCREENLOAD="screenload";
	
	private static final String SYS_PARA_ACC_ENTRY="cra.accounting.isaccountingenabled";
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invContext)
								throws CommandInvocationException {
		log.entering("ScreenloadListCN51Command","execute");
					
			ListCN51ScreenForm listCN51ScreenForm 
					= (ListCN51ScreenForm)invContext.screenModel;
			ListCN51ScreenSession listCN51ScreenSession
					= (ListCN51ScreenSession)this.getScreenSession(MODULE_NAME,SCREEN_ID);
			clearFormAttributes(listCN51ScreenForm);
			listCN51ScreenSession.setAirlineCN51FilterVO(null);
			listCN51ScreenSession.setAirlineCN51SummaryVOs(null);
			
			//listCN51ScreenForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			listCN51ScreenForm.setScreenStatus(SCREENLOAD);
			LogonAttributes logonAttributes = this.getApplicationSession().getLogonVO(); 
			log.log(Log.INFO, " ###### logged in as Company : ",
					logonAttributes.getCompanyCode());
			Collection<String> filedTypes = new ArrayList<String>();
			filedTypes.add(ONE_TIME_FIELDTYPE_INTBLGTYP);			
			filedTypes.add(ONE_TIME_FIELDTYPE_BLGTYP); //added by a-7929 as part of ICRD-265471
			
			Map<String, Collection<OneTimeVO>> oneTimeValueMap = null;
			try {
				oneTimeValueMap = getAllOneTimes(logonAttributes.getCompanyCode(),
						  		   				 filedTypes);
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE," ####### BusinessDelegateException ######### ");
				invContext.addAllError(handleDelegateException(e));				
			}									
			if(oneTimeValueMap != null ){
				log.log(Log.INFO," #######  onetimes got ############### ");
				listCN51ScreenSession.setOneTimeValues(oneTimeValueMap);
			}
//			 code for acc entry sys para starts
			Collection<String> systemParameterCodes = new ArrayList<String>();

			systemParameterCodes.add(SYS_PARA_ACC_ENTRY);

			Map<String, String> systemParameters = null;

			try {

				systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);

			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				invContext.addAllError(handleDelegateException(e));
			}

			String accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
			log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
			if("N".equals(accountingEnabled)){
				listCN51ScreenForm.setAccEntryFlag("N");
			}else{
				listCN51ScreenForm.setAccEntryFlag("Y");
			}
//			code for acc entry sys para ends
			invContext.target = SCREENLOAD_SUCESS;
		log.exiting("ScreenloadListCN51Command","execute");		
		
	}
	
	/**
	 * method for calling shared server call :findOneTimeValues
	 * @param companyCode
	 * @param fieldTypes
	 * @return
	 * @throws BusinessDelegateException
	 */
	private Map<String,Collection<OneTimeVO>> getAllOneTimes(String companyCode,Collection<String> fieldTypes) 
																throws BusinessDelegateException{
		log.entering("ScreenloadListCN51Command","getAllOneTimes");
		return new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldTypes);		
	}
		
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	/*public Map<String, Collection<OneTimeVO>> 
								findOneTimeVOs(String companyCode,Collection<String> fieldValues ) 
									throws BusinessDelegateException {
		log.entering("ScreenloadListCN51Command","findOneTimeVOs");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		oneTimes = new SharedDefaultsDelegate()
							.findOneTimeValues(companyCode,fieldValues) ;
		log.entering("ScreenloadListCN51Command","findOneTimeVOs");
		return oneTimes;
	}*/
	private void clearFormAttributes(ListCN51ScreenForm listCN51ScreenForm) {
		listCN51ScreenForm.setAirlineCode(BLANK_VALUE);
		listCN51ScreenForm.setBlgFromDateStr(BLANK_VALUE);
		listCN51ScreenForm.setBlgToDateStr(BLANK_VALUE);
	}

}
