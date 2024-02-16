/*
 * AutoAttachAWBCommand.java Created on Oct 31 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.arrival.
 *					AutoAttachAWBCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	U-1267	:	Oct 31, 2017	:	Draft
 */
public class AutoAttachAWBCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	private static final String CLASS_NAME = "AutoAttachAWBCommand";
	private static final String TARGET_SUCCESS = "success";
	private static final String TARGET_FAILURE = "failure";
	private static final String FLIGHT_CLOSED ="mailtracking.defaults.err.flightclosed";
	private static final String NO_MAILS_TO_ATTACH ="mailtracking.defaults.attachawb.msg.err.nomailstoattach";
	private static final String ALREADY_ATTACHED="mailtracking.defaults.attachawb.msg.err.mailbagsalreadyattached";
	private static final String DIFFERENT_ORG_DST="mailtracking.defaults.attachawb..msg.err.differentairports";
	private static final String SHOW_ATTACH_AWB="showAttachAWB";
	/**
	 * 
	 *	Overriding Method	:@see com.ibsplc.icargo.framework.web.command.Command#
	 *						 execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			:U-1267 on Oct 31, 2017
	 * 	Used for 			:ICRD-211205
	 *	Parameters			:@param invocationContext
	 *	Parameters			:@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		if(!SHOW_ATTACH_AWB.equals(mailArrivalForm.getChkFlag())){
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO
				.getContainerDetails();
		if (containerDetailsVOs == null || containerDetailsVOs.size() ==0) {
			invocationContext.addError(new ErrorVO(NO_MAILS_TO_ATTACH));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

		boolean isFlightClosed = false;
		try {

			isFlightClosed = mailTrackingDefaultsDelegate
					.isFlightClosedForMailOperations(mailArrivalSession
							.getOperationalFlightVO());

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
		}
		if (isFlightClosed) {
			Object[] obj = { mailArrivalVO.getFlightCarrierCode(),
					mailArrivalVO.getFlightNumber(),
					mailArrivalVO.getArrivalDate().toString().substring(0, 11) };
			invocationContext.addError(new ErrorVO(
					FLIGHT_CLOSED, obj));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		Collection<ContainerDetailsVO> awbAttachedVOs = new ArrayList<ContainerDetailsVO>();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			try {
				awbAttachedVOs = mailTrackingDefaultsDelegate.autoAttachAWBDetails(
						containerDetailsVOs,
						mailArrivalSession.getOperationalFlightVO());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}

			log.log(Log.INFO, "awbAttachedVOs---->>", awbAttachedVOs);
			mailArrivalVO.setContainerDetails(awbAttachedVOs);
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			mailArrivalForm.setSaveSuccessFlag(MailConstantsVO.FLAG_YES);
		}
		}else{	
			String[] primaryKey = mailArrivalForm.getChildCont().split(",");
			int primaryKeyLen = primaryKey.length;
			int cnt=0;
		    int count = 0;
			ContainerDetailsVO containerDetailsVO = 
		        	((ArrayList<ContainerDetailsVO>)mailArrivalVO.getContainerDetails()).get(Integer.parseInt(primaryKey[0].split("~")[0]));
			ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
		    	try{
		    		BeanHelper.copyProperties(containerDtlsVO,containerDetailsVO);
		    	}catch(SystemException systemException){
		    		systemException.getMessage();
		    	}
			Collection<DSNVO> dsnVOs = containerDtlsVO.getDsnVOs();
			Collection<DSNVO> selectedDsnVOs = new ArrayList<DSNVO>();
	        if (dsnVOs != null && dsnVOs.size() >= 0) {
	        	if(primaryKey[0].contains("~")){
	        	for (DSNVO dsnVO : dsnVOs) {
	        		String primaryKeyFromVO = String.valueOf(count);
	        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
	       			          equalsIgnoreCase(primaryKey[cnt].split("~")[1].trim())) {
	        			selectedDsnVOs.add(dsnVO);
	        			cnt++;
	        		}
	        		count++;
	       	  	}
	        	containerDtlsVO.setDsnVOs(selectedDsnVOs);
	        }
	       	}
			boolean isValid=false;
			log.log(Log.INFO, "primaryKeyLen------------>>", primaryKeyLen);
			if (primaryKey != null && primaryKey.length > 0) {
				 isValid = isAlreadyAttached(containerDtlsVO.getDsnVOs());
				if (!isValid) {
					invocationContext.addError(new ErrorVO(ALREADY_ATTACHED));
					invocationContext.target = TARGET_SUCCESS;
					mailArrivalForm.setChkFlag(MailConstantsVO.SEPARATOR);
					return;
				}
			}
			isValid=validateOriginDestination(containerDtlsVO.getDsnVOs(),logonAttributes);
			if (!isValid) {
				invocationContext.addError(new ErrorVO(DIFFERENT_ORG_DST));
				invocationContext.target = TARGET_SUCCESS;
				mailArrivalForm.setChkFlag(MailConstantsVO.SEPARATOR);
				return;
			}
	    	mailArrivalSession.setContainerDetailsVO(containerDtlsVO);
	    	mailArrivalForm.setChkFlag(SHOW_ATTACH_AWB);
		}
		invocationContext.target = TARGET_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}
	/**
	 * 
	 * 	Method		:	AutoAttachAWBCommand.findSystemParameterValue
	 *	Added by 	:	U-1267 on Oct 31, 2017
	 * 	Used for 	: 	ICRD-211205
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	HashMap<String,String>
	 */
	public HashMap<String, String> findSystemParameterValue()
			throws SystemException {
/*		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		HashMap<String, String> systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);*/
		return null;
	}
	  /**
	   *  
	   * 	Method		:	AutoAttachAWBCommand.isAlreadyAttached
	   *	Added by 	:	U-1267 on 09-Nov-2017
	   * 	Used for 	:	ICRD-211205
	   *	Parameters	:	@param dsnVOsdsnVOs
	   *	Parameters	:	@return 
	   *	Return type	: 	boolean
	   */
	  private boolean isAlreadyAttached(Collection<DSNVO>dsnVOs){
		   boolean isValid=true;
		if (dsnVOs != null && dsnVOs.size() > 0) {
			for(DSNVO dsnVO:dsnVOs){
				if (dsnVO.getMasterDocumentNumber() != null
						&& dsnVO.getSequenceNumber() > 0) {
					isValid=false;
					break;
				}
			}
		}
		return isValid;
	   }
	  /**
	   * 
	   * 	Method		:	AutoAttachAWBCommand.validateOriginDestination
	   *	Added by 	:	U-1267 on 09-Nov-2017
	   * 	Used for 	:	ICRD-211205
	   *	Parameters	:	@param dsnVOs
	   *	Parameters	:	@param logonAttributes
	   *	Parameters	:	@return 
	   *	Return type	: 	boolean
	   */
	  private boolean validateOriginDestination(Collection<DSNVO>dsnVOs,LogonAttributes logonAttributes){
		  boolean isValid=false;
		  Collection<String> officeOfExchanges=new ArrayList<String>();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
			HashMap<String,String> originDestinationMap = new HashMap<>();
		  if (dsnVOs != null && dsnVOs.size() > 0) {
			  for(DSNVO dsnVO:dsnVOs){
				  officeOfExchanges.add(dsnVO.getOriginExchangeOffice());
				  officeOfExchanges.add(dsnVO.getDestinationExchangeOffice());
			  }
			  try {
				  groupedOECityArpCodes=
					  new MailTrackingDefaultsDelegate().findCityAndAirportForOE(logonAttributes.getCompanyCode(), officeOfExchanges);
				}catch (BusinessDelegateException businessDelegateException) {
		        	Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
					log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
					
			  	}
				if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
					if (cityAndArpForOE.size() == 3) {
						if (!originDestinationMap.containsKey(cityAndArpForOE.get(2))) {
							originDestinationMap.put(cityAndArpForOE.get(2),
									cityAndArpForOE.get(0));
						}
					}
					}
				}
			if(originDestinationMap!=null&&originDestinationMap.size()==2){
				isValid=true;
			}	
		  }
		  return isValid;
	  }

}
