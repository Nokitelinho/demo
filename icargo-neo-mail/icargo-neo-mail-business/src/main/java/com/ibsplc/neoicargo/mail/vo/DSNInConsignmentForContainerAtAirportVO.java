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
public class DSNInConsignmentForContainerAtAirportVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String consignmentNumber;
	private ZonedDateTime consignmentDate;
	private int acceptedBags;
	private Quantity acceptedWeight;
	private int statedBags;
	private Quantity statedWeight;
	private String acceptedUser;
	private String mailClass;
	private ZonedDateTime acceptedDate;
	private String paCode;
	private int sequenceNumber;
	private String offloadFlag;
	private Quantity statedVolume;
	private Quantity acceptedVolume;
	private String remarks;
	private String transactionCode;
}
