/*
 * DamagedDSNDetail.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *   
 * 
 * @author A-5991
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 27, 2016 A-5991 First Draft
 */
@Entity
@Table(name = "MALDSNDMGDTL")
@Staleable
public class DamagedDSNDetail {

	private DamagedDSNDetailPK damageDetailPK;

	private int damagedBags;

	private double damagedWeight;

	private int returnedBags;

	private double returnedWeight;

	private Calendar damageDate;

	private String userCode;

	private String damageDescription;

	private String remarks;

	private String returnedPaCode;
	
	private String mailClass;

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	
	/**
	 * @return Returns the damageDate.
	 */
	@Column(name = "DMGDAT")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDamageDate() {
		return damageDate;
	}

	/**
	 * @param damageDate
	 *            The damageDate to set.
	 */
	public void setDamageDate(Calendar damageDate) {
		this.damageDate = damageDate;
	}

	/**
	 * @return Returns the damagedBags.
	 */
	@Column(name = "DMGBAG")
	public int getDamagedBags() {
		return damagedBags;
	}

	/**
	 * @param damagedBags
	 *            The damagedBags to set.
	 */
	public void setDamagedBags(int damagedBags) {
		this.damagedBags = damagedBags;
	}

	/**
	 * @return Returns the damageDescription.
	 */
	@Column(name = "DMGDES")
	public String getDamageDescription() {
		return damageDescription;
	}

	/**
	 * @param damageDescription
	 *            The damageDescription to set.
	 */
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}

	/**
	 * @return Returns the damagedWeight.
	 */
	@Column(name = "DMGWGT")
	public double getDamagedWeight() {
		return damagedWeight;
	}

	/**
	 * @param damagedWeight
	 *            The damagedWeight to set.
	 */
	public void setDamagedWeight(double damagedWeight) {
		this.damagedWeight = damagedWeight;
	}

	/**
	 * @return Returns the damageUser.
	 */
	@Column(name = "USRCOD")
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param damageUser
	 *            The damageUser to set.
	 */
	public void setUserCode(String damageUser) {
		this.userCode = damageUser;
	}

	/**
	 * @return Returns the returnedBags.
	 */
	@Column(name = "RTNBAG")
	public int getReturnedBags() {
		return returnedBags;
	}

	/**
	 * @param returnedBags
	 *            The returnedBags to set.
	 */
	public void setReturnedBags(int returnedBags) {
		this.returnedBags = returnedBags;
	}

	/**
	 * @return Returns the returnedWeight.
	 */
	@Column(name = "RTNWGT")
	public double getReturnedWeight() {
		return returnedWeight;
	}

	/**
	 * @param returnedWeight
	 *            The returnedWeight to set.
	 */
	public void setReturnedWeight(double returnedWeight) {
		this.returnedWeight = returnedWeight;
	}

	/**
	 * @return Returns the damageDetailPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "dsn", column = @Column(name = "DSN")),
			@AttributeOverride(name = "originExchangeOffice", column = @Column(name = "ORGEXGOFC")),
			@AttributeOverride(name = "destinationExchangeOffice", column = @Column(name = "DSTEXGOFC")),
			@AttributeOverride(name = "mailCategoryCode", column = @Column(name = "MALCTGCOD")),
			@AttributeOverride(name = "mailSubclass", column = @Column(name = "MALSUBCLS")),
			@AttributeOverride(name = "year", column = @Column(name = "YER")),
			@AttributeOverride(name = "consignmentNumber", column = @Column(name = "CSGDOCNUM")),
			@AttributeOverride(name = "consignmentSequenceNumber", column = @Column(name = "CSGSEQNUM")),
			@AttributeOverride(name = "paCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "damageSequenceNumber", column = @Column(name = "DMGSEQNUM")),
			@AttributeOverride(name = "damageCode", column = @Column(name = "DMGCOD")) })
	public DamagedDSNDetailPK getDamageDetailPK() {
		return damageDetailPK;
	}

	/**
	 * @param damageDetailPK
	 *            The damageDetailPK to set.
	 */
	public void setDamageDetailPK(DamagedDSNDetailPK damageDetailPK) {
		this.damageDetailPK = damageDetailPK;
	}

	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return Returns the returnedPaCode.
	 */
	@Column(name = "RTNPOACOD")
	public String getReturnedPaCode() {
		return returnedPaCode;
	}

	/**
	 * @param returnedPaCode
	 *            The returnedPaCode to set.
	 */
	public void setReturnedPaCode(String returnedPaCode) {
		this.returnedPaCode = returnedPaCode;
	}

	/**
	 * @return Returns the mailClass.
	 */
	@Column(name="MALCLS")
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	
	
	
	public DamagedDSNDetail() {

	}

	/**
	 * @author A-5991
	 * @param damagedDSNPK
	 * @param damageDetailVO
	 * @throws SystemException
	 */
	public DamagedDSNDetail(DamagedDSNPK damagedDSNPK,
			DamagedDSNDetailVO damageDetailVO) throws SystemException {
		populatePK(damagedDSNPK, damageDetailVO);
		populateAttributes(damageDetailVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/**
	 * A-5991
	 * 
	 * @param damagedDSNPK
	 * @param damageDetailVO
	 */
	private void populatePK(DamagedDSNPK damagedDSNPK,
			DamagedDSNDetailVO damageDetailVO) {
		damageDetailPK = new DamagedDSNDetailPK();
		damageDetailPK.setCompanyCode(   damagedDSNPK.getCompanyCode());
		damageDetailPK.setDsn(   damagedDSNPK.getDsn());
		damageDetailPK.setOriginExchangeOffice(   damagedDSNPK.getOriginExchangeOffice());
		damageDetailPK.setDestinationExchangeOffice(   damagedDSNPK.getDestinationExchangeOffice());
		damageDetailPK.setMailCategoryCode (  damagedDSNPK.getMailCategoryCode());
		damageDetailPK.setMailSubclass(   damagedDSNPK.getMailSubclass());
		damageDetailPK.setYear(   damagedDSNPK.getYear());
		damageDetailPK.setConsignmentNumber(   damagedDSNPK.getConsignmentNumber());
		damageDetailPK.setConsignmentSequenceNumber(   damagedDSNPK.getConsignmentSequenceNumber());
		damageDetailPK.setPaCode(   damagedDSNPK.getPaCode());
		damageDetailPK.setDamageSequenceNumber(   damagedDSNPK.getDamageSequenceNumber());
		damageDetailPK.setAirportCode(   damagedDSNPK.getAirportCode());
		damageDetailPK.setDamageCode(   damageDetailVO.getDamageCode());
	}

	/**
	 * A-5991
	 * 
	 * @param damageDetailVO
	 */
	private void populateAttributes(DamagedDSNDetailVO damageDetailVO) {
		setDamageDescription(damageDetailVO.getDamageDescription());
		setUserCode(damageDetailVO.getDamageUser());
		setDamageDate(damageDetailVO.getDamageDate().toCalendar());
		setDamagedBags(damageDetailVO.getLatestDamagedBags());
		//setDamagedWeight(damageDetailVO.getLatestDamagedWeight());
		setDamagedWeight(damageDetailVO.getLatestDamagedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		setReturnedBags(damageDetailVO.getLatestReturnedBags());
		//setReturnedWeight(damageDetailVO.getLatestReturnedWeight());
		setReturnedWeight(damageDetailVO.getLatestReturnedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		setRemarks(damageDetailVO.getRemarks());
		setReturnedPaCode(damageDetailVO.getReturnedPaCode());
		setMailClass(damageDetailVO.getMailClass());
	}
	
	
	/**
	 * A-5991
	 * 
	 * @param detailPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static DamagedDSNDetail find(DamagedDSNDetailPK detailPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				DamagedDSNDetail.class, detailPK);
	}

	/**
	 * A-5991
	 * 
	 * @param dmgDetailVO
	 */
	public void update(DamagedDSNDetailVO dmgDetailVO) {
		log.entering("DamagedDSNDetail", "update");

		int currDmgBags = dmgDetailVO.getLatestDamagedBags()
		+ getDamagedBags();
		/*double currDmgWeight = dmgDetailVO.getLatestDamagedWeight()
				+ getDamagedWeight();*/
		double currDmgWeight = dmgDetailVO.getLatestDamagedWeight().getRoundedSystemValue()
				+ getDamagedWeight();//added by A-7371
		setDamagedBags(currDmgBags);
		setDamagedWeight(currDmgWeight);
		
		int currReturnBags = dmgDetailVO.getLatestReturnedBags()
				+ getReturnedBags();
		/*double currReturnWeight = dmgDetailVO.getLatestReturnedWeight()
				+ getReturnedWeight();*/
		double currReturnWeight = dmgDetailVO.getLatestReturnedWeight().getRoundedSystemValue()
				+ getReturnedWeight();
		setReturnedBags(currReturnBags);
		setReturnedWeight(currReturnWeight);
		
		setUserCode(dmgDetailVO.getDamageUser());
		setDamageDate(dmgDetailVO.getDamageDate().toCalendar());
		setRemarks(dmgDetailVO.getRemarks());
		setReturnedPaCode(dmgDetailVO.getReturnedPaCode());
		log.entering("DamagedDSNDetail", "update");
		
	}


}
