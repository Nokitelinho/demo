/*
 * CustomerGroupLoyaltyProgramme.java Created on April 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.profile;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.persistence.dao.customermanagement.defaults.CustomerMgmntDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;






/**
 * @author A-2048
 *
 *
 */
@Table(name="CUSGRPLTYPRG")
@Entity
public class CustomerGroupLoyaltyProgramme {

   private CustomerGroupLoyaltyProgrammePK customerGroupLoyaltyProgrammePK;
   
  // private int pointsAccruded;
   private Calendar fromDate;
   private Calendar toDate;
   private Calendar lastUpdatedTime;
   private String lastUpdatedUser;
   
   
   
	 /**
	 * Log
	 */
   private Log log = LogFactory.getLogger("CUSTOMER.DEFAULTS");
   
   private static final String MODULE = "customermanagement.defaults";
   
   
   
   
   
	/**
	 * @return CustomerGroupLoyaltyProgrammePK Returns the customerGroupLoyaltyProgrammePK.
	*/
   
   
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="groupCode", column=@Column(name="GRPCOD")),
	    @AttributeOverride(name="loyaltyProgramCode", column=@Column(name="LTYPRGCOD")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))})
	public CustomerGroupLoyaltyProgrammePK getCustomerGroupLoyaltyProgrammePK() {
		return this.customerGroupLoyaltyProgrammePK;
	}
	/**
	 * @param customerGroupLoyaltyProgrammePK The customerGroupLoyaltyProgrammePK to set.
	 */
	public void setCustomerGroupLoyaltyProgrammePK(
			CustomerGroupLoyaltyProgrammePK customerGroupLoyaltyProgrammePK) {
		this.customerGroupLoyaltyProgrammePK = customerGroupLoyaltyProgrammePK;
	}
	/**
	 * @return Calendar Returns the fromDate.
	 */
	@Column(name="FRMDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return this.fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name="LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return int Returns the pointsAccruded.
	 */
//	@Column(name="PTSARD")
//	public int getPointsAccruded() {
//		return this.pointsAccruded;
//	}
	/**
	 * @param pointsAccruded The pointsAccruded to set.
	 */
//	public void setPointsAccruded(int pointsAccruded) {
//		this.pointsAccruded = pointsAccruded;
//	}
	/**
	 * @return Calendar Returns the toDate.
	 */
	@Column(name="TOODAT")

	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return this.toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
   
   
   
	 /**
	 * Constructor
	 */
	public CustomerGroupLoyaltyProgramme() {
	}
	
	
	/**
	 * 
	 * @param customerGroupLoyaltyProgrammeVO
	 * @throws SystemException
	 */
	public CustomerGroupLoyaltyProgramme(CustomerGroupLoyaltyProgrammeVO 
			customerGroupLoyaltyProgrammeVO)throws SystemException {
		log.entering("CustomerGroupLoyaltyProgramme", "CustomerGroupLoyaltyProgramme");
		try{
			populatePk(customerGroupLoyaltyProgrammeVO);
			populateAttributes(customerGroupLoyaltyProgrammeVO);
			
			log.log(Log.SEVERE,"just Before Persisting:");
			PersistenceController.getEntityManager().persist(this);
		} catch(CreateException createException) {
		log.log(Log.SEVERE,"CreateException caught, SystemException thrown");
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("CustomerGroupLoyaltyProgramme", "CustomerGroupLoyaltyProgramme");
	}
	/**
	 * 
	 * @param groupLoyaltyProgrammeVO 
	 */
	public void populatePk(CustomerGroupLoyaltyProgrammeVO 
			groupLoyaltyProgrammeVO) {
		log.entering("CustomerGroupLoyaltyProgramme", "populatePk");
		CustomerGroupLoyaltyProgrammePK groupLoyaltyProgrammePK = 
			new CustomerGroupLoyaltyProgrammePK();
		groupLoyaltyProgrammePK.setCompanyCode(  
			groupLoyaltyProgrammeVO.getCompanyCode());
		groupLoyaltyProgrammePK.setGroupCode(  
			groupLoyaltyProgrammeVO.getGroupCode());
		groupLoyaltyProgrammePK.setLoyaltyProgramCode( 
			groupLoyaltyProgrammeVO.getLoyaltyProgramCode());
		setCustomerGroupLoyaltyProgrammePK(groupLoyaltyProgrammePK);
		log.exiting("CustomerGroupLoyaltyProgramme", "populatePk");
	}
	/**
	 * 
	 * @param groupLoyaltyProgrammeVO
	 */
	public void populateAttributes(CustomerGroupLoyaltyProgrammeVO 
			groupLoyaltyProgrammeVO) {
		log.entering("CustomerGroupLoyaltyProgramme", "populateAttributes");
		
		//   setPointsAccruded(groupLoyaltyProgrammeVO.getPointsAccruded());
		   if(groupLoyaltyProgrammeVO.getFromDate() != null) {
			   setFromDate(groupLoyaltyProgrammeVO.getFromDate().toCalendar());
		   }
		   if(groupLoyaltyProgrammeVO.getToDate() != null) {
			   setToDate(groupLoyaltyProgrammeVO.getToDate().toCalendar());   
		   }
		   
		   setLastUpdatedUser(groupLoyaltyProgrammeVO.getLastUpdatedUser());
		
		
		log.exiting("CustomerGroupLoyaltyProgramme", "populateAttributes");
		
	}
    /**
     * 
     * @param companyCode
     * @param groupCode
     * @param loyaltyProgramCode
     * @param sequenceNumber
     * @return CustomerGroupLoyaltyProgramme
     * @throws SystemException
     */
	public static  CustomerGroupLoyaltyProgramme find(String companyCode,
			String groupCode,
			String loyaltyProgramCode,
			int sequenceNumber)
	throws SystemException {
		CustomerGroupLoyaltyProgrammePK groupLoyaltyProgrammePK = 
			new CustomerGroupLoyaltyProgrammePK();
		groupLoyaltyProgrammePK.setCompanyCode(  companyCode);
			
		groupLoyaltyProgrammePK.setGroupCode(  groupCode);
			
		groupLoyaltyProgrammePK.setLoyaltyProgramCode( loyaltyProgramCode);
		
		groupLoyaltyProgrammePK.setSequenceNumber(   sequenceNumber);
		
		EntityManager entityManager = 
	       	 PersistenceController.getEntityManager();
			try {
				return entityManager.find(CustomerGroupLoyaltyProgramme.class,
						groupLoyaltyProgrammePK);
			} catch (FinderException e) {
				
//printStackTraccee()();
				throw new SystemException(e.getErrorCode());
			}
			
	}
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("CustomerGroupLoyaltyProgramme", "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
		
//printStackTraccee()();
			throw new SystemException(e.getErrorCode());
		} catch (OptimisticConcurrencyException e) {
			
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		} 
		
		log.exiting("CustomerGroupLoyaltyProgramme", "remove");
	}
	/**
	 * 
	 * @param customerGroupLoyaltyProgrammeVO
	 */
	public void update(CustomerGroupLoyaltyProgrammeVO 
			customerGroupLoyaltyProgrammeVO) {
		this.setLastUpdatedTime(customerGroupLoyaltyProgrammeVO.getLastUpdatedTime());
		populateAttributes(customerGroupLoyaltyProgrammeVO);
	}
	/**
	 * 
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupLoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public static Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
			String companyCode,String groupCode)
			throws SystemException{
		EntityManager em = PersistenceController.getEntityManager();
		   try {
				CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
					   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
				return customerMgmntDefaultsDAO.listGroupLoyaltyPgm(
						companyCode,groupCode);
		       } catch (PersistenceException e) {
//printStackTraccee()();
				throw new SystemException(e.getErrorCode());
		       }	
	}
	
}
