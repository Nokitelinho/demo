/*
 * SharedCustomerProxy.java Created on apr 23, 2006 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.customermanagement.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class represents the product proxy for shared customer subsystem
 *
 * @author A-2048
 *
 *
 **/
 
@Module("shared")
@SubModule("customer")
public class SharedCustomerProxy extends ProductProxy {
   
	private  Log log = LogFactory.getLogger("Customer Management");
	/**
	 * 
	 * @param customerVO
	 * @return String
	 * @throws SystemException
	 * @throws ProxyException
	 */
  public String  saveCustomer( CustomerVO customerVO)  throws
			SystemException,ProxyException {
	  return  despatchRequest("saveCustomer", 
			  customerVO);
	}
  
  /**
   * 
   * 	Method		:	SharedCustomerProxy.saveCustomerDetails
   *	Added by 	:	A-4789 on 12-Oct-2012
   * 	Used for 	:	Saves customer details.
   *	Parameters	:	@param customerDetails
   *	Parameters	:	@return Collection<CustomerVO>
   *	Parameters	:	@throws SystemException
   *	Parameters	:	@throws ProxyException 
   *	Return type	: 	Collection<CustomerVO>
   */
  public Collection<CustomerVO> saveCustomerDetails(Collection<CustomerVO> customerDetails)
  throws SystemException,ProxyException {
	return despatchRequest("saveCustomerDetails", customerDetails);  
  }
  
  /**
   * 
   * 	Method		:	SharedCustomerProxy.saveCustomerCreditDetails
   *	Added by 	:	A-10509 on 08-Nov-2022
   * 	Used for 	:	Saves customer credit details.
   *	Parameters	:	@param customerCreditVO
   *	Parameters	:	@return customerCreditVO
   *	Parameters	:	@throws SystemException
   *	Parameters	:	@throws ProxyException 
   *	Return type	: 	
   */
  public CustomerCreditVO saveCustomerCreditDetails(CustomerCreditVO customerCreditVO)
  throws SystemException, ProxyException {
	return despatchRequest("saveCustomerCreditDetails", customerCreditVO);  
  }
  
  /**
   * 
   * 	Method		:	SharedCustomerProxy.findCustomerCreditDetails
   *	Added by 	:	A-10509 on 08-Nov-2022
   * 	Used for 	:	finds customer credit details.
   *	Parameters	:	@param customerCreditFilterVO
   *	Parameters	:	@return customerCreditFilterVO
   *	Parameters	:	@throws SystemException
   *	Parameters	:	@throws ProxyException 
   *	Return type	: 	
   */
  public CustomerCreditVO findCustomerCreditDetails(CustomerCreditFilterVO customerCreditFilterVO)
  throws SystemException, ProxyException {
	return despatchRequest("findCustomerCreditDetails", customerCreditFilterVO);  
  }

  
  /**
   * 
   * 	Method		:	SharedCustomerProxy.saveCustomerCertificateDetails
   *	Added by 	:	A-4789 on 12-Oct-2012
   * 	Used for 	:	Saves the customer certificate details.
   *	Parameters	:	@param customerCertificates
   *	Parameters	:	@return Collection<CustomerCertificateVO>
   *	Parameters	:	@throws SystemException
   *	Parameters	:	@throws ProxyException 
   *	Return type	: 	Collection<CustomerCertificateVO>
   */
  public Collection<CustomerCertificateVO> saveCustomerCertificateDetails(Collection<CustomerCertificateVO> customerCertificates)
  throws SystemException,ProxyException {
	return despatchRequest("saveCustomerCertificateDetails", customerCertificates);  
  }
  
  /**
   * 
   * @param customerFilterVO
   * @return CustomerVO
   * @throws SystemException
   * @throws ProxyException
   */
  public CustomerVO listCustomer( CustomerFilterVO customerFilterVO)  throws
	SystemException,ProxyException {
   return  despatchRequest("listCustomer",customerFilterVO);
  }
  /**
   * 
   * @param customerListFilterVO
   * @return Page<CustomerVO>
   * @throws SystemException
   * @throws ProxyException
   */
  public  Page<CustomerVO> listCustomerDetails(CustomerListFilterVO 
			customerListFilterVO)  throws
			SystemException,ProxyException {
	  return  despatchRequest("listCustomerDetails",customerListFilterVO);
  }
  /**
   * @author A-1883
   * @param companyCode
   * @param customerCode
   * @param points
   * @return String       GroupCode 
   * @throws SystemException
   * @throws ProxyException
   */
	public String addCustomerPoints(String companyCode,String customerCode,
			double points) throws SystemException,ProxyException {
		log.entering("SharedCustomerProxy","addCustomerPoints");
		  return  despatchRequest("addCustomerPoints",companyCode,customerCode,points);
	}
  /**
   * @author A-1883
   * @param companyCode
   * @param groupCode
   * @param points
   * @throws SystemException
   * @throws ProxyException
   */
	public void addGroupPoints(String companyCode,String groupCode,
			double points) throws SystemException,ProxyException {
		    log.entering("SharedCustomerProxy","addGroupPoints");
		    despatchRequest("addGroupPoints",companyCode,groupCode,points);
		    log.exiting("SharedCustomerProxy","addGroupPoints");
	}
/**
 * 
 * @param tempCustomerVOs
 * @return
 * @throws SystemException
 * @throws ProxyException
 */
    public String saveTempCustomer(Collection<TempCustomerVO> tempCustomerVOs)
    throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","saveTempCustomer");
    	 return  despatchRequest("saveTempCustomer",tempCustomerVOs);
    }
    /**
     * 
     * @param companyCode
     * @param tempCustCode
     * @return TempCustomerVO
     * @throws SystemException
     * @throws ProxyException
     */
    public TempCustomerVO listTempCustomer(String companyCode,
			String tempCustCode)throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","listTempCustomer");
   	 return  despatchRequest("listTempCustomer",companyCode,tempCustCode);
    }
    /**
     * 
     * @param listTempCustomerVO
     * @return Page<TempCustomerVO>
     * @throws SystemException
     * @throws ProxyException
     */
    public Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO 
			listTempCustomerVO)throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","listTempCustomerDetails");
      	 return  despatchRequest("listTempCustomerDetails",listTempCustomerVO);
    }
    /**
     * 
     * @param companyCode
     * @param customerCode
     * @return Collection<CustomerContactVO>
     * @throws SystemException
     * @throws ProxyException
     */
    public Collection<CustomerContactVO> customerContactsLov(
	  String companyCode,String customerCode)
      throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","customerContactsLov");
     	 return  despatchRequest("customerContactsLov",companyCode,customerCode);
    }
    /**
     * 
     * @param pointsVOs
     * @throws SystemException
     * @throws ProxyException
     */
    public void saveCustomerContactPoints(Collection<CustomerContactPointsVO> 
    pointsVOs) throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","saveCustomerContactPoints");
    	despatchRequest("saveCustomerContactPoints",pointsVOs);
    }
    /**
     * 
     * @param companyCode
     * @param customerCode
     * @return
     * @throws SystemException
     * @throws ProxyException
     */
    public Collection<CustomerContactPointsVO> listCustomerContactPoints(
    		String companyCode,String customerCode)
            throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","listCustomerContactPoints");
    	return despatchRequest("listCustomerContactPoints",companyCode,customerCode);
    }
    /**
     * 
     * @param customerVOs
     * @throws SystemException
     * @throws ProxyException
     */
    public void changeStatusOfCustomers(Collection<CustomerVO> customerVOs)
    throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","changeStatusOfCustomers");
    	 despatchRequest("changeStatusOfCustomers",customerVOs);
    }
    /**
     * @author a-1883
     * @param companyCode
     * @param customerCode
     * @throws SystemException
     * @throws ProxyException
     */
    public void clearCustomerPoints(String companyCode,String customerCode)
    throws SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","clearCustomerPoints");
    	 despatchRequest("clearCustomerPoints",companyCode,customerCode);
    }
    
    /**
     * 
     * @param customerVOs
     * @throws SystemException
     * @throws ProxyException
     */
    /*public void deleteCustomers(Collection<CustomerVO> customerVOs) 
    throws SystemException,ProxyException{
    	log.entering("SharedCustomerProxy","deleteCustomers");
   	 despatchRequest("deleteCustomers",customerVOs);
    }*/
    
    
    /* ******************************* added by kiran starts ***************************** */
    
    /**
     * method for listing the customerGroupDetails for ListCustomerScreen 
     * @author A-2049
     * @param customerGroupFilterVO
     * @return Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO>
     * @throws SystemException
     * @throws ProxyException
     */
    public Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> viewCustomerGroups(CustomerGroupFilterVO customerGroupFilterVO)
     throws SystemException,ProxyException {
        log.entering("SharedCustomerProxy","viewCustomerGroups");
        log.log(Log.FINE," going for despatch request to shared:customer module ");
        return despatchRequest("listCustomerGroup",customerGroupFilterVO);
    }   
    
    /**
     * method for listing the customerGroupDetails for MaintainCustomerScreen based on GroupCode & company code
     * @author A-2049
     * @param companyCode
     * @param groupCode
     * @return
     * @throws SystemException
     * @throws ProxyException
     */
    public com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO listCustomerGroupDetails(String companyCode, String groupCode) 
    throws SystemException,ProxyException {
       log.entering("SharedCustomerProxy","listCustomerGroupDetails");
       log.log(Log.FINE," going for despatch request to shared:customer module ");
       return despatchRequest("listCustomerGroupDetails",companyCode,groupCode);      
   }
    
    /**
     * method for saving,modifying,deleting a collection of customergroupdetails from MaintainCustomerGroupScreen
     * @author A-2049
     * @param customerGroupVOs
     * @return groupCode
     * @throws SystemException
     * @throws ProxyException
     */
    public String saveCustomerGroupDetails(Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs)
    throws SystemException,ProxyException {
        log.entering("SharedCustomerProxy","saveCustomerGroupDetails");
        return despatchRequest("saveCustomerGroupDetails",customerGroupVOs);
    }
    
    /**
     * Method for deleting multiple CustomerGroups from ListCustomerGroupScreen 
     * which do not contain any customers attached
     * @author A-2049
     * @param customerGroupVOs
     * @throws SystemException
     * @throws ProxyException
     */
    public void deleteCustomerGroups (Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs )
    throws SystemException,ProxyException {
        log.entering("SharedCustomerProxy","deleteCustomerGroups");
        despatchRequest("deleteCustomerGroups",customerGroupVOs);
    }

    /* ******************************* added by kiran ends ***************************** */
    
    /**
     * Method will upload the datas from the excel sheet
     * @author A-3045
     * @param fileType
     * @param tsaData
     * @return groupCode
     * @throws SystemException
     * @throws ProxyException
     */
    public Collection<ErrorVO> uploadTSAData(String fileType,
			byte[] tsaData)throws SystemException,ProxyException {
        log.entering("SharedCustomerProxy","uploadTSAData");
        return despatchRequest("uploadTSAData",fileType, tsaData);
    }
    /**
     * 
     * @param customerFilterVO
     * @return CustomerVO
     * @throws SystemException
     * @throws ProxyException
     */
    public CustomerVO customerEnquiryDetails( CustomerFilterVO customerFilterVO)  
    throws 	SystemException,ProxyException {
    	log.entering("SharedCustomerProxy","customerEnquiryDetails");
    	return despatchRequest("customerEnquiryDetails",customerFilterVO);
    }

	/**
	 * 
	 * 	Method		:	SharedCustomerProxy.updateCustomerStatus
	 *	Added by 	:	A-4789 on 17-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customers
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> updateCustomerStatus(
			Collection<CustomerVO> customers) 
			throws ProxyException, SystemException {
    	log.entering("SharedCustomerProxy","updateCustomerStatus");
    	return despatchRequest("updateCustomerStatus",customers);
    }

	/**
	 * 	Method		:	SharedCustomerProxy.getCustomerDetails
	 *	Added by 	:	A-4789 on 19-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilters
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CustomerVO>
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public Collection<CustomerVO> getCustomerDetails(
			Collection<CustomerFilterVO> customerFilters) 
			throws ProxyException, SystemException {
    	log.entering("SharedCustomerProxy","getCustomerDetails");
    	return despatchRequest("getCustomerDetails",customerFilters);
    }
    
	/**
	 * 
	 * 	Method		:	SharedCustomerProxy.loadCustomerDetailsHistory
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	To Load Customer Details History. Added for ICRD-67442.
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> loadCustomerDetailsHistory(CustomerFilterVO customerFilterVO) throws ProxyException, SystemException {
		log.entering("SharedCustomerProxy","loadCustomerDetailsHistory");
		return  despatchRequest("loadCustomerDetailsHistory",customerFilterVO);
	}
	/**
	 * 
	 * 	Method		:	SharedCustomerProxy.getAllCustomers
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> getAllCustomers(CustomerFilterVO customerFilterVO) 
			throws ProxyException, SystemException {
    	log.entering("SharedCustomerProxy","getAllCustomers");
    	return despatchRequest("getAllCustomers",customerFilterVO);
    }
	
	/**
	 * @author A-5183
	 * @param customerListFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<CustomerVO> listCustomerDetailsForReport(CustomerListFilterVO customerListFilterVO) 
			throws SystemException,ProxyException {
		log.entering("SharedCustomerProxy","listCustomerDetailsForReport");       
    	return despatchRequest("listCustomerDetailsForReport",customerListFilterVO);
	}

	/**
	 *
	 * @param customerFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<CustomerCertificateVO> findCertificateTypes(
			CustomerFilterVO customerFilterVO) throws SystemException, ProxyException {
		log.entering("SharedCustomerProxy ","findCertificateTypes");
		return despatchRequest("findCertificateTypes",customerFilterVO);
	}
	
	public Collection<CustomerAgentVO> validateSinglePoa(ShipmentFilterVO shipmentFilterVO) throws ProxyException, SystemException {
		return despatchRequest("validateSinglePoa",shipmentFilterVO);
	}
}
