package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/** 
 * @author a-3109Stores ULD assigned to destination . Delete from this entity while assigning to flight
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(ULDAtAirportPK.class)
@Table(name = "MALARPULD")
public class ULDAtAirport extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "ULDNUM")
	private String uldNumber;
	@Id
	@Column(name = "ARPCOD")
	private String airportCode;
	@Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
	/** 
	* finalDestination
	*/
	@Column(name = "DSTCOD")
	private String finalDestination;
	/** 
	* numberOfBags
	*/
	@Column(name = "ACPBAG")
	private int numberOfBags;
	/** 
	* totalWeight
	*/
	@Column(name = "ACPWGT")
	private double totalWeight;
	/** 
	* remarks
	*/
	@Column(name = "RMK")
	private String remarks;
	/** 
	* carrierCode
	*/
	@Column(name = "FLTCARCOD")
	private String carrierCode;

	/** 
	* warehouseCode
	*/
	@Column(name = "WHSCOD")
	private String warehouseCode;
	/** 
	* locationCode
	*/
	@Column(name = "LOCCOD")
	private String locationCode;
	/** 
	* transferFromCarrier
	*/
	@Column(name = "FRMCARCOD")
	private String transferFromCarrier;
	/** 
	* mailbagInULDAtAirports
	*/
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false) })
	private Set<MailbagInULDAtAirport> mailbagInULDAtAirports;

	public ULDAtAirport() {
	}

	public ULDAtAirport(ULDAtAirportVO uldAtAirportVO) {
		populatePK(uldAtAirportVO);
		populateAttributes(uldAtAirportVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
		populateChildren(uldAtAirportVO);
	}

	/** 
	* A-3109
	* @param uldAtAirportVO
	* @throws SystemException
	*/
	private void populateChildren(ULDAtAirportVO uldAtAirportVO) {
		Collection<MailbagInULDAtAirportVO> mailbagInUldVOs = uldAtAirportVO.getMailbagInULDAtAirportVOs();
		if (mailbagInUldVOs != null && mailbagInUldVOs.size() > 0) {
			populateMailbags(mailbagInUldVOs);
		}
	}

	/** 
	* A-3109
	* @param uldAtAirportVO
	*/
	private void populatePK(ULDAtAirportVO uldAtAirportVO) {
		this.setCompanyCode(uldAtAirportVO.getCompanyCode());
		this.setUldNumber(uldAtAirportVO.getUldNumber());
		this.setCarrierId(uldAtAirportVO.getCarrierId());
		this.setAirportCode(uldAtAirportVO.getAirportCode());
	}

	/** 
	* A-3109
	* @param uldAtAirportVO
	*/
	private void populateAttributes(ULDAtAirportVO uldAtAirportVO) {
		setNumberOfBags(uldAtAirportVO.getNumberOfBags());
		if (uldAtAirportVO.getTotalWeight() != null) {
			setTotalWeight(uldAtAirportVO.getTotalWeight().getValue().doubleValue());
		}
		setFinalDestination(uldAtAirportVO.getFinalDestination());
		setRemarks(uldAtAirportVO.getRemarks());
		setCarrierCode(uldAtAirportVO.getCarrierCode());
		setWarehouseCode(uldAtAirportVO.getWarehouseCode());
		setLocationCode(uldAtAirportVO.getLocationCode());
		setTransferFromCarrier(uldAtAirportVO.getTransferFromCarrier());
	}

	/** 
	* @author A-3109
	* @param uldAtAirportPK
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static ULDAtAirport find(ULDAtAirportPK uldAtAirportPK) throws FinderException {
		return PersistenceController.getEntityManager().find(ULDAtAirport.class, uldAtAirportPK);
	}

	/** 
	* This method is used to remove the Instance Of the Entity
	* @throws SystemException
	*/
	public void remove() {
		removeChildren();
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

	/** 
	* @author A-1739 This method is used to remove the Children
	* @throws SystemException
	*/
	private void removeChildren() {
		if (getMailbagInULDAtAirports() != null) {
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				mailbagInULDAtAirport.remove();
			}
			mailbagInULDAtAirports.clear();
		}
	}

	/** 
	* @author a-3109 methods the DAO instance ..
	* @return
	* @throws SystemException
	*/
	public static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/** 
	* @author A-5991
	* @param containerDetailsVO
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	public void insertMailBagInULDAtArpAcceptanceDtls(ContainerDetailsVO containerDetailsVO,
			boolean isInventoryForArrival) throws DuplicateMailBagsException {
		log.debug("MailAcceptance" + " : " + "insertMailBagInULDAtArpAcceptanceDtls" + " Entering");
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
		if (containerDetailsVO.getMailDetails() != null) {
			mailbagVOs.addAll(containerDetailsVO.getMailDetails());
		}
		if (despatchDetailsVOs != null) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				mailbagVOs.add(MailOperationsVOConverter.convertToMailBagVO(despatchDetailsVO));
			}
		}
		if (mailbagVOs.size() > 0) {
			addAcceptedMailbags(mailbagVOs);
		}
		log.debug("MailAcceptance" + " : " + "insertMailBagInULDAtArpAcceptanceDtls" + " Exiting");
	}

	private String constructBulkULDNumber(String airport, String carrierCode) {
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
					.append(airport).toString();
		} else {
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}

	/** 
	* @author A-5991
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	public void addAcceptedMailbags(Collection<MailbagVO> mailbagVOs) throws DuplicateMailBagsException {
		log.debug("DSNInULDAtAirport" + " : " + "addMailbagsToULDAtArp" + " Entering");
		Collection<MailbagInULDAtAirportVO> mailbagsInULD = new ArrayList<MailbagInULDAtAirportVO>();
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbags = null;
			mailbagVO = mailController.constructOriginDestinationDetails(mailbagVO);
			MailbagPK mailbagPKs = constructMailbagPK(mailbagVO);
			try {
				mailbags = Mailbag.find(mailbagPKs);
			} catch (FinderException e) {
			}
			boolean isDuplicate = false;
			if (mailbags != null) {
				if (!(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbags.getLatestStatus()))
						&& !MailConstantsVO.HHT_TRA.equals(mailbags.getLatestStatus())
						&& !("ARRSCN").equals(mailbagVO.getMailbagDataSource())
						&& !MailConstantsVO.MAIL_STATUS_NEW.equals(mailbags.getLatestStatus())) {
					if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()))
						isDuplicate = new MailController().checkForDuplicateMailbag(mailbagVO.getCompanyCode(),
								mailbagVO.getPaCode(), mailbags);
				}
				mailbags.updateAcceptanceFlightDetails(mailbagVO);
				mailbags.updateAcceptanceDamage(mailbagVO);
				mailbags.updatePrimaryAcceptanceDetails(mailbagVO);
			}
			MailbagInULDAtAirport mailbagInULDAtArp = null;
			try {
				mailbagInULDAtArp = findMailbagInULD(constructMailbagInULDAtAirportPK(mailbagVO));
			} catch (FinderException e) {
			}
			if (mailbagInULDAtArp != null) {
				if (mailbagVO.getDamageFlag() != null) {
					mailbagInULDAtArp.setDamageFlag(mailbagVO.getDamageFlag());
				} else {
					mailbagInULDAtArp.setDamageFlag(MailbagVO.FLAG_NO);
				}
				mailbagInULDAtArp.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
				mailbagInULDAtArp.setScannedDate(mailbagVO.getScannedDate());
			} else {
				if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					Mailbag mailbag = null;
					MailbagPK mailbagPK = constructMailbagPK(mailbagVO);
					try {
						mailbag = Mailbag.find(mailbagPK);
					} catch (FinderException e) {
					}
					if (mailbag == null || isDuplicate) {
						String paCode = null;
						String companyCode = mailbagVO.getCompanyCode();
						String originOfFiceExchange = mailbagVO.getOoe();
						if (mailbagVO.getPaCode() == null) {
							try {
								paCode = new MailController().findPAForOfficeOfExchange(companyCode,
										originOfFiceExchange);
							} finally {
							}
							mailbagVO.setPaCode(paCode);
						}
						mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
						String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
						if (scanWaved != null) {
							mailbagVO.setScanningWavedFlag(scanWaved);
						}
						if (new MailController().isUSPSMailbag(mailbagVO)) {
							mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
						} else {
							mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
						}
						mailbagVO.setMailbagDataSource(mailbagVO.getMailbagDataSource());
						mailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
						MailAcceptance.populatePrimaryAcceptanceDetails(mailbagVO);
						mailbag = new Mailbag(mailbagVO);
					}
					if (mailbagVO != null) {
						mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
						mailbagsInULD.add(constructMailbagInULDAtArp(mailbagVO));
					}
				}
			}
		}
		populateMailbags(mailbagsInULD);
		log.debug("DSNInULDAtAirport" + " : " + "addMailbagsToULDAtArp" + " Exiting");
	}

	/** 
	* @author a-1936 This method is used to construct theMailbagInULDAtAirportPK
	* @param mailbagVo
	* @return
	* @throws SystemException
	*/
	private MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(MailbagVO mailbagVo) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		//TODO: Meera to review the code
		if (this.airportCode != null && this.getCompanyCode() != null) {
			mailbagInULDAtAirportPK.setCompanyCode(this.getCompanyCode());
			mailbagInULDAtAirportPK.setUldNumber(this.getUldNumber());
			mailbagInULDAtAirportPK.setAirportCode(this.getAirportCode());
			mailbagInULDAtAirportPK.setCarrierId(this.getCarrierId());
			mailbagInULDAtAirportPK
					.setMailSequenceNumber(mailbagVo.getMailSequenceNumber() > 0 ? mailbagVo.getMailSequenceNumber()
							: findMailSequenceNumber(mailbagVo.getMailbagId(), mailbagVo.getCompanyCode()));
		} else {
			mailbagInULDAtAirportPK.setCompanyCode(mailbagVo.getCompanyCode());
			if (mailbagVo.getUldNumber() != null) {
				if (MailConstantsVO.BULK_TYPE.equals(mailbagVo.getContainerType())) {
					mailbagInULDAtAirportPK.setUldNumber(
							constructBulkULDNumber(mailbagVo.getFinalDestination(), mailbagVo.getCarrierCode()));
				} else {
					mailbagInULDAtAirportPK.setUldNumber(mailbagVo.getUldNumber());
				}
			}
			if (MailConstantsVO.BULK_TYPE.equals(mailbagVo.getContainerType())
					&& MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVo.getOperationalFlag())
					&& (mailbagVo.getAcceptanceFlag() == null || "N".equals(mailbagVo.getAcceptanceFlag()))
					&& (mailbagVo.getArrivedFlag() == null || "N".equals(mailbagVo.getArrivedFlag()))
					&& "-1".equals(mailbagVo.getFlightNumber())) {
				String bulkNumber = constructBulkULDNumber(mailbagVo.getPou());
				mailbagInULDAtAirportPK.setUldNumber(bulkNumber);
			} else {
				mailbagInULDAtAirportPK.setUldNumber(mailbagVo.getContainerNumber());
			}
			mailbagInULDAtAirportPK.setAirportCode(mailbagVo.getScannedPort());
			mailbagInULDAtAirportPK.setCarrierId(mailbagVo.getCarrierId());
			mailbagInULDAtAirportPK
					.setMailSequenceNumber(mailbagVo.getMailSequenceNumber() > 0 ? mailbagVo.getMailSequenceNumber()
							: findMailSequenceNumber(mailbagVo.getMailbagId(), mailbagVo.getCompanyCode()));
		}
		return mailbagInULDAtAirportPK;
	}

	/** 
	* @author A-5991
	* @param mailBagId
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public long findMailSequenceNumber(String mailBagId, String companyCode) {
		return constructDAO().findMailSequenceNumber(mailBagId, companyCode);
	}

	/** 
	* @author A-5991
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO) {
		Collection<MailbagVO> mailbagVos = null;
		try {
			mailbagVos = constructDAO().findMailBagForDespatch(mailbagVO);
		} catch (PersistenceException e) {
			log.error("PersistenceException Caught");
		}
		return mailbagVos;
	}

	/** 
	* A-5991
	* @param mailbagInAirportPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailbagInULDAtAirport findMailbagInULD(MailbagInULDAtAirportPK mailbagInAirportPK)
			throws FinderException {
		return MailbagInULDAtAirport.find(mailbagInAirportPK);
	}

	/** 
	* A-5991
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	private MailbagInULDAtAirportVO constructMailbagInULDAtArp(MailbagVO mailbagVO) {
		MailbagInULDAtAirportVO mailbagInULD = new MailbagInULDAtAirportVO();
		mailbagInULD.setMailId(mailbagVO.getMailbagId());
		mailbagInULD.setComapnyCode(mailbagVO.getCompanyCode());
		mailbagInULD.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULD.setAirportCode(mailbagVO.getScannedPort());
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
			mailbagInULD
					.setUldNumber(constructBulkULDNumber(mailbagVO.getFinalDestination(), mailbagVO.getCarrierCode()));
		} else {
			if (mailbagVO.getUldNumber() != null) {
				mailbagInULD.setUldNumber(mailbagVO.getUldNumber());
			} else {
				mailbagInULD.setUldNumber(mailbagVO.getContainerNumber());
			}
		}
		if (MailConstantsVO.ULD_TYPE.equals(mailbagVO.getContainerType()) && mailbagVO.getCarrierCode() != null
				&& MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR)
						.concat(mailbagVO.getCarrierCode()).equals(mailbagVO.getContainerNumber())) {
			mailbagInULD.setUldNumber(mailbagVO.getContainerNumber());
		}
		mailbagInULD.setContainerNumber(mailbagVO.getContainerNumber());
		mailbagInULD.setDamageFlag(mailbagVO.getDamageFlag());
		mailbagInULD.setAcceptedFlag(mailbagVO.getAcceptanceFlag());
		mailbagInULD.setArrivedFlag(mailbagVO.getArrivedFlag());
		mailbagInULD.setDeliveredFlag(mailbagVO.getDeliveredFlag());
		mailbagInULD.setScannedDate(mailbagVO.getScannedDate());
		mailbagInULD.setWeight(mailbagVO.getWeight());
		mailbagInULD.setMailClass(mailbagVO.getMailClass());
		mailbagInULD.setSealNumber(mailbagVO.getSealNumber());
		mailbagInULD.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		if (mailbagVO.getMailSequenceNumber() > 0) {
			mailbagInULD.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		}
		return mailbagInULD;
	}

	/** 
	* A-1739
	* @param mailbagInUldVOs
	* @throws SystemException
	*/
	private void populateMailbags(Collection<MailbagInULDAtAirportVO> mailbagInUldVOs) {

		MailbagInULDAtAirport uldAtAirport = null;
		if (getMailbagInULDAtAirports() == null) {
			mailbagInULDAtAirports = new HashSet<MailbagInULDAtAirport>();
		}
		for (MailbagInULDAtAirportVO mailbagInULDVO : mailbagInUldVOs) {
			//TODO: NEO:Meera to review code
			if (this.airportCode != null) {
				MailbagInULDAtAirportPK mailbagInULDAtAiportPK = new MailbagInULDAtAirportPK();
				mailbagInULDAtAiportPK.setCompanyCode(this.getCompanyCode());
				mailbagInULDAtAiportPK.setAirportCode(this.getAirportCode());
				mailbagInULDAtAiportPK.setCarrierId(this.getCarrierId());
				mailbagInULDAtAiportPK.setMailSequenceNumber(mailbagInULDVO.getMailSequenceNumber());
				mailbagInULDAtAiportPK.setUldNumber(this.getUldNumber());
				try {
					uldAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAiportPK);
				} catch (FinderException exception) {
					log.debug(exception.getMessage());
				}
			} else {
				MailbagInULDAtAirportPK mailbagInULDAtAiportPK = new MailbagInULDAtAirportPK();
				mailbagInULDAtAiportPK.setCompanyCode(mailbagInULDVO.getComapnyCode());
				mailbagInULDAtAiportPK.setAirportCode(mailbagInULDVO.getAirportCode());
				mailbagInULDAtAiportPK.setCarrierId(mailbagInULDVO.getCarrierId());
				mailbagInULDAtAiportPK.setMailSequenceNumber(mailbagInULDVO.getMailSequenceNumber());
				mailbagInULDAtAiportPK.setUldNumber(mailbagInULDVO.getUldNumber());
				try {
					uldAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAiportPK);
				} catch (FinderException exception) {
					log.debug(exception.getMessage());
				}
			}
			if (uldAtAirport == null) {
				mailbagInULDVO.setComapnyCode(ContextUtil.getInstance().callerLoginProfile().getCompanyCode());
				if(Objects.isNull(mailbagInULDVO.getAirportCode())){
					mailbagInULDVO.setAirportCode(this.airportCode);
				}
				if(Objects.isNull(mailbagInULDVO.getUldNumber())){
					mailbagInULDVO.setUldNumber(this.uldNumber);
				}
				if(mailbagInULDVO.getCarrierId()==0){
					mailbagInULDVO.setCarrierId(this.carrierId);
				}
				if (!MailConstantsVO.ONLOAD_MESSAGE.equals(mailbagInULDVO.getMailSource())) {
					new MailbagInULDAtAirport(mailbagInULDVO);
				} else {
					mailbagInULDAtAirports.add(new MailbagInULDAtAirport(mailbagInULDVO));
				}
			}
		}
	}

	/** 
	* @author A-1936 This method is used to reassign the MailBags ToDestination
	* @param flightAssignedMailbags
	* @param toDestinationContainerVO
	* @throws SystemException
	*/
	public void reassignMailToDestination(Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toDestinationContainerVO) {
		if (flightAssignedMailbags != null && flightAssignedMailbags.size() > 0) {
			for (MailbagVO mailbagVO : flightAssignedMailbags) {
				MailbagInULDAtAirportPK mailBagInUldAtAirportPK = constructMailbagInULDAtAirportPK(mailbagVO);
				MailbagInULDAtAirport mailbagInULDAtAirport = null;
				try {
					mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailBagInUldAtAirportPK);
				} catch (FinderException ex) {
					MailbagInULDAtAirportVO mailbagInULDAtAirportVO = constructMailBagInULDAirport(mailbagVO,
							toDestinationContainerVO);
					mailbagInULDAtAirportVO.setUldNumber(this.getUldNumber());
					mailbagInULDAtAirport = new MailbagInULDAtAirport( mailbagInULDAtAirportVO);
				}
			}
		}
	}

	/** 
	* @author a-1936 This method is used to reassign the MailFrom DestinationGroup the Mailbags Based on their DSNS in a Particular ULD at the Airport say DSN1-U1-ARP DSN2-UI-ARP DSN3-U1-ARP
	* @param destinationAssignedMailbags
	* @throws SystemException
	*/
	public void reassignMailFromDestination(Collection<MailbagVO> destinationAssignedMailbags) {
		log.debug("ULDAtAirport" + " : " + "reassignMailFromDestination" + " Entering");
		for (MailbagVO mailbagVo : destinationAssignedMailbags) {
			MailbagInULDAtAirportPK mailbagInULDAtAirportPK = constructMailbagInULDAtAirportPK(mailbagVo);
			removeMailBag(mailbagInULDAtAirportPK, mailbagVo);
		}
	}

	/** 
	* @author a-1936 This method is used to remove the mailbagFromAirport
	* @param mailbagInULDAtAirportPK
	* @throws SystemException
	*/
	private void removeMailBag(MailbagInULDAtAirportPK mailbagInULDAtAirportPK, MailbagVO mailBagVo) {
		log.debug("DSNinuldAtAirport" + " : " + "removeMailBag" + " Entering");
		MailbagInULDAtAirport mailbagInULDAtAirport = null;
		try {
			mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAirportPK);
		} catch (FinderException ex) {
		}
		if (mailbagInULDAtAirport != null) {
			if (!MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailBagVo.getMailStatus())) {
				mailBagVo.setTransferFromCarrier(mailbagInULDAtAirport.getTransferFromCarrier());
			}
			mailbagInULDAtAirport.remove();
		}
	}

	/** 
	* A-1739
	* @return
	*/
	public ULDAtAirportVO retrieveVO() {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(this.getCompanyCode());
		uldAtAirportVO.setCarrierId(this.getCarrierId());
		uldAtAirportVO.setAirportCode(this.getAirportCode());
		uldAtAirportVO.setUldNumber(this.getUldNumber());
		uldAtAirportVO.setNumberOfBags(getNumberOfBags());
		uldAtAirportVO
				.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(getTotalWeight())));
		uldAtAirportVO.setFinalDestination(getFinalDestination());
		uldAtAirportVO.setCarrierCode(getCarrierCode());
		uldAtAirportVO.setRemarks(getRemarks());
		uldAtAirportVO.setWarehouseCode(getWarehouseCode());
		uldAtAirportVO.setLocationCode(getLocationCode());
		uldAtAirportVO.setTransferFromCarrier(getTransferFromCarrier());
		Collection<MailbagInULDAtAirport> mailbagInULDAtAirports = getMailbagInULDAtAirports();
		if (mailbagInULDAtAirports != null && mailbagInULDAtAirports.size() > 0) {
			Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs = new ArrayList<MailbagInULDAtAirportVO>();
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				mailbagInULDAtAirportVOs.add(mailbagInULDAtAirport.retrieveVO());
			}
			uldAtAirportVO.setMailbagInULDAtAirportVOs(mailbagInULDAtAirportVOs);
		}
		return uldAtAirportVO;
	}

	/** 
	* @author a-1936 This method is used to constructMailBagInULDAirport
	* @param mailbagVo
	* @param toDestinationVO
	* @return
	* @throws SystemException
	*/
	private MailbagInULDAtAirportVO constructMailBagInULDAirport(MailbagVO mailbagVo, ContainerVO toDestinationVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
		if(this.getCompanyCode()!=null && this.getAirportCode()!=null ){
		mailbagInULDAtAirportVO.setComapnyCode(this.getCompanyCode());
		mailbagInULDAtAirportVO.setCarrierId(this.getCarrierId());
		mailbagInULDAtAirportVO.setAirportCode(this.getAirportCode());
		}
		mailbagInULDAtAirportVO.setContainerNumber(toDestinationVO.getContainerNumber());
		mailbagInULDAtAirportVO.setMailId(mailbagVo.getMailbagId());
		if (mailbagVo.getMailSequenceNumber() > 0) {
			mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
		} else {
			mailbagInULDAtAirportVO.setMailSequenceNumber(
					findMailSequenceNumber(mailbagVo.getMailbagId(), mailbagVo.getCompanyCode()));
		}
		mailbagInULDAtAirportVO.setDamageFlag(mailbagVo.getDamageFlag());
		mailbagInULDAtAirportVO.setWeight(mailbagVo.getWeight());
		mailbagInULDAtAirportVO.setScannedDate(localDateUtil.getLocalDate(toDestinationVO.getAssignedPort(), true));
		mailbagInULDAtAirportVO.setMailClass(mailbagVo.getMailClass());
		mailbagInULDAtAirportVO.setTransferFromCarrier(mailbagVo.getTransferFromCarrier());
		return mailbagInULDAtAirportVO;
	}

	/** 
	* @author a-1936 This method is used to constructMailBagInULDAirport
	* @param toDestinationVO
	* @return
	*/
	private MailbagInULDAtAirportVO constructMailBagInULDAirportForDespatch(DespatchDetailsVO despatchDetailsVO,
			ContainerVO toDestinationVO, MailbagVO mailbagvo) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
		mailbagInULDAtAirportVO.setContainerNumber(toDestinationVO.getContainerNumber());
		mailbagInULDAtAirportVO.setMailId(mailbagvo.getMailbagId());
		mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		mailbagInULDAtAirportVO.setWeight(despatchDetailsVO.getAcceptedWeight());
		mailbagInULDAtAirportVO.setScannedDate(localDateUtil.getLocalDate(toDestinationVO.getAssignedPort(), true));
		mailbagInULDAtAirportVO.setMailClass(mailbagvo.getMailClass());
		return mailbagInULDAtAirportVO;
	}

	public static String createDespatchBag(DespatchDetailsVO despatchDetailsVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(despatchDetailsVO.getOriginOfficeOfExchange())
				.append(despatchDetailsVO.getDestinationOfficeOfExchange())
				.append(despatchDetailsVO.getMailCategoryCode()).append(despatchDetailsVO.getMailSubclass())
				.append(despatchDetailsVO.getYear()).append(despatchDetailsVO.getDsn());
		return dsnid.toString();
	}

	/** 
	* @author a-1936 This method is used to reassign the Despatches to theDestination
	* @param despatchDetailVos
	* @param toContainerVO
	* @throws SystemException
	*/
	public void reassignDSNsToDestination(Collection<DespatchDetailsVO> despatchDetailVos, ContainerVO toContainerVO) {
		if (despatchDetailVos != null && despatchDetailVos.size() > 0) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailVos) {
				MailbagVO mailbagVO = constructMailbagInULDAtAirportvoFromDespatch(despatchDetailsVO);
				Collection<MailbagVO> mailbagVOs = findMailBagForDespatch(mailbagVO);
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagvo : mailbagVOs) {
						MailbagInULDAtAirportPK mailBagInUldAtAirportPK = constructMailbagInULDAtAirportPKFromDespatch(
								mailbagvo);
						MailbagInULDAtAirport mailbagInULDAtAirport = null;
						try {
							mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailBagInUldAtAirportPK);
						} catch (FinderException ex) {
							MailbagInULDAtAirportVO mailbagInULDAtAirportVO = constructMailBagInULDAirportForDespatch(
									despatchDetailsVO, toContainerVO, mailbagvo);
							mailbagInULDAtAirport = new MailbagInULDAtAirport(mailbagInULDAtAirportVO);
						}
					}
				}
			}
		}
		log.debug("ULDAtAirport" + " : " + "reassignDSNsToDestination" + " Exiting");
	}

	/** 
	* @author a-1936 This method is used to construct theMailbagInULDAtAirportPK
	* @return
	* @throws SystemException
	*/
	private MailbagInULDAtAirportPK constructMailbagInULDAtAirportPKFromDespatch(MailbagVO mailbagvo) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(mailbagvo.getCompanyCode());
		mailbagInULDAtAirportPK.setUldNumber(mailbagvo.getUldNumber());
		mailbagInULDAtAirportPK.setAirportCode(mailbagvo.getScannedPort());
		mailbagInULDAtAirportPK.setCarrierId(this.getCarrierId());
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		return mailbagInULDAtAirportPK;
	}

	/** 
	* @author a-1936 This method is used to construct theMailbagInULDAtAirportPK
	* @return
	* @throws SystemException
	*/
	private MailbagVO constructMailbagInULDAtAirportvoFromDespatch(DespatchDetailsVO despatchDetailsVO) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(this.getCompanyCode());
		mailbagVO.setUldNumber(this.getUldNumber());
		mailbagVO.setScannedPort(this.getAirportCode());
		mailbagVO.setCarrierId(this.getCarrierId());
		mailbagVO.setDespatchId(createDespatchBag(despatchDetailsVO));
		return mailbagVO;
	}

	/** 
	* A-1739
	* @param containerNumber
	* @throws SystemException
	*/
	public Collection<MailbagInULDAtAirportVO> reassignBulkContainer(String containerNumber) {
		Collection<MailbagInULDAtAirport> mailbagInULDAtAirports = getMailbagInULDAtAirports();
		Collection<MailbagInULDAtAirportVO> mailbagInULDVOs = new ArrayList<MailbagInULDAtAirportVO>();
		if (mailbagInULDAtAirports != null && mailbagInULDAtAirports.size() > 0) {
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				if (containerNumber != null
						&& containerNumber.equalsIgnoreCase(mailbagInULDAtAirport.getContainerNumber())) {
					mailbagInULDVOs.add(mailbagInULDAtAirport.retrieveVO());
					mailbagInULDAtAirport.remove();
				}
			}
		}
		return mailbagInULDVOs;
	}

	public Collection<MailbagInULDAtAirportVO> reassignBulkContainerFormail(String containerNumber) {
		Collection<MailbagInULDAtAirport> mailbagInULDAtAirports = getMailbagInULDAtAirports();
		Collection<MailbagInULDAtAirportVO> mailbagInULDVOs = new ArrayList<MailbagInULDAtAirportVO>();
		if (mailbagInULDAtAirports != null && mailbagInULDAtAirports.size() > 0) {
			for (MailbagInULDAtAirport mailbagInULDAtAirport : mailbagInULDAtAirports) {
				if (containerNumber != null
						&& containerNumber.equalsIgnoreCase(mailbagInULDAtAirport.getContainerNumber())) {
					mailbagInULDVOs.add(mailbagInULDAtAirport.retrieveVO());
					mailbagInULDAtAirport.remove();
				}
			}
		}
		return mailbagInULDVOs;
	}

	/** 
	* @author A-1739
	* @throws SystemException
	*/
	public void assignBulkContainer(Collection<MailbagInULDAtAirportVO> mailbags) {
		if (getMailbagInULDAtAirports() == null) {
			mailbagInULDAtAirports = new HashSet<MailbagInULDAtAirport>();
		}

		for (MailbagInULDAtAirportVO mailbagInULDVO : mailbags) {
			mailbagInULDVO.setComapnyCode(ContextUtil.getInstance().callerLoginProfile().getCompanyCode());
			if(Objects.isNull(mailbagInULDVO.getAirportCode())){
				mailbagInULDVO.setAirportCode(this.airportCode);
			}
			if(Objects.isNull(mailbagInULDVO.getUldNumber())){
				mailbagInULDVO.setUldNumber(this.uldNumber);
			}
			if(mailbagInULDVO.getCarrierId()==0){
				mailbagInULDVO.setCarrierId(this.carrierId);
			}
			mailbagInULDAtAirports.add(new MailbagInULDAtAirport(mailbagInULDVO));
		}
	}

	/** 
	* @author a-1883
	* @throws SystemException
	*/
	public void saveDestAssignedDetailsForTransfer(ULDAtAirportVO uLDAtAirportVO, Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs) {
		log.debug("ULDAtAirport" + " : " + "saveDestAssignedDetailsForTransfer" + " Entering");
		if (CollectionUtils.isNotEmpty(mailbagInULDForSegmentVOs)) {
			for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : mailbagInULDForSegmentVOs) {
				var mailbagInULDAtAirportVO = constructMailbagArpVO(uLDAtAirportVO, mailbagInULDForSegmentVO);
				var mailbagInULDAtAirportPK = constructMailbagInULDAtAirportPK(mailbagInULDAtAirportVO);
				try {
					MailbagInULDAtAirport.find(mailbagInULDAtAirportPK);
				} catch (FinderException e) {
					new MailbagInULDAtAirport(mailbagInULDAtAirportVO);
				}
			}
		}
		log.debug("ULDAtAirport" + " : " + "saveDestAssignedDetailsForTransfer" + " Exiting");
	}

	public MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(MailbagInULDAtAirportVO mailbagInULDAtAirportVO) {
		var mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(mailbagInULDAtAirportVO.getComapnyCode());
		mailbagInULDAtAirportPK.setCarrierId(mailbagInULDAtAirportVO.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(mailbagInULDAtAirportVO.getAirportCode());
		mailbagInULDAtAirportPK.setUldNumber(mailbagInULDAtAirportVO.getUldNumber());
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagInULDAtAirportVO
				.getMailSequenceNumber());
		return mailbagInULDAtAirportPK;
	}

	/** 
	* @author A-5991
	* @param uldAtAirportPK
	* @param mailbagInULDAtAirportVO
	* @return
	*/
	public MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(ULDAtAirportPK uldAtAirportPK,
			MailbagInULDAtAirportVO mailbagInULDAtAirportVO) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(uldAtAirportPK.getCompanyCode());
		mailbagInULDAtAirportPK.setCarrierId(uldAtAirportPK.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(uldAtAirportPK.getAirportCode());
		mailbagInULDAtAirportPK.setUldNumber(uldAtAirportPK.getUldNumber());
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagInULDAtAirportVO.getMailSequenceNumber());
		return mailbagInULDAtAirportPK;
	}

	/**
	 * @param mailbagSegmentVO

	 * @return
	 * @throws SystemException
	 */
	private MailbagInULDAtAirportVO constructMailbagArpVO(
			ULDAtAirportVO uLDAtAirportVO, MailbagInULDForSegmentVO mailbagSegmentVO){
		log.debug("DSNInULDAtAirport" + " : " + "constructMailbagVO" + " Entering");
		var mailbagArpVO = new MailbagInULDAtAirportVO();
		mailbagArpVO.setComapnyCode(ContextUtil.getInstance().callerLoginProfile().getCompanyCode());
		mailbagArpVO.setCarrierId(uLDAtAirportVO.getCarrierId());
		mailbagArpVO.setAirportCode(uLDAtAirportVO.getAirportCode());
		mailbagArpVO.setUldNumber(uLDAtAirportVO.getUldNumber());
		mailbagArpVO.setContainerNumber(mailbagSegmentVO.getContainerNumber());
		mailbagArpVO.setDamageFlag(mailbagSegmentVO.getDamageFlag());
		mailbagArpVO.setMailId(mailbagSegmentVO.getMailId());
		mailbagArpVO.setScannedDate(mailbagSegmentVO.getScannedDate());
		mailbagArpVO.setWeight(mailbagSegmentVO.getWeight());
		mailbagArpVO.setMailClass(mailbagSegmentVO.getMailClass());
		mailbagArpVO.setTransferFromCarrier(mailbagSegmentVO.getTransferFromCarrier());
		mailbagArpVO.setMailSequenceNumber(mailbagSegmentVO.getMailSequenceNumber());
		log.debug("DSNInULDAtAirport" + " : " + "constructMailbagVO" + " Exiting");
		return mailbagArpVO;
	}

	/** 
	* @param mailbagVOs
	* @param toContainerVO
	* @throws SystemException
	*/
	public void saveDestAssignedMailsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO) {
		log.debug("ULDAtAirport" + " : " + "saveDestAssignedDetailsForTransfer" + " Entering");
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailbagInULDAtAirportVO mailbagInULDAtAirportVO = constructMailBagInULDAirport(mailbagVO, toContainerVO);
			mailbagInULDAtAirportVO.setUldNumber(this.getUldNumber());
			MailbagInULDAtAirport mailbagInULDAtAirport = new MailbagInULDAtAirport(mailbagInULDAtAirportVO
					);
			if (mailbagVO.getTransferFromCarrier() != null && mailbagVO.getTransferFromCarrier().trim().length() > 0) {
				mailbagInULDAtAirport.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
			} else {
				if (!toContainerVO.isFoundTransfer()) {
					mailbagInULDAtAirport.setTransferFromCarrier(mailbagVO.getCarrierCode());
				}
			}
			if (getMailbagInULDAtAirports() == null) {
				setMailbagInULDAtAirports(new HashSet<MailbagInULDAtAirport>());
			}
			getMailbagInULDAtAirports().add(mailbagInULDAtAirport);
		}
	}

	/** 
	* @author a-1883
	* @param mailbagVOs
	* @throws SystemException
	*/
	public void saveDamageDetailsForMailbags(Collection<MailbagVO> mailbagVOs) {
		log.debug("ULDAtAirport" + " : " + "saveDamageDetailsForMailbags" + " Entering");
		for (MailbagVO mailbagVO : mailbagVOs) {
			updateMailbagInULDAtAirport(mailbagVO);
		}
		log.debug("ULDAtAirport" + " : " + "saveDamageDetailsForMailbags" + " Exiting");
	}

	/** 
	* @author a-1883
	* @param mailbagVO
	* @throws SystemException
	*/
	private void updateMailbagInULDAtAirport(MailbagVO mailbagVO) {
		log.debug("DSNInULDAtAirport" + " : " + "updateMailbagInULDAtAirport" + " Entering");
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDAtAirportPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDAtAirportPK
				.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
						: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		mailbagInULDAtAirportPK.setAirportCode(mailbagVO.getScannedPort());
		mailbagInULDAtAirportPK.setUldNumber(mailbagVO.getUldNumber());
		mailbagInULDAtAirportPK.setUldNumber(mailbagVO.getContainerNumber());
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
			log.info("THE MAL BAG IS  ASSOCIATED WITH A BARROW");
			mailbagInULDAtAirportPK.setUldNumber(constructBulkULDNumber(mailbagVO.getFinalDestination()));
			log.info("" + "THE MAL BAG IS  ASSOCIATED WITH A BARROW" + " " + mailbagInULDAtAirportPK.getUldNumber());
		}
		try {
			MailbagInULDAtAirport mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAirportPK);
			mailbagInULDAtAirport.setDamageFlag(MailbagVO.FLAG_YES);
		} catch (FinderException finderException) {
			log.debug(" MailbagInULDAtAirport Not existing ");
		}
		log.debug("DSNInULDAtAirport" + " : " + "updateMailbagInULDAtAirport" + " Exiting");
	}

	/** 
	* A-1739
	* @param pou
	* @return
	*/
	private String constructBulkULDNumber(String pou) {
		return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR).append(pou)
				.toString();
	}

	/** 
	* @author A-5991
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
				: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		return mailbagPK;
	}

}
