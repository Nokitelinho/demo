package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailScanDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String mailBagId;
	private int serialNumber;
	private String scanData;
	private String scannedUser;
	private String airport;
	private String deviceId;
	private String deviceIpAddress;
	private int fileSequence;
	private int mailSequenceNumber;
	private String uploadStatus;
	private String poaCode;
	private String totalEventCodes;
	private String messageType;
	private String lastUpdateUser;
	private LocalDateTime lastUpdateTime;
	private ZonedDateTime scanDate;
	private String nodeName;
	private String consignmentNumber;
	private boolean rdtProcessing;
	private String eventCode;
	private String rawMessageBlob;
}
