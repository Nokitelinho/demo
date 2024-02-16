/*
 * ReassignMailbagCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightValidation;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ReassignMailbagCommand extends AbstractCommand {

	   private Log log = LogFactory.getLogger("ReassignMailbagCommand");	  
	   private static final String CONST_RETURN_CODE = "RTN";
	   private static final String CONST_ACCEPT_FLG = "ACP";
	   private static final String CONST_OFFLOAD_FLG = "OFL";
	   private static final String CONST_NOTUPTIFTED_FLG = "NUP";
	   private static final String CONST_ARRIVED_FLG = "ARR";
	   private static final String CONST_DELIVERED_FLG = "DLV";
	   private static final String CONST_SCREENID = "MAIL ENQ";
	   private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	   private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
	   private static final String FLIGHT= "FLIGHT";
	   private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ReassignMailbagCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();

		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailbags = null;
		ContainerDetails selectedContainer = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		Collection<MailbagVO> mailbagVOs = null;

		String assignTo = mailbagEnquiryModel.getAssignToFlight();
		String mailbags = "";

		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		Integer errorFlag = 0;
		ErrorVO errorVO = null;
		String screenId=CONST_SCREENID;
		if(mailbagEnquiryModel.getNumericalScreenId()!=null){
		screenId= mailbagEnquiryModel.getNumericalScreenId();
		}
		Map<String, String> warningMap = mailbagEnquiryModel.getWarningMessagesStatus();
		if ((warningMap == null) || (warningMap.isEmpty()))
		{
		  warningMap = new HashMap<String, String>();
		  mailbagEnquiryModel.setWarningMessagesStatus(warningMap);
		}

		if(mailbagEnquiryModel.getScanDate()==null||mailbagEnquiryModel.getScanDate().trim().length()==0){
			actionContext.addError(new ErrorVO("Enter Scan Date"));
			return;
		}
		if(mailbagEnquiryModel.getScanTime()==null||mailbagEnquiryModel.getScanTime().trim().length()==0){
			actionContext.addError(new ErrorVO("Enter Scan Time"));
			return;
		}
		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			selectedContainer = mailbagEnquiryModel.getSelectedContainer();
			log.log(Log.FINE, "selectedContainers --------->>", selectedContainer);
			
			boolean isReturnedMailbag = false;
			boolean isCapNotAcceptedMailbag = false;

			for (Mailbag selected : selectedMailbags) {
				if (CONST_RETURN_CODE.equals(selected.getLatestStatus())) {
					isReturnedMailbag = true;
					break;
				}
				if (MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selected.getLatestStatus())) {
					isCapNotAcceptedMailbag = true;
					break;
				}

				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(selected.getLatestStatus())){ 
   						
   				actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.arrivedcannotreassign"));
				return;
   			   }
				
				if (!logonAttributes.getAirportCode().equals(selected.getScannedPort())
						&& !(CONST_ACCEPT_FLG.equals(selected.getLatestStatus())
              			 || CONST_OFFLOAD_FLG.equals(selected.getLatestStatus())
              			 || CONST_NOTUPTIFTED_FLG.equals(selected.getLatestStatus()))) {
              		 
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));	
              	 		return;
              	 	}
              	 	if(CONST_ARRIVED_FLG.equals(selected.getLatestStatus()) || CONST_DELIVERED_FLG.equals(selected.getLatestStatus())){
              	 		actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));	
          	 			return;
              	 	}
              	   
           	 	}
			if (isCapNotAcceptedMailbag) {
				
				actionContext.addError(new ErrorVO("mailtracking.defaults.err.capturedbutnotaccepted"));
				return;
			}
			if (isReturnedMailbag) {
				
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.returnedMailbagsCannotReassign"));
				return;
			}

			ContainerVO containerVO = null;
			if (selectedContainer != null ) {

				containerVO = MailOperationsModelConverter.constructContainerVO(selectedContainer, logonAttributes);
			
			}

			FlightValidation flightValidation = mailbagEnquiryModel.getFlightValidation();

			if (FLIGHT.equals(mailbagEnquiryModel.getAssignToFlight())) {
				// flightValidationVO =
				// mailbagEnquiryModel.getFlightValidation();--- Code for
				// validation VO to be added
				//Based on Soncy comment
				//IASCB-63549: Modifed
				boolean isToBeActioned = FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidation.getFlightStatus());
				isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
				if ((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidation.getFlightStatus())
						|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidation.getFlightStatus()))) {
					Object[] obj = { flightValidation.getCarrierCode().toUpperCase(),
							flightValidation.getFlightNumber() };
					errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba", obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				if (flightValidation.getOperationalStatus() != null
						&& ("Closed").equals(flightValidation.getOperationalStatus())) {

					errorVO = new ErrorVO("mailtracking.defaults.reassignmail.errorVO.flightclosed", new Object[] {
							flightValidation.getCarrierCode(), flightValidation.getFlightNumber(),
							flightValidation.getApplicableDateAtRequestedAirport()});
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				if (flightValidation.getAtd() != null) {
					containerVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
				}
			}

			if (mailbagEnquiryModel.getScanDate() == null || ("").equals(mailbagEnquiryModel.getScanDate())) {

				errorVO = new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(errorVO);
				return;
			}
			if (mailbagEnquiryModel.getScanTime() == null || ("").equals(mailbagEnquiryModel.getScanTime())) {

				errorVO = new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(errorVO);
				return;
			}
			String scanDateStr = new StringBuilder().append(mailbagEnquiryModel.getScanDate()).append(" ")
					.append(mailbagEnquiryModel.getScanTime()).append(":00").toString();
			LocalDate scanDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			scanDate.setDateAndTime(scanDateStr);

			if (containerVO != null) {

				containerVO.setOperationTime(scanDate);

				if ("Y".equals(containerVO.getArrivedStatus())) {

					errorVO = new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
							new Object[] { containerVO.getContainerNumber() });
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				if (containerVO.getContainerNumber() != null && containerVO.getContainerNumber().trim().length() > 3) {
					if ("OFL".equals(containerVO.getContainerNumber().substring(0, 3))) {

						errorVO = new ErrorVO("mailtracking.defaults.reassignmail.offloadedmails",
								new Object[] { containerVO.getContainerNumber() });
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(errorVO);
						return;
					}
				}

				if (containerVO.getContainerNumber() != null && containerVO.getContainerNumber().length() > 5) {
					if ("TRASH".equals(containerVO.getContainerNumber().substring(0, 5))) {

						errorVO = new ErrorVO("mailtracking.defaults.reassignmail.trashmails",
								new Object[] { containerVO.getContainerNumber() });
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(errorVO);
						return;
					}
				}

				containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
				
				containerVO.setMailSource(screenId);	// Added for IASCB-4749
					
			}

			// Validation for already delivered mailbags

			mailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags, logonAttributes);

			for (MailbagVO mailbagVO : mailbagVOs) {

				// EmptyULDsSession emptyUldsSession =
				// getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);

				if (FLIGHT.equals(assignTo)) {

					if (mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& containerVO.getFlightNumber().equals(mailbagVO.getFlightNumber())
							&& mailbagVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
							&& mailbagVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
							&& containerVO.getContainerNumber().equals(mailbagVO.getContainerNumber())) {
						errorFlag = 1;
						if ("".equals(mailbags)) {
							mailbags = mailbagVO.getMailbagId();
						} else {
							mailbags = new StringBuilder(mailbags).append(",").append(mailbagVO.getMailbagId())
									.toString();
						}
					}
				} else {
					if (mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& containerVO.getContainerNumber().equals(mailbagVO.getContainerNumber())
							&& containerVO.getFinalDestination().equals(mailbagVO.getPou())) {
						errorFlag = 1;
						if ("".equals(mailbags)) {
							mailbags = mailbagVO.getMailbagId();
						} else {
							mailbags = new StringBuilder(mailbags).append(",").append(mailbagVO.getMailbagId())
									.toString();
						}
					}
				}

				mailbagVO.setScannedDate(scanDate);
				mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				mailbagVO.setScannedUser(logonAttributes.getUserId());
				mailbagVO.setMailSource(screenId);// Added for IASCB-47499
				

				// Added for ICRD-204654 starts
				if (mailbagVO.getPou() != null && !mailbagVO.getPou().isEmpty()) {
					if (MailConstantsVO.CONST_BULK.equals(mailbagVO.getContainerType())) {
						StringBuilder sb = new StringBuilder(10);
						mailbagVO.setUldNumber(sb.append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
								.append(mailbagVO.getPou()).toString());
					}
				}
				List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
				if (errorFlag == 1) {

					errorVO = new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
							new Object[] { mailbags, mailbagVO.getContainerNumber() });
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					//ErrorVO warningError = new ErrorVO(errorVO.getErrorCode());
					//warningError.setErrorDisplayType(ErrorDisplayType.WARNING_MULTIOPTION);
					//warningErrors.add(warningError);
					//actionContext.addAllError(warningErrors); 
					return;
				}
				String overrideWarningtoNonaPAbuilt=null;
				String overrideWarningtoPAbuilt=null;
				Map<String, String> existigWarningMap = mailbagEnquiryModel.getWarningMessagesStatus();
				if(existigWarningMap != null && existigWarningMap.size()>0) {
					overrideWarningtoNonaPAbuilt=existigWarningMap.get("mailtracking.defaults.reassignmail.pabuiltToNonPabuilt");
				}
				if(overrideWarningtoNonaPAbuilt == null){  
					overrideWarningtoNonaPAbuilt = ContainerVO.FLAG_NO;	
		    	}
				if(overrideWarningtoNonaPAbuilt.equalsIgnoreCase(ContainerVO.FLAG_NO)  &&mailbagVO.getScannedPort()!=null&& (mailbagVO.getScannedPort().equals(mailbagVO.getAcceptanceAirportCode())) &&  mailbagVO.getPaBuiltFlag() !=null && mailbagVO.getPaBuiltFlag().equals("Y") &&(containerVO.getPaBuiltFlag() ==null || containerVO.getPaBuiltFlag().equals("N"))){
					ErrorVO warningError = new ErrorVO(
							"mailtracking.defaults.reassignmail.pabuiltToNonPabuilt",
							new Object[] { mailbagVO.getContainerNumber() });
					warningError.setErrorDisplayType(ErrorDisplayType.WARNING_MULTIOPTION);
					warningErrors.add(warningError);
					actionContext.addAllError(warningErrors); 
					return;
				 }
				if(existigWarningMap != null && existigWarningMap.size()>0) {
					overrideWarningtoPAbuilt=existigWarningMap.get("mailtracking.defaults.reassignmail.pabuiltToPabuilt");
				}
				if(overrideWarningtoPAbuilt == null){  
					overrideWarningtoPAbuilt = ContainerVO.FLAG_NO;	
		    	}
				if(overrideWarningtoPAbuilt.equalsIgnoreCase(ContainerVO.FLAG_NO) &&mailbagVO.getScannedPort()!=null&& (mailbagVO.getScannedPort().equals(mailbagVO.getAcceptanceAirportCode())) && mailbagVO.getPaBuiltFlag() !=null && mailbagVO.getPaBuiltFlag().equals("Y") &&( containerVO.getPaBuiltFlag() !=null && containerVO.getPaBuiltFlag().equals("Y"))) {
					ErrorVO warningError = new ErrorVO(
							"mailtracking.defaults.reassignmail.pabuiltToPabuilt",
							new Object[] { mailbagVO.getContainerNumber() });
					warningError.setErrorDisplayType(ErrorDisplayType.WARNING_MULTIOPTION);
					warningErrors.add(warningError);
					actionContext.addAllError(warningErrors); 
					return;
				}

				log.log(Log.FINE, "\n\n mailbagVOs for reassign ------->", mailbagVOs);
				log.log(Log.FINE, "\n\n containerVO for reassign --------->", containerVO);

				//// ********************
				if (doSecurityScreeningValidations(mailbagVO,flightValidation,assignTo,logonAttributes,actionContext,existigWarningMap,containerVO)){
					return;
				}
			}

			if (mailbagVOs != null && mailbagVOs.size() > 0 && containerVO != null) {

				try {

					containerDetailsVOs = new MailTrackingDefaultsDelegate().reassignMailbags(mailbagVOs, containerVO);

				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList) handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					//actionContext.addAllError(errors);
					ErrorVO curError = errors.iterator().next();
			    	 if(curError.getErrorCode().equals("mailtracking.defaults.err.flightclosed")){
			    		 curError.setErrorCode("mailtracking.defaults.err.flightclosedforreassign");
			    	 }
			    	 actionContext.addError(curError);
					return;
				}
			}

			log.log(Log.FINE, "containerDetailsVOs ----->>", containerDetailsVOs);
			if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
				// Code to be corrected
				// emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
				// reassignMailForm.setCloseFlag("SHOWPOPUP");
				// invocationContext.target = TARGET;
				return;
			}

		}

		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ReassignMailbagCommand", "execute");

	}
	/**
	 * for AA no need to validate against TBC flight
	 * @return
	 */
	private boolean canIgnoreToBeActionedCheck() {
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add("mail.operations.ignoretobeactionedflightvalidation");
		Map<String, String> systemParameters = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO, "caught BusinessDelegateException ");
		}
		if(systemParameters!=null && systemParameters.containsKey("mail.operations.ignoretobeactionedflightvalidation")) {
			return "Y".equals(systemParameters.get("mail.operations.ignoretobeactionedflightvalidation"));
		}
		return false;
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailAcceptanceVO
	 * @param flightCarrierFlag
	 * @param logonAttributes 
	 * @param actionContext 
	 * @param containerVO 
	 * @param errors 
	 * @param outboundModel 
	 * @param warningMap 
	 * @throws BusinessDelegateException 
	 */
	private boolean doSecurityScreeningValidations(MailbagVO mailbagVO, FlightValidation flightValidation, String assignTo,
			LogonAttributes logonAttributes, ActionContext actionContext, Map<String, String> existigWarningMap, ContainerVO containerVO) throws BusinessDelegateException {
		String securityWarningStatus = "N";
		if(existigWarningMap != null && existigWarningMap.size()>0 && existigWarningMap.containsKey(SECURITY_SCREENING_WARNING)) {
			securityWarningStatus=existigWarningMap.get(SECURITY_SCREENING_WARNING);
		}
		if(MailConstantsVO.FLAG_NO.equals(securityWarningStatus)) {
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
			securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			if(FLIGHT.equals(assignTo)){
				securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				securityScreeningValidationFilterVO.setFlightType(flightValidation.getFlightType());
				securityScreeningValidationFilterVO.setTransistAirport(containerVO.getPou());
			}
			else{
				securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			}
			securityScreeningValidationFilterVO.setAppRegValReq(true);
			securityScreeningValidationFilterVO.setTransactionAirport(logonAttributes.getAirportCode());
			securityScreeningValidationFilterVO.setOriginAirport(mailbagVO.getOrigin());
			securityScreeningValidationFilterVO.setDestinationAirport(mailbagVO.getDestination());
			securityScreeningValidationFilterVO.setMailbagId(mailbagVO.getMailbagId());
			securityScreeningValidationFilterVO.setSubClass(mailbagVO.getMailSubclass());
			securityScreeningValidationVOs= new MailTrackingDefaultsDelegate().findSecurityScreeningValidations(securityScreeningValidationFilterVO);
			if (securityScreeningValidationVOs!=null &&! securityScreeningValidationVOs.isEmpty()){
				for( SecurityScreeningValidationVO securityScreeningValidationVO:securityScreeningValidationVOs){
					if( checkForWarningOrError(mailbagVO, actionContext, securityScreeningValidationVO)){
						return true;
					}


				}
			}
		}
		return false;
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param actionContext
	 * @param existigWarningMap
	 * @param securityScreeningValidationVO
	 * @return
	 */
	private boolean checkForWarningOrError(MailbagVO mailbagVO, ActionContext actionContext, SecurityScreeningValidationVO securityScreeningValidationVO) {
		if ("W".equals(securityScreeningValidationVO
				.getErrorType())) {
				List<ErrorVO> warningErrors = new ArrayList<>();
				ErrorVO warningError = new ErrorVO(
						SECURITY_SCREENING_WARNING,
						new Object[]{mailbagVO.getMailbagId()});
				warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
				warningErrors.add(warningError);
				actionContext.addAllError(warningErrors); 
				return true;
		}
		if ("E".equals(securityScreeningValidationVO
				.getErrorType())) {
			if ("AR".equals(securityScreeningValidationVO
					.getValidationType())){
				actionContext.addError(new ErrorVO(APPLICABLE_REGULATION_ERROR));
			}
			else{
			actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
					new Object[]{mailbagVO.getMailbagId()}));
			}
			return true;
		}
		return false;
	}

}
