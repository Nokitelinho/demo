package com.ibsplc.neoicargo.mail.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import com.ibsplc.neoicargo.mail.vo.TransferManifestVO;
import com.ibsplc.neoicargo.mail.vo.TransferManifestFilterVO;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-5991 This class represents transfer manifest details for transfermanifest reports.
 */
@Setter
@Getter
@Entity
@IdClass(TransferManifestPK.class)
@Table(name = "MALTRFMFT")
public class TransferManifest extends BaseEntity implements Serializable {

	private static final String PRODUCT_NAME = "mail.operations";
	private static final String MODULE = "mail.operations";
	private static final String TRAEND = "TRAEND";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name="TRFMFTIDR")
	private String transferManifestId;

	@Column(name = "ARPCOD")
	private String airPort;
	@Column(name = "TRFDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime transferDate;
	@Column(name = "ONWCARCOD")
	private String transferredToCarrierCode;
	@Column(name = "ONWFLTNUM")
	private String transferredToFltNumber;
	@Column(name = "FRMFLTNUM")
	private String transferredFromFltNumber;
	@Column(name = "FRMFLTDAT")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime fromFlightDate;
	@Column(name = "ONWFLTDAT")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime toFlightDate;
	@Column(name = "FRMCARCOD")
	private String transferredFromCarrierCode;

	@Column(name = "FRMFLTSEQNUM")
	private long transferredfrmFltSeqNum;
	@Column(name = "FRMSEGSERNUM")
	private int transferredfrmSegSerNum;
	@Column(name = "TRFSTA")
	private String transferStatus;

	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "TRFMFTIDR", referencedColumnName = "TRFMFTIDR", insertable = false, updatable = false) })
	private Set<TransferManifestDSN> transferManifestDSN;

	/** 
	* @author a-5991 This method is used to insert the new record in the Entity
	* @param transferManifestVO
	* @throws SystemException
	*/
	public TransferManifest(TransferManifestVO transferManifestVO) {
		populatePK(transferManifestVO);
		populateAttributes(transferManifestVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		populateChildren(transferManifestVO);
	}

    public TransferManifest() {

    }

	/** 
	* Method		:	TransferManifest.populateChildren Added by 	:	A-4809 on Sep 2, 2016 Used for 	: Parameters	:	@param transferManifestVO  Return type	: 	void
	* @throws SystemException 
	*/
	public void populateChildren(TransferManifestVO transferManifestVO) {
		Collection<DSNVO> dsnVOs = null;
		if (transferManifestVO.getDsnVOs() != null && transferManifestVO.getDsnVOs().size() > 0) {
			dsnVOs = transferManifestVO.getDsnVOs();
			boolean status = false;
			if (TRAEND.equals(transferManifestVO.getStatus())) {
				status = true;
			}

			populateDSNs(dsnVOs, status, this.companyCode, this.transferManifestId);
		}
	}

	/** 
	* Method		:	TransferManifest.populateDSNs Added by 	:	A-4809 on Sep 2, 2016 Used for 	: Parameters	:	@param dsnVOs Parameters	:	@param transferMainfestPK  Return type	: 	void
	* @throws SystemException 
	*/
	public void populateDSNs(Collection<DSNVO> dsnVOs, boolean status, String companyCode, String transferManifestId) {
		TransferManifestPK transferManifestPK = new TransferManifestPK();
		transferManifestPK.setCompanyCode(companyCode);
		transferManifestPK.setTransferManifestId(transferManifestId);
		for (DSNVO dsnVO : dsnVOs) {
			if (status) {
				dsnVO.setStatus(TRAEND);
			}
			TransferManifestDSN trfManifestDSN = new TransferManifestDSN(dsnVO, transferManifestPK);
			if (getTransferManifestDSN() == null) {
				setTransferManifestDSN(new HashSet<TransferManifestDSN>());
			}
			getTransferManifestDSN().add(trfManifestDSN);
		}
	}

	/** 
	* @author a-5991 This method is used to populate the PK
	* @param transferManifestVO
	* @throws SystemException
	*/
	private void populatePK(TransferManifestVO transferManifestVO) {
		this.setCompanyCode(transferManifestVO.getCompanyCode());
		this.setTransferManifestId(transferManifestVO.getTransferManifestId());
	}

	/** 
	* @author a-5991 This method is used to populate the Attributes
	* @param transferManifestVO
	* @throws SystemException
	*/
	private void populateAttributes(TransferManifestVO transferManifestVO) {
		this.setAirPort(transferManifestVO.getAirPort());
		if (transferManifestVO.getTransferredDate() != null) {
			this.setTransferDate(transferManifestVO.getTransferredDate());
		}
		this.setTransferredToCarrierCode(transferManifestVO.getTransferredToCarrierCode());
		if (transferManifestVO.getTransferredToFltNumber() != null) {
			this.setTransferredToFltNumber(transferManifestVO.getTransferredToFltNumber());
		}
		this.setTransferredFromCarrierCode(transferManifestVO.getTransferredFromCarCode());
		this.setTransferredFromFltNumber(transferManifestVO.getTransferredFromFltNum());
		if (transferManifestVO.getFromFltDat() != null) {
			this.setFromFlightDate(transferManifestVO.getFromFltDat());
		}
		if (transferManifestVO.getToFltDat() != null) {
			this.setToFlightDate(transferManifestVO.getToFltDat());
		}
		this.setTransferredfrmFltSeqNum(transferManifestVO.getTransferredfrmFltSeqNum());
		this.setTransferredfrmSegSerNum(transferManifestVO.getTransferredfrmSegSerNum());
		if (transferManifestVO.getStatus() != null && transferManifestVO.getStatus().equals(TRAEND)) {
			this.setTransferStatus("TRFEND");
		} else {
			this.setTransferStatus("TRFINT");
		}
	}

	public static TransferManifest find(TransferManifestPK transferManifestPK) throws FinderException {
		return PersistenceController.getEntityManager().find(TransferManifest.class, transferManifestPK);
	}

	public void setTransferDate(ZonedDateTime transferDate) {
		if (Objects.nonNull(transferDate)) {
			this.transferDate = transferDate.toLocalDateTime();
		}
	}

	public void setFromFlightDate(ZonedDateTime fromFlightDate) {
		if (Objects.nonNull(fromFlightDate)) {
			this.fromFlightDate = fromFlightDate.toLocalDateTime();
		}
	}

	public void setToFlightDate(ZonedDateTime toFlightDate) {
		if (Objects.nonNull(toFlightDate)) {
			this.toFlightDate = toFlightDate.toLocalDateTime();
		}
	}
	/**
	 * @return
	 * @throws SystemException
	 */
	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
	/**
	 * @author a-1936This method is used to find the Transfer Manifest for the Different Transactions
	 * @param tranferManifestFilterVo
	 * @return
	 * @throws SystemException
	 */
	public static Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo) {
		try {
			return constructDAO().findTransferManifest(tranferManifestFilterVo);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode());
		}
	}
	public static TransferManifestVO generateTransferManifestReport(String companyCode,String transferManifestId)
			throws SystemException{
		try {
			return constructDAO().generateTransferManifestReport(companyCode,transferManifestId);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

}
