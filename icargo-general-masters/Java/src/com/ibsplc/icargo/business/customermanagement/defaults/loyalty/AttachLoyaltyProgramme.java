/*
 * AttachLoyaltyProgramme.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

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

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.customermanagement.defaults.CustomerMgmntDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 *
 */

@Table(name="CUSLTYPRG")
@Entity
public class AttachLoyaltyProgramme {

	private Log log=LogFactory.getLogger("CUSTOMER MANAGEMENT");
    private AttachLoyaltyProgrammePK attachLoyaltyProgrammePK;

    private Calendar fromDate;
    private Calendar toDate;

	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;

    
	 private static final String MODULE = "customermanagement.defaults";
	
	/**
	 * @return Returns the fromDate.
	 */
	@Column(name = "FRMDAT")
	@Audit(name = "fromDate")

	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	/**
	 * @return Returns the toDate.
	 */
	@Column(name = "TOODAT")
	@Audit(name = "toDate")

	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return toDate;
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
	public AttachLoyaltyProgramme() {
	}

	/**
	 * Constructor
	 *
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 */
	public AttachLoyaltyProgramme(AttachLoyaltyProgrammeVO loyaltyProgrammeVO)
		throws SystemException {
		log.entering("AttachLoyaltyProgramme","AttachLoyaltyProgramme Constructor");
		populatePk(loyaltyProgrammeVO);
		populateAttributes(loyaltyProgrammeVO);
		try{
    		PersistenceController.getEntityManager().persist(this);
    	}catch(CreateException createException){
    		log.log(Log.SEVERE,"CreateException");
    		throw new SystemException(createException.getErrorCode(),createException);
    	}
    	
   
		log.exiting("AttachLoyaltyProgramme","AttachLoyaltyProgramme Constructor");
	}

	/**
	 * private method to populate PK
	 *
	 * @param loyaltyProgrammeVO
	 */
	public void populatePk(AttachLoyaltyProgrammeVO loyaltyProgrammeVO) {
		log.entering("AttachLoyaltyProgramme","populatePk");
		AttachLoyaltyProgrammePK attachLoyaltyProgrammePk = new AttachLoyaltyProgrammePK();
		attachLoyaltyProgrammePk.setCompanyCode(   loyaltyProgrammeVO.
											getCompanyCode());
		attachLoyaltyProgrammePk.setCustomerCode(   loyaltyProgrammeVO.
											getCustomerCode());
		attachLoyaltyProgrammePk.setLoyaltyProgrammeCode(   loyaltyProgrammeVO.
											getLoyaltyProgrammeCode());
		this.setAttachLoyaltyProgrammePK(attachLoyaltyProgrammePk);
		log.exiting("AttachLoyaltyProgramme","populatePk");
	}

	/**
	 * private method to populate attributes
	 *
	 * @param loyaltyProgrammeVO
	 */
	public void populateAttributes(AttachLoyaltyProgrammeVO loyaltyProgrammeVO) {
		log.entering("AttachLoyaltyProgramme","populateAttributes");
		this.setLastUpdatedUser(loyaltyProgrammeVO.getLastUpdatedUser());
		LocalDate startDate = loyaltyProgrammeVO.getFromDate();
		if(startDate != null ){
			this.setFromDate(startDate.toCalendar());
		}
	
		LocalDate endDate = loyaltyProgrammeVO.getToDate();
		if(endDate != null ){
		this.setToDate(endDate.toCalendar());
		}
		log.exiting("AttachLoyaltyProgramme","populateAttributes");
	}

  /**
   * This method finds the AttachLoyaltyProgramme instance based on the TempCustomerPK
   *
   * @param loyaltyProgrammeVO
   * @return
   * @throws SystemException
   */
	public static AttachLoyaltyProgramme find( AttachLoyaltyProgrammeVO loyaltyProgrammeVO )
		throws SystemException {
		AttachLoyaltyProgramme attachLoyaltyProgramme = null;
		AttachLoyaltyProgrammePK attachLoyaltyProgrammePk = new AttachLoyaltyProgrammePK();
		attachLoyaltyProgrammePk.setCompanyCode(   loyaltyProgrammeVO.
											getCompanyCode());
		attachLoyaltyProgrammePk.setCustomerCode(   loyaltyProgrammeVO.
											getCustomerCode());
		attachLoyaltyProgrammePk.setLoyaltyProgrammeCode(   loyaltyProgrammeVO.
											getLoyaltyProgrammeCode());
		attachLoyaltyProgrammePk.setSequenceNumber(   loyaltyProgrammeVO.
											getSequenceNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			attachLoyaltyProgramme = entityManager.find(AttachLoyaltyProgramme.class,
					attachLoyaltyProgrammePk);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),finderException);
		}
	    return attachLoyaltyProgramme;
	}

	/**
	 * This method is used to remove the business object.
	 * It interally calls the remove method within EntityManager
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("AttachLoyaltyProgramme","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		log.exiting("AttachLoyaltyProgramme","remove");
	}
/**
 * @param loyaltyProgrammeVO
 * @throws SystemException
 */
	public void update( AttachLoyaltyProgrammeVO loyaltyProgrammeVO)
	throws SystemException{
		log.entering("AttachLoyaltyProgramme","update");
		this.setLastUpdatedTime(loyaltyProgrammeVO.getLastUpdatedTime());
		populateAttributes(loyaltyProgrammeVO);
		log.exiting("AttachLoyaltyProgramme","update");
	}
    
   /**
     * @return Returns the attachLoyaltyProgrammePK.
     */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="customerCode", column=@Column(name="CUSCOD")),
		@AttributeOverride(name="loyaltyProgrammeCode", column=@Column(name="LTYPRGCOD")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}
	)
    public AttachLoyaltyProgrammePK getAttachLoyaltyProgrammePK() {
        return attachLoyaltyProgrammePK;
    }
    /**
     * @param attachLoyaltyProgrammePK The attachLoyaltyProgrammePK to set.
     */
    public void setAttachLoyaltyProgrammePK(AttachLoyaltyProgrammePK attachLoyaltyProgrammePK) {
        this.attachLoyaltyProgrammePK = attachLoyaltyProgrammePK;
    }
    /**
     * 
     * @param companyCode
     * @param customerCode
     * @param groupCode
     * @return Collection<AttachLoyaltyProgrammeVO>
     * @throws SystemException
     */
    public static Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
			String companyCode,String customerCode,String groupCode)
	         throws SystemException{
    	 EntityManager em = PersistenceController.getEntityManager();
  	   try {
  			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
  				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
  			return customerMgmntDefaultsDAO.listLoyaltyPgmToCustomers(
  					companyCode,customerCode,groupCode);
  					
  	       } catch (PersistenceException e) {
//printStackTraccee()();
  			throw new SystemException(e.getErrorCode());
  	       }
    }
    /**
     * 
     * @param programmeVO
     * @return
     * @throws SystemException
     */
    public static String checkForCustomerLoyalty(CustomerGroupLoyaltyProgrammeVO 
    		programmeVO)throws SystemException{
    	EntityManager em = PersistenceController.getEntityManager();
   	   try {
   			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
   				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
   			return customerMgmntDefaultsDAO.checkForCustomerLoyalty(programmeVO);
   		  					
   	       } catch (PersistenceException e) {
//printStackTraccee()();
   			throw new SystemException(e.getErrorCode());
   	       }
    }



}
