package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.OnwardRouteForSegmentVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Entity
@IdClass(OnwardRouteForSegmentPK.class)
@SequenceGenerator(name = "MALONWRTGSEGSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALONWRTGSEG_SEQ")
@Table(name = "MALONWRTGSEG")
public class OnwardRouteForSegment extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.operations";

	@Id
	@Transient
	private String companyCode;

	@Id
	@Column(name = "ULDNUM")
	private String uldNumber;

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
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMSTSEQ")
	@Column(name = "RTGSERNUM")
	private int routingSerialNumber;

	@Column(name = "ONWCARCOD")
	private String onwardCarrierCode;
	@Column(name = "ONWFLTDAT")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime onwardFlightDate;
	@Column(name = "POU")
	private String pou;
	@Column(name = "ONWCARIDR")
	private int onwardCarrierId;
	@Column(name = "ONWFLTNUM")
	private String onwardFlightNumber;

	/** 
	* @author A-5991
	* @return
	*/
	public OnwardRouteForSegmentVO retrieveVO() {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
		onwardRouteForSegmentVO.setRoutingSerialNumber(this.getRoutingSerialNumber());
		onwardRouteForSegmentVO.setOnwardCarrierCode(getOnwardCarrierCode());
		onwardRouteForSegmentVO.setOnwardCarrierId(getOnwardCarrierId());
		onwardRouteForSegmentVO.setOnwardFlightDate(localDateUtil.getLocalDateTime(getOnwardFlightDate(), null));
		onwardRouteForSegmentVO.setOnwardFlightNumber(getOnwardFlightNumber());
		onwardRouteForSegmentVO.setPou(getPou());
		return onwardRouteForSegmentVO;
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
	* A-5991
	* @param onwardRouteForSegmentVO
	*/
	public void update(OnwardRouteForSegmentVO onwardRouteForSegmentVO) {
		populateAttributes(onwardRouteForSegmentVO);
	}

	public OnwardRouteForSegment() {
	}

	/** 
	* @author A-5991
	* @param uldForSegmentPK
	* @param onwardRouteForSegmentVO
	* @throws SystemException
	*/
	public OnwardRouteForSegment(ULDForSegmentPK uldForSegmentPK, OnwardRouteForSegmentVO onwardRouteForSegmentVO) {
		populatePK(uldForSegmentPK, onwardRouteForSegmentVO);
		populateAttributes(onwardRouteForSegmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	/** 
	* A-5991
	* @param onwardRouteForSegmentVO
	* @param uldForSegmentPK
	*/
	private void populatePK(ULDForSegmentPK uldForSegmentPK, OnwardRouteForSegmentVO onwardRouteForSegmentVO) {

		this.setCompanyCode(uldForSegmentPK.getCompanyCode());
		this.setCarrierId(uldForSegmentPK.getCarrierId());
		this.setFlightNumber(uldForSegmentPK.getFlightNumber());
		this.setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
		this.setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
		this.setUldNumber(uldForSegmentPK.getUldNumber());
	}

	/** 
	* A-5991
	* @param onwardRouteForSegmentVO
	*/
	private void populateAttributes(OnwardRouteForSegmentVO onwardRouteForSegmentVO) {
		onwardCarrierCode = onwardRouteForSegmentVO.getOnwardCarrierCode();
		onwardCarrierId = onwardRouteForSegmentVO.getOnwardCarrierId();
		onwardFlightNumber = onwardRouteForSegmentVO.getOnwardFlightNumber();
		onwardFlightDate = onwardRouteForSegmentVO.getOnwardFlightDate().toLocalDateTime();
		pou = onwardRouteForSegmentVO.getPou();
	}

}
