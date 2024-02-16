/*
 * CustomerMgmntDefaultsDelegate.java Created on Dec 19, 2005
 *                                             
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.customermanagement.defaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceDetailsVO;
import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.CharterOperationsVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.ListCharterOperationsFilterVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryFilterVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingFilterVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimFilterVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimListVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CCADetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerBillingInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.PaymentDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.RedemptionValidationVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageHistoryVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageListFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestFilterVO;
import com.ibsplc.icargo.business.tariff.freight.vo.RateLineVO;
import com.ibsplc.icargo.business.tariff.freight.vo.TariffFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.zip.ByteObject;



/**
 * @author A-1496
 *
 */

@Module("customermanagement")
@SubModule("defaults")
public class CustomerMgmntDefaultsDelegate extends BusinessDelegate{
    
/***
 * 
 * @param tempCustomerVO
 * @throws BusinessDelegateException
 */
    //@Action("requestTempCustomerReg")
	public void requestTempCustomerReg(TempCustomerVO tempCustomerVO) 
	throws BusinessDelegateException {
	}


	/**
	 * 
	 * @param listTempCustomerVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	//To be reviewed Collection<TempCustomerVO>
	//@Action("listTempCustomerReg")
	public Collection<TempCustomerVO> listTempCustomerReg(ListTempCustomerVO listTempCustomerVO) 
	throws BusinessDelegateException {
	    return null;
	}
	/***
	 * 
	 * @param tempCustomerVOs
	 * @param action
	 * @throws BusinessDelegateException
	 */
	//To be reviewed Collection<TempCustomerVO>
	//@Action("actionTempCustomerReg")
	public void actionTempCustomerReg(Collection<TempCustomerVO> tempCustomerVOs,String action)
	throws BusinessDelegateException {
	    
	}
	
	/***
	 * 
	 * @param customerVO
	 * @return 
	 * @throws BusinessDelegateException
	 */
	
	public String registerCustomer(CustomerVO customerVO,Collection<LockVO> locks)
	throws BusinessDelegateException {
	    log.entering("CustomerMgmntDefaultsDelegate","registerCustomer");
	    return despatchRequest("registerCustomer",locks,customerVO);
	}
	
	
	/***
	 * 
	 * @param customerListFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	//To be reviewed Collection<CustomerVO>
	//@Action("listCustomerReg")
	public Collection<CustomerVO> listCustomerReg(CustomerListFilterVO customerListFilterVO) 
	throws BusinessDelegateException {
	    return null;
	}
	
	/***
	 * 
	 * @param customerCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	//To be reviewed Collection<ListCustomerPointsVO>
	//@Action("listCustomerLoyaltyPoints")	
	public Collection<ListCustomerPointsVO> listCustomerLoyaltyPoints(String customerCode)
	throws BusinessDelegateException {
	    return null;
	}
	
	/***
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @throws BusinessDelegateException
	 */

	public void createLoyaltyProgramme(LoyaltyProgrammeVO loyaltyProgrammeVO)
	throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","createLoyaltyProgramme");
	    despatchRequest("createLoyaltyProgramme",loyaltyProgrammeVO);
	    log.exiting("CustomerMgmntDefaultsDelegate","createLoyaltyProgramme");
	}
	
	/***
	 * 
	 * @param loyaltyProgrammeFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
	throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","listLoyaltyProgramme");
		return  despatchRequest("listLoyaltyProgramme",loyaltyProgrammeFilterVO);
	    
	}
	/***
	 * 
	 * @param attachLoyaltyProgrammeVOs
	 * @throws BusinessDelegateException
	 */
	//To be reviewed Collection<AttachLoyaltyProgrammeVO>
    //@Action("attachLoyaltyPrgToCustomer")
	public void attachLoyaltyPrgToCustomer(Collection<AttachLoyaltyProgrammeVO> attachLoyaltyProgrammeVOs)
	throws BusinessDelegateException {
	    
	}
	


	/***
	 * @author A-1883
	 * @param listPointsAccumulatedFilterVO
	 * @param pageNumber
	 * @return Page<ListCustomerPointsVO>
	 * @throws BusinessDelegateException
	 */
  
	public Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(ListPointsAccumulatedFilterVO 
			listPointsAccumulatedFilterVO,int pageNumber)
	throws BusinessDelegateException {
    	log.entering("CustomerMgmntDefaultsDelegate","listLoyaltyPointsForAwb");
		return despatchRequest("listLoyaltyPointsForAwb",
				listPointsAccumulatedFilterVO,pageNumber);
	}
	/**
	 * 
	 * @param companyCode
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws BusinessDelegateException
	 */
	
	/*public Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(String companyCode)
	throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","findAllLoyaltyProgrammes");
		return despatchRequest("findAllLoyaltyProgrammes",companyCode);
	}*/
	/**
	 * 
	 * @param customerGroupVOs
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
	/*
	public Collection<String> saveCustomerGroup(Collection<CustomerLoyaltyGroupVO> 
			customerGroupVOs) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","saveCustomerGroup");
		return despatchRequest("saveCustomerGroup",customerGroupVOs);
	}
	*/
	/**
	 * @author A-1883
	 * @param companyCode
	 * @return HashMap<String,Collection<LoyaltyAttributeVO>>
	 * @throws BusinessDelegateException
	 */
	
	public HashMap<String,Collection<LoyaltyAttributeVO>> listLoyaltyAttributeDetails
	  (String companyCode)throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listLoyaltyAttributeDetails");
		return despatchRequest("listLoyaltyAttributeDetails",companyCode);
	}
	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupVO>
	 * @throws BusinessDelegateException
	 */
	/*
	public Collection<CustomerLoyaltyGroupVO> listCustomerGroup(String companyCode ,
			String groupCode)throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listCustomerGroup");
		return despatchRequest("listCustomerGroup",companyCode,groupCode);
	}
	*/
	/**
	 * s
	 * @param companyCode
	 * @param groupCode
	 * @param pageNumber 
	 * @return
	 * @throws BusinessDelegateException
	 */
	/*
	public Page<CustomerLoyaltyGroupVO> customerGroupLOV(String companyCode ,
			String groupCode,int pageNumber)throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listCustomerGroup");
		return despatchRequest("customerGroupLOV",companyCode,groupCode,
				pageNumber);	
	}
	*/
	/**
	 * 
	 * @param tempCustomerVOs
	 * @return String
	 * @throws BusinessDelegateException
	 */
	
	public String saveTempCustomer(Collection<TempCustomerVO> tempCustomerVOs)
	      throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","saveTempCustomer");
		return despatchRequest("saveTempCustomer",tempCustomerVOs);	
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param tempCustCode
	 * @return TempCustomerVO
	 * @throws BusinessDelegateException
	 */
	
	public TempCustomerVO listTempCustomer(String companyCode,
			String tempCustCode) throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listTempCustomer");
		return despatchRequest("listTempCustomer",companyCode,
				tempCustCode);		
	}
	/**
	 * 
	 * @param listTempCustomerVO
	 * @return Page<TempCustomerVO>
	 * @throws BusinessDelegateException
	 */
	
	public Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO 
			listTempCustomerVO) throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listTempCustomerDetails");
		return despatchRequest("listTempCustomerDetails",listTempCustomerVO);		
	}
	/**
	 * 
	 * @param groupLoyaltyProgrammeVOs
	 * @throws BusinessDelegateException
	 */
	
	public void saveGroupLoyaltyPgm(Collection<CustomerGroupLoyaltyProgrammeVO>
	       groupLoyaltyProgrammeVOs) throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","saveGroupLoyaltyPgm");
		despatchRequest("saveGroupLoyaltyPgm",groupLoyaltyProgrammeVOs);
		
	}
	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return  Collection<CustomerGroupLoyaltyProgrammeVO>
	 * @throws BusinessDelegateException
	 */
	
	public Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
			String companyCode,String groupCode)
	    throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listGroupLoyaltyPgm");
		return  despatchRequest("listGroupLoyaltyPgm",companyCode,
				groupCode); 
	}
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws BusinessDelegateException
	 */
	
	 public Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO,int pageNumber)
			throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listAllLoyaltyProgrammes");
		return  despatchRequest("listAllLoyaltyProgrammes",loyaltyProgrammeFilterVO,
				pageNumber); 
	}
	/**
	 * Calculates points 
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @throws BusinessDelegateException
	 */
	
	/*public void generateLoyaltyPointsForAWB(AirWayBillLoyaltyProgramVO 
			airWayBillLoyaltyProgramVO)throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","generateLoyaltyPointsForAWB");
		despatchRequest("generateLoyaltyPointsForAWB",airWayBillLoyaltyProgramVO);
		log.exiting("CustomerMgmntDefaultsDelegate","generateLoyaltyPointsForAWB");
	}*/
	/**
	 * 
	 * @param customerFilterVO
	 * @return CustomerVO
	 * @throws BusinessDelegateException
	 */
	
	public CustomerVO listCustomer(CustomerFilterVO customerFilterVO) throws 
	BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listCustomer");
		return despatchRequest("listCustomer",customerFilterVO);
	}
	/***
	 * 
	 * @param customerListFilterVO
	 * @return Page<CustomerVO>
	 * @throws BusinessDelegateException
	 */
	
	/*public Page<CustomerVO> listCustomerDetails(CustomerListFilterVO 
			customerListFilterVO) throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","listCustomerDetails");
		return despatchRequest("listCustomerDetails",customerListFilterVO);	
	}*/
	/**
	 * 
	 * @param customerGroupVOs
	 * @throws BusinessDelegateException
	 */
	/*
	public void checkForCustomer(Collection<CustomerLoyaltyGroupVO> 
	customerGroupVOs) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","checkForCustomer");
		despatchRequest("checkForCustomer",customerGroupVOs);
	}
	*/
	/***
	 * 
	 * @param companyCode
	 * @param customerCode
	 * @param groupCode
	 * @return Collection<AttachLoyaltyProgrammeVO>
	 * @throws BusinessDelegateException
	 */
	
	public Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
			String companyCode,String customerCode,String groupCode)
	        throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","listLoyaltyPgmToCustomers");
		return despatchRequest("listLoyaltyPgmToCustomers",companyCode,
				customerCode,groupCode);
	}
	/**
	 * 
	 * @param attachLoyaltyProgrammeVOs
	 * @throws BusinessDelegateException
	 */
	
	public void saveLoyaltyPgmToCustomers( Collection<AttachLoyaltyProgrammeVO> 
	attachLoyaltyProgrammeVOs) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","saveLoyaltyPgmToCustomers");
		despatchRequest("saveLoyaltyPgmToCustomers",attachLoyaltyProgrammeVOs);
			
	}
	/**
	 * 
	 * @param airWayBillLoyaltyProgramFilterVO
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
	
	public Collection<String> listPointAccumulated(
			AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
			throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","listPointAccumulated");
		return despatchRequest("listPointAccumulated",
				airWayBillLoyaltyProgramFilterVO);
	}
	/**
	 * Finds Paramter Description Details ( Screen Load call)
	 * @author A-1883
	 * @param companyCode
	 * @throws BusinessDelegateException
	 */
	
	public Collection<ParameterDescriptionVO> findParameterDetails(String companyCode)
			throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","findParameterDetails");
		return despatchRequest("findParameterDetails",companyCode);
	}
	/**
	 * 
	 * @param airWayBillLoyaltyProgramFilterVO
	 * @return Collection<AirWayBillLoyaltyProgramVO>
	 * @throws BusinessDelegateException
	 */
	
	public Collection<AirWayBillLoyaltyProgramVO> showPoints(AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
			throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","showPoints");
		return despatchRequest("showPoints",
				airWayBillLoyaltyProgramFilterVO);
	}
	/**
	 * 
	 * @param companyCode
	 * @param customerCode
	 * @return Collection<CustomerContactVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<CustomerContactVO> customerContactsLov(String companyCode,
			String customerCode)throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","customerContactsLov");
		return despatchRequest("customerContactsLov",
				companyCode,customerCode);
	}
	
	/**
     * 
     * @param pointsVOs
     * @throws BusinessDelegateException
     */
    public void saveCustomerContactPoints(Collection<CustomerContactPointsVO> 
    pointsVOs)throws BusinessDelegateException{
    	log.entering("CustomerDelegate", "saveCustomerContactPoints");
    	 despatchRequest("saveCustomerContactPoints",pointsVOs);
    	
    }
    
    /**
     * @param companyCode 
     * @param customerCode 
     * @return Collection<CustomerContactPointsVO>
     * @throws BusinessDelegateException 
     * 
     */
    public Collection<CustomerContactPointsVO> listCustomerContactPoints(
    		String companyCode,String customerCode)
    		throws BusinessDelegateException {
    	log.entering("CustomerDelegate","listCustomerContactPoints");
    	 return despatchRequest("listCustomerContactPoints",companyCode,customerCode);
    	
    }
    
    /**
     * 
     * @param customerVOs
     * @throws BusinessDelegateException
     */
    public void changeStatusOfCustomers(Collection<CustomerVO> customerVOs)
    throws BusinessDelegateException {
    	log.entering("CustomerDelegate","changeStatusOfCustomers");
   	  despatchRequest("changeStatusOfCustomers",customerVOs);
    }
    /**
     * Validates customer for points redemption
     * @author a-1883
     * @param redemptionValidationVO
     * @return Collection<ErrorVO>
     * @throws BusinessDelegateException
     */
    public Collection<ErrorVO> validateCustomerForPointsRedemption(
			 RedemptionValidationVO redemptionValidationVO) throws BusinessDelegateException {
       log.entering("CustomerMgmntDefaultsDelegate","validateCustomerForPointsRedemption");
        return despatchRequest("validateCustomerForPointsRedemption",redemptionValidationVO);
   } 
   
    /* ******************************** added by kiran starts ************************************ */
    
    /**
     * @author A-2049
     * method for listing CustomerGroups in ListCustomerGroups ListCommand
     * @param customerGroupFilterVO
     * @return Page<CustomerGroupVO>
     * @throws BusinessDelegateException
     */
    public Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> viewCustomerGroups(CustomerGroupFilterVO customerGroupFilterVO)
    throws BusinessDelegateException {
        log.entering("CustomerMgmntDefaultsDelegate","viewCustomerGroups");
        return despatchRequest("viewCustomerGroups",customerGroupFilterVO);
    }
    
    /**
     * @author A-2049
     * @param companyCode
     * @param groupCode
     * @return CustomerGroupVO
     * @throws BusinessDelegateException
     */
    public com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO listCustomerGroupDetails(String companyCode,String groupCode) 
    throws BusinessDelegateException {
        log.entering("CustomerMgmntDefaultsDelegate","listCustomerGroupDetails");
        return despatchRequest("listCustomerGroupDetails",companyCode,groupCode);
    } 
    
    /**
     * method for saving,modifying,deleting a collection of customergroupdetails from MaintainCustomerGroupScreen
     * @author A-2049
     * @param customerGroupVOs
     * @return groupCode
     * @throws BusinessDelegateException
     */
    public String saveCustomerGroupDetails(Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs)
    throws BusinessDelegateException {
        log.entering("CustomerMgmntDefaultsDelegate","saveCustomerGroupDetails");
        return despatchRequest("saveCustomerGroupDetails",customerGroupVOs);
    }
    
    /**
     * Method for deleting multiple CustomerGroups from ListCustomerGroupScreen 
     * which do not contain any customers attached
     * @author A-2049
     * @param customerGroupVOs
     * @throws BusinessDelegateException
     */
    public void deleteCustomerGroups (Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs )
    throws BusinessDelegateException {
        log.entering("CustomerMgmntDefaultsDelegate","deleteCustomerGroups");
      despatchRequest("deleteCustomerGroups",customerGroupVOs);
    }
    
    /* ******************************** added by kiran ends ************************************ */
    /**
	  * @author a-1883
	  * @param companyCode
	  * @param currentDate
	  * @param pageNumber
	  * @return Page<LoyaltyProgrammeVO>
	  * @throws  BusinessDelegateException
	  */
    public  Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
			 String companyCode,LocalDate currentDate,int pageNumber)
			 throws BusinessDelegateException {
    	 log.entering("CustomerMgmntDefaultsDelegate","runningLoyaltyProgrammeLOV");
         return despatchRequest("runningLoyaltyProgrammeLOV",companyCode,
 				currentDate,pageNumber);
    }
    /**
     * 
     * @param customerVOs
     * @throws BusinessDelegateException
     */
    /*public void deleteCustomers(Collection<CustomerVO> customerVOs) 
     throws BusinessDelegateException {
    	 log.entering("CustomerMgmntDefaultsDelegate","deleteCustomers");
    	 despatchRequest("deleteCustomers",customerVOs);
  	}*/
    /**
     * 
     * @param servicesVO
     * @throws BusinessDelegateException
     */
    public void saveCustomerServices(CustomerServicesVO servicesVO)
    throws BusinessDelegateException {
    	log.entering("CustomerMgmntDefaultsDelegate","saveCustomerServices");
    	 despatchRequest("saveCustomerServices",servicesVO);
    }
    /**
     * 
     * @param companyCode
     * @param serviceCode
     * @return
     * @throws BusinessDelegateException
     */
    public CustomerServicesVO listCustomerServices(String companyCode,
    		String serviceCode)throws BusinessDelegateException {
    	log.entering("CustomerMgmntDefaultsDelegate","listCustomerServices");
   	    return despatchRequest("listCustomerServices",companyCode,serviceCode);
    }
    /**
     * 
     * @param companyCode
     * @param serviceCode
     * @param pageNumber 
     * @return
     * @throws BusinessDelegateException
     */
    public Page<CustomerServicesVO> customerServicesLOV(String companyCode,
    		String serviceCode,int pageNumber)throws BusinessDelegateException {
    	log.entering("CustomerMgmntDefaultsDelegate","customerServicesLOV");
   	    return despatchRequest("customerServicesLOV",companyCode,serviceCode,pageNumber);
    }
    
    /**
	 * Method will return all the shipments with the same prefix and awb number
	 * It wil return empty Set if no awbs exist with the same prefix and awb
	 * number
	 *
	 * @param shipmentFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 * @author A-2593
	 */
	/*public Collection<ShipmentVO> findShipments(
			ShipmentFilterVO shipmentFilterVO) throws BusinessDelegateException {
		log.entering("ShipmentDelegate", "findShipments");
		log.exiting("ShipmentDelegate", "findShipments");
		return despatchRequest("findShipments", shipmentFilterVO);
	}*/
	
    /**
	 * Method will upload the datas from the excel sheet
	 * @param fileType
	 * @param tsaData
	 * @return Collection <ErrorVO>
	 * @throws BusinessDelegateException
	 * @author A-3045
	 */
	public Collection <ErrorVO> uploadTSAData(String fileType,
			byte[] tsaData) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate", "uploadTSAData");
		return despatchRequest("uploadTSAData", fileType, tsaData);
	}
	/**
	  * @author a-2883
	  * @param bookingFilterVO
	  * @return Page<BookingVO>
	  * @throws BusinessDelegateException
	  */
	 public Page<BookingVO> findBookings(BookingFilterVO bookingFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findBookings");
		 return  despatchRequest("findBookings",bookingFilterVO);
	 }
	 /**
	  * @author a-2883
	  * @param claimFilterVO
	  * @return Page<ClaimListVO>
	  * @throws BusinessDelegateException
	  */
	 public Page<ClaimListVO> findClaimList(ClaimFilterVO claimFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findClaimList");
		 return  despatchRequest("findClaimList",claimFilterVO);
	 }
	 
	 /**
	  * @author a-2883
	  * @param customerAllotmentEnquiryFilterVO
	  * @return Page<CustomerAllotmentEnquiryVO>
	  * @throws BusinessDelegateException
	  */
	 public Page<CustomerAllotmentEnquiryVO> findCustomerEnquiryDetails(
	 CustomerAllotmentEnquiryFilterVO customerAllotmentEnquiryFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findCustomerEnquiryDetails");
		 return  despatchRequest("findCustomerEnquiryDetails",customerAllotmentEnquiryFilterVO);
	 }
	 /**
	  * @author a-2883
	  * @param listCharterOperationsFilterVO
	  * @return  Page<CharterOperationsVO>
	  * @throws BusinessDelegateException
	  */
	 public Page<CharterOperationsVO> findCharterForCustomer(
	 ListCharterOperationsFilterVO listCharterOperationsFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findCharterForCustomer");
		 return  despatchRequest("findCharterForCustomer",listCharterOperationsFilterVO);
	 }
	 /**
	  * @author a-2883
	  * @param claimFilterVO
	  * @return Page<ClaimListVO>
	  * @throws BusinessDelegateException
	  */
	 public Page<SpotRateRequestDetailsVO> findSpotRateRequestsByFilter(
	 SpotRateRequestFilterVO spotRateRequestFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findSpotRateRequestsByFilter");
		 return  despatchRequest("findSpotRateRequestsByFilter",spotRateRequestFilterVO);
	 }
	 /**
	  * @author a-2883
	  * @param tariffFilterVO
	  * @return Page<RateLineVO> 
	  * @throws BusinessDelegateException
	  */
	 public Page<RateLineVO> findRateLineDetailsByFilter(TariffFilterVO tariffFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findRateLineDetailsByFilter");
		 return  despatchRequest("findRateLineDetailsByFilter",tariffFilterVO);
	 }
	 /**
	  * @author a-2883
	  * @param invoiceFilterVO
	  * @return Page<InvoiceDetailsVO> 
	  * @throws BusinessDelegateException
	  */
	 public Page<InvoiceDetailsVO> listInvoices(
	 InvoiceFilterVO invoiceFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "listInvoices");
		 return  despatchRequest("listInvoices",invoiceFilterVO);
	 }
	 
	 /**
	  * @author a-2883
	  * @param findMessageForCustomers
	  * @return Page<MessageVO> 
	  * @throws BusinessDelegateException
	  */
	 public Page<MessageHistoryVO> findMessageForCustomers(
	 MessageListFilterVO messageListFilterVO,Collection<String> address)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findMessageForCustomers");
		 return  despatchRequest("findMessageForCustomers",messageListFilterVO,address);
	 }
	 /**
	  * @author a-2883
	  * @param stockDetailsFilterVO
	  * @return StockDetailsFilterVO
	  * @throws BusinessDelegateException
	  */
	 public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "findCustomerStockDetails");
		 return  despatchRequest("findCustomerStockDetails",stockDetailsFilterVO);
	 }
	 /**
	  * @author a-2883
	  * @param customerFilterVO
	  * @return CustomerVO
	  * @throws BusinessDelegateException
	  */
	 public CustomerVO customerEnquiryDetails(CustomerFilterVO customerFilterVO)
	 throws BusinessDelegateException{
		 log.entering("CustomerMgmntDefaultsDelegate", "customerEnquiryDetails");
		 return  despatchRequest("customerEnquiryDetails",customerFilterVO);
		 
	 }

	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsDelegate.loadCustomerDetailsHistory
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Load Customer Details History. Added for ICRD-67442.
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> loadCustomerDetailsHistory(CustomerFilterVO customerFilterVO) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","loadCustomerDetailsHistory");
		return despatchRequest("loadCustomerDetailsHistory", customerFilterVO);
	}
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsDelegate.findUserDetails
	 *	Added by 	:	a-5956 on 19-Oct-2017
	 * 	Used for 	:	ICRD-212854
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param userID
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	UserVO
	 */
	public UserVO findUserDetails(String companyCode, String userID)throws BusinessDelegateException{
		log.entering("CustomerMgmntDefaultsDelegate","loadCustomerDetailsHistory");
		return despatchRequest("findUserDetails", companyCode,userID);
	}
	
	/**
	 * 	Method		:	CustomerMgmntDefaultsDelegate.validateStockHolderFromCustomerActivation
	 *	Added by 	:	A-8154 
	 *	Used for	: 	CR ICRD-253447
	 *	Parameters	:	@param customerVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws BusinessDelegateException
	 *  Return type	: 	boolean
	 */
	public boolean validateStockHolderFromCustomerActivation(CustomerVO customerVO)
		    throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","validateStockHolderFromCustomerActivation");
		return despatchRequest("validateStockHolderFromCustomerActivation",customerVO);
	}
	
	/**
	 * 	Method		:	CustomerMgmntDefaultsDelegate.getBillingInvoiceDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param filterVO 	
	 * 	Parameters	:	@throws BusinessDelegateException
	 *  Return type	: 	CustomerBillingInvoiceDetailsVO
	 */
	public CustomerBillingInvoiceDetailsVO getBillingInvoiceDetails(
			CustomerFilterVO filterVO) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","getBillingInvoiceDetails");
		return despatchRequest("getBillingInvoiceDetails",filterVO);
	}
	/**
	 * 	Method		:	CustomerMgmntDefaultsDelegate.getCCADetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param companyCode
	 * 	Parameters	:	@throws BusinessDelegateException
	 *  Return type	: 	List<CCADetailsVO>
	 */
	public List<CCADetailsVO> getCCADetails(String invoiceNumber,String companyCode) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","getCCADetails");
		return despatchRequest("getCCADetails",invoiceNumber,companyCode);
	}
	/**
	 * 	Method		:	CustomerMgmntDefaultsDelegate.getPaymentDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param companyCode 	
	 * 	Parameters	:	@throws BusinessDelegateException
	 *  Return type	: 	Collection<PaymentDetailsVO>
	 */
	public Collection<PaymentDetailsVO> getPaymentDetails(CustomerInvoiceDetailsVO customerInvoiceDetailsVO) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate","getPaymentDetails");
		return despatchRequest("getPaymentDetails", customerInvoiceDetailsVO);
	}
	/**
	 * 	Method		:	CustomerMgmntDefaultsDelegate.printAccountStatement
	 *	Added by 	:	A-5491 
	 *	Used for	: 	CR IASCB-45955
	 *	Parameters	:	@param customerFilterVO 	
	 * 	Parameters	:	@throws BusinessDelegateException
	 *  Return type	: 	byte[]
	 */
	/*public byte[] printAccountStatement(CustomerFilterVO customerFilterVO) throws BusinessDelegateException {
		log.entering("CustomerMgmntDefaultsDelegate", "printAccountStatement");
		return despatchRequest("printAccountStatement", customerFilterVO);
	}*/
	public ByteObject printAccountStatement(CustomerFilterVO customerFilterVO)
			throws BusinessDelegateException{
		return despatchRequest("printAccountStatement", customerFilterVO);
	}

	public void emailAccountStatement(EmailAccountStatementFeatureVO featureVO)
			throws BusinessDelegateException {
		despatchRequest("emailAccountStatement", featureVO);
	}
	/**
	 * Done for IASCB-130291
	 * @param generalMasterGroupFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<GeneralMasterGroupVO> listGroupDetailsForCustomer
										(GeneralMasterGroupFilterVO generalMasterGroupFilterVO)throws BusinessDelegateException{
		return despatchRequest("listGroupDetailsForCustomer", generalMasterGroupFilterVO);
		
	}
	
	public Collection<ShipmentVO> validateShipmentDetails(ShipmentFilterVO shipmentFilterVO) throws BusinessDelegateException {
		return despatchRequest("validateShipmentDetails", shipmentFilterVO);
	}
	public Collection<CustomerAgentVO> validateSinglePoa(ShipmentFilterVO shipmentFilterVO) throws BusinessDelegateException {
		return despatchRequest("validateSinglePoa", shipmentFilterVO);
	}
	public Collection<ShipmentHistoryVO> findShipmentHandlingHistory(ShipmentHistoryFilterVO shipmentHistoryFilterVO) throws BusinessDelegateException {
		return despatchRequest("findShipmentHandlingHistory", shipmentHistoryFilterVO);
	}
	public String saveBrokerMapping(CustomerVO customerVO) throws BusinessDelegateException {
		return despatchRequest("saveBrokerMapping", customerVO);
	}
}