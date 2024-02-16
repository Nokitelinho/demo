/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-7794
 *
 */

@KeyTable(table = "MALMRAGPABLGKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALMRAGPABLGKEY", key = "SERNUM")
@Embeddable
public class MRAGPABillingDetailsPK implements Serializable{

	
	private String companyCode;
	
	private long mailSequenceNumber;
	private int serialNumber;
	
	
	/**
	 * @return the sequenceNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the comapnyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * @return the mailSequenceNumber
	 */
	@KeyCondition(column = "MALSEQNUM")
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	
	/**
	 * @param comapnyCode
	 * @param mailSequenceNumber
	 * @param serialNumber
	 */
	public MRAGPABillingDetailsPK(String companyCode,long mailSequenceNumber,int serialNumber) {
		super();
		
		this.companyCode = companyCode;
		this.mailSequenceNumber = mailSequenceNumber;
		this.serialNumber = serialNumber;
	}
	/**
	 * 
	 */
	public MRAGPABillingDetailsPK() {
		
	}
	
	/**
	 * @param object
	 * @return boolean
	 */
	public boolean equals(Object object) {
		if (object != null) {
			return (hashCode() == object.hashCode());
		}
		return false;
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder()
				.append(companyCode).append(mailSequenceNumber).append(serialNumber).toString().hashCode();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MRAGPABillingDetailsPK [companyCode=" + companyCode
				+ ", mailSequenceNumber=" + mailSequenceNumber
				+ ", serialNumber=" + serialNumber + "]";
	}
	
}
