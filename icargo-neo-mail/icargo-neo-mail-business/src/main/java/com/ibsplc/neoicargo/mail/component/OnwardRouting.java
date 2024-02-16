package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.OnwardRoutingVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-5991
 * @Entity
 * @Table(name = "MALTRKRTG")
 * @Staleable
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(OnwardRoutingPK.class)
@SequenceGenerator(name = "MALFLTCONRTGSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALFLTCONRTG_SEQ")
@Table(name = "MALFLTCONRTG")
public class OnwardRouting extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CONNUM")
	private String containerNumber;
	@Id
	@Column(name = "ASGPRT")
	private String assignmentPort;
	@Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
	@Id
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Id
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Id
	@Column(name = "LEGSERNUM")
	private int legSerialNumber;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALFLTCONRTGSEQ")
	@Column(name = "RTGSERNUM")
	private int routingSerialNumber;

	@Column(name = "POU")
	private String pou;
	@Column(name = "ONWFLTNUM")
	private String onwardFlightNumber;
	@Column(name = "ONWFLTDAT")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime onwardFlightDate;
	@Column(name = "ONWFLTCARCOD")
	private String onwardCarrierCode;
	@Column(name = "ONWFLTCARIDR")
	private int onwardCarrierId;

	/** 
	* @author A-5991
	* @return
	*/
	public OnwardRoutingVO retrieveVO() {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("OnwardRouting" + " : " + "retrieveVO" + " Entering");
		OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
		onwardRoutingVO.setCompanyCode(this.getCompanyCode());
		onwardRoutingVO.setContainerNumber(this.getContainerNumber());
		onwardRoutingVO.setCarrierId(this.getCarrierId());
		onwardRoutingVO.setFlightNumber(this.getFlightNumber());
		onwardRoutingVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		onwardRoutingVO.setLegSerialNumber(this.getLegSerialNumber());
		onwardRoutingVO.setRoutingSerialNumber(this.getRoutingSerialNumber());
		onwardRoutingVO.setAssignmenrPort(this.getAssignmentPort());
		onwardRoutingVO.setOnwardCarrierCode(getOnwardCarrierCode());
		onwardRoutingVO.setOnwardCarrierId(getOnwardCarrierId());
		if (getOnwardFlightDate() != null) {
			onwardRoutingVO.setOnwardFlightDate(localDateUtil.getLocalDateTime(getOnwardFlightDate(), null));
		}
		onwardRoutingVO.setOnwardFlightNumber(getOnwardFlightNumber());
		onwardRoutingVO.setPou(getPou());
		log.debug("OnwardRouting" + " : " + "retrieveVO" + " Exiting");
		return onwardRoutingVO;
	}

	/** 
	* The DefaultConstructor
	*/
	public OnwardRouting() {
	}

	/** 
	* @author a-5991 This method is used to populate the attributes into theEntity
	* @param onwardRoutingVO
	* @throws SystemException
	*/
	public OnwardRouting(OnwardRoutingVO onwardRoutingVO) {
		populatePK(onwardRoutingVO);
		populateAttributes(onwardRoutingVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @author a-5991 This method is used to populate the pk details
	* @param onwardRoutingVO
	* @throws SystemException
	*/
	private void populatePK(OnwardRoutingVO onwardRoutingVO) {
		log.debug("INSIDE THE POPULATE PK" + " : " + "ROUTINGPK" + " Entering");
		log.debug("INSIDE THE POPULATE PK" + " : " + "ROUTINGPK" + " Entering");
		this.setAssignmentPort(onwardRoutingVO.getAssignmenrPort());
		this.setCompanyCode(onwardRoutingVO.getCompanyCode());
		this.setContainerNumber(onwardRoutingVO.getContainerNumber());
		this.setCarrierId(onwardRoutingVO.getCarrierId());
		this.setFlightNumber(onwardRoutingVO.getFlightNumber());
		this.setFlightSequenceNumber(onwardRoutingVO.getFlightSequenceNumber());
		this.setLegSerialNumber(onwardRoutingVO.getLegSerialNumber());
		this.setRoutingSerialNumber(onwardRoutingVO.getRoutingSerialNumber());
	}

	/** 
	* @author a-5991 This method is used to populate the Attributes
	* @param onwardRoutingVO
	* @throws SystemException
	*/
	private void populateAttributes(OnwardRoutingVO onwardRoutingVO) {
		log.debug("INSIDE THE POPULATE ATTRIBUTES" + " : " + "ROUTINGPK" + " Entering");
		this.setOnwardCarrierCode(onwardRoutingVO.getOnwardCarrierCode());
		this.setOnwardCarrierId(onwardRoutingVO.getOnwardCarrierId());
		if (onwardRoutingVO.getOnwardFlightDate() != null) {
			this.setOnwardFlightDate(onwardRoutingVO.getOnwardFlightDate().toLocalDateTime());
		}
		this.setOnwardFlightNumber(onwardRoutingVO.getOnwardFlightNumber());
		this.setPou(onwardRoutingVO.getPou());
	}

	/** 
	* @author a-5991 This method is used to remove the business Objectsinstance
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
