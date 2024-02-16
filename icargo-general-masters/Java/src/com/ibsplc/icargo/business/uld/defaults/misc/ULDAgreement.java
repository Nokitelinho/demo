/*
 * ULDAgreement.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementExceptionVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;

/**
 * @author A-1496
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDAGRMNT")
@Entity
public class ULDAgreement {
    
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	
    private String txnType;
    private String agreementStatus;
    private Calendar agreementDate;
    private String partyType;
    private String partyCode;
    private String partyName;
    //added as part of ICRD-232684 by A-4393 starts 
    private String fromPartyType;
    private String fromPartyCode;
    private String fromPartyName;
    //added as part of ICRD-232684 by A-4393 ends 
    private int freeLoanPeriod;
    private Calendar agreementFromDate;
    private Calendar agreementToDate;
    private double demurrageRate;
    private String demurrageFrequency;
    private double tax;
    private String currency;
    private String remark;
    
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;


    
    private ULDAgreementPK uldAgreementPK;
    
   // Set<ULDAgreementDetails>
    
    private Set<ULDAgreementDetails> uldAgreementDetails; 
			

    /**
     * @return Returns the agreementDate.
     */
    //@Audit(name = "agreementDate")
    @Column(name="AGRMNTDAT")	
   

	@Temporal(TemporalType.DATE)
    public Calendar getAgreementDate() {
        return agreementDate;
    }
    /**
     * @param agreementDate The agreementDate to set.
     */
    public void setAgreementDate(Calendar agreementDate) {
        this.agreementDate = agreementDate;
    }
    /**
     * @return Returns the agreementFromDate.
     */
    @Column(name="AGRMNTFRMDAT") 
   //@Audit(name = "agreementFromDate")

	@Temporal(TemporalType.DATE)
    public Calendar getAgreementFromDate() {
        return agreementFromDate;
    }
    /**
     * @param agreementFromDate The agreementFromDate to set.
     */
    public void setAgreementFromDate(Calendar agreementFromDate) { 
        this.agreementFromDate = agreementFromDate;
    }
    /**
     * @return Returns the agreementStatus.
     */
    @Column(name="AGRMNTSTA")    
    @Audit(name="agreementStatus")
    public String getAgreementStatus() {
        return agreementStatus;
    }
    /**
     * @param agreementStatus The agreementStatus to set.
     */
    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
    }
    /**
     * @return Returns the agreementToDate.
     */
    @Column(name="AGRMNTTOODAT")   
    //@Audit(name="agreementToDate")

	@Temporal(TemporalType.DATE)
    public Calendar getAgreementToDate() {
        return agreementToDate;
    }
    /**
     * @param agreementToDate The agreementToDate to set.
     */
    public void setAgreementToDate(Calendar agreementToDate) {
        this.agreementToDate = agreementToDate;
    }
    /**
     * @return Returns the currency.
     */
    @Column(name="CURCOD")  
    @Audit(name="currency")
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
     * @return Returns the demurrageFrequency.
     */
    @Column(name="DMRFQY")    
     @Audit(name="demurrageFrequency")
    public String getDemurrageFrequency() {
        return demurrageFrequency;
    }
    /**
     * @param demurrageFrequency The demurrageFrequency to set.
     */
    public void setDemurrageFrequency(String demurrageFrequency) {
        this.demurrageFrequency = demurrageFrequency;
    }
    /**
     * @return Returns the demurrageRate.
     */
    @Column(name="DMRRAT")  
     @Audit(name="demurrageRate")
    public double getDemurrageRate() {
        return demurrageRate;
    }
    /**
     * @param demurrageRate The demurrageRate to set.
     */
    public void setDemurrageRate(double demurrageRate) {
        this.demurrageRate = demurrageRate;
    }
    /**
     * @return Returns the freeLoanPeriod.
     */
    @Column(name="FRELONPRD")   
     @Audit(name="freeLoanPeriod")
    public int getFreeLoanPeriod() {
        return freeLoanPeriod;
    }
    /**
     * @param freeLoanPeriod The freeLoanPeriod to set.
     */
    public void setFreeLoanPeriod(int freeLoanPeriod) {
        this.freeLoanPeriod = freeLoanPeriod;
    }
    /**
     * @return Returns the partyCode.
     */
    @Column(name="PTYCOD")   
    @Audit(name="partyCode")
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
     * @return Returns the partyType.
     */
    @Column(name="PTYTYP")  
    @Audit(name="partyType")
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
     * @return Returns the fromPartyCode.
     */
    @Column(name="FRMPTYCOD")   
    @Audit(name="fromPartyCode")
    public String getFromPartyCode() {
        return fromPartyCode;
    }
    /**
     * @param fromPartyCode The fromPartyCode to set.
     */
    public void setFromPartyCode(String fromPartyCode) {
        this.fromPartyCode = fromPartyCode;
    }
    /**
     * @return Returns the fromPartyName.
     */
    @Column(name="FRMPTYNAM")    
    public String getFromPartyName() {
        return fromPartyName;
    }
    /**
     * @param fromPartyName The fromPartyName to set.
     */
    public void setFromPartyName(String fromPartyName) {
        this.fromPartyName = fromPartyName;
    }
    /**
     * @return Returns the partyType.
     */
    @Column(name="FRMPTYTYP")  
    @Audit(name="fromPartyType")
    public String getFromPartyType() {
        return fromPartyType;
    }
    /**
     * @param partyType The partyType to set.
     */
    public void setFromPartyType(String fromPartyType) {
        this.fromPartyType = fromPartyType;
    }
    /**
     * @return Returns the remark.
     */
    @Column(name="AGRMNTRMK")    
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the tax.
     */
    @Column(name="TAXAMT")   
    @Audit(name="tax")
    public double getTax() {
        return tax;
    }
    /**
     * @param tax The tax to set.
     */
    public void setTax(double tax) {
        this.tax = tax;
    }
    /**
     * @return Returns the txnType.
     */
    @Column(name="TXNTYP")    
    @Audit(name="txnType")
    public String getTxnType() {
        return txnType;
    }
    /**
     * @param txnType The txnType to set.
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    

    /**
     * @return Returns the agreementDate.
     */
    /**
     * @return Returns the uldAgreementDetails.
     */

      @OneToMany
      @JoinColumns( {
 	  @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
 	  @JoinColumn(name = "AGRMNTNUM", referencedColumnName = "AGRMNTNUM", insertable=false, updatable=false)})
 	 
      public Set<ULDAgreementDetails> getUldAgreementDetails() {
 		  return uldAgreementDetails;
 	  }
    /**
     * @param uldAgreementDetails The uldAgreementDetails to set.
     */
 	  public void setUldAgreementDetails(Set<ULDAgreementDetails> uldAgreementDetails) {
 		  this.uldAgreementDetails = uldAgreementDetails;
 	  }
 	  /**
 	   * @return Returns the uldAgreementPK.
 	   */

    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),		
		@AttributeOverride(name="agreementNumber", column=@Column(name="AGRMNTNUM"))})  
    public ULDAgreementPK getUldAgreementPK() {
        return uldAgreementPK;
    }
    /**
     * @param uldAgreementPK The uldAgreementPK to set.
     */
    public  void setUldAgreementPK(ULDAgreementPK uldAgreementPK) {
        this.uldAgreementPK = uldAgreementPK;
       
    }

    /**
     * @return Returns the lastUpdatedTime.
     */
    @Version
    @Column(name="LSTUPDTIM")    
	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    /**
     * @param lastUpdatedTime The lastUpdatedTime to set.
     */
    public void setLastUpdatedTime(Calendar lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    /**
     * @return Returns the lastUpdatedUser.
     */
    @Column(name="LSTUPDUSR")    
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }
    /**
     * @param lastUpdatedUser The lastUpdatedUser to set.
     */
    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
    
    
    /*This method is used for listing uld agreement in the system
	 * for a ULD Transaction
	 *
	 *@param String ULDAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 * 
	 *@throws SystemException 
	 */
	//To be reviewed Page<ULDAgreementVO>
	/*public Page findULDAgreementsForTransaction(ULDAgreementFilterVO uldAgreementFilterVO)
	throws SystemException{
	    return null;
	}*/
    
    
    /*This method is used for finding the Agreement for
     * for a ULD Transaction
	 *
	 *@param String ULDAgreementFilterVO
	 *@return ULDAgreementVO
	 * 
	 *@throws SystemException 
	 */
    /**
	 * Constructor
	 */
	public ULDAgreement() {
	}
	
	/**
	 * Constructor
	 *
	 * @param uldAgreementVO
	 * @throws SystemException
	 */
	public ULDAgreement(ULDAgreementVO uldAgreementVO) 
		throws SystemException {	
    	log.entering("ULDAgreement","Constructor");
		populatePk(uldAgreementVO);
		populateAttributes(uldAgreementVO);
		try{
		  PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());	
		}
		log.log(Log.INFO,"INSERTED MAIN");
		if(uldAgreementVO.getUldAgreementDetailVOs()!= null && 
				uldAgreementVO.getUldAgreementDetailVOs().size()>0){
			populateChildren(uldAgreementVO);
		}
		String agrNumber = this.getUldAgreementPK().getAgreementNumber();		
		uldAgreementVO.setAgreementNumber(agrNumber);
	}
	
	/**
	 * private method to populate PK
	 *
	 * @param uldAgreementVO
	 */
	public void populatePk(ULDAgreementVO uldAgreementVO) {
		log.entering("PopulateULDAgreement","populatePk");
		ULDAgreementPK uldAgreementPk = new ULDAgreementPK();
		uldAgreementPk.setCompanyCode(uldAgreementVO.getCompanyCode());
		uldAgreementPk.setAgreementNumber(uldAgreementVO.getAgreementNumber());
		setUldAgreementPK(uldAgreementPk);
	}

	/**
	 * private method to populate attributes
	 *
	 * @param uldAgreementVO
	 * @throws SystemException
	 */
	public void populateAttributes(ULDAgreementVO uldAgreementVO)
		throws SystemException {
		log.entering("PopulateULDAgreementAttributes","populateAttributes");
		setTxnType(uldAgreementVO.getTxnType());
		setAgreementStatus(uldAgreementVO.getAgreementStatus());
		setAgreementDate(uldAgreementVO.getAgreementDate()==null?null:uldAgreementVO.getAgreementDate().toCalendar());
		setPartyType(uldAgreementVO.getPartyType());
		setPartyCode(uldAgreementVO.getPartyCode());
		setPartyName(uldAgreementVO.getPartyName());
		setFromPartyType(uldAgreementVO.getFromPartyType());
		setFromPartyCode(uldAgreementVO.getFromPartyCode());
		setFromPartyName(uldAgreementVO.getFromPartyName());
		setFreeLoanPeriod(uldAgreementVO.getFreeLoanPeriod());
		LocalDate frmDate = new LocalDate(uldAgreementVO.getAgreementFromDate() , false);
		LocalDate toDate = null;
		if(uldAgreementVO.getAgreementToDate() != null){
			toDate = new LocalDate(uldAgreementVO.getAgreementToDate() , false);
		}
		//setAgreementFromDate(uldAgreementVO.getAgreementFromDate().toCalendar());
		//setAgreementToDate(uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());
		setAgreementFromDate(frmDate);
		setAgreementToDate(toDate==null?null:toDate);
		setDemurrageRate(uldAgreementVO.getDemurrageRate());
		setDemurrageFrequency(uldAgreementVO.getDemurrageFrequency());
		setTax(uldAgreementVO.getTax());
		setCurrency(uldAgreementVO.getCurrency());
		setRemark(uldAgreementVO.getRemark());
		setLastUpdatedUser(uldAgreementVO.getLastUpdatedUser());		
		log.exiting("PopulateULDAgreementAttributes","populateAttributes");
		
	}
	
	/**
	 * This method invokes populate ULDAgreement 
	 * @param uldAgreementVO
	 * @throws SystemException
	 */	
	public void populateChildren(ULDAgreementVO uldAgreementVO)
		throws SystemException {
		log.entering("populateChildren","populateChildren");
		Collection<ULDAgreementDetailsVO> uldAgreementDetailsVos = uldAgreementVO.getUldAgreementDetailVOs();
		for(ULDAgreementDetailsVO uldAgreementDetailsVO: uldAgreementDetailsVos){
			ULDAgreementDetails uLDAgreementDetails = 
					new ULDAgreementDetails(
							uldAgreementPK.getCompanyCode(),
							uldAgreementPK.getAgreementNumber(),
							uldAgreementDetailsVO);
			 try{
				 PersistenceController.getEntityManager().persist(uLDAgreementDetails);
			 }catch(CreateException createException){
				 throw new SystemException(createException.getErrorCode());
			 }
		}
		
	}	
 
  /**
   * This method is used to populate the ULD Agreement Details
   * 
   * @param companyCode
   * @param agreementNumber
   * @param Collection<ULDAgreementDetailsVO> uldAgreementDetailsVOs
   * @throws SystemException
   */
 /* private void populateULDAgreementDetails(String companyCode, String agreementNumber,
          Collection uldAgreementDetailsVOs) throws SystemException {
      
  }*/
  
  
  
  /**
   * This method updates the ULD Agreement Details for the ULD Agreement
   * 
   * @param uldAgreementDetailsVO
   * @throws SystemException
   */
  /*private void updateULDAgreementDetails(ULDAgreementDetailsVO uldAgreementDetailsVO)
	throws SystemException {
      
  }*/
  
  /**
   * This method deletes the Agreement Details for the ULD Agreement
   * @param uldAgreementDetailsVO
   * @throws SystemException
   */
 /* private void removeULDAgreementDetails(ULDAgreementDetailsVO uldAgreementDetailsVO)
	throws SystemException {
     
 }*/
  
  /**
	 * This method is used for populating the ULD Agreements
	 *
	 *@param  companyCode
	 *@param pageNumber
	 *@return Page<ULDAgreementVO>
	 *@throws SystemException
	 */
	//To be reviewed Collection<Strings>
	public static Page<ULDAgreementVO> populateULDAgreementLOV(ULDAgreementFilterVO uldAgreementFilterVO)
	throws SystemException{
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDAgreement","populateULDAgreementLOV");
		EntityManager em = PersistenceController.getEntityManager();
		try{
			ULDDefaultsDAO uldDefaultsDAO = 
				ULDDefaultsDAO.class.cast(em.getQueryDAO(
						ULDDefaultsPersistenceConstants.MODULE_NAME));
				return uldDefaultsDAO.populateULDAgreementLOV(uldAgreementFilterVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
    /**
     * This method is used for listing uld agreement in the system
	 *@param uldAgreementFilterVO
	 *@return Page<ULDAgreementVO>
	 *@throws SystemException 
	 */

	//To be reviewed Page<ULDAgreementVO>
	public static Page<ULDAgreementVO> listULDAgreements(ULDAgreementFilterVO uldAgreementFilterVO)
	throws SystemException{
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDAgreement","listULDAgreements");
		EntityManager em = PersistenceController.getEntityManager();
		try{
			ULDDefaultsDAO uldDefaultsDAO = 
				ULDDefaultsDAO.class.cast(em.getQueryDAO(
						ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uldDefaultsDAO.listULDAgreements(uldAgreementFilterVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
		

	

	
    /**
     * This method is used to find the ULDType details
     * @param  companyCode
     * @param  agreementNumber
     * @return ULDAgreementVO
     * 
     * @throws SystemException
     */
    public static ULDAgreementVO  findULDAgreementDetails(String companyCode, String agreementNumber)
    	throws SystemException{
    	ULDAgreementVO vo = null;
    	 try {
 	    	EntityManager em = PersistenceController.getEntityManager();
 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
 	    	return uldDefaultsDAO.findULDAgreementDetails(companyCode, agreementNumber);
 	     }catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
    }	
    
    //Added by A-8445 as a part of IASCB-28460 Starts
    /**
     * This method is used to find the ULDType details
     * @param  companyCode
     * @param  agreementNumber
     * @param  uldAgreementFilterVO
     * @return Page<ULDAgreementVO>
     * 
     * @throws SystemException
     */
    public static Page<ULDAgreementDetailsVO> findULDAgreementDetailsPagination(String companyCode, String agreementNumber,ULDAgreementFilterVO uldAgreementFilterVO)
    	throws SystemException{
    	 try {
 	    	EntityManager em = PersistenceController.getEntityManager();
 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
 	    	return uldDefaultsDAO.findULDAgreementDetailsPagination(companyCode, agreementNumber, uldAgreementFilterVO);
 	     }catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
    }	
    //Added by A-8445 as a part of IASCB-28460 Ends
  
  /**
   * This method finds the ULDAgreement instance based on the AgreementPK
   * 
   * @param companyCode
   * @param agreementNumber
   * @return
   * @throws SystemException
   */
	public static ULDAgreement find( String companyCode, String agreementNumber)
		throws SystemException {
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDAgreement","find");
		ULDAgreement uldAgreement = null ;
    	try {
	    	EntityManager em  = PersistenceController.getEntityManager() ;
	    	ULDAgreementPK uldAgreementPk = new ULDAgreementPK() ;	
	    	log.log(Log.INFO,"!!!!companyCode");
	    	log.log(Log.INFO,"!!!!agreementNumber");
	    	uldAgreementPk.setCompanyCode(companyCode );
	    	uldAgreementPk.setAgreementNumber(agreementNumber );
	
	    	uldAgreement = em.find(ULDAgreement.class , uldAgreementPk);
    	}catch(FinderException finderException){
    		uldAgreement = null ;
    		throw new SystemException(finderException.getErrorCode());
    		
    	}
    	return uldAgreement ;
    }
	
		
	
	
	/**
	 * method to update the BO
	 *
	 * @param uldAgreementVO
	 * @throws SystemException
	 */
	public void update(ULDAgreementVO uldAgreementVO)
		throws SystemException {		
		Collection<ULDAgreementDetailsVO> uldAgreementDetailsVOs =new ArrayList <ULDAgreementDetailsVO>();
		log.entering("ULDAgreement","update"+uldAgreementVO);

		this.setTxnType(uldAgreementVO.getTxnType());
		this.setAgreementStatus(uldAgreementVO.getAgreementStatus());
		this.setAgreementDate(uldAgreementVO.getAgreementDate()==null?null:uldAgreementVO.getAgreementDate().toCalendar());
		this.setPartyType(uldAgreementVO.getPartyType());
		this.setPartyCode(uldAgreementVO.getPartyCode());
		this.setPartyName(uldAgreementVO.getPartyName());
		this.setFromPartyType(uldAgreementVO.getFromPartyType());
		this.setFromPartyCode(uldAgreementVO.getFromPartyCode());
		this.setFromPartyName(uldAgreementVO.getFromPartyName());
		this.setFreeLoanPeriod(uldAgreementVO.getFreeLoanPeriod());
		this.setAgreementFromDate(uldAgreementVO.getAgreementFromDate().toCalendar());
		this.setAgreementToDate(uldAgreementVO.getAgreementToDate()==null?null:uldAgreementVO.getAgreementToDate().toCalendar());
		this.setDemurrageRate(uldAgreementVO.getDemurrageRate());
		this.setDemurrageFrequency(uldAgreementVO.getDemurrageFrequency());
		this.setTax(uldAgreementVO.getTax());
		this.setCurrency(uldAgreementVO.getCurrency());
		this.setRemark(uldAgreementVO.getRemark());
		
		log.log(Log.INFO, "\n befor update this.getLastUpdatedTime------->",
				this.getLastUpdatedTime());
		log.log(Log.INFO, "\n uldAgreementVO.getLastUpdatedTime()------->",
				uldAgreementVO.getLastUpdatedTime());
		this.setLastUpdatedUser(uldAgreementVO.getLastUpdatedUser());
		this.setLastUpdatedTime(uldAgreementVO.getLastUpdatedTime().toCalendar());
		log.log(Log.INFO, "\n after update this.getLastUpdatedTime------->",
				this.getLastUpdatedTime());
		log.log(Log.INFO,"\n\nBefore Going to save the Children");
		if(uldAgreementVO.getUldAgreementDetailVOs()!= null && 
		uldAgreementVO.getUldAgreementDetailVOs().size()>0){
			log.log(Log.INFO,"--->>>Size");
			uldAgreementDetailsVOs = uldAgreementVO.getUldAgreementDetailVOs();
			for(ULDAgreementDetailsVO uldAgreementDetailsVO :
				uldAgreementDetailsVOs){
				log.log(Log.INFO,"\nChild Operation Flag");
				if(ULDAgreementDetailsVO.OPERATION_FLAG_DELETE.
	    				equals(uldAgreementDetailsVO.getOperationFlag())){
	    			log.log(Log.INFO,"--->>>DELETION STARTS");
	    			log.log(Log.INFO,"FLAG---->>>>");
	    			ULDAgreementDetails uLDAgreementDetails =
	    				ULDAgreementDetails.find(
	    						uldAgreementDetailsVO.getCompanyCode(),
	    						uldAgreementDetailsVO.getAgreementNumber(),
	    						uldAgreementDetailsVO.getSequenceNumber());	    					
	    			uLDAgreementDetails.remove();
	    				}
	    		}				
				for(ULDAgreementDetailsVO uldAgreementDetailsVO :
				uldAgreementDetailsVOs){
				if(ULDAgreementDetailsVO.OPERATION_FLAG_UPDATE.
    				equals(uldAgreementDetailsVO.getOperationFlag())){
					log.log(Log.INFO,"---->>>UPDATION STARTS");
					log.log(Log.INFO,"FLAG---->>>>");
					ULDAgreementDetails uLDAgreementDetails =
    				ULDAgreementDetails.find(
    						uldAgreementDetailsVO.getCompanyCode(),
    						uldAgreementDetailsVO.getAgreementNumber(),
    						uldAgreementDetailsVO.getSequenceNumber());
    					
					uLDAgreementDetails.update(uldAgreementDetailsVO);
    				}
				}
			
			for(ULDAgreementDetailsVO uldAgreementDetailsVO :
				uldAgreementDetailsVOs){
				if(ULDAgreementDetailsVO.OPERATION_FLAG_INSERT.
						equals(uldAgreementDetailsVO.getOperationFlag())){
					log.log(Log.INFO,"INSERTION STARTS");
					log.log(Log.INFO,"FLAG---->>>>");
					new ULDAgreementDetails(uldAgreementVO.getCompanyCode(),uldAgreementVO.getAgreementNumber(),uldAgreementDetailsVO);
				}
			}
		}
		
		
}
	    
	/**
	 * This method is used to remove the business object.
	 * It interally calls the remove method within EntityManager
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("ULDAgreement","remove");
		log.log(Log.INFO,"Going to Remove ULDAgreement");
		try{
    		PersistenceController.getEntityManager().remove(this);
    	}
    	catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
    }

	
	/**
	 * private method to remove children
	 * 
	 * @throws SystemException
	 */
	/*private void removeChildren() throws SystemException {
	    
	}*/
	
    /**
     * This method is used to update the status of ULD Agreement to
     * the changed status.ie Active to Inactive and ViceVeraa
     * 
     * @param companyCode
     * @param agreementNumber
     * @param changedStatus
     * @return 
     * @throws SystemException
     */

   /* public void updateULDAgreementStatus(
            String companyCode, String agreementNumber,
            String changedStatus) 
    throws SystemException{
    	log.entering("ULDAgreement","updateULDAgreementStatus");
    	setAgreementStatus(changedStatus);
    	
    }*/
	
	 public void updateULDAgreementStatus(ULDAgreementVO vo,String changedStatus) throws SystemException{
	    	log.entering("ULDAgreement","updateULDAgreementStatus");
	    	setAgreementStatus(changedStatus);
	    	setLastUpdatedTime(vo.getLastUpdatedTime());
	    	setLastUpdatedUser(vo.getLastUpdatedUser());
	    	
	    }
	/**
	 * Used to populate the business object with values from VO
	 *
	 * @return ULDAgreementVO
	 */
	public ULDAgreementVO retrieveVO() {
		return null;
	}
	    
	/**
	 * 
	 * @param uldAgreementFilterVO
	 * @return ULDAgreementVO
	 * @throws SystemException
	 */

	public static  ULDAgreementVO findULDAgreementForReturnTransaction(ULDAgreementFilterVO uldAgreementFilterVO)
	throws SystemException{
		try {
 	    	EntityManager em = PersistenceController.getEntityManager();
 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
 	    	return uldDefaultsDAO.findULDAgreementForReturnTransaction(uldAgreementFilterVO);
 	     }catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
	}
	
	/**
	 * 
	 * @param uldAgreementVO
	 * @return Collection<ULDAgreementExceptionVO> 
	 * @throws SystemException
	 */
    
	public static Collection<ULDAgreementExceptionVO> 
		checkULDAgreementAlreadyExists(ULDAgreementVO uldAgreementVO)
	throws SystemException{
		try {
 	    	EntityManager em = PersistenceController.getEntityManager();
 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
 	    	return uldDefaultsDAO.checkULDAgreementDetails(uldAgreementVO);
 	     }catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
	}
	/**
	 * 
	 * @param uldAgreementVO
	 * @return
	 * @throws SystemException
	 */
	public static String checkForInvoice(ULDAgreementVO uldAgreementVO) 
    throws SystemException{
		try {
 	    	EntityManager em = PersistenceController.getEntityManager();
 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
 	    	return uldDefaultsDAO.checkForInvoice(uldAgreementVO);
 	     }catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
	}
	
	 /**
     * This method is used for listing collection of uld agreement in the system,bug25282
     * @author A-3045
	 * @param uldAgreementFilterVO
	 * @return Collection<ULDAgreementVO>
	 * @throws SystemException 
	 */
	public static Collection<ULDAgreementVO> listULDAgreementsColl(ULDAgreementFilterVO uldAgreementFilterVO)
	throws SystemException{
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDAgreement","listULDAgreementsColl");
		EntityManager em = PersistenceController.getEntityManager();
		try{
			ULDDefaultsDAO uldDefaultsDAO = 
				ULDDefaultsDAO.class.cast(em.getQueryDAO(
						ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uldDefaultsDAO.listULDAgreementsColl(uldAgreementFilterVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
