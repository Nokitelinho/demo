package com.ibsplc.neoicargo.mailmasters.model;

import java.util.Calendar;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class RoutingIndexLegModel extends BaseModel {
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
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String routingIndex;
	private int routingSeqNum;
	private int routingSerNum;
	private int tagIndex;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
