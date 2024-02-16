/*
 * DespatchLovVO.java Created on Apr 25, 2007
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
public class DespatchLovVO extends AbstractVO {
	
	private String companyCode;
	
	private String dsn;
	
	private String gpaCode;
	
	private String despatch;
	
	public DespatchLovVO(){
		
	}

	

	/**
	 * @return Returns the despatch.
	 */
	public String getDespatch() {
		return despatch;
	}



	/**
	 * @param despatch The despatch to set.
	 */
	public void setDespatch(String despatch) {
		this.despatch = despatch;
	}



	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}



	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}



	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}



	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}



	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
}