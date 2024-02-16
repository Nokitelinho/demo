/*
 * TransferMailbagCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
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

public class TransferMailbagCommand extends AbstractCommand {

	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";

	private Log log = LogFactory.getLogger("TransferMailbagCommand");
	private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
	private static final String FLIGHT= "FLIGHT";
    private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("TransferMailbagCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailbags = null;

		CommodityValidationVO commodityValidationVO = null;

		ErrorVO errorVO = null;

		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		ContainerDetails selectedContainer = null;

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			//// log.entering("SaveTransferMailCommand","execute");

			String mailCommidityCode = null;
			Collection<String> commodites = new ArrayList<String>();
			Collection<String> codes = new ArrayList<String>();
			codes.add(MAIL_COMMODITY_SYS);
			Map<String, String> results = null;
			try {
				results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			if (results != null && results.size() > 0) {
				mailCommidityCode = results.get(MAIL_COMMODITY_SYS);
			}
			if (mailCommidityCode != null && mailCommidityCode.trim().length() > 0) {
				commodites.add(mailCommidityCode);
				Map<String, CommodityValidationVO> densityMap = null;
				CommodityDelegate commodityDelegate = new CommodityDelegate();

				try {
					densityMap = commodityDelegate.validateCommodityCodes(logonAttributes.getCompanyCode(), commodites);
				} catch (BusinessDelegateException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
				if (densityMap != null && densityMap.size() > 0) {
					commodityValidationVO = densityMap.get(mailCommidityCode);
					log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
				}
			}

			ContainerVO containerVO = new ContainerVO() ;  //modified as part oD IASCB-34119

			if(mailbagEnquiryModel.getAssignToFlight().equalsIgnoreCase("CARRIER")){
				
				if (mailbagEnquiryModel.getScanDate() == null || mailbagEnquiryModel.getScanDate().trim().length()==0) {

					errorVO = new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				if (mailbagEnquiryModel.getScanTime() == null || mailbagEnquiryModel.getScanTime().trim().length()==0) {

					errorVO = new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				
			}
			

			String scanDate = new StringBuilder().append(mailbagEnquiryModel.getScanDate()).append(" ")
					.append(mailbagEnquiryModel.getScanTime()).append(":00").toString();
			LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			scanDat.setDateAndTime(scanDate);

			//if ("FLIGHT".equals(mailbagEnquiryModel.getAssignToFlight())) {  //Commented as part of IASCB-34119

				log.log(Log.FINE, "selected mail ===>");
				selectedContainer = mailbagEnquiryModel.getSelectedContainer();

				if (selectedContainer != null) {
				
					containerVO = MailOperationsModelConverter.constructContainerVO(selectedContainer,
								logonAttributes);
					
				}
				containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
				containerVO.setOperationTime(scanDat);
				// Modified as a part of ICRD-214920 by A-7540
				if (null!=containerVO &&MailConstantsVO.FLAG_YES.equals(containerVO.getArrivedStatus())) { //Modified as part of IASCB-34119

					errorVO = new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
							new Object[] { containerVO.getContainerNumber() });
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				if (FLIGHT.equals(mailbagEnquiryModel.getAssignToFlight())) {  //Added as part of IASCB-34119
				FlightValidation flightValidation = mailbagEnquiryModel.getFlightValidation();
				//based on the Soncy comment
    			//IASCB-63549: Modified	
				boolean isToBeActioned = FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidation.getFlightStatus()); 
				isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
				// A-5249 from ICRD-84046
				if ((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidation.getFlightStatus())
						|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidation.getFlightStatus()))) {
					Object[] obj = { flightValidation.getCarrierCode().toUpperCase(),
							flightValidation.getFlightNumber() };
					errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba", obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				if (flightValidation.getAtd() != null) {
					containerVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
				}
			}

			Collection<MailbagVO> mailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags,
					logonAttributes);
			Collection<MailbagVO> mailbagVoForTranferAtExport = new ArrayList<>();
			Collection<MailbagVO> mailbagVoForTranferAtImport = new ArrayList<>();
			String assignTo = mailbagEnquiryModel.getAssignToFlight();
			String mailbags = "";
			int errorFlag = 0;
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					if (FLIGHT.equals(assignTo)) {
						if (mailbagVO.getCarrierId() == containerVO.getCarrierId()
								&& mailbagVO.getFlightNumber().equals(containerVO.getFlightNumber())
								&& mailbagVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
								&& mailbagVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
								&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())) {
							errorFlag = 1;
							if ("".equals(mailbags)) {
								mailbags = mailbagVO.getMailbagId();
							} else {
								mailbags = new StringBuilder(mailbags).append(",").append(mailbagVO.getMailbagId())
										.toString();
							}
						}
					} else {
						/**
						 * To validate carrier
						 */
						AirlineValidationVO airlineValidationVO = null;
						String carrier = mailbagEnquiryModel.getCarrierCode().trim().toUpperCase();
						if (carrier != null && !"".equals(carrier)) {
							try {
								airlineValidationVO = new AirlineDelegate()
										.validateAlphaCode(logonAttributes.getCompanyCode(), carrier);

							} catch (BusinessDelegateException businessDelegateException) {
								errors = (ArrayList) handleDelegateException(businessDelegateException);
							}
							if (errors != null && errors.size() > 0) {
								Object[] obj = { carrier };
								errorVO = new ErrorVO("mailtracking.defaults.invalidcarrier", obj);
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								actionContext.addError(errorVO);
								return;
							}
						}
						if(containerVO == null){  //Modified as part of IASCB-34119
						containerVO = new ContainerVO();
						} 
						containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
						containerVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerVO.setCarrierCode(carrier);
						containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						containerVO.setAssignedUser(logonAttributes.getUserId());
						containerVO.setOperationTime(scanDat);
						containerVO.setAssignedPort(logonAttributes.getAirportCode());
						containerVO.setMailSource("MAIL ENQ");// Added for
																// ICRD-156218

						if (mailbagVO.getCarrierId() == containerVO.getCarrierId()
								&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())
								&& mailbagVO.getPou().equals(containerVO.getFinalDestination())) {
							errorFlag = 1;
							if ("".equals(mailbags)) {
								mailbags = mailbagVO.getMailbagId();
							} else {
								mailbags = new StringBuilder(mailbags).append(",").append(mailbagVO.getMailbagId())
										.toString();
							}
						}
					}
				}
			}

			if (errorFlag == 1) {

				errorVO = new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
						new Object[] { mailbags, containerVO.getContainerNumber() });
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(errorVO);
				return;
			}

			FlightFilterVO flightFilterVO = null;

			for (MailbagVO mailbagVO : mailbagVOs) {
				 boolean isExportTransfer=false;
					if (logonAttributes.getAirportCode().equals(mailbagVO.getScannedPort())
							&&!(MailConstantsVO.MAIL_STATUS_ARRIVED
							.equals(mailbagVOs.iterator().next().getLatestStatus()))){
						      isExportTransfer=true;
							 if(MailConstantsVO.BULK_TYPE.equalsIgnoreCase(mailbagVO.getContainerType())
							 &&mailbagVO.getUldNumber()!=null&&mailbagVO.getUldNumber().trim().length()>0) {
							ContainerAssignmentVO containerAssignmentVO = null;
							containerAssignmentVO=mailTrackingDefaultsDelegate.findLatestContainerAssignment(mailbagVO.getUldNumber());
							if(containerAssignmentVO!=null&&containerAssignmentVO.getDestination()!=null){
								mailbagVO.setFinalDestination(containerAssignmentVO.getDestination());
							}
					     }
					}
				if (mailbagVO.getLegSerialNumber() == 0) {
					FlightValidationVO flightValidationVO = null;

					flightFilterVO = new FlightFilterVO();
					flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
					flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
					flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
					flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
					flightFilterVO.setDirection(isExportTransfer?MailConstantsVO.OPERATION_OUTBOUND:MailConstantsVO.OPERATION_INBOUND);
					flightFilterVO.setStation(mailbagVO.getScannedPort());
					Collection<FlightValidationVO> flightValidationVOs = null;

					try {
						log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
						flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = (ArrayList) handleDelegateException(businessDelegateException);
						actionContext.addAllError(errors);

					}
					if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
						for (FlightValidationVO f1 : flightValidationVOs) {
							flightValidationVO = f1;
							break;
						}
					}
					if(flightValidationVO!=null){
					mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					mailbagVO.setCarrierCode(flightValidationVO.getCarrierCode());
					mailbagVO.setFlightDate(flightValidationVO.getFlightDate());
					}
					Map<String, String> existigWarningMap = mailbagEnquiryModel.getWarningMessagesStatus();
					if (doSecurityScreeningValidations(mailbagVO,flightValidationVO,assignTo,logonAttributes,actionContext,existigWarningMap,containerVO)){
						return;
					}
				}
				mailbagVO.setScannedDate(scanDat);
				mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				mailbagVO.setScannedUser(logonAttributes.getUserId());
				// mailbagVO.setVolume(getScaledVolume(mailbagVO.getWeight()));
				mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,
						getScaledVolume(mailbagVO.getWeight().getRoundedSystemValue(), commodityValidationVO)));
				mailbagVO.setMailSource("MAIL ENQ");// Added for ICRD-156218
				if(isExportTransfer){
					mailbagVoForTranferAtExport.add(mailbagVO);
				}
				else{
					 mailbagVoForTranferAtImport.add(mailbagVO);
				}
			}

			log.log(Log.FINE, "\n\n mailbagVOs for transfer ------->", mailbagVOs);
			log.log(Log.FINE, "\n\n mailbagVOs for transfer ------->", containerVO);

			String printFlag = mailbagEnquiryModel.getPrintTransferManifestFlag();
			TransferManifestVO transferManifestVO = null;
			try {
				if (!mailbagVoForTranferAtExport.isEmpty()) {

					transferManifestVO = new MailTrackingDefaultsDelegate().transferMailAtExport(mailbagVoForTranferAtExport,
							containerVO, printFlag);
				} 
				if(!mailbagVoForTranferAtImport.isEmpty()) {
					transferManifestVO = new MailTrackingDefaultsDelegate().transferMail(null, mailbagVoForTranferAtImport, containerVO,
							"Y");   
				}
			} catch (BusinessDelegateException businessDelegateException) {
				errors = (ArrayList) handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				for (ErrorVO err : errors) {
					if ("mailtracking.defaults.transfermail.mailbagnotavailableatairport"
							.equalsIgnoreCase(err.getErrorCode())) {

						errorVO = new ErrorVO("mailtracking.defaults.transfermail.mailbagnotavailableatairport");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					} else {
						errorVO = err;
					}

					actionContext.addError(errorVO);
				}

				return;
			} else {
				log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->", transferManifestVO);
				// Printing manifest to be handled
				// if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){
				// printNeeded=true;
				// ReportSpec reportSpec = getReportSpec();
				// reportSpec.setReportId(TRFMFT_REPORT_ID);
				// reportSpec.setProductCode(PRODUCTCODE);
				// reportSpec.setSubProductCode(SUBPRODUCTCODE);
				// reportSpec.setPreview(true);
				// reportSpec.addFilterValue(transferManifestVO);
				// reportSpec.setResourceBundle(BUNDLE);
				// reportSpec.setAction(ACTION);
				//
				// generateReport();
				//
				//
				//
				// }

			}

		}

		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}

		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
	}

	private double getScaledVolume(double value, CommodityValidationVO commodityValidationVO) {
		double volume = 0.0;

		if (commodityValidationVO != null && commodityValidationVO.getDensityFactor() > 0) {
			volume = value / commodityValidationVO.getDensityFactor();

			if (volume < 0.01) {
				volume = 0.01;
			}

		}
		return volume;
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
	private boolean doSecurityScreeningValidations(MailbagVO mailbagVO, FlightValidationVO flightValidationVO, String assignTo,
			LogonAttributes logonAttributes, ActionContext actionContext, Map<String, String> existigWarningMap, ContainerVO containerVO) throws BusinessDelegateException {
		String securityWarningStatus = "N";
		if(existigWarningMap != null && existigWarningMap.size()>0 && existigWarningMap.containsKey(SECURITY_SCREENING_WARNING)) {
			securityWarningStatus=existigWarningMap.get(SECURITY_SCREENING_WARNING);
		}
		if("N".equals(securityWarningStatus)) {
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
			securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			if(FLIGHT.equals(assignTo)){
				securityScreeningValidationFilterVO.setApplicableTransaction("ASG");
				securityScreeningValidationFilterVO.setTransistAirport(containerVO.getPou());
				if(flightValidationVO!=null){
				securityScreeningValidationFilterVO.setFlightType(flightValidationVO.getFlightType());
				}
			}
			else{
				securityScreeningValidationFilterVO.setApplicableTransaction("ACP");
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
