package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailActivityDetailModel extends BaseModel {
	private String companyCode;
	private String mailBagNumber;
	private String serviceLevelActivity;
	private int airlineIdentifier;
	private String airlineCode;
	private String gpaCode;
	private String slaIdentifier;
	private int flightCarrierId;
	private String flightNumber;
	private String flightCarrierCode;
	private LocalDate plannedTime;
	private LocalDate actualTime;
	private String slaStatus;
	private String mailCategory;
	private int serviceTime;
	private String alertStatus;
	private int numberOfChasers;
	private int alertTime;
	private int chaserTime;
	private int chaserFrequency;
	private int maximumNumberOfChasers;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
