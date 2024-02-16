package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.vo.CarditTransportationVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * This entity contains the transportation information of a cardit
 * @author A-1739
 */
@Setter
@Getter
@Entity
@IdClass(CarditTransportationPK.class)
@SequenceGenerator(name = "MALCDTTRTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCDTTRT_SEQ")
@Table(name = "MALCDTTRT")
public class CarditTransportation  extends BaseEntity implements Serializable {
	private static final String MAILTRACKING_DEFAULTS = "mailtracking.defaults";
	/** 
	* Departure place of flight
	*/
	@Column(name = "ORGCOD")
	private String departurePort;
	/** 
	* Departure time of flight
	*/
	@Column(name = "DEPTIM")
	private LocalDateTime departureTime;
	/** 
	* Departure time in UTC of flight
	*/
	@Column(name = "DEPDATUTC")
	private LocalDateTime departureTimeUTC;
	/** 
	* carrier identification
	*/
	@Column(name = "CARIDR")
	private int carrierID;
	/** 
	* This field is a code list qualifier indicating carrier code
	*/
	@Column(name = "CARCOD")
	private String carrierCode;
	/** 
	* carrier name
	*/
	@Column(name = "CARNAM")
	private String carrierName;
	/** 
	* This field indicates code list responsible agency for place of arrival
	*/
	@Column(name = "DSTCODLSTAGY")
	private String arrivalCodeListAgency;
	/** 
	* This field indicates code list responsible agency for place of departure
	*/
	@Column(name = "ORGCODLSTAGY")
	private String departureCodeListAgency;
	/** 
	* This field indicates code list responsible agency for carrier eg: 3 -IATA, 139 - UPU
	*/
	@Column(name = "CARCODLSTAGY")
	private String agencyForCarrierCodeList;
	/** 
	* This field indicates conveyance reference number
	*/
	@Column(name = "CNVREF")
	private String conveyanceReference;
	/** 
	* This field indicates mode of transport eg: air, road , rail
	*/
	@Column(name = "TRTMOD")
	private String modeOfTransport;
	/** 
	* This field indicates transport stage qualifier eg: 10 - pre carriage transport eg: 20 - main carriage transport
	*/
	@Column(name = "TRTSTGQLF")
	private String transportStageQualifier;
	/** 
	* The pk of this flight
	*/
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Column(name = "LEGSERNUM")
	private int legSerialNumber;
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	@Column(name = "ARRDAT")
	private LocalDateTime arrivalDate;
	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@Column(name = "DSTCOD")
	private String arrivalPort;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCDTTRTSEQ")
	@Column(name = "TRTSERNUM")
	private long sequenceNumber;

	@Column(name = "CNTRFF")
	private String contractReference;

	/** 
	* Empty constr for hibernate
	*/
	public CarditTransportation() {
	}

	/** 
	* @author A-1739
	* @param transportationVO
	* @throws SystemException
	*/
	public CarditTransportation(CarditTransportationPK transportPK, CarditTransportationVO transportationVO) {
		populatePk(transportPK);
		populateAttributes(transportationVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	/** 
	* @author A-1739
	*/
	private void populatePk(CarditTransportationPK carditTransportPK) {

		this.setCompanyCode(carditTransportPK.getCompanyCode());
		this.setCarditKey(carditTransportPK.getCarditKey());
		this.setArrivalPort(carditTransportPK.getArrivalPort());
	}

	/** 
	* @author A-1739
	* @param transportationVO
	*/
	private void populateAttributes(CarditTransportationVO transportationVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		setAgencyForCarrierCodeList(transportationVO.getAgencyForCarrierCodeList());
		setArrivalCodeListAgency(transportationVO.getArrivalCodeListAgency());
		if(transportationVO.getArrivalDate()!=null)
		setArrivalDate(transportationVO.getArrivalDate().toLocalDateTime());
		setCarrierCode(transportationVO.getCarrierCode());
		setCarrierID(transportationVO.getCarrierID());
		setCarrierName(transportationVO.getCarrierName());
		setConveyanceReference(transportationVO.getConveyanceReference());
		setDepartureCodeListAgency(transportationVO.getDepartureCodeListAgency());
		setDeparturePort(transportationVO.getDeparturePort());
		if (departurePort != null && transportationVO.getDepartureTime() != null) {
			setDepartureTime(transportationVO.getDepartureTime().toLocalDateTime());
		}
		setModeOfTransport(transportationVO.getModeOfTransport());
		setTransportStageQualifier(transportationVO.getTransportStageQualifier());
		setFlightNumber(transportationVO.getFlightNumber());
		setFlightSequenceNumber(transportationVO.getFlightSequenceNumber());
		setLegSerialNumber(transportationVO.getLegSerialNumber());
		setSegmentSerialNumber(transportationVO.getSegmentSerialNum());
		if (getDepartureTime() != null && transportationVO.getDepartureTime() != null) {
			//TODO: Neo to correct below code
			//setDepartureTimeUTC(localDateUtil.toUTCTime(transportationVO.getDepartureTime()));
		}
		setContractReference(transportationVO.getContractReference());
	}

	/** 
	* @author A-3227
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

}
