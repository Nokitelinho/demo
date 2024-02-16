package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNInConsignmentForULDSegmentModel extends BaseModel {
	private String consignmentNumber;
	private LocalDate consignmentDate;
	private String paCode;
	private int sequenceNumber;
	private int acceptedBags;
	private Measure acceptedWeight;
	private int statedBags;
	private Measure statedWeight;
	private String acceptedUser;
	private String mailCategoryCode;
	private String mailClass;
	private String mailSubclass;
	private LocalDate acceptedDate;
	private int receivedBags;
	private Measure receivedWeight;
	private int deliveredBags;
	private Measure deliveredWeight;
	private LocalDate receivedDate;
	private String transferFlag;
	private Measure statedVolume;
	private Measure acceptedVolume;
	private String mraStatus;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
