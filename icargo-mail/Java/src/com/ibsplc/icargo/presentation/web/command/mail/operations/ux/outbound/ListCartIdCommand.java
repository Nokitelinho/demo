package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCartIdCommand extends AbstractCommand {

	private static final String CLASS_NAME = "ListCartIdCommand";
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";	
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	private static final String SYSTEM_PARAMETER_NO_OF_DAYS_FOR_PA_BUILT_MAILBAGS = "mail.operations.noofdaysforpabuiltmailbags";
	private static final int WEIGHT_DIVISION_FACTOR = 10;
	private static final Log log = LogFactory.getLogger(MAIL_OPERATIONS);
	private static final String MAIL_OPERATIONS_UX_OUTBOUND_CONTAINER_JNY_ID_OR_BELLY_CART_ID_MANDATORY = "mail.operations.ux.outbound.containerjnyidorbellycartidmandatory";
	private static final String MAIL_OPERATIONS_UX_OUTBOUND_CONTAINER_JNY_ID_MANDATORY_WITH_BELLY_CART_ID = "mail.operations.ux.outbound.containerjnyidmandatorywithbellycartid";
	private static final String MAIL_OPERATIONS_UX_OUTBOUND_MAIL_COMMODITY_DENSITY_FACTOR_MISSING = "mail.operations.ux.outbound.mailcommoditydensityfactormissing";
	private static final String MAIL_OPERATIONS_UX_OUTBOUND_MISMATCH_CONTAINER_NUMBER_JNY_ID = "mail.operations.ux.outbound.mismatchcontainernumberjnyid";
	private static final String MAIL_OPERATIONS_UX_OUTBOUND_INVALID_PAIR_BELLY_CART_ID_JNY_ID = "mail.operations.ux.outbound.invalidpairbellycartidjnyid";
	private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		ResponseVO responseVO = new ResponseVO();
		List<OutboundModel> results = new ArrayList<>();
		ContainerDetails importedContainer = outboundModel.getImportFromContainer();
		ContainerDetails selectedContainer = outboundModel.getSelectedContainer();
		List<ErrorVO> errors;

		errors = validateData(importedContainer);
		if (!errors.isEmpty()) {
			responseVO.setResults(results);
			responseVO.setStatus(FAILURE);
			actionContext.setResponseVO(responseVO);
			actionContext.addAllError(errors);
			return;
		}
		Map<String, String> systemParameterMap = findSystemParameters();
		if (Objects.isNull(systemParameterMap)) {
			responseVO.setResults(results);
			responseVO.setStatus(FAILURE);
			actionContext.setResponseVO(responseVO);
			actionContext.addError(new ErrorVO(MAIL_OPERATIONS_UX_OUTBOUND_MAIL_COMMODITY_DENSITY_FACTOR_MISSING));
			return;
		}
		String density = getDensity(logonAttributes.getCompanyCode(), systemParameterMap.get(MAIL_COMMODITY_SYS));
		if (isNullOrEmpty(density)) {
			responseVO.setResults(results);
			responseVO.setStatus(FAILURE);
			actionContext.setResponseVO(responseVO);
			actionContext.addError(new ErrorVO(MAIL_OPERATIONS_UX_OUTBOUND_MAIL_COMMODITY_DENSITY_FACTOR_MISSING));
			return;
		}
		boolean isMultiple = false;
		if (isNotNullAndEmpty(importedContainer.getContainerJnyID()) &&
				isNotNullAndEmpty(importedContainer.getBellyCarditId())) {
			isMultiple = true;
			selectedContainer.setBellyCarditId(importedContainer.getBellyCarditId());
		}
		selectedContainer.setContainerJnyId(importedContainer.getContainerJnyID());
		selectedContainer.setContainerJnyID(importedContainer.getContainerJnyID());
		importedContainer.setContainerNumber(selectedContainer.getContainerNumber());
		
		Collection<MailbagVO> newMailbagVOs = null;
		ConsignmentFilterVO consignmentFilterVO =
				populateConsignmentFilterVO(importedContainer, systemParameterMap.get(SYSTEM_PARAMETER_NO_OF_DAYS_FOR_PA_BUILT_MAILBAGS));
		try {
			newMailbagVOs = new MailTrackingDefaultsDelegate().findCartIds(consignmentFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if ((Objects.nonNull(errors) && !errors.isEmpty()) || Objects.isNull(newMailbagVOs) || newMailbagVOs.isEmpty()) {
			if (isMultiple) {
				actionContext.addError(new ErrorVO(MAIL_OPERATIONS_UX_OUTBOUND_INVALID_PAIR_BELLY_CART_ID_JNY_ID));
			} else {
				actionContext.addError(new ErrorVO(MAIL_OPERATIONS_UX_OUTBOUND_MISMATCH_CONTAINER_NUMBER_JNY_ID));
			}
			responseVO.setResults(results);
			responseVO.setStatus(FAILURE);
			actionContext.setResponseVO(responseVO);
			return;
		}
		else{	
			doApplicableRegulationFlagValidationForPABuidContainer(newMailbagVOs,selectedContainer,actionContext,responseVO);
			if(FAILURE.equals(responseVO.getStatus())){
			  return;
			}
		}
		List<Mailbag> mailbags = outboundModel.getMailbags();
		ContainerDetailsVO containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(selectedContainer, logonAttributes);
		Collection<MailbagVO> mailbagsInSession = MailOperationsModelConverter.constructMailbagVOs(mailbags, logonAttributes);
		if (!mailbagsInSession.isEmpty()) {
			newMailbagVOs = removeExistingMailbags(mailbagsInSession, newMailbagVOs);
		}
		setMailBagVODetails(newMailbagVOs, containerDetailsVO, logonAttributes, density);
		Collection<Mailbag> importedMailbags = MailOperationsModelConverter.constructMailbags(newMailbagVOs);	
		outboundModel.setImportedmailbags(importedMailbags);
		results.add(outboundModel);
		responseVO.setResults(results);
		responseVO.setStatus(SUCCESS);
		actionContext.setResponseVO(responseVO);
		log.exiting(CLASS_NAME, "execute");

	}

	private Collection<MailbagVO> removeExistingMailbags(Collection<MailbagVO> mailbagsInSession,
			Collection<MailbagVO> newMailbagVOs) {
		return newMailbagVOs.stream()
				.filter(currentMail -> mailbagsInSession.stream()
						.noneMatch(sessionMail -> sessionMail.getMailbagId().equals(currentMail.getMailbagId())))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private String getDensity(String companyCode, String commodityCode) {
		String density = null;
		if (commodityCode != null && commodityCode.trim().length() > 0) {
			Collection<String> commodities = new ArrayList<>();
			commodities.add(commodityCode);
			Map<String, CommodityValidationVO> densityMap = null;
			CommodityDelegate commodityDelegate = new CommodityDelegate();
			try {
				densityMap = commodityDelegate.validateCommodityCodes(companyCode, commodities);
			} catch (BusinessDelegateException e) {
				log.log(Log.FINE, e.getMessage());
			}
			if (densityMap != null && densityMap.size() > 0) {
				CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
				log.log(Log.FINE, "DENSITY====>:", commodityValidationVO.getDensityFactor());
				density = (String.valueOf(commodityValidationVO.getDensityFactor()));
			}
		}
		return density;
	}

	private void setMailBagVODetails(Collection<MailbagVO> mailbagVOs, ContainerDetailsVO containerDetailsVO,
									 LogonAttributes logonAttributes, String density) {
		if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				double vol ;
				mailbagVO.setBellyCartId(containerDetailsVO.getBellyCartId());
				mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
				mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				mailbagVO.setDamageFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
				mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
				mailbagVO.setContainerType(containerDetailsVO.getContainerType());
				mailbagVO.setPou(containerDetailsVO.getPou());
				mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
				mailbagVO.setOperationFlag(MailbagVO.OPERATION_FLAG_INSERT);
				mailbagVO.setDisplayLabel(MailbagVO.FLAG_YES);
				mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
				if (Objects.nonNull(mailbagVO.getStrWeight())) {
					vol = mailbagVO.getStrWeight().getRoundedSystemValue()
							/ (WEIGHT_DIVISION_FACTOR * Double.parseDouble(density));
					mailbagVO.setVolume(new Measure(UnitConstants.VOLUME, vol));
					mailbagVO.setVol(vol);
					mailbagVO.setDisplayUnit(mailbagVO.getStrWeight().getDisplayUnit());
				}
				mailbagVO.setImportMailbag(true);
			}
		}

	}

	private static boolean isNotNullAndEmpty(String s) {
		return Objects.nonNull(s) && !s.trim().isEmpty();
	}

	private static boolean isNullOrEmpty(String s) {
		return Objects.isNull(s) || s.trim().isEmpty();
	}

	private List<ErrorVO> validateData(ContainerDetails selectedContainer) {
		List<ErrorVO> errorVOs = new ArrayList<>();
		if (isNullOrEmpty(selectedContainer.getContainerJnyID()) && isNullOrEmpty(selectedContainer.getBellyCarditId())) {
			ErrorVO errorVO = new ErrorVO(MAIL_OPERATIONS_UX_OUTBOUND_CONTAINER_JNY_ID_OR_BELLY_CART_ID_MANDATORY);
			errorVOs.add(errorVO);
		}
		if (isNullOrEmpty(selectedContainer.getContainerJnyID()) && isNotNullAndEmpty(selectedContainer.getBellyCarditId())) {
			ErrorVO errorVO = new ErrorVO(MAIL_OPERATIONS_UX_OUTBOUND_CONTAINER_JNY_ID_MANDATORY_WITH_BELLY_CART_ID);
			errorVOs.add(errorVO);
		}
		return errorVOs;
	}

	private ConsignmentFilterVO populateConsignmentFilterVO(ContainerDetails importedContainer, String paBuiltDaysOffset) {
		LogonAttributes logonAttributes = getLogonAttribute();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setContainerJourneyId(importedContainer.getContainerJnyID());
		consignmentFilterVO.setBellyCartId(importedContainer.getBellyCarditId());
		if(isNullOrEmpty(importedContainer.getBellyCarditId())){
			consignmentFilterVO.setContainerNumber(importedContainer.getContainerNumber());
		}
		if (isNullOrEmpty(paBuiltDaysOffset)) {
			paBuiltDaysOffset = "0";
		}
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		if (Objects.nonNull(importedContainer.getFlightDate())) {
			fromDate.setDate(importedContainer.getFlightDate());
			toDate.setDate(importedContainer.getFlightDate());
		} else if (Objects.nonNull(importedContainer.getScannedDate())) {
			fromDate.setDate(importedContainer.getScannedDate());
			toDate.setDate(importedContainer.getScannedDate());
		}
		fromDate.addDays(-Integer.parseInt(paBuiltDaysOffset));
		consignmentFilterVO.setConsignmentFromDate(fromDate);
		consignmentFilterVO.setConsignmentToDate(toDate);
		return consignmentFilterVO;
	}

	private Map<String, String> findSystemParameters() {
		log.entering(CLASS_NAME, "findSystemParameters");
		Map<String, String> systemParameterMap = null;
		Collection<String> systemParameters = new ArrayList<>();
		systemParameters.add(SYSTEM_PARAMETER_NO_OF_DAYS_FOR_PA_BUILT_MAILBAGS);
		systemParameters.add(MAIL_COMMODITY_SYS);
		try {
			systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
		} catch (BusinessDelegateException e) {
			log.log(Log.SEVERE, "System parameters not found");
		}
		return systemParameterMap;
	}
	private void doApplicableRegulationFlagValidationForPABuidContainer(Collection<MailbagVO> newMailbagVOs,
			ContainerDetails selectedContainer, ActionContext actionContext, ResponseVO responseVO) throws BusinessDelegateException {
		HashMap<String,MailbagVO>mailbagDetailsMap=new HashMap<>();
		 for (MailbagVO mailbagVO:newMailbagVOs){
			 String mailBagKey=mailbagVO.getConsignmentNumber()+mailbagVO.getMailSubclass();
			 if(!mailbagDetailsMap.containsKey(mailBagKey)){
				 SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO=new SecurityScreeningValidationFilterVO();
				 securityScreeningValidationFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
				 securityScreeningValidationFilterVO.setOriginAirport(selectedContainer.getPol());
				 securityScreeningValidationFilterVO.setTransactionAirport(selectedContainer.getPol());
				 securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				 if(selectedContainer.getPou()!=null &&selectedContainer.getPou().trim().length()>0 ){
					 securityScreeningValidationFilterVO.setDestinationAirport(selectedContainer.getPou());
					 securityScreeningValidationFilterVO.setAppRegDestArp(selectedContainer.getPou());
					 securityScreeningValidationFilterVO.setTransistAirport(selectedContainer.getPou());
				 }
				 else{
					 securityScreeningValidationFilterVO.setDestinationAirport(selectedContainer.getDestination());
					 securityScreeningValidationFilterVO.setAppRegDestArp(selectedContainer.getDestination());
				 }
				 securityScreeningValidationFilterVO.setSubClass(mailbagVO.getMailSubclass());
				 securityScreeningValidationFilterVO.setSecurityValNotReq(true);
				 securityScreeningValidationFilterVO.setSecurityValNotRequired(MailConstantsVO.FLAG_YES);
				 securityScreeningValidationFilterVO.setAppRegValReq(true);
				 Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
				 securityScreeningValidationVOs= new MailTrackingDefaultsDelegate().doApplicableRegulationFlagValidationForPABuidContainer(mailbagVO,securityScreeningValidationFilterVO);
				 if (securityScreeningValidationVOs!=null &&!securityScreeningValidationVOs.isEmpty()){
					 for( SecurityScreeningValidationVO securityScreeningValidationVO:securityScreeningValidationVOs){
						 if ("E".equals(securityScreeningValidationVO
									.getErrorType())&&"AR".equals(securityScreeningValidationVO
											.getValidationType())) {
									List<OutboundModel> results = new ArrayList<>();
									responseVO.setResults(results);
									responseVO.setStatus(FAILURE);
									actionContext.setResponseVO(responseVO);
									actionContext.addError(new ErrorVO(APPLICABLE_REGULATION_ERROR));
									return;
							}
					 }
				 }
				 mailbagDetailsMap.put(mailBagKey, mailbagVO);
			 }
		 }
	}
}
