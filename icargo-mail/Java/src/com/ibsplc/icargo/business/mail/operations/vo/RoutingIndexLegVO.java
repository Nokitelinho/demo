/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexLegVO.java
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

import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexLegVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
public class RoutingIndexLegVO extends  AbstractVO {
	
	
	private String companyCode;
	private String legAirStopScanInd;
	private String legArvTime;
	private String legDepTime;
	private String legDlvTime;
	private String legDstn;
	private String legEqpmnt;
	private String legFlight;
	private int legStopNum;
	private String legOrg;
	private String legRoute;
	private String legTranportCode;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String routingIndex;
	private int routingSeqNum;
	private int routingSerNum;
	private int tagIndex;
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
	 * 	Getter for legAirStopScanInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegAirStopScanInd() {
		return legAirStopScanInd;
	}
	/**
	 *  @param legAirStopScanInd the legAirStopScanInd to set
	 * 	Setter for legAirStopScanInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegAirStopScanInd(String legAirStopScanInd) {
		this.legAirStopScanInd = legAirStopScanInd;
	}
	/**
	 * 	Getter for legArvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegArvTime() {
		return legArvTime;
	}
	/**
	 *  @param legArvTime the legArvTime to set
	 * 	Setter for legArvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegArvTime(String legArvTime) {
		this.legArvTime = legArvTime;
	}
	/**
	 * 	Getter for legDepTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegDepTime() {
		return legDepTime;
	}
	/**
	 *  @param legDepTime the legDepTime to set
	 * 	Setter for legDepTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegDepTime(String legDepTime) {
		this.legDepTime = legDepTime;
	}
	/**
	 * 	Getter for legDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegDlvTime() {
		return legDlvTime;
	}
	/**
	 *  @param legDlvTime the legDlvTime to set
	 * 	Setter for legDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegDlvTime(String legDlvTime) {
		this.legDlvTime = legDlvTime;
	}
	/**
	 * 	Getter for legDstn 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegDstn() {
		return legDstn;
	}
	/**
	 *  @param legDstn the legDstn to set
	 * 	Setter for legDstn 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegDstn(String legDstn) {
		this.legDstn = legDstn;
	}
	/**
	 * 	Getter for legEqpmnt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegEqpmnt() {
		return legEqpmnt;
	}
	/**
	 *  @param legEqpmnt the legEqpmnt to set
	 * 	Setter for legEqpmnt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegEqpmnt(String legEqpmnt) {
		this.legEqpmnt = legEqpmnt;
	}
	/**
	 * 	Getter for legFlight 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegFlight() {
		return legFlight;
	}
	/**
	 *  @param legFlight the legFlight to set
	 * 	Setter for legFlight 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegFlight(String legFlight) {
		this.legFlight = legFlight;
	}
	/**
	 * 	Getter for legStopNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getLegStopNum() {
		return legStopNum;
	}
	/**
	 *  @param legStopNum the legStopNum to set
	 * 	Setter for legStopNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegStopNum(int legStopNum) {
		this.legStopNum = legStopNum;
	}
	/**
	 * 	Getter for legOrg 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegOrg() {
		return legOrg;
	}
	/**
	 *  @param legOrg the legOrg to set
	 * 	Setter for legOrg 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegOrg(String legOrg) {
		this.legOrg = legOrg;
	}
	/**
	 * 	Getter for legRoute 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegRoute() {
		return legRoute;
	}
	/**
	 *  @param legRoute the legRoute to set
	 * 	Setter for legRoute 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegRoute(String legRoute) {
		this.legRoute = legRoute;
	}
	/**
	 * 	Getter for legTranportCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public String getLegTranportCode() {
		return legTranportCode;
	}
	/**
	 *  @param legTranportCode the legTranportCode to set
	 * 	Setter for legTranportCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegTranportCode(String legTranportCode) {
		this.legTranportCode = legTranportCode;
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
	 * 	Getter for routingSerNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public int getRoutingSerNum() {
		return routingSerNum;
	}
	/**
	 *  @param routingSerNum the routingSerNum to set
	 * 	Setter for routingSerNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setRoutingSerNum(int routingSerNum) {
		this.routingSerNum = routingSerNum;
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
	

}
