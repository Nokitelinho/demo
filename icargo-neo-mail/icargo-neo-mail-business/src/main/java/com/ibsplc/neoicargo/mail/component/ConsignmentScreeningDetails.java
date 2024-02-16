package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@Entity
@IdClass(ConsignmentScreeningDetailsPK.class)
@SequenceGenerator(name = "MALCSGCSDDTLSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCSGCSDDTL_SEQ")
@Table(name = "MALCSGCSDDTL")
public class ConsignmentScreeningDetails extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";
	private static final String SOURCE = "SCREEN";

	/** 
	* Dummy constructor
	*/
	public ConsignmentScreeningDetails() {

	}

	@Column(name = "SRCIND")
	private String sourceIndicator;
	@Column(name = "SCRLOC")
	private String screeningLocation;
	@Column(name = "SECSCRMTHCOD")
	private String screeningMethodCode;
	@Column(name = "SCRAPLAUT")
	private String screeningAuthority;
	@Column(name = "SCRAPLREG")
	private String screeningRegulation;
	@Column(name = "RMK")
	private String remarks;
	@Column(name = "STDBAG")
	private int statedBags;
	@Column(name = "STDWGT")
	private double statedWeight;
	@Column(name = "ADLSECINF")
	private String additionalSecurityInfo;
	@Column(name = "SECSTADAT")
	private LocalDateTime securityStatusDate;
	@Column(name = "SECSTAPTY")
	private String securityStatusParty;
	@Column(name = "CSGSRC")
	private String source;
	@Column(name = "ARPCOD")
	private String airportCode;
	@Column(name = "SCRRES")
	private String result;
	@Column(name = "SCRLVL")
	private String screenLevelValue;
	@Column(name = "SCRDTLTYP")
	private String screenDetailType;
	@Column(name = "AGTTYP")
	private String agentType;
	@Column(name = "AGTIDR")
	private String agentID;
	@Column(name = "CNTCOD")
	private String countryCode;
	@Column(name = "EXPDAT")
	private String expiryDate;
	@Column(name = "CSGDOCNUM")
	private String consignmentNumber;
	@Column(name = "POACOD")
	private String paCode;
	@Column(name = "CSGSEQNUM")
	private int consignmentSequenceNumber;
	@Column(name = "APLREGTRPDIR")
	private String applicableRegTransportDirection;
	@Column(name = "APLREGBRDAGYAUT")
	private String applicableRegBorderAgencyAuthority;
	@Column(name = "APLREGREFIDR")
	private String applicableRegReferenceID;
	@Column(name = "APLREGFLG")
	private String applicableRegFlag;
	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "SERNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCSGCSDDTLSEQ")
	private long serialNumber;

	@Column(name = "MALSEQNUM")
	private long malseqnum;
	@Column(name = "SCEAPLAUT")
	private String seScreeningAuthority;
	@Column(name = "SCERSNCOD")
	private String seScreeningReasonCode;
	@Column(name = "SCEAPLREG")
	private String seScreeningRegulation;
	@Column(name = "AGTSERNUM")
	private long agentSerialNumber;


	public ConsignmentScreeningDetails(ConsignmentScreeningVO consignmentScreeningVO) {
		populatePk(consignmentScreeningVO);
		populateAttributes(consignmentScreeningVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	private void populatePk(ConsignmentScreeningVO consignmentScreeningVO) {
		this.setCompanyCode(consignmentScreeningVO.getCompanyCode());
	}

	private void populateAttributes(ConsignmentScreeningVO consignmentScreeningVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		this.setSourceIndicator(consignmentScreeningVO.getSourceIndicator());
		this.setScreeningAuthority(consignmentScreeningVO.getScreeningAuthority());
		this.setScreeningMethodCode(consignmentScreeningVO.getScreeningMethodCode());
		this.setScreeningLocation(consignmentScreeningVO.getScreeningLocation());
		this.setScreeningRegulation(consignmentScreeningVO.getScreeningRegulation());
		this.setStatedBags(consignmentScreeningVO.getStatedBags());
		populateStatedWeight(consignmentScreeningVO);
		this.setSecurityStatusParty(consignmentScreeningVO.getSecurityStatusParty());
		populateSecurityStatusDate(consignmentScreeningVO);
		this.setAdditionalSecurityInfo(consignmentScreeningVO.getAdditionalSecurityInfo());
		this.setRemarks(consignmentScreeningVO.getRemarks());
		this.setResult(consignmentScreeningVO.getResult());
		populateAirportCode(consignmentScreeningVO, contextUtil);
		populateSource(consignmentScreeningVO);
		this.setPaCode(consignmentScreeningVO.getPaCode());
		this.setConsignmentNumber(consignmentScreeningVO.getConsignmentNumber());
		this.setConsignmentSequenceNumber(consignmentScreeningVO.getConsignmentSequenceNumber());
		this.setScreenLevelValue(consignmentScreeningVO.getScreenLevelValue());
		this.setScreenDetailType(consignmentScreeningVO.getSecurityReasonCode());
		this.setApplicableRegTransportDirection(consignmentScreeningVO.getApplicableRegTransportDirection());
		this.setApplicableRegBorderAgencyAuthority(consignmentScreeningVO.getApplicableRegBorderAgencyAuthority());
		this.setApplicableRegReferenceID(consignmentScreeningVO.getApplicableRegReferenceID());
		this.setApplicableRegFlag(consignmentScreeningVO.getApplicableRegFlag());
		this.setSecurityStatusDate(consignmentScreeningVO.getSecurityStatusDate());
		this.setAgentID(consignmentScreeningVO.getAgentID());
		this.setAgentType(consignmentScreeningVO.getAgentType());
		this.setCountryCode(consignmentScreeningVO.getIsoCountryCode());
		this.setExpiryDate(consignmentScreeningVO.getExpiryDate());
		this.setMalseqnum(consignmentScreeningVO.getMalseqnum());
		this.setSeScreeningAuthority(consignmentScreeningVO.getSeScreeningAuthority());
		this.setSeScreeningReasonCode(consignmentScreeningVO.getSeScreeningReasonCode());
		this.setSeScreeningRegulation(consignmentScreeningVO.getSeScreeningRegulation());
	}

	private void populateSource(ConsignmentScreeningVO consignmentScreeningVO) {
		if (ConsignmentScreeningVO.OPERATION_FLAG_INSERT.equals(consignmentScreeningVO.getOpFlag())) {
			this.setSource(SOURCE);
		} else {
			if (consignmentScreeningVO.getSource() != null) {
				this.setSource(consignmentScreeningVO.getSource());
			}
		}
	}

	private void populateAirportCode(ConsignmentScreeningVO consignmentScreeningVO, ContextUtil contextUtil) {
		if (!AbstractVO.OPERATION_FLAG_UPDATE.equals(consignmentScreeningVO.getOpFlag())) {
			if (consignmentScreeningVO.getAirportCode() != null) {
				this.setAirportCode(consignmentScreeningVO.getAirportCode());
			} else {
				LoginProfile logonVO;
				try {
					logonVO = contextUtil.callerLoginProfile();
					this.setAirportCode(logonVO.getAirportCode());
				} finally {
				}
			}
		}
	}

	private void populateSecurityStatusDate(ConsignmentScreeningVO consignmentScreeningVO) {
		if (Objects.nonNull(consignmentScreeningVO.getSecurityStatusDate())) {
			this.setSecurityStatusDate(consignmentScreeningVO.getSecurityStatusDate());
		}
	}

	private void populateStatedWeight(ConsignmentScreeningVO consignmentScreeningVO) {
		if (Objects.nonNull(consignmentScreeningVO.getStatedWeight())) {
			this.setStatedWeight(consignmentScreeningVO.getStatedWeight().getValue().doubleValue());
		}
	}

	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

	public static ConsignmentScreeningDetails find(String companyCode, long serialNumber) throws FinderException {
		ConsignmentScreeningDetailsPK consignmentScreeningDetailsPK = new ConsignmentScreeningDetailsPK();
		consignmentScreeningDetailsPK.setCompanyCode(companyCode);
		consignmentScreeningDetailsPK.setSerialNumber(serialNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(ConsignmentScreeningDetails.class, consignmentScreeningDetailsPK);
	}

	private static MailOperationsDAO constructDAO() {
		MailOperationsDAO mailOperationsDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mailOperationsDAO = MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailOperationsDAO;
	}

	/** 
	* @author A-8353
	* @param consignmentScreeningVO
	* @return
	* @throws SystemException
	*/
	public long findLatestRegAgentIssuing(ConsignmentScreeningVO consignmentScreeningVO) {
		return constructDAO().findLatestRegAgentIssuing(consignmentScreeningVO);
	}

	/** 
	* @author A-8353
	* @param consignmentScreeningVO
	* @return
	* @throws SystemException
	*/
	public Collection<ConsignmentScreeningVO> findScreeningMethodsForStampingRegAgentIssueMapping(
			ConsignmentScreeningVO consignmentScreeningVO) {
		return constructDAO().findScreeningMethodsForStampingRegAgentIssueMapping(consignmentScreeningVO);
	}

	public void setSecurityStatusDate(ZonedDateTime securityStatusDate) {
		if (Objects.nonNull(securityStatusDate)) {
			this.securityStatusDate = securityStatusDate.toLocalDateTime();
		}
	}
}
