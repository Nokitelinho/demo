/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController.java
 *
 *	Created by	:	A-10647
 *	Created on	:	7-Jan-2022
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.BatchSettlementAuditVO;
import com.ibsplc.xibase.server.framework.audit.AbstractAudit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;



@Entity
@Staleable
@Table(name = "MALMRABTHSTLAUD")
public class BatchSettlementAudit extends AbstractAudit {

	private BatchSettlementAuditPK batchSettlementAuditPk;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "batchSettlementID", column = @Column(name = "BTHSTLIDR")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")), })
	public BatchSettlementAuditPK getBatchSettlementAuditPk() {
		return batchSettlementAuditPk;
	}

	public void setBatchSettlementAuditPk(BatchSettlementAuditPK batchSettlementAuditPk) {
		this.batchSettlementAuditPk = batchSettlementAuditPk;
	}

	public BatchSettlementAudit() {
	}

	public BatchSettlementAudit(BatchSettlementAuditVO batchSettlementAuditVO) throws SystemException {
		populatePK(batchSettlementAuditVO);
		populateAttributes(batchSettlementAuditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException);
		}

	}

	private void populatePK(BatchSettlementAuditVO batchSettlementAuditVO) {
		this.setBatchSettlementAuditPk(new BatchSettlementAuditPK(batchSettlementAuditVO.getCompanyCode(),
				batchSettlementAuditVO.getBatchID(), batchSettlementAuditVO.getSerialNumber()));
	}
	/** 
	 * 
	 * 	Method		:	BatchSettlementAudit.populateAttributes
	 *	Added by 	:	A-10647 on 17-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param batchSettlementAuditVO 
	 *	Return type	: 	void
	 */ 
	private void populateAttributes(BatchSettlementAuditVO batchSettlementAuditVO) {
		batchSettlementAuditVO.setActionCode(batchSettlementAuditVO.getActionCode());
	       setActionCode(batchSettlementAuditVO.getActionCode());
	       setAdditionalInfo(batchSettlementAuditVO.getAdditionalInformation());
	       setAuditRemarks(batchSettlementAuditVO.getAuditRemarks());
	       this.setStationCode(batchSettlementAuditVO.getStationCode());
	       this.setTriggerPoint(batchSettlementAuditVO.getAuditTriggerPoint());
	       this.setUpdateTxnTime(batchSettlementAuditVO.getTxnLocalTime());
	       setUpdateUser(batchSettlementAuditVO.getUserId());
	       setUpdateTxnTimeUTC(batchSettlementAuditVO.getTxnTime());
	    
	    

	   }

}
