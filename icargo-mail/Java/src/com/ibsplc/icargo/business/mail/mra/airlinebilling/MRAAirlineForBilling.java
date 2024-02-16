/*

 * MRAAirlineForBilling.java Created on July 13, 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS Software Services (P) Ltd.

 * Use is subject to license terms.

 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import static com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingPersistenceConstants.MODULE_NAME;

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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.MRAAirlineForBillingPK;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Kiran S P Revision History
 * 
 * 
 * 
 *         Version Date Author Description
 * 
 * 
 * 
 *         0.1 Jun 20, 2006 Kiran S P Initial draft , Added method
 *         findInterlineInvoices
 * 
 * 
 * 
 *         0.2 Nov 27, 2006 Prem Kumar M Added methods for
 *         updateDocumentTransactionStatus,assignException
 * 
 *         0.3 Dec 28, 2006 Sarath G added method findExceptionDetailsForReport
 * 
 *         0.3 Jan 3, 2006 Sarath G added method findRejectionsForReport
 * 
 *         0.3 Jan 5, 2006 sarath G added method findRejectionReasonsForReport
 * 
 */

@Table(name = "MALMRAARLBLG")
@Entity
public class MRAAirlineForBilling {

	private Log log = LogFactory.getLogger("MRA:AIRLINEBILLING");

	private static final String NEW = "N";

	private MRAAirlineForBillingPK airlineForBillingPK;

	private String airlinecode;

	private Calendar creationDate;

	private String formThreeStatus;

	private String zoneIndicator;

	private String isFormTwoGenerated;

	private String generatedFormOneFlag;

	private String generatedInvoiceFlag;

	private String capturedInvoiceFlag;

	private String capturedFormOneFlag;

	private String isFormThreeCaptured;

	private String airlineNumber;

	/**
	 * @author A-3447 Added as part of INT_MRA19 starts
	 */

	private double outWardMisAmount;

	private double outWardTotalAmount;

	private double inwardMisAmount;

	private double inwardTotalAmount;

	private double inwardCreditAmount;

	private double inwardNetAmount;

	/**
	 * @author A-3447 Added as part of INT_MRA19 ends
	 */

	// private Set<InterlineInvoice> interlineInvoices;
	// private Set<FormOne> formOne;
	private static final String CLASS_NAME = "AirlineForBilling";

	// added by A-2554 , for optimistic locking
	private String lastUpdateUser;

	private Calendar lastUpdateTime;

	/**
	 * 
	 * @return Returns the airlineBillingStatus.
	 * 
	 */

	/*
	 * 
	 * @Column(name="ARLBLGSTA")
	 * 
	 * public String getAirlineBillingStatus() {
	 * 
	 * return airlineBillingStatus; }
	 */

	/**
	 * 
	 * @param airlineBillingStatus
	 *            The airlineBillingStatus to set.
	 * 
	 */

	/*
	 * public void setAirlineBillingStatus(String airlineBillingStatus) {
	 * 
	 * this.airlineBillingStatus = airlineBillingStatus; }
	 */

	/**
	 * 
	 * @return Returns the airlineNumber.
	 * 
	 */

	@Column(name = "ARLNUM")
	public String getAirlineNumber() {

		return airlineNumber;

	}

	/**
	 * 
	 * @param airlineNumber
	 *            The airlineNumber to set.
	 * 
	 */

	public void setAirlineNumber(String airlineNumber) {

		this.airlineNumber = airlineNumber;

	}

	/**
	 * 
	 * @return Returns the airlinecode.
	 * 
	 */

	@Column(name = "ARLCOD")
	public String getAirlinecode() {

		return airlinecode;

	}

	/**
	 * 
	 * @param airlinecode
	 *            The airlinecode to set.
	 * 
	 */

	public void setAirlinecode(String airlinecode) {

		this.airlinecode = airlinecode;

	}

	/**
	 * 
	 * @return Returns the formThreeStatus.
	 * 
	 */

	@Column(name = "FORTHRSTA")
	public String getFormThreeStatus() {

		return formThreeStatus;

	}

	/**
	 * 
	 * @param formThreeStatus
	 *            The formThreeStatus to set.
	 * 
	 */

	public void setFormThreeStatus(String formThreeStatus) {

		this.formThreeStatus = formThreeStatus;

	}

	/**
	 * 
	 * @return Returns the airlineForBillingPK.
	 * 
	 */

	@EmbeddedId
	@AttributeOverrides({

			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),

			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),

			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")) })
	public MRAAirlineForBillingPK getAirlineForBillingPK() {

		return airlineForBillingPK;

	}

	/**
	 * 
	 * @param airlineForBillingPK
	 *            The airlineForBillingPK to set.
	 * 
	 */

	public void setAirlineForBillingPK(
			MRAAirlineForBillingPK airlineForBillingPK) {

		this.airlineForBillingPK = airlineForBillingPK;

	}

	/**
	 * 
	 * @return Returns the creationDate.
	 * 
	 */

	@Column(name = "CREDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getCreationDate() {

		return creationDate;

	}

	/**
	 * 
	 * @param creationDate
	 *            The creationDate to set.
	 * 
	 */

	public void setCreationDate(Calendar creationDate) {

		this.creationDate = creationDate;

	}

	/**
	 * 
	 * @return Returns the isFormThreeCaptured.
	 * 
	 */

	@Column(name = "CAPFRMTHRFLG")
	public String getIsFormThreeCaptured() {

		return isFormThreeCaptured;

	}

	/**
	 * 
	 * @param isFormThreeCaptured
	 *            The isFormThreeCaptured to set.
	 * 
	 */

	public void setIsFormThreeCaptured(String isFormThreeCaptured) {

		this.isFormThreeCaptured = isFormThreeCaptured;

	}

	/**
	 * 
	 * @return Returns the isFormTwoGenerated.
	 * 
	 */

	@Column(name = "GENFRMTWOFLG")
	public String getIsFormTwoGenerated() {

		return isFormTwoGenerated;

	}

	/**
	 * 
	 * @param isFormTwoGenerated
	 *            The isFormTwoGenerated to set.
	 * 
	 */

	public void setIsFormTwoGenerated(String isFormTwoGenerated) {

		this.isFormTwoGenerated = isFormTwoGenerated;

	}

	/**
	 * 
	 * @return Returns the zoneIndicator.
	 * 
	 */

	@Column(name = "ZONIND")
	public String getZoneIndicator() {

		return zoneIndicator;

	}

	/**
	 * 
	 * @param zoneIndicator
	 *            The zoneIndicator to set.
	 * 
	 */

	public void setZoneIndicator(String zoneIndicator) {

		this.zoneIndicator = zoneIndicator;

	}

	/**
	 * 
	 * @return Returns the capturedInvoiceFlag.
	 * 
	 */
	@Audit(name = "capturedInvoiceFlag")
	@Column(name = "CAPINVFLG")
	public String getCapturedInvoiceFlag() {

		return capturedInvoiceFlag;

	}

	/**
	 * 
	 * @param capturedInvoiceFlag
	 *            The capturedInvoiceFlag to set.
	 * 
	 */

	public void setCapturedInvoiceFlag(String capturedInvoiceFlag) {

		this.capturedInvoiceFlag = capturedInvoiceFlag;

	}

	/**
	 * 
	 * @return Returns the capturedFormOneFlag.
	 * 
	 */

	@Column(name = "CAPFRMONEFLG")
	public String getCapturedFormOneFlag() {

		return capturedFormOneFlag;

	}

	/**
	 * 
	 * @param capturedFormOneFlag
	 *            The capturedFormOneFlag to set.
	 * 
	 */

	public void setCapturedFormOneFlag(String capturedFormOneFlag) {

		this.capturedFormOneFlag = capturedFormOneFlag;

	}

	/**
	 * 
	 * @return Returns the generatedFormOneFlag.
	 * 
	 */

	@Column(name = "GENFRMONEFLG")
	public String getGeneratedFormOneFlag() {

		return generatedFormOneFlag;

	}

	/**
	 * 
	 * @param generatedFormOneFlag
	 *            The generatedFormOneFlag to set.
	 * 
	 */

	public void setGeneratedFormOneFlag(String generatedFormOneFlag) {

		this.generatedFormOneFlag = generatedFormOneFlag;

	}

	/**
	 * 
	 * @return Returns the generatedInvoiceFlag.
	 * 
	 */

	@Column(name = "GENINVFLG")
	public String getGeneratedInvoiceFlag() {

		return generatedInvoiceFlag;

	}

	/**
	 * 
	 * @param generatedInvoiceFlag
	 *            The generatedInvoiceFlag to set.
	 * 
	 */

	public void setGeneratedInvoiceFlag(String generatedInvoiceFlag) {

		this.generatedInvoiceFlag = generatedInvoiceFlag;

	}

	/**
	 * 
	 * @throws SystemException
	 * 
	 */

	private static MRAAirlineBillingDAO constructMraAirlineBillingDAO()
			throws SystemException {

		MRAAirlineBillingDAO mraAirlineBillingDAO = null;

		try {

			EntityManager em = PersistenceController.getEntityManager();

			mraAirlineBillingDAO = MRAAirlineBillingDAO.class.cast

			(em.getQueryDAO(MODULE_NAME));

		} catch (PersistenceException e) {

			throw new SystemException(e.getMessage(), e);

		}

		return mraAirlineBillingDAO;

	}

	// added by A-2554,Tito for optimistic locking

	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 
	 * 
	 * 
	 */

	public MRAAirlineForBilling() {

	}

	public MRAAirlineForBilling(AirlineForBillingVO airlineForBillingVO)
			throws SystemException {

		populateAttributes(airlineForBillingVO);
		populatePK(airlineForBillingVO);
	}

	/**
	 * 
	 * @throws SystemException
	 * 
	 */

	public void remove() throws SystemException {

		try {

			PersistenceController.getEntityManager().remove(this);

		}

		catch (RemoveException e) {

			throw new SystemException(e.getMessage(), e);

		}

		catch (OptimisticConcurrencyException e) {

			throw new SystemException(e.getMessage(), e);

		}

		catch (SystemException e) {

			throw new SystemException(e.getMessage(), e);

		}

	}

	/**
	 * 
	 * @param companyCode
	 * 
	 * @param airlineIdentifier
	 * 
	 * @param clearancePeriod
	 * 
	 * @return AirlineForBilling
	 * 
	 * @throws SystemException
	 * 
	 * @throws FinderException
	 * 
	 */

	public static MRAAirlineForBilling find(String companyCode,
			int airlineIdentifier,

			String clearancePeriod) throws SystemException, FinderException {

		MRAAirlineForBilling airlineForBilling = null;

		MRAAirlineForBillingPK constructedPK = new MRAAirlineForBillingPK(
				companyCode, airlineIdentifier,

				clearancePeriod);


		return PersistenceController.getEntityManager().find(MRAAirlineForBilling.class, constructedPK);

	}

	/**
	 * 
	 * @author A-3434
	 * 
	 * @param formOneFilterVo
	 * 
	 * @return Page<FormOneVO>
	 * 
	 * @throws SystemException
	 * 
	 */

	public static Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)

	throws SystemException {

		MRAAirlineBillingDAO mraAirlineBillingDAO = constructMraAirlineBillingDAO();

		return mraAirlineBillingDAO.findFormOnes(formOneFilterVo);

	}

	/**
	 * 
	 * 
	 * 
	 * @param airlineForBillingVO
	 * @throws SystemException
	 * 
	 */

	public void populatePK(AirlineForBillingVO airlineForBillingVO)
			throws SystemException {

		Log log = LogFactory.getLogger("MRA AIRLINEFORBILLING");

		log.entering("AirlineForBilling", "populatePK");

		MRAAirlineForBillingPK airlineForBillingPKToPersist = new MRAAirlineForBillingPK(

		airlineForBillingVO.getCompanyCode(),
				airlineForBillingVO.getAirlineIdentifier(),
				airlineForBillingVO.getClearancePeriod());

		this.setAirlineForBillingPK(airlineForBillingPKToPersist);

		log.log(Log.INFO, "populateAttributes set");
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode());
		}

		log.exiting("AirlineForBilling", "populatePK");

	}

	/**
	 * 
	 * 
	 * 
	 * @param airlineForBillingVO
	 * 
	 */

	public void populateAttributes(AirlineForBillingVO airlineForBillingVO) {

		Log log = LogFactory.getLogger("MRA AIRLINEFORBILLING");

		log.entering("AirlineForBilling", "populateAttributes");
		log.log(Log.INFO, "airlineForBillingVO----inside populate",
				airlineForBillingVO);
		setAirlinecode(airlineForBillingVO.getAirlineCode());

		setCreationDate(new LocalDate(LocalDate.NO_STATION,

		Location.NONE, Calendar.getInstance(), true));

		setLastUpdateUser(airlineForBillingVO.getLastUpdateUser());

		/**
		 * @author A-3447
		 */
		if (airlineForBillingVO.getOutWardTotalAmount() > 0) {
			this.setOutWardTotalAmount(airlineForBillingVO
					.getOutWardTotalAmount());
		}
		if (airlineForBillingVO.getInwardMisAmount() > 0) {
			this.setInwardMisAmount(airlineForBillingVO.getInwardMisAmount());
		}
		if (airlineForBillingVO.getOutWardMisAmount() > 0) {
			this.setOutWardMisAmount(airlineForBillingVO.getOutWardMisAmount());
		}
		if (airlineForBillingVO.getInwardTotalAmount() > 0) {
			this.setInwardTotalAmount(airlineForBillingVO
					.getInwardTotalAmount());
		}
		if (airlineForBillingVO.getInwardCreditAmount() > 0) {
			this.setInwardCreditAmount(airlineForBillingVO
					.getInwardCreditAmount());
		}
		if (airlineForBillingVO.getInwardNetAmount() > 0) {
			this.setInwardNetAmount(airlineForBillingVO.getInwardNetAmount());
		}
		/**
		 * @author A-3447
		 */

		// setCreationDate(new
		// LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		// setAirlineBillingStatus(airlineForBillingVO.getStatus());
		setAirlineNumber(airlineForBillingVO.getAirlineNumber());

		setFormThreeStatus(NEW);

		if (airlineForBillingVO.isFormTwoGenerated()) {

			setIsFormTwoGenerated(AirlineForBillingVO.FLAG_YES);

		} else {

			setIsFormTwoGenerated(AirlineForBillingVO.FLAG_NO);

		}

		if (airlineForBillingVO.isFormThreeCaptured()) {

			setIsFormThreeCaptured(AirlineForBillingVO.FLAG_YES);

		} else {

			setIsFormThreeCaptured(AirlineForBillingVO.FLAG_NO);

		}

		if (airlineForBillingVO.isCapturedFormOneFlag()) {

			setCapturedFormOneFlag(AirlineForBillingVO.FLAG_YES);

		} else {

			setCapturedFormOneFlag(AirlineForBillingVO.FLAG_NO);

		}

		if (airlineForBillingVO.isCapturedInvoiceFlag()) {

			setCapturedInvoiceFlag(AirlineForBillingVO.FLAG_YES);

		} else {

			setCapturedInvoiceFlag(AirlineForBillingVO.FLAG_NO);

		}

		if (airlineForBillingVO.isGeneratedFormOneFlag()) {

			setGeneratedFormOneFlag(AirlineForBillingVO.FLAG_YES);

		} else {

			setGeneratedFormOneFlag(AirlineForBillingVO.FLAG_NO);

		}

		if (airlineForBillingVO.isGeneratedInvoiceFlag()) {

			setGeneratedInvoiceFlag(AirlineForBillingVO.FLAG_YES);

		} else {

			setGeneratedInvoiceFlag(AirlineForBillingVO.FLAG_NO);

		}

		log.exiting("AirlineForBilling", "populateAttributes");
	}
	/**
	 * 
	 * @author A-3434
	 * 
	 * @param interlineFilterVo
	 * 
	 * @param Collection
	 *            <AirlineForBillingVO>
	 * 
	 * @throws SystemException
	 * 
	 * @return
	 * 
	 */
	public static Collection<AirlineForBillingVO> findAirlineDetails(
			InterlineFilterVO interlineFilterVo)
	throws SystemException {
		Collection<AirlineForBillingVO> airlineForBillingVOs = null;
		try {
			MRAAirlineBillingDAO mraAirlineBillingDAO = constructMraAirlineBillingDAO();
			airlineForBillingVOs = constructMraAirlineBillingDAO()
					.findAirlineDetails(interlineFilterVo);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		return airlineForBillingVOs;
	}
	/**
	 * 
	 * @author a-3456
	 * 
	 * @param interlineFilterVo
	 * 
	 * @return
	 * 
	 * @throws SystemException
	 * 
	 */
	public static FormOneVO findFormOneDetails(
			InterlineFilterVO interlineFilterVo)
	throws SystemException {
		Log log = LogFactory.getLogger(CLASS_NAME);
		log.entering("AirlineBilling", "findFormOneDetails");
		return MRAFormOne.findFormOneDetails(interlineFilterVo);
	}
	/**
	 * 
	 * @author A-3108
	 * 
	 * @param interlineFilterVO
	 * 
	 * @return Collection<AirlineForBillingVO>
	 * 
	 * @throws SystemException
	 * 
	 */
	public static Collection<AirlineForBillingVO> findFormThreeDetails(
			InterlineFilterVO interlineFilterVO)
	throws SystemException {
		MRAAirlineBillingDAO mraAirlineBillingDAO = constructMraAirlineBillingDAO();
		return mraAirlineBillingDAO.findFormThreeDetails(interlineFilterVO);
	}
	/**
	 * 
	 * @author a-3108
	 * 
	 * @param lstupdusr
	 * 
	 * @param lstupddat
	 * 
	 * @param serialNumber
	 * 
	 * @param companyCode
	 * 
	 * @throws SystemException
	 * 
	 */
	public static void saveFormThree(String lstupdusr,
	LocalDate lstupddat, int serialNumber, String companyCode)
	throws SystemException {
		Log log = LogFactory.getLogger("CRA AIRLINEBILLING");
		log.entering("AirlineBilling", "saveFormThreeDetails");
		constructMraAirlineBillingDAO().saveFormThree(lstupdusr, lstupddat,
				serialNumber, companyCode);

	}

	/**
	 * @return the inwardCreditAmount
	 */

	//@Column(name = "INWCRDAMT")      //Modified by A-7929 as part of ICRD-265471
	@Column(name = "INWCRDAMTLSTCUR")
	public double getInwardCreditAmount() {
		return inwardCreditAmount;
	}

	/**
	 * @param inwardCreditAmount
	 *            the inwardCreditAmount to set
	 */
	public void setInwardCreditAmount(double inwardCreditAmount) {
		this.inwardCreditAmount = inwardCreditAmount;
	}

	/**
	 * @return the inwardMisAmount
	 */
	//@Column(name = "INWMISAMT")      
	@Column(name = "INWMISAMTLSTCUR")                    //Modified by A-7929 as part of ICRD-265471
	public double getInwardMisAmount() {
		return inwardMisAmount;
	}

	/**
	 * @param inwardMisAmount
	 *            the inwardMisAmount to set
	 */
	public void setInwardMisAmount(double inwardMisAmount) {
		this.inwardMisAmount = inwardMisAmount;
	}

	/**
	 * @return the inwardNetAmount
	 */

	@Column(name = "INWNETAMT")
	public double getInwardNetAmount() {
		return inwardNetAmount;
	}

	/**
	 * @param inwardNetAmount
	 *            the inwardNetAmount to set
	 */
	public void setInwardNetAmount(double inwardNetAmount) {
		this.inwardNetAmount = inwardNetAmount;
	}

	/**
	 * @return the inwardTotalAmount
	 */

	//@Column(name = "INWTOTAMT")
	@Column(name = "INWTOTAMTLSTCUR")              //Modified by A-7929 as part of ICRD-265471
	public double getInwardTotalAmount() {
		return inwardTotalAmount;
	}

	/**
	 * @param inwardTotalAmount
	 *            the inwardTotalAmount to set
	 */
	public void setInwardTotalAmount(double inwardTotalAmount) {
		this.inwardTotalAmount = inwardTotalAmount;
	}

	/**
	 * @return the outWardMisAmount
	 */

	//@Column(name = "OUTMISAMT")
	@Column(name = "OUTMISAMTLSTCUR")  //Modified by A-7929 as part of ICRD-265471
	public double getOutWardMisAmount() {
		return outWardMisAmount;
	}

	/**
	 * @param outWardMisAmount
	 *            the outWardMisAmount to set
	 */
	public void setOutWardMisAmount(double outWardMisAmount) {
		this.outWardMisAmount = outWardMisAmount;
	}

	/**
	 * @return the outWardTotalAmount
	 */

	//@Column(name = "OUTTOTAMT")
	@Column(name = "OUTTOTAMTLSTCUR")           //Modified by A-7929 as part of ICRD-265471   
	public double getOutWardTotalAmount() {
		return outWardTotalAmount;
	}

	/**
	 * @param outWardTotalAmount
	 *            the outWardTotalAmount to set
	 */
	public void setOutWardTotalAmount(double outWardTotalAmount) {
		this.outWardTotalAmount = outWardTotalAmount;
	}

}
