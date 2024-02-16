package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ResditEventModel extends BaseModel {
	private String companyCode;
	private String resditEventCode;
	private String resditEventStatus;
	private String consignmentNumber;
	private String eventPort;
	private String uniqueIdForFlag;
	private int messageSequenceNumber;
	private String actualResditEvent;
	private String paCode;
	private Collection<MailbagModel> resditMailbagVOs;
	private String resditVersion;
	private String eventPortName;
	private String carditExist;
	private String actualSenderId;
	private String interchangeControlReference;
	private LocalDate eventDate;
	private boolean m49Resdit;
	private String msgText;
	private String msgDetails;
	private boolean isMsgEventLocationEnabled;
	private boolean isPartialResdit;
	private String partyName;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String reciever;
	private String senderIdentifier;
	private String recipientIdentifier;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
