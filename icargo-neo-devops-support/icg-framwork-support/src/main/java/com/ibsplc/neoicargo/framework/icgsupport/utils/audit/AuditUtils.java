package com.ibsplc.neoicargo.framework.icgsupport.utils.audit;

public class AuditUtils {

	 public static <T extends AuditVO> void performAudit(T auditVO){
		 throw new UnsupportedOperationException();
	 }
	 
	 public static <T extends AuditVO> void performAudit(T auditVO, boolean isForceAudit){
		 throw new UnsupportedOperationException();
	 }
	 
	 public static final AuditVO populateAuditDetails(AuditVO auditVO, Object entity){
		 return auditVO;
	 }
	
	 public static final AuditVO populateAuditDetails(AuditVO auditVO, Object entity, boolean isCreate){
		return auditVO; 
	 }
	 
}
