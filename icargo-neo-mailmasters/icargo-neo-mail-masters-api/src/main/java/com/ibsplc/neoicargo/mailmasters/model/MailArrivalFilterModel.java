package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailArrivalFilterModel extends BaseModel {
	private String companyCode;
	private String pou;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private String mailStatus;
	private String paCode;
	private int nextCarrierId;
	private String nextCarrierCode;
	private String containerNumber;
	private String containerType;
	private int defaultPageSize;
	private int pageNumber;
	private MailbagEnquiryFilterModel additionalFilter;
	private String pol;
	private String destination;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
