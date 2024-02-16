/*
 * UserAllowableOffices.java Created on Jun 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user;

import com.ibsplc.icargo.business.admin.user.vo.UserAllowableOfficesVO;
import com.ibsplc.neoicargo.persistence.admin.user.AdminUserDAO;
import com.ibsplc.neoicargo.persistence.admin.user.AdminUserPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import javax.persistence.*;
import java.util.Collection;

/**
 * The class describes the allowable offices associated with the user
 * 
 * @author A-1417
 */

@Table(name = "ADMUSRALLOFF")
@Entity
public class UserAllowableOffices {

	private static final String MODULE_NAME = "admin.user";

	private static final Log LOG = LogFactory.getLogger(MODULE_NAME);

	/**
	 * The primary key for UserAllowableOffices
	 */
	private UserAllowableOfficesPK userAllowableOfficesPK;

	/**
	 * Default constructor
	 */
	public UserAllowableOffices() {
	}

	/**
	 * Overloaded Constructor
	 * 
	 * @param userAllowableOfficesVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	public UserAllowableOffices(UserAllowableOfficesVO userAllowableOfficesVO)
			throws CreateException, SystemException {
		LOG.entering("UserAllowableOffices", "Constructor");

		// populatePK(userAllowableOfficesVO);
		UserAllowableOfficesPK allowableOfficesPK = new UserAllowableOfficesPK();
		allowableOfficesPK.setCompanyCode(   userAllowableOfficesVO
				.getCompanyCode());
		allowableOfficesPK.setUserCode(   userAllowableOfficesVO.getUserCode());
		allowableOfficesPK.setOfficeCode(   userAllowableOfficesVO.getOfficeCode());
		setUserAllowableOfficesPK(allowableOfficesPK);

		PersistenceController.getEntityManager().persist(this);

		LOG.exiting("UserAllowableOffices", "Constructor");

	}

	/**
	 * @return Returns the userAllowableOfficesPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "userCode", column = @Column(name = "USRCOD")),
			@AttributeOverride(name = "officeCode", column = @Column(name = "OFFCOD")) })
	public UserAllowableOfficesPK getUserAllowableOfficesPK() {
		return userAllowableOfficesPK;
	}

	/**
	 * @param userAllowableOfficesPK
	 *            The userAllowableOfficesPK to set.
	 */
	public void setUserAllowableOfficesPK(
			UserAllowableOfficesPK userAllowableOfficesPK) {
		this.userAllowableOfficesPK = userAllowableOfficesPK;
	}
//added by nisha starts
	
	/**
	 * @param companyCode
	 * @param userCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<UserAllowableOfficesVO> findAllOffices(String companyCode,String userCode)
	throws SystemException{
		Collection<UserAllowableOfficesVO> userAllowableOfficesVOs = null;
		try {
			AdminUserDAO adminUserDAO = AdminUserDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							AdminUserPersistenceConstants.MODULE_NAME));
			userAllowableOfficesVOs = adminUserDAO.findAllOffices(companyCode, userCode);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		LOG.exiting(" User BO class ", " findalloffices ");
		return userAllowableOfficesVOs;
	}
//added by nisha ends	
	/**
	 * This method removes the UserAllowableOffices
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		LOG.entering("UserAllowableOffice", " remove ");
		PersistenceController.getEntityManager().remove(this);
		LOG.exiting("UserAllowableOffice", " remove ");
	}
	
	
	public static Collection<UserAllowableOfficesVO> findUserAllowableOffices(String companyCode,String userCode)
	throws SystemException {
		LOG.entering("<:UserAllowableOffice:>", "<:findUserAllowableOffices:>");
		Collection<UserAllowableOfficesVO> officesVOs = null;
		try {
			AdminUserDAO adminUserDAO = AdminUserDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							AdminUserPersistenceConstants.MODULE_NAME));
			officesVOs = adminUserDAO.findUserAllowableOffices(companyCode, userCode);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		LOG.exiting("<:UserAllowableOffice:>", "<:findUserAllowableOffices:>");
		return officesVOs;
	}
	
}
