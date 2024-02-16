/*
 * DSNInContainerAtAirportVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-5991
 * 
 */

public class DSNInContainerAtAirportVO extends AbstractVO {

	private String containerNumber;

	private int statedBags;

	//private double statedWeight;
	private Measure statedWeight;//added by A-7371

	private int acceptedBags;

	//private double acceptedWeight;
	private Measure acceptedWeight;//added by A-7371

	private String remarks;

	private String warehouseCode;

	private String locationCode;
	
	private String mailClass;

	private Collection<DSNInConsignmentForContainerAtAirportVO> 
	               dsnInConsignments;
/**
 * 
 * @return statedWeight
 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
/**
 * 
 * @param statedWeight
 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
/**
 * 
 * @return acceptedWeight
 */
	public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
/**
 * 
 * @param acceptedWeight
 */
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	/**
	 * @return Returns the acceptedBags.
	 */
	public int getAcceptedBags() {
		return acceptedBags;
	}

	/**
	 * @param acceptedBags
	 *            The acceptedBags to set.
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	/**
	 * @return Returns the acceptedWeight.
	 */
	/*public double getAcceptedWeight() {
		return acceptedWeight;
	}

	*//**
	 * @param acceptedWeight
	 *            The acceptedWeight to set.
	 *//*
	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}*/

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the statedBags.
	 */
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags
	 *            The statedBags to set.
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return Returns the statedWeight.
	 */
	/*public double getStatedWeight() {
		return statedWeight;
	}

	*//**
	 * @param statedWeight
	 *            The statedWeight to set.
	 *//*
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}
*/
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the locationCode.
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode
	 *            The locationCode to set.
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return Returns the warehouseCode.
	 */
	public String getWarehouseCode() {
		return warehouseCode;
	}

	/**
	 * @param warehouseCode
	 *            The warehouseCode to set.
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	/**
	 * @return Returns the dsnInConsignments.
	 */
	public Collection<DSNInConsignmentForContainerAtAirportVO> getDsnInConsignments() {
		return dsnInConsignments;
	}

	/**
	 * @param dsnInConsignments
	 *            The dsnInConsignments to set.
	 */
	public void setDsnInConsignments(
			Collection<DSNInConsignmentForContainerAtAirportVO> dsnInConsignments) {
		this.dsnInConsignments = dsnInConsignments;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

}
