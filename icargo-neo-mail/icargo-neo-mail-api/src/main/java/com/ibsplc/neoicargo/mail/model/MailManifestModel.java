package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailManifestModel extends BaseModel {
	private String companyCode;
	private String depPort;
	private String flightNumber;
	private String flightCarrierCode;
	private int carrierId;
	private LocalDate depDate;
	private String strDepDate;
	private String flightStatus;
	private String flightRoute;
	private int totalbags;
	private int totalAcceptedBags;
	private Measure totalAcceptedWeight;
	private Measure totalWeight;
	private Collection<ContainerDetailsModel> containerDetails;
	private Collection<MailSummaryModel> mailSummaryVOs;
	private HashMap<String, Collection<String>> polPouMap;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
