package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OffloadModel extends BaseModel {
	private String companyCode;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private String userCode;
	private String offloadType;
	private Collection<ContainerModel> offloadContainers;
	private Page<DespatchDetailsModel> offloadDSNs;
	private Page<MailbagModel> offloadMailbags;
	private Page<ContainerModel> offloadContainerDetails;
	private boolean isDepartureOverride;
	private boolean fltClosureChkNotReq;
	private boolean isRemove;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
