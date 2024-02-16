package com.ibsplc.neoicargo.mail.model;

import java.util.Calendar;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailScanDetailModel extends BaseModel {
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
	private Calendar lastUpdateTime;
	private LocalDate scanDate;
	private String nodeName;
	private String consignmentNumber;
	private boolean rdtProcessing;
	private String eventCode;
	private String rawMessageBlob;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
