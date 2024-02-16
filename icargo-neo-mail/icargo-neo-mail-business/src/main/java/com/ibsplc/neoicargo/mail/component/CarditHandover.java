package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.vo.CarditHandoverInformationVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
@Setter
@Getter
@Entity
@IdClass(CarditHandoverPK.class)
@SequenceGenerator(name = "MALCDTHNDOVRSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCDTHNDOVR_SEQ")
@Table(name = "MALCDTHNDOVR")
public class CarditHandover extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCDTHNDOVRSEQ")
	@Column(name = "HNDSERNUM")
	private int handoverSerialNumber;

	@Column(name = "CDTTYP")
	private String carditType;
	@Column(name = "HNDORGLOC")
	private String handoverOriginIdentifier;
	@Column(name = "HNDORGNAM")
	private String handoverOriginName;
	@Column(name = "HNDORGQLF")
	private String handoverOriginCodeListQualifier;
	@Column(name = "HNDORGAGT")
	private String handoverOriginCodeListAgency;
	@Column(name = "HNDDSTLOC")
	private String handoverDestnIdentifier;
	@Column(name = "HNDDSTNAM")
	private String handoverDestnName;
	@Column(name = "HNDDSTQLF")
	private String handoverDestnCodeListQualifier;
	@Column(name = "HNDDSTAGT")
	private String handoverDestnCodeListAgency;
	@Column(name = "CLNDAT")
	private LocalDateTime collectionDate;
	@Column(name = "DLVDAT")
	private LocalDateTime deliveryDate;

	public CarditHandover() {
	}

	/** 
	* @author A-3227
	* @throws SystemException
	*/
	public CarditHandover(CarditHandoverPK carditHandovrPK, CarditHandoverInformationVO carditHandoverInfo) {
		populatePK(carditHandovrPK);
		populateAttributes(carditHandoverInfo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	/** 
	* @author A-3227
	* @param carditHndovrPK
	* @throws SystemException
	*/
	private void populatePK(CarditHandoverPK carditHndovrPK) {
		this.setCompanyCode(carditHndovrPK.getCompanyCode());
		this.setCarditKey(carditHndovrPK.getCarditKey());
		this.setHandoverSerialNumber(carditHndovrPK.getHandoverSerialNumber());
	}

	/** 
	* @author A-3227
	* @param carditHndovrInfo
	*/
	private void populateAttributes(CarditHandoverInformationVO carditHndovrInfo) {
		this.handoverDestnCodeListAgency = carditHndovrInfo.getHandoverDestnCodeListAgency();
		this.handoverDestnCodeListQualifier = carditHndovrInfo.getHandoverDestnCodeListQualifier();
		this.handoverDestnIdentifier = carditHndovrInfo.getHandoverDestnLocationIdentifier();
		this.handoverDestnName = carditHndovrInfo.getHandoverDestnLocationName();
		this.handoverOriginCodeListAgency = carditHndovrInfo.getHandoverOriginCodeListAgency();
		this.handoverOriginCodeListQualifier = carditHndovrInfo.getHandoverOriginCodeListQualifier();
		this.handoverOriginIdentifier = carditHndovrInfo.getHandoverOriginLocationIdentifier();
		this.handoverOriginName = carditHndovrInfo.getHandoverOriginLocationName();
		this.carditType = carditHndovrInfo.getCarditType();
		if (carditHndovrInfo.getOriginCutOffPeriod() != null) {
			setCollectionDate(carditHndovrInfo.getOriginCutOffPeriod().toLocalDateTime());
		}
		if (carditHndovrInfo.getDestinationCutOffPeriod() != null) {
			setDeliveryDate(carditHndovrInfo.getDestinationCutOffPeriod().toLocalDateTime());
		}
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
