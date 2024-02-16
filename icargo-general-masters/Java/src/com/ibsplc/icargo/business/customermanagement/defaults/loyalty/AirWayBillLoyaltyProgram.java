/*
 * AirWayBillLoyaltyProgram.java
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

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.customermanagement.defaults.CustomerMgmntDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */

@Staleable
@Table(name="CUSAWBLTYPRG")
@Entity

public class AirWayBillLoyaltyProgram {

	
	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
	private static final String MODULE = "customermanagement.defaults";
	
	private AirWayBillLoyaltyProgramPK airwayBillLoyaltyProgramPK;
	
	private String awbNumber;
	private double pointsAccrued;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private Calendar createdDate;

	
	/**
	 * @return Returns the airwayBillLoyaltyProgramPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="customerCode", column=@Column(name="CUSCOD")),
		@AttributeOverride(name="masterAwbNumber", column=@Column(name="MSTAWBNUM")),
		@AttributeOverride(name="documentOwnerIdentifier", column=@Column(name="DOCOWRIDR")),
		@AttributeOverride(name="duplicateNumber", column=@Column(name="DUPNUM")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM")),
		@AttributeOverride(name="loyaltyProgrammeCode", column=@Column(name="LTYPRGCOD")),
		@AttributeOverride(name="loyaltyAttribute", column=@Column(name="LTYATR")),
		@AttributeOverride(name="loyaltyAttributeUnit", column=@Column(name="LTYUNT"))}
	)
	public AirWayBillLoyaltyProgramPK getAirwayBillLoyaltyProgramPK() {
		return airwayBillLoyaltyProgramPK;
	}
	/**
	 * @param airwayBillLoyaltyProgramPK The airwayBillLoyaltyProgramPK to set.
	 */
	public void setAirwayBillLoyaltyProgramPK(
			AirWayBillLoyaltyProgramPK airwayBillLoyaltyProgramPK) {
		this.airwayBillLoyaltyProgramPK = airwayBillLoyaltyProgramPK;
	}
	/**
	 * @return Returns the awbNumber.
	 */
	@Column(name = "AWBNUM")
	@Audit(name="awbNumber")
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber The awbNumber to set.
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @return Returns the lastUpdatedTime.
	 */
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
	 * @return Returns the createdDate.
	 */
	@Column(name = "CRTDAT")
	@Audit(name="createdDate")

	@Temporal(TemporalType.DATE)
	public Calendar getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate The createdDate to set.
	 */
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return Returns the pointsAccrued.
	 */
	@Column(name = "PTSARD")
	@Audit(name="pointsAccrued")
	public double getPointsAccrued() {
		return pointsAccrued;
	}
	/**
	 * @param pointsAccrued The pointsAccrued to set.
	 */
	public void setPointsAccrued(double pointsAccrued) {
		this.pointsAccrued = pointsAccrued;
	}
	/**
	 * Default Constructor
	 */
	public AirWayBillLoyaltyProgram(){
	}

/**
 * 
 * @param airWayBillLoyaltyProgramVO
 * @throws SystemException
 */
	public AirWayBillLoyaltyProgram(AirWayBillLoyaltyProgramVO 
			airWayBillLoyaltyProgramVO)throws SystemException {
		log.entering("AirWayBillLoyaltyProgram","AirWayBillLoyaltyProgram");
		populatePK(airWayBillLoyaltyProgramVO);
		populateAttributes(airWayBillLoyaltyProgramVO);
		try{
    		PersistenceController.getEntityManager().persist(this);
    	}catch(CreateException createException){
    		log.log(Log.SEVERE,"CreateException");
    		throw new SystemException(createException.getErrorCode());
    	}
		log.exiting("AirWayBillLoyaltyProgram","AirWayBillLoyaltyProgram");
	}
	/**
	 * @param airWayBillLoyaltyProgramVO
	 */
	private void populatePK(AirWayBillLoyaltyProgramVO 
			airWayBillLoyaltyProgramVO){
		log.entering("AirWayBillLoyaltyProgram","populatePK");
		AirWayBillLoyaltyProgramPK airWayBillLoyaltyProgramPk =
					new AirWayBillLoyaltyProgramPK();
		    airWayBillLoyaltyProgramPk.setCompanyCode(   airWayBillLoyaltyProgramVO.
		    											getCompanyCode());
			airWayBillLoyaltyProgramPk.setCustomerCode(   airWayBillLoyaltyProgramVO.
															getCustomerCode());
			airWayBillLoyaltyProgramPk.setDocumentOwnerIdentifier(   
				airWayBillLoyaltyProgramVO.getDocumentOwnerIdentifier());
			airWayBillLoyaltyProgramPk.setDuplicateNumber(   airWayBillLoyaltyProgramVO.
															getDuplicateNumber());
			airWayBillLoyaltyProgramPk.setLoyaltyProgrammeCode(   airWayBillLoyaltyProgramVO.
														getLoyaltyProgrammeCode());
			airWayBillLoyaltyProgramPk.setMasterAwbNumber(   airWayBillLoyaltyProgramVO.
															getMasterAwbNumber());
			airWayBillLoyaltyProgramPk.setSequenceNumber(   airWayBillLoyaltyProgramVO.
															getSequenceNumber());
			airWayBillLoyaltyProgramPk.setLoyaltyAttribute(   airWayBillLoyaltyProgramVO.
															getLoyaltyAttribute());
			airWayBillLoyaltyProgramPk.setLoyaltyAttributeUnit(airWayBillLoyaltyProgramVO.getLoyaltyAttributeUnit());
			this.setAirwayBillLoyaltyProgramPK(airWayBillLoyaltyProgramPk);
		log.exiting("AirWayBillLoyaltyProgram","populatePK");
	}
	/**
	 * @param airWayBillLoyaltyProgramVO
	 */
	private void populateAttributes(AirWayBillLoyaltyProgramVO 
			airWayBillLoyaltyProgramVO){
		LocalDate createdAWBDate = null;
		log.entering("AirWayBillLoyaltyProgram","populateAttributes");
		this.setAwbNumber(airWayBillLoyaltyProgramVO.getAwbNumber());
		this.setPointsAccrued(this.getPointsAccrued()+
				airWayBillLoyaltyProgramVO.getPointsAccrued());
		this.setLastUpdatedUser(airWayBillLoyaltyProgramVO.getLastUpdatedUser());
		LocalDate lstUpdDate = airWayBillLoyaltyProgramVO.getLastUpdatedTime();
		if(lstUpdDate != null){
			this.setLastUpdatedTime(lstUpdDate.toCalendar());
		}
		createdAWBDate = airWayBillLoyaltyProgramVO.getAwbDate();
		if(createdAWBDate != null){
			this.setCreatedDate(createdAWBDate.toCalendar());
		}
		log.exiting("AirWayBillLoyaltyProgram","populateAttributes");
	}
	/**
	 * @param airWayBillLoyaltyProgramVO
	 * @return AirWayBillLoyaltyProgram
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static AirWayBillLoyaltyProgram find(AirWayBillLoyaltyProgramVO 
			airWayBillLoyaltyProgramVO)throws FinderException,SystemException {
		AirWayBillLoyaltyProgramPK airWayBillLoyaltyProgramPk =
					new AirWayBillLoyaltyProgramPK();
		    airWayBillLoyaltyProgramPk.setCompanyCode(   airWayBillLoyaltyProgramVO.
		    											getCompanyCode());
			airWayBillLoyaltyProgramPk.setCustomerCode(   airWayBillLoyaltyProgramVO.
															getCustomerCode());
			airWayBillLoyaltyProgramPk.setDocumentOwnerIdentifier(   
				airWayBillLoyaltyProgramVO.getDocumentOwnerIdentifier());
			airWayBillLoyaltyProgramPk.setDuplicateNumber(   airWayBillLoyaltyProgramVO.
															getDuplicateNumber());
			airWayBillLoyaltyProgramPk.setLoyaltyProgrammeCode(   airWayBillLoyaltyProgramVO.
														getLoyaltyProgrammeCode());
			airWayBillLoyaltyProgramPk.setMasterAwbNumber(   airWayBillLoyaltyProgramVO.
															getMasterAwbNumber());
			airWayBillLoyaltyProgramPk.setSequenceNumber(   airWayBillLoyaltyProgramVO.
															getSequenceNumber());
			airWayBillLoyaltyProgramPk.setLoyaltyAttribute(   airWayBillLoyaltyProgramVO.
															getLoyaltyAttribute());
			airWayBillLoyaltyProgramPk.setLoyaltyAttributeUnit( airWayBillLoyaltyProgramVO.
			getLoyaltyAttributeUnit());
			EntityManager entityManager = PersistenceController.getEntityManager();
			return entityManager.find(AirWayBillLoyaltyProgram.class,airWayBillLoyaltyProgramPk);
	}
	/**
	 * @param airWayBillLoyaltyProgramVO
	 * @throws SystemException
	 */
	public void update(AirWayBillLoyaltyProgramVO 
			airWayBillLoyaltyProgramVO)throws SystemException {
		log.entering("AirWayBillLoyaltyProgram","update");
		populateAttributes(airWayBillLoyaltyProgramVO);
		log.exiting("AirWayBillLoyaltyProgram","update");
	}
	/**
	  * @author A-1883
	  * @param listPointsAccumulatedFilterVO
	  * @param pageNumber
	  * @return  Page<ListCustomerPointsVO>
	  * @throws SystemException
	  */
	public static Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(ListPointsAccumulatedFilterVO 
			 listPointsAccumulatedFilterVO,int pageNumber)
			 throws SystemException {
		return constructDAO().listLoyaltyPointsForAwb(listPointsAccumulatedFilterVO,
				pageNumber);
	}
	 /**
     * 
     * @return
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
    /**
     * 
     * @param airWayBillLoyaltyProgramFilterVO
     * @return Collection<String>
     * @throws SystemException
     */
    public static Collection<String> listPointAccumulated(
			AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
			throws SystemException{
    	EntityManager em = PersistenceController.getEntityManager();
 	   try {
 			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
 				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
 			return customerMgmntDefaultsDAO.listPointAccumulated(
 					airWayBillLoyaltyProgramFilterVO);
 					
 	       } catch (PersistenceException e) {
//printStackTraccee()();
 			throw new SystemException(e.getErrorCode());
 	       }
    }
    /**
     * 
     * @param airWayBillLoyaltyProgramFilterVO
     * @return Collection<AirWayBillLoyaltyProgramVO>
     * @throws SystemException
     */
    public static Collection<AirWayBillLoyaltyProgramVO> showPoints(
    		AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
	throws SystemException{
    	EntityManager em = PersistenceController.getEntityManager();
  	   try {
  			CustomerMgmntDefaultsDAO customerMgmntDefaultsDAO = 
  				   CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
  			return customerMgmntDefaultsDAO.showPoints(
  					airWayBillLoyaltyProgramFilterVO);
  					
  	       } catch (PersistenceException e) {
//printStackTraccee()();
  			throw new SystemException(e.getErrorCode());
  	       }
    }
	
}
