/*
 * MailInvoicPackage.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicPackageVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVPAC")
@Staleable
@Deprecated
public class MailInvoicPackage {
	private MailInvoicPackagePK mailInvoicPackagePK;
	
	private int packageCount;
	
	private String containerType;
	
	private String weightOfUnit;
	
	private double containerWeight;
	
	private double containerMinimumWeight;
	
	private String originOfficeOfExchange;
	
	private String destinationOfficeOfExchange;
	
	private String mailCategoryCode;
	
	private String mailSubclassCode;
	
	private String lastDigitOfYear;
	
	private String despatchSerialNumber;
	
	private String receptacleSerialNumber;
	
	private String highestReceptacleIndicator;
	
	private String registeredInsuredIndicator;
	
	private double receptacleWeight;
	
	private String reconciliationStatus;
	
	/**
	 * 
	 */
	public MailInvoicPackage(){
		
	}
	/**
	 * @param packageVO
	 * @throws SystemException
	 */
	public MailInvoicPackage(MailInvoicPackageVO packageVO)
	throws SystemException{
		MailInvoicPackagePK packagePK=new MailInvoicPackagePK();
		
		packagePK.setCompanyCode(packageVO.getCompanyCode());
		packagePK.setInvoiceKey(packageVO.getInvoiceKey());
		packagePK.setPoaCode(packageVO.getPoaCode());
		packagePK.setReceptacleIdentifier(packageVO.getReceptacleIdentifier());
		packagePK.setSectorDestination(packageVO.getSectorDestination());
		packagePK.setSectorOrigin(packageVO.getSectorOrigin());
		this.setMailInvoicPackagePK(packagePK);
		populateAttributes(packageVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	}
	/**
	 * @return Returns the containerMinimumWeight.
	 */
	@Column(name="CNTMINWGT")
	public double getContainerMinimumWeight() {
		return containerMinimumWeight;
	}

	/**
	 * @param containerMinimumWeight The containerMinimumWeight to set.
	 */
	public void setContainerMinimumWeight(double containerMinimumWeight) {
		this.containerMinimumWeight = containerMinimumWeight;
	}

	/**
	 * @return Returns the containerType.
	 */
	@Column(name="CNTTYP")
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the containerWeight.
	 */
	@Column(name="CNTWGT")
	public double getContainerWeight() {
		return containerWeight;
	}

	/**
	 * @param containerWeight The containerWeight to set.
	 */
	public void setContainerWeight(double containerWeight) {
		this.containerWeight = containerWeight;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	@Column(name="DSPSRLNUM")
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the destinationOfficeOfExchange.
	 */
	@Column(name="DSTEXGOFF")
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return Returns the highestReceptacleIndicator.
	 */
	@Column(name="HSTRCPNUM")
	public String getHighestReceptacleIndicator() {
		return highestReceptacleIndicator;
	}

	/**
	 * @param highestReceptacleIndicator The highestReceptacleIndicator to set.
	 */
	public void setHighestReceptacleIndicator(String highestReceptacleIndicator) {
		this.highestReceptacleIndicator = highestReceptacleIndicator;
	}

	/**
	 * @return Returns the lastDigitOfYear.
	 */
	@Column(name="LSTDGTYER")
	public String getLastDigitOfYear() {
		return lastDigitOfYear;
	}

	/**
	 * @param lastDigitOfYear The lastDigitOfYear to set.
	 */
	public void setLastDigitOfYear(String lastDigitOfYear) {
		this.lastDigitOfYear = lastDigitOfYear;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	@Column(name="MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailInvoicPackagePK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceKey", column=@Column(name="INVKEY")),
			@AttributeOverride(name="poaCode", column=@Column(name="POACOD")),
			@AttributeOverride(name="receptacleIdentifier", column=@Column(name="RCPIDR")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SECORG")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SECDST"))}
		)
	public MailInvoicPackagePK getMailInvoicPackagePK() {
		return mailInvoicPackagePK;
	}

	/**
	 * @param mailInvoicPackagePK The mailInvoicPackagePK to set.
	 */
	public void setMailInvoicPackagePK(MailInvoicPackagePK mailInvoicPackagePK) {
		this.mailInvoicPackagePK = mailInvoicPackagePK;
	}

	/**
	 * @return Returns the mailSubclassCode.
	 */
	@Column(name="MALSUBCOD")
	public String getMailSubclassCode() {
		return mailSubclassCode;
	}

	/**
	 * @param mailSubclassCode The mailSubclassCode to set.
	 */
	public void setMailSubclassCode(String mailSubclassCode) {
		this.mailSubclassCode = mailSubclassCode;
	}

	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	@Column(name="ORGEXGOFF")
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return Returns the packageCount.
	 */
	@Column(name="PACCNT")
	public int getPackageCount() {
		return packageCount;
	}

	/**
	 * @param packageCount The packageCount to set.
	 */
	public void setPackageCount(int packageCount) {
		this.packageCount = packageCount;
	}

	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	@Column(name="RCPSRLNUM")
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the receptacleWeight.
	 */
	@Column(name="RCPWGT")
	public double getReceptacleWeight() {
		return receptacleWeight;
	}

	/**
	 * @param receptacleWeight The receptacleWeight to set.
	 */
	public void setReceptacleWeight(double receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}

	/**
	 * @return Returns the reconciliationStatus.
	 */
	@Column(name="RCLSTA")
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}

	/**
	 * @param reconciliationStatus The reconciliationStatus to set.
	 */
	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	/**
	 * @return Returns the registeredInsuredIndicator.
	 */
	@Column(name="RCPRGDINS")
	public String getRegisteredInsuredIndicator() {
		return registeredInsuredIndicator;
	}

	/**
	 * @param registeredInsuredIndicator The registeredInsuredIndicator to set.
	 */
	public void setRegisteredInsuredIndicator(String registeredInsuredIndicator) {
		this.registeredInsuredIndicator = registeredInsuredIndicator;
	}

	/**
	 * @return Returns the weightOfUnit.
	 */
	@Column(name="WGTUNT")
	public String getWeightOfUnit() {
		return weightOfUnit;
	}

	/**
	 * @param weightOfUnit The weightOfUnit to set.
	 */
	public void setWeightOfUnit(String weightOfUnit) {
		this.weightOfUnit = weightOfUnit;
	}
	/**
	 * @param packageVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInvoicPackage find(MailInvoicPackageVO packageVO)
	throws SystemException, FinderException{
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering("MailInvoicPackage", "find");
		MailInvoicPackagePK mailInvoicPackagePKToFind = new MailInvoicPackagePK();
		mailInvoicPackagePKToFind.setCompanyCode(packageVO.getCompanyCode());
		mailInvoicPackagePKToFind.setInvoiceKey(packageVO.getInvoiceKey());
		mailInvoicPackagePKToFind.setPoaCode(packageVO.getPoaCode());
		mailInvoicPackagePKToFind.setReceptacleIdentifier(packageVO.getReceptacleIdentifier());
		mailInvoicPackagePKToFind.setSectorDestination(packageVO.getSectorDestination());
		mailInvoicPackagePKToFind.setSectorOrigin(packageVO.getSectorOrigin());
		log.exiting("MailInvoicPackage", "find");
		return PersistenceController.getEntityManager().find(
				MailInvoicPackage.class, mailInvoicPackagePKToFind);
	}
	/**
	 * @param packageVO
	 */
	private void populateAttributes(MailInvoicPackageVO packageVO){
		this.setPackageCount(packageVO.getPackageCount());
		this.setContainerType(packageVO.getContainerType());
		this.setWeightOfUnit(packageVO.getWeightOfUnit());
		this.setContainerWeight(packageVO.getContainerWeight());
		this.setContainerMinimumWeight(packageVO.getContainerMinimumWeight());
		this.setOriginOfficeOfExchange(packageVO.getOriginOfficeOfExchange());
		this.setDestinationOfficeOfExchange(packageVO.getDestinationOfficeOfExchange());
		this.setMailCategoryCode(packageVO.getMailCategoryCode());
		this.setMailSubclassCode(packageVO.getMailSubclassCode());
		this.setLastDigitOfYear(packageVO.getLastDigitOfYear());
		this.setDespatchSerialNumber(packageVO.getDespatchSerialNumber());
		this.setReceptacleSerialNumber(packageVO.getReceptacleSerialNumber());
		this.setHighestReceptacleIndicator(packageVO.getHighestReceptacleIndicator());
		this.setRegisteredInsuredIndicator(packageVO.getRegisteredInsuredIndicator());
		this.setReceptacleWeight(packageVO.getReceptacleWeight());
		this.setReconciliationStatus(packageVO.getReconciliationStatus());
		
	}
	
	/**
	 * @param companyCode
	 * @param sectororigin
	 * @param sectorDest
	 * @param payType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static String findDuplicateRecticles(String companyCode,
			String sectororigin,String sectorDest,String payType,String recpIdr) throws SystemException{
		try{
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO( "mail.mra.defaults")).findDuplicateRecticles(companyCode,
							 sectororigin,sectorDest,payType,recpIdr);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
			
}