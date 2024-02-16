/*
 * BillingMatrixLovVO.java Created on Mar 6, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class BillingMatrixLovVO extends AbstractVO {
	
	private String companyCode;
	
	private String billingMatrixCode;
	
	private String billingMatrixDescription;
	
	public BillingMatrixLovVO(){
		
	}

	public String getBillingMatrixCode() {
		return billingMatrixCode;
	}

	public void setBillingMatrixCode(String billingMatrixCode) {
		this.billingMatrixCode = billingMatrixCode;
	}

	public String getBillingMatrixDescription() {
		return billingMatrixDescription;
	}

	public void setBillingMatrixDescription(String billingMatrixDescription) {
		this.billingMatrixDescription = billingMatrixDescription;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
}