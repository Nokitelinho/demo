/*
 * ScreenloadMailManifestCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadMailManifestCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";

   private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
   private static final String WEIGHT_CODE = "shared.defaults.weightUnitCodes";
   private static final String CATEGORY_CODE = "mailtracking.defaults.mailcategory";
   private static final String CONTAINER_TYPE = "mailtracking.defaults.containertype";
   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
   //Added by A-7531 as part of CR ICRD-197299
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String ALLOW_NON_STANDARD_AWB = "operations.shipment.allownonstandardawb";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ScreenloadMailManifestCommand","execute");

    	MailManifestForm mailManifestForm =
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	mailManifestForm.setOperationalStatus("");
    	mailManifestSession.setMessageStatus("");
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		Collection<ErrorVO> errors= null;
		Collection<String> parameter = new ArrayList<String>();		
		parameter.add(MailConstantsVO.STOCK_HOLDER_PARAMETER);
		
/*		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
		String agentCode="";
		try {

    		agentCode = mailTrackingDefaultsDelegate.findStockHolderForMail(logonAttributes.getCompanyCode(),
    				logonAttributes.getAirportCode(),logonAttributes.isGHAUser());		

    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	mailManifestSession.setAgentCode(agentCode);
    	log.log(Log.INFO, "\n\n\n\n  agentCode----->>", agentCode);*/
		/*
		 * Getting the system parameters for
		 * whether stock request is required and
		 * the value of stock holder
		 */
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(STOCK_REQ_PARAMETER);
		/*
		 * Added By Karthick V as the part of the  Air New Zealand Mail Tracking CR 
		 * 
		 */
		parameterTypes.add(MailConstantsVO.SHIPMENTDESCRIPTION_FORAWB);
		parameterTypes.add(MAIL_COMMODITY_SYS);
		parameterTypes.add(ALLOW_NON_STANDARD_AWB);
		//parameterTypes.add(STOCK_HOLDER_PARAMETER);

		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(WEIGHT_CODE);
		fieldTypes.add(CATEGORY_CODE);
		fieldTypes.add(CONTAINER_TYPE);
		//Added by A-7531 as part of CR ICRD-197299
		fieldTypes.add(MAIL_STATUS);
		Map<String, String> parameters = null;
		Map<String,Collection<OneTimeVO>> oneTimeData
			= new HashMap<String,Collection<OneTimeVO>>();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
    	try {

    		oneTimeData = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),fieldTypes);

    		parameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);

    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	log.log(Log.INFO, "parameters----->>", parameters);
		if (parameters != null) {
    		mailManifestSession.setSystemParameters((HashMap<String, String>)parameters);
		}
    	log.log(Log.INFO, "oneTimeData----->>", oneTimeData);
		if (oneTimeData != null) {
			Collection<OneTimeVO> weightCode = oneTimeData.get(WEIGHT_CODE);
			Collection<OneTimeVO> catVOs = oneTimeData.get(CATEGORY_CODE);
			Collection<OneTimeVO> containerTypeVOs = oneTimeData.get(CONTAINER_TYPE);
			   //Added by A-7531 as part of CR ICRD-197299
			Collection<OneTimeVO> latestStatus = oneTimeData.get(MAIL_STATUS);
			mailManifestSession.setWeightCodes(weightCode);
			mailManifestSession.setOneTimeCat(catVOs);
			mailManifestSession.setContainerTypes(containerTypeVOs);
			  //Added by A-7531 as part of CR ICRD-197299
            mailManifestSession.setMailStatus(latestStatus);
			log.log(Log.INFO, "Mail status --------------------->",mailManifestSession.getMailStatus());
		}

		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifestSession.setMailManifestVO(mailManifestVO);

		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailManifestSession.setFlightValidationVO(flightValidationVO);
		mailManifestForm.setInitialFocus(FLAG_YES);
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailManifestForm.setDeparturePort(logonAttributes.getAirportCode());
    	invocationContext.target = TARGET;

    	log.exiting("ScreenloadMailManifestCommand","execute");

    }

}
