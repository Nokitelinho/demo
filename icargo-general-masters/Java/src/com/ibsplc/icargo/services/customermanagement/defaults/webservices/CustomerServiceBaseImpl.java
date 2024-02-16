/**
 *	Java file	: 	com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerServiceBaseImpl.java
 *
 *	Created by	:	A-4789
 *	Created on	:	22-Nov-2012
 *
 *  Copyright 2012 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.customermanagement.defaults.webservices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AddressDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgentDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.BillingDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.BillingPeriod;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CertificateDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CollectionAgentDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ContactDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.DefaultNotificationMode;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ErrorDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.HeaderDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.IACDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.MiscDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.NaccsDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.SaveStatusType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.YESNOFlag;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerBillingDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerErrorVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.IACDetailsVO;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerServiceBaseImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4789	:	22-Nov-2012	:	Draft
 */
public class CustomerServiceBaseImpl extends WebServiceEndPoint {
	
	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT_DEFAULTS");
	
	private static final String CLASS_NAME = "CustomerServiceImpl";
	
	private static final String MSG_TYPE_ACK = "A";
	
	private static final String MSG_SOURCE = "iCargo";
	
	@Override
    public boolean isProtected() {
    	return true;
	}
	
	private HeaderDetailType getMessageHeader() {
		HeaderDetailType header = new HeaderDetailType();
		header.setMessageType(MSG_TYPE_ACK);
		header.setSourceSystem(MSG_SOURCE);
		return header;
	}
	
	private long getResponseId() {
		LocalDate datetime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		return datetime.getTimeInMillis();
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceImplementation.getCustomerDetails
	 *	Added by 	:	A-4789 on 22-Nov-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault 
	 *	Return type	: 	CustomerResponse
	 */
	public CustomerResponse getCustomerDetails(CustomerRequest customerRequest) throws ServiceFault , CustomerFault    { 
    	log.entering(CLASS_NAME, "getCustomer");
    	CustomerResponse response = null;
    	Collection<CustomerVO> customerVOs = new ArrayList<CustomerVO> ();
    	Collection<CustomerFilterVO> customerFilters = new ArrayList<CustomerFilterVO>();
        try {
        	customerFilters.add(populateCustomerFilterVO(customerRequest));
        	if(customerFilters.isEmpty()) {
        		throw new ServiceFault("Request data unavailable.");
        	}
        	customerVOs = despatchRequest("getCustomerDetails", customerFilters);
        	if(customerVOs.isEmpty()) {
        		throw new ServiceFault("Response data unavailable.");
        	}
        	response = populateCustomerResponse(customerVOs.iterator().next());
        } catch (Exception ex) {
        	throw new ServiceFault("Exception occured!!" + ex.getMessage());
        }
        return response;
    }
	
	/**
	 * 	Method		:	CustomerServiceImpl.populateCustomerFilterVO
	 *	Added by 	:	A-4789 on 17-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerFilterVO
	 */
	private CustomerFilterVO populateCustomerFilterVO(
			CustomerRequest customerRequest) {
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		
		if(customerRequest != null
				&& customerRequest.getCustomerRequestData() != null
				&& customerRequest.getCustomerRequestData().getCustomerRequestDetails() != null) {
			customerFilterVO.setCompanyCode(getCompanyCode());
			customerFilterVO.setCustomerCode(customerRequest.getCustomerRequestData().getCustomerRequestDetails().getCustomerCode());
			customerFilterVO.setCustomerShortCode(customerRequest.getCustomerRequestData().getCustomerRequestDetails().getCustomerShortCode());
			return customerFilterVO;
		}
		return null;
	}
	
	/**
	 * 	Method		:	CustomerServiceImpl.populateCustomerResponse
	 *	Added by 	:	A-4789 on 18-Oct-2012
	 * 	Used for 	:	Populating the response from the customerVO.
	 * 					Used by the getCustomer service
	 *	Parameters	:	@param customerVO
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerResponse
	 */
	private CustomerResponse populateCustomerResponse(CustomerVO customerVO) {
		CustomerResponse response = new CustomerResponse();
		CustomerResponseType responseType = new CustomerResponseType();
		CustomerType customerType = new CustomerType();
		
		response.setMessageHeader(getMessageHeader());
		customerType.setCustomerCode(customerVO.getCustomerCode());
		customerType.setCustomerShortCode(customerVO.getCustomerShortCode());
		Collection<CustomerErrorVO> customerErrors = customerVO.getCustomerErrors();
		if(customerErrors != null 
				&& customerErrors.size() > 0) {
			for(CustomerErrorVO customerErrorVO : customerErrors) {
				ErrorDetailType error = new ErrorDetailType();
				error.setErrorCode(customerErrorVO.getErrorCode());
				error.setErrorDesc(customerErrorVO.getErrorDescription());
				customerType.getErrorDetails().add(error);
			}
		} else {
			
			customerType.setAccountNumber(customerVO.getAccountNumber());
			
			// Sets the address details
			AddressDetailType addressType = new AddressDetailType();
			addressType.setAddressOne(customerVO.getAddress1());
			addressType.setAddressTwo(customerVO.getAddress2());
			addressType.setCity(customerVO.getCity());
			addressType.setCountryCode(customerVO.getCountry());
			addressType.setEmailAddress(customerVO.getEmail());
			addressType.setFaxNumber(customerVO.getFax());
			addressType.getLocation(); // what to do for this?
			addressType.setMobilenumber(customerVO.getMobile());
			addressType.setPhonenumber(customerVO.getTelephone());
			addressType.setState(customerVO.getState());
			addressType.setStreet(customerVO.getArea()); // is this right?
			addressType.setZipCode(customerVO.getZipCode());
			customerType.setAddressDetails(addressType);
			
			// If the customer type is AG then agent details needs to be set.
			AgentDetailType agentType = null;
			if(CustomerVO.CUSTOMER_TYPE_AG.equals(customerVO.getCustomerType())) {
				agentType = new AgentDetailType();
				agentType.setCountryCode(customerVO.getAgentCountryCode());
				agentType.setCurrencyCode(customerVO.getAgentCurrencyCode());
				if(customerVO.getAgentExportFlag() != null) {
					agentType.setExportFlag(YESNOFlag.fromValue(customerVO.getAgentExportFlag()));
				}
				agentType.setIATACode(customerVO.getAgentIATACode());
				if(customerVO.getAgentImportFlag() != null) {
					agentType.setImportFlag(YESNOFlag.fromValue(customerVO.getAgentImportFlag()));
				}
				agentType.setOwnAirlineCode(customerVO.getAgentOwnAirlineCode());
				if(customerVO.getAgentSalesFlag() != null) {
					agentType.setSalesFlag(YESNOFlag.fromValue(customerVO.getAgentSalesFlag()));
				}
				agentType.setValidFromDate(customerVO.getAgentValidFromDate() != null ? 
						customerVO.getAgentValidFromDate().toDefaultStringFormat() : null);
				agentType.setValidToDate(customerVO.getAgentValidToDate() != null ? 
						customerVO.getAgentValidToDate().toDefaultStringFormat() : null);
			}
			customerType.setAgentDetails(agentType);
			
			customerType.setAirportCode(customerVO.getAirportCode());
			
			// Billing Details type
			BillingDetailType billingDetailType = new BillingDetailType();
			CustomerBillingDetailsVO customerBillingDetailsVO = customerVO.getCustomerBillingDetailsVO();
			if(customerBillingDetailsVO != null) {
				AddressDetailType billingAddressType = new AddressDetailType();
				billingAddressType.setCity(customerBillingDetailsVO.getCityCode());
				billingAddressType.setCountryCode(customerBillingDetailsVO.getCountry());
				billingAddressType.setEmailAddress(customerBillingDetailsVO.getEmail());
				billingAddressType.setFaxNumber(customerBillingDetailsVO.getFax());
				billingAddressType.setLocation(customerBillingDetailsVO.getLocation());
				billingAddressType.setState(customerBillingDetailsVO.getState());
				billingAddressType.setStreet(customerBillingDetailsVO.getStreet());
				billingAddressType.setPhonenumber(customerBillingDetailsVO.getTelephone());
				billingAddressType.setZipCode(customerBillingDetailsVO.getZipcode());
				
				billingDetailType.setBillingAddressDetail(billingAddressType);
				billingDetailType.setBillingCurrencyCode(customerVO.getBillingCode());
				
				customerType.setBillingDetails(billingDetailType);
			}
			
			if(customerVO.getBillingPeriod() != null) {
				customerType.setBillingPeriod(BillingPeriod.fromValue(customerVO.getBillingPeriod()));
			}
			
			Collection<CustomerCertificateVO> customerCertificates = customerVO.getCustomerCertificateDetails();
			if(customerCertificates != null 
					&& customerCertificates.size() > 0) {
				for(CustomerCertificateVO certificate : customerCertificates) {
					CertificateDetailsType certificateType = new CertificateDetailsType();
					certificateType.setCertificateType(certificate.getCertificateCode());
					certificateType.setValidityFromDate(certificate.getValidityStartDate() != null ?
							certificate.getValidityStartDate().toDefaultStringFormat() : null);
					certificateType.setValidityToDate(certificate.getValidityEndDate() != null ? 
							certificate.getValidityEndDate().toDefaultStringFormat() : null);
					customerType.getCertificateDetails().add(certificateType);
				}
			}
			
			// Contact Detail Type set to customerType
			Collection<CustomerContactVO> contactVOs = customerVO.getCustomerContactVOs();
			if(contactVOs != null 
					&& contactVOs.size() > 0) {
				for(CustomerContactVO contactVO : contactVOs) {
					ContactDetailType contactDetailType  = new ContactDetailType();
					
					contactDetailType.setDesignation(contactVO.getCustomerDesignation());
					contactDetailType.setFaxNumber(contactVO.getFax());
					contactDetailType.setFirstName(contactVO.getContactCustomerCode());
					contactDetailType.setLastName(contactVO.getCustomerName());
					contactDetailType.setMobilenumber(contactVO.getMobile());
					contactDetailType.setPhonenumber(contactVO.getTelephone());
					if(contactVO.getPrimaryUserFlag() != null) {
						contactDetailType.setPrimaryContact(YESNOFlag.fromValue(contactVO.getPrimaryUserFlag()));
					}
					contactDetailType.setRemarks(contactVO.getRemarks());
					contactDetailType.setSitaAddress(contactVO.getSiteAddress());
					customerType.getContactDetails().add(contactDetailType);
				}
			}
			
			// Collection Agent Details
			Collection<CustomerAgentVO> collectionAgents = customerVO.getCustomerAgentVOs();
			if(collectionAgents != null 
					&& collectionAgents.size() > 0) {
				for(CustomerAgentVO customerAgentVO : collectionAgents) {
					CollectionAgentDetailType collectionAgentDetailType = new CollectionAgentDetailType();
					
					collectionAgentDetailType.setAgentCode(customerAgentVO.getAgentCode());
					if(customerAgentVO.getExportFlag() != null) {
						collectionAgentDetailType.setExportFlag(YESNOFlag.fromValue(customerAgentVO.getExportFlag()));
					}
					collectionAgentDetailType.setHandlingCode(customerAgentVO.getScc());
					if(customerAgentVO.getImportFlag() != null) {
						collectionAgentDetailType.setImportFlag(YESNOFlag.fromValue(customerAgentVO.getImportFlag()));
					}
					collectionAgentDetailType.setStationCode(customerAgentVO.getStationCode());
				}
			}
			
			customerType.setCustomerEORINumber(customerVO.getEoriNo());
			customerType.setCustomerEstablishedDate(customerVO.getEstablishedDate() != null ?
					customerVO.getEstablishedDate().toDefaultStringFormat() : null);
			customerType.setCustomerGroup(customerVO.getCustomerGroup());
			
			// Miscellaneous Detail Type set to customerType.
			MiscDetailType miscDetailType = new MiscDetailType();
			miscDetailType.setLavNumber(customerVO.getLavNumber());
			customerType.setCustomermiscDetails(miscDetailType);
			
			customerType.setCustomerName(customerVO.getCustomerName());
			if(customerVO.getStatus() != null) {
				customerType.setCustomerStatus(customerVO.getStatus());
			}
			customerType.setCustomerType(customerVO.getCustomerType());
			if(customerVO.getDefaultNotifyMode() != null) {
				customerType.setDefaultNotificationMode(DefaultNotificationMode.fromValue(customerVO.getDefaultNotifyMode()));
			}
			if(customerVO.getGlobalCustomerFlag() != null) {
				customerType.setGlobalCustomer(YESNOFlag.fromValue(customerVO.getGlobalCustomerFlag()));
			}
			
			// IAC Details set to the customerType.
			IACDetailsVO iacDetailsVO = customerVO.getIacDetailsVO();
			if(iacDetailsVO != null) {
				IACDetailType iacDetailsType = new IACDetailType();
				iacDetailsType.setCustomerIACexpirydate(iacDetailsVO.getIacExpiryDate() != null ? 
						iacDetailsVO.getIacExpiryDate().toDefaultStringFormat() : null);
				iacDetailsType.setCustomerIACSSPExpiryDate(iacDetailsVO.getApiacsspExpiryDate() != null ?
						iacDetailsVO.getApiacsspExpiryDate().toDefaultStringFormat() : null);
				iacDetailsType.setCustomerIACsspNumber(iacDetailsVO.getApiacsspNumber());
				iacDetailsType.setCustomerIACNumber(iacDetailsVO.getIacNumber());
				customerType.setIacDetails(iacDetailsType);
			}
			
			if(customerVO.getKnownShipper() != null) {
				customerType.setKnownShipper(YESNOFlag.fromValue(customerVO.getKnownShipper()));
			}
			
			// NACCS Detail Type
			NaccsDetailType naccsDetailType = new NaccsDetailType();
			naccsDetailType.setBillingPeriodCode(customerVO.getBillPeriod());
			naccsDetailType.setBranchCode(customerVO.getBranch());
			naccsDetailType.setDefaultHAWBLength(customerVO.getDefaultHawbLength());
			naccsDetailType.setForwarderType(customerVO.getForwarderType());
			if(customerVO.getHandledCustomerExport() != null) {
				naccsDetailType.setHandledCustomerForExport(YESNOFlag.fromValue(customerVO.getHandledCustomerExport()));
			}
			if(customerVO.getHandledCustomerForwarder() != null) {
				naccsDetailType.setHandledCustomerForForwarder(YESNOFlag.fromValue(customerVO.getHandledCustomerForwarder()));
			}
			naccsDetailType.setNaccsAgentCode(customerVO.getNaacsbbAgentCode());
			naccsDetailType.setNaccsAirCargoAgentCode(customerVO.getNaccsAircargoAgentCode());
			naccsDetailType.setNaccsDeclarationCode(customerVO.getNaccsDeclarationCode());
			naccsDetailType.setNaccsInvoiceCode(customerVO.getNaccsInvoiceCode());
			customerType.setNaccsDetails(naccsDetailType);
			
			customerType.setRemarks(customerVO.getRemarks());
			customerType.setSITAAddress(customerVO.getSita());
			customerType.setStationCode(customerVO.getStationCode());
			if(customerVO.getStopCredit() != null) {
				customerType.setStopCreditFlag(YESNOFlag.fromValue(customerVO.getStopCredit()));
			}
		}
		responseType.setRequestId(Long.toString(getResponseId()));
		responseType.setCustomerDetails(customerType);
		response.setCustomerData(responseType);
		return response;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceImplementation.updateStatus
	 *	Added by 	:	A-4789 on 22-Nov-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerStatusUpdateRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault 
	 *	Return type	: 	CustomerStatusUpdateResponse
	 */
	public CustomerStatusUpdateResponse updateCustomerStatus(CustomerStatusUpdateRequest customerStatusUpdateRequest) 
	    throws ServiceFault, CustomerFault { 
	    	log.entering(CLASS_NAME, "updateStatus");
	    	CustomerStatusUpdateResponse response = null;
	    	Collection<CustomerVO> customers = null;
	        try {
	        	customers = populateCustomers(customerStatusUpdateRequest);
	        	if(customers == null) {
	        		throw new ServiceFault("No Details to udpate");
	        	}
	        	Collection<CustomerVO> returnCustomers = despatchRequest("updateCustomerStatus", customers);
	        	response = populateCustomerStatusUpdateResponse(returnCustomers);
	        } catch (Exception ex) {
	            throw new ServiceFault("Exception occured!!" + ex.getMessage());
	        }
	        return response;
	}
	
	/**
	 * 	Method		:	CustomerServiceImpl.populateCustomers
	 *	Added by 	:	A-4789 on 17-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerStatusUpdateRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	private Collection<CustomerVO> populateCustomers(
			CustomerStatusUpdateRequest customerStatusUpdateRequest) {
		Collection<CustomerVO> customers = new ArrayList<CustomerVO>();
		if(customerStatusUpdateRequest != null
				&& customerStatusUpdateRequest.getCustomerStatusUpdateData() != null
				&& customerStatusUpdateRequest.getCustomerStatusUpdateData().size() > 0) {
			for(CustomerStatusUpdateRequestType request : customerStatusUpdateRequest.getCustomerStatusUpdateData()) {
				for(CustomerStatusUpdateDetailType details : request.getCustomerStatusUpdateDetails()) {
					CustomerVO customerVO = new CustomerVO();
					customerVO.setCompanyCode(getCompanyCode());
					customerVO.setCustomerCode(details.getCustomerCode());
					customerVO.setStatus(details.getStatus().toString());
					customerVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
					customerVO.setLastUpdatedUser(customerStatusUpdateRequest.getMessageHeader().getSourceSystem());
					customers.add(customerVO);
				}
			}
			return customers;
		}
		return null;
	}

	 /**
	 * 	Method		:	CustomerServiceImpl.populateCustomerStatusUpdateResponse
	 *	Added by 	:	A-4789 on 19-Oct-2012
	 * 	Used for 	:	Populating CustomerStatusUpdateResponse from collection<CustomerVO>
	 *	Parameters	:	@param returnCustomers
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerStatusUpdateResponse
	 */
	private CustomerStatusUpdateResponse populateCustomerStatusUpdateResponse(
			Collection<CustomerVO> returnCustomers) {
		CustomerStatusUpdateResponse response = new CustomerStatusUpdateResponse();
		
		response.setMessageHeader(getMessageHeader());
		
		if(returnCustomers != null
				&& returnCustomers.size() > 0) {
			for(CustomerVO customerVO : returnCustomers) {
				CustomerStatusUpdateResponseType responseType = new CustomerStatusUpdateResponseType();
				CustomerStatusUpdateResponseDetailType responseDetailType = new CustomerStatusUpdateResponseDetailType();
				Collection<CustomerErrorVO> customerErrors = customerVO.getCustomerErrors();
				
				responseDetailType.setCustomerCode(customerVO.getCustomerCode());
				responseDetailType.setCustomerStatus(CustomerStatusType.fromValue(customerVO.getStatus()));
				responseDetailType.setSaveStatus(SaveStatusType.SUCCESS);
				
				if(customerErrors != null 
						&& customerErrors.size() > 0) {
					for(CustomerErrorVO customerErrorVO : customerErrors) {
						ErrorDetailType error = new ErrorDetailType();
						error.setErrorCode(customerErrorVO.getErrorCode());
						error.setErrorDesc(customerErrorVO.getErrorDescription());
						
						responseDetailType.setSaveStatus(SaveStatusType.FAILURE);
						responseDetailType.getErrorDetails().add(error);
					}
				}
				responseType.setCustomerStatusUpdateResponseDetails(responseDetailType);
				responseType.setResponseID(Long.toString(getResponseId()));
				response.getCustomerStatusUpdateResponseData().add(responseType);
			}
		}
		return response;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceImplementation.saveCertificateDetails
	 *	Added by 	:	A-4789 on 22-Nov-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerCertificateSaveRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault 
	 *	Return type	: 	CustomerCertificateSaveResponse
	 */
    public CustomerCertificateSaveResponse saveCertificateDetails(CustomerCertificateSaveRequest customerCertificateSaveRequest) 
    throws ServiceFault, CustomerFault { 
        log.entering(CLASS_NAME, "saveCertificate");
        Collection<CustomerCertificateVO> customerCertificates = populateCertificateDetails(customerCertificateSaveRequest);
        CustomerCertificateSaveResponse response = null;
        try {
        	if(customerCertificates == null) {
        		throw new ServiceFault("No customer certificates to save!");
        	}
            Collection<CustomerCertificateVO> returnCertificates = 
            	despatchRequest("saveCustomerCertificateDetails", customerCertificates);
            response = populateCustomerCertificateSaveResponse(returnCertificates);
        } catch (Exception ex) {
        	throw new ServiceFault("Exception occured!!" + ex.getMessage());
        }
        log.exiting(CLASS_NAME, "saveCertificate");
        return response;
    }
	
	/**
	 * 	Method		:	CustomerServiceImpl.populateCertificateDetails
	 *	Added by 	:	A-4789 on 16-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerCertificateSaveRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CustomerCertificateVO>
	 */
	private Collection<CustomerCertificateVO> populateCertificateDetails(
			CustomerCertificateSaveRequest customerCertificateSaveRequest) {
		Collection<CustomerCertificateVO> customerCertificates = null;
		
		if(customerCertificateSaveRequest != null
				&& customerCertificateSaveRequest.getCertificateData() != null
				&& customerCertificateSaveRequest.getCertificateData().size() > 0) {
			customerCertificates = new ArrayList<CustomerCertificateVO>();
			for(CustomerCertificateSaveRequestType request : customerCertificateSaveRequest.getCertificateData()) {
				CustomerCertificateVO customerCertificateVO = new CustomerCertificateVO();
				CustomerCertificateDetailsType certificateType = request.getCertificateDetails();
				
				if(certificateType.getValidityFromDate() != null) {
					LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
					startDate.setDate(certificateType.getValidityFromDate());
					customerCertificateVO.setValidityStartDate(startDate);
				}
				
				if(certificateType.getValidityToDate() != null) {
					LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
					endDate.setDate(certificateType.getValidityToDate());
					customerCertificateVO.setValidityEndDate(endDate);
				}
				
				customerCertificateVO.setCompanyCode(getCompanyCode());
				customerCertificateVO.setCustomerCode(certificateType.getCustomerCode());
				customerCertificateVO.setCertificateCode(certificateType.getCertificateType());
				customerCertificateVO.setSource(CustomerVO.SOURCE_INTERFACE);
				customerCertificateVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
				customerCertificateVO.setLastUpdatedUser(customerCertificateSaveRequest.getMessageHeader().getSourceSystem());
				customerCertificates.add(customerCertificateVO);
			}
		}
		return customerCertificates;
	}
	
    /**
	 * 	Method		:	CustomerServiceImpl.populateCustomerCertificateSaveResponse
	 *	Added by 	:	A-4789 on 19-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param returnCertificates
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerCertificateSaveResponse
	 */
	private CustomerCertificateSaveResponse populateCustomerCertificateSaveResponse(
			Collection<CustomerCertificateVO> returnCertificates) {
		CustomerCertificateSaveResponse response = new CustomerCertificateSaveResponse();
		
		response.setMessageHeader(getMessageHeader());
		if(returnCertificates != null
				&& returnCertificates.size() > 0) {
			for(CustomerCertificateVO certificateVO : returnCertificates) {
				CustomerCertificateResponseType responseType = new CustomerCertificateResponseType();
				CustomerCertificateResponseDetailType responseDetailType = new CustomerCertificateResponseDetailType();
				Collection<CustomerErrorVO> customerErrors = certificateVO.getCustomerErrors();
				
				responseDetailType.setCustomerCode(certificateVO.getCustomerCode());
				responseDetailType.setCertificateType(certificateVO.getCertificateCode());
				responseDetailType.setSaveStatus(SaveStatusType.SUCCESS);
				
				if(customerErrors != null 
						&& customerErrors.size() > 0) {
					for(CustomerErrorVO customerErrorVO : customerErrors) {
						ErrorDetailType error = new ErrorDetailType();
						error.setErrorCode(customerErrorVO.getErrorCode());
						error.setErrorDesc(customerErrorVO.getErrorDescription());
						
						responseDetailType.setSaveStatus(SaveStatusType.FAILURE);
						responseDetailType.getErrorDetails().add(error);
					}
				}
				responseType.setCustomerCertificateResponseDetails(responseDetailType);
				responseType.setRequestId(Long.toString(getResponseId()));
				response.getCustomerCertificateResponseData().add(responseType);
			}
		}
		return response;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceImplementation.saveCustomerDetails
	 *	Added by 	:	A-4789 on 22-Nov-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerSaveRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault 
	 *	Return type	: 	CustomerSaveResponse
	 */
	public CustomerSaveResponse saveCustomerDetails(CustomerSaveRequest customerSaveRequest) throws ServiceFault, CustomerFault { 
    	log.entering(CLASS_NAME, "saveCustomer");
    	Collection<CustomerVO> customers = null;
        try {
        	String isValid = validateConditionalMandatory(customerSaveRequest);
        	if(isValid != null) {
				throw new ServiceFault(isValid);
			}
        	customers = populateCustomer(customerSaveRequest);
        	if(customers == null) {
        		throw new ServiceFault("No customers to save!");
        	}
        	Collection<CustomerVO> returnCustomers = despatchRequest("saveCustomerDetails", customers);
            return populateCustomerSaveResponse(returnCustomers);
        } catch (Exception ex) {
        	throw new ServiceFault("Exception occured!!" + ex.getMessage());
        }
    }
	
	/**
	 * 	Method		:	CustomerServiceImpl.populateCustomer
	 *	Added by 	:	A-4789 on 17-Oct-2012
	 * 	Used for 	:	Populating the customerVO from the request type.
	 *	Parameters	:	@param customerSaveRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	private Collection<CustomerVO> populateCustomer(
			CustomerSaveRequest customerSaveRequest) {
		Collection<CustomerVO> customers = new ArrayList<CustomerVO>();
		
		if(customerSaveRequest != null 
				&& customerSaveRequest.getCustomerData() != null
				&& customerSaveRequest.getCustomerData().size() > 0) {
			for(CustomerSaveRequestType request : customerSaveRequest.getCustomerData()) {
				CustomerSaveType customerType = request.getCustomerDetails();
				CustomerVO customerVO = new CustomerVO();
				
				customerVO.setCompanyCode(getCompanyCode());
				customerVO.setOperationFlag(request.getOperationalFlag() == null ? 
						null : request.getOperationalFlag().value());
				customerVO.setAccountNumber(customerType.getAccountNumber());
				
				//Address of the customer
				AddressDetailType addressType = customerType.getAddressDetails();
				if(addressType != null) {
					customerVO.setAddress1(addressType.getAddressOne());
					customerVO.setAddress2(addressType.getAddressTwo());
					customerVO.setCity(addressType.getCity());
					customerVO.setCountry(addressType.getCountryCode());
					customerVO.setEmail(addressType.getEmailAddress());
					customerVO.setFax(addressType.getFaxNumber());
					addressType.getLocation(); // what to do for this?
					customerVO.setMobile(addressType.getMobilenumber());
					customerVO.setTelephone(addressType.getPhonenumber());
					customerVO.setState(addressType.getState());
					customerVO.setArea(addressType.getStreet()); // is this right?
					customerVO.setZipCode(addressType.getZipCode());
				}
				
				// When the custyp of the customer is AG, it needs to populated in the
				// Shared Agent tables also.
				// Temporarily added the fields as attributes VO.
				// Manu to get back as to whether the fields shud be added as a VO to the customerVO / as attributes to the parent VO
				AgentDetailType agent = customerType.getAgentDetails();
				if(agent != null) {
					if(agent.getValidFromDate() != null) {
						LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
						startDate.setDate(agent.getValidFromDate());
						customerVO.setAgentValidFromDate(startDate);
					}
					if(agent.getValidToDate() != null) {
						LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
						endDate.setDate(agent.getValidToDate());
						customerVO.setAgentValidToDate(endDate);
					}
					
					customerVO.setAgentCountryCode(agent.getCountryCode());
					customerVO.setAgentCurrencyCode(agent.getCurrencyCode());
					customerVO.setAgentExportFlag(agent.getExportFlag() == null ? 
							null : agent.getExportFlag().value());
					customerVO.setAgentIATACode(agent.getIATACode());
					customerVO.setAgentImportFlag(agent.getImportFlag() == null ? 
							null : agent.getImportFlag().value());
					customerVO.setAgentOwnAirlineCode(agent.getOwnAirlineCode());
					customerVO.setAgentSalesFlag(agent.getSalesFlag() == null ? 
							null : agent.getSalesFlag().value());
				}
				
				customerVO.setAirportCode(customerType.getAirportCode());
				
				// Set the billing details.
				BillingDetailType billingDetailType = customerType.getBillingDetails();
				CustomerBillingDetailsVO customerBillingDetailsVO = new CustomerBillingDetailsVO();
				if(billingDetailType != null) {
					customerBillingDetailsVO.setCityCode(billingDetailType.getBillingAddressDetail().getCity());
					customerBillingDetailsVO.setCountry(billingDetailType.getBillingAddressDetail().getCountryCode());
					customerBillingDetailsVO.setEmail(billingDetailType.getBillingAddressDetail().getEmailAddress());
					customerBillingDetailsVO.setFax(billingDetailType.getBillingAddressDetail().getFaxNumber());
					customerBillingDetailsVO.setLocation(billingDetailType.getBillingAddressDetail().getLocation());
					customerBillingDetailsVO.setState(billingDetailType.getBillingAddressDetail().getState());
					customerBillingDetailsVO.setStreet(billingDetailType.getBillingAddressDetail().getStreet());
					customerBillingDetailsVO.setTelephone(billingDetailType.getBillingAddressDetail().getPhonenumber());
					customerBillingDetailsVO.setZipcode(billingDetailType.getBillingAddressDetail().getZipCode());
					customerVO.setBillingCode(billingDetailType.getBillingCurrencyCode());
				}
				customerVO.setCustomerBillingDetailsVO(customerBillingDetailsVO);
				
				customerVO.setBillingPeriod(customerType.getBillingPeriod() == null ? 
						null : customerType.getBillingPeriod().value()); // Either one needs to be removed.
				
				// Sets the customer certificate details
				List<CertificateDetailsType> customerCertificateDetailsType = customerType.getCertificateDetails();
				Collection<CustomerCertificateVO> customerCertificates = new ArrayList<CustomerCertificateVO>();
				if(customerCertificateDetailsType != null
						&& customerCertificateDetailsType.size() > 0) {
					
					for(CertificateDetailsType certificate : customerCertificateDetailsType) {
						CustomerCertificateVO customerCertificateVO = new CustomerCertificateVO();
						
						if(certificate.getValidityFromDate() != null) {
							LocalDate startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
							startDate.setDate(certificate.getValidityFromDate());
							customerCertificateVO.setValidityStartDate(startDate);
						}
						
						if(certificate.getValidityToDate() != null) {
							LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
							endDate.setDate(certificate.getValidityToDate());
							customerCertificateVO.setValidityEndDate(endDate);
						}
						
						customerCertificateVO.setCustomerCode(customerVO.getCustomerCode());
						customerCertificateVO.setCertificateCode(certificate.getCertificateType());
						
						customerCertificates.add(customerCertificateVO);
					}
					customerVO.setCustomerCertificateDetails(customerCertificates);
				}
				
				// Identifies clearing agents at the delivery side and forwarders at the export side.
				List<CollectionAgentDetailType> collectionAgentDetails = customerType.getCollectionagentDetails();
				Collection<CustomerAgentVO> customerAgents = new ArrayList<CustomerAgentVO>();
				if(collectionAgentDetails != null 
						&& collectionAgentDetails.size() > 0) {
					for(CollectionAgentDetailType agentDetail : collectionAgentDetails) {
						CustomerAgentVO customerAgentVO = new CustomerAgentVO();
						customerAgentVO.setCompanyCode(getCompanyCode());
						customerAgentVO.setCustomerCode(customerType.getCustomerCode());
						customerAgentVO.setAgentCode(agentDetail.getAgentCode());
						customerAgentVO.setExportFlag(agentDetail.getExportFlag() == null ? 
								null : agentDetail.getExportFlag().value());
						customerAgentVO.setImportFlag(agentDetail.getImportFlag() == null ? 
								null : agentDetail.getImportFlag().value());
						customerAgentVO.setStationCode(agentDetail.getStationCode());
						customerAgentVO.setScc(agentDetail.getHandlingCode());
						customerAgents.add(customerAgentVO);
					}
					customerVO.setCustomerAgentVOs(customerAgents);
				}
				
				// Contact Details
				List<ContactDetailType> contactDetailTypes = customerType.getContactDetails();
				Collection<CustomerContactVO> customerContacts = new ArrayList<CustomerContactVO>();
				if(contactDetailTypes != null
						&& contactDetailTypes.size() > 0) {
					for(ContactDetailType contactDetailType : contactDetailTypes) {
						CustomerContactVO customerContactVO = new CustomerContactVO();
						
						customerContactVO.setCustomerDesignation(contactDetailType.getDesignation());
						customerContactVO.setFax(contactDetailType.getFaxNumber());
						customerContactVO.setMobile(contactDetailType.getMobilenumber());
						customerContactVO.setTelephone(contactDetailType.getPhonenumber());
						customerContactVO.setContactCustomerCode(contactDetailType.getFirstName());
						customerContactVO.setCustomerName(contactDetailType.getLastName());
						customerContactVO.setPrimaryUserFlag(contactDetailType.getPrimaryContact() == null ? 
								null : contactDetailType.getPrimaryContact().value());
						customerContactVO.setRemarks(contactDetailType.getRemarks());
						customerContactVO.setSiteAddress(contactDetailType.getSitaAddress());
						customerContactVO.setEmailAddress(contactDetailType.getEmailAddress());
						customerContacts.add(customerContactVO);
					}
					customerVO.setCustomerContactVOs(customerContacts);
				}
				
				customerVO.setCustomerCode(customerType.getCustomerCode());
				customerVO.setEoriNo(customerType.getCustomerEORINumber());
				
				if(customerType.getCustomerEstablishedDate() != null) {
					LocalDate establishedDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
					establishedDate.setDate(customerType.getCustomerEstablishedDate());
					customerVO.setEstablishedDate(establishedDate);
				}
				
				customerVO.setCustomerGroup(customerType.getCustomerGroup());
				customerVO.setLavNumber(customerType.getCustomermiscDetails() != null ? 
						customerType.getCustomermiscDetails().getLavNumber() : null);
				customerVO.setCustomerName(customerType.getCustomerName());
				customerVO.setCustomerShortCode(customerType.getCustomerShortCode());
				customerVO.setStatus(customerType.getCustomerStatus() == null ? 
						null : customerType.getCustomerStatus().value());
				customerVO.setCustomerType(customerType.getCustomerType());
				customerVO.setDefaultNotifyMode(customerType.getDefaultNotificationMode() == null ? 
						null : customerType.getDefaultNotificationMode().value());
				customerVO.setGlobalCustomerFlag(customerType.getGlobalCustomer() == null ? 
						null : customerType.getGlobalCustomer().value());
				
				// IAC Details set to the customerVO.
				IACDetailType iacDetailsType = customerType.getIacDetails();
				if(iacDetailsType != null) {
					IACDetailsVO iacDetailsVO = new IACDetailsVO();
					if(iacDetailsType.getCustomerIACSSPExpiryDate() != null) {
						LocalDate apiacsspExpiryDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
						apiacsspExpiryDate.setDate(iacDetailsType.getCustomerIACSSPExpiryDate());
						iacDetailsVO.setApiacsspExpiryDate(apiacsspExpiryDate);
					}
					
					if(iacDetailsType.getCustomerIACexpirydate() != null) {
						LocalDate iacExpiryDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
						iacExpiryDate.setDate(iacDetailsType.getCustomerIACexpirydate());
						iacDetailsVO.setIacExpiryDate(iacExpiryDate);
					}
					
					iacDetailsVO.setApiacsspNumber(iacDetailsType.getCustomerIACsspNumber());
					iacDetailsVO.setIacNumber(iacDetailsType.getCustomerIACNumber());
					
					customerVO.setIacDetailsVO(iacDetailsVO);
				}
				
				customerVO.setKnownShipper(customerType.getKnownShipper() == null ? 
						null : customerType.getKnownShipper().value());
				
				// NACCS Details set to the customerVO.
				NaccsDetailType naccsDetailType = customerType.getNaccsDetails();
				if(naccsDetailType != null) {
					customerVO.setBillPeriod(naccsDetailType.getBillingPeriodCode());
					customerVO.setBranch(naccsDetailType.getBranchCode());
					customerVO.setDefaultHawbLength(naccsDetailType.getDefaultHAWBLength());
					customerVO.setForwarderType(naccsDetailType.getForwarderType());
					customerVO.setHandledCustomerImport(naccsDetailType.getHandledCustomerForImport() == null ? 
							null : naccsDetailType.getHandledCustomerForImport().value());
					customerVO.setHandledCustomerExport(naccsDetailType.getHandledCustomerForExport() == null ? 
							null : naccsDetailType.getHandledCustomerForExport().value());
					customerVO.setHandledCustomerForwarder(naccsDetailType.getHandledCustomerForForwarder() == null ? 
							null : naccsDetailType.getHandledCustomerForForwarder().value());
					customerVO.setNaacsbbAgentCode(naccsDetailType.getNaccsAgentCode());
					customerVO.setNaccsAircargoAgentCode(naccsDetailType.getNaccsAirCargoAgentCode());
					customerVO.setNaccsDeclarationCode(naccsDetailType.getNaccsDeclarationCode());
					customerVO.setNaccsInvoiceCode(naccsDetailType.getNaccsInvoiceCode());
				}
				
				customerVO.setRemarks(customerType.getRemarks()); // Either one needs to be removed.
				customerVO.setRemark(customerType.getRemarks());// Either one needs to be removed.
				
				customerVO.setSita(customerType.getSITAAddress());
				customerVO.setStationCode(customerType.getStationCode());
				customerVO.setStopCredit(customerType.getStopCreditFlag() == null ? 
						null : customerType.getStopCreditFlag().value());
				
				customerVO.setSource(CustomerVO.SOURCE_INTERFACE);
				customerVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
				customerVO.setLastUpdatedUser(customerSaveRequest.getMessageHeader().getSourceSystem());
				
				// Add the customerVO to the collection
				customers.add(customerVO);
			}
			return customers;
		}
		// If there are no customers, it would return null
		return null;
	}

	/**
	 * 
	 * 	Method		:	CustomerServiceImpl.validateConditionalMandatory
	 *	Added by 	:	A-4789 on 21-Nov-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerSaveRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String validateConditionalMandatory(CustomerSaveRequest customerSaveRequest) {
		String isValid = null;
		if(customerSaveRequest != null 
				&& customerSaveRequest.getCustomerData() != null
				&& customerSaveRequest.getCustomerData().size() > 0) {
			for(CustomerSaveRequestType request : customerSaveRequest.getCustomerData()) {
				CustomerSaveType customerType = request.getCustomerDetails();
				
				if(customerType.getAirportCode() == null &&
						customerType.getStationCode() == null) {
					isValid = "Either Airport Code or Station Code is mandatory";
				}
				
				if(CustomerVO.CUSTOMER_TYPE_AG.equals((customerType.getCustomerType()))) {
					AgentDetailType agentType = customerType.getAgentDetails();
					if(agentType == null) {
						isValid = "Agent Details is mandatory";
					} else {
						if(agentType.getExportFlag() == null 
								&& agentType.getImportFlag() == null 
								&& agentType.getSalesFlag() == null) {
							isValid = "Either of import/export/sales flag is mandatory";
						}
					}
				}
			}
		}
		return isValid;
	}

	/**
	 * 	Method		:	CustomerServiceImpl.populateCustomerSaveResponse
	 *	Added by 	:	A-4789 on 19-Oct-2012
	 * 	Used for 	:	Used for populating the customer save response from the customervo
	 *	Parameters	:	@param returnCustomers
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerSaveResponse
	 */
	private CustomerSaveResponse populateCustomerSaveResponse(
			Collection<CustomerVO> returnCustomers) {
		CustomerSaveResponse response = new CustomerSaveResponse();
		response.setMessageHeader(getMessageHeader());
		if(returnCustomers != null
				&& returnCustomers.size() > 0) {
			for(CustomerVO customerVO : returnCustomers) {
				CustomerSaveResponseType responseType = new CustomerSaveResponseType();
				CustomerSaveResponseDetailType responseDetailType = new CustomerSaveResponseDetailType();
				Collection<CustomerErrorVO> customerErrors = customerVO.getCustomerErrors();
				
				responseDetailType.setCustomerCode(customerVO.getCustomerCode());
				responseDetailType.setCustomerStatus(customerVO.getStatus());
				responseDetailType.setSaveStatus(SaveStatusType.SUCCESS);
				
				if(customerErrors != null 
						&& customerErrors.size() > 0) {
					for(CustomerErrorVO customerErrorVO : customerErrors) {
						ErrorDetailType error = new ErrorDetailType();
						error.setErrorCode(customerErrorVO.getErrorCode());
						error.setErrorDesc(customerErrorVO.getErrorDescription());
						
						responseDetailType.setSaveStatus(SaveStatusType.FAILURE);
						responseDetailType.getErrorDetails().add(error);
					}
				}
				responseType.setCustomerSaveResponseDetails(responseDetailType);
				responseType.setRequestId(Long.toString(getResponseId()));
				response.getCustomerSaveResponseData().add(responseType);
			}
		}
		return response;
	}

}
