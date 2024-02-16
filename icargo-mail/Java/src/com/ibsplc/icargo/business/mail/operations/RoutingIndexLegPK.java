/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexLegPK.java
 *
 *	Created by	:	A-7531
 *	Created on	:	31-Aug-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexLegPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
public class RoutingIndexLegPK implements Serializable {

	
	
	private String companyCode;
	private String routingIndex;
	private int routingSeqNum;
	private int routingSerNum;
	
	
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for routingIndex 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	@KeyCondition(column = "RTGIDX")
	public String getRoutingIndex() {
		return routingIndex;
	}
	/**
	 *  @param routingIndex the routingIndex to set
	 * 	Setter for routingIndex 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	public void setRoutingIndex(String routingIndex) {
		this.routingIndex = routingIndex;
	}
	/**
	 * 	Getter for routingSeqNum 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	@KeyCondition(column = "RTGSEQNUM")
	public int getRoutingSeqNum() {
		return routingSeqNum;
	}
	/**
	 *  @param routingSeqNum the routingSeqNum to set
	 * 	Setter for routingSeqNum 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	public void setRoutingSeqNum(int routingSeqNum) {
		this.routingSeqNum = routingSeqNum;
	}
	/**
	 * 	Getter for routingSerNum 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	@KeyCondition(column = "RTGSERNUM")
	public int getRoutingSerNum() {
		return routingSerNum;
	}
	/**
	 *  @param routingSerNum the routingSerNum to set
	 * 	Setter for routingSerNum 
	 *	Added by : A-7531 on 12-Oct-2018
	 * 	Used for :
	 */
	public void setRoutingSerNum(int routingSerNum) {
		this.routingSerNum = routingSerNum;
	}
	

}
