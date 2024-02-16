/*
 * StockRangeUtilisation.java Created on Jun 15, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;




import java.util.ArrayList;
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Represents the range of documents belonging to a particular stock
 * Aggregation of ranges constitute a stock
 * @author A-1358
 */
@Table(name="STKRNGUTL")
@Entity
@Staleable
public class StockRangeUtilisation {

	private  Log log = LogFactory.getLogger("STOCK RANGE TRANSACTION");
    /**
     * Range P
     */
    private StockRangeUtilisationPK stockRangeUtilisationPK;

  
    
    private long asciiDocumentNumber;
    
    private Calendar lastUpdateTime;
    
    private String status;

    private String lastUpdateUser;


  
    @Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}


	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
  

	/**
	 * @return Returns the asciiDocumentNumber.
	 */
	@Column(name = "ASCDOCNUM")
	public long getAsciiDocumentNumber() {
		return asciiDocumentNumber;
	}


	/**
	 * @param asciiDocumentNumber The asciiDocumentNumber to set.
	 */
	public void setAsciiDocumentNumber(long asciiDocumentNumber) {
		this.asciiDocumentNumber = asciiDocumentNumber;
	}
    /**
	 * @return Returns the lastUpdateTime.
	 */
	@Column(name = "LSTUPDTIMUTC")
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
	 * @return Returns the status.
	 */
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}


	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
     * Default Constructor
     *
     */
    public StockRangeUtilisation(){

    }


    /**
     * @return Returns the rangePK.
     *
     */
    @EmbeddedId
    @AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="airlineIdentifier",column=@Column(name="ARLIDR")),
		@AttributeOverride(name="stockHolderCode",column=@Column(name="STKHLDCOD")),
		@AttributeOverride(name="documentType",column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType",column=@Column(name="DOCSUBTYP")),
		@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM")),
    	@AttributeOverride(name="documentNumber",column=@Column(name="MSTDOCNUM"))}
		)
    public StockRangeUtilisationPK getStockRangeUtilisationPK() {
        return stockRangeUtilisationPK;
    }
    /**
     * @param rangePK The rangePK to set.
     */
    public void setStockRangeUtilisationPK(StockRangeUtilisationPK
    		stockRangeUtilisationPK) {
        this.stockRangeUtilisationPK = stockRangeUtilisationPK;
    }
    
    /**
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @param airlineIdentifier
	 * @param rangeVO
	 * @param status
	 * @param lastUpdateTime
	 * @throws SystemException
	 */
	public StockRangeUtilisation(String companyCode,String stockHolderCode,String docType,
			String docSubType,int airlineIdentifier,RangeVO rangeVO,String status,GMTDate lastUpdateTime)
	throws SystemException{
		log.log(Log.FINE,"------------>>StockRangeTransaction ----->>>>");
		log.log(Log.FINE, "-companycode-", companyCode);
		log.log(Log.FINE, "-stockholdercode-", stockHolderCode);
		log.log(Log.FINE, "-doctyp-", docType);
		log.log(Log.FINE, "-docsubtyp-", docSubType);
		log.log(Log.FINE, "-airlineId-", airlineIdentifier);
		log.log(Log.FINE, "-rangevo-", rangeVO);
		log.log(Log.FINE, "-status-", status);
		log.log(Log.FINE, "-lastupdateTime-", lastUpdateTime);
		populatePK(companyCode,stockHolderCode,docType,docSubType
				,airlineIdentifier,rangeVO.getStartRange());
		populateAttributes(rangeVO.getStartRange(),status,lastUpdateTime);
		try{
		PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}

	}

	/**
	 *Removing a particular range
	 * @throws SystemException
	 */
    public void remove()throws SystemException{
    	try{
      	PersistenceController.getEntityManager().remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
    }

    /**
     * Method for calculating the base of number
     * @param input
     * @return base
     */
	public static long calculateBase(char input){
		long inside=input;
		long base=0;
		try{
			base=Integer.parseInt(""+input);
		}catch(NumberFormatException numberFormatException){
			base=inside-65;
		}
		return base;
	}

	/** To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	public static long findLong(String range){
		char[] sArray=range.toCharArray();
		long base=1;
		long sNumber=0;
		for(int i=sArray.length-1;i>=0;i--){
			sNumber+=base*calculateBase(sArray[i]);
			int index=sArray[i];
			if (index>57) {
				base*=26;
			} else {
				base*=10;
			}
		}
		return sNumber;
	}

	/** To find the difference between range from and range to value
	 *
	 * @param rangeFrom Range from value
	 * @param rangeTo Range to value
	 * @return Difference between the ranges
	 */
	public static int difference(String rangeFrom,String rangeTo){

		long difference=findLong(rangeTo)-findLong(rangeFrom);
		difference++;
		return (int)difference;
	}
    /**
     *Method for populating the pk
     * @param companyCode
     * @param stockHolderCode
     * @param docType
     * @param docSubType
     * @param airlineIdentifier
     * @return
     */
    private void populatePK(String companyCode,String stockHolderCode
    					,String docType,String docSubType,int airlineIdentifier,String documentNumber){
    	log.entering(" StockRangeUtilisation ", " populatePK ");
		stockRangeUtilisationPK = new StockRangeUtilisationPK();
		stockRangeUtilisationPK.setCompanyCode(companyCode);
		stockRangeUtilisationPK.setStockHolderCode(stockHolderCode);
		stockRangeUtilisationPK.setDocumentType(docType);
		stockRangeUtilisationPK.setDocumentSubType(docSubType);
		stockRangeUtilisationPK.setAirlineIdentifier(airlineIdentifier);
		stockRangeUtilisationPK.setDocumentNumber(documentNumber);
		this.setStockRangeUtilisationPK(stockRangeUtilisationPK);
		log.exiting(" StockRangeUtilisation ", " populatePK ");
    }

    /**
     *This method is for populate other attributes of range
     *
     * @param rangeVO
     * @param status
     * @param lastUpdateTime
     * @throws SystemException
     */
    private void populateAttributes(String range,String status,GMTDate lastUpdateTime) throws SystemException{
    	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
       	this.asciiDocumentNumber = findLong(range);
       	this.status = status;
       	this.lastUpdateTime = lastUpdateTime;
       	this.lastUpdateUser = logon.getUserId();

     }
    
    
    /**
	 * used for finding the User object from the database based on the primary
	 * key values uses the Persistence Controller find method
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @exception SystemException
	 * @exception FinderException
	 */
		public static StockRangeUtilisation find(String companyCode,String stockHolderCode,
				String sequenceNumber,String docType,String docSubType,int airlineIdentifier
				,String documentNumber)throws SystemException, FinderException {
		StockRangeUtilisation stockRangeUtilisation = null;
		StockRangeUtilisationPK	StockRangeUtilisationPk = new StockRangeUtilisationPK();
		StockRangeUtilisationPk.setCompanyCode(companyCode);
		StockRangeUtilisationPk.setStockHolderCode(stockHolderCode);
		StockRangeUtilisationPk.setDocumentType(docType);
		StockRangeUtilisationPk.setDocumentSubType(docSubType);
		StockRangeUtilisationPk.setAirlineIdentifier(airlineIdentifier);
		StockRangeUtilisationPk.setSequenceNumber(sequenceNumber);
		StockRangeUtilisationPk.setDocumentNumber(documentNumber);
		
		stockRangeUtilisation = PersistenceController.getEntityManager()
				.find(StockRangeUtilisation.class, StockRangeUtilisationPk);
		return stockRangeUtilisation;
	}
		
	
	/**
	 * @param rangeVO
	 * @param status
	 * @param lastUpdateTime
	 * @throws SystemException
	 */
	public void update(RangeVO rangeVO,String status,GMTDate lastUpdateTime)
			throws SystemException{
		log.entering("StockRangeUtilisation","---------update---------");
		populateAttributes(rangeVO.getStartRange(),status,lastUpdateTime);
		log.exiting("StockRangeUtilisation","---------update---------");
	}
	
		



    private static StockControlDefaultsDAO constructDAO()
    	throws SystemException, PersistenceException {
    	EntityManager em = PersistenceController.getEntityManager();
    	return StockControlDefaultsDAO.class.cast(em.getQueryDAO(
    			StockControlDefaultsPersistenceConstants.MODULE_NAME));
    }

    /**
	 * 
	 * @param stockDepleteFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static String findStockRangeUtilisationExists(String companyCode,
			String stockHolderCode,String documentType,String documentSubType,
				int airlineIdentifier,RangeVO rangeVO)throws SystemException{
	    try {
			return constructDAO().findStockRangeUtilisationExists(companyCode,
					stockHolderCode,documentType,documentSubType,airlineIdentifier,rangeVO);
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}	
		
	}
	/**
	 * Added By A-6767 For ICRD-139677
	 * @param 
	 * @return
	 * @throws SystemException
	 */
	public static Collection<BlacklistStockVO> findBlacklistedStockFromUTL ()throws SystemException{
	    try {
			return constructDAO().findBlacklistedStockFromUTL ();
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}		
	}
	 /**
	 * Added By A-6767 For ICRD-139677
	 * @param 
	 * @return
	 * @throws SystemException
	 */
	public void deleteBlackListedStockFromUTL (ArrayList<String> masterDocNumbers)throws SystemException{
	    try {
	    	constructDAO().deleteBlackListedStockFromUTL (masterDocNumbers);
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}	
	
	}

  }
