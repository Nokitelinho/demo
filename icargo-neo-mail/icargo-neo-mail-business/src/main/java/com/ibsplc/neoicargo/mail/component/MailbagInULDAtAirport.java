package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagInULDAtAirportVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.component.MailbagInULDAtAirportPK;
import com.ibsplc.neoicargo.mail.component.MailbagInULDAtAirport;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Entity
@Table(name = "MALARPULDDTL")
@IdClass(MailbagInULDAtAirportPK.class)
public class MailbagInULDAtAirport extends BaseEntity implements Serializable {

    @Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
	@Id
	@Column(name = "ULDNUM")
	private String uldNumber;
	@Id
	@Column(name = "ARPCOD")
	private String airportCode;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;

	@Column(name = "CONNUM")
	private String containerNumber;
	@Column(name = "DMGFLG")
	private String damageFlag;
	@Column(name = "SCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime scannedDate;

	@Column(name = "FRMCARCOD")
	private String transferFromCarrier;

	@Column(name = "DOCOWRIDR")
	private long documentNumber;
	@Column(name = "MSTDOCNUM")
	private String masterDocumentNumber;
	@Column(name = "DUPNUM")
	private int duplicateNumber;
	@Column(name = "SEQNUM")
	private int sequenceNumber;
	@Column(name = "DOCOWRCOD")
	private String documentOwnerCode;
	@Column(name = "ACPBAG")
	private int acceptedBags;
	@Column(name = "ACPWGT")
	private double acceptedWeight;
	@Column(name = "STDBAG")
	private int statedBags;
	@Column(name = "STDWGT")
	private double statedWeight;

	/**
	* A-5991
	* @param mailbagInAirportPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailbagInULDAtAirport find(MailbagInULDAtAirportPK mailbagInAirportPK) throws FinderException {
		return PersistenceController.getEntityManager().find(MailbagInULDAtAirport.class, mailbagInAirportPK);
	}

	public MailbagInULDAtAirport() {
	}

	public MailbagInULDAtAirport( MailbagInULDAtAirportVO mailbagInULDVO) {
		populatePK(mailbagInULDVO);
		populateAttributes(mailbagInULDVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	private void populatePK(MailbagInULDAtAirportVO mailbagInULDVO) {
		this.setCompanyCode(mailbagInULDVO.getComapnyCode());
		this.setCarrierId(mailbagInULDVO.getCarrierId());
		this.setAirportCode(mailbagInULDVO.getAirportCode());
		this.setUldNumber(mailbagInULDVO.getUldNumber());
		this.setMailSequenceNumber(mailbagInULDVO.getMailSequenceNumber());
	}

	private void populateAttributes(MailbagInULDAtAirportVO mailbagInULDVO) {
		setContainerNumber(mailbagInULDVO.getContainerNumber());
		if (mailbagInULDVO.getDamageFlag() != null) {
			setDamageFlag(mailbagInULDVO.getDamageFlag());
		} else {
			setDamageFlag(MailbagInULDAtAirportVO.FLAG_NO);
		}
		setScannedDate(mailbagInULDVO.getScannedDate());
		mailbagInULDVO.setAcceptedBags(1);
		mailbagInULDVO.setAcceptedWgt(mailbagInULDVO.getWeight());
		setTransferFromCarrier(mailbagInULDVO.getTransferFromCarrier());
		setAcceptedBags(mailbagInULDVO.getAcceptedBags());
		if (mailbagInULDVO.getAcceptedWgt() != null&&mailbagInULDVO.getAcceptedWgt().getValue()!=null){
			if((mailbagInULDVO.getAcceptedWgt().getValue().doubleValue()==0.0)&&mailbagInULDVO.getAcceptedWgt().getDisplayValue()!=null) {
				setAcceptedWeight(mailbagInULDVO.getAcceptedWgt().getDisplayValue().doubleValue());
			}
			else {
				setAcceptedWeight(mailbagInULDVO.getAcceptedWgt().getValue().doubleValue());
			}

		}
		setStatedBags(mailbagInULDVO.getStatedBags());
		if (mailbagInULDVO.getStatedWgt() != null) {
			setStatedWeight(mailbagInULDVO.getStatedWgt().getValue().doubleValue());
		}

	}

	/** 
	* @author A-5991
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

	/** 
	* A-1739
	* @return
	*/
	public MailbagInULDAtAirportVO retrieveVO() {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagInULDAtAirportVO mailbagInULDVO = new MailbagInULDAtAirportVO();
		mailbagInULDVO.setMailSequenceNumber(this.getMailSequenceNumber());
		mailbagInULDVO.setContainerNumber(getContainerNumber());
		mailbagInULDVO.setDamageFlag(getDamageFlag());
		mailbagInULDVO
				.setScannedDate(localDateUtil.getLocalDateTime(getScannedDate(), this.getAirportCode()));
		mailbagInULDVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(getAcceptedWeight())));
		mailbagInULDVO.setTransferFromCarrier(getTransferFromCarrier());
		return mailbagInULDVO;
	}

	/** 
	* @author A-8353
	* @param mailbagVO
	* @param containerVO
	* @param airportCode
	*/
	public void updateHandoverReceivedCarrierForTransfer(MailbagVO mailbagVO, ContainerVO containerVO,
			String airportCode) {
		MailbagInULDAtAirportPK mailbagInULDAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAirportPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDAirportPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDAirportPK.setAirportCode(airportCode);
		mailbagInULDAirportPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())
				&& containerVO.getFinalDestination() != null) {
			mailbagInULDAirportPK.setUldNumber(
					MailConstantsVO.CONST_BULK + MailConstantsVO.SEPARATOR + containerVO.getFinalDestination());
		} else {
			mailbagInULDAirportPK.setUldNumber(mailbagVO.getContainerNumber());
		}
		MailbagInULDAtAirport mailbagInULDAtArp = null;
		try {
			mailbagInULDAtArp = find(mailbagInULDAirportPK);
		} catch (FinderException e) {
			e.getMessage();
		}
		if (mailbagInULDAtArp != null) {
			mailbagInULDAtArp.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		}
	}

	public void setScannedDate(ZonedDateTime scannedDate) {
		if (Objects.nonNull(scannedDate)) {
			this.scannedDate = scannedDate.toLocalDateTime();
		}
	}

}
