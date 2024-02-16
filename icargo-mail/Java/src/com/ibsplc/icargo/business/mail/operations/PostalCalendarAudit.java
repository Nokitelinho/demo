package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.PostalCalendarAuditPK;
import com.ibsplc.icargo.business.mail.operations.vo.PostalCalendarAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.PostalCalendarAudit.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	13-Aug-2020	:	Draft
 */
@Staleable
@Table(name = "SHRENTAUD")
@Entity
public class PostalCalendarAudit {
	
	private Log log = LogFactory.getLogger("SHARED:BILLINGSITE:BillingSiteAudit");
	private static final String MODULE = "mail.mra";
	private String additionalInformation;
	private String auditRemarks;
	private String actionCode;
	private String updatedUser;
	private Calendar updatedTransactionTime;
	private Calendar updatedTransactionTimeUTC;
	private PostalCalendarAuditPK postalCalendarAuditPK;
	private String stationCode;
	private String period;
	private String gpaCode;
	private String triggerPoint;
	
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
	 * @return the period
	 */
	@Column(name = "REFTWO")
	public String getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-5219 on 22-Aug-2020
	 * 	Used for :
	 */
	@Column(name = "REFONE")
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-5219 on 22-Aug-2020
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the triggerPoint
	 */
	@Column(name = "TRGPNT")
	public String getTriggerPoint() {
		return triggerPoint;
	}
	/**
	 * @param triggerPoint the triggerPoint to set
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}
	/**
	 * @return the billingSiteAuditPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "entityCode", column = @Column(name = "ENTCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
	public PostalCalendarAuditPK getPostalCalendarAuditPK() {
		return postalCalendarAuditPK;
	}
	/**
	 * @param billingSiteAuditPK the billingSiteAuditPK to set
	 */
	public void setPostalCalendarAuditPK(PostalCalendarAuditPK postalCalendarAuditPK) {
		this.postalCalendarAuditPK = postalCalendarAuditPK;
	}
	
	
	public PostalCalendarAudit(){
		log.entering("PostalCalendarAudit", "PostalCalendarAudit");
	}
	
	public PostalCalendarAudit(PostalCalendarAuditVO postalAuditVO) throws SystemException{
		populatePK(postalAuditVO);
		populateAttributes(postalAuditVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
		
	}
	
	/**
	 * 
	 * 	Method		:	PostalCalendarAudit.populatePK
	 *	Added by 	:	A-5219 on 13-Aug-2020
	 * 	Used for 	:
	 *	Parameters	:	@param postalAuditVO 
	 *	Return type	: 	void
	 */
	private void populatePK(PostalCalendarAuditVO postalAuditVO) {
		log.entering("Inside populatePK()", "populatePK");
		PostalCalendarAuditPK postalCalendarAuditPK = new PostalCalendarAuditPK();
		postalCalendarAuditPK.setCompanyCode(postalAuditVO.getCompanyCode());
		postalCalendarAuditPK.setEntityCode("MALCALMST");
		this.setPostalCalendarAuditPK(postalCalendarAuditPK);
	}
	
	/**
	 * 
	 * 	Method		:	PostalCalendarAudit.populateAttributes
	 *	Added by 	:	A-5219 on 13-Aug-2020
	 * 	Used for 	:
	 *	Parameters	:	@param postalAuditVO 
	 *	Return type	: 	void
	 */
	private void populateAttributes(PostalCalendarAuditVO postalAuditVO){
		log.entering("populateAttributes()", "Inside populateAttributes()");
		this.setAdditionalInformation(postalAuditVO.getAdditionalInformation());
		this.setAuditRemarks(postalAuditVO.getAuditRemarks());
		this.setPeriod(postalAuditVO.getPeriod());
		this.setGpaCode(postalAuditVO.getPostalCode());
		this.setActionCode(postalAuditVO.getActionCode());
		this.setUpdatedUser(postalAuditVO.getUserId());
		this.setUpdatedTransactionTime(postalAuditVO.getTxnTime());
		this.setUpdatedTransactionTimeUTC(postalAuditVO.getTxnLocalTime());
		this.setStationCode(postalAuditVO.getStationCode());
		this.setTriggerPoint("MTK055");
	}
}

