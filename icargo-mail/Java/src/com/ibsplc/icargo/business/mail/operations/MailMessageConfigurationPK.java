package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
@SequenceKeyGenerator(name="ID_GEN",sequence="MALMSGCFG_SEQ")
@Embeddable
public class MailMessageConfigurationPK implements Serializable {
	/**
     * The mailSequenceNumber
     */
	private long messageConfigurationSequenceNumber;
	/**
	 * The companyCode
	 */
	private String companyCode;
	/**
	 * @return the messageConfigurationSequenceNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1" )
	public long getMessageConfigurationSequenceNumber() {
		return messageConfigurationSequenceNumber;
	}
	/**
	 * @param messageConfigurationSequenceNumber the messageConfigurationSequenceNumber to set
	 */
	public void setMessageConfigurationSequenceNumber(long messageConfigurationSequenceNumber) {
		this.messageConfigurationSequenceNumber = messageConfigurationSequenceNumber;
	}
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
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
	 * @param other
	 * @return
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
    /**
     * @return
     */
	public int hashCode() {
		return new StringBuffer(companyCode).
				
				append(messageConfigurationSequenceNumber).
				toString().hashCode();
	}
}