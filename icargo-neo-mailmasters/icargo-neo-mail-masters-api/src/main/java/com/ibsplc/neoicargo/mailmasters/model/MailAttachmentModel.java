package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailAttachmentModel extends BaseModel {
	private String fileName;
	private String attachmentStation;
	private LocalDate uploadDate;
	private String uploadUser;
	private LocalDate lastUpdateTime;
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
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
