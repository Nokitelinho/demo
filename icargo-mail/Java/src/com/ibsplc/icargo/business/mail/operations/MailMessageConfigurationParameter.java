package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigParameterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
/**
 * 
 * @author A-8923
 * Entity for storing Mail related Message Configuration Parameters.
 *
 */
@Entity
@Table(name = "MALMSGCFGPAR")
public class MailMessageConfigurationParameter {
	
	/**
	 * mailMessageConfigurationParameterPK
	 */
	private MailMessageConfigurationParameterPK mailMessageConfigurationParameterPK;
	
	/**
	 * parameterValue
	 */
	private String parameterValue;
	
	/**
	 * @return the mailMessageConfigurationParameterPK
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "messageConfigurationSequenceNumber", column = @Column(name = "MSGCFGSEQNUM")),
			@AttributeOverride(name = "parameterCode", column = @Column(name = "PARCOD")) })
	public MailMessageConfigurationParameterPK getMailMessageConfigurationParameterPK() {
		return mailMessageConfigurationParameterPK;
	}
	/**
	 * @param mailMessageConfigurationParameterPK the mailMessageConfigurationParameterPK to set
	 */
	public void setMailMessageConfigurationParameterPK(
			MailMessageConfigurationParameterPK mailMessageConfigurationParameterPK) {
		this.mailMessageConfigurationParameterPK = mailMessageConfigurationParameterPK;
	}
	
	/**
	 * @return the parameterValue
	 */
	@Column(name = "PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	public MailMessageConfigurationParameter(MailRuleConfigParameterVO mailRuleConfigParameter) throws SystemException,
	FinderException{
		populatePK(mailRuleConfigParameter);
		populateAttributes(mailRuleConfigParameter);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		
	}
	
	private void populateAttributes(MailRuleConfigParameterVO mailRuleConfigParameter) {
		
	    this.parameterValue = mailRuleConfigParameter.getParameterValue();
		
	}
	private void populatePK(MailRuleConfigParameterVO mailRuleConfigParameter) {
		MailMessageConfigurationParameterPK mailMessageConfigParameterPK = new MailMessageConfigurationParameterPK();
		mailMessageConfigParameterPK.setCompanyCode(mailRuleConfigParameter.getCompanyCode());
		mailMessageConfigParameterPK.setMessageConfigurationSequenceNumber(mailRuleConfigParameter.getMessageConfigurationSequenceNumber());
		mailMessageConfigParameterPK.setParameterCode(mailRuleConfigParameter.getParameterCode());
		this.mailMessageConfigurationParameterPK=mailMessageConfigParameterPK;
		
	}
}