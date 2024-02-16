package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5526This VO can be used to store mail attachments 
 */
@Setter
@Getter
public class MailAttachmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String fileName;
	private String attachmentStation;
	private ZonedDateTime uploadDate;
	private String uploadUser;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String attachmentOpFlag;
	private int serialNumber;
	private byte[] fileData;
	private String companyCode;
	private long mailbagId;
	private String contentType;
	private String remarks;
	private String documentType;
	private String reference1;
	private int docSerialNumber;
	private String attachmentType;
	private long attachmentSerialNumber;
	private String reference2;
}
