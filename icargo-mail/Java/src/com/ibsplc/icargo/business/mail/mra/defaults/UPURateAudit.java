package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



@Entity
@Table(name = "MALMRARATLINAUD")
@Staleable
public class UPURateAudit {
	
	private Log log = LogFactory.getLogger("SHARED:UPURATELINE:RateLineAudit");
	private UPURateAuditPK upuRateAuditPK;
	private String additionalInformation;
	private String auditRemarks;
	private String actionCode;
	private String updatedUser;
	private Calendar updatedTransactionTime;
	private Calendar updatedTransactionTimeUTC;
	StringBuilder additionalInfo = new StringBuilder();
	private String stationCode;
	private String origin;
	private String destination;
	private String orgDstLevel;
		
	
	public void setUpuRateAuditPK(UPURateAuditPK upuRateAuditPK) {
		this.upuRateAuditPK = upuRateAuditPK;
	}
	@Column(name = "ADLINF")
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	@Column(name = "AUDRMK")
	public String getAuditRemarks() {
		return auditRemarks;
	}
	public void setAuditRemarks(String auditRemarks) {
		this.auditRemarks = auditRemarks;
	}
	 @Column(name = "ACTCOD")
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	 @Column(name = "UPDUSR")
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	 @Column(name = "UPDTXNTIM")
	public Calendar getUpdatedTransactionTime() {
		return updatedTransactionTime;
	}
	public void setUpdatedTransactionTime(Calendar updatedTransactionTime) {
		this.updatedTransactionTime = updatedTransactionTime;
	}
	 @Column(name = "UPDTXNTIMUTC")
	public Calendar getUpdatedTransactionTimeUTC() {
		return updatedTransactionTimeUTC;
	}
	public void setUpdatedTransactionTimeUTC(Calendar updatedTransactionTimeUTC) {
		this.updatedTransactionTimeUTC = updatedTransactionTimeUTC;
	}
	 @Column(name = "STNCOD")
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	 @Column(name = "ORGCOD")
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	 @Column(name = "DSTCOD")
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	@EmbeddedId
			@AttributeOverrides( {
					@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
					@AttributeOverride(name = "rateCardID", column = @Column(name = "RATCRDCOD")),
					@AttributeOverride(name = "rateLineSerNum", column = @Column(name = "RATLINSERNUM")),
					@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
		public UPURateAuditPK getUpuRateAuditPK() {
			return upuRateAuditPK;
		}
	
	
	
	
	public UPURateAudit() {
	}

	public UPURateAudit(RateLineAuditVO rateLineAuditVO) throws SystemException{
		populatePK(rateLineAuditVO);
		populateAttributes(rateLineAuditVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
		
	}
	
	/**
	 * Populate pk.
	 *
	 * @param rateLineAuditVO the rate line audit vo
	 */
	private void populatePK(RateLineAuditVO rateLineAuditVO) {
		log.entering("Inside populatePK()", "populatePK");
		UPURateAuditPK upuRateAuditPK = new UPURateAuditPK();
		upuRateAuditPK.setCompanyCode(rateLineAuditVO.getCompanyCode());
		upuRateAuditPK.setRateCardID(rateLineAuditVO.getRateCardID());
		upuRateAuditPK.setRateLineSerNum(rateLineAuditVO.getRateLineSerNum());
		this.setUpuRateAuditPK(upuRateAuditPK);
	}
	
	private void populateAttributes(RateLineAuditVO rateLineAuditVO) {
	
	this.setOrigin(rateLineAuditVO.getOrigin());
	this.setDestination(rateLineAuditVO.getDestination());
	this.setUpdatedTransactionTime(rateLineAuditVO.getLastUpdateDate());
	this.setUpdatedUser(rateLineAuditVO.getLastUpdateUser());
	this.setStationCode(rateLineAuditVO.getStationCode());
	this.setActionCode(rateLineAuditVO.getActionCode());
	this.setAdditionalInformation(rateLineAuditVO.getAdditionalInformation());
	this.setAuditRemarks(rateLineAuditVO.getAuditRemarks());
	this.setUpdatedTransactionTimeUTC(rateLineAuditVO.getLastUpdateTime());
	this.setOrgDstLevel(rateLineAuditVO.getLevel());	
	
	}
	/**
	 * 	Getter for orgDstLevel 
	 *	Added by : A-5219 on 27-Oct-2020
	 * 	Used for :
	 */
	@Column(name = "ORGDSTLVL")
	public String getOrgDstLevel() {
		return orgDstLevel;
	}
	/**
	 *  @param orgDstLevel the orgDstLevel to set
	 * 	Setter for orgDstLevel 
	 *	Added by : A-5219 on 27-Oct-2020
	 * 	Used for :
	 */
	public void setOrgDstLevel(String orgDstLevel) {
		this.orgDstLevel = orgDstLevel;
	}
	

}
