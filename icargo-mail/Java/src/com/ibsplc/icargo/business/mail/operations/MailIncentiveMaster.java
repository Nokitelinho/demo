/*
 * MailIncentiveMaster.java Created on SEP 10, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
@Entity
@Table(name = "MALMRAINCCFGMST")
@Staleable
public class MailIncentiveMaster {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SEPARATOR = "~";

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");


	private String poaCode;
	private String incentiveFlag;
	private String serviceResponsiveFlag;
	private double incentivePercentage;
	private Calendar validFrom;
	private Calendar validTo;
	private String dispFormulaExpression;
	private String formulaExpression;
	private String basisExpression;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private Set<MailIncentiveDetail> incentiveDetails;
	private MailIncentiveMasterPK mailIncentiveMasterPK;
	/**
	 * @return the poaCode
	 */
	@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	/**
	 * @return the incentiveFlag
	 */
	@Column(name = "INCFLG")
	public String getIncentiveFlag() {
		return incentiveFlag;
	}
	/**
	 * @param incentiveFlag the incentiveFlag to set
	 */
	public void setIncentiveFlag(String incentiveFlag) {
		this.incentiveFlag = incentiveFlag;
	}
	/**
	 * @return the serviceResponsiveFlag
	 */
	@Column(name = "SRVRSPLANFLG")
	public String getServiceResponsiveFlag() {
		return serviceResponsiveFlag;
	}
	/**
	 * @param serviceResponsiveFlag the serviceResponsiveFlag to set
	 */
	public void setServiceResponsiveFlag(String serviceResponsiveFlag) {
		this.serviceResponsiveFlag = serviceResponsiveFlag;
	}
	/**
	 * @return the incentivePercentage
	 */
	@Column(name = "INCPER")
	public double getIncentivePercentage() {
		return incentivePercentage;
	}
	/**
	 * @param incentivePercentage the incentivePercentage to set
	 */
	public void setIncentivePercentage(double incentivePercentage) {
		this.incentivePercentage = incentivePercentage;
	}
	/**
	 * @return the validFrom
	 */
	@Column(name = "VLDFRMDAT")
	public Calendar getValidFrom() {
		return validFrom;
	}
	/**
	 * @param validFrom the validFrom to set
	 */
	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}
	/**
	 * @return the validTo
	 */
	@Column(name = "VLDTOODAT")
	public Calendar getValidTo() {
		return validTo;
	}
	/**
	 * @param validTo the validTo to set
	 */
	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
	}
	/**
	 * @return the dispFormulaExpression
	 */
	@Column(name = "DISPAREXP")
	public String getDispFormulaExpression() {
		return dispFormulaExpression;
	}
	/**
	 * @param dispFormulaExpression the dispFormulaExpression to set
	 */
	public void setDispFormulaExpression(String dispFormulaExpression) {
		this.dispFormulaExpression = dispFormulaExpression;
	}
	/**
	 * @return the formulaExpression
	 */
	@Column(name = "PAREXP")
	public String getFormulaExpression() {
		return formulaExpression;
	}
	/**
	 * @param formulaExpression the formulaExpression to set
	 */
	public void setFormulaExpression(String formulaExpression) {
		this.formulaExpression = formulaExpression;
	}
	/**
	 * @return the basisExpression
	 */
	@Column(name = "BSSEXP")
	public String getBasisExpression() {
		return basisExpression;
	}
	/**
	 * @param basisExpression the basisExpression to set
	 */
	public void setBasisExpression(String basisExpression) {
		this.basisExpression = basisExpression;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
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
	 * @return the mailIncentiveMasterPK
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "incentiveSerialNumber", column = @Column(name = "INSSERNUM")) })
	public MailIncentiveMasterPK getMailIncentiveMasterPK() {
		return mailIncentiveMasterPK;
	}
	/**
	 * @param mailIncentiveMasterPK the mailIncentiveMasterPK to set
	 */
	public void setMailIncentiveMasterPK(MailIncentiveMasterPK mailIncentiveMasterPK) {
		this.mailIncentiveMasterPK = mailIncentiveMasterPK;
	}
	/**
	 * @return the incentiveDetails
	 */
	 @OneToMany
	    @JoinColumns( {
	    @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	    @JoinColumn(name = "INSSERNUM", referencedColumnName = "INSSERNUM", insertable=false, updatable=false)})
	public Set<MailIncentiveDetail> getIncentiveDetails() {
		return incentiveDetails;
	}
	/**
	 * @param incentiveDetails the incentiveDetails to set
	 */
	public void setIncentiveDetails(Set<MailIncentiveDetail> incentiveDetails) {
		this.incentiveDetails = incentiveDetails;
	}
	public MailIncentiveMaster() {
		
	}
	public MailIncentiveMaster(IncentiveConfigurationVO incentiveConfigurationVO) throws SystemException {
		log.entering(MODULE_NAME, "MailIncentiveMaster");
		populatePK(incentiveConfigurationVO);
		populateAttributes(incentiveConfigurationVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception);
		}
		incentiveConfigurationVO.setIncentiveSerialNumber(this.mailIncentiveMasterPK.getIncentiveSerialNumber());
		populateChildren(incentiveConfigurationVO);
		log.exiting(MODULE_NAME, "MailIncentiveMaster");
	}
	private void populatePK(IncentiveConfigurationVO incentiveConfigurationVO){
		MailIncentiveMasterPK mailIncentiveMasterPK = new MailIncentiveMasterPK();
		mailIncentiveMasterPK.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
		this.mailIncentiveMasterPK = mailIncentiveMasterPK;
	}
	private void populateAttributes(IncentiveConfigurationVO incentiveConfigurationVO){
		
		String newExpression = null;
		this.setPoaCode(incentiveConfigurationVO.getPaCode());
		this.setIncentiveFlag(incentiveConfigurationVO.getIncentiveFlag());
		this.setServiceResponsiveFlag(incentiveConfigurationVO.getServiceRespFlag());
		if(MailConstantsVO.FLAG_YES.equals(incentiveConfigurationVO.getIncentiveFlag())){
			this.setIncentivePercentage(incentiveConfigurationVO.getIncPercentage());
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			fromDate.setDate(incentiveConfigurationVO.getIncValidFrom());
			this.setValidFrom(fromDate.toCalendar());
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			toDate.setDate(incentiveConfigurationVO.getIncValidTo());
			this.setValidTo(toDate.toCalendar());
		}else if(MailConstantsVO.FLAG_NO.equals(incentiveConfigurationVO.getIncentiveFlag())){
			this.setIncentivePercentage(incentiveConfigurationVO.getDisIncPercentage());
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			fromDate.setDate(incentiveConfigurationVO.getDisIncValidFrom());
			this.setValidFrom(fromDate.toCalendar());
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			toDate.setDate(incentiveConfigurationVO.getDisIncValidTo());
			this.setValidTo(toDate.toCalendar());
			this.setBasisExpression(incentiveConfigurationVO.getBasis());
			this.setDispFormulaExpression(incentiveConfigurationVO.getFormula());
			
			String[] formula =  incentiveConfigurationVO.getFormula().split(SEPARATOR);
			
			for(int j=0;j<formula.length;j++){
				if (StringUtils.isNotEmpty(formula[j]) && StringUtils.isNumeric(formula[j])){
					formula[j] = formula[j].concat("/1440");
				}
				
				if(newExpression!= null){
					newExpression =newExpression.concat(formula[j]);
				}else{
					newExpression = formula[j];
				}
			}
	
		this.setFormulaExpression(newExpression);
		}
		
		
		this.setLastUpdatedTime(incentiveConfigurationVO.getLastUpdatedTime());
		this.setLastUpdatedUser(incentiveConfigurationVO.getLastUpdatedUser());
	}
	private void populateChildren(IncentiveConfigurationVO incentiveConfigurationVO) throws SystemException{
		log.entering("MailIncentiveMaster", "populateChildren");
		Collection<IncentiveConfigurationDetailVO> incentiveDetailVOs = incentiveConfigurationVO.
				getIncentiveConfigurationDetailVOs();
		if(incentiveDetailVOs != null && incentiveDetailVOs.size()>0){
			Set<MailIncentiveDetail> detailVOs = new HashSet<MailIncentiveDetail>();
			for(IncentiveConfigurationDetailVO incentiveDetailVO : incentiveDetailVOs){
				if(MailConstantsVO.FLAG_YES.equals(incentiveConfigurationVO.getIncentiveFlag())){
					if(incentiveDetailVO.getIncParameterCode()!= null && 
						incentiveDetailVO.getIncParameterValue()!= null &&
						incentiveDetailVO.getIncParameterCode().length()>0){
						incentiveDetailVO.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
						incentiveDetailVO.setLastUpdatedTime(incentiveConfigurationVO.getLastUpdatedTime());
						incentiveDetailVO.setLastUpdatedUser(incentiveConfigurationVO.getLastUpdatedUser());
						incentiveDetailVO.setIncentiveSerialNumber(incentiveConfigurationVO.getIncentiveSerialNumber());
						MailIncentiveDetail mailIncentiveDetail = new MailIncentiveDetail
								(incentiveDetailVO,incentiveConfigurationVO.getIncentiveFlag());
						detailVOs.add(mailIncentiveDetail);
					}
				}else if(MailConstantsVO.FLAG_NO.equals(incentiveConfigurationVO.getIncentiveFlag())){
					if(incentiveDetailVO.getDisIncParameterCode()!= null && 
							incentiveDetailVO.getDisIncParameterValue()!= null &&
							 incentiveDetailVO.getDisIncParameterCode().length()>0){
							incentiveDetailVO.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
							incentiveDetailVO.setLastUpdatedTime(incentiveConfigurationVO.getLastUpdatedTime());
							incentiveDetailVO.setLastUpdatedUser(incentiveConfigurationVO.getLastUpdatedUser());
							incentiveDetailVO.setIncentiveSerialNumber(
									incentiveConfigurationVO.getIncentiveSerialNumber());
							MailIncentiveDetail mailIncentiveDetail = new MailIncentiveDetail
									(incentiveDetailVO,incentiveConfigurationVO.getIncentiveFlag());
							detailVOs.add(mailIncentiveDetail);
						}
				}
			}
			this.setIncentiveDetails(detailVOs);
		}
	}
    /**
     * @param mailIncentiveMasterPK
	 * @return MailIncentiveMaster
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailIncentiveMaster find(MailIncentiveMasterPK mailIncentiveMasterPK ) 
	throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				MailIncentiveMaster.class,mailIncentiveMasterPK);
	}
	 /**
     * This method returns the Instance of the DAO
     * @return
     * @throws SystemException
     */
    private static MailTrackingDefaultsDAO constructDAO()
    		throws SystemException {
    	try {
    		EntityManager em = PersistenceController.getEntityManager();
    		return MailTrackingDefaultsDAO.class.cast(em.
    				getQueryDAO(MODULE_NAME));
    	}
    	catch(PersistenceException persistenceException) {
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    /**
	 *
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException {
		try {
			Collection<MailIncentiveDetail> incentiveDetails = this.getIncentiveDetails();
			if(incentiveDetails != null && incentiveDetails.size()>0){
				for(MailIncentiveDetail incentiveDetail : incentiveDetails){
					incentiveDetail.remove();
				}
				this.incentiveDetails.removeAll(incentiveDetails);
			}
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	public void update (IncentiveConfigurationVO incentiveConfigurationVO)throws SystemException{
		populateAttributes(incentiveConfigurationVO);
	}
	 /**
     * @param incentiveConfigurationFilterVO
	 * @return incentiveConfigurationFilterVO
	 * @throws SystemException
	 */
	public static  Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails (
			IncentiveConfigurationFilterVO incentiveConfigurationFilterVO)throws SystemException{
		try{
			return constructDAO().findIncentiveConfigurationDetails(incentiveConfigurationFilterVO);
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
