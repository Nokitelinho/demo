/*
 * CustomerLoyaltyAttribute.java
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.persistence.dao.customermanagement.defaults.CustomerMgmntDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-1883
 *
 */
@Staleable
@Table(name="CUSLTYATRMST")
@Entity
public class CustomerLoyaltyAttribute {
	
	private static final String MODULE = "customermanagement.defaults";
	private String companyCode;
	private String attribute;
	private String unit;
	
	
	/**
	 * @return Returns the attribute.
	 */
	@Column(name = "ATR")
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute The attribute to set.
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
	 * @return Returns the companyCode.
	 */
	@Column(name = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;

	}
	/**
	 * @return Returns the unit.
	 */
	@Column(name = "UNT")
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit The unit to set.
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
/**
 * 
 * @param companyCode
 * @return Collection<LoyaltyAttributeVO>
 * @throws SystemException
 */
	public Collection<LoyaltyAttributeVO> listLoyaltyAttributeDetails
	  (String companyCode)throws SystemException{
		return constructDAO().listLoyaltyAttributeDetails(companyCode);
	}
    /**
     * 
     * @return CustomerMgmntDefaultsDAO
     * @throws SystemException
     * @throws PersistenceException
     */
    private static CustomerMgmntDefaultsDAO constructDAO()
	throws SystemException {
		try{
			EntityManager em = PersistenceController.getEntityManager();
		return CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		}
		catch (PersistenceException persistenceException) {
//printStackTraccee()();
			throw new SystemException(persistenceException.getMessage());
		}
	}
}
