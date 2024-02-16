/*
	 * AttachLoyaltyProgrammeAudit.java Created on may 11th, 2006
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeAuditVO;
import com.ibsplc.xibase.server.framework.audit.AbstractAudit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1598
 **/

@Table(name="CUSLTYPRGAUD")
@Entity
public class AttachLoyaltyProgrammeAudit extends AbstractAudit{
	 private Log log=LogFactory.getLogger("CUSTOMER"); 
	
	 /**
     * SectorChargeAuditPK
     */
  private AttachLoyaltyProgrammeAuditPK attachLoyaltyProgrammeAuditPK;

	public AttachLoyaltyProgrammeAudit() {

		// To be reviewed Auto-generated constructor stub
	}

	
	/**
     *
     * @param auditVO
     * @throws SystemException
     */
    public AttachLoyaltyProgrammeAudit(AttachLoyaltyProgrammeAuditVO auditVO)
    throws SystemException{
    	log.log(Log.FINE,"---------Going for audit AttachLoyaltyProgrammeAudit-------");
    	AttachLoyaltyProgrammeAuditPK auditPK = new AttachLoyaltyProgrammeAuditPK();
    	auditPK.setCompanyCode(   auditVO.getCompanyCode());
    	
    	auditPK.setLoyaltyProgrammeCode(   auditVO.getLoyaltyProgrammeCode());
    	
    	this.setAttachLoyaltyProgrammeAuditPK(auditPK);
    	
    	populateGenericAttributes(auditVO);
    	
    	
       	try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
//printStackTraccee()();
			throw new SystemException(createException.getErrorCode());
		}

    }

	/**
	 * @return Returns the longTermCostAuditPK.
	 */
	
    @EmbeddedId
	@AttributeOverrides({
    	@AttributeOverride(name="companyCode",column=@Column(name="CMPCOD")),
    	@AttributeOverride(name="loyaltyProgrammeCode",column=@Column(name="LTYPRGCOD")),
    	@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM"))})
    public AttachLoyaltyProgrammeAuditPK getAttachLoyaltyProgrammeAuditPK() {
		return attachLoyaltyProgrammeAuditPK;
	}


	/**
	 * @param attachLoyaltyProgrammeAuditPK The longTermCostAuditPK to set.
	 */
	public void setAttachLoyaltyProgrammeAuditPK(AttachLoyaltyProgrammeAuditPK attachLoyaltyProgrammeAuditPK) {
		this.attachLoyaltyProgrammeAuditPK = attachLoyaltyProgrammeAuditPK;
	}	
 }
