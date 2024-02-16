package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.AWBAttachedMailbagDetailsMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	a-7779	:	31-Aug-2017	:	Draft
 */
public class AWBAttachedMailbagDetailsMapper implements MultiMapper<ScannedMailDetailsVO> {
	public List<ScannedMailDetailsVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = null;
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		while (rs.next()) {
			MailbagVO mailBagVO = new MailbagVO();
			mailBagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailBagVO.setDespatchSerialNumber(rs.getString("DSN"));
			mailBagVO.setMailClass(rs.getString("MALCLS"));
			mailBagVO.setYear(rs.getInt("YER"));
			mailBagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			mailBagVO.setMailbagId(rs.getString("MALIDR"));
			mailBagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailBagVO.setMailSubclass(rs.getString("MALSUBCLS"));
			mailBagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailBagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailBagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
			mailBagVO.setMailStatus(rs.getString("MALSTA"));
			mailBagVO.setLatestStatus(rs.getString("MALSTA"));
			mailBagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailBagVO.setFlightNumber(rs.getString("FLTNUM"));
			mailBagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			mailBagVO.setFlightStatus(rs.getString("FLTSTA"));
			mailBagVO.setStationCode(rs.getString("STNCOD"));
			if (!"Y".equals(rs.getString("SPLIND")) && ("ACP".equals(rs.getString("MALSTA"))
					|| "ASG".equals(rs.getString("MALSTA")) || ("ARR".equals(rs.getString("MALSTA"))))) {
				mailBagVO.setFlightNumber(rs.getString("MALMSTFLTNUM"));
				mailBagVO.setFlightSequenceNumber(rs.getInt("MALMSTFLTSEQNUM"));
			}
			mailBagVO.setFlightStatus(rs.getString("FLTSTA"));
			mailBagVO.setCarrierId(rs.getInt("FLTCARIDR"));
			mailBagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			mailBagVO.setContainerNumber(rs.getString("CONNUM"));
			mailBagVO.setContainerType(rs.getString("CONTYP"));
			mailBagVO.setOoe(rs.getString("ORGEXGOFC"));
			mailBagVO.setDoe(rs.getString("DSTEXGOFC"));
			mailBagVO.setShipmentPrefix(rs.getString("SHPPFX"));
			mailBagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
			mailBagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
			mailBagVO.setSequenceNumber(rs.getInt("SEQNUM"));
			if ("-1".equals(rs.getString("MALMSTFLTNUM")) && "-1".equals(rs.getString("MALMSTFLTSEQNUM"))) {
				mailBagVO.setOffloadedRemarks("OFLMAILBAG");
			}
			if (rs.getString("MALIDR") != null && rs.getString("MALIDR").trim().length() > 0) {
				mailBagVO.setMailbagId(rs.getString("MALIDR"));
			} else {
				String mailbagId = new StringBuilder().append(rs.getString("ORGEXGOFC"))
						.append(rs.getString("DSTEXGOFC")).append(rs.getString("MALCTGCOD"))
						.append(rs.getString("MALSUBCLS")).append(rs.getInt("YER")).append(rs.getString("DSN"))
						.append(rs.getString("RSN")).append(rs.getString("HSN")).append(rs.getString("REGIND"))
						.append(rs.getString("WGT")).toString();
				mailBagVO.setMailbagId(mailbagId);
			}
			mailBagVO.setPol(rs.getString("SCNPRT"));
			mailBagVO.setPou(rs.getString("POU"));
			mailBagVO.setScannedDate(localDateUtil.getLocalDate(null, true));
			mailBagVO.setScannedPort(rs.getString("SCNPRT"));
			mailBagVOs.add(mailBagVO);
			scannedMailDetailsVO.setMailDetails(mailBagVOs);
			if ("Y".equals(rs.getString("SPLIND"))) {
				scannedMailDetailsVO.setSplitBooking(true);
			} else {
				scannedMailDetailsVO.setSplitBooking(false);
			}
			ContainerVO containerVO = new ContainerVO();
			containerVO.setCompanyCode(rs.getString("CMPCOD"));
			containerVO.setPou(rs.getString("POU"));
			containerVO.setCarrierCode(rs.getString("FLTCARCOD"));
			containerVO.setFlightNumber(rs.getString("FLTNUM"));
			containerVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			containerVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			containerVO.setCarrierId(rs.getInt("FLTCARIDR"));
			containerVO.setFlightStatus(rs.getString("FLTSTA"));
			containerVO.setContainerNumber(rs.getString("CONNUM"));
			containerVO.setType(rs.getString("CONTYP"));
			containerVO.setPol(rs.getString("SCNPRT"));
			containerVO.setAssignedPort(rs.getString("SCNPRT"));
			containerVOs.add(containerVO);
			scannedMailDetailsVO.setScannedContainerDetails(containerVOs);
			scannedMailDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			scannedMailDetailsVO.setContainerNumber(rs.getString("CONNUM"));
			scannedMailDetailsVO.setContainerType(rs.getString("CONTYP"));
			scannedMailDetailsVO.setDestination(rs.getString("POU"));
			scannedMailDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
			scannedMailDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
			scannedMailDetailsVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			scannedMailDetailsVO.setFlightStatus(rs.getString("FLTSTA"));
			scannedMailDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			scannedMailDetailsVO.setPol(rs.getString("SCNPRT"));
			scannedMailDetailsVO.setPou(rs.getString("POU"));
			scannedMailDetailsVO.setStatus(rs.getString("MALSTA"));
			if (!"Y".equals(rs.getString("SPLIND")) && ("ACP".equals(rs.getString("MALSTA"))
					|| "ASG".equals(rs.getString("MALSTA")) || "TRA".equals(rs.getString("MALSTA")))) {
				scannedMailDetailsVO.setFlightNumber(rs.getString("MALMSTFLTNUM"));
				scannedMailDetailsVO.setFlightSequenceNumber(rs.getInt("MALMSTFLTSEQNUM"));
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			scannedMailDetailsVOs = new ArrayList<ScannedMailDetailsVO>();
			scannedMailDetailsVOs.add(scannedMailDetailsVO);
		}
		if (scannedMailDetailsVOs == null) {
			scannedMailDetailsVOs = new ArrayList<ScannedMailDetailsVO>();
		}
		return (ArrayList<ScannedMailDetailsVO>) scannedMailDetailsVOs;
	}
}
