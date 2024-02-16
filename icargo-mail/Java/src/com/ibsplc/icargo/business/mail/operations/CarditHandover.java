/*
 * CarditHandover.java Created on JAN 04, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;


/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		 Author			   Description
 * -------------------------------------------------------------------------
 *  0.1			JAN 04, 2009	    	A-3227 RENO K ABRAHAM	First Draft
 */

@Entity
@Table(name = "MALCDTHNDOVR")
@Staleable
public class CarditHandover {

	private CarditHandoverPK carditHandoverPK;
	private String carditType;
	private String handoverOriginIdentifier;
	private String handoverOriginName;
	private String handoverOriginCodeListQualifier;
	private String handoverOriginCodeListAgency;
	private String handoverDestnIdentifier;
	private String handoverDestnName;
	private String handoverDestnCodeListQualifier;
	private String handoverDestnCodeListAgency;
	private Calendar collectionDate;
	private Calendar deliveryDate;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	
	public CarditHandover() {
		
	}
	/**
	 * @author A-3227
	 * @param mailbagPK
	 * @param mailbagHistoryVO
	 * @throws SystemException
	 */
    public CarditHandover(CarditHandoverPK carditHandovrPK, CarditHandoverInformationVO carditHandoverInfo)
            throws SystemException{
        populatePK(carditHandovrPK);
        populateAttributes(carditHandoverInfo);
        try {
            PersistenceController.getEntityManager().persist(this);

        } catch(CreateException createException) {
            throw new SystemException(createException.getMessage(),
                    createException);
        }        
    }

    /**
     * @author A-3227
     * @param carditHndovrPK
     * @throws SystemException
     */
    private void populatePK(CarditHandoverPK carditHndovrPK)
        throws SystemException {
    	this.carditHandoverPK = new CarditHandoverPK();
    	this.carditHandoverPK.setCompanyCode(carditHndovrPK.getCompanyCode());
    	this.carditHandoverPK.setCarditKey(carditHndovrPK.getCarditKey());
    	this.carditHandoverPK.setHandoverSerialNumber(carditHndovrPK.getHandoverSerialNumber());
    }
    
    /**
     * @author A-3227
     * @param carditHndovrInfo
     */
    private void populateAttributes(CarditHandoverInformationVO carditHndovrInfo) {
    	this.handoverDestnCodeListAgency = carditHndovrInfo.getHandoverDestnCodeListAgency();
    	this.handoverDestnCodeListQualifier = carditHndovrInfo.getHandoverDestnCodeListQualifier();
    	this.handoverDestnIdentifier = carditHndovrInfo.getHandoverDestnLocationIdentifier();
    	this.handoverDestnName = carditHndovrInfo.getHandoverDestnLocationName();
    	this.handoverOriginCodeListAgency = carditHndovrInfo.getHandoverOriginCodeListAgency();
    	this.handoverOriginCodeListQualifier = carditHndovrInfo.getHandoverOriginCodeListQualifier();
    	this.handoverOriginIdentifier = carditHndovrInfo.getHandoverOriginLocationIdentifier();
    	this.handoverOriginName = carditHndovrInfo.getHandoverOriginLocationName();
    	this.carditType = carditHndovrInfo.getCarditType();
        if(carditHndovrInfo.getOriginCutOffPeriod()!=null){
        	setCollectionDate(carditHndovrInfo.getOriginCutOffPeriod().toCalendar());
        }
        if(carditHndovrInfo.getDestinationCutOffPeriod() != null) {
        	setDeliveryDate(carditHndovrInfo.getDestinationCutOffPeriod().toCalendar());
        }
    }

	/**
	 * @author A-3227
	 * @param carditHndovrPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static CarditHandover find(CarditHandoverPK carditHndovrPK)
	throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(CarditHandover.class, carditHndovrPK);
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}
	/**
	 * @return the carditHandoverPK
	 */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
        @AttributeOverride(name="carditKey", column=@Column(name="CDTKEY")),
        @AttributeOverride(name="handoverSerialNumber", column=@Column(name="HNDSERNUM"))} )
	public CarditHandoverPK getCarditHandoverPK() {
		return carditHandoverPK;
	}
	/**
	 * @param carditHandoverPK the carditHandoverPK to set
	 */
	public void setCarditHandoverPK(CarditHandoverPK carditHandoverPK) {
		this.carditHandoverPK = carditHandoverPK;
	}
	/**
	 * @return the collectionDate
	 */
    @Column(name="CLNDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCollectionDate() {
		return collectionDate;
	}
	/**
	 * @param collectionDate the collectionDate to set
	 */
	public void setCollectionDate(Calendar collectionDate) {
		this.collectionDate = collectionDate;
	}
	/**
	 * @return the deliveryDate
	 */
    @Column(name="DLVDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the handoverDestnCodeListAgency
	 */
    @Column(name="HNDDSTAGT")
	public String getHandoverDestnCodeListAgency() {
		return handoverDestnCodeListAgency;
	}
	/**
	 * @param handoverDestnCodeListAgency the handoverDestnCodeListAgency to set
	 */
	public void setHandoverDestnCodeListAgency(String handoverDestnCodeListAgency) {
		this.handoverDestnCodeListAgency = handoverDestnCodeListAgency;
	}
	/**
	 * @return the handoverDestnCodeListQualifier
	 */
    @Column(name="HNDDSTQLF")
	public String getHandoverDestnCodeListQualifier() {
		return handoverDestnCodeListQualifier;
	}
	/**
	 * @param handoverDestnCodeListQualifier the handoverDestnCodeListQualifier to set
	 */
	public void setHandoverDestnCodeListQualifier(
			String handoverDestnCodeListQualifier) {
		this.handoverDestnCodeListQualifier = handoverDestnCodeListQualifier;
	}
	/**
	 * @return the handoverDestnIdentifier
	 */
    @Column(name="HNDDSTLOC")
	public String getHandoverDestnIdentifier() {
		return handoverDestnIdentifier;
	}
	/**
	 * @param handoverDestnIdentifier the handoverDestnIdentifier to set
	 */
	public void setHandoverDestnIdentifier(String handoverDestnIdentifier) {
		this.handoverDestnIdentifier = handoverDestnIdentifier;
	}
	/**
	 * @return the handoverDestnName
	 */
    @Column(name="HNDDSTNAM")
	public String getHandoverDestnName() {
		return handoverDestnName;
	}
	/**
	 * @param handoverDestnName the handoverDestnName to set
	 */
	public void setHandoverDestnName(String handoverDestnName) {
		this.handoverDestnName = handoverDestnName;
	}
	/**
	 * @return the handoverOriginCodeListAgency
	 */
    @Column(name="HNDORGAGT")
	public String getHandoverOriginCodeListAgency() {
		return handoverOriginCodeListAgency;
	}
	/**
	 * @param handoverOriginCodeListAgency the handoverOriginCodeListAgency to set
	 */
	public void setHandoverOriginCodeListAgency(String handoverOriginCodeListAgency) {
		this.handoverOriginCodeListAgency = handoverOriginCodeListAgency;
	}
	/**
	 * @return the handoverOriginCodeListQualifier
	 */
    @Column(name="HNDORGQLF")
	public String getHandoverOriginCodeListQualifier() {
		return handoverOriginCodeListQualifier;
	}
	/**
	 * @param handoverOriginCodeListQualifier the handoverOriginCodeListQualifier to set
	 */
	public void setHandoverOriginCodeListQualifier(
			String handoverOriginCodeListQualifier) {
		this.handoverOriginCodeListQualifier = handoverOriginCodeListQualifier;
	}
	/**
	 * @return the handoverOriginIdentifier
	 */
    @Column(name="HNDORGLOC")
	public String getHandoverOriginIdentifier() {
		return handoverOriginIdentifier;
	}
	/**
	 * @param handoverOriginIdentifier the handoverOriginIdentifier to set
	 */
	public void setHandoverOriginIdentifier(String handoverOriginIdentifier) {
		this.handoverOriginIdentifier = handoverOriginIdentifier;
	}
	/**
	 * @return the handoverOriginName
	 */
    @Column(name="HNDORGNAM")
	public String getHandoverOriginName() {
		return handoverOriginName;
	}
	/**
	 * @param handoverOriginName the handoverOriginName to set
	 */
	public void setHandoverOriginName(String handoverOriginName) {
		this.handoverOriginName = handoverOriginName;
	}
	/**
	 * @return the lastUpdateTime
	 */
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the lastUpdateUser
	 */
    @Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the carditType
	 */
	@Column(name="CDTTYP")
	public String getCarditType() {
		return carditType;
	}

	/**
	 * @param carditType the carditType to set
	 */
	public void setCarditType(String carditType) {
		this.carditType = carditType;
	}
	
}
