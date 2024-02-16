package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.OffloadDetailVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author A-5991 The entity containing the Offload Details
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailOffloadDetailPK.class)
@SequenceGenerator(name = "MALOFLDTLSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALOFLDTL_SEQ")
@Table(name = "MALOFLDTL")
public class MailOffloadDetail extends BaseEntity implements Serializable {

	private static final String MODULE = "mail.operations";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "FLTCARIDR ")
	private int carrierId;
	@Id
	@Column(name = "FLTNUM ")
	private String flightNumber;
	@Id
	@Column(name = "FLTSEQNUM ")
	private long flightSequenceNumber;
	@Id
	@Column(name = "SEGSERNUM ")
	private int segmentSerialNumber;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALOFLDTLSEQ")
	@Column(name = "SERNUM ")
	private int offloadSerialNumber;

	@Column(name = "OFLDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime offloadedDate;
	@Column(name = "OFLUSR")
	private String offloadUser;
	@Column(name = "OFLBAG")
	private int offloadedBags;
	@Column(name = "OFLWGT")
	private double offloadedWeight;
	@Column(name = "RMK")
	private String offloadRemarks;
	@Column(name = "OFLRSNCOD")
	private String offloadReasonCode;
	@Column(name = "FLTCARCOD")
	private String carrierCode;
	@Column(name = "OFLTYP")
	private String offloadType;
	@Column(name = "CONNUM")
	private String containerNumber;
	@Column(name = "ARPCOD")
	private String airportCode;
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;

	public MailOffloadDetail(OffloadDetailVO offloadDetailVO) {
		log.debug("OffloadDetail" + " : " + "Create Instance of the Entity" + " Entering");
		populatePK(offloadDetailVO);
		populateAttributes(offloadDetailVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @param offloadDetailVO
	*/
	private void populatePK(OffloadDetailVO offloadDetailVO) {
		log.debug("OffloadDetail" + " : " + "populatePK" + " Entering");
		log.debug("" + "The OFFLOAD DETAIL VO IS " + " " + offloadDetailVO);
		this.setCompanyCode(offloadDetailVO.getCompanyCode());
		this.setCarrierId(offloadDetailVO.getCarrierId());
		this.setFlightNumber(offloadDetailVO.getFlightNumber());
		this.setFlightSequenceNumber(offloadDetailVO.getFlightSequenceNumber());
		this.setSegmentSerialNumber(offloadDetailVO.getSegmentSerialNumber());
		this.setOffloadSerialNumber(offloadDetailVO.getOffloadSerialNumber());
	}

	/** 
	* @param offloadDetailVO
	*/
	private void populateAttributes(OffloadDetailVO offloadDetailVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("OffloadDetail" + " : " + "poipulateAttributes" + " Entering");
		this.setAirportCode(offloadDetailVO.getAirportCode());
		this.setCarrierCode(offloadDetailVO.getCarrierCode());
		this.setContainerNumber(offloadDetailVO.getContainerNumber());
		this.setOffloadedBags(offloadDetailVO.getOffloadedBags());
		this.setOffloadedDate(
				offloadDetailVO.getOffloadedDate() != null ? offloadDetailVO.getOffloadedDate().toLocalDateTime()
						: localDateUtil.getLocalDate(offloadDetailVO.getAirportCode(), true).toLocalDateTime());
		if (offloadDetailVO.getOffloadedWeight() != null) {
			this.setOffloadedWeight(offloadDetailVO.getOffloadedWeight().getValue().doubleValue());
		}
		this.setMailSequenceNumber(offloadDetailVO.getMailSequenceNumber());
		this.setOffloadReasonCode(offloadDetailVO.getOffloadReasonCode());
		log.debug("" + "THE REMARKS IN OFFLOAD DETAIL IS " + " " + offloadDetailVO.getOffloadRemarks());
		this.setOffloadRemarks(offloadDetailVO.getOffloadRemarks());
		this.setOffloadType(offloadDetailVO.getOffloadType());
		this.setOffloadUser(offloadDetailVO.getOffloadUser());
		this.setLastUpdatedUser(offloadDetailVO.getOffloadUser());
	}
	/**
	 * @author a-5991 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */
	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @return
	 * @throws SystemException
	 */
	public static String findOffloadReasonForMailbag(String companyCode, String receptacleID) {
		try {
			return constructDAO().findOffloadReasonForMailbag(companyCode, receptacleID);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param containerNumber
	 * @return
	 * @throws SystemException
	 */
	public static String findOffloadReasonForULD(String companyCode, String containerNumber) {
		try {
			return constructDAO().findOffloadReasonForULD(companyCode, containerNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}


}
