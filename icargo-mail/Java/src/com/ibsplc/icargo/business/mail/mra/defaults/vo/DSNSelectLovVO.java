/*
 * DSNSelectLovVO.java Created on Jul 3, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2554
 *
 */
public class DSNSelectLovVO extends AbstractVO {
	
	private String companyCode;
	
	private String dsn;
	
	private String gpaCode;
	
	private String despatch;
	
	private String blgBasis;
	
	private String dsnDate;
	
	public DSNSelectLovVO(){
		
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

/**
 * 
 * @return
 */

	public String getCompanyCode() {
		return companyCode;
	}
/**
 * 
 * @param companyCode
 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}



	/**
	 * @return Returns the dsnDate.
	 */
	public String getDsnDate() {
		return dsnDate;
	}



	/**
	 * @param dsnDate The dsnDate to set.
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}



	/**
	 * @return Returns the blgBasis.
	 */
	public String getBlgBasis() {
		return blgBasis;
	}



	/**
	 * @param blgBasis The blgBasis to set.
	 */
	public void setBlgBasis(String blgBasis) {
		this.blgBasis = blgBasis;
	}
	
}