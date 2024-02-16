/*
 * CustomerMgmntDefaultsDAO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.util.Collection;

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
//import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerLoyaltyGroupVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-1496
 *
 */
public interface CustomerMgmntDefaultsDAO {
	
	
	/***
	 * Finds All Loyalty programmes
	 * @author A-1883
	 * @param companyCode
	 * @return Collection<LoyaltyProgrammeVO>
	 */
	/*Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(String companyCode)
	throws SystemException ;*/
	/**
	 * List Loyalty programmes based on Filter
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
			throws SystemException;
	/**
	 * 
	 * @param companyCode
	 * @return Collection<LoyaltyAttributeVO>
	 * @throws SystemException
	 */
	Collection<LoyaltyAttributeVO> listLoyaltyAttributeDetails
		  (String companyCode)throws SystemException;
	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupVO>
	 * @throws SystemException
	 */
//	Collection<CustomerLoyaltyGroupVO> listCustomerGroup(String companyCode ,
//	       	String groupCode) throws SystemException;
	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @param pageNumber 
	 * @return
	 * @throws SystemException
	 */
	//Page<CustomerLoyaltyGroupVO> customerGroupLOV(String companyCode ,
	//		String groupCode,int pageNumber) throws SystemException;
	
	
	/**
	 * 
	 * @param companyCode
	 * @param tempCustCode
	 * @return TempCustomerVO
	 * @throws SystemException
	 */
//	TempCustomerVO listTempCustomer(String companyCode,
//			String tempCustCode)  throws SystemException;
	/**
	 * 
	 * @param listTempCustomerVO
	 * @return Page<TempCustomerVO>
	 * @throws SystemException
	 */
//	Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO 
//			listTempCustomerVO)   throws SystemException;
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<LoyaltyProgrammeVO> 
	 * @throws SystemException
	 */
	Collection<LoyaltyProgrammeVO>  validateParameter(LoyaltyProgrammeVO loyaltyProgrammeVO)
    throws SystemException;
	
	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupLoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
			String companyCode,String groupCode)
			 throws SystemException;
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	Collection<String> checkCustomerAttached(LoyaltyProgrammeVO loyaltyProgrammeVO) 
		throws	SystemException;
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	Collection<String> checkLoyaltyProgrammeAttached(LoyaltyProgrammeVO 
			loyaltyProgrammeVO)	throws	SystemException;
	/**
	 * @author A-1883
	 * @param filterVO
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
			LoyaltyProgrammeFilterVO filterVO,int pageNumber)
			throws SystemException;
	/**
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	 Collection<LoyaltyProgrammeVO> findAttachedLoyaltyProgrammes(
				AirWayBillLoyaltyProgramVO airWayBillLoyaltyProgramVO) 
				throws SystemException; 
	 /**
	  * @author A-1883
	  * @param listPointsAccumulatedFilterVO
	  * @param pageNumber
	  * @return  Page<ListCustomerPointsVO>
	  * @throws SystemException
	  */
	 Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(ListPointsAccumulatedFilterVO 
			 listPointsAccumulatedFilterVO,int pageNumber)	throws SystemException; 
	 /**
	  * 
	  * @param customerGroupVO 
	  * @return String
	  * @throws SystemException
	  */
	// String checkForCustomer(CustomerLoyaltyGroupVO 
	//	customerGroupVO) throws SystemException;
	 /**
	  * 
	  * @param companyCode
	  * @param customerCode
	  * @param groupCode
	  * @return Collection<AttachLoyaltyProgrammeVO>
	  * @throws SystemException
	  */
	 Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
				String companyCode,String customerCode,String groupCode)
				throws SystemException;
	 /**
	  * @author A-1883
	  * @param companyCode
	  * @return Collection<ParameterDescriptionVO>
	  * @throws SystemException
	  */
	 Collection<ParameterDescriptionVO> findParameterDetails(String companyCode)
	 			throws SystemException;
	 /**
	  * 
	  * @param airWayBillLoyaltyProgramFilterVO
	  * @return  Collection<String>
	  * @throws SystemException
	  */
	 Collection<String> listPointAccumulated(
			AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
			throws SystemException;
	 /**
	  * 
	  * @param airWayBillLoyaltyProgramFilterVO
	  * @return  Collection<AirWayBillLoyaltyProgramVO>
	  * @throws SystemException
	  */
	 Collection<AirWayBillLoyaltyProgramVO> showPoints(
	    		AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
		throws SystemException;
	 /**
	  * Finds unit of pameter (Distance and Revenue)
	  * @author A-1883
	  * @param companyCode
	  * @param parameterCode
	  * @return String 
	  * @throws SystemException
	  */
	 String findParameterUnit(String companyCode,String parameterCode)
	 throws SystemException;
	 /**
	  * 
	  * @param programPointVOs
	  * @return double
	  * @throws SystemException
	  */
	 double findEntryPoints(Collection<AttachLoyaltyProgrammeVO> 
     programPointVOs)throws SystemException;
	 /**
	  * @author a-1883
	  * @param redemptionValidationVO
	  * @return Collection<LoyaltyProgrammeVO>
	  * @throws SystemException
	  */
	 Collection<LoyaltyProgrammeVO> validateCustomerForPointsRedemption(
			 RedemptionValidationVO redemptionValidationVO)
			 throws SystemException;
	 /**
	  * 
	  * @param programmeVO
	  * @return
	  * @throws SystemException
	  */
	 String checkForCustomerLoyalty(CustomerGroupLoyaltyProgrammeVO 
	    		programmeVO)throws SystemException;
	 /**
	  * @author a-1883
	  * @param companyCode
	  * @param currentDate
	  * @param pageNumber
	  * @return Page<LoyaltyProgrammeVO>
	  * @throws SystemException
	  */
	 Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
			 String companyCode,LocalDate currentDate,int pageNumber)
			 throws SystemException;
	 /**
	  * 
	  * @param serviceVO
	  * @return
	  * @throws SystemException
	  */
	 String checkForService(CustomerServicesVO serviceVO)
	   throws SystemException;
	 /**
	  * 
	  * @param companyCode
	  * @param serviceCode
	  * @return
	  */
	 CustomerServicesVO listCustomerServices(String companyCode,
	     		String serviceCode)throws SystemException;
	 /**
	  * 
	  * @param companyCode
	  * @param serviceCode
	 * @param pageNumber 
	  * @return
	  * @throws SystemException
	  */
	 Page<CustomerServicesVO> customerServicesLOV(String companyCode,
	     		String serviceCode,int pageNumber)throws SystemException;
	 
	 
} 
