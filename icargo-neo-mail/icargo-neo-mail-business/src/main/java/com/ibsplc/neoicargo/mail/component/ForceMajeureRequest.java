package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.neoicargo.mail.vo.ForceMajeureRequestVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/** 
 * @author A-5219
 */
@Setter
@Getter
@Slf4j
@IdClass(ForceMajeureRequestPK.class)
@SequenceGenerator(name = "MALFORMJRREQMST", initialValue = 1, allocationSize = 1, sequenceName = "MALFORMJRREQMST_SEQ")
@Table(name = "MALFORMJRREQMST")
@Entity
public class ForceMajeureRequest extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.operations";
	private static final String CLASS_NAME = "ForceMajeureRequest";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "FORMJRIDR")
	private String forceMajuereID;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALFORMJRREQMST")
	@Column(name = "SEQNUM")
	private long sequenceNumber;

	@Column(name = "MALSEQNUM")
	private long mailSeqNumber;
	@Column(name = "MALIDR")
	private String mailID;
	@Column(name = "ARPCOD")
	private String airportCode;
	@Column(name = "FLTCARCOD")
	private String carrierCode;
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Column(name = "FLTDAT")
	private LocalDateTime flightDate;
	@Column(name = "FRMDAT")
	private LocalDateTime fromDate;
	@Column(name = "TOODAT")
	private LocalDateTime toDate;
	@Column(name = "OPRSRC")
	private String source;
	@Column(name = "WGT")
	private double weight;
	@Column(name = "ORGARPCOD")
	private String originAirport;
	@Column(name = "DSTARPCOD")
	private String destinationAirport;
	@Column(name = "CSGDOCNUM")
	private String consignmentDocNumber;
	@Column(name = "FORMJRSTA")
	private String status;
	@Column(name = "REQRMK")
	private String requestRemarks;
	@Column(name = "APRRMK")
	private String approvalRemarks;
	@Column(name = "FILPAR")
	private String filterParameters;
	@Column(name = "REQDAT")
	private LocalDateTime requestDate;

	/** 
	*/
	public ForceMajeureRequest() {
	}

	/** 
	* @param requestVO
	* @throws SystemException
	*/
	public ForceMajeureRequest(ForceMajeureRequestVO requestVO) {
		log.debug(CLASS_NAME + " : " + "ForceMajeureRequest" + " Entering");
		try {
			populatePK(requestVO);
			populateAttributes(requestVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.debug(CLASS_NAME + " : " + "ForceMajeureRequest" + " Exiting");
	}

	/** 
	* @param requestVO
	*/
	private void populatePK(ForceMajeureRequestVO requestVO) {
		log.debug(CLASS_NAME + " : " + "populatePK" + " Entering");
		ForceMajeureRequestPK pk = new ForceMajeureRequestPK();
		pk.setCompanyCode(requestVO.getCompanyCode());
		pk.setForceMajuereID(requestVO.getForceMajuereID());
		log.debug(CLASS_NAME + " : " + "populatePK" + " Exiting");
	}

	/** 
	* @param requestVO
	*/
	private void populateAttributes(ForceMajeureRequestVO requestVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "populateAttributes" + " Entering");
		setAirportCode(requestVO.getAirportCode());
		setApprovalRemarks(requestVO.getApprovalRemarks());
		setCarrierCode(requestVO.getCarrierCode());
		setConsignmentDocNumber(requestVO.getConsignmentDocNumber());
		setDestinationAirport(requestVO.getDestinationAirport());
		setFilterParameters(requestVO.getFilterParameters());
		setFlightDate(requestVO.getFlightDate().toLocalDateTime());
		setFlightNumber(requestVO.getFlightNumber());
		setFromDate(requestVO.getFromDate().toLocalDateTime());
		setToDate(requestVO.getToDate().toLocalDateTime());
		//TODO: Neo to verify below code
		//setLastUpdatedTime(localDateUtil.getLocalDate(null, true));
		setLastUpdatedUser(requestVO.getLastUpdatedUser());
		setMailID(requestVO.getMailID());
		setMailSeqNumber(requestVO.getMailSeqNumber());
		setOriginAirport(requestVO.getOriginAirport());
		setRequestRemarks(requestVO.getRequestRemarks());
		setSource(requestVO.getSource());
		setStatus(requestVO.getStatus());
		setWeight(requestVO.getWeight().getValue().doubleValue());
		log.debug(CLASS_NAME + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		log.debug(CLASS_NAME + " : " + "constructDAO" + " Entering");
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @param companyCode
	* @param forceMajuereID
	* @param sequenceNumber
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static ForceMajeureRequest find(String companyCode, String forceMajuereID, long sequenceNumber)
			throws FinderException {
		log.debug(CLASS_NAME + " : " + "find" + " Entering");
		ForceMajeureRequestPK pk = new ForceMajeureRequestPK();
		pk.setCompanyCode(companyCode);
		pk.setForceMajuereID(forceMajuereID);
		pk.setSequenceNumber(sequenceNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(ForceMajeureRequest.class, pk);
	}

	/** 
	* @throws RemoveException
	* @throws SystemException
	*/
	public void remove() throws RemoveException {
		log.debug(CLASS_NAME + " : " + "remove" + " Entering");
		PersistenceController.getEntityManager().remove(this);
		log.debug(CLASS_NAME + " : " + "remove" + " Exiting");
	}

	/** 
	* @param filterVO
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public static Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO,
			int pageNumber) {
		log.debug(CLASS_NAME + " : " + "listForceMajeureApplicableMails" + " Entering");
		try {
			return constructDAO().listForceMajeureApplicableMails(filterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @param filterVO
	* @return
	* @throws SystemException
	*/
	public static String saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO) {
		log.debug(CLASS_NAME + " : " + "saveForceMajeureRequest" + " Entering");
		String outParameter = "";
		try {
			outParameter = constructDAO().saveForceMajeureRequest(filterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		log.debug(CLASS_NAME + " : " + "saveForceMajeureRequest" + " Exiting");
		return outParameter;
	}

	/** 
	* @param filterVO
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public static Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO,
			int pageNumber) {
		log.debug(CLASS_NAME + " : " + "listForceMajeureDetails" + " Entering");
		try {
			return constructDAO().listForceMajeureDetails(filterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @param filterVO
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO,
			int pageNumber) {
		log.debug(CLASS_NAME + " : " + "listForceMajeureRequestIds" + " Entering");
		try {
			return constructDAO().listForceMajeureRequestIds(filterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @param filterVO
	* @return
	* @throws SystemException
	*/
	public static String updateForceMajeureRequest(ForceMajeureRequestFilterVO filterVO) {
		log.debug(CLASS_NAME + " : " + "updateForceMajeureRequest" + " Entering");
		try {
			return constructDAO().updateForceMajeureRequest(filterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	* @return
	* @throws SystemException
	*/
	public static Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode,
			String mailBagId, long mailSequenceNumber) {
		log.debug(CLASS_NAME + " : " + "listForceMajeureDetails" + " Entering");
		try {
			return constructDAO().findApprovedForceMajeureDetails(companyCode, mailBagId, mailSequenceNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
