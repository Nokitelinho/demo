package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(RoutingInConsignmentPK.class)
@SequenceGenerator(name = "MALCSGRTGSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCSGRTG_SEQ")
@Table(name = "MALCSGRTG")
public class RoutingInConsignment extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CSGDOCNUM")
	private String consignmentNumber;
	@Id
	@Column(name = "POACOD")
	private String paCode;
	@Id
	@Column(name = "CSGSEQNUM")
	private int consignmentSequenceNumber;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCSGRTGSEQ")
	@Column(name = "RTGSERNUM")
	private int routingSerialNumber;
	@Column(name = "POU")
	private String pou;
	@Column(name = "FLTNUM")
	private String onwardFlightNumber;
	@Column(name = "FLTDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime onwardFlightDate;
	@Column(name = "FLTCARCOD")
	private String onwardCarrierCode;
	@Column(name = "FLTCARIDR")
	private int onwardCarrierId;
	@Column(name = "POL")
	private String pol;
	@Column(name = "FLTSEQNUM")
	private long onwardCarrierSeqNum;
	@Column(name = "RMK")
	private String remarks;
	@Column(name = "LEGSTA")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime legSta;
	@Column(name = "TRSSTG")
	private String transportStageQualifier;

	public RoutingInConsignment(){
	}
	/** 
	* @author A-5991
	* @param routingInConsignmentVO
	* @throws SystemException
	*/
	public RoutingInConsignment(RoutingInConsignmentVO routingInConsignmentVO) {
		log.debug("RoutingInConsignment" + " : " + "RoutingInConsignment" + " Entering");
		populatePk(routingInConsignmentVO);
		populateAttributes(routingInConsignmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("RoutingInConsignment" + " : " + "RoutingInConsignment" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param routingInConsignmentVO
	*/
	private void populatePk(RoutingInConsignmentVO routingInConsignmentVO) {
		log.debug("RoutingInConsignment" + " : " + "populatePk" + " Entering");
		this.setCompanyCode(routingInConsignmentVO.getCompanyCode());
		this.setConsignmentNumber(routingInConsignmentVO.getConsignmentNumber());
		this.setConsignmentSequenceNumber(routingInConsignmentVO.getConsignmentSequenceNumber());
		this.setPaCode(routingInConsignmentVO.getPaCode());

		log.debug("RoutingInConsignment" + " : " + "populatePk" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param routingInConsignmentVO
	*/
	private void populateAttributes(RoutingInConsignmentVO routingInConsignmentVO) {
		log.debug("RoutingInConsignment" + " : " + "populateAttributes" + " Entering");
		this.setOnwardCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
		this.setOnwardCarrierId(routingInConsignmentVO.getOnwardCarrierId());
		if (routingInConsignmentVO.getOnwardFlightDate() != null) {
			this.setOnwardFlightDate(routingInConsignmentVO.getOnwardFlightDate().toLocalDateTime());
		}
		this.setOnwardFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
		this.setPou(routingInConsignmentVO.getPou());
		this.setPol(routingInConsignmentVO.getPol());
		this.setOnwardCarrierSeqNum(routingInConsignmentVO.getOnwardCarrierSeqNum());
		if (routingInConsignmentVO.getRemarks() != null) {
			this.setRemarks(routingInConsignmentVO.getRemarks());
		}
		if (routingInConsignmentVO.getScheduledArrivalDate() != null) {
			this.setLegSta(routingInConsignmentVO.getScheduledArrivalDate().toLocalDateTime());
		}
		if (routingInConsignmentVO.getTransportStageQualifier() != null) {
			this.setTransportStageQualifier(routingInConsignmentVO.getTransportStageQualifier());
		}
		log.debug("RoutingInConsignment" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param routingInConsignmentVO
	*/
	public void update(RoutingInConsignmentVO routingInConsignmentVO) {
		log.debug("RoutingInConsignment" + " : " + "update" + " Entering");
		populateAttributes(routingInConsignmentVO);
		log.debug("RoutingInConsignment" + " : " + "update" + " Exiting");
	}

	/** 
	* @author A-5991
	* @throws SystemException
	*/
	public void remove() {
		log.debug("RoutingInConsignment" + " : " + "remove" + " Entering");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
		log.debug("RoutingInConsignment" + " : " + "remove" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param routingInConsignmentVO
	* @return RoutingInConsignment
	* @throws SystemException
	*/
	public static RoutingInConsignment find(RoutingInConsignmentVO routingInConsignmentVO) {
		log.debug("RoutingInConsignment" + " : " + "find" + " Entering");
		RoutingInConsignment routingInConsignment = null;
		RoutingInConsignmentPK routingInConsignmentPk = new RoutingInConsignmentPK();
		routingInConsignmentPk.setCompanyCode(routingInConsignmentVO.getCompanyCode());
		routingInConsignmentPk.setConsignmentNumber(routingInConsignmentVO.getConsignmentNumber());
		routingInConsignmentPk.setConsignmentSequenceNumber(routingInConsignmentVO.getConsignmentSequenceNumber());
		routingInConsignmentPk.setPaCode(routingInConsignmentVO.getPaCode());
		routingInConsignmentPk.setRoutingSerialNumber(routingInConsignmentVO.getRoutingSerialNumber());
		try {
			routingInConsignment = PersistenceController.getEntityManager().find(RoutingInConsignment.class,
					routingInConsignmentPk);
		} catch (FinderException finderException) {
			log.error("  Finder Exception ");
			throw new SystemException(finderException.getErrorCode(), finderException.getMessage(), finderException);
		}
		return routingInConsignment;
	}

}
