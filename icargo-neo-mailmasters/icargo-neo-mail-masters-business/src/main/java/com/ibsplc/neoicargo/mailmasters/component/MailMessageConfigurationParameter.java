package com.ibsplc.neoicargo.mailmasters.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailRuleConfigParameterVO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** 
 * @author A-8923Entity for storing Mail related Message Configuration Parameters.
 */
@Setter
@Getter
@Entity
@IdClass(MailMessageConfigurationParameterPK.class)
@SequenceGenerator(name = "MALMSGCFGPARSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMSGCFGPAR_SEQ")
@Table(name = "MALMSGCFGPAR")
public class MailMessageConfigurationParameter  extends BaseEntity implements Serializable {

	/**
	 * The mailSequenceNumber
	 */
	@Id
	@Column(name = "MSGCFGSEQNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMSGCFGPARSEQ")
	private long messageConfigurationSequenceNumber;
	/**
	 * parameterCode
	 */
	@Id
	@Column(name = "PARCOD")
	private String parameterCode;
	/** 
	* parameterValue
	*/
	@Column(name = "PARVAL")
	private String parameterValue;

	/** 
	* @return the parameterValue
	*/
	public String getParameterValue() {
		return parameterValue;
	}

	public MailMessageConfigurationParameter(MailRuleConfigParameterVO mailRuleConfigParameter)  {
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
		this.setCompanyCode(mailRuleConfigParameter.getCompanyCode());
		this
				.setMessageConfigurationSequenceNumber(mailRuleConfigParameter.getMessageConfigurationSequenceNumber());
		this.setParameterCode(mailRuleConfigParameter.getParameterCode());

	}
}
