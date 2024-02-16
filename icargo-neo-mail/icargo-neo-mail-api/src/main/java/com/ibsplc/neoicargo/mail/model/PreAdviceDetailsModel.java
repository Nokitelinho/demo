package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class PreAdviceDetailsModel extends BaseModel {
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private int totalbags;
	private Measure totalWeight;
	private String uldNumbr;
	private String mailCategory;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
