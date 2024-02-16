/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteMaster.java
 *
 *	Created by	:	A-5219
 *	Created on	:	19-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteMaster.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	19-Nov-2013	:	Draft
 */
@Entity
@Table(name = "MALMRABLGSITMST")
@Staleable
public class BillingSiteMaster {
	
	/** The log. */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mail.mra.defaults";
	
	/** The billing site master pk. */
	private BillingSiteMasterPK billingSiteMasterPK;
	
	
	/** The billing site. */
	private String billingSite;
	
	/** The airline address. */
	private String airlineAddress;
	
	/** The correspondence address. */
	private String correspondenceAddress;
	
	/** The signator1. */
	private String signatorOne;
	
	/** The designator1. */
	private String designatorOne;
	
	/** The signator2. */
	private String signatorTwo;
	
	/** The designator2. */
	private String designatorTwo;
	
	/** The free text. */
	private String freeText;
	
	/** The from date. */
	private Calendar fromDate;
	
	/** The to date. */
	private Calendar toDate;
	
	/** The last updated time. */
	private Calendar lastUpdatedTime;
	
	/** The last updated user. */
	private String lastUpdatedUser;
	
	/** The billing site bank details. */
	private Set<BillingSiteBankDetails> billingSiteBankDetails;
	
	/** The billing site country details. */
	private Set<BillingSiteCountryDetails> billingSiteCountryDetails;

	
		/**
		 * Getter for billingSiteMasterPK
		 * Added by : A-5219 on 19-Nov-2013
		 * Used for :.
		 *
		 * @return the billing site master pk
		 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="billingSiteCode", column=@Column(name="BLGSITCOD"))
	})
	public BillingSiteMasterPK getBillingSiteMasterPK() {
		return billingSiteMasterPK;
	}

	/**
	 * Sets the billing site master pk.
	 *
	 * @param billingSiteMasterPK the billingSiteMasterPK to set
	 * Setter for billingSiteMasterPK
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setBillingSiteMasterPK(BillingSiteMasterPK billingSiteMasterPK) {
		this.billingSiteMasterPK = billingSiteMasterPK;
	}

	/**
	 * Getter for billingSite
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site
	 */
	@Column(name="BLGSITNAM")
	public String getBillingSite() {
		return billingSite;
	}

	/**
	 * Sets the billing site.
	 *
	 * @param billingSite the billingSite to set
	 * Setter for billingSite
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setBillingSite(String billingSite) {
		this.billingSite = billingSite;
	}

	/**
	 * Getter for airlineAddress
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the airline address
	 */
	@Column(name="ARLADR")
	@Audit(name="Airline Address")
	public String getAirlineAddress() {
		return airlineAddress;
	}

	/**
	 * Sets the airline address.
	 *
	 * @param airlineAddress the airlineAddress to set
	 * Setter for airlineAddress
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setAirlineAddress(String airlineAddress) {
		this.airlineAddress = airlineAddress;
	}

	/**
	 * Getter for correspondenceAddress
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the correspondence address
	 */
	@Column(name="CORADR")
	@Audit(name="Correspondence Address")
	public String getCorrespondenceAddress() {
		return correspondenceAddress;
	}

	/**
	 * Sets the correspondence address.
	 *
	 * @param correspondenceAddress the correspondenceAddress to set
	 * Setter for correspondenceAddress
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setCorrespondenceAddress(String correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}

	/**
	 * Getter for signator1
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the signator1
	 */
	@Column(name="SIGONE")
	@Audit(name="Signator 1")
	public String getSignatorOne() {
		return signatorOne;
	}

	/**
	 * Sets the signator1.
	 *
	 * @param signator1 the signator1 to set
	 * Setter for signator1
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setSignatorOne(String signatorOne) {
		this.signatorOne = signatorOne;
	}

	/**
	 * Getter for designator1
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the designator1
	 */
	@Column(name="DSGONE")
	@Audit(name="Designation 1")
	public String getDesignatorOne() {
		return designatorOne;
	}

	/**
	 * Sets the designator1.
	 *
	 * @param designator1 the designator1 to set
	 * Setter for designator1
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setDesignatorOne(String designatorOne) {
		this.designatorOne = designatorOne;
	}

	/**
	 * Getter for signator2
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the signator2
	 */
	@Column(name="SIGTWO")
	@Audit(name="Signator 2")
	public String getSignatorTwo() {
		return signatorTwo;
	}

	/**
	 * Sets the signator2.
	 *
	 * @param signator2 the signator2 to set
	 * Setter for signator2
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setSignatorTwo(String signatorTwo) {
		this.signatorTwo = signatorTwo;
	}

	/**
	 * Getter for designator2
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the designator2
	 */
	@Column(name="DSGTWO")
	@Audit(name="Designation 2")
	public String getDesignatorTwo() {
		return designatorTwo;
	}

	/**
	 * Sets the designator2.
	 *
	 * @param designator2 the designator2 to set
	 * Setter for designator2
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setDesignatorTwo(String designatorTwo) {
		this.designatorTwo = designatorTwo;
	}

	/**
	 * Getter for freeText
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the free text
	 */
	@Column(name="RMK")
	public String getFreeText() {
		return freeText;
	}

	/**
	 * Sets the free text.
	 *
	 * @param freeText the freeText to set
	 * Setter for freeText
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	/**
	 * Getter for fromDate
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the from date
	 */
	@Column(name="FRMDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return fromDate;
	}

	/**
	 * Sets the from date.
	 *
	 * @param fromDate the fromDate to set
	 * Setter for fromDate
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Getter for toDate
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the to date
	 */
	@Audit(name="To Date")
	@Column(name="TOODAT")
	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return toDate;
	}

	/**
	 * Sets the to date.
	 *
	 * @param toDate the toDate to set
	 * Setter for toDate
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	} 
	
	/**
	 * Sets the last updated time.
	 *
	 * @param lastUpdatedTime the new last updated time
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * Gets the last updated time.
	 *
	 * @return the lastUpdatedTime
	 */
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * Sets the last updated user.
	 *
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * Gets the last updated user.
	 *
	 * @return the lastUpdatedUser
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * Sets the billing site country details.
	 *
	 * @param billingSiteCountryDetails the billingSiteCountryDetails to set
	 */
	public void setBillingSiteCountryDetails(
			Set<BillingSiteCountryDetails> billingSiteCountryDetails) {
		this.billingSiteCountryDetails = billingSiteCountryDetails;
	}

	/**
	 * Gets the billing site country details.
	 *
	 * @return the billingSiteCountryDetails
	 */
	@OneToMany
	@JoinColumns({
		@JoinColumn(name="CMPCOD",referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name="BLGSITCOD",referencedColumnName = "BLGSITCOD", insertable=false, updatable=false)
	})
	@Audit(name="Billing Site Country Details")
	public Set<BillingSiteCountryDetails> getBillingSiteCountryDetails() {
		return billingSiteCountryDetails;
	}
	
	/**
	 * Gets the billing site bank details.
	 *
	 * @return the billing site bank details
	 */
	@OneToMany
	@JoinColumns({
		@JoinColumn(name="CMPCOD",referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name="BLGSITCOD",referencedColumnName = "BLGSITCOD", insertable=false, updatable=false)
	})
	@Audit(name="Billing Site Bank Details")
	public Set<BillingSiteBankDetails> getBillingSiteBankDetails() {
		return billingSiteBankDetails;
	}

	/**
	 * Sets the billing site bank details.
	 *
	 * @param billingSiteBankDetails the billingSiteBankDetails to set
	 */
	public void setBillingSiteBankDetails(
			Set<BillingSiteBankDetails> billingSiteBankDetails) {
		this.billingSiteBankDetails = billingSiteBankDetails;
	}
	
	/**
	 * Instantiates a new billing site master.
	 */
	public BillingSiteMaster(){
		
	}
	
	/**
	 * Instantiates a new billing site master.
	 *
	 * @param billingSiteVO the billing site vo
	 * @throws SystemException the system exception
	 */
	public BillingSiteMaster(BillingSiteVO billingSiteVO)throws SystemException{
		
		populatePK(billingSiteVO);
		populateAttributes(billingSiteVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	    populateChildAttributes(billingSiteVO);	
		log.exiting("MRA Defaults","contructor");
		
	}
	
	/**
	 * Populate pk.
	 *
	 * @param billingSiteVO the billing site vo
	 */
	private void populatePK(BillingSiteVO billingSiteVO){
		BillingSiteMasterPK billingSiteMasterPK = new BillingSiteMasterPK();
		billingSiteMasterPK.setCompanyCode(billingSiteVO.getCompanyCode());
		billingSiteMasterPK.setBillingSiteCode(billingSiteVO.getBillingSiteCode());
		this.setBillingSiteMasterPK(billingSiteMasterPK);
	}
	/**
	 * Populate attributes.
	 *
	 * @param billingSiteVO the billing site vo
	 */
	private void populateAttributes(BillingSiteVO billingSiteVO){
		log.entering("MRA Defaults","populateAttributes");
		if(!"D".equals(billingSiteVO.getOperationFlag())){
		this.setFromDate(billingSiteVO.getFromDate());
		this.setToDate(billingSiteVO.getToDate());
		this.setBillingSite(billingSiteVO.getBillingSite());
		this.setAirlineAddress(billingSiteVO.getAirlineAddress());
		this.setCorrespondenceAddress(billingSiteVO.getCorrespondenceAddress());
		this.setDesignatorOne(billingSiteVO.getDesignator1());
		this.setDesignatorTwo(billingSiteVO.getDesignator2());
		this.setSignatorOne(billingSiteVO.getSignator1());
		this.setSignatorTwo(billingSiteVO.getSignator2());
		this.setFreeText(billingSiteVO.getFreeText());
		this.setLastUpdatedTime(billingSiteVO.getLastUpdatedTime());
		this.setLastUpdatedUser(billingSiteVO.getLastUpdatedUser());
		}
		log.exiting("MRA Defaults","populateAttributes");
	}
	
	/**
	 * Populate child attributes.
	 *
	 * @param billingSiteVO the billing site vo
	 * @throws SystemException the system exception
	 */
	private void populateChildAttributes(BillingSiteVO billingSiteVO)throws SystemException{
		log.entering("MRA Defaults","populateChildAttributes");
          if(billingSiteVO.getBillingSiteGPACountriesVO()!=null && billingSiteVO.getBillingSiteGPACountriesVO().size()>0){
			BillingSiteCountryDetails billingSiteCountryDetail=null;
			for(BillingSiteGPACountriesVO billingSiteGPACountriesVO:billingSiteVO.getBillingSiteGPACountriesVO()){
				if("I".equals(billingSiteGPACountriesVO.getOperationalFlag())){
					billingSiteGPACountriesVO.setBillingSiteCode(billingSiteVO.getBillingSiteCode());
					billingSiteCountryDetail = new BillingSiteCountryDetails(billingSiteGPACountriesVO);
					if(this.getBillingSiteCountryDetails()!=null)
						{
						this.getBillingSiteCountryDetails().add(billingSiteCountryDetail);
						}
                  }else{
                        billingSiteCountryDetail = BillingSiteCountryDetails.find(billingSiteGPACountriesVO.getCompanyCode(),
                        billingSiteGPACountriesVO.getBillingSiteCode(), billingSiteGPACountriesVO.getSerialNumber());
					if("U".equals(billingSiteGPACountriesVO.getOperationalFlag())){
						billingSiteCountryDetail.update(billingSiteGPACountriesVO);
						this.getBillingSiteCountryDetails().add(billingSiteCountryDetail);
					}
                        if("D".equals(billingSiteGPACountriesVO.getOperationalFlag())){
						billingSiteCountryDetail.remove();
                            this.getBillingSiteCountryDetails().remove(billingSiteCountryDetail);
                         }
                   }
              }
          }
          if(billingSiteVO.getBillingSiteBankDetailsVO()!=null && billingSiteVO.getBillingSiteBankDetailsVO().size()>0){
			BillingSiteBankDetails billingSiteBankDetail=null;
			for(BillingSiteBankDetailsVO billingSiteBankDetailsVO:billingSiteVO.getBillingSiteBankDetailsVO()){
				if("I".equals(billingSiteBankDetailsVO.getOperationalFlag())){
					billingSiteBankDetailsVO.setBillingSiteCode(billingSiteVO.getBillingSiteCode());
					billingSiteBankDetail= new BillingSiteBankDetails(billingSiteBankDetailsVO);
					if(this.getBillingSiteBankDetails()!=null)
						{
						this.getBillingSiteBankDetails().add(billingSiteBankDetail);
						}
                   }else{
					billingSiteBankDetail=BillingSiteBankDetails.find(
					billingSiteBankDetailsVO.getCompanyCode(), billingSiteBankDetailsVO.getBillingSiteCode(),billingSiteBankDetailsVO.getSerialNumber());
					if("U".equals(billingSiteBankDetailsVO.getOperationalFlag())){
						billingSiteBankDetail.update(billingSiteBankDetailsVO);
						this.getBillingSiteBankDetails().add(billingSiteBankDetail);
					}
                       if("D".equals(billingSiteBankDetailsVO.getOperationalFlag())){
						billingSiteBankDetail.remove();
                    	   this.getBillingSiteBankDetails().remove(billingSiteBankDetail);
                       }
                   }
               }                
          }    
     }
	
	  /**
  	 * Find.
  	 *
  	 * @param companyCode the company code
  	 * @param billingSiteCode the billing site code
  	 * @return the billing site master
  	 * @throws SystemException the system exception
  	 * @throws FinderException the finder exception
  	 */
  	public static BillingSiteMaster find(String companyCode, String billingSiteCode)
	    throws SystemException, FinderException
	  {
		  BillingSiteMasterPK billingSiteMasterPK = new BillingSiteMasterPK();
		  billingSiteMasterPK.setCompanyCode(companyCode);
		  billingSiteMasterPK.setBillingSiteCode(billingSiteCode);
		  return PersistenceController.getEntityManager().find(BillingSiteMaster.class, billingSiteMasterPK);
	  }
  	
  	/**
	   * Update.
	   *
	   * @param billingSiteVO the billing site vo
	   * @throws SystemException the system exception
	   */
	  public void update(BillingSiteVO billingSiteVO) throws SystemException {
		   
	    populateAttributes(billingSiteVO);
	    populateChildAttributes(billingSiteVO);
	  }
  	
    /**
  	 * Removes the.
  	 *
  	 * @throws SystemException the system exception
  	 */
  	public void remove() throws SystemException {
	    try {
	    	PersistenceController.getEntityManager().remove(this);
	    } catch (RemoveException removeException) {
	      throw new SystemException(removeException.getErrorCode());
	    }
	  }
  	
  	/**
	   * Find billing site details.
	   *
	   * @param billingSiteFilterVO the billing site filter vo
	   * @return the collection
	   * @throws SystemException the system exception
	   */
	  public Collection<BillingSiteVO> findBillingSiteDetails(
  			BillingSiteFilterVO billingSiteFilterVO)throws SystemException{
  		 try
  	    {
  	      return constructDAO().findBillingSiteDetails(billingSiteFilterVO); 
  	      } 
  		 catch (PersistenceException e) {
  			 throw new SystemException(e.getErrorCode(), e);
  	    }
  	   
  	  }
	  
	 
  	

/**
 * Construct dao.
 *
 * @return the mRA defaults dao
 * @throws SystemException the system exception
 */
private static MRADefaultsDAO constructDAO() throws SystemException
{
  try {
    EntityManager em = PersistenceController.getEntityManager();
    return (MRADefaultsDAO)MRADefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME)); 
    }
  catch (PersistenceException ex) {
	  throw new SystemException(ex.getErrorCode(), ex);
  }
  
}

/**
 * List parameter lov.
 *
 * @param bsLovFilterVo the bs lov filter vo
 * @return the page
 * @throws SystemException the system exception
 */
public static Page<BillingSiteLOVVO> listParameterLov(BillingSiteLOVFilterVO bsLovFilterVo)
throws SystemException {
	try {
		return constructDAO().listParameterLov(bsLovFilterVo);
	} catch (PersistenceException e) {
		throw new SystemException(e.getErrorCode(), e);
	}
}

/**
 * Find country over lapping.
 *
 * @param billingSiteVO the billing site vo
 * @return the collection
 * @throws SystemException the system exception
 */
public Collection<BillingSiteVO> findCountryOverLapping(
			BillingSiteVO billingSiteVO)throws SystemException{
		 try
	    {
	      return constructDAO().findCountryOverLapping(billingSiteVO); 
	      } 
		 catch (PersistenceException e) {
			 throw new SystemException(e.getErrorCode(), e);
	    }
	   
	  }


/**
 * Generate serial number.
 *
 * @param companyCode the company code
 * @param airportCode the airport code
 * @return the string
 * @throws SystemException the system exception
 */

public static String generateSerialNumber(String companyCode,String keyFormat)
	throws SystemException{
	Criterion criterion = KeyUtils.getCriterion(companyCode,"MRA");
	String key = KeyUtils.getKey(criterion);
	int keynum = Integer.parseInt(key);
	StringBuilder str = new StringBuilder("MRA");
	if(keynum<10){
		str.append("00");
	}else if(keynum<100){
		str.append("0");
	}else if(keynum<1000){
		str.append("");
	}
	str.append(key);
   return str.toString();
}

}

	
	

