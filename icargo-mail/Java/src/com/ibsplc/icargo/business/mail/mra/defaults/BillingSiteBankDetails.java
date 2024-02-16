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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * The Class BillingSiteBankDetails.
 *
 * @author A-5219
 */
@Entity
@Table(name = "MALMRABLGSITBNKDTL")
@Staleable
public class BillingSiteBankDetails {

	
	/** The currency. */
	private String currency;
	
	/** The bank name. */
	private String bankName;
	
	/** The branch. */
	private String branch;
	
	/** The acc no. */
	private String accNo;
	
	/** The city. */
	private String city;
	
	/** The country. */
	private String country;
	
	/** The swift code. */
	private String swiftCode;
	
	/** The iban no. */
	private String ibanNo;
	
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

	/** The billing site bank details pk. */
	private BillingSiteBankDetailsPK billingSiteBankDetailsPK;
	
	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	@Column(name="CURCOD")
	@Audit(name="Currency")
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency the currency to set
	 */
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the bank name.
	 *
	 * @return the bankName
	 */
	@Column(name="BNKNAM")
	@Audit(name="Bank Name")
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName the bankName to set
	 */
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	@Column(name="BNKBRN")
	@Audit(name="Branch")
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch the branch to set
	 */
	
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets the acc no.
	 *
	 * @return the accNo
	 */
	@Column(name="ACCNUM")
	@Audit(name="Acc No")
	public String getAccNo() {
		return accNo;
	}

	/**
	 * Sets the acc no.
	 *
	 * @param accNo the accNo to set
	 */
	
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	@Column(name="CTYNAM")
	@Audit(name="City")
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the city to set
	 */
	
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	@Column(name="CNTNAM")
	@Audit(name="Country")
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 */
	
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the swift code.
	 *
	 * @return the swiftCode
	 */
	@Column(name="SWTCOD")
	@Audit(name="Swift Code")
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * Sets the swift code.
	 *
	 * @param swiftCode the swiftCode to set
	 */
	
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * Gets the iban no.
	 *
	 * @return the ibanNo
	 */
	@Column(name="IBNNUM")
	@Audit(name="Iban No")
	public String getIbanNo() {
		return ibanNo;
	}

	/**
	 * Sets the iban no.
	 *
	 * @param ibanNo the ibanNo to set
	 */
	
	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}

	/**
	 * Sets the billing site bank details pk.
	 *
	 * @param billingSiteBankDetailsPK the billingSiteBankDetailsPK to set
	 */
	
	public void setBillingSiteBankDetailsPK(BillingSiteBankDetailsPK billingSiteBankDetailsPK) {
		this.billingSiteBankDetailsPK = billingSiteBankDetailsPK;
	}

	/**
	 * Gets the billing site bank details pk.
	 *
	 * @return the billingSiteBankDetailsPK
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM")),
		@AttributeOverride(name="billingSiteCode", column=@Column(name="BLGSITCOD"))
	})
	public BillingSiteBankDetailsPK getBillingSiteBankDetailsPK() {
		return billingSiteBankDetailsPK;
	}
	
	/**
	 * Instantiates a new billing site bank details.
	 */
	public BillingSiteBankDetails(){
		
	}
	
	/**
	 * Instantiates a new billing site bank details.
	 *
	 * @param billingSiteBankDetailsVO the billing site bank details vo
	 * @throws SystemException the system exception
	 */
	public BillingSiteBankDetails(BillingSiteBankDetailsVO billingSiteBankDetailsVO)throws SystemException{
		populatePK(billingSiteBankDetailsVO);
		populateAttributes(billingSiteBankDetailsVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
		}
	
	
	/**
	 * Populate pk.
	 *
	 * @param billingSiteBankDetailsVO the billing site bank details vo
	 */
	private void populatePK(BillingSiteBankDetailsVO billingSiteBankDetailsVO){
		BillingSiteBankDetailsPK billingSiteBankDetailsPK = new BillingSiteBankDetailsPK();
		billingSiteBankDetailsPK.setBillingSiteCode(billingSiteBankDetailsVO.getBillingSiteCode());
		billingSiteBankDetailsPK.setCompanyCode(billingSiteBankDetailsVO.getCompanyCode());
		setBillingSiteBankDetailsPK(billingSiteBankDetailsPK);
	}
	
	/**
	 * Populate pk.
	 *
	 * @param billingSiteBankDetailsVO the billing site bank details vo
	 */
	
	private void populateAttributes(BillingSiteBankDetailsVO billingSiteBankDetailsVO){
		if(billingSiteBankDetailsVO!=null){
		this.setAccNo(billingSiteBankDetailsVO.getAccNo());
		this.setBankName(billingSiteBankDetailsVO.getBankName());
		this.setBranch(billingSiteBankDetailsVO.getBranch());
		this.setCity(billingSiteBankDetailsVO.getCity());
		this.setCountry(billingSiteBankDetailsVO.getCountry());
		this.setCurrency(billingSiteBankDetailsVO.getCurrency());
		this.setIbanNo(billingSiteBankDetailsVO.getIbanNo());
		this.setSwiftCode(billingSiteBankDetailsVO.getSwiftCode());
		this.setLastUpdatedTime(billingSiteBankDetailsVO.getLastUpdatedTime());
		this.setLastUpdatedUser(billingSiteBankDetailsVO.getLastUpdatedUser());
		}
	}
	
	/**
	 * Find.
	 *
	 * @param companyCode the company code
	 * @param billingSiteCode the billing site code
	 * @return the billing site bank details
	 * @throws SystemException the system exception
	 */
	public static BillingSiteBankDetails find(String companyCode, String billingSiteCode, int serialNumber)
    throws SystemException
  {
		BillingSiteBankDetailsPK billingSiteBankDetailsPK = new BillingSiteBankDetailsPK();
		billingSiteBankDetailsPK.setCompanyCode(companyCode);
		billingSiteBankDetailsPK.setBillingSiteCode(billingSiteCode);
		billingSiteBankDetailsPK.setSerialNumber(serialNumber);
		BillingSiteBankDetails billingSiteBankDetails = null;
    try {
    	billingSiteBankDetails = (BillingSiteBankDetails)PersistenceController.getEntityManager().find(BillingSiteBankDetails.class, billingSiteBankDetailsPK);
    }
    catch (FinderException finderException) {
      throw new SystemException(finderException.getErrorCode(), finderException);
    }

    return billingSiteBankDetails;
  }
	
	
	
	/**
	 * Update.
	 *
	 * @param billingSiteBankDetailsVO the billing site bank details vo
	 * @throws SystemException the system exception
	 */
	public void update(BillingSiteBankDetailsVO billingSiteBankDetailsVO) throws SystemException {
		   
	    populateAttributes(billingSiteBankDetailsVO);
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
