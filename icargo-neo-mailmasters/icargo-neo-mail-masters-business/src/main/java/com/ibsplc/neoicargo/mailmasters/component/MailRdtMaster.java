package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailRdtMasterVO;
import com.ibsplc.neoicargo.mailmasters.vo.RdtMasterFilterVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.Getter;
import lombok.Setter;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMaster.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6991	:	17-Jul-2018	:	Draft
 */
@Setter
@Getter
@Entity
@IdClass(MailRdtMasterPK.class)
@SequenceGenerator(name = "MALRDTREQDLVTIMSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALRDTREQDLVTIM_SEQ")
@Table(name = "MALRDTREQDLVTIM")
public class MailRdtMaster  extends BaseEntity implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALRDTREQDLVTIMSEQ")
	@Column(name = "SERNUM")
	private long SERNUM;
	@Column(name = "DSTARPCOD")
	private String destinationAirportCodes;
	@Column(name = "ORGARPCOD")
	private String originAirportCodes;
	@Column(name = "MALSRVLVL")
	private String malServiceLevel;
	@Column(name = "RDTOFT")
	private int rdtOffset;
	@Column(name = "RDTDAY")
	private int rdtDay;
	@Column(name = "RDTRUL")
	private String rdtRule;
	@Column(name = "MALCLS")
	private String malClass;
	@Column(name = "MALTYP")
	private String malType;
	/** 
	* The GPACode
	*/
	@Column(name = "GPACOD")
	private String gpaCode;


	private static final String MODULE = "mail.masters";

	public MailRdtMaster() {
	}
	public MailRdtMaster(MailRdtMasterVO mailRdtMasterVO) {
		populatePK(mailRdtMasterVO);
		populateAtrributes(mailRdtMasterVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* Method		:	MailRdtMaster.populatePK Added by 	:	A-6991 on 17-Jul-2018 Used for 	:   ICRD-212544 Parameters	:	@param mailRdtMasterVO  Return type	: 	void
	*/
	private void populatePK(MailRdtMasterVO mailRdtMasterVO) {
		this.setCompanyCode(mailRdtMasterVO.getCompanyCode());
	}

	/** 
	* Method		:	MailRdtMaster.populateAtrributes Added by 	:	A-6991 on 17-Jul-2018 Used for 	:   ICRD-212544 Parameters	:	@param mailRdtMasterVO Parameters	:	@throws SystemException  Return type	: 	void
	*/
	private void populateAtrributes(MailRdtMasterVO mailRdtMasterVO) {
		this.setLastUpdatedUser(mailRdtMasterVO.getLastUpdateUser());
		this.setLastUpdatedTime(Timestamp.valueOf(mailRdtMasterVO.getLastUpdateTime().toLocalDateTime()));
		this.setDestinationAirportCodes(mailRdtMasterVO.getAirportCodes());
		this.setOriginAirportCodes(mailRdtMasterVO.getOriginAirportCodes());
		this.setMalClass(mailRdtMasterVO.getMailClass());
		this.setMalServiceLevel(mailRdtMasterVO.getMailServiceLevel());
		this.setMalType(mailRdtMasterVO.getMailType());
		this.setRdtDay(mailRdtMasterVO.getRdtDay());
		this.setRdtOffset(mailRdtMasterVO.getRdtOffset());
		this.setRdtRule(mailRdtMasterVO.getRdtRule());
		this.setGpaCode(mailRdtMasterVO.getGpaCode());
	}

	public static MailRdtMaster find(MailRdtMasterPK mailRdtMasterPK) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailRdtMaster.class, mailRdtMasterPK);
	}

	/**
	* @throws SystemException
	*/
	public void update(MailRdtMasterVO mailRdtMasterVO) {
		populateAtrributes(mailRdtMasterVO);
		this.setLastUpdatedTime(Timestamp.valueOf(mailRdtMasterVO.getLastUpdateTime().toLocalDateTime()));
	}

	/**
	* @return Collection<CoTerminusVO>
	* @throws SystemException
	*/
	public static Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) {
		try {
			return constructDAO().findRdtMasterDetails(filterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
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

	/** 
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException.getMessage(), removeException);
		}
	}


}
