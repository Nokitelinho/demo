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
 * The Class BillingSiteCountryDetailsPK.
 *
 * @author A-5219
 */
@KeyTable(table = "MALMRABLGSITMSTKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALMRABLGSITMSTKEY", key = "BLGSITCNT_KEY")
@Embeddable
public class BillingSiteCountryDetailsPK implements Serializable{
	
	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The serial number. */
	private int serialNumber;

	/**
	 * Gets the company code.
	 *
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Gets the billing site code.
	 *
	 * @return the billingSiteCode
	 */
	@KeyCondition(column = "BLGSITCOD")
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the billingSiteCode to set
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}
	
	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the new serial number
	 */
	public void setSerialNumber(int serialNumber){
		this.serialNumber=serialNumber;
	}
	
	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumber(){
		return serialNumber;
	}
	
	/**
	 * Instantiates a new billing site country details pk.
	 */
	public BillingSiteCountryDetailsPK(){
		
	}
	
	/**
	 * Instantiates a new billing site country details pk.
	 *
	 * @param companyCode the company code
	 * @param billingSiteCode the billing site code
	 * @param serialNumber the serial number
	 */
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if(other != null ){
			return (hashCode() == other.hashCode());
		}else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(billingSiteCode).
				append(serialNumber).
				toString().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
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
