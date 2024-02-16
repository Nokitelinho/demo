package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class MailOnHandDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String currentAirport;
	private String destination;
	private String subclassGroup;
	private String uldCount;
	private int mailTrolleyCount;
	private int noOfDaysInCurrentLoc;
	private ZonedDateTime scnDate;
	private String scanPort;
	private String uldCountDisplay;
	private int noOfMTSpace;
}
