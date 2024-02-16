package com.ibsplc.neoicargo.mailmasters.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailServiceStandardFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailServiceStandardVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-8149
 */
@Setter
@Getter
@Entity
@IdClass(MailServiceStandardPK.class)
@Table(name = "MALSRVSTDCFG")
public class MailServiceStandard  extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.masters";

	@Id
	@Column(name = "GPACOD")
	private String gpaCode;
	@Id
	@Column(name = "ORGCOD")
	private String originCode;
	@Id
	@Column(name = "DSTCOD")
	private String destCode;
	@Id
	@Column(name = "SRVLVL")
	private String serviceLevel;
	@Column(name = "SCNWVDFLG")
	private String scanningWaived;
	@Column(name = "SRVSTD")
	private int serviceStandard;
	@Column(name = "CTRIDR")
	private String contractID;
	@Column(name = "TAGIDX")
	private int tagIndex;

	public MailServiceStandard() {
	}

	public MailServiceStandard(MailServiceStandardVO mailServiceStandardVO) {
		populatePK(mailServiceStandardVO);
		populateAtrributes(mailServiceStandardVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	private void populatePK(MailServiceStandardVO mailServiceStandardVO) {
		this.setCompanyCode(mailServiceStandardVO.getCompanyCode());
		this.setGpaCode(mailServiceStandardVO.getGpaCode());
		this.setOriginCode(mailServiceStandardVO.getOriginCode());
		this.setDestCode(mailServiceStandardVO.getDestinationCode());
		this.setServiceLevel(mailServiceStandardVO.getServicelevel());
	}

	private void populateAtrributes(MailServiceStandardVO mailServiceStandardVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		this.setScanningWaived(mailServiceStandardVO.getScanWaived());
		this.setServiceStandard(Integer.parseInt(mailServiceStandardVO.getServicestandard()));
		this.setContractID(mailServiceStandardVO.getContractid());
		this.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
		this.setLastUpdatedUser(mailServiceStandardVO.getLastUpdateUser());
		this.setTagIndex(0);
	}

	public static MailServiceStandard find(MailServiceStandardPK mailServiceStandardPK) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailServiceStandard.class, mailServiceStandardPK);
	}

	public void update(MailServiceStandardVO mailServiceStandardVO) {
		populateAtrributes(mailServiceStandardVO);
	}

	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException.getMessage(), removeException);
		}
	}

	public static Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO, int pageNumeber) {
		try {
			return constructDAO().listServiceStandardDetails(mailServiceStandardFilterVO, pageNumeber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

}
