/*
 * ULDSCMReconcile.java Created on Aug 01, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import java.util.Calendar;


import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;




import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDSCMMSGREC")
@Entity
public class ULDSCMReconcile {
    
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	private static final String MODULE = "uld.defaults";
	
	private ULDSCMReconcilePK reconcilePK ;
	
    private Calendar stockCheckDate;
    
    private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	
	private Set<ULDSCMReconcileDetails> reconcileDetails;
	
	
	
	private String messageSendFlag;
	
//	Added by nisha on 16Jun08 for QF1019
	private String remarks;
	
	
	/**
	 * @return String Returns the messageSendFlag.
	 */
	 @Column(name="MSGSNDFLG")
	public String getMessageSendFlag() {
		return this.messageSendFlag;
	}
	/**
	 * @param messageSendFlag The messageSendFlag to set.
	 */
	public void setMessageSendFlag(String messageSendFlag) {
		this.messageSendFlag = messageSendFlag;
	}
	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	 @Column(name="LSTUPDTIM")
	 @Version

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
	 * @return Calendar Returns the stockCheckDate.
	 */
	 @Column(name="STKCHKDAT")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getStockCheckDate() {
		return this.stockCheckDate;
	}
	/**
	 * @param stockCheckDate The stockCheckDate to set.
	 */
	public void setStockCheckDate(Calendar stockCheckDate) {
		this.stockCheckDate = stockCheckDate;
	}
	/**
	 * @return Returns the remarks.
	 */
	 @Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return ULDFlightMessageReconcilePK Returns the reconcilePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
	    
	    @AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))})  
	public ULDSCMReconcilePK getReconcilePK() {
		return this.reconcilePK;
	}
	/**
	 * @param reconcilePK The reconcilePK to set.
	 */
	public void setReconcilePK(ULDSCMReconcilePK reconcilePK) {
		this.reconcilePK = reconcilePK;
	}
	
	/**
	 * @return Set<ULDFlightMessageReconcileDetails> Returns the reconcileDetails.
	 */
	@OneToMany
    @JoinColumns( {
	  
	  @JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable=false, updatable=false),
	  @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	  @JoinColumn(name = "ARLIDR", referencedColumnName = "ARLIDR", insertable=false, updatable=false),
	  @JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable=false, updatable=false)})
	public Set<ULDSCMReconcileDetails> getReconcileDetails() {
		return this.reconcileDetails;
	}
	/**
	 * @param reconcileDetails The reconcileDetails to set.
	 */
	public void setReconcileDetails(
			Set<ULDSCMReconcileDetails> reconcileDetails) {
		this.reconcileDetails = reconcileDetails;
	}
	
	/**
	 * 
	 *
	 */
	public ULDSCMReconcile() {
		
	}
	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public ULDSCMReconcile(ULDSCMReconcileVO reconcileVO)
	throws SystemException{
		log.entering("ULDSCMReconcile","ULDSCMReconcile");
		populatePK(reconcileVO);
		populateAttributes(reconcileVO);
		 try{
			  PersistenceController.getEntityManager().persist(this);
			}catch(CreateException createException){
				throw new SystemException(createException.getErrorCode());	
			}
			
			reconcileVO.setSequenceNumber(this.getReconcilePK().getSequenceNumber());
			log.log(Log.FINE, " Sequence Number inside Entity", this.getReconcilePK().getSequenceNumber());
			populateChildren(reconcileVO);
		
	}
	/**
	 * 
	 * @param reconcileVO
	 */
	public void populatePK(ULDSCMReconcileVO reconcileVO) {
		log.entering("ULDSCMReconcile","populatePK");
		ULDSCMReconcilePK messageReconcilePK = new ULDSCMReconcilePK();
		
		messageReconcilePK.setAirportCode(reconcileVO.getAirportCode());
		messageReconcilePK.setCompanyCode(reconcileVO.getCompanyCode());
		messageReconcilePK.setAirlineIdentifier(reconcileVO.getAirlineIdentifier());
		
		
		setReconcilePK(messageReconcilePK);
    	
	}
	/**
	 * 
	 * @param reconcileVO
	 */
	public void populateAttributes(ULDSCMReconcileVO reconcileVO) {

		log.entering("ULDSCMReconcile","populateAttributes");
		if(reconcileVO.getStockCheckDate() != null) {
			this.setStockCheckDate(reconcileVO.getStockCheckDate());
		}
		this.setLastUpdatedUser(reconcileVO.getLastUpdatedUser());
		this.setMessageSendFlag(reconcileVO.getMessageSendFlag());
		this.setRemarks(reconcileVO.getRemarks());
		
	}
	/**
	 * @param reconcileVO 
	 * @throws SystemException 
	 * 
	 *
	 */
	public void populateChildren(ULDSCMReconcileVO reconcileVO) 
	throws SystemException{
		log.entering("ULDSCMReconcile","PopulateChildren");
		if(reconcileVO.getReconcileDetailsVOs() != null &&
				reconcileVO.getReconcileDetailsVOs().size() >0) {
			for(ULDSCMReconcileDetailsVO detailsVO :reconcileVO.getReconcileDetailsVOs()) {
				detailsVO.setSequenceNumber(reconcileVO.getSequenceNumber());
				new ULDSCMReconcileDetails(detailsVO);
			}
		}
	}
	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException 
	 */
	public void update(ULDSCMReconcileVO reconcileVO)
	throws SystemException{
		log.entering("ULDSCMReconcile","update");
		populateAttributes(reconcileVO);
		updateChildren(reconcileVO);
	}
	
	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public void updateChildren(ULDSCMReconcileVO reconcileVO)throws SystemException {
		log.entering("ULDSCMReconcile","updateChildren");
		if(reconcileVO.getReconcileDetailsVOs() != null &&
				reconcileVO.getReconcileDetailsVOs().size()>0) {
			for(ULDSCMReconcileDetailsVO detailsVO :reconcileVO.getReconcileDetailsVOs()) {
				if(ULDSCMReconcileVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(detailsVO.getOperationFlag())) {
					detailsVO.setSequenceNumber(reconcileVO.getSequenceNumber());
					ULDSCMReconcileDetails messageReconcileDetails =null;
					messageReconcileDetails = ULDSCMReconcileDetails.find(detailsVO);
					messageReconcileDetails.remove();
				}
			}
			for(ULDSCMReconcileDetailsVO detailsVO :reconcileVO.getReconcileDetailsVOs()) {
				if(ULDSCMReconcileVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(detailsVO.getOperationFlag())) {
					new ULDSCMReconcileDetails(detailsVO);
				}else if(ULDSCMReconcileDetailsVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(detailsVO.getOperationFlag())){
					detailsVO.setSequenceNumber(reconcileVO.getSequenceNumber());
					ULDSCMReconcileDetails messageReconcileDetails =null;
					messageReconcileDetails = ULDSCMReconcileDetails.find(detailsVO);
					messageReconcileDetails.update(detailsVO);
				}
				
			}
		}
	}
	
	/**
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException 
	 */
	public static ULDSCMReconcile find(ULDSCMReconcileVO reconcileVO)
	throws SystemException ,FinderException{
		ULDSCMReconcilePK messageReconcilePK = new ULDSCMReconcilePK();
		
		messageReconcilePK.setAirportCode(reconcileVO.getAirportCode());
		messageReconcilePK.setCompanyCode(reconcileVO.getCompanyCode());
		messageReconcilePK.setAirlineIdentifier(reconcileVO.getAirlineIdentifier());
		messageReconcilePK.setSequenceNumber(reconcileVO.getSequenceNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		return entityManager.find(ULDSCMReconcile.class,
					messageReconcilePK);
	}
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		if(this.getReconcileDetails() != null) {
			for(ULDSCMReconcileDetails details :this.getReconcileDetails()) {
				details.remove();
			}
		}
		try{
    		PersistenceController.getEntityManager().remove(this);
    	}catch(RemoveException removeException){
    		throw  new SystemException(removeException.getErrorCode());
    	}
	}
	
	 /**
	 * This method returns the ULDDefaultsDAO.
	 * 
	 * @throws SystemException
	 */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDSCMReconcileVO> listSCMMessage(SCMMessageFilterVO filterVO)
    throws SystemException{
		try{
			 return constructDAO().listSCMMessage(filterVO);
		 }catch(PersistenceException persistenceException){
			 throw new SystemException(persistenceException.getErrorCode());
		 }
	}
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDSCMReconcileDetailsVO> listULDErrorsInSCM(SCMMessageFilterVO filterVO)
    throws SystemException{
		try{
			 return constructDAO().listULDErrorsInSCM(filterVO);
		 }catch(PersistenceException persistenceException){
			 throw new SystemException(persistenceException.getErrorCode());
		 }
	}
	/**
	 * 
	 * @param filterVO
	 * @throws SystemException
	 */
	public static ULDSCMReconcileVO sendSCMMessage(SCMMessageFilterVO filterVO)
    throws SystemException{
		try{
			 return constructDAO().sendSCMMessage(filterVO);
		 }catch(PersistenceException persistenceException){
			 throw new SystemException(persistenceException.getErrorCode());
		 }
	}
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDSCMReconcileVO> listSCMMessageLOV(SCMMessageFilterVO filterVO)
    throws SystemException{
		try{
			 return constructDAO().listSCMMessageLOV(filterVO);
		 }catch(PersistenceException persistenceException){
			 throw new SystemException(persistenceException.getErrorCode());
		 }
	}
/**
 * 
 * @param companyCode
 * @param uldNumber
 * @param scmSequenceNumber
 * @param airportCode
 * @return
 * @throws SystemException
 */
   public static int findAirlineIdentifierForSCM(String companyCode,String uldNumber,String scmSequenceNumber,
		   String airportCode)
   throws SystemException{
	   try{
			 return constructDAO().findAirlineIdentifierForSCM(companyCode,uldNumber,scmSequenceNumber,airportCode);
		 }catch(PersistenceException persistenceException){
			 throw new SystemException(persistenceException.getErrorCode());
		 }
   }
}
