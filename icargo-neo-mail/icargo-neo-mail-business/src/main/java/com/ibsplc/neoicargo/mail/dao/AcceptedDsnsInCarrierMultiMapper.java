package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/** 
 * @author a-1936This class is used to map the ResultSet into Vos...
 */
@Slf4j
public class AcceptedDsnsInCarrierMultiMapper implements MultiMapper<ContainerDetailsVO> {
	/** 
	* @author a-1936
	* @param rs
	* @throws SQLException
	* @return
	*/
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		String currDespatchKey = null;
		String prevDespatchKey = null;
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		String currMailbagKey = null;
		String prevMailbagKey = null;
		String dmgMailbagKey = null;
		String mailType = "M";
		String currDmgMailbagKey = null;
		DespatchDetailsVO despatchDetailsVO = null;
		Collection<DespatchDetailsVO> despatches = new ArrayList<DespatchDetailsVO>();
		Map<String, MailbagVO> mailbagMap = new LinkedHashMap<String, MailbagVO>();
		Map<String, DamagedMailbagVO> damagedMailbagMap = new LinkedHashMap<String, DamagedMailbagVO>();
		containerDetailsVOsList.add(containerDetailsVO);
		Quantity totalWeight = quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.ZERO);
		while (rs.next()) {
			if (containerDetailsVO.getContainerNumber() == null) {
				containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
			}
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
					containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags() + 1);
						if (containerDetailsVO.getTotalWeight() == null) {
							containerDetailsVO.setTotalWeight(
									quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
						}
						totalWeight = totalWeight.add(
								(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")))));
						if(Objects.nonNull(rs.getString("DSPWGTUNT"))) {
							containerDetailsVO
									.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
											totalWeight.getValue(), rs.getString("DSPWGTUNT")));
						} else{
							containerDetailsVO
									.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT,
											totalWeight.getValue()));
						}

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
		despatchDetailsVO.setAcceptedBags(rs.getInt("CSGACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CSGACPBAG"));
		if (rs.getDate("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(localDateUtil.getLocalDate(rs.getString("ASGPRT"), rs.getDate("ACPDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGACPWGT"))));
		despatchDetailsVO.setPrevAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGACPWGT"))));
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
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGSTDWGT"))));
		despatchDetailsVO.setPrevStatedBags(rs.getInt("CSGSTDBAG"));
		despatchDetailsVO.setPrevStatedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGSTDWGT"))));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setStatedVolume(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDVOL"))));
		despatchDetailsVO.setAcceptedVolume(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPVOL"))));
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
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.ZERO);
		if(Objects.nonNull(rs.getString("DSPWGTUNT"))) {
			wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSPWGT")),
					BigDecimal.valueOf(rs.getDouble("CSGACPWGT")), rs.getString("DSPWGTUNT"));
		} else{
			wgt = quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(rs.getDouble("CSGACPWGT")));
		}
		mailbagVO.setWeight(wgt);
		if (rs.getDate("SCNDAT") != null) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(rs.getString("ASGPRT"), rs.getTimestamp("SCNDAT")));
		}
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setMailStatus(rs.getString("MALSTA"));
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setScannedUser(rs.getString("SCNUSR"));
		mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
		mailbagVO.setContainerType(rs.getString("CONTYP"));
		mailbagVO.setPou(rs.getString("POU"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
		mailbagVO.setFinalDestination(rs.getString("POU"));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setTransferFromCarrier(rs.getString("MALTRFCAR"));
		if(Objects.nonNull(rs.getString("VOLUNT"))) {
			mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("VOL")),
					BigDecimal.valueOf(0.0), rs.getString("VOLUNT")));
		} else{
			mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(rs.getDouble("VOL"))));
		}
		Timestamp lastUpdateTime = rs.getTimestamp("MALLSTUPDTIM");
		if (lastUpdateTime != null) {
			mailbagVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lastUpdateTime));
		}
		mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		mailbagVO.setBellyCartId(rs.getString("BLYCRTIDR"));
		mailbagVO.setSealNumber(rs.getString("SELNUM"));
		mailbagVO.setSealNumber(rs.getString("ARRSELNUM"));
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		if (rs.getString("DMGCOD") != null) {
			currDmgMailbagKey = new StringBuilder().append(mailbagId).append(rs.getString("DMGCOD")).toString();
			DamagedMailbagVO damagedMailbagVO = populateDamageDetails(rs);
			damagedVOs.add(damagedMailbagVO);
			mailbagVO.setDamagedMailbags(damagedVOs);
		}
		mailbagVO.setAcceptancePostalContainerNumber(rs.getString("ACPPOACONNUM"));
		mailbagVO.setAcceptanceAirportCode(rs.getString("ACPARPCOD"));
		mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
		mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
		mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
	}

	private DamagedMailbagVO populateDamageDetails(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
		damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));
		damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
		damagedMailbagVO.setDamageDate(localDateUtil.getLocalDate(null, rs.getTimestamp("DMGDAT")));
		damagedMailbagVO.setRemarks(rs.getString("RMK"));
		return damagedMailbagVO;
	}
}
