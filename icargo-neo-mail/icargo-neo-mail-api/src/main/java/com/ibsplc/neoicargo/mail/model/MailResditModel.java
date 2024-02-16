package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailResditModel extends BaseModel {
	private String companyCode;
	private String mailId;
	private String eventCode;
	private String eventAirport;
	private LocalDate eventDate;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String uldNumber;
	private String resditSentFlag;
	private long resditSequenceNum;
	private String processedStatus;
	private long messageSequenceNumber;
	private String carditKey;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String partyIdentifier;
	private String mailboxID;
	private long messageIdentifier;
	private boolean fromDeviationList;
	private String senderIdentifier;
	private String recipientIdentifier;
	private String dependantEventCode;
	private String paOrCarrierCode;
	private int mailEventSequenceNumber;
	private String interchangeControlReference;
	private String reconciliationStatus;
	private long mailSequenceNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
