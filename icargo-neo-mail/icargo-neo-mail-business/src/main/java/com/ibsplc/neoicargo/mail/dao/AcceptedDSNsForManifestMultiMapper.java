package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** 
 * This methosd is used to map the Result set into the Despatch Details and the Mail bag Details Respectively.
 * @author a-1936
 */
@Slf4j
public class AcceptedDSNsForManifestMultiMapper implements MultiMapper<ContainerDetailsVO> {
	/** 
	* @author a-1936
	* @param rs
	* @throws SQLException
	* @return
	*/
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("AcceptedDsnsInFlightMultiMapper" + " : " + "map(ResultSet rs" + " Entering");
		List<ContainerDetailsVO> containerDetails = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		MailbagVO mailbagVO = null;
		DSNVO dsnVo = null;
		DespatchDetailsVO despatchDetailsVO = null;
		Collection<MailbagVO> mailbagVOs = null;
		Collection<DSNVO> dsnVOs = null;
		Collection<DespatchDetailsVO> despatches = null;
		while (rs.next()) {
			currContainerKey = rs.getString("ULDNUM");
			log.debug("" + "currContainerKey " + " " + currContainerKey);
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
				dsnVOs = new ArrayList<DSNVO>();
				despatches = new ArrayList<DespatchDetailsVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				containerDetailsVO.setDesptachDetailsVOs(despatches);
				containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerDetails.add(containerDetailsVO);
				prevContainerKey = currContainerKey;
			}
			currDSNKey = new StringBuilder().append(currContainerKey).append(rs.getString("DSN"))
					.append(rs.getString("ORGEXGOFC")).append(rs.getString("DSTEXGOFC"))
					.append(rs.getString("MALSUBCLS")).append(rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
					.toString();
			log.debug("" + "CurrDSNKey " + " " + currDSNKey);
			if (!currDSNKey.equals(prevDSNKey)) {
				dsnVo = new DSNVO();
				populateDSNDetails(dsnVo, rs);
				mailbagVOs = new ArrayList<MailbagVO>();
				dsnVo.setMailbags(mailbagVOs);
				dsnVOs.add(dsnVo);
				prevDSNKey = currDSNKey;
			}
			currMailbagKey = rs.getString("MALIDR");
			if (currMailbagKey != null) {
				log.debug("" + "curramilbag key " + " " + currMailbagKey);
				if (currMailbagKey != null && !currMailbagKey.equals(prevMailbagKey)) {
					mailbagVO = new MailbagVO();
					populateMailbagDetails(mailbagVO, rs);
					prevMailbagKey = currMailbagKey;
					mailbagVOs.add(mailbagVO);
				}
			}
			String csgDocNum = rs.getString("CSGDOCNUM");
			log.debug("" + "csgDocNum" + " " + csgDocNum);
			if (csgDocNum != null) {
				currDespatchKey = new StringBuilder().append(currDSNKey).append(csgDocNum)
						.append(rs.getString("POACOD")).append(rs.getInt("CSGSEQNUM")).toString();
				log.debug("" + "THE CONSIGNEMENT KEY" + " " + currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO();
					populateDespatchDetails(despatchDetailsVO, rs);
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				} else {
					despatchDetailsVO.setStatedBags(despatchDetailsVO.getStatedBags() + rs.getInt("CSGSTDBAG"));
					despatchDetailsVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(despatchDetailsVO.getStatedWeight().getRoundedValue().doubleValue()
									+ rs.getDouble("CSGSTDWGT"))));
					despatchDetailsVO.setPrevStatedBags(despatchDetailsVO.getPrevStatedBags() + rs.getInt("CSGSTDBAG"));
					despatchDetailsVO.setPrevStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(despatchDetailsVO.getPrevStatedWeight().getRoundedValue().doubleValue()
									+ rs.getDouble("CSGSTDWGT"))));
					despatchDetailsVO.setAcceptedBags(despatchDetailsVO.getAcceptedBags() + rs.getInt("CSGACPBAG"));
					despatchDetailsVO
							.setPrevAcceptedBags(despatchDetailsVO.getPrevAcceptedBags() + rs.getInt("CSGACPBAG"));
					despatchDetailsVO.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(despatchDetailsVO.getAcceptedWeight().getRoundedValue().doubleValue()
									+ rs.getDouble("CSGACPWGT"))));
					despatchDetailsVO.setPrevAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(despatchDetailsVO.getPrevAcceptedWeight().getRoundedValue().doubleValue()
									+ rs.getDouble("CSGACPWGT"))));
					despatchDetailsVO.getContainers().add(rs.getString("CONNUM"));
				}
			}
		}
		log.debug("" + "THE container Details Vo Finally " + " " + containerDetailsVO);
		return containerDetails;
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
		String currentPort = rs.getString("POL");
		despatchDetailsVO.setAcceptedBags(rs.getInt("CSGACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CSGACPBAG"));
		if (rs.getTimestamp("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(localDateUtil.getLocalDate(currentPort, rs.getTimestamp("ACPDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		despatchDetailsVO.setAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGACPWGT"))));
		despatchDetailsVO.setPrevAcceptedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("CSGACPWGT"))));
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchDetailsVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getTimestamp("CSGDAT")));
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
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setContainers(new ArrayList<String>());
		despatchDetailsVO.getContainers().add(rs.getString("CONNUM"));
		despatchDetailsVO.setContainerType(rs.getString("CONTYP"));
		despatchDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
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
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString("MALIDR");
		mailbagVO.setMailbagId(mailbagId);
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("DSPWGT")),
				BigDecimal.valueOf(rs.getDouble("WGT")), rs.getString("DSPWGTUNT")));
		String scannedPort = rs.getString("POL");
		if (rs.getTimestamp("SCNDAT") != null) {
			mailbagVO.setScannedDate(localDateUtil.getLocalDate(scannedPort, rs.getTimestamp("SCNDAT")));
		}
		mailbagVO.setScannedPort(scannedPort);
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setTransferFromCarrier(rs.getString("MALFRMCARCOD"));
		if (MailConstantsVO.ULD_TYPE.equals(rs.getString("CONTYP"))) {
			mailbagVO.setContainerNumber(rs.getString("ULDNUM"));
		} else {
			mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		}
		mailbagVO.setContainerType(rs.getString("CONTYP"));
	}

	/** 
	* A-1739
	* @param dsnVO
	* @param rs
	* @throws SQLException
	*/
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs) throws SQLException {
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
	}
}
