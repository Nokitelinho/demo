/*
 * FlightRevIntLockVO.java Created on Feb 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo.lock;


import com.ibsplc.xibase.server.framework.persistence.lock.LockKeys;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

/**
 * @author a-8061
 *
 */

public class FlightRevIntLockVO extends LockVO {

	private String companyCode;
	private String jobOwnerIdr;
	
	
	@Override
	public String getEntityName() {
		return "MALFLTREVJOB";
		
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the jobOwnerIdr
	 */
	public String getJobOwnerIdr() {
		return jobOwnerIdr;
	}

	/**
	 * @param jobOwnerIdr the jobOwnerIdr to set
	 */
	public void setJobOwnerIdr(String jobOwnerIdr) {
		this.jobOwnerIdr = jobOwnerIdr;
	}

	@Override
	public LockKeys getKeys() {
		LockKeys keys = new LockKeys();
		keys.addKey("CMPCOD", getCompanyCode());
        keys.addKey("OWRIDR", getJobOwnerIdr());

        return keys;
	}

	@Override
	public String getEntityDisplayString() {
		StringBuffer displayString = new StringBuffer("");
		displayString.append(getCompanyCode())
		.append(" ").append(getJobOwnerIdr())
		;
		return displayString.toString();
	}

	

}
