/*
 * ScreenloadCommand.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returnmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.dispatcher.BatchedResponse;
import com.ibsplc.xibase.client.framework.dispatcher.DispatcherException;
import com.ibsplc.xibase.client.framework.dispatcher.RequestDispatcher;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class ScreenloadCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILTRACKING");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.returnmail";
   private static final String MAILBAGENQUIRY_SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";

   private static final String DAMAGE_CODE = "mailtracking.defaults.return.reasoncode";
   private static final String CONST_MAILBAGENQUIRY = "MAILBAG_ENQUIRY";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ScreenLoadCommand","execute");

    	ReturnMailForm returnMailForm =
    		(ReturnMailForm)invocationContext.screenModel;
    	ReturnMailSession returnMailSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailBagEnquirySession mailBagEnquirySession = getScreenSession(MODULE_NAME,MAILBAGENQUIRY_SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(DAMAGE_CODE);

		Map<String,Collection<OneTimeVO>> oneTimeData
			= new HashMap<String,Collection<OneTimeVO>>();

    	/*
		 * Start the batch processing
		 */
		RequestDispatcher.startBatch();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

    	try {
    		sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),fieldTypes);
    	}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

    	/*
		 * Obtain the responses after the batch fetch
		 */
		try {
			BatchedResponse response[] = RequestDispatcher.executeBatch();
			log.log(Log.INFO, "Response length:--", response.length);
			if(!response[0].hasError()) {
				oneTimeData = (HashMap<String,Collection<OneTimeVO>>)response[0].getReturnValue();
				log.log(Log.INFO, "oneTimeData:--", oneTimeData);
			}

		}catch (DispatcherException dispatcherException) {
			dispatcherException.getMessage();

		}

		if (oneTimeData != null) {
			Collection<OneTimeVO> damagecodes =
				oneTimeData.get(DAMAGE_CODE);

			returnMailSession.setOneTimeDamageCodes(damagecodes);
		}

		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		//Added as part of ICRD-329974
		String code=null;
		for(MailbagVO mailbagVO:mailBagEnquirySession.getMailbagVOs())
			
			code=mailbagVO.getMailbagId().substring(2,5);

		try {
			airportValidationVO = areaDelegate.validateAirportCode(
    				logonAttributes.getCompanyCode(),
    				code);

			String countryCode = airportValidationVO.getCountryCode();

			if (countryCode != null) {
				postalAdministrationVOs = mailTrackingDefaultsDelegate.findLocalPAs(
						logonAttributes.getCompanyCode(),
						countryCode);
			}

		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		if (postalAdministrationVOs != null) {
			returnMailSession.setPostalAdministrationVOs(postalAdministrationVOs);
		}


		/**
		 * To check the paBuiltFlag for the mailbags that are going to be returned
		 */
		String containers = returnMailForm.getSelectedContainers();
		String[] selectedConts = containers.split(",");
		ArrayList<String> conts = new ArrayList<String>();
		for (String str : selectedConts) {
			conts.add(str);
		}
		log.log(Log.FINE, "Selected indexes------------> ", conts);
		if (CONST_MAILBAGENQUIRY.equals(returnMailForm.getFromScreen())) {
			Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
			Collection<MailbagVO> selectedMailbagVOs = new ArrayList<MailbagVO>();
			int index = 0;
			for (MailbagVO selectedvo : mailbagVOs) {
				if (conts.contains(String.valueOf(index))) {
					selectedMailbagVOs.add(selectedvo);
				}
				index++;
			}
			for (MailbagVO vo : selectedMailbagVOs) {
				if("Y".equals(vo.getPaBuiltFlag())){
						returnMailForm.setPaBuiltFlag("Y");
				}else{
						returnMailForm.setPaBuiltFlag("N");
				}
			}
		}


		returnMailForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);

    	invocationContext.target = TARGET;

    	log.exiting("ScreenLoadCommand","execute");

    }

}
