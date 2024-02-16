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
public class MailbagInULDAtAirportVO extends AbstractVO {
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
	private long mailSequenceNumber;
	private int statedBags;
	private Quantity statedWgt;
	private int acceptedBags;
	private Quantity acceptedWgt;
	private int deliveredPcs;
	private Quantity deliveredWgt;
	private int recievedPcs;
	private Quantity recievedWgt;
	private String comapnyCode;
	private int carrierId;
	private String airportCode;
	private String uldNumber;
	private String arrivedFlag;
	private String deliveredFlag;
	private String acceptedFlag;
	private String mailSource;
}
