/*
 * AttachLoyaltyProgrammeAuditVO.java Created on Feb 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-2048
 *
 */
public class AttachLoyaltyProgrammeAuditVO extends AuditVO {

	 private String loyaltyProgrammeCode;
	
	 /**
		 * @param arg0
		 * @param arg1
		 * @param arg2
		 */
		public AttachLoyaltyProgrammeAuditVO(String arg0, String arg1, String arg2) {
			super(arg0, arg1, arg2);
			// To be reviewed Auto-generated constructor stub
		}

	/**
	 * @return String Returns the loyaltyProgrammeCode.
	 */
	public String getLoyaltyProgrammeCode() {
		return this.loyaltyProgrammeCode;
	}


	/**
	 * @param loyaltyProgrammeCode The loyaltyProgrammeCode to set.
	 */
	public void setLoyaltyProgrammeCode(String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode = loyaltyProgrammeCode;
	}

}
