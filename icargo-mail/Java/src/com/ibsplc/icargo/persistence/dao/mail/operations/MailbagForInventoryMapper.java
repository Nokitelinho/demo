/*
 * MailbagForInventoryMapper.java Created on Jan 18 , 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 * 
 */
public class MailbagForInventoryMapper implements Mapper<MailbagVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @author a-1936 This method is used to set the values from the resultset
	 *         into Mapper
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
     log.log(Log.INFO,"Entering the MailBag Mapper");
		MailbagVO mailbagVO = new MailbagVO();
		String airport = rs.getString("SCNPRT");
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setDespatchId(rs.getString("DSNIDR"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		//Added to include the DSN PK 
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		log.log(Log.FINE, "The Mail Sub Class  ", mailbagVO.getMailSubclass());
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		log.log(Log.FINE, "The Mail Category Code ", mailbagVO.getMailCategoryCode());
		mailbagVO.setYear(rs.getInt("YER"));
		if (rs.getDate("SCNDAT") != null) {
			mailbagVO.setScannedDate(new LocalDate(airport, Location.ARP, rs
					.getDate("SCNDAT")));
		}
		log.log(Log.FINE, "THE MAIL STATUS IS ", rs.getString("MALSTA"));
		String mailStatus = rs.getString("MALSTA");
		log.log(Log.FINE, "THE MAIL STATUS IS ", mailStatus);
		mailbagVO.setLatestStatus(mailStatus);
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		if (rs.getLong("FLTSEQNUM") > 0) {
			if (rs.getDate("FLTDAT") != null) {
				mailbagVO.setFlightDate(new LocalDate(airport, Location.ARP, rs
						.getDate("FLTDAT")));
			}
		}
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
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
		mailbagVO.setWeight(wgt);//added by A-7371
		mailbagVO.setFinalDestination(rs.getString("POU"));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailStatus)) {
			 mailbagVO.setArrivedFlag(MailbagVO.FLAG_YES);
		}
		/*
		 * Added By karthick V to fetch the Inventory Details 
		 */
		
		if(rs.getString("INBFLTNUM")!=null){
			log.log(Log.FINE,"THE INBOUND DETAILS ARE COLLECTED");
			mailbagVO.setFromContainer(rs.getString("INBCONNUM"));
			mailbagVO.setFromContainerType(rs.getString("INBCONTYP"));
			mailbagVO.setFromFightNumber(rs.getString("INBFLTNUM"));
			mailbagVO.setFromFlightDate(new LocalDate(airport, Location.ARP, rs
					.getDate("INBFLTDAT")));
			mailbagVO.setFromFlightSequenceNumber(rs.getLong("INBFLTSEQNUM"));
			mailbagVO.setFromSegmentSerialNumber(rs.getInt("INBSEGSERNUM"));
		}
		log.log(Log.FINE, "THE MAILBAG VO IS FOUND TO BE ", mailbagVO);
		return mailbagVO;

	}

}

