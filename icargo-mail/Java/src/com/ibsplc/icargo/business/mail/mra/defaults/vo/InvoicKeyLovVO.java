/*
 * InvoicKeyLovVO.java Created on Aug 21, 2007
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
public class InvoicKeyLovVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoicKey;
	
	private String poaCode;

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the invoicKey.
	 */
	public String getInvoicKey() {
		return invoicKey;
	}

	/**
	 * @param invoicKey The invoicKey to set.
	 */
	public void setInvoicKey(String invoicKey) {
		this.invoicKey = invoicKey;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	
	
	
	
}