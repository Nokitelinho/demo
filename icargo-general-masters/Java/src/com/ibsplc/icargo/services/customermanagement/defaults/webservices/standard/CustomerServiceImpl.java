/**
 *	Java file	: 	com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerServiceImpl.java
 *
 *	Created by	:	A-4789
 *	Created on	:	12-Oct-2012
 *
 *  Copyright 2012 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.customermanagement.defaults.webservices.standard;

import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingReportRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.AgeingReportResponseType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ListCustomerRequest;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ListCustomerResponse;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.StatementOfAccountRequestType;
import com.ibsplc.icargo.business.customermanagement.defaults.types.standard.StatementOfAccountResponseType;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

/**
 * This class was generated by Apache CXF 2.4.3
 * 2012-10-15T17:14:12.035+05:30
 * Generated source version: 2.4.3
 * 
 */
/**
 *	Java file	: 	com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerServiceImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4789	:	12-Oct-2012	:	Draft
 */
@javax.jws.WebService(
                      serviceName = "CustomerService",
                      portName = "CustomerServiceBinding_SOAP",
                      targetNamespace = "http://www.ibsplc.com/icargo/services/CustomerService/standard/2012/12/12_01",
                      wsdlLocation = "file:./wsdl/customermanagement/defaults/CustomerService_v0.1.wsdl",
                      endpointInterface = "com.ibsplc.icargo.services.customermanagement.defaults.webservices.standard.CustomerService")
@Module("customermanagement")
@SubModule("defaults")
public class CustomerServiceImpl extends CustomerServiceBaseImpl implements CustomerService {

	/**
   * 
   *	Overriding Method	:	@see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService
   *#getCustomer(com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerRequest)
   *	Added by 			: A-4789 on 22-Nov-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerRequest
	 *	Parameters	:	@return 
   *	Parameters	:	@throws ServiceFault
   *	Parameters	:	@throws CustomerFault
	 */
    public CustomerResponse getCustomer(CustomerRequest customerRequest) throws ServiceFault , CustomerFault    {
    	return super.getCustomerDetails(customerRequest);
	}

	/**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService
     *	#updateStatus(com.ibsplc.icargo.services.customermanagement.defaults.webservices.types.CustomerStatusUpdateRequest)
     *	Added by 			: A-4789 on 17-Oct-2012
     * 	Used for 	:
     *	Parameters	:	@param customerStatusUpdateRequest
     *	Parameters	:	@return
     *	Parameters	:	@throws ServiceFault
     *	Parameters	:	@throws CustomerFault
     */
    public CustomerStatusUpdateResponse updateStatus(CustomerStatusUpdateRequest customerStatusUpdateRequest) 
    throws ServiceFault, CustomerFault { 
    	return super.updateCustomerStatus(customerStatusUpdateRequest);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService
	 *	#saveCertificate(com.ibsplc.icargo.services.customermanagement.defaults.webservices.types.CustomerCertificateSaveRequest)
	 *	Added by 			: A-4789 on 17-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerCertificateSaveRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault
	 */
    public CustomerCertificateSaveResponse saveCertificate(CustomerCertificateSaveRequest customerCertificateSaveRequest) 
    throws ServiceFault, CustomerFault { 
    	return super.saveCertificateDetails(customerCertificateSaveRequest);
	}

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService
	 *  #saveCustomer(com.ibsplc.icargo.services.customermanagement.defaults.webservices.types.CustomerSaveRequest)
	 *	Added by 			: A-4789 on 17-Oct-2012
	 * 	Used for 	:	Used for saving the customer details
	 *	Parameters	:	@param customerSaveRequest
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault
	 */
    public CustomerSaveResponse saveCustomer(CustomerSaveRequest customerSaveRequest) throws ServiceFault, CustomerFault { 
    	return super.saveCustomerDetails(customerSaveRequest);
    } 
    
    /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.services.customermanagement.defaults.webservices.standard.CustomerServiceBaseImpl#listCustomers(com.ibsplc.icargo.business.customermanagement.defaults.types.standard.ListCustomerRequest)
     *	Added by 			: A-4816 on 16-Jun-2015
     * 	Used for 	:
     *	Parameters	:	@param listCustomerRequest
     *	Parameters	:	@return
     *	Parameters	:	@throws ServiceFault
     */
    public ListCustomerResponse listCustomers(ListCustomerRequest listCustomerRequest) throws ServiceFault     {
    	return super.listCustomers(listCustomerRequest);
	}

    /**
     * 
     *	Overriding Method	:	CustomerServiceBaseImpl#customerAgeingDetails
     *	Added by 	: A-5153 on Nov 14, 2018
     * 	Used for 	:
     *	Parameters	:	@param ageingReportRequestType
     *	Parameters	:	@return
     *	Parameters	:	@throws ServiceFault
     *	Parameters	:	@throws CustomerFault
     */
	public AgeingReportResponseType customerAgeingDetails(AgeingReportRequestType ageingReportRequestType)
			throws ServiceFault, CustomerFault {
		return super.customerAgeingDetails(ageingReportRequestType);
	}

	/**
	 * 
	 *	Overriding Method	:	CustomerServiceBaseImpl#customerOutstandingInvoices
	 *	Added by 	: A-5153 on Nov 14, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param accountRequestType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ServiceFault
	 *	Parameters	:	@throws CustomerFault
	 */
	public StatementOfAccountResponseType customerOutstandingInvoices(StatementOfAccountRequestType accountRequestType)
			throws ServiceFault, CustomerFault {
		return super.customerOutstandingInvoices(accountRequestType);
	}
	
}