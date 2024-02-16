/*
 * EmbargoAudit.java Created on Sep 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import java.util.Calendar;


import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;


/**
 * @author A-1358
 * This class handles the persistence of Embargo Audit
 */


@Table(name="RECAUD")
@Entity
@Staleable
public class EmbargoRulesAudit {
    
	public static final String KEY_AUDIT="EMBARGO_AUDIT";
	
    private EmbargoRulesAuditPK embargoAuditPk;
    
    /**
     * Action Code
     */
    private String actionCode; 
    
    /**
     * Additional info on the actionn performed
     */
    private String additionalInfo; 

    /**
     * Audit Remarks
     */
    private String auditRemarks; 
    
    /**
     * Last update user code. For optimistic locking
     */
    private String lastUpdateUser;

    /**
	  * Local time of transaction. This is for display purposes
	  */  
	 private Calendar txnLocalTime;
	 /**
	   *Time of transaction in GMT
	  */
	 private Calendar txnTime;	
	 /**
	  * The station code
	  */
	 private String stationCode; 
          
    /**
     * @return Returns the embargoAuditPk.
     */
      
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="embargoReferenceNumber", column=@Column(name="REFNUM")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}
	)
	      
    public EmbargoRulesAuditPK getEmbargoAuditPk() {
        return embargoAuditPk;
    }
    
    /**
     * @param embargoAuditPk The embargoAuditPk to set.
     */
    public void setEmbargoAuditPk(EmbargoRulesAuditPK embargoAuditPk) {
        this.embargoAuditPk = embargoAuditPk;
    }
    
    /**
     * @return Returns the actionCode.
     */
    @Column(name="ACTCOD")
    public String getActionCode() {
        return actionCode;
    }
    
    /**
     * @param actionCode The actionCode to set.
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }
    
    /**
	 * @return Returns the stationCode.
	 */
    @Column(name="STNCOD")
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	
	/**
     * @return Returns the additionalInfo.
     */
    @Column(name="ADLINF")
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    /**
     * @param additionalInfo The additionalInfo to set.
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    /**
     * @return Returns the auditRemarks.
     */
    @Column(name="AUDRMK")
    public String getAuditRemarks() {
        return auditRemarks;
    }
    
    /**
     * @param auditRemarks The auditRemarks to set.
     */
    public void setAuditRemarks(String auditRemarks) {
        this.auditRemarks = auditRemarks;
    }
    
    

	/**
     * @return Returns the txnLocalTime.
    */
   
    @Column(name="UPDTXNTIM")
	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getTxnLocalTime() {
        return txnLocalTime;
    }
    /**
     * @param txnLocalTime The txnLocalTime to set.
     */
    public void setTxnLocalTime(Calendar txnLocalTime) {
        this.txnLocalTime =txnLocalTime;
    }
    /**
     * @return Returns the txnTime.
     */
    
    @Column(name="UPDTXNTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getTxnTime() {
        return txnTime;
    }
    /**
     * @param txnTime The txnTime to set.
     */
    public void setTxnTime(Calendar txnTime) {
        this.txnTime = txnTime;
    }
    
    /**
     * @return Returns the lastUpdateUser.
     */
    @Column(name="UPDUSR")
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
     * Constructor for creating Business Object
     * @param embargoAuditVO
     * @throws CreateException
     * @throws SystemException
     */
    public EmbargoRulesAudit(EmbargoAuditVO embargoAuditVO) 
    throws CreateException, SystemException{    	
    	/*EmbargoAuditPK embargoAuditPK= new EmbargoAuditPK(embargoAuditVO.getCompanyCode(),
    			embargoAuditVO.getEmbargoReferenceNumber());*/
    	EmbargoRulesAuditPK embargoAuditPK= new EmbargoRulesAuditPK();
    	embargoAuditPK.setCompanyCode( embargoAuditVO.getCompanyCode());
    	embargoAuditPK.setEmbargoReferenceNumber( embargoAuditVO.getEmbargoReferenceNumber());
    	embargoAuditPK.setSequenceNumber( embargoAuditVO.getSequenceNumber());
          this.embargoAuditPk=embargoAuditPK;
       	
    	setActionCode(embargoAuditVO.getActionCode());
    	setAuditRemarks(embargoAuditVO.getAuditRemarks());
    	setLastUpdateUser(embargoAuditVO.getUserId());
    	setTxnLocalTime(embargoAuditVO.getTxnLocalTime());
    	setTxnTime(embargoAuditVO.getTxnTime());
    	setStationCode(embargoAuditVO.getStationCode());
    	if(embargoAuditVO.getAdditionalInformation()!=null){
    		setAdditionalInfo(embargoAuditVO.getAdditionalInformation());
    	}
    	PersistenceController.getEntityManager().persist(this);
    	
    	
    }
/*
 * Default Constructor
 */
public EmbargoRulesAudit(){
	
}

}
