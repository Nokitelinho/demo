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
package com.ibsplc.icargo.services.customermanagement.defaults.webservices.standard;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerBillingInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerReceivablesAgeingVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerStatusViewVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CCCollectorVO;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AccountingPointOfContactType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AdditionalContactType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AdditionalCustomerInfoType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AddressDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingBucketsDaysType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingBucketsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingReportRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingReportResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingReportResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgentDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AwbDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.BillingAddressDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.BillingDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.BillingPeriod;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CCCollectorDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CertificateDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CollectionAgentDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ContactDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ContactMode;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerAddressType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCreditDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerGuaranteeDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponseType;
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
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.GuaranteeReferenceType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.IACDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ListCustomerRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ListCustomerResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ListCustomerResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.MiscDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.NaccsDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.NotificationPreferenceType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.OperationalFlagType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.OutstandingInvoiceDetailsType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.OutstandingPaymentStatusType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.PoaParametersType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.SaveStatusType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.StatementOfAccountRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.StatementOfAccountResponseDetailType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.StatementOfAccountResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ToleranceLevelType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.YESNOFlag;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.AdditionalContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerBillingDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditReferenceVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerErrorVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscellaneousVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.IACDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.NotificationPreferenceVO;
import com.ibsplc.icargo.business.shared.defaults.types.standard.MessageHeaderType;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.crypto.EncryptionFailedException;
import com.ibsplc.icargo.framework.security.crypto.util.Base64Encoder;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

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
	//Modified by A-5169 as part of ICRD-28529
	private static final String DAT_FORMAT = "dd-MMM-yyyy"; 
	private static final String NO_RECORDS_FOUND_ERROR = "shared.customer.noactivecustomerexists";
	private static final String NO_RECORDS_FOUND_ERROR_DESC = "No Active customer exist";

	private static final String DATE_FORMAT_MILLISEC = "dd-MMM-yyyy HH:mm:ss.SSS";
	
	private static final String CUSTOMERID_AUTOGENERATION = "customermanagement.defaults.customerIDGenerationRequired";
	
	private static final String STATUS_SUCCESS = "S";
    private static final String STATUS_FAILURE = "F";
	private static final String ONE_TO_FIFTEEN = "0-15";
	private static final String SIXTEEN_TO_THIRTY = "16-30";
	private static final String THIRTYONE_TO_FOURTYFIVE = "31-45";
	private static final String FOURTYSIX_TO_SIXTY = "46-60";
	private static final String SIXTYONE_TO_NINETY = "61-90";
	private static final String NINETYONE_TO_ONETWENTY = "91-120";
	private static final String ABOVE_ONETWENTY = ">120";
	private static final String UNPAID = "UNPAID";
	private static final String SHORTPAID = "SHORTPAID";
	private static final String OVERPAID = "OVERPAID";
	private static final String DEFAULT_GUARANTEE_TYPE= "shared.defaults.creditmaster.defaultguranteetype";
	
	public static final String DUPLICATE_USER_ERROR = "admin.user.duplicateuser";
	public static final String DUPLICATE_USER_ERRORDESC = "Duplicate User Exists. Cannot proceed with customer save";	
	//POA 
	private static final String SINGLE_POA="Single POA";
	private static final String GENPOA="General POA";
	private static final String SPLPOA="Special POA";
	private static final String SOURCE_POA = "MNGPOA";
	private static final String POA_PORTAL ="POAPORTAL";
	private static final String LIST_CUSTOMER ="listCustomer";
	private static final String VLD_END_DATE = "31-Dec-2099";
	
	
	@Override
    public boolean isProtected() {
    	return true;
	}
	
	private MessageHeaderType getMessageHeader() {
		MessageHeaderType header = new MessageHeaderType();
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
    	CustomerFilterVO customerFilter = new CustomerFilterVO();
    	//Added as part of ICRD-230504 Start. It should be removed as par to 29JAN Release.
    	String sourceSystem= customerRequest.getMessageHeader().getSourceSystem();
    	//Added as part of ICRD-230504 End. It should be removed as par to 29JAN Release.
        try {
        	customerFilter =populateCustomerFilterVO(customerRequest);
        	if(customerFilter!=null && (customerFilter.getCustomerCode()==null && customerFilter.getCustomerShortCode()==null )){
        		throw new ServiceFault("Either customer Code or Customer Short Code is mandatory.");
        	}
        	customerFilters.add(customerFilter);
        	if(customerFilters.isEmpty()) {
        		throw new ServiceFault("Request data unavailable.");
        	}
        	customerVOs = despatchRequest("getCustomerDetails", customerFilters);
        	if(customerVOs.isEmpty()) {
        		throw new ServiceFault("Response data unavailable.");
        	}
        	response = populateCustomerResponse(customerVOs.iterator().next(),sourceSystem);
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
			customerFilterVO.setHoldingCompany(customerRequest.getCustomerRequestData().getCustomerRequestDetails().getHoldingCompany());
			customerFilterVO.setHoldingCompanyGroup(customerRequest.getCustomerRequestData().getCustomerRequestDetails().getHoldingCompanyGroup());
			
			if(POA_PORTAL.equals(customerRequest.getMessageHeader().getSourceSystem())){
				customerFilterVO.setSource(SOURCE_POA);
			}
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
	private CustomerResponse populateCustomerResponse(CustomerVO customerVO,String sourceSystem) {
		CustomerResponse response = new CustomerResponse();
		CustomerResponseType responseType = new CustomerResponseType();

		response.setMessageHeader(getMessageHeader());
		Collection<CustomerErrorVO> customerErrors = customerVO
				.getCustomerErrors();
		if (customerErrors != null && customerErrors.size() > 0) {
			for (CustomerErrorVO customerErrorVO : customerErrors) {
				ErrorDetailType error = new ErrorDetailType();
				error.setErrorCode(customerErrorVO.getErrorCode());
				error.setErrorDesc(customerErrorVO.getErrorDescription());
				// modified by a-4810 for icrd-33029
				// customerType.getErrorDetails().add(error);
				responseType.setStatus(SaveStatusType.FAILURE);   
				responseType.getErrorDetails().add(error);
			}
		} else {
			CustomerType customerType = new CustomerType();
			customerType.setCustomerCode(customerVO.getCustomerCode());
			customerType.setCustomerShortCode(customerVO.getCustomerShortCode());
			customerType.setInternalAccountHolder(customerVO.getInternalAccountHolder());

			customerType.setAccountNumber(customerVO.getAccountNumber());
			customerType.setClearingAgent(customerVO.isClearingAgentFlag() ? YESNOFlag
					.fromValue("Y") : YESNOFlag.fromValue("N"));// Added BY A-8374 For ICRD-247693

			// Sets the address details
			AddressDetailType addressType = new AddressDetailType();
			addressType.setAddressOne(customerVO.getAddress1());
			addressType.setAddressTwo(customerVO.getAddress2());
			addressType.setCity(customerVO.getCity());
			addressType.setCountryCode(customerVO.getCountry());
			addressType.setEmailAddress(customerVO.getEmail());
			addressType.setFaxNumber(customerVO.getFax());
			addressType.setMobileNumber(customerVO.getMobile());
			addressType.setPhoneNumber(customerVO.getTelephone());
			addressType.setState(customerVO.getState());
			addressType.setZipCode(customerVO.getZipCode());
			addressType.setState(customerVO.getState());
			customerType.setAddressDetails(addressType);

			// If the customer type is AG then agent details needs to be set.
			AgentDetailType agentType = null;
			if (CustomerVO.CUSTOMER_TYPE_AG
					.equals(customerVO.getCustomerType())) {
				agentType = new AgentDetailType();
				// Added by A-5169 for ICRD-18283 on 27FEB13 starts
				if (customerVO.getAgentVOs() != null
						&& customerVO.getAgentVOs().size() > 0) {

					AgentVO agentVO = customerVO.getAgentVOs().iterator()
							.next();
					// commented by a-4810 for 33029
					agentType.setIATACode(agentVO.getIataAgentCode());
					agentType.setAgentType(agentVO.getAgentType());
					agentType.setExportCass(agentVO.getCassIdentifier());
					agentType.setHoldingCompany(agentVO.getHoldingCompany());
					agentType.setNormalCommission((int) agentVO
							.getNormCommPrc());
					agentType.setFixedCommission((int) agentVO.getFixedValue());
					agentType
							.setOwnSales(agentVO.getOwnSalesFlag() == null ? null
									: YESNOFlag.fromValue(agentVO
											.getOwnSalesFlag()));

					agentType.setSettlementCurrency(customerVO
							.getSettlementCurrencyCodes());
					agentType
					.setSalesReportingAgent(agentVO.isSalesReporting() ? YESNOFlag
							.fromValue("Y") : YESNOFlag.fromValue("N"));
					agentType.setProformaInvoice(agentVO
							.isInvoiceGenerationFlag() ? YESNOFlag
							.fromValue("Y") : YESNOFlag.fromValue("N"));

					agentType.setRemarks(agentVO.getRemarks());
					
				}
			}
			customerType.setAgentDetails(agentType);

			customerType.setAirportCode(customerVO.getAirportCode());

			// Billing Details type
			BillingDetailType billingDetailType = new BillingDetailType();
			CustomerBillingDetailsVO customerBillingDetailsVO = customerVO
					.getCustomerBillingDetailsVO();
			if (customerBillingDetailsVO != null) {
			if(customerBillingDetailsVO.getCityCode()!=null || customerBillingDetailsVO.getCountry()!=null) {
			   	BillingAddressDetailType billingAddressType = new BillingAddressDetailType();

				// addressone, two not needed?
			   	billingAddressType.setAddressOne(customerBillingDetailsVO.getLocation());
				billingAddressType.setAddressTwo(customerBillingDetailsVO.getStreet());

				billingAddressType.setEmailAddress(customerBillingDetailsVO
						.getEmail());
				//Added as part of ICRD-228463 starts
				if(customerBillingDetailsVO.getEmailOne()!=null
						&& customerBillingDetailsVO.getEmailOne().trim().length()>0){
					billingAddressType.setEmailAddressOne(customerBillingDetailsVO.getEmailOne());
				}
				if(customerBillingDetailsVO.getEmailTwo()!=null
						&& customerBillingDetailsVO.getEmailTwo().trim().length()>0){
					billingAddressType.setEmailAddressTwo(customerBillingDetailsVO.getEmailTwo());
				}
				//Added as part of ICRD-228463 ends
				
				billingAddressType.setPhonenumber(customerBillingDetailsVO
						.getTelephone());
				billingAddressType.setFaxNumber(customerBillingDetailsVO
						.getFax());
				billingAddressType.setZipCode(customerBillingDetailsVO
						.getZipcode());
				billingAddressType.setCity(customerBillingDetailsVO
						.getCityCode());
				billingAddressType.setCountryCode(customerBillingDetailsVO
						.getCountry());
				billingAddressType
						.setState(customerBillingDetailsVO.getState());

				billingDetailType.setBillingAddressDetail(billingAddressType);
			  }
				billingDetailType.setBillingCurrencyCode(customerVO
						.getBillingCode());
				// is this correct?
				if (customerVO.getBillingPeriod() != null) {
					billingDetailType.setBillingPeriod(BillingPeriod
							.fromValue(customerVO.getBillingPeriod()));
				}
				billingDetailType.setSalesID(customerVO.getSalesId());
				billingDetailType.setToleranceLevel(customerVO
						.isEnduranceFlag() ? ToleranceLevelType.fromValue("Y")
						: ToleranceLevelType.fromValue("N"));
				billingDetailType.setTolerancePercentage((int) customerVO
						.getEndurancePercentage());
				billingDetailType.setToleranceValue((long) customerVO
						.getEnduranceValue());
				billingDetailType.setMaximumValue(new BigDecimal(customerVO
						.getEnduranceMaxValue()));
				customerType.setBillingDetails(billingDetailType);
			}
			if(customerVO.getDueDateBasis()!=null){
				customerType.setDueDateBasis(customerVO.getDueDateBasis());
			}
			customerType.setVatRegNumber(customerVO.getVatRegNumber());
			customerType
					.setInvoiceToCustomer(customerVO.getInvoiceToCustomer() == null ? null
							: (YESNOFlag.fromValue(customerVO
									.getInvoiceToCustomer())));
			Collection<CustomerCertificateVO> customerCertificates = customerVO
					.getCustomerCertificateDetails();
			if (customerCertificates != null && customerCertificates.size() > 0) {
				for (CustomerCertificateVO certificate : customerCertificates) {
					CertificateDetailsType certificateType = new CertificateDetailsType();
					certificateType.setCertificateType(certificate
							.getCertificateType());
					if(certificate.getCertificateNumber()!=null && certificate.getCertificateNumber().trim().length()>0) { 
						certificateType.setCertificateNumber(certificate.getCertificateNumber());
					}
					if (certificate.getValidityStartDate() != null) {
						String validfrm = TimeConvertor
								.toStringFormat(certificate
										.getValidityStartDate().toCalendar(),
										DAT_FORMAT);

						certificateType.setValidityFromDate(validfrm);
					}

					if (certificate.getValidityEndDate() != null) {
						String validtill = TimeConvertor.toStringFormat(
								certificate.getValidityEndDate().toCalendar(),
								DAT_FORMAT);

						certificateType.setValidityToDate(validtill);
					}

					customerType.getCertificateDetails().add(certificateType);
				}
			}

			// Added by A-4810 for icrd-33029

			Collection<CCCollectorVO> cCCollectorVOs = customerVO
					.getCcCollectorVOs();
			if (cCCollectorVOs != null && cCCollectorVOs.size() > 0
					&& "CC".equals(customerVO.getCustomerType())) {
				for (CCCollectorVO cCCollectorVO : cCCollectorVOs) {
					CCCollectorDetailsType cCCollectorDetailsType = new CCCollectorDetailsType();
					cCCollectorDetailsType.setAirportCode(cCCollectorVO
							.getAirportCode());
					cCCollectorDetailsType.setAircraftType(cCCollectorVO
							.getAircraftTypeHandled());
					cCCollectorDetailsType.setDateOfExchange(cCCollectorVO
							.getDateOfExchange());

					cCCollectorDetailsType.setCassImport(cCCollectorVO
							.isCassBillingIndicator() ? YESNOFlag
							.fromValue("Y") : YESNOFlag.fromValue("N"));

					cCCollectorDetailsType
							.setBillThroughInterline(cCCollectorVO
									.isBillingThroughInterline() ? YESNOFlag
									.fromValue("Y") : YESNOFlag.fromValue("N"));

					cCCollectorDetailsType.setImportCassCode(cCCollectorVO
							.getCassCode());

					cCCollectorDetailsType.setAirlineCode(cCCollectorVO
							.getAirlineCode());
					cCCollectorDetailsType.setSettlementCurrency(customerVO
							.getSettlementCurrencyCodes());
					cCCollectorDetailsType.setRemarks(cCCollectorVO
							.getRemarks());
					//Added by A-7656 for ICRD-242148
					cCCollectorDetailsType.setCcfeeDueGHA(cCCollectorVO.isCCFeeDueGHA() ? 
							YESNOFlag.fromValue("Y") : YESNOFlag.fromValue("N"));
					customerType.setCcCollectorDetails(cCCollectorDetailsType);
				}

			}
			Collection<CustomerCreditVO> customerCreditVOs = customerVO
					.getCustomerCreditDetails();
			if (customerCreditVOs != null && customerCreditVOs.size() > 0) {
				for (CustomerCreditVO customerCreditVO : customerCreditVOs) {

					CustomerCreditDetailsType customerCreditDetailsType = new CustomerCreditDetailsType();
					customerCreditDetailsType.setCreditCurrency(customerCreditVO
							.getBaseCurrency());

					customerCreditDetailsType
							.setDepleteCreditLimiForAWBChargesFlag(customerCreditVO
									.isDepleteForAwbChgs() ? YESNOFlag
									.fromValue("Y") : YESNOFlag.fromValue("N"));

					customerCreditDetailsType
							.setDepleteNonCreditLimitForAWBChargesFlag(customerCreditVO
									.isDepleteForAwbChgs() ? YESNOFlag
									.fromValue("Y") : YESNOFlag.fromValue("N"));
					
					customerCreditDetailsType.setDepleteCreditLimitForImportAWBChargesFlag(customerCreditVO
									.isDepleteCreditLimitForImpCharge()? YESNOFlag
									.fromValue("Y") : YESNOFlag.fromValue("N"));
					
					customerCreditDetailsType
					.setDepleteCreditLimitForExportAWBChargesFlag(customerCreditVO
							.isDepleteCreditLimitForExpCharge() ? YESNOFlag
							.fromValue("Y") : YESNOFlag.fromValue("N"));
					
					customerCreditDetailsType.setOverrideFlag(customerCreditVO
							.isOverride() ? YESNOFlag.fromValue("Y")
							: YESNOFlag.fromValue("N"));

					customerCreditDetailsType
							.setAmountToBeReported((long) customerCreditVO
									.getAmtToBeReported().getAmount());
					customerCreditDetailsType
							.setPendingReportedAmount((long) customerCreditVO
									.getPendingReportedAmt().getAmount());
					CustomerGuaranteeDetailsType customerGuaranteeDetailsType = new CustomerGuaranteeDetailsType();

					Collection<CustomerCreditReferenceVO> agentCreditRefVOs = customerCreditVO
							.getCustomerCreditReferences();
					if(agentCreditRefVOs!=null && agentCreditRefVOs.size()>0) {
					for (CustomerCreditReferenceVO agentCreditReferenceVO : agentCreditRefVOs) {
						GuaranteeReferenceType referenceTyp = new GuaranteeReferenceType();
						referenceTyp.setGuaranteeAmount(BigDecimal
								.valueOf(agentCreditReferenceVO.getAmount()
										.getAmount()));
						// ICRD-101159_LakshmiN_06Mar2015 starts
						referenceTyp.setGuaranteeAmountAvailable(BigDecimal
								.valueOf(agentCreditReferenceVO.getAvailableGuaranteeAmount()
										.getAmount()));
						// ICRD-101159_LakshmiN_06Mar2015 ends
						referenceTyp
								.setGuaranteeReferenceCode(agentCreditReferenceVO
										.getReferenceCode());
						referenceTyp.setGuaranteeRemarks(agentCreditReferenceVO
								.getRemarks());
						if (agentCreditReferenceVO.getValidTill() != null) {
							String validtill = TimeConvertor.toStringFormat(
									agentCreditReferenceVO.getValidTill()
											.toCalendar(), DAT_FORMAT);
							referenceTyp.setGuaranteeValidityEndDate(validtill);
							
						}

						referenceTyp.setGuaranteeCategory(agentCreditReferenceVO.getCategory());
						
						//referenceTyps.add(referenceTyp);
						customerGuaranteeDetailsType.getGuaranteeReferenceType().add(referenceTyp);
					}
					
					/*customerGuaranteeDetailsType.getGuaranteeReferenceType()
					.addAll(referenceTyps);*/
					
					}
					
					customerCreditDetailsType
							.setCustomerGuaranteeDetails(customerGuaranteeDetailsType);
					customerType
							.setCustomerCreditDetails(customerCreditDetailsType);
				}

			}

			// Contact Detail Type set to customerType
			Collection<CustomerContactVO> contactVOs = customerVO
					.getCustomerContactVOs();
			if (contactVOs != null && contactVOs.size() > 0) {
				for (CustomerContactVO contactVO : contactVOs) {
					//Added as part of ICRD-230504 Start. It should be removed as par to 29JAN Release.
					if(!CustomerVO.SOURCE_PORTAL.equals(sourceSystem) && ("EFRT").equals(contactVO.getContactType()) ){
						continue;
					}
					//Added as part of ICRD-230504 end.It should be removed as par to 29JAN Release
					ContactDetailType contactDetailType = new ContactDetailType();
					// Added by A-5169 for ICRD-30075 starts
					contactDetailType.setEmailAddress(contactVO
							.getEmailAddress());
					// Added by A-5169 for ICRD-30075 ends
					contactDetailType.setDesignation(contactVO
							.getCustomerDesignation());
					contactDetailType.setFaxNumber(contactVO.getFax());
					contactDetailType.setFirstName(contactVO
							.getContactCustomerCode());
					contactDetailType.setLastName(contactVO.getCustomerName());
					contactDetailType.setMobileNumber(contactVO.getMobile());
					contactDetailType.setPhoneNumber(contactVO.getTelephone());
					contactDetailType.setPrimaryContact(contactVO
							.getPrimaryUserFlag() == null ? null : YESNOFlag
							.fromValue(contactVO.getPrimaryUserFlag()));

					contactDetailType.setContactRemarks(contactVO.getRemarks());
					contactDetailType
							.setSitaAddress(contactVO.getSiteAddress());
					//Added by A-5220 for ICRD-55852
					contactDetailType.setContactType(contactVO.getContactType());
					//Added for ICRD-159064
					contactDetailType.setNotificationLanguage(contactVO.getNotificationLanguageCode());
					if(contactVO.getNotificationPreferences() != null &&
							contactVO.getNotificationPreferences().size() > 0){
						for (NotificationPreferenceVO notificationPreferenceVO : contactVO.getNotificationPreferences()) {
								NotificationPreferenceType prefType = new NotificationPreferenceType();
								prefType.setEventCode(notificationPreferenceVO.getEventCode());
								prefType.setEventDescription(notificationPreferenceVO.getEventDescription());
								prefType.setEmailFlag(notificationPreferenceVO
										.getEmailFlag() == null ? null : YESNOFlag
												.fromValue(notificationPreferenceVO.getEmailFlag()));
								prefType.setMobileFlag(notificationPreferenceVO
										.getMobileFlag() == null ? null : YESNOFlag
												.fromValue(notificationPreferenceVO.getMobileFlag()));
								prefType.setFaxFlag(notificationPreferenceVO
										.getFaxFlag() == null ? null : YESNOFlag
												.fromValue(notificationPreferenceVO.getFaxFlag()));
								contactDetailType.getNotificationPreferences().add(prefType);
						}
					}
					if(contactVO.getAdditionalContacts() != null &&
							contactVO.getAdditionalContacts().size() > 0){
						for (AdditionalContactVO additionalContactVO : contactVO.getAdditionalContacts()) {
								AdditionalContactType additionalContactType = new AdditionalContactType();
								additionalContactType.setContactAddress(additionalContactVO.getContactAddress());
								additionalContactType.setContactMode(ContactMode
										.fromValue(additionalContactVO.getContactMode()));
								contactDetailType.getAdditionalContacts().add(additionalContactType);
						}
					}
					contactDetailType.setNotificationFormat(contactVO.getNotificationFormat());//CRQ ID:ICRD-143720 - A-5127 added					
					customerType.setNotifyShipmentType(contactVO.getNotifyShipmentType());
					customerType.getContactDetails().add(contactDetailType);
				}
			}
			customerType.setCountryCurrency(customerVO.getCountryCurrency());//CRQ ID:ICRD-162691 - A-5127 added
			//Added for ICRD-159064
			if(customerVO.getCustomerPreferences() != null &&
					customerVO.getCustomerPreferences().size() > 0){
				for (CustomerPreferenceVO customerPreferenceVO : customerVO.getCustomerPreferences()) {
					AdditionalCustomerInfoType additionalCustomerInfoType = new AdditionalCustomerInfoType();
					additionalCustomerInfoType.setParameterCode(customerPreferenceVO.getPreferenceCode());
					additionalCustomerInfoType.setParameterValue(customerPreferenceVO.getPreferenceValue());
					customerType.getAdditionalCustomerInformation().add(additionalCustomerInfoType);
				}
			}
			// Collection Agent Details
			Collection<CustomerAgentVO> collectionAgents = customerVO
					.getCustomerAgentVOs();
			if (collectionAgents != null && collectionAgents.size() > 0) {
				for (CustomerAgentVO customerAgentVO : collectionAgents) {
					CollectionAgentDetailType collectionAgentDetailType = new CollectionAgentDetailType();

					collectionAgentDetailType.setCustomerCode(customerAgentVO
							.getAgentCode());
					collectionAgentDetailType.setExportFlag(customerAgentVO
							.getExportFlag() == null ? null : YESNOFlag
							.fromValue(customerAgentVO.getExportFlag()));
					collectionAgentDetailType.setHandlingCode(customerAgentVO
							.getScc());
					collectionAgentDetailType.setSalesFlag(customerAgentVO
							.getSalesFlag() == null ? null : YESNOFlag
							.fromValue(customerAgentVO.getSalesFlag()));
					collectionAgentDetailType.setCarrierCode(customerAgentVO.getCarrier());
					collectionAgentDetailType.setRemarks(customerAgentVO.getRemarks());
					collectionAgentDetailType.setContractStartDate(customerAgentVO.getValidityStartDate().toDisplayFormat(true));
					collectionAgentDetailType.setContractEndDate(customerAgentVO.getValidityEndDate().toDisplayFormat(true));
					collectionAgentDetailType.setOriginCode(customerAgentVO.getOrigin());
					collectionAgentDetailType.setImportFlag(customerAgentVO
							.getImportFlag() == null ? null : YESNOFlag
							.fromValue(customerAgentVO.getImportFlag()));

					collectionAgentDetailType.setStationCode(customerAgentVO
							.getStationCode());
					collectionAgentDetailType.setBillToCustomerFlag(customerVO
							.getInvoiceToCustomer() == null ? null : YESNOFlag
							.fromValue(customerVO.getInvoiceToCustomer()));
					// Added by A-5169 starts for ICRD-30075
					customerType.getCollectionagentDetails().add(
							collectionAgentDetailType);
					// Added by A-5169 ends for ICRD-30075
				}
			}
			customerType.setCustomerEORINumber(customerVO.getEoriNo());
			// Modified by A-5169 for incorrect date format starts
			if (customerVO.getEstablishedDate() != null) {
				String establishedDate = TimeConvertor.toStringFormat(
						customerVO.getEstablishedDate().toCalendar(),
						DAT_FORMAT);
				log.log(Log.FINE,
						"establishedDate----------------------------->",
						establishedDate);
				customerType.setCustomerEstablishedDate(establishedDate);
			}
			if (customerVO.isSellingLocation()) {
				customerType.setSellingLocation(YESNOFlag.fromValue("Y"));
			} else {
				customerType.setSellingLocation(YESNOFlag.fromValue("N"));
			}
			if (customerVO.isControllingLocation()) {
				customerType.setControllingLocation(YESNOFlag.fromValue("Y"));
			} else {
				customerType.setControllingLocation(YESNOFlag.fromValue("N"));
			}
			customerType.setControllingCustomerCode(customerVO
					.getContyrollingAgentCode());
			customerType.setControllingCustomerName(customerVO
					.getControllingAgentName());
			customerType.setControllingBillToIndicator(customerVO
					.getBillingIndicator());
			//by A-7567 for ICRD-305684
			customerType.setCntLocBillingApplicableTo(customerVO.getCntLocBillingApplicableTo());
			customerType.setInvoiceClubbingFlag(customerVO
					.getInvoiceClubbingIndicator() == null ? null : YESNOFlag
					.fromValue(customerVO.getInvoiceClubbingIndicator()));
			if (customerVO.getValidFrom() != null) {
				String validFromDate = TimeConvertor.toStringFormat(customerVO
						.getValidFrom().toCalendar(), DAT_FORMAT);
				log.log(Log.FINE,
						"establishedDate----------------------------->",
						validFromDate);
				customerType.setValidFromDate(validFromDate);
			}
			if (customerVO.getValidTo() != null) {
				/*String validTo = TimeConvertor.toStringFormat(customerVO
						.getValidFrom().toCalendar(), DAT_FORMAT);*/
				String validTo = TimeConvertor.toStringFormat(customerVO
						.getValidTo().toCalendar(), DAT_FORMAT);//Modified by A-5233 for BUG ICRD-38184
				log.log(Log.FINE,
						"establishedDate----------------------------->",
						validTo);
				customerType.setValidToDate(validTo);
			}
			// Modified by A-5169 for incorrect date format ends
			customerType.setCustomerGroup(customerVO.getCustomerGroup());

			// Miscellaneous Detail Type set to customerType.

			CustomerMiscellaneousVO customerMiscellaneousVO = customerVO
					.getCustomerMiscDetails();
			if (customerMiscellaneousVO != null) {
				MiscDetailType miscDetailType = new MiscDetailType();
				miscDetailType
						.setAlfaIndicatorForCustoms(customerMiscellaneousVO
								.getAlfaIndicatorForCustoms());
				miscDetailType.setCustomsLocationNumber(customerMiscellaneousVO
						.getCustomsLocationNumber());
				miscDetailType.setLavNumber(customerMiscellaneousVO
						.getLavNumber());
				//changing
				customerType.setCustomermiscDetails(miscDetailType);
			}
			customerType.setCustomerName(customerVO.getCustomerName());
			if (customerVO.getStatus() != null) {
				customerType.setCustomerStatus(customerVO.getStatus());
			}
			customerType.setCustomerType(customerVO.getCustomerType());
			if (customerVO.getDefaultNotifyMode() != null) {
				customerType.setDefaultNotificationMode(DefaultNotificationMode
						.fromValue(customerVO.getDefaultNotifyMode()));
			}
			customerType
					.setGlobalCustomer(customerVO.getGlobalCustomerFlag() == null ? null
							: YESNOFlag.fromValue(customerVO
									.getGlobalCustomerFlag()));

			// IAC Details set to the customerType.
			IACDetailsVO iacDetailsVO = customerVO.getIacDetailsVO();
			if (iacDetailsVO != null) {
				IACDetailType iacDetailsType = new IACDetailType();
				if (iacDetailsVO.getIacExpiryDate() != null) {
					String acExpiryDate = TimeConvertor.toStringFormat(
							iacDetailsVO.getIacExpiryDate().toCalendar(),
							DAT_FORMAT);
					iacDetailsType.setCustomerIACexpirydate(acExpiryDate);
				}
				if (iacDetailsVO.getApiacsspExpiryDate() != null) {
					String acssExpiryDate = TimeConvertor.toStringFormat(
							iacDetailsVO.getApiacsspExpiryDate().toCalendar(),
							DAT_FORMAT);
					iacDetailsType.setCustomerIACSSPExpiryDate(acssExpiryDate);
				}
				// Modified by A-5169 for incorrect date format ends
				iacDetailsType.setCustomerIACsspNumber(iacDetailsVO
						.getApiacsspNumber());
				iacDetailsType
						.setCustomerIACNumber(iacDetailsVO.getIacNumber());
				customerType.setIacDetails(iacDetailsType);
			}

			customerType
					.setKnownShipper(customerVO.getKnownShipper() == null ? null
							: YESNOFlag.fromValue(customerVO.getKnownShipper()));

			// NACCS Detail Type
			NaccsDetailType naccsDetailType = new NaccsDetailType();
			//commenting for icrd-35865 by a-4810
			//naccsDetailType.setBillingPeriodCode(customerVO.getBillPeriod());
			naccsDetailType.setBranchCode(customerVO.getBranch());
			  
		  if(customerVO
					.getDefaultHawbLength()!=null && customerVO
							.getDefaultHawbLength().equals("0")){  
			  naccsDetailType.setDefaultHAWBLength(null);  
		  }else{
			naccsDetailType.setDefaultHAWBLength(customerVO
					.getDefaultHawbLength());
		  }
			naccsDetailType.setForwarderType(customerVO.getForwarderType());

			naccsDetailType.setHandledCustomerForExport(customerVO
					.getHandledCustomerExport() == null ? null : YESNOFlag
					.fromValue(customerVO.getHandledCustomerExport()));

			naccsDetailType.setHandledCustomerForForwarder(customerVO
					.getHandledCustomerForwarder() == null ? null : YESNOFlag
					.fromValue(customerVO.getHandledCustomerForwarder()));

			// Added by A-5169 for ICRD-30075 starts

			naccsDetailType.setHandledCustomerForImport(customerVO
					.getHandledCustomerImport() == null ? null : YESNOFlag
					.fromValue(customerVO.getHandledCustomerImport()));

			// Added by A-5169 for ICRD-30075 ends
			naccsDetailType.setNaccsAgentCode(customerVO.getNaacsbbAgentCode());
			naccsDetailType.setNaccsAirCargoAgentCode(customerVO
					.getNaccsAircargoAgentCode());
			naccsDetailType.setNaccsDeclarationCode(customerVO
					.getNaccsDeclarationCode());
			naccsDetailType.setNaccsInvoiceCode(customerVO
					.getNaccsInvoiceCode());
			customerType.setNaccsDetails(naccsDetailType);

			customerType.setRemarks(customerVO.getRemarks());
			customerType.setSITAAddress(customerVO.getSita());
			customerType.setStationCode(customerVO.getStationCode());
			customerType
					.setStopCreditFlag(customerVO.getStopCredit() == null ? null
							: YESNOFlag.fromValue(customerVO.getStopCredit()));
			//CRQ ID:117235 - A-5127 added
			customerType.setImportCass(customerVO.getCassImportIdentifier());
			customerType.setRecipientCode(customerVO.getRecipientCode());
			//End - CRQ ID:117235 - A-5127 added
			//Added for ICRD-159064
			if(customerVO.getLastUpdatedTime() != null){
				customerType.setLastUpdatedTime(customerVO.getLastUpdatedTime().toTimeStampFormat());
			}
			//Added as part of POA portal
			//clearing collectionagentdetails to pass data to portal
			if (POA_PORTAL.equals(sourceSystem)) {
				customerType.getCollectionagentDetails().clear();
				if ((customerVO.getCustomerAgentVOs() != null && !customerVO.getCustomerAgentVOs().isEmpty())) {
					for (CustomerAgentVO agentVO : customerVO.getCustomerAgentVOs()) {
						CollectionAgentDetailType collectionAgentDetailType = new CollectionAgentDetailType();
						PoaParametersType poaParametersType = new PoaParametersType();
						poaParametersType.setSinglePoaInfo(agentVO.getAwbNum());
						poaParametersType.setDestinationCode(agentVO.getDestination()!=null? agentVO.getDestination() :"STA");
						poaParametersType.setExcludedHandlingCodes(agentVO.getExcludedScc());
						poaParametersType.setIncludedHandlingCodes(agentVO.getScc());
						poaParametersType.setExcludedOriginCodes(agentVO.getExcludedOrigins());
						poaParametersType.setIncludedOriginCodes(agentVO.getIncludedOrigins());
						poaParametersType.setPoaType(agentVO.getPoaType());
						poaParametersType.setSequenceNumber(agentVO.getSequenceNumber());
						if (agentVO.getPoaCreationTime() != null) {
							String poaCreationDate = TimeConvertor.toStringFormat(
									agentVO.getPoaCreationTime().toCalendar(),
									"dd-MMM-yyyy HH:mm:ss");
							poaParametersType.setPoaCreationDate(poaCreationDate);
						}
						String validityStartDate = null;
						String validityEndDate = null;
						
						if (agentVO.getValidityStartDate() != null) {
							validityStartDate = TimeConvertor.toStringFormat(
									agentVO.getValidityStartDate().toCalendar(),
									DAT_FORMAT);
						}
						if (agentVO.getValidityEndDate() != null) {
							validityEndDate = TimeConvertor.toStringFormat(
									agentVO.getValidityEndDate().toCalendar(),
									DAT_FORMAT);	
						}
						collectionAgentDetailType.setContractStartDate(validityStartDate);
						collectionAgentDetailType.setContractEndDate(validityEndDate);
						collectionAgentDetailType.setPoaParameters(poaParametersType);
						collectionAgentDetailType.setCustomerCode(agentVO.getCustomerCode());
						collectionAgentDetailType
								.setStationCode(agentVO.getStationCode() != null ? agentVO.getStationCode() : "STA");
						customerType.getCollectionagentDetails().add(collectionAgentDetailType);
					}
				}
				if ((customerVO.getCustomerConsigneeVOs() != null && !customerVO.getCustomerConsigneeVOs().isEmpty())) {
					for (CustomerAgentVO agentVO : customerVO.getCustomerConsigneeVOs()) {
						CollectionAgentDetailType collectionAgentDetailType = new CollectionAgentDetailType();
						PoaParametersType poaParametersType = new PoaParametersType();
						poaParametersType.setSinglePoaInfo(agentVO.getAwbNum());
						poaParametersType.setDestinationCode(agentVO.getDestination()!=null? agentVO.getDestination() :"STA");
						poaParametersType.setExcludedHandlingCodes(agentVO.getExcludedScc());
						poaParametersType.setIncludedHandlingCodes(agentVO.getScc());
						poaParametersType.setExcludedOriginCodes(agentVO.getExcludedOrigins());
						poaParametersType.setIncludedOriginCodes(agentVO.getIncludedOrigins());
						poaParametersType.setPoaType(agentVO.getPoaType());
						poaParametersType.setSequenceNumber(agentVO.getSequenceNumber());
						CustomerSupportingDocumentVO fileName =agentVO.getSupportingDocumentVOs().get(0);
						poaParametersType.setPoaDocumentFileName(fileName.getFileName());
						if (agentVO.getPoaCreationTime() != null) {
							String poaCreationDate = TimeConvertor.toStringFormat(
									agentVO.getPoaCreationTime().toCalendar(),
									"dd-MMM-yyyy HH:mm:ss");
							poaParametersType.setPoaCreationDate(poaCreationDate);
						}
						String validityStartDate = null;
						String validityEndDate = null;
						
						if (agentVO.getValidityStartDate() != null) {
							validityStartDate = TimeConvertor.toStringFormat(
									agentVO.getValidityStartDate().toCalendar(),
									DAT_FORMAT);
							
						}
						if (agentVO.getValidityEndDate() != null) {
							validityEndDate = TimeConvertor.toStringFormat(
									agentVO.getValidityEndDate().toCalendar(),
									DAT_FORMAT);
							
						}
						collectionAgentDetailType.setContractStartDate(validityStartDate);
						collectionAgentDetailType.setContractEndDate(validityEndDate);
						collectionAgentDetailType.setPoaParameters(poaParametersType);
						collectionAgentDetailType.setCustomerCode(agentVO.getAgentCode());
						collectionAgentDetailType.setConsigneeName(agentVO.getAgentName());
						collectionAgentDetailType
								.setStationCode(agentVO.getStationCode() != null ? agentVO.getStationCode() : "STA");
						customerType.getCollectionagentDetails().add(collectionAgentDetailType);
					}
				}
			}
			responseType.setCustomerDetails(customerType);
			responseType.setStatus(SaveStatusType.SUCCESS);
		}
		responseType.setRequestId(Long.toString(getResponseId()));
		// modified by a-4810 for icrd-33029
		// response.setResponseDetails(responseType);
		response.getResponseDetails().add(responseType);
		// response.setCustomerData(responseType);
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
	 * 	Used for 	:   populating the certificate details
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
				customerCertificateVO.setCertificateType(certificateType.getCertificateType());
				customerCertificateVO.setCertificateNumber(certificateType.getCertificateNumber());
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
				responseDetailType.setCertificateType(certificateVO.getCertificateType());
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
    	CustomerSaveResponse response = null;
    	Collection<ErrorVO> errors = new ArrayList<>();
    	Collection<CustomerVO> returnCustomers = null;
        try {
        	String isValid = validateConditionalMandatory(customerSaveRequest);
        	if(isValid != null) {
				throw new ServiceFault(isValid);
			}
        	// need to change the Source system name
			if (POA_PORTAL.equals(customerSaveRequest.getMessageHeader().getSourceSystem())) {

				CustomerType customerDetails = customerSaveRequest.getCustomerData().get(0).getCustomerDetails();

				// validations method (skipping for delete)
				if(!OperationalFlagType.D.equals(customerSaveRequest.getCustomerData().get(0).getOperationalFlag())) {
				errors = poaValidations(customerDetails);
				if (!(errors).isEmpty()) {
					response = populateCustomerSaveResponse(returnCustomers, errors, customers);
					return response;
				}
				}
				

				//populate CustomerVO to save	
				despatchRequest("saveBrokerMapping", populateCustomerVOSavePortal(customerDetails));
			} else {
	        	customers = populateCustomer(customerSaveRequest);
	        	if(customers == null) {
	        		throw new ServiceFault("No customers to save!");
	        	}
				returnCustomers = despatchRequest("saveCustomerDetails", customers);
			}
			response = populateCustomerSaveResponse(returnCustomers, null, customers);
			// Added by A-5290 for ICRD-128609
		} catch (WSBusinessException e) {
			errors.addAll(e.getErrors());
			response = populateCustomerSaveResponse(returnCustomers, errors, customers);
		} catch (SystemException e) {
			errors.addAll(e.getErrors());
			response = populateCustomerSaveResponse(returnCustomers, errors, customers);
		}
        return response;
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

		if (customerSaveRequest != null
				&& customerSaveRequest.getCustomerData() != null
				&& !customerSaveRequest.getCustomerData().isEmpty()) {
			for (CustomerSaveRequestType request : customerSaveRequest
					.getCustomerData()) {
				CustomerType customerType = request.getCustomerDetails();
				CustomerVO customerVO = new CustomerVO();
				
				if(request.getOperationalFlag()!=null){
				customerVO.setOperationFlag(request.getOperationalFlag()
						.toString());
				}
				customerVO.setCompanyCode(getCompanyCode());
				// added by a-4810 for icrd-30629
				customerVO.setCustomerCode(customerType.getCustomerCode());
				//Added by A-7364 as part of ICRD-257204 starts
				if(customerType.getCustomerCode()==null || customerType.getCustomerCode().trim().length()==0){
					customerVO.setCustomerIDGenerationRequired(CustomerVO.FLAG_YES);
				}
				//Added by A-7364 as part of ICRD-257204 ends
				customerVO.setCustomerName(customerType.getCustomerName());
				customerVO.setCustomerShortCode(customerType
						.getCustomerShortCode());
				customerVO.setStationCode(customerType.getStationCode());
				customerVO.setAirportCode(customerType.getAirportCode());
				if(customerType.getCustomerType()==null){
					customerType.setCustomerType("CU");
				}
				customerVO.setInternalAccountHolder(customerType.getInternalAccountHolder());
				// Address of the customer
				AddressDetailType addressType = customerType
						.getAddressDetails();
				if (addressType != null) {
					customerVO.setAddress1(addressType.getAddressOne());
					customerVO.setAddress2(addressType.getAddressTwo());
					customerVO.setCity(addressType.getCity());
					customerVO.setCountry(addressType.getCountryCode());
					customerVO.setEmail(addressType.getEmailAddress());
					customerVO.setFax(addressType.getFaxNumber());
					customerVO.setMobile(addressType.getMobileNumber());
					customerVO.setTelephone(addressType.getPhoneNumber());
					customerVO.setState(addressType.getState());
					customerVO.setZipCode(addressType.getZipCode());
				}

				// The customer, agent, cc collection master screens merged.
				// CustomerVO has collection of agentVOs and hence modifying the
				// code as per the latest VO structure.
				AgentDetailType agent = customerType.getAgentDetails();
				 if (agent != null
						&& "AG".equals(customerType.getCustomerType())) {
					AgentVO agentVO = new AgentVO();
					agentVO.setIataAgentCode(agent.getIATACode());
					agentVO.setAgentType(agent.getAgentType());
					agentVO.setCassIdentifier(agent.getExportCass());
					agentVO.setHoldingCompany(agent.getHoldingCompany());
					if(customerVO.getCountry()!=null && customerVO.getCountry().trim().length()>0){
						agentVO.setAgentCountry(customerVO.getCountry());
					}
					if(agent.getNormalCommission()!=null){
					agentVO.setNormCommPrc(agent.getNormalCommission());
					}
					if(agent.getFixedCommission()!=null) {
					agentVO.setFixedValue(agent.getFixedCommission());
					}
					agentVO.setOwnSalesFlag(agent.getOwnSales() == null ? null
							: agent.getOwnSales().value());
					/*Collection<String> settlementcurrencycodes = new ArrayList<String>();
					settlementcurrencycodes.add(agent.getSettlementCurrency());
					agentVO.setSettlementCurrencyCodes(settlementcurrencycodes);*/
					customerVO.setSettlementCurrencyCodes(agent
							.getSettlementCurrency());
					if (agent.getSalesReportingAgent() != null) {
					agentVO.setSalesReporting("Y".equals(agent.getSalesReportingAgent().value()) ? true
								: false);
					}
					if(agent.getProformaInvoice() != null) {
					agentVO.setInvoiceGenerationFlag("Y".equals(agent
							.getProformaInvoice().value()) ? true : false);
					}
					agentVO.setRemarks(agent.getRemarks());
					
					if (customerType.getValidFromDate() != null) {
						LocalDate validFromDate = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validFromDate.setDate(customerType.getValidFromDate());
						agentVO.setValidFrom(validFromDate);
					}
					
					
					if (customerType.getValidToDate() != null) {
						LocalDate validToDate = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validToDate.setDate(customerType.getValidToDate());
						 agentVO.setValidTo(validToDate);
					}

					if (customerVO.getAgentVOs() != null) {
						customerVO.getAgentVOs().add(agentVO);
					} else {
						Collection<AgentVO> agentVOs = new ArrayList<AgentVO>();
						agentVOs.add(agentVO);
						customerVO.setAgentVOs(agentVOs);
					}

					// Added by A-5169 for ICRD-18283 on 27FEB13 ends
				}

				// Set the billing details.
				BillingDetailType billingDetailType = customerType
						.getBillingDetails();
				CustomerBillingDetailsVO customerBillingDetailsVO = new CustomerBillingDetailsVO();
				if (billingDetailType != null) {
					customerBillingDetailsVO.setCityCode(billingDetailType
							.getBillingAddressDetail().getCity());
					customerBillingDetailsVO.setCountry(billingDetailType
							.getBillingAddressDetail().getCountryCode());
					customerBillingDetailsVO.setEmail(billingDetailType
							.getBillingAddressDetail().getEmailAddress());
					//Added as part of ICRD-228463 starts
					if(billingDetailType.getBillingAddressDetail().getEmailAddressOne()!=null 
							&& billingDetailType.getBillingAddressDetail().getEmailAddressOne().trim().length()>0){
						customerBillingDetailsVO.setEmailOne(billingDetailType.
								getBillingAddressDetail().getEmailAddressOne());
					} 
					if(billingDetailType.getBillingAddressDetail().getEmailAddressTwo()!=null
							&& billingDetailType.getBillingAddressDetail().getEmailAddressTwo().trim().length()>0){
						customerBillingDetailsVO.setEmailTwo(billingDetailType.
								getBillingAddressDetail().getEmailAddressTwo());
					}
					//Added as part of ICRD-228463 ends
					customerBillingDetailsVO.setFax(billingDetailType
							.getBillingAddressDetail().getFaxNumber());
					customerBillingDetailsVO.setState(billingDetailType
							.getBillingAddressDetail().getState());
					customerBillingDetailsVO.setTelephone(billingDetailType
							.getBillingAddressDetail().getPhonenumber());
					customerBillingDetailsVO.setZipcode(billingDetailType
							.getBillingAddressDetail().getZipCode());
					customerVO.setBillingCode(billingDetailType
							.getBillingCurrencyCode());
					customerBillingDetailsVO.setLocation(billingDetailType.getBillingAddressDetail().getAddressOne());
					customerBillingDetailsVO.setStreet(billingDetailType.getBillingAddressDetail().getAddressTwo());

					customerVO
							.setCustomerBillingDetailsVO(customerBillingDetailsVO);
					customerVO.setBillingCode(billingDetailType
							.getBillingCurrencyCode());
					customerVO.setBillingPeriod(billingDetailType
							.getBillingPeriod() == null ? null
							: billingDetailType.getBillingPeriod().value());
					customerVO.setSalesId(billingDetailType.getSalesID());
					ToleranceLevelType toleranceLevelType = billingDetailType
							.getToleranceLevel();
					if (toleranceLevelType != null) {
						customerVO.setEnduranceFlag("Y"
								.equals(toleranceLevelType.value()) ? true
								: false);
					}
					if(billingDetailType
							.getTolerancePercentage()!=null){
					customerVO.setEndurancePercentage(billingDetailType
							.getTolerancePercentage());
					}
					if(billingDetailType
							.getToleranceValue()!=null) {
					customerVO.setEnduranceValue(billingDetailType
							.getToleranceValue());
					}
					if(billingDetailType
							.getMaximumValue()!=null) {
					customerVO.setEnduranceMaxValue(billingDetailType
							.getMaximumValue().doubleValue());
				}
				}
				// Sets the customer certificate details

				customerVO.setSita(customerType.getSITAAddress());
				customerVO.setRemarks(customerType.getRemarks());
				customerVO.setDefaultNotifyMode(customerType
						.getDefaultNotificationMode() == null ? null
						: customerType.getDefaultNotificationMode().value());
				customerVO.setCustomerGroup(customerType.getCustomerGroup());
				customerVO.setAccountNumber(customerType.getAccountNumber());
				customerVO
						.setKnownShipper(customerType.getKnownShipper() == null ? null
								: customerType.getKnownShipper().value());

				customerVO.setStatus(customerType.getCustomerStatus());
				customerVO.setEoriNo(customerType.getCustomerEORINumber());
				customerVO.setGlobalCustomerFlag(customerType
						.getGlobalCustomer() == null ? "N" : customerType
						.getGlobalCustomer().value());
				if (customerType.getClearingAgent() != null) {
					customerVO.setClearingAgentFlag("Y".equals(customerType
							.getClearingAgent().value()) ? true : false);
				}
				customerVO.setStopCredit(customerType.getStopCreditFlag() == null ? null
								: customerType.getStopCreditFlag().value());

				customerVO.setInvoiceToCustomer(customerType
						.getInvoiceToCustomer() == null ? null : customerType
						.getInvoiceToCustomer().value());

				customerVO.setVatRegNumber(customerType.getVatRegNumber());
				if (customerType.getCustomerEstablishedDate() != null) {
					LocalDate establishedDate = new LocalDate(
							LocalDate.NO_STATION, Location.NONE, false);
					establishedDate.setDate(customerType
							.getCustomerEstablishedDate());
					customerVO.setEstablishedDate(establishedDate);
				}
				customerVO.setCustomerType(customerType.getCustomerType());
				if (customerType.getSellingLocation() != null) {
					customerVO.setSellingLocation("Y".equals(customerType
							.getSellingLocation().value()) ? true : false);
				}
				if (customerType.getControllingLocation() != null) {
					customerVO.setControllingLocation("Y".equals(customerType
							.getControllingLocation().value()) ? true : false);
				}
				customerVO.setContyrollingAgentCode(customerType
						.getControllingCustomerCode());
				customerVO.setControllingAgentName(customerType
						.getControllingCustomerName());
				customerVO.setBillingIndicator(customerType
						.getControllingBillToIndicator());
				//by A-7567 for ICRD-305684
				customerVO.setCntLocBillingApplicableTo(customerType.getCntLocBillingApplicableTo());

				customerVO.setInvoiceClubbingIndicator(customerType
						.getInvoiceClubbingFlag() == null ? null : customerType
						.getInvoiceClubbingFlag().value());
				customerType.getInvoiceClubbingFlag();

				if (customerType.getValidFromDate() != null) {
					LocalDate validFromDate = new LocalDate(
							LocalDate.NO_STATION, Location.NONE, false);
					validFromDate.setDate(customerType.getValidFromDate());
					customerVO.setValidFrom(validFromDate);
				}
				if (customerType.getValidToDate() != null) {
					LocalDate validToDate = new LocalDate(LocalDate.NO_STATION,
							Location.NONE, false);
					validToDate.setDate(customerType.getValidToDate());
					customerVO.setValidTo(validToDate);
				}
				List<CertificateDetailsType> customerCertificateDetailsType = customerType
						.getCertificateDetails();
				Collection<CustomerCertificateVO> customerCertificates = new ArrayList<CustomerCertificateVO>();
				if (customerCertificateDetailsType != null
						&& customerCertificateDetailsType.size() > 0) {

					for (CertificateDetailsType certificate : customerCertificateDetailsType) {
						CustomerCertificateVO customerCertificateVO = new CustomerCertificateVO();

						if (certificate.getValidityFromDate() != null) {
							LocalDate startDate = new LocalDate(
									LocalDate.NO_STATION, Location.NONE, false);
							startDate
									.setDate(certificate.getValidityFromDate());
							customerCertificateVO
									.setValidityStartDate(startDate);
						}

						if (certificate.getValidityToDate() != null) {
							LocalDate endDate = new LocalDate(
									LocalDate.NO_STATION, Location.NONE, false);
							endDate.setDate(certificate.getValidityToDate());
							customerCertificateVO.setValidityEndDate(endDate);
						}

						customerCertificateVO.setCustomerCode(customerVO
								.getCustomerCode());
						customerCertificateVO.setCertificateType(certificate
								.getCertificateType());
						customerCertificateVO.setCertificateNumber(certificate.getCertificateNumber());
						// added by a-4810 for icrd-30629
						if(certificate.getOperationalFlag()!=null){
							customerCertificateVO.setOperationFlag(certificate.getOperationalFlag().toString());
						}
						customerCertificateVO.setCompanyCode(customerVO
								.getCompanyCode());						
						customerCertificates.add(customerCertificateVO);
					}
					customerVO
							.setCustomerCertificateDetails(customerCertificates);
				}

				// Identifies clearing agents at the delivery side and
				// forwarders at the export side.
				List<CollectionAgentDetailType> collectionAgentDetails = customerType
						.getCollectionagentDetails();
				Collection<CustomerAgentVO> customerAgents = new ArrayList<CustomerAgentVO>();
				if (collectionAgentDetails != null
						&& collectionAgentDetails.size() > 0) {
					for (CollectionAgentDetailType agentDetail : collectionAgentDetails) {
						CustomerAgentVO customerAgentVO = new CustomerAgentVO();
						customerAgentVO.setAgentCode(agentDetail
								.getCustomerCode());
						customerAgentVO.setCompanyCode(getCompanyCode());
						customerAgentVO.setCustomerCode(customerType
								.getCustomerCode());
						customerAgentVO.setExportFlag(agentDetail
								.getExportFlag() == null ? null : agentDetail
								.getExportFlag().value());
						customerAgentVO.setImportFlag(agentDetail
								.getImportFlag() == null ? null : agentDetail
								.getImportFlag().value());
						customerAgentVO.setStationCode(agentDetail
								.getStationCode());
						customerVO.setInvoiceToCustomer(agentDetail
								.getBillToCustomerFlag() == null ? null
								: agentDetail.getBillToCustomerFlag().value());

						agentDetail.getBillToCustomerFlag();
						customerAgentVO.setScc(agentDetail.getHandlingCode());
						
						customerAgentVO.setSalesFlag(agentDetail
								.getSalesFlag() == null ? null :agentDetail.getSalesFlag().value());   
						customerAgentVO.setCarrier(agentDetail.getCarrierCode());
						customerAgentVO.setRemarks(agentDetail.getRemarks());
						customerAgentVO.setOrigin(agentDetail.getOriginCode());
						customerAgentVO.setAgentType(agentDetail.getAgentType());
						LocalDate validityStartDate = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						validityStartDate.setDate(agentDetail.getContractStartDate());
						customerAgentVO.setValidityStartDate(validityStartDate);
						LocalDate validityEndDate = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						validityEndDate.setDate(agentDetail.getContractEndDate());
						customerAgentVO.setValidityEndDate(validityEndDate);
						customerAgents.add(customerAgentVO);
					}
					customerVO.setCustomerAgentVOs(customerAgents);
				}

				// Contact Details
				List<ContactDetailType> contactDetailTypes = customerType
						.getContactDetails();
				Collection<CustomerContactVO> customerContacts = new ArrayList<CustomerContactVO>();
				if (contactDetailTypes != null && contactDetailTypes.size() > 0) {
					for (ContactDetailType contactDetailType : contactDetailTypes) {
						CustomerContactVO customerContactVO = new CustomerContactVO();
						customerContactVO.setOperationFlag(customerVO.getOperationFlag());
						if(customerContactVO.getOperationFlag()==null || customerContactVO.getOperationFlag().trim().length()>0) {
							customerContactVO.setOperationFlag(CustomerContactVO.OPERATION_FLAG_INSERT);
						}
						customerContactVO.setCompanyCode(customerVO.getCompanyCode());
						customerContactVO
								.setCustomerDesignation(contactDetailType
										.getDesignation());
						customerContactVO.setFax(contactDetailType
								.getFaxNumber());
						customerContactVO.setMobile(contactDetailType
								.getMobileNumber());
						customerContactVO.setTelephone(contactDetailType
								.getPhoneNumber());
						customerContactVO
								.setContactCustomerCode(contactDetailType
										.getFirstName());
						customerContactVO.setCustomerName(contactDetailType
								.getLastName());
						customerContactVO
								.setPrimaryUserFlag(contactDetailType
										.getPrimaryContact() == null ? null
										: contactDetailType.getPrimaryContact()
												.value());
						customerContactVO.setRemarks(contactDetailType
								.getContactRemarks());
						customerContactVO.setSiteAddress(contactDetailType
								.getSitaAddress());
						customerContactVO.setEmailAddress(contactDetailType
								.getEmailAddress());
						//Added by A-5220 for ICRD-55852
						customerContactVO.setContactType(contactDetailType.getContactType());
						//Added for ICRD-159064
						customerContactVO.setNotificationLanguageCode(contactDetailType.getNotificationLanguage());
						customerContactVO.setNotificationFormat(contactDetailType.getNotificationFormat());//ICRD-143720
						customerContactVO.setNotifyShipmentType(customerType.getNotifyShipmentType());
						if(contactDetailType.getNotificationPreferences() != null
								&& contactDetailType.getNotificationPreferences().size() > 0){
							Collection<NotificationPreferenceVO> notificationPreferenceVOs = new ArrayList<NotificationPreferenceVO>();
							for (NotificationPreferenceType notificationPreferenceType : contactDetailType.getNotificationPreferences()) {
								NotificationPreferenceVO preferenceVO = new NotificationPreferenceVO();
								preferenceVO.setCompanyCode(customerVO.getCompanyCode());
								preferenceVO.setCustomerCode(customerType.getCustomerCode());
								preferenceVO.setEmailFlag(notificationPreferenceType
										.getEmailFlag() == null ? null
										: notificationPreferenceType.getEmailFlag()
												.value());
								preferenceVO.setMobileFlag(notificationPreferenceType
										.getMobileFlag() == null ? null
										: notificationPreferenceType.getMobileFlag()
												.value());
								preferenceVO.setFaxFlag(notificationPreferenceType
										.getFaxFlag() == null ? null
										: notificationPreferenceType.getFaxFlag()
												.value());
								preferenceVO.setEventCode(notificationPreferenceType.getEventCode());
								preferenceVO.setEventDescription(notificationPreferenceType.getEventDescription());
								notificationPreferenceVOs.add(preferenceVO);
							}
							customerContactVO.setNotificationPreferences(notificationPreferenceVOs);
						}
						if(contactDetailType.getAdditionalContacts() != null
								&& contactDetailType.getAdditionalContacts().size() > 0){
							Collection<AdditionalContactVO> additionalContactVOs = new ArrayList<AdditionalContactVO>();
							for (AdditionalContactType additionalContactType : contactDetailType.getAdditionalContacts()) {
								AdditionalContactVO additionalContactVO = new AdditionalContactVO();
								additionalContactVO.setCompanyCode(customerVO.getCompanyCode());
								additionalContactVO.setCustomerCode(additionalContactType.getContactAddress());
								additionalContactVO.setContactMode(additionalContactType
										.getContactMode() == null ? null
												: additionalContactType.getContactMode().value());
								additionalContactVO.setContactAddress(additionalContactType.getContactAddress());
								additionalContactVOs.add(additionalContactVO);
							}
							customerContactVO.setAdditionalContacts(additionalContactVOs);
						}
						customerContacts.add(customerContactVO);
					}
					customerVO.setCustomerContactVOs(customerContacts);
				}
				//Added for ICRD-159064
				if(customerType.getAdditionalCustomerInformation() != null &&
						customerType.getAdditionalCustomerInformation().size() > 0){
					Collection<CustomerPreferenceVO> customerPreferenceVOs = new ArrayList<CustomerPreferenceVO>();
					for (AdditionalCustomerInfoType additionalCustomerInfoType : customerType.getAdditionalCustomerInformation()) {
						CustomerPreferenceVO customerPreferenceVO = new CustomerPreferenceVO();
						customerPreferenceVO.setPreferenceCode(additionalCustomerInfoType.getParameterCode());
						customerPreferenceVO.setPreferenceValue(additionalCustomerInfoType.getParameterValue());
						customerPreferenceVOs.add(customerPreferenceVO);
					}
					customerVO.setCustomerPreferences(customerPreferenceVOs);
				}
				// customermiscellaneousdetails
				customerVO.setCustomerCode(customerType.getCustomerCode());
				if (customerType.getCustomermiscDetails() != null) {

					CustomerMiscellaneousVO customerMiscellaneousVO = new CustomerMiscellaneousVO();
					customerMiscellaneousVO.setLavNumber(customerType
							.getCustomermiscDetails().getLavNumber());
					customerMiscellaneousVO
							.setAlfaIndicatorForCustoms(customerType
									.getCustomermiscDetails()
									.getAlfaIndicatorForCustoms());
					customerMiscellaneousVO
							.setCustomsLocationNumber(customerType
									.getCustomermiscDetails()
									.getCustomsLocationNumber());
					customerMiscellaneousVO.setCustomerCode(customerType
							.getCustomerCode());
					customerMiscellaneousVO.setCompanyCode(customerVO
							.getCompanyCode());
					customerVO.setCustomerMiscDetails(customerMiscellaneousVO);
				}

				// IAC Details set to the customerVO.
				IACDetailType iacDetailsType = customerType.getIacDetails();
				if (iacDetailsType != null) {
					IACDetailsVO iacDetailsVO = new IACDetailsVO();
					if (iacDetailsType.getCustomerIACSSPExpiryDate() != null) {
						LocalDate apiacsspExpiryDate = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						apiacsspExpiryDate.setDate(iacDetailsType
								.getCustomerIACSSPExpiryDate());
						iacDetailsVO.setApiacsspExpiryDate(apiacsspExpiryDate);
					}

					if (iacDetailsType.getCustomerIACexpirydate() != null) {
						LocalDate iacExpiryDate = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						iacExpiryDate.setDate(iacDetailsType
								.getCustomerIACexpirydate());
						iacDetailsVO.setIacExpiryDate(iacExpiryDate);
					}

					iacDetailsVO.setApiacsspNumber(iacDetailsType
							.getCustomerIACsspNumber());
					iacDetailsVO.setIacNumber(iacDetailsType
							.getCustomerIACNumber());

					customerVO.setIacDetailsVO(iacDetailsVO);
				}

				// NACCS Details set to the customerVO.
				NaccsDetailType naccsDetailType = customerType
						.getNaccsDetails();
				if (naccsDetailType != null) {
					//commenting for icrd-35865 by a-4810
					/*customerVO.setBillPeriod(naccsDetailType
							.getBillingPeriodCode());*/
					customerVO.setBranch(naccsDetailType.getBranchCode());
					customerVO.setDefaultHawbLength(naccsDetailType
							.getDefaultHAWBLength());
					customerVO.setForwarderType(naccsDetailType
							.getForwarderType());
					customerVO.setHandledCustomerImport(naccsDetailType
							.getHandledCustomerForImport() == null ? null
							: naccsDetailType.getHandledCustomerForImport()
									.value());
					customerVO.setHandledCustomerExport(naccsDetailType
							.getHandledCustomerForExport() == null ? null
							: naccsDetailType.getHandledCustomerForExport()
									.value());
					customerVO.setHandledCustomerForwarder(naccsDetailType
							.getHandledCustomerForForwarder() == null ? null
							: naccsDetailType.getHandledCustomerForForwarder()
									.value());
					customerVO.setNaacsbbAgentCode(naccsDetailType
							.getNaccsAgentCode());
					customerVO.setNaccsAircargoAgentCode(naccsDetailType
							.getNaccsAirCargoAgentCode());
					customerVO.setNaccsDeclarationCode(naccsDetailType
							.getNaccsDeclarationCode());
					customerVO.setNaccsInvoiceCode(naccsDetailType
							.getNaccsInvoiceCode());
				}

				CCCollectorDetailsType ccCollectorDetailsType = customerType
						.getCcCollectorDetails();
				CCCollectorVO ccCollectorVO = new CCCollectorVO();
				Collection<CCCollectorVO> cCCollectorVOs = new ArrayList<CCCollectorVO>();
				if (ccCollectorDetailsType != null
						&& "CC".equals(customerType.getCustomerType())) {
					ccCollectorVO.setAirportCode(ccCollectorDetailsType
							.getAirportCode());
					ccCollectorVO.setAircraftTypeHandled(ccCollectorDetailsType
							.getAircraftType());

					ccCollectorVO
							.setDateOfExchange(ccCollectorDetailsType
									.getDateOfExchange() != null ? ccCollectorDetailsType
									.getDateOfExchange() : null);
					if(ccCollectorDetailsType.getCassImport()!=null) {
					ccCollectorVO.setCassBillingIndicator("Y"
							.equals(ccCollectorDetailsType.getCassImport()
									.value()) ? true : false);
					}
					ccCollectorVO.setCassCode(ccCollectorDetailsType
							.getImportCassCode());

					ccCollectorVO
							.setBillingThroughInterline(ccCollectorDetailsType
									.getBillThroughInterline() != null ? true
									: false);

					ccCollectorVO.setAirlineCode(ccCollectorDetailsType
							.getAirlineCode());

					customerVO
							.setSettlementCurrencyCodes(ccCollectorDetailsType
									.getSettlementCurrency());
					ccCollectorVO.setCcCollectorName(customerType
							.getCustomerName());
					ccCollectorVO.setCompanyCode(customerVO.getCompanyCode());
					if (customerType.getValidFromDate() != null) {
						LocalDate validFromDate = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						validFromDate.setDate(customerType.getValidFromDate());
						ccCollectorVO.setValidFrom(validFromDate);
					}
					if (customerType.getValidToDate() != null) {
						LocalDate validToDate = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						validToDate.setDate(customerType.getValidToDate());
						ccCollectorVO.setValidTo(validToDate);
					}
					ccCollectorVO.setSitaAddress(customerType.getSITAAddress());
					ccCollectorVO.setVatRegNumber(customerType
							.getVatRegNumber());

					ccCollectorVO.setCcCollectorCode(customerType
							.getCustomerCode());
					ccCollectorVO.setRemarks(ccCollectorDetailsType
							.getRemarks());

					ccCollectorVO.setLastUpdatedTime(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, true));
					ccCollectorVO.setLastUpdatedUser(customerSaveRequest
							.getMessageHeader().getSourceSystem());
					ccCollectorVO.setCustomerCode(customerType
							.getCustomerCode());
					cCCollectorVOs.add(ccCollectorVO);
					customerVO.setCcCollectorVOs(cCCollectorVOs);
				}
				// agentcreditvo
				CustomerCreditDetailsType customerCreditDetailsType = customerType
						.getCustomerCreditDetails();
				if (customerCreditDetailsType != null) {
					Collection<CustomerCreditVO> customerCreditVOs = new ArrayList<CustomerCreditVO>();
					CustomerCreditVO customerCreditVO = new CustomerCreditVO();

					Collection<CustomerCreditReferenceVO> customerCreditReferenceVOs = new ArrayList<CustomerCreditReferenceVO>();

					customerCreditVO.setBaseCurrency(customerCreditDetailsType
							.getCreditCurrency());
					if(customerCreditDetailsType.getDepleteCreditLimiForAWBChargesFlag() != null) {
						customerCreditVO.setDepleteForAwbChgs("Y"
							.equals(customerCreditDetailsType
									.getDepleteCreditLimiForAWBChargesFlag()
									.value()) ? true : false);
					}
					if(customerCreditDetailsType.getDepleteNonCreditLimitForAWBChargesFlag() != null) {
						customerCreditVO
							.setDepleteForNonAwbChgs("Y"
									.equals(customerCreditDetailsType
											.getDepleteNonCreditLimitForAWBChargesFlag()
											.value()) ? true : false);
				   }
					if(customerCreditDetailsType.getDepleteCreditLimitForExportAWBChargesFlag()!= null) {
						customerCreditVO
							.setDepleteCreditLimitForExpCharge("Y"
									.equals(customerCreditDetailsType
											.getDepleteCreditLimitForExportAWBChargesFlag()
											.value()) ? true : false);
				   }
					if(customerCreditDetailsType.getDepleteCreditLimitForImportAWBChargesFlag() != null) {
						customerCreditVO
							.setDepleteCreditLimitForImpCharge("Y"
									.equals(customerCreditDetailsType
											.getDepleteCreditLimitForImportAWBChargesFlag()   
											.value()) ? true : false);
				   }
					if(customerCreditDetailsType.getOverrideFlag() != null) {
						customerCreditVO.setOverride("Y"
							.equals(customerCreditDetailsType.getOverrideFlag()
									.value()) ? true : false);
					}
					if (customerCreditDetailsType.getAmountToBeReported() != null) {
						customerCreditVO.setAmtToBeReported(convertToMoney(
								customerCreditDetailsType
										.getAmountToBeReported().doubleValue(),
								customerCreditDetailsType.getCreditCurrency()));
					}
					if (customerCreditDetailsType.getPendingReportedAmount() != null) {
						customerCreditVO.setPendingReportedAmt(convertToMoney(
								customerCreditDetailsType
										.getPendingReportedAmount()
										.doubleValue(),
								customerCreditDetailsType.getCreditCurrency()));
					}
					// customerCreditDetails
					CustomerGuaranteeDetailsType customerGuaranteeDetailsType = customerCreditDetailsType
							.getCustomerGuaranteeDetails();
                   //null chk needed?
					if(customerGuaranteeDetailsType!= null ) {
					Collection<GuaranteeReferenceType> guaranteeReferenceType = customerGuaranteeDetailsType
							.getGuaranteeReferenceType();
                    if(guaranteeReferenceType!=null && guaranteeReferenceType.size()>0) {
					for (GuaranteeReferenceType referenceTyp : guaranteeReferenceType) {
						CustomerCreditReferenceVO customerCreditReferenceVO = new CustomerCreditReferenceVO();
						if (referenceTyp.getGuaranteeAmount() != null) {
							customerCreditReferenceVO.setAmount(convertToMoney(
									referenceTyp.getGuaranteeAmount()
											.doubleValue(),
									customerCreditDetailsType
											.getCreditCurrency()));

						}
						// ICRD-101159_LakshmiN_06Mar2015 starts
						if (referenceTyp.getGuaranteeAmountAvailable() != null) {
							customerCreditReferenceVO.setAvailableGuaranteeAmount(convertToMoney(
									referenceTyp.getGuaranteeAmountAvailable()
											.doubleValue(),
									customerCreditDetailsType
											.getCreditCurrency()));
							
							if ("I".equalsIgnoreCase(referenceTyp.getGuaranteeCategory())) {
								Money impGrntAmt = convertToMoney(
										referenceTyp.getGuaranteeAmountAvailable().doubleValue(),
										customerCreditDetailsType.getCreditCurrency());
								customerCreditReferenceVO.setAvailableGuaranteeAmountImp(impGrntAmt);
							} else if ("E".equalsIgnoreCase(referenceTyp.getGuaranteeCategory())) {
								Money expGrntAmt = convertToMoney(
										referenceTyp.getGuaranteeAmountAvailable().doubleValue(),
										customerCreditDetailsType.getCreditCurrency());
								customerCreditReferenceVO.setAvailableGuaranteeAmountExp(expGrntAmt);
							} else {
								Money splitGrntAmt = convertToMoney(
										(referenceTyp.getGuaranteeAmountAvailable().doubleValue() / 2),
										customerCreditDetailsType.getCreditCurrency());
								customerCreditReferenceVO.setAvailableGuaranteeAmountImp(splitGrntAmt);
								customerCreditReferenceVO.setAvailableGuaranteeAmountExp(splitGrntAmt);
							}

						}
						// ICRD-101159_LakshmiN_06Mar2015 ends						
						customerCreditReferenceVO.setReferenceCode(referenceTyp
								.getGuaranteeReferenceCode());

						if (referenceTyp.getGuaranteeValidityEndDate() != null) {
							LocalDate validityEndDate = new LocalDate(
									LocalDate.NO_STATION, Location.NONE, false);
							validityEndDate.setDate(referenceTyp
									.getGuaranteeValidityEndDate());
							customerCreditReferenceVO.setValidTill(validityEndDate);
						}
						customerCreditReferenceVO.setRemarks(referenceTyp
								.getGuaranteeRemarks());
						customerCreditReferenceVO.setCategory(referenceTyp.getGuaranteeCategory());
						//Added by a-4810 for icrd-73897
                        customerCreditReferenceVO.setCustomerCode(customerType
								.getCustomerCode());
                          /* Removed creditCodes as a part of ICRD-19029  */
						/*customerCreditReferenceVO.setCreditCode(customerType.
								getCustomerCode());*/
                        
                        Collection<String> systemCodes = new ArrayList<String>();
						systemCodes.add(DEFAULT_GUARANTEE_TYPE);
						String defaultGuaranteeType = null;
						Map<String, String> systemParameterList = null;
						try {
							systemParameterList = despatchRequest(
									"findSystemParameterByCodes", systemCodes);
						} catch (WSBusinessException e) {
							log.log(Log.SEVERE, "Exception caught while fetching system parameter");
						} catch (SystemException e) {
							log.log(Log.SEVERE, "Exception caught while fetching system parameter");
						}
						if(systemParameterList!=null && systemParameterList.size()>0 && systemParameterList.containsKey(DEFAULT_GUARANTEE_TYPE)){
							defaultGuaranteeType = systemParameterList.get(DEFAULT_GUARANTEE_TYPE);
							customerCreditReferenceVO.setGuaranteeType(defaultGuaranteeType);
						}
						
						customerCreditReferenceVO.setOperationFlag(CustomerCreditReferenceVO.OPERATION_FLAG_INSERT);
						customerCreditReferenceVO.setCompanyCode(customerVO
								.getCompanyCode());
						customerCreditReferenceVOs.add(customerCreditReferenceVO);

					}
					customerCreditVO
							.setCustomerCreditReferences(customerCreditReferenceVOs);
				   } 
                  }
					customerCreditVO.setCompanyCode(customerVO.getCompanyCode());
					customerCreditVO.setCustomerCode(customerType
							.getCustomerCode());
					/*customerCreditVO.setCreditCode(customerType
							.getCustomerCode());*/
					customerCreditVO.setCustomerName(customerType
							.getCustomerName());
					customerCreditVO.setCompanyCode(customerVO.getCompanyCode());
					customerCreditVO.setCustomerCode(customerType
							.getCustomerCode());
					/*customerCreditVO.setCreditCode(customerType
							.getCustomerCode());*/
					customerCreditVO.setCustomerName(customerType
							.getCustomerName());
					customerCreditVOs.add(customerCreditVO);
					customerVO.setCustomerCreditDetails(customerCreditVOs);
				}

				customerVO.setRemark(customerType.getRemarks());// Either one
																// needs to be
																// removed.
				
				//Added for ICRD-159064
				customerVO.setSource(CustomerVO.SOURCE_INTERFACE);
				if (CustomerVO.SOURCE_PORTAL.equals(customerSaveRequest.getMessageHeader().getSourceSystem())) {
					customerVO.setSource(CustomerVO.SOURCE_PORTAL);
					if(customerVO.getCustomerContactVOs() != null && customerVO.getCustomerContactVOs().size() > 0) {
						for (CustomerContactVO contactVO : customerVO.getCustomerContactVOs()) {
							if(contactVO.getContactType() != null && "ETR".equals(contactVO.getContactType()) || "ETRTOA".equals(contactVO.getContactType())
									|| "EFRT".equals(contactVO.getContactType())) {
								if(contactVO.getNotificationPreferences() != null && !contactVO.getNotificationPreferences().isEmpty()) {
									contactVO.setActiveStatus("A");
								}
								else {
									contactVO.setActiveStatus("I");
								}
							}
						}
					}
				}else if(CustomerVO.SOURCE_SALESFORCE.equalsIgnoreCase(customerSaveRequest.getMessageHeader().getSourceSystem())){
					customerVO.setSource(CustomerVO.SOURCE_SALESFORCE);
					if (customerVO.getCustomerContactVOs() != null && customerVO.getCustomerContactVOs().size() > 0) {
						for (CustomerContactVO contactVO : customerVO.getCustomerContactVOs()) {
							contactVO.setActiveStatus("A");
							}
						}
				}else{//Added by A-4772 for ICRD-186604 starts here
					if (customerVO.getCustomerContactVOs() != null) {
					for (CustomerContactVO contactVO : customerVO.getCustomerContactVOs()) {
						contactVO.setActiveStatus("A");
						}
					}
					//Added by A-4772 for ICRD-186604 ends here
				}
	
				if (customerType.getLastUpdatedTime() != null) {
					customerVO.setLastUpdatedTime(getLocalDateTimeStampMilliSec(customerType.getLastUpdatedTime()));
				}else{
					customerVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				}
				customerVO.setLastUpdatedUser(customerSaveRequest
						.getMessageHeader().getSourceSystem());
				//CRQ ID:117235 - A-5127 added
				customerVO.setRecipientCode(customerType.getRecipientCode());		
				customerVO.setCassImportIdentifier(customerType.getImportCass());
				//End - CRQ ID:117235 - A-5127 added
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
			
			//Added by A-7364 as part of ICRD-257204 starts
			Collection<String> systemCodes = new ArrayList<String>();
			systemCodes.add(CUSTOMERID_AUTOGENERATION);
			String isCustomerIdAutoGenerationRequired = null;
			Map<String, String> customerIdAutoGenerationParameter = null;
				try {
					customerIdAutoGenerationParameter = despatchRequest(
							"findSystemParameterByCodes", systemCodes);
				} catch (WSBusinessException e) {
					log.log(Log.SEVERE, "Exception caught while fetching system parameter");
				} catch (SystemException e) {
					log.log(Log.SEVERE, "Exception caught while fetching system parameter");
				}
				if(customerIdAutoGenerationParameter!=null && customerIdAutoGenerationParameter.size()>0){
					isCustomerIdAutoGenerationRequired = customerIdAutoGenerationParameter.get(
							CUSTOMERID_AUTOGENERATION);
				}
			//Added by A-7364 as part of ICRD-257204 ends
			
			//modified
			for(CustomerSaveRequestType request : customerSaveRequest.getCustomerData()) {
				////CustomerSaveType customerType = request.getCustomerDetails();
				CustomerType customerType = request.getCustomerDetails();
				if(customerType.getAirportCode() == null &&
						customerType.getStationCode() == null) {
					isValid = "Either Airport Code or Station Code is mandatory";
				}
				
				if(CustomerVO.CUSTOMER_TYPE_AG.equals((customerType.getCustomerType()))) {
					AgentDetailType agentType = customerType.getAgentDetails();
					if(agentType == null) {
						isValid = "Agent Details is mandatory";
					}
					else{
						if(agentType.getIATACode() == null){
							isValid = "IATA Code is mandatory";
						} 
					}
					/*else {
						if(agentType.getExportFlag() == null 
								&& agentType.getImportFlag() == null 
								&& agentType.getSalesFlag() == null) {
							isValid = "Either of import/export/sales flag is mandatory";
						}*/
					}
				if(CustomerVO.CUSTOMER_TYPE_CC.equals((customerType.getCustomerType()))) {
					CCCollectorDetailsType ccCollectorDetailsType = customerType.getCcCollectorDetails();
					if(ccCollectorDetailsType == null) {
						isValid = "CC Collector Details is mandatory";
					}
				
				}
				//Added by A-7364 as part of ICRD-257204 starts
				if(!CustomerVO.FLAG_YES.equals(isCustomerIdAutoGenerationRequired) &&
						(customerType.getCustomerCode()==null || customerType.getCustomerCode().trim().length()==0)){
					isValid = "Customer Code is mandatory";
				}
				//Added by A-7364 as part of ICRD-257204 ends
				
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
	private CustomerSaveResponse populateCustomerSaveResponse(Collection<CustomerVO> returnCustomers,Collection<ErrorVO> errors, 
			Collection<CustomerVO> customers) {
		CustomerSaveResponse response = new CustomerSaveResponse();
		response.setMessageHeader(getMessageHeader());
		if(returnCustomers != null && returnCustomers.size() > 0) {
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
		} else { // Added by A-5290 for ICRD-128609
			CustomerSaveResponseType responseType = new CustomerSaveResponseType();
			CustomerSaveResponseDetailType responseDetailType = new CustomerSaveResponseDetailType();
			ErrorDetailType error = null;
			if (errors != null && !errors.isEmpty()) {
				for (ErrorVO errorVO : errors) {
					if (("STLDTA").equals(errorVO.getErrorCode())) {
						error = new ErrorDetailType();
						error.setErrorCode(errorVO.getErrorCode());
						error.setErrorDesc(CustomerErrorVO.ERR_DESC_STALE_DATA);
					} else if (DUPLICATE_USER_ERROR.equalsIgnoreCase(errorVO.getErrorCode())) {
						error = new ErrorDetailType();
						error.setErrorCode(errorVO.getErrorCode());
						error.setErrorDesc(DUPLICATE_USER_ERRORDESC);
					}else {
						error = new ErrorDetailType();
						error.setErrorCode(errorVO.getErrorCode());
						error.setErrorDesc(errorVO.getErrorDescription());
						
					}
				}
			}
			
			if(error == null){
				error = new ErrorDetailType();
				error.setErrorCode(CustomerErrorVO.ERR_CODE_CUSTOMER_UPDATE_FAILED);
				error.setErrorDesc(CustomerErrorVO.ERR_DESC_CUSTOMER_UPDATE_FAILED);
			}
			if(customers!=null) {
				responseDetailType.setCustomerCode(customers.iterator().next().getCustomerCode());
				responseDetailType.setCustomerStatus(customers.iterator().next().getStatus());
				}else {
					responseDetailType.setCustomerCode("POASAVED");
					responseDetailType.setCustomerStatus("POASAVED");
				}
			responseDetailType.setSaveStatus(SaveStatusType.FAILURE);
			responseDetailType.getErrorDetails().add(error);
			
			responseType.setCustomerSaveResponseDetails(responseDetailType);
			responseType.setRequestId(Long.toString(getResponseId()));
			response.getCustomerSaveResponseData().add(responseType);
		}
		return response;
	}
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.convertToMoney
	 *	Added by 	:	
	 * 	Used for 	:
	 *	Parameters	:	@param value
	 *	Parameters	:	@param basecurrency
	 *	Parameters	:	@return 
	 *	Return type	: 	Money
	 */
	private Money convertToMoney(Double value, String basecurrency) {
		Money chargeMoney = null;

		try {

			chargeMoney = CurrencyHelper.getMoney(basecurrency);

			chargeMoney.setAmount(value == null ? 0

			: value);

		} catch (CurrencyException e) {

			e.getErrorCode();

		}

		return chargeMoney;

	} 
	
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.listCustomers
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param listCustomerRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault 
	 *	Return type	: 	ListCustomerResponse
	 */
	public ListCustomerResponse listCustomers (ListCustomerRequest listCustomerRequest) throws  ServiceFault {
		log.entering("CustomerServiceBaseImpl","=========>>> listCustomers"); 
		Collection< CustomerVO> customerVOs=null;
		try {
		CustomerFilterVO customerFilterVO = populateCustomerFilterVO(listCustomerRequest);
		customerVOs = despatchRequest("getAllCustomers", customerFilterVO);
		}catch (SystemException systemException) {
			return throwErrorFindDetails(systemException.getErrors().iterator().next()
			.getErrorCode(),listCustomerRequest);
		} catch (WSBusinessException e) {
			return throwErrorFindDetails(e.getErrors().iterator().next()
			.getErrorCode(),listCustomerRequest);
		}
		if (customerVOs == null || customerVOs.size() == 0) {
			return throwErrorFindDetails(NO_RECORDS_FOUND_ERROR, listCustomerRequest);
		}
		log.exiting("CustomerServiceBaseImpl","=========>>> listCustomers"); 
		return populateCustomerDetailsResponseType(customerVOs, listCustomerRequest);
	}
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.populateCustomerFilterVO
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param listCustomerRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	CustomerFilterVO
	 */
	private CustomerFilterVO populateCustomerFilterVO (ListCustomerRequest listCustomerRequest ) throws SystemException{
		log.entering("CustomerServiceBaseImpl","=========>>> populateCustomerFilterVO "); 
		CustomerFilterVO customerFilterVO = new CustomerFilterVO (); 
		customerFilterVO.setCustomerCode(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getCustomerCode());
		customerFilterVO.setCustomerShortCode(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getCustomerShortCode());
		customerFilterVO.setCustomerType(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getCustomerType());
		if(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getUpdatedAfter() != null){
			LocalDate updatedAfter = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			updatedAfter.setDateAndTime(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getUpdatedAfter()); 
			customerFilterVO.setUpdatedAfter(updatedAfter); 
		}
		customerFilterVO.setInternalAccountHolder(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getInternalAccountHolder());
		/*Set customerCode, customerShortCode, customerType, updatedAfter from request type to VO*/
		customerFilterVO.setHoldingCompany(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getHoldingCompany());
		customerFilterVO.setHoldingCompanyGroup(listCustomerRequest.getListcustomerRequestData().getListcustomerRequestDetails().getHoldingCompanyGroup());
		
		log.exiting("CustomerServiceBaseImpl","=========>>> populateCustomerFilterVO"); 
		return customerFilterVO;
	}
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.throwErrorFindDetails
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param errorCode
	 *	Parameters	:	@param listCustomerRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	ListCustomerResponse
	 */
	private ListCustomerResponse throwErrorFindDetails(
		String errorCode, ListCustomerRequest listCustomerRequest) {
		log.entering("CustomerServiceBaseImpl","=========>>> throwErrorFindDetails "); 
		ListCustomerResponse listCustomerResponse = new ListCustomerResponse();
		ListCustomerResponseType listCustomerResponseType = new ListCustomerResponseType(); 
		ErrorDetailType errorDetailType= new ErrorDetailType();

		if (NO_RECORDS_FOUND_ERROR.equals(errorCode)) {
			errorDetailType.setErrorDesc(NO_RECORDS_FOUND_ERROR_DESC);
		}
		errorDetailType.setErrorCode(errorCode);
		listCustomerResponse.setMessageHeader(getMessageHeader()); 
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		String responseTime = TimeConvertor.toStringFormat(currentDate.toCalendar(),"dd-MMM-yyyy hh:mm:ss");
		listCustomerResponseType.setRequestId(listCustomerRequest.getListcustomerRequestData().getRequestId());
		listCustomerResponseType.setResponseSentTime(responseTime); 
		listCustomerResponseType.getErrorDetails().add(errorDetailType); 
		listCustomerResponse.getResponseDetails().add(listCustomerResponseType); 
		log.exiting("CustomerServiceBaseImpl","=========>>> throwErrorFindDetails"); 
		return listCustomerResponse;

	}
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.populateCustomerDetailsResponseType
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param customerVOs
	 *	Parameters	:	@param listCustomerRequest
	 *	Parameters	:	@return 
	 *	Return type	: 	ListCustomerResponse
	 */
	private ListCustomerResponse populateCustomerDetailsResponseType(
		Collection< CustomerVO > customerVOs, ListCustomerRequest listCustomerRequest) { 
		log.entering("CustomerServiceBaseImpl"," =========>>> populateCustomerDetailsResponseType"); 
		ListCustomerResponse listCustomerResponse = new ListCustomerResponse();
		ListCustomerResponseType listCustomerResponseType = new ListCustomerResponseType(); 
		listCustomerResponseType.setRequestId(listCustomerRequest.getListcustomerRequestData().getRequestId());
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		String responseTime = TimeConvertor.toStringFormat(currentDate.toCalendar(),"dd-MMM-yyyy hh:mm:ss");
		//set current server time
		listCustomerResponseType.setResponseSentTime(responseTime); 
		listCustomerResponseType.getCustomerDetails().addAll(populateCustomerType(customerVOs)); 
		listCustomerResponse.setMessageHeader(getMessageHeader());
		listCustomerResponse.getResponseDetails().add(listCustomerResponseType); 
		log.exiting("CustomerServiceBaseImpl","=========>>> populateCustomerDetailsResponseType"); 
		return listCustomerResponse;
	}
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.populateCustomerType
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param customerVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	ArrayList<CustomerType>
	 */
	private ArrayList< CustomerType > populateCustomerType (
		Collection< CustomerVO > customerVOs) { 
		log.entering("CustomerServiceBaseImpl","=========>>> populateCustomerType "); 
		ArrayList< CustomerType > customerTypes = new ArrayList< CustomerType >();
		for (CustomerVO customerVO: customerVOs) {
			CustomerType customerType = new CustomerType ();
			if(customerVO.getCustomerCode() != null && customerVO.getCustomerCode().trim().length() > 0){
				customerType.setCustomerCode(customerVO.getCustomerCode()); 
			}
			if(customerVO.getCustomerType() != null && customerVO.getCustomerType().trim().length() > 0){
				customerType.setCustomerType(customerVO.getCustomerType()); 
			}
			//Added by A-7534 for ICRD-228903
			if(customerVO.getInternalAccountHolder()!=null && customerVO.getInternalAccountHolder().trim().length()>0){
				customerType.setInternalAccountHolder(customerVO.getInternalAccountHolder());
			}
			//----------
			if(customerVO.getCustomerName() != null && customerVO.getCustomerName().trim().length() > 0){
				customerType.setCustomerName(customerVO.getCustomerName());
			}
			if(customerVO.getValidFrom() != null ){
				String validFromDate = TimeConvertor.toStringFormat(customerVO.getValidFrom().toCalendar(),"dd-MMM-yyyy");
				customerType.setValidFromDate(validFromDate); 
			}
			if(customerVO.getValidTo() != null ){
				String validToDate = TimeConvertor.toStringFormat(customerVO.getValidTo().toCalendar(),"dd-MMM-yyyy");
				customerType.setValidToDate(validToDate); 
			}
			if(customerVO.getStatus() != null && customerVO.getStatus().trim().length() > 0 ){
				customerType.setCustomerStatus(customerVO.getStatus());
			}
			if(customerVO.getCustomerShortCode() != null && customerVO.getCustomerShortCode().trim().length() > 0 ){
				customerType.setCustomerShortCode(customerVO.getCustomerShortCode()); 
			}
			/*
			 * Setting address details		
			 */
			AddressDetailType addressType = new AddressDetailType();
			if(customerVO.getAddress1() != null && customerVO.getAddress1().trim().length() > 0){
				addressType.setAddressOne(customerVO.getAddress1());
			}
			if(customerVO.getAddress1() != null && customerVO.getAddress1().trim().length() > 0){
				addressType.setAddressTwo(customerVO.getAddress2());
			}
			if(customerVO.getCity() != null && customerVO.getCity().trim().length() > 0){
				addressType.setCity(customerVO.getCity());
			}
			if(customerVO.getCountry() != null && customerVO.getCountry().trim().length() > 0){
				addressType.setCountryCode(customerVO.getCountry());
			}
			if(customerVO.getEmail() != null && customerVO.getEmail().trim().length() > 0){
				addressType.setEmailAddress(customerVO.getEmail());
			}
			if(customerVO.getFax() != null && customerVO.getFax().trim().length() > 0){
				addressType.setFaxNumber(customerVO.getFax());
			}
			if(customerVO.getMobile() != null && customerVO.getMobile().trim().length() > 0){
				addressType.setMobileNumber(customerVO.getMobile());
			}
			if(customerVO.getTelephone() != null && customerVO.getTelephone().trim().length() > 0){
				addressType.setPhoneNumber(customerVO.getTelephone());
			}
			if(customerVO.getState() != null && customerVO.getState().trim().length() > 0){
				addressType.setState(customerVO.getState());
			}
			if(customerVO.getZipCode() != null && customerVO.getZipCode().trim().length() > 0){
				addressType.setZipCode(customerVO.getZipCode());
			}			
			if(customerVO.getState() != null && customerVO.getState().trim().length() > 0){
				addressType.setState(customerVO.getState());
			}
			
			customerType.setAddressDetails(addressType);
			customerTypes.add(customerType); 
		}
		log.exiting("CustomerServiceBaseImpl","=========>>> populateCustomerType"); 
		return customerTypes;

	}
	
	    /**
		  * 
		  * 
		  * @param dateString
		  * @return
		  * method to convert timestamp_string to localdate format with milli seconds
		  */
	  private LocalDate getLocalDateTimeStampMilliSec(String dateString){
	  	SimpleDateFormat datetimeFormatter = new SimpleDateFormat(DATE_FORMAT_MILLISEC);
	  	Date date= new Date();
			try {
				date = datetimeFormatter.parse(dateString);
			} catch (ParseException e) {
				log.log(Log.FINE, "_ParseException_");
			}
	  	Timestamp timestamp = new Timestamp(date.getTime());
	  	 return new LocalDate(LocalDate.NO_STATION, Location.NONE, timestamp);
	  }
	  
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.customerOutstandingInvoices
	 *	Added by 	:	A-5153 on Nov 08, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param accountRequestType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault 
	 *	Return type	: 	StatementOfAccountResponseType
	 */
	public StatementOfAccountResponseType customerOutstandingInvoices(StatementOfAccountRequestType accountRequestType) 
			throws ServiceFault, CustomerFault {
		log.entering(CustomerServiceBaseImpl.class.getName(), "customerOutstandingInvoices");
		Collection<ErrorDetailType> errorDetailTypes = new ArrayList<ErrorDetailType>();
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setCompanyCode(getCompanyCode());
		filterVO.setCustomerCode(accountRequestType.getRequestData().getListInvoiceFilter().getCustomerCode());
		filterVO.setFromPortal("PORTAL_INV");
		CustomerVO customerVO = null;
		CustomerBillingInvoiceDetailsVO billingInvoiceDetailsVO = null;
		try {
			customerVO = despatchRequest(LIST_CUSTOMER, filterVO);
			if(customerVO != null){
				billingInvoiceDetailsVO = despatchRequest("getBillingInvoiceDetails", filterVO);
			}
		} catch (WSBusinessException ws) {
			errorDetailTypes = constructCommonErrors(ws.getErrors());
		} catch (SystemException se) {
			errorDetailTypes = constructCommonErrors(se.getErrors());
		}
		
		StatementOfAccountResponseType accountResponseType = new StatementOfAccountResponseType();
		MessageHeaderType headerType = getMessageHeader();
		MessageHeaderType requestHeaderType = accountRequestType.getMessageHeader();
		if (requestHeaderType.getUserId() != null
				&& requestHeaderType.getUserId().length() > 0) {
			headerType.setUserId(requestHeaderType.getUserId());
		}
		if (requestHeaderType.getMessageID() != null
				&& requestHeaderType.getMessageID().length() > 0) {
			headerType.setMessageID(requestHeaderType.getMessageID());
		}
		if (requestHeaderType.getCorrelationID() != null
				&& requestHeaderType.getCorrelationID().length() > 0) {
			headerType.setCorrelationID(requestHeaderType.getCorrelationID());
		}
		accountResponseType.setMessageHeader(headerType);
		
		StatementOfAccountResponseDetailType responseDetail = new StatementOfAccountResponseDetailType();
		responseDetail.setRequestId(accountRequestType.getRequestData().getRequestId());
		
		if (customerVO != null) {
			CustomerDetailsType customerDetailsType = constructCustomerDetails(customerVO);

			if (billingInvoiceDetailsVO != null) {
				CustomerContactVO customerContactVO = billingInvoiceDetailsVO.getCustomerAccounting();
				if(customerContactVO != null ){
					AccountingPointOfContactType contactType = constructAccountingContactPoint(customerContactVO);
					customerDetailsType.setAccountingPointOfContact(contactType);
				}
				Collection<CustomerStatusViewVO> customerStatusViewVOs = billingInvoiceDetailsVO.getStatusView();
				if (customerStatusViewVOs != null && customerStatusViewVOs.size() > 0) {
					OutstandingPaymentStatusType statusType = constructOutstandingPaymentStatus(customerStatusViewVOs);
					customerDetailsType.setOutstandingPaymentStatus(statusType);
				}

				Collection<CustomerInvoiceDetailsVO> customerInvoiceDetailsVOs = billingInvoiceDetailsVO.getInvoiceDetails();
				if (customerInvoiceDetailsVOs != null && customerInvoiceDetailsVOs.size() > 0) {
					List<OutstandingInvoiceDetailsType> invoiceDetailsTypes = constructOutstandingInvoiceDetails(customerInvoiceDetailsVOs);
					customerDetailsType.getOutstandingInvoiceDetails().addAll(invoiceDetailsTypes);
				}

			}

			responseDetail.setCustomerDetails(customerDetailsType);
			// responseDetail.setStatus(STATUS_SUCCESS);

		} else if (errorDetailTypes != null && errorDetailTypes.size() > 0) {
			responseDetail.getErrorDetails().addAll(errorDetailTypes);
			//responseDetail.setStatus(STATUS_FAILURE);
		} else {
			//responseDetail.setStatus(STATUS_FAILURE);
			ErrorDetailType errorDetailsType = new ErrorDetailType();
			errorDetailsType
					.setErrorCode("customermanagement.customeroutstandinginvoices.portal.msg.err.nodatafound");
			errorDetailsType
					.setErrorDesc("No CustomerOutstandingInvoices found for the provided search criteria");
			responseDetail.getErrorDetails().add(errorDetailsType);
		}
		accountResponseType.setResponseDetails(responseDetail);
		return accountResponseType;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.constructCustomerDetails
	 *	Added by 	:	A-5153 on Nov 15, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param customerVO
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerDetailsType
	 */
	private CustomerDetailsType constructCustomerDetails(CustomerVO customerVO) {
		CustomerDetailsType customerDetailsType = new CustomerDetailsType();
		customerDetailsType.setCustomerCode(customerVO.getCustomerCode());
		customerDetailsType.setCustomerName(customerVO.getCustomerName());
		if (customerVO.getAccountNumber() != null) {
			customerDetailsType.setCustomerAccountNumber(customerVO
					.getAccountNumber());
		}
		CustomerBillingDetailsVO billingDetailsVO = customerVO
				.getCustomerBillingDetailsVO();
		if (billingDetailsVO != null) {
			CustomerAddressType addressType = new CustomerAddressType();
			if (billingDetailsVO.getStreet() != null
					&& billingDetailsVO.getStreet().trim().length() > 0) {
				addressType.setCustomerStreet(billingDetailsVO.getStreet());
			}
			if (billingDetailsVO.getLocation() != null
					&& billingDetailsVO.getLocation().trim().length() > 0) {
				addressType.setCustomerLocation(billingDetailsVO.getLocation());
			}
			if (billingDetailsVO.getCityCode() != null
					&& billingDetailsVO.getCityCode().trim().length() > 0) {
				addressType.setCustomerCity(billingDetailsVO.getCityCode());
			}
			if (billingDetailsVO.getState() != null
					&& billingDetailsVO.getState().trim().length() > 0) {
				addressType.setCustomerState(billingDetailsVO.getState());
			}
			if (billingDetailsVO.getEmail() != null
					&& billingDetailsVO.getEmail().trim().length() > 0) {/*modified for ICRD-313434*/
				addressType.setPrimaryEmail(billingDetailsVO.getEmail());
			}

			customerDetailsType.setCustomerAddress(addressType);
		}


		return customerDetailsType;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.constructOutstandingPaymentStatus
	 *	Added by 	:	A-5153 on Nov 15, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param customerStatusViewVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	OutstandingPaymentStatusType
	 */
	private OutstandingPaymentStatusType constructOutstandingPaymentStatus(
			Collection<CustomerStatusViewVO> customerStatusViewVOs) {
		int totalItemCount = 0;
		double totalIAmount = 0;
		OutstandingPaymentStatusType statusType = new OutstandingPaymentStatusType();
		for (CustomerStatusViewVO statusViewVO : customerStatusViewVOs) {
			if (UNPAID.equals(statusViewVO.getAwbType())) {
				statusType.setUnpaidInvoiceCount(BigDecimal
						.valueOf(statusViewVO.getAwbCount()));
				totalItemCount += statusViewVO.getAwbCount();
				statusType.setUnpaidTotal(BigDecimal.valueOf(statusViewVO
						.getAwbAmount()));
				totalIAmount += statusViewVO.getAwbAmount();
			} else if (SHORTPAID.equals(statusViewVO.getAwbType())) {
				statusType.setShortPaidInvoiceCount(BigDecimal
						.valueOf(statusViewVO.getAwbCount()));
				totalItemCount += statusViewVO.getAwbCount();
				statusType.setShortPaidTotal(BigDecimal.valueOf(statusViewVO
						.getAwbAmount()));
				totalIAmount += statusViewVO.getAwbAmount();
			} else if (OVERPAID.equals(statusViewVO.getAwbType())) {
				statusType.setOverPaidInvoiceCount(BigDecimal
						.valueOf(statusViewVO.getAwbCount()));
				totalItemCount += statusViewVO.getAwbCount();
				statusType.setOverPaidTotal(BigDecimal.valueOf(statusViewVO
						.getAwbAmount()));
				totalIAmount += statusViewVO.getAwbAmount();
			}
			if (statusType.getCurrencyCode() == null
					&& statusViewVO.getCurrencyCode() != null) {
				statusType.setCurrencyCode(statusViewVO.getCurrencyCode());
			}
			/*modified totalCount and totalAmount logic for ICRD-313434*/
		}
		statusType.setTotalItemCount(BigDecimal.valueOf(totalItemCount));
		statusType.setTotalAmount(BigDecimal.valueOf(totalIAmount));
		return statusType;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.constructOutstandingInvoiceDetails
	 *	Added by 	:	A-5153 on Nov 15, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param customerInvoiceDetailsVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	List<OutstandingInvoiceDetailsType>
	 */
	private List<OutstandingInvoiceDetailsType> constructOutstandingInvoiceDetails(
			Collection<CustomerInvoiceDetailsVO> customerInvoiceDetailsVOs) {

		List<OutstandingInvoiceDetailsType> outstandingInvoiceDetailsTypes = new ArrayList<OutstandingInvoiceDetailsType>();
		OutstandingInvoiceDetailsType invoiceDetailsType = null;
		for (CustomerInvoiceDetailsVO invoiceDetailsVO : customerInvoiceDetailsVOs) {
			invoiceDetailsType = new OutstandingInvoiceDetailsType();
			if (invoiceDetailsVO.getInvoiceNumber() != null
					&& invoiceDetailsVO.getInvoiceNumber().trim().length() > 0) {
				invoiceDetailsType.setInvoiceNumber(invoiceDetailsVO.getInvoiceNumber());
			}
			if (invoiceDetailsVO.getInvoiceDate() != null
					&& invoiceDetailsVO.getInvoiceDate().trim().length() > 0) {
				invoiceDetailsType.setInvoiceDate(invoiceDetailsVO.getInvoiceDate());
			}
			if (invoiceDetailsVO.getPeriodFromDate() != null
					&& invoiceDetailsVO.getPeriodFromDate().trim().length() > 0) {
				invoiceDetailsType.setPeriodFrom(invoiceDetailsVO.getPeriodFromDate());
			}
			if (invoiceDetailsVO.getPeriodToDate() != null
					&& invoiceDetailsVO.getPeriodToDate().trim().length() > 0) {
				invoiceDetailsType.setPeriodToo(invoiceDetailsVO.getPeriodToDate());
			}
			if (invoiceDetailsVO.getStatus() != null
					&& invoiceDetailsVO.getStatus().trim().length() > 0) {
				invoiceDetailsType.setInvoiceStatus(invoiceDetailsVO.getStatus());
			}
			invoiceDetailsType.setAgeInDays(BigDecimal.valueOf(invoiceDetailsVO.getAge()));
			/* added for ICRD-313434 starts */
			Collection<CustomerInvoiceAWBDetailsVO> customerInvoiceAWBDetailsVOs = invoiceDetailsVO.getCustomerInvoiceAWBDetailsVOs();
			if(customerInvoiceAWBDetailsVOs!=null && customerInvoiceAWBDetailsVOs.size()>0){
				List<AwbDetailsType> awbDetailsTypes =  constructOutstandingAWBDetails(customerInvoiceAWBDetailsVOs);
				invoiceDetailsType.getAwbDetails().addAll(awbDetailsTypes);
			}
			/* added for ICRD-313434 ends*/
			outstandingInvoiceDetailsTypes.add(invoiceDetailsType);
		}
		return outstandingInvoiceDetailsTypes;
	}
	/** 
     * @descriptor method to set awb level details 
     * @author for ICRD-313434
     * @param customerInvoiceAWBDetailsVOs 
     * @return awbDetailsTypes
     * @throws 
     */ 
	private List<AwbDetailsType> constructOutstandingAWBDetails(Collection<CustomerInvoiceAWBDetailsVO> customerInvoiceAWBDetailsVOs){
		List<AwbDetailsType> awbDetailsTypes = new ArrayList<AwbDetailsType>();
		AwbDetailsType awbDetailsType = null;
		for(CustomerInvoiceAWBDetailsVO awbDetailsVO : customerInvoiceAWBDetailsVOs){
			awbDetailsType = new AwbDetailsType();
			if(awbDetailsVO.getShipmentPrefix() != null && awbDetailsVO.getShipmentPrefix().trim().length()>0){
				awbDetailsType.setShipmentPrefix(awbDetailsVO.getShipmentPrefix());
			}
			if(awbDetailsVO.getAwbNumber() != null && awbDetailsVO.getAwbNumber().trim().length()>0){
				awbDetailsType.setAwbNumber(awbDetailsVO.getAwbNumber());
			}
			if(awbDetailsVO.getOrigin() != null && awbDetailsVO.getOrigin().trim().length()>0){
				awbDetailsType.setAwbOrigin(awbDetailsVO.getOrigin());
			}
			if(awbDetailsVO.getDestination() != null && awbDetailsVO.getDestination().trim().length()>0){
				awbDetailsType.setAwbDestination(awbDetailsVO.getDestination());
			}
			if(awbDetailsVO.getStatus() != null && awbDetailsVO.getStatus().trim().length()>0){
				awbDetailsType.setStatus(awbDetailsVO.getStatus());
			}
			DecimalFormat decimalFormat = new DecimalFormat("##.###");
			decimalFormat.setMaximumFractionDigits(3);
			decimalFormat.setMaximumIntegerDigits(14);
			awbDetailsType.setAmountDue(new BigDecimal(decimalFormat.format(awbDetailsVO.getAmountDue())));
			awbDetailsTypes.add(awbDetailsType);
		}
		return awbDetailsTypes;
	}
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.customerAgeingDetails
	 *	Added by 	:	A-5153 on Nov 05, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param ageingReportRequestType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault 
	 *	Return type	: 	AgeingReportResponseType
	 */
	public AgeingReportResponseType customerAgeingDetails(AgeingReportRequestType ageingReportRequestType)
			throws ServiceFault, CustomerFault {
		log.entering(CustomerServiceBaseImpl.class.getName(), "customerAgeingDetails");
		Collection<ErrorDetailType> errorDetailTypes = new ArrayList<ErrorDetailType>();
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setCompanyCode(getCompanyCode());
		filterVO.setCustomerCode(ageingReportRequestType.getRequestData().getCustomerCode());
		filterVO.setFromPortal("PORTAL_AGE");
		Collection<CustomerReceivablesAgeingVO> customerReceivablesAgeingVOs = null;
		CustomerBillingInvoiceDetailsVO billingInvoiceDetailsVO = null;
		try {
			billingInvoiceDetailsVO = despatchRequest("getBillingInvoiceDetails", filterVO);
			if (billingInvoiceDetailsVO != null) {
				customerReceivablesAgeingVOs = billingInvoiceDetailsVO.getReceivablesAgeing();
			}
		} catch (WSBusinessException ws) {
			errorDetailTypes = constructCommonErrors( ws.getErrors());;
		} catch (SystemException se) {
			errorDetailTypes = constructCommonErrors( se.getErrors());
		}
		AgeingReportResponseType responseType = new AgeingReportResponseType();
		MessageHeaderType headerType = getMessageHeader();
		MessageHeaderType requestHeaderType = ageingReportRequestType.getMessageHeader();
		if (requestHeaderType.getUserId() != null
				&& requestHeaderType.getUserId().length() > 0) {
			headerType.setUserId(requestHeaderType.getUserId());
		}
		if (requestHeaderType.getMessageID() != null
				&& requestHeaderType.getMessageID().length() > 0) {
			headerType.setMessageID(requestHeaderType.getMessageID());
		}
		if (requestHeaderType.getCorrelationID() != null
				&& requestHeaderType.getCorrelationID().length() > 0) {
			headerType.setCorrelationID(requestHeaderType.getCorrelationID());
		}
		responseType.setMessageHeader(headerType);
		AgeingReportResponseDetailType responseDetail = new AgeingReportResponseDetailType();
		responseDetail.setRequestId(ageingReportRequestType.getRequestData().getRequestId());
		responseDetail.setCustomerCode(ageingReportRequestType.getRequestData().getCustomerCode());
		
		if (customerReceivablesAgeingVOs != null
				&& customerReceivablesAgeingVOs.size() > 0) {
			AgeingBucketsType bucketsType = new AgeingBucketsType();
			AgeingBucketsDaysType daysType = null;
			for (CustomerReceivablesAgeingVO ageingVO : customerReceivablesAgeingVOs) {
				daysType = new AgeingBucketsDaysType();
				daysType.setCountOfAWBs(BigInteger.valueOf(ageingVO.getAwbCount()));
				daysType.setTotalAWBvalue(BigDecimal.valueOf(ageingVO.getAwbValue()));
				if (ONE_TO_FIFTEEN.equals(ageingVO.getAgeing())) {
					bucketsType.setOneToFifteenDays(daysType);
				} else if (SIXTEEN_TO_THIRTY.equals(ageingVO.getAgeing())) {
					bucketsType.setSixteenToThirtyDays(daysType);
				} else if (THIRTYONE_TO_FOURTYFIVE.equals(ageingVO.getAgeing())) {
					bucketsType.setThirtyOneToFourtyFiveDays(daysType);
				} else if (FOURTYSIX_TO_SIXTY.equals(ageingVO.getAgeing())) {
					bucketsType.setFourtySixToSixtyDays(daysType);
				} else if (SIXTYONE_TO_NINETY.equals(ageingVO.getAgeing())) {
					bucketsType.setSixtyOneToNinetyDays(daysType);
				} else if (NINETYONE_TO_ONETWENTY.equals(ageingVO.getAgeing())) {
					bucketsType.setNinetyOneToOneTwentyDays(daysType);
				} else if (ABOVE_ONETWENTY.equals(ageingVO.getAgeing())) {
					bucketsType.setAboveOneTwentyDays(daysType);
				}
			}
			responseDetail.getAgeingBuckets().add(bucketsType);
			responseDetail.setStatus(STATUS_SUCCESS);
		} else if (errorDetailTypes != null && errorDetailTypes.size() > 0) {
			responseDetail.getErrorDetails().addAll(errorDetailTypes);
			responseDetail.setStatus(STATUS_FAILURE);
		} else {
			responseDetail.setStatus(STATUS_FAILURE);
			ErrorDetailType errorDetailsType = new ErrorDetailType();
			errorDetailsType
					.setErrorCode("customermanagement.customerageingdetails.portal.msg.err.nodatafound");
			errorDetailsType
					.setErrorDesc("No CustomerAgeingDetails found for the provided search criteria");
			responseDetail.getErrorDetails().add(errorDetailsType);
		}
		responseType.setResponseDetails(responseDetail);
		return responseType;
	}
		
	/**
	 * 
	 * 	Method		:	CustomerServiceBaseImpl.constructCommonErrors
	 *	Added by 	:	A-5153 on Nov 13, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param errorVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	List<ErrorDetailType>
	 */
	private List<ErrorDetailType> constructCommonErrors(
			Collection<ErrorVO> errorVOs) {
		ErrorDetailType error = null;
		List<ErrorDetailType> errorDetailTypes = null;
		if (errorVOs != null && errorVOs.size() > 0) {
			errorDetailTypes = new ArrayList<ErrorDetailType>(errorVOs.size());
			for (ErrorVO errorVO : errorVOs) {
				error = new ErrorDetailType();
				error.setErrorCode(errorVO.getErrorCode());
				errorDetailTypes.add(error);
			}
		} else {
			error = new ErrorDetailType();
			error.setErrorCode("UNK Exception");
			return Arrays.asList(new ErrorDetailType[] { error });
		}
		return errorDetailTypes;
	}
	/** 
     * @descriptor method to set customer accounting contact details
     * @author for ICRD-317262
     * @param customerContactVO 
     * @return contactType
     * @throws 
     */ 
	private AccountingPointOfContactType constructAccountingContactPoint(CustomerContactVO customerContactVO){
		AccountingPointOfContactType contactType = new AccountingPointOfContactType();
		if(customerContactVO.getContactCustomerCode() != null){
			contactType.setAccountingPointOfContactName(customerContactVO.getContactCustomerCode());
		}
		if(customerContactVO.getTelephone() != null){
			contactType.setPhoneNumber(customerContactVO.getTelephone());
		}
		if(customerContactVO.getEmailAddress() != null){
			contactType.setEmail(customerContactVO.getEmailAddress());
		}
		return contactType;
	}
	
	//Methods for POA Portal 
	
	private Collection<ErrorVO> poaValidations(CustomerType customerDetails) throws SystemException, WSBusinessException {
		Collection<ErrorVO> errors = new ArrayList<>();
		Collection <CollectionAgentDetailType> collectionAgentDetailsTypes = customerDetails.getCollectionagentDetails();
		PoaParametersType poaParametersType = null;
		String agentCode = customerDetails.getCustomerCode();
		
		for(CollectionAgentDetailType collectionAgentDetailType : collectionAgentDetailsTypes){
			poaParametersType = collectionAgentDetailType.getPoaParameters();
			if(SINGLE_POA.equals(poaParametersType.getPoaType()) && poaParametersType.getSinglePoaInfo() != null){			
				try {
					awbValidation(poaParametersType.getSinglePoaInfo(),errors,agentCode);
				}
				catch (ProxyException e) {
					errors.add(new ErrorVO(e.getMessage()));
					throw new SystemException(e.getMessage(), e);
					
				}		catch (WSBusinessException e) {
					errors.add(new ErrorVO(e.getMessage()));
					throw new WSBusinessException(e.getMessage(), e);
					
				}
			}
			if((GENPOA.equals(poaParametersType.getPoaType()) || (SPLPOA.equals(poaParametersType.getPoaType())))){
				agentCode = collectionAgentDetailType.getCustomerCode();
				try {
					genSplValidations(poaParametersType,errors,agentCode);
				} catch (BusinessDelegateException e) {
					errors.add(new ErrorVO(e.getMessage()));
					throw new SystemException(e.getMessage(), e);
				} catch (WSBusinessException e) {
					errors.add(new ErrorVO(e.getMessage()));
					throw new SystemException(e.getMessage(), e);
				} catch (ProxyException e) {
					errors.add(new ErrorVO(e.getMessage()));
					throw new SystemException(e.getMessage(), e);
				}				
			}
		}
		return errors;
	}


	private Collection<ErrorVO> awbValidation(String singlePoaInfo, Collection<ErrorVO> errors, String agentCode) throws SystemException, WSBusinessException, ProxyException {		
		String shipmentPrefix = singlePoaInfo.split(" ")[0];
		String mstdocnumber = singlePoaInfo.split(" ")[1];		
			validateAWB(shipmentPrefix,mstdocnumber, errors,agentCode);
		return errors;
	}

	private void validateAWB(String shipmentPrefix, String mstdocnumber, Collection<ErrorVO> errors, String agentCode) throws SystemException, WSBusinessException {
		Collection<ShipmentVO> shipmentVOs = null;
		Collection<CustomerAgentVO> customerAgentVOs = null;

		ShipmentFilterVO shipmentFilterVO = populateShipmentFilterVO(getCompanyCode(), shipmentPrefix, mstdocnumber);
		shipmentVOs = despatchRequest("validateShipmentDetails", shipmentFilterVO);
		if (shipmentVOs != null && !shipmentVOs.isEmpty()) {
			customerAgentVOs = despatchRequest("validateSinglePoa", shipmentFilterVO);
			if (customerAgentVOs != null && !customerAgentVOs.isEmpty()) {
				for(CustomerAgentVO customerAgentVO :customerAgentVOs) {
					if(customerAgentVO.getCustomerCode()!=null&& agentCode.contentEquals(customerAgentVO.getCustomerCode())) {
						errors.add(new ErrorVO("autobroker.createautobroker.errorPOASameBroker"));
					}else {
					 	errors.add(new ErrorVO("autobroker.createautobroker.errorPOAexistsDifferentBroker"));
					}
				}
				
			} else {
				Collection<ShipmentHistoryVO> shpHisVOs = null;
				ShipmentHistoryFilterVO shipmentHistoryFilterVO = populateShipmentHistoryFilterVO(shipmentVOs);
				shpHisVOs = despatchRequest("findShipmentHandlingHistory", shipmentHistoryFilterVO);
				if (shpHisVOs != null && !shpHisVOs.isEmpty()) {
					errors.add(new ErrorVO("autobroker.createautobroker.errorflightArrived")); 
				}
			}
		} else {
			errors.add(new ErrorVO("autobroker.createautobroker.errorAwbDoesnotExist"));
		}
	}

	private ShipmentFilterVO populateShipmentFilterVO(String companyCode, String shipmentPrefix, String mstdocnumber) {
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
        shipmentFilterVO.setCompanyCode(companyCode);
        shipmentFilterVO.setShipmentPrefix(shipmentPrefix);
        shipmentFilterVO.setDocumentNumber(mstdocnumber);                  
        shipmentFilterVO.setRetrieveMasterOnly(true);
        return shipmentFilterVO;
	}
	
	private ShipmentHistoryFilterVO populateShipmentHistoryFilterVO(Collection<ShipmentVO> shipmentVOs) {
		ShipmentVO shipmentVO=shipmentVOs.iterator().next();
		ShipmentHistoryFilterVO shipmentHistoryFilterVO=new ShipmentHistoryFilterVO();
		shipmentHistoryFilterVO.setCompanyCode(shipmentVO.getCompanyCode());
		shipmentHistoryFilterVO.setMasterDocumentNumber(shipmentVO.getMasterDocumentNumber());
		shipmentHistoryFilterVO.setDocumentOwnerId(shipmentVO.getOwnerId());
		shipmentHistoryFilterVO.setDuplicateNumber(shipmentVO.getDuplicateNumber());
		shipmentHistoryFilterVO.setAirportCode(shipmentVO.getDestination());
		shipmentHistoryFilterVO.setTransactionCode("ARR");
		return shipmentHistoryFilterVO;
	}
	
	private void genSplValidations(PoaParametersType poaParametersType, Collection<ErrorVO> errors, String agentCode)
			throws SystemException, WSBusinessException, BusinessDelegateException, ProxyException {
				
			validateConsigneeDetails(poaParametersType,errors,agentCode);

	}
	
	private void validateConsigneeDetails(PoaParametersType poaParametersType, Collection<ErrorVO> errors, String agentCode) throws WSBusinessException, SystemException {
		
		ErrorVO error = null;
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setCustomerCode(agentCode);
		filterVO.setCompanyCode(getCompanyCode());
		filterVO.setSource(SOURCE_POA);
		
		CustomerVO customerVO = despatchRequest(LIST_CUSTOMER, filterVO);
		
		if(customerVO != null && customerVO.getCustomerAgentVOs()!=null && !customerVO.getCustomerAgentVOs().isEmpty() ){
			error=validateSpeacialParameters(customerVO,poaParametersType,poaParametersType.getPoaType());			
		}
		if(error!=null){
			errors.add(error);
		}		
	}

	private ErrorVO validateSpeacialParameters(CustomerVO customerVO, PoaParametersType poaParametersType,
			String poaType) {
		ErrorVO error = null;
		for(CustomerAgentVO cutomerAgtVO:customerVO.getCustomerAgentVOs())
		{
			if(!SINGLE_POA.equalsIgnoreCase(cutomerAgtVO.getPoaType())){
				if(cutomerAgtVO.getDestination().equalsIgnoreCase(String.join(",", poaParametersType.getDestinationCode()))){
					if(GENPOA.equalsIgnoreCase(poaType)){
						error= new ErrorVO("autobroker.createautobroker.GenSplerror.poaalreadyexists");
						
					}
					if(SPLPOA.equalsIgnoreCase(poaType)){
						error=validateSpecialPoaDetails(cutomerAgtVO,poaParametersType);
					}
					
				}
				if(error!= null){
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					break;
				}
			}
		}
		return error;
	}

	private ErrorVO validateSpecialPoaDetails(CustomerAgentVO cutomerAgtVO, PoaParametersType poaParametersType) {
		ErrorVO error = null;
		if(cutomerAgtVO.getScc()==null && cutomerAgtVO.getExcludedScc() ==null && cutomerAgtVO.getExcludedOrigins() ==null && cutomerAgtVO.getIncludedOrigins() ==null){
			error= new ErrorVO("autobroker.createautobroker.GenSplerror.AnotherPoacreated");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			
		}
		else{
     		boolean validationPassed=false;
			String sccCodeInclude=String.join(",",poaParametersType.getIncludedHandlingCodes());
			String sccCodeExclude=String.join(",",poaParametersType.getExcludedHandlingCodes());
			String originInclude=String.join(",", poaParametersType.getIncludedOriginCodes());
			String originExclude=String.join(",", poaParametersType.getExcludedOriginCodes());
			
			String[] sccCodeIncludeAgtVO={};
			String[] sccCodeExcludeAgtVO={};
			String[] originIncludeAgtVO={};
			String[] originExcludeAgtVO={};
			if(cutomerAgtVO.getScc()!=null){
				sccCodeIncludeAgtVO=cutomerAgtVO.getScc().split(",");
			}
			if(cutomerAgtVO.getExcludedScc()!=null){
				sccCodeExcludeAgtVO=cutomerAgtVO.getExcludedScc().split(",");
			}
			if(cutomerAgtVO.getIncludedOrigins()!=null){
				originIncludeAgtVO=cutomerAgtVO.getIncludedOrigins().split(",");
			}
			if(cutomerAgtVO.getExcludedOrigins()!=null){
				originExcludeAgtVO=cutomerAgtVO.getExcludedOrigins().split(",");
			}
			
			
			boolean sccError=validateSCC(sccCodeInclude,sccCodeExclude,sccCodeIncludeAgtVO,sccCodeExcludeAgtVO);
			boolean originError=validateOrigin(originInclude,originExclude,originIncludeAgtVO,originExcludeAgtVO);
			if(!originError || !sccError){
            	validationPassed=true;
            }
			
			if(!validationPassed){
				error= new ErrorVO("autobroker.createautobroker.GenSplerror.Specialpoasameparameters");
			}
		}
	   return error;
	}
	private boolean validateOrigin(String originInclude, String originExclude, String[] originIncludeAgtVO,
			String[] originExcludeAgtVO) {
		boolean validateOrigin=false;
		if(originInclude.isEmpty() && originIncludeAgtVO.length==0){
			validateOrigin=true;
		}else if(originInclude.isEmpty() && originIncludeAgtVO.length>0){
			validateOrigin=validateCombination(originExclude,originIncludeAgtVO);
		}else if(!originInclude.isEmpty() && originIncludeAgtVO.length==0){
			validateOrigin=validateCombination(String.join(",", originExcludeAgtVO),originInclude.split(","));
		}else{
			if(!originInclude.isEmpty() && originIncludeAgtVO.length>0){
				validateOrigin=checkForSplParameters(originIncludeAgtVO,originInclude);
			}
		}
		return validateOrigin;
	}

	private boolean validateSCC(String sccCodeInclude, String sccCodeExclude,
			String[] sccCodeIncludeAgtVO, String[] sccCodeExcludeAgtVO) {
		boolean validateScc=false;
		if(sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length==0){
			validateScc=true;
		}else if(sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length>0){
			validateScc=validateCombination(sccCodeExclude,sccCodeIncludeAgtVO);
		}else if(!sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length==0){
			validateScc=validateCombination(String.join(",", sccCodeExcludeAgtVO),sccCodeInclude.split(","));
		}else{
			if(!sccCodeInclude.isEmpty() && sccCodeIncludeAgtVO.length>0){
				validateScc=checkForSplParameters(sccCodeIncludeAgtVO,sccCodeInclude);
			}
		}
		return validateScc;
	}

	private boolean validateCombination(String param, String[] paramArry) {
		boolean validationPassed=false;
		if(!param.isEmpty() && paramArry.length>0){
			for(String pramValue:paramArry){
				if(! param.contains(pramValue)){
					validationPassed=true;
					break;
				}
			}
		}else{
			validationPassed=true;
		}
		return validationPassed;
	}
	private boolean checkForSplParameters(String[] parameterArray, String parameter) {
		boolean check=false;
		if(parameterArray.length>0 && parameter != null){
			for(String param:parameterArray){
				if(parameter.contains(param)){
					check=true;
					break;
				}
			}
		}
		return check;
		
	}
	
	private CustomerVO populateCustomerVOSavePortal(CustomerType customerDetails)
			throws WSBusinessException, SystemException {

		// listing customer to save
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setCompanyCode(getCompanyCode());
		filterVO.setCustomerCode(customerDetails.getCustomerCode());
		filterVO.setFromPortal("POA_PORTAL");
		CustomerVO customerVO = despatchRequest(LIST_CUSTOMER, filterVO);
		if (customerVO != null) {
			Collection<CollectionAgentDetailType> collectionAgentDetailsTypes = customerDetails
					.getCollectionagentDetails();
		PoaParametersType poaParametersType = null;

		Collection<CustomerAgentVO> customerPOADetails = new ArrayList<>();

		customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
		customerVO.setSource(SOURCE_POA);

		for (CollectionAgentDetailType collectionAgentDetailType : collectionAgentDetailsTypes) {
			poaParametersType = collectionAgentDetailType.getPoaParameters();
			CustomerAgentVO agentVO = new CustomerAgentVO();
			agentVO.setCompanyCode(getCompanyCode());
			
			
			String operationFlag = poaParametersType.getOperationalFlag().toString();
			agentVO.setOperationFlag(operationFlag);

			String stationCode = customerDetails.getStationCode(); // doubt
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
				agentVO.setRemarks("Deleted from E-Services ");

				localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				localDate.setDate(poaParametersType.getPoaCreationDate().substring(0, 11));
				agentVO.setSequenceNumber(poaParametersType.getSequenceNumber());	 
			}
			
			vldStartDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			if(collectionAgentDetailType.getContractStartDate()!=null){
				 vldStartDate.setDate(collectionAgentDetailType.getContractStartDate().substring(0,11));
			}
			vldEndDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			vldEndDate.setDate(VLD_END_DATE);
			
			agentVO.setPoaCreationTime(localDate);
			agentVO.setValidityStartDate(vldStartDate);
			agentVO.setValidityEndDate(vldEndDate);
			String poaType = poaParametersType.getPoaType();
			agentVO.setPoaType(poaType);

			switch (poaType) {
			case GENPOA:
			case SPLPOA:
				String consigneeName=collectionAgentDetailType.getConsigneeName();
					agentVO.setCustomerCode(customerDetails.getCustomerCode()); 
					agentVO.setAgentCode(collectionAgentDetailType.getCustomerCode());
					agentVO.setAgentName(consigneeName);
				agentVO.setDestination(poaParametersType.getDestinationCode());
				
				//Adding POA Documents
				if(!OPERATION_FLAG_DELETE.equals(operationFlag)) {
				CustomerSupportingDocumentVO customerSupportingDocumentVO =new CustomerSupportingDocumentVO();
					  customerSupportingDocumentVO.setFileName(poaParametersType.
					  getPoaDocumentFileName());
					try {
						customerSupportingDocumentVO
								.setFileData(Base64Encoder.decode(poaParametersType.getPoaDocumentUploadFileData()));
					} catch (EncryptionFailedException e) {
						log.log(Log.SEVERE, "EncryptionFailedException caught", e);
					}
					 
					 
				customerSupportingDocumentVO.setContentType("application/pdf");
				customerSupportingDocumentVO.setOperationFlag("I");
				customerSupportingDocumentVO.setTriggerPoint("POAPortal");
				List<CustomerSupportingDocumentVO> customerSupportingDocumentVOs =new ArrayList<>();
				customerSupportingDocumentVOs.add(customerSupportingDocumentVO);
				agentVO.setSupportingDocumentVOs(customerSupportingDocumentVOs);
				}
				
				if (SPLPOA.equals(poaType)) {
					agentVO.setScc(poaParametersType.getIncludedHandlingCodes());
					agentVO.setExcludedScc(poaParametersType.getExcludedHandlingCodes());
					agentVO.setIncludedOrigins(poaParametersType.getIncludedOriginCodes());
					agentVO.setExcludedOrigins(poaParametersType.getExcludedOriginCodes());
				}

				break;
			case SINGLE_POA:
				/* awbNumber as '020 02002000' */
				agentVO.setCustomerCode(collectionAgentDetailType.getCustomerCode());
				agentVO.setAwbNum(poaParametersType.getSinglePoaInfo());
				break;
			default:
				break;
			}
			customerPOADetails.add(agentVO);
		}

		String poaType = customerPOADetails.iterator().next().getPoaType();
		if (SINGLE_POA.equals(poaType)) {
			customerVO.setCustomerAgentVOs(customerPOADetails);
		} else {
			customerVO.setCustomerConsigneeVOs(customerPOADetails);
		}
		}else{
			throw new WSBusinessException("No customer exists");
		}
		return customerVO;
	}
}
