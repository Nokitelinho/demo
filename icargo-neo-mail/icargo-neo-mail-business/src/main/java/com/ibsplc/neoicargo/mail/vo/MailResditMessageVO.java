
package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	28-Jun-2019	:	Draft
 */
@Setter
@Getter
public class MailResditMessageVO extends AbstractVO {

	private String companyCode;
	private String msgDetails;
	private String msgText;
	private ZonedDateTime eventDate;
	private String poaCode;
	private String msgVersionNumber;
	private String carditExist;
	private String messageIdentifier;
	private String messageType;
	private String eventCode;
	private String eventPort;
	private long msgSequenceNumber;


}
