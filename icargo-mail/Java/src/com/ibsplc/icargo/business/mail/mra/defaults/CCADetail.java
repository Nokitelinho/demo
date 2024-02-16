/*
 * CCADetail.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;
/**
 * @author A-3447
 */
import java.util.Calendar;
import java.util.Collection;
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


import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 *
 *Updated by A-7794 as part of MRA revamp
 */
@Entity
@Table(name = "MALMRAMCA")
@Staleable
public class CCADetail {

	private static final String MODULE_NAME = "mail.mra.defaults";

	private static final String CLASS_NAME = "CCADetails";


	private static final String CCA_NUMBER = "CCA_NUM";

	private CCADetailPK cCADetailPK;

	private Calendar  issueDate;
	private Calendar billingFrom;
	private Calendar billingTo;
	private String mcaType;
	private String mcaRemark;
	private double grossWeight;
	private double revGrossWeight;
	private double weightChargeCTR;
	private double revWtChargeCTR;
	private double dueArl;
	private double revDueArl;
	private double duePostDbt;
	private double revDuePostDbt;
	private String gpaCode;
	private String revGpaCode;
	private String gpaName;
	private String revGpaName;
	private String grossWeightChangeInd;
	private String weightChargeChangeInd;
	private String gpaChangeInd;
	private String mcaStatus;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private String sectFrom;
	private String sectTo;
	private String contCurCode;
	private String autorateFlag;
	private double serviceTax;
	private double tds;
	private double netAmount;
	private double revSrvTax;
	private String revContCurCod;
	private String curChgInd;
	private double revNetAmount;
	private double revTds;
	private String billingStatus;
	private double surChargeCTR;
	private double revSurChargeCTR;
	//Added by A-7540
	private String autoMca;
	private double rate;
	private double revisedRate;
	private String rateChangeInd;
	//added for IASCB-858
	 
		 private String mcaReasonCodes;
	
	
	private Set<CCASurchargeDetail> ccaSurchargeDetails;
	

	/**
	 * @return the issueDate
	 */
	@Column(name = "ISSDAT")
	public Calendar getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Calendar issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the billingFrom
	 */
	@Column(name = "BILPRDFRM")
	public Calendar getBillingFrom() {
		return billingFrom;
	}

	/**
	 * @param billingFrom the billingFrom to set
	 */
	public void setBillingFrom(Calendar billingFrom) {
		this.billingFrom = billingFrom;
	}

	/**
	 * @return the billingTo
	 */
	@Column(name = "BILPRDTOO")
	public Calendar getBillingTo() {
		return billingTo;
	}

	/**
	 * @param billingTo the billingTo to set
	 */
	public void setBillingTo(Calendar billingTo) {
		this.billingTo = billingTo;
	}

	/**
	 * @return the mcaType
	 */
	@Column(name = "MCATYP")
	public String getMcaType() {
		return mcaType;
	}

	/**
	 * @param mcaType the mcaType to set
	 */
	public void setMcaType(String mcaType) {
		this.mcaType = mcaType;
	}

	/**
	 * @return the mcaRemark
	 */
	@Column(name = "MCARMK")
	public String getMcaRemark() {
		return mcaRemark;
	}

	/**
	 * @param mcaRemark the mcaRemark to set
	 */
	public void setMcaRemark(String mcaRemark) {
		this.mcaRemark = mcaRemark;
	}

	/**
	 * @return the grossWeight
	 */
	@Column(name = "GRSWGT")
	public double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @param grossWeight the grossWeight to set
	 */
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * @return the revGrossWeight
	 */
	@Column(name = "REVGRSWGT")
	public double getRevGrossWeight() {
		return revGrossWeight;
	}

	/**
	 * @param revGrossWeight the revGrossWeight to set
	 */
	public void setRevGrossWeight(double revGrossWeight) {
		this.revGrossWeight = revGrossWeight;
	}

	/**
	 * @return the weightChargeCTR
	 */
	@Column(name = "WGTCHGCTR")
	public double getWeightChargeCTR() {
		return weightChargeCTR;
	}

	/**
	 * @param weightChargeCTR the weightChargeCTR to set
	 */
	public void setWeightChargeCTR(double weightChargeCTR) {
		this.weightChargeCTR = weightChargeCTR;
	}

	/**
	 * @return the revWtChargeCTR
	 */
	@Column(name = "REVWGTCHGCTR")
	public double getRevWtChargeCTR() {
		return revWtChargeCTR;
	}

	/**
	 * @param revWtChargeCTR the revWtChargeCTR to set
	 */
	public void setRevWtChargeCTR(double revWtChargeCTR) {
		this.revWtChargeCTR = revWtChargeCTR;
	}

	/**
	 * @return the dueArl
	 */
	@Column(name = "DUEARL")
	public double getDueArl() {
		return dueArl;
	}

	/**
	 * @param dueArl the dueArl to set
	 */
	public void setDueArl(double dueArl) {
		this.dueArl = dueArl;
	}

	/**
	 * @return the revDueArl
	 */
	@Column(name = "REVDUEARL")
	public double getRevDueArl() {
		return revDueArl;
	}

	/**
	 * @param revDueArl the revDueArl to set
	 */
	public void setRevDueArl(double revDueArl) {
		this.revDueArl = revDueArl;
	}

	/**
	 * @return the duePostDbt
	 */
	@Column(name = "DUEPSTDBT")
	public double getDuePostDbt() {
		return duePostDbt;
	}

	/**
	 * @param duePostDbt the duePostDbt to set
	 */
	public void setDuePostDbt(double duePostDbt) {
		this.duePostDbt = duePostDbt;
	}

	/**
	 * @return the revDuePostDbt
	 */
	@Column(name = "REVDUEPSTDBT")
	public double getRevDuePostDbt() {
		return revDuePostDbt;
	}

	/**
	 * @param revDuePostDbt the revDuePostDbt to set
	 */
	public void setRevDuePostDbt(double revDuePostDbt) {
		this.revDuePostDbt = revDuePostDbt;
	}

	/**
	 * @return the gpaCode
	 */
	@Column(name = "GPACOD")
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return the revGpaCode
	 */
	@Column(name = "REVGPACOD")
	public String getRevGpaCode() {
		return revGpaCode;
	}

	/**
	 * @param revGpaCode the revGpaCode to set
	 */
	public void setRevGpaCode(String revGpaCode) {
		this.revGpaCode = revGpaCode;
	}

	/**
	 * @return the gpaName
	 */
	@Column(name = "GPANAM")
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName the gpaName to set
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return the revGpaName
	 */
	@Column(name = "REVGPANAM")
	public String getRevGpaName() {
		return revGpaName;
	}

	/**
	 * @param revGpaName the revGpaName to set
	 */
	public void setRevGpaName(String revGpaName) {
		this.revGpaName = revGpaName;
	}

	/**
	 * @return the grossWeightChangeInd
	 */
	@Column(name = "GRSWGTIND")
	public String getGrossWeightChangeInd() {
		return grossWeightChangeInd;
	}

	/**
	 * @param grossWeightChangeInd the grossWeightChangeInd to set
	 */
	public void setGrossWeightChangeInd(String grossWeightChangeInd) {
		this.grossWeightChangeInd = grossWeightChangeInd;
	}

	/**
	 * @return the weightChargeChangeInd
	 */
	@Column(name = "WGTCHGIND")
	public String getWeightChargeChangeInd() {
		return weightChargeChangeInd;
	}

	/**
	 * @param weightChargeChangeInd the weightChargeChangeInd to set
	 */
	public void setWeightChargeChangeInd(String weightChargeChangeInd) {
		this.weightChargeChangeInd = weightChargeChangeInd;
	}

	/**
	 * @return the gpaChangeInd
	 */
	@Column(name = "GPACHGIND")
	public String getGpaChangeInd() {
		return gpaChangeInd;
	}

	/**
	 * @param gpaChangeInd the gpaChangeInd to set
	 */
	public void setGpaChangeInd(String gpaChangeInd) {
		this.gpaChangeInd = gpaChangeInd;
	}

	/**
	 * @return the mcaStatus
	 */
	@Column(name = "MCASTA")
	public String getMcaStatus() {
		return mcaStatus;
	}

	/**
	 * @param mcaStatus the mcaStatus to set
	 */
	public void setMcaStatus(String mcaStatus) {
		this.mcaStatus = mcaStatus;
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
	 * @return the sectFrom
	 */
	@Column(name = "SECFRM")
	public String getSectFrom() {
		return sectFrom;
	}

	/**
	 * @param sectFrom the sectFrom to set
	 */
	public void setSectFrom(String sectFrom) {
		this.sectFrom = sectFrom;
	}

	/**
	 * @return the sectTo
	 */
	@Column(name = "SECTOO")
	public String getSectTo() {
		return sectTo;
	}

	/**
	 * @param sectTo the sectTo to set
	 */
	public void setSectTo(String sectTo) {
		this.sectTo = sectTo;
	}

	/**
	 * @return the contCurCode
	 */
	@Column(name = "CTRCURCOD")
	public String getContCurCode() {
		return contCurCode;
	}

	/**
	 * @param contCurCode the contCurCode to set
	 */
	public void setContCurCode(String contCurCode) {
		this.contCurCode = contCurCode;
	}

	/**
	 * @return the autorateFlag
	 */
	@Column(name = "AUTORATFLG")
	public String getAutorateFlag() {
		return autorateFlag;
	}

	/**
	 * @return the serviceTax
	 */
	@Column(name = "SRVTAX")
	public double getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the tds
	 */
	@Column(name = "TDS")
	public double getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(double tds) {
		this.tds = tds;
	}

	/**
	 * @return the netAmount
	 */
	@Column(name = "NETAMT")
	public double getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the revSrvTax
	 */
	@Column(name = "REVSRVTAX")
	public double getRevSrvTax() {
		return revSrvTax;
	}

	/**
	 * @param revSrvTax the revSrvTax to set
	 */
	public void setRevSrvTax(double revSrvTax) {
		this.revSrvTax = revSrvTax;
	}

	/**
	 * @return the revContCurCod
	 */
	@Column(name = "REVCTRCURCOD")
	public String getRevContCurCod() {
		return revContCurCod;
	}

	/**
	 * @param revContCurCod the revContCurCod to set
	 */
	public void setRevContCurCod(String revContCurCod) {
		this.revContCurCod = revContCurCod;
	}

	/**
	 * @return the curChgInd
	 */
	@Column(name = "CURCHGIND")
	public String getCurChgInd() {
		return curChgInd;
	}

	/**
	 * @param curChgInd the curChgInd to set
	 */
	public void setCurChgInd(String curChgInd) {
		this.curChgInd = curChgInd;
	}

	/**
	 * @return the revNetAmount
	 */
	@Column(name = "REVNETAMT")
	public double getRevNetAmount() {
		return revNetAmount;
	}

	/**
	 * @param revNetAmount the revNetAmount to set
	 */
	public void setRevNetAmount(double revNetAmount) {
		this.revNetAmount = revNetAmount;
	}

	/**
	 * @return the revTds
	 */
	@Column(name = "REVTDS")
	public double getRevTds() {
		return revTds;
	}

	/**
	 * @param revTds the revTds to set
	 */
	public void setRevTds(double revTds) {
		this.revTds = revTds;
	}

	/**
	 * @return the billingStatus
	 */
	@Column(name = "BLGSTA")
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 * @param billingStatus the billingStatus to set
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	/**
	 * @return the surChargeCTR
	 */
	@Column(name = "OTHCHGCTR")
	public double getSurChargeCTR() {
		return surChargeCTR;
	}

	/**
	 * @param surChargeCTR the surChargeCTR to set
	 */
	public void setSurChargeCTR(double surChargeCTR) {
		this.surChargeCTR = surChargeCTR;
	}

	/**
	 * @return the revSurChargeCTR
	 */
	@Column(name = "REVOTHCHGCTR")
	public double getRevSurChargeCTR() {
		return revSurChargeCTR;
	}

	/**
	 * @param revSurChargeCTR the revSurChargeCTR to set
	 */
	public void setRevSurChargeCTR(double revSurChargeCTR) {
		this.revSurChargeCTR = revSurChargeCTR;
	}

	
    @Column(name = "AUTMCA")
	public String getAutoMca() {
		return autoMca;
	}

	public void setAutoMca(String autoMca) {
		this.autoMca = autoMca;
	}
    
	@Column(name = "RAT")
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
    
	@Column(name = "REVRAT")
	public double getRevisedRate() {
		return revisedRate;
	}

	public void setRevisedRate(double revisedRate) {
		this.revisedRate = revisedRate;
	}
    
	@Column(name = "RATCHGIND")
	public String getRateChangeInd() {
		return rateChangeInd;
	}

	public void setRateChangeInd(String rateChangeInd) {
		this.rateChangeInd = rateChangeInd;
	}

	/**
	 * 	Getter for mcaReasonCodes 
	 *	Added by : A-7531 on 06-Feb-2019
	 * 	Used for :
	 */
	@Column(name = "MCARSNCOD")
	public String getMcaReasonCodes() {
		return mcaReasonCodes;
	}

	/**
	 *  @param mcaReasonCodes the mcaReasonCodes to set
	 * 	Setter for mcaReasonCodes 
	 *	Added by : A-7531 on 06-Feb-2019
	 * 	Used for :
	 */
	public void setMcaReasonCodes(String mcaReasonCodes) {
		this.mcaReasonCodes = mcaReasonCodes;
	}

	public CCADetail() {

	}

	/**
	 * @author A-3447
	 * @param cCAdetailsVO
	 * @throws SystemException
	 */
	public CCADetail(CCAdetailsVO cCAdetailsVO) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering("MRA Defaults", "contructor");
		/**
		 * @author A-3447
		 */

		//generateCCARefNumber to correct --------->>>
		String ccaRefNumber=generateCCARefNumber(cCAdetailsVO.getCompanyCode());
		log.log(Log.INFO,"ccaRefNumber=--->" +ccaRefNumber);
		CCADetailPK cCaDetailPK = new CCADetailPK();
		cCaDetailPK.setCompanyCode(cCAdetailsVO.getCompanyCode());
		cCaDetailPK.setMcaRefNumber(ccaRefNumber);
		if("Y".equals(cCAdetailsVO.getAutoMca()))
		{
			//ccaRefNumber.concat("A");
			if(cCAdetailsVO.isVoidMailbag()){
				ccaRefNumber = ccaRefNumber+'V';
			}else{
			ccaRefNumber = ccaRefNumber+'A';
			}
		    cCaDetailPK.setMcaRefNumber(ccaRefNumber);
		}
		cCaDetailPK.setMailSequenceNumber(cCAdetailsVO.getMailSequenceNumber());
		//cCaDetailPK.setBillingBasis(cCAdetailsVO.getBillingBasis());
		//cCaDetailPK.setCsgSequenceNumber(cCAdetailsVO.getCsgSequenceNumber());
		//cCaDetailPK.setCsgDocumentNumber(cCAdetailsVO.getCsgDocumentNumber());
		//cCaDetailPK.setPoaCode(cCAdetailsVO.getPoaCode());
		this.setCCADetailsPK(cCaDetailPK);
		populateAttributes(cCAdetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
			
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode());
		}
		
		cCAdetailsVO.setCcaRefNumber(ccaRefNumber);
		populateChildren(cCAdetailsVO);
		log.exiting("MRA Defaults", "contructor");

	}

	/**
	 * @author A-3447
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public static String generateCCARefNumber(String companyCode)
	throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		Criterion criterion = KeyUtils.getCriterion(companyCode, CCA_NUMBER);
		String key = KeyUtils.getKey(criterion);
		log.log(Log.INFO, "keyGenerated---:)" + key);

		int keynum = Integer.parseInt(key);
		StringBuilder str = new StringBuilder("MCA");
		if(keynum<10){
			str.append("00000");
		}
		else if(keynum<100){
			str.append("0000");
		}else if(keynum<1000){
			str.append("000");
		}else if(keynum<10000){
			str.append("00");
		}
		else if(keynum<100000){
			str.append("0");
		}
		else if(keynum<1000000){
			str.append("");
		}
		str.append(key);
		log.log(Log.INFO,"keynum=--->" +keynum);
		return str.toString();
	}

	/**
	 * @param cCAdetailsVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static CCADetail find(CCAdetailsVO cCAdetailsVO)
	throws SystemException , FinderException{

		CCADetailPK ccADetailPK = new CCADetailPK();
		CCADetail cCADetail = null;
		ccADetailPK.setCompanyCode(cCAdetailsVO.getCompanyCode());
		ccADetailPK.setMcaRefNumber(cCAdetailsVO.getCcaRefNumber());
		ccADetailPK.setMailSequenceNumber(cCAdetailsVO.getMailSequenceNumber());
		//ccADetailPK.setBillingBasis(cCAdetailsVO.getBillingBasis());
		//ccADetailPK.setCsgSequenceNumber(cCAdetailsVO.getCsgSequenceNumber());
		//ccADetailPK.setCsgDocumentNumber(cCAdetailsVO.getCsgDocumentNumber());
		//ccADetailPK.setPoaCode(cCAdetailsVO.getPoaCode());
		
		
		return PersistenceController.getEntityManager().find(CCADetail.class, ccADetailPK);
	}

	/**@author A-3447
	 * @param cCAdetailsVO
	 * @throws SystemException
	 */
	public void update(CCAdetailsVO cCAdetailsVO) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering("MRA Defaults", "update");
		populateAttributes(cCAdetailsVO);
		populateChildren(cCAdetailsVO);
		log.exiting("MRA Defaults", "update");
	}
	
	/**@author A-5255
	 * @param cCAdetailsVO
	 * @throws SystemException
	 */
	public void updateMaster(CCAdetailsVO cCAdetailsVO) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering("MRA Defaults", "update ");
		populateAttributes(cCAdetailsVO);
		log.exiting("MRA Defaults", "update");
	}

	/**
	 * @author A-3447
	 * @param cCAdetailsVO
	 */
	private void populateAttributes(CCAdetailsVO cCAdetailsVO) {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering("MRA Defaults", "populateAttributes");
		this.setMcaStatus(cCAdetailsVO.getCcaStatus());

		if(cCAdetailsVO.getOperationFlag()!=null&& "I".equals(cCAdetailsVO.getOperationFlag())){
			this.setIssueDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).toCalendar());
		}
		else{
			
			this.setIssueDate(cCAdetailsVO.getIssueDat().toCalendar());
		}

		//this.setIssueParty(cCAdetailsVO.getIssuingParty());
		if(cCAdetailsVO.getAirlineCode()!=null){
		//this.setAirlineCode(cCAdetailsVO.getAirlineCode());
		}
		this.setMcaType(cCAdetailsVO.getCcaType());
		if(cCAdetailsVO.getBillingPeriodFrom()!=null){
			this.setBillingFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(cCAdetailsVO.getBillingPeriodFrom()).toCalendar());
		}
		if(cCAdetailsVO.getBillingPeriodTo()!=null){
			this.setBillingTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(cCAdetailsVO.getBillingPeriodTo()).toCalendar());
		}
		
		
		//this.setOriginCode(cCAdetailsVO.getOriginCode());
		//this.setDestinationCode(cCAdetailsVO.getDestnCode());
		//this.setCategoryCode(cCAdetailsVO.getCategory());
		this.setGrossWeight(cCAdetailsVO.getGrossWeight());
		this.setRevGrossWeight(cCAdetailsVO.getRevGrossWeight());
		if(cCAdetailsVO.getChgGrossWeight()!=null){
			this.setWeightChargeCTR(cCAdetailsVO.getChgGrossWeight().getAmount());
		}
		if(cCAdetailsVO.getRevChgGrossWeight()!=null){
			//this.setRevChgGrossWeight(cCAdetailsVO.getRevChgGrossWeight().getAmount());
		}
		//Modified by A-7794 as part of ICRD-263952
		if(cCAdetailsVO.getOtherChgGrossWgt()!=null){
			this.setSurChargeCTR(cCAdetailsVO.getOtherChgGrossWgt().getAmount());
		}
		if(cCAdetailsVO.getOtherRevChgGrossWgt()!=null){
			this.setRevSurChargeCTR(cCAdetailsVO.getOtherRevChgGrossWgt().getAmount());
		}
		
		if(cCAdetailsVO.getDueArl()!=null){
			this.setDueArl(cCAdetailsVO.getDueArl().getAmount());
		}
		if(cCAdetailsVO.getRevDueArl()!=null){
			this.setRevDueArl(cCAdetailsVO.getRevDueArl().getAmount());
		}
		if(cCAdetailsVO.getDuePostDbt()!=null){
			this.setDuePostDbt(cCAdetailsVO.getDuePostDbt().getAmount());
		}
		this.setRevDuePostDbt(cCAdetailsVO.getRevDuePostDbt());
		//this.setRevOrgCode(cCAdetailsVO.getOriginCode());
		//this.setRevDStCode(cCAdetailsVO.getRevDStCode());
		this.setGpaCode(cCAdetailsVO.getGpaCode());
		this.setGpaName(cCAdetailsVO.getGpaName());
		this.setMcaRemark(cCAdetailsVO.getCcaRemark());
		//this.setDsn(cCAdetailsVO.getDsnNo());
		if(cCAdetailsVO.getDsnDate()!=null){
			//this.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(cCAdetailsVO.getDsnDate()).toCalendar());
		}
		//this.setGpaArlIndicator(cCAdetailsVO.getGpaArlIndicator());
		this.setRevGpaCode(cCAdetailsVO.getRevGpaCode());
		this.setRevGpaName(cCAdetailsVO.getRevGpaName());
		this.setGrossWeightChangeInd(cCAdetailsVO.getGrossWeightChangeInd());
		this.setWeightChargeChangeInd(cCAdetailsVO.getWeightChargeChangeInd());
		this.setGpaChangeInd(cCAdetailsVO.getGpaChangeInd());
		//this.setDoeChangeInd(cCAdetailsVO.getDoeChangeInd());
		//this.setWriteOffInd(cCAdetailsVO.getWriteOffInd());
		//this.setUsrccanum(cCAdetailsVO.getUsrccanum());
		this.setSectFrom(cCAdetailsVO.getSectFrom());
		this.setSectTo(cCAdetailsVO.getSectTo());
		this.setLastUpdatedTime(cCAdetailsVO.getLastUpdateTime());
		this.setLastUpdatedUser(cCAdetailsVO.getLastUpdateUser());
		this.setContCurCode(cCAdetailsVO.getContCurCode());
		//this.setPayFlag(cCAdetailsVO.getPayFlag());
		//this.setUpdBillTo(cCAdetailsVO.getUpdBillTo());
		//this.setUpdBillToIdr(cCAdetailsVO.getUpdBillToIdr());
		if(cCAdetailsVO.getNetDueArlUSD()!=null){
			//this.setNetDueArlUSD(cCAdetailsVO.getNetDueArlUSD().getAmount());
		}
		this.setAutorateFlag(cCAdetailsVO.getAutorateFlag());
		//Added for CRQ-7352
		this.setTds(cCAdetailsVO.getTds());
		this.setServiceTax(cCAdetailsVO.getTax());
		
		this.setRevContCurCod(cCAdetailsVO.getRevContCurCode());
		this.setRevSrvTax(cCAdetailsVO.getRevTax());
		this.setCurChgInd(cCAdetailsVO.getCurrChangeInd());
		this.setRevTds(cCAdetailsVO.getRevTds());
		if(cCAdetailsVO.getRevNetAmount()!=null){
			this.setRevNetAmount(cCAdetailsVO.getRevNetAmount().getAmount());
			
		}
		if(cCAdetailsVO.getNetAmount()!=null){
			this.setNetAmount(cCAdetailsVO.getNetAmount().getAmount());
		}
		//Commented by A-7794 as part of ICRD-263952
		//this.setWeightChargeCTR(cCAdetailsVO.getWtChgAmtinCTR());
		if(cCAdetailsVO.getRevChgGrossWeight()!=null){
		this.setRevWtChargeCTR(cCAdetailsVO.getRevChgGrossWeight().getAmount());
		}
		this.setBillingStatus(cCAdetailsVO.getBillingStatus());
		
		//Added by A-7540
		this.setAutoMca(cCAdetailsVO.getAutoMca());
		//Added by a-7929  as part of ICRD-132548
		this.setRate(cCAdetailsVO.getRate());
		this.setRevisedRate(cCAdetailsVO.getRevisedRate());
		this.setRateChangeInd(cCAdetailsVO.getRateChangeInd());
		this.setMcaReasonCodes(cCAdetailsVO.getMcaReasonCodes());//added for IASCB-858
		//this.setCategoryCode(cCAdetailsVO.getCategoryCode());//Commented as part of ICRD-151490
		//this.setSubclass(cCAdetailsVO.getSubClass());
		log.log(Log.INFO,"VO=--->" +cCAdetailsVO);
		log.exiting("MRADefaults", "populateAttributes");
	}


	/**
	 * @return the cCADetailsPK
	 */
	
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
		@AttributeOverride(name = "mcaRefNumber", column = @Column(name = "MCAREFNUM"))})
		public CCADetailPK getCCADetailsPK() {
		return cCADetailPK;
	}

	/**
	 * @param detailsPK
	 *            the cCADetailsPK to set
	 */
	public void setCCADetailsPK(CCADetailPK detailsPK) {
		cCADetailPK = detailsPK;
	}

	/**
	 * @return the ccaReason
	 *//*
	@Column(name = "CCAREASON")
	public String getCcaReason() {
		return ccaReason;
	}

	  *//**
	  * @param ccaReason
	  *            the ccaReason to set
	  *//*
	public void setCcaReason(String ccaReason) {
		this.ccaReason = ccaReason;
	}*/


	/**
	 * @return the ccaSurchargeDetails
	 */
	@OneToMany
    @JoinColumns({
    	@JoinColumn(name = "CMPCOD",referencedColumnName = "CMPCOD",insertable = false, updatable = false),
    	@JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM",insertable = false, updatable = false),
        @JoinColumn(name = "MCAREFNUM", referencedColumnName = "MCAREFNUM",insertable = false, updatable = false)})
	public Set<CCASurchargeDetail> getCcaSurchargeDetails() {
		return ccaSurchargeDetails;
	}

	/**
	 * @param ccaSurchargeDetails the ccaSurchargeDetails to set
	 */
	public void setCcaSurchargeDetails(Set<CCASurchargeDetail> ccaSurchargeDetails) {
		this.ccaSurchargeDetails = ccaSurchargeDetails;
	}

	/**
	 *
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CCAdetailsVO> findCCAdetails(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException {

		try {
			Log log = LogFactory.getLogger("--CCADetails---");
			log.entering("BillingMaster", "entity");
			return constructDAO().findCCAdetails(maintainCCAFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @author A-3447
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRADefaultsDAO constructDAO() throws PersistenceException,
	SystemException {
		Log log = LogFactory.getLogger("MRA BILLING");
		log.entering("MRABillingMaster", "constructDAO");
		EntityManager entityManager = PersistenceController.getEntityManager();
		return MRADefaultsDAO.class
		.cast(entityManager.getQueryDAO(MODULE_NAME));
	}
	/**
	 * @author A-3447
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CCAdetailsVO> findCCA(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).findCCA(maintainCCAFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}


	/**
	 * This method is used for listing CCAs
	 * @author A-3429
	 * @param listCCAFilterVo
	 * @param pageNumber
	 * @return Page<CCAdetailsVO>
	 * @throws SystemException
	 *
	 */
	public static Page<CCAdetailsVO> listCCAs(ListCCAFilterVO listCCAFilterVo)
	throws SystemException{
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).listCCAs(listCCAFilterVo);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	/**
	 * This method is used for printing the CCAs
	 * @author A-3429
	 * @param listCCAFilterVo
	 * @param pageNumber
	 * @return Page<CCAdetailsVO>
	 * @throws SystemException
	 *
	 */
	public static Collection<CCAdetailsVO> listCCAforPrint(ListCCAFilterVO listCCAFilterVo)
	throws SystemException{
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).listCCAforPrint(listCCAFilterVo);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * This method is used to remove the Instance of the Entity
	 *@author A-3251
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

	/**
	 * @param autorateFlag the autorateFlag to set
	 */
	public void setAutorateFlag(String autorateFlag) {
		this.autorateFlag = autorateFlag;
	}
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws SystemException 
	 */
	public static Page<CCAdetailsVO> findMCALov(CCAdetailsVO ccAdetailsVO,
			int displayPage) throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).findMCALov(ccAdetailsVO,displayPage);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	/**
	 * 
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 */
	public static Page<CCAdetailsVO> findDSNLov(CCAdetailsVO ccAdetailsVO,
			int displayPage)throws SystemException  {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).findDSNLov(ccAdetailsVO,displayPage);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	public static Collection<CCAdetailsVO> findApprovedMCA(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).findApprovedMCA(maintainCCAFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	/**
	 * 
	 * @author A-5255
	 * @param cCAdetailsVO
	 * @throws SystemException
	 */
	private void populateChildren(CCAdetailsVO cCAdetailsVO) throws SystemException{
		/**
		 * removing existing children
		 */
		if(this.ccaSurchargeDetails!=null && this.ccaSurchargeDetails.size()>0){
			for(CCASurchargeDetail ccaSurchargeDetail:ccaSurchargeDetails){
				ccaSurchargeDetail.remove();
			}
			this.ccaSurchargeDetails=null;
		}
		/**
		 * inserting all values
		 */
		if(cCAdetailsVO!=null && cCAdetailsVO.getSurchargeCCAdetailsVOs()!=null){
			for(SurchargeCCAdetailsVO surchargeCCAdetailsVO:cCAdetailsVO.getSurchargeCCAdetailsVOs()){
				surchargeCCAdetailsVO.setCompanyCode(cCAdetailsVO.getCompanyCode());
				surchargeCCAdetailsVO.setBillingBasis(cCAdetailsVO.getBillingBasis());
				surchargeCCAdetailsVO.setCcaRefNumber(cCAdetailsVO.getCcaRefNumber());
				surchargeCCAdetailsVO.setCsgDocumentNumber(cCAdetailsVO.getCsgDocumentNumber());
				surchargeCCAdetailsVO.setCsgSequenceNumber(cCAdetailsVO.getCsgSequenceNumber());
				surchargeCCAdetailsVO.setPoaCode(cCAdetailsVO.getPoaCode());
				//Added by A-7794 as part of ICRD-263953
				surchargeCCAdetailsVO.setOrgSurCharge(cCAdetailsVO.getOtherChgGrossWgt());
				surchargeCCAdetailsVO.setRevSurCharge(cCAdetailsVO.getOtherRevChgGrossWgt());
				//a-8061 added for ICRD-254294
				surchargeCCAdetailsVO.setLastUpdateTime(cCAdetailsVO.getLastUpdateTime());
				surchargeCCAdetailsVO.setLastUpdateUser(cCAdetailsVO.getLastUpdateUser());
				surchargeCCAdetailsVO.setMailSequenceNumber(cCAdetailsVO.getMailSequenceNumber());//added by A-7371 as part of ICRD-257661
				//Added by a-7540 
				surchargeCCAdetailsVO.setSurchareOrgRate(surchargeCCAdetailsVO.getSurchareOrgRate());
				surchargeCCAdetailsVO.setSurchargeRevRate(surchargeCCAdetailsVO.getSurchargeRevRate());
				new CCASurchargeDetail(surchargeCCAdetailsVO);
			}
		}
	}
	/**
	 * 
	 * 	Method		:	CCADetail.findCCAStatus
	 *	Added by 	:	A-7531 on 02-Jun-2017
	 * 	Used for 	:
	 *	Parameters	:	@param maintainCCAFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	public static String findCCAStatus(MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException{
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).findCCAStatus(maintainCCAFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	public CCAdetailsVO retrieveVO() {   //added by a-7531 for ICRD-282931
		CCAdetailsVO ccaDetailsVO  = new CCAdetailsVO();
		cCADetailPK = getCCADetailsPK();
		ccaDetailsVO.setCompanyCode(cCADetailPK.getCompanyCode());
		ccaDetailsVO.setMailSequenceNumber(cCADetailPK.getMailSequenceNumber());
		ccaDetailsVO.setCcaRefNumber(cCADetailPK.getMcaRefNumber());
		ccaDetailsVO.setContCurCode(getContCurCode());
		ccaDetailsVO.setGpaCode(getGpaCode());
		ccaDetailsVO.setAutoMca(getAutoMca());
		ccaDetailsVO.setAutorateFlag(getAutorateFlag());
		//ccaDetailsVO.setBillingPeriodFrom(getBillingFrom().toString());
		//ccaDetailsVO.setBillingPeriodTo(getBillingTo().toString());
		ccaDetailsVO.setBillingStatus(getBillingStatus());
		ccaDetailsVO.setCcaStatus(getMcaStatus());
		ccaDetailsVO.setCcaType(getMcaType());
		ccaDetailsVO.setCcaRemark(getMcaRemark());
		ccaDetailsVO.setGrossWeight(getGrossWeight());
		ccaDetailsVO.setRevGrossWeight(getRevGrossWeight());
		ccaDetailsVO.setRevGpaCode(getRevGpaCode());
		ccaDetailsVO.setRevContCurCode(getRevContCurCod());
		ccaDetailsVO.setRevisedRate(getRevisedRate());
		ccaDetailsVO.setPoaCode(getGpaCode());
		
		//A-8061 Added for ICRD-293199 Begin
		ccaDetailsVO.setGrossWeightChangeInd(getGrossWeightChangeInd());
		ccaDetailsVO.setWeightChargeChangeInd(getWeightChargeChangeInd());
		ccaDetailsVO.setGpaChangeInd(getGpaChangeInd());
		ccaDetailsVO.setCurrChangeInd(getCurChgInd());
		ccaDetailsVO.setRateChangeInd(getRateChangeInd());
		//A-8061 Added for ICRD-293199 End
		
		Money revNetAmt=null;
		Money chggrswt=null;
		Money revChgGrswt=null;
		Money otherChgGrossWgt=null;
		Money revOtherChgGrossWgt=null;
		Money netAmt=null;
		
		
		if(getContCurCode()!=null){
			try {
				
				revNetAmt = CurrencyHelper.getMoney(getContCurCode());
				chggrswt = CurrencyHelper.getMoney(getContCurCode());
				revChgGrswt = CurrencyHelper.getMoney(getContCurCode());
				otherChgGrossWgt= CurrencyHelper.getMoney(getContCurCode());
				revOtherChgGrossWgt=CurrencyHelper.getMoney(getContCurCode());
				netAmt=CurrencyHelper.getMoney(getContCurCode());
				
				revChgGrswt.setAmount(getRevWtChargeCTR());
				chggrswt.setAmount(getWeightChargeCTR());
				revNetAmt.setAmount(getRevNetAmount());
				otherChgGrossWgt.setAmount(getSurChargeCTR());
				revOtherChgGrossWgt.setAmount(getRevSurChargeCTR());
				netAmt.setAmount(getNetAmount());
				
		ccaDetailsVO.setRevNetAmount(revNetAmt);
		ccaDetailsVO.setChgGrossWeight(chggrswt);
		ccaDetailsVO.setRevChgGrossWeight(revChgGrswt);
		ccaDetailsVO.setOtherChgGrossWgt(otherChgGrossWgt);
		ccaDetailsVO.setOtherRevChgGrossWgt(revOtherChgGrossWgt);
		ccaDetailsVO.setNetAmount(netAmt);
			}
		catch (CurrencyException e) {
		//	log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		}
		ccaDetailsVO.setRate(getRate());
		
      return ccaDetailsVO;
	}
}