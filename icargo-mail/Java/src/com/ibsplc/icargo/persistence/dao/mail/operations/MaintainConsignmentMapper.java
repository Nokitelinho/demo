/*
 * MaintainConsignmentMapper.java Created on Aug08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-3429
 * 
 */
public class MaintainConsignmentMapper implements Mapper<MailInConsignmentVO> {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCCAMapper";
	private static final String MSTDOCNUM ="MSTDOCNUM";
	private static final String SHPPFX = "SHPPFX";

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailInConsignmentVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		MailInConsignmentVO   mailInConsignmentVO =null;
		
			mailInConsignmentVO = new MailInConsignmentVO();

		if (rs.getString("CMPCOD") != null) {
			mailInConsignmentVO.setCompanyCode(rs.getString("CMPCOD"));
		}
		if (rs.getString("CSGDOCNUM") != null) {
			mailInConsignmentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		}
		if (rs.getString("POACOD") != null) {
			mailInConsignmentVO.setPaCode(rs.getString("POACOD"));
		}
		
			mailInConsignmentVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			mailInConsignmentVO.setDsn(rs.getString("DSN"));
			mailInConsignmentVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
			mailInConsignmentVO.setDestinationExchangeOffice(rs
					.getString("DSTEXGOFC"));
			//Added to include the MailClass
			mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
			mailInConsignmentVO.setYear(rs.getInt("YER"));
			mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
			//Added to include  the DSN PK
			mailInConsignmentVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
			mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs
					.getString("REGIND"));
			//mailInConsignmentVO.setStatedWeight(rs.getDouble("WGT"));  
			mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),rs.getDouble("DSPWGT"),rs.getString("DSPWGTUNT"))); //added by A-7371 
			mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
			mailInConsignmentVO.setDeclaredValue(rs.getDouble("DCLVAL"));
			mailInConsignmentVO.setCurrencyCode(rs.getString("CURCOD"));
			mailInConsignmentVO.setStatedBags(rs.getInt("BAGCNT"));
			mailInConsignmentVO.setMailStatus(rs.getString("MALSTA"));

			if(rs.getString(MSTDOCNUM)!=null && !rs.getString(MSTDOCNUM).isEmpty()){
				mailInConsignmentVO.setAwbAttached(true);
				mailInConsignmentVO.setMasterDocumentNumber(rs.getString(MSTDOCNUM));
			}else{
				mailInConsignmentVO.setAwbAttached(false);
			}
			if (rs.getTimestamp("TRPSRVENDTIM") != null) {
				mailInConsignmentVO.setTransWindowEndTime(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs
								.getTimestamp("TRPSRVENDTIM")));
			}
			if (rs.getTimestamp("REQDLVTIM") != null) {
				mailInConsignmentVO.setReqDeliveryTime(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs
								.getTimestamp("REQDLVTIM")));
			}
			mailInConsignmentVO.setMailOrigin(rs.getString("ORGCOD"));
			mailInConsignmentVO.setMailDestination(rs.getString("DSTCOD"));
			mailInConsignmentVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			if(rs.getString(SHPPFX)!=null && !rs.getString(SHPPFX).isEmpty()){
				mailInConsignmentVO.setShipmentPrefix(rs.getString(SHPPFX));
			}
			mailInConsignmentVO.setMailDuplicateNumber(rs.getInt("DUPNUM"));
			mailInConsignmentVO.setMailBagDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
			mailInConsignmentVO.setSequenceNumberOfMailbag(rs.getInt("SEQNUM"));
		return mailInConsignmentVO;
	}

}
