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
@KeyTable(table="MALMRAGPAINCSTLKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN",table="MALMRAGPAINCSTLKEY",key="INVVERKEY")
@Embeddable
public class InvoicSettlementDetailPK implements Serializable{

    private String companyCode;
    private long serialNumber;
    private long mailSequenceNumber;

	/**
	 * Default constructor
	 *
	 */
    public InvoicSettlementDetailPK(){

    }

	/**
	 *
	 * @param companyCode
	 * @param poaCode
	 * @param mailSequenceNumber
	 */
	public InvoicSettlementDetailPK(String companyCode, long serialNumber, long mailSequenceNumber) {
		this.companyCode = companyCode;
		this.serialNumber = serialNumber;
		this.mailSequenceNumber = mailSequenceNumber;
	}

    public boolean equals(Object other) {
    	boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
    }

	public int hashCode() {
		return this.toString().hashCode();
	}



	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	@KeyCondition(column =  "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber=serialNumber;
	}

	@KeyCondition(column =  "SERNUM")
	public long getSerialNumber() {
		return this.serialNumber;
	}


	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	@KeyCondition(column = "MALSEQNUM")
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}




	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(199);
		sbul.append("InvoicSettlementDetailPK [ ");
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("', mailSequenceNumber '").append(this.mailSequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
