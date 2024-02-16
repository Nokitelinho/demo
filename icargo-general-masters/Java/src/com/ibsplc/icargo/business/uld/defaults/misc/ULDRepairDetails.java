/*
 * ULDRepairDetails.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import javax.persistence.AttributeOverrides;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Staleable
@Table(name="ULDRPRDTL")
@Entity
public class ULDRepairDetails {
    
	private Log log = LogFactory.getLogger("ULD");
	
    private ULDRepairDetailsPK uldRepairDetailsPK; 
    

    
	/**
	 * 
     * Default constructor
     *
     */
	public ULDRepairDetails() {

	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param repairSequenceNumber
	 * @param damageReferenceNumber
	 * @throws SystemException
	 */
	public ULDRepairDetails(String companyCode, String uldNumber, 
			long repairSequenceNumber, long damageReferenceNumber)
		throws SystemException {
		log.entering("ULDRepairDetails","ULDRepairDetails");
		populatePk(companyCode , uldNumber , repairSequenceNumber , damageReferenceNumber);
		EntityManager em =PersistenceController.getEntityManager();
		log.log(Log.INFO,"!!!Going to persists child");
		try{
			em.persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param repairSequenceNumber
	 * @param damageReferenceNumber
	 */	
	private void populatePk(String companyCode,
							String uldNumber, 
							long repairSequenceNumber, 
							long damageReferenceNumber) {
		log.entering("ULDRepairDetailsPK","populatePk");
		ULDRepairDetailsPK repaidetailsPK = new ULDRepairDetailsPK();
		repaidetailsPK.setCompanyCode(companyCode);
		repaidetailsPK.setUldNumber(uldNumber);
		repaidetailsPK.setRepairSequenceNumber(repairSequenceNumber);
		repaidetailsPK.setDamageReferenceNumber(damageReferenceNumber);
		this.setUldRepairDetailsPK(repaidetailsPK);
		log.exiting("ULDRepairDetailsPK","populateAttribute");
	}
    
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param repairSequenceNumber
	 * @param damageReferenceNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDRepairDetails find(
	        String companyCode, String uldNumber, 
	        long repairSequenceNumber, long damageReferenceNumber)
	throws SystemException{
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDRepairDetails","find");
		ULDRepairDetails rpdetails = null;
		ULDRepairDetailsPK rpdetailsPK = new ULDRepairDetailsPK();
		rpdetailsPK.setCompanyCode(companyCode); 
		rpdetailsPK.setUldNumber(uldNumber);
		rpdetailsPK.setRepairSequenceNumber(repairSequenceNumber);
		rpdetailsPK.setDamageReferenceNumber(damageReferenceNumber); 
		EntityManager em = PersistenceController.getEntityManager();
		try{
			rpdetails = em.find(ULDRepairDetails.class , rpdetailsPK);
		}catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode());
		}
	    return rpdetails;
	}
	

   /**
    * method to update the BO
    *
    * @param uldRepairDetailsVO
    * @throws SystemException
    */
    public void update(ULDRepairDetailsVO uldRepairDetailsVO)
   		throws SystemException {
    }
    
  
	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	log.entering("ULDRepairDetails","remove");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
    }
	/**
	 * @return Returns the uldRepairDetailsPK.
	 */

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),		
		@AttributeOverride(name="uldNumber", column=@Column(name="ULDNUM")),
		@AttributeOverride(name="repairSequenceNumber", column=@Column(name="RPRSEQNUM")),
		@AttributeOverride(name="damageReferenceNumber", column=@Column(name="DMGREFNUM"))})
		public ULDRepairDetailsPK getUldRepairDetailsPK() {
		return uldRepairDetailsPK;
	}
	
	/**
	 * @param uldRepairDetailsPK The uldRepairDetailsPK to set.
	 */
	public void setUldRepairDetailsPK(ULDRepairDetailsPK uldRepairDetailsPK) {
		this.uldRepairDetailsPK = uldRepairDetailsPK;
	}   
    
}
