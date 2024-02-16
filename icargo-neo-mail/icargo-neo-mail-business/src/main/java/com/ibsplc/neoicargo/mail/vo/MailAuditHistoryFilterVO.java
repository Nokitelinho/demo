package com.ibsplc.neoicargo.mail.vo;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailAuditHistoryFilterVO implements Serializable {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String mailbagId;
	private String dsn;
	/** 
	* The originExchangeOffice
	*/
	private String originExchangeOffice;
	/** 
	* The destinationExchangeOffice
	*/
	private String destinationExchangeOffice;
	/** 
	* The mailSubClass
	*/
	private String mailSubclass;
	/** 
	* The mailCategory
	*/
	private String mailCategoryCode;
	private String companyCode;
	private String transaction;
	private String auditableField;
}
