package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempMailBagDetailsPK.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6287	:	02-Mar-2020	:	Draft
 */
@Setter
@Getter
@Embeddable
public class CarditTempMailBagDetailsPK implements Serializable {
	private String companyCode;
	private long sequenceNumber;
	private String ConsignmentIdentifier;
	private String DRTagNo;

	/** 
	* Method		:	CarditTempMailBagDetailsPK.setCompanyCode Added by 	:	A-6287 on 02-Mar-2020 Used for 	: Parameters	:	@param companyCode  Return type	: 	void
	*/
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/** 
	* Method		:	CarditTempMailBagDetailsPK.setSequenceNumber Added by 	:	A-6287 on 02-Mar-2020 Used for 	: Parameters	:	@param sequenceNumber  Return type	: 	void
	*/
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Column(name = "CNSMNTIDR")
	public String getConsignmentIdentifier() {
		return ConsignmentIdentifier;
	}

	/** 
	* @param consignmentIdentifier the consignmentIdentifier to setSetter for consignmentIdentifier  Added by : A-6287 on 26-Feb-2020 Used for :
	*/
	public void setConsignmentIdentifier(String consignmentIdentifier) {
		ConsignmentIdentifier = consignmentIdentifier;
	}

	/** 
	* Getter for dRTagNo  Added by : A-6287 on 26-Feb-2020 Used for :
	*/
	@Column(name = "DRTAGNUM")
	public String getDRTagNo() {
		return DRTagNo;
	}

	/** 
	* @param dRTagNo the dRTagNo to setSetter for dRTagNo  Added by : A-6287 on 26-Feb-2020 Used for :
	*/
	public void setDRTagNo(String dRTagNo) {
		DRTagNo = dRTagNo;
	}
}
