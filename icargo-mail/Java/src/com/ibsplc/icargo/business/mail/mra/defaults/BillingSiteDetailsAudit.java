/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteDetailsAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
@Staleable
@Table(name = "SHRBLGSITAUD")
@Entity
public class BillingSiteDetailsAudit {
	
		private Log log = LogFactory.getLogger("SHARED:BILLINGSITE:BillingSiteAudit");
		private static final String MODULE = "mail.mra";
		private String additionalInformation;
		private String auditRemarks;
		private String actionCode;
		private String updatedUser;
		private Calendar updatedTransactionTime;
		private Calendar updatedTransactionTimeUTC;
		private BillingSiteDetailsAuditPK billingSiteAuditDetailsPK;
		private String stationCode;
		/**
		 * @return the stationCode
		 */
		@Column(name = "STNCOD")
		public String getStationCode() {
			return stationCode;
		}
		/**
		 * @param stationCode the stationCode to set
		 */
		public void setStationCode(String stationCode) {
			this.stationCode = stationCode;
		}
		/**
		 * @return the updatedTransactionTime
		 */
		@Column(name = "UPDTXNTIM")
		public Calendar getUpdatedTransactionTime() {
			return updatedTransactionTime;
		}
		/**
		 * @param updatedTransactionTime the updatedTransactionTime to set
		 */
		public void setUpdatedTransactionTime(Calendar updatedTransactionTime) {
			this.updatedTransactionTime = updatedTransactionTime;
		}
		/**
		 * @return the updatedTransactionTimeUTC
		 */
		@Column(name = "UPDTXNTIMUTC")
		public Calendar getUpdatedTransactionTimeUTC() {
			return updatedTransactionTimeUTC;
		}
		/**
		 * @param updatedTransactionTimeUTC the updatedTransactionTimeUTC to set
		 */
		public void setUpdatedTransactionTimeUTC(Calendar updatedTransactionTimeUTC) {
			this.updatedTransactionTimeUTC = updatedTransactionTimeUTC;
		}
		/**
		 * @return the additionalInformation
		 */
		@Column(name = "ADLINF")
		public String getAdditionalInformation() {
			return additionalInformation;
		}
		/**
		 * @param additionalInformation the additionalInformation to set
		 */
		public void setAdditionalInformation(String additionalInformation) {
			this.additionalInformation = additionalInformation;
		}
		/**
		 * @return the additionalRemarks
		 */
		@Column(name = "AUDRMK")
		public String getAuditRemarks() {
			return auditRemarks;
		}
		/**
		 * @param additionalRemarks the additionalRemarks to set
		 */
		public void setAuditRemarks(String auditRemarks) {
			this.auditRemarks = auditRemarks;
		}
		/**
		 * @return the actionCode
		 */
		@Column(name = "ACTCOD")
		public String getActionCode() {
			return actionCode;
		}
		/**
		 * @param actionCode the actionCode to set
		 */
		public void setActionCode(String actionCode) {
			this.actionCode = actionCode;
		}
		/**
		 * @return the updatedUser
		 */
		@Column(name = "UPDUSR")
		public String getUpdatedUser() {
			return updatedUser;
		}
		/**
		 * @param updatedUser the updatedUser to set
		 */
		public void setUpdatedUser(String updatedUser) {
			this.updatedUser = updatedUser;
		}
		
		/**
		 * @return the billingSiteAuditPK
		 */
		@EmbeddedId
		@AttributeOverrides( {
				@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
				@AttributeOverride(name = "billingSiteCode", column = @Column(name = "BLGSITCOD")),
				@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
		public BillingSiteDetailsAuditPK getBillingSiteDetailsAuditPK() {
			return billingSiteAuditDetailsPK;
		}
		/**
		 * @param billingSiteAuditPK the billingSiteAuditPK to set
		 */
		public void setBillingSiteDetailsAuditPK(BillingSiteDetailsAuditPK billingSiteAuditDetailsPK) {
			this.billingSiteAuditDetailsPK = billingSiteAuditDetailsPK;
		}
		
		
		public BillingSiteDetailsAudit(){
			log.entering("BillingSiteAudit", "BillingSiteAudit");
		}
		
		public BillingSiteDetailsAudit(BillingSiteDetailsAuditVO billingSiteAuditVO) throws SystemException{
			populatePK(billingSiteAuditVO);
			populateAttributes(billingSiteAuditVO);
			
			try {
				PersistenceController.getEntityManager().persist(this);
			} catch (CreateException e) {
				throw new SystemException(e.getErrorCode(),e);
			}
			
		}
		
		/**
		 * Populate pk.
		 *
		 * @param billingSiteAuditVO the billing site audit vo
		 */
		private void populatePK(BillingSiteDetailsAuditVO billingSiteAuditVO) {
			log.entering("Inside populatePK()", "populatePK");
			BillingSiteDetailsAuditPK billSiteAuditPK = new BillingSiteDetailsAuditPK();
			billSiteAuditPK.setCompanyCode(billingSiteAuditVO.getCompanyCode());
			billSiteAuditPK.setBillingSiteCode(billingSiteAuditVO.getBillingSiteCode());
			this.setBillingSiteDetailsAuditPK(billSiteAuditPK);
		}
		
		private void populateAttributes(BillingSiteDetailsAuditVO billingSiteAuditVO){
			log.entering("populateAttributes()", "Inside populateAttributes()");
			this.setAdditionalInformation(billingSiteAuditVO.getAdditionalInformation());
			this.setAuditRemarks(billingSiteAuditVO.getAuditRemarks());
			this.setActionCode(billingSiteAuditVO.getActionCode());
			this.setUpdatedUser(billingSiteAuditVO.getUserId());
			this.setUpdatedTransactionTime(billingSiteAuditVO.getTxnTime());
			this.setUpdatedTransactionTimeUTC(billingSiteAuditVO.getTxnLocalTime());
			this.setStationCode(billingSiteAuditVO.getStationCode());
			
		}
}



