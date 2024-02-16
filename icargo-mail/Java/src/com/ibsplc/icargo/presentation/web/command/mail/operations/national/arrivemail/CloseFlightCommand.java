/* CloseFlightCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */
public class CloseFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING,CloseFlightCommand");

	   private static final String MODULE_NAME = "mail.operations";
	   private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";

	   /**
	    * TARGET
	    */
	   private static final String CLOSE_SUCCESS = "success";
	   private static final String CLOSE_FAILURE = "failure";


		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering("CloseFlightCommand","execute");

	    	ArriveDispatchForm arriveDiapatchForm = (ArriveDispatchForm)invocationContext.screenModel;
	    	ArriveDispatchSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID);

	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			//LogonAttributes logonAttributes = applicationSession.getLogonVO();

	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
	    	arriveDiapatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

			/*
			 * 
			 * This is to check, whether any
			 * mailbag or despatch is not associated with an onward routing info.
			 * If any such found, exception should be thrown.
			 * This is implemented to cooperate with MRA module.
			 * START
			 */
			Collection<String> sysParameters = new ArrayList<String>();
			sysParameters.add(MailConstantsVO.CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT);
			Map<String, String> sysParameterMap = null;
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			try {
				sysParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(sysParameters);
			} catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, " systemParameterMap ", sysParameterMap);
			if (sysParameterMap != null && OperationalFlightVO.FLAG_YES.equals(sysParameterMap
							.get(MailConstantsVO.CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT))) {
				Collection<ContainerDetailsVO> containerDetails=mailArrivalVO.getContainerDetails();
				boolean canCloseFlight=true;
				if(containerDetails!=null && containerDetails.size()>0){
					for(ContainerDetailsVO containerDtls : containerDetails){
						if(containerDtls.getDsnVOs()!=null && containerDtls.getDsnVOs().size()>0){
							for(DSNVO dSNVO : containerDtls.getDsnVOs()){
								if(MailConstantsVO.FLAG_NO.equals(dSNVO.getRoutingAvl())){
									canCloseFlight=false;
									break;
								}
							}
						}
						if(!canCloseFlight){
							break;
						}
					}
				}
				if(!canCloseFlight){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.national.err.routingunavailable"));
		    		invocationContext.target = CLOSE_FAILURE;
					return;
				}
			}
			
	    	try{
	    		delegate.closeInboundFlight(mailArrivalSession.getOperationalFlightVO());
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}

 		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
 		invocationContext.target = CLOSE_SUCCESS;

 		log.exiting("CloseFlightCommand","execute");

	}

}
