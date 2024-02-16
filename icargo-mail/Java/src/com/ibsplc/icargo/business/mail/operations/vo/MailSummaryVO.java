/*
 * MailSummaryVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *  
 * @author A-3109
 * 
 */

public class MailSummaryVO extends AbstractVO {
	
	private String destination;
	
	private String mailCategory;
	
	private String originPA;
	
	private String DestinationPA;
	
	private int bagCount;
	
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371

	private String origin;//Added as part of ICRD-113230
	/**
	 * 
	 * @return totalWeight
	 */
	public Measure getTotalWeight() {
		return totalWeight;
	}
/**
 * 
 * @param totalWeight
 */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the bagCount.
	 */
	public int getBagCount() {
		return bagCount;
	}

	/**
	 * @param bagCount The bagCount to set.
	 */
	public void setBagCount(int bagCount) {
		this.bagCount = bagCount;
	}

	/**
	 * @return Returns the categoryCode.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param categoryCode The categoryCode to set.
	 */
	public void setMailCategory(String categoryCode) {
		this.mailCategory = categoryCode;
	}

	/**
	 * @return Returns the destinationCode.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destinationCode The destinationCode to set.
	 */
	public void setDestination(String destinationCode) {
		this.destination = destinationCode;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	/*public double getTotalWeight() {
		return totalWeight;
	}

	*//**
	 * @param totalWeight The totalWeight to set.
	 *//*
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/

	public String getDestinationPA() {
		return DestinationPA;
	}

	public void setDestinationPA(String destinationPA) {
		DestinationPA = destinationPA;
	}

	public String getOriginPA() {
		return originPA;
	}

	public void setOriginPA(String originPA) {
		this.originPA = originPA;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

}
