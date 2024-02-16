package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.DamagedMailbagVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(DamagedMailbagPK.class)
@Table(name = "MALDMGDTL")
public class DamagedMailbag  extends BaseEntity implements Serializable {
	@Id
	@Transient
	private String companyCode;

	@Id
	@Column(name = "DMGCOD")
	private String damageCode;
	@Id
	@Column(name = "ARPCOD")
	private String airportCode;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;

	@Column(name = "DMGDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime damageDate;
	@Column(name = "RMK")
	private String remarks;
	@Column(name = "DMGDES")
	private String damageDescription;
	private static final String MAIL_OPERATIONS = "mail.operations";
	@Column(name = "OPRTYP")
	private String operationType;
	@Column(name = "RTNFLG")
	private String returnFlag;
	@Column(name = "POACOD")
	private String paCode;

	public DamagedMailbag() {
	}

	/**
	* @param damagedMailbagVO
	* @throws SystemException
	*/
	public DamagedMailbag(DamagedMailbagPK damagedMailbagPK, DamagedMailbagVO damagedMailbagVO) {
		populatePK(damagedMailbagPK, damagedMailbagVO);
		populateAttributes(damagedMailbagVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* A-1739
	* @param damagedMailbagPK
	* @param damagedMailbagVO
	*/
	private void populatePK(DamagedMailbagPK damagedMailbagPK, DamagedMailbagVO damagedMailbagVO) {
		this.setCompanyCode(damagedMailbagPK.getCompanyCode());
		this.setAirportCode(damagedMailbagVO.getAirportCode());
		this.setDamageCode(damagedMailbagVO.getDamageCode());
		this.setMailSequenceNumber(damagedMailbagPK.getMailSequenceNumber());
	}

	/** 
	* A-1739
	* @param damagedMailbagVO
	*/
	private void populateAttributes(DamagedMailbagVO damagedMailbagVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		setDamageDate(damagedMailbagVO.getDamageDate().toLocalDateTime());
		setOperationType(damagedMailbagVO.getOperationType());
		setRemarks(damagedMailbagVO.getRemarks());
		setDamageDescription(damagedMailbagVO.getDamageDescription());
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		if (damagedMailbagVO.getUserCode() != null) {
			setLastUpdatedUser(damagedMailbagVO.getUserCode());
		} else {
			setLastUpdatedUser(logonAttributes.getUserId());
		}
		if (MailConstantsVO.FLAG_YES.equals(damagedMailbagVO.getReturnedFlag())) {
			setReturnFlag(MailConstantsVO.FLAG_YES);
		} else {
			setReturnFlag(MailConstantsVO.FLAG_NO);
		}
		setPaCode(damagedMailbagVO.getPaCode());
	}

	/** 
	* A-5991
	* @param mailbagPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static DamagedMailbag find(DamagedMailbagPK mailbagPK) throws FinderException {
		return PersistenceController.getEntityManager().find(DamagedMailbag.class, mailbagPK);
	}

	/** 
	* @author A-5991
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}
	/**
	 * A-1739
	 *
	 * @param damagedMailbagVO
	 */
	public void update(DamagedMailbagVO damagedMailbagVO) {
		populateAttributes(damagedMailbagVO);
	}

	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception.getMessage(), exception);
		}
	}
	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @param airportCode TODO
	 * @return
	 * @throws SystemException
	 */
	public static String findDamageReason(String companyCode, String receptacleID, String airportCode) {
		try {
			return constructDAO().findDamageReason(companyCode, receptacleID, airportCode);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}



}
