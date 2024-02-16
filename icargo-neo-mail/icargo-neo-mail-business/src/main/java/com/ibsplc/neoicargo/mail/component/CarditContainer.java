package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditContainerVO;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** 
 * This entity persists the information of each container in a cardit
 * @author A-1739
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(CarditContainerPK.class)
@Table(name = "MALCDTCON")
public class CarditContainer extends BaseEntity implements Serializable {
	/** 
	* Equipment qualifier code
	*/
	@Column(name = "EQPQLF")
	private String equipmentQualifier;
	/** 
	* Code list responsible agency coded
	*/
	@Column(name = "NUMCODLSTAGY")
	private String codeListResponsibleAgency;
	/** 
	* Equipment size and type, identification
	*/
	@Column(name = "TYPCOD")
	private String containerType;
	/** 
	* Code list responsible agency , coded
	*/
	@Column(name = "TYPCODLSTAGY")
	private String typeCodeListResponsibleAgency;
	/** 
	* Measurement dimensions, coded-Unit net weight
	*/
	@Column(name = "MMTDMN")
	private String measurementDimension;
	/** 
	* container weight If value is null default value is set as -1
	*/
	@Column(name = "NETWGT")
	private double containerWeight;
	/** 
	* Container seal number
	*/
	@Column(name = "CONSELNUM")
	private String sealNumber;
	/** 
	* Completion Status
	*/
	@Column(name = "CMPSTA")
	private String completionStatus;
	/** 
	* The CDTTYP : CarditType (Message Function) 
	*/
	@Column(name = "CDTTYP")
	private String carditType;
	/** 
	* Container Journey Identifier
	*/
	@Column(name = "CONJRNIDR")
	private String containerJourneyIdentifier;

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@Column(name = "CONNUM")
	private String containerNumber;

	public CarditContainer() {
	}

	public CarditContainer(CarditPK carditPK, CarditContainerVO carditContainerVO) {
		populatePK(carditPK, carditContainerVO);
		populateAttributes(carditContainerVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @param carditPK
	* @param carditContainerVO
	*/
	private void populatePK(CarditPK carditPK, CarditContainerVO carditContainerVO) {

		this.setCompanyCode(carditPK.getCompanyCode());
		this.setCarditKey(carditPK.getCarditKey());
		this.setContainerNumber(carditContainerVO.getContainerNumber());
	}

	/** 
	* @param carditContainerVO
	*/
	private void populateAttributes(CarditContainerVO carditContainerVO) {
		setCodeListResponsibleAgency(carditContainerVO.getCodeListResponsibleAgency());
		setContainerType(carditContainerVO.getContainerType());
		if (carditContainerVO.getContainerWeight() != null) {
			//TODO:Neo to verify the value
			setContainerWeight(carditContainerVO.getContainerWeight().getValue().doubleValue());
		}
		setEquipmentQualifier(carditContainerVO.getEquipmentQualifier());
		setMeasurementDimension(carditContainerVO.getMeasurementDimension());
		setSealNumber(carditContainerVO.getSealNumber());
		setTypeCodeListResponsibleAgency(carditContainerVO.getTypeCodeListResponsibleAgency());
		setCompletionStatus("N");
		setCarditType(carditContainerVO.getCarditType());
		setContainerJourneyIdentifier(carditContainerVO.getContainerJourneyIdentifier());
	}

	/** 
	* @author A-3227
	* @param carditContPK
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static CarditContainer find(CarditContainerPK carditContPK) throws FinderException {
		return PersistenceController.getEntityManager().find(CarditContainer.class, carditContPK);
	}


	/** 
	* @author a-1936This method is used to find the Cardit Container //
	* @param companyCode
	* @param carditKey
	* @param containerNumber
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static CarditContainer find(String companyCode, String carditKey, String containerNumber)
			throws FinderException {
		CarditContainerPK carditContPK = new CarditContainerPK();
		carditContPK.setCompanyCode(companyCode);
		carditContPK.setCarditKey(carditKey);
		carditContPK.setContainerNumber(containerNumber);
		log.debug("The Company Code " + carditContPK.getCompanyCode());
		log.debug("The CarditKey " + carditContPK.getCarditKey());
		log.debug("The ContainerNumber " + carditContPK.getContainerNumber());
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(CarditContainer.class, carditContPK);
	}

	/** 
	* @return the containerJourneyIdentifier
	*/
	public String getContainerJourneyIdentifier() {
		return containerJourneyIdentifier;
	}

	/** 
	* @param containerJourneyIdentifier the containerJourneyIdentifier to set
	*/
	public void setContainerJourneyIdentifier(String containerJourneyIdentifier) {
		this.containerJourneyIdentifier = containerJourneyIdentifier;
	}
}
