/*
 * FindConsignmentDetailsMapper.java Created on Aug 21, 2007	
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
 * @author A-2667
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Aug 21, 2007			  	 A-2667 	Created
 */
public class FindConsignmentDetailsMapper implements Mapper<MailbagVO>{

	private Log log = LogFactory.getLogger("MAIL TRACKING");
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.entering("MailbagDetailsForReportMapper","Map");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
		mailbagVO.setWeight(wgt);//added by A-7371
		if(rs.getDouble("DSPWGT") != 0 && rs.getString("DSPWGTUNT")!=null){    
		mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),rs.getDouble("DSPWGT"),rs.getString("DSPWGTUNT")));
		}
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		mailbagVO.setPaCode(rs.getString("POACOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		//Added by A-7540
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setAcceptedBags(rs.getInt("BAGCNT"));
		//mailbagVO.setAcceptedWeight(rs.getDouble("WGT"));
		mailbagVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		mailbagVO.setUldNumber(rs.getString("ULDNUM"));
		
		if("Y".equals(rs.getString("ACP"))){
			mailbagVO.setAccepted("Y");
		
		}
		else{
			mailbagVO.setAccepted("N");
		}
		if (rs.getDate("CSGDAT") != null) {
			mailbagVO.setConsignmentDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getDate("CSGDAT")));
		}
		return mailbagVO;
	}


}
