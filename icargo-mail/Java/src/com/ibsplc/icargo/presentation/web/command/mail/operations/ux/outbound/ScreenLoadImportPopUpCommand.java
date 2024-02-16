package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound.ScreenLoadImportPopUpCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6245	:	15-Jun-2021	:	Draft
 */
public class ScreenLoadImportPopUpCommand extends AbstractCommand {

	private static final String CLASS_NAME = "ScreenLoadImportPopUpCommand";
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";
	private static final String BLANK = "";
	private static final String FLIGHT_DATE = "flightdate";
	private static final String CARRIER_IDENTIFIER = "carrierid";
	private static final String FLIGHT_SEQUENCE_NUMBER = "flightsequencenumber";
	private static final String FLIGHT_NUMBER = "flightnumber";
	private static final String SYSTEM_PARAMETER_NO_OF_DAYS_FOR_PA_BUILT_MAILBAGS = "mail.operations.noofdaysforpabuiltmailbags";
	private static final Log log = LogFactory.getLogger(MAIL_OPERATIONS);
	private static final String SUCCESS = "success";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		  ResponseVO responseVO = new ResponseVO();
		  List<OutboundModel> results = new ArrayList<>();
		if (Objects.nonNull(outboundModel.getSelectedContainer())) {
			Collection<ContainerDetailsVO> containerJourneyIdDetails = null;
			List<String> containerJourneyIds = null;
			ContainerDetails selectedContainer = outboundModel.getSelectedContainer();

			ConsignmentFilterVO consignmentFilterVO = populateConsignmentFilterVO(selectedContainer);
			try {
				containerJourneyIdDetails = new MailTrackingDefaultsDelegate()
						.findContainerJourneyID(consignmentFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				actionContext.addAllError(handleDelegateException(businessDelegateException));
			}

			if (Objects.nonNull(containerJourneyIdDetails) && !containerJourneyIdDetails.isEmpty()) {
				if (Objects.nonNull(selectedContainer.getFlightDate()) && !selectedContainer.getFlightDate().isEmpty()) {
					Predicate<ContainerDetailsVO> flightNumberPredicate = getPredicate(selectedContainer.getFlightNumber(),FLIGHT_NUMBER);
					Predicate<ContainerDetailsVO> carrierIdPredicate = getPredicate(String.valueOf(selectedContainer.getCarrierId()), CARRIER_IDENTIFIER);
					Predicate<ContainerDetailsVO> flightDatePredicate = getPredicate(selectedContainer.getFlightDate(),FLIGHT_DATE);
					containerJourneyIds = containerJourneyIdDetails.stream()
							.filter(flightNumberPredicate.and(carrierIdPredicate).and(flightDatePredicate))
							.map(ContainerDetailsVO::getContainerJnyId).collect(Collectors.toList());
				}else{
					containerJourneyIds = containerJourneyIdDetails.stream()
							.map(ContainerDetailsVO::getContainerJnyId).collect(Collectors.toList());				
				}
				if (Objects.nonNull(containerJourneyIds) && !containerJourneyIds.isEmpty()) {
					outboundModel.setContainerJnyID(containerJourneyIds.get(0));
					results.add(outboundModel);
					responseVO.setResults(results);
					responseVO.setStatus(SUCCESS);
					actionContext.setResponseVO(responseVO);
				}				
			}
		}
		log.exiting(CLASS_NAME, "execute");
	}

	private ConsignmentFilterVO populateConsignmentFilterVO(ContainerDetails selectedContainer) {
		LogonAttributes logonAttributes = getLogonAttribute();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setContainerNumber(selectedContainer.getContainerNumber());
		String paBuiltDaysOffset = "0";
		Map<String, String> systemParameterMap = findSystemParameters();

		if (Objects.nonNull(systemParameterMap) && !systemParameterMap.isEmpty()) {
			paBuiltDaysOffset = systemParameterMap.get(SYSTEM_PARAMETER_NO_OF_DAYS_FOR_PA_BUILT_MAILBAGS);
		}
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		if(Objects.nonNull(selectedContainer.getFlightDate())){
			fromDate.setDate(selectedContainer.getFlightDate());
			toDate.setDate(selectedContainer.getFlightDate());	
		}else if(Objects.nonNull(selectedContainer.getScannedDate())){
			fromDate.setDate(selectedContainer.getScannedDate());
			toDate.setDate(selectedContainer.getScannedDate());	
		}
		fromDate.addDays(-Integer.parseInt(paBuiltDaysOffset));
		consignmentFilterVO.setConsignmentFromDate(fromDate);
		consignmentFilterVO.setConsignmentToDate(toDate);
		return consignmentFilterVO;
	}

	private static Predicate<ContainerDetailsVO> getPredicate(String value, String function) {
		Predicate<ContainerDetailsVO> predicate = null;
		if (FLIGHT_NUMBER.equals(function)) {
			predicate = detail -> detail.getFlightNumber().equals(value);
		} else if (FLIGHT_SEQUENCE_NUMBER.equals(function)) {
			predicate = detail -> detail.getFlightSequenceNumber() == (Long.parseLong(value));
		} else if (CARRIER_IDENTIFIER.equals(function)) {
			predicate = detail -> detail.getCarrierId() == (Integer.parseInt(value));
		} else if (FLIGHT_DATE.equals(function)) {
			predicate = detail -> (Objects.nonNull(detail.getFlightDate())?detail.getFlightDate().toDisplayDateOnlyFormat():BLANK).equals(value);
		} else {
			predicate = detail -> detail.getFlightNumber().equals(value);
		}
		return predicate;
	}

	private Map<String, String> findSystemParameters() {
		log.entering(CLASS_NAME, "findSystemParameters");
		Map<String, String> systemParameterMap = null;
		Collection<String> systemParameters = new ArrayList<>();
		systemParameters.add(SYSTEM_PARAMETER_NO_OF_DAYS_FOR_PA_BUILT_MAILBAGS);
		try {
			systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
		} catch (BusinessDelegateException e) {
			log.log(Log.SEVERE, "System parameters not found");
		}
		return systemParameterMap;
	}

}
