/*
 * RecoDefaultsServiceBaseImpl.java Created on Feb 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.services.reco.defaults.webservices.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.ibsplc.icargo.business.reco.defaults.types.standard.ApplicableTransactionsType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoDetailType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoDetailsType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryFilterType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryRequestType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryResponseData;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryResponseType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoParameterType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.ErrorDetailType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.GeographicalLevelDetailsType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.GeographicalLevelValuesType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.ParameterValuesType;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.shared.defaults.types.standard.MessageHeaderType;

import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.services.reco.defaults.webservices.standard.RecoDefaultsServiceBaseImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5153	:	Feb 20, 2018	:	Draft
 */
public class RecoDefaultsServiceBaseImpl extends WebServiceEndPoint{

	private static final Logger LOG = Logger.getLogger(RecoDefaultsServiceBaseImpl.class.getName());
	private static final String RESOURCEBUNDLE = "resources.reco.defaults.Reco_en_US";
	private static final Locale LOCALE = Locale.US;
	private static final String ALL = "ALL";
	private static final String ACTIVE = "A";
	private static final String SUSPENDED = "S";
	private static final String ALL_DAYS_OPERATION = "All";
	private static final String ALL_DAYSOFOPERATION = "1234567";
	private static final String MON="Mon";
	private static final String TUE="Tue";
	private static final String WED="Wed";
	private static final String THU="Thu";
	private static final String FRI="Fri";
	private static final String SAT="Sat";
	private static final String SUN="Sun";
	private static final String INCLUDE = "IN";
	private static final String EXCLUDE = "EX";
	private static final String COMMA = ",";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String BLANK = "";
	private static final String ERROR = "Error";
	private static final String NO_RESULT_FOUND ="No Result Found";
    
    /**
     * 
     * 	Method		:	RecoDefaultsServiceBaseImpl.getEmbargoEnquiryDetails
     *	Added by 	:	A-5153 on Feb 22, 2018
     * 	Used for 	:
     *	Parameters	:	@param requestType
     *	Parameters	:	@return
     *	Parameters	:	@throws ServiceFault
     *	Parameters	:	@throws InvalidRequestFault 
     *	Return type	: 	EmbargoEnquiryResponseType
     */
	public EmbargoEnquiryResponseType getEmbargoEnquiryDetails(
			EmbargoEnquiryRequestType requestType) throws ServiceFault,
			InvalidRequestFault {
		LOG.info("Executing operation getEmbargoEnquiryDetails");
		EmbargoEnquiryResponseType responseType = new EmbargoEnquiryResponseType();
		EmbargoEnquiryResponseData responseData = new EmbargoEnquiryResponseData();
		Collection<ErrorDetailType> errorDetailTypes = new ArrayList<ErrorDetailType>();
		List<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
		try {

			MessageHeaderType messageHeaderType = new MessageHeaderType();
			if (requestType.getMessageHeader() != null) {
				messageHeaderType.setMessageType(requestType.getMessageHeader()
						.getMessageType());
				messageHeaderType.setSourceSystem(requestType
						.getMessageHeader().getSourceSystem());
				messageHeaderType.setUserId(requestType.getMessageHeader()
						.getUserId());
				messageHeaderType.setMessageID(requestType.getMessageHeader()
						.getMessageID());
				messageHeaderType.setCorrelationID(requestType
						.getMessageHeader().getCorrelationID());
			}
			responseType.setMessageHeader(messageHeaderType);
			responseType.setResponseDetails(responseData);
			responseData.setRequestID(requestType.getRequestData()
					.getRequestID());

			EmbargoFilterVO embargoFilterVO = populateEmbargoFilterVO(requestType);
			embargoDetailsVOs = despatchRequest("getEmbargoEnquiryDetails", embargoFilterVO);

		} catch (WSBusinessException wsBusinessException) {
			if (wsBusinessException.getErrors() != null
					&& wsBusinessException.getErrors().size() > 0) {
				for (ErrorVO error : wsBusinessException.getErrors()) {
					handleErrors(error, errorDetailTypes);
				}
			}

		} catch (SystemException e) {
			if (e.getErrors() != null && e.getErrors().size() > 0) {
				for (ErrorVO error : e.getErrors()) {
					handleErrors(error, errorDetailTypes);
				}
			}
		}

		if (embargoDetailsVOs != null && embargoDetailsVOs.size() > 0) {
			doPopulateEmbargoDetailsResponse(responseData, embargoDetailsVOs);
		} else {
			if (errorDetailTypes.size() > 0) {
				responseData.getErrorDetails().addAll(errorDetailTypes);
			} else {
				ErrorDetailType errorDetailType = new ErrorDetailType();
				errorDetailType.setErrorCode(ERROR);
				errorDetailType.setErrorDescription(NO_RESULT_FOUND);
				responseData.getErrorDetails().add(errorDetailType);
			}
		}
		return responseType;
	}
    
    /**
     * 
     * 	Method		:	RecoDefaultsServiceBaseImpl.doPopulateEmbargoDetailsResponse
     *	Added by 	:	A-5153 on Feb 20, 2018
     * 	Used for 	:
     *	Parameters	:	@param responseData
     *	Parameters	:	@param embargoDetailsVOs 
     *	Return type	: 	void
     */
	public void doPopulateEmbargoDetailsResponse(
			EmbargoEnquiryResponseData responseData,
			List<EmbargoDetailsVO> embargoDetailsVOs) {
		LOG.info("Populating Embargo Details Response");
		List<EmbargoDetailType> detailsTypes = new ArrayList<EmbargoDetailType>();
		EmbargoDetailType detailType = null;
		EmbargoDetailsType detailsType = null;
		List<GeographicalLevelDetailsType> levelDetailsTypes = null;
		GeographicalLevelValuesType geographicalLevelValuesType = null;
		GeographicalLevelDetailsType geographicalLevelDetailsType = null;
		List<EmbargoParameterType> parameterTypes = null;
		EmbargoParameterType parameterType = null;
		ParameterValuesType parameterValuesType = null;
		ApplicableTransactionsType transactionsType = null;
		for (EmbargoDetailsVO detailsVO : embargoDetailsVOs) {
			detailType = new EmbargoDetailType();
			detailsType = new EmbargoDetailsType();
			detailType.setRuleType(detailsVO.getRuleType());
			detailsType.setEmbargoRefNum(detailsVO.getEmbargoReferenceNumber());
			detailsType.setStartDate(detailsVO.getStartDate()
					.toDisplayDateOnlyFormat());
			detailsType.setEndDate(detailsVO.getEndDate()
					.toDisplayDateOnlyFormat());
			if (detailsVO.getIsSuspended()
					&& ACTIVE.equals(detailsVO.getStatus())) {
				detailsType.setStatus(SUSPENDED);
			} else {
				detailsType.setStatus(detailsVO.getStatus());
			}
			detailsType.setEmbargoLevel(detailsVO.getEmbargoLevel());
			if (detailsVO.getEmbargoDescription() != null
					&& detailsVO.getEmbargoDescription().length() > 0) {
				detailsType.setEmbargoDescription(detailsVO
						.getEmbargoDescription());
			}
			if (detailsVO.getRemarks() != null
					&& detailsVO.getRemarks().length() > 0) {
				detailsType.setRemarks(detailsVO.getRemarks());
			}
			if (detailsVO.getDaysOfOperation() != null
					&& detailsVO.getDaysOfOperation().length() > 0) {
				String daysOfOperation = convertFormat(detailsVO
						.getDaysOfOperation());
				detailsType.setDayOfWeek(new BigInteger(daysOfOperation));
			}

			if (detailsVO.getGeographicLevels() != null) {
				levelDetailsTypes = new ArrayList<GeographicalLevelDetailsType>();
				for (EmbargoGeographicLevelVO geographicLevelVO : detailsVO
						.getGeographicLevels()) {
					geographicalLevelValuesType = new GeographicalLevelValuesType();
					geographicalLevelDetailsType = new GeographicalLevelDetailsType();
					String[] levelValues = geographicLevelVO
							.getGeographicLevelValues().split(COMMA);
					for (String value : levelValues) {
						geographicalLevelValuesType.getGeographicalLevelValue()
								.add(value);
					}
					geographicalLevelDetailsType
							.setGeographicalLevel(geographicLevelVO
									.getGeographicLevel());
					geographicalLevelDetailsType
							.setGeographicalLevelType(geographicLevelVO
									.getGeographicLevelType());
					geographicalLevelDetailsType
							.setApplicableCondition(geographicLevelVO
									.getGeographicLevelApplicableOn());
					geographicalLevelDetailsType
							.setGeographicalLevelValues(geographicalLevelValuesType);
					levelDetailsTypes.add(geographicalLevelDetailsType);
				}

				detailsType.getGeographicalLevelDetails().addAll(
						levelDetailsTypes);
			}
			
			if (detailsVO.getParams() != null) {
				parameterTypes = new ArrayList<EmbargoParameterType>();
				for (EmbargoParameterVO parameterVO : detailsVO.getParams()) {
					parameterType = new EmbargoParameterType();
					parameterValuesType = new ParameterValuesType();
					parameterValuesType.setParameterValue(parameterVO
							.getParameterValues());
					parameterType.setParameterType(parameterVO
							.getParameterCode());
					parameterType.setParameterValues(parameterValuesType);
					parameterType
							.setParameterCondition(parameterVO.getApplicable()
									.equalsIgnoreCase(INCLUDE) ? YES
									: parameterVO.getApplicable()
											.equalsIgnoreCase(EXCLUDE) ? NO
											: parameterVO.getApplicable());
					parameterTypes.add(parameterType);
				}
				detailsType.getEmbargoParameter().addAll(parameterTypes);
			}

			if (detailsVO.getApplicableTransactions() != null) {
				transactionsType = new ApplicableTransactionsType();
				String[] appTxn = detailsVO.getApplicableTransactions().split(COMMA);
				for (String txnCode : appTxn) {
					transactionsType.getTransactionCode().add(txnCode);
				}
				detailsType.setApplicableTransactions(transactionsType);
			}

			detailType.setEmbargoDetails(detailsType);
			detailsTypes.add(detailType);
		}

		responseData.getEmbargoResponseDetails().addAll(detailsTypes);
	}
    
	/**
	 * 
	 * 	Method		:	RecoDefaultsServiceBaseImpl.populateEmbargoFilterVO
	 *	Added by 	:	A-5153 on Feb 20, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param embargoEnquiryRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	EmbargoFilterVO
	 */
	private EmbargoFilterVO populateEmbargoFilterVO(
			EmbargoEnquiryRequestType embargoEnquiryRequest)
			throws SystemException {
		LOG.info("Populating Embargo Filter VO");
		EmbargoEnquiryFilterType filterType = embargoEnquiryRequest
				.getRequestData().getEmbargoEnquiryFilterDetails();
		EmbargoFilterVO filterVO = new EmbargoFilterVO();
		/*LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.FINE, "Exception caught in populateEmbargoFilterVO of "
					+ "RecoDefaultsServiceBaseImpl");
		}*/
		filterVO.setCompanyCode(getCompanyCode());

		if (filterType.getEmbargoRefNum() != null
				&& filterType.getEmbargoRefNum().trim().length() > 0) {
			String refnum = filterType.getEmbargoRefNum().toUpperCase();
			int zeroAppendCount = 5 - refnum.length();
			while (zeroAppendCount-- > 0) {
				refnum = "0" + refnum;
			}
			filterVO.setEmbargoRefNumber(refnum);
		} else {
			filterVO.setEmbargoRefNumber(null);
		}

		if (filterType.getRuleType() != null
				&& filterType.getRuleType().trim().length() > 0) {
			filterVO.setRuleType(filterType.getRuleType());
		}

		if (filterType.getEmbargoLevel() != null
				&& filterType.getEmbargoLevel().trim().length() > 0) {
			if (!filterType.getEmbargoLevel().equals(ALL)) {
				filterVO.setEmbargoLevel(filterType.getEmbargoLevel());
			} else {
				filterVO.setEmbargoLevel("");
			}
		}
		if (filterType.getDayOfWeek() != null) {
			filterVO.setDaysOfOperation(filterType.getDayOfWeek().toString());
		}

		if (filterType.getStatus() != null
				&& filterType.getStatus().trim().length() > 0) {
			if (filterType.getStatus().equals(SUSPENDED)) {
				filterVO.setSuspendFlag(YES);
			} else if (!filterType.getStatus().equals(ALL)) {
				filterVO.setStatus(filterType.getStatus());
			} else {
				filterVO.setStatus(BLANK);
			}
		}

		if (filterType.getStartDate() != null
				&& filterType.getStartDate().trim().length() > 0) {
			LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			filterVO.setStartDate(localStartDate.setDate(filterType
					.getStartDate()));
		} else {
			filterVO.setStartDate(null);
		}
		if (filterType.getEndDate() != null
				&& filterType.getEndDate().trim().length() > 0) {
			LocalDate localEndDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			filterVO.setEndDate(localEndDate.setDate(filterType.getEndDate()));
		} else {
			filterVO.setEndDate(null);
		}

		if (filterType.getTransactionCode() != null
				&& filterType.getTransactionCode().trim().length() > 0) {
			filterVO.setApplicableTransactions(filterType.getTransactionCode());
		}

		if (filterType.getOriginType() != null
				&& filterType.getOriginType().trim().length() > 0) {
			String origin = filterType.getOrigin().toUpperCase();
			filterVO.setOrigin(origin);
			filterVO.setOriginType(filterType.getOriginType());
		} else {
			filterVO.setOrigin(null);
			filterVO.setOriginType(null);
		}
		if (filterType.getDestinationType() != null
				&& filterType.getDestinationType().trim().length() > 0) {
			String destination = filterType.getDestination().toUpperCase();
			filterVO.setDestination(destination);
			filterVO.setDestinationType(filterType.getDestinationType());
		} else {
			filterVO.setDestination(null);
			filterVO.setDestinationType(null);
		}
		if (filterType.getViaPointType() != null
				&& filterType.getViaPointType().trim().length() > 0) {
			filterVO.setViaPoint(filterType.getViaPoint().toUpperCase());
			filterVO.setViaPointType(filterType.getViaPointType());
		}

		if (filterType.getEmbargoParameterCode() != null
				&& filterType.getEmbargoParameterCode().trim().length() > 0) {
			if (!filterType.getEmbargoParameterCode().equals(ALL)) {
				filterVO.setParameterCode(filterType.getEmbargoParameterCode());
			} else {
				filterVO.setParameterCode(BLANK);
			}
		}
		if (filterType.getEmbargoParameterValue() != null
				&& filterType.getEmbargoParameterValue().trim().length() > 0) {
			String value = filterType.getEmbargoParameterValue().trim()
					.toUpperCase();
			filterVO.setParameterValues(value);
		}

		log.log(Log.FINE, "populateEmbargoFilterVO", filterVO);

		return filterVO;
	}
    
	/**
	 * 
	 * 	Method		:	RecoDefaultsServiceBaseImpl.handleErrors
	 *	Added by 	:	A-5153 on Feb 20, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param error
	 *	Parameters	:	@param errorDetailTypes 
	 *	Return type	: 	void
	 */
	private void handleErrors(ErrorVO error,
			Collection<ErrorDetailType> errorDetailTypes) {
		log.entering("RecoDefaultsServiceBaseImpl", "handleErrors");
		String errorDescription = null;
		ErrorDetailType errorType = new ErrorDetailType();
		errorType.setErrorCode(error.getErrorCode());
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
					RESOURCEBUNDLE, LOCALE);
			errorDescription = resourceBundle.getString(error.getErrorCode());
		} catch (Exception exception) {
			log.log(Log.FINE, "Exception caught in handleErrors of "
					+ "RecoDefaultsServiceBaseImpl");
		}
		if ((errorDescription == null)
				|| (errorDescription.trim().length() == 0)) {
			errorDescription = error.getErrorCode();
		}
		errorType.setErrorDescription(errorDescription);
		errorDetailTypes.add(errorType);
	}
	
	/**
	 * 
	 * 	Method		:	RecoDefaultsServiceBaseImpl.convertFormat
	 *	Added by 	:	A-5153 on Feb 21, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param daysOfOperation
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String convertFormat(String daysOfOperation) {
		StringBuffer convertDays = new StringBuffer();
		String[] days = daysOfOperation.split(COMMA);
		String str = null;
		for (String day : days) {
			switch (day) {
			case MON: {
				str = "1";
				break;
			}
			case TUE: {
				str = "2";
				break;
			}
			case WED: {
				str = "3";
				break;
			}
			case THU: {
				str = "4";
				break;
			}
			case FRI: {
				str = "5";
				break;
			}
			case SAT: {
				str = "6";
				break;
			}
			case SUN: {
				str = "7";
				break;
			}
			case ALL_DAYS_OPERATION: {
				str = ALL_DAYSOFOPERATION;
				break;
			}
			default: {
				str = BLANK;
			}
			}
			convertDays.append(str);
		}
		return convertDays.toString();
	}
    

}
