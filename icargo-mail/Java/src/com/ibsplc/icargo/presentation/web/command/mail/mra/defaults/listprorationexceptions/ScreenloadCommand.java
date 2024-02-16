/*
 * ScreenloadCommand.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;


/**
 * @author A-3108
 *
 */
public class ScreenloadCommand extends BaseCommand {

		/**
		 * Logger and the file name
		 */
		private Log log = LogFactory.getLogger("MRA PRORATION");
		/**
		 * Strings for SCREEN_ID and MODULE_NAME
		 */
		private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		/*
		 * String for CLASS_NAME
		 */
		private static final String CLASS_NAME="ScreenLoadCommand";
		/**
		 * Target String  for success
		 */
		private static final String SCREENLOAD_SUCCESS = "screenload_success";
		

		private static final String KEY_EXCEPTION = "mra.proration.exceptions";
		private static final String KEY_STATUS = "mra.proration.exceptionstatus";
		private static final String KEY_TRIGGERPOINT = "mailtracking.mra.proration.triggerpoint";
		private static final String KEY_ASSIGNED_STATUS="mra.proration.assignedstatus";
		private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
		private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
		/*
		 * Parameter code for system parameter - level for data import to mra
		 */
		private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mra.defaults.levelfordataimporttomra";
		private static final String  SYSPAR_NOOFRECORDSALLOWED_BULKROUTEUPD="mailtracking.mra.noofrecordsallowedforbulkrouteupdate";
		
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
			HashMap<String, String> systemParameterValues = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
			ListProrationExceptionsSession listExceptionSession=getScreenSession(MODULE_NAME,SCREEN_ID);
			try {
				/** getting collections of OneTimeVOs */
				
				systemParameterValues=(HashMap<String, String>)new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
			} catch (BusinessDelegateException e) {
	    		e.getMessage();
				errors=handleDelegateException( e );
			}
			for (Map.Entry<String, String> entry : systemParameterValues.entrySet()) { 
				listExceptionForm.setParameterValue(entry.getValue());
				
	    		}
			
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			String currentDateString = TimeConvertor.toStringFormat(currentDate,logonAttributes.getDateFormat());
			listExceptionForm.setToDate(currentDateString);
			listExceptionForm.setCalendarFormat(logonAttributes.getDateFormat());
			
			/*Getting OneTimeValues*/
			getOneTimeValues(listExceptionSession,logonAttributes);
			listExceptionSession.removeProrationException();
			listExceptionSession.removeProrationExceptionVOs();
			listExceptionSession.removeProrationExceptionVOss();
			listExceptionSession.setFromScreenFlag(null);
			listExceptionSession.setSystemParametres(systemParameterValues);
			//listExceptionSession.removeAllAttributes();
			listExceptionForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

			log.exiting(CLASS_NAME, "execute");
			invocationContext.target =  SCREENLOAD_SUCCESS;
    	}

		/**
		 *
		 * @param listExceptionSession
		 * @param logonAttributes
		 */
		 void getOneTimeValues(ListProrationExceptionsSession listExceptionSession, LogonAttributes logonAttributes){
		    	log.entering(CLASS_NAME,"getOneTimeValues");
		    	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			    Map<String,Collection<OneTimeVO>> hashMap = null;
			    Collection<String> oneTimeList = new ArrayList<String>();
				oneTimeList.add(KEY_EXCEPTION);
				oneTimeList.add(KEY_STATUS);
				oneTimeList.add(KEY_TRIGGERPOINT);
				oneTimeList.add(KEY_ASSIGNED_STATUS);
				oneTimeList.add(KEY_BILLING_TYPE_ONETIME);
				oneTimeList.add(KEY_CATEGORY_ONETIME);
				try {
					log.log(Log.FINEST,"***********************************inside try");
					hashMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
					log.log(Log.FINEST,
							"hash map*****************************", hashMap);

				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
					log.log(Log.SEVERE, "status fetch exception");
				}
				if(hashMap!=null){
					Collection<OneTimeVO> vos = hashMap.get(KEY_STATUS);
					if(vos != null && !vos.isEmpty()){
						Iterator<OneTimeVO> iterator = vos.iterator();
						while(iterator.hasNext()){
							OneTimeVO vo = iterator.next();
							if("R".equals(vo.getFieldValue())){
								iterator.remove();
							}
						}
						
					}
					listExceptionSession.setOneTimeValues((HashMap<String,Collection<OneTimeVO>>)hashMap);
				}
				log.exiting(CLASS_NAME,"getOneTimeValues");	
		    }
		 private Collection<String> getSystemParameterTypes(){
		    	log.entering("RefreshCommand", "getSystemParameterTypes");
		    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
		    	
		    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
		    	systemparameterTypes.add(SYSPAR_NOOFRECORDSALLOWED_BULKROUTEUPD);
		    	
		    	
		    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
		    	return systemparameterTypes;
		    }
}
