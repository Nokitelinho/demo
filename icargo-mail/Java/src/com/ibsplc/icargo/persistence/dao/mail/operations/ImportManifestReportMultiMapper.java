/*
 * ImportManifestReportMultiMapper.java Created on  Mar 27 2008
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
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The mapper for fetching arrival details of a flight
 * 
 * @author a-1936
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * 27 Mar 2008 Karthick V First Draft
 */
public class ImportManifestReportMultiMapper implements
		MultiMapper<MailManifestVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {

		List<ContainerDetailsVO> containerDetails = null;
		List<MailManifestVO>  manifestVos =new ArrayList<MailManifestVO>();
		MailManifestVO mailManifestVo = new MailManifestVO();
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
		containerDetails = new ArrayList<ContainerDetailsVO>();
		mailManifestVo.setContainerDetails(containerDetails);
		manifestVos.add(mailManifestVo);
		while (rs.next()) {

			currContainerKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(
							rs.getLong("FLTSEQNUM")).append(
							rs.getInt("SEGSERNUM")).append(
							rs.getString("ULDNUM")).toString();
			log.log(Log.FINE, "currContainerKey ", currContainerKey);
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				mailbagVOs = new ArrayList<MailbagVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				containerDetails.add(containerDetailsVO);
				/*
				 * Set the total bags and the weight in the MailManifest Vo
				 * 
				 * 
				 */
				mailManifestVo.setTotalbags(mailManifestVo.getTotalbags()+containerDetailsVO.getTotalBags());
				//mailManifestVo.setTotalWeight(mailManifestVo.getTotalWeight()+containerDetailsVO.getTotalWeight());
				try {
					mailManifestVo.setTotalWeight(Measure.addMeasureValues(mailManifestVo.getTotalWeight(), containerDetailsVO.getTotalWeight()));//added by A-7371
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE,"UnitException",e.getMessage());
				}//added by A-7371
				prevContainerKey = currContainerKey;
			}

			currDSNKey = new StringBuilder().append(currContainerKey).append(
					rs.getString("DSN")).append(rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
					.toString();
			log.log(Log.FINE, "CurrDSNKey ", currDSNKey);
			if (rs.getString("DSN") != null && !currDSNKey.equals(prevDSNKey)) {
				dsnVO = new DSNVO();
				populateDSNDetails(dsnVO, rs);
				dsnVO.setContainerNumber(containerDetailsVO
						.getContainerNumber());
				dsnVO.setMailbags(new ArrayList<MailbagVO>());
				dsnVOs.add(dsnVO);
				prevDSNKey = currDSNKey;
			}

			if (dsnVO != null) {
				if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {

					currMailbagKey = rs.getString("MALIDR");
					log.log(Log.FINE, "curramilbag key ", currMailbagKey);
					if (currMailbagKey != null
							&& !currMailbagKey.equals(prevMailbagKey)) {
						mailbagVO = new MailbagVO();
						mailbagVO.setCarrierId(containerDetailsVO
								.getCarrierId());
						mailbagVO.setFlightNumber(containerDetailsVO
								.getFlightNumber());
						mailbagVO.setFlightSequenceNumber(containerDetailsVO
								.getFlightSequenceNumber());
						mailbagVO.setSegmentSerialNumber(containerDetailsVO
								.getSegmentSerialNumber());
						mailbagVO.setPou(containerDetailsVO.getPou());
						mailbagVO.setPol(containerDetailsVO.getPol());
						mailbagVO.setContainerNumber(containerDetailsVO
								.getContainerNumber());
						mailbagVO.setContainerType(containerDetailsVO
								.getContainerType());
						dsnVO.getMailbags().add(mailbagVO);
						 /*
						  * Added By Karthick V
						  * 
						  */
                        populateMailbagDetails(mailbagVO, rs);
                        mailManifestVo.setTotalAcceptedBags(mailManifestVo.getTotalAcceptedBags()+mailbagVO.getAcceptedBags());
                        try{
                        	mailManifestVo.setTotalAcceptedWeight(Measure.addMeasureValues(mailManifestVo.getTotalAcceptedWeight(),mailbagVO.getAcceptedWeight()));
					    } catch (UnitException e) {
						// TODO Auto-generated catch block
						log.log(Log.SEVERE,"UnitException",e.getMessage());
					    }//added by A-8893
                        mailbagVO.setContainerType(containerDetailsVO.getContainerType());
						if (MailConstantsVO.FLAG_YES.equals(mailbagVO
								.getTransferFlag())) {
							dsnVO.setTransferFlag(MailConstantsVO.FLAG_YES);
						}
						prevMailbagKey = currMailbagKey;
						mailbagVOs.add(mailbagVO);
					}
				}
			}
		}
		return manifestVos;
	}

	/**
	 * A-1739
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
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
		mailbagVO.setMailSubclass( rs.getString("MALSUBCLS"));
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		/*mailbagVO
		.setWeight(rs.getDouble("WGT"));*/
		mailbagVO
				.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		mailbagVO.setArrivedFlag(rs.getString("MALARRFLG"));
		mailbagVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		mailbagVO.setAcceptedBags(rs.getInt("ACPBAG"));
		if(null!=rs.getString("SHPPFX")&&null!=rs.getString("MSTDOCNUM")){
		mailbagVO.setAwbNumber(rs.getString("SHPPFX")+"-"+rs.getString("MSTDOCNUM"));
		}
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
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		// Added to include the DSN PK
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		/*
		 * Updated By karthick as  the received bags and  the weight has to be shown in the 
		 * Import Mainfest Report rather than  the Accepted Bags and the Accepted Weight 
		 * 
		 */
		dsnVO.setBags(rs.getInt("DSNRCVBAG"));
		//dsnVO.setWeight(rs.getDouble("DSNRCVWGT"));
		dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNRCVWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		dsnVO.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
	}

	/**
	 * A-1739
	 * 
	 * @param containerDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO, ResultSet rs)
			throws SQLException {
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		/*
		 * Added by karthick V as the part of the ANZ Mail Tracking  BUG FIX
		 * 
		 */
		containerDetailsVO.setTotalBags(rs.getInt("RCVBAG"));
		//containerDetailsVO.setTotalWeight(rs.getDouble("RCVWGT"));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
		//containerDetailsVO.setReceivedWeight(rs.getDouble("RCVWGT"));
		containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		if (containerDetailsVO.getContainerNumber().startsWith(
				MailConstantsVO.CONST_BULK)) {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			// ADDED By karthick V to include the Destination Code so
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		}
	}

}
