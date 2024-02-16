/*
 * CustomerMgmntDefaultsServicesEJB.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.services.customermanagement.defaults;

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
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerBusinessException;
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntController;
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomerAlreadyAttachedToLoyaltyException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomerMgmntDefaultsBusinessException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomersAlreadyAttatchedException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.LoyaltyProgrammeNotAttachedException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.ParameterExistsException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.ServiceInUseException;
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
import com.ibsplc.icargo.business.shared.customer.CustomerAlreadyExistsException;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
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
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteDetailsVO;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteFilterVO;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.zip.ByteObject;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
//import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;



/**
 * Bean implementation class for Enterprise Bean: CustomerMgmntDefaultsServicesEJB
 *
 *	@ejb.bean description="CustomerMgmntDefaultsServices"
 *	display-name="CustomerMgmntDefaultsServices"
 *	jndi-name="com.ibsplc.icargo.services.customermanagement.defaults.CustomerMgmntDefaultsServicesHome"
 *	name="CustomerMgmntDefaultsServices"
 *	type="Stateless"
 *	view-type="remote"
 *	remote-business-interface="com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI"
 *
 *	@ejb.transaction type="Supports"
 *
 */
public class CustomerMgmntDefaultsServicesEJB
    extends AbstractFacadeEJB implements CustomerMgmntDefaultsBI {

	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
	private static final String CUSTOMERMGMNT_CONTROLLER_BEAN = "customerMgmntController";
	/***
     * Finds all Loyalty programmes
     * @author A-1883
     * @param companyCode
     * @return Collection<LoyaltyProgrammeVO>
     * @throws SystemException
     * @throws RemoteException
     */
    /*public Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(String companyCode)
    throws SystemException,RemoteException {
    	log.entering("CustomerMgmntDefaultsServicesEJB","findAllLoyaltyProgrammes");
    	return new CustomerMgmntController().findAllLoyaltyProgrammes(companyCode);
    }*/

    /**
     * Creates and Modifies Loyalty Programme
     * @author A-1883
     * @param loyaltyProgrammeVO
     * @throws SystemException
     * @throws RemoteException
     * @throws CustomerMgmntDefaultsBusinessException
     */
        public void createLoyaltyProgramme(LoyaltyProgrammeVO loyaltyProgrammeVO)
    	throws SystemException,RemoteException,CustomerMgmntDefaultsBusinessException {
        	log.entering("CustomerMgmntDefaultsServicesEJB","createLoyaltyProgramme");
        	try{
        	 new CustomerMgmntController().createLoyaltyProgramme(loyaltyProgrammeVO);
        	}
        	catch (ParameterExistsException parameterExistsException) {
				throw new CustomerMgmntDefaultsBusinessException(parameterExistsException);
			}catch(CustomersAlreadyAttatchedException customersAlreadyAttatchedException){
				throw new CustomerMgmntDefaultsBusinessException(customersAlreadyAttatchedException);
			}
        }

        /**
         * List Loyalty programmes based on Filter
         * @author A-1883
         * @param loyaltyProgrammeFilterVO
         * @return Collection<LoyaltyProgrammeVO>
         * @throws SystemException
         * @throws RemoteException
         */
        public Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
    			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
    			throws SystemException ,RemoteException {
        	log.entering("CustomerMgmntDefaultsServicesEJB","listLoyaltyProgramme");
        	return  new CustomerMgmntController().listLoyaltyProgramme(
       			loyaltyProgrammeFilterVO);
        }
        /**
         * @author A-1883
         * @param companyCode
         * @return HashMap<String,Collection<LoyaltyAttributeVO>>
         * @throws SystemException
         * @throws RemoteException
         */
        	public HashMap<String,Collection<LoyaltyAttributeVO>> listLoyaltyAttributeDetails
        	  (String companyCode)throws SystemException ,RemoteException{
        		log.entering("CustomerMgmntDefaultsServicesEJB","listLoyaltyAttributeDetails");
            	return new CustomerMgmntController().listLoyaltyAttributeDetails(companyCode);
        	}

    	 /**
         *
         * @param customerGroupVOs
         * @return
         * @throws SystemException
         * @throws RemoteException
         */
    /*     public Collection<String> saveCustomerGroup(Collection<CustomerLoyaltyGroupVO> customerGroupVOs)
         throws SystemException ,RemoteException {
        	 log.entering("CustomerMgmntDefaultsServicesEJB","saveCustomerGroup");
        	 return new CustomerMgmntController().saveCustomerGroup(customerGroupVOs);
         }*/


         /**
          *
          * @param companyCode
          * @param groupCode
          * @return Collection<CustomerGroupVO>
          * @throws SystemException
          * @throws RemoteException
          */
   /*  	public  Collection<CustomerLoyaltyGroupVO> listCustomerGroup(String companyCode ,
        	String groupCode) throws SystemException,RemoteException{
     	 log.entering("CustomerMgmntDefaultsServicesEJB","listCustomerGroup");
     	 return new CustomerMgmntController().listCustomerGroup(
     			companyCode,groupCode);
     	}*/

     	/**
     	 *
     	 * @param companyCode
     	 * @param groupCode
     	 * @param pageNumber
     	 * @return
     	 * @throws SystemException
     	 * @throws RemoteException
     	 */
    /* 	public Page<CustomerLoyaltyGroupVO> customerGroupLOV(String companyCode ,
    			String groupCode,int pageNumber)
    			throws SystemException,RemoteException{
     		log.entering("CustomerMgmntDefaultsServicesEJB","customerGroupLOV");
        	 return new CustomerMgmntController().customerGroupLOV(
        			companyCode,groupCode,pageNumber);
     	}*/

     	/**
     	 *
     	 * @param tempCustomerVOs
     	 * @return String
     	 * @throws SystemException
     	 * @throws RemoteException
     	 */
     	public String saveTempCustomer(Collection<TempCustomerVO> tempCustomerVOs)
	      throws SystemException,RemoteException{
     		log.entering("CustomerMgmntDefaultsServicesEJB","saveTempCustomer");
     		return new CustomerMgmntController().saveTempCustomer(
     				tempCustomerVOs);
     	}


     	/**
     	 *
     	 * @param companyCode
     	 * @param tempCustCode
     	 * @return TempCustomerVO
     	 * @throws SystemException
     	 * @throws RemoteException
     	 */
     	public TempCustomerVO listTempCustomer(String companyCode,
    			String tempCustCode)
     	 throws SystemException,RemoteException {
     		log.entering("CustomerMgmntDefaultsServicesEJB","listTempCustomer");
     		return new CustomerMgmntController().listTempCustomer(
     				companyCode,tempCustCode);
     	}

     	/**
     	 *
     	 * @param listTempCustomerVO
     	 * @return TempCustomerVO
     	 * @throws SystemException
     	 * @throws RemoteException
     	 */
     	public Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO
    			listTempCustomerVO)
     	 throws SystemException,RemoteException{
     		log.entering("CustomerMgmntDefaultsServicesEJB","listTempCustomerDetails");
     		return new CustomerMgmntController().listTempCustomerDetails(
     				listTempCustomerVO);

     	}


     	/**
     	 *
     	 * @param groupLoyaltyProgrammeVOs
     	 * @throws SystemException
     	 * @throws RemoteException
     	 * @throws CustomerBusinessException
     	 */
     	public void saveGroupLoyaltyPgm(Collection<CustomerGroupLoyaltyProgrammeVO>
	       groupLoyaltyProgrammeVOs)
     	 throws SystemException,RemoteException,CustomerBusinessException{
     		log.entering("CustomerMgmntDefaultsServicesEJB","saveGroupLoyaltyPgm");
     		try {
				new CustomerMgmntController().saveGroupLoyaltyPgm(
						groupLoyaltyProgrammeVOs);
			} catch (CustomerAlreadyAttachedToLoyaltyException e) {
				throw new CustomerBusinessException(e);
			}
     	}

     	/**
     	 *
     	 * @param companyCode
     	 * @param groupCode
     	 * @return Collection<CustomerGroupLoyaltyProgrammeVO>
     	 * @throws SystemException
     	 * @throws RemoteException
     	 */
     	public Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
    			String companyCode,String groupCode)
    			throws SystemException,RemoteException {
     		log.entering("CustomerMgmntDefaultsServicesEJB","listGroupLoyaltyPgm");
     		return new CustomerMgmntController().listGroupLoyaltyPgm(
     				companyCode,groupCode);
     	}
     	/**
    	 * @author A-1883
    	 * Lists All Loyalty Programmes based on Filter
    	 * @param loyaltyProgrammeFilterVO
    	 * @param pageNumber
    	 * @return Page<LoyaltyProgrammeVO>
    	 * @throws SystemException
    	 * @throws RemoteException
    	 */
    	 public Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
    			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO,int pageNumber)
    			throws SystemException,RemoteException{
    		 log.entering("CustomerMgmntDefaultsServicesEJB","listAllLoyaltyProgrammes");
      		return new CustomerMgmntController().listAllLoyaltyProgrammes(
      				loyaltyProgrammeFilterVO,pageNumber);
    	 }
    	 /**
    	  * @author A-1883
    	  * @param airWayBillLoyaltyProgramVO
    	  * @throws SystemException
    	  * @throws RemoteException
    	  */
    	/*public void generateLoyaltyPointsForAWB(AirWayBillLoyaltyProgramVO
    				airWayBillLoyaltyProgramVO)
    	 throws SystemException,RemoteException {
    		log.entering("CustomerMgmntDefaultsServicesEJB","generateLoyaltyPointsForAWB");
    		new CustomerMgmntController().generateLoyaltyPointsForAWB(airWayBillLoyaltyProgramVO);
    		log.exiting("CustomerMgmntDefaultsServicesEJB","generateLoyaltyPointsForAWB");
    	 }*/

    	 /**
   	  *
   	  * @param customerVO
   	  * @return String
   	  * @throws SystemException
   	  * @throws RemoteException
    	 * @throws CustomerBusinessException
   	  */
   	 public String registerCustomer(CustomerVO customerVO)
   	 throws SystemException,RemoteException, CustomerBusinessException{
   		 log.entering("CustomerMgmntDefaultsServicesEJB","registerCustomer");
   		return new CustomerMgmntController().registerCustomer(customerVO);

   	 }


   	 /**
	  *
	  * @param customerFilterVO
	  * @return CustomerVO
	  * @throws SystemException
	  * @throws RemoteException
	  */
   	public CustomerVO listCustomer(CustomerFilterVO customerFilterVO)
	 throws SystemException,RemoteException{
		 log.entering("CustomerMgmntDefaultsServicesEJB","listCustomer");
		 return new CustomerMgmntController().listCustomer(customerFilterVO);
	 }

    /**
	  *
	  * @param customerListFilterVO
	  * @return
	  * @throws SystemException
	  * @throws RemoteException
	  */
	/* public Page<CustomerVO> listCustomerDetails(CustomerListFilterVO
	 customerListFilterVO) throws SystemException,RemoteException{
		 log.entering("CustomerMgmntDefaultsServicesEJB","listCustomerDetails");
		 return new CustomerMgmntController().listCustomerDetails(customerListFilterVO);
	 }*/

	 /**
	  *
	  * @param customerGroupVOs
	  * @throws SystemException
	  * @throws RemoteException
	 * @throws CustomerBusinessException
	  */
/*	 public void checkForCustomer(Collection<CustomerLoyaltyGroupVO>
		customerGroupVOs) throws SystemException,RemoteException,
		CustomerBusinessException{
		 log.entering("CustomerMgmntDefaultsServicesEJB","checkForCustomer");
		  try {
			new CustomerMgmntController().checkForCustomer(customerGroupVOs);
		} catch (CustomerAttachedToGroupException e) {
			throw new CustomerBusinessException(e);
		}
	 }*/
	 /**
	  *
	  * @param companyCode
	  * @param customerCode
	  * @param groupCode
	  * @return Collection<AttachLoyaltyProgrammeVO>
	  * @throws SystemException
	  * @throws RemoteException
	  */
	 public Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
				String companyCode,String customerCode,String groupCode)
		         throws SystemException,RemoteException{
		  log.entering("CustomerMgmntDefaultsServicesEJB","listLoyaltyPgmToCustomers");
		  return new CustomerMgmntController().listLoyaltyPgmToCustomers(
				  companyCode,customerCode,groupCode);

	}
	 /**
	  * @author A-1883
	  * @param listPointsAccumulatedFilterVO
	  * @param pageNumber
	  * @return Page<ListCustomerPointsVO>
	  * @throws SystemException
	  * @throws RemoteException
	  */
	public  Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(ListPointsAccumulatedFilterVO
			 listPointsAccumulatedFilterVO,int pageNumber)
			 throws SystemException,RemoteException {
		log.entering("CustomerMgmntDefaultsServicesEJB","listLoyaltyPointsForAwb");
		  return new CustomerMgmntController().listLoyaltyPointsForAwb(
				  listPointsAccumulatedFilterVO,pageNumber);
	}

	/**
	   *
	   * @param attachLoyaltyProgrammeVOs
	   * @throws SystemException
	   * @throws RemoteException
	   */
	 public void saveLoyaltyPgmToCustomers( Collection<AttachLoyaltyProgrammeVO>
		attachLoyaltyProgrammeVOs)
	  throws SystemException,RemoteException{
		 log.entering("CustomerMgmntDefaultsServicesEJB","saveLoyaltyPgmToCustomers");
		 new CustomerMgmntController().saveLoyaltyPgmToCustomers(
				 attachLoyaltyProgrammeVOs);
	  }
	 	/**
		 * @author A-1883
		 * @param companyCode
		 * @return Collection<ParameterDescriptionVO>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<ParameterDescriptionVO> findParameterDetails(String companyCode)
		throws SystemException,RemoteException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findParameterDetails");
			return new CustomerMgmntController().findParameterDetails(
					 companyCode);
		}

		/**
		 *
		 * @param airWayBillLoyaltyProgramFilterVO
		 * @return Collection<String>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<String> listPointAccumulated(
				AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
				throws SystemException,RemoteException{
			log.entering("CustomerMgmntDefaultsServicesEJB","listPointAccumulated");
			return new CustomerMgmntController().listPointAccumulated(
					airWayBillLoyaltyProgramFilterVO);
		}

		/**
		 *
		 * @param airWayBillLoyaltyProgramFilterVO
		 * @return Collection<AirWayBillLoyaltyProgramVO>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<AirWayBillLoyaltyProgramVO> showPoints(AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
		throws SystemException,RemoteException{
			log.entering("CustomerMgmntDefaultsServicesEJB","showPoints");
			return new CustomerMgmntController().showPoints(
					airWayBillLoyaltyProgramFilterVO);
		}

		/**
		 *
		 * @param companyCode
		 * @param customerCode
		 * @return Collection<CustomerContactVO>
		 * @throws SystemException
		 * @throws RemoteException
		 */
		 public Collection<CustomerContactVO> customerContactsLov(
				 String companyCode,String customerCode)
				 throws SystemException,RemoteException{
			 log.entering("CustomerMgmntDefaultsServicesEJB","customerContactsLov");
				return new CustomerMgmntController().customerContactsLov(
						companyCode,customerCode);
		 }

		 /**
			 *
			 * @param pointsVOs
			 * @throws RemoteException
			 * @throws SystemException
			 */
		public void saveCustomerContactPoints(Collection<CustomerContactPointsVO>
		     pointsVOs) throws RemoteException,SystemException{
			 log.entering("CustomerMgmntDefaultsServicesEJB","saveCustomerContactPoints");
			 new CustomerMgmntController().saveCustomerContactPoints(
					 pointsVOs);
		 }

		/**
	     * @param companyCode
	     * @param customerCode
	     * @return Collection<CustomerContactPointsVO>
	     * @throws RemoteException
	     * @throws SystemException
	     *
	     */
	    public Collection<CustomerContactPointsVO> listCustomerContactPoints(
	    		String companyCode,String customerCode)
	    		throws RemoteException,SystemException{
	    	log.entering("CustomerMgmntDefaultsServicesEJB","listCustomerContactPoints");
	    	return new CustomerMgmntController().listCustomerContactPoints(
	                companyCode,customerCode);

	    }

	    /**
	     *
	     * @param customerVOs
	     * @throws RemoteException
	     * @throws SystemException
	     */
	    public void changeStatusOfCustomers(Collection<CustomerVO> customerVOs)
	    throws RemoteException,SystemException{
	    	log.entering("CustomerMgmntDefaultsServicesEJB","changeStatusOfCustomers");
	    	 new CustomerMgmntController().changeStatusOfCustomers(
	    			customerVOs);
	    }
        /**
         * @param redemptionValidationVO
         * @return  Collection<ErrorVO>
         * @throws SystemException
         * @throws RemoteException
         * @throws CustomerMgmntDefaultsBusinessException
         */
	    public Collection<ErrorVO> validateCustomerForPointsRedemption(
				 RedemptionValidationVO redemptionValidationVO)
				 throws SystemException,RemoteException,CustomerMgmntDefaultsBusinessException {
	    	log.entering("CustomerMgmntDefaultsServicesEJB","validateCustomerForPointsRedemption");
	    	 Collection<ErrorVO>  errors = null;
	    	 try{
	    		 errors = new CustomerMgmntController().validateCustomerForPointsRedemption(
	    			 redemptionValidationVO);
	    	 }catch(LoyaltyProgrammeNotAttachedException loyaltyProgrammeNotAttachedException){
					throw new CustomerMgmntDefaultsBusinessException(
							loyaltyProgrammeNotAttachedException);
			 }
			log.exiting("CustomerMgmntDefaultsServicesEJB","validateCustomerForPointsRedemption");
			return errors;
	    }
	    /**
		  * @author a-1883
		  * @param companyCode
		  * @param currentDate
		  * @param pageNumber
		  * @return Page<LoyaltyProgrammeVO>
		  * @throws SystemException
		  * @throws RemoteException
		  */
		 public Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
				 String companyCode,LocalDate currentDate,int pageNumber)
				 throws SystemException,RemoteException {
			 log.entering("CustomerMgmntDefaultsServicesEJB","runningLoyaltyProgrammeLOV");
	    		return new CustomerMgmntController().runningLoyaltyProgrammeLOV(companyCode,
	    					currentDate,pageNumber);
		 }

        /* ********************************** added by kiran starts ********************************** */

        /**
         * @author A-2049
         * @param customerGroupFilterVO
         * @return Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO>
         * @throws RemoteException
         * @throws SystemException
         */
        public Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> viewCustomerGroups(CustomerGroupFilterVO customerGroupFilterVO)
        throws RemoteException,SystemException{
            log.entering("CustomerMgmntDefaultsServicesEJB","viewCustomerGroups");
            return new CustomerMgmntController().viewCustomerGroups(customerGroupFilterVO);
        }

        /* (non-Javadoc)
         * @see com.ibsplc.icargo.business.shared.customer.CustomerBI#listCustomerGroupDetails(java.lang.String, java.lang.String)
         */
        /**
         *  @author A-2049
         *  @return com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO
         *  @param companyCode
         *  @param groupCode
         *  @throws RemoteException
         *  @throws SystemException
         */
        public com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO listCustomerGroupDetails(String companyCode, String groupCode)
        throws RemoteException, SystemException {
            log.entering("CustomerServicesEJB","listCustomerGroupDetails");
            return new CustomerMgmntController().listCustomerGroupDetails(companyCode,groupCode);
        }

        /**
         * @author A-2049
         * @param customerGroupVOs
         * @throws RemoteException
         * @throws SystemException
         * @throws CustomerBusinessException
         */
        public void deleteCustomerGroups(Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs)
        throws RemoteException, SystemException ,CustomerBusinessException{
            log.entering("CustomerServicesEJB","listCustomerGroupDetails");
            new CustomerMgmntController().deleteCustomerGroups(customerGroupVOs);

        }

        /**
         * @author A-2049
         * @param customerGroupVOs
         * @return generatedGroupCode
         * @throws RemoteException
         * @throws SystemException
         */
        public String saveCustomerGroupDetails(Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs)
        throws RemoteException, SystemException {
            log.entering("CustomerMgmntDefaultsServicesEJB","saveCustomerGroupDetails");
            return new CustomerMgmntController().saveCustomerGroupDetails(customerGroupVOs);
        }

        /* ********************************** added by kiran ends ********************************** */
        /**
         *
         * @param customerVOs
         * @throws RemoteException
         * @throws SystemException
         */
        /*public void deleteCustomers(Collection<CustomerVO> customerVOs)
        throws RemoteException,SystemException{
        	 log.entering("CustomerMgmntDefaultsServicesEJB","deleteCustomers");
        	 new CustomerMgmntController().deleteCustomers(customerVOs);
        }*/

        /**
         *
         * @param servicesVO
         *
         * @throws RemoteException
         * @throws SystemException
         * @throws CustomerMgmntDefaultsBusinessException
         */
        public void saveCustomerServices(CustomerServicesVO servicesVO)
        throws RemoteException,SystemException,CustomerMgmntDefaultsBusinessException{
        	 log.entering("CustomerMgmntDefaultsServicesEJB","saveCustomerServices");
        	 try {
				new CustomerMgmntController().saveCustomerServices(servicesVO);
			} catch (ServiceInUseException e) {
				throw new CustomerMgmntDefaultsBusinessException(e);
			}
        }

        /**
         *
         * @param companyCode
         * @param serviceCode
         * @return
         * @throws RemoteException
         * @throws SystemException
         */
         public CustomerServicesVO listCustomerServices(String companyCode,
         		String serviceCode) throws RemoteException,SystemException{
        	 log.entering("CustomerMgmntDefaultsServicesEJB","listCustomerServices");
        	 return new CustomerMgmntController().listCustomerServices(
        			 companyCode,serviceCode);
         }

         /**
          *
          * @param companyCode
          * @param serviceCode
          * @param pageNumber
          * @return
          * @throws RemoteException
          * @throws SystemException
          */
         public Page<CustomerServicesVO> customerServicesLOV(String companyCode,
         		String serviceCode,int pageNumber) throws RemoteException,SystemException{
        	 log.entering("CustomerMgmntDefaultsServicesEJB","listCustomerServices");
        	 return new CustomerMgmntController().customerServicesLOV(
        			 companyCode,serviceCode,pageNumber);
         }

     	/**
     	 * @author a-1863
     	 * @param reportSpec
     	 * @return Map<String, Object>
     	 * @throws SystemException
     	 * @throws ReportGenerationException
     	 */
     	public Map<String, Object> generateLoyaltyDetailsReport(ReportSpec reportSpec)
    	throws SystemException, RemoteException {
     		return new CustomerMgmntController().generateLoyaltyDetailsReport(reportSpec);
     	}
     	/**
		 *
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws ReportGenerationException
		 */
		public Map<String, Object> printListCustomerReport(ReportSpec reportSpec)
				throws SystemException, RemoteException{
			log.entering("CUSTOMER MANAGEMENT","printListCustomerReport");
			log.exiting("CUSTOMER MANAGEMENT","printListCustomerReport");
			return new CustomerMgmntController().printListCustomerReport(reportSpec);
		}
		
		/**
		 * Method will return all the shipments with the same prefix and awb number.
		 * It wil return empty Set if no awbs exist with the same prefix and awb
		 * number
		 *
		 * @param shipmentFilterVO
		 * @return Collection<ShipmentVO>
		 * @throws RemoteException
		 * @throws SystemException
		 */
		/*public Collection<ShipmentVO> findShipments(
				ShipmentFilterVO shipmentFilterVO) throws RemoteException,
				SystemException// ,ShipmentBusinessException
		{
			log.entering("CUSTOMER MANAGEMENT", "findShipments");
			Collection<ShipmentVO> shipments = null;
			shipments = new CustomerMgmntController().findShipments(shipmentFilterVO);
			log.log(Log.FINE, "The Shipments is got----> ", shipments);
			return shipments;
		}*/
		
		/**
		 * Method will upload the datas from the excel sheet
		 * @param fileType
		 * @param tsaData
		 * @return Collection<ErrorVO>
		 * @throws RemoteException
		 * @throws SystemException
		 */
		public Collection<ErrorVO> uploadTSAData(String fileType,
	    		byte[] tsaData) throws RemoteException,SystemException{
			log.entering("CUSTOMER MANAGEMENT", "uploadTSAData");
			Collection<ErrorVO> errors = null;
			errors = new CustomerMgmntController().uploadTSAData(fileType, tsaData);
			log.log(Log.FINE, "The errors got----> ", errors);
			return errors;
		}
		/**
		 * @param spotRateRequestFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<SpotRateRequestDetailsVO>
		 */
		public Page<SpotRateRequestDetailsVO> findSpotRateRequestsByFilter(
				SpotRateRequestFilterVO spotRateRequestFilterVO) 
				throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findSpotRateRequestsByFilter");
			return new CustomerMgmntController().findSpotRateRequestsByFilter(spotRateRequestFilterVO);
		}

		/**
		 * @param invoiceFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<InvoiceDetailsVO>
		 */
		public Page<InvoiceDetailsVO> listInvoices(InvoiceFilterVO invoiceFilterVO) 
		throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","listInvoices");
			return new CustomerMgmntController().listInvoices(invoiceFilterVO);
		}

		/**
		 * @param messageListFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<MessageVO>
		 */
		public Page<MessageHistoryVO> findMessageForCustomers(MessageListFilterVO messageListFilterVO,Collection<String> address) 
		throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findMessageForCustomers");
			return new CustomerMgmntController().findMessageForCustomers(messageListFilterVO,address);
		}

		/**
		 * @param bookingFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<BookingVO>
		 */
		public Page<BookingVO> findBookings(BookingFilterVO bookingFilterVO) 
		throws RemoteException, SystemException  {
			log.entering("CustomerMgmntDefaultsServicesEJB","findBookings");
			return new CustomerMgmntController().findBookings(bookingFilterVO);
		}

	

		/**
		 * @param stockDetailsFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return StockDetailsVO
		 */
		public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO) 
		throws RemoteException, SystemException,CustomerBusinessException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findCustomerStockDetails");
			return new CustomerMgmntController().findCustomerStockDetails(stockDetailsFilterVO);
		}
		
		/**
		 * @param customerAllotmentEnquiryFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 *  @return Page<CustomerAllotmentEnquiryVO>
		 */
		public Page<CustomerAllotmentEnquiryVO> findCustomerEnquiryDetails(
		CustomerAllotmentEnquiryFilterVO customerAllotmentEnquiryFilterVO) 
		throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findCustomerEnquiryDetails");
			return new CustomerMgmntController().findCustomerEnquiryDetails(customerAllotmentEnquiryFilterVO);
		}

		/**
		 * @param tariffFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<RateLineVO>
		 */
		
		public Page<RateLineVO> findRateLineDetailsByFilter(
		TariffFilterVO tariffFilterVO) throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findRateLineDetailsByFilter");
			return new CustomerMgmntController().findRateLineDetailsByFilter(tariffFilterVO);
		}

		/**
		 * @param listCharterOperationsFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<CharterOperationsVO>
		 */
		public Page<CharterOperationsVO> findCharterForCustomer(
		ListCharterOperationsFilterVO listCharterOperationsFilterVO) 
		throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findCharterForCustomer");
			return new CustomerMgmntController().findCharterForCustomer(listCharterOperationsFilterVO);
		}

		/**
		 * @param customerFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return CustomerVO
		 */
		public CustomerVO customerEnquiryDetails(CustomerFilterVO customerFilterVO) 
		throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","customerEnquiryDetails");
			return new CustomerMgmntController().customerEnquiryDetails(customerFilterVO);
		}
		/**
		 * @param claimFilterVO
		 * @throws RemoteException
		 * @throws SystemException
		 * @author A-2883
		 * @return Page<ClaimListVO>
		 */
		public Page<ClaimListVO> findClaimList(ClaimFilterVO claimFilterVO) 
		throws RemoteException, SystemException {
			log.entering("CustomerMgmntDefaultsServicesEJB","findClaimList");
        	return new CustomerMgmntController().findClaimList(claimFilterVO);
		}

		/**
		 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#saveCustomerDetails(java.util.Collection)
		 *	Added by 			: A-4789 on 15-Oct-2012
		 * 	Used for 	:
		 *	Parameters	:	@param customerDetails
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException
		 *	Parameters	:	@throws CustomerAlreadyExistsException 
		 */
		@Override
		public Collection<CustomerVO> saveCustomerDetails(
				Collection<CustomerVO> customerDetails) throws SystemException,
				RemoteException, CustomerAlreadyExistsException {
			return new CustomerMgmntController().saveCustomerDetails(customerDetails);
		}

		/**
		 *	Overriding Method	:@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#saveCustomerCreditDetails()
		 *	Added by 	: 	A-10509 on 08-Nov-2022
		 * 	Used for 	:
		 *	Parameters	:	@param customerCreditVO 
		 *	Parameters	:	@return 
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException 
		 */
		@Override
		public CustomerCreditVO saveCustomerCreditDetails(CustomerCreditVO customerCreditVO)
				throws SystemException, RemoteException {
			return new CustomerMgmntController().saveCustomerCreditDetails(customerCreditVO);
		}
		
		/**
		 *	Overriding Method	:@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#findCustomerCreditDetails()
		 *	Added by 	: 	A-10509 on 10-Nov-2022
		 * 	Used for 	:
		 *	Parameters	:	@param customerCreditFilterVO
		 *	Parameters	:	@return 
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException 
		 */
		@Override
		public CustomerCreditVO findCustomerCreditDetails(CustomerCreditFilterVO customerCreditFilterVO)
				throws SystemException, RemoteException {
			return new CustomerMgmntController().findCustomerCreditDetails(customerCreditFilterVO);
		}

	/**
	 *
	 * @param customerVO
	 * @return
	 * @throws SystemException
	 */
		@Override
		public Collection<CustomerCertificateVO> findCustomerCertificates(CustomerVO customerVO) throws SystemException,RemoteException {
			return new CustomerMgmntController().findCustomerCertificates(customerVO);
	     }
		
		/**
		 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#saveCustomerCertificateDetails(java.util.Collection)
		 *	Added by 			: A-4789 on 15-Oct-2012
		 * 	Used for 	:
		 *	Parameters	:	@param customerCertificates
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException 
		 */
		@Override
		public Collection<CustomerCertificateVO> saveCustomerCertificateDetails(
				Collection<CustomerCertificateVO> customerCertificates)
				throws SystemException, RemoteException {
			return new CustomerMgmntController().saveCustomerCertificateDetails(customerCertificates);
		}

		/**
		 * 
		 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#updateCustomerStatus(java.util.Collection)
		 *	Added by 			: A-4789 on 17-Oct-2012
		 * 	Used for 	:
		 *	Parameters	:	@param customers
		 *	Parameters	:	@return Collection<CustomerVO>
		 *	Parameters	:	@throws RemoteException
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws CustomerBusinessException
		 */
		@Override
		public Collection<CustomerVO> updateCustomerStatus(Collection<CustomerVO> customers) 
		throws RemoteException, SystemException, CustomerBusinessException {
			return new CustomerMgmntController().updateCustomerStatus(customers);
		}
		
		public Collection<CustomerVO> getCustomerDetails(Collection<CustomerFilterVO> customerFilters) 
		throws RemoteException,SystemException,CustomerBusinessException {
			return new CustomerMgmntController().getCustomerDetails(customerFilters);
		}

	/**
	 * 	
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#loadCustomerDetailsHistory(com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO)
	 *	Added by 			:   A-5163 on August 22, 2014
	 * 	Used for 	:	To Load Customer Details History. ICRD-67442.
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<CustomerVO> loadCustomerDetailsHistory(CustomerFilterVO customerFilterVO) 
				throws RemoteException, SystemException {
		return new CustomerMgmntController().loadCustomerDetailsHistory(customerFilterVO);
	} 
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsServicesEJB.getAllCustomers
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
		throws RemoteException,SystemException,CustomerBusinessException {
		return new CustomerMgmntController().getAllCustomers(customerFilterVO);
	}
	/**
	 * 
	 * 	Method		:	CustomerMgmntDefaultsServicesEJB.findUserDetails
	 *	Added by 	:	a-5956 on 19-Oct-2017
	 * 	Used for 	:	ICRD-212854
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param userID
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	UserVO
	 */
	public UserVO findUserDetails(String companyCode, String userID)throws RemoteException, SystemException{
		log.entering("CustomerMgmntDefaultsServicesEJB","findCharterForCustomer");
		return new CustomerMgmntController().findUserDetails(companyCode,userID);
	}

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#findSystemParameterByCodes(java.util.Collection)
	 *	Added by 			: A-7364 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param parameterCodes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	public Map<String, String> findSystemParameterByCodes(Collection<String> parameterCodes) 
			throws RemoteException,SystemException{
		return new CustomerMgmntController().findSystemParameterByCodes(parameterCodes);
	}
	
	//Added as part of CR ICRD-253447
	/**
	 * 	Method		:	CustomerMgmntDefaultsServicesEJB.validateStockHolderFromCustomerActivation
	 *	Added by 	:	A-8154 
	 *	Parameters	:	@param customerVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	boolean
	 */
	public boolean validateStockHolderFromCustomerActivation(CustomerVO customerVO)
			throws SystemException,RemoteException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "validateStockHolderFromCustomerActivation");
		return new CustomerMgmntController().validateStockHolderFromCustomerActivation(customerVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#getBillingInvoiceDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param filterVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	CustomerBillingInvoiceDetailsVO
	 */
	public CustomerBillingInvoiceDetailsVO getBillingInvoiceDetails(
			CustomerFilterVO filterVO) throws RemoteException, SystemException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "getBillingInvoiceDetails");
		return new CustomerMgmntController().getBillingInvoiceDetails(filterVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#getCCADetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	 List<CCADetailsVO>
	 */
	public List<CCADetailsVO> getCCADetails(String invoiceNumber,String companyCode) throws RemoteException, SystemException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "getCCADetails");
		return new CustomerMgmntController().getCCADetails(invoiceNumber,companyCode);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#getPaymentDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	 Collection<PaymentDetailsVO>
	 */
	public Collection<PaymentDetailsVO> getPaymentDetails(CustomerInvoiceDetailsVO customerInvoiceDetailsVO)
			throws RemoteException, SystemException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "getPaymentDetails");
		return new CustomerMgmntController().getPaymentDetails(customerInvoiceDetailsVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#printAccountStatement
	 *	Added by 	:	A-8169 on 14-Nov-2018 
	 *	Used for	: 	ICRD-236527
	 *  Parameters	:	@param reportSpec 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	Map<String, Object>
	 *  Modified return type to byte[] as part of IASCB-45955
	 */
	public ByteObject printAccountStatement(CustomerFilterVO customerFilterVO)
			throws RemoteException, SystemException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "printAccountStatement");
		return new ByteObject(new CustomerMgmntController().printAccountStatement(customerFilterVO));
		//return new CustomerMgmntController().printAccountStatement(customerFilterVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#getCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *  Parameters	:	@param ScribbleNoteDetailsVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	List<ScribbleNoteDetailsVO>
	 */
	public List<ScribbleNoteDetailsVO> getCustomerScribbleDetails(ScribbleNoteFilterVO scribbleNoteFilterVO)
			throws RemoteException, SystemException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "getCustomerScribbleDetails");
		return new CustomerMgmntController().getCustomerScribbleDetails(scribbleNoteFilterVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#saveCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *  Parameters	:	@param ScribbleNoteDetailsVO 
	 *	Parameters	:	@throws SystemException 	
	 * 	Parameters	:	@throws RemoteException
	 *  Return type	: 	void
	 */
	public void saveCustomerScribbleDetails(ScribbleNoteDetailsVO scribbleNoteDetailsVO)
			throws RemoteException, SystemException {
		log.entering("CustomerMgmntDefaultsServicesEJB", "saveCustomerScribbleDetails");
		new CustomerMgmntController().saveCustomerScribbleDetails(scribbleNoteDetailsVO);
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#emailAccountStatement(com.ibsplc.icargo.business.msgbroker.message.vo.template.email.CustomerAccountStatementEmailTemplateVO)
	 */
	@Override
	public void emailAccountStatement(EmailAccountStatementFeatureVO featureVO)
			throws RemoteException, SystemException {
		CustomerMgmntController controller = (CustomerMgmntController) SpringAdapter.getInstance()
				.getBean("customerMgmntController");
		controller.emailAccountStatement(featureVO);
	}
	/**
	 * Done for IASCB-130291
	 * @param generalMasterGroupFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<GeneralMasterGroupVO> listGroupDetailsForCustomer
							(GeneralMasterGroupFilterVO generalMasterGroupFilterVO)
										throws RemoteException,SystemException {
		CustomerMgmntController controller = (CustomerMgmntController) SpringAdapter.getInstance()
				.getBean("customerMgmntController");
		return controller.listGroupDetailsForCustomer(generalMasterGroupFilterVO);
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#validateShipmentDetails(com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO)
	 * Used for 		:	to validate shipment details
	 */
	public Collection<ShipmentVO> validateShipmentDetails(
			ShipmentFilterVO shipmentFilterVO) throws SystemException{
		return new CustomerMgmntController().validateShipmentDetails(shipmentFilterVO);
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#validateSinglePoa(com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO)
	 * Used for 		:	to validate single poa details
	 */
	public Collection<CustomerAgentVO> validateSinglePoa(
			ShipmentFilterVO shipmentFilterVO) throws SystemException{
		return new CustomerMgmntController().validateSinglePoa(shipmentFilterVO);
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#findShipmentHandlingHistory(com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO)
	 * Used for 		:	to find shipment history
	 */
	public Collection<ShipmentHistoryVO> findShipmentHandlingHistory(
			ShipmentHistoryFilterVO shipmentHistoryFilterVO) throws SystemException{
		return new CustomerMgmntController().findShipmentHandlingHistory(shipmentHistoryFilterVO);
	}
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#saveBrokerMapping(com.ibsplc.icargo.business.shared.customer.vo.CustomerVO)
	 * Used for 		:	to save customer broker mapping
	 */
	@Override
	public String saveBrokerMapping(CustomerVO customerVO)
			throws CustomerBusinessException, SystemException, RemoteException {
		CustomerMgmntController controller = (CustomerMgmntController) SpringAdapter.getInstance().getBean(CUSTOMERMGMNT_CONTROLLER_BEAN);
		return controller.saveBrokerMapping(customerVO);
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#uploadBrokerMappingDocuments(com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO)
	 * Used for 		:	to upload customer broker poa documents
	 */
	@Override
	public void uploadBrokerMappingDocuments(DocumentRepositoryMasterVO documentRepositoryMasterVO)
			throws CustomerBusinessException, SystemException, RemoteException {
		CustomerMgmntController controller = (CustomerMgmntController) SpringAdapter.getInstance().getBean(CUSTOMERMGMNT_CONTROLLER_BEAN);
		controller.uploadBrokerMappingDocuments(documentRepositoryMasterVO);
	}

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntDefaultsBI#interfaceBrokerMappingDocuments(java.util.Collection)
	 * Used for 		:	to interface customer broker poa documents
	 */
	@Override
	public void interfaceBrokerMappingDocuments(Collection<POADocumentJMSTemplateVO> poaDocumentJMSTemplateVOs)
			throws CustomerBusinessException, SystemException, RemoteException {
		CustomerMgmntController controller = (CustomerMgmntController) SpringAdapter.getInstance().getBean(CUSTOMERMGMNT_CONTROLLER_BEAN);
		controller.interfaceBrokerMappingDocuments(poaDocumentJMSTemplateVOs);
	}
}
