package com.ibsplc.neoicargo.mailmasters.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailServiceLevelVO;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Slf4j
@IdClass(MailServiceLevelPK.class)
@Table(name = "MALSRVLVLMAP")
@Entity
public class MailServiceLevel  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.operations";
	@Column(name = "MALSRVLVL")
	private String mailServiceLevel;

	@Id
	@Column(name = "POACOD")
	private String poaCode;
	@Id
	@Column(name = "MALCTG")
	private String mailCategory;
	@Id
	@Column(name = "MALCLS")
	private String mailClass;
	@Id
	@Column(name = "MALSUBCLS")
	private String mailSubClass;

	public MailServiceLevel() {
	}

	/** 
	* @param mailServiceLevelVO
	* @throws SystemException 
	*/
	public MailServiceLevel(MailServiceLevelVO mailServiceLevelVO) throws CreateException {
		try {
			populatePK(mailServiceLevelVO);
			populateAttributes(mailServiceLevelVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	private void populatePK(MailServiceLevelVO mailServiceLevelVO) {
		this.setCompanyCode(mailServiceLevelVO.getCompanyCode());
		this.setPoaCode(mailServiceLevelVO.getPoaCode());
		this.setMailCategory(mailServiceLevelVO.getMailCategory());
		this.setMailClass(mailServiceLevelVO.getMailClass());
		this.setMailSubClass(mailServiceLevelVO.getMailSubClass());
	}

	private void populateAttributes(MailServiceLevelVO mailServiceLevelVO) {
		this.setMailServiceLevel(mailServiceLevelVO.getMailServiceLevel());
		this.setLastUpdatedTime(Timestamp.valueOf(mailServiceLevelVO.getLastUpdatedTime().toLocalDateTime()));
		this.setLastUpdatedUser(mailServiceLevelVO.getLastUpdatedUser());
	}

	public void remove() throws RemoveException {
		log.debug("MailServiceLevel" + " : " + " remove " + " Entering");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
		log.debug("MailServiceLevel" + " : " + " remove " + " Exiting");
	}
}
