package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;



@SequenceKeyGenerator(name="ID_GEN",sequence="MALRDTMSGADDDTL_SEQ")
@Embeddable

public class MailResditAddressPK implements Serializable{
	private String companyCode;
    private long messageAddressSequenceNumber;

	public boolean equals(Object other) {
		return (other != null) && (this.hashCode() == other.hashCode());
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(messageAddressSequenceNumber).hashCode();
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setMessageAddressSequenceNumber(long messageAddressSequenceNumber) {
		this.messageAddressSequenceNumber = messageAddressSequenceNumber;
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public long getMessageAddressSequenceNumber() {
		return this.messageAddressSequenceNumber;
	}
	

	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("MailResditAddressPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', messageAddressSequenceNumber '").append(this.messageAddressSequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
