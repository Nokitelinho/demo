package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ArriveAndImportMailModel extends BaseModel {
	private String companyCode;
	private int offset;
	private boolean isArrivalAndDeliveryMarkedTogether;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
