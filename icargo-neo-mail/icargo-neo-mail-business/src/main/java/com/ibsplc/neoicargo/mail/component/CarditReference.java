package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditReferenceInformationVO;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(CarditReferencePK.class)
@Table(name = "MALCDTREF")
@SequenceGenerator(name = "MALCDTREFSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCDTREF_SEQ")
public class CarditReference extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@Column(name = "REFSERNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCDTREFSEQ")
	private int referrenceSerialNumber;

	@Column(name = "CDTTYP")
	private String carditType;
	@Column(name = "REFQLF")
	private String referenceQualifier;
	@Column(name = "REFNUM")
	private String referrenceNumber;

	public CarditReference() {
	}
	/** 
	* @author A-3227
	* @throws SystemException
	*/
	public CarditReference(CarditReferencePK carditRefPK, CarditReferenceInformationVO carditReferenceInfo) {
		populatePK(carditRefPK);
		populateAttributes(carditReferenceInfo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}
	/** 
	* @author A-3227
	* @throws SystemException
	*/
	private void populatePK(CarditReferencePK carditReferPK) {

		this.setCompanyCode(carditReferPK.getCompanyCode());
		this.setCarditKey(carditReferPK.getCarditKey());
		this.setReferrenceSerialNumber(carditReferPK.getReferrenceSerialNumber());
	}
	/** 
	* @author A-3227
	*/
	private void populateAttributes(CarditReferenceInformationVO carditReferenceInfo) {
		this.carditType = carditReferenceInfo.getCarditType();
		this.referenceQualifier = carditReferenceInfo.getTransportContractReferenceQualifier();
		this.referrenceNumber = carditReferenceInfo.getConsignmentContractReferenceNumber();
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
