package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailAuditFilterModel extends BaseModel {
	private String companyCode;
	private LocalDate txnFromDate;
	private LocalDate txnToDate;
	private String flightCarrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private String assignPort;
	private String containerNo;
	private int carrierId;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String dsn;
	private String ooe;
	private String doe;
	private String category;
	private String subclass;
	private String mailClass;
	private int year;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
