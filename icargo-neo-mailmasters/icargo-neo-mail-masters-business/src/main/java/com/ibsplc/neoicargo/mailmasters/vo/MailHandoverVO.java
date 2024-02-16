package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class MailHandoverVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String gpaCode;
	private String hoAirportCodes;
	private String serviceLevels;
	private String handoverTimes;
	private String hoOperationFlags;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String mailClass;
	private String exchangeOffice;
	private String mailSubClass;
	private int serialNumber;
	public static final java.lang.String OPERATION_FLAG_INSERT = "I";
	public static final java.lang.String OPERATION_FLAG_DELETE = "D";
	public static final java.lang.String OPERATION_FLAG_UPDATE = "U";
}
