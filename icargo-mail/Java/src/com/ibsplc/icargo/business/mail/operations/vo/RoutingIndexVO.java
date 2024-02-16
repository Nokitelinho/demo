/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO.java
 *
 *	Created by	:	A-7531
 *	Created on	:	31-Aug-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Calendar;
import java.util.Collection;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
public class RoutingIndexVO extends  AbstractVO {
	
	private String clsOutTim;
	private String companyCode;
	private int dayCount;
	private String contractType;
	private String destination;
	private int equitableTender;
	private String finalDlvTime;
	private String hazardousIndicator;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String mailClass;
	private int mailCost;
	private double maxWgt;
	private double minWgt;
	private int onetimePercent;
	private String origin;
	private String perishableIndicator;
	private String plannedDisDate;
	private String plannedEffectiveDate;
	private String planRouteGenInd;
	private String planRouteScnInd;
	private String priorityCode;
	private String routingIndex;
	private int routingSeqNum;
	private int tagIndex;
	private String wgtUnit;
	private int frequency;
	private LocalDate scannedDate;
	
	private Collection<RoutingIndexLegVO> routingIndexLegVO;

	/**
	 * 	Getter for clsOutTim 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getClsOutTim() {
		return clsOutTim;
	}
	/**
	 *  @param clsOutTim the clsOutTim to set
	 * 	Setter for clsOutTim 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setClsOutTim(String clsOutTim) {
		this.clsOutTim = clsOutTim;
	}
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for dayCount 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getDayCount() {
		return dayCount;
	}
	/**
	 *  @param dayCount the dayCount to set
	 * 	Setter for dayCount 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	/**
	 * 	Getter for contractType 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getContractType() {
		return contractType;
	}
	/**
	 *  @param contractType the contractType to set
	 * 	Setter for contractType 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	/**
	 * 	Getter for destination 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 *  @param destination the destination to set
	 * 	Setter for destination 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * 	Getter for equitableTender 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getEquitableTender() {
		return equitableTender;
	}
	/**
	 *  @param equitableTender the equitableTender to set
	 * 	Setter for equitableTender 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setEquitableTender(int equitableTender) {
		this.equitableTender = equitableTender;
	}
	/**
	 * 	Getter for finalDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getFinalDlvTime() {
		return finalDlvTime;
	}
	/**
	 *  @param finalDlvTime the finalDlvTime to set
	 * 	Setter for finalDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setFinalDlvTime(String finalDlvTime) {
		this.finalDlvTime = finalDlvTime;
	}
	/**
	 * 	Getter for hazardousIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getHazardousIndicator() {
		return hazardousIndicator;
	}
	/**
	 *  @param hazardousIndicator the hazardousIndicator to set
	 * 	Setter for hazardousIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setHazardousIndicator(String hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}
	/**
	 * 	Getter for lastUpdateTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 *  @param lastUpdateTime the lastUpdateTime to set
	 * 	Setter for lastUpdateTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 	Getter for lastUpdateUser 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 *  @param lastUpdateUser the lastUpdateUser to set
	 * 	Setter for lastUpdateUser 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * 	Getter for mailClass 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 *  @param mailClass the mailClass to set
	 * 	Setter for mailClass 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * 	Getter for mailCost 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getMailCost() {
		return mailCost;
	}
	/**
	 *  @param mailCost the mailCost to set
	 * 	Setter for mailCost 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMailCost(int mailCost) {
		this.mailCost = mailCost;
	}
	/**
	 * 	Getter for maxWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public double getMaxWgt() {
		return maxWgt;
	}
	/**
	 *  @param maxWgt the maxWgt to set
	 * 	Setter for maxWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMaxWgt(double maxWgt) {
		this.maxWgt = maxWgt;
	}
	/**
	 * 	Getter for minWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public double getMinWgt() {
		return minWgt;
	}
	/**
	 *  @param minWgt the minWgt to set
	 * 	Setter for minWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMinWgt(double minWgt) {
		this.minWgt = minWgt;
	}
	/**
	 * 	Getter for onetimePercent 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getOnetimePercent() {
		return onetimePercent;
	}
	/**
	 *  @param onetimePercent the onetimePercent to set
	 * 	Setter for onetimePercent 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setOnetimePercent(int onetimePercent) {
		this.onetimePercent = onetimePercent;
	}
	/**
	 * 	Getter for origin 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 *  @param origin the origin to set
	 * 	Setter for origin 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * 	Getter for perishableIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getPerishableIndicator() {
		return perishableIndicator;
	}
	/**
	 *  @param perishableIndicator the perishableIndicator to set
	 * 	Setter for perishableIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPerishableIndicator(String perishableIndicator) {
		this.perishableIndicator = perishableIndicator;
	}
	
	/**
	 * 	Getter for planRouteGenInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getPlanRouteGenInd() {
		return planRouteGenInd;
	}
	/**
	 *  @param planRouteGenInd the planRouteGenInd to set
	 * 	Setter for planRouteGenInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPlanRouteGenInd(String planRouteGenInd) {
		this.planRouteGenInd = planRouteGenInd;
	}
	/**
	 * 	Getter for planRouteScnInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getPlanRouteScnInd() {
		return planRouteScnInd;
	}
	/**
	 *  @param planRouteScnInd the planRouteScnInd to set
	 * 	Setter for planRouteScnInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPlanRouteScnInd(String planRouteScnInd) {
		this.planRouteScnInd = planRouteScnInd;
	}
	/**
	 * 	Getter for priorityCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getPriorityCode() {
		return priorityCode;
	}
	/**
	 *  @param priorityCode the priorityCode to set
	 * 	Setter for priorityCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}
	/**
	 * 	Getter for routingIndex 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getRoutingIndex() {
		return routingIndex;
	}
	/**
	 *  @param routingIndex the routingIndex to set
	 * 	Setter for routingIndex 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setRoutingIndex(String routingIndex) {
		this.routingIndex = routingIndex;
	}
	/**
	 * 	Getter for routingSeqNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getRoutingSeqNum() {
		return routingSeqNum;
	}
	/**
	 *  @param routingSeqNum the routingSeqNum to set
	 * 	Setter for routingSeqNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setRoutingSeqNum(int routingSeqNum) {
		this.routingSeqNum = routingSeqNum;
	}
	/**
	 * 	Getter for tagIndex 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getTagIndex() {
		return tagIndex;
	}
	/**
	 *  @param tagIndex the tagIndex to set
	 * 	Setter for tagIndex 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setTagIndex(int tagIndex) {
		this.tagIndex = tagIndex;
	}
	/**
	 * 	Getter for wgtUnit 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getWgtUnit() {
		return wgtUnit;
	}
	/**
	 *  @param wgtUnit the wgtUnit to set
	 * 	Setter for wgtUnit 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setWgtUnit(String wgtUnit) {
		this.wgtUnit = wgtUnit;
	}
	public Collection<RoutingIndexLegVO> getRoutingIndexLegVO() {
		return routingIndexLegVO;
	}
	public void setRoutingIndexLegVO(Collection<RoutingIndexLegVO> routingIndexLegVO) {
		this.routingIndexLegVO = routingIndexLegVO;
	}
	public String getPlannedDisDate() {
		return plannedDisDate;
	}
	public void setPlannedDisDate(String plannedDisDate) {
		this.plannedDisDate = plannedDisDate;
	}
	public String getPlannedEffectiveDate() {
		return plannedEffectiveDate;
	}
	public void setPlannedEffectiveDate(String plannedEffectiveDate) {
		this.plannedEffectiveDate = plannedEffectiveDate;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public LocalDate getScannedDate() {
		return scannedDate;
	}

	public void setScannedDate(LocalDate scannedDate) {
		this.scannedDate = scannedDate;
	}
	
	
	
	
	

}
