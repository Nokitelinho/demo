/* ListMailArrivalCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */
public class ListMailArrivalCommand extends BaseCommand {
	
  
	
private Log log = LogFactory.getLogger("MAILTRACKING");

/**
 * TARGET
 */
private static final String TARGET = "list_success";

private static final String MODULE_NAME = "mail.operations";	
private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";	
/**
 * Module name
 */
private static final String MODULE_NAME_FLIGHT =  "flight.operation";
/**
 * Screen id
 */
private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

private static final String INBOUND = "I";
private static final String CATEGORY = "mailtracking.defaults.mailcategory";
 
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
 public void execute(InvocationContext invocationContext)
         throws CommandInvocationException {
 	
	 log.entering("ListMailArrivalCommand","execute");

	 ArriveDispatchForm arriveDispatchForm = 
		 (ArriveDispatchForm)invocationContext.screenModel;
	 ArriveDispatchSession arriveDispatchSession = 
		 getScreenSession(MODULE_NAME,SCREEN_ID);
	 DuplicateFlightSession duplicateFlightSession = getScreenSession(
			 MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);


	 LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();


	 OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
	 operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
	 operationalFlightVO.setPou(logonAttributes.getAirportCode());

	 MailArrivalVO newMailArrivalVO = new MailArrivalVO();
	 FlightValidationVO flightValidationVO = new FlightValidationVO();
	 arriveDispatchForm.setOperationalStatus("");		

	 if(FLAG_YES.equals(arriveDispatchForm.getDuplicateFlightStatus())){
		 //arriveDispatchForm.setListFlag("");
		 arriveDispatchSession.setFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
		 arriveDispatchForm.setDuplicateFlightStatus(FLAG_NO);
	 }
	 log.log(Log.FINE, "flightValidationVO in ListMailArrivalCommand..",
			arriveDispatchSession.getFlightValidationVO());
	flightValidationVO = arriveDispatchSession.getFlightValidationVO();
	 Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());


	 if(oneTimes!=null){
		 log.log(Log.FINE, "oneTimes is not null");
		 Collection<OneTimeVO> resultStatus=
			 oneTimes.get("flight.operation.flightlegstatus");
		 flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
		 flightValidationVO.setDirection(INBOUND);
	 }



	 arriveDispatchSession.setFlightValidationVO(flightValidationVO);

	 operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
	 operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
	 operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	 operationalFlightVO.setFlightDate(flightValidationVO.getSta());
	 operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	 operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	 operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
	 operationalFlightVO.setDirection(INBOUND);
	 operationalFlightVO.setPol(flightValidationVO.getLegOrigin());


	 MailArrivalFilterVO mailArrivalFilterVO = arriveDispatchSession.getMailArrivalFilterVO();

	 if(mailArrivalFilterVO==null){
		 mailArrivalFilterVO = constructMailArrivalFilterVO(operationalFlightVO);

	 }

		
		
	 arriveDispatchForm.setCarrierId(mailArrivalFilterVO.getCarrierId());
	 arriveDispatchForm.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
	 arriveDispatchForm.setLegSerialNumber(mailArrivalFilterVO.getLegSerialNumber());

	 log.log(Log.FINE, "mailArrivalFilterVO before going to server call...",
			mailArrivalFilterVO);
	try {
		 newMailArrivalVO = new MailTrackingDefaultsDelegate().findArrivalDetails(
				 mailArrivalFilterVO);
	 }catch (BusinessDelegateException businessDelegateException) {
		 errors = handleDelegateException(businessDelegateException);
	 }
	 if (errors != null && errors.size() > 0) {
		 invocationContext.addAllError(errors);

		 invocationContext.target = TARGET;
		 return;
	 }else{
		 FlightValidationVO fltVal=arriveDispatchSession.getFlightValidationVO();

		 arriveDispatchSession.setFromScreen("MailArrival");
		 arriveDispatchSession.setMailArrivalFilterVO(mailArrivalFilterVO);

		 if(fltVal!=null && fltVal.getFlightRoute()!=null){


			 if(newMailArrivalVO.getFlightStatus()==null || "".equals(newMailArrivalVO.getFlightStatus()))
			 {
				 arriveDispatchForm.setOperationalStatus("NONE");
			 }else{

				 if(oneTimes!=null){
					 Collection<OneTimeVO> resultStatus=
						 oneTimes.get("mailtracking.defaults.flightstatus");

					 fltVal.setOperationalStatus(findOneTimeDescription(resultStatus,newMailArrivalVO.getFlightStatus()));
					 resultStatus=
						 oneTimes.get("flight.operation.flightlegstatus");
					 fltVal.setStatusDescription(findOneTimeDescription(resultStatus,fltVal.getLegStatus()));
				 }


				 if("O".equals(newMailArrivalVO.getFlightStatus()))
				 {
					 arriveDispatchForm.setOperationalStatus("OPEN");
				 }else
					 if("C".equals(newMailArrivalVO.getFlightStatus()))
					 {
						 arriveDispatchForm.setOperationalStatus("CLOSED");

					 }}
			
			 arriveDispatchSession.setFlightValidationVO(fltVal);
		 }
	 }
			
		

	 if(newMailArrivalVO == null){
		 newMailArrivalVO = new MailArrivalVO();
	 }
	 newMailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
	 newMailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
     //	 Added by a-4810 for icrd-18030
	 Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	 Collection<String> fieldTypes = new ArrayList<String>();
	 fieldTypes.add(CATEGORY);
	 try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		//Map<String, Collection<OneTimeVO>> oneTimMap = (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
		arriveDispatchSession.setMailCategory( oneTimeValues.get(CATEGORY));
	

	 Collection<ContainerDetailsVO> containerDetails = newMailArrivalVO.getContainerDetails();  
	 if(containerDetails == null ||containerDetails.size() == 0){

		 invocationContext.addError(new ErrorVO("mailtracking.defaults.national.nocontainerdetails"));
	 }

	 newMailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	 newMailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	 
	 log.log(Log.FINE, "*******MAIL ARRIVAL VOS***IN ListMailArrivalCommand***",
			newMailArrivalVO);
	arriveDispatchSession.setMailArrivalVO(newMailArrivalVO);
	 arriveDispatchSession.setOperationalFlightVO(operationalFlightVO);
	 arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	 invocationContext.target = TARGET;
	 log.exiting("ListMailArrivalCommand","execute");

 }
    
 /**
  * TODO Purpose
  * 
  * @param operationalFlightVO
  * @return
  */
 private MailArrivalFilterVO constructMailArrivalFilterVO(
		 OperationalFlightVO operationalFlightVO) {
	 MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
	 filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
	 filterVO.setCarrierId(operationalFlightVO.getCarrierId());
	 filterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
	 filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
	 filterVO.setFlightSequenceNumber(
			 operationalFlightVO.getFlightSequenceNumber());
	 filterVO.setFlightDate(operationalFlightVO.getFlightDate());
	 filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
	 filterVO.setPou(operationalFlightVO.getPou());
	 filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
	 return filterVO;
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
		 fieldValues.add("mailtracking.defaults.flightstatus");

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

 /**
	 * @author a-4810
	 * @param oneTimeVOs
	 * @param code
	 * @return
	 */
	public String populateOneTimeDescription (Collection<OneTimeVO> oneTimeVOs, String code){
		 String description = code;
		if(oneTimeVOs != null && oneTimeVOs.size()>0){
			for(OneTimeVO oneTimeVO :oneTimeVOs){
				if(code.equals(oneTimeVO.getFieldValue())){
					description = oneTimeVO.getFieldDescription();
					return description;
				}

			}
		}
		return description;		
	}

}
