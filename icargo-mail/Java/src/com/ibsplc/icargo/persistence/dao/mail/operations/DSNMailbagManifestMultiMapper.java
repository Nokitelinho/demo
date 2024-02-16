/*
 * DSNMailbagManifestMultiMapper.java Created on Mar 27, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
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
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2553
 * 
 */
public class DSNMailbagManifestMultiMapper implements
		MultiMapper<MailManifestVO> {

	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/**
	 * @param resultSet
	 * @return List<MailManifestVO>
	 * @throws SQLException
	 */
	public List<MailManifestVO> map(ResultSet resultSet)
			throws SQLException {
		log.entering("DSNMailbagManifestMultiMapper ", "map");
		MailManifestVO mailManifestVO = new MailManifestVO();
		List<MailManifestVO> mailManifests = new ArrayList<MailManifestVO>();
		mailManifests.add(mailManifestVO);
		
		Collection<ContainerDetailsVO> containerDetails = 
			new ArrayList<ContainerDetailsVO>();
		mailManifestVO.setContainerDetails(containerDetails);
		
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> despatches = null;
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagVOs = null;
		
		
		String currContainerKey = null;
		String prevContainerKey = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		
		int uldTotalbags = 0;
		double uldTotalWeight = 0;
		int totalbags = 0;
		double totalWeight = 0;
		
		while(resultSet.next()) {
			 		 
			 currContainerKey = new StringBuilder().append(
					 resultSet.getString("CMPCOD")).append(resultSet.getInt("FLTCARIDR"))
						.append(resultSet.getString("FLTNUM")).append(
								resultSet.getLong("FLTSEQNUM")).append(
										resultSet.getInt("SEGSERNUM")).append(
												resultSet.getString("ULDNUM")).toString();
			 if(!currContainerKey.equals(prevContainerKey)) {
				 uldTotalbags = 0;
				 uldTotalWeight = 0;
				 dsnVO = null;
				 prevContainerKey = currContainerKey;
				 containerDetailsVO = new ContainerDetailsVO();
				 containerDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
				 containerDetailsVO.setCarrierId(resultSet.getInt("FLTCARIDR"));
				 containerDetailsVO.setFlightNumber(resultSet.getString("FLTNUM"));
				 containerDetailsVO.setFlightSequenceNumber(resultSet.getLong("FLTSEQNUM"));
				 containerDetailsVO.setSegmentSerialNumber(resultSet.getInt("SEGSERNUM"));
				 containerDetailsVO.setContainerNumber(resultSet.getString("ULDNUM"));
				 //containerDetailsVO.setTotalBags(resultSet.getInt("BAGCNT"));
				// containerDetailsVO.setTotalWeight(resultSet.getDouble("BAGWGT"));
				 //containerDetailsVO.setTotalWeight(resultSet.getDouble("BAGWGT"));
				 containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,resultSet.getDouble("BAGWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
				 totalbags+=resultSet.getInt("BAGCNT");
				 totalWeight+=resultSet.getDouble("BAGWGT");
				 populateContainerDetailsVO(containerDetailsVO, resultSet);
				 containerDetails.add(containerDetailsVO);				 
				 despatches = new ArrayList<DSNVO>();
				 containerDetailsVO.setDsnVOs(despatches);	
				 prevDespatchKey = null;
			 }
			 currDespatchKey = new StringBuilder().append(currContainerKey).append(
					 resultSet.getString("DSN")).append(resultSet.getString("ORGEXGOFC"))
						.append(resultSet.getString("DSTEXGOFC")).append(
								resultSet.getString("MALSUBCLS")).append(
										resultSet.getString("MALCTGCOD")).append(resultSet.getInt("YER")).toString();
			 
			 if(!currDespatchKey.equals(prevDespatchKey)){
				 prevDespatchKey = currDespatchKey;
				 dsnVO = new DSNVO();
				 mailbagVO = null;
				 populateDSNDetails(resultSet, dsnVO);
				 mailbagVOs = new ArrayList<MailbagVO>();
				 dsnVO.setMailbags(mailbagVOs);
				 despatches.add(dsnVO);
				 /*uldTotalbags += dsnVO.getBags();
				 uldTotalWeight += dsnVO.getWeight();
				 totalbags += dsnVO.getBags();
				 totalWeight += dsnVO.getWeight();*/
				 prevMailbagKey =null;
			 }	
			 
			 if (dsnVO != null) {
				if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
					currMailbagKey = resultSet.getString("MALIDR");
					log.log(Log.FINE, "curramilbag key ", currMailbagKey);
					if (currMailbagKey != null && !currMailbagKey.equals(prevMailbagKey)) {
						mailbagVO = new MailbagVO();
						populateMailbagDetails(mailbagVO, resultSet);
						prevMailbagKey = currMailbagKey;
						mailbagVOs.add(mailbagVO);
					}
				}
			}
		}
		mailManifestVO.setTotalbags(totalbags);
		//mailManifestVO.setTotalWeight(totalWeight);
		Measure totalWt=new Measure(UnitConstants.MAIL_WGT,totalWeight);
		mailManifestVO.setTotalWeight(totalWt);//added by A-7371
		return mailManifests;
	}
	/**
	 * A-2553
	 *  @param mailbagVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateMailbagDetails(MailbagVO mailbagVO, ResultSet rs)
			throws SQLException {
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
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
		//mailbagVO.setWeight(rs.getDouble("MALWGT")));
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("MALWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, rs.getTimestamp("SCNDAT")));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setContainerType(rs.getString("CONTYP"));		
		mailbagVO.setContainerNumber(rs.getString("ULDNUM"));
		log.log(Log.FINE, "CONNUM", mailbagVO.getContainerNumber());	
	}
	/**
	 * TODO Purpose
	 * Mar 27 2008, A-2553
	 * @param rs
	 * @param dsnVO
	 * @throws SQLException
	 */
	private void populateDSNDetails(ResultSet rs, DSNVO dsnVO) throws SQLException {
		dsnVO.setOrigin(rs.getString("ORG"));
		dsnVO.setDestination(rs.getString("DST"));
		dsnVO.setBags(rs.getInt("ACPBAG"));
		//dsnVO.setWeight(rs.getDouble("ACPWGT"));
		dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setContainerNumber(rs.getString("ULDNUM"));
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		dsnVO.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
	}
	/**
	 * TODO Purpose
	 * Mar 27 2008, a-2553
	 * @param containerDetailsVO
	 * @param rs
	 */
	private void populateContainerDetailsVO(
			ContainerDetailsVO containerDetailsVO, ResultSet rs) 
	throws SQLException{
		containerDetailsVO.setPou(rs.getString("POU"));	
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
	}

}
