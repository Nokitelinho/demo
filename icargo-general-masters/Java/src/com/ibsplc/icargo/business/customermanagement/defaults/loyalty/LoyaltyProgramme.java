/*
 * LoyaltyProgramme.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.RedemptionValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.customermanagement.defaults.CustomerMgmntDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
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
 * @author A-1496
 *
 *
 */
//@Staleable
@Table(name="CUSLTYPRGMST")
@Entity
public class LoyaltyProgramme {

	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	
	private static final String MODULE = "customermanagement.defaults";
	
    private LoyaltyProgrammePK loyaltyProgrammePK;

	private String loyaltyProgrammeDesc;
	private double entryPoints;
	private Calendar fromDate;
	private Calendar toDate;
	private double expiryPeriod;
	private String expiryDuration;
	private String activeStatus;
	private String attibute;
	private String units;
	private double points;
    private double amount;
	private Set<LoyaltyParameter> loyaltyParameters;

	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;


    /**
	 * @return Returns the activeStatus.
	 */
	@Column(name = "ACTSTA")
	public String getActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus The activeStatus to set.
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * @return Returns the entryPoints.
	 */
	@Column(name = "ENTPTS")
	public double getEntryPoints() {
		return entryPoints;
	}

	/**
	 * @param entryPoints The entryPoints to set.
	 */
	public void setEntryPoints(double entryPoints) {
		this.entryPoints = entryPoints;
	}

	/**
	 * @return Returns the expiryDuration.
	 */
	@Column(name = "EXPDUR")
	public String getExpiryDuration() {
		return expiryDuration;
	}

	/**
	 * @param expiryDuration The expiryDuration to set.
	 */
	public void setExpiryDuration(String expiryDuration) {
		this.expiryDuration = expiryDuration;
	}

	/**
	 * @return Returns the expiryPeriod.
	 */
	@Column(name = "EXPPER")
	public double getExpiryPeriod() {
		return expiryPeriod;
	}

	/**
	 * @param expiryPeriod The expiryPeriod to set.
	 */
	public void setExpiryPeriod(double expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@Column(name = "FRMDAT")
	@Audit(name="fromDate")

	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
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
	 * @param lastUpdatedTime The lastUpdatedTime to set.
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
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the loyaltyParameters.
	 */
	@OneToMany
     @JoinColumns( {
	  @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	  @JoinColumn(name = "LTYPRGCOD", referencedColumnName = "LTYPRGCOD", insertable=false, updatable=false)}
     )
	public Set<LoyaltyParameter> getLoyaltyParameters() {
		return loyaltyParameters;
	}

	/**
	 * @param loyaltyParameters The loyaltyParameters to set.
	 */
	public void setLoyaltyParameters(Set<LoyaltyParameter> loyaltyParameters) {
		this.loyaltyParameters = loyaltyParameters;
	}

	/**
	 * @return Returns the loyaltyProgrammeDesc.
	 */
	@Column(name = "LTYPRGDES")
	public String getLoyaltyProgrammeDesc() {
		return loyaltyProgrammeDesc;
	}

	/**
	 * @param loyaltyProgrammeDesc The loyaltyProgrammeDesc to set.
	 */
	public void setLoyaltyProgrammeDesc(String loyaltyProgrammeDesc) {
		this.loyaltyProgrammeDesc = loyaltyProgrammeDesc;
	}

	/**
	 * @return Returns the toDate.
	 */
	@Column(name = "TOODAT")
	@Audit(name="toDate")

	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the attibute.
	 */
	@Column(name = "ATR")
	@Audit(name="attibute")
	public String getAttibute() {
		return attibute;
	}
	
	/**
	 * @param attibute The attibute to set.
	 */
	public void setAttibute(String attibute) {
		this.attibute = attibute;
	}
	/**
	 * @return Returns the points.
	 */
	@Column(name = "PTS")
	@Audit(name="points")
	public double getPoints() {
		return points;
	}
	
	/**
	 * @param points The points to set.
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	
	/**
	 * @return Returns the units.
	 */
	@Column(name = "UNT")
	@Audit(name="units")
	public String getUnits() {
		return units;
	}
	
	/**
	 * @param units The units to set.
	 */
	public void setUnits(String units) {
		this.units = units;
	}
	
	/**
	 * @return Returns the amount.
	 */
	@Column(name = "AMT")
	@Audit(name="amount")
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Constructor
	 */
	public LoyaltyProgramme() {
	}

/***
 * 
 * @param loyaltyProgrammeVO
 * @throws SystemException
 */
	public LoyaltyProgramme(LoyaltyProgrammeVO loyaltyProgrammeVO)
		throws SystemException {
		log.entering("LoyaltyProgramme","LoyaltyProgramme Constructor");
		populatePk(loyaltyProgrammeVO);
		populateAttributes(loyaltyProgrammeVO);
		populateChild(loyaltyProgrammeVO);
		try{
    		PersistenceController.getEntityManager().persist(this);
    	}catch(CreateException createException){
    		log.log(Log.SEVERE,"CreateException");
    		throw new SystemException(createException.getErrorCode());
    	}
    	log.exiting("LoyaltyProgramme","LoyaltyProgramme Constructor");
	}

	/**
	 * private method to populate PK
	 *
	 * @param loyaltyProgrammeVO
	 */
	public void populatePk(LoyaltyProgrammeVO loyaltyProgrammeVO) {
		log.entering("LoyaltyProgramme","populatePk");
		LoyaltyProgrammePK loyaltyProgrammePk = new LoyaltyProgrammePK();
		loyaltyProgrammePk.setCompanyCode(   loyaltyProgrammeVO.getCompanyCode());
		loyaltyProgrammePk.setLoyaltyProgrammeCode(   loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		this.loyaltyProgrammePK = loyaltyProgrammePk;
		log.exiting("LoyaltyProgramme","populatePk");
	}

	/**
	 * private method to populate attributes
	 *
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 */
	public void populateAttributes(LoyaltyProgrammeVO loyaltyProgrammeVO)
		throws SystemException {
		log.entering("LoyaltyProgramme","populateAttributes");
		this.setActiveStatus(loyaltyProgrammeVO.getActiveStatus());
		this.setEntryPoints(loyaltyProgrammeVO.getEntryPoints());
		this.setExpiryDuration(loyaltyProgrammeVO.getExpiryDuration());
		this.setExpiryPeriod(loyaltyProgrammeVO.getExpiryPeriod());
		LocalDate fromLoyaltyDate = loyaltyProgrammeVO.getFromDate();
		if(fromLoyaltyDate != null ){
			this.setFromDate(fromLoyaltyDate.toCalendar());
		}
		LocalDate lstUpdTime = loyaltyProgrammeVO.getLastUpdatedTime();
		if(lstUpdTime != null){
		this.setLastUpdatedTime(lstUpdTime.toCalendar());
		}
		this.setLastUpdatedUser(loyaltyProgrammeVO.getLastUpdatedUser());
		this.setLoyaltyProgrammeDesc(loyaltyProgrammeVO.getLoyaltyProgrammeDesc());
		LocalDate toLoayaltyDate = loyaltyProgrammeVO.getToDate();
		if(toLoayaltyDate != null ){
		    this.setToDate(toLoayaltyDate.toCalendar());
		}
		this.setAttibute(loyaltyProgrammeVO.getAttibute());
		this.setPoints(loyaltyProgrammeVO.getPoints());
		this.setUnits(loyaltyProgrammeVO.getUnits());
		this.setAmount(loyaltyProgrammeVO.getAmount());
		log.entering("LoyaltyProgramme","populateAttributes");
	}
	/**
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 */
 private void populateChild(LoyaltyProgrammeVO loyaltyProgrammeVO)
		throws SystemException {
	 log.entering("LoyaltyProgramme","populateChild");
	 Collection<LoyaltyParameterVO> loyaltyParameterVOs = 
		 loyaltyProgrammeVO.getLoyaltyParameterVOs();
	 if(loyaltyParameterVOs != null && loyaltyParameterVOs.size() > 0 ){
		 log.log(Log.FINE, " Number of Children : ", loyaltyParameterVOs.size());
		for(LoyaltyParameterVO loyaltyParameterVO:loyaltyParameterVOs){
			 if(LoyaltyParameterVO.OPERATION_FLAG_INSERT.equals(
					 loyaltyParameterVO.getOperationFlag())){
				 LoyaltyParameter loyaltyParameter = new 
			        LoyaltyParameter(loyaltyParameterVO); 
				 if(loyaltyParameters == null){
					 loyaltyParameters = new HashSet<LoyaltyParameter>();
				 }
				 loyaltyParameters.add(loyaltyParameter);
			 }
			 else if(LoyaltyParameterVO.OPERATION_FLAG_DELETE.equals(
					 loyaltyParameterVO.getOperationFlag())){
				 LoyaltyParameter loyaltyParameter = LoyaltyParameter.
				  						find(loyaltyParameterVO);
				 this.loyaltyParameters.remove(loyaltyParameter);
				 loyaltyParameter.remove();
			 }else if(LoyaltyParameterVO.OPERATION_FLAG_UPDATE.equals(
					 loyaltyParameterVO.getOperationFlag())){
				 LoyaltyParameter loyaltyParameter = LoyaltyParameter.
					find(loyaltyParameterVO);
				 loyaltyParameter.update(loyaltyParameterVO);
			 }
			
		 }
	 }
	 log.exiting("LoyaltyProgramme","populateChild"); 
 }
  /**
   * @param loyaltyProgrammeVO
   * @return LoyaltyProgramme
   * @throws SystemException
   */
	public static LoyaltyProgramme find(LoyaltyProgrammeVO loyaltyProgrammeVO)
		throws SystemException {
		LoyaltyProgramme loyaltyProgramme = null;
		LoyaltyProgrammePK paramPK = new LoyaltyProgrammePK();
		paramPK.setCompanyCode(   loyaltyProgrammeVO.getCompanyCode());
		paramPK.setLoyaltyProgrammeCode(   loyaltyProgrammeVO.getLoyaltyProgrammeCode());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			loyaltyProgramme=entityManager.find(LoyaltyProgramme.class,paramPK);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),finderException);
		}
	    return loyaltyProgramme;
	}

	/**
	 * This method is used to remove the business object.
	 * It interally calls the remove method within EntityManager
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("LoyaltyProgramme","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
			Collection<LoyaltyParameter> loyaltyPrms = this.getLoyaltyParameters();
			if(loyaltyPrms != null && loyaltyPrms.size() > 0){
				for(LoyaltyParameter loyaltyParameter:loyaltyPrms){
					loyaltyParameter.remove();
				}
			}
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		log.exiting("LoyaltyProgramme","remove");

	}
/**
 * 
 * @param loyaltyProgrammeVO
 * @throws SystemException
 */
	public void update(LoyaltyProgrammeVO loyaltyProgrammeVO)throws SystemException {
		log.entering("LoyaltyProgramme","update");
		this.setLastUpdatedTime(loyaltyProgrammeVO.getLastUpdatedTime());
		populateAttributes(loyaltyProgrammeVO);
		populateChild(loyaltyProgrammeVO);
		log.exiting("LoyaltyProgramme","update");

	}

    //To be reviewed
    /**
     * @OneToMany
     * @JoinColumns( {
 	 * @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
 	 * @JoinColumn(name = "LTYPRGCOD", referencedColumnName = "LTYPRGCOD", insertable=false, updatable=false))}
     */
	 /**
     * @return Returns the loyaltyProgrammePK.
     */
	 @EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="loyaltyProgrammeCode", column=@Column(name="LTYPRGCOD"))}
		)
    public LoyaltyProgrammePK getLoyaltyProgrammePK() {
        return loyaltyProgrammePK;
    }
    /**
     * @param loyaltyProgrammePK The loyaltyProgrammePK to set.
     */
    public void setLoyaltyProgrammePK(LoyaltyProgrammePK loyaltyProgrammePK) {
        this.loyaltyProgrammePK = loyaltyProgrammePK;
    }
    /**
     * List Loyalty programmes based on Filter
     * @author A-1883
     * @param loyaltyProgrammeFilterVO
     * @return Collection<LoyaltyProgrammeVO>
     * @throws SystemException
     */
    public Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
			throws SystemException {
    		log.entering("LoyaltyProgramme","listLoyaltyProgramme");
		        	return constructDAO().listLoyaltyProgramme(loyaltyProgrammeFilterVO);
		        }
    /***
     * 
     * @param companyCode
     * @return Collection<LoyaltyProgrammeVO>	
     * @throws SystemException
     */
    /*public static Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(String companyCode)
    throws SystemException {
    	return constructDAO().findAllLoyaltyProgrammes(companyCode);
    }*/
    /**
     * 
     * @param loyaltyProgrammeVO
     * @return Collection<LoyaltyProgrammeVO> 
     * @throws SystemException
     */
    public static Collection<LoyaltyProgrammeVO> validateParameter(LoyaltyProgrammeVO loyaltyProgrammeVO)
    throws SystemException {
    	return constructDAO().validateParameter(loyaltyProgrammeVO);
    }
    /**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public static Collection<String> checkCustomerAttached(LoyaltyProgrammeVO loyaltyProgrammeVO) 
		throws	SystemException {
		return constructDAO().checkCustomerAttached(loyaltyProgrammeVO);
	}
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public static Collection<String> checkLoyaltyProgrammeAttached(LoyaltyProgrammeVO loyaltyProgrammeVO) 
		throws	SystemException {
		return constructDAO().checkLoyaltyProgrammeAttached(loyaltyProgrammeVO);
	}
	 /**
	 * Lists All Loyalty Programmes based on Filter 
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public static Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO,int pageNumber)
			throws SystemException {
		return constructDAO().listAllLoyaltyProgrammes(loyaltyProgrammeFilterVO,
				pageNumber);
	}
	/**
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	 public static Collection<LoyaltyProgrammeVO> findAttachedLoyaltyProgrammes(
				AirWayBillLoyaltyProgramVO airWayBillLoyaltyProgramVO) 
				throws SystemException {
		 return constructDAO().findAttachedLoyaltyProgrammes(airWayBillLoyaltyProgramVO);
	    }
	 /**
	  * @author A-1883
	  * @param companyCode
	  * @return Collection<ParameterDescriptionVO>
	  * @throws SystemException
	  */
	 public static Collection<ParameterDescriptionVO> findParameterDetails(String companyCode)
	 			throws SystemException {
		 return constructDAO().findParameterDetails(companyCode);
	 }
	 /**
	  * Finds unit of pameter (Distance and Revenue)
	  * @author A-1883
	  * @param companyCode
	  * @param parameterCode
	  * @return String 
	  * @throws SystemException
	  */
	public static String findParameterUnit(String companyCode,String parameterCode)
	 throws SystemException {
		return constructDAO().findParameterUnit(companyCode,parameterCode);
	}
	 
	/**
	 *  Returns Loyalty programme expiry details
	  * @author a-1883
	  * @param redemptionValidationVO
	  * @return Collection<LoyaltyProgrammeVO>
	  * @throws SystemException
	  */
	public static Collection<LoyaltyProgrammeVO> validateCustomerForPointsRedemption(
			 RedemptionValidationVO redemptionValidationVO)
			 throws SystemException {
		return constructDAO().validateCustomerForPointsRedemption(redemptionValidationVO);
	}
	 /**
	  * @author a-1883
	  * @param companyCode
	  * @param currentDate
	  * @param pageNumber
	  * @return Page<LoyaltyProgrammeVO>
	  * @throws SystemException
	  */
	public  static Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
			 String companyCode,LocalDate currentDate,int pageNumber)
			 throws SystemException{
		return constructDAO().runningLoyaltyProgrammeLOV(companyCode,
				currentDate,pageNumber);
	}
    /**
     * 
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    private static CustomerMgmntDefaultsDAO constructDAO()
	throws SystemException {
		try{
			EntityManager em = PersistenceController.getEntityManager();
		return CustomerMgmntDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		}
		catch (PersistenceException persistenceException) {
//printStackTraccee()();
			throw new SystemException(persistenceException.getMessage());
		}
	}
    /**
     * 
     * @param programPointVOs
     * @return
     * @throws SystemException
     */
    public static double findEntryPoints(Collection<AttachLoyaltyProgrammeVO> 
      programPointVOs)
	 throws SystemException {
		return constructDAO().findEntryPoints(programPointVOs);
	}
   
}
