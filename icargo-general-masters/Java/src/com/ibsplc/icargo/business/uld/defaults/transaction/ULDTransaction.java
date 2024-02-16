/*
 * ULDTransaction.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.icargo.business.uld.defaults.ULDObjectQueryInterface;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1347 
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Staleable
@Table(name="ULDTXNMST")
@Entity
public class ULDTransaction {
    
      private Log log =LogFactory.getLogger("ULD_DEFAULTS");
      private static final String MODULE="uld.defaults";
      //Transaction Type will be L-Loan/B-Borrow/R-Return    
      private String transactionType;
      private String uldType;
      //Transaction Status will be P-Permanent/T-Temporary    
      private String transactionNature;
      private String partyType;
      private String partyCode;
      private String partyIdentifier;
      //Station at which Loan/Borrow    
      private String transactionStationCode;
    //  private String transationPeriod;
      private Calendar transactionDate; 
      private Calendar leaseEndDate;
      private Calendar leaseEndDateUtc;
      private String transactionRemark;
      //This filed will only be there for Borrow Transaction    
      private String capturedRefNumber;
      private String damageStatus;
      private Calendar returnDate;
      //Station at which Return happens    
      private String returnStationCode;
      private double demurrageAmount;
      private String currency;
    //  private String invoiceStatus;
      private double waived;
      private double taxes;
      
      private double otherCharges;
      private double total;
      //Stamping and Tracking Invoice 
      private String invoiceRefNumber;
      private String paymentStatus;
      private Calendar lastUpdateTime;
      private String lastUpdateUser;
      private String transactionStatus;
      private String partyName;
            
      //the ailineCode of the party which loans uld
      //this is being used as returnPartyCode
      private String returnPartyCode;
      
      private ULDTransactionPK uldTransactionPK;   
      
      private String returnRemarks;
      
      private String agreementNumber;
     
      // the identifier of the party whiich loans uld
      // fromPartyCode in VO
      private int returnPartyidentifier;
      
      private String destinationAirport;
      
      private String controlReceiptNumber;
      
  	/* added by a-3278 for 28897 on 06Jan09
	 * a new CRN is maintained to save the latest and the old CRN seperately		 
	 */      
      private String returnCRN;
    
      //Added by A-2412
      private String uldConditionCode;
      //ends
      
      private String mucIsSent;       
      //Added by nisha for QF1018 starts
      private String poolOwnerFlag;
      //ends
      
//    added by a-3045 for CR QF1013 starts
      //added for MUC Tracking
      private String mucReferenceNumber;
      private Calendar mucDate;
      //added by a-3045 for CR QF1013 ends
      private String awbNumber;
      private String emptyStatus;
      //added by a-3045 for bug 19640 starts
      //Added by A-4072 Starts
      private String originatorName;
      private String damagedFlag;
      private String damageRemark;
      private String odlnCode;
    //Added by A-4072 Starts
      private static final String AGENT = "G";
      private static final String OTHERS = "O";
      //added by a-3045 for bug 19640 ends
      /****************added for bug 103811 starts***********/
      //private static final String AIRLINEIDENTIFIER = "081";
      /****************added for bug 103811 ends***********/
      //Added by A-3415 for ICRD-114538         
      private String returnCloseFlag;
      private double demurrageRate;
      private String demurrageFrequency;
      private int freeLoanPeriod;
      
	@Column(name = "MUCSNT")
      public String getMucIsSent() {
		return mucIsSent;
	}


	public void setMucIsSent(String mucIsSent) {
		this.mucIsSent = mucIsSent;
	}


	@Column(name = "CONCOD")
    public String getUldConditionCode() {
		return uldConditionCode;
	}


	public void setUldConditionCode(String uldConditionCode) {
		this.uldConditionCode = uldConditionCode;
	}


	@Column(name = "DSTAPTCOD")
	/**
	 * @return Returns the destinationAirport.
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}


	/**
	 * @param destinationAirport The destinationAirport to set.
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}


	/**
	 * @return Returns the partyIdentifier.
	 */
      @Column(name="PTYIDR")
	public String getPartyIdentifier() {
		return partyIdentifier;
	}


	/**
	 * @param partyIdentifier The partyIdentifier to set.
	 */
	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}


	/**
	 * @return Returns the partyName.
	 */
      @Column(name="PTYNAM")
	public String getPartyName() {
		return partyName;
	}


	/**
	 * @param partyName The partyName to set.
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}


	/**
	 * Constructor
	 */
	public ULDTransaction() {
	}
	
	
	/**
	 * Constructor
	 *
	 * @param ULDTransactionDetailsVO
	 * @throws SystemException
	 */
	
	
    /**
	 * @return Returns the borrowRefNumber.
	 */
    
    @Column(name = "CPDREFNUM")
	public String getCapturedRefNumber() {
		return capturedRefNumber;
	}


	/**
	 * @param capturedRefNumber The borrowRefNumber to set.
	 */
	public void setCapturedRefNumber(String capturedRefNumber) {
		this.capturedRefNumber = capturedRefNumber;
	}


	/**
	 * @return Returns the currency.
	 */
	@Column(name = "CURCOD")
	public String getCurrency() {
		return currency;
	}


	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

    
	/**
	 * @return Returns the transactionStatus.
	 */
      @Column(name = "TXNSTA")
	public String getTransactionStatus() {
		return transactionStatus;
	}


	/**
	 * @param transactionStatus The transactionStatus to set.
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	
	/**
	 * @return Returns the damageStatus.
	 */
	@Column(name = "DMGSTA")
	public String getDamageStatus() {
		return damageStatus;
	}


	/**
	 * @param damageStatus The damageStatus to set.
	 */
	
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}


	/**
	 * @return Returns the demurrageAmount.
	 */
	@Column(name = "DMRAMT")
	public double getDemurrageAmount() {
		return demurrageAmount;
	}


	/**
	 * @param demurrageAmount The demurrageAmount to set.
	 */
	public void setDemurrageAmount(double demurrageAmount) {
		this.demurrageAmount = demurrageAmount;
	}


	/**
	 * @return Returns the invoiceRefNumber.
	 */
	@Column(name = "INVREFNUM")
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}


	/**
	 * @param invoiceRefNumber The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber=  invoiceRefNumber;
	}


	
	/**
	 * @return Returns the otherCharges.
	 */
	@Column(name = "OTRCRG")
	public double getOtherCharges() {
		return otherCharges;
	}


	/**
	 * @param otherCharges The otherCharges to set.
	 */
	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}


	/**
	 * @return Returns the partyCode.
	 */
	@Column(name = "PTYCOD")
	@Audit(name="PartyCode")
	public String getPartyCode() {
		return partyCode;
	}


	/**
	 * @param partyCode The partyCode to set.
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}


	/**
	 * @return Returns the partyType.
	 */
	@Column(name = "PTYTYP")
	public String getPartyType() {
		return partyType;
	}


	/**
	 * @param partyType The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}


	/**
	 * @return Returns the paymentStatus.
	 */
	@Column(name = "PAYSTA")
	public String getPaymentStatus() {
		return paymentStatus;
	}


	/**
	 * @param paymentStatus The paymentStatus to set.
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	/**
	 * @return Returns the returnDate.
	 */
	@Column(name = "RTNDAT")
	@Audit(name="ReturnDate")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getReturnDate() {
		return returnDate;
	}


	/**
	 * @param returnDate The returnDate to set.
	 */
	
	public void setReturnDate(Calendar returnDate) {
		this.returnDate = returnDate;
	}


	/**
	


	/**
	 * @return Returns the returnStationCode.
	 */
	@Column(name = "RTNARPCOD")
	public String getReturnStationCode() {
		return returnStationCode;
	}


	/**
	 * @param returnStationCode The returnStationCode to set.
	 */
	
	public void setReturnStationCode(String returnStationCode) {
		this.returnStationCode = returnStationCode;
	}


	/**
	 * @return Returns the taxes.
	 */
	@Column(name = "TAXAMT")
	public double getTaxes() {
		return taxes;
	}


	/**
	 * @param taxes The taxes to set.
	 */
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}


	/**
	 * @return Returns the total.
	 */
	@Column(name = "TOTAMT")
	public double getTotal() {
		return total;
	}


	/**
	 * @param total The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}


	/**
	 * @return Returns the transactionDate.
	 */
	@Column(name = "TXNDAT")
	@Audit(name="TransactionDate")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTransactionDate() {
		return transactionDate;
	}


	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Calendar transactionDate) {
		this.transactionDate = transactionDate;
	}


	/**
	 * @return Returns the transactionNature.
	 */
	@Column(name = "TXNNAT")
	@Audit(name="TransactionNature")
	public String getTransactionNature() {
		return transactionNature;
	}


	/**
	 * @param transactionNature The transactionNature to set.
	 */
	public void setTransactionNature(String transactionNature) {
		this.transactionNature = transactionNature;
	}


	/**
	 * @return Returns the transactionRemark.
	 */
	@Column(name = "TXNRMK")
	public String getTransactionRemark() {
		return transactionRemark;
	}


	/**
	 * @param transactionRemark The transactionRemark to set.
	 */
	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}


	/**
	 * @return Returns the transactionStationCode.
	 */
	@Column(name = "TXNARPCOD")
	@Audit(name="TransactionStationCode")
	public String getTransactionStationCode() {
		return transactionStationCode;
	}


	/**
	 * @param transactionStationCode The transactionStationCode to set.
	 */
	public void setTransactionStationCode(String transactionStationCode) {
		this.transactionStationCode = transactionStationCode;
	}


	/**
	 * @return Returns the transactionType.
	 */
	@Column(name = "TXNTYP")
	public String getTransactionType() {
		return transactionType;
	}


	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	

	/**
	 * @return Returns the uldType.
	 */
	@Column(name = "ULDTYP")
	public String getUldType() {
		return uldType;
	}


	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}


	/**
	 * @return Returns the waived.
	 */
	@Column(name = "WVRAMT")
	public double getWaived() {
		return waived;
	}


	/**
	 * @param waived The waived to set.
	 */
	public void setWaived(double waived) {
		this.waived = waived;
	}
	 /**
     * @return Returns the lastUpdateTime.
     */
	//@Version
    @Column(name = "LSTUPDTIM")	    
	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    /**
     * @return Returns the lastUpdateUser.
     */
    @Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
        return lastUpdateUser;
    }
    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

	
    /**
	 * @return Returns the uldTransactionPK.
	 */
    
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode",column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "uldNumber",column = @Column(name = "ULDNUM")),
		@AttributeOverride(name = "transactionRefNumber",column = @Column(name = "TXNREFNUM")) }
	)
	public ULDTransactionPK getUldTransactionPK() {
		return uldTransactionPK;
	}


	/**
	 * @param uldTransactionPK The uldTransactionPK to set.
	 */
	public void setUldTransactionPK(ULDTransactionPK uldTransactionPK) {
		this.uldTransactionPK = uldTransactionPK;
	}


	
	/**
	 * @return the poolOwnerFlag
	 */
	 @Column(name = "POLOWN")
	public String getPoolOwnerFlag() {
		return poolOwnerFlag;
	}


	/**
	 * @return the awbNumber
	 */
	 @Column(name = "AWBRMK")
	public String getAwbNumber() {
		return awbNumber;
	}


	/**
	 * @param awbNumber the awbNumber to set
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}


	/**
	 * @return the emptyStatus
	 */
	 @Column(name = "EMTSTA")
	public String getEmptyStatus() {
		return emptyStatus;
	}


	/**
	 * @param emptyStatus the emptyStatus to set
	 */
	public void setEmptyStatus(String emptyStatus) {
		this.emptyStatus = emptyStatus;
	}

	/**
	 * @return the originatorName
	 */
	@Column(name = "ORNNAM")
	public String getOriginatorName() {
		return originatorName;
	}


	/**
	 * @param originatorName the originatorName to set
	 */
	public void setOriginatorName(String originatorName) {
		this.originatorName = originatorName;
	}


	/**
	 * @return the damagedFlag
	 */
	@Column(name = "DMGFLG")
	public String getDamagedFlag() {
		return damagedFlag;
	}


	/**
	 * @param damagedFlag the damagedFlag to set
	 */
	public void setDamagedFlag(String damagedFlag) {
		this.damagedFlag = damagedFlag;
	}


	/**
	 * @return the damageRemark
	 */
	@Column(name = "DMGRMK")
	public String getDamageRemark() {
		return damageRemark;
	}


	/**
	 * @param damageRemark the damageRemark to set
	 */
	public void setDamageRemark(String damageRemark) {
		this.damageRemark = damageRemark;
	}


	/**
	 * @return the oDLNCode
	 */
	@Column(name = "ODNCOD")
	public String getOdlnCode() {
		return odlnCode;
	}  


	/**
	 * @param oDLNCode the oDLNCode to set
	 */
	public void setOdlnCode(String odlnCode) {
		this.odlnCode = odlnCode;
	}
	/**
	 * @param poolOwnerFlag the poolOwnerFlag to set
	 */
	public void setPoolOwnerFlag(String poolOwnerFlag) {
		this.poolOwnerFlag = poolOwnerFlag;
	}
	
	/**
	 * 
	 * 	Method		:	ULDTransaction.getLeaseEndDate
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	getting lease end date
	 *	Parameters	:	@return 
	 *	Return type	: 	Calendar
	 */
	@Column(name = "ENDLSEDAT")
	@Temporal(TemporalType.TIMESTAMP)
	
	public Calendar getLeaseEndDate() {
		return leaseEndDate;
	}

	/**
	 * 
	 * 	Method		:	ULDTransaction.setLeaseEndDate
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	setting lease end date
	 *	Parameters	:	@param leaseEndDate 
	 *	Return type	: 	void
	 */
	public void setLeaseEndDate(Calendar leaseEndDate) {
		this.leaseEndDate = leaseEndDate;
	}
	/**
	 * 
	 * 	Method		:	ULDTransaction.getLeaseEndDateUtc
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	getting lease end date in UTC time
	 *	Parameters	:	@return 
	 *	Return type	: 	Calendar
	 */
	@Column(name = "ENDLSEDATUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLeaseEndDateUtc() {
		return leaseEndDateUtc;
	}

	/**
	 * 
	 * 	Method		:	ULDTransaction.setLeaseEndDateUtc
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	setting lease end date in UTC time
	 *	Parameters	:	@param leaseEndDateUtc 
	 *	Return type	: 	void
	 */
	public void setLeaseEndDateUtc(Calendar leaseEndDateUtc) {
		this.leaseEndDateUtc = leaseEndDateUtc;
	}


	public  ULDTransaction(ULDTransactionDetailsVO uldTransactionDetailsVO) 
		  throws SystemException { 
		  populatePk(uldTransactionDetailsVO);
		  //Added by AG for setting back the Transaction reference number to the Client

		  populateAttributes(uldTransactionDetailsVO);
		  try{
			  log.entering("CREATE CALLED FOR THE ","ULDTRANSACTION");
			 EntityManager em= PersistenceController.getEntityManager();
			 em.persist(this);
		  }catch(CreateException ex){
			 throw new SystemException(ex.getMessage(),ex);
		 }
		 uldTransactionDetailsVO.setTransactionRefNumber(this.getUldTransactionPK().getTransactionRefNumber());
	}	
	
	/**
	 * 
	 * @param uldTransactionDetailsVO
	 */
	public void populatePk(ULDTransactionDetailsVO uldTransactionDetailsVO) {
		log.entering("POPULATE THE PK FOR","ULD TRANSACTION");
		ULDTransactionPK  pk =new ULDTransactionPK();
		pk.setCompanyCode(uldTransactionDetailsVO.getCompanyCode());
		pk.setUldNumber(uldTransactionDetailsVO.getUldNumber());
		this.setUldTransactionPK(pk);
		
	}
	
	/**
	 * 
	 * @param uldTransactionDetailsVO
	 * @throws SystemException
	 */
	public void populateAttributes(ULDTransactionDetailsVO uldTransactionDetailsVO)
		throws SystemException {
		log.log(Log.FINE, "THE TRANSACTION DETAILS VO ",
				uldTransactionDetailsVO);
		log.entering("INSIDE THE POPULATE ATTRIBUTES FOR THE "," ULD TRANSACTION");
		//this.setCapturedRefNumber(uldTransactionDetailsVO.getCapturedRefNumber());
		this.setDamageStatus(uldTransactionDetailsVO.getDamageStatus());
		// Added for the time being to prevent Stale Data by A-3268 
		// To be commented once Stale Data is fixed
		LocalDate localDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		this.setLastUpdateTime(localDate);
		// Added by A-3268 ends
		this.setLastUpdateUser(uldTransactionDetailsVO.getLastUpdateUser());
		//***change
		this.setPartyCode(uldTransactionDetailsVO.getToPartyCode());	
		this.setPartyType(uldTransactionDetailsVO.getPartyType());
		this.setPaymentStatus(uldTransactionDetailsVO.getPaymentStatus());
		this.setTransactionDate(uldTransactionDetailsVO.getTransactionDate().toCalendar());
		this.setLeaseEndDate(Objects.nonNull(uldTransactionDetailsVO.getLeaseEndDate()) ? uldTransactionDetailsVO.getLeaseEndDate().toCalendar() : null);
		this.setLeaseEndDateUtc(Objects.nonNull(uldTransactionDetailsVO.getLeaseEndDate()) ? uldTransactionDetailsVO.getLeaseEndDate().toGMTDate().toCalendar() : null);
		this.setTransactionNature(uldTransactionDetailsVO.getTransactionNature());
		this.setTransactionRemark(uldTransactionDetailsVO.getTransactionRemark());
		log.log(Log.FINE,
				"uldTransactionDetailsVO.getTransactionType()======= ",
				uldTransactionDetailsVO.getTransactionType());
		if("R".equalsIgnoreCase(uldTransactionDetailsVO.getTransactionType())){
			this.setTransactionStationCode(uldTransactionDetailsVO.getTxStationCode());
		}else{
			this.setTransactionStationCode(uldTransactionDetailsVO.getTransactionStationCode());
		}
		//Added by A-2052 for the bug 103661 ends
		this.setTransactionType(uldTransactionDetailsVO.getTransactionType());
		this.setUldType(uldTransactionDetailsVO.getUldType());
		this.setTransactionStatus(uldTransactionDetailsVO.getTransactionStatus());
		this.setPartyIdentifier(String.valueOf(uldTransactionDetailsVO.getOperationalAirlineIdentifier()));
		
		//***change
		this.setPartyName(uldTransactionDetailsVO.getToPartyName());
				
		/**   NEWLY ADDED BY ASHRAF */
		
		//***change
		this.setReturnPartyCode(uldTransactionDetailsVO.getFromPartyCode());
		//Removed Dummy check  by A-7359 for ICRD-265423
		this.setReturnCRN(uldTransactionDetailsVO.getReturnCRN());
		this.setReturnPartyidentifier(uldTransactionDetailsVO.getFromPartyIdentifier());
		this.setDestinationAirport(uldTransactionDetailsVO.getTxStationCode());
		this.setControlReceiptNumber(uldTransactionDetailsVO.getControlReceiptNumber());
	//	added for bug  102920  by A-3725 starts
		if("R".equals(uldTransactionDetailsVO.getTransactionType())
				&& uldTransactionDetailsVO.getReturnDate()!=null){
		 this.setReturnDate(uldTransactionDetailsVO.getReturnDate().toCalendar());
		}
		//	added for bug  102920  by A-3725 starts
		//Added by A-2412
		this.setUldConditionCode(uldTransactionDetailsVO.getUldConditionCode());
		//added by a-3045 for CR QF1142 starts
		//For MUC Generation
		//Added the check for poolOwner aslo in MUC Generation on 03Apr09
		/*************ADDED FOR BUG 103811 STARTS*****************/
		String crn=null;
		if(uldTransactionDetailsVO.getControlReceiptNumberPrefix() != null &&
				uldTransactionDetailsVO.getControlReceiptNumberPrefix().trim().length()>0){
		int length =uldTransactionDetailsVO.getControlReceiptNumberPrefix().trim().length();
		 crn=uldTransactionDetailsVO.getControlReceiptNumberPrefix().substring(0,length-2);
		}
		log.log(Log.FINE, "crn>>>>>>>>", crn);
		/*************ADDED FOR BUG 103811 ENDS*****************/
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	String ownAirlineCode = logonAttributes.getOwnAirlineCode();
		if("L".equals(uldTransactionDetailsVO.getTransactionType())){
			if("ZZZ".equals(uldTransactionDetailsVO.getUldConditionCode()) 
					|| AGENT.equals(uldTransactionDetailsVO.getPartyType()) 
					|| OTHERS.equals(uldTransactionDetailsVO.getPartyType())
					|| "Y".equals(uldTransactionDetailsVO.getPoolOwnerFlag())
					||ownAirlineCode.equals(uldTransactionDetailsVO.getToPartyCode())){		      	
				this.setMucIsSent("N");
			}else{
				this.setMucIsSent("Q");
			}
		}else{
			this.setMucIsSent("N");
		}
	
		//added by a-3045 for CR QF1142 ends
		//this.setMucIsSent("N");
		//this.setReturnedBy(uldTransactionDetailsVO.getReturnPartyCode());
		//this.setReturnPartyidentifier(uldTransactionDetailsVO.getReturnPartyIdentifier());		
		this.setPoolOwnerFlag(uldTransactionDetailsVO.getPoolOwnerFlag());
		this.setAwbNumber(uldTransactionDetailsVO.getAwbNumber());
		this.setEmptyStatus(uldTransactionDetailsVO.getEmptyStatus());
		//Added by A-4072 for CR ICRD-192300 starts
		//Below details are only expected from screen ULD010.
		if(ULDTransactionDetailsVO.FLAG_YES.equals(uldTransactionDetailsVO.getDamageFlagFromScreen())){
			this.setDamagedFlag(ULDTransactionDetailsVO.FLAG_YES);
			this.setDamageRemark(uldTransactionDetailsVO.getDamageRemark());
			this.setOdlnCode(uldTransactionDetailsVO.getOdlnCode());
			this.setOriginatorName(uldTransactionDetailsVO.getOriginatorName());
		}else if(ULDTransactionDetailsVO.FLAG_NO.equals(uldTransactionDetailsVO.getDamageFlagFromScreen())){
			this.setDamagedFlag(ULDTransactionDetailsVO.FLAG_NO);
			this.setDamageRemark(uldTransactionDetailsVO.getDamageRemark());
			this.setOdlnCode(uldTransactionDetailsVO.getOdlnCode());
			this.setOriginatorName(uldTransactionDetailsVO.getOriginatorName());  
		}
		//Added by A-4072 for CR ICRD-192300 end
		
	}	
	
	  /**
	   * 
	   * @param companyCode
	   * @param uldNumber
	   * @param transactionRefNumber
	   * @return
	   * @throws SystemException
	   */
		public static ULDTransaction find( String companyCode, 
		        			String uldNumber,
		        int transactionRefNumber)
			throws SystemException {
			 Log loger =LogFactory.getLogger("ULD_MANAGEMENT");
			 loger.entering("ULDTransaction","find");
			ULDTransaction uLDTransaction = null;
			ULDTransactionPK uLDTransactionPK = new ULDTransactionPK();
			uLDTransactionPK.setCompanyCode(companyCode);
			uLDTransactionPK.setTransactionRefNumber(transactionRefNumber);
			uLDTransactionPK.setUldNumber(uldNumber);
			try{
				EntityManager entityManager = PersistenceController.
							getEntityManager();
				uLDTransaction = entityManager.find(ULDTransaction.class,uLDTransactionPK);
				}
				catch (FinderException finderException) {
					throw new SystemException(finderException.getErrorCode());
				}
				loger.exiting("ULDTransaction","find");
		    return uLDTransaction;
		}
		
		/**
		 * 
		 * @param uldTransactionDetailsVO
		 * @throws SystemException
		 */
		public void update(ULDTransactionDetailsVO uldTransactionDetailsVO)
			throws SystemException {
			  log.entering("UPDATE METHOD CALLED FOR THE","ULD TRANSACTION");
			  log.log(Log.FINE, "THE ULD TRANSACTION DETAILS VO IS",
					uldTransactionDetailsVO);
			this.setLastUpdateTime(uldTransactionDetailsVO.getLastUpdateTime().toCalendar());
			  this.setLastUpdateUser(uldTransactionDetailsVO.getLastUpdateUser());
		      this.setTransactionType(uldTransactionDetailsVO.getTransactionType());
		      this.setTransactionStatus(uldTransactionDetailsVO.getTransactionStatus());
		      this.setDemurrageAmount(uldTransactionDetailsVO.getDemurrageAmount());
		      this.setCurrency(uldTransactionDetailsVO.getCurrency());
		      this.setWaived(uldTransactionDetailsVO.getWaived());
		      this.setDamageStatus(uldTransactionDetailsVO.getDamageStatus());
		      this.setTaxes(uldTransactionDetailsVO.getTaxes());
		      this.setTotal(uldTransactionDetailsVO.getTotal());
		      this.setOtherCharges(uldTransactionDetailsVO.getOtherCharges()); 
		      //Added by A-3278 for bug ULD824 on 16Dec08
		      this.setTransactionDate(uldTransactionDetailsVO.getTransactionDate().toCalendar());
		      //Added by A-3278 ends
		      this.setReturnDate(uldTransactionDetailsVO.getReturnDate().toCalendar());
		      this.setReturnStationCode(uldTransactionDetailsVO.getReturnStationCode());
		      this.setAgreementNumber(uldTransactionDetailsVO.getAgreementNumber());
		      /** COMMENTED BY ASHRAF */
		      //this.setReturnedBy(uldTransactionDetailsVO.getReturnedBy());
		      this.setReturnRemarks(uldTransactionDetailsVO.getReturnRemark());
		      this.setDestinationAirport(uldTransactionDetailsVO.getTxStationCode());
		      //aysh
		      //A-5125 for ICRD-29611 transaction Air port code shoule be tansaction station code
		      this.setTransactionStationCode(uldTransactionDetailsVO.getTransactionStationCode());
				//aysh
		      // Added by A-2412
		      this.setUldConditionCode(uldTransactionDetailsVO.getUldConditionCode());
		      /*
				 * added by a-3278 for 28897 on 06Jan09
				 * a new CRN is maintained to save the latest and the old CRN seperately
				 * for return always the updation happens for returnCRN
				 */
		      //this.setControlReceiptNumber(uldTransactionDetailsVO.getControlReceiptNumber());
		      this.setReturnCRN(uldTransactionDetailsVO.getReturnCRN());
		      /*************ADDED FOR BUG 103811 STARTS*****************/
		      String crn =null;
		      if(uldTransactionDetailsVO.getControlReceiptNumberPrefix() != null && 
		    		  uldTransactionDetailsVO.getControlReceiptNumberPrefix().trim().length()>0){
		      int length =uldTransactionDetailsVO.getControlReceiptNumberPrefix().trim().length();
				 crn=uldTransactionDetailsVO.getControlReceiptNumberPrefix().substring(0,length-2);
	          	}
				log.log(Log.FINE, "crn in update>>>>>>>>", crn);
			if(!"ZZZ".equals(uldTransactionDetailsVO.getUldConditionCode()) &&
		    		  !"Y".equals(uldTransactionDetailsVO.getPoolOwnerFlag()) ){
		    	  this.setMucIsSent("Q");
		      }
		  	/*************ADDED FOR BUG 103811 ENDS*****************/
		      //Added by A-3459 as part of BUG 18657 starts
		      if("L".equals(uldTransactionDetailsVO.getTransactionType())&& (("R".equals(uldTransactionDetailsVO.getTransactionStatus()))||("I".equals(uldTransactionDetailsVO.getTransactionStatus())))){
		    	  this.setMucIsSent("N");
		      }
		      //Added by A-3459 as part of BUG 18657 ends
		      //BUG_101497_SowmyaK_11Oct10 starts
		      else if("R".equals(uldTransactionDetailsVO.getTransactionType())&& "G".equals(uldTransactionDetailsVO.getPartyType())){
		    	  this.setMucIsSent("N");
		      }
		      //BUG_101497_SowmyaK_11Oct10 ends
		    //Added by A-4072 for CR ICRD-192300 starts
				/*
				 * As part of new UCR report few fields are added in ULD010
				 * they are only required to display in Report
				 * Below details are only expected from screen ULD010.
				 */
				if(ULDTransactionDetailsVO.FLAG_YES.equals(uldTransactionDetailsVO.getDamageFlagFromScreen())){
					this.setDamagedFlag(ULDTransactionDetailsVO.FLAG_YES);
					this.setDamageRemark(uldTransactionDetailsVO.getDamageRemark());
					this.setOdlnCode(uldTransactionDetailsVO.getOdlnCode());
					this.setOriginatorName(uldTransactionDetailsVO.getOriginatorName());
				}else if(ULDTransactionDetailsVO.FLAG_NO.equals(uldTransactionDetailsVO.getDamageFlagFromScreen())){
					this.setDamagedFlag(ULDTransactionDetailsVO.FLAG_NO);
					this.setDamageRemark(uldTransactionDetailsVO.getDamageRemark());
					this.setOdlnCode(uldTransactionDetailsVO.getOdlnCode());
					this.setOriginatorName(uldTransactionDetailsVO.getOriginatorName());
				}
				//Added by A-4072 for CR ICRD-192300 end
		     
			
		      
		      //Added by Deepu for Bug 102922 starts
		      this.setTransactionRemark(uldTransactionDetailsVO.getTransactionRemark());
		      //Added by Deepu for Bug 102922 Ends
		      this.setDemurrageRate(uldTransactionDetailsVO.getDemurrageRate());
		      this.setDemurrageFrequency(uldTransactionDetailsVO.getDemurrageFrequency());
		      this.setFreeLoanPeriod(uldTransactionDetailsVO.getFreeLoanPeriod());
		      log.exiting("ULD_TRANSACTION","update(ULDTransactionDetailsVO uldTransactionDetailsVO)"+uldTransactionDetailsVO);
		}	
		
		/**
		 * This method is used to remove the business object.
		 * It interally calls the remove method within EntityManager
		 *
		 * @throws SystemException
		 */
		public void remove() throws SystemException { 
			log.log(Log.INFO,"CALLING THE REMOVE METHOD FOR THE ULDTRANSACTION");
			try {
				log.log(Log.INFO,"PersistenceController.getEntityManager().remove(this)");
				PersistenceController.getEntityManager().remove(this);
			} catch (RemoveException removeException) {
				throw new SystemException(removeException.getErrorCode(),
						removeException);
			}
		}
		
		/**
		 * Used to populate the business object with values from VO
		 *
		 * @return ULDTransactionDetailsVO
		 */
		public ULDTransactionDetailsVO retrieveVO() {
			return null;
		}		


    /**
     * This method checks if the uld is currently 
     * loaned or borrowed
     * 
     * @param companyCode
     * @param uldNumber
     * @return
     * @throws SystemException
     * @throws ULDInTransactionException
     */
    public void borrowULD(String companyCode, String uldNumber)
    throws SystemException{
    }
    
    
	/**
	 * This method is used for listing uld transaction 
	 * @author A-1883
	 *@param transactionFilterVO
	 *@return TransactionListVO
	 *@throws SystemException 
	 */

	public static TransactionListVO listULDTransactionDetails(TransactionFilterVO transactionFilterVO)
	throws SystemException{
		 Log logger =LogFactory.getLogger("ULD_MANAGEMENT");
		 logger.entering("INSIDE THE ULDTRANSACTION","listULDTransactionDetails");
 	    	return constructDAO().listULDTransactionDetails(transactionFilterVO);
	}   
	
	/* Added by A-2412 on 22 nd Oct for UCR prinintg */
	public static TransactionListVO listUCRULDTransactionDetails(TransactionFilterVO transactionFilterVO)
	throws SystemException{
		 Log logger =LogFactory.getLogger("ULD_MANAGEMENT");
		 logger.entering("INSIDE THE ULDTRANSACTION","listUCRULDTransactionDetails");
 	    	return constructDAO().listUCRULDTransactionDetails(transactionFilterVO);
	}  
	/* Addition  by A-2412 on 22 nd Oct for UCR prinintg ends*/
	
	 /**
	 * @author A-3278
	 * for QF1015 on 24Jul08
	 * to find the total demmurage calculated 
	 * @param transactionFilterVO
	 * @return double value
	 * @throws SystemException
	 */
    public static ULDTransactionDetailsVO findTotalDemmurage(
			TransactionFilterVO transactionFilterVO) throws SystemException {
		return constructDAO().findTotalDemmurage(transactionFilterVO);
	}
	/**
	 * 
	 * @param uldTransactionFilterVO
	 * @return
	 * @throws SystemException
	 */
    public static TransactionListVO findULDTransactionDetailsCol(
			TransactionFilterVO uldTransactionFilterVO)
    throws SystemException{
    	return constructDAO().findULDTransactionDetailsCol(uldTransactionFilterVO);
    }
	/**
	 * 
	 * @param companyCode
	 * @param ulds
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDTransactionDetailsVO> 
		listULDTransactionsForMUCReconciliation(String companyCode ,Set<String> ulds)
	throws SystemException{
		 Log log = LogFactory.getLogger("ULD");
		 log.entering("ULDtransaction","listULDTransactionsForMUCReconciliation");
		 try{
			return constructDAO().listULDTransactionsForMUCReconciliation(companyCode , ulds);
		 }catch(PersistenceException persistenceException){
			 throw new SystemException(persistenceException.getErrorCode());
		 }
	}
	
	 /**
	  * 
	  * @param companyCode
	  * @param uldNumber
	  * @param transactionType
	  * @return
	  * @throws SystemException
	  */
	public static boolean checkLoanedULDAlreadyLoaned(String companyCode,
			                            String uldNumber,
			                            String transactionType)throws SystemException{
		 Log loging =LogFactory.getLogger("ULD_DEFAULTS");
		 loging.entering("INSIDE THE ULDTRANSACTION","checkLoanedULDAlreadyLoaned");
		 try{
		  return constructDAO().checkLoanedULDAlreadyLoaned(companyCode,uldNumber,transactionType);
		 }catch(PersistenceException ex){
			 throw new SystemException(ex.getMessage(),ex);
		 }
	}
	 
	 

     
	 
    /**
     * 
     * @param companyCode
     * @param uldNumber
     * @param partyType
     * @param partycode
     * @param transactionType
     * @return
     * @throws SystemException
     */
    public static  boolean checkBorrowedULDLoanedToSameParty(String  companyCode,
                      String  uldNumber,
                      String  partyType,
                      String  partycode,
                      String  transactionType)
        throws SystemException{
    	   Log  logger= LogFactory.getLogger("ULD_DEFAULTS");
    	   logger.entering("INSIDE THE ULDTRANSACTION","checkBorrowedULDLoanedToSameParty");
    	   try{
           return constructDAO().checkBorrowedULDLoanedToSameParty(companyCode,uldNumber,partyType,
        		   partycode,transactionType);
      }catch(PersistenceException ex){
    	  throw new SystemException(ex.getMessage(),ex);
      }
    	   
    }
    /**
     * 
     * @return ULDDefaultsDAO
     * @throws SystemException
     */
    private static ULDDefaultsDAO constructDAO() throws SystemException {
        try {
            EntityManager em = PersistenceController.getEntityManager();
            return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
        } catch (PersistenceException ex) {
            ex.getErrorCode();
            throw new SystemException(ex.getMessage());
        }
}


	/**
	 * @return Returns the agreementNumber.
	 */
    @Column(name = "AGRMNTNUM")
	public String getAgreementNumber() {
		return agreementNumber;
	}


	/**
	 * @param agreementNumber The agreementNumber to set.
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}


	/**
	 * @return Returns the returnPartyCode.
	 */
	@Column(name = "RTNPTYCOD")
	@Audit(name="ReturnPartyCode")
	public String getReturnPartyCode() {
		return returnPartyCode;
	}


	/**
	 * @param returnPartyCode The returnPartyCode to set.
	 */
	public void setReturnPartyCode(String returnPartyCode) {
		this.returnPartyCode = returnPartyCode;
	}


	/**
	 * @return Returns the returnRemarks.
	 */
	@Column(name = "RTNRMK") 
	public String getReturnRemarks() {
		return returnRemarks;
	}


	/**
	 * @param returnRemarks The returnRemarks to set.
	 */
	public void setReturnRemarks(String returnRemarks) {
		this.returnRemarks = returnRemarks;
	}


	
	/**
	 * @return Returns the returnPartyidentifier.
	 */
	@Column(name = "RTNPTYIDR")
	public int getReturnPartyidentifier() {
		return returnPartyidentifier;
	}


	/**
	 * @param returnPartyidentifier The returnPartyidentifier to set.
	 */
	public void setReturnPartyidentifier(int returnPartyidentifier) {
		this.returnPartyidentifier = returnPartyidentifier;
	}

/***
 * 
 * @return
 */
	@Column(name = "RTNCRN")
	public String getReturnCRN() {
		return returnCRN;
	}

/***
 * 
 * @param controlReceiptNumber
 */
	public void setReturnCRN(String returnCRN) {
		this.returnCRN = returnCRN;
	}
	
/***
 * 
 * @return
 */
	@Column(name = "CRN")
	public String getControlReceiptNumber() {
		return controlReceiptNumber;
	}

/***
 * 
 * @param controlReceiptNumber
 */
	public void setControlReceiptNumber(String controlReceiptNumber) {
		this.controlReceiptNumber = controlReceiptNumber;
	}


/**
 * @return the mucDate
 */
@Column(name = "MUCDAT")
@Temporal(TemporalType.DATE)
public Calendar getMucDate() {
	return mucDate;
}


/**
 * @param mucDate the mucDate to set
 */
public void setMucDate(Calendar mucDate) {
	this.mucDate = mucDate;
}


/**
 * @return the mucReferenceNumber
 */
@Column(name = "MUCREFNUM")
public String getMucReferenceNumber() {
	return mucReferenceNumber;
}


/**
 * @param mucReferenceNumber the mucReferenceNumber to set
 */
public void setMucReferenceNumber(String mucReferenceNumber) {
	this.mucReferenceNumber = mucReferenceNumber;
}	
/**
 * 
 * 	Method		:	ULDTransaction.getDemurrageRate
 *	Added on 	:	16-Apr-2023
 * 	Used for 	:	getting demurrage rate
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
@Column(name = "DMRRAT")
	public double getDemurrageRate() {
	return demurrageRate;
}

/**
 * 
 * 	Method		:	ULDTransaction.setDemurrageRate
 *	Added on 	:	16-Apr-2023
 * 	Used for 	:	setting Demurrage Rate
 *	Parameters	:	@param demurrageRate 
 *	Return type	: 	void
 */
public void setDemurrageRate(double demurrageRate) {
	this.demurrageRate = demurrageRate;
}

/**
 * 
 * 	Method		:	ULDTransaction.getDemurrageFrequency
 *	Added on 	:	16-Apr-2023
 * 	Used for 	:	getting demurrage frequency
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
@Column(name = "DMRFQY")
public String getDemurrageFrequency() {
	return demurrageFrequency;
}

/**
 * 
 * 	Method		:	ULDTransaction.setDemurrageFrequency
 *	Added on 	:	16-Apr-2023
 * 	Used for 	:	setting demurrage frequency
 *	Parameters	:	@param demurrageFrequency 
 *	Return type	: 	void
 */
public void setDemurrageFrequency(String demurrageFrequency) {
	this.demurrageFrequency = demurrageFrequency;
}

/**
 * 
 * 	Method		:	ULDTransaction.getFreeLoanPeriod
 *	Added on 	:	16-Apr-2023
 * 	Used for 	:	getting free loan period
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
@Column(name = "FRELONPRD")
public int getFreeLoanPeriod() {
	return freeLoanPeriod;
}

/**
 * 
 * 	Method		:	ULDTransaction.setFreeLoanPeriod
 *	Added on 	:	16-Apr-2023
 * 	Used for 	:	setting free loan period
 *	Parameters	:	@param freeLoanPeriod 
 *	Return type	: 	void
 */
public void setFreeLoanPeriod(int freeLoanPeriod) {
	this.freeLoanPeriod = freeLoanPeriod;
}


	/**
	 * For InvoiceRefNumber LOV
	 * @author A-3045
	 * @param companyCode
	 * @param displayPage
	 * @param invRefNo
	 * @return Page<String>
	 * @throws SystemException
	 * @exception PersistenceException
	 */
	  public static Page<String> findMUCRefNumberLov(String companyCode,int displayPage,String mucRefNum, String mucDate)
	  throws SystemException{
		try {
		    	EntityManager em = PersistenceController.getEntityManager();
		    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(
		    			em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
		    	return uldDefaultsDAO.findMUCRefNumberLov(companyCode,displayPage,mucRefNum,mucDate);
		     }catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
	}
	  
		/**
		 *This method is used for listing Transaction details in ULD History screen
		 * @author A-3045
		 *@param transactionFilterVO
		 *@return TransactionListVO
		 *@throws SystemException 
		 */

		public static TransactionListVO findTransactionHistory(TransactionFilterVO transactionFilterVO)
		throws SystemException{
			 Log logger =LogFactory.getLogger("ULD_MANAGEMENT");
			 logger.entering("INSIDE THE ULDTRANSACTION","findTransactionHistory");
	 	    	return constructDAO().findTransactionHistory(transactionFilterVO);
		}
	/**
	 * Added for Bug 102024
	 * @author A-3268
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDTransactionDetailsVO listULDTransactionDetailsForDemurrage(TransactionFilterVO transactionFilterVO)
	throws SystemException{
		try{
			return constructDAO().listULDTransactionDetailsForDemurrage(transactionFilterVO);
		}catch(PersistenceException exception){
			throw new SystemException(exception.getErrorCode());
		}
	}
	//Added by A-3415 for ICRD-114538 starts--
   /* @Column(name = "THRPTYFLG")
    public String getThirdPartyFlag() {
		return thirdPartyFlag;
    }
    public void setThirdPartyFlag(String thirdPartyFlag) {
		this.thirdPartyFlag = thirdPartyFlag;
    }  */ 
    @Column(name = "SYSRTNFLG")
    public String getReturnCloseFlag() {
		return returnCloseFlag;
    }
    public void setReturnCloseFlag(String returnCloseFlag) {
		this.returnCloseFlag = returnCloseFlag;
    }
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<ULDTransaction> findOpenTxnULDObjects(TransactionFilterVO filterVO)
	throws SystemException,PersistenceException{
		try {
			return constructULDObjectDAO().findOpenTxnULDObjects(filterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static ULDObjectQueryInterface constructULDObjectDAO() throws SystemException,PersistenceException {
		return PersistenceController.getEntityManager().getObjectQueryDAO("uld.defaults");
	}
	/** 
	 * @param transactionFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDTransactionDetailsVO findLastTransactionsForUld(TransactionFilterVO transactionFilterVO)
	throws SystemException{
		try{
			return constructDAO().findLastTransactionsForUld(transactionFilterVO);
		}catch(PersistenceException exception){
			throw new SystemException(exception.getErrorCode());
		}
	}
  //Added by A-3415 for ICRD-114538 ends--
	  
}
