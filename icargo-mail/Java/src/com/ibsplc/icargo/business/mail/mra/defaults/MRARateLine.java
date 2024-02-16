/*
 * MRARateLine.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 *
 */

@Entity
@Table(name = "MALMRARATLIN")
@Staleable
public class MRARateLine {

	private MRARateLinePK rateLinePK;

	private String ratelineStatus;

	private String origin;

	private String destination;

	private double iataKilometre;

	private double mailKilometre;

	private double rateInXDRForCategoryRefOne;

	private double rateInXDRForCategoryRefTwo;

	private double rateInXDRForCategoryRefThree;

	private double rateInXDRForCategoryRefFour;

	private double rateInXDRForCategoryRefFive;

	private double rateInBaseCurrForCategoryRefOne;

	private double rateInBaseCurrForCategoryRefTwo;

	private double rateInBaseCurrForCategoryRefThree;

	private double rateInBaseCurrForCategoryRefFour;

	private double rateInBaseCurrForCategoryRefFive;

	private Calendar validityStartDate;

	private Calendar validityEndDate;

	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;
	
	private String orgDstLevel;

	private static final String MRA_DEFAULTS = "mail.mra.defaults";

	public MRARateLine() {
	}

	public MRARateLine(RateLineVO rateLineVO) throws SystemException {
		MRARateLinePK rateLinepk = new MRARateLinePK(rateLineVO
				.getCompanyCode(), rateLineVO.getRateCardID(), rateLineVO
				.getRatelineSequenceNumber());
		this.setRateLinePK(rateLinepk);
		populateAttributes(rateLineVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode());
		}

	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUPdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the destination.
	 */

	@Column(name = "DSTCOD")
	public String getDestination() {
		return destination;
	}

	/**
	 * @return Returns the iataKilometre.
	 */

	@Column(name = "IATKLM")
	public double getIataKilometre() {
		return iataKilometre;
	}

	/**
	 * @return Returns the mailKilometre.
	 */

	@Column(name = "MALKLM")
	public double getMailKilometre() {
		return mailKilometre;
	}

	/**
	 * @return Returns the origin.
	 */

	@Column(name = "ORGCOD")
	public String getOrigin() {
		return origin;
	}

	/**
	 * @return Returns the rateInBaseCurrForCategoryRefFive.
	 */

	@Column(name = "CATFIVBASCURRAT")
	public double getRateInBaseCurrForCategoryRefFive() {
		return rateInBaseCurrForCategoryRefFive;
	}

	/**
	 * @return Returns the rateInBaseCurrForCategoryRefFour.
	 */

	@Column(name = "CATFORBASCURRAT")
	public double getRateInBaseCurrForCategoryRefFour() {
		return rateInBaseCurrForCategoryRefFour;
	}

	/**
	 * @return Returns the rateInBaseCurrForCategoryRefOne.
	 */

	@Column(name = "CATONEBASCURRAT")
	public double getRateInBaseCurrForCategoryRefOne() {
		return rateInBaseCurrForCategoryRefOne;
	}

	/**
	 * @return Returns the rateInBaseCurrForCategoryRefThree.
	 */

	@Column(name = "CATTHRBASCURRAT")
	public double getRateInBaseCurrForCategoryRefThree() {
		return rateInBaseCurrForCategoryRefThree;
	}

	/**
	 * @return Returns the rateInBaseCurrForCategoryRefTwo.
	 */

	@Column(name = "CATTWOBASCURRAT")
	public double getRateInBaseCurrForCategoryRefTwo() {
		return rateInBaseCurrForCategoryRefTwo;
	}

	

	/**
	 * @return Returns the rateLinePK.
	 */

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "rateCardID", column = @Column(name = "RATCRDCOD")),
			@AttributeOverride(name = "ratelineSequenceNumber", column = @Column(name = "RATLINSEQNUM")) })
	public MRARateLinePK getRateLinePK() {
		return rateLinePK;
	}

	/**
	 * @return Returns the ratelineStatus.
	 */

	@Column(name = "RATLINSTA")
	public String getRatelineStatus() {
		return ratelineStatus;
	}

	/**
	 * @return Returns the validityEndDate.
	 */

	@Column(name = "VLDENDDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getValidityEndDate() {
		return validityEndDate;
	}

	/**
	 * @return Returns the validityStartDate.
	 */

	@Column(name = "VLDSTRDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getValidityStartDate() {
		return validityStartDate;
	}

	
	/**
	 * @return the rateInXDRForCategoryRefOne
	 */
	@Column(name = "CATONEXDRCURRAT")
	public double getRateInXDRForCategoryRefOne() {
		return rateInXDRForCategoryRefOne;
	}

	/**
	 * @param rateInXDRForCategoryRefOne the rateInXDRForCategoryRefOne to set
	 */
	public void setRateInXDRForCategoryRefOne(double rateInXDRForCategoryRefOne) {
		this.rateInXDRForCategoryRefOne = rateInXDRForCategoryRefOne;
	}

	/**
	 * @return the rateInXDRForCategoryRefTwo
	 */
	@Column(name = "CATTWOXDRCURRAT")
	public double getRateInXDRForCategoryRefTwo() {
		return rateInXDRForCategoryRefTwo;
	}

	/**
	 * @param rateInXDRForCategoryRefTwo the rateInXDRForCategoryRefTwo to set
	 */
	public void setRateInXDRForCategoryRefTwo(double rateInXDRForCategoryRefTwo) {
		this.rateInXDRForCategoryRefTwo = rateInXDRForCategoryRefTwo;
	}

	/**
	 * @return the rateInXDRForCategoryRefThree
	 */
	@Column(name = "CATTHRXDRCURRAT")
	public double getRateInXDRForCategoryRefThree() {
		return rateInXDRForCategoryRefThree;
	}

	/**
	 * @param rateInXDRForCategoryRefThree the rateInXDRForCategoryRefThree to set
	 */
	public void setRateInXDRForCategoryRefThree(double rateInXDRForCategoryRefThree) {
		this.rateInXDRForCategoryRefThree = rateInXDRForCategoryRefThree;
	}

	/**
	 * @return the rateInXDRForCategoryRefFour
	 */
	@Column(name = "CATFORXDRCURRAT")
	public double getRateInXDRForCategoryRefFour() {
		return rateInXDRForCategoryRefFour;
	}

	/**
	 * @param rateInXDRForCategoryRefFour the rateInXDRForCategoryRefFour to set
	 */
	public void setRateInXDRForCategoryRefFour(double rateInXDRForCategoryRefFour) {
		this.rateInXDRForCategoryRefFour = rateInXDRForCategoryRefFour;
	}

	/**
	 * @return the rateInXDRForCategoryRefFive
	 */
	@Column(name = "CATFIVXDRCURRAT")
	public double getRateInXDRForCategoryRefFive() {
		return rateInXDRForCategoryRefFive;
	}

	/**
	 * @param rateInXDRForCategoryRefFive the rateInXDRForCategoryRefFive to set
	 */
	public void setRateInXDRForCategoryRefFive(double rateInXDRForCategoryRefFive) {
		this.rateInXDRForCategoryRefFive = rateInXDRForCategoryRefFive;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @param iataKilometre The iataKilometre to set.
	 */
	public void setIataKilometre(double iataKilometre) {
		this.iataKilometre = iataKilometre;
	}

	/**
	 * @param mailKilometre The mailKilometre to set.
	 */
	public void setMailKilometre(double mailKilometre) {
		this.mailKilometre = mailKilometre;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @param rateInBaseCurrForCategoryRefFive The rateInBaseCurrForCategoryRefFive to set.
	 */
	public void setRateInBaseCurrForCategoryRefFive(
			double rateInBaseCurrForCategoryRefFive) {
		this.rateInBaseCurrForCategoryRefFive = rateInBaseCurrForCategoryRefFive;
	}

	/**
	 * @param rateInBaseCurrForCategoryRefFour The rateInBaseCurrForCategoryRefFour to set.
	 */
	public void setRateInBaseCurrForCategoryRefFour(
			double rateInBaseCurrForCategoryRefFour) {
		this.rateInBaseCurrForCategoryRefFour = rateInBaseCurrForCategoryRefFour;
	}

	/**
	 * @param rateInBaseCurrForCategoryRefOne The rateInBaseCurrForCategoryRefOne to set.
	 */
	public void setRateInBaseCurrForCategoryRefOne(
			double rateInBaseCurrForCategoryRefOne) {
		this.rateInBaseCurrForCategoryRefOne = rateInBaseCurrForCategoryRefOne;
	}

	/**
	 * @param rateInBaseCurrForCategoryRefThree The rateInBaseCurrForCategoryRefThree to set.
	 */
	public void setRateInBaseCurrForCategoryRefThree(
			double rateInBaseCurrForCategoryRefThree) {
		this.rateInBaseCurrForCategoryRefThree = rateInBaseCurrForCategoryRefThree;
	}

	/**
	 * @param rateInBaseCurrForCategoryRefTwo The rateInBaseCurrForCategoryRefTwo to set.
	 */
	public void setRateInBaseCurrForCategoryRefTwo(
			double rateInBaseCurrForCategoryRefTwo) {
		this.rateInBaseCurrForCategoryRefTwo = rateInBaseCurrForCategoryRefTwo;
	}

	/**
	 * @param rateLinePK The rateLinePK to set.
	 */
	public void setRateLinePK(MRARateLinePK rateLinePK) {
		this.rateLinePK = rateLinePK;
	}

	/**
	 * @param ratelineStatus The ratelineStatus to set.
	 */
	public void setRatelineStatus(String ratelineStatus) {
		this.ratelineStatus = ratelineStatus;
	}

	/**
	 * @param validityEndDate The validityEndDate to set.
	 */
	public void setValidityEndDate(Calendar validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	/**
	 * @param validityStartDate The validityStartDate to set.
	 */
	public void setValidityStartDate(Calendar validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	/**
	 * 	Getter for orgDstLevel 
	 *	Added by : A-5219 on 15-Oct-2020
	 * 	Used for :
	 */
	@Column(name = "ORGDSTLVL")
	public String getOrgDstLevel() {
		return orgDstLevel;
	}

	/**
	 *  @param orgDstLevel the orgDstLevel to set
	 * 	Setter for orgDstLevel 
	 *	Added by : A-5219 on 15-Oct-2020
	 * 	Used for :
	 */
	public void setOrgDstLevel(String orgDstLevel) {
		this.orgDstLevel = orgDstLevel;
	}

	/**
	 * @param companyCode
	 * @param rateCardID
	 * @param companyCode
	 * @param ratelineSequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MRARateLine find(String companyCode, String rateCardID,
			int ratelineSequenceNumber) throws SystemException, FinderException {

		MRARateLinePK rateLinepk = new MRARateLinePK(companyCode, rateCardID,
				ratelineSequenceNumber);
		MRARateLine rateLine = null;
		try {
			rateLine = PersistenceController.getEntityManager().find(
					MRARateLine.class, rateLinepk);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
		return rateLine;
	}

	/**
	 * Returns the ratelines based on the filter criteria
	 *
	 * @param rateLineFilterVO
	 * @return Page<RateLineVO>
	 * @throws SystemException
	 *
	 */
	public static Page findRateLineDetails(RateLineFilterVO rateLineFilterVO)
			throws SystemException {
		return null;
	}

	/**
	 *
	 * @param rateLineVOs
	 * @return Collection <RateLineErrorVO>
	 * @throws SystemException
	 */
	public static Collection<RateLineErrorVO> validateRatelines(
			Collection<RateLineVO> rateLineVOs) throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MRA_DEFAULTS))
					.findOverLappingRateLines(rateLineVOs);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}

	}

	/**
	 * @param rateLineVO
	 * @return
	 * @throws SystemException
	 */
	public void update(RateLineVO rateLineVO) throws SystemException {

		populateAttributes(rateLineVO);
	}

	/**
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	private void populateAttributes(RateLineVO rateLineVO) {
		this.setRatelineStatus(rateLineVO.getRatelineStatus());
		this.setOrigin(rateLineVO.getOrigin());
		this.setDestination(rateLineVO.getDestination());
		this.setIataKilometre(rateLineVO.getIataKilometre());
		this.setMailKilometre(rateLineVO.getMailKilometre());
		this.setRateInXDRForCategoryRefOne(rateLineVO
				.getRateInSDRForCategoryRefOne());
		this.setRateInXDRForCategoryRefTwo(rateLineVO
				.getRateInSDRForCategoryRefTwo());
		this.setRateInXDRForCategoryRefThree(rateLineVO
				.getRateInSDRForCategoryRefThree());
		this.setRateInXDRForCategoryRefFour(rateLineVO
				.getRateInSDRForCategoryRefFour());
		this.setRateInXDRForCategoryRefFive(rateLineVO
				.getRateInSDRForCategoryRefFive());
		this.setRateInBaseCurrForCategoryRefOne(rateLineVO
				.getRateInBaseCurrForCategoryRefOne());
		this.setRateInBaseCurrForCategoryRefTwo(rateLineVO
				.getRateInBaseCurrForCategoryRefTwo());
		this.setRateInBaseCurrForCategoryRefThree(rateLineVO
				.getRateInBaseCurrForCategoryRefThree());
		this.setRateInBaseCurrForCategoryRefFour(rateLineVO
				.getRateInBaseCurrForCategoryRefFour());
		this.setRateInBaseCurrForCategoryRefFive(rateLineVO
				.getRateInBaseCurrForCategoryRefFive());
		
		this.setValidityStartDate(rateLineVO.getValidityStartDate());
		this.setValidityEndDate(rateLineVO.getValidityEndDate());
		this.setLastUpdatedUser(rateLineVO.getLastUpdateUser());
		this.setLastUpdatedTime(rateLineVO.getLastUpdateTime());
		if(rateLineVO.getOrgDstLevel() != null &&
				("A".equals(rateLineVO.getOrgDstLevel())
						|| rateLineVO.getOrgDstLevel().startsWith("A"))){
			this.setOrgDstLevel("A");
		}else{
			this.setOrgDstLevel("C");
		}
	}
}
