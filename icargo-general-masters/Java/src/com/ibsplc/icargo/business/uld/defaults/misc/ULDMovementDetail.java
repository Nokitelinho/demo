/*
 * ULDMovementDetail.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347 
 * 
 */
@Table(name = "ULDMVTDTL")
@Entity
public class ULDMovementDetail {

	private ULDMovementDetailPK uldMovementDetailPK;

	private int flightCarrierIdentifier;

	private String flightNumber;

	private Calendar flightDate;

	private String pointOfLading;

	private String pointOfUnLading;

	private String content;

	private String isDummyMovement;

	private String remark;

	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;
	
	//Added by AG
	
	private Calendar movementDate;
	
	private Calendar movementDateUTC; 
	
	//Added by Sreekumar S
	private String agentCode;
	
	private String agentName;

	//Added by A-8368 as part of CR - IASCB-29023
	private Calendar flightATA;
    /**
     * 
     */
	  private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	/**
	 * Default constructor
	 * 
	 */
	public ULDMovementDetail() {

	}

	/**
	 * 
	 * @param uldMovementDetailVO
	 * @throws SystemException
	 */
	public ULDMovementDetail(ULDMovementDetailVO uldMovementDetailVO)
			throws SystemException {
		log.entering("INSIDE THE ULDMOVEMENTDETAIL",
				"INSIDE THE ULDMOVEMENTDETAIL");
		populatePk(uldMovementDetailVO);
		populateAttributes(uldMovementDetailVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * 
	 * @param uldMovementDetailVO
	 */
	private void populatePk(ULDMovementDetailVO uldMovementDetailVO) {
		ULDMovementDetailPK pk = new ULDMovementDetailPK();
		pk.setCompanyCode(uldMovementDetailVO.getCompanyCode());
		pk.setMovementSequenceNumber(uldMovementDetailVO
				.getMovementSequenceNumber());
		pk.setUldNumber(uldMovementDetailVO.getUldNumber());
		this.setUldMovementDetailPK(pk);

	}

	/**
	 * This method populate the attributes 
	 * @param uldMovementDetailVO
	 * @throws SystemException 
	 */
	private void populateAttributes(ULDMovementDetailVO uldMovementDetailVO)
			throws SystemException {
		this.setContent(uldMovementDetailVO.getContent());
		this.setDummyMovement(uldMovementDetailVO.getIsDummyMovement() ? "Y": "N");
		log.log(Log.FINE, "uldMovementDetailVO.getIsDummyMovement()",
				uldMovementDetailVO.getIsDummyMovement());
		log.log(Log.INFO, "uldMovementDetailVO", uldMovementDetailVO);
		this.setPointOfLading(uldMovementDetailVO.getPointOfLading());
		this.setPointOfUnLading(uldMovementDetailVO.getPointOfUnLading());
		this.setFlightDate(uldMovementDetailVO.getFlightDate());
		this.setFlightNumber(uldMovementDetailVO.getFlightNumber());
		this.setFlightCarrierIdentifier(uldMovementDetailVO
                .getFlightCarrierIdentifier());
		if(uldMovementDetailVO.getLastUpdatedTime()!=null){
			log.log(Log.INFO, "Last Update Date from Client ::",
					uldMovementDetailVO.getLastUpdatedTime());
			this.setMovementDate(uldMovementDetailVO.getLastUpdatedTime());
			log.log(Log.FINE, "GMT MOVEMENT DATE::", uldMovementDetailVO.getLastUpdatedTime().toGMTDate());
			this.setMovementDateUTC(uldMovementDetailVO.getLastUpdatedTime().toGMTDate());
		}
		this.setLastUpdatedUser(uldMovementDetailVO.getLastUpdatedUser());
		this.setRemark(uldMovementDetailVO.getRemark());
		this.setAgentCode(uldMovementDetailVO.getAgentCode());
		this.setAgentName(uldMovementDetailVO.getAgentName());
		//Added by A-8368 as part of CR - IASCB-29023
		this.setFlightATA(uldMovementDetailVO.getFlightATA());
	}

	/**
	 * '
	 * @param companyCode
	 * @param movementSequenceNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDMovementDetail find(String companyCode, 
			long movementSequenceNumber,long movementSerialNumber,String uldNumber)throws SystemException {
		ULDMovementDetail uldMovementDetail = null;
		ULDMovementDetailPK uldPk = new ULDMovementDetailPK();
		uldPk.setCompanyCode(companyCode);
		uldPk.setMovementSequenceNumber(movementSequenceNumber);
		uldPk.setMovementSerialNumber(movementSerialNumber);
		// Added by Preet on 14Th Feb 08 -- starts
		uldPk.setUldNumber(uldNumber);		
		// Added by Preet on 14Th Feb 08 -- ends
	    EntityManager em = PersistenceController.getEntityManager();
		try{
	    uldMovementDetail = em.find(ULDMovementDetail.class, uldPk);
		}catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode(),finderException);
		}

		return uldMovementDetail;
	}
   /**
    * This method is used to update the BO
    * @param uldMovementDetailVO
    * @throws SystemException
    */
	public void update(ULDMovementDetailVO uldMovementDetailVO) throws SystemException {
		populateAttributes(uldMovementDetailVO);
	}

	/**
	 * method to remove BO
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
    	log.log(Log.INFO,"!!!!GOING TO REMOVE THIS");
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}
		
	

	

	/**
	 * @return Returns the content.
	 */
	@Column(name = "CNT")
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	@Column(name = "FLTCARIDR")
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/**
	 * @param flightCarrierIdentifier
	 *            The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/**
	 * @return Returns the flightDate.
	 */
	@Column(name = "FLTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the isDummyMovement.
	 */
	@Column(name = "DUMMVTFLG")
	public String getDummyMovement() {
		return isDummyMovement;
	}

	/**
	 * @param isDummyMovement
	 *            The isDummyMovement to set.
	 */
	public void setDummyMovement(String isDummyMovement) {
		this.isDummyMovement = isDummyMovement;
	}

	/**
	 * @return Returns the pointOfLading.
	 */
	@Column(name = "POL")
	public String getPointOfLading() {
		return pointOfLading;
	}

	/**
	 * @param pointOfLading
	 *            The pointOfLading to set.
	 */
	public void setPointOfLading(String pointOfLading) {
		this.pointOfLading = pointOfLading;
	}

	/**
	 * @return Returns the pointOfUnLading.
	 */
	@Column(name = "POU")
	public String getPointOfUnLading() {
		return pointOfUnLading;
	}

	/**
	 * @param pointOfUnLading
	 *            The pointOfUnLading to set.
	 */
	public void setPointOfUnLading(String pointOfUnLading) {
		this.pointOfUnLading = pointOfUnLading;
	}

	/**
	 * @return Returns the remark.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "movementSequenceNumber", column = @Column(name = "MVTSEQNUM")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "movementSerialNumber", column = @Column(name = "MVTSERNUM")) })
	public ULDMovementDetailPK getUldMovementDetailPK() {
		return uldMovementDetailPK;
	}

	/**
	 * 
	 * @param uldMovementDetailPK
	 */
	public void setUldMovementDetailPK(ULDMovementDetailPK uldMovementDetailPK) {
		this.uldMovementDetailPK = uldMovementDetailPK;
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
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
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
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the remark.
	 */
	@Column(name = "RMK")
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
/**
 * 
 * @return
 */
	@Column(name = "LSTMVTDAT")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getMovementDate() {
		return movementDate;
	}
/**
 * 
 * @param movementDate
 */
	public void setMovementDate(Calendar movementDate) {
		this.movementDate = movementDate;
	}
	
	/**
	 * 
	 * @return
	 */
		@Column(name = "LSTMVTDATUTC")

		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getMovementDateUTC() {
			return movementDateUTC;
		}
		/**
		 * 
		 * @param movementDateUTC
		 */
		public void setMovementDateUTC(Calendar movementDateUTC) {
			this.movementDateUTC = movementDateUTC;
		}
		/**
		 * 
		 * @return
		 */
		@Column(name = "AGTCOD")
		public String getAgentCode() {
			return agentCode;
		}
		/**
		 * 
		 * @param agentCode
		 */
		public void setAgentCode(String agentCode) {
			this.agentCode = agentCode;
		}
		/**
		 * 
		 * @return
		 */
		@Column(name = "AGTNAM")
		public String getAgentName() {
			return agentName;
		}
		/**
		 * 
		 * @param agentName
		 */
		public void setAgentName(String agentName) {
			this.agentName = agentName;
		}	
		//Added by A-8368 as part of CR - IASCB-29023
		/**
		 * @return Returns the flightATA.
		 */
		@Column(name = "FLTATA")
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getFlightATA() {
			return flightATA;
		}
		/**
		 * @param flightATA
		 *            The flightATA to set.
		 */
		public void setFlightATA(Calendar flightATA) {
			this.flightATA = flightATA;
		}	

}
