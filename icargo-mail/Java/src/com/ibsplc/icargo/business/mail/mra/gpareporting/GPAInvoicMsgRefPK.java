/**
 *
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-5219
 *
 */
@KeyTable(table="MALMRAGPAINCMSGREFKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="KEY_GEN",table="MALMRAGPAINCMSGREFKEY",key="GPAINCMSGREFKEY_SEQ")
@Embeddable
public class GPAInvoicMsgRefPK implements Serializable {

	private String companyCode;
    private long serialNumber;
    private long mailSequenceNumber;

	/**
	 * @return the companyCode
	 */
    @KeyCondition(column =  "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the serialNumber
	 */
	@KeyCondition(column = "SERNUM")
	public long getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
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
	 *
	 */
	public GPAInvoicMsgRefPK(){
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(199);
		sbul.append("GPAInvoicMsgRefPK [ ");
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("', mailSequenceNumber '").append(this.mailSequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return this.toString().hashCode();
	}


}
