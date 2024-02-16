package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditEnquiryModel extends BaseModel {
	private String companyCode;
	private String searchMode;
	private String unsendResditEvent;
	private String flightType;
	private String resditEventPort;
	private String resditEventCode;
	private OperationalFlightModel operationalFlightVo;
	private Collection<ContainerModel> containerVos;
	private Collection<MailbagModel> mailbagVos;
	private Collection<DespatchDetailsModel> despatchDetailVos;
	private Collection<ConsignmentDocumentModel> consignmentDocumentVos;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
