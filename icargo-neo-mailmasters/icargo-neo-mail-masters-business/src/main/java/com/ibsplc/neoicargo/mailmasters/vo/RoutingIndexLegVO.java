package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexLegVO.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
@Setter
@Getter
public class RoutingIndexLegVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String legAirStopScanInd;
	private String legArvTime;
	private String legDepTime;
	private String legDlvTime;
	private String legDstn;
	private String legEqpmnt;
	private String legFlight;
	private int legStopNum;
	private String legOrg;
	private String legRoute;
	private String legTranportCode;
	private LocalDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String routingIndex;
	private int routingSeqNum;
	private int routingSerNum;
	private int tagIndex;
}
