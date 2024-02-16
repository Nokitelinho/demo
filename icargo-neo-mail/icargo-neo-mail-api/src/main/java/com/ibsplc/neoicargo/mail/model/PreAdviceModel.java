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
public class PreAdviceModel extends BaseModel {
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private LocalDate flightDate;
	private String carrierCode;
	private int totalBags;
	private Measure totalWeight;
	private Collection<PreAdviceDetailsModel> preAdviceDetails;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
