/*
 * StockRequestForOAL.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 *
 */
@Staleable
@Table(name="STKREQOAL")
@Entity
public class StockRequestForOAL {
    
    private StockRequestForOALPK stockRequestForOALPK;
    
    private String modeOfCommunication;
    
    private String content;
    
    private Calendar requestedDate;
    
    private String isRequestCompleted;    
    
    private String address;
    
    private Calendar actionTime;
    
    /**
     * @return Returns the content.
     */
    @Column(name="REQCNT")    
    public String getContent() {
        return content;
    }
    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return Returns the isRequestCompleted.
     */
    @Column(name="REQCMPFLG")    
    public String getIsRequestCompleted() {
        return isRequestCompleted;
    }
    /**
     * @param isRequestCompleted The isRequestCompleted to set.
     */
    public void setIsRequestCompleted(String isRequestCompleted) {
        this.isRequestCompleted = isRequestCompleted;
    }
    /**
     * @return Returns the modeOfCommunication.
     */
    @Column(name="MODCOM")    
    public String getModeOfCommunication() {
        return modeOfCommunication;
    }
    /**
     * @param modeOfCommunication The modeOfCommunication to set.
     */
    public void setModeOfCommunication(String modeOfCommunication) {
        this.modeOfCommunication = modeOfCommunication;
    }
    /**
     * @return Returns the requestedDate.
     */
    @Column(name="REQDAT")  
    @Temporal(TemporalType.DATE)
    public Calendar getRequestedDate() {
        return requestedDate;
    }
    /**
     * @param requestedDate The requestedDate to set.
     */
    public void setRequestedDate(Calendar requestedDate) {
        this.requestedDate = requestedDate;
    }

    /**
     * @return Returns the stockRequestForOALPK.
     */
    
     @EmbeddedId
     @AttributeOverrides({
	 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	 @AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
	 @AttributeOverride(name="documentType", column=@Column(name="DOCTYP")),
	 @AttributeOverride(name="documentSubType", column=@Column(name="DOCSUBTYP")),
	 @AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),		
	 @AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))})	 
	public StockRequestForOALPK getStockRequestForOALPK() {
        return stockRequestForOALPK;
    }
    /**
     * @param stockRequestForOALPK The stockRequestForOALPK to set.
     */
    public void setStockRequestForOALPK(
            StockRequestForOALPK stockRequestForOALPK) {
        this.stockRequestForOALPK = stockRequestForOALPK;
    }
	/**
	 * @return Returns the actionTime.
	 */
    @Column(name="ACTTIM") 
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getActionTime() {
		return actionTime;
	}
	/**
	 * @param actionTime The actionTime to set.
	 */
	public void setActionTime(Calendar actionTime) {
		this.actionTime = actionTime;
	}
	/**
	 * @return Returns the address.
	 */
	@Column(name="REQADR") 
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Log
	 */
	private static Log localLogger() {
		return LogFactory.getLogger("STOCKCONTROL_DEFAULTS");
	}
    /**
     * 
     *
     */
    public StockRequestForOAL(){
        
    }
    
    /**
     * 
     * @param stockRequestForOALVO
     * @throws SystemException
     */
    public StockRequestForOAL(StockRequestForOALVO stockRequestForOALVO)
    			throws SystemException{
    	localLogger().entering("StockRequestForOAL","StockRequestForOAL");
        populatePK(stockRequestForOALVO);
        populateAttributes(stockRequestForOALVO);
        try{
        PersistenceController.getEntityManager().persist(this);
        }catch(CreateException createException){
        	throw new SystemException(createException.getErrorCode(),createException);
        }
        localLogger().exiting("StockRequestForOAL","StockRequestForOAL");
    }    
    
    /**
     * @param companyCode
     * @param airportCode
     * @param documentType
     * @param documentSubType
     * @param airlineIdentifier
     * @param serialNumber
     * @return
     * @throws SystemException
     * @throws FinderException
     */
    public static StockRequestForOAL find(String companyCode,String airportCode,
            	String documentType,String documentSubType,int airlineIdentifier,
            	int serialNumber) throws SystemException, FinderException {
        StockRequestForOALPK oalPk = new StockRequestForOALPK();
        oalPk.setAirlineIdentifier(airlineIdentifier);
        oalPk.setAirportCode(airportCode);
        oalPk.setCompanyCode(companyCode);
        oalPk.setDocumentSubType(documentSubType);
        oalPk.setDocumentType(documentType);
        oalPk.setSerialNumber(serialNumber);
        return PersistenceController.getEntityManager().find(StockRequestForOAL.class, oalPk);
    }
    
    
    /**
     * 
     * @throws SystemException
     */
    public void remove() throws SystemException {
        
    }
    
    /**
     * @author A-1863
     * @param stockRequestForOALVO
     */
    private void populatePK(StockRequestForOALVO stockRequestForOALVO){
    	localLogger().entering("StockRequestForOAL","populatePK");
    	StockRequestForOALPK requestForOALPK = new StockRequestForOALPK();
    	requestForOALPK.setAirlineIdentifier(stockRequestForOALVO.getAirlineId());
    	requestForOALPK.setAirportCode(stockRequestForOALVO.getAirportCode());
    	requestForOALPK.setCompanyCode(stockRequestForOALVO.getCompanyCode());
    	requestForOALPK.setDocumentSubType(stockRequestForOALVO.getDocumentSubType());
    	requestForOALPK.setDocumentType(stockRequestForOALVO.getDocumentType());
    	this.setStockRequestForOALPK(requestForOALPK);
    	localLogger().exiting("StockRequestForOAL","populatePK");
    }
    
    /**
     * @author A-1863
     * @param stockRequestForOALVO
     */
    private void populateAttributes(StockRequestForOALVO stockRequestForOALVO){
    	localLogger().entering("StockRequestForOAL","populateAttributes");
    	if(stockRequestForOALVO.getActionTime()!=null){
    		this.setActionTime(stockRequestForOALVO.getActionTime().toCalendar());
    	}
    	this.setAddress(stockRequestForOALVO.getAddress());
    	this.setContent(stockRequestForOALVO.getContent());
    	if(stockRequestForOALVO.isRequestCompleted()){
    		this.setIsRequestCompleted("Y");
    	}else{
    		this.setIsRequestCompleted("N");
    	}
    	this.setModeOfCommunication(stockRequestForOALVO.getModeOfCommunication());
    	if(stockRequestForOALVO.getRequestedDate()!=null){
    		this.setRequestedDate(stockRequestForOALVO.getRequestedDate().toCalendar());
    	}
    }

}
