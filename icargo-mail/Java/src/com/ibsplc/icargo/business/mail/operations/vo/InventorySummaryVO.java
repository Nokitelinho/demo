/*
 * InventorySummaryVO.java Created on JUN 30, 2016
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
 *  This value Object is 
 */
public class InventorySummaryVO  extends AbstractVO{

	private int totalBags;
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371
	private Collection<MailInInventoryListVO> mailInInventoryListVos;
	/**
	 * @return Returns the mailInInventoryListVos.
	 */
	public Collection<MailInInventoryListVO> getMailInInventoryListVos() {
		return mailInInventoryListVos;
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
	 * @param mailInInventoryListVos The mailInInventoryListVos to set.
	 */
	public void setMailInInventoryListVos(
			Collection<MailInInventoryListVO> mailInInventoryListVos) {
		this.mailInInventoryListVos = mailInInventoryListVos;
	}
	/**
	 * @return Returns the totalBags.
	 */
	public int getTotalBags() {
		return totalBags;
	}
	/**
	 * @param totalBags The totalBags to set.
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
	 * @param totalWeight The totalWeight to set.
	 *//*
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/
	
}
