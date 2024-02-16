/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping.SaveCommand.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.CustomerDetails;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.PoaMappingDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class SaveCommand extends AbstractCommand {
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String TYPE_TMP = "TMP";
	private static final String SOURCE_POA = "MNGPOA";
	
	private static final String POATYPE_GENERAL = "General POA";
	private static final String POATYPE_SPECIAL = "Special POA";
	private static final String POATYPE_SINGLE = "Single POA";
	private static final String VLD_END_DATE = "31-Dec-2099";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		BrokerMappingModel brokerMappingModel = (BrokerMappingModel) actionContext.getScreenModel();
		ScreenSession  screenSession = getScreenSession();
		Map<String, UploadFileModel> uploadedFileMap = screenSession.getFromScreenSessionMap("fileUploadMap");
		LogonAttributes logonAttributes = getLogonAttribute();
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();

		List<ErrorVO> errors = new ArrayList<>();

		CustomerVO customerVO = null;
		String companyCode = logonAttributes.getCompanyCode();

		BrokerMappingFilter brokerMappingFilter = brokerMappingModel.getBrokerMappingFilter();
		if (brokerMappingFilter.getCustomerCode() != null && brokerMappingFilter.getCustomerCode().trim().length() > 0) {
			CustomerFilterVO customerFilterVO = new CustomerFilterVO();
			customerFilterVO.setCompanyCode(companyCode);
			customerFilterVO.setCustomerCode(brokerMappingFilter.getCustomerCode());
			customerFilterVO.setSource(SOURCE_POA);

			try {
				customerVO = delegate.listCustomer(customerFilterVO);
				
				customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
				populateCustomerVO(customerVO, brokerMappingModel, uploadedFileMap);
			} catch (BusinessDelegateException ex) {
				handleDelegateException(ex);
			}
		} else {
			errors.addAll(validateStation(brokerMappingModel.getCustomerDetails().getStation(), companyCode));
			errors.addAll(validateCountry(brokerMappingModel.getCustomerDetails().getCountry(), companyCode));

			if (errors.isEmpty()) {
				customerVO = new CustomerVO();
				customerVO.setCompanyCode(companyCode);
				customerVO.setCustomerType(TYPE_TMP);
				customerVO.setOperationFlag(OPERATION_FLAG_INSERT);

				populateCustomerVO(customerVO, brokerMappingModel, uploadedFileMap);
			}
		}

		if (errors.isEmpty()) {
			try {
				String customerCode = delegate.saveBrokerMapping(customerVO);
				List<String> results = new ArrayList<>();
				if(customerCode==null){
					customerCode=brokerMappingFilter.getCustomerCode();
				}
				results.add(customerCode);

				ResponseVO responseVO = new ResponseVO();
				responseVO.setResults(results);
				responseVO.setStatus(LIST_SUCCESS);
				actionContext.setResponseVO(responseVO);
			} catch (BusinessDelegateException ex) {
				handleDelegateException(ex);
			}
		} else {
			actionContext.addAllError(errors);
		}
	}

	private void populateCustomerVO(CustomerVO customerVO, BrokerMappingModel brokerMappingModel,
			Map<String, UploadFileModel> uploadedFileMap) {
		customerVO.setSource(SOURCE_POA);

		Collection<PoaMappingDetails> brokerDetails = brokerMappingModel.getBrokerDetails();
		Collection<PoaMappingDetails> singlePoaDetails = brokerMappingModel.getAwbDetails();
		if (brokerDetails != null && !brokerDetails.isEmpty()) {
			if (singlePoaDetails != null && !singlePoaDetails.isEmpty()) {
				brokerDetails.addAll(singlePoaDetails);
			}
		} else {
			brokerDetails = singlePoaDetails;
		}
		if (brokerDetails != null && !brokerDetails.isEmpty()) {
			customerVO.setCustomerAgentVOs(populatePOADetails(brokerDetails, uploadedFileMap,
					customerVO.getCompanyCode(), customerVO.getCustomerCode()));
		}

		Collection<PoaMappingDetails> consigneeDetails = brokerMappingModel.getConsigneeDetails();
		if (consigneeDetails != null && !consigneeDetails.isEmpty()) {
			customerVO.setCustomerConsigneeVOs(populatePOADetails(consigneeDetails, uploadedFileMap,
					customerVO.getCompanyCode(), customerVO.getCustomerCode()));
		}

		CustomerDetails customerDetails = brokerMappingModel.getCustomerDetails();
		customerVO.setAdditionalDetails(customerDetails.getAdditionalDetails());

		if (TYPE_TMP.equalsIgnoreCase(customerVO.getCustomerType())) {
			customerVO.setCustomerName(customerDetails.getCustomerName());
			customerVO.setAddress1(customerDetails.getStreet());
			customerVO.setCity(customerDetails.getCity());
			customerVO.setCountry(customerDetails.getCountry());
			customerVO.setZipCode(customerDetails.getZipCode());
			customerVO.setStationCode(customerDetails.getStation());
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			customerVO.setValidFrom(fromDate);
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).addYears(100);
			customerVO.setValidTo(toDate);
			customerVO.setStatus("A");
		}

		if (!customerDetails.getAdditionalNames().isEmpty()) {
			List<String> addNames = new ArrayList<>();
			customerDetails.getAdditionalNames().forEach(name -> addNames.add(name.getAdlNam()));
			customerVO.setAdditionalNames(String.join(";", addNames));
		}else{
			customerVO.setAdditionalNames(null); 
		}
	}

	private Collection<CustomerAgentVO> populatePOADetails(Collection<PoaMappingDetails> poaDetails,
			Map<String, UploadFileModel> uploadedFileMap, String companyCode, String customerCode) {
		Collection<CustomerAgentVO> customerPOADetails = new ArrayList<>();

		for (PoaMappingDetails detail : poaDetails) {
			CustomerAgentVO agentVO = new CustomerAgentVO();

			agentVO.setCompanyCode(companyCode);
			agentVO.setSequenceNumber(detail.getSequenceNumber());
			agentVO.setCustomerCode(customerCode);

			String operationFlag = detail.getOperatonalFlag();
			agentVO.setOperationFlag(operationFlag);

			String stationCode = detail.getStation();
			agentVO.setStationCode(stationCode);

			LocalDate localDate = null;
			LocalDate vldStartDate = null;
			LocalDate vldEndDate = null;
			if (OPERATION_FLAG_INSERT.equals(operationFlag)) {
				if (stationCode != null) {
					localDate = new LocalDate(stationCode, Location.STN, true);
				} else {
					localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				}
			} else {
				agentVO.setRemarks(detail.getDeletionRemarks());

				localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				localDate.setDate(detail.getCreationDate().substring(0, 11));	
			}
			vldStartDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			if(detail.getVldStartDate()!= null){
				vldStartDate.setDate(detail.getVldStartDate().substring(0, 11));
			}
			
			vldEndDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			vldEndDate.setDate(VLD_END_DATE);
			
			agentVO.setPoaCreationTime(localDate);
			agentVO.setValidityStartDate(vldStartDate);
			agentVO.setValidityEndDate(vldEndDate);
			
			String poaType = detail.getPoaType();
			agentVO.setPoaType(poaType);

			switch (poaType) {
			case POATYPE_GENERAL:
			case POATYPE_SPECIAL:
				agentVO.setAgentName(detail.getAgentName());
				agentVO.setDestination(setArrayToString(detail.getDestination()));
				
				String agentCode = detail.getAgentCode();
				agentVO.setAgentCode(agentCode);

				agentVO.setSupportingDocumentVOs(setPOAAttachcments(uploadedFileMap, operationFlag, agentCode));

				if (POATYPE_SPECIAL.equals(poaType)) {
					agentVO.setScc(setArrayToString(detail.getSccCodeInclude()));
					agentVO.setExcludedScc(setArrayToString(detail.getSccCodeExclude()));
					agentVO.setIncludedOrigins(setArrayToString(detail.getOrginInclude()));
					agentVO.setExcludedOrigins(setArrayToString(detail.getOrginExclude()));
				}

				break;
			case POATYPE_SINGLE:
				/* awbNumber as '020 02002000' */
				agentVO.setAwbNum(detail.getAwbNumber());
				break;
			default:
				break;
			}

			customerPOADetails.add(agentVO);
		}

		return customerPOADetails;
	}

	private List<CustomerSupportingDocumentVO> setPOAAttachcments(Map<String, UploadFileModel> uploadedFileMap,
			String operationFlag, String agentCode) {
		List<CustomerSupportingDocumentVO> documents = new ArrayList<>();

		if (OPERATION_FLAG_INSERT.equals(operationFlag) && uploadedFileMap != null && !uploadedFileMap.isEmpty()) {
			String fileKey = new StringBuilder().append("UPLOAD_FILE_KEY_").append(SOURCE_POA).append(agentCode).toString();
			UploadFileModel fileModel = uploadedFileMap.get(fileKey);

			if (fileModel != null) {
				CustomerSupportingDocumentVO documentVO = new CustomerSupportingDocumentVO();
				documentVO.setOperationFlag(operationFlag);
				documentVO.setFileData(fileModel.getData());
				documentVO.setFileSize(fileModel.getFileSize());
				documentVO.setContentType(fileModel.getContentType());

				String fileName = fileModel.getFileName().split(".pdf")[0];
				if (fileName.length() > 46) {
					fileName = fileName.substring(0, 46);
				}
				documentVO.setFileName(new StringBuilder().append(fileName).append(".pdf").toString());

				documents.add(documentVO);
			}
		}

		return documents;
	}

	private String setArrayToString(String[] stringArray) {
		String value = null;
		if (stringArray != null && stringArray.length > 0) {
			value = String.join(",", stringArray);
		}
		return value;
	}

	private List<ErrorVO> validateStation(String stationCode, String companyCode) {
		List<ErrorVO> errors = new ArrayList<>();
		AreaValidationVO areaValidationVO = null;
		try {
			areaValidationVO = new AreaDelegate().validateLevel(companyCode, "STN", stationCode);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		if (areaValidationVO == null) {
			ErrorVO error = new ErrorVO("Invalid Station Code");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		return errors;
	}

	private List<ErrorVO> validateCountry(String countryCode, String companyCode) {
		List<ErrorVO> errors = new ArrayList<>();
		AreaValidationVO areaValidationVO = null;
		try {
			areaValidationVO = new AreaDelegate().validateLevel(companyCode, "CNT", countryCode);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		if (areaValidationVO == null) {
			ErrorVO error = new ErrorVO("Invalid Country Code");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		return errors;
	}

}
