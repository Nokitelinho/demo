/*
 * PostalAdministrationDetails.java Created on June 27, 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-3109
 * 
 */

@Entity
@Table(name = "MALPOADTL")
@Staleable
public class PostalAdministrationDetails {

	private static final String MODULE_NAME = "mail.operations";

	private PostalAdministrationDetailsPK postalAdministrationDetailsPK;

	/**
	 * billingSource
	 */
	private String billingSource;

	/**
	 * billingFrequency
	 */
	private String billingFrequency;



	/**
	 * settlementCurrencyCode
	 */
	private String settlementCurrencyCode;

	/**
	 * validFrom
	 */
	private Calendar validFrom;

	/**
	 * validTo
	 */
	private Calendar validTo;

	/**
	 * parameterType
	 */
	private String parameterType;

	/**
	 * parameterValue
	 */
	private String parameterValue;

	/**
	 * partyIdentifier
	 */
	private String partyIdentifier;

	/**
	 * detailedRemarks
	 */
	private String detailedRemarks;

	public PostalAdministrationDetails() {

	}

	@Column(name = "BLGSRC")
	public String getBillingSource() {
		return billingSource;
	}

	public void setBillingSource(String billingSource) {
		this.billingSource = billingSource;
	}

	@Column(name = "BLGFRQ")
	public String getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}


	@Column(name = "STLCUR")
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	@Column(name = "VLDFRM")
	@Temporal(TemporalType.DATE)
	public Calendar getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name = "VLDTOO")
	@Temporal(TemporalType.DATE)
	public Calendar getValidTo() {
		return validTo;
	}

	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
	}

	@Column(name = "PARTYP")
	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	@Column(name = "PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Column(name = "DTLRMK")
	public String getDetailedRemarks() {
		return detailedRemarks;
	}

	public void setDetailedRemarks(String detailedRemarks) {
		this.detailedRemarks = detailedRemarks;
	}

	@Column(name = "PTYIDR")
	public String getPartyIdentifier() {
		return partyIdentifier;
	}

	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}

	/**
	 * @return Returns the postalAdministrationDetailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "poacod", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "parcod", column = @Column(name = "PARCOD")),
			@AttributeOverride(name = "sernum", column = @Column(name = "SERNUM")) })
	public PostalAdministrationDetailsPK getPostalAdministrationDetailsPK() {
		return postalAdministrationDetailsPK;
	}

	/**
	 * @param postalAdministrationDetailsPK
	 *            The postalAdministrationDetailsPK to set.
	 */
	public void setPostalAdministrationDetailsPK(
			PostalAdministrationDetailsPK postalAdministrationDetailsPK) {
		this.postalAdministrationDetailsPK = postalAdministrationDetailsPK;
	}

	public PostalAdministrationDetails(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO)
			throws SystemException {

		populatePK(postalAdministrationDetailsVO);
		populateAttributes(postalAdministrationDetailsVO);
		try {

			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}

	}

	/**
	 * This method is used to find the Instance of the Entity
	 * 
	 * @param companyCode
	 * @param
	 * @param
	 * @param
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static PostalAdministrationDetails find(String companyCode,
			String poaCode, String parcode, String sernum)
			throws SystemException, FinderException {
		PostalAdministrationDetailsPK postalAdminDetailsPK = new PostalAdministrationDetailsPK();
		postalAdminDetailsPK.setCompanyCode(companyCode);
		postalAdminDetailsPK.setPoacod(poaCode);
		postalAdminDetailsPK.setParcod(parcode);
		postalAdminDetailsPK.setSernum(sernum);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(PostalAdministrationDetails.class, postalAdminDetailsPK);
	}

	/**
	 * 
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException {

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw ex;
		}

	}

	/**
	 * 
	 * @param mailEventVO
	 */
	public void update(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		populateAttributes(postalAdministrationDetailsVO);
	}

	/**
	 * 
	 * @param postalAdministrationVO
	 */
	private void populateAttributes(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO) {

		this.billingSource = postalAdministrationDetailsVO.getBillingSource();
		this.billingFrequency = postalAdministrationDetailsVO
				.getBillingFrequency();
		this.settlementCurrencyCode = postalAdministrationDetailsVO
				.getSettlementCurrencyCode();
		if(postalAdministrationDetailsVO.getValidFrom()!=null){
		this.validFrom = postalAdministrationDetailsVO.getValidFrom()
				.toCalendar();
		}
		if(postalAdministrationDetailsVO.getValidTo()!=null){
		this.validTo = postalAdministrationDetailsVO.getValidTo().toCalendar();
		}
		this.parameterType = postalAdministrationDetailsVO.getParameterType();
		this.parameterValue = postalAdministrationDetailsVO.getParameterValue();
		this.detailedRemarks = postalAdministrationDetailsVO
				.getDetailedRemarks();
		this.partyIdentifier = postalAdministrationDetailsVO
				.getPartyIdentifier();
	}

	/**
	 * 
	 * @param postalAdministrationVO
	 */
	private void populatePK(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO) {

		PostalAdministrationDetailsPK postalDetailsPK = new PostalAdministrationDetailsPK();
		postalDetailsPK.setCompanyCode(postalAdministrationDetailsVO
				.getCompanyCode());
		postalDetailsPK.setPoacod(postalAdministrationDetailsVO.getPoaCode());
		postalDetailsPK.setParcod(postalAdministrationDetailsVO.getParCode());
		postalDetailsPK.setSernum(postalAdministrationDetailsVO.getSernum());
		this.setPostalAdministrationDetailsPK(postalDetailsPK);
	}

	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	
	/**
	 * @author A-3251
	 * @param postalAdministrationDetailsVO
	 * @throws SystemException
	 */
   public static PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
   throws SystemException{

		try {
			return constructDAO().validatePoaDetails(postalAdministrationDetailsVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
   }

}
