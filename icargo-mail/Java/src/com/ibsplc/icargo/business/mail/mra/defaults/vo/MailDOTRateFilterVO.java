/*
 * MailDOTRateFilterVO.java created on Aug 03, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailDOTRateFilterVO extends AbstractVO {
	
	private String companyCode;
	
	private String sectorOriginCode;
	
	private String sectorDestinationCode;
	
	private int gcm;
	
	private String rateCode;

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
	 * @return Returns the gcm.
	 */
	public int getGcm() {
		return gcm;
	}

	/**
	 * @param gcm The gcm to set.
	 */
	public void setGcm(int gcm) {
		this.gcm = gcm;
	}

	/**
	 * @return Returns the rateCode.
	 */
	public String getRateCode() {
		return rateCode;
	}

	/**
	 * @param rateCode The rateCode to set.
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * @return Returns the sectorDestinationCode.
	 */
	public String getSectorDestinationCode() {
		return sectorDestinationCode;
	}

	/**
	 * @param sectorDestinationCode The sectorDestinationCode to set.
	 */
	public void setSectorDestinationCode(String sectorDestinationCode) {
		this.sectorDestinationCode = sectorDestinationCode;
	}

	/**
	 * @return Returns the sectorOriginCode.
	 */
	public String getSectorOriginCode() {
		return sectorOriginCode;
	}

	/**
	 * @param sectorOriginCode The sectorOriginCode to set.
	 */
	public void setSectorOriginCode(String sectorOriginCode) {
		this.sectorOriginCode = sectorOriginCode;
	}

	
	
	
}