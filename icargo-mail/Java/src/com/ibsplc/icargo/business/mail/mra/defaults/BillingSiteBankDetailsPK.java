/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * The Class BillingSiteBankDetailsPK.
 *
 * @author A-5219
 */
@KeyTable(table = "MALMRABLGSITMSTKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALMRABLGSITMSTKEY", key = "BLGSITBNK_KEY")
@Embeddable
public class BillingSiteBankDetailsPK implements Serializable{
	
	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The serial number. */
	private int serialNumber;
	
	

	/**
	 * Gets the serial number.
	 *
	 * @return the serialNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Getter for companyCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the company code
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 * Setter for companyCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Getter for billingSiteCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site code
	 */
	@KeyCondition(column = "BLGSITCOD")
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the billingSiteCode to set
	 * Setter for billingSiteCode
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}
	
	/**
	 * Instantiates a new billing site master pk.
	 */
	public BillingSiteBankDetailsPK(){
		
	}
		
	/**
	 * Overriding Method	:	@see java.lang.Object#equals(java.lang.Object)
	 * Added by 			: A-5219 on 19-Nov-2013
	 * Used for 	:
	 * Parameters	:	@param other
	 * Parameters	:	@return
	 *
	 * @param other the other
	 * @return true, if successful
	 */
	public boolean equals(Object other) {
		if(other != null ){
			return (hashCode() == other.hashCode());
		}else {
			return false;
		}
	}
	
	/**
	 * Overriding Method	:	@see java.lang.Object#hashCode()
	 * Added by 			: A-5219 on 19-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(billingSiteCode).
				append(serialNumber).
				toString().hashCode();
	}
	
	/**
	 * Overriding Method	:	@see java.lang.Object#toString()
	 * Added by 			: A-5219 on 19-Nov-2013
	 * Used for 	:
	 * Parameters	:	@return
	 *
	 * @return the string
	 */
	public String toString(){
		return new StringBuffer().append("companyCode=")
						.append(companyCode)
						.append("\nbillingSiteCode=")
						.append(billingSiteCode)
						.append("\nserialNumber=")
						.append(serialNumber)
						.toString() ;
	}

}
