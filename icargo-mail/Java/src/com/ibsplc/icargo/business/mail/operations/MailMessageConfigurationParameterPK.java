package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;
import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
/**
 * @author A-8923
 * Primary Key of MailMessageConfigurationParameter
 */
@Embeddable 
public class MailMessageConfigurationParameterPK implements Serializable {
	/**
     * The mailSequenceNumber
     */
	private long messageConfigurationSequenceNumber;
	/**
	 * The companyCode
	 */
	private String companyCode;
	/**
	 * parameterCode
	 */
	private String parameterCode;
	
	/**
	 * @return the parameterCode
	 */
	@KeyCondition(column = "PARCOD")
	public String getParameterCode() {
		return parameterCode;
	}
	/**
	 * @param parameterCode the parameterCode to set
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * @return the messageConfigurationSequenceNumber
	 */
	@KeyCondition(column = "MSGCFGSEQNUM")
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
				append(parameterCode).
				append(messageConfigurationSequenceNumber).
				toString().hashCode();
	}
}