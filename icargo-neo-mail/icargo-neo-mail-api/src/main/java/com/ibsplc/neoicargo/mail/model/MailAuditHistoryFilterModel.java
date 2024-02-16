package com.ibsplc.neoicargo.mail.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailAuditHistoryFilterModel extends BaseModel {
	private String mailbagId;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailSubclass;
	private String mailCategoryCode;
	private String companyCode;
	private String transaction;
	private String auditableField;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
