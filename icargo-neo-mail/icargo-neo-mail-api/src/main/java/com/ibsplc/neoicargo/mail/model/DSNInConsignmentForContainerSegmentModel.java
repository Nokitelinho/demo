package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNInConsignmentForContainerSegmentModel extends BaseModel {
	private String consignmentNumber;
	private LocalDate consignmentDate;
	private int acceptedBags;
	private Measure acceptedWeight;
	private int statedBags;
	private Measure statedWeight;
	private String acceptedUser;
	private String mailCategoryCode;
	private LocalDate acceptedDate;
	private LocalDate receivedDate;
	private String paCode;
	private int sequenceNumber;
	private int receivedBags;
	private Measure receivedWeight;
	private int deliveredBags;
	private Measure deliveredWeight;
	private String mailClass;
	private String mailSubclass;
	private double statedVolume;
	private double acceptedVolume;
	private String mraStatus;
	private String remarks;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
