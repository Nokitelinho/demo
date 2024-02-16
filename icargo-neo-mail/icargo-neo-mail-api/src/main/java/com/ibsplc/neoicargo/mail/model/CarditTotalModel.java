package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditTotalModel extends BaseModel {
	private String numberOfReceptacles;
	private Measure weightOfReceptacles;
	private String mailClassCode;
	private Measure totalWeight;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
