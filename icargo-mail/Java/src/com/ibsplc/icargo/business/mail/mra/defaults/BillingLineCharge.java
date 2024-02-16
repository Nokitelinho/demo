/*
 * BillingLineCharge.java Created on Jun 24, 2015
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Jun 24, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jun 24, 2015 A-5255
 * First draft
 */
@Table(name = "MALMRABLGMTXLINBSS")
@Entity
@Staleable
public class BillingLineCharge {
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private BillingLineChargePK billingLineChargePK;
	
	private double applicableRate;
	
	/**
	 * 
	 */
	public BillingLineCharge() {

	}
	/**
	 * 
	 * @param billingLineChargeVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	public BillingLineCharge(BillingLineChargeVO billingLineChargeVO,String billinglinebasis)
			throws CreateException, SystemException {
		populatePK(billingLineChargeVO,billinglinebasis);
		populateAttributes(billingLineChargeVO);
		PersistenceController.getEntityManager().persist(this);
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineChargeVO
	 */
	public void populatePK(BillingLineChargeVO billingLineChargeVO, String billinglinebasis) {
		BillingLineChargePK billingLineChargePK = new BillingLineChargePK();
		billingLineChargePK
				.setCompanyCode(billingLineChargeVO.getCompanycode());
		billingLineChargePK.setBillingMatrixID(billingLineChargeVO
				.getBillingMatrixID());
		billingLineChargePK.setBillingLineSequenceNumber(billingLineChargeVO
				.getBillingLineSequenceNumber());
		billingLineChargePK.setChargeType(billingLineChargeVO.getChargeType());
		billingLineChargePK.setFrmWgt(billingLineChargeVO.getFrmWgt());
		billingLineChargePK.setRatingBasis(billinglinebasis);
		this.billingLineChargePK=billingLineChargePK;
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineChargeVO
	 */
	public void populateAttributes(BillingLineChargeVO billingLineChargeVO) {
		setApplicableRate(billingLineChargeVO.getApplicableRateCharge());
		//setRateType(billingLineChargeVO.getRateType());
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineChargePK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static BillingLineCharge find(BillingLineChargePK billingLineChargePK)
			throws SystemException, FinderException {
		Log findlog = LogFactory.getLogger("mailtracking mra");
		findlog.entering("BillingLineCharge", "find");
		EntityManager em = PersistenceController.getEntityManager();
		try {
			return em.find(BillingLineCharge.class, billingLineChargePK);
		} catch (FinderException ex) {
			ex.getErrorCode();
			throw ex;
		}
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineChargeVO
	 * @throws SystemException
	 */
	public void update(BillingLineChargeVO billingLineChargeVO)
	throws SystemException {
		log.log(Log.FINE, "UPDATE BillingLineChargeVO", billingLineChargeVO);
		populateAttributes(billingLineChargeVO);
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
			throw new SystemException(removeException.getErrorCode());
		} catch (OptimisticConcurrencyException optimisticConcurrencyException) {
			throw new SystemException(
					optimisticConcurrencyException.getErrorCode());
		}
	}
	/**
	 * @return the billingLineChargePK
	 */
	@Audit(name="BillingLineChargePK")
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "billingMatrixID", column = @Column(name = "BLGMTXCOD")),
			@AttributeOverride(name = "billingLineSequenceNumber", column = @Column(name = "BLGLINSEQNUM")),
			@AttributeOverride(name = "chargeType", column = @Column(name = "CHGTYP")),
			@AttributeOverride(name = "frmWgt", column = @Column(name = "FRMWGT")),
			@AttributeOverride(name = "ratingBasis", column = @Column(name = "RATBSS"))})
	public BillingLineChargePK getBillingLineChargePK() {
		return billingLineChargePK;
	}

	/**
	 * @param billingLineChargePK
	 *            the billingLineChargePK to set
	 */
	public void setBillingLineChargePK(BillingLineChargePK billingLineChargePK) {
		this.billingLineChargePK = billingLineChargePK;
	}

	/**
	 * @return the applicableRate
	 */
	@Column(name="APPRAT")
	public double getApplicableRate() {
		return applicableRate;
	}
	/**
	 * @param applicableRate the applicableRate to set
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	

	
}
