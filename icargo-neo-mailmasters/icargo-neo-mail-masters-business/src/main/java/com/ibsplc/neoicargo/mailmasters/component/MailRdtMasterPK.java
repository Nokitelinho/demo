package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMasterPK.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6991	:	17-Jul-2018	:	Draft
 */
@Setter
@Getter
@Embeddable
public class MailRdtMasterPK implements Serializable {
	/** 
	* The CompanyCode
	*/
	private String companyCode;
	/** 
	* The SERNUM
	*/
	private long SERNUM;

}
