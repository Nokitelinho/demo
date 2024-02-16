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


import com.ibsplc.icargo.business.mail.mra.defaults.vo.MCAAuditVO;
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
@Table(name = "MALMRAMCAAUD")
@Entity
public class MCADetailsAudit {
	private Log log = LogFactory.getLogger("MAILTRACKING:MRA:MCADetailsAudit");
	private static final String MODULE = "mailtracking.mra";
	
	//private int serialNumber;
	private String additionalInformation;
	private String auditRemarks;
	private String actionCode;
	private String updatedUser;
	private Calendar updatedTransactionTime;
	private Calendar updatedTransactionTimeUTC;
	private String stationCode;
	
	private MCADetailsAuditPK mCADetailsAuditPK;
	
	
	/**
	 * @return the serialNumber
	 */
	//@Column(name = "SERNUM")
	//public int getSerialNumber() {
	//	return serialNumber;
	//}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	//public void setSerialNumber(int serialNumber) {
	//	this.serialNumber = serialNumber;
	//}
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
	 * @return the auditRemarks
	 */
	@Column(name = "AUDRMK")
	public String getAuditRemarks() {
		return auditRemarks;
	}
	/**
	 * @param auditRemarks the auditRemarks to set
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
	 * @param mCADetailsAuditPK the mCADetailsAuditPK to set
	 */
	public void setmCADetailsAuditPK(MCADetailsAuditPK mCADetailsAuditPK) {
		this.mCADetailsAuditPK = mCADetailsAuditPK;
	}
	
	/**
	 * @return the mCADetailsAuditPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mcaRefNumber", column = @Column(name = "MCAREFNUM")),
			@AttributeOverride(name = "mailSequenecNumber", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
	public MCADetailsAuditPK getmCADetailsAuditPK() {
		return mCADetailsAuditPK;
	}
	
	public MCADetailsAudit(){
		log.entering("MCADetailsAudit", "MCADetailsAudit");
	}
	
	public MCADetailsAudit(MCAAuditVO mCAAuditVO) throws SystemException{
		populatePK(mCAAuditVO);
		populateAttributes(mCAAuditVO);
		
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
	private void populatePK(MCAAuditVO mCAAuditVO) {
		log.entering("Inside populatePK()", "populatePK");
		MCADetailsAuditPK mCADetailsAuditPK = new MCADetailsAuditPK();
		mCADetailsAuditPK.setCompanyCode(mCAAuditVO.getCompanyCode());
		mCADetailsAuditPK.setMcaRefNumber(mCAAuditVO.getCcaRefNumber());
		//mCADetailsAuditPK.setBillingBasis(mCAAuditVO.getBillingBasis());
		mCADetailsAuditPK.setMailSequenecNumber(mCAAuditVO.getMailSequenceNumber());
		this.setmCADetailsAuditPK(mCADetailsAuditPK);
	}
	
	private void populateAttributes(MCAAuditVO mCAAuditVO){
		log.entering("populateAttributes()", "Inside populateAttributes()");
		this.setAdditionalInformation(mCAAuditVO.getAdditionalInformation());
		this.setAuditRemarks(mCAAuditVO.getAuditRemarks());
		this.setActionCode(mCAAuditVO.getActionCode());
		if(mCAAuditVO.getAutoMCAUpdatedUser()!=null && mCAAuditVO.getAutoMCAUpdatedUser().trim().length()>0){
			this.setUpdatedUser(mCAAuditVO.getAutoMCAUpdatedUser());
		}else{
		this.setUpdatedUser(mCAAuditVO.getUserId());
		}
		this.setUpdatedTransactionTime(mCAAuditVO.getTxnTime());
		this.setUpdatedTransactionTimeUTC(mCAAuditVO.getTxnLocalTime());
		this.setStationCode(mCAAuditVO.getStationCode());
		
	}

}
