
/**
 * Please modify this class to meet your needs
 * This class is not complete
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
 * 2012-11-21T18:24:34.611+05:30
 * Generated source version: 2.4.3
 * 
 */

@javax.jws.WebService(
                      serviceName = "CustomerService",
                      portName = "CustomerService",
                      targetNamespace = "http://www.ibsplc.com/icargo/services/CustomerService/standard/2012/12/12_01",
                      wsdlLocation = "file:./wsdl/customermanagement/defaults/CustomerService_v0.1.wsdl",
                      endpointInterface = "com.ibsplc.icargo.services.customermanagement.defaults.webservices.standard.CustomerService")
@Module("customermanagement")
@SubModule("defaults")                    
public class CustomerServiceJMSImpl extends CustomerServiceBaseImpl implements CustomerService {

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService#getCustomer(com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerRequest  customerRequest )*
     */
    public CustomerResponse getCustomer(CustomerRequest customerRequest) 
    throws ServiceFault , CustomerFault    {
    	return super.getCustomerDetails(customerRequest);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService#updateStatus(com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerStatusUpdateRequest  customerStatusUpdateRequest )*
     */
    public CustomerStatusUpdateResponse updateStatus(CustomerStatusUpdateRequest customerStatusUpdateRequest) 
    throws ServiceFault , CustomerFault    {
    	return super.updateCustomerStatus(customerStatusUpdateRequest);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService#saveCertificate(com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerCertificateSaveRequest  customerCertificateSaveRequest )*
     */
    public CustomerCertificateSaveResponse saveCertificate(CustomerCertificateSaveRequest customerCertificateSaveRequest) throws ServiceFault , CustomerFault {
    	return super.saveCertificateDetails(customerCertificateSaveRequest);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.services.customermanagement.defaults.webservices.CustomerService#saveCustomer(com.ibsplc.icargo.business.customermanagement.defaults.types.standard.CustomerSaveRequest  customerSaveRequest )*
     */
    public CustomerSaveResponse saveCustomer(CustomerSaveRequest customerSaveRequest) throws ServiceFault , CustomerFault    {
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
     *	Added by 	: 	A-5153 on Nov 14, 2018
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
	 *	Added by 	: 	A-5153 on Nov 14, 2018
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