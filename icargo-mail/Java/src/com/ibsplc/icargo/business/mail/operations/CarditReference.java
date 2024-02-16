/*
 * CarditReference.java Created on JAN 04, 2009
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

import com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO;
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
 *  Revision 	Date      	           		 Author			    Description
 * -------------------------------------------------------------------------
 *  0.1			JAN 04, 2009	    	A-3227 RENO K ABRAHAM	First Draft
 */

@Entity
@Table(name = "MALCDTREF")
@Staleable
public class CarditReference {

	private CarditReferencePK carditReferencePK;
	private String carditType;
	private String referenceQualifier;
	private String referrenceNumber;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;

	public CarditReference() {
		
	}
	/**
	 * @author A-3227
	 * @param mailbagPK
	 * @param mailbagHistoryVO
	 * @throws SystemException
	 */
    public CarditReference(CarditReferencePK carditRefPK, CarditReferenceInformationVO carditReferenceInfo)
            throws SystemException{
        populatePK(carditRefPK);
        populateAttributes(carditReferenceInfo);
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
    private void populatePK(CarditReferencePK carditReferPK)
        throws SystemException {
    	this.carditReferencePK = new CarditReferencePK();
    	this.carditReferencePK.setCompanyCode(carditReferPK.getCompanyCode());
    	this.carditReferencePK.setCarditKey(carditReferPK.getCarditKey());
    	this.carditReferencePK.setReferrenceSerialNumber(carditReferPK.getReferrenceSerialNumber());
    }
    
    /**
     * @author A-3227
     * @param carditHndovrInfo
     */
    private void populateAttributes(CarditReferenceInformationVO carditReferenceInfo) {
    	this.carditType = carditReferenceInfo.getCarditType();
    	this.referenceQualifier = carditReferenceInfo.getTransportContractReferenceQualifier();
    	this.referrenceNumber = carditReferenceInfo.getConsignmentContractReferenceNumber();
    }

	/**
	 * @author A-3227
	 * @param carditReferPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static CarditReference find(CarditReferencePK carditReferPK)
	throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(CarditReference.class, carditReferPK);
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
	 * @return the carditReferencePK
	 */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
        @AttributeOverride(name="carditKey", column=@Column(name="CDTKEY")),
        @AttributeOverride(name="referrenceSerialNumber", column=@Column(name="REFSERNUM"))} )
	public CarditReferencePK getCarditReferencePK() {
		return carditReferencePK;
	}
	/**
	 * @param carditReferencePK the carditReferencePK to set
	 */
	public void setCarditReferencePK(CarditReferencePK carditReferencePK) {
		this.carditReferencePK = carditReferencePK;
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
	 * @return the referenceQualifier
	 */
	@Column(name="REFQLF")
	public String getReferenceQualifier() {
		return referenceQualifier;
	}
	/**
	 * @param referenceQualifier the referenceQualifier to set
	 */
	public void setReferenceQualifier(String referenceQualifier) {
		this.referenceQualifier = referenceQualifier;
	}
	/**
	 * @return the referrenceNumber
	 */
	@Column(name="REFNUM")
	public String getReferrenceNumber() {
		return referrenceNumber;
	}
	/**
	 * @param referrenceNumber the referrenceNumber to set
	 */
	public void setReferrenceNumber(String referrenceNumber) {
		this.referrenceNumber = referrenceNumber;
	}
}
