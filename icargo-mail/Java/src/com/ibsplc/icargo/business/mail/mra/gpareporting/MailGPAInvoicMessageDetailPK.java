package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;


/**
 * 
 * @author A-7371
 *
 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALMRAGPAINCMSGDTL_SEQ")
@Embeddable
public class MailGPAInvoicMessageDetailPK implements Serializable {

	
	private String companyCode;    

	private long serialNumber; 
	
	private long sequenceNumber;
	


	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
   
    
	public int hashCode() {

		return new StringBuffer(companyCode).
				append(serialNumber).
				append(sequenceNumber).
				toString().hashCode();
	}

	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}


	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	public long getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Key(generator = "ID_GEN", startAt = "1" )
	public long getSequenceNumber() {
		return sequenceNumber;
	}


	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("MailInvoicMessageDetailPK [ ");
		sbul.append("companyCode '").append(this.companyCode);

		sbul.append("serialNumber '").append(this.serialNumber);
		sbul.append("sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
	

}
