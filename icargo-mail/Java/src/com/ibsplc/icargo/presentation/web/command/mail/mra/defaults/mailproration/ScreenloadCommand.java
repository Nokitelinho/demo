/*
 * ScreenloadCommand.java Created on Mar 6, 2007
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;


import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 *
 */
public class ScreenloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
	
	private static final String CLASS_NAME = "ScreenloadCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration";
	
	
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	
	private static final String SUBCLASS_ONETIME = "mailtracking.defaults.mailsubclassgroup";
	
	private static final String PRORATIONTYPE_ONETIME = "mtk.proration.prorationtype";
	
	private static final String PRORATIONPAYTYPE_ONETIME = "mtk.proration.prorationpaytype";
	
	
	
	
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	

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

		MailProrationSession  mailProrationSession = 
			(MailProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
					
		MailProrationForm mailProrationForm=(MailProrationForm)invocationContext.screenModel;
		
		Map<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		
		
		mailProrationSession.setCategory(
				(ArrayList<OneTimeVO>) oneTimeValues.get(CATEGORY_ONETIME));
		
		
		mailProrationSession.setSubClass(
				(ArrayList<OneTimeVO>) oneTimeValues.get(SUBCLASS_ONETIME));
		
		mailProrationSession.setProrationType(
				(ArrayList<OneTimeVO>) oneTimeValues.get(PRORATIONTYPE_ONETIME));
		
		mailProrationSession.setProrationPayType(
				(ArrayList<OneTimeVO>) oneTimeValues.get(PRORATIONPAYTYPE_ONETIME));
		
		
		
		log.log(Log.INFO, "Onetime of CATEGORY ---->", mailProrationSession.getCategory());
		log.log(Log.INFO, "Onetime of SUBCLASS---->", mailProrationSession.getSubClass());
		log.log(Log.INFO, "Onetime of PRORATION TYPE---->",
				mailProrationSession.getProrationType());
		log.log(Log.INFO, "Onetime of PRORATION PAY TYPE---->",
				mailProrationSession.getProrationPayType());
		mailProrationSession.removeProrationFilterVO();
		mailProrationSession.removeProrationVOs();				
		mailProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);		
		
		invocationContext.target=SCREENLOAD_SUCCESS;
		
		log.exiting(CLASS_NAME,"execute");

	}
	
	  /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private Map<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			log.log(Log.FINE, "****inside try*", getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
//printStackTrrace()();
			 handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return oneTimeValues;
	}
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	
    	parameterTypes.add(CATEGORY_ONETIME);
    	parameterTypes.add(SUBCLASS_ONETIME);
    	parameterTypes.add(PRORATIONTYPE_ONETIME);   
    	parameterTypes.add(PRORATIONPAYTYPE_ONETIME);
    	    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

	
}
