package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** 
 * @author A-3109 This class represents transfer manifest details for transfermanifest reports.
 */
@Setter
@Getter
@Entity
@IdClass(TransferManifestDSNPK.class)
@Table(name = "MALTRFMFTDTL")
public class TransferManifestDSN extends BaseEntity implements Serializable {

	private static final String PRODUCT_NAME = "mail.operations";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "TRFMFTIDR")
	private String transferManifestId;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;

	/** 
	* The bagCount
	*/
	@Column(name = "BAGCNT")
	private int bagCount;
	/** 
	* The containerNumber
	*/
	@Column(name = "CONNUM")
	private String containerNumber;
	@Column(name = "DSNIDR")
	private String dsn;
	@Column(name = "TRFSTA")
	private String transferStatus;
	/**
	* The weight
	*/
	@Column(name = "BAGWGT")
	private double weight;

	public TransferManifestDSN() {
	}
	/**
	* @author a-2553 This method is used to populate the Entity
	* @throws SystemException
	*/
	public TransferManifestDSN(DSNVO dsnVO, TransferManifestPK transferManifestPK) {
		populatePK(dsnVO, transferManifestPK);
		populateAttributes(dsnVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @author a-2553 This method is used to populate the PK
	* @throws SystemException
	*/
	private void populatePK(DSNVO dsnVO, TransferManifestPK transferManifestPK) {
		this.setCompanyCode(transferManifestPK.getCompanyCode());
		this.setTransferManifestId(transferManifestPK.getTransferManifestId());
		this.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
	}

	/** 
	* @author a-2553 This method is used to populate the Attributes
	* @param dsnVO
	* @throws SystemException
	*/
	private void populateAttributes(DSNVO dsnVO) {
		this.setBagCount(dsnVO.getBags());
		this.setContainerNumber(dsnVO.getContainerNumber());
		this.setWeight(dsnVO.getWeight().getRoundedValue().doubleValue());
		StringBuilder dsn = new StringBuilder();
		dsn.append(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice())
				.append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass()).append(dsnVO.getYear())
				.append(dsnVO.getDsn());
		this.setDsn(dsn.toString());
		if ("TRAEND".equals(dsnVO.getStatus())) {
			this.setTransferStatus("TRFEND");
		} else {
			this.setTransferStatus("TRFINT");
		}
		setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
	}

	public static TransferManifestDSN find(TransferManifestDSNPK transferManifestDSNPK) throws FinderException {
		return PersistenceController.getEntityManager().find(TransferManifestDSN.class, transferManifestDSNPK);
	}
}
