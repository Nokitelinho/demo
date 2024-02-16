package com.ibsplc.neoicargo.mail.component;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailResditMessagePK.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Jun 4, 2019	:	Draft
 */
@Setter
@Getter
@Embeddable
public class MailResditMessagePK implements Serializable {
	private String companyCode;
	private long messageIdentifier;


}
