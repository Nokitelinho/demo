package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Embeddable
public class MailActivityDetailPK implements Serializable {
	/** 
	* Company code
	*/
	private String companyCode;
	/** 
	* Mail bag number
	*/
	private String mailBagNumber;
	/** 
	* Service level activity
	*/
	private String slaActivity;

	/** 
	* @param companycode
	* @param mailBagNumber
	* @param slaActivity
	*/
	public MailActivityDetailPK(String companycode, String mailBagNumber, String slaActivity) {
		this.companyCode = companycode;
		this.mailBagNumber = mailBagNumber;
		this.slaActivity = slaActivity;
	}

	/** 
	* Instantiates a new mail activity detail pk.
	*/
	public MailActivityDetailPK() {
	}
}
