package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class TransferManifestModel extends BaseModel {
	private String companyCode;
	private String transferManifestId;
	private String airPort;
	private String transferredToCarrierCode;
	private String transferredToFltNumber;
	private String transferredFromCarCode;
	private String transferredFromFltNum;
	private LocalDate fromFltDat;
	private LocalDate toFltDat;
	private LocalDate transferredDate;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String toCarCodeDesc;
	private String fromCarCodeDesc;
	private int totalBags;
	private Measure totalWeight;
	private String transferStatus;
	private Collection<DSNModel> dsnVOs;
	private long transferredfrmFltSeqNum;
	private int transferredfrmSegSerNum;
	private String containerNumber;
	private String mailbagId;
	private long mailsequenceNumber;
	private String tranferSource;
	private String status;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
