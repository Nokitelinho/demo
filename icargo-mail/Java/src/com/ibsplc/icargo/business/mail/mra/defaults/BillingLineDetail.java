/*
 * BillingLineDetail.java Created on Jun 24, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
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
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 24, 2015	     A-5255		First draft
 */
@Table(name = "MTKBLGLINDTL")
@Entity
@Staleable
@Deprecated
public class BillingLineDetail {
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");	
	private BillingLineDetailPK billingLineDetailPK;
	private String ratingBasis;
	private Set<BillingLineCharge> billingLineCharges;
	
	public BillingLineDetail(){
		
	}
	/**
	 * 
	 * @param billingLineDetailVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	public BillingLineDetail(BillingLineDetailVO billingLineDetailVO) throws CreateException, SystemException{
		log.entering("BillingLineDetail", "Constructor");
		populatePK(billingLineDetailVO);
		populateAttributes(billingLineDetailVO);
		PersistenceController.getEntityManager().persist(this);
		if(billingLineDetailVO.getBillingLineCharges()!=null){
			for (BillingLineChargeVO billingLineChargeVO : billingLineDetailVO
					.getBillingLineCharges()) {
				billingLineChargeVO.setCompanycode(billingLineDetailVO.getCompanycode());
				billingLineChargeVO.setBillingMatrixID(billingLineDetailVO
				.getBillingMatrixID());
				billingLineChargeVO.setBillingLineSequenceNumber(billingLineDetailVO
				.getBillingLineSequenceNumber());
				billingLineChargeVO.setChargeType(billingLineDetailVO.getChargeType());
				saveBillingLineCharge(billingLineChargeVO);
			}
		}
		log.exiting("BillingLineDetail", "Constructor");
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineDetailVO
	 */
	public void populatePK(BillingLineDetailVO billingLineDetailVO){
		log.entering("BillingLineDetail", "populatePK");
		BillingLineDetailPK billingLineDetailPK=new BillingLineDetailPK();
		billingLineDetailPK.setCompanyCode(billingLineDetailVO.getCompanycode());
		billingLineDetailPK.setBillingMatrixID(billingLineDetailVO.getBillingMatrixID());
		billingLineDetailPK.setBillingLineSequenceNumber(billingLineDetailVO.getBillingLineSequenceNumber());
		billingLineDetailPK.setChargeType(billingLineDetailVO.getChargeType());
		log.exiting("BillingLineDetail", "populatePK");
		this.billingLineDetailPK=billingLineDetailPK;
	}
	/**
	 *
	 * @author A-5255
	 * @param billingLineDetailVO
	 */
	public void populateAttributes(BillingLineDetailVO billingLineDetailVO){
		log.entering("BillingLineDetail", "populateAttributes");
		setRatingBasis(billingLineDetailVO.getRatingBasis());
		log.exiting("BillingLineDetail", "populateAttributes");
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineChargeVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	private void saveBillingLineCharge(BillingLineChargeVO billingLineChargeVO) throws CreateException, SystemException{
		log.entering("BillingLineDetail", "saveBillingLineCharge");
	//	BillingLineCharge bllingLineCharge=new BillingLineCharge(billingLineChargeVO,);
		if (this.getBillingLineCharges() == null) {
			this.setBillingLineCharges(new HashSet<BillingLineCharge>());
		}
		log.exiting("BillingLineDetail", "saveBillingLineCharge");
		//this.getBillingLineCharges().add(bllingLineCharge);
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineDetailPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static BillingLineDetail find(BillingLineDetailPK billingLineDetailPK)
			throws SystemException, FinderException {
		Log findlog = LogFactory.getLogger("mailtracking mra");
		findlog.entering("BillingLineCharge", "find");
		EntityManager em = PersistenceController.getEntityManager();
		try {
			return em.find(BillingLineDetail.class, billingLineDetailPK);
		} catch (FinderException ex) {
			ex.getErrorCode();
			throw ex;
		}
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineDetailVO
	 * @throws RemoveException
	 * @throws SystemException
	 * @throws CreateException
	 */
	public void update(BillingLineDetailVO billingLineDetailVO) throws RemoveException, SystemException, CreateException {
		log.entering("BillingLineDetail", "updat e");
		Set<BillingLineCharge> billingLineCharges = new HashSet<BillingLineCharge>();
		setRatingBasis(billingLineDetailVO.getRatingBasis());
		BillingLineCharge billingLineCharge=null;
		removeChildren();
		this.setBillingLineCharges(new HashSet<BillingLineCharge>());
		if (billingLineDetailVO.getBillingLineCharges() != null
				&& billingLineDetailVO.getBillingLineCharges().size() > 0) {
			for (BillingLineChargeVO billingLineChargeVO : billingLineDetailVO
					.getBillingLineCharges()) {
				billingLineChargeVO.setCompanycode(billingLineDetailVO.getCompanycode());
				billingLineChargeVO.setBillingMatrixID(billingLineDetailVO
				.getBillingMatrixID());
				billingLineChargeVO.setBillingLineSequenceNumber(billingLineDetailVO
				.getBillingLineSequenceNumber());
				billingLineChargeVO.setChargeType(billingLineDetailVO.getChargeType());
				//billingLineCharge=new BillingLineCharge(billingLineChargeVO);
			//	billingLineCharges.add(billingLineCharge);
			}
		}
		this.setBillingLineCharges(billingLineCharges);
		log.exiting("BillingLineDetail", "update");
	}
	/**
	 * 
	 * @author A-5255
	 * @throws SystemException
	 */
	public void remove() throws SystemException {

		try {
			PersistenceController.getEntityManager().remove(this);
			removeChildren();
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		} catch (OptimisticConcurrencyException optimisticConcurrencyException) {
			throw new SystemException(
					optimisticConcurrencyException.getErrorCode());
		}
	}
	/**
	 * 
	 * @author A-5255
	 * @throws SystemException
	 * @throws RemoveException
	 */
	private void removeChildren() throws SystemException, RemoveException {
		log.entering("BillingLineDetail", "removeChildren");
		if (getBillingLineCharges() != null) {
			for (BillingLineCharge billingLineCharge : this.getBillingLineCharges()) {
				billingLineCharge.remove();
			}
		}
		log.exiting("BillingLineDetail", "removeChildren");
	}
	/**
	 * @return the billingLineDetailPK
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="billingMatrixID", column=@Column(name="BLGMTXCOD")),
		@AttributeOverride(name="billingLineSequenceNumber", column=@Column(name="BLGLINSEQNUM")),
		@AttributeOverride(name="chargeType", column=@Column(name="CHGTYP"))
		})
	public BillingLineDetailPK getBillingLineDetailPK() {
		return billingLineDetailPK;
	}
	/**
	 * @param billingLineDetailPK the billingLineDetailPK to set
	 */
	public void setBillingLineDetailPK(BillingLineDetailPK billingLineDetailPK) {
		this.billingLineDetailPK = billingLineDetailPK;
	}
	/**
	 * @return the ratingBasis
	 */
	@Audit(name="Rating Basis")
	@Column(name="RATBSS")
	public String getRatingBasis() {
		return ratingBasis;
	}
	/**
	 * @param ratingBasis the ratingBasis to set
	 */
	public void setRatingBasis(String ratingBasis) {
		this.ratingBasis = ratingBasis;
	}
	/**
	 * @return the billingLineCharges
	 */
	@Audit(name="BillingLineCharge")
	@OneToMany
    @JoinColumns({
    	@JoinColumn(name = "CMPCOD",referencedColumnName = "CMPCOD",insertable = false, updatable = false),
    	@JoinColumn(name = "BLGMTXCOD", referencedColumnName = "BLGMTXCOD",insertable = false, updatable = false),
        @JoinColumn(name = "BLGLINSEQNUM", referencedColumnName = "BLGLINSEQNUM",insertable = false, updatable = false),
        @JoinColumn(name = "CHGTYP", referencedColumnName = "CHGTYP",insertable = false, updatable = false)})
	public Set<BillingLineCharge> getBillingLineCharges() {
		return billingLineCharges;
	}
	/**
	 * @param billingLineCharges the billingLineCharges to set
	 */
	public void setBillingLineCharges(Set<BillingLineCharge> billingLineCharges) {
		this.billingLineCharges = billingLineCharges;
	}
	
	
}
