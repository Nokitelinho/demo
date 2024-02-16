/*
 * StockRangeUtilisationHistory.java Created on Jan 18,2008.
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeUtilisationVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



@Table(name="STKRNGUTLHIS")
@Entity

/**
 *
 * Author A-3155 & A-3184
 *  StockRangeUtilisationHistory represents  the history of utilised ranges
 *  of AWB
 */

public class StockRangeUtilisationHistory {

	private Log log = LogFactory.getLogger("STOCK RANGE UTILISATION HISTORY");

	private StockRangeUtilisationHistoryPK stockRangeUtilisationHistoryPK;

    private String startRange;

	private String endRange;

	private long asciiStartRange;

	private long asciiEndRange;

	private long numberOfDocuments;

	private String status;

	private String rangeType;

	private Calendar  transactionDate;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;
	
	


	/**
     * Default Constructor
     */


	public StockRangeUtilisationHistory(){

	}
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="serialNumber", column=@Column(name="HISSEQNUM")),
		@AttributeOverride(name="stockHolderCode", column=@Column(name="STKHLDCOD")),
		@AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),
		@AttributeOverride(name="documentType", column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType", column=@Column(name="DOCSUBTYP")),
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD"))
		})




	/**
	 * @return Returns the stockRangeUtilisationHistoryPK.
	 */

	public StockRangeUtilisationHistoryPK getStockRangeUtilisationHistoryPK() {
		return stockRangeUtilisationHistoryPK;
	}


	/**
	 * @param stockRangeUtilisationHistoryPK The stockRangeUtilisationHistoryPK to set.
	 */
	public void setStockRangeUtilisationHistoryPK(
			StockRangeUtilisationHistoryPK stockRangeUtilisationHistoryPK) {
		this.stockRangeUtilisationHistoryPK = stockRangeUtilisationHistoryPK;
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
	 * @return the numberOfDocuments
	 */
	@Column(name="NUMDOC")
	public long getNumberOfDocuments() {
		return numberOfDocuments;
	}
	/**
	 * @param numberOfDocuments the numberOfDocuments to set
	 */
	public void setNumberOfDocuments(long numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
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

	/**
	 * @return the rangeType
	 */
	@Column(name="RNGTYP")
	public String getRangeType() {
		return rangeType;
	}
	/**
	 * @param rangeType the rangeType to set
	 */

	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}


//	Constructor
	public StockRangeUtilisationHistory(StockRangeUtilisationVO stockRangeUtilisationVO)
	throws SystemException{
	log.entering("Constructor","StockRangeUtilisationHistory");
	populatePK(stockRangeUtilisationVO);
	populateAttributes(stockRangeUtilisationVO);
	try{
		PersistenceController.getEntityManager().persist(this);
	}catch(CreateException createException){
		throw new SystemException(createException.getErrorCode());
	}
	log.exiting("Constructor","StockRangeUtilisationHistory");

	}
	//Method for creating pk
	private void populatePK(StockRangeUtilisationVO stockRangeUtilisationVO)throws
	SystemException{

    	StockRangeUtilisationHistoryPK stockRangeUtilisationHistoryPk=new StockRangeUtilisationHistoryPK();

		log.log(Log.FINE, "----------populate pk- util hist----  ",
				stockRangeUtilisationVO);
		log.log(Log.FINE,stockRangeUtilisationVO.getDocumentType());
    	log.log(Log.FINE,stockRangeUtilisationVO.getDocumentSubType());
    	log.log(Log.FINE,stockRangeUtilisationVO.getSerialNumber());  

    	stockRangeUtilisationHistoryPk.setCompanyCode(stockRangeUtilisationVO.getCompanyCode());
    	stockRangeUtilisationHistoryPk.setAirlineIdentifier(stockRangeUtilisationVO.getAirlineIdentifier());
     	stockRangeUtilisationHistoryPk.setDocumentType(stockRangeUtilisationVO.getDocumentType());
    	stockRangeUtilisationHistoryPk.setDocumentSubType(stockRangeUtilisationVO.getDocumentSubType());
    	stockRangeUtilisationHistoryPk.setStockHolderCode(stockRangeUtilisationVO.getStockHolderCode());
    	log.log(Log.FINE, "Values of populatePK",
				stockRangeUtilisationHistoryPk);
		this.stockRangeUtilisationHistoryPK=stockRangeUtilisationHistoryPk;  


	}
	/**
	 * Method for populating other attributes
	 * @param stockRangeUtilisationVO
	 * @return
	 * @throws SystemException
	 */
	public void populateAttributes(StockRangeUtilisationVO stockRangeUtilisationVO)
	throws SystemException{
		log.log(Log.FINE,"----------populate attributes-----  ");

    	log.log(Log.FINE,stockRangeUtilisationVO.getStockHolderCode());
    	log.log(Log.FINE,stockRangeUtilisationVO.getStartRange());
    	log.log(Log.FINE,stockRangeUtilisationVO.getEndRange());

    	log.log(Log.FINE,stockRangeUtilisationVO.getTransactionDate().toString());
 
    	this.setStatus(stockRangeUtilisationVO.getStatus());
    	this.setTransactionDate(stockRangeUtilisationVO.getTransactionDate());
    	this.setStartRange(stockRangeUtilisationVO.getStartRange());
    	this.setEndRange(stockRangeUtilisationVO.getEndRange());
    	this.setAsciiStartRange(stockRangeUtilisationVO.getAsciiStartRange());
    	this.setAsciiEndRange(stockRangeUtilisationVO.getAsciiEndRange());
    	this.setNumberOfDocuments(stockRangeUtilisationVO.getNumberOfDocuments());
    	this.setRangeType(stockRangeUtilisationVO.getRangeType());
    	this.setLastUpdateTime(stockRangeUtilisationVO.getLastUpdateTime());
    	this.setLastUpdateUser(stockRangeUtilisationVO.getLastUpdateUser());



    	log.log(Log.FINE, "----------values populate attributes-----");
		log.log(Log.FINE,"----------populate attributes completed-----");


	}
	/**
	 * @author a-3155
	 * @param rangeVO
	 * @return
	 * @throws SystemException
	 */

	public static Collection<RangeVO> findUtilisationRangesForMerge
	(RangeVO rangeVO)throws SystemException{
	    try {


	    	return constructDAO().findUtilisationRangesForMerge(rangeVO);
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author a-3155
	 * @param rangeVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	    private static StockControlDefaultsDAO constructDAO()
    	throws SystemException, PersistenceException {
    	EntityManager em = PersistenceController.getEntityManager();


    	return StockControlDefaultsDAO.class.cast(em.getQueryDAO(
    			StockControlDefaultsPersistenceConstants.MODULE_NAME));
	    	} 


	    /**
		 * @author a-3155
		 * @param stockRangeUtilisationVO
		 * @return
		 * @throws SystemException
		 * @throws FinderException
		 */
	public static StockRangeUtilisationHistory find(StockRangeUtilisationVO stockRangeUtilisationVO)
							throws FinderException,SystemException{
		StockRangeUtilisationHistoryPK pk = new StockRangeUtilisationHistoryPK();
		pk.setCompanyCode(stockRangeUtilisationVO.getCompanyCode());
		pk.setAirlineIdentifier(stockRangeUtilisationVO.getAirlineIdentifier());
		pk.setDocumentType(stockRangeUtilisationVO.getDocumentType());
		pk.setDocumentSubType(stockRangeUtilisationVO.getDocumentSubType());
		pk.setStockHolderCode(stockRangeUtilisationVO.getStockHolderCode());
		pk.setSerialNumber(stockRangeUtilisationVO.getSerialNumber());
		return PersistenceController.getEntityManager().find(StockRangeUtilisationHistory.class,pk);
	}

	/**
	 * Method to update
	 * @param stockRangeUtilisationVO
	 * @return
	 * @throws SystemException
	 */
	public void update(StockRangeUtilisationVO stockRangeUtilisationVO) throws SystemException{
		populateAttributes(stockRangeUtilisationVO);
		//this.setLastUpdateTime(regionVO.getLastUpdateTime());
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

	 /**
	   * @author A-3184
	    * @param blacklistStockVO
	    * @return
	    * @throws SystemException
	    */
	public static Collection<RangeVO> checkForUsedStockInUTLHis
	(BlacklistStockVO blacklistStockVO)throws SystemException{
	    try {
	    	return constructDAO().checkForUsedStockInUTLHis(blacklistStockVO);
			}
	    catch (PersistenceException persistenceException) {  
			throw new SystemException(persistenceException.getErrorCode());
		}
	}




}
