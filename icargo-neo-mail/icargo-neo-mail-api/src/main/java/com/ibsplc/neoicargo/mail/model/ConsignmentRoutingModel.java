package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ConsignmentRoutingModel extends BaseModel {
	private String companyCode;
	private String consignmentDocNumber;
	private int consignmentSeqNumber;
	private String poaCode;
	private int routingSerialNumber;
	private String flightCarrierCode;
	private int flightCarrierId;
	private String flightNumber;
	private LocalDate flightDate;
	private String pol;
	private String pou;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
