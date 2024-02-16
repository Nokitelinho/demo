/*
 * ProductEventVO.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;


/**
 * @author A-1358
 *
 */
public class EventLovVO implements  Serializable {

	private String companyCode;
	
	private String milestoneCode;
	
	private String milestoneDescription;
	
	private boolean isService;

	/**
	 * @return Returns the isService.
	 */
	public boolean isService() {
		return isService;
	}

	/**
	 * @param isService The isService to set.
	 */
	public void setService(boolean isService) {
		this.isService = isService;
	}

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
	 * @return Returns the milestoneCode.
	 */
	public String getMilestoneCode() {
		return milestoneCode;
	}

	/**
	 * @param milestoneCode The milestoneCode to set.
	 */
	public void setMilestoneCode(String milestoneCode) {
		this.milestoneCode = milestoneCode;
	}

	/**
	 * @return Returns the milestoneDescription.
	 */
	public String getMilestoneDescription() {
		return milestoneDescription;
	}

	/**
	 * @param milestoneDescription The milestoneDescription to set.
	 */
	public void setMilestoneDescription(String milestoneDescription) {
		this.milestoneDescription = milestoneDescription;
	}
	
}

