package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNInContainerForSegmentModel extends BaseModel {
	private String containerNumber;
	private int statedBags;
	private Measure statedWeight;
	private int acceptedBags;
	private Measure acceptedWeight;
	private String remarks;
	private String warehouseCode;
	private String locationCode;
	private String mailClass;
	private Collection<DSNInConsignmentForContainerSegmentModel> dsnInConsignments;
	private String ubrNumber;
	private double mailrate;
	private String currencyCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
