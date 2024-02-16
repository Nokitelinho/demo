/*
 * AcceptedDSNsForManifestMultiMapper.java Created on Feb 13, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This methosd is used to map the Result set into the Despatch Details and the
 * Mail bag Details Respectively.
 * 
 * @author a-1936
 * 
 */
public class AcceptedDSNsForManifestMultiMapper implements
		MultiMapper<ContainerDetailsVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @author a-1936
	 * @param rs
	 * @throws SQLException
	 * @return
	 */

	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("AcceptedDsnsInFlightMultiMapper", "map(ResultSet rs");
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
			log.log(Log.FINE, "currContainerKey ", currContainerKey);
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

			currDSNKey = new StringBuilder().append(currContainerKey).append(
					rs.getString("DSN")).append(rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
					.toString();
			log.log(Log.FINE, "CurrDSNKey ", currDSNKey);
			if (!currDSNKey.equals(prevDSNKey)) {
				dsnVo = new DSNVO();
				populateDSNDetails(dsnVo, rs);
				//if (MailConstantsVO.FLAG_YES.equals(rs.getString("PLTENBFLG"))) {
					mailbagVOs = new ArrayList<MailbagVO>();
			//	}
				dsnVo.setMailbags(mailbagVOs);
				dsnVOs.add(dsnVo);
				prevDSNKey = currDSNKey;
			}

			currMailbagKey = rs.getString("MALIDR");
			if (currMailbagKey != null) {
				log.log(Log.FINE, "curramilbag key ", currMailbagKey);
				if (currMailbagKey != null
						&& !currMailbagKey.equals(prevMailbagKey)) {
					mailbagVO = new MailbagVO();
					populateMailbagDetails(mailbagVO, rs);
					prevMailbagKey = currMailbagKey;
					mailbagVOs.add(mailbagVO);
				}
			}

			String csgDocNum = rs.getString("CSGDOCNUM");
			log.log(Log.FINE, "csgDocNum", csgDocNum);
			if (csgDocNum != null) {
				currDespatchKey = new StringBuilder().append(currDSNKey)
						.append(csgDocNum).append(rs.getString("POACOD"))
						.append(rs.getInt("CSGSEQNUM")).toString();
				log.log(Log.FINE, "THE CONSIGNEMENT KEY", currDespatchKey);
				if (!currDespatchKey.equals(prevDespatchKey)) {
					despatchDetailsVO = new DespatchDetailsVO();
					populateDespatchDetails(despatchDetailsVO, rs);
					despatches.add(despatchDetailsVO);
					prevDespatchKey = currDespatchKey;
				} else {
					despatchDetailsVO.setStatedBags(despatchDetailsVO
							.getStatedBags()
							+ rs.getInt("CSGSTDBAG"));
					/*despatchDetailsVO.setStatedWeight(despatchDetailsVO
							.getStatedWeight()
							+ rs.getDouble("CSGSTDWGT"));*/
					despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,despatchDetailsVO
							.getStatedWeight().getRoundedSystemValue()
							+ rs.getDouble("CSGSTDWGT")));//added by A-7371
					despatchDetailsVO.setPrevStatedBags(despatchDetailsVO
							.getPrevStatedBags()
							+ rs.getInt("CSGSTDBAG"));
					/*despatchDetailsVO.setPrevStatedWeight(despatchDetailsVO
							.getPrevStatedWeight()
							+ rs.getDouble("CSGSTDWGT"));*/
					despatchDetailsVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,despatchDetailsVO
							.getPrevStatedWeight().getRoundedSystemValue()
							+ rs.getDouble("CSGSTDWGT")));//added by A-7371
					despatchDetailsVO.setAcceptedBags(despatchDetailsVO
							.getAcceptedBags()
							+ rs.getInt("CSGACPBAG"));
					despatchDetailsVO.setPrevAcceptedBags(despatchDetailsVO
							.getPrevAcceptedBags()
							+ rs.getInt("CSGACPBAG"));
					/*despatchDetailsVO.setAcceptedWeight(despatchDetailsVO
							.getAcceptedWeight()
							+ rs.getDouble("CSGACPWGT"));*/
					despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,despatchDetailsVO
							.getAcceptedWeight().getRoundedSystemValue()
							+ rs.getDouble("CSGACPWGT")));//added by A-7371
					/*despatchDetailsVO.setPrevAcceptedWeight(despatchDetailsVO
							.getPrevAcceptedWeight()
							+ rs.getDouble("CSGACPWGT"));*/
					despatchDetailsVO.setPrevAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,despatchDetailsVO
							.getPrevAcceptedWeight().getRoundedSystemValue()
							+ rs.getDouble("CSGACPWGT")));//added by A-7371
					despatchDetailsVO.getContainers().add(
							rs.getString("CONNUM"));
				}
			}
		}
		log.log(Log.FINE, "THE container Details Vo Finally ",
				containerDetailsVO);
		return containerDetails;
	}

	/**
	 * A-1936
	 * 
	 * @param despatchDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDespatchDetails(DespatchDetailsVO despatchDetailsVO,
			ResultSet rs) throws SQLException {
		String currentPort = rs.getString("POL");
		despatchDetailsVO.setAcceptedBags(rs.getInt("CSGACPBAG"));
		despatchDetailsVO.setPrevAcceptedBags(rs.getInt("CSGACPBAG"));
		if(rs.getTimestamp("ACPDAT")!=null){
		despatchDetailsVO.setAcceptedDate(new LocalDate(currentPort,
				Location.ARP, rs.getTimestamp("ACPDAT")));
		}
		
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		//despatchDetailsVO.setAcceptedWeight(rs.getDouble("CSGACPWGT"));
		despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGACPWGT")));//added by A-7371
		//despatchDetailsVO.setPrevAcceptedWeight(rs.getDouble("CSGACPWGT"));
		despatchDetailsVO.setPrevAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGACPWGT")));//added by A-7371
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchDetailsVO
				.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,
						Location.NONE, rs.getTimestamp("CSGDAT")));
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS"));
		// Added to include the DSN PK
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setStatedBags(rs.getInt("CSGSTDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("CSGSTDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGSTDWGT")));//added by A-7371
		despatchDetailsVO.setPrevStatedBags(rs.getInt("CSGSTDBAG"));
		//despatchDetailsVO.setPrevStatedWeight(rs.getDouble("CSGSTDWGT"));
		despatchDetailsVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("CSGSTDWGT")));//added by A-7371
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
	//	if (MailConstantsVO.BULK_TYPE.equals(rs.getString("CONTYP"))) {
			despatchDetailsVO.setContainers(new ArrayList<String>());
			despatchDetailsVO.getContainers().add(rs.getString("CONNUM"));
	//	} 
		despatchDetailsVO.setContainerType(rs.getString("CONTYP"));
		despatchDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
	}

	/**
	 * A-1936
	 * 
	 * @param mailbagVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs)
			throws SQLException {
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		String mailbagId = rs.getString("MALIDR");
		mailbagVO.setMailbagId(mailbagId);
		//Added by A-7531 as part of CR ICRD-197299
		mailbagVO.setLatestStatus(rs.getString("MALSTA"));
		//Added for icrd-95515
		mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		// Added to include the DSN PK..
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		/*double systemWt=Double.parseDouble(mailbagId.substring(25, 29)) / 10;
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,systemWt);//added by A-7371
		mailbagVO
				.setWeight(wgt);*/
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"),rs.getDouble("DSPWGT"),rs.getString("DSPWGTUNT")));
		String scannedPort = rs.getString("POL");
		if(rs.getTimestamp("SCNDAT")!=null){
		mailbagVO.setScannedDate(new LocalDate(scannedPort, Location.ARP, rs
				.getTimestamp("SCNDAT")));
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
	 * 
	 * @param dsnVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs)
			throws SQLException {
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		// Added to include the DSN PK..
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
	}

}
