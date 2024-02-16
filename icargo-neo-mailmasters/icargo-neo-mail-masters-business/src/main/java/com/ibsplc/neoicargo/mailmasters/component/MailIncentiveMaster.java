package com.ibsplc.neoicargo.mailmasters.component;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.mailmasters.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.neoicargo.mailmasters.vo.IncentiveConfigurationFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.IncentiveConfigurationVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** 
 * @author A-6986
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailIncentiveMasterPK.class)
@SequenceGenerator(name = "MALMRAINCCFGMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMRAINCCFG_SEQ")
@Table(name = "MALMRAINCCFGMST")
public class MailIncentiveMaster  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.masters";
	private static final String SEPARATOR = "~";
	@Column(name = "POACOD")
	private String poaCode;
	@Column(name = "INCFLG")
	private String incentiveFlag;
	@Column(name = "SRVRSPLANFLG")
	private String serviceResponsiveFlag;
	@Column(name = "INCPER")
	private double incentivePercentage;
	@Column(name = "VLDFRMDAT")
	private LocalDateTime validFrom;
	@Column(name = "VLDTOODAT")
	private LocalDateTime validTo;
	@Column(name = "DISPAREXP")
	private String dispFormulaExpression;
	@Column(name = "PAREXP")
	private String formulaExpression;
	@Column(name = "BSSEXP")
	private String basisExpression;

	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INSSERNUM", referencedColumnName = "INSSERNUM", insertable = false, updatable = false) })
	private Set<MailIncentiveDetail> incentiveDetails;

	@Id
	@Column(name = "INSSERNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMRAINCCFGMSTSEQ")
	private int incentiveSerialNumber;

	public MailIncentiveMaster() {
	}

	public MailIncentiveMaster(IncentiveConfigurationVO incentiveConfigurationVO) {
		log.debug(MODULE_NAME + " : " + "MailIncentiveMaster" + " Entering");
		populatePK(incentiveConfigurationVO);
		populateAttributes(incentiveConfigurationVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		incentiveConfigurationVO.setIncentiveSerialNumber(this.getIncentiveSerialNumber());
		populateChildren(incentiveConfigurationVO);
		log.debug(MODULE_NAME + " : " + "MailIncentiveMaster" + " Exiting");
	}

	private void populatePK(IncentiveConfigurationVO incentiveConfigurationVO) {

		this.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
	}

	private void populateAttributes(IncentiveConfigurationVO incentiveConfigurationVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String newExpression = null;
		this.setPoaCode(incentiveConfigurationVO.getPaCode());
		this.setIncentiveFlag(incentiveConfigurationVO.getIncentiveFlag());
		this.setServiceResponsiveFlag(incentiveConfigurationVO.getServiceRespFlag());
		if (MailConstantsVO.FLAG_YES.equals(incentiveConfigurationVO.getIncentiveFlag())) {
			this.setIncentivePercentage(incentiveConfigurationVO.getIncPercentage());
			com.ibsplc.icargo.framework.util.time.LocalDate fromDateToSave = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
			fromDateToSave.setDate(incentiveConfigurationVO.getIncValidFrom());
			this.setValidFrom(LocalDateMapper.toZonedDateTime(fromDateToSave).toLocalDateTime());
			com.ibsplc.icargo.framework.util.time.LocalDate toDateToSave = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
			toDateToSave.setDate(incentiveConfigurationVO.getIncValidTo());
			this.setValidTo(LocalDateMapper.toZonedDateTime(toDateToSave).toLocalDateTime());
		} else if (MailConstantsVO.FLAG_NO.equals(incentiveConfigurationVO.getIncentiveFlag())) {
			this.setIncentivePercentage(incentiveConfigurationVO.getDisIncPercentage());
			com.ibsplc.icargo.framework.util.time.LocalDate fromDateToSave = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
			fromDateToSave.setDate(incentiveConfigurationVO.getDisIncValidFrom());
			this.setValidFrom(LocalDateMapper.toZonedDateTime(fromDateToSave).toLocalDateTime());
			com.ibsplc.icargo.framework.util.time.LocalDate toDateToSave = new com.ibsplc.icargo.framework.util.time.LocalDate(com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, true);
			toDateToSave.setDate(incentiveConfigurationVO.getDisIncValidTo());
			this.setValidTo(LocalDateMapper.toZonedDateTime(toDateToSave).toLocalDateTime());
			this.setBasisExpression(incentiveConfigurationVO.getBasis());
			String formulaReplacement=incentiveConfigurationVO.getFormula();
			String formulaReplaced=formulaReplacement;
			if(formulaReplaced.contains("&gt;")){
				formulaReplaced = formulaReplaced.replaceAll("&gt;", ">");
			}
			if(formulaReplaced.contains("&lt;"))
			{
				formulaReplaced = formulaReplaced.replaceAll("&lt;", "<");
			}
			incentiveConfigurationVO.setFormula(formulaReplaced);
			this.setDispFormulaExpression(incentiveConfigurationVO.getFormula());
			String[] formula = incentiveConfigurationVO.getFormula().split(SEPARATOR);
			for (int j = 0; j < formula.length; j++) {
				if (StringUtils.isNotEmpty(formula[j]) && StringUtils.isNumeric(formula[j])) {
					formula[j] = formula[j].concat("/1440");
				}
				if (newExpression != null) {
					newExpression = newExpression.concat(formula[j]);
				} else {
					newExpression = formula[j];
				}
			}
			this.setFormulaExpression(newExpression);
		}

		//	this.setLastUpdatedTime(Timestamp.valueOf(incentiveConfigurationVO.getLastUpdatedTime().toLocalDateTime()));
		this.setLastUpdatedUser(incentiveConfigurationVO.getLastUpdatedUser());
	}

	private void populateChildren(IncentiveConfigurationVO incentiveConfigurationVO) {
		log.debug("MailIncentiveMaster" + " : " + "populateChildren" + " Entering");
		Collection<IncentiveConfigurationDetailVO> incentiveDetailVOs = incentiveConfigurationVO
				.getIncentiveConfigurationDetailVOs();
		if (incentiveDetailVOs != null && incentiveDetailVOs.size() > 0) {
			Set<MailIncentiveDetail> detailVOs = new HashSet<MailIncentiveDetail>();
			for (IncentiveConfigurationDetailVO incentiveDetailVO : incentiveDetailVOs) {
				if (MailConstantsVO.FLAG_YES.equals(incentiveConfigurationVO.getIncentiveFlag())) {
					if (incentiveDetailVO.getIncParameterCode() != null
							&& incentiveDetailVO.getIncParameterValue() != null
							&& incentiveDetailVO.getIncParameterCode().length() > 0) {
						incentiveDetailVO.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
						incentiveDetailVO.setLastUpdatedTime(incentiveConfigurationVO.getLastUpdatedTime());
						incentiveDetailVO.setLastUpdatedUser(incentiveConfigurationVO.getLastUpdatedUser());
						incentiveDetailVO.setIncentiveSerialNumber(incentiveConfigurationVO.getIncentiveSerialNumber());
						MailIncentiveDetail mailIncentiveDetail = new MailIncentiveDetail(incentiveDetailVO,
								incentiveConfigurationVO.getIncentiveFlag());
						detailVOs.add(mailIncentiveDetail);
					}
				} else if (MailConstantsVO.FLAG_NO.equals(incentiveConfigurationVO.getIncentiveFlag())) {
					if (incentiveDetailVO.getDisIncParameterCode() != null
							&& incentiveDetailVO.getDisIncParameterValue() != null
							&& incentiveDetailVO.getDisIncParameterCode().length() > 0) {
						incentiveDetailVO.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
						incentiveDetailVO.setLastUpdatedTime(incentiveConfigurationVO.getLastUpdatedTime());
						incentiveDetailVO.setLastUpdatedUser(incentiveConfigurationVO.getLastUpdatedUser());
						incentiveDetailVO.setIncentiveSerialNumber(incentiveConfigurationVO.getIncentiveSerialNumber());
						MailIncentiveDetail mailIncentiveDetail = new MailIncentiveDetail(incentiveDetailVO,
								incentiveConfigurationVO.getIncentiveFlag());
						detailVOs.add(mailIncentiveDetail);
					}
				}
			}
			this.setIncentiveDetails(detailVOs);
		}
	}

	/** 
	* @param mailIncentiveMasterPK
	* @return MailIncentiveMaster
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailIncentiveMaster find(MailIncentiveMasterPK mailIncentiveMasterPK) throws FinderException {
		return PersistenceController.getEntityManager().find(MailIncentiveMaster.class, mailIncentiveMasterPK);
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
			Collection<MailIncentiveDetail> incentiveDetails = this.getIncentiveDetails();
			if (incentiveDetails != null && incentiveDetails.size() > 0) {
				for (MailIncentiveDetail incentiveDetail : incentiveDetails) {
					incentiveDetail.remove();
				}
				this.incentiveDetails.removeAll(incentiveDetails);
			}
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	public void update(IncentiveConfigurationVO incentiveConfigurationVO) {
		populateAttributes(incentiveConfigurationVO);
	}

	/** 
	* @param incentiveConfigurationFilterVO
	* @return incentiveConfigurationFilterVO
	* @throws SystemException
	*/
	public static Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterVO incentiveConfigurationFilterVO) {
		try {
			return constructDAO().findIncentiveConfigurationDetails(incentiveConfigurationFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}



}
