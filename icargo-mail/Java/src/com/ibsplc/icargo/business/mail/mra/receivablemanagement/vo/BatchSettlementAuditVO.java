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
package com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

public class BatchSettlementAuditVO extends AuditVO{
	/**Module Name**/
	public static final String AUDIT_MODULENAME = "mail";
	/**Sub Module Name **/
	public static final String AUDIT_SUBMODULENAME = "mra";
	public static final String ENTITY = "mail.mra.batchSettlement";
	
/**
 * 
 * @param moduleName
 * @param subModuleName
 * @param entityName
 */
	public BatchSettlementAuditVO(String moduleName, String subModuleName, String entityName) {
		super(moduleName, subModuleName, entityName);
		
	} 
	
	private long serialNumber;
	private String batchID;
	

	
	 
	public long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	


	
	
}