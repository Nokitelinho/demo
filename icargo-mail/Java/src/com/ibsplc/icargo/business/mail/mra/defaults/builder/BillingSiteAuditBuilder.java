/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.builder;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteDetailsAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;

/**
 * The Class BillingSiteAuditBuilder.
 *
 * @author A-5219
 */
public class BillingSiteAuditBuilder extends AbstractActionBuilder{
	
	/** The audit vo. */
	private BillingSiteDetailsAuditVO auditVO;
	
	/** The Constant INSERT. */
	private static final String INSERT="I";
	
	/** The Constant UPDATE. */
	private static final String DELETE="D";
	
	/**
	 * Gets the audit vo.
	 *
	 * @return the audit vo
	 */
	public BillingSiteDetailsAuditVO getAuditVO() {
		return new BillingSiteDetailsAuditVO(BillingSiteDetailsAuditVO.AUDIT_MODULENAME,
				BillingSiteDetailsAuditVO.AUDIT_SUBMODULENAME,BillingSiteDetailsAuditVO.AUDIT_ENTITY);
		
	}
		
		/**
		 * Audit billing site details.
		 *
		 * @param billingSiteVO the billing site vo
		 * @throws SystemException the system exception
		 */
		public void auditBillingSiteDetails(
				BillingSiteVO billingSiteVO)
				throws SystemException{
			if(!"U".equals(billingSiteVO.getOperationFlag())&& !"N".equals(billingSiteVO.getOperationFlag()) && !billingSiteVO.isDuplicate() && !billingSiteVO.isOverlapping()){
				auditVO=getAuditVO();
				auditVO.setCompanyCode(billingSiteVO.getCompanyCode());
				auditVO.setGeneratedSiteCode(billingSiteVO.getGeneratedBillingSiteCode());
				auditVO.setBillingSiteCode(billingSiteVO.getBillingSiteCode());
				if(INSERT.equals(billingSiteVO.getOperationFlag())){
					auditVO.setActionCode(BillingSiteDetailsAuditVO.BILLINGSITE_CREATED);
					auditVO.setAuditRemarks("Billing Site created");
					auditVO.setAdditionalInformation(new StringBuilder("The Billing Site Code ").append(billingSiteVO.getBillingSiteCode()).append(" has been created").toString());
				}	
				else if(DELETE.equals(billingSiteVO.getOperationFlag())){
					auditVO.setActionCode(BillingSiteDetailsAuditVO.BILLINGSITE_DELETED);
					auditVO.setAuditRemarks("Billing Site Deleted");
					auditVO.setAdditionalInformation(new StringBuilder("The Billing Site Code ").append(billingSiteVO.getBillingSiteCode()).append(" has been deleted").toString());
				}
				AuditUtils.performAudit(auditVO);
			}
		}
}
