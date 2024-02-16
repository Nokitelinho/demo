package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author A-8923Entity for storing mail related message configuration details.
 */
@Setter
@Getter
@Entity
@IdClass(MailMessageConfigurationPK.class)
@SequenceGenerator(name = "MALMSGCFGSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMSGCFG_SEQ")
@Table(name = "MALMSGCFG")
public class MailMessageConfiguration extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMSGCFGSEQ")
	@Column(name = "MSGCFGSEQNUM")
	private long messageConfigurationSequenceNumber;
	@Id
	@Transient
	private String companyCode;
	@Column(name = "MALBOXID")
	private String mailboxId;
	@Column(name = "CFGSTA")
	private String status;
	@Column(name = "VLDFRMDAT")
	private LocalDateTime fromDate;
	@Column(name = "VLDTOODAT")
	private LocalDateTime toDate;

	/**
	* methods the DAO instance
	* @return
	* @throws SystemException
	*/
	public static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	public static String findMailboxIdFromConfig(MailbagVO mailbagVO) {
		String mailBoxID = null;
		Page<OfficeOfExchangeVO> exchangeOfficeDetails;
		mailBoxID = constructDAO().findMailboxIdFromConfig(mailbagVO);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		if ((mailBoxID == null || mailBoxID.isEmpty()) && mailbagVO.getOoe() != null && !mailbagVO.getOoe().isEmpty()) {
			exchangeOfficeDetails = mailController.findOfficeOfExchange(mailbagVO.getCompanyCode(),
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

}
