/**
 * ReturnDispatchCommand.java Created on January 20, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.returnmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReturnDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ReturnDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ReturnDispatchCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID_ASSIGN = "mailtracking.defaults.national.assignmailbag";
	private static final String SCREEN_ID_DSN = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String SCREEN_ID= "mailtracking.defaults.national.return";
	private static final String RETURN_REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String ASG_MAIL_BAG = "ASG_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadReturnCommand", "execute");
		AssignMailBagSession assignMailBagSession = getScreenSession(MODULE_NAME, SCREEN_ID_ASSIGN);
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID_DSN);
		ReturnDispatchSession returnSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		ReturnDispatchForm returnForm = (ReturnDispatchForm)invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		FlightValidationVO flightValidationVO = new FlightValidationVO();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(returnForm);
		if (errors != null && errors.size() > 0) { 
			invocationContext.addAllError(errors);
			returnForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		Boolean statusFlag = true;

		String fromScreen = returnForm.getFromScreen();
		Collection<DSNVO> selectedDsnVOs = returnSession.getSelectedDSNVO();
		Collection<DamagedDSNDetailVO> damagedDSNDetailVOs = new ArrayList<DamagedDSNDetailVO>();
		 errors = validateReturn(returnForm,selectedDsnVOs); 
		if(errors != null && errors.size() >0){
			invocationContext.addAllError(errors);
//			returnForm.setScreenStatusFlag(ComponentAttributeConstants.
//					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;

		}

		if(ASG_MAIL_BAG.equals(fromScreen)){
			flightValidationVO = assignMailBagSession.getFlightValidationVO();
		}

		else if(DSN_ENQUIRY.equals(fromScreen)){

//			for(DSNVO dsnvo : selectedDsnVOs){
//				dsnvo.setBags(Integer.parseInt(returnForm.getPieces()));
//				dsnvo.setWeight(Double.valueOf(returnForm.getWeight()));
//
//			}
			flightValidationVO = dispatchEnquirySession.getFlightValidationVO();

		}
		damagedDSNDetailVOs = populatedamagedDSNDetailVO(returnForm,selectedDsnVOs);
		Collection<DamagedDSNVO> damagedDSNVOs = null;
		damagedDSNVOs = populatedamagedDSNVOs(damagedDSNDetailVOs,selectedDsnVOs ,returnForm);

		try{
			new MailTrackingDefaultsDelegate().returnDespatches(damagedDSNVOs);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
			statusFlag = false;
		}
		//Added by a-4810 for icrd-19343
		//this check is to bi pass updating CSGRTGMST while returning  from offloaded despatches
		if(damagedDSNVOs!=null && damagedDSNVOs.size()>0){
		 if(!"-1".equals(damagedDSNVOs.iterator().next().getFlightNumber())){
		if(statusFlag){
			try{
				new MailTrackingDefaultsDelegate().updateConsignmentDetailsForMailOperations(populateRoutingDetailsForConsignmentReturn(damagedDSNVOs, flightValidationVO), null, "RET", null, null);
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
				statusFlag = false;
			}

		}
	 }
	}	
		if(statusFlag){
			returnForm.setCloseFlag("Y");
		}
		returnForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SCREENLOAD_SUCCESS;

	}
	/**
	 * 
	 * @param returnForm
	 * @param selectedDsnVOs
	 * @return
	 * This method validates the pcs and wgt entered in the pop up
	 */
	private Collection<ErrorVO> validateReturn(ReturnDispatchForm returnForm,
			Collection<DSNVO> selectedDsnVOs) {
		Collection<ErrorVO> returnErrors = new ArrayList<ErrorVO>();
		for(DSNVO dsnvo : selectedDsnVOs){
			/*if(Integer.parseInt(returnForm.getPieces()) > dsnvo.getBags() || Double.valueOf(returnForm.getWeight()) > dsnvo.getWeight()){*/
			try {
				if(Integer.parseInt(returnForm.getPieces()) > dsnvo.getBags() 
						|| Measure.compareTo(returnForm.getWeightMeasure(), dsnvo.getWeight())>0){
					returnErrors.add(new ErrorVO("mailtracking.defaults.national.return.error.acceptedweightlessthanreturned"));
				}
			} catch (NumberFormatException | UnitException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE,"",e.getMessage());
			}
		}
		return returnErrors;
	}
	/**
	 * 
	 * @param damagedDSNDetailVOs
	 * @param mailManifestVO
	 * @param selectedDsnVOs
	 * @param returnForm
	 * @return
	 */
	private Collection<DamagedDSNVO> populatedamagedDSNVOs(
			Collection<DamagedDSNDetailVO> damagedDSNDetailVOs,  Collection<DSNVO> selectedDsnVOs, ReturnDispatchForm returnForm ) {
		Collection<DamagedDSNVO> damagedDSNVOs = new ArrayList<DamagedDSNVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ReturnDispatchSession returnSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		DamagedDSNVO damagedDSNVO = new DamagedDSNVO();
		for(DSNVO dsnvo : selectedDsnVOs){
			damagedDSNVO.setAcceptedBags(dsnvo.getBags());
			damagedDSNVO.setAcceptedWeight(dsnvo.getWeight());
			damagedDSNVO.setAirportCode(logonAttributes.getAirportCode());
			damagedDSNVO.setCarrierCode(dsnvo.getCarrierCode());
			damagedDSNVO.setCarrierId(dsnvo.getCarrierId());
			damagedDSNVO.setCompanyCode(logonAttributes.getCompanyCode());
			damagedDSNVO.setContainerNumber("BULK");
			damagedDSNVO.setContainerType("B");
			damagedDSNVO.setDamagedDsnDetailVOs(damagedDSNDetailVOs);
			damagedDSNVO.setLatestReturnedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNVO.setLatestReturnedWeight(Double.valueOf(returnForm.getWeight()));
			damagedDSNVO.setLatestReturnedWeight(returnForm.getWeightMeasure());//added by A-7371
			//damagedDSNVO.setReceivedBags(dsnvo.getBags());
			//damagedDSNVO.setReceivedWeight(dsnvo.getWeight());
			damagedDSNVO.setReturnedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNVO.setReturnedWeight(Double.valueOf(returnForm.getWeight()));
			damagedDSNVO.setReturnedWeight(returnForm.getWeightMeasure());//added by A-7371
			damagedDSNVO.setConsignmentNumber(dsnvo.getCsgDocNum());
			damagedDSNVO.setConsignmentSequenceNumber(dsnvo.getCsgSeqNum());
			damagedDSNVO.setDestinationExchangeOffice(dsnvo.getDestinationExchangeOffice());
			damagedDSNVO.setDsn(dsnvo.getDsn());
			//damagedDSNVO.setFlightDate(dsnvo.getDepDate());
			damagedDSNVO.setFlightNumber(dsnvo.getFlightNumber());
			damagedDSNVO.setFlightSequenceNumber(dsnvo.getFlightSequenceNumber());
			damagedDSNVO.setMailCategoryCode(dsnvo.getMailCategoryCode());
			damagedDSNVO.setMailClass(dsnvo.getMailClass());
			damagedDSNVO.setMailSubclass(dsnvo.getMailSubclass());
			damagedDSNVO.setOriginExchangeOffice(dsnvo.getOriginExchangeOffice());
			damagedDSNVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			damagedDSNVO.setPaCode(dsnvo.getPaCode());
			damagedDSNVO.setPou(dsnvo.getPou());
			damagedDSNVO.setSegmentSerialNumber(dsnvo.getSegmentSerialNumber());
			damagedDSNVO.setYear(dsnvo.getYear());	
			//Added by a-4810 for icrd-19343
			if("-1".equals(dsnvo.getFlightNumber() )){
				damagedDSNVO.setContainerNumber(dsnvo.getContainerNumber());
			}
			//damagedDSNVO.setRemarks(returnForm.getReturnRemarks());
			// modified as part of fix icrd-16977 by a-4810
			if(returnForm.getReturnRemarks()!=null) {
			 damagedDSNVO.setRemarks(new StringBuilder().append(returnForm.getReturnRemarks()).append(",").append(handleRemarksCode(returnForm.getReturnCode(),returnSession )).toString());
			}
			damagedDSNVOs.add(damagedDSNVO);


		}
		return damagedDSNVOs;
	}
	/**
	 * 
	 * @param returnForm
	 * @param mailManifestVO
	 * @param selectedDsnVOs 
	 * @return 
	 */
	private Collection<DamagedDSNDetailVO> populatedamagedDSNDetailVO(ReturnDispatchForm returnForm,
			Collection<DSNVO> selectedDsnVOs) {
		ReturnDispatchSession returnSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<DamagedDSNDetailVO> damagedDSNVOs = new ArrayList<DamagedDSNDetailVO>();
		DamagedDSNDetailVO damagedDSNDetailVO = new DamagedDSNDetailVO();
		for(DSNVO dsnvo : selectedDsnVOs){

			damagedDSNDetailVO.setAirportCode(logonAttributes.getAirportCode());
			damagedDSNDetailVO.setDamageDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,false));
			damagedDSNDetailVO.setDamagedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNDetailVO.setDamagedWeight(Double.valueOf(returnForm.getWeight()));
			damagedDSNDetailVO.setDamagedWeight(returnForm.getWeightMeasure());//added by A-7371
			damagedDSNDetailVO.setDamageUser(logonAttributes.getUserId());
			damagedDSNDetailVO.setLatestDamagedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNDetailVO.setLatestDamagedWeight(Double.valueOf((returnForm.getWeight())));
			damagedDSNDetailVO.setLatestDamagedWeight(returnForm.getWeightMeasure());//added by A-7371
			//damagedDSNDetailVO.setRemarks(returnForm.getReturnRemarks());
			damagedDSNDetailVO.setReturnedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNDetailVO.setReturnedWeight(Double.valueOf(returnForm.getWeight()));
			damagedDSNDetailVO.setReturnedWeight(returnForm.getWeightMeasure());//added by A-7371
			damagedDSNDetailVO.setDamageCode(returnForm.getReturnCode());
			damagedDSNDetailVO.setDamageDescription(handleRemarksCode(returnForm.getReturnCode(),returnSession ));
			damagedDSNDetailVO.setMailCategoryCode(dsnvo.getMailCategoryCode());
			damagedDSNDetailVO.setMailClass(dsnvo.getMailClass());
			damagedDSNDetailVO.setMailSubclass(dsnvo.getMailSubclass());
			damagedDSNDetailVO.setReturnedPaCode(dsnvo.getPaCode());
			damagedDSNDetailVO.setOperationFlag("I");
			damagedDSNDetailVO.setLatestReturnedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNDetailVO.setLatestReturnedWeight(Double.valueOf(returnForm.getWeight()));
			damagedDSNDetailVO.setLatestReturnedWeight(returnForm.getWeightMeasure());//added by A-7371
			damagedDSNDetailVO.setTotalreturnedBags(Integer.parseInt(returnForm.getPieces()));
			//damagedDSNDetailVO.setTotalreturnedWeight(Double.valueOf(returnForm.getWeight()));
			damagedDSNDetailVO.setTotalreturnedWeight(returnForm.getWeightMeasure());//added by A-7371
			//damagedDSNDetailVO.setRemarks(returnForm.getReturnRemarks());
			// modified as part of fix icrd-16977 by a-4810
			damagedDSNDetailVO.setRemarks(new StringBuilder().append(returnForm.getReturnRemarks()).append(",").append(damagedDSNDetailVO.getDamageDescription()).toString());
			

			damagedDSNVOs.add(damagedDSNDetailVO);
		}
		return damagedDSNVOs;
	}

	/**
	 * 
	 * @param returnCode
	 * @param returnSession
	 * @return
	 */
	private String handleRemarksCode(String returnCode,
			ReturnDispatchSession returnSession) {
		String desc = "";
		Collection<OneTimeVO> remarksCodes = returnSession.getOneTimeVOs().get(RETURN_REASONCODE);
		if(remarksCodes != null){
			for(OneTimeVO onetimevo : remarksCodes){
				if(onetimevo.getFieldValue().equals(returnCode)){
					desc = onetimevo.getFieldDescription();
					break;
				}
			}
		}
		return desc;
	}

	/**
	 * 
	 * @param returnForm
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ReturnDispatchForm returnForm) {
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		if(returnForm.getReturnCode() == null || returnForm.getReturnCode().trim().length() == 0){
			errorVOs.add(new ErrorVO("mailtracking.defaults.national.return.error.returncodemandatory"));

		}
		if(returnForm.getReturnRemarks() == null || returnForm.getReturnRemarks().trim().length() == 0){
			errorVOs.add(new ErrorVO("mailtracking.defaults.national.return.error.returnremarksmandatory"));

		}
		if(returnForm.getPieces() == null || returnForm.getPieces().trim().length() == 0){
			errorVOs.add(new ErrorVO("mailtracking.defaults.national.return.error.piecesmandatory"));

		}
		if(returnForm.getWeight() == null || returnForm.getWeight().trim().length() == 0){
			errorVOs.add(new ErrorVO("mailtracking.defaults.national.return.error.weightmandatory"));

		}

		return errorVOs;
	}

	/**
	 * 
	 * @param damagedDSNVOs
	 * @param flightValidationVO
	 */
	private RoutingInConsignmentVO  populateRoutingDetailsForConsignmentReturn(Collection<DamagedDSNVO> damagedDSNVOs,FlightValidationVO flightValidationVO){
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		DamagedDSNVO damagedDSNVO = damagedDSNVOs.iterator().next();

		routingInConsignmentVO.setCompanyCode(damagedDSNVO.getCompanyCode());
		routingInConsignmentVO.setConsignmentNumber(damagedDSNVO.getConsignmentNumber());
		routingInConsignmentVO.setConsignmentSequenceNumber(damagedDSNVO.getConsignmentSequenceNumber());
		routingInConsignmentVO.setPaCode(damagedDSNVO.getPaCode());

		routingInConsignmentVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
		routingInConsignmentVO.setOnwardFlightNumber(flightValidationVO.getFlightNumber());
		routingInConsignmentVO.setOnwardCarrierId(flightValidationVO.getFlightCarrierId());
		String segment  = findFlightSector(flightValidationVO, damagedDSNVO.getSegmentSerialNumber());

		routingInConsignmentVO.setPol(segment.split("~")[0]);
		routingInConsignmentVO.setPou(segment.split("~")[1]);
		routingInConsignmentVO.setNoOfPieces(damagedDSNVO.getReturnedBags());
		routingInConsignmentVO.setWeight(damagedDSNVO.getReturnedWeight());

		return routingInConsignmentVO;
	}

	/**
	 * 
	 * @param flightValidationVO
	 * @param segmentSerialNumber
	 * @return
	 */
	private String findFlightSector(FlightValidationVO flightValidationVO,int segmentSerialNumber){
		String [] flightRoute = flightValidationVO.getFlightRoute().split("-");
		List<String> routes = new ArrayList<String>();
		if(flightRoute != null && flightRoute.length >0){
			for(int i =0;i <flightRoute.length-1;i++ ){
				for(int j =i+1;j < flightRoute.length;j++){
					routes.add(flightRoute[i].concat("~").concat(flightRoute[j]));
				}
			}


		}
		return routes.get(0);
	}

}
