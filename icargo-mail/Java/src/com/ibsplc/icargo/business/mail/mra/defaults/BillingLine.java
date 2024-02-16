/*
 * BillingLine.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1556
 *
 */


@Table(name = "MALMRABLGMTXLIN")
@Entity
@Staleable
public class BillingLine {

    private static final String OPFLAG_INS = "I";
    private static final String OPFLAG_UPD = "U";
    private static final String YES="Y";
    private static final String NO="N";
    
    private static final Log log = LogFactory.getLogger("MRA BILLINGLINE");
    
    
	
	private BillingLinePK billingLinePK;
	
    private String billingLineStatus;
    private Calendar validityStartDate;
    private Calendar validityEndDate; 
    private String currencyCode; 
    private int airlineIdentifier;
    private String partyCode;
    private String partyType;
    private double applicableRate;
    private String revenueExpenditureFlag;
    private String billingSector;
    private String billingBasis;
    private String lastUpdatedUser;
    private Calendar lastUpdatedTime;
    private String isTaxIncludedInRateFlag;
    private String unitCode;
    private Set<BillingLineParameter> billingLineParameters;
    
    
   private Set<BillingLineCharge> billingLineCharges;
   
	
	/**
     * @return Returns the billingLinePK.
     */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="billingMatrixID", column=@Column(name="BLGMTXCOD")),
		@AttributeOverride(name="billingLineSequenceNumber", column=@Column(name="BLGLINSEQNUM"))
		}
	)
    public BillingLinePK getBillingLinePK() {
        return billingLinePK;
    }
    /**
     * @param billingLinePK The billingLinePK to set.
     */
    public void setBillingLinePK(BillingLinePK billingLinePK) {
        this.billingLinePK = billingLinePK;
    }
   
	/**
     * @return Returns the PartyCode.
     */
    @Audit(name="Blg Pty") 
    @Column(name="BILTOOPTYCOD")
    public String getPartyCode() {
        return partyCode;
    }
    /**
     * @param airlineCode The PartyCode to set.
     */
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
    /**
     * @return Returns the airlineIdentifier.
     */
    @Audit(name="airlineIdentifier") 
    @Column(name="BILPTYARLIDR")
    public int getAirlineIdentifier() {
        return airlineIdentifier;
    }
    /**
     * @param airlineIdentifier The airlineIdentifier to set.
     */
    public void setAirlineIdentifier(int airlineIdentifier) {
        this.airlineIdentifier = airlineIdentifier;
    }
    /**
     * @return Returns the applicableRate.
     */
    @Column(name="APLRAT")
    public double getApplicableRate() {
        return applicableRate;
    }
    /**
     * @param applicableRate The applicableRate to set.
     */
    public void setApplicableRate(double applicableRate) {
        this.applicableRate = applicableRate;
    }
    /**
     * @return Returns the billingBasis.
     */
    @Audit(name="Billing Basis") 
    @Column(name="BLGBAS")
    public String getBillingBasis() {
        return billingBasis;
    }
    /**
     * @param billingBasis The billingBasis to set.
     */
    
    public void setBillingBasis(String billingBasis) {
        this.billingBasis = billingBasis;
    }
    
    /**
     * @return Returns the billingLineStatus.
     */
    @Column(name="BLGLINSTA")
    public String getBillingLineStatus() {
        return billingLineStatus;
    }
    /**
     * @param billingLineStatus The billingLineStatus to set.
     */
    public void setBillingLineStatus(String billingLineStatus) {
        this.billingLineStatus = billingLineStatus;
    }
    /**
     * @return Returns the billingSector.
     */
    @Audit(name="Billed Sector") 
    @Column(name="BILSEC")
    public String getBillingSector() {
        return billingSector;
    }
    /**
     * @param billingSector The billingSector to set.
     */
    public void setBillingSector(String billingSector) {
        this.billingSector = billingSector;
    }
    /**
     * @return Returns the currencyCode.
     */
    @Audit(name="Currency")
    @Column(name="CURCOD")
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @param currencyCode The currencyCode to set.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    /**
     * @return Returns the partyType.
     */
    @Audit(name="Bill To") 
    @Column(name="BILTOOPTYTYP")
    public String getPartyType() {
        return partyType;
    }
    /**
     * @param poaCode The partyType to set.
     */
    
    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }
    /**
     * @return Returns the revenueExpenditureFlag.
     */
    @Audit(name="Receivable") 
    @Column(name="REVEXPFLG")
    public String getRevenueExpenditureFlag() {
        return revenueExpenditureFlag;
    }
    /**
     * @param revenueExpenditureFlag The revenueExpenditureFlag to set.
     */
    public void setRevenueExpenditureFlag(String revenueExpenditureFlag) {
        this.revenueExpenditureFlag = revenueExpenditureFlag;
    }
    /**
     * @return Returns the validityEndDate.
     */
    @Column(name="VLDENDDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getValidityEndDate() {
        return validityEndDate;
    }
    /**
     * @param validityEndDate The validityEndDate to set.
     */
    public void setValidityEndDate(Calendar validityEndDate) {
        this.validityEndDate = validityEndDate;
    }
    /**
     * @return Returns the validityStartDate.
     */
    @Column(name="VLDSTRDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getValidityStartDate() {
        return validityStartDate;
    }
    /**
     * @param validityStartDate The validityStartDate to set.
     */
    public void setValidityStartDate(Calendar validityStartDate) {
        this.validityStartDate = validityStartDate;
    }
	/**
	 * @return Returns the billingLineParameters.
	 */
    @Audit(name="BillingLineParameter") 
	@OneToMany
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 @JoinColumn(name = "BLGMTXCOD", referencedColumnName = "BLGMTXCOD", insertable=false, updatable=false), 
	 @JoinColumn(name = "BLGLINSEQNUM", referencedColumnName = "BLGLINSEQNUM", insertable=false, updatable=false) })
    public Set<BillingLineParameter> getBillingLineParameters() {
		return billingLineParameters;
	}

	/**
	 * @param billingLineParameters The billingLineParameters to set.
	 */
	public void setBillingLineParameters(
			Set<BillingLineParameter> billingLineParameters) {
		this.billingLineParameters = billingLineParameters;
	}
	
	
	/**
	 * @return Returns the billingLineCharges.
	 */
	@Audit(name="BillingLineCharge") 
	@OneToMany
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 @JoinColumn(name = "BLGMTXCOD", referencedColumnName = "BLGMTXCOD", insertable=false, updatable=false), 
	 @JoinColumn(name = "BLGLINSEQNUM", referencedColumnName = "BLGLINSEQNUM", insertable=false, updatable=false) })
	public Set<BillingLineCharge> getBillingLineCharges() {
		return billingLineCharges;
	}
	/**
	 * @param billingLineCharges The billingLineCharges to set.
	 */
	public void setBillingLineCharges(Set<BillingLineCharge> billingLineCharges) {
		this.billingLineCharges = billingLineCharges;
	}
    /**
	 * @return the billingLineDetails
	 */
	/*public Set<BillingLineDetail> getBillingLineDetails() {
		return billingLineDetails;
	}*/
	/**
	 * @param billingLineDetails the billingLineDetails to set
	 */
	/*public void setBillingLineDetails(Set<BillingLineDetail> billingLineDetails) {
		this.billingLineDetails = billingLineDetails;
	}*/
    /**
     * 
     */
    public BillingLine() { 

    }
    
    /**
	 * @return the lastUpdatedTime
	 */
	//@Version
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
	
	@Audit(name="Weight Unit") 
	@Column(name = "UNTCOD")
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	@Audit(name="Rate inclusive of service tax") 
	@Column(name = "RATINCSRVTAXFLG")
	public String getIsTaxIncludedInRateFlag() {
		return isTaxIncludedInRateFlag;
	}
	/**
	 * @param isTaxIncludedInRateFlag the isTaxIncludedInRateFlag to set
	 */
	public void setIsTaxIncludedInRateFlag(String isTaxIncludedInRateFlag) {
		this.isTaxIncludedInRateFlag = isTaxIncludedInRateFlag;
	}
   /**
    * @author A-2398
    * @param billingMatrixID
    * @param billingLineVO
    * @return BillingLine
    *@exception SystemException
    */
    public BillingLine(String billingMatrixID,BillingLineVO billingLineVO) throws SystemException{
    	populatePK(billingLineVO.getCompanyCode(),billingMatrixID);
    	
    	populateAttributes(billingLineVO);
    	try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
    	this.billingLinePK.getBillingLineSequenceNumber();
    	billingLineVO.setBillingLineSequenceNumber(this.billingLinePK.getBillingLineSequenceNumber());
		populateChildren(billingMatrixID,billingLineVO);
    }
    /**
     * 
     * @param companyCode
     * @param billingMatrixID
     */
    private void populatePK(String companyCode,String billingMatrixID) {

		BillingLinePK billingLinepk=new BillingLinePK(companyCode,billingMatrixID);
		this.setBillingLinePK(billingLinepk);
		log.exiting("BillingLine","populatePK");
	}
    /**
     * 
     * @param billingLineVO
     */
    private void populateAttributes(BillingLineVO billingLineVO){
    	log.entering("BillingLine","populateAttribute");
    	this.setAirlineIdentifier(billingLineVO.getAirlineIdentifier());
    	this.setApplicableRate(billingLineVO.getApplicableRate());
    	this.setBillingBasis(billingLineVO.getBillingBasis());
    	this.setBillingLineStatus(billingLineVO.getBillingLineStatus());
    	this.setBillingSector(billingLineVO.getBillingSector());
    	this.setCurrencyCode(billingLineVO.getCurrencyCode());
    	if(billingLineVO.getPoaCode()!=null){
    		this.setPartyCode(billingLineVO.getPoaCode());
    		this.setPartyType("G");
    	}else{
    		this.setPartyCode(billingLineVO.getAirlineCode());
    		this.setPartyType("A");
    	}
    	this.setRevenueExpenditureFlag(billingLineVO.getRevenueExpenditureFlag());
    	this.setValidityStartDate(billingLineVO.getValidityStartDate());
    	this.setValidityEndDate(billingLineVO.getValidityEndDate());
    	this.setLastUpdatedTime(billingLineVO.getLastUpdatedTime());
    	this.setLastUpdatedUser(billingLineVO.getLastUpdatedUser());
    	//Added for CRQ 12578
    	this.setUnitCode(billingLineVO.getUnitCode());
    	if(billingLineVO.isTaxIncludedInRateFlag()){
    		this.setIsTaxIncludedInRateFlag(YES);
    	}
    	else{
    		this.setIsTaxIncludedInRateFlag(NO);
    	}
    	
    	log.exiting("BillingLine","populateAttribute");
    }
    /**
     * @author A-2398
     * @param billingMatrixID
     * @param billingLineVO
     * @throws SystemException
     */
    private void populateChildren(String billingMatrixID,BillingLineVO billingLineVO) throws SystemException{
    	log.entering("BillingLine","populateChildren");
    	Collection<BillingLineParameterVO> parameters =
    		billingLineVO.getBillingLineParameters();
    	Collection<BillingLineParameterVO> newParams =
    		new ArrayList<BillingLineParameterVO>();
    	String key="";
    	Collection<BillingLineDetailVO>  detailVOs = billingLineVO.getBillingLineDetails();
    	BillingLineParameterVO newVO = new BillingLineParameterVO();
    	for(BillingLineParameterVO parVO: parameters){
    		if(!key.equals(parVO.getParameterCode()+parVO.getExcludeFlag())){
    			key=parVO.getParameterCode()+parVO.getExcludeFlag();
    			newVO = new BillingLineParameterVO();
    			newVO.setExcludeFlag(parVO.getExcludeFlag());
    			newVO.setParameterCode(parVO.getParameterCode());
    			newVO.setParameterValue(parVO.getParameterValue());
    			newParams.add(newVO);
    		}else{
    			newVO.setParameterValue(newVO.getParameterValue().concat(",").concat(parVO.getParameterValue()));
    		}
    	}
    	parameters = newParams;
    	
    	if(parameters != null && parameters.size() > 0){
    		if(OPFLAG_INS.equals(billingLineVO.getOperationFlag())){
    			Set<BillingLineParameter>parameterSet = new HashSet<BillingLineParameter>(); 
    			for(BillingLineParameterVO parameterVO : parameters){
    			
    				BillingLineParameter parameter = new BillingLineParameter(billingLineVO.getCompanyCode(),billingMatrixID,
    						this.getBillingLinePK().getBillingLineSequenceNumber(),parameterVO);
    				parameterSet.add(parameter);
    			}
    			this.setBillingLineParameters(parameterSet);
    	    	   			
    		}
    		else if(OPFLAG_UPD.equals(billingLineVO.getOperationFlag())){
    			Collection<BillingLineParameterVO> oldParameters =
    		    		new ArrayList<BillingLineParameterVO>();//Added by a7531 for icrd-224979
    			Set<BillingLineParameter> presentParameters =
    	    		this.getBillingLineParameters();
    			constructBlgLinParVOs(oldParameters,presentParameters);//added by a7531 for icrd-224979
    			billingLineVO.setOldBlgLinPars(oldParameters);
    			Set<BillingLineParameter>parameterSet = new HashSet<BillingLineParameter>(); 
    			for(BillingLineParameter parameter : presentParameters){
    				log
							.log(Log.INFO, "parameter being thrashed....",
									parameter);
					parameter.remove();
    				parameterSet.add(parameter);
    			}
    			this.setBillingLineParameters(parameterSet);
    			for(BillingLineParameterVO parameterVO : parameters){
        				new BillingLineParameter(billingLineVO.getCompanyCode(),billingMatrixID,
    						this.getBillingLinePK().getBillingLineSequenceNumber(),parameterVO);
    			}
    		}
    	} 
    	//Added for IASCB-26294
    	if (detailVOs!=null){
    	
        for(BillingLineDetailVO vo: detailVOs){
    	Collection<BillingLineChargeVO> billingLineChargeVOs =
    			vo.getBillingLineCharges();
		if (billingLineChargeVOs != null && billingLineChargeVOs.size() > 0) {
			if (OPFLAG_INS.equals(billingLineVO.getOperationFlag())) {
				if (billingLineChargeVOs != null
						&& billingLineChargeVOs.size() > 0) {
					Set<BillingLineCharge> billingLineSet = new HashSet<BillingLineCharge>();
					for (BillingLineChargeVO billingLineChargeVO : billingLineChargeVOs) {
						BillingLineCharge billingLineCharge;
						try {
							billingLineChargeVO.setCompanycode(billingLineVO.getCompanyCode());
							billingLineChargeVO.setBillingMatrixID(billingLineVO.getBillingMatrixId());
							billingLineChargeVO.setBillingLineSequenceNumber(billingLineVO.getBillingLineSequenceNumber());
							billingLineChargeVO.setChargeType(vo.getChargeType());
							billingLineCharge = new BillingLineCharge(billingLineChargeVO,vo.getRatingBasis());
							billingLineSet.add(billingLineCharge);
						} catch (CreateException createException) {
							throw new SystemException(createException.getErrorCode());
						}
					}
					//this.setBillingLineDetails(billingLineSet);
				}
			}else if(OPFLAG_UPD.equals(billingLineVO.getOperationFlag())){
				BillingLineDetailPK billingLineDetailPK=null;				
				BillingLineDetail billingLineDetail=null;
				
				for (BillingLineChargeVO billingLineChargeVO : billingLineChargeVOs) {
					BillingLineChargePK billingLineChargePK;
					billingLineChargeVO.setCompanycode(billingLineVO.getCompanyCode());
					billingLineChargeVO.setBillingMatrixID(billingLineVO.getBillingMatrixId());
					billingLineChargeVO.setBillingLineSequenceNumber(billingLineVO.getBillingLineSequenceNumber());
					billingLineChargeVO.setChargeType(vo.getChargeType());
					billingLineChargePK=new BillingLineChargePK();
					billingLineChargePK.setCompanyCode(billingLineChargeVO.getCompanycode());
					billingLineChargePK.setBillingMatrixID(billingLineChargeVO.getBillingMatrixID());
					billingLineChargePK.setBillingLineSequenceNumber(billingLineChargeVO.getBillingLineSequenceNumber());
					billingLineChargePK.setChargeType(billingLineChargeVO.getChargeType());
				    billingLineChargePK.setFrmWgt(billingLineChargeVO.getFrmWgt());//Modified by a7531 for icrd-224979
				    billingLineChargePK.setRatingBasis(vo.getRatingBasis());//Modified by a7531 for icrd-224979
					BillingLineCharge billingLineCharge;
					try {
						billingLineCharge=BillingLineCharge.find(billingLineChargePK);
						billingLineCharge.update(billingLineChargeVO);
					} catch (FinderException finderException) {
						try {
							new BillingLineCharge(billingLineChargeVO,vo.getRatingBasis());
						} catch (CreateException createException) {
							throw new SystemException(createException.getErrorCode());
						}
					} /*catch (RemoveException removeException) {
						throw new SystemException(removeException.getErrorCode());
					} catch (CreateException createException) {
						throw new SystemException(createException.getErrorCode());
					}*/
				}
			}
		}}}
    	
    	log.exiting("BillingLine","populateChildren");
    }
    /**
     * 
     * @param companyCode
     * @param billingMatrixId
     * @param billingLineSequenceNumber
     * @return
     * @throws SystemException
     */
    public static BillingLine find(String companyCode,
    		String billingMatrixId,int billingLineSequenceNumber)throws SystemException{
	log.entering("BillingLine","find");
		BillingLine billingLine=null;
		BillingLinePK blgLinePK=new BillingLinePK(companyCode,billingMatrixId,billingLineSequenceNumber);
		try {
			billingLine=PersistenceController.getEntityManager().find(
					BillingLine.class,blgLinePK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		log.exiting("BillingLine","find");
		return billingLine;
		
	}
    /**
     * @author A-2398
     * @param billingLineVO
     * @param billingMatrixID
     * @return void
     * @throws SystemException
     */
    public void update(String billingMatrixID,BillingLineVO billingLineVO) throws SystemException{
		log.entering("BillingLineVO","update");
		populateAttributes(billingLineVO);
		
		populateChildren(billingMatrixID,billingLineVO);
		log.exiting("BillingLineVO","update");
		
		
	}
    /**
     * 
     * @param billingLine
     * @throws SystemException
     */
    public void deleteParameters() throws SystemException{
    	Set<BillingLineParameter> parameters = this.getBillingLineParameters();
    	for(BillingLineParameter parameter : parameters ){
    		parameter.remove();
    	}
    	log.log(Log.INFO, "The parameters are.....---->>", this.getBillingLineParameters());
    }
    /** 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		log.entering("BillingLine","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		log.exiting("BillingLine","remove");
	}

	
	/**
	 * 
	 * 	Method		:	BillingLine.constructBlgLinParVOs
	 *	Added by 	:	A-7531 on 20-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@param oldParameters
	 *	Parameters	:	@param presentParameters 
	 *	Return type	: 	void
	 */
	private void constructBlgLinParVOs(Collection<BillingLineParameterVO> oldParameters,
	Set<BillingLineParameter> presentParameters){
		for(BillingLineParameter parameter : presentParameters){
			BillingLineParameterVO parvo = new BillingLineParameterVO();
			parvo.setExcludeFlag(parameter.getExcludeFlag());
			parvo.setParameterCode(parameter.getBillingLineParameterPK().getParameterCode());
			parvo.setParameterValue(parameter.getParameterValue());
			oldParameters.add(parvo);
		}
	}
}
