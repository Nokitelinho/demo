package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-5991
 */
@Setter
@Getter
public class MailbagInULDForSegmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String mailId;
	private String containerNumber;
	private String damageFlag;
	private ZonedDateTime scannedDate;
	private Quantity weight;
	private String mailClass;
	private String mailSubclass;
	private String mailCategoryCode;
	private String transferFromCarrier;
	private String sealNumber;
	private String acceptanceFlag;
	private String arrivalFlag;
	private String deliveredStatus;
	private String transferFlag;
	private String scannedPort;
	private String ubrNumber;
	private String controlDocumentNumber;
	private String transferToCarrier;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String arrivalSealNumber;
	private long mailSequenceNumber;
	private String recievedFlag;
	private String deliveredFlag;
	private Quantity deliveredWeight;
	private ZonedDateTime ghttim;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int carrierId;
	private String companyCode;
	private String assignedPort;
	private String containerType;
	private int legSerialNumber;
	private String paBuiltFlag;
	private String isFromTruck;
	private String mraStatus;
}
