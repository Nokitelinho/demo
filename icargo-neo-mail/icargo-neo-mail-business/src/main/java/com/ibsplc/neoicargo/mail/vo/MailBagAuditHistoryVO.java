package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailBagAuditHistoryVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* The Dsn
	*/
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
	private int year;
	private long serialNumber;
	private long historySequenceNumber;
	private String lastUpdateUser;
	private String auditField;
	private String oldValue;
	private String newValue;
	private String usercode;
	private String station;
	/** 
	* Last update date and time
	*/
	private LocalDateTime lastUpdateTime;
	private long mailSequenceNumber;


}
