package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.*;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.GPAContractFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.GPAContractVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Slf4j
@Entity
@NoArgsConstructor
@IdClass(GPAContractPK.class)
@SequenceGenerator(name = "MALGPACTRSEQ", initialValue = 1, allocationSize = 1, sequenceName = "malgpactr_seq")
@Table(name = "MALGPACTR")
public class GPAContract extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.masters";
	@Column(name = "ORGARP")
	private String origin;
	@Column(name = "DSTARP")
	private String destination;
	@Column(name = "VLDFRM")
	private LocalDateTime fromDate;
	@Column(name = "VDLTOO")
	private LocalDateTime toDate;

	@Column(name = "CTRIDR")
	private String contractID;
	@Column(name = "REGCOD")
	private String region;
	@Column(name = "AMOTFLG")
	private String amotflag;
	@Id
	@Column(name = "GPACOD")
	private String gpaCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALGPACTRSEQ")
	@Column(name = "SERNUM")
	private int sernum;

	public GPAContract(GPAContractVO gpaContractVO) {
		log.debug(MODULE_NAME + " : " + "GPAContract" + " Entering");
		populatePK(gpaContractVO);
		populateAttributes(gpaContractVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug(MODULE_NAME + " : " + "GPAContract" + " Exiting");
	}

	private void populatePK(GPAContractVO gpaContractVO) {
		this.setCompanyCode(gpaContractVO.getCompanyCode());
		this.setGpaCode(gpaContractVO.getPaCode());
	}

	private void populateAttributes(GPAContractVO gpaContractVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		setOrigin(gpaContractVO.getOriginAirports());
		setDestination(gpaContractVO.getDestinationAirports());
		com.ibsplc.icargo.framework.util.time.LocalDate fromDateToSave = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
		fromDateToSave.setDate(gpaContractVO.getCidFromDates());
		setFromDate(LocalDateMapper.toZonedDateTime(fromDateToSave).toLocalDateTime());
		com.ibsplc.icargo.framework.util.time.LocalDate toDateToSave = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
		toDateToSave.setDate(gpaContractVO.getCidToDates());
		setToDate(LocalDateMapper.toZonedDateTime(toDateToSave).toLocalDateTime());
		setLastUpdatedTime(Timestamp.valueOf(gpaContractVO.getLastUpdatedTime().toLocalDateTime()));
		setLastUpdatedUser(gpaContractVO.getLastUpdatedUser());
		setContractID(gpaContractVO.getContractIDs());
		setRegion(gpaContractVO.getRegions());
		setAmotflag(gpaContractVO.getAmot());
	}

	public static GPAContract find(GPAContractPK gpaContractPK) throws FinderException {
		return PersistenceController.getEntityManager().find(GPAContract.class, gpaContractPK);
	}

	/** 
	* This method returns the Instance of the DAO
	* @return
	* @throws SystemException
	*/
	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
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

	public void update(GPAContractVO gpaContractVO) {
		populateAttributes(gpaContractVO);
	}

	public static Collection<GPAContractVO> listContractdetails(GPAContractFilterVO contractFilterVO) {
		try {
			return constructDAO().listContractdetails(contractFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static Collection<GPAContractVO> listODForContract(GPAContractFilterVO contractFilterVO) {
		try {
			return constructDAO().listContractdetails(contractFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
