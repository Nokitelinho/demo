package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailbagForArrivalMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Sep 3, 2016	:	Draft
 */
@Slf4j
public class MailbagForArrivalMultiMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String CLASS = "MailbagForArrivalMultiMapper";
	private static final String SCNPRT = "SCNPRT";

	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet) Added by 			: A-4809 on Sep 3, 2016 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		log.debug(CLASS + " : " + "ResultSet" + " Entering");
		List<ContainerDetailsVO> containerDetails = new ArrayList<ContainerDetailsVO>();
		String currContainerKey = null;
		String prevContainerKey = null;
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		MailbagVO mailbagVO = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		DespatchDetailsVO despatchDetailsVO = null;
		Collection<DespatchDetailsVO> despatches = null;
		Map<String, ArrayList<String>> csgDocForDSN = new HashMap<String, ArrayList<String>>();
		String dsnKey = null;
		while (rs.next()) {
			currContainerKey = new StringBuilder().append(rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(rs.getLong("FLTSEQNUM")).append(rs.getInt("SEGSERNUM"))
					.append(rs.getString("ULDNUM")).toString();
			log.debug("" + "currContainerKey " + " " + currContainerKey);
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				mailbagVOs = new ArrayList<MailbagVO>();
				despatches = new ArrayList<DespatchDetailsVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				containerDetailsVO.setDesptachDetailsVOs(despatches);
				containerDetails.add(containerDetailsVO);
				prevContainerKey = currContainerKey;
			}
			dsnKey = new StringBuilder().append(rs.getString("DSN")).append(rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(rs.getString("MALSUBCLS"))
					.append(rs.getString("MALCTGCOD")).append(rs.getInt("YER")).toString();
			currDSNKey = new StringBuilder().append(currContainerKey).append(dsnKey).toString();
			log.debug("" + "CurrDSNKey " + " " + currDSNKey);
			if (rs.getString("DSN") != null) {
				if (dsnKey != null && dsnKey.trim().length() > 0 && !(csgDocForDSN.containsKey(dsnKey))) {
					ArrayList<String> csgDetails = new ArrayList<String>();
					if ((rs.getString("CSGDOCNUM") != null && rs.getString("CSGDOCNUM").length() > 0)
							&& (rs.getString("POACOD") != null && rs.getString("POACOD").length() > 0)) {
						csgDetails.add(rs.getString("CSGDOCNUM"));
						csgDetails.add(rs.getString("POACOD"));
						csgDocForDSN.put(dsnKey, csgDetails);
					}
				}
				if (!currDSNKey.equals(prevDSNKey)) {
					dsnVO = new DSNVO();
					populateDSNDetails(dsnVO, rs);
					dsnVOs.add(dsnVO);
					prevDSNKey = currDSNKey;
				} else {
					if (rs.getString("CSGDOCNUM") != null && rs.getString("CSGDOCNUM").length() > 0) {
						dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
					}
					if (rs.getString("POACOD") != null && rs.getString("POACOD").length() > 0) {
						dsnVO.setPaCode(rs.getString("POACOD"));
					}
				}
			}
			if (dsnVO != null) {
				if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
					currMailbagKey = rs.getString("MALIDR");
					log.debug("" + "curramilbag key " + " " + currMailbagKey);
					if (currMailbagKey != null && !currMailbagKey.equals(prevMailbagKey)) {
						mailbagVO = new MailbagVO();
						mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
						mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
						mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
						mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
						mailbagVO.setPou(containerDetailsVO.getPou());
						mailbagVO.setPol(containerDetailsVO.getPol());
						mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
						mailbagVO.setContainerType(containerDetailsVO.getContainerType());
						populateMailbagDetails(mailbagVO, rs);
						if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getTransferFlag())) {
							dsnVO.setTransferFlag(MailConstantsVO.FLAG_YES);
						}
						prevMailbagKey = currMailbagKey;
						mailbagVOs.add(mailbagVO);
					}
				}
			}
			if (rs.getString("ULDCSGDOCNUM") != null) {
				currDespatchKey = new StringBuilder().append(currDSNKey).append(rs.getString("ULDCSGDOCNUM"))
						.append(rs.getString("ULDPOACOD")).append(rs.getInt("ULDCSGSEQNUM")).toString();
				log.debug("" + "THE CONSIGNEMENT KEY" + " " + currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO();
					despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
					populateDespatchDetailsForULD(despatchDetailsVO, rs);
					if (MailConstantsVO.FLAG_YES.equals(despatchDetailsVO.getTransferFlag())) {
						dsnVO.setTransferFlag(MailConstantsVO.FLAG_YES);
					}
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				}
			} else if (rs.getString("CONCSGDOCNUM") != null) {
				currDespatchKey = new StringBuilder().append(currDSNKey).append(rs.getString("CONCSGDOCNUM"))
						.append(rs.getString("CONPOACOD")).append(rs.getInt("CONCSGSEQNUM")).toString();
				log.debug("" + "THE CONSIGNEMENT KEY" + " " + currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO();
					despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
					populateDespatchDetailsForBulk(despatchDetailsVO, rs);
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				}
			}
		}
		return containerDetails;
	}

	/** 
	* Method		:	MailbagForArrivalMultiMapper.populateDespatchDetailsForBulk Added by 	:	A-4809 on Oct 1, 2015 Used for 	: Parameters	:	@param despatchDetailsVO Parameters	:	@param rs  Return type	: 	void
	* @throws SQLException 
	*/
	private void populateDespatchDetailsForBulk(DespatchDetailsVO despatchDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		despatchDetailsVO.setAcceptedBags(rs.getInt("CONACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CONACPBAG"));
		if (rs.getTimestamp("CONACPDAT") != null) {
			despatchDetailsVO
					.setAcceptedDate(localDateUtil.getLocalDate(rs.getString("POL"), rs.getTimestamp("CONACPDAT")));
		}
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONACPWGT"))));
		despatchDetailsVO.setPrevAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONACPWGT"))));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getTimestamp("CONCSGDAT") != null) {
			despatchDetailsVO
					.setConsignmentDate(localDateUtil.getLocalDate(rs.getString("POL"), rs.getTimestamp("CONCSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("CONCSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setPltEnabledFlag(rs.getString("PLTENBFLG"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("CONSTDBAG"));
		despatchDetailsVO.setStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONSTDWGT"))));
		despatchDetailsVO.setPrevStatedBags(rs.getInt("CONSTDBAG"));
		despatchDetailsVO.setPrevStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONSTDWGT"))));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("CONPOACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CONCSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setReceivedBags(rs.getInt("CONRCVBAG"));
		despatchDetailsVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONRCVWGT"))));
		despatchDetailsVO.setDeliveredBags(rs.getInt("CONDLVBAG"));
		despatchDetailsVO.setDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONDLVWGT"))));
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("CONRCVBAG"));
		despatchDetailsVO.setPrevReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONRCVWGT"))));
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("CONDLVBAG"));
		despatchDetailsVO.setPrevDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CONDLVBAG"))));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setTransferFlag(rs.getString("CSGCONTRAFLG"));
		despatchDetailsVO.setAirportCode(rs.getString("POU"));
		despatchDetailsVO.setStatedVolume(
				quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("BULKVOL"))));
		despatchDetailsVO.setRemarks(rs.getString("RMKS"));
	}

	/** 
	* Method		:	MailbagForArrivalMultiMapper.populateDespatchDetailsForULD Added by 	:	A-4809 on Oct 1, 2015 Used for 	: Parameters	:	@param despatchDetailsVO Parameters	:	@param rs  Return type	: 	void
	* @throws SQLException 
	*/
	private void populateDespatchDetailsForULD(DespatchDetailsVO despatchDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		despatchDetailsVO.setAcceptedBags(rs.getInt("ULDACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("ULDACPBAG"));
		if (rs.getTimestamp("ULDACPDAT") != null) {
			despatchDetailsVO
					.setAcceptedDate(localDateUtil.getLocalDate(rs.getString("POL"), rs.getTimestamp("ULDACPDAT")));
		}
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDACPWGT"))));
		despatchDetailsVO.setPrevAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDACPWGT"))));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getTimestamp("ULDCSGDAT") != null) {
			despatchDetailsVO
					.setConsignmentDate(localDateUtil.getLocalDate(rs.getString("POL"), rs.getTimestamp("ULDCSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("ULDCSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setPltEnabledFlag(rs.getString("PLTENBFLG"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("ULDSTDBAG"));
		despatchDetailsVO.setStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDSTDWGT"))));
		despatchDetailsVO.setPrevStatedBags(rs.getInt("ULDSTDBAG"));
		despatchDetailsVO.setPrevStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDSTDWGT"))));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("ULDPOACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("ULDCSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setReceivedBags(rs.getInt("ULDRCVBAG"));
		despatchDetailsVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDRCVWGT"))));
		despatchDetailsVO.setDeliveredBags(rs.getInt("ULDDLVBAG"));
		despatchDetailsVO.setDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDDLVWGT"))));
		despatchDetailsVO.setPrevReceivedBags(rs.getInt("ULDRCVBAG"));
		despatchDetailsVO.setPrevReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDRCVWGT"))));
		despatchDetailsVO.setPrevDeliveredBags(rs.getInt("ULDDLVBAG"));
		despatchDetailsVO.setPrevDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ULDDLVBAG"))));
		despatchDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setUldNumber(despatchDetailsVO.getContainerNumber());
		despatchDetailsVO.setTransferFlag(rs.getString("CSGULDTRAFLG"));
		despatchDetailsVO.setAirportCode(rs.getString("POU"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		despatchDetailsVO.setStatedVolume(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDVOL"))));
		despatchDetailsVO.setContainerForInventory(rs.getString("INVCONNUM"));
		despatchDetailsVO.setContainerTypeAtAirport(rs.getString("CONTYP"));
	}

	/** 
	* Method		:	MailbagForArrivalMultiMapper.populateMailbagDetails Added by 	:	A-4809 on Oct 1, 2015 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param rs  Return type	: 	void
	*/
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString("MALIDR");
		mailbagVO.setMailbagId(mailbagId);
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
		mailbagVO.setTransferFlag(rs.getString("MALTRAFLG"));
		mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("VOL"))));
		mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		mailbagVO.setMraStatus(rs.getString("MALMRASTA"));
		mailbagVO.setScannedPort(rs.getString(SCNPRT));
		mailbagVO.setSealNumber(rs.getString("SELNUM"));
		mailbagVO.setArrivalSealNumber(rs.getString("ARRSELNUM"));
		if (rs.getTimestamp("MALLSTUPDTIM") != null) {
			mailbagVO.setLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("MALLSTUPDTIM")));
		}
		mailbagVO.setAcceptanceFlag(rs.getString("MALACPSTA"));
		mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
		mailbagVO.setMailStatus(rs.getString("MALSTA"));
		mailbagVO.setFromFightNumber(rs.getString("MALFLTNUM"));
		mailbagVO.setFromCarrierId(rs.getInt("MALFLTCARIDR"));
		mailbagVO.setFromFlightSequenceNumber(rs.getLong("MALFLTSEQNUM"));
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		if (Objects.nonNull(rs.getDate("SCNDAT")) && Objects.nonNull(rs.getString(SCNPRT))) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(rs.getString(SCNPRT), true));
		}
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setDestination(rs.getString("MALDSTCOD"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setContainerNumber(rs.getString("MALCONNUM"));
		mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
	}

	/** 
	* Method		:	MailbagForArrivalMultiMapper.populateDSNDetails Added by 	:	A-4809 on Oct 1, 2015 Used for 	: Parameters	:	@param dsnVO Parameters	:	@param rs  Return type	: 	void
	* @throws SQLException 
	*/
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		dsnVO.setBags(rs.getInt("ACPBAG"));
		dsnVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT"))));
		dsnVO.setReceivedBags(rs.getInt("DSNRCVBAG"));
		dsnVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSNRCVWGT"))));
		dsnVO.setDeliveredBags(rs.getInt("DLVBAG"));
		dsnVO.setDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DLVWGT"))));
		dsnVO.setPrevReceivedBags(rs.getInt("DSNRCVBAG"));
		dsnVO.setPrevReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSNRCVWGT"))));
		dsnVO.setPrevDeliveredBags(rs.getInt("DLVBAG"));
		dsnVO.setPrevDeliveredWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DLVWGT"))));
		dsnVO.setPol(rs.getString("ORGEXGOFC").substring(2, 5));
		dsnVO.setDestination(rs.getString("DSTEXGOFC").substring(2, 5));
		if (rs.getString("CSGDOCNUM") != null) {
			dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		} else {
			dsnVO.setCsgDocNum(rs.getString("CONCSGDOCNUM"));
		}
		if (rs.getString("POACOD") != null) {
			dsnVO.setPaCode(rs.getString("POACOD"));
		} else {
			dsnVO.setPaCode(rs.getString("CONPOACOD"));
		}
		if (rs.getDate("CONCSGDAT") != null) {
			dsnVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate("CONCSGDAT")));
		}
		dsnVO.setCsgSeqNum(rs.getInt("CONCSGSEQNUM"));
		if (rs.getDate("CONACPDAT") != null) {
			dsnVO.setAcceptedDate(localDateUtil.getLocalDate(null, rs.getDate("CONACPDAT")));
		}
		if (rs.getDate("CONRCVDAT") != null) {
			dsnVO.setReceivedDate(localDateUtil.getLocalDate(null, rs.getDate("CONRCVDAT")));
		}
		if (rs.getInt("TRFBAG") >= 0) {
			dsnVO.setTransferredPieces(rs.getInt("TRFBAG"));
		}
		if (rs.getDouble("TRFWGT") >= 0) {
			dsnVO.setTransferredWeight(
					quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TRFWGT"))));
		}
		dsnVO.setRemarks(rs.getString("RMKS"));
		dsnVO.setCurrencyCode(rs.getString("CURCOD"));
		dsnVO.setMailrate(rs.getDouble("MALRAT"));
	}

	/** 
	* Method		:	MailbagForArrivalMultiMapper.populateContainerDetails Added by 	:	A-4809 on Oct 1, 2015 Used for 	: Parameters	:	@param containerDetailsVO Parameters	:	@param rs  Return type	: 	void
	* @throws SQLException 
	*/
	private void populateContainerDetails(ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setFlightStatus(rs.getString("CLSFLG"));
		containerDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		containerDetailsVO.setTotalWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("BAGWGT"))));
		containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		containerDetailsVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT"))));
		containerDetailsVO.setArrivedStatus(rs.getString("CONARRSTA"));
		containerDetailsVO.setDeliveredStatus(rs.getString("DLVFLG"));
		if (containerDetailsVO.getContainerNumber().startsWith(MailConstantsVO.CONST_BULK)) {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		}
		containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerDetailsVO.setTransferFlag(rs.getString("CONTRAFLG"));
		containerDetailsVO.setReleasedFlag(rs.getString("RELFLG"));
		containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
		containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		if (rs.getTimestamp("CONLSTUPDTIM") != null) {
			containerDetailsVO.setLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("CONLSTUPDTIM")));
		}
		if (rs.getTimestamp("SEGLSTUPDTIM") != null) {
			containerDetailsVO.setUldLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("SEGLSTUPDTIM")));
		}
		containerDetailsVO.setIntact(rs.getString("INTFLG"));
		containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
		containerDetailsVO.setPaCode(rs.getString("SBCODE"));
		if (rs.getString("REMARK") != null) {
			containerDetailsVO.setRemarks(rs.getString("REMARK"));
		}
	}
}
