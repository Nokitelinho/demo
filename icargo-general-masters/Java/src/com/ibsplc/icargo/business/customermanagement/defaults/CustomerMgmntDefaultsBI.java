/*
 * CustomerMgmntDefaultsBI.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceDetailsVO;
import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
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
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomerMgmntDefaultsBusinessException;
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
import com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.customer.CustomerAlreadyExistsException;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
//import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
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
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteDetailsVO;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteFilterVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.zip.ByteObject;


/**
 * @author A-1496
 *
 */
public interface CustomerMgmntDefaultsBI {


	/***
	 * Finds all Loyalty programmes
	 * @author A-1883
	 * @param companyCode
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/*Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(String companyCode)
	throws SystemException,RemoteException ;*/
	/**
	 * Creates and Modifies Loyalty Programme
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws CustomerMgmntDefaultsBusinessException
	 */
	void createLoyaltyProgramme(LoyaltyProgrammeVO loyaltyProgrammeVO)
	throws SystemException,RemoteException,CustomerMgmntDefaultsBusinessException ;
	/**
	 * List Loyalty programmes based on Filter
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
			throws SystemException ,RemoteException ;

	/**
	 *
	 * @param customerGroupVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	//Collection<String> saveCustomerGroup(Collection<CustomerLoyaltyGroupVO>
	//customerGroupVOs) throws SystemException ,RemoteException ;
	/**
	 * @author A-1883
	 * @param companyCode
	 * @return HashMap<String,Collection<LoyaltyAttributeVO>>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	HashMap<String,Collection<LoyaltyAttributeVO>> listLoyaltyAttributeDetails
	(String companyCode)throws SystemException,RemoteException ;
	/**
	 *
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	//Collection<CustomerLoyaltyGroupVO> listCustomerGroup(String companyCode ,
	//		String groupCode) throws SystemException,RemoteException ;
	/**
	 *
	 * @param companyCode
	 * @param groupCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	//Page<CustomerLoyaltyGroupVO> customerGroupLOV(String companyCode ,
	//		String groupCode,int pageNumber)
	//		throws SystemException,RemoteException ;

	/***
	 * @param tempCustomerVOs
	 * @return String
	 * @throws SystemException
	 * @throws RemoteException
	 */

	String saveTempCustomer(Collection<TempCustomerVO> tempCustomerVOs)
	throws SystemException,RemoteException ;
	/**
	 *
	 * @param companyCode
	 * @param tempCustCode
	 * @return TempCustomerVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	TempCustomerVO listTempCustomer(String companyCode,
			String tempCustCode)
	throws SystemException,RemoteException ;
	/**
	 *
	 * @param listTempCustomerVO
	 * @return Page<TempCustomerVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO
			listTempCustomerVO)
			throws SystemException,RemoteException ;
	/**
	 *
	 * @param groupLoyaltyProgrammeVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws CustomerBusinessException
	 */
	void saveGroupLoyaltyPgm(Collection<CustomerGroupLoyaltyProgrammeVO>
	groupLoyaltyProgrammeVOs)
	throws SystemException,RemoteException,CustomerBusinessException ;
	/**
	 *
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupLoyaltyProgrammeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
			String companyCode,String groupCode)
			throws SystemException,RemoteException ;
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO,int pageNumber)
			throws SystemException,RemoteException;
	/**
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/*void generateLoyaltyPointsForAWB(AirWayBillLoyaltyProgramVO
			airWayBillLoyaltyProgramVO)
	throws SystemException,RemoteException;*/
	/**
	 *
	 * @param customerVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws CustomerBusinessException
	 */
	String registerCustomer(CustomerVO customerVO)
	throws SystemException,RemoteException,CustomerBusinessException;
	/**
	 *
	 * @param customerFilterVO
	 * @return CustomerVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	CustomerVO listCustomer(CustomerFilterVO customerFilterVO)
	throws SystemException,RemoteException;
	/**
	 *
	 * @param customerListFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/*Page<CustomerVO> listCustomerDetails(CustomerListFilterVO
			customerListFilterVO) throws SystemException,RemoteException;*/
	/**
	 *
	 * @param customerGroupVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws CustomerBusinessException
	 */
	//void checkForCustomer(Collection<CustomerLoyaltyGroupVO>
	//customerGroupVOs) throws SystemException,RemoteException,
	//CustomerBusinessException;
	/**
	 *
	 * @param companyCode
	 * @param customerCode
	 * @param groupCode
	 * @return Collection<AttachLoyaltyProgrammeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
			String companyCode,String customerCode,String groupCode)
			throws SystemException,RemoteException;

	/**
	 * @author A-1883
	 * @param listPointsAccumulatedFilterVO
	 * @param pageNumber
	 * @return Page<ListCustomerPointsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(ListPointsAccumulatedFilterVO
			listPointsAccumulatedFilterVO,int pageNumber)
			throws SystemException,RemoteException;
	/**
	 *
	 * @param attachLoyaltyProgrammeVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void saveLoyaltyPgmToCustomers( Collection<AttachLoyaltyProgrammeVO>
	attachLoyaltyProgrammeVOs)
	throws SystemException,RemoteException;
	/**
	 * @author A-1883
	 * @param companyCode
	 * @return Collection<ParameterDescriptionVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ParameterDescriptionVO> findParameterDetails(String companyCode)
	throws SystemException,RemoteException;
	/**
	 *
	 * @param airWayBillLoyaltyProgramFilterVO
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<String> listPointAccumulated(
			AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
			throws SystemException,RemoteException;
	/**
	 *
	 * @param airWayBillLoyaltyProgramFilterVO
	 * @return Collection<AirWayBillLoyaltyProgramVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<AirWayBillLoyaltyProgramVO> showPoints(AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
	throws SystemException,RemoteException;
	/**
	 *
	 * @param companyCode
	 * @param customerCode
	 * @return Collection<CustomerContactVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	 Collection<CustomerContactVO> customerContactsLov(String companyCode,
				String customerCode)throws SystemException,RemoteException;


   /**
	 *
	 * @param pointsVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	 void saveCustomerContactPoints(Collection<CustomerContactPointsVO>
     pointsVOs) throws RemoteException,SystemException;

	 /**
	     * @param companyCode
	     * @param customerCode
	     * @return Collection<CustomerContactPointsVO>
	     * @throws RemoteException
	     * @throws SystemException
	     *
	     */
	    Collection<CustomerContactPointsVO> listCustomerContactPoints(
	    		String companyCode,String customerCode)
	    		throws RemoteException,SystemException;

	/**
	 *
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 */
	void audit(Collection<AuditVO> auditVo) throws AuditException, RemoteException;

	 /**
     *
     * @param customerVOs
     * @throws RemoteException
     * @throws SystemException
     */
    void changeStatusOfCustomers(Collection<CustomerVO> customerVOs)
    throws RemoteException,SystemException;
    /**
     * @param redemptionValidationVO
     * @return Collection<ErrorVO>
     * @throws SystemException
     * @throws RemoteException
     * @throws CustomerMgmntDefaultsBusinessException
     */
    Collection<ErrorVO> validateCustomerForPointsRedemption(
			 RedemptionValidationVO redemptionValidationVO)
			 throws SystemException,RemoteException,CustomerMgmntDefaultsBusinessException ;

    /**
	  * @author a-1883
	  * @param companyCode
	  * @param currentDate
	  * @param pageNumber
	  * @return Page<LoyaltyProgrammeVO>
	  * @throws SystemException
	  * @throws RemoteException
	  *
	  */
	 Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
			 String companyCode,LocalDate currentDate,int pageNumber)
			 throws SystemException,RemoteException;
    /* ******************************* added by kiran starts *********************************** */

    /**
     * @author A-2049
     * @param customerGroupFilterVO
     * @return Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO>
     * @throws RemoteException
     * @throws SystemException
     */
    Page<CustomerGroupVO> viewCustomerGroups(CustomerGroupFilterVO customerGroupFilterVO)
    throws RemoteException,SystemException;

    /**
     * @author A-2049
     * @param companyCode
     * @param groupCode
     * @throws RemoteException
     * @throws SystemException
     * @return CustomerGroupVO
     */
    CustomerGroupVO listCustomerGroupDetails(String companyCode,String groupCode)
    throws RemoteException,SystemException;

    /**
     * @author A-2049
     * @param customerGroupVOs
     * @throws RemoteException
     * @throws SystemException
     */
    String saveCustomerGroupDetails(Collection<CustomerGroupVO> customerGroupVOs)
    throws RemoteException,SystemException;


    /**
     * @author A-2049
     * @param customerGroupVOs
     * @throws RemoteException
     * @throws SystemException
     * @throws CustomerBusinessException
     */
     void deleteCustomerGroups(Collection<CustomerGroupVO> customerGroupVOs )
    throws RemoteException,SystemException,CustomerBusinessException;

     /**
      *
      * @param customerVOs
      * @throws RemoteException
      * @throws SystemException
      */
     /*void deleteCustomers(Collection<CustomerVO> customerVOs)
     throws RemoteException,SystemException;*/
     /**
      *
      * @param servicesVO
      * @throws RemoteException
      * @throws SystemException
     * @throws CustomerMgmntDefaultsBusinessException
      */
     void saveCustomerServices(CustomerServicesVO servicesVO)
     throws RemoteException,SystemException,CustomerMgmntDefaultsBusinessException;
    /**
     *
     * @param companyCode
     * @param serviceCode
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
     CustomerServicesVO listCustomerServices(String companyCode,
     		String serviceCode) throws RemoteException,SystemException;
     /**
      *
      * @param companyCode
      * @param serviceCode
     * @param pageNumber
      * @return
      * @throws RemoteException
      * @throws SystemException
      */
     Page<CustomerServicesVO> customerServicesLOV(String companyCode,
     		String serviceCode,int pageNumber) throws RemoteException,SystemException;

    /**
     * @param reportSpec
     * @return Map<String, Object>
     * @throws SystemException
     * @throws ReportGenerationException
     * @throws RemoteException
     */
    Map<String, Object> generateLoyaltyDetailsReport(ReportSpec reportSpec)
 	throws SystemException,RemoteException;

    /**
     *
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws ReportGenerationException
     */
    Map<String, Object> printListCustomerReport(ReportSpec reportSpec)
	throws SystemException,RemoteException ;
    
    /**
     * @author A-2593
     * @return Collection<ShipmentVO>
     * @param ShipmentFilterVO
     * @throws RemoteException
     * @throws SystemException
     */
   /* public Collection<ShipmentVO> findShipments(
			ShipmentFilterVO shipmentFilterVO) throws RemoteException,
			SystemException;*/
    
    /**
     * @author A-3045
     * @return Collection<ErrorVO>
     * @param fileType
     * @param tsaData
     * @throws RemoteException
     * @throws SystemException
     */
    public Collection<ErrorVO> uploadTSAData(String fileType,
    		byte[] tsaData) throws RemoteException,SystemException;
    
    /**
     * @author A-2883
     * @param claimFilterVO
     * @return Page<ClaimListVO>
     * @throws RemoteException
     * @throws SystemException
     */
    public Page<ClaimListVO> findClaimList(ClaimFilterVO claimFilterVO)
    throws RemoteException,SystemException;

    /**
     * @author a-2883
     * @param spotRateRequestFilterVO
     * @return Page<ClaimListVO>
     * @throws RemoteException
     * @throws SystemException
     */
    public Page<SpotRateRequestDetailsVO> findSpotRateRequestsByFilter(
    SpotRateRequestFilterVO spotRateRequestFilterVO) throws RemoteException,SystemException;
    
    
    /**
     * @author a-2883
     * @param invoiceFilterVO
     * @return Page<InvoiceDetailsVO>
     * @throws BusinessDelegateException
     */
    public Page<InvoiceDetailsVO> listInvoices(InvoiceFilterVO invoiceFilterVO)
    throws RemoteException,SystemException;
    
    /**
     * @author a-2883
     * @param messageListFilterVO
     * @return Page<MessageVO>
     * @throws RemoteException
     * @throws SystemException
     */
    public Page<MessageHistoryVO> findMessageForCustomers(
    MessageListFilterVO messageListFilterVO,Collection<String> address) throws RemoteException,SystemException;		 
    
    
    /**
     * @author a-2883
     * @param bookingFilterVO
     * @return Page<BookingVO> 
     * @throws BusinessDelegateException
     */
    public Page<BookingVO> findBookings(BookingFilterVO bookingFilterVO)
	throws RemoteException,SystemException;
	 
  
    /**
     * @author a-2883
     * @param stockDetailsFilterVO
     * @return StockDetailsFilterVO
     * @throws RemoteException
     * @throws SystemException
     */
    public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
    throws RemoteException,SystemException,CustomerBusinessException;
	 
    /**
     * @author a-2883
     * @param customerAllotmentEnquiryFilterVO
     * @return Page<CustomerAllotmentEnquiryVO>
     * @throws RemoteException
     * @throws SystemException
     */
    public Page<CustomerAllotmentEnquiryVO> findCustomerEnquiryDetails(
    CustomerAllotmentEnquiryFilterVO customerAllotmentEnquiryFilterVO)
    throws RemoteException,SystemException;
    
    /**
     * @author a-2883
     * @param tariffFilterVO
     * @return Page<RateLineVO>
     * @throws RemoteException
     * @throws SystemException
     */
    public Page<RateLineVO> findRateLineDetailsByFilter(TariffFilterVO tariffFilterVO)
    throws RemoteException,SystemException;
    
    /**
     * @author a-2883
     * @param listCharterOperationsFilterVO
     * @return Page<CharterOperationsVO>
     * @throws RemoteException
     * @throws SystemException
     */
    public Page<CharterOperationsVO> findCharterForCustomer(
    ListCharterOperationsFilterVO listCharterOperationsFilterVO)
    throws RemoteException,SystemException;
    
    
    
    /**
	  * @author a-2883
	  * @param customerFilterVO
	  * @return CustomerVO
	  * @throws BusinessDelegateException
	  */
	 public CustomerVO customerEnquiryDetails(CustomerFilterVO customerFilterVO)
	 throws RemoteException,SystemException;

	/**
	 * @param eventAsyncHelperVO
	 * @throws RemoteException
	 */
	public void handleEvents(com.ibsplc.xibase.server.framework.event.vo.EventAsyncHelperVO eventAsyncHelperVO) throws RemoteException;
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.saveCustomerDetails
	 *	Added by 	:	A-4789 on 15-Oct-2012
	 * 	Used for 	:	Saving the customer details
	 *	Parameters	:	@param customerDetails
	 *	Parameters	:	@return Collection<CustomerVO>
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws CustomerAlreadyExistsException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> saveCustomerDetails(Collection<CustomerVO> customerDetails)
	  throws SystemException, RemoteException, CustomerAlreadyExistsException;
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.saveCustomerCreditDetails
	 *	Added by 	:	A-10509 on 08-Nov-2022
	 * 	Used for 	:	Saving the customer details
	 *	Parameters	:	@param customerCreditVO
	 *	Parameters	:	@return customerCreditVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	
	 */
	public CustomerCreditVO saveCustomerCreditDetails(CustomerCreditVO customerCreditVO)
	  throws SystemException, RemoteException;
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.findCustomerCreditDetails
	 *	Added by 	:	A-10509 on 08-Nov-2022
	 * 	Used for 	:	finds the customer credit details
	 *	Parameters	:	@param customerCreditFilterVO
	 *	Parameters	:	@return customerCreditFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	
	 */
	public CustomerCreditVO findCustomerCreditDetails(CustomerCreditFilterVO customerCreditFilterVO)
	  throws SystemException, RemoteException;

	/**
	 *
	 * @param customerVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<CustomerCertificateVO> findCustomerCertificates(CustomerVO customerVO)
			throws SystemException, RemoteException;
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.saveCustomerCertificateDetails
	 *	Added by 	:	A-4789 on 15-Oct-2012
	 * 	Used for 	:	Saving the customer certificate details
	 *	Parameters	:	@param customerCertificates
	 *	Parameters	:	@return Collection<CustomerCertificateVO>
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	Collection<CustomerCertificateVO>
	 */
	public Collection<CustomerCertificateVO> saveCustomerCertificateDetails(Collection<CustomerCertificateVO> customerCertificates)
	  throws SystemException, RemoteException;
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.updateCustomerStatus
	 *	Added by 	:	A-4789 on 17-Oct-2012
	 * 	Used for 	:	Updates the status of the customer
	 *	Parameters	:	@param customers
	 *	Parameters	:	@return Collection<CustomerVO>
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws CustomerBusinessException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> updateCustomerStatus(Collection<CustomerVO> customers) 
	throws RemoteException,SystemException,CustomerBusinessException; 
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.getCustomerDetails
	 *	Added by 	:	
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilters
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws CustomerBusinessException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> getCustomerDetails(Collection<CustomerFilterVO> customerFilters) 
	throws RemoteException,SystemException,CustomerBusinessException; 
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.loadCustomerDetailsHistory
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	To Load Customer Details History. Added for ICRD-67442.
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> loadCustomerDetailsHistory(CustomerFilterVO customerFilterVO) throws RemoteException,SystemException; 
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.getAllCustomers
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws CustomerBusinessException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> getAllCustomers(CustomerFilterVO customerFilterVO)  
		throws RemoteException,SystemException,CustomerBusinessException; 
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.findUserDetails
	 *	Added by 	:	a-5956 on 19-Oct-2017
	 * 	Used for 	:	ICRD-212854
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param userID
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	UserVO
	 */
	public UserVO findUserDetails(String companyCode, String userID)
			throws RemoteException,SystemException; 
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsBI.findSystemParameterByCodes
	 *	Added by 	:	A-7364 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param parameterCodes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,String>
	 */
	public Map<String, String> findSystemParameterByCodes(Collection<String> parameterCodes) 
			throws RemoteException,SystemException;
	
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.validateStockHolderFromCustomerActivation
	 *	Added by 	:	A-8154 
	 *	Used for	: 	CR ICRD-253447
	 *	Parameters	:	@param customerVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	boolean
	 */
	public boolean validateStockHolderFromCustomerActivation(CustomerVO customerVO)
		    throws RemoteException,SystemException;
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.getInvoiceDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param filterVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	CustomerBillingInvoiceDetailsVO
	 */
	public CustomerBillingInvoiceDetailsVO getBillingInvoiceDetails(
			CustomerFilterVO filterVO) throws RemoteException,SystemException;
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.getCCADetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	List<CCADetailsVO>
	 */
	public List<CCADetailsVO> getCCADetails(String invoiceNumber,String companyCode)throws RemoteException,SystemException;
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.getPaymentDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	Collection<PaymentDetailsVO>
	 */
	public Collection<PaymentDetailsVO> getPaymentDetails(CustomerInvoiceDetailsVO customerInvoiceDetailsVO)throws RemoteException,SystemException;
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.printAccountStatement
	 *	Added by 	:	A-8169 on 14-Nov-2018 
	 *	Used for	: 	ICRD-236527
	 *  Parameters	:	@param reportSpec 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	Map<String, Object>
	 *  Modified return type to byte[] as part of IASCB-45955
	 */
	public ByteObject printAccountStatement(CustomerFilterVO customerFilterVO)throws RemoteException,SystemException;
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.getCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *  Parameters	:	@param ScribbleNoteFilterVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	List<ScribbleNoteDetailsVO>
	 */
	public List<ScribbleNoteDetailsVO> getCustomerScribbleDetails(ScribbleNoteFilterVO scribbleNoteFilterVO)throws RemoteException,SystemException;
	/**
	 * 	Method		:	CustomerMgmntDefaultsBI.saveCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *  Parameters	:	@param ScribbleNoteDetailsVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	void
	 */
	public void saveCustomerScribbleDetails(ScribbleNoteDetailsVO scribbleNoteDetailsVO)throws RemoteException,SystemException;
	
	/**
	 * Method		:	CustomerMgmntDefaultsBI.emailAccountStatement()
	 * Used for 		:	for IASCB-104246
	 */
	public void emailAccountStatement(EmailAccountStatementFeatureVO featureVO) throws RemoteException, SystemException;
	
	/**
	 * Done for IASCB-130291
	 * @param generalMasterGroupFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<GeneralMasterGroupVO> listGroupDetailsForCustomer
	     (GeneralMasterGroupFilterVO generalMasterGroupFilterVO)throws RemoteException,SystemException;
	
	public Collection<ShipmentVO> validateShipmentDetails(ShipmentFilterVO shipmentFilterVO)throws RemoteException,SystemException;
	
	public Collection<CustomerAgentVO> validateSinglePoa(ShipmentFilterVO shipmentFilterVO)throws RemoteException,SystemException;
	
	public Collection<ShipmentHistoryVO> findShipmentHandlingHistory(ShipmentHistoryFilterVO shipmentHistoryFilterVO)throws RemoteException,SystemException;
	
	public String saveBrokerMapping(CustomerVO customerVO) throws CustomerBusinessException, SystemException, RemoteException;

	public void uploadBrokerMappingDocuments(DocumentRepositoryMasterVO documentRepositoryMasterVO) throws CustomerBusinessException, SystemException, RemoteException;
	
	public void interfaceBrokerMappingDocuments(Collection<POADocumentJMSTemplateVO> poaDocumentJMSTemplateVOs) throws CustomerBusinessException, SystemException, RemoteException;

}
