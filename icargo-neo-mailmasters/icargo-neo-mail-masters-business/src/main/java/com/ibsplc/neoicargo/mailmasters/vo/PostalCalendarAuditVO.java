package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.PostalCalendarAuditVO.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-5219	:	13-Aug-2020	:	Draft
 */
@Setter
@Getter
public class PostalCalendarAuditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	public static final String AUDIT_MODULENAME = "mail";
	/** 
	* submodule name. 
	*/
	public static final String AUDIT_SUBMODULENAME = "operations";
	/** 
	* Entity name. 
	*/
	public static final String AUDIT_ENTITY = "mail.operations.postalcalendar";
	public static final String POSTAL_CALENDAR_CREATED = "MALCALMSTCRT";
	public static final String POSTAL_CALENDAR_UPDATED = "MALCALMSTUPD";
	public static final String POSTAL_CALENDAR_DELETED = "MALCALMSTDEL";
	private String postalCode;
	private String period;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
//
//	public PostalCalendarAuditVO(String moduleName, String subModuleName, String entityName) {
//		super(moduleName, subModuleName, entityName);
//	}
}
