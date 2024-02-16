/**
 *	Java file	: 	com.ibsplc.icargo.services.stockcontrol.defaults.webservices.StockcontrolDefaultsServiceBaseImpl.java
 *
 *	Created by	:	A-4823
 *	Created on	:	13-04-2015
 *
 *  Copyright 2012 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.stockcontrol.defaults.webservices.standard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.shared.defaults.types.standard.ErrorDetailsType;
import com.ibsplc.icargo.business.shared.defaults.types.standard.MessageHeaderType;
import com.ibsplc.icargo.business.shared.defaults.types.standard.PaginationType;
import com.ibsplc.icargo.business.stockcontrol.defaults.types.standard.*;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.*;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.services.stockcontrol.defaults.webservices.standard.InvalidRequestFault;
import com.ibsplc.icargo.services.stockcontrol.defaults.webservices.standard.ServiceFault;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4823
 *
 */
public class StockcontrolDefaultsServiceBaseImpl extends WebServiceEndPoint{
	private static final Log log = LogFactory.getLogger("STOCKCONTROL");
	private static final String STATUS_ACTIVE = "A";
	private static final String STATUS_INACTIVE = "I";
	private static final String MSG_TYPE = "A";
	private static final String MSG_SOURCE = "ICARGO";
	private static final String SUCCESS = "S";
	private static final String FAILURE = "F";
	//private static final String STK_HLD_TYPE_AGENT = "A";
	//private static final String STK_DOC_SUB_TYP_COMAT = "COMAT";
	private static final String CLASS_NAME = "StockcontrolDefaultsServiceBaseImpl";
	private static final String SOURCE_PORTAL = "PORTAL";

	/**
	 * 
	 * @param validateDocumentRequestType
	 * @return
	 * @throws InvalidRequestFault
	 * @throws ServiceFault
	 */
	public ValidateDocumentResponseType validateDocument(ValidateDocumentRequestType validateDocumentRequestType) throws InvalidRequestFault, ServiceFault {
    	log.entering("StockcontrolDefaultsServiceImpl", "validateDocument");
    	ValidateDocumentResponseType validateDocumentResponseType = new ValidateDocumentResponseType();

    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	DocumentFilterVO documentFilterVO = populateDocumentFilterVO(validateDocumentRequestType);
    	DocumentValidationVO  documentValidationVO = null;
    	try {
    		documentValidationVO = despatchRequest("validateDocumentForWS", documentFilterVO);
		} catch (WSBusinessException e) {
			errors = e.getErrors();

		} catch (SystemException ex) {
			if(ex != null && !(ex.getErrors().isEmpty())) {
				for(ErrorVO errorVO : ex.getErrors()) {
					if("shared.airline.invalidairline".equals(errorVO.getErrorCode())){
						errors = ex.getErrors();
					}
					else{
						throw new ServiceFault("validateDocument ,Exception occured in validateDocument"+ errorVO.getErrorCode());}
				}
			}
		}

    	return populateValidateDocumentResponseType(documentValidationVO, errors, validateDocumentRequestType);


    }

	/**
	 * 
	 * @param documentValidationVO
	 * @param errors
	 * @param validateDocumentRequestType
	 * @return
	 */
	private ValidateDocumentResponseType populateValidateDocumentResponseType(
			DocumentValidationVO documentValidationVO,
			Collection<ErrorVO> errors,
			ValidateDocumentRequestType validateDocumentRequestType) {
		ValidateDocumentResponseType documentResponseType = new ValidateDocumentResponseType();
		documentResponseType.setMessageHeader(getMessageHeader());	
		
		documentResponseType.setResponseDetails(populateValidateDocumentResponseDataType(documentValidationVO,validateDocumentRequestType,errors));
	
		return documentResponseType;
	}

	/**
	 *
	 * @param documentValidationVO
	 * @param errors
	 * @param validateDocumentRequestType
	 * @return
	 */
	private ValidateDocumentStockResponseType populateValidateStockDocumentResponseType(
			DocumentValidationVO documentValidationVO,
			Collection<ErrorVO> errors,
			ValidateDocumentStockRequestType validateDocumentRequestType) {
		ValidateDocumentStockResponseType documentResponseType = new ValidateDocumentStockResponseType();
		documentResponseType.setMessageHeader(getMessageHeader());

		documentResponseType.setResponseDetails(populateValidateStockDocumentResponseDataType(documentValidationVO,validateDocumentRequestType,errors));

		return documentResponseType;
	}
	/**
	 * 
	 * @param documentValidationVO
	 * @param validateDocumentRequestType 
	 * @param errors 
	 * @param isValid 
	 * @return
	 */
	private ValidateDocumentResponseData populateValidateDocumentResponseDataType(
			DocumentValidationVO documentValidationVO, ValidateDocumentRequestType validateDocumentRequestType, Collection<ErrorVO> errors) {
		ValidateDocumentResponseData documentResponseData = new ValidateDocumentResponseData();
		documentResponseData.setRequestID(validateDocumentRequestType.getRequestData().getRequestID());
		DocumentValidationType documentValidationType = new DocumentValidationType();
		documentValidationType.setShipmentPrefix(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getShipmentPrefix());
		
		
		documentValidationType.setDocumentNumber(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentNumber());
		documentValidationType.setAgentCode(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getAgentCode());			
		documentValidationType.setDocumentType(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentType());
		if(documentValidationVO!=null){  
		documentValidationType.setDocumentSubType(documentValidationVO.getDocumentSubType());  	  
			documentValidationType.setProductCode(documentValidationVO.getProductStockVOs() != null
					&& documentValidationVO.getProductStockVOs().size() == 1 ? ((ArrayList<ProductStockVO>) documentValidationVO
					.getProductStockVOs()).get(0).getProductCode(): null);
			documentValidationType.setProductName(documentValidationVO.getProductStockVOs() != null
					&& documentValidationVO.getProductStockVOs().size() == 1 ? ((ArrayList<ProductStockVO>) documentValidationVO
					.getProductStockVOs()).get(0).getProductName(): null);       
		}
		documentValidationType.setValidationStatus(STATUS_ACTIVE);
		documentResponseData.setStatus(SUCCESS);
		if(documentValidationVO == null){
			documentValidationType.setValidationStatus(STATUS_INACTIVE);
			documentResponseData.setStatus(FAILURE);
		}
		documentResponseData.setDocumentValidationDetails(documentValidationType);
		if (errors != null && errors.size() > 0) {
			documentValidationType.setValidationStatus(STATUS_INACTIVE);
			documentResponseData.setStatus(FAILURE);
			documentResponseData.getErrorDetails().addAll(populateErrorDetailType(errors));
			}
		return documentResponseData;
	}

	/**
	 *
	 * @param documentValidationVO
	 * @param validateDocumentRequestType
	 * @param errors
	 * @param isValid
	 * @return
	 */
	private ValidateDocumentStockResponseData populateValidateStockDocumentResponseDataType(
			DocumentValidationVO documentValidationVO, ValidateDocumentStockRequestType validateDocumentRequestType, Collection<ErrorVO> errors) {
		ValidateDocumentStockResponseData documentResponseData = new ValidateDocumentStockResponseData();
		documentResponseData.setRequestID(validateDocumentRequestType.getRequestData().getRequestID());
		DocumentStockValidationResponseType documentValidationType = new DocumentStockValidationResponseType();
	     boolean isdocumentValidationTypeAdded = false;
			if(documentValidationVO != null) {
			Collection<AgentDetailVO> agentDetails = ((AWBDocumentValidationVO) documentValidationVO).getAgentDetails();
			if (agentDetails != null && !agentDetails.isEmpty()) {
				for (AgentDetailVO agentDetail : agentDetails) {
					 documentValidationType = new DocumentStockValidationResponseType();
					documentValidationType.setShipmentPrefix(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getShipmentPrefix());

					documentValidationType.setDocumentNumber(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentNumber());
					documentValidationType.setAgentCode(agentDetail.getAgentCode());
					documentValidationType.setDocumentType(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentType());
					if (documentValidationVO != null) {
						documentValidationType.setDocumentSubType(documentValidationVO.getDocumentSubType());
						documentValidationType.setProductCode(documentValidationVO.getProductStockVOs() != null
								&& documentValidationVO.getProductStockVOs().size() == 1 ? ((ArrayList<ProductStockVO>) documentValidationVO
								.getProductStockVOs()).get(0).getProductCode() : null);
						documentValidationType.setProductName(documentValidationVO.getProductStockVOs() != null
								&& documentValidationVO.getProductStockVOs().size() == 1 ? ((ArrayList<ProductStockVO>) documentValidationVO
								.getProductStockVOs()).get(0).getProductName() : null);
					}
					documentValidationType.setValidationStatus(STATUS_ACTIVE);
					documentResponseData.setStatus(SUCCESS);
					documentResponseData.getDocumentValidationDetails().add(documentValidationType);
				}
			}
			else{
				documentValidationType.setShipmentPrefix(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getShipmentPrefix());
				documentValidationType.setDocumentNumber(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentNumber());
				documentValidationType.setDocumentType(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentType());
				documentValidationType.setValidationStatus(STATUS_INACTIVE);
				documentResponseData.setStatus(FAILURE);
				documentResponseData.getDocumentValidationDetails().add(documentValidationType);
				isdocumentValidationTypeAdded = true;
			}
		}
				else {
			documentValidationType.setShipmentPrefix(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getShipmentPrefix());
			documentValidationType.setDocumentNumber(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentNumber());
			documentValidationType.setDocumentType(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentType());
					documentValidationType.setValidationStatus(STATUS_INACTIVE);
					documentResponseData.setStatus(FAILURE);
					documentResponseData.getDocumentValidationDetails().add(documentValidationType);
				isdocumentValidationTypeAdded = true;
				}

				if (errors != null && errors.size() > 0) {
					if(!isdocumentValidationTypeAdded) {
						documentValidationType.setShipmentPrefix(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getShipmentPrefix());
						documentValidationType.setDocumentNumber(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentNumber());
						documentValidationType.setDocumentType(validateDocumentRequestType.getRequestData().getDocumentFilterDetails().getDocumentType());
						documentValidationType.setValidationStatus(STATUS_INACTIVE);
						documentResponseData.setStatus(FAILURE);
						documentResponseData.getDocumentValidationDetails().add(documentValidationType);
					}
					documentResponseData.getErrorDetails().addAll(populateErrorDetailType(errors));

				}





		return documentResponseData;
	}
	/**
	 * 
	 * @param errors
	 * @return
	 */
	private Collection<ErrorDetailsType> populateErrorDetailType(Collection<ErrorVO> errors) {
		Collection<ErrorDetailsType> details = null;
		ErrorDetailsType detail = null;
		for (ErrorVO error : errors) {
			String errorCode = error.getErrorCode();
			detail = new ErrorDetailsType();
			
			if ("shared.airline.invalidairline".equals(errorCode)) {
				detail.setErrorCode("stockcontrol.defaults.invalidprefix");
				detail.setErrorDescription("Document Prefix is invalid.");
			} else if ("stockcontrol.defaults.nostockfoundforstockholder".equals(errorCode)) {
				detail.setErrorCode(errorCode);
				detail.setErrorDescription("No stock found for the agent.");
			} else if ("stockcontrol.defaults.awbnotexistinginanyrangeforstockholder".equals(errorCode) || "stockcontrol.defaults.awbnumbernotfoundinanystock".equals(errorCode)) {
			
				detail.setErrorCode("stockcontrol.defaults.awbnotexistinginanyrangeforstockholder");
				detail.setErrorDescription("Document does not exist in the available stock for agent.");
			}
			else if("stockcontrol.defaults.awbnumberinvalid".equals(errorCode)){
				detail.setErrorCode("stockcontrol.defaults.awbnumberinvalid");
				detail.setErrorDescription("AWB number invalid ");
			}
			else if("stockcontrol.defaults.agentnotfound".equals(errorCode)){
				detail.setErrorCode("stockcontrol.defaults.agentnotfound");
				detail.setErrorDescription("Agent Not Found ");
			}
			else {
				detail.setErrorDescription(errorCode);
				detail.setErrorCode(errorCode);

			}

			if (details == null) {
				details = new ArrayList<ErrorDetailsType>();
			}
			details.add(detail);
		}

	return details;
		
	}

	/**
	 * 
	 * @param validateDocumentRequestType
	 * @return
	 */
	private DocumentFilterVO populateDocumentFilterVO(
			ValidateDocumentRequestType validateDocumentRequestType) {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		if(validateDocumentRequestType.getRequestData() != null && validateDocumentRequestType.getRequestData().getDocumentFilterDetails() != null){
			DocumentValidationType documentValidationType = validateDocumentRequestType.getRequestData().getDocumentFilterDetails();
			documentFilterVO.setShipmentPrefix(documentValidationType.getShipmentPrefix());
			documentFilterVO.setDocumentNumber(documentValidationType.getDocumentNumber());
			documentFilterVO.setStockOwner(documentValidationType.getAgentCode());			
			documentFilterVO.setDocumentType(documentValidationType.getDocumentType());
			documentFilterVO.setDocumentSubType(documentValidationType.getDocumentSubType());
			documentFilterVO.setStatus(documentValidationType.getValidationStatus());
			
		}
	
		return documentFilterVO;
	}

	/**
	 *
	 * @param validateDocumentRequestType
	 * @return
	 */
	private DocumentFilterVO populateStockDocumentFilterVO(
			ValidateDocumentStockRequestType validateDocumentRequestType) {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		if(validateDocumentRequestType.getRequestData() != null && validateDocumentRequestType.getRequestData().getDocumentFilterDetails() != null){
			DocumentStockValidationType documentValidationType = validateDocumentRequestType.getRequestData().getDocumentFilterDetails();
			documentFilterVO.setShipmentPrefix(documentValidationType.getShipmentPrefix());
			documentFilterVO.setDocumentNumber(documentValidationType.getDocumentNumber());
			documentFilterVO.setStockOwner(documentValidationType.getAgentCode());
			documentFilterVO.setDocumentType(documentValidationType.getDocumentType());
			documentFilterVO.setDocumentSubType(documentValidationType.getDocumentSubType());
			documentFilterVO.setStatus(documentValidationType.getValidationStatus());

		}

		return documentFilterVO;
	}

	/**
	 * 
	 * @param messageType
	 * @param messageSource
	 * @return
	 */
	private MessageHeaderType getMessageHeader() {
		MessageHeaderType header = new MessageHeaderType();
		header.setMessageType(MSG_TYPE);
		header.setSourceSystem(MSG_SOURCE);
		return header;
	}
	
	//CRQ_ICRD-240740_SKSingh_14Jun2018
	/**
	 * 
	 * 	Method		:	StockcontrolDefaultsServiceBaseImpl.saveStockRequestDetails
	 *	Added by 	:	A-5258 on Jun 22, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param saveStockRequestType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws InvalidRequestFault
	 *	Parameters	:	@throws ServiceFault 
	 *	Return type	: 	SaveStockResponseType
	 */
	public SaveStockResponseType saveStockRequestDetails(SaveStockRequestType saveStockRequestType) throws InvalidRequestFault, ServiceFault {
		log.entering(CLASS_NAME, "saveStockRequestDetails");
		SaveStockResponseType responseType = new SaveStockResponseType();
		SaveStockResponseData responseData = new SaveStockResponseData();
		responseData.setRequestID(saveStockRequestType.getRequestData().getRequestID());
		responseType.setResponseDetails(responseData);
		responseType.setMessageHeader(saveStockRequestType.getMessageHeader());
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		StockRequestType stockRequestType = saveStockRequestType.getRequestData().getStockRequest();
		StockRequestVO stockRequestVO = new StockRequestVO();
		if(stockRequestType.getAgentCode()!=null && stockRequestType.getAgentCode().trim().length()!=0){ 
			try {
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				StockAgentVO stockAgentVO = getStockAgentMapping(stockRequestType.getAgentCode());
				if(stockAgentVO!=null){
					if(stockRequestType.getStockHolderCode()!=null  
							&& !stockRequestType.getStockHolderCode().equalsIgnoreCase(stockAgentVO.getStockHolderCode())) {
						throw new WSBusinessException("stockcontrol.defaults.invalidstockholdercodeforagent");
					}
					stockRequestVO.setCompanyCode(getCompanyCode());
					stockRequestVO.setStockHolderCode(stockAgentVO.getStockHolderCode());
					stockRequestVO.setDocumentType(stockRequestType.getDocType());
					stockRequestVO.setDocumentSubType(stockRequestType.getSubType());
					LocalDate reqDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					if(stockRequestType.getRequestedDate()!=null){
					stockRequestVO.setRequestDate(reqDate.setDate(stockRequestType.getRequestedDate()));
					}else{
						stockRequestVO.setRequestDate(reqDate);
					}
					stockRequestVO.setRequestedStock(stockRequestType.getRequestedStockQuantity().longValue());
					stockRequestVO.setRemarks(stockRequestType.getRemarks());
					stockRequestVO.setAirlineIdentifier(String.valueOf(logonAttributes.getOwnAirlineIdentifier()));
					stockRequestVO.setStatus(StockRequestVO.STATUS_NEW);
					stockRequestVO.setOperationFlag(StockRequestVO.OPERATION_FLAG_INSERT);
					stockRequestVO.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					stockRequestVO.setLastUpdateUser(logonAttributes.getUserId());
					stockRequestVO.setRequestCreatedBy(stockRequestType.getRequestCreatedBy());
					
				}
				String refNum = despatchRequest("saveStockRequestDetails", stockRequestVO);
				//populating response
				if(refNum!=null){
					stockRequestType.setRequestedRefNumber(refNum);
					responseData.setStockRequest(stockRequestType);
					responseType.setResponseDetails(responseData);
					responseType.getResponseDetails().setStatus(SUCCESS);
				}
			} catch (WSBusinessException e) {
				errorVOs = e.getErrors();
			} catch (SystemException ex) {
				errorVOs = ex.getErrors();
			}
		}else{
			ErrorVO errorVO = new ErrorVO("stockcontrol.defaults.maintainstockagentmapping.error.invalidagent");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorVOs.add(errorVO);
		}
		if (errorVOs != null && errorVOs.size() > 0) {
			responseType.getResponseDetails().setStatus(FAILURE);
			responseType.getResponseDetails().getErrorDetails().addAll(populateErrorDetailType(errorVOs));
		}
		responseType.getMessageHeader().setSourceSystem(MSG_SOURCE);
		responseType.getMessageHeader().setMessageType(MSG_TYPE);
		return responseType;
	}
	
	/**
	 * 
	 * 	Method		:	StockcontrolDefaultsServiceBaseImpl.findStockRequests
	 *	Added by 	:	A-5258 on Jun 22, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param findStockRequestType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws InvalidRequestFault
	 *	Parameters	:	@throws ServiceFault 
	 *	Return type	: 	FindStockResponseType
	 */
	public FindStockResponseType findStockRequests(FindStockRequestType findStockRequestType) throws InvalidRequestFault, ServiceFault {
		log.entering(CLASS_NAME, "findStockRequests");
		FindStockResponseType findStockResponseType = new FindStockResponseType();
		MessageHeaderType headerType = findStockRequestType.getMessageHeader();
		findStockResponseType.setMessageHeader(headerType);
		FindStockResponseData findStockResponseData = new FindStockResponseData();
		findStockResponseData.setRequestID(findStockRequestType.getRequestData().getRequestID());
		findStockResponseType.setResponseDetails(findStockResponseData);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		StockRequestFilterType filterType = findStockRequestType.getRequestData().getStockRequestFilter();
		try {
			StockRequestFilterVO stockRequestFilterVO = new StockRequestFilterVO();
			StockAgentVO stockAgentVO = null; 
			Page<StockRequestVO> stockRequestVOs = null;
		if(filterType.getAgentCode()!= null && filterType.getAgentCode().trim().length() > 0){
				stockAgentVO = getStockAgentMapping(filterType.getAgentCode());
				stockRequestFilterVO.setStockHolderCode(stockAgentVO.getStockHolderCode());
			}
			else{
				if(headerType!=null && SOURCE_PORTAL.equals(headerType.getSourceSystem())) {
					throw new WSBusinessException("stockcontrol.defaults.maintainstockagentmapping.error.invalidagent");
				}
			}
				stockRequestFilterVO.setCompanyCode(getCompanyCode());
				stockRequestFilterVO.setStatus(filterType.getRequestStatus()); 
				stockRequestFilterVO.setDocumentType(filterType.getDocType());
				stockRequestFilterVO.setDocumentSubType(filterType.getSubType());
				LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				if(filterType.getRequestedDateFrom()!= null && filterType.getRequestedDateFrom().trim().length()!=0){
					stockRequestFilterVO.setFromDate(from.setDate(filterType.getRequestedDateFrom().trim()));
				}
				if(filterType.getRequestedDateTo()!= null && filterType.getRequestedDateTo().trim().length()!=0 ){
					stockRequestFilterVO.setToDate(to.setDate(filterType.getRequestedDateTo()));
				}
				if(filterType.getRequestedBy()!= null && filterType.getRequestedBy().size() > 0){
					if(filterType.getRequestedBy().size() > 1){
						StringBuilder stockHolderCodes = new StringBuilder();
						for (String stockHolderCode : filterType.getRequestedBy()) {
						//stockAgentVO = getStockAgentMapping(stockHolderCode);//requestedBy field represents stockholderCode so no need to call getStockAgentMapping
						stockHolderCodes.append(stockHolderCode).append(",");
						}
						//privilage level values are setting only for reusing existing dynamic query for multiple user filter
						stockRequestFilterVO.setPrivilegeRule("STK_HLDR_CODE");
						stockRequestFilterVO.setPrivilegeLevelType("STKHLD");
						stockRequestFilterVO.setPrivilegeLevelValue(stockHolderCodes.substring(0,stockHolderCodes.lastIndexOf(",")));
					}else{
					//stockAgentVO = getStockAgentMapping(filterType.getRequestedBy().get(0));
					stockRequestFilterVO.setStockHolderCode(filterType.getRequestedBy().get(0));
				}
			}
			if(filterType.getRequestCreatedBy()!= null && filterType.getRequestCreatedBy().size() > 0){
				List<String> requestCreatedBy = new ArrayList<String>();
				for (String createdBy : filterType.getRequestCreatedBy()) {
					requestCreatedBy.add(createdBy);
				} 
				stockRequestFilterVO.setRequestCreatedBy(requestCreatedBy);
				}
				if(filterType.getPaginationInfo()!=null && filterType.getPaginationInfo().getPageNumber() > 0){
					stockRequestFilterVO.setPageSize(filterType.getPaginationInfo().getPageSize());
					stockRequestVOs = despatchRequest("findStockRequests", stockRequestFilterVO,filterType.getPaginationInfo().getPageNumber()); 
				}else{
					stockRequestVOs = despatchRequest("findStockRequests", stockRequestFilterVO,1); 
				}
				if(stockRequestVOs!=null){ 
					PaginationType paginationType = new PaginationType();
					paginationType.setPageNumber(stockRequestVOs.getPageNumber());
					paginationType.setPageSize(stockRequestVOs.getActualPageSize());
					paginationType.setLastPageIndex(stockRequestVOs.getLastPageIndex());
					paginationType.setTotalRecordCount(stockRequestVOs.getTotalRecordCount());
					StockRequestType requestType = null;
					for (StockRequestVO stockRequestVO : stockRequestVOs) {
						requestType = new StockRequestType();
						requestType.setStockHolderType(stockRequestVO.getStockHolderType());
						requestType.setDocType(stockRequestVO.getDocumentType());
						requestType.setSubType(stockRequestVO.getDocumentSubType());
						requestType.setRequestedDate(stockRequestVO.getRequestDate().toDisplayFormat());
						requestType.setRequestStatus(stockRequestVO.getStatus());
						requestType.setRequestedStockQuantity(new BigInteger(String.valueOf(stockRequestVO.getRequestedStock())));
						requestType.setApprovedStockQuantity(new BigInteger(String.valueOf(stockRequestVO.getApprovedStock())));
						requestType.setAllocatedStockQuantity(new BigInteger(String.valueOf(stockRequestVO.getAllocatedStock())));   
						requestType.setStockHolderCode(stockRequestVO.getStockHolderCode());   
					requestType.setRequestCreatedBy(stockRequestVO.getRequestCreatedBy());
						findStockResponseData.getStockRequestDetails().add(requestType);    
					}
					findStockResponseType.getResponseDetails().setPaginationInfo(paginationType);
					findStockResponseType.getResponseDetails().setStatus(SUCCESS);	
				}else{
					ErrorVO errorVO = new ErrorVO("stockcontrol.defaults.norecordsmonitornotauthorized");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
				}
			} catch (WSBusinessException e) {
				errorVOs = e.getErrors();
			} catch (SystemException ex) {
				errorVOs = ex.getErrors();
			}
		if (errorVOs != null && errorVOs.size() > 0) {
			findStockResponseType.getResponseDetails().setStatus(FAILURE);
			findStockResponseType.getResponseDetails().getErrorDetails().addAll(populateErrorDetailType(errorVOs));
		}
		//setting source and messge type
		findStockResponseType.getMessageHeader().setSourceSystem(MSG_SOURCE);
		findStockResponseType.getMessageHeader().setMessageType(MSG_TYPE);
		return findStockResponseType;
	}

	/**
	 * 
	 * 	Method		:	StockcontrolDefaultsServiceBaseImpl.findAwbStockList
	 *	Added by 	:	A-5258 on Jun 22, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param findAwbStockListRequesType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws InvalidRequestFault
	 *	Parameters	:	@throws ServiceFault 
	 *	Return type	: 	FindAwbStockListResponseType
	 */
	public FindAwbStockListResponseType findAwbStockList(
			FindAwbStockListRequesType findAwbStockListRequesType) throws InvalidRequestFault,
			ServiceFault {
		log.entering(CLASS_NAME, "findAwbStockList");
		FindAwbStockListResponseType responseType = new FindAwbStockListResponseType();
		AwbStockListFilterType  stockListFilterType = findAwbStockListRequesType.getRequestData().getAwbStockListFilter();
		MessageHeaderType headerType = findAwbStockListRequesType.getMessageHeader();
		responseType.setMessageHeader(headerType);
		FindAwbStockListResponseData responseData = new FindAwbStockListResponseData();
		responseData.setRequestID(findAwbStockListRequesType.getRequestData().getRequestID());
		responseType.setResponseDetails(responseData);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		Page<StockRangeHistoryVO> stockRangeHistoryVOs= null;
		StockRangeFilterVO stockRangeFilterVO = null;
		try {
			StockAgentVO stockAgentVO = null;
			stockRangeFilterVO = new StockRangeFilterVO();
			if(stockListFilterType.getAgentCode()!= null && stockListFilterType.getAgentCode().trim().length()!=0){
				String agentCodes[] = stockListFilterType.getAgentCode().split(",");
				StringBuilder stockHolderCodes = new StringBuilder(); 
				for (String agentCode : agentCodes) {
					if(agentCode!=null && agentCode.trim().length() > 0){
						stockAgentVO = getStockAgentMapping(agentCode);
						stockHolderCodes.append(stockAgentVO.getStockHolderCode()).append(",");
					} 
				} 
				//privilage level values are setting only for reusing existing dynamic query for multiple user filter
				stockRangeFilterVO.setPrivilegeRule("STK_HLDR_CODE");
				stockRangeFilterVO.setPrivilegeLevelType("STKHLD");
				stockRangeFilterVO.setPrivilegeLevelValue(stockHolderCodes.substring(0,stockHolderCodes.lastIndexOf(",")));
			}else{
				if(headerType!=null && SOURCE_PORTAL.equals(headerType.getSourceSystem())) {
					throw new WSBusinessException("stockcontrol.defaults.maintainstockagentmapping.error.invalidagent");
				}
			}
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			stockRangeFilterVO.setCompanyCode(getCompanyCode());
			stockRangeFilterVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
					LocalDate startDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true); 
					LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
					if(stockListFilterType.getRequestedDateFrom()!= null && stockListFilterType.getRequestedDateFrom().trim().length()!=0){
						stockRangeFilterVO.setStartDate(startDate.setDate(stockListFilterType.getRequestedDateFrom()));
					}
					if(stockListFilterType.getRequestedDateTo()!= null && stockListFilterType.getRequestedDateTo().trim().length()!=0){
						stockRangeFilterVO.setEndDate(endDate.setDate(stockListFilterType.getRequestedDateTo()));
					}
					if(stockListFilterType.getStatus()!= null && stockListFilterType.getRequestedDateTo().trim().length()!=0){
						stockRangeFilterVO.setStatus(stockListFilterType.getStatus());
					}else{
						stockRangeFilterVO.setStatus("");
					}
					if(stockListFilterType.getAwbRangeFrom()!= null && stockListFilterType.getAwbRangeFrom().trim().length()!=0){
						stockRangeFilterVO.setStartRange(stockListFilterType.getAwbRangeFrom());
					}
					if(stockListFilterType.getAwbRangeTo()!= null && stockListFilterType.getAwbRangeTo().trim().length()!=0){
						stockRangeFilterVO.setEndRange(stockListFilterType.getAwbRangeTo());
					}
					if(stockListFilterType.getPaginationInfo()!=null && stockListFilterType.getPaginationInfo().getPageNumber() > 0){
						stockRangeFilterVO.setPageSize(stockListFilterType.getPaginationInfo().getPageSize());
						stockRangeFilterVO.setPageNumber(stockListFilterType.getPaginationInfo().getPageNumber()); 
					}else{
						stockRangeFilterVO.setPageNumber(1); 
					}
					stockRangeHistoryVOs = despatchRequest("findStockRangeHistoryForPage", stockRangeFilterVO);
					if(stockRangeHistoryVOs!=null){
						PaginationType paginationType = new PaginationType();
						paginationType.setPageNumber(stockRangeHistoryVOs.getPageNumber());
						paginationType.setPageSize(stockRangeHistoryVOs.getActualPageSize());
						paginationType.setLastPageIndex(stockRangeHistoryVOs.getLastPageIndex());
						paginationType.setTotalRecordCount(stockRangeHistoryVOs.getTotalRecordCount());
						AwbStockListDetailsType detailsType = null;
						for (StockRangeHistoryVO historyVO : stockRangeHistoryVOs) {
							detailsType = new AwbStockListDetailsType();
							detailsType.setAwbRange(historyVO.getAwbRange());
							detailsType.setStockQty(new BigInteger(String.valueOf(historyVO.getNumberOfDocuments())));
							detailsType.setRequestedDate(historyVO.getTransDateStr());
							detailsType.setStatus(historyVO.getStatus());
							detailsType.setDocType(historyVO.getDocumentType());
							detailsType.setSubType(historyVO.getDocumentSubType());  
							responseData.getAwbStockListDetails().add(detailsType);
						}
						responseData.setPaginationInfo(paginationType);
						responseData.setStatus(SUCCESS);	
			}else{
				ErrorVO errorVO = new ErrorVO("stockcontrol.defaults.norecordsmonitornotauthorized");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorVOs.add(errorVO);
			}
		} catch (WSBusinessException e) {
			errorVOs = e.getErrors();
		} catch (SystemException ex) {
			errorVOs = ex.getErrors();
		}
		if (errorVOs != null && errorVOs.size() > 0) {
			responseType.getResponseDetails().setStatus(FAILURE);
			responseType.getResponseDetails().getErrorDetails().addAll(populateErrorDetailType(errorVOs));
		}
		//setting source and messge type
		responseType.getMessageHeader().setSourceSystem(MSG_SOURCE);
		responseType.getMessageHeader().setMessageType(MSG_TYPE);
		return responseType;
	}

	/**
	 * 
	 * 	Method		:	StockcontrolDefaultsServiceBaseImpl.findStockRangeHistory
	 *	Added by 	:	A-5258 on Jun 22, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param historyRequestType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws InvalidRequestFault
	 *	Parameters	:	@throws ServiceFault 
	 *	Return type	: 	FindStockRangeHistoryResponseType
	 */
	public FindStockRangeHistoryResponseType findStockRangeHistory(
			FindStockRangeHistoryRequestType historyRequestType) throws InvalidRequestFault,
			ServiceFault {
		log.entering(CLASS_NAME, "findStockRangeHistory");
		FindStockRangeHistoryResponseType responseType = new FindStockRangeHistoryResponseType();
		MessageHeaderType headerType = historyRequestType.getMessageHeader();
		FindStockRangeHistoryResponseData responseData = new FindStockRangeHistoryResponseData();
		responseData.setRequestID(historyRequestType.getRequestData().getRequestID());
		responseType.setResponseDetails(responseData);
		AwbStockHistoryFilterType  filterType = historyRequestType.getRequestData().getAwbStockHistoryFilter();
		responseType.setMessageHeader(headerType);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		try {
			if(headerType!=null && SOURCE_PORTAL.equals(headerType.getSourceSystem())) {
			if(filterType.getAgentCode() == null || filterType.getAgentCode().trim().length() == 0){
					throw new WSBusinessException("stockcontrol.defaults.maintainstockagentmapping.error.invalidagent");
				}
			 }
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				StockRangeFilterVO stockRangeFilterVO= new StockRangeFilterVO();
				Collection<StockRangeHistoryVO> stockRangeHistoryVOs = null;
				stockRangeFilterVO.setCompanyCode(getCompanyCode());
				stockRangeFilterVO.setAwp(filterType.getAwbPrefix());
				stockRangeFilterVO.setAwb(filterType.getAwbNumber());
				stockRangeFilterVO.setHistory(false);
				stockRangeFilterVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
				stockRangeHistoryVOs = despatchRequest("findStockRangeHistory", stockRangeFilterVO);
				if(stockRangeHistoryVOs!=null && stockRangeHistoryVOs.size() > 0){
					AwbStockHistoryDetailsType detailsType = null;
					for (StockRangeHistoryVO historyVO : stockRangeHistoryVOs) {
						detailsType = new AwbStockHistoryDetailsType();
						detailsType.setFromStockHolder(historyVO.getFromStockHolderCode());
						detailsType.setToStockHolder(historyVO.getToStockHolderCode());
						detailsType.setAwbPrefix(filterType.getAwbPrefix());
						detailsType.setAwbNumber(filterType.getAwbNumber());
						detailsType.setStatus(historyVO.getStatus());
						detailsType.setBy(historyVO.getLastUpdateUser());
						if(historyVO.getTransDateStr()!=null && historyVO.getTransDateStr().length() > 11){
							detailsType.setDate(historyVO.getTransDateStr().substring(0,11));
							}else{
								detailsType.setDate(historyVO.getTransDateStr());
						}
						responseData.getAwbStockHistoryDetails().add(detailsType);
					}
					responseData.setStatus(SUCCESS);
				}else{
					ErrorVO errorVO = new ErrorVO("stockcontrol.defaults.norecordsmonitornotauthorized");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
				}
		} catch (WSBusinessException e) {
			errorVOs = e.getErrors();
		} catch (SystemException ex) {
			errorVOs = ex.getErrors();
		}
		if (errorVOs != null && errorVOs.size() > 0) {
			responseType.getResponseDetails().setStatus(FAILURE);
			responseType.getResponseDetails().getErrorDetails().addAll(populateErrorDetailType(errorVOs));
		}
		//setting source and messge type
		historyRequestType.getMessageHeader().setSourceSystem(MSG_SOURCE);
		historyRequestType.getMessageHeader().setMessageType(MSG_TYPE);
		
		return responseType;
	}
	
	/**
	 * 
	 * 	Method		:	StockcontrolDefaultsServiceBaseImpl.getStockAgentMapping
	 *	Added by 	:	A-5258 on Jun 25, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws WSBusinessException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	StockAgentVO
	 */
	
	private StockAgentVO getStockAgentMapping(String agentCode) throws WSBusinessException,SystemException{
		log.entering(CLASS_NAME, "getStockHolderCode");
		StockAgentVO stockAgentVO = null;
		if(agentCode!=null && agentCode.trim().length()!=0){ 
			Page<StockAgentVO> stockAgentVOs = null;
			StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
			stockAgentFilterVO.setAgentCode(agentCode);
			stockAgentFilterVO.setCompanyCode(getCompanyCode());
			stockAgentFilterVO.setPageNumber(1);
			stockAgentVOs = despatchRequest("findStockAgentMappings",stockAgentFilterVO);
			if(stockAgentVOs!=null && stockAgentVOs.size() > 0){
				stockAgentVO = stockAgentVOs.iterator().next();
			}else{
				throw new WSBusinessException("stockcontrol.defaults.maintainstockagentmapping.error.invalidagent");
			}
		}
		return stockAgentVO;
	}



	public ValidateDocumentStockResponseType validateDocumentStock(ValidateDocumentStockRequestType validateDocumentRequestType) throws InvalidRequestFault , ServiceFault    {
		log.entering("StockcontrolDefaultsServiceImpl", "validateDocument");
		ValidateDocumentStockResponseType validateDocumentResponseType = new ValidateDocumentStockResponseType();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		DocumentFilterVO documentFilterVO = populateStockDocumentFilterVO(validateDocumentRequestType);
		DocumentValidationVO  documentValidationVO = null;
		try {
			documentValidationVO = despatchRequest("validateDocumentStockForWS", documentFilterVO);
		} catch (WSBusinessException e) {
			errors = e.getErrors();

		} catch (SystemException ex) {
			if(ex != null && !(ex.getErrors().isEmpty())) {
				for(ErrorVO errorVO : ex.getErrors()) {
					if ("shared.airline.invalidairline".equals(errorVO.getErrorCode())
							|| "stockcontrol.defaults.awbnumberinvalid".equals(errorVO.getErrorCode())
							|| "stockcontrol.defaults.agentnotfound".equals(errorVO.getErrorCode())) {
						errors = ex.getErrors();
					}
					else{
						throw new ServiceFault("validateDocument ,Exception occured in validateDocument"+ errorVO.getErrorCode());}
				}
			}
		}

		return populateValidateStockDocumentResponseType(documentValidationVO, errors, validateDocumentRequestType);


	}

}
