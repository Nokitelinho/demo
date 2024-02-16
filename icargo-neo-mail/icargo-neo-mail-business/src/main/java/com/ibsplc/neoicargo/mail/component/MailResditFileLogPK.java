package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * TODO Add the purpose of this class
 * @author A-2135
 */
@Setter
@Getter
@Embeddable
public class MailResditFileLogPK implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The interchangeControlReference
	*/
	private String interchangeControlReference;
	/** 
	* The recipientID
	*/
	private String recipientID;

	/** 
	* @param companyCode the companyCode to set
	*/
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/** 
	* @param interchangeControlReference the interchangeControlReference to set
	*/
	public void setInterchangeControlReference(String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	/** 
	* @param recipientID the recipientID to set
	*/
	public void setRecipientID(String recipientID) {
		this.recipientID = recipientID;
	}
}
