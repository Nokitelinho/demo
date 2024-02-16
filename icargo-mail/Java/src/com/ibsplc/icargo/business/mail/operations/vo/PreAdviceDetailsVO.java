/*
 * PreAdviceDetailsVO.java Created on Jun 30, 2016
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
 * @author a-3109
 *
 */
public class PreAdviceDetailsVO extends AbstractVO {
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private int totalbags;
    //private double totalWeight;
    private Measure totalWeight;//added by A-7371
    private String uldNumbr;
    private String mailCategory;
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
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	/**
	 * @param destinationExchangeOffice The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}
	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}
	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	/**
	 * @param originExchangeOffice The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}
	/**
	 * @return Returns the totalbags.
	 */
	public int getTotalbags() {
		return totalbags;
	}
	/**
	 * @param totalbags The totalbags to set.
	 */
	public void setTotalbags(int totalbags) {
		this.totalbags = totalbags;
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
	/**
	 * @return Returns the uldNumbr.
	 */
	public String getUldNumbr() {
		return uldNumbr;
	}
	/**
	 * @param uldNumbr The uldNumbr to set.
	 */
	public void setUldNumbr(String uldNumbr) {
		this.uldNumbr = uldNumbr;
	}
}
