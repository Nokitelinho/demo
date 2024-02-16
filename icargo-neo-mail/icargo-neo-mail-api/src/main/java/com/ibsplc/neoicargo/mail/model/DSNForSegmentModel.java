package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNForSegmentModel extends BaseModel {
	private int acceptedBags;
	private Measure acceptedWeight;
	private int statedBags;
	private Measure statedWeight;
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailClass;
	private int year;
	private int receivedBags;
	private Measure receivedWeight;
	private int deliveredBags;
	private String mailSubclass;
	private String mailCategoryCode;
	private Measure deliveredWeight;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
