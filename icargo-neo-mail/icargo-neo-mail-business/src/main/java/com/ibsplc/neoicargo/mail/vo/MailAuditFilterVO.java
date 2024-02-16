package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailAuditFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private ZonedDateTime txnFromDate;
	private ZonedDateTime txnToDate;
	private String flightCarrierCode;
	private String flightNumber;
	private ZonedDateTime flightDate;
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
}
