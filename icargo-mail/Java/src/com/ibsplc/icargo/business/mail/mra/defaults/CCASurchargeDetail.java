/*
 * CCASurchargeDetail.java Created on Jul 13, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;
//import com.ibm.icu.util.Calendar;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jul 13, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 13, 2015	     A-5255		First draft
 * 0.2		Dec 05, 2017		 A-7794		Second Draft
 */
@Entity
@Table(name = "MALMRAMCACHGDTL")
@Staleable
public class CCASurchargeDetail {
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	
	
	private double orgSurChargeCTR;
	private double revSurChargeCTR;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private double surchareOrgRate;
	private double surchargeRevRate;
	private String billingStatus;
	
	
	/**
	 * @return the orgSurChargeCTR
	 */
	@Column(name = "ORGCHGAMTCTR")
	public double getOrgSurChargeCTR() {
		return orgSurChargeCTR;
	}
	/**
	 * @param orgSurChargeCTR the orgSurChargeCTR to set
	 */
	public void setOrgSurChargeCTR(double orgSurChargeCTR) {
		this.orgSurChargeCTR = orgSurChargeCTR;
	}
	/**
	 * @return the revSurChargeCTR
	 */
	@Column(name = "REVCHGAMTCTR")
	public double getRevSurChargeCTR() {
		return revSurChargeCTR;
	}
	/**
	 * @param revSurChargeCTR the revSurChargeCTR to set
	 */
	public void setRevSurChargeCTR(double revSurChargeCTR) {
		this.revSurChargeCTR = revSurChargeCTR;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	
	@Column(name = "ORGRAT")
	public double getSurchareOrgRate() {
		return surchareOrgRate;
	}
	public void setSurchareOrgRate(double surchareOrgRate) {
		this.surchareOrgRate = surchareOrgRate;
	}
	
	@Column(name = "REVRAT")
	public double getSurchargeRevRate() {
		return surchargeRevRate;
	}
	public void setSurchargeRevRate(double surchargeRevRate) {
		this.surchargeRevRate = surchargeRevRate;
	}

	private CCASurchargeDetailPK ccaSurchargeDetailPK;
	
	@Column(name = "BLGSTA")	
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public CCASurchargeDetail(){
		
	}
	public CCASurchargeDetail(SurchargeCCAdetailsVO surchargeCCAdetailsVO) throws SystemException{
		populatePK(surchargeCCAdetailsVO);
		populateAttributes(surchargeCCAdetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode());
		} 
	}
	/**
	 * 
	 * @author A-5255
	 * @param surchargeCCAdetailsVO
	 */
	private void populatePK(SurchargeCCAdetailsVO surchargeCCAdetailsVO){
		log.log(Log.FINE, "CCASurchargeDetail populatePK ", surchargeCCAdetailsVO);
		CCASurchargeDetailPK ccaSurchargeDetailPK=new CCASurchargeDetailPK();
		ccaSurchargeDetailPK.setCompanyCode(surchargeCCAdetailsVO.getCompanyCode());
		//ccaSurchargeDetailPK.setBillingBasis(surchargeCCAdetailsVO.getBillingBasis());
		//ccaSurchargeDetailPK.setCsgDocumentNumber(surchargeCCAdetailsVO.getCsgDocumentNumber());
		//ccaSurchargeDetailPK.setCsgSequenceNumber(surchargeCCAdetailsVO.getCsgSequenceNumber());
		//ccaSurchargeDetailPK.setPoaCode(surchargeCCAdetailsVO.getPoaCode());
		ccaSurchargeDetailPK.setMcaRefNumber(surchargeCCAdetailsVO.getCcaRefNumber());
		//ccaSurchargeDetailPK.setChargeType(surchargeCCAdetailsVO.getChargeHeadName());
		ccaSurchargeDetailPK.setChargeCode(surchargeCCAdetailsVO.getChargeHeadName());//a-8061 added for ICRD-254294
		ccaSurchargeDetailPK.setMailSequenceNumber(surchargeCCAdetailsVO.getMailSequenceNumber());//added by A-7371 as part of ICRD-257661
		this .ccaSurchargeDetailPK=ccaSurchargeDetailPK;
	}
	/**
	 * 
	 * @author A-5255
	 * @param surchargeCCAdetailsVO
	 */
	private void populateAttributes(SurchargeCCAdetailsVO surchargeCCAdetailsVO){
		if(surchargeCCAdetailsVO.getRevSurCharge()!=null){
			setRevSurChargeCTR(surchargeCCAdetailsVO.getRevSurCharge().getAmount());
		}
		if(surchargeCCAdetailsVO.getOrgSurCharge()!=null){
			setOrgSurChargeCTR(surchargeCCAdetailsVO.getOrgSurCharge().getAmount());
		}
		////a-8061 added for ICRD-254294 
		this.setLastUpdatedTime(surchargeCCAdetailsVO.getLastUpdateTime());
		this.setLastUpdatedUser(surchargeCCAdetailsVO.getLastUpdateUser());
		//Added by a-7540
		this.setSurchargeRevRate(surchargeCCAdetailsVO.getSurchargeRevRate());
		this.setSurchareOrgRate(surchargeCCAdetailsVO.getSurchareOrgRate());
		this.setBillingStatus("BB");
	}
	/**
	 *
	 * @return the ccaSurchargeDetailPK
	 */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="mailSequenceNumber", column=@Column(name="MALSEQNUM")),
		@AttributeOverride(name="mcaRefNumber", column=@Column(name="MCAREFNUM")),
		@AttributeOverride(name="chargeCode", column=@Column(name="CHGCOD"))}
	)
	public CCASurchargeDetailPK getCcaSurchargeDetailPK() {
		return ccaSurchargeDetailPK;
	}
	/**
	 * @param ccaSurchargeDetailPK the ccaSurchargeDetailPK to set
	 */
	public void setCcaSurchargeDetailPK(CCASurchargeDetailPK ccaSurchargeDetailPK) {
		this.ccaSurchargeDetailPK = ccaSurchargeDetailPK;
	}
	
	/**
	 * 
	 * @author A-5255
	 * @throws SystemException
	 */
	public void remove() throws SystemException {

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}
}
