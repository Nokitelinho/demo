package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class AWBDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private int ownerId;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private int statedPieces;
	private Quantity statedWeight;
	private Quantity statedVolume;
	private String shipmentDescription;
	private String origin;
	private String destination;
	private String operationFlag;
	private String shipmentPrefix;
	private String statedWeightCode;
	private String agentCode;
	private String documentSubType;
	private String ownerCode;
}
