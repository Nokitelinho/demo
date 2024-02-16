/*
 * DeliverMailbagsCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class DeliverMailbagsCommand extends AbstractCommand {

	private static final String CONST_DELIVERED_FLG = "DLV";
	private static final String CONST_ARRIVED_FLG = "ARR";

	private Log log = LogFactory.getLogger("DeliverMailbagsCommand");
	private static final String ONLINE_HANDLED_AIRPORT="operations.flthandling.isonlinehandledairport";
	private static final String ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING="operations.flthandling.enableatdcapturevalildationforimporthandling";
	private static final String ATD_NOT_CAPTURED="mailtracking.defaults.mailbagenquiry.msg.err.atdnotcaptured";
	private static final String ATD_NOT_CAPTURED_FOR_ONE_ULD="mailtracking.defaults.mailbagenquiry.msg.err.atdnotcapturedforoneuld";
	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("DeliverMailbagsCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailbags = null;
		Collection<MailArrivalVO> mailArrivalVOs = null;
		Collection<String> DOEs = new ArrayList<String>();

		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		Integer errorFlag = 0;

		String airport = logonAttributes.getAirportCode();
		String companyCode = logonAttributes.getCompanyCode();

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			//Validation for already delivered mailbags
			for (Mailbag selectedvo : selectedMailbags) {

				if (CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())) {
					errors.add(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.mailbagalreadydelivered"));
					actionContext.addAllError(errors);
					return;
				}

				if (!CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus())) {
					errors.add(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannotdeliveratthisport",
							new Object[] { logonAttributes.getAirportCode() }));
					actionContext.addAllError(errors);
					return;
				}

			}

			// validate Destination office of exchanges
			for (Mailbag selectedvo : selectedMailbags) {
				DOEs.add(selectedvo.getDoe());
			}
			errorFlag = validateDOEs(DOEs, companyCode, airport);

			if (1 == errorFlag) {
				
				log.log(Log.FINE, "Error case ----");
				errors.add(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannotdeliveratthisport",
						new Object[] { logonAttributes.getAirportCode() }));
				actionContext.addAllError(errors);
				return;
			}

			// validate flight closure
			String fltNo = "";
			String carrierCode = "";
			String scannedPort = "";
			long fltseqNo = 0;
			int carrierid = 0;
			LocalDate fltDate = null;

			for (Mailbag selectedvo : selectedMailbags) {

				log.log(Log.FINE, "Inside VO creation ----");
				
				fltNo = (selectedvo.getFlightNumber() != null) ? selectedvo.getFlightNumber() : "";
				fltseqNo = selectedvo.getFlightSequenceNumber();
				carrierid = selectedvo.getCarrierId();

				carrierCode = selectedvo.getCarrierCode();
				scannedPort = selectedvo.getScannedPort();

				if (selectedvo.getFlightDate() != null && selectedvo.getFlightDate().trim().length() > 0) {

					fltDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
					fltDate.setDate(selectedvo.getFlightDate());
				}
				// Validating Flight to obtain the LegSerialNumber
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				flightFilterVO.setFlightNumber(fltNo);
				flightFilterVO.setStation(scannedPort);
				flightFilterVO.setActiveAlone(false);
				flightFilterVO.setStringFlightDate(String.valueOf(fltDate));
				flightFilterVO.setFlightDate(fltDate);
				flightFilterVO.setCarrierCode(carrierCode);
				flightFilterVO.setFlightCarrierId(carrierid);
				flightFilterVO.setFlightSequenceNumber(fltseqNo);
				Collection<FlightValidationVO> flightValidationVOs = null;
				FlightValidationVO flightValidationVO = null;

				try {
					log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
					flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList<ErrorVO>) handleDelegateException(businessDelegateException);
				}

				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					log.log(Log.FINE, "SIZE ------------> ", flightValidationVOs.size());
					flightValidationVO = ((ArrayList<FlightValidationVO>) flightValidationVOs).get(0);

				}

				log.log(Log.FINE, "flightValidationVO ------------> ", flightValidationVO);
				// Validating Flight Closure
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
				operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVO.setPou(selectedvo.getPou());

				if (FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())) {

					operationalFlightVO.setLegSerialNumber(selectedvo.getLegSerialNumber());
				} else {
					operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					selectedvo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				}
				try {
					isFlightClosed = mailTrackingDefaultsDelegate
							.isFlightClosedForInboundOperations(operationalFlightVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList<ErrorVO>) handleDelegateException(businessDelegateException);
				}

				if (errors != null && errors.size() > 0) {
					actionContext.addAllError(errors);					
					return;
				}
				log.log(Log.FINE, "MailbagEnquiry>OffloadMailCommand->isFlightClosed->", isFlightClosed);

				if (isFlightClosed) {
					Object[] obj = { new StringBuilder(carrierCode).append("").append(fltNo).append(" on ")
							.append(fltDate.toDisplayDateOnlyFormat()).toString() };
					errors.add(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.flightclosed", obj));
					actionContext.addAllError(errors);
					return;
				}

			}
			//below code will check whether user has privilege to deliver selected mailbags 
			  //start here
				ArrayList<String> systemParameters = new ArrayList<String>();
					systemParameters.add("mail.operations.deliveryrestictedpacodes");
					systemParameters.add(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
				Map<String, String> systemParameterMap=null;
				String deliveryRestictedPACodes = "";
				List<String> restictedPACodes = new ArrayList<>();
					try {
						systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
					} catch (BusinessDelegateException businessDelegateException) {
						log.log(Log.INFO,"businessDelegateException");
					}
				if(systemParameterMap!=null && systemParameterMap.containsKey("mail.operations.deliveryrestictedpacodes"));
				{
					deliveryRestictedPACodes = systemParameterMap.get("mail.operations.deliveryrestictedpacodes");
				}
				if(deliveryRestictedPACodes!=null && !deliveryRestictedPACodes.isEmpty()){
				restictedPACodes = Arrays.asList(deliveryRestictedPACodes.split(",")) ;
				}
				Map<String,Boolean> paCodeDeliveryPrvlg = new HashMap<>();
				StringBuilder privilegeCode = null;
				//dynamically construct privilege code
				for(String paCode:restictedPACodes) {
					privilegeCode = new StringBuilder("mail.operations.mailbagdeliveryprivilege.").append(paCode);
					paCodeDeliveryPrvlg.put(paCode, hasPrivilege(privilegeCode.toString()));
				}     
					if(selectedMailbags !=null){
		      			for(Mailbag mailbag :selectedMailbags ){
		      				if(mailbag.getPaCode()!=null && paCodeDeliveryPrvlg.containsKey(mailbag.getPaCode())) {
		      					if(!paCodeDeliveryPrvlg.get(mailbag.getPaCode())) {
		      						actionContext.addError(new ErrorVO("User does not have privilege to deliver the mailbag "));
		      						return;
		      					}
		      				}
		      			}
					}
				//end here
			int errorval=0;
			
			errorval= validateATDCapture(mailTrackingDefaultsDelegate, selectedMailbags, systemParameterMap,errorval);
			if(errorval== 1){
				 ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED_FOR_ONE_ULD);
				  actionContext.addError(errorVO);
				  return;
			}
			else{
				if(errorval== 2){
					ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED);
					  actionContext.addError(errorVO);
					  return;
				}
			}				
			mailArrivalVOs = makeMailArrivalVOsForDelivery(selectedMailbags, logonAttributes);
			

			try {
				new MailTrackingDefaultsDelegate().saveScannedDeliverMails(mailArrivalVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = (ArrayList<ErrorVO>) handleDelegateException(businessDelegateException);
			}
			

		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}		
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("DeliverMailbagsCommand", "execute");

	}
	private int validateATDCapture(
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate, Collection<Mailbag> selectedMailbags,
			Map<String, String> systemParameterMap,int errorval) throws BusinessDelegateException {
		String valildationforimporthandling = null;
		if (systemParameterMap != null) {
			valildationforimporthandling=systemParameterMap.get(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
		}
		if("Y".equals(valildationforimporthandling)){
			for (Mailbag selectedvo : selectedMailbags) {
				 FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(selectedvo.getCompanyCode());
				flightFilterVO.setFlightCarrierId(selectedvo.getCarrierId());
				flightFilterVO.setFlightNumber(selectedvo.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(selectedvo.getFlightSequenceNumber());
				
				errorval=doATDValidation(mailTrackingDefaultsDelegate, selectedMailbags, flightFilterVO,errorval);	
			}
		}
		return errorval;
	}
	private int doATDValidation(MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate,
			Collection<Mailbag> selectedMailbags, FlightFilterVO flightFilterVO,int seterror) throws BusinessDelegateException {
		Collection<FlightValidationVO> flightValidationVOs = null;
		 String onlineHndledParameter = null;
		flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			
			String fltOrigin =  flightValidationVOs.iterator().next().getLegOrigin();
			flightFilterVO.setAirportCode(fltOrigin);
			Collection<String> parCodes =new ArrayList<>();
			parCodes.add(ONLINE_HANDLED_AIRPORT);
			Map<String, String> arpMap= mailTrackingDefaultsDelegate.findAirportParameterCode(flightFilterVO,parCodes);
			 onlineHndledParameter =arpMap.get(ONLINE_HANDLED_AIRPORT);
			
			 if(("N").equals(onlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null && selectedMailbags.size() >1){
				 seterror = 1; 
				}
			 
			 else{
				 if(("N").equals(onlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null && selectedMailbags.size()==1){
					seterror = 2; 
				 }
			 }
				 
			
		}
		return seterror;
	}
	public boolean hasPrivilege(String privilegeCode){
        boolean hasPrivilege = false;
        SecurityAgent agent = null;
        try {
        	agent = SecurityAgent.getInstance();
			hasPrivilege = agent.checkPrivilegeForAction(privilegeCode);
		} catch (SystemException e) {
			hasPrivilege = false;
		}
        return hasPrivilege;
    }
	private Collection<MailArrivalVO> makeMailArrivalVOsForDelivery(Collection<Mailbag> selectedMailbags,
			LogonAttributes logonAttributes) throws BusinessDelegateException {
		log.entering("DeliverMailCommand", "makeMailArrivalVOsForDelivery");
		
		Map<String, MailArrivalVO> mailArrivalVOmap = new HashMap<String, MailArrivalVO>();
		Collection<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
		MailArrivalVO mailArrivalVOForDelivery = null;
		String deliverMapKey = null;
		MailbagVO mailbagVO = null;

		ContainerDetailsVO containerDetailsVO = null;
		LocalDate fltDate = null;	

		if (selectedMailbags != null && selectedMailbags.size() > 0) {
			for (Mailbag mailbagVOToSave : selectedMailbags) {
				
				mailArrivalVOForDelivery = new MailArrivalVO();

				deliverMapKey = new StringBuilder(mailbagVOToSave.getCompanyCode())
						.append(mailbagVOToSave.getCarrierCode()).append(mailbagVOToSave.getFlightNumber())
						.append(mailbagVOToSave.getFlightDate()).append(mailbagVOToSave.getCarrierId())
						.append(mailbagVOToSave.getFlightSequenceNumber()).append(mailbagVOToSave.getLegSerialNumber())
						.append(mailbagVOToSave.getPou()).append(mailbagVOToSave.getContainerNumber()).toString();
				
				if (!mailArrivalVOmap.containsKey(deliverMapKey)) {

					
					mailArrivalVOForDelivery.setCompanyCode(mailbagVOToSave.getCompanyCode());
					mailArrivalVOForDelivery.setFlightNumber(mailbagVOToSave.getFlightNumber());
					
					fltDate = null;
					if (mailbagVOToSave.getFlightDate() != null && mailbagVOToSave.getFlightDate().trim().length() > 0) {

						fltDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
						fltDate.setDate(mailbagVOToSave.getFlightDate());
					}
					mailArrivalVOForDelivery.setFlightDate(fltDate);
					mailArrivalVOForDelivery.setArrivalDate(fltDate);
					mailArrivalVOForDelivery.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					mailArrivalVOForDelivery.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					mailArrivalVOForDelivery.setCarrierId(mailbagVOToSave.getCarrierId());
					mailArrivalVOForDelivery.setPol(mailbagVOToSave.getPol());
					mailArrivalVOForDelivery.setPou(mailbagVOToSave.getPou());
					mailArrivalVOForDelivery.setSegmentSerialNumber(mailbagVOToSave.getSegmentSerialNumber());					
					
					
					mailArrivalVOForDelivery.setAirportCode(logonAttributes.getAirportCode());
					mailArrivalVOForDelivery.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailArrivalVOForDelivery.setArrivedUser(logonAttributes.getUserId().toUpperCase());
					LocalDate scanDate = null;
					if (mailbagVOToSave.getScannedDate() != null) {

						scanDate = new LocalDate(mailbagVOToSave.getScannedPort(), ARP, false);
						scanDate.setDate(mailbagVOToSave.getScannedDate());
						mailArrivalVOForDelivery.setScanDate(scanDate);
					}
					mailArrivalVOForDelivery.setScanned(true);
					if(mailbagVOToSave.getDeliveryRemarks()!=null && mailbagVOToSave.getDeliveryRemarks().trim().length()>0) {
						mailArrivalVOForDelivery.setDeliveryRemarks(mailbagVOToSave.getDeliveryRemarks());
					}
					Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
					
					
					containerDetailsVO = new ContainerDetailsVO();
					

					containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					containerDetailsVO.setFlightNumber(mailbagVOToSave.getFlightNumber());
					containerDetailsVO.setFlightDate(fltDate);
					containerDetailsVO.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					containerDetailsVO.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					containerDetailsVO.setPou(logonAttributes.getAirportCode());
					containerDetailsVO.setCarrierId(mailbagVOToSave.getCarrierId());
					containerDetailsVO.setCarrierCode(mailbagVOToSave.getCarrierCode());
					containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);// to
																							// do
					containerDetailsVO.setContainerNumber(mailbagVOToSave.getContainerNumber());
					containerDetailsVO.setContainerType(mailbagVOToSave.getContainerType());
					containerDetailsVO.setSegmentSerialNumber(mailbagVOToSave.getSegmentSerialNumber());

					containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
						containerDetailsVO.setContainerNumber(constructBulkULDNumber(logonAttributes.getAirportCode()));
					}

					if (containerDetailsVO.getPol() == null) {
						containerDetailsVO.setDestination(logonAttributes.getAirportCode());
						containerDetailsVO.setPol(mailbagVOToSave.getPol());
					}

					containerDetailsVOs.add(containerDetailsVO);
					mailArrivalVOForDelivery.setContainerDetails(containerDetailsVOs);
					mailArrivalVOmap.put(deliverMapKey, mailArrivalVOForDelivery);

				} else {
					mailArrivalVOForDelivery = mailArrivalVOmap.get(deliverMapKey);

					if (mailArrivalVOForDelivery.getContainerDetails() != null
							&& mailArrivalVOForDelivery.getContainerDetails().size() > 0) {

						containerDetailsVO = mailArrivalVOForDelivery.getContainerDetails().iterator().next();
					}
				}

				mailbagVO = new MailbagVO();
				mailbagVO = constructMailbagVO(mailbagVOToSave);

				mailbagVO.setCarrierId(mailbagVOToSave.getCarrierId());
				mailbagVO.setContainerNumber(mailbagVOToSave.getContainerNumber());
				mailbagVO.setContainerType(mailbagVOToSave.getContainerType());
				mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				mailbagVO.setCarrierCode(containerDetailsVO.getCarrierCode());
				mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
				mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				mailbagVO.setFlightDate(containerDetailsVO.getFlightDate());
				mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				mailbagVO.setContainerType(containerDetailsVO.getContainerType());
				if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
					mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
				} else {
					mailbagVO.setUldNumber(constructBulkULDNumber(containerDetailsVO.getPou()));
				}
				mailbagVO.setPou(containerDetailsVO.getPou());
				mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
				// mailbagToArrive.setArrivedFlag(MailConstantsVO.FLAG_YES);
				mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_YES);// to do
				mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
				if (mailbagVO.getSegmentSerialNumber() > 0) {
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);

					mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);

				} else {
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					mailbagVO.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
				}

				if (containerDetailsVO.getMailDetails() == null) {

					containerDetailsVO.setMailDetails(new ArrayList<MailbagVO>());
				}
				containerDetailsVO.getMailDetails().add(mailbagVO);

				containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags() + 1);
				
				mailArrivalVOForDelivery.setPartialDelivery(false);
				

			}

		
		}
		
		if(mailArrivalVOmap.values()!=null && mailArrivalVOmap.values().size()>0){
			for(MailArrivalVO mailarrivalVO : mailArrivalVOmap.values()){
				mailArrivalVOs.add(mailarrivalVO);
			}
			
		}
		
		HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
			for (Mailbag mailbgVO : selectedMailbags) {
				int numBags = 0;
				double bagWgt = 0;
				String outerpk = mailbgVO.getOoe()+mailbgVO.getDoe()
				+(mailbgVO.getMailSubclass())
				+ mailbgVO.getMailCategoryCode()
				+mailbgVO.getDespatchSerialNumber()+mailbgVO.getYear();
				if(dsnMap.get(outerpk) == null){
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbgVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbgVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbgVO.getDoe());
					dsnVO.setMailClass(mailbgVO.getMailSubclass().substring(0,1));
					dsnVO.setMailSubclass(mailbgVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbgVO.getMailCategoryCode());
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					dsnVO.setYear(mailbgVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					for(Mailbag innerVO : selectedMailbags){
						String innerpk = innerVO.getOoe()+innerVO.getDoe()
						+(innerVO.getMailSubclass())
						+ innerVO.getMailCategoryCode()
						+innerVO.getDespatchSerialNumber()+innerVO.getYear();
						if(outerpk.equals(innerpk)){
							numBags = numBags + 1;
							//bagWgt = bagWgt + innerVO.getWeight();
							bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
						}
					}
					dsnVO.setReceivedBags(numBags);
					//dsnVO.setReceivedWeight(bagWgt);
					dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
					dsnMap.put(outerpk,dsnVO);
					numBags = 0;
					bagWgt = 0;
				}
			}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		int totBags = 0;
		double totWgt = 0;
		for(String key:dsnMap.keySet()){
			DSNVO dsnVO = dsnMap.get(key);
			totBags = totBags + dsnVO.getReceivedBags();
			//totWgt = totWgt + dsnVO.getReceivedWeight();
			totWgt = totWgt + dsnVO.getReceivedWeight().getRoundedSystemValue();//added by A-7371
			newDSNVOs.add(dsnVO);
		}
		containerDetailsVO.setDsnVOs(newDSNVOs);
		log.exiting("MailController", "makeMailArrivalVOsForDelivery");
		return mailArrivalVOs;
	}

	private MailbagVO constructMailbagVO(Mailbag mailbagDetails) throws BusinessDelegateException {
		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(mailbagDetails.getCompanyCode());
		mailbagVO.setMailbagId(mailbagDetails.getMailbagId()) ;
		mailbagVO.setMailSequenceNumber(mailbagDetails.getMailSequenceNumber());
		mailbagVO.setDespatchId(mailbagDetails.getDespatchId()); 
		mailbagVO.setOoe(mailbagDetails.getOoe());
		mailbagVO.setDoe(mailbagDetails.getDoe());
		mailbagVO.setMailCategoryCode(mailbagDetails.getMailCategoryCode());
		mailbagVO.setMailSubclass(mailbagDetails.getMailSubclass());
		mailbagVO.setMailClass(mailbagDetails.getMailClass());
		mailbagVO.setYear(mailbagDetails.getYear());
		mailbagVO.setDespatchSerialNumber(mailbagDetails.getDespatchSerialNumber()); 
		mailbagVO.setReceptacleSerialNumber(mailbagDetails.getReceptacleSerialNumber()); 
		mailbagVO.setRegisteredOrInsuredIndicator(mailbagDetails.getRegisteredOrInsuredIndicator());
		mailbagVO.setHighestNumberedReceptacle(mailbagDetails.getHighestNumberedReceptacle()); 
		
		
		mailbagVO.setScannedUser(mailbagDetails.getScannedUser());
		mailbagVO.setCarrierId(mailbagDetails.getCarrierId());
		mailbagVO.setFlightNumber(mailbagDetails.getFlightNumber()); 		
		mailbagVO.setUldNumber(mailbagDetails.getUldNumber());
		mailbagVO.setDespatch(mailbagDetails.isDespatch());
				
		mailbagVO.setShipmentPrefix(mailbagDetails.getShipmentPrefix()); 
		mailbagVO.setDocumentNumber(mailbagDetails.getMasterDocumentNumber()); 
		mailbagVO.setDocumentOwnerIdr(mailbagDetails.getDocumentOwnerIdr());
		mailbagVO.setDuplicateNumber(mailbagDetails.getDuplicateNumber());
		mailbagVO.setSequenceNumber(mailbagDetails.getSequenceNumber());				
		mailbagVO.setOperationalFlag(mailbagDetails.getOperationalFlag());
		mailbagVO.setContainerType(mailbagDetails.getContainerType());
		mailbagVO.setContainerNumber(mailbagDetails.getContainerNumber()); 
		mailbagVO.setIsoffload(mailbagDetails.isIsoffload());	
		
		mailbagVO.setScannedPort(mailbagDetails.getScannedPort()); 
		mailbagVO.setLatestStatus(mailbagDetails.getLatestStatus());
		mailbagVO.setOperationalStatus(mailbagDetails.getOperationalFlag());
		mailbagVO.setDamageFlag(mailbagDetails.getDamageFlag());
		mailbagVO.setConsignmentNumber(mailbagDetails.getConsignmentNumber());
		mailbagVO.setConsignmentSequenceNumber(mailbagDetails.getConsignmentSequenceNumber()); 
		mailbagVO.setPaCode(mailbagDetails.getPaCode()); 
		mailbagVO.setLastUpdateUser(mailbagDetails.getLastUpdateUser());
		mailbagVO.setMailStatus(mailbagDetails.getMailStatus());
		mailbagVO.setDisplayUnit(mailbagDetails.getDisplayUnit());
		mailbagVO.setMailRemarks (mailbagDetails.getMailRemarks());
		mailbagVO.setCarrierCode(mailbagDetails.getCarrierCode());
		mailbagVO.setPou(mailbagDetails.getPou());
		mailbagVO.setArrivedFlag(mailbagDetails.getArrivedFlag());
		mailbagVO.setDeliveredFlag("Y");
		
		return mailbagVO;
	}

	/**
	 * @param pou
	 * @return
	 */
	private String constructBulkULDNumber(String airport) {
		/*
		 * This "airport" can be the POU / Destination
		 */
		String bulkULDNumber = "";
		if (airport != null && airport.trim().length() > 0) {
			bulkULDNumber = new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
					.append(airport).toString();
		}
		return bulkULDNumber;
	}

	/**
	 * validateDOEs will validate the DOE's of Mailbags/Dispatches to know
	 * whether the Current Airport is matching with the DOE for Deliver.
	 * 
	 * @param does
	 * @param companyCode
	 * @param airport
	 * @return
	 */
	private Integer validateDOEs(Collection<String> does, String companyCode, String airport) {
		Collection<ArrayList<String>> groupedOECityArpCodes = null;
		Integer errorFlag = 0;
		
		Collection<ErrorVO> errors = null;
				
		if (does != null && does.size() != 0) {
			try {
				/*
				 * findCityAndAirportForOE method returns
				 * Collection<ArrayList<String>> in which, the inner collection
				 * contains the values in the order : 0.OFFICE OF EXCHANGE
				 * 1.CITY NEAR TO OE 2.NEAREST AIRPORT TO CITY
				 */
				groupedOECityArpCodes = new MailTrackingDefaultsDelegate().findCityAndAirportForOE(companyCode, does);
			} catch (BusinessDelegateException businessDelegateException) {
				 errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO, "ERROR--SERVER------findCityAndAirportForOE---->>",groupedOECityArpCodes);
			}
			if (groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
				for (String doe : does) {
					for (ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
						if (cityAndArpForOE.size() == 3 && doe.equals(cityAndArpForOE.get(0))
								&& !airport.equals(cityAndArpForOE.get(2))) {
							log.log(Log.FINE, "cityAndArpForOE.get(2)---",cityAndArpForOE.get(2));
							errorFlag = 1;
							break;
						}
					}
					if (errorFlag == 1) {
						break;
					}
				}
			}
		}
		log.log(Log.FINE, "errorFlag--",errorFlag);
		return errorFlag;
	}

}
