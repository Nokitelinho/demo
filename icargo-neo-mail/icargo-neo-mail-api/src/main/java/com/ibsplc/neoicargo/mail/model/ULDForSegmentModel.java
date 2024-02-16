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
public class ULDForSegmentModel extends BaseModel {
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private String uldNumber;
	private String pou;
	private String operationalFlag;
	private int receivedBags;
	private Measure receivedWeight;
	private int noOfBags;
	private Measure totalWeight;
	private String remarks;
	private String warehouseCode;
	private String locationCode;
	private String lastUpdateUser;
	private String transferFromCarrier;
	private String transferToCarrier;
	private String releasedFlag;
	private Collection<DSNInULDForSegmentModel> dsnInULDForSegmentVOs;
	private Collection<MailbagInULDForSegmentModel> mailbagInULDForSegmentVOs;
	private Collection<OnwardRouteForSegmentModel> onwardRoutings;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
