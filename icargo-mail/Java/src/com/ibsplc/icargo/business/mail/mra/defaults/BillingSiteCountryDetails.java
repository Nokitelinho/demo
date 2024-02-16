/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * The Class BillingSiteCountryDetails.
 *
 * @author A-5219
 */
@Entity
@Table(name = "MALMRABLGSITCNTDTL")
@Staleable
public class BillingSiteCountryDetails {
	
	/** The country code. */
	private String countryCode;
	
	/** The last updated time. */
	private Calendar lastUpdatedTime;
	
	/** The last updated user. */
	private String lastUpdatedUser;
	
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
	 * Sets the last updated time.
	 *
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
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
	 * Sets the last updated user.
	 *
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/** The billing site country details pk. */
	private BillingSiteCountryDetailsPK billingSiteCountryDetailsPK;
	
	/**
	 * Instantiates a new billing site country details.
	 */
	public BillingSiteCountryDetails(){
		
	}
	
	/**
	 * Gets the country code.
	 *
	 * @return the countryCode
	 */
	@Column(name="CNTCOD")
	@Audit(name="Country Code")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Sets the country code.
	 *
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	
	/**
	 * Sets the billing site country details pk.
	 *
	 * @param billingSiteCountryDetailsPK the billingSiteCountryDetailsPK to set
	 */
	public void setBillingSiteCountryDetailsPK(
			BillingSiteCountryDetailsPK billingSiteCountryDetailsPK) {
		this.billingSiteCountryDetailsPK = billingSiteCountryDetailsPK;
	}

	/**
	 * Gets the billing site country details pk.
	 *
	 * @return the billingSiteCountryDetailsPK
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="billingSiteCode", column=@Column(name="BLGSITCOD")),
		@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))
		})
	public BillingSiteCountryDetailsPK getBillingSiteCountryDetailsPK() {
		return billingSiteCountryDetailsPK;
	}
	
	/**
	 * Instantiates a new billing site country details.
	 *
	 * @param billingSiteGPACountriesVO the billing site gpa countries vo
	 * @throws SystemException the system exception
	 */
	public BillingSiteCountryDetails(BillingSiteGPACountriesVO billingSiteGPACountriesVO)
		throws SystemException{
		populatePK(billingSiteGPACountriesVO);		
		populateAttributes(billingSiteGPACountriesVO);
				try{
			    	PersistenceController.getEntityManager().persist(this);
			    	}
			    	catch(CreateException e){
			    		throw new SystemException(e.getErrorCode());
			    	}
   	
				}
	
	/**
	 * Populate attributes.
	 *
	 * @param billingSiteGPACountriesVO the billing site gpa countries vo
	 */
	private void populateAttributes(BillingSiteGPACountriesVO billingSiteGPACountriesVO){
		if(billingSiteGPACountriesVO!=null){
		this.setCountryCode(billingSiteGPACountriesVO.getGpaCountry());
		this.setLastUpdatedTime(billingSiteGPACountriesVO.getLastUpdatedTime());
		this.setLastUpdatedUser(billingSiteGPACountriesVO.getLastUpdatedUser());
		}
		
	}
	
	/**
	 * Populate pk.
	 *
	 * @param billingSiteGPACountriesVO the billing site gpa countries vo
	 */
	private void populatePK(BillingSiteGPACountriesVO billingSiteGPACountriesVO){
		BillingSiteCountryDetailsPK billingSiteCountryDetailsPK = new BillingSiteCountryDetailsPK();
		billingSiteCountryDetailsPK.setCompanyCode(billingSiteGPACountriesVO.getCompanyCode());
		billingSiteCountryDetailsPK.setBillingSiteCode(billingSiteGPACountriesVO.getBillingSiteCode());
		this.setBillingSiteCountryDetailsPK(billingSiteCountryDetailsPK);
	}
	
	/**
	 * Find.
	 *
	 * @param companyCode the company code
	 * @param billingSiteCode the billing site code
	 * @return the billing site country details
	 * @throws SystemException the system exception
	 */
	public static BillingSiteCountryDetails find(String companyCode, String billingSiteCode, int serialNumber)
    throws SystemException {
		BillingSiteCountryDetailsPK billingSiteCountryDetailsPK = new BillingSiteCountryDetailsPK();
		billingSiteCountryDetailsPK.setCompanyCode(companyCode);
		billingSiteCountryDetailsPK.setBillingSiteCode(billingSiteCode);
		billingSiteCountryDetailsPK.setSerialNumber(serialNumber);
		BillingSiteCountryDetails billingSiteCountryDetails = null;
    try {
    	billingSiteCountryDetails = (BillingSiteCountryDetails)PersistenceController.getEntityManager().find(BillingSiteCountryDetails.class, billingSiteCountryDetailsPK);
    }
    catch (FinderException finderException) {
      throw new SystemException(finderException.getErrorCode(), finderException);
    }

    return billingSiteCountryDetails;
  }
	
	 /**
 	 * Update.
 	 *
 	 * @param billingSiteGPACountriesVO the billing site gpa countries vo
 	 * @throws SystemException the system exception
 	 */
 	public void update(BillingSiteGPACountriesVO billingSiteGPACountriesVO) throws SystemException {
		   
		    populateAttributes(billingSiteGPACountriesVO);
		  }
	 

	  /**
  	 * Removes the.
  	 *
  	 * @throws SystemException the system exception
  	 */
  	public void remove() throws SystemException {
	     EntityManager em = PersistenceController.getEntityManager();
	    try {
	      em.remove(this);
	    } catch (RemoveException removeException) {
	      throw new SystemException(removeException.getErrorCode());
	    }
	  }
		
}

