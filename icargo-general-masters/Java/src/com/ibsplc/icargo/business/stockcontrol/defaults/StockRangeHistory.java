/*
 * StockRangeHistory.java Created on Jan 18,2008.
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Represents a StockRangeHistory that can store the stock range distribution for
 * different stock.
 *
 * @author A-3184 & A-3155
 *
 */


@Table(name="STKRNGTXNHIS")
@Entity
public class StockRangeHistory {

	private Log log = LogFactory.getLogger("STOCK RANGE HISTORY");

	private StockRangeHistoryPK stockRangeHistoryPK;

	private String rangeType; 

	private String startRange;

	private String endRange;

	private long asciiStartRange;

	private long asciiEndRange;

	private long numberOfDocuments;

	private String status;

	private Calendar transactionDate;

	private String accountNumber;

	private String toStockHolderCode;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;

	//Added by A-2881 for ICRD-3082
	private String userId;
	
	private String remarks;
	
    private double voidingCharge;
	
	private String currencyCode;
    
	private String autoAllocated;

	/**
     * Default Constructor
     */

	public StockRangeHistory(){

	}



    /**
     * @return Returns the StockRangeHistoryPk.
     * */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="serialNumber", column=@Column(name="HISSEQNUM")),
		@AttributeOverride(name="fromStockHolderCode", column=@Column(name="FRMSTKHLDCOD")),
		@AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),
		@AttributeOverride(name="documentType", column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType", column=@Column(name="DOCSUBTYP")),
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD"))
		})
	public StockRangeHistoryPK getStockRangeHistoryPK() {
		return stockRangeHistoryPK;
	}
	/**
	 * @param stockRangeHistoryPK The stockRangeHistoryPK to set.
	 */

	public void setStockRangeHistoryPK(StockRangeHistoryPK stockRangeHistoryPK) {
		this.stockRangeHistoryPK = stockRangeHistoryPK;
	}



	/**
	 * @return Returns the asciiEndRange.
	 */
	@Column(name="ASCENDRNG")
	public long getAsciiEndRange() {
		return asciiEndRange;
	}


	/**
	 * @param asciiEndRange The asciiEndRange to set.
	 */
	public void setAsciiEndRange(long asciiEndRange) {
		this.asciiEndRange = asciiEndRange;
	}


	/**
	 * @return Returns the asciiStartRange.
	 */
	@Column(name="ASCSTARNG")
	public long getAsciiStartRange() {
		return asciiStartRange;
	}


	/**
	 * @param asciiStartRange The asciiStartRange to set.
	 */
	public void setAsciiStartRange(long asciiStartRange) {
		this.asciiStartRange = asciiStartRange;
	}


	/**
	 * @return Returns the endRange.
	 */
	@Column(name="ENDRNG")
	public String getEndRange() {
		return endRange;
	}


	/**
	 * @param endRange The endRange to set.
	 */
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}


	/**
	 * @return Returns the numberOfDocuments.
	 */
	@Column(name="NUMDOC")
	public long getNumberOfDocuments() {
		return numberOfDocuments;
	}


	/**
	 * @param numberOfDocuments The numberOfDocuments to set.
	 */
	public void setNumberOfDocuments(long numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}


	/**
	 * @return Returns the rangeType.
	 */
	@Column(name="RNGTYP")
	public String getRangeType() {
		return rangeType;
	}


	/**
	 * @param rangeType The rangeType to set.
	 */
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}


	/**
	 * @return Returns the startRange.
	 */
	@Column(name="STARNG")
	public String getStartRange() {
		return startRange;
	}


	/**
	 * @param startRange The startRange to set.
	 */
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}


	/**
	 * @return Returns the status.
	 */
	@Column(name="STATUS")
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
	 * @return Returns the toStockHolderCode.
	 */
	@Column(name="TOSTKHLDCOD")
	public String getToStockHolderCode() {
		return toStockHolderCode;
	}


	/**
	 * @param toStockHolderCode The toStockHolderCode to set.
	 */
	public void setToStockHolderCode(String toStockHolderCode) {
		this.toStockHolderCode = toStockHolderCode;
	}


	/**
	 * @return Returns the transactionDate.
	 */
	@Column(name="TXNDAT")
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
	 * @return Returns the accountNumber.
	 */

	@Column(name="ACCNUM")
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber The accountNumber to set.
	 */

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}



	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name="LSTUPDDAT")
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
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}



	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	//Constructor
	public StockRangeHistory(StockRangeHistoryVO stockRangeHistoryVO)
	throws SystemException{
	log.entering("Constructor","StockRangeHistory");
	populatePK(stockRangeHistoryVO);
	populateAttributes(stockRangeHistoryVO);
	try{
		PersistenceController.getEntityManager().persist(this);
	}catch(CreateException createException){
		throw new SystemException(createException.getErrorCode());
	}
	log.exiting("Constructor","StockRangeHistory");

	}
	/**
	 *
	 * Method for creating pk
	 * @param stockRangeHistoryVO
	 * @return
	 * @throws SystemException
	 */
	private void populatePK(StockRangeHistoryVO stockRangeHistoryVO)throws
	SystemException{

		StockRangeHistoryPK stockRangeHistoryPk=new StockRangeHistoryPK();
		log.log(Log.FINE,"----------populate pk-----  ");



    	log.log(Log.FINE,stockRangeHistoryVO.getDocumentType());
    	log.log(Log.FINE,stockRangeHistoryVO.getDocumentSubType());
    	log.log(Log.FINE,stockRangeHistoryVO.getSerialNumber());

    	stockRangeHistoryPk.setCompanyCode(stockRangeHistoryVO.getCompanyCode());
    	stockRangeHistoryPk.setAirlineIdentifier(stockRangeHistoryVO.getAirlineIdentifier());
    	stockRangeHistoryPk.setDocumentType(stockRangeHistoryVO.getDocumentType());
    	stockRangeHistoryPk.setDocumentSubType(stockRangeHistoryVO.getDocumentSubType());
    	stockRangeHistoryPk. setFromStockHolderCode(stockRangeHistoryVO.getFromStockHolderCode());
    log.log(Log.FINE, "Values of populatePK", stockRangeHistoryPk);
		this.stockRangeHistoryPK=stockRangeHistoryPk;


	}
	/**
	 * Method for populating other attributes
	 * @param stockRangeHistoryVO
	 * @return
	 * @throws SystemException
	 */
	public void populateAttributes(StockRangeHistoryVO stockRangeHistoryVO)
	throws SystemException{
		log.log(Log.FINE,"----------populate attributes-----  ");

    	log.log(Log.FINE,stockRangeHistoryVO.getRangeType());
    	log.log(Log.FINE,stockRangeHistoryVO.getStartRange());
    	log.log(Log.FINE,stockRangeHistoryVO.getEndRange());

    	log.log(Log.FINE,stockRangeHistoryVO.getLastUpdateUser());
    	this.setLastUpdateTime(stockRangeHistoryVO.getLastUpdateTime());
    	this.setLastUpdateUser(stockRangeHistoryVO.getLastUpdateUser());

    	this.setRangeType(stockRangeHistoryVO.getRangeType());
    	this.setNumberOfDocuments(stockRangeHistoryVO.getNumberOfDocuments());
    	this.setStatus(stockRangeHistoryVO.getStatus());
    	this.setTransactionDate(stockRangeHistoryVO.getTransactionDate());
    	this.setStartRange(stockRangeHistoryVO.getStartRange());
    	this.setEndRange(stockRangeHistoryVO.getEndRange());
    	this.setAsciiStartRange(stockRangeHistoryVO.getAsciiStartRange());
    	this.setAsciiEndRange(stockRangeHistoryVO.getAsciiEndRange());
    	this.setToStockHolderCode(stockRangeHistoryVO. getToStockHolderCode());
    	this.setAccountNumber(stockRangeHistoryVO.getAccountNumber());
    	this.setUserId(stockRangeHistoryVO.getUserId());
    	this.setRemarks(stockRangeHistoryVO.getRemarks());
    	this.setVoidingCharge(stockRangeHistoryVO.getVoidingCharge());
    	this.setCurrencyCode(stockRangeHistoryVO.getCurrencyCode());
    	this.setAutoAllocated(stockRangeHistoryVO.getAutoAllocated());
         log.log(Log.FINE, "----------values populate attributes-----");
		log.log(Log.FINE,"----------populate attributes completed-----");


	}

	/**
	   * @author A-3184 & A-3155
	    * Finding a particular blacklisted stock
	    * @param stockRangeHistoryVO
	    * @return
	    * @throws SystemException
	    */
	
	public static Collection<StockRangeHistoryVO>findStockRangeHistory
	(StockRangeFilterVO stockRangeFilterVO)throws SystemException{
	    try {
	    	return constructDAO().findStockRangeHistory(stockRangeFilterVO);
			}
	    catch (PersistenceException persistenceException) { 
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	   
	/**
	 * @author A-3184
	 * @param rangeVO
	 * @return
	 * @throws SystemException
	 */

	public static Collection<RangeVO> findUsedRangesForMerge
	(RangeVO rangeVO,String status)throws SystemException{
	    try {


	    	return constructDAO().findUsedRangesForMerge(rangeVO,status);
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	public static Collection<RangeVO> findUsedRangesInHis
	(RangeVO rangeVO,String status)throws SystemException{
	    try {


	    	return constructDAO().findUsedRangesInHis(rangeVO,status);
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	} 
	
	 /**
	   * @author A-2415
	    * @param stockRangeHistoryVO
	    * @return 
	    * @throws SystemException
	    */
	public static Collection<StockRangeHistoryVO>findAgentCSVDetails
	(StockRangeFilterVO stockRangeFilterVO)throws SystemException{
	    try {
	    	return constructDAO().findAgentCSVDetails(stockRangeFilterVO);
			}
	    catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	 /**
	 * @author a-3184
	 * @param stockRangeHistoryVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static StockRangeHistory find(StockRangeHistoryVO stockRangeHistoryVO)
							throws FinderException,SystemException{
		StockRangeHistoryPK pk=new StockRangeHistoryPK();
		pk.setCompanyCode(stockRangeHistoryVO.getCompanyCode());   
		pk.setAirlineIdentifier(stockRangeHistoryVO.getAirlineIdentifier());
		pk.setDocumentType(stockRangeHistoryVO.getDocumentType());
		pk.setDocumentSubType(stockRangeHistoryVO.getDocumentSubType());
		pk.setFromStockHolderCode(stockRangeHistoryVO.getFromStockHolderCode());
		pk.setSerialNumber(stockRangeHistoryVO.getSerialNumber());
		return PersistenceController.getEntityManager().find(StockRangeHistory.class,pk);
	}
	/**
	 * Method to remove
	 * @param
	 * @return
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		try{
			PersistenceController.getEntityManager().remove(this);
		}catch(RemoveException removeException){
			throw new SystemException(
					removeException.getErrorCode(),removeException);
		}
	}

	
	   
	 private static StockControlDefaultsDAO constructDAO()
		throws SystemException, PersistenceException {
		EntityManager em = PersistenceController.getEntityManager();
		
		
		return StockControlDefaultsDAO.class.cast(em.getQueryDAO(
				StockControlDefaultsPersistenceConstants.MODULE_NAME));
	    	}
	
	 /**
	   * @author A-3184
	    * @param blacklistStockVO
	    * @return
	    * @throws SystemException
	    */
	public static Collection<RangeVO> checkForUsedStockInHis
	(BlacklistStockVO blacklistStockVO)throws SystemException{
	    try {
	    	return constructDAO().checkForUsedStockInHis(blacklistStockVO);
			}
	    catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	 /**
	   * @author A-3184
	    * @param blacklistStockVO
	    * @return
	    * @throws SystemException
	    */
	public static String returnFirstLevelStockHolder
	(BlacklistStockVO blacklistStockVO)throws SystemException{
	    try {
	    	return constructDAO().returnFirstLevelStockHolder(blacklistStockVO);
			}
	    catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
    
	/**
	 * @author A-2881
	 * @param stockRangeFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<StockRangeHistoryVO>findStockRangeHistoryForPage
	(StockRangeFilterVO stockRangeFilterVO)throws SystemException{
	    try {
	    	return constructDAO().findStockRangeHistoryForPage(stockRangeFilterVO);
			}
	    catch (PersistenceException persistenceException) { 
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @return the userId
	 */
	@Column(name="USRCOD")
	public String getUserId() {
		return userId;
	}



	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}



	/**
	 * @return the remarks
	 */
	@Column(name="TXNRMK")
	public String getRemarks() {
		return remarks;
	}



	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	/**
	 * @return the currencyCode
	 */
	@Column(name="CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}



	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}



	/**
	 * @return the voidingCharge
	 */
	@Column(name="VODCHG")
	public double getVoidingCharge() {
		return voidingCharge;
	}



	/**
	 * @param voidingCharge the voidingCharge to set
	 */
	public void setVoidingCharge(double voidingCharge) {
		this.voidingCharge = voidingCharge;
	}
	
	@Column(name="AUTALCFLG")
	public String getAutoAllocated() {
		return autoAllocated;
	}

	/**
	 * @param autoAllocated The autoAllocated to set.
	 */

	public void setAutoAllocated(String autoAllocated) {
		this.autoAllocated = autoAllocated;
	}

	
	

}
