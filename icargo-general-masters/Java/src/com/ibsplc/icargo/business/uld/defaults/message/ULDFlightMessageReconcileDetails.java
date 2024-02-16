/*
 * ULDFlightMessageReconcileDetails.java Created on Jul 7, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

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

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
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
 */
//Staleable
@Table(name = "ULDFLTMSGRECDTL")
@Entity
public class ULDFlightMessageReconcileDetails {
	
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	
	private ULDFlightMessageReconcileDetailsPK detailsPK ;
	private String pou;
    private String content;
    private String errorCode;
    private String damageStatus;
    private Calendar lastUpdateTime;
    private String lastUpdateUser;
    private String uldFlightStatus;
    //Added by a-3459 as part of ICRD-192413 starts
    private String uldSource;
    private String uldStatus;
    //Added by a-3459 as part of ICRD-192413 ends
	/**
     * 
     * 	Method		:	ULDFlightMessageReconcileDetails.getUldSource
     *	Added by 	:	A-7359 on 24-Aug-2017
     * 	Used for 	:   ICRD-192413
     *	Parameters	:	@return 
     *	Return type	: 	String
     */
    @Column(name="ULDSRC")
    public String getUldSource() {
		return uldSource;
	}
    /**
     * 
     * 	Method		:	ULDFlightMessageReconcileDetails.setUldSource
     *	Added by 	:	A-7359 on 24-Aug-2017
     * 	Used for 	:	ICRD-192413
     *	Parameters	:	@param uldSource 
     *	Return type	: 	void
     */
	public void setUldSource(String uldSource) {
		this.uldSource = uldSource;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileDetails.getUldStatus
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:	ICRD-192413	
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	@Column(name="ULDSTA")
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileDetails.setUldStatus
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:	ICRD-192413
	 *	Parameters	:	@param uldStatus 
	 *	Return type	: 	void
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * @return String Returns the damageStatus.
	 */
    @Column(name="DMGSTA")
	public String getDamageStatus() {
		return this.damageStatus;
	}
	/**
	 * @param damageStatus The damageStatus to set.
	 */
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}
	@Column(name="ULDFLTSTA")
	public String getUldFlightStatus() {
		return uldFlightStatus;
	}
	public void setUldFlightStatus(String uldFlightStatus) {
		this.uldFlightStatus = uldFlightStatus;
	}
	/**
	 * @return String Returns the content.
	 */
    @Column(name="CNT")
	public String getContent() {
		return this.content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return ULDFlightMessageReconcileDetailsPK Returns the detailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="flightCarrierIdentifier", column=@Column(name="FLTCARIDR")),
	    @AttributeOverride(name="flightNumber", column=@Column(name="FLTNUM")),
	    @AttributeOverride(name="flightSequenceNumber", column=@Column(name="FLTSEQNUM")),
	    @AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="messageType", column=@Column(name="MSGTYP")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM")),
	    @AttributeOverride(name="uldNumber", column=@Column(name="ULDNUM"))})  
	public ULDFlightMessageReconcileDetailsPK getDetailsPK() {
		return this.detailsPK;
	}
	/**
	 * @param detailsPK The detailsPK to set.
	 */
	public void setDetailsPK(ULDFlightMessageReconcileDetailsPK detailsPK) {
		this.detailsPK = detailsPK;
	}
	/**
	 * @return String Returns the errorCode.
	 */
	 @Column(name="ERRCOD")
	public String getErrorCode() {
		return this.errorCode;
	}
	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return String Returns the pou.
	 */
	 @Column(name="POU")
	public String getPou() {
		return this.pou;
	}
	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * 
	 *
	 */
	public ULDFlightMessageReconcileDetails() {
		
	}
	/**
	 * 
	 * @param detailsVO
	 * @throws SystemException
	 */
	public ULDFlightMessageReconcileDetails(ULDFlightMessageReconcileDetailsVO
			 detailsVO )throws SystemException{
		log.entering("ULDFlightMessageReconcileDetails","ULDFlightMessageReconcileDetails");
		populatePK(detailsVO);
		populateAttributes(detailsVO);
		
		try{
			  PersistenceController.getEntityManager().persist(this);
			}catch(CreateException createException){
				throw new SystemException(createException.getErrorCode());	
			}
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void populatePK(ULDFlightMessageReconcileDetailsVO detailsVO) {
		log.entering("ULDFlightMessageReconcileDetails","populatePk");
		ULDFlightMessageReconcileDetailsPK reconcileDetailsPK = new 
		ULDFlightMessageReconcileDetailsPK();
		reconcileDetailsPK.setFlightCarrierIdentifier(detailsVO.getFlightCarrierIdentifier());
		log.log(Log.INFO, "%%%%%%%%%  detailsVO.getFlightNumber() ", detailsVO.getFlightNumber());
		if(detailsVO.getFlightNumber().trim().length() == 1){
			reconcileDetailsPK.setFlightNumber("000"+detailsVO.getFlightNumber());
		}else if(detailsVO.getFlightNumber().trim().length() == 2){
			reconcileDetailsPK.setFlightNumber("00"+detailsVO.getFlightNumber());
		}else if(detailsVO.getFlightNumber().trim().length() == 3){
			reconcileDetailsPK.setFlightNumber("0"+detailsVO.getFlightNumber());
		}else{
			reconcileDetailsPK.setFlightNumber(detailsVO.getFlightNumber());
		}	
		log.log(Log.INFO, "%%%%%%%%%  detailsVO.getFlightNumber() ", detailsVO.getFlightNumber());
		//added by a-3045 for bug18957 ends
		reconcileDetailsPK.setFlightSequenceNumber(detailsVO.getFlightSequenceNumber());
		
		reconcileDetailsPK.setAirportCode(detailsVO.getAirportCode());
		reconcileDetailsPK.setCompanyCode(detailsVO.getCompanyCode());
		reconcileDetailsPK.setMessageType(   detailsVO.getMessageType());
		reconcileDetailsPK.setSequenceNumber(Integer.parseInt((detailsVO.getSequenceNumber())));  
		reconcileDetailsPK.setUldNumber(   detailsVO.getUldNumber());
		setDetailsPK(reconcileDetailsPK);
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void populateAttributes(ULDFlightMessageReconcileDetailsVO detailsVO) {
		log.entering("ULDFlightMessageReconcileDetails","populateAttributes");
		this.setContent(detailsVO.getContent());
		this.setErrorCode(detailsVO.getErrorCode());
		this.setPou(detailsVO.getPou());
		this.setDamageStatus(detailsVO.getDamageStatus());
		this.setLastUpdateUser(detailsVO.getLastUpdateUser());
		if(detailsVO.getUldFlightStatus()!=null)
		{
			this.setUldFlightStatus(detailsVO.getUldFlightStatus());
		}
		if(detailsVO.getUldSource()!=null)
		{
			this.setUldSource(detailsVO.getUldSource());
		}else{
			this.setUldSource(ULDFlightMessageReconcileDetailsVO.NIL);
		}
		if(detailsVO.getUldStatus()!=null)
		{
			this.setUldStatus(detailsVO.getUldStatus());
		}else{
			this.setUldStatus(ULDFlightMessageReconcileDetailsVO.FLAG_NO);
		}
		
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void update(ULDFlightMessageReconcileDetailsVO detailsVO) {
		log.entering("ULDFlightMessageReconcileDetails","update");
		this.setLastUpdateTime(detailsVO.getLastUpdateTime());
		populateAttributes(detailsVO);
	}
	/**
	 * 
	 * @param detailsVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageReconcileDetails find(
			ULDFlightMessageReconcileDetailsVO detailsVO)
	        throws SystemException {
		ULDFlightMessageReconcileDetailsPK reconcileDetailsPK = new 
		ULDFlightMessageReconcileDetailsPK();
		reconcileDetailsPK.setFlightCarrierIdentifier(detailsVO.getFlightCarrierIdentifier());
		reconcileDetailsPK.setFlightNumber(detailsVO.getFlightNumber());
		reconcileDetailsPK.setFlightSequenceNumber(   detailsVO.getFlightSequenceNumber());
		
		reconcileDetailsPK.setAirportCode(   detailsVO.getAirportCode());
		reconcileDetailsPK.setCompanyCode(   detailsVO.getCompanyCode());
		reconcileDetailsPK.setMessageType(   detailsVO.getMessageType());
		reconcileDetailsPK.setSequenceNumber(Integer.parseInt(detailsVO.getSequenceNumber()));
		reconcileDetailsPK.setUldNumber(   detailsVO.getUldNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDFlightMessageReconcileDetails.class,
					reconcileDetailsPK);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode());
			
		}
	}
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode());
		} catch (OptimisticConcurrencyException e) {
			throw new SystemException(e.getMessage());
		}
	}
	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return Returns the lastUpdateTime.
	 */
	 @Column(name="LSTUPDTIM")
	 @Version

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/*
	 * A-5125
	 * Method to find collection of Uld's in ULDFLTMSFRECDTLS
	 * 
	 */
	public static Collection<ULDFlightMessageReconcileDetailsVO> findUldFltMsgRecDtlsVOs(ULDFlightMessageFilterVO uldFltmsgFilterVO)
	        throws SystemException {
		Collection<ULDFlightMessageReconcileDetailsVO> uldFltMsgRecDtls = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
			uldFltMsgRecDtls = uldDefaultsDAO.findUldFltMsgRecDtlsVOs(uldFltmsgFilterVO);
			} 
		catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
		return uldFltMsgRecDtls;
	}
	
	/**
	 * @author A-5125 FOR for BUG-ICRD-50392
	 * @param cmpCod
	 * @param flightDate
	 * @param flightNumber
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDFlightMessageReconcileDetailsVO> findTransitStateULDs(
			String cmpCod, LocalDate flightDate, String flightNumber) throws SystemException {
		// TODO Auto-generated method stub
		Collection<ULDFlightMessageReconcileDetailsVO> uldFltMsgRecDtlsCol=null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
	uldFltMsgRecDtlsCol = uldDefaultsDAO.findTransitStateULDs(cmpCod,flightDate,flightNumber);
			} 
		catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
		return uldFltMsgRecDtlsCol;
	}
	
    
}
