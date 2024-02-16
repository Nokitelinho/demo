package com.ibsplc.neoicargo.mailmasters.component;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mailmasters.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailIncentiveDetailPK.class)
@SequenceGenerator(name = "MALMRAINCCFGDTLSEQ", initialValue = 1, allocationSize = 1, sequenceName = "malmrainccfgdtl_seq")
@Table(name = "MALMRAINCCFGDTL")
public class MailIncentiveDetail  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_INCENTIVE_CONFIGURATION = "INCENTIVE_CONFIGURATION";
	@Column(name = "PARTYP")
	private String parameterType;
	@Column(name = "PARCOD")
	private String parameterCode;
	@Column(name = "PARVAL")
	private String parameterValue;
	@Column(name = "EXCFLG")
	private String excludeFlag;

	@Id
	@Column(name = "INSSERNUM")
	private int incentiveSerialNumber;
	@Id
	@Column(name = "SEQNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMRAINCCFGDTLSEQ")
	private int sequenceNumber;

	public MailIncentiveDetail() {
	}

	public MailIncentiveDetail(IncentiveConfigurationDetailVO incentiveConfigurationDetailVO, String incentiveFlag) {
		log.debug(MODULE_NAME + " : " + "MailIncentiveDetail" + " Entering");
		populatePK(incentiveConfigurationDetailVO);
		populateAttributes(incentiveConfigurationDetailVO, incentiveFlag);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug(MODULE_NAME + " : " + "MailIncentiveDetail" + " Exiting");
	}

	private void populatePK(IncentiveConfigurationDetailVO incentiveConfigurationDetailVO) {
		this.setCompanyCode(incentiveConfigurationDetailVO.getCompanyCode());
		this.setIncentiveSerialNumber(incentiveConfigurationDetailVO.getIncentiveSerialNumber());
		this.setSequenceNumber(this.getSequenceNumber());

	}

	private void populateAttributes(IncentiveConfigurationDetailVO incentiveConfigurationDetailVO,
			String incentiveFlag) {
		if (MailConstantsVO.FLAG_YES.equals(incentiveFlag)) {
			this.setParameterCode(incentiveConfigurationDetailVO.getIncParameterCode());
			this.setParameterType(incentiveConfigurationDetailVO.getIncParameterType());
			this.setParameterValue(incentiveConfigurationDetailVO.getIncParameterValue());
		} else if (MailConstantsVO.FLAG_NO.equals(incentiveFlag)) {
			this.setParameterCode(incentiveConfigurationDetailVO.getDisIncParameterCode());
			this.setParameterType(incentiveConfigurationDetailVO.getDisIncParameterType());
			this.setParameterValue(incentiveConfigurationDetailVO.getDisIncParameterValue());
		}
		this.setExcludeFlag(incentiveConfigurationDetailVO.getExcludeFlag());
		this.setLastUpdatedTime(Timestamp.valueOf(incentiveConfigurationDetailVO.getLastUpdatedTime().toLocalDateTime()));
		this.setLastUpdatedUser(incentiveConfigurationDetailVO.getLastUpdatedUser());
	}


	/** 
	* @throws SystemException
	* @throws RemoveException
	*/
	public void remove() throws RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

}
