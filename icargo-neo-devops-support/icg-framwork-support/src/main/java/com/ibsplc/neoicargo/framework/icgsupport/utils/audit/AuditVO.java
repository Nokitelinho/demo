package com.ibsplc.neoicargo.framework.icgsupport.utils.audit;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditVO {

	public AuditVO(String moduleName, String subModuleName, String entityName) {
		this.moduleName=moduleName;
		this.subModuleName=subModuleName;
		this.entityName=entityName;
	}
	public static final String CREATE_ACTION = "CREATE";
	public static final String UPDATE_ACTION = "UPDATE";
	public static final String DELETE_ACTION = "DELETE";
	private String companyCode;
	private String moduleName;
	private String subModuleName;
	private String entityName;
	private String userId;
	private LocalDateTime txnTime;
	private LocalDateTime txnLocalTime;
	private Collection<AuditFieldVO> auditFields;
	private Collection<AuditHistoryVO> auditHistoryVos;
	private String auditRemarks;
	private String additionalInformation;
	private String actionCode;
	private String stationCode;
	private String auditEntityclassName;
	private String auditHistoryClassName;
	private String entityclassName;
	private String auditTriggerPoint;
	private boolean cmnAudited;
	private String entityDes;
	private boolean isAuditEnabled;
	private String probeCorrelationId;
	
	public void setTxnTime(ZonedDateTime txnTime){
		if(Objects.nonNull(txnTime)){
			this.txnTime = txnTime.toLocalDateTime();
		}
	}
	
	public void setTxnLocalTime(ZonedDateTime txnLocalTime){
		if(Objects.nonNull(txnLocalTime)){
			this.txnLocalTime = txnLocalTime.toLocalDateTime();
		}
	}

	public void setTxnTime(LocalDateTime txnTime) {
		this.txnTime = txnTime;
	}

	public void setTxnLocalTime(LocalDateTime txnLocalTime) {
		this.txnLocalTime = txnLocalTime;
	}
}
