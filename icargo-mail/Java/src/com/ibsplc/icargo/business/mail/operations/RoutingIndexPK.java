/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexPK.java
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
public class RoutingIndexPK implements Serializable{
	
	
	private String companyCode;
	private String routingIndex;
	private int routingSeqNum;
	
	
	
	  public boolean equals(Object other) {
			return (other != null) && ((hashCode() == other.hashCode()));
		}
	    /**
	     * @return
	     */
		public int hashCode() {

			return new StringBuffer(companyCode).
					
					append(routingIndex).
					append(routingSeqNum).
					toString().hashCode();
		}

	
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}

	@KeyCondition(column = "RTGIDX")
	public String getRoutingIndex() {
		return routingIndex;
	}

	public void setRoutingIndex(String routingIndex) {
		this.routingIndex = routingIndex;
	}

	@KeyCondition(column = "RTGSEQNUM")
	public int getRoutingSeqNum() {
		return routingSeqNum;
	}

	public void setRoutingSeqNum(int routingSeqNum) {
		this.routingSeqNum = routingSeqNum;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("RoutingIndexPK [ ");
		sbul.append("companyCode '").append(this.companyCode);

		sbul.append("routingIndex '").append(this.routingIndex);
		sbul.append("routingSeqNum '").append(this.routingSeqNum);
		sbul.append("' ]");
		return sbul.toString();
	}

}
