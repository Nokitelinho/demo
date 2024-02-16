/*
 * StockRangeUtilisationLog.java Created on Dec 01, 2009
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
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionException;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *            
 * @author A-3155
 *
 */
@Table(name="STKRNGUTLLOG")
@Entity
public class StockRangeUtilisationLog {

	private  Log log = LogFactory.getLogger(this.getClass().getName());   
	
	 private StockRangeUtilisationLogPK stockRangeUtilisationLogPK;
	 
	 private Calendar lastUpdateTime;
	    
	 private String lastUpdateUser;

	private static final String STOCKCONTROL_DEFAULTS_ENHANCED_STOCK_UTILISATION = "stockcontrol.defaults.enhancedStockUtilisation";
	/**
	 * @return the stockRangeUtilisationLogPK
 */
   @EmbeddedId
    @AttributeOverrides({
	@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	@AttributeOverride(name="airlineIdentifier",column=@Column(name="ARLIDR")),
 	@AttributeOverride(name="documentNumber",column=@Column(name="MSTDOCNUM")),
 	@AttributeOverride(name="documentType",column=@Column(name="DOCTYP")),
 	@AttributeOverride(name="documentSubType",column=@Column(name="DOCSUBTYP"))}
	)
	public StockRangeUtilisationLogPK getStockRangeUtilisationLogPK() {
		return stockRangeUtilisationLogPK;
	}
           
	/**
	 * @param stockRangeUtilisationLogPK the stockRangeUtilisationLogPK to set
	 */
	
	public void setStockRangeUtilisationLogPK(
			StockRangeUtilisationLogPK stockRangeUtilisationLogPK) {
		this.stockRangeUtilisationLogPK = stockRangeUtilisationLogPK;
	}

	/**
	 * @return the lastUpdateTime
	 */  
	                  
	@Column(name = "LSTUPDTIMUTC")
	@Temporal(TemporalType.TIMESTAMP) 
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}                 

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}  
	
	/**
     * Default Constructor
     *
     */
    public StockRangeUtilisationLog(){

    }
    /**
     * @author A-3155
     * @param lastUpdateUser
     * @param lastUpdateTime
     * @throws SystemException
     */
    private void populateAttributes(String lastUpdateUser,GMTDate lastUpdateTime) throws SystemException{
    	log.entering(" StockRangeUtilisationLog ", " populateAttributes ");
       	this.lastUpdateUser = lastUpdateUser;
       	this.lastUpdateTime = lastUpdateTime;
    	log.exiting(" StockRangeUtilisationLog ", " populateAttributes ");
     }
    /**
     * @author A-3155
     * @param companyCode
     * @param stockHolderCode
     * @param docType
     * @param docSubType
     * @param airlineIdentifier
     * @param documentNumber
     */
    private void populatePK(String companyCode,int airlineIdentifier,String documentNumber, String documentType, String documentSubType){
	log.entering(" StockRangeUtilisationLog ", " populatePK ");
	stockRangeUtilisationLogPK = new StockRangeUtilisationLogPK();
	stockRangeUtilisationLogPK.setCompanyCode(companyCode);
	stockRangeUtilisationLogPK.setAirlineIdentifier(airlineIdentifier);
	stockRangeUtilisationLogPK.setDocumentNumber(documentNumber);
	stockRangeUtilisationLogPK.setDocumentType(documentType);
	stockRangeUtilisationLogPK.setDocumentSubType(documentSubType);
	this.setStockRangeUtilisationLogPK(stockRangeUtilisationLogPK);
	log.exiting(" StockRangeUtilisationLog ", " populatePK ");
	}
    /**
     * @author A-3155
     * @param companyCode
     * @param airlineIdentifier
     * @param documentNumber
     * @throws SystemException
     */                       
    public StockRangeUtilisationLog(String companyCode,int airlineIdentifier,String documentNumber, String documentType, String documentSubType)
	throws SystemException{
    	log.entering(" StockRangeUtilisationLog ", " Entity Save ");
    	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
     	LocalDate updatedTime=new LocalDate(logon
    			.getStationCode(),Location.STN,true);
     	populateAttributes(logon.getUserId(), updatedTime.toGMTDate()); 
     	populatePK(companyCode,airlineIdentifier,documentNumber,documentType,documentSubType);
	    Map<String,String> systemParameters=null;
	    Collection<String> parameterCodes=new ArrayList<>();
	    parameterCodes.add(STOCKCONTROL_DEFAULTS_ENHANCED_STOCK_UTILISATION);
	    try {
			systemParameters=new SharedDefaultsProxy().findSystemParameterByCodes(parameterCodes);
		} catch (ProxyException e) {
		log.log(Log.SEVERE, "Exception Caught>>>>"+e.getMessage());
		}
	    if(systemParameters!=null && StockRangeVO.FLAG_YES.equals(systemParameters.get(STOCKCONTROL_DEFAULTS_ENHANCED_STOCK_UTILISATION))){
		    enhancedFlow(companyCode, airlineIdentifier, documentNumber, documentType, documentSubType);
	    }else{
		    normalFlow();
	    }
     	log.exiting(" StockRangeUtilisationLog ", " Entity Save Transaction Commited");
    }
    /**
     * 
     * 	Method		:	StockRangeUtilisationLog.enhancedFlow
     *	Added by 	:	A-8146 on 22-Aug-2019
     * 	Used for 	:
     *	Parameters	:	@param companyCode
     *	Parameters	:	@param airlineIdentifier
     *	Parameters	:	@param documentNumber
     *	Parameters	:	@param documentType
     *	Parameters	:	@param documentSubType
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	void
     */
	private void enhancedFlow(String companyCode, int airlineIdentifier, String documentNumber, String documentType,
			String documentSubType) throws SystemException {
	    Transaction tx  = null;
    	try{
    		EntityManager em = PersistenceController.getEntityManager();
    		StockControlDefaultsDAO stockControlDefaultsDAO = StockControlDefaultsDAO.class
					.cast(em.getQueryDAO(StockControlDefaultsPersistenceConstants.MODULE_NAME));
    		stockControlDefaultsDAO
				.saveStockRangeUtilisationLog(companyCode, airlineIdentifier,  documentNumber,
						 documentType,  documentSubType, lastUpdateUser, lastUpdateTime);
		}catch(Exception exception){
			throw new SystemException(exception.getMessage());
		}
	}
    /**
     * 
     * 	Method		:	StockRangeUtilisationLog.normalFlow
     *	Added by 	:	A-8146 on 22-Aug-2019
     * 	Used for 	:
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	void
     */
	private void normalFlow() throws SystemException {
	    boolean success = false;;
	    Transaction tx  = null;
	                    
    	try{
	           TransactionProvider tp = PersistenceController.getTransactionProvider();
	            tx = tp.getNewTransaction(false);
		PersistenceController.getEntityManager().persist(this);
	            
	            success = true;
		}catch(CreateException createException){
        	
        	 //log.exiting(" StockRangeUtilisationLog ", "Thrown From first try catch ABCD"+createException.getClass());
			throw new SystemException(createException.getErrorCode());
		}                
		finally {
			try{
				if(tx!=null){
					if (success)
		tx.commit();    
					else
						tx.rollback();
				}
			}catch(TransactionException e){
				
				//log.exiting(" StockRangeUtilisationLog ", "Thrown from Finalyy 12345"+e.getClass());
				throw new SystemException(e.getMessage());
			}
		
		}
    
    }

}
