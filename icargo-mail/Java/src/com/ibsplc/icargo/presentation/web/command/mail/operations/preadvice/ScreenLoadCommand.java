/*
 * ScreenLoadCommand.java Created on July 03, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.preadvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PreAdviceSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger("PreAdvice,ScreenLoadCommand");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.preadvice";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the screen load---------->\n\n");

    	PreAdviceSession preAdviceSession =
								getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;

    	OperationalFlightVO operationalFlightVO = 
    							preAdviceSession.getOperationalFlightVO();

    	PreAdviceVO preAdviceVO = new PreAdviceVO();

    	MailTrackingDefaultsDelegate delegate = 
    							new MailTrackingDefaultsDelegate();

    	try{
    		preAdviceVO = delegate.findPreAdvice(operationalFlightVO);
    	}catch(BusinessDelegateException businessDelegateException){
    		errors = handleDelegateException(businessDelegateException);
    	}

    	FlightValidationVO flightValidationVO = 
    							preAdviceSession.getFlightValidationVO();

		Map<String, Collection<OneTimeVO>> oneTimes = 
			findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> resultStatus=
				oneTimes.get("flight.operation.flightlegstatus");
			log.log(Log.FINE, "*******flightlegstatus******");
			flightValidationVO.setStatusDescription(
					findOneTimeDescription(
							resultStatus,
							flightValidationVO.getLegStatus()));
		}

		log.log(Log.FINE, "\n\n flightValidationVO----------> ",
				flightValidationVO);
		log.log(Log.FINE, "\n\n preAdviceVO----------> ", preAdviceVO);
		preAdviceSession.setFlightValidationVO(flightValidationVO);
		preAdviceSession.setPreAdviceVO(preAdviceVO);

		invocationContext.target = SUCCESS;

	}

	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add("flight.operation.flightlegstatus");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * This method will the status description corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}

}
