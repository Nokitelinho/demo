/*
 * InventoryListVO.java Created on JUN 30, 2016
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
 * 
 * @author a-5991
 * 
 */
public class InventoryListVO extends AbstractVO {

	private String companyCode;

	private int totalBags;

	private String builtUpCarrier;

	private String currentAirport;

	//private double totalWeight;
	private Measure totalWeight;//added by A-7371

	/**
	 * Collection<ContainerInInventoryListVO>
	 */
	private Collection<ContainerInInventoryListVO> containerInInventoryList;
	
	private ContainerInInventoryListVO trashInInventoryListVO;

	/**
	 * @return Returns the builtUpCarrier.
	 */
	public String getBuiltUpCarrier() {
		return builtUpCarrier;
	}
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
	 * @param builtUpCarrier
	 *            The builtUpCarrier to set.
	 */
	public void setBuiltUpCarrier(String builtUpCarrier) {
		this.builtUpCarrier = builtUpCarrier;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the containerInInventoryList.
	 */
	public Collection<ContainerInInventoryListVO> getContainerInInventoryList() {
		return containerInInventoryList;
	}

	/**
	 * @param containerInInventoryList
	 *            The containerInInventoryList to set.
	 */
	public void setContainerInInventoryList(
			Collection<ContainerInInventoryListVO> containerInInventoryList) {
		this.containerInInventoryList = containerInInventoryList;
	}

	/**
	 * @return Returns the currentAirport.
	 */
	public String getCurrentAirport() {
		return currentAirport;
	}

	/**
	 * @param currentAirport
	 *            The currentAirport to set.
	 */
	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
	}

	/**
	 * @return Returns the totalBags.
	 */
	public int getTotalBags() {
		return totalBags;
	}

	/**
	 * @param totalBags
	 *            The totalBags to set.
	 */
	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	/*public double getTotalWeight() {
		return totalWeight;
	}

	*//**
	 * @param totalWeight
	 *            The totalWeight to set.
	 *//*
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/

	public ContainerInInventoryListVO getTrashInInventoryListVO() {
		return trashInInventoryListVO;
	}

	public void setTrashInInventoryListVO(
			ContainerInInventoryListVO trashInInventoryListVO) {
		this.trashInInventoryListVO = trashInInventoryListVO;
	}

}
