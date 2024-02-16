/*
 * MailbagEnquiryMapper.java Created on Mar 26, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-8464
 *
 */
public class MailbagEnquiryMapper implements Mapper<MailbagVO> {

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	/**
	 * @author a-8464 
	 * @param rs
	 * @return  
	 * @throws SQLException
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "Entering the MailbagEnquiryMapper");
		MailbagVO mailbagVO = new MailbagVO();
		String airport = rs.getString("SCNPRT");
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));

		if (rs.getDate("SCNDAT") != null && airport!=null) {
			mailbagVO.setScannedDate(new LocalDate(airport, Location.ARP, rs.getTimestamp("SCNDAT")));
		}
		
		String mailStatus = rs.getString("MALSTA");
		mailbagVO.setLatestStatus(mailStatus);
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		//mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		if (rs.getString("FLTNUM")!=null && !"0".equals(rs.getString("FLTNUM")) && !"-1".equals(rs.getString("FLTNUM"))  && airport!=null) {
			if (rs.getDate("FLTDAT") != null) {
				mailbagVO.setFlightDate(new LocalDate(airport, Location.ARP, rs
						.getDate("FLTDAT")));
			}
		}
		mailbagVO.setContainerNumber(rs.getString("CONNUM"));
		mailbagVO.setPou(rs.getString("POU"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
		mailbagVO.setWeight(wgt);
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		if(rs.getString("ACTWGTDSPUNT")!=null){
		mailbagVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTWGT")));
		}
		mailbagVO.setLastUpdateUser(rs.getString("SCNUSR"));

			if (rs.getTimestamp("REQDLVTIM") != null) {
				mailbagVO.setReqDeliveryTime(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs
								.getTimestamp("REQDLVTIM")));
			}
		if(rs.getTimestamp("CSGDAT")!=null && airport!=null)
		{
			mailbagVO.setConsignmentDate(new LocalDate(airport, Location.ARP, rs.getTimestamp("CSGDAT")));
		}
		mailbagVO.setRoutingInfo(rs.getString("ROUTEINFO"));
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		
		
		if(rs.getString("TWOAPHCOD")!=null)
		{
			mailbagVO.setCarrierCode(rs.getString("TWOAPHCOD"));
			
		}
		if(rs.getString("FLTNUM")!=null &&rs.getString("FLTNUM")!="-1"){
			mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		}
		if(rs.getString("FLTCARIDR")!=null){ // added by A-8353 for ICRD-333808 starts
			mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		}
		if(rs.getInt("FLTSEQNUM")!=0){
			mailbagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		}
		if(rs.getString("CONTYP")!=null)
		{
			mailbagVO.setContainerType(rs.getString("CONTYP"));
		}
		 log.log(Log.FINEST, "THE MAILBAG VO IS FOUND TO BE ", mailbagVO);
		return mailbagVO;

	}

}
