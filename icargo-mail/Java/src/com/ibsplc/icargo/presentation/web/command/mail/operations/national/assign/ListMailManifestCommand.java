/**
 * ListMailManifestCommand.java Created on January 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ListMailManifestCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING");
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String OUTBOUND = "O";
	private static final String LIST_SUCCESS = "success";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String FLIGHTSTATUS = "mailtracking.defaults.flightstatus";
	private static final String OPERATIONALSTATUS = "flight.operation.flightlegstatus";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		log.entering("ListMailManifestCommand","execute");
		AssignMailBagForm assignMailBagForm = 
			(AssignMailBagForm)invocationcontext.screenModel;
		AssignMailBagSession assignMailBagSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		
		MailManifestVO mailManifestVO = assignMailBagSession.getMailManifestVO();
		FlightValidationVO flightValidationVO = assignMailBagSession.getFlightValidationVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPol(logonAttributes.getAirportCode());		
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());		 
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setFlightDate(flightValidationVO.getStd());
		operationalFlightVO.setDirection(OUTBOUND);
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setPou(flightValidationVO.getLegDestination());


		MailManifestVO newMailManifestVO = new MailManifestVO();
		log.log(Log.FINE, "operationalFlightVO in Manifest session...",
				operationalFlightVO);
		try {
			newMailManifestVO = new MailTrackingDefaultsDelegate().findContainersInFlightForManifest(operationalFlightVO);
			log.log(Log.FINE, "newMailManifestVO.............",
					newMailManifestVO);


		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationcontext.addAllError(errors);
			assignMailBagSession.setMailManifestVO(mailManifestVO);
			invocationcontext.target = LIST_SUCCESS;
			return;
		}
		if(newMailManifestVO == null){
			newMailManifestVO = new MailManifestVO();
		}
		newMailManifestVO.setCompanyCode(logonAttributes.getCompanyCode());
		newMailManifestVO.setDepPort(logonAttributes.getAirportCode());
		newMailManifestVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
		newMailManifestVO.setFlightNumber(flightValidationVO.getFlightNumber());
		newMailManifestVO.setDepDate(flightValidationVO.getStd());
		newMailManifestVO.setCarrierId(flightValidationVO.getFlightCarrierId());


		Collection<ContainerDetailsVO> containerDetails = newMailManifestVO.getContainerDetails();  

		if((invocationcontext.getErrors()== null || invocationcontext.getErrors().size() ==0)
				&& (containerDetails == null ||containerDetails.size() == 0)){
			invocationcontext.addError(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.nocontainerdetails"));	
			assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		}else{
			assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}


		log.log(Log.FINE, "ContainerDetailsVO................",
				containerDetails);
		assignMailBagSession.setMailManifestVO(newMailManifestVO);
		assignMailBagSession.setOperationalFlightVO(operationalFlightVO);
		flightValidationVO.setDirection(OUTBOUND);
		Map<String, Collection<OneTimeVO>> oneTimMap = assignMailBagSession.getOneTimeVOs();
		if(newMailManifestVO.getFlightStatus() != null){
		assignMailBagForm.setOperationalStatus(newMailManifestVO.getFlightStatus());
		flightValidationVO.setOperationalStatus(findOneTimeDescription(newMailManifestVO.getFlightStatus(), oneTimMap.get(FLIGHTSTATUS)));
		}
		if(flightValidationVO.getLegStatus() != null){
		flightValidationVO.setStatusDescription(findOneTimeDescription(flightValidationVO.getLegStatus(), oneTimMap.get(OPERATIONALSTATUS)));
		}	
		
		
		log.log(Log.FINE, "FlightStatus................", assignMailBagForm.getOperationalStatus());
		invocationcontext.target = LIST_SUCCESS;
		log.exiting("ListMailManifestCommand","execute");

	}
	/**
	 * 
	 * @param fieldValue
	 * @param oneTimeVOs
	 * @return desc
	 */
	private String findOneTimeDescription(String fieldValue,
			Collection<OneTimeVO> oneTimeVOs) {
		String desc = "" ;
		
		if(oneTimeVOs != null){
			for(OneTimeVO onetimeVo : oneTimeVOs){
				if(onetimeVo.getFieldValue().equals(fieldValue)){
					desc = onetimeVo.getFieldDescription();
				}
			}
		}

		return desc;
	}


}
