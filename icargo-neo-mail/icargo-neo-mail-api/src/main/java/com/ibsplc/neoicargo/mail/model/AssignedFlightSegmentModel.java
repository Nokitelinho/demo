package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class AssignedFlightSegmentModel extends BaseModel {
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String pou;
	private String pol;
	private Collection<ULDForSegmentModel> containersForSegment;
	private Collection<DSNForSegmentModel> dsnsForSegment;
	private String mraStatus;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
