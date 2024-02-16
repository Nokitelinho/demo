/*
 * MRAMemoInInvoice.java Created on Feb15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

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

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2407 
 */

@Entity
@Table(name = "MALMRAARLMEMINV")

public class MRAMemoInInvoice {
	
	private static final String MODULE_NAME = "mail.mra.airlinebilling";
	
	private static final String CLASS_NAME = "MRAMemoInInvoice";
	
	private static final String MRA_ARL_BLG_REJECTION_MEMO_NO =
        "MRA_ARL_BLG_REJECTION_MEMO_NO";
	
	private Log log = localLogger();
	
	/*
	 * method to get instance of log
	 */
	private static Log localLogger() {
		return LogFactory.getLogger("MRA AIRLINEBILLING");
	}
	
	private MRAMemoInInvoicePK mraMemoInInvoicePK;

	private String airlineCode;
	
	private String remarks;
	
	private double provisionalAmount;
	
	private double reportedAmount;
	
	private double differenceAmount;
	
	private String contractCurrCode;
	
	private Calendar memoDate;
	
	private String lastUpdatedUser;
	
	private Calendar lastUpdatedTime;
	
	
	
	
	/**
	 * @return Returns the mraMemoInInvoicePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
		@AttributeOverride(name = "interlineBlgType", column = @Column(name = "INTBLGTYP")),
		@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM")),
		@AttributeOverride(name = "memoCode", column = @Column(name = "MEMCOD")),
		@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD"))
		 })
	public MRAMemoInInvoicePK getMraMemoInInvoicePK() {
		return mraMemoInInvoicePK;
	}

	/**
	 * @param mraMemoInInvoicePK The mraMemoInInvoicePK to set.
	 */
	public void setMraMemoInInvoicePK(MRAMemoInInvoicePK mraMemoInInvoicePK) {
		this.mraMemoInInvoicePK = mraMemoInInvoicePK;
	}
	
	/**
	 * @return the lastUpdatedTime
	 */
	@Version
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
	
	
	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	
	
	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	/**
	 * @return Returns the provisionalAmount.
	 */
	//@Column(name = "PVNAMT")
	@Column(name = "PVNAMTLSTCUR")   //Modified by A-7929 as part of ICRD_265471
	public double getProvisionalAmount() {
		return provisionalAmount;
	}

	/**
	 * @param provisionalAmount The provisionalAmount to set.
	 */
	public void setProvisionalAmount(double provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}
	
	/**
	 * @return Returns the reportedAmount.
	 */
	//@Column(name = "RPDAMT")
	@Column(name = "RPDAMTLSTCUR")               //Modified by A-7929 as part of ICRD_265471
	public double getReportedAmount() {
		return reportedAmount;
	}

	/**
	 * @param reportedAmount The reportedAmount to set.
	 */
	public void setReportedAmount(double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}
	
	/**
	 * @return Returns the differenceAmount.
	 */
	//@Column(name = "DIFAMT")                //Modified by A-7929 as part of ICRD_265471
	@Column(name = "DIFAMTLSTCUR")
	public double getDifferenceAmount() {
		return differenceAmount;
	}

	/**
	 * @param differenceAmount The differenceAmount to set.
	 */
	public void setDifferenceAmount(double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}
	
	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "CRTCURCOD")
	public String getContractCurrCode() {
		return contractCurrCode;
	}

	/**
	 * @param contractCurrCode The contractCurrCode to set.
	 */
	public void setContractCurrCode(String contractCurrCode) {
		this.contractCurrCode = contractCurrCode;
	}
	
	/**
	 * @return Returns the memoDate.
	 */
	@Column(name = "MEMDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getMemoDate() {
		return memoDate;
	}

	/**
	 * @param memoDate The memoDate to set.
	 */
	public void setMemoDate(Calendar memoDate) {
		this.memoDate = memoDate;
	}
	/**
    *
    * @param 
    *
    */
	public MRAMemoInInvoice(){
		
	}
	/**
    * @return MRAMemoInInvoice
    * @param memoInInvoiceVO
    * @throws SystemException
    */
	public MRAMemoInInvoice(MemoInInvoiceVO memoInInvoiceVO) throws SystemException {
        log.entering("MRAMemoInInvoice", "MRAMemoInInvoice");
        if(memoInInvoiceVO!= null){
	        populatePK(memoInInvoiceVO);
	        populateAttributes(memoInInvoiceVO);
	        try {
	            PersistenceController.getEntityManager().persist(this);
	        } catch(CreateException createException) {
	            throw new SystemException(createException.getErrorCode(),
	                    createException);
	        }
        }
        log.exiting("MRAMemoInInvoice", "MRAMemoInInvoice");
    }
	
	private void populatePK(MemoInInvoiceVO memoInInvoiceVO) 
	throws SystemException{
        log.entering("MRAMemoInInvoice", "populatePK");
        this.setMraMemoInInvoicePK(
        		new MRAMemoInInvoicePK(
        				memoInInvoiceVO.getCompanyCode(), 
        				memoInInvoiceVO.getAirlineIdentifier(),
        				generateMemoCode(memoInInvoiceVO), 
        				memoInInvoiceVO.getInvoiceNumber(), 
        				memoInInvoiceVO.getInterlineBlgType(),
        				memoInInvoiceVO.getClearancePeriod()));
        log.exiting("MRAMemoInInvoice", "populatePK");
	}
	/**
    * this method is used to generate memo code
    * @param memoInInvoiceVO
    * @return String
    * @throws SystemException
    */
   private String generateMemoCode(MemoInInvoiceVO memoInInvoiceVO) throws SystemException {
       log.entering("MRAMemoInInvoice", "generateMemoNUmber");
       Criterion criterion = null;
       String generatedKey = null;
       String key = null;
      // StringBuffer criterionKeyType = new StringBuffer();
      // criterionKeyType.append(MRA_ARL_BLG_REJECTION_MEMO_NO).append(memoInInvoiceVO.getClearancePeriod());
     
           StringBuilder keyBuilder = new StringBuilder(5);
           criterion = KeyUtils.getCriterion(memoInInvoiceVO.getCompanyCode(),
        		   MRA_ARL_BLG_REJECTION_MEMO_NO);
           generatedKey = KeyUtils.getKey(criterion);
           int keyLength = generatedKey.length();
           for(int count = 0;
               count < MemoInInvoiceVO.MEMO_NUMBER_KEY_LENGTH - keyLength ;
               count++) {
               keyBuilder.append("0");
           }
           keyBuilder.append(generatedKey);
           key = new StringBuilder().append(MemoInInvoiceVO.PREFIX_REJECTION_MEMO)
           			.append(memoInInvoiceVO.getAirlineCode())
                    .append(memoInInvoiceVO.getClearancePeriod())
                    .append("INV")
                    .append(keyBuilder).toString();
                    
       

       log.log(Log.INFO, new StringBuilder().append("Generated Key -->")
               .append(key).toString());
       log.exiting("MRAMemoInInvoice", "generateMemoNUmber");
       return key;
   }
	private void populateAttributes(MemoInInvoiceVO memoInInvoiceVO) {
        log.entering("MRAMemoInInvoice", "populateAttributes");
        this.setAirlineCode(memoInInvoiceVO.getAirlineCode());
        this.setRemarks(memoInInvoiceVO.getRemarks());
        this.setProvisionalAmount(memoInInvoiceVO.getProvisionalAmount());
        this.setReportedAmount(memoInInvoiceVO.getReportedAmount());
        this.setDifferenceAmount(memoInInvoiceVO.getDifferenceAmount());
        this.setContractCurrCode(memoInInvoiceVO.getContractCurrCode());
        this.setMemoDate(memoInInvoiceVO.getMemoDate());
        this.setLastUpdatedUser(memoInInvoiceVO.getLastUpdatedUser());
        if(memoInInvoiceVO.getLastUpdatedTime()!=null){
        	this.setLastUpdatedTime(memoInInvoiceVO.getLastUpdatedTime());
        }
        log.exiting("MRAMemoInInvoice", "populateAttributes");
	}
	/**
    * method to find
    * @return MRAMemoInInvoice
    * @param companyCode
    * @param airlineIdentifier
    * @param memoCode
    * @param invoiceNumber
    * @param clearancePeriod
    * @param interlineBlgType
    * @throws SystemException
    * @throws FinderException
    */
	 public static MRAMemoInInvoice find(String companyCode, int airlineIdentifier,
             				String memoCode, String invoiceNumber, String interlineBlgType , String clearancePeriod)
	 throws SystemException, FinderException {
		 localLogger().entering(CLASS_NAME,"find");
		 return PersistenceController.getEntityManager().find(MRAMemoInInvoice.class,
				 new MRAMemoInInvoicePK(companyCode, airlineIdentifier, 
						 				memoCode, invoiceNumber, interlineBlgType , clearancePeriod));
	 }
	 /**
	    * method to update 
	    * @return
	    * @param memoInInvoiceVO
	    * @throws SystemException
	    */
	 public void update(MemoInInvoiceVO memoInInvoiceVO) throws SystemException {
	        log.entering("MRAMemoInInvoice", "update");
	        populateAttributes(memoInInvoiceVO);
	        log.exiting("MRAMemoInInvoice", "update");
	    }
	 /**
	    * method to remove
	    * @return
	    * @param 
	    * @throws SystemException
	    */
	 public void remove() throws SystemException {
	        log.entering("MRAMemoInInvoice", "remove");
	        try {
	            PersistenceController.getEntityManager().remove(this);
	        } catch(RemoveException removeException) {
	            throw new SystemException(removeException.getErrorCode(),
	                    removeException);
	        }
	        log.exiting("MRAMemoInInvoice", "remove");
	    }
	 private static MRAAirlineBillingDAO constructDAO()
	 throws PersistenceException, SystemException{
		 
			localLogger().entering(CLASS_NAME,"constructDAO");
			EntityManager entityManager =
				PersistenceController.getEntityManager();
			return MRAAirlineBillingDAO.class.cast(
					entityManager.getQueryDAO(MODULE_NAME));
	 }
	 /**
	    * method to find Memo Details for listing
	    * @param memoFilterVo
	    * @throws SystemException
	    */
	public static Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
	throws SystemException{
		localLogger().entering(CLASS_NAME,"findMemoDetails");
		try{
			return constructDAO().findMemoDetails(memoFilterVo);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	 /**
     * 
     * @param memoFilterVO
     * @return Collection<MemoInInvoiceVO>
     * @throws SystemException
     * 
     */
    public static Collection<MemoInInvoiceVO> findOutwardRejectionMemo(
    		MemoFilterVO memoFilterVO) throws SystemException {
    	localLogger().entering(CLASS_NAME,"findOutwardRejectionMemo");
		try{
			return constructDAO().findOutwardRejectionMemo(memoFilterVO);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
    }
}
