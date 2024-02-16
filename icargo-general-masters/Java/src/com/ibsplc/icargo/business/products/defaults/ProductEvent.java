/*
 * ProductEvent.java Created on Jun 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-1358
 * ProductEvent is for ProductEvents
 *	
 */
 @Table(name="PRDEVT")
 @Entity
 @Staleable
 public class ProductEvent {
	
    private ProductEventPK productEventPk;
    
    /**
     * Minimum timegap before the occurance of the milestone
     */
    private double minimumTime;

    /**
     * Maximum timegap before the occurance of the milestone
     */
    private double maximumTime;

    /**
     * Duration of the milestone
     */
    private int duration;

    /**
     * Indicates whether the milestone is internal to the airline or
     * external to the customer
     */
    private String isExternal;

    /**
     * Identifies whether the milestone is associated to Import or Export
     */
    private String isExport;

    /**
     * offset time from minimum time in minutes before which alert
     * has to be sent
     */
    private double alertTime;

    /**
     * offset time from maximum time in minutes after which alert
     * has to be sent
     */
    private double chaserTime;
    
    private double chaserFrequency;
    
    private int maxNoOfChasers;
    
    private String isInternal;
    
    private String transitFlag;

    /**
     * Default Constructor
     *
     */
	public ProductEvent(){
	 }
	/**
	 * @author A-1885
	 * Constructor with arguments for creating productEvent
	 * @param companyCode
	 * @param productCode
	 * @param productEventVo
	 * @throws SystemException
	 * @return ProductEvent
	 */
	public ProductEvent(String companyCode,String productCode,
			ProductEventVO productEventVo)throws SystemException{
	 	populatePk(companyCode,productCode,productEventVo);
	 	populateAttribute(productEventVo);
	 	try{
	 	PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
	
	/**
	* @return Returns the productEventPk.
	*/
	@EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			@AttributeOverride(name="eventCode", column=@Column(name="EVTCOD")),
			@AttributeOverride(name="eventType", column=@Column(name="EVTTYP"))}
	 )
    public ProductEventPK getProductEventPk() {
        return productEventPk;
    }
    /**
     * @param productEventPk The productEventPk to set.
     */
    public void setProductEventPk(ProductEventPK productEventPk) {
        this.productEventPk = productEventPk;
    }

    /**
     * @return Returns the alertTime.
     */
    @Column(name="EVTALTTIM")
    public double getAlertTime() {
        return alertTime;
    }
    /**
     * @param alertTime The alertTime to set.
     */
    public void setAlertTime(double alertTime) {
        this.alertTime = alertTime;
    }
    /**
     * @return Returns the chaserTime.
     */
    @Column(name="EVTCHRTIM")
    public double getChaserTime() {
        return chaserTime;
    }
    /**
     * @param chaserTime The chaserTime to set.
     */
    public void setChaserTime(double chaserTime) {
        this.chaserTime = chaserTime;
    }
    /**
     * @return Returns the maximumTime.
     */
    @Column(name="MAXTIM")
    public double getMaximumTime() {
        return maximumTime;
    }
    /**
     * @param maximumTime The maximumTime to set.
     */
    public void setMaximumTime(double maximumTime) {
        this.maximumTime = maximumTime;
    }
    /**
     * @return Returns the minimumTime.
     */
    @Column(name="MINTIM")
    public double getMinimumTime() {
        return minimumTime;
    }
    /**
     * @param minimumTime The minimumTime to set.
     */
    public void setMinimumTime(double minimumTime) {
        this.minimumTime = minimumTime;
    }
    /**
     * @return Returns the duration.
     */
    @Column(name="EVTDUR")
    public int getDuration() {
        return duration;
    }
    /**
     * @param duration The duration to set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    /**
     * @return Returns the isExport.
     */
    @Column(name="EXPFLG")
    public String getIsExport() {
        return isExport;
    }
    /**
     * @param isExport The isExport to set.
     */
    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }
    /**
     * @return Returns the isExternal.
     */
    @Column(name="EXTFLG")
    public String getIsExternal() {
        return isExternal;
    }
    /**
     * @param isExternal The isExternal to set.
     */
    public void setIsExternal(String isExternal) {
        this.isExternal = isExternal;
    }
    /**
     * Method for getting the chaser frequence
     * @return double
     */
    @Column(name="EVTCHRFRQ")
	public double  getChaserFrequency(){
    	return this.chaserFrequency;
    }
    /**
     * Method for setting the chaser frequency
     * @param chaserFrequency
     */
    public void setChaserFrequency(double chaserFrequency){
    	this.chaserFrequency=chaserFrequency;
    }
    /**
     * Method for getting maximum number of chasers
     * @return int
     */
    @Column(name="EVTMAXCHR")
    public int getMaxNoOfChasers() {
		return maxNoOfChasers;
	}
    /**
     * Method for setting maximum number of chaser
     * @param maxNoOfChasers
     * @return
     */
    public void setMaxNoOfChasers(int maxNoOfChasers) {
		this.maxNoOfChasers = maxNoOfChasers;
	}
    /**
     * Method for getting the internal flag
     * @return String
     */
    @Column(name="INTFLG")
    public String getIsInternal(){
    	return isInternal;
    }
    /**
     * Method for setting the internal flag
     * @param internal
     * @return
     */
    public void setIsInternal(String internal){
    	this.isInternal=internal;
    }
    /**
	 * @return Returns the transitFlag.
	 */
    @Column(name="TRNFLG")
	public String getTransitFlag() {
		return transitFlag;
	}
	/**
	 * @param transitFlag The transitFlag to set.
	 */
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}
    /**
     * @author A-1885
     * remove the object 
     * @throws SystemException
     */
    public void remove()throws SystemException{
	   	EntityManager entityManager = PersistenceController.getEntityManager();
		try{
	   		entityManager.remove(this);
		}catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}
	}
	/**
	 * update the attribute
	 * @param companyCode
	 * @param productCode
	 * @param productEventVo
	 */
	public void update(String companyCode,String productCode,ProductEventVO productEventVo){
		populateAttribute(productEventVo);
	}
	/**
	 * @author A-1885
	 * Method for retrieving ProductEventVO
	 * @return ProductEventVO
	 */
	public ProductEventVO retrieveVO(){
		ProductEventVO productEventVO = new ProductEventVO();
		productEventVO.setEventType(this.getProductEventPk().getEventType());
		productEventVO.setEventCode(this.getProductEventPk().getEventCode());
		productEventVO.setAlertTime(this.getAlertTime());
		productEventVO.setChaserTime(this.getChaserTime());
		productEventVO.setDuration(this.getDuration());
		productEventVO.setMaximumTime(this.getMaximumTime());
		productEventVO.setMinimumTime(this.getMinimumTime());
		productEventVO.setIsExport(this.getIsExport());
		if(this.getIsExternal().equals(ProductEventVO.FLAG_YES))
		{
			productEventVO.setExternal(true);
		}
		else
		{
			productEventVO.setExternal(false);
		}
		productEventVO.setTransit((ProductEventVO.FLAG_YES.equals(this.getTransitFlag())) ? true :false);
		return productEventVO;
		
	}
	/**
	 * @author A-1885
	 * Method for populating Primary Key of ProductEvent
	 * @param companyCode
	 * @param productCode
	 * @param productEventVo
	 */
	private void populatePk(String companyCode,String productCode,ProductEventVO productEventVo){
	 	ProductEventPK productEventPK = new ProductEventPK();
	 	productEventPK.setCompanyCode(companyCode);
	 	productEventPK.setProductCode(productCode);
	 	productEventPK.setEventCode(productEventVo.getEventCode());
	 	productEventPK.setEventType(productEventVo.getEventType());
	 	this.productEventPk = productEventPK;
	}
	/**
	 * @author A-1885
	 * Method for populating Other attributes of ProductEvent
	 * @param productEventVo
	 */
	private void populateAttribute(ProductEventVO productEventVo){
		this.minimumTime=productEventVo.getMinimumTime();
		this.maximumTime=productEventVo.getMaximumTime();
		this.duration=productEventVo.getDuration();
		if(productEventVo.isExternal()){
			this.isExternal=ProductEventVO.FLAG_YES;
		}
		else{
			this.isExternal=ProductEventVO.FLAG_NO;
		}
		if(productEventVo.isInternal()){
			this.isInternal=ProductEventVO.FLAG_YES;
		}
		else
		{
			this.isInternal=ProductEventVO.FLAG_NO;
		}
		if("Export".equals(productEventVo.getEventType())){
			this.isExport = "Y";
		}
		else{
			this.isExport = "N";
		}
		//this.isExport=productEventVo.getIsExport();
		//this.isExport="N";
		this.alertTime=productEventVo.getAlertTime();
		this.chaserTime=productEventVo.getChaserTime();
		this.chaserFrequency=productEventVo.getChaserFrequency();
		this.maxNoOfChasers=productEventVo.getMaxNoOfChasers();
		this.transitFlag = (productEventVo.isTransit() ?  ProductEventVO.FLAG_YES 
				  :ProductEventVO.FLAG_NO);
	}
	
}
