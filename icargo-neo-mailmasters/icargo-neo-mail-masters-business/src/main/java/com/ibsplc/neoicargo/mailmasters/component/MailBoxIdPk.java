package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxIdPk.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1 : A-4809 : Aug 5, 2016 : Draft
 */
@Setter
@Getter
@Embeddable
public class MailBoxIdPk implements Serializable {
	/** 
	* The companyCode
	*/
	private String companyCode;
	/** 
	* The mailboxCode
	*/
	private String mailboxCode;
}
