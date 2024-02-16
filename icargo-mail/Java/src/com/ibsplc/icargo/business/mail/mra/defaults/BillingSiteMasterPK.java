/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteMasterPK.java
 *
 *	Created by	:	A-5219
 *	Created on	:	19-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteMasterPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	19-Nov-2013	:	Draft
 */
@Embeddable
public class BillingSiteMasterPK implements Serializable{
	
	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/**
	 * Getter for companyCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the company code
	 */
	@KeyCondition(column="CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 * Setter for companyCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Getter for billingSiteCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site code
	 */
	 @KeyCondition(column="BLGSITCOD")
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the billingSiteCode to set
	 * Setter for billingSiteCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}
	
	/**
	 * Instantiates a new billing site master pk.
	 */
	public BillingSiteMasterPK(){
		
	}
	
	/**
	 * Instantiates a new billing site master pk.
	 *
	 * @param companyCode the company code
	 * @param billingSiteCode the billing site code
	 */
	public BillingSiteMasterPK(String companyCode, String billingSiteCode){
		super();
		this.companyCode=companyCode;
		this.billingSiteCode=billingSiteCode;
			
	}
	
	/**
	 *	Overriding Method	:	@see java.lang.Object#equals(java.lang.Object)
	 *	Added by 			: A-5219 on 19-Nov-2013
	 * 	Used for 	:
	 *	Parameters	:	@param other
	 *	Parameters	:	@return 
	 */
	public boolean equals(Object other) {
		if(other != null ){
			return (hashCode() == other.hashCode());
		}else {
			return false;
		}
	}
	
	/**
	 *	Overriding Method	:	@see java.lang.Object#hashCode()
	 *	Added by 			: A-5219 on 19-Nov-2013
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(billingSiteCode).
				toString().hashCode();
	}
	
	/**
	 * Overriding Method	:	@see java.lang.Object#toString()
	 * Added by 			: A-5219 on 19-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the string
	 */
	public String toString(){
		return new StringBuffer().append("companyCode=")
						.append(companyCode)
						.append("\billingSiteCode=")
						.append(billingSiteCode)
						.toString() ;
	}

}
