package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DamagedMailbagVO;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author a-1936This class is used to map the ResultSet into Vos returned to the Request...
 */
@Slf4j
public class AcceptedDsnsInFlightMultiMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String DSPWGTUNT = "DSPWGTUNT";
	private static final String CSGACPWGT = "CSGACPWGT";
	private static final String MSTDOCNUM = "MSTDOCNUM";
	private static final String SHPPFX = "SHPPFX";

	/** 
	* @author a-1936
	* @param rs 
	* @throws SQLException
	* @return
	*/
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("AcceptedDsnsInFlightMultiMapper" + " : " + "map(ResultSet rs" + " Entering");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVOsList.add(containerDetailsVO);
		String currDespatchKey = null;
		String prevDespatchKey = null;
		MailbagVO mailbagVO = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		String mailType = "M";
		DespatchDetailsVO despatchDetailsVO = null;
		String currDmgMailbagKey = null;
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatches = new ArrayList<DespatchDetailsVO>();
		Map<String, MailbagVO> mailbagMap = new LinkedHashMap<String, MailbagVO>();
		Map<String, DamagedMailbagVO> damagedMailbagMap = new LinkedHashMap<String, DamagedMailbagVO>();
		Quantity totalWeight = quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.ZERO, BigDecimal.ZERO, "K");
		Quantity receivedWeight = null;
		Quantity deliveredWeight = null;
		while (rs.next()) {
			if (containerDetailsVO.getContainerNumber() == null) {
				containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
				containerDetailsVO.setContainerType(rs.getString("CONTYP"));
				containerDetailsVO.setContentId(rs.getString("CNTIDR"));
				containerDetailsVO.setPou(rs.getString("POU"));
				containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			}
			containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
			containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
			containerDetailsVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			containerDetailsVO.setPol(rs.getString("ASGPRT"));
			containerDetailsVO.setContainerType(rs.getString("CONTYP"));
			containerDetailsVO.setUldFulIndFlag(rs.getString("ULDFULIND"));
			containerDetailsVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
			String csgDocNum = rs.getString("CSGDOCNUM");
			mailType = rs.getString("MALTYP");
			log.debug("" + "csgDocNum" + " " + csgDocNum);
			if ("D".equals(mailType)) {
				currDespatchKey = new StringBuilder().append(rs.getString("DSN")).append(rs.getString("ORGEXGOFC"))
						.append(rs.getString("DSTEXGOFC")).append(rs.getString("MALSUBCLS"))
						.append(rs.getString("MALCTGCOD")).append(rs.getInt("YER")).append(csgDocNum)
						.append(rs.getString("POACOD")).append(rs.getInt("CSGSEQNUM")).toString();
				log.debug("" + "THE CONSIGNEMENT KEY" + " " + currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO();
					populateDespatchDetails(despatchDetailsVO, rs);
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				}
			}
			String mailbagId = rs.getString("MALIDR");
			if (mailbagId != null) {
				currMailbagKey = mailbagId;
				log.debug("" + "currMailbagKey " + " " + currMailbagKey);
				if (currMailbagKey != null && !mailbagMap.containsKey(currMailbagKey)) {
					log.debug("" + "curramilbag key " + " " + currMailbagKey);
					mailbagVO = new MailbagVO();
					populateMailbagDetails(mailbagVO, rs);
					mailbagMap.put(currMailbagKey, mailbagVO);
					mailbagVOs.add(mailbagVO);
					if (rs.getInt("RCVBAG") != 0) {
						containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags() + 1);
					}
					try {
						if (containerDetailsVO.getReceivedWeight() == null) {
							containerDetailsVO.setReceivedWeight(
									quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
						}
						if (rs.getInt("RCVBAG") != 0) {
							if(Objects.nonNull(receivedWeight)) {
								receivedWeight = receivedWeight.add((quantities.getQuantity(Quantities.MAIL_WGT,
										BigDecimal.valueOf(rs.getDouble("WGT")),
										BigDecimal.valueOf(rs.getDouble("WGT")), "K")));
							}else {
								receivedWeight =(quantities.getQuantity(Quantities.MAIL_WGT,
										BigDecimal.valueOf(rs.getDouble("WGT")),
										BigDecimal.valueOf(rs.getDouble("WGT")), "K"));
							}
							containerDetailsVO.setReceivedWeight(quantities.getQuantity(Quantities.MAIL_WGT,
									BigDecimal.valueOf(0.0), (receivedWeight.getValue()),
									rs.getString(DSPWGTUNT)));
						}
					} finally {
					}
					if (rs.getInt("DLVBAG") != 0) {
						containerDetailsVO.setDeliveredBags(containerDetailsVO.getDeliveredBags() + 1);
					}
					try {
						if (containerDetailsVO.getDeliveredWeight() == null) {
							containerDetailsVO.setDeliveredWeight(
									quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
						}
						if (rs.getInt("DLVBAG") != 0) {
							if(Objects.nonNull(deliveredWeight)) {
								deliveredWeight = deliveredWeight.add((quantities.getQuantity(Quantities.MAIL_WGT,
										BigDecimal.valueOf(rs.getDouble("WGT")),
										BigDecimal.valueOf(rs.getDouble("WGT")), "K")));
							}
							else{
								deliveredWeight =quantities.getQuantity(Quantities.MAIL_WGT,
										BigDecimal.valueOf(rs.getDouble("WGT")),
										BigDecimal.valueOf(rs.getDouble("WGT")), "K");
							}
							containerDetailsVO.setDeliveredWeight(quantities.getQuantity(Quantities.MAIL_WGT,
									BigDecimal.valueOf(0.0), deliveredWeight.getValue(),
									rs.getString(DSPWGTUNT)));
						}
					} finally {
					}
					containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags() + 1);
						if (containerDetailsVO.getTotalWeight() == null) {
							containerDetailsVO.setTotalWeight(
									quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
						}
						totalWeight = totalWeight.add(
								(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")),
										BigDecimal.valueOf(rs.getDouble("WGT")), "K")));
						containerDetailsVO
								.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
										totalWeight.getValue(), rs.getString(DSPWGTUNT)));

				} else {
					if (rs.getString("DMGCOD") != null) {
						currDmgMailbagKey = new StringBuilder().append(currMailbagKey).append(rs.getString("DMGCOD"))
								.toString();
						mailbagVO = mailbagMap.get(currMailbagKey);
						if (!damagedMailbagMap.containsKey(currDmgMailbagKey)) {
							if (mailbagVO.getDamagedMailbags() != null && mailbagVO.getDamagedMailbags().size() > 0) {
								DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
								mailbagVO.getDamagedMailbags().add(damagedMailbagVO);
							}
						}
					}
				}
			}
		}
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setDesptachDetailsVOs(despatches);
		log.debug("" + "THE container Details Vo Finally " + " " + containerDetailsVO);
		return containerDetailsVOsList;
	}

	/** 
	* A-1936
	* @param despatchDetailsVO
	* @param rs
	* @throws SQLException
	*/
	private void populateDespatchDetails(DespatchDetailsVO despatchDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String currentPort = rs.getString("ASGPRT");
		despatchDetailsVO.setAcceptedBags(rs.getInt("CSGACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CSGACPBAG"));
		if (rs.getDate("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(localDateUtil.getLocalDate(currentPort, rs.getDate("ACPDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble(CSGACPWGT)),
						BigDecimal.valueOf(rs.getDouble(CSGACPWGT)), "K"));
		despatchDetailsVO.setPrevAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble(CSGACPWGT)),
						BigDecimal.valueOf(rs.getDouble(CSGACPWGT)), "K"));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getDate("CSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate("CSGDAT")));
		}
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("CSGSTDBAG"));
		despatchDetailsVO.setStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGSTDWGT")),
						BigDecimal.valueOf(rs.getDouble("CSGSTDWGT")), "K"));
		despatchDetailsVO.setPrevStatedBags(rs.getInt("CSGSTDBAG"));
		despatchDetailsVO.setPrevStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGSTDWGT")),
						BigDecimal.valueOf(rs.getDouble("CSGSTDWGT")),"K"));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO
				.setStatedVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("STDVOL"))));
		despatchDetailsVO.setAcceptedVolume(
				quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("ACPVOL"))));
	}

	/** 
	* A-1936
	* @param mailbagVO
	* @param rs
	* @throws SQLException
	*/
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Collection<DamagedMailbagVO> damagedVOs = new ArrayList<DamagedMailbagVO>();
		String currDmgMailbagKey = null;
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString("MALIDR");
		mailbagVO.setMailbagId(mailbagId);
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSPWGT")),
				BigDecimal.valueOf(rs.getDouble(CSGACPWGT)), rs.getString(DSPWGTUNT)));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		String scannedPort = rs.getString("ASGPRT");
		if (rs.getTimestamp("SCNDAT") != null) {
			Timestamp scandateAndTime = rs.getTimestamp("SCNDAT");
			if (scandateAndTime != null && scannedPort != null) {
				mailbagVO.setScannedDate(localDateUtil.getLocalDate(scannedPort, scandateAndTime));
			}
		}
		mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		mailbagVO.setScannedPort(scannedPort);
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setTransferFromCarrier(rs.getString("MALFRMCARCOD"));
		mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("VOL")),
				BigDecimal.valueOf(0.0), rs.getString("VOLUNT")));
		Timestamp lastUpdateTime = rs.getTimestamp("MALLSTUPDTIM");
		if (lastUpdateTime != null) {
			mailbagVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lastUpdateTime));
		}
		mailbagVO.setConsignmentNumber(rs.getString("MALCSGDOCNUM"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("MALCSGDOCSEQ"));
		mailbagVO.setPaCode(rs.getString("MALPOACOD"));
		mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
		mailbagVO.setBellyCartId(rs.getString("BLYCRTIDR"));
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setMailStatus(rs.getString("MALSTA"));
		mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
		mailbagVO.setSealNumber(rs.getString("SELNUM"));
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setSealNumber(rs.getString("ARRSELNUM"));
		if (rs.getString("DMGCOD") != null) {
			currDmgMailbagKey = new StringBuilder().append(mailbagId).append(rs.getString("DMGCOD")).toString();
			DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
			damagedVOs.add(damagedMailbagVO);
			mailbagVO.setDamagedMailbags(damagedVOs);
		}
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setDestination(rs.getString("DSTCOD"));
		mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		mailbagVO.setAcceptancePostalContainerNumber(rs.getString("ACPPOACONNUM"));
		mailbagVO.setAcceptanceAirportCode(rs.getString("ACPARPCOD"));
		mailbagVO.setDocumentNumber(rs.getString(MSTDOCNUM));
		mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		mailbagVO.setShipmentPrefix(rs.getString(SHPPFX));
		mailbagVO.setSecurityStatusCode(rs.getString("SECSTACOD"));
		if (null != rs.getString(SHPPFX) && null != rs.getString(MSTDOCNUM)) {
			mailbagVO.setAwbNumber(rs.getString(SHPPFX) + "-" + rs.getString(MSTDOCNUM));
		}
		mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
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
}
