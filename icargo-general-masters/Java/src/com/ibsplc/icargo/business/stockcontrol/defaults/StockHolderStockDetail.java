/*
 * StockHolder.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;

import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;




@Table(name="STKHLDSTKDTL")
@Entity
public class StockHolderStockDetail {

	private static final String DOCSUBTYP = "S";

	private static final String HQ = "HQ";

	private  Log log = LogFactory.getLogger("STOCKHOLDER STOCKDETAIL");

    private StockHolderStockDetailPK stockHolderPk;

    private Calendar txnDateUTC;;

    private long openingBalance;
    
    private long receivedStock;
    
    private long allocatedStock;
    
    private long transferredStock;
    
    private long returnStock;
    
    private long returnUtilisedStock;
    
    private long blackListedStock;
    
    private long utilisedStock;
    
    private long closingBalance;

    private Calendar lastUpdateTime;

	private String lastUpdateUser;
	
	private Calendar lastUpdateTimeUTC;
	

	@Column(name="LSTUPDTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTimeUTC() {
		return lastUpdateTimeUTC;
	}
	public void setLastUpdateTimeUTC(Calendar lastUpdateTimeUTC) {
		this.lastUpdateTimeUTC = lastUpdateTimeUTC;
	}
	/**
     * @return Returns the lastUpdateTime.
     */
	
	@Version
    @Column(name="LSTUPDTIM")
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
     * Default Constructor
     */
    public StockHolderStockDetail(){
        super();
    }

    
	/**
	 * @return the allocatedStock
	 */
    @Column(name="ALCSTK")
	public long getAllocatedStock() {
		return allocatedStock;
	}
	/**
	 * @param allocatedStock the allocatedStock to set
	 */
	public void setAllocatedStock(long allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	/**
	 * @return the blackListedStock
	 */
	@Column(name="BLKLSTSTK")
	public long getBlackListedStock() {
		return blackListedStock;
	}
	/**
	 * @param blackListedStock the blackListedStock to set
	 */
	public void setBlackListedStock(long blackListedStock) {
		this.blackListedStock = blackListedStock;
	}
	/**
	 * @return the closingBalance
	 */
	@Column(name="CLSBAL")
	public long getClosingBalance() {
		return closingBalance;
	}
	/**
	 * @param closingBalance the closingBalance to set
	 */
	public void setClosingBalance(long closingBalance) {
		this.closingBalance = closingBalance;
	}
	/**
	 * @return the openingBalance
	 */
	@Column(name="OPGBAL")
	public long getOpeningBalance() {
		return openingBalance;
	}
	/**
	 * @param openingBalance the openingBalance to set
	 */
	public void setOpeningBalance(long openingBalance) {
		this.openingBalance = openingBalance;
	}
	/**
	 * @return the receivedStock
	 */
	@Column(name="RCVSTK")
	public long getReceivedStock() {
		return receivedStock;
	}
	/**
	 * @param receivedStock the receivedStock to set
	 */
	public void setReceivedStock(long receivedStock) {
		this.receivedStock = receivedStock;
	}
	/**
	 * @return the returnStock
	 */
	@Column(name="RTNSTK")
	public long getReturnStock() {
		return returnStock;
	}
	/**
	 * @param returnStock the returnStock to set
	 */
	public void setReturnStock(long returnStock) {
		this.returnStock = returnStock;
	}
	/**
	 * @return the returnUtilisedStock
	 */
	@Column(name="RTNUTLSTK")
	public long getReturnUtilisedStock() {
		return returnUtilisedStock;
	}
	/**
	 * @param returnUtilisedStock the returnUtilisedStock to set
	 */
	public void setReturnUtilisedStock(long returnUtilisedStock) {
		this.returnUtilisedStock = returnUtilisedStock;
	}
	/**
	 * @return the transferredStock
	 */
	@Column(name="TFDSTK")
	public long getTransferredStock() {
		return transferredStock;
	}
	/**
	 * @param transferredStock the transferredStock to set
	 */
	public void setTransferredStock(long transferredStock) {
		this.transferredStock = transferredStock;
	}
	/**
	 * @return the txnDateUTC
	 */
	@Column(name="TXNDATUTC")
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getTxnDateUTC() {
		return txnDateUTC;
	}
	/**
	 * @param txnDateUTC the txnDateUTC to set
	 */
	public void setTxnDateUTC(Calendar txnDateUTC) {
		this.txnDateUTC = txnDateUTC;
	}
	/**
	 * @return the utilisedStock
	 */
	@Column(name="UTLSTK")
    public long getUtilisedStock() {
		return utilisedStock;
	}
	/**
	 * @param utilisedStock the utilisedStock to set
	 */
	public void setUtilisedStock(long utilisedStock) {
		this.utilisedStock = utilisedStock;
	}
	/**
	 * @param stockHolderPk the stockHolderPk to set
	 */
	public void setStockHolderPk(StockHolderStockDetailPK stockHolderPk) {
		this.stockHolderPk = stockHolderPk;
	}
	/**
	 * @return the stockHolderPk
	 */
	@EmbeddedId
    @AttributeOverrides({
 		@AttributeOverride(name="companyCode",
 				column=@Column(name="CMPCOD")),
 		@AttributeOverride(name="stockHolderCode",
 				column=@Column(name="STKHLDCOD")),
 				@AttributeOverride(name="documentType",
 		 				column=@Column(name="DOCTYP")),
 		 				@AttributeOverride(name="documentSubType",
 		 		 				column=@Column(name="DOCSUBTYP")),
 		 		 				@AttributeOverride(name="txnDateString",
 		 		 		 				column=@Column(name="TXNDATSTR"))}
    )
	public StockHolderStockDetailPK getStockHolderPk() {
		return stockHolderPk;
	}
	  //Added by Chippy for CR 1878
	
    public StockHolderStockDetail(StockDetailsVO 
    		stockDetailsVO,String txncode)throws SystemException{
    	
    	  log.log(log.INFO,"StockHolderStockDetail");
        populatePk(stockDetailsVO);
        populateAttributes(stockDetailsVO);
        try{
        PersistenceController.getEntityManager().persist(this);
        }catch(CreateException createException){
        	  log.log(Log.INFO, "Exception on saving-->", createException);
			throw new SystemException(createException.getErrorCode(),createException);
        }
        log.exiting(log.INFO+"--->","StockHolderStockDetail");
    }
	 private static StockControlDefaultsDAO constructDAO()
		throws SystemException, PersistenceException {
			EntityManager em = PersistenceController.getEntityManager();
			return StockControlDefaultsDAO.class.cast
			(em.getQueryDAO(StockControlDefaultsPersistenceConstants.MODULE_NAME));

		}
	 
    public static Page<StockDetailsVO> listStockDetails
    (StockDetailsFilterVO filterVO)throws SystemException {
    	try {
    		
    		return constructDAO().listStockDetails(filterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		
    }
    //Added by Chippy
/*    public StockHolderStockDetail (StockHolderStockDetailsVO 
    		stockHolderStockDetailsVO,String transactionDateString)throws SystemException {
    	
    	populatePk(stockHolderStockDetailsVO, transactionDateString);
    	populateAttributes(stockHolderStockDetailsVO);
    	 try{
         	PersistenceController.getEntityManager().persist(this);
         }catch(CreateException createException){
         	throw new SystemException(createException.getErrorCode());
         }
    }*/
    	public void update(StockDetailsVO stockDetailsVO,
    	String transactionDateString)throws SystemException{
		log.entering("StockHolderStockDetails","---------update---------");
		populateAttributes(stockDetailsVO);
		log.exiting("StockRequest","---------update---------");
	}
    
    private void populatePk(StockDetailsVO stockDetailsVO){
    	
    	StockHolderStockDetailPK stockHolderStockDetailPK=new StockHolderStockDetailPK();
    	stockHolderStockDetailPK.setCompanyCode(stockDetailsVO.getCompanyCode());
    	stockHolderStockDetailPK.setStockHolderCode(stockDetailsVO.getStockHolderCode());
    	stockHolderStockDetailPK.setDocumentType(stockDetailsVO.getDocumentType());
    	stockHolderStockDetailPK.setDocumentSubType(stockDetailsVO.getDocumentSubType());
    	
    	String issueDate = TimeConvertor.toStringFormat
		(stockDetailsVO.getTransactionDate().toGMTDate().toCalendar(),TimeConvertor.NORMAL_DATE_FORMAT);
    	issueDate=issueDate.replace("/","");
    	stockHolderStockDetailPK.setTxnDateString(Integer.parseInt(issueDate));
    	this.stockHolderPk=stockHolderStockDetailPK;
    }
    private void populateAttributes(StockDetailsVO stockDetailsVO){
    	
    	this.setUtilisedStock(stockDetailsVO.getUtilizedStock());
    	if(stockDetailsVO.getTransactionDate()!=null){
    		this.setTxnDateUTC(stockDetailsVO.getTransactionDate().toGMTDate());
       	}
    	this.setTransferredStock(stockDetailsVO.getTransferredStock());
    	this.setReturnUtilisedStock(stockDetailsVO.getReturnedUtilizedStock());
    	this.setReturnStock(stockDetailsVO.getReturnedStock());
    	this.setReceivedStock(stockDetailsVO.getReceivedStock());
    	this.setOpeningBalance(stockDetailsVO.getOpeningBalance());
    	this.setClosingBalance(stockDetailsVO.getAvailableBalance());
    	this.setAllocatedStock(stockDetailsVO.getAllocatedStock());
    	this.setBlackListedStock(stockDetailsVO.getBlacklistedStock());
    	
     	this.setLastUpdateTime(stockDetailsVO.getLastUpdatedTime());
    	this.setLastUpdateUser(stockDetailsVO.getLastUpdatedUser());
     	//this.setBlackListedStock)
    }
    
    public static StockHolderStockDetail find(StockHolderStockDetailPK pk)
    throws SystemException,FinderException {
    	StockHolderStockDetail stockHolderStockDetail=null;
		
		EntityManager entityManager = PersistenceController.getEntityManager();

		stockHolderStockDetail=entityManager.find(StockHolderStockDetail.class,pk);
		
		return stockHolderStockDetail;
    }
    public static  void updateStockUtilization(String source)throws SystemException{
    	try{
    		
    	constructDAO().updateStockUtilization(source);
    	}
    	catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
    }
}
