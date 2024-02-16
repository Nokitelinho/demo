/*
 * ClearCommand.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.time.TimeConvertor;



/**
 * @author A-3108
 *
 */
public class ClearCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA PRORATION");
	
	/**
	 * Strings for SCREEN_ID and MODULE_NAME 
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String CLASS_NAME = "ClearCommand";	
	private static final String CLEAR_SUCCESS = "clear_success";
	
	
	private static final String KEY_EXCEPTION = "mra.proration.exceptions";
	private static final String KEY_STATUS = "mra.proration.exceptionstatus";
	private static final String KEY_ASSIGNED_STATUS="mra.proration.assignedstatus";
	private static final String  EMTPY_STRING ="";
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
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
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
	ListProrationExceptionsSession listExceptionSession=getScreenSession(MODULE_NAME,SCREEN_ID);
	clearFormFields(listExceptionForm,logonAttributes);

	/*Getting OneTimeValues*/
	getOneTimeValues(listExceptionSession,logonAttributes);
	listExceptionSession.removeProrationException();
	listExceptionSession.setSelectedVoidMailbags(null);
	listExceptionSession.setSelectedRows(null);
	listExceptionSession.removeProrationExceptionVOs();
	listExceptionSession.removeProrationExceptionVOss();
	listExceptionSession.removeParentId();
	listExceptionSession.setFromScreenFlag(null);
	//listExceptionSession.removeAllAttributes();
	listExceptionForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	
	log.exiting(CLASS_NAME, "execute");
	invocationContext.target =  CLEAR_SUCCESS;

	log.exiting(CLASS_NAME, "execute");
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
			oneTimeList.add(KEY_ASSIGNED_STATUS);
			oneTimeList.add(KEY_BILLING_TYPE_ONETIME);
			oneTimeList.add(KEY_CATEGORY_ONETIME);
			try {
				log.log(Log.FINEST,"***********************************inside try");
				hashMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
				log.log(Log.FINEST, "hash map*****************************",
						hashMap);

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
	 
	 /**
	     * Method to clear form fields
	     * @param listExceptionForm
	     * @param logonAttributes
	     */
	    void clearFormFields(ListMailProrationExceptionsForm listExceptionForm,LogonAttributes logonAttributes){
	    	log.entering(CLASS_NAME,"clearFormFields");
	    	/* Clearing Formfields */
	    	listExceptionForm.setExceptionCode(EMTPY_STRING);
	    	listExceptionForm.setFlightNumber(EMTPY_STRING);
	    	listExceptionForm.setCarrierCode(EMTPY_STRING);
	    	listExceptionForm.setFlightDate(EMTPY_STRING);
	    	listExceptionForm.setOrigin(EMTPY_STRING);
	    	listExceptionForm.setDestination(EMTPY_STRING);
	    	listExceptionForm.setDispatchNo(EMTPY_STRING);
	    	listExceptionForm.setStatus(EMTPY_STRING);
	    	listExceptionForm.setFromDate(EMTPY_STRING);
	    	listExceptionForm.setOperationFlag(EMTPY_STRING);
	    	listExceptionForm.setAsignee(EMTPY_STRING);
	    	listExceptionForm.setAssignedStatus(EMTPY_STRING);
	    	listExceptionForm.setOriginOfficeOfExchange(EMTPY_STRING);
	    	listExceptionForm.setDestinationOfficeOfExchange(EMTPY_STRING);
	    	listExceptionForm.setMailCategory(EMTPY_STRING);
	    	listExceptionForm.setSubClass(EMTPY_STRING);
	    	listExceptionForm.setYear(EMTPY_STRING);
	    	listExceptionForm.setReceptacleSerialNumber(EMTPY_STRING);
	    	listExceptionForm.setHighestNumberIndicator(EMTPY_STRING);
	    	listExceptionForm.setRegisteredIndicator(EMTPY_STRING);
	    	listExceptionForm.setMailbagID(EMTPY_STRING);//Added as part of ICRD-205027
	    	listExceptionForm.setDisplayPage("1");
	    	
	    	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);	
			String currentDateString = TimeConvertor.toStringFormat(currentDate,logonAttributes.getDateFormat());
			listExceptionForm.setToDate(currentDateString);
	    	
	    	log.exiting(CLASS_NAME,"clearFormFields");    	  
	    }

}
