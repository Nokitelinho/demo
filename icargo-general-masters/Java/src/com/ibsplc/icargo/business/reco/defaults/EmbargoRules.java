

/*
 * Embargo.java Created on Jul 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLocalLanguageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesDAO;
import com.ibsplc.icargo.persistence.dao.reco.defaults.EmbargoRulesSqlDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;

/**
 * This class handles the persistence of Embargo
 *
 * @author A-1358
 *
 */
//@Staleable
@Table(name = "RECMST")
@Entity
public class EmbargoRules {

	private EmbargoRulesPK embargoRulesPk;

	private Calendar startDate;

	private Calendar endDate;

	private String category;
	
	private String complianceType;
	
	private String applicableTransactions;
	
	private String isSuspended;
	
	/*
	

	/*
	 * Possible values are 'Active', 'Cancelled'
	 */
	private String status;

	/*
	 * Indicates the severity of the embargo. This field determines whether the
	 * embargo results in an error, warning or information Possible values are
	 * E{ERROR}, W{WARNING}, I{INFO}
	 */
	private String embargoLevel;

	/*
	 * Description Of the embargo. If the embargoLevel is INFO, then this is
	 * shown to the user as information message
	 */
	private String embargoDescription;

	/*
	 * General embargo remarks
	 */
	private String remarks;

	/*
	 * Indicates whether the embargo is enabled or disabled
	 */

	/*
	 * Holds the parameters over which the
	 * embargo is defined Set<EmbargoParameter>
	 */
	private Set<EmbargoRulesParameter> parameters;
	
	
	/*
	 * For Optimistic locking
	 */
	private Calendar lastUpdatedTime;

	/*
	 * For Optimistic locking
	 */
	private String lastUpdatedUser;
	
	//Added by A-5160 for ICRD-27155
	private String airportCode;
	
	private String ruleType;
	
	
	@Column(name = "RULTYP")
	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	@Column(name = "USRSTNCOD")
	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the embargoPk.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "embargoReferenceNumber", column = @Column(name = "REFNUM")),
			@AttributeOverride(name = "embargoVersion", column = @Column(name = "VERNUM"))})
	public EmbargoRulesPK getEmbargoRulesPk() {
		return embargoRulesPk;
	}

	/**
	 * @param embargoPk
	 *            The embargoPk to set.
	 */
	public void setEmbargoRulesPk(EmbargoRulesPK embargoRulesPk) {
		this.embargoRulesPk = embargoRulesPk;
	}

	

	/**
	 * @return Returns the embargoDescription.
	 */
	@Audit(name = "EmbargoDescription")
	@Column(name = "RECDES")
	public String getEmbargoDescription() {
		return embargoDescription;
	}

	/**
	 * @param embargoDescription
	 *            The embargoDescription to set.
	 */
	public void setEmbargoDescription(String embargoDescription) {
		this.embargoDescription = embargoDescription;
	}

	/**
	 * @return Returns the embargoLevel.
	 */
	@Audit(name = "EmbargoLevel")
	@Column(name = "RECTYP")
	public String getEmbargoLevel() {
		return embargoLevel;
	}

	/**
	 * @param embargoLevel
	 *            The embargoLevel to set.
	 */
	public void setEmbargoLevel(String embargoLevel) {
		this.embargoLevel = embargoLevel;
	}

	/**
	 * @return Returns the endDate.
	 */
	@Audit(name = "EndDate")
	@Column(name = "ENDDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            The endDate to set.
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	
	@Audit(name = "IsSuspended")
	@Column(name = "SUSFLG")
	public String getIsSuspended() {
		return isSuspended;
	}

	/**
	 *
	 * @param isSuspended
	 */
	public void setIsSuspended(String isSuspended) {

		this.isSuspended = isSuspended;
	}
	
	
	/**
	 * @return Returns the remarks.
	 */
	@Audit(name = "Remarks")
	@Column(name = "RECRMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the startDate.
	 */
	@Audit(name = "StartDate")
	@Column(name = "STRDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            The startDate to set.
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the status.
	 */
	@Audit(name = "Status")
	@Column(name = "RECSTA")
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the parameters. Set<EmbargoParameters>
	 */

	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "REFNUM", referencedColumnName = "REFNUM", insertable = false, updatable = false),
			@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable = false, updatable = false)})
	public Set<EmbargoRulesParameter> getParameters() {

		return parameters;
	}

	/**
	 * @param parameters
	 *            The parameters to set. Set<EmbargoParameters>
	 */
	public void setParameters(Set<EmbargoRulesParameter> parameters) {
		this.parameters = parameters;
	}



	/**
	 * @return Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	
	

	
	@Audit(name = "Category")
	@Column(name = "CATTYP")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Audit(name = "Compliance Type")
	@Column(name = "CMPTYP")
	public String getComplianceType() {
		return complianceType;
	}

	public void setComplianceType(String complianceType) {
		this.complianceType = complianceType;
	}
	
	@Audit(name = "Applicable Transactions")
	@Column(name = "APPTXN")
	public String getApplicableTransactions() {
		return applicableTransactions;
	}

	public void setApplicableTransactions(String applicableTransactions) {
		this.applicableTransactions = applicableTransactions;
	}
	// added for ICRD-213193 by A-7815
	private Set<EmbargoRulesLocalLanguage> embargoRulesLocalLanguage;

	/**
	 * This method checks whether a duplicate embargo exists in the system
	 *
	 * @param embargoVO
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static boolean checkDuplicateEmbargo(EmbargoRulesVO embargoVO)
			throws SystemException {

		EntityManager entityManager = PersistenceController.getEntityManager();

		EmbargoRulesDAO embargoDAO = null;
		try {
			embargoDAO = EmbargoRulesSqlDAO.class.cast(entityManager
					.getQueryDAO("reco.defaults"));
			return embargoDAO.checkDuplicateEmbargo(embargoVO);
		} catch (PersistenceException e) {
//printStackTraccee()();
			throw new SystemException(e.getErrorCode());

		}

	}

	/**
	 * Used to list the details of a selected embargo
	 *
	 * @param companyCode
	 * @param embargoReferenceNumber
	 * @return EmbargoVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static EmbargoRulesVO findEmbargoDetails(EmbargoFilterVO embargoFilterVO) throws SystemException,
			PersistenceException {
		EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));

		return embargoDAO.findEmbargoDetails(embargoFilterVO);

	}

	/**
	 * This method finds embargos which meet the filter
	 *
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */

	public static Page findEmbargos(EmbargoFilterVO filterVO, int pageNumber)
			throws SystemException, PersistenceException {

		EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));

		return embargoDAO.findEmbargos(filterVO, pageNumber);
	}

	/**
	 *
	 * @param embargoVO
	 *
	 * Populates EmbargoPk
	 */
	private void populatePk(EmbargoRulesVO embargoVO) throws SystemException {
		EmbargoRulesPK embargoRulesPK = new EmbargoRulesPK();
		embargoRulesPK.setCompanyCode(   embargoVO.getCompanyCode());
		embargoRulesPK.setEmbargoReferenceNumber(   embargoVO
				.getEmbargoReferenceNumber());
		setEmbargoRulesPk(embargoRulesPK);

	}

	/**
	 * Populates the attributes in Embargo
	 *
	 * @param embargoVO
	 */
	private void populateAttributes(EmbargoRulesVO embargoVO) {

		
		setEmbargoDescription(embargoVO.getEmbargoDescription());
		setEmbargoLevel(embargoVO.getEmbargoLevel());

		

		setEndDate(embargoVO.getEndDate());
		setLastUpdatedTime(embargoVO.getLastUpdatedTime());
		setLastUpdatedUser(embargoVO.getLastUpdatedUser());
		

		setRemarks(embargoVO.getRemarks());
		setStartDate(embargoVO.getStartDate());
		if (embargoVO.getIsSuspended()) {
			setIsSuspended("Y");
		}

		else{
			setIsSuspended("N");
		}
		setStatus(embargoVO.getStatus());
		
		setCategory(embargoVO.getCategory());
		setComplianceType(embargoVO.getComplianceType());
		setApplicableTransactions(embargoVO.getApplicableTransactions());
		
	
		setAirportCode(embargoVO.getAirportCode());
		setRuleType(embargoVO.getRuleType());
	}

	/**
	 * Populates the parameters in embargo
	 *
	 * @param embargoVO
	 * @throws SystemException
	 * @throws CreateException
	 */

	private void populateChilden(EmbargoRulesVO embargoVO) throws SystemException,
			CreateException {

		embargoVO.setEmbargoVersion(getEmbargoRulesPk().getEmbargoVersion());
		
		if (embargoVO.getParameters() != null
				&& embargoVO.getParameters().size() > 0) {
			Set<EmbargoRulesParameter> embargoParameters =
				new HashSet<EmbargoRulesParameter>();
					
			for (EmbargoParameterVO embargoParameterVO : embargoVO
					.getParameters()) {
				
				// added by A-3087
			if(embargoParameterVO.getParameterCode()!=null && embargoParameterVO.getParameterCode().trim().length()>0 ){
						
				EmbargoRulesParameter embargoParameter = new EmbargoRulesParameter(
							embargoParameterVO, embargoVO);
					embargoParameters.add(embargoParameter);
					setParameters(embargoParameters);
				}
				
			}
			
		}
		if (embargoVO.getLocalLanguageVOs() != null
				&& embargoVO.getLocalLanguageVOs().size() > 0) {
			Set<EmbargoRulesLocalLanguage> embargoRulesLocalLanguages =
				new HashSet<EmbargoRulesLocalLanguage>();				
			for (EmbargoLocalLanguageVO embargoLocalLanguageVO : embargoVO
					.getLocalLanguageVOs()) {
				EmbargoRulesLocalLanguage embargoRulesLocalLanguage = new EmbargoRulesLocalLanguage(
						embargoLocalLanguageVO, embargoVO);
				embargoRulesLocalLanguages.add(embargoRulesLocalLanguage);
				setEmbargoRulesLocalLanguage(embargoRulesLocalLanguages);				
			}
		}
	}

	/**
	 * Method to fetch Embargo Parameter
	 *
	 * @param parameterVO
	 * @return EmbargoParameter
	 * @throws SystemException 
	 * @throws RemoveException 
	 */
	private EmbargoRulesParameter retrieveParameterFromCollection(
			EmbargoParameterVO parameterVO) throws RemoveException, SystemException {

		for (EmbargoRulesParameter embargoParameter : getParameters()) {

			if (((embargoParameter.getEmbargoParameterPk()
					.getCompanyCode())
					.equals(parameterVO.getCompanyCode()))
					&& ((embargoParameter.getEmbargoParameterPk().getEmbargoReferenceNumber())
							.equals(parameterVO.getEmbargoReferenceNumber()))
					&& ((embargoParameter.getEmbargoParameterPk().getParameterCode())
							.equals(parameterVO.getParameterCode()))
					&& ((embargoParameter.getEmbargoParameterPk().getParameterType())
									.equals(parameterVO.getParameterLevel()))
					&& ((embargoParameter.getEmbargoParameterPk().getEmbargoVersion())==
											(parameterVO.getEmbargoVersion()))
					&& ((embargoParameter.getEmbargoParameterPk().getApplicableOn())
						.equals((parameterVO.getApplicable())))) {

				return embargoParameter;
			}
		}

		return null;
	}

	/**
	 * Method to find Embargo business object
	 *
	 * @param companyCode
	 * @param embargoReferenceNumber
	 * @return Embargo
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static EmbargoRules find(String companyCode,
			String embargoReferenceNumber,int embargoVersion)
			throws SystemException, FinderException {

		EmbargoRulesPK embargoPK = new EmbargoRulesPK();
		embargoPK.setCompanyCode(   companyCode);
		embargoPK.setEmbargoReferenceNumber(   embargoReferenceNumber);
		embargoPK.setEmbargoVersion(embargoVersion);
		return PersistenceController.getEntityManager().find(EmbargoRules.class,
				embargoPK);

	}

	/**
	 * This method finds Embargo Global Parameters
	 *
	 * @param companyCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public static Collection<EmbargoGlobalParameterVO> findGlobalParameterCodes(
			String companyCode) throws PersistenceException, SystemException {

		EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));
		return embargoDAO.findGlobalParameterCodes(companyCode);

	}

	/**
	 * Method to update Embargo business object from VO
	 *
	 * @param embargoVO
	 * @throws SystemException
	 * @throws CreateException
	 * @throws RemoveException
	 */
	public void update(EmbargoRulesVO embargoVO) throws SystemException,
			EmbargoRulesBusinessException, RemoveException, CreateException {
		getEmbargoRulesPk().setEmbargoVersion(embargoVO.getEmbargoVersion());
		setLastUpdatedTime(embargoVO.getLastUpdatedTime());
		populateAttributes(embargoVO);

		updateChildren(embargoVO);
	}

	/**
	 * This method is used to update children of Embargo which is
	 * EmbargoParameter
	 *
	 * @param embargoVO
	 * @throws RemoveException
	 * @throws SystemException
	 * @throws CreateException
	 */
	private void updateChildren(EmbargoRulesVO embargoVO) throws RemoveException,
			SystemException, CreateException {
		Collection<EmbargoParameterVO> embargoParameterInsertVOs = new ArrayList<EmbargoParameterVO>();
		Collection<EmbargoParameterVO> embargoParameterDeleteVOs = new ArrayList<EmbargoParameterVO>();
		Collection<EmbargoParameterVO> embargoParameterUpdateVOs = new ArrayList<EmbargoParameterVO>();
		Collection<String> embargoParmaterDeleteVOKeys =  new ArrayList<String>();
		StringBuilder embargoParmaterDeleteVOKey = null;
		StringBuilder embargoParmaterInsertVOKey = null;
		if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
			//Added by A-7364 as part of ICRD-266255 starts
			for (EmbargoParameterVO embargoParameterVO:embargoVO.getParameters()){
				if (EmbargoRulesVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(embargoParameterVO.getOperationalFlag())){
					embargoParmaterDeleteVOKey = new StringBuilder();
					embargoParmaterDeleteVOKey.append(embargoParameterVO.getCompanyCode())
						.append(embargoParameterVO.getEmbargoReferenceNumber())
						.append(embargoParameterVO.getEmbargoVersion())
						.append(embargoParameterVO.getParameterCode())
						.append(embargoParameterVO.getParameterLevel())
						.append(embargoParameterVO.getApplicable());
					embargoParmaterDeleteVOKeys.add(embargoParmaterDeleteVOKey.toString());
				}
			}
			//Added by A-7364 as part of ICRD-266255 ends
			for (EmbargoParameterVO embargoParameterVO:embargoVO.getParameters()){

				if (EmbargoRulesVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(embargoParameterVO.getOperationalFlag())){
					embargoParameterDeleteVOs.add(embargoParameterVO);
					//(retrieveParameterFromCollection(embargoParameterVO)).remove();

				} else if (EmbargoRulesVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(embargoParameterVO.getOperationalFlag())) {
					//Added by A-7364 as part of ICRD-266255 starts
					embargoParmaterInsertVOKey = new StringBuilder();
					embargoParmaterInsertVOKey.append(embargoParameterVO.getCompanyCode())
						.append(embargoParameterVO.getEmbargoReferenceNumber())
						.append(embargoParameterVO.getEmbargoVersion())
						.append(embargoParameterVO.getParameterCode())
						.append(embargoParameterVO.getParameterLevel())
						.append(embargoParameterVO.getApplicable());
					if(retrieveParameterFromCollection(embargoParameterVO)!=null && 
							!embargoParmaterDeleteVOKeys.contains(embargoParmaterInsertVOKey)){
						embargoParameterVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
						embargoParameterUpdateVOs.add(embargoParameterVO);
						//Added by A-7364 as part of ICRD-266255 ends
					}else{
					embargoParameterInsertVOs.add(embargoParameterVO);
					}
					//new EmbargoRulesParameter(embargoParameterVO, embargoVO);
				} else if (EmbargoRulesVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(embargoParameterVO.getOperationalFlag())) {
					if(embargoParameterVO.getParameterCode()!=null && embargoParameterVO.getParameterCode().trim().length()>0 ){
						embargoParameterUpdateVOs.add(embargoParameterVO);	
						//(retrieveParameterFromCollection(embargoParameterVO)).update(embargoParameterVO);
					}
				}
							
			}
			if(embargoParameterDeleteVOs!=null && embargoParameterDeleteVOs.size()>0){
				for(EmbargoParameterVO embargoParameterVO:embargoParameterDeleteVOs){
					if(retrieveParameterFromCollection(embargoParameterVO)!=null){
					(retrieveParameterFromCollection(embargoParameterVO)).remove();
				}}
			}
			if(embargoParameterUpdateVOs!=null && embargoParameterUpdateVOs.size()>0){
				for(EmbargoParameterVO embargoParameterVO:embargoParameterUpdateVOs){
					if(retrieveParameterFromCollection(embargoParameterVO)!=null){
						(retrieveParameterFromCollection(embargoParameterVO)).update(embargoParameterVO);
					}}
				}
			if(embargoParameterInsertVOs!=null && embargoParameterInsertVOs.size()>0){
				for(EmbargoParameterVO embargoParameterVO:embargoParameterInsertVOs){
					new EmbargoRulesParameter(embargoParameterVO, embargoVO);
				}
			}

		}
		
		// added for ICRD-213193 by A-7815
		if(this.getEmbargoRulesLocalLanguage() != null){
			for(EmbargoRulesLocalLanguage embargoRulesLocalLanguage :this.getEmbargoRulesLocalLanguage() ){
				embargoRulesLocalLanguage.remove();
			}
		}
		if(embargoVO.getLocalLanguageVOs()!=null ){
				for(EmbargoLocalLanguageVO embargoLocalLanguageVO:embargoVO.getLocalLanguageVOs()){
					new EmbargoRulesLocalLanguage(embargoLocalLanguageVO, embargoVO);
				}
		}
		
		
		
		
	}

	/**
	 * Method to obtain the Vo corresponding to the business object
	 *
	 * @return EmbargoVO
	 */
	public EmbargoRulesVO retrieve() {
		// To be reviewed: Populate EmbargoVO based on the current instance of
		// Embargo business object
		return null;
	}

	/**
	 * This method identifies all embargos associated with the given shipment
	 *
	 * @param shipmentVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws SystemException
	 */
	public static Collection<EmbargoDetailsVO> checkForEmbargo(
			Collection<ShipmentDetailsVO> shipmentVO) throws SystemException {

		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							"reco.defaults"));
			return embargoDAO.checkForEmbargo(shipmentVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * This method identifies all embargos associated with the given shipment
	 *
	 * @param shipmentDetailsVOs
	 * @return Collection<ShipmentDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<EmbargoDetailsVO> checkForCoolFrozenEmbargo(
			Collection<ShipmentDetailsVO> shipmentDetailsVOs) throws SystemException {

		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							"reco.defaults"));
			return embargoDAO.checkForCoolFrozenEmbargo(shipmentDetailsVOs);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * This method identifies all embargos for expiry.
	 *
	 * @param embargoFilterVO the embargo filter vo
	 * @return Collection<EmbargoDetailsVO>
	 * @throws SystemException the system exception
	 * @author a-5160
	 */
	public Collection<EmbargoDetailsVO> findEmbargoCandidatesForExpiry(EmbargoFilterVO embargoFilterVO) throws SystemException{
		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							"reco.defaults"));
			return embargoDAO.findEmbargoCandidatesForExpiry(embargoFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Constructor used to create a new Embargo
	 *
	 * @param embargoVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public EmbargoRules(EmbargoRulesVO embargoVO) throws SystemException,
	CreateException {
		if(embargoVO.getEmbargoReferenceNumber().trim().length()==0){
		embargoVO
		.setEmbargoReferenceNumber(
				generateEmbargoReferenceNumber(
						embargoVO));
		}
		populatePk(embargoVO);
		populateAttributes(embargoVO);
		PersistenceController.getEntityManager().persist(this);
	
		populateChilden(embargoVO);

	}

	/**
	 * This method is used to create a unique key for a new Embargo
	 *
	 * @param embargoVO
	 * @return
	 * @throws SystemException
	 */
	private String generateEmbargoReferenceNumber(EmbargoRulesVO embargoVO)
			throws SystemException {

		Criterion criterion = KeyUtils.getCriterion(embargoVO.getCompanyCode(),
				EmbargoRulesVO.KEY_EMBARGO, "DEFAULT");
		StringBuffer key = new StringBuffer(KeyUtils.getKey(criterion));
		int length = key.length();
		for(int count=0;count<5-length;count++){
			key.insert(0,'0');
		}
		return key.toString();
	}

	/**
	 * Default Constructor-This Constructor is Mandatory for while using
	 * Hibernate find
	 *
	 */
	public EmbargoRules() {

	}

	
	/** Added by A-5867 for ICRD-68630**/
	public static EmbargoSearchVO searchEmbargos(EmbargoFilterVO filterVO)
	throws SystemException, PersistenceException {
	EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
			.getEntityManager().getQueryDAO("reco.defaults"));
	return embargoDAO.searchEmbargos(filterVO);
	}
	
	public static Collection<EmbargoDetailsVO> findDuplicateEmbargos(EmbargoFilterVO filterVO)
	throws SystemException, PersistenceException {
	EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
			.getEntityManager().getQueryDAO("reco.defaults"));
	return embargoDAO.findDuplicateEmbargos(filterVO);
	}
	
	 /**
     * Method to call procedure to update embargo view
     * @throws SystemException
     */
	public static void updateEmbargoView()throws SystemException{
	try {
		EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));
		embargoDAO.updateEmbargoView();
	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
	
	}
	
	/**
     * This method finds embargos/regulatory compliance which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Collection
     * @throws PersistenceException
     * @throws SystemException
     * Page<EmbargoDetailsVO>
     */
	public static Page<EmbargoDetailsVO> findRegulatoryComplianceRules(EmbargoFilterVO filterVO, int pageNumber)
			throws SystemException, PersistenceException {
		EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class.cast(PersistenceController
				.getEntityManager().getQueryDAO("reco.defaults"));
		return embargoDAO.findRegulatoryComplianceRules(filterVO, pageNumber);
	}
	/**
	 * This method checks any embargo exists in system
	 * @param embargoFilterVO
	 * @return boolean
	 * @throws SystemException
	 */
	public static boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO)
			throws SystemException {
		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							"reco.defaults"));
			return embargoDAO.checkAnyEmbargoExists(embargoFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * 	Getter for embargoRulesLocalLanguage 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for : ICRD-213193
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "REFNUM", referencedColumnName = "REFNUM", insertable = false, updatable = false),
			@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable = false, updatable = false)})
	public Set<EmbargoRulesLocalLanguage> getEmbargoRulesLocalLanguage() {
		return embargoRulesLocalLanguage;
	}
	/**
	 *  @param embargoRulesLocalLanguage the embargoRulesLocalLanguage to set
	 * 	Setter for embargoRulesLocalLanguage 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for : ICRD-213193
	 */
	public void setEmbargoRulesLocalLanguage(
			Set<EmbargoRulesLocalLanguage> embargoRulesLocalLanguage) {
		this.embargoRulesLocalLanguage = embargoRulesLocalLanguage;
	}
	
	/**
	 * 
	 * 	Method		:	EmbargoRules.getEmbargoEnquiryDetails
	 *	Added by 	:	A-5153 on Feb 16, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	List<EmbargoDetailsVO>
	 */
	public static List<EmbargoDetailsVO> getEmbargoEnquiryDetails(
			EmbargoFilterVO filterVO) throws PersistenceException,
			SystemException {
		EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class
				.cast(PersistenceController.getEntityManager().getQueryDAO(
						"reco.defaults"));

		return embargoDAO.getEmbargoEnquiryDetails(filterVO);
	}
	
	/**
	 * @author A-4823
	 * @param shipmentVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<EmbargoDetailsVO> parameterSelectiveEmbargoSearch(
			EmbargoFilterVO filterVO) throws SystemException {

		try {
			EmbargoRulesDAO embargoDAO = EmbargoRulesSqlDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							"reco.defaults"));
			return embargoDAO.parameterSelectiveEmbargoSearch(filterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
