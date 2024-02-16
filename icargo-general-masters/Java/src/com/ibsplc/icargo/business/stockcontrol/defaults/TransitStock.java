/*
 * BlackListStock.java Created on Mar 13, 2012
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4443
 * Stock Transit Range Table
 *  */
 @Table(name="STKTRSRNG") 
 @Entity
 //@Staleable

public class TransitStock {

	 private static Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");
    /**
     * BlackListStockPK
     */
    private TransitStockPK transitStockPK;
    
    private String stockControlFor;
    /**
     * Actual Start Range
     */
    private String actualStartRange;
    /**
     * Actual Start Range
     */
    private String actualEndRange;
    /**
     * Actual Start Range
     */
    private String missingStartRange;
    /**
     * Actual Start Range
     */
    private String missingEndRange;

    /**
     * Last updated date
     */
    private Calendar lastUpdateTime;
    /**
     * Last updated user
     */
    private String lastUpdateUser;
    
    /**
     * 
     */
    private String confirmStatus;
    
    /**
     * 
     */
    private Calendar confirmDate;
    
    /**
     * ascii format of startRange
     */
    private long asciiMissingStartRange;
    /**
     * ascii format of endRange
     */
    private long asciiMissingEndRange;
    
    /**
     * 
     */
    private String txnCode;
    
    /**
     * 
     */
    private Calendar txnDate;
    
    private String isManual;
    
    private long missingNumberOfDocs;
    
    private long numberOfDocs;
    
    private String missingRemarks;
    
    private String txnRemarks;

    


	public TransitStock(){
		super();
    }

    /**
     * @return Returns the blackListStockPk.
     * */
    @EmbeddedId
    @AttributeOverrides({
		@AttributeOverride(name="companyCode", 	column=@Column(name="CMPCOD")),
		@AttributeOverride(name="airlineIdentifier",	column=@Column(name="ARLIDR")),
		@AttributeOverride(name="documentType",	column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType",	column=@Column(name="DOCSUBTYP")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM")),
		@AttributeOverride(name="stockHolderCode",	column=@Column(name="STKHLDCOD"))})
    public TransitStockPK getTransitStockPK() {
        return transitStockPK;
    }
    /**
     * @param blackListStockPk The blackListStockPk to set.
     */
    public void setTransitStockPK(TransitStockPK
    		transitStockPK) {
        this.transitStockPK = transitStockPK;
    }
    /**
     * @return Returns the lastUpdateDate.
     *
     */
    @Version
    @Column(name="LSTUPDTIM")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateDate
     * The lastUpdateDate to set.
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * Returns the lastUpdateUser.
     * @return lastUpdateUser
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
	 * @return the missingRemarks
	 */
    @Column(name="MISRMK")
	public String getMissingRemarks() {
		return missingRemarks;
	}

	/**
	 * @param missingRemarks the missingRemarks to set
	 */
	public void setMissingRemarks(String missingRemarks) {
		this.missingRemarks = missingRemarks;
	}

	/**
	 * @return the actualEndRange
	 */
    @Column(name="ACTENDRNG")
	public String getActualEndRange() {
		return actualEndRange;
	}

	/**
	 * @param actualEndRange the actualEndRange to set
	 */
	public void setActualEndRange(String actualEndRange) {
		this.actualEndRange = actualEndRange;
	}

	/**
	 * @return the actualStartRange
	 */
	@Column(name="ACTSTARNG")
	public String getActualStartRange() {
		return actualStartRange;
	}

	/**
	 * @param actualStartRange the actualStartRange to set
	 */
	public void setActualStartRange(String actualStartRange) {
		this.actualStartRange = actualStartRange;
	}

	/**
	 * @return the confirmDate
	 */
	@Column(name="CFRDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getConfirmDate() {
		return confirmDate;
	}

	/**
	 * @param confirmDate the confirmDate to set
	 */
	public void setConfirmDate(Calendar confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * @return the confirmStatus
	 */
	@Column(name="CFRSTA")
	public String getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param confirmStatus the confirmStatus to set
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	

	/**
	 * @return the missingEndRange
	 */
	@Column(name="MISENDRNG")
	public String getMissingEndRange() {
		return missingEndRange;
	}

	/**
	 * @param missingEndRange the missingEndRange to set
	 */
	public void setMissingEndRange(String missingEndRange) {
		this.missingEndRange = missingEndRange;
	}

	/**
	 * @return the missingStartRange
	 */
	@Column(name="MISSTARNG")
	public String getMissingStartRange() {
		return missingStartRange;
	}

	/**
	 * @param missingStartRange the missingStartRange to set
	 */
	public void setMissingStartRange(String missingStartRange) {
		this.missingStartRange = missingStartRange;
	}

	

	/**
	 * @return the stockControlFor
	 */
	@Column(name="FRMSTKHLDCOD")
	public String getStockControlFor() {
		return stockControlFor;
	}

	/**
	 * @param stockControlFor the stockControlFor to set
	 */
	public void setStockControlFor(String stockControlFor) {
		this.stockControlFor = stockControlFor;
	}

	/**
     * Constructor
     * @param blacklistStockVO
     * @throws SystemException
     */
    public TransitStock(TransitStockVO transitStockVO)
    throws SystemException{
    	log.entering("Constructor","BlacklistStock");
    	//validateRangeFormat(blacklistStockVO);
    	populatePK(transitStockVO);
    	populateAttributes(transitStockVO);
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    	log.exiting("Constructor","BlacklistStock");
    }
    /**
     */
/*    public void Operation1() {
    }
*/   /**
    * Finding a particular blacklisted stock
    * @param blacklistStockVO
    * @return
    * @throws SystemException
    */
    public static TransitStock  find(TransitStockVO
    		transitStockVO)
			throws SystemException {

		TransitStock trnStock = null;
		TransitStockPK trnStockPK = new TransitStockPK();
		trnStockPK.setCompanyCode(transitStockVO.getCompanyCode());
		trnStockPK.setAirlineIdentifier(transitStockVO.getAirlineIdentifier());
		trnStockPK.setDocumentType(transitStockVO.getDocumentType());
		trnStockPK.setDocumentSubType(transitStockVO.getDocumentSubType());
		trnStockPK.setStockHolderCode(transitStockVO.getStockHolderCode());
		trnStockPK.setSequenceNumber(Integer.parseInt(transitStockVO.getSequenceNumber()));

		try {
			EntityManager entityManager = PersistenceController
					.getEntityManager();

			trnStock = entityManager.find(TransitStock.class, trnStockPK);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		return trnStock;
	}

   
    /**
	 * populating the pk for blackliststock
	 * 
	 * @param blacklistStockVO
	 */
    private void populatePK(TransitStockVO transitStockVO){
    	log.log(Log.FINE,"----------populate pk-----  ");
    	log.log(Log.FINE,transitStockVO.getCompanyCode());
    	log.log(Log.FINE,transitStockVO.getDocumentType());
    	log.log(Log.FINE,transitStockVO.getDocumentSubType());
    	log.log(Log.FINE,transitStockVO.getStockHolderCode());
    	
    	TransitStockPK transitStockPk=new TransitStockPK();
    	transitStockPk.setCompanyCode(transitStockVO.getCompanyCode());
    	transitStockPk.setAirlineIdentifier(transitStockVO.getAirlineIdentifier());
    	transitStockPk.setDocumentType(transitStockVO.getDocumentType());
    	transitStockPk.setDocumentSubType(transitStockVO.getDocumentSubType());
    	transitStockPk.setStockHolderCode(transitStockVO.getStockHolderCode());
    	this.transitStockPK=transitStockPk;

    }
    /**
     * popultating other attributes
     * @param blacklistStockVO
     */
    private void populateAttributes(TransitStockVO transitStockVO)
    {
		log.log(Log.FINE, "----------populate attributes-----  ");
		log.log(Log.FINE, transitStockVO.getLastUpdateUser());
		this.setStockControlFor(transitStockVO.getStockControlFor());
		this.setActualStartRange(transitStockVO.getActualStartRange());
		this.setActualEndRange(transitStockVO.getActualEndRange());
		this.setMissingEndRange(transitStockVO.getMissingEndRange());
		this.setMissingStartRange(transitStockVO.getMissingStartRange());
		this.setAsciiMissingEndRange(transitStockVO.getAsciiMissingEndRange());
		this.setAsciiMissingStartRange(transitStockVO
				.getAsciiMissingStartRange());
		this.setConfirmDate(transitStockVO.getConfirmDate());
		this.setConfirmStatus(transitStockVO.getConfirmStatus());
		this.setTxnCode(transitStockVO.getTxnCode());
		this.setTxnDate(transitStockVO.getTxnDate());
		this.setIsManual(convertBoolean(transitStockVO.isManual()));
		this.setNumberOfDocs(transitStockVO.getNumberOfDocs());
		this.setMissingNumberOfDocs(transitStockVO.getMissingNumberOfDocs());
		this.setMissingRemarks(transitStockVO.getMissingRemarks());
		this.setTxnRemarks(transitStockVO.getTxnRemarks());
		this.setLastUpdateTime(transitStockVO.getLastUpdateTime().toCalendar());
		this.setLastUpdateUser(transitStockVO.getLastUpdateUser());
		

	}

       

	/**
	 * @return the asciiMissingEndRange
	 */
	 @Column(name="ASCMISENDRNG")
	public long getAsciiMissingEndRange() {
		return asciiMissingEndRange;
	}

	/**
	 * @param asciiMissingEndRange the asciiMissingEndRange to set
	 */
	public void setAsciiMissingEndRange(long asciiMissingEndRange) {
		this.asciiMissingEndRange = asciiMissingEndRange;
	}

	/**
	 * @return the asciiMissingStartRange
	 */
	@Column(name="ASCMISSTARNG")
	public long getAsciiMissingStartRange() {
		return asciiMissingStartRange;
	}

	/**
	 * @param asciiMissingStartRange the asciiMissingStartRange to set
	 */
	public void setAsciiMissingStartRange(long asciiMissingStartRange) {
		this.asciiMissingStartRange = asciiMissingStartRange;
	}

	/**
	 * @return the txnCode
	 */
	@Column(name="TXNCOD")
	public String getTxnCode() {                              
		return txnCode;                 
	}

	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	/**
	 * @return the txnDate
	 */
	@Column(name="TXNDAT")                 
	public Calendar getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(Calendar txnDate) {
		this.txnDate = txnDate;
	}

	/**
	 * @return the isManual
	 */
	@Column(name="MNLFLG") 
	public String getIsManual() {
		return isManual;
	}

	/**
	 * @param isManual the isManual to set
	 */
	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}

	/**
	 * @return the missingNumberOfDocs
	 */
	@Column(name="MISNUMDOC") 
	public long getMissingNumberOfDocs() {
		return missingNumberOfDocs;
	}

	/**
	 * @param missingNumberOfDocs the missingNumberOfDocs to set
	 */
	public void setMissingNumberOfDocs(long missingNumberOfDocs) {
		this.missingNumberOfDocs = missingNumberOfDocs;
	}

	/**
	 * @return the numberOfDocs
	 */
	@Column(name="NUMDOC") 
	public long getNumberOfDocs() {
		return numberOfDocs;
	}

	/**
	 * @param numberOfDocs the numberOfDocs to set
	 */
	public void setNumberOfDocs(long numberOfDocs) {
		this.numberOfDocs = numberOfDocs;
	}
	
	/**
	 * Method to return Y/N based on the boolean value
	 * @param flag
	 * @return
	 */
	private String convertBoolean(boolean flag) {
		if (flag) {
			return TransitStockVO.FLAG_YES;
		} else {
			return TransitStockVO.FLAG_NO;
		}
	}
	
   /**
    * 
    * @throws SystemException
    */
   public void remove() throws SystemException {
		try {
			EntityManager entityManager = PersistenceController
					.getEntityManager();
			entityManager.remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
	}
   
   /**
    * 
    * @param transitStockVOs
    * @throws SystemException
    */
   public static void saveTransitStock(
			Collection<TransitStockVO> transitStockVOs) throws SystemException {
		//log.entering("TransitStock", "saveTransitStock");
		log.log(Log.FINE, "----------saveTransitStock-----  ");
		for (TransitStockVO transitStockVO : transitStockVOs) {
			if(TransitStockVO.OPERATION_FLAG_DELETE.equals(transitStockVO.getOperationFlag())){
				log.log(Log.FINE, "Operation Flag is D");
				TransitStock transitStockForRemoval=TransitStock.find(transitStockVO);
				transitStockForRemoval.remove();
			}else if(TransitStockVO.OPERATION_FLAG_INSERT.equals(transitStockVO.getOperationFlag())){
				log.log(Log.FINE, "Operation Flag is I");
				TransitStock transit = new TransitStock(transitStockVO);
			}
			
		}
		log.exiting("TransitStock", "saveTransitStock");
	}

   //added by a-4443 for icrd-3024 starts
   /**
    * a-4443
    * Finder method for transit stocks
    * @return
    * @throws SystemException
    * @throws InvalidStockHolderException
    */
   public static Collection<TransitStockVO> findTransitStocks(StockRequestFilterVO stockRequestFilterVO)
	throws SystemException,PersistenceException{
   	try {
			return constructDAO().findTransitStocks(stockRequestFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
   }
   /**
	 * a-4443
	 * @return StockControlDefaultsSqlDAO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static StockControlDefaultsSqlDAO constructDAO()
	throws SystemException, PersistenceException {
		EntityManager entityManager = PersistenceController.getEntityManager();
		return StockControlDefaultsSqlDAO.class.cast
		(entityManager.getQueryDAO(StockControlDefaultsPersistenceConstants.MODULE_NAME));
	}
	 /**
	  * a-4443
	    * Finder method for transit stocks
	    * @return
	    * @throws SystemException
	    * @throws InvalidStockHolderException
	    */
	   public static Collection<TransitStockVO> findBlackListRangesFromTransit(BlacklistStockVO blacklistStockVO)
		throws SystemException,PersistenceException{
	   	try {
				return constructDAO().findBlackListRangesFromTransit(blacklistStockVO);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
	   }
	//added by a-4443 for icrd-3024 ends

	/**
	 * @return the txnRemarks
	 */
	@Column(name="TXNRMK")
	public String getTxnRemarks() {
		return txnRemarks;
	}

	/**
	 * @param txnRemarks the txnRemarks to set
	 */
	public void setTxnRemarks(String txnRemarks) {
		this.txnRemarks = txnRemarks;
	}
}
