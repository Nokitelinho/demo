package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class MailArrivalFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String pou;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String mailStatus;
	private String paCode;
	private int nextCarrierId;
	private String nextCarrierCode;
	private String containerNumber;
	private String containerType;
	private int defaultPageSize;
	private int pageNumber;
	private MailbagEnquiryFilterVO additionalFilter;
	private String pol;
	private String destination;
}
