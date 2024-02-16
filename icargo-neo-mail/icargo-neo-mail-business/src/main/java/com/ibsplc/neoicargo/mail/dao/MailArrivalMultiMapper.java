package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

/** 
 * The mapper for fetching arrival details of a flight
 * @author A-1739
 */
@Slf4j
public class MailArrivalMultiMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String MALIDR = "MALIDR";
	private static final String ULD_NUMBER = "ULDNUM";

	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		List<ContainerDetailsVO> containerDetails = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		String currMailKey = null;
		String prevMailKey = null;
		int prevRoutingNum = 0;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		MailbagVO mailbagVO = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		DespatchDetailsVO despatchDetailsVO = null;
		Collection<DespatchDetailsVO> despatches = null;
		containerDetails = new ArrayList<ContainerDetailsVO>();
		Map<String, ArrayList<String>> csgDocForDSN = new HashMap<String, ArrayList<String>>();
		String childId = "";
		String dsnKey = null;
		String prevChildId = "";
		StringBuilder onwardFlightBuilder = null;
		String pou = null;
		String flightNumber = null;
		String carrierCode = null;
		while (rs.next()) {
			currContainerKey = new StringBuilder().append(rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(rs.getLong("FLTSEQNUM")).append(rs.getInt("SEGSERNUM"))
					.append(rs.getString(ULD_NUMBER)).toString();
			log.debug("" + "currContainerKey " + " " + currContainerKey);
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				prevRoutingNum = 0;
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				mailbagVOs = new ArrayList<MailbagVO>();
				despatches = new ArrayList<DespatchDetailsVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				containerDetailsVO.setDesptachDetailsVOs(despatches);
				containerDetails.add(containerDetailsVO);
				prevContainerKey = currContainerKey;
				onwardFlightBuilder = null;
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
					prevMailKey = rs.getString(MALIDR);
				} else {
					if (rs.getString("CSGDOCNUM") != null && rs.getString("CSGDOCNUM").length() > 0) {
						dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
					}
					if (rs.getString("POACOD") != null && rs.getString("POACOD").length() > 0) {
						dsnVO.setPaCode(rs.getString("POACOD"));
					}
					currMailKey = rs.getString(MALIDR);
					if (currMailKey != null && !currMailKey.equals(prevMailKey)) {
						dsnVO.setBags(dsnVO.getBags() + rs.getInt("ACPBAG"));
						try {
							dsnVO.setWeight(dsnVO.getWeight().add(quantities
									.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT")))));
						} finally {
						}
						dsnVO.setReceivedBags(dsnVO.getReceivedBags() + rs.getInt("DSNRCVBAG"));
						try {
							dsnVO.setReceivedWeight(dsnVO.getReceivedWeight().add(quantities
									.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSNRCVWGT")))));
						} finally {
						}
						dsnVO.setDeliveredBags(dsnVO.getDeliveredBags() + rs.getInt("DLVBAG"));
						try {
							dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight().add(quantities
									.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DLVWGT")))));
						} finally {
						}
						dsnVO.setPrevReceivedBags(dsnVO.getPrevReceivedBags() + rs.getInt("DSNRCVBAG"));
						try {
							dsnVO.setPrevReceivedWeight(dsnVO.getPrevReceivedWeight()
									.add(quantities.getQuantity(Quantities.MAIL_WGT,
											BigDecimal.valueOf(rs.getDouble("DSNRCVWGT")))));
						} finally {
						}
						dsnVO.setPrevDeliveredBags(dsnVO.getPrevDeliveredBags() + rs.getInt("DLVBAG"));
						try {
							dsnVO.setPrevDeliveredWeight(dsnVO.getPrevDeliveredWeight()
									.add(quantities.getQuantity(Quantities.MAIL_WGT,
											BigDecimal.valueOf(rs.getDouble("DLVWGT")))));
						} finally {
						}
						prevMailKey = currMailKey;
					} else {
						prevMailKey = currMailKey;
					}
				}
			}
			if (dsnVO != null && MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())
					&& Objects.nonNull(rs.getString(MALIDR))) {
				currMailbagKey = rs.getString(ULD_NUMBER) + rs.getString(MALIDR);
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
			log.debug("" + "RTGSERNUM :" + " " + rs.getInt("RTGSERNUM"));
			if (rs.getInt("RTGSERNUM") > 0) {
				childId = new StringBuffer(currContainerKey).append(rs.getString("RTGSERNUM")).toString();
				log.debug("" + "THE CHILD ID IS FOUND TO BE " + " " + childId);
				int currRoutingNum = rs.getInt("RTGSERNUM");
				if (!childId.equals(prevChildId)) {
					if (currRoutingNum > prevRoutingNum) {
						flightNumber = rs.getString("ONWFLTNUM");
						pou = rs.getString("RTGPOU");
						carrierCode = rs.getString("ONWFLTCARCOD");
						if (rs.getString("ONWFLTDAT") != null && flightNumber != null
								&& flightNumber.trim().length() > 0 && carrierCode != null
								&& carrierCode.trim().length() > 0 && pou != null && pou.trim().length() > 0) {
							if (onwardFlightBuilder == null) {
								onwardFlightBuilder = new StringBuilder();
							} else {
								onwardFlightBuilder.append(",");
							}
							onwardFlightBuilder.append(carrierCode).append("-").append(flightNumber).append("-")
									.append(rs.getString("ONWFLTDAT")).append("-").append(pou);
						}
						if (onwardFlightBuilder != null && !currContainerKey.contains("BULK")) {
							containerDetailsVO.setOnwardFlights(onwardFlightBuilder.toString());
						}
						prevChildId = childId;
						prevRoutingNum = currRoutingNum;
					}
				}
			}
			if (rs.getString("ULDCSGDOCNUM") != null
					&& MailConstantsVO.CARDITENQ_MODE_DESP.equals(rs.getString("MALTYP"))) {
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
			} else if (rs.getString("CONCSGDOCNUM") != null
					&& MailConstantsVO.CARDITENQ_MODE_DESP.equals(rs.getString("MALTYP"))) {
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
			Date date = rs.getDate("FLTDAT");
			if (date != null) {
				containerDetailsVO.setFlightDate(localDateUtil.getLocalDate(null, date));
			}
		}
		autoSuggestConsignmentForDSN(containerDetails, csgDocForDSN);
		return containerDetails;
	}

	/** 
	* @param containerDetails
	* @param csgDocForDSN
	*/
	private void autoSuggestConsignmentForDSN(List<ContainerDetailsVO> containerDetails,
			Map<String, ArrayList<String>> csgDocForDSN) {
		if (containerDetails != null) {
			String dsnKey = null;
			for (ContainerDetailsVO cntDetails : containerDetails) {
				for (DSNVO dsnVO : cntDetails.getDsnVOs()) {
					if (dsnVO.getCsgDocNum() == null
							|| (dsnVO.getCsgDocNum() != null && dsnVO.getCsgDocNum().length() == 0)) {
						dsnKey = new StringBuilder().append(dsnVO.getDsn()).append(dsnVO.getOriginExchangeOffice())
								.append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailSubclass())
								.append(dsnVO.getMailCategoryCode()).append(String.valueOf(dsnVO.getYear())).toString();
						if (csgDocForDSN != null && csgDocForDSN.size() > 0 && csgDocForDSN.containsKey(dsnKey)) {
							ArrayList<String> csgDetails = csgDocForDSN.get(dsnKey);
							if (csgDetails != null) {
								dsnVO.setCsgDocNum(csgDetails.get(0));
								dsnVO.setPaCode(csgDetails.get(1));
							}
						}
					}
				}
			}
		}
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @param rs
	* @throws SQLException
	*/
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Collection<DamagedMailbagVO> damagedVOs = new ArrayList<DamagedMailbagVO>();
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString(MALIDR);
		mailbagVO.setMailbagId(mailbagId);
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
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
		if (rs.getTimestamp("SCNDAT") != null) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(rs.getString("POU"), rs.getTimestamp("SCNDAT")));
		}
		if (rs.getTimestamp("SCNDATTIME") != null) {
			mailbagVO.setLatestScannedDate(
					localDateUtil.getLocalDate(rs.getString("POU"), rs.getTimestamp("SCNDATTIME")));
		}
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setSealNumber(rs.getString("SELNUM"));
		mailbagVO.setArrivalSealNumber(rs.getString("ARRSELNUM"));
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		if (rs.getTimestamp("MALLSTUPDTIM") != null) {
			mailbagVO.setLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("MALLSTUPDTIM")));
		}
		mailbagVO.setAcceptanceFlag(rs.getString("MALACPSTA"));
		mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
		mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		mailbagVO.setMailStatus(rs.getString("MALSTA"));
		mailbagVO.setFromFightNumber(rs.getString("MALFLTNUM"));
		mailbagVO.setFromCarrierId(rs.getInt("MALFLTCARIDR"));
		mailbagVO.setFromFlightSequenceNumber(rs.getLong("MALFLTSEQNUM"));
		mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
		mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
		mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
		if (rs.getString("INVULDNUM") != null) {
			if ((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM")))
					&& !(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK))) {
				mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
			} else {
				mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
			}
		}
		if (rs.getString("DMGCOD") != null) {
			DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
			damagedVOs.add(damagedMailbagVO);
			mailbagVO.setDamagedMailbags(damagedVOs);
		}
		Date date = rs.getDate("FLTDAT");
		if (date != null) {
			mailbagVO.setFlightDate(localDateUtil.getLocalDate(null, date));
		}
		mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
		mailbagVO.setScanningWavedFlag(rs.getString("SCNWVDFLG"));
		mailbagVO.setDestination(rs.getString("DSTCOD"));
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
	}

	private DamagedMailbagVO populateDamageDetails(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
		damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
		damagedMailbagVO.setDamageDate(localDateUtil.getLocalDate(null, rs.getTimestamp("DMGDAT")));
		damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));
		damagedMailbagVO.setRemarks(rs.getString("RMK"));
		return damagedMailbagVO;
	}

	/** 
	* A-1739
	* @param dsnVO
	* @param rs
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
		dsnVO.setPol(rs.getString("ORGCOD"));
		dsnVO.setDestination(rs.getString("DSTCOD"));
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
		dsnVO.setUbrNumber(rs.getString("UBRNUM"));
		dsnVO.setCurrencyCode(rs.getString("CURCOD"));
		dsnVO.setMailrate(rs.getDouble("MALRAT"));
		Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
		Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
		ZonedDateTime bookingLastUpdateTime = localDateUtil.getLocalDate(null, true);
		if (bookingUpdateTime != null && bookingFlightDetailUpdateTime != null) {
			if (bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
				bookingLastUpdateTime = localDateUtil.getLocalDate(null, bookingUpdateTime);
			} else {
				bookingLastUpdateTime = localDateUtil.getLocalDate(null, bookingFlightDetailUpdateTime);
			}
			if (bookingLastUpdateTime != null) {
				dsnVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				dsnVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			}
		}
		if (rs.getString("RTGAVL") != null && "Y".equals(rs.getString("RTGAVL"))) {
			dsnVO.setRoutingAvl("Y");
		}
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setDocumentOwnerCode(rs.getString("SHPPFX"));
		dsnVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		dsnVO.setSequenceNumber(rs.getInt("SEQNUM"));
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @param rs
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
		containerDetailsVO.setContainerNumber(rs.getString(ULD_NUMBER));
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
		containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
	}

	/** 
	* This method is used to find the DespatchDetails For the ULD.
	* @param despatchDetailsVO
	* @param rs
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
		despatchDetailsVO.setContainerNumber(rs.getString(ULD_NUMBER));
		despatchDetailsVO.setUldNumber(despatchDetailsVO.getContainerNumber());
		despatchDetailsVO.setTransferFlag(rs.getString("CSGULDTRAFLG"));
		despatchDetailsVO.setAirportCode(rs.getString("POU"));
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		despatchDetailsVO.setStatedVolume(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDVOL"))));
		despatchDetailsVO.setContainerForInventory(rs.getString("INVCONNUM"));
		if (rs.getString("INVULDNUM") != null) {
			if ((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM")))
					&& !(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK))) {
				despatchDetailsVO.setContainerTypeAtAirport(MailConstantsVO.ULD_TYPE);
			} else {
				despatchDetailsVO.setContainerTypeAtAirport(MailConstantsVO.BULK_TYPE);
			}
		}
	}

	/** 
	* This method is used to find the DespatchDetails For the ULD.
	* @param despatchDetailsVO
	* @param rs
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
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("BULKVOL"))));
		despatchDetailsVO.setRemarks(rs.getString("RMKS"));
	}
}
