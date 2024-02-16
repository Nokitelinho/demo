package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailRuleConfigParameterVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailRuleConfigVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailbagVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.mailmasters.vo.OfficeOfExchangeVO;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-8923Entity for storing mail related message configuration details.
 */
@Setter
@Getter
@Entity
@IdClass(MailMessageConfigurationPK.class)
@SequenceGenerator(name = "MALMSGCFGSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMSGCFG_SEQ")
@Table(name = "MALMSGCFG")
public class MailMessageConfiguration  extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.masters";

	@Id
	@Column(name = "MSGCFGSEQNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMSGCFGSEQ")
	private long messageConfigurationSequenceNumber;
	@Column(name = "MALBOXID")
	private String mailboxId;
	@Column(name = "CFGSTA")
	private String status;
	@Column(name = "VLDFRMDAT")
	private LocalDateTime fromDate;
	@Column(name = "VLDTOODAT")
	private LocalDateTime toDate;

	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
			@JoinColumn(name="MSGCFGSEQNUM", referencedColumnName="MSGCFGSEQNUM", insertable=false, updatable=false)})
	private Set<MailMessageConfigurationParameter> mailMessageConfigurationParameters;

	/** 
	* methods the DAO instance
	* @return
	* @throws SystemException
	*/
	public static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	public static String findMailboxIdFromConfig(MailbagVO mailbagVO) {
		String mailBoxID = null;
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		mailBoxID = constructDAO().findMailboxIdFromConfig(mailbagVO);
		if ((mailBoxID == null || mailBoxID.isEmpty()) && mailbagVO.getOoe() != null && !mailbagVO.getOoe().isEmpty()) {
			exchangeOfficeDetails = new MailController().findOfficeOfExchange(mailbagVO.getCompanyCode(),
					mailbagVO.getOoe(), 1);
			if (exchangeOfficeDetails != null && !exchangeOfficeDetails.isEmpty()) {
				OfficeOfExchangeVO officeOfExchangeVO = exchangeOfficeDetails.iterator().next();
				mailBoxID = officeOfExchangeVO.getMailboxId();
			}
		}
		if ((mailBoxID == null || mailBoxID.isEmpty()) && mailbagVO.getPaCode() != null
				&& !mailbagVO.getPaCode().isEmpty()) {
			try {
				mailBoxID = constructDAO().findMailboxIdForPA(mailbagVO);
			} catch (PersistenceException e) {
				throw new SystemException("PersistenceException");
			}
		}
		return mailBoxID;
	}

	public MailMessageConfiguration(MailRuleConfigVO mailRuleConfigVO) {
		populatePK(mailRuleConfigVO);
		populateAttributes(mailRuleConfigVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		mailRuleConfigVO.setMessageConfigurationSequenceNumber(
				this.getMessageConfigurationSequenceNumber());
		mailRuleConfigVO.setCompanyCode(this.getCompanyCode());
		populateChildren(mailRuleConfigVO);
	}

	private void populateChildren(MailRuleConfigVO mailRuleConfigVO) {
		Set<MailMessageConfigurationParameter> mailMessageConfigurationParameterList = new HashSet<MailMessageConfigurationParameter>();
		Collection<MailRuleConfigParameterVO> detailVOs = mailRuleConfigVO.getMailRuleConfigParameters();
		if (!detailVOs.isEmpty()) {
			for (MailRuleConfigParameterVO detailVO : detailVOs) {
				detailVO.setMessageConfigurationSequenceNumber(
						mailRuleConfigVO.getMessageConfigurationSequenceNumber());
				detailVO.setCompanyCode(mailRuleConfigVO.getCompanyCode());
				MailMessageConfigurationParameter mailMessageConfigurationParameter = new MailMessageConfigurationParameter(
						detailVO);
				mailMessageConfigurationParameterList.add(mailMessageConfigurationParameter);
			}
			this.setMailMessageConfigurationParameters(mailMessageConfigurationParameterList);
		}
	}

	private void populateAttributes(MailRuleConfigVO mailRuleConfigVO) {
		this.mailboxId = mailRuleConfigVO.getMailboxId();
		this.status = "INACTIVE";
		setFromDate(mailRuleConfigVO.getFromDate().toLocalDateTime());
		setToDate(mailRuleConfigVO.getToDate().toLocalDateTime());
	}

	private void populatePK(MailRuleConfigVO mailRuleConfigVO) {
		this.setCompanyCode(mailRuleConfigVO.getCompanyCode());
	}

}
