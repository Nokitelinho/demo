/*
 * AdminUserDAO.java Created on Jun 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.persistence.admin.user;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.admin.user.vo.GuessablePasswordVO;
import com.ibsplc.icargo.business.admin.user.vo.PasswordHistoryVO;
import com.ibsplc.icargo.business.admin.user.vo.PortalUserDetailsVO;
import com.ibsplc.icargo.business.admin.user.vo.PortalUserPrivilegeFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.PortalUserPrivilegeVO;
import com.ibsplc.icargo.business.admin.user.vo.UserAllowableOfficesVO;
import com.ibsplc.icargo.business.admin.user.vo.UserAllowableStationsVO;
import com.ibsplc.icargo.business.admin.user.vo.UserEnquiryFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserLovFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserLovVO;
import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1417
 * 
 */

public interface AdminUserDAO {
	/**
	 * This method lists the user details
	 * 
	 * @param companyCode
	 * @param userCode
	 * @return UserVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	UserVO findUserDetails(String companyCode, String userCode)
			throws PersistenceException, SystemException;
	
	/**
	 * This method lists the user details for print
	 * 
	 * @param userEnquiryFilterVO
	 * @return Collection<UserVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<UserVO> printUserEnquiryDetails( 
				UserEnquiryFilterVO userEnquiryFilterVO )throws PersistenceException, SystemException;		
		
	 
	 
	//added by A-2396
//	added by Sharika
	/**
	 * @param companyCode
	 * @param userCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<UserAllowableStationsVO> findUserAllowableStationsDetails(String companyCode, String userCode)
	throws PersistenceException, SystemException;
	
	/**
	 * Method to find the allowable offices for user...
	 * If no offices have been allowed for the user..then the system will pick up
	 * the allowable offices coming under the user allowed station 
	 * for the user
	 * 
	 * @author A-2049
	 * @param companyCode -- the companyCode
	 * @param userCode    -- the userCode
	 * @return 	      -- the collection of allowable offices 
	 * @throws SystemException          
	 * @throws PersistenceException
	 */
	Collection<UserAllowableOfficesVO> findUserAllowableOffices(String companyCode,String userCode)
	throws SystemException,PersistenceException;
	
	/**
	 * @param companyCode
	 * @param userCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<UserAllowableOfficesVO> findAllOffices(String companyCode,String userCode)
	throws SystemException,PersistenceException;
	
	//ends
	 
// Added by bejoy {starts}
	/**
	 * This method get the logon details
	 * 
	 * @param companyCode
	 * @param userCode
	 * @return LogonAttributes
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	LogonAttributes getUserDetailsForLogon(String companyCode, String userCode)
			throws PersistenceException, SystemException;
	// Added by bejoy {ends}
	/**
	 * This method lists the user details based on rolegroupCode
	 * and airportCode
	 * 
	 * @param userLovFilterVO
	 * @return Page<UserLovVO> 
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Page<UserLovVO> findUserDetailsLov( UserLovFilterVO userLovFilterVO )
			throws PersistenceException, SystemException;
	/**
	 * This method lists the user details
	 * @author A-1833
	 * @param userLovFilterVO
	 * @return Collection<UserLovVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<UserLovVO> findUsers( UserLovFilterVO userLovFilterVO )
			throws PersistenceException,SystemException;
	/**
	 * This method returns all the valid userids in a collection
	 * of users, based on rolegroupCode and airportCode
	 * 
	 * @param userCodes
	 * @param companyCode
	 * @param rolegroupCode
	 * @return Collection<ValidUsersVO> valid user codes
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<ValidUsersVO> validateUsers (
			Collection<String> userCodes, String companyCode, String rolegroupCode ) 
			throws PersistenceException, SystemException;
	
	
	
	// Added by Sinoob starts
	/**
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param userCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findSpecificStaff(String companyCode,
			String airportCode, String warehouseCode, String userCode)
			throws SystemException, PersistenceException ;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param userCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findSpecificStaffOnline(String companyCode,
			String airportCode, String warehouseCode, String userCode)
			throws SystemException, PersistenceException ;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param roleGroupCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findUsersInRoleGroupOnline(String companyCode,
			String airportCode, String warehouseCode, String roleGroupCode)
			throws SystemException, PersistenceException ;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param roleGroupCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findUsersInRoleGroup(String companyCode,
			String airportCode, String warehouseCode, String roleGroupCode)
			throws SystemException, PersistenceException ;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param roleGroupCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findRoleGroup(String companyCode,
			String airportCode, String warehouseCode, String roleGroupCode)
			throws SystemException, PersistenceException ;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param roleGroupCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findRoleGroupOnline(String companyCode,
			String airportCode, String warehouseCode, String roleGroupCode)
			throws SystemException, PersistenceException ;

	// Added by Sinoob ends

	/**
	 * @author A-1863
	 * @param userLovFilterVO
	 * @return Collection<UserLovVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<UserLovVO> findUserDetailsByFilterLov(UserLovFilterVO 
			userLovFilterVO ) throws SystemException,PersistenceException ;
	
	/**
	 * @author A-1863
	 * @param userCodes
	 * @param companyCode
	 * @return Collection<ValidUsersVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ValidUsersVO> validateUsersWithoutRoleGroup( 
				Collection<String> userCodes, String companyCode)
				throws SystemException,PersistenceException ;
	
	/**
	 * @author A-1863
	 * @param companyCode
	 * @return Collection<UserRoleGroupDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<UserRoleGroupDetailsVO> validateRoleGroup( 
				Collection<String> roleGroupCodes, String companyCode)
				throws SystemException,PersistenceException ;	
	/**
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param encryptedPassword
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
  boolean	 checkUserValid(String companyCode,String userCode,String encryptedPassword)
                 throws SystemException,PersistenceException ;	
  /**
   * 
   * @param userEnquiryFilterVO
   * @return
   * @throws PersistenceException
   * @throws SystemException
   */
  
  Page<UserVO> findUserEnquiryDetails( 
			UserEnquiryFilterVO userEnquiryFilterVO )throws PersistenceException, SystemException;		
	
	
  /**
   * 
   * @param companyCode
   * @param inActivePeriod
   * @return
   * @throws PersistenceException
   * @throws SystemException
   */
  Collection<String> getAllUsersToDisable(String companyCode,int inActivePeriod)
	throws PersistenceException, SystemException;
 
	/**
	 * This method returns all the valid userids in a collection
	 * of users, based on rolegroupCode and airportCode
	 * 
	 * @param userCodes
	 * @param companyCode
	 * @param rolegroupCodeOne
	 * @param rolegroupCodeTwo
	 * @return Collection<ValidUsersVO> valid user codes
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<ValidUsersVO> validateCashiers (
			Collection<String> userCodes, String companyCode, String rolegroupCodeOne, String  rolegroupCodeTwo) 
			throws PersistenceException, SystemException;
  
  Page<UserAllowableStationsVO> findUserAllowableStationsVOPage(String companyCode, String userCode, int pageNum, int absoluteIndex)
	throws PersistenceException, SystemException;
	/**
	 * 
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	void udapteUserDetails(String companyCode)throws PersistenceException, SystemException;

	/**
	 * 
	 * @param userFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<UserVO> listUsers(UserFilterVO userFilterVO)throws PersistenceException, SystemException;
  
	// ICRD-57144 changes begins here
	/**
	 * This method retrieves user parameter values from data store.
	 * 
	 * @param logonAttributes
	 *            logonAttributes for an authenticated user
	 * @return Returns modified logonAttributes object with user parameter
	 *         details
	 * @throws PersistenceException
	 *             Throws wneh any exceptional condition occur while dealing
	 *             with database
	 * @throws SystemException
	 *             Throw whenever an exceptional condition occur other than DB
	 *             related.
	 * 
	 * @author A-6042
	 * @since ICRD-57144
	 */
	LogonAttributes getUserParametersValueMap(LogonAttributes logonAttributes)
			throws PersistenceException, SystemException;
	// ICRD-57144 changes ends here
	/**
	 * @author A-1885
	 */
	UserVO findUserDetailsForPortal(String companyCode, String userCode)
	throws PersistenceException, SystemException;
	/**
	 * Find user details for role groups.
	 * @author A-5497
	 * @param userCodes the user codes
	 * @return the collection
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	Collection<ValidUsersVO>  findUserDetailsForRoleGroups( 
			Collection<String> userCodes, String companyCode, String rolegroupCode, String adminRoleGroupCode,String stationCode)
	throws PersistenceException, SystemException;
	/**
	 * Find user details for role groups lov.
	 * @author A-5497
	 * @param userLovFilterVO the user lov filter vo
	 * @return the page
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	Page<UserLovVO> findUserDetailsForRoleGroupsLov(UserLovFilterVO userLovFilterVO)
	throws PersistenceException, SystemException;

	/**
	 * 
	 * @param companyCode
	 * @param userCode
	 * @return
	 * @throws SystemException
	 */
	PortalUserDetailsVO getPortalUserdetails(String companyCode, String userCode)throws  SystemException;
	/**
	 * Added as part of ICRD-204467
	 * @param userEnquiryFilterVO
	 * @return
	 */
	List<UserVO> findAllHandlingAreaUsers(UserEnquiryFilterVO userEnquiryFilterVO) throws  SystemException;
	/**
	 * 
	 * 	Method		:	AdminUserDAO.findUserDetailsForCustomer
	 *	Added by 	:	A-7364 on 20-Jun-2018
	 * 	Used for 	:
	 *	Parameters	:	@param customerCode
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	UserVO
	 */
	UserVO findUserDetailsForCustomer(String customerCode, String companyCode ) 
			throws PersistenceException, SystemException;
	/**
	 * @author a-5505
	 * @param userEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 */
	UserVO validateHandlingAreaUsers(UserEnquiryFilterVO userEnquiryFilterVO) throws  SystemException;
	
	/**
	 * 
	 * @param userEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 */
	List<UserRoleGroupDetailsVO> getRoleDetailsForUser(UserEnquiryFilterVO userEnquiryFilterVO) throws  SystemException;

	/**
	 * @author A-5810
	 * @param companyCode
	 * @param roleGroupsToSendMail
	 * @param stations
	 * @return
	 * @throws SystemException
	 */
	List<UserRoleGroupDetailsVO> findRoleGroupUserEmailDetails(String companyCode, String roleGroupsToSendMail,
			String stations)throws  SystemException;
}
