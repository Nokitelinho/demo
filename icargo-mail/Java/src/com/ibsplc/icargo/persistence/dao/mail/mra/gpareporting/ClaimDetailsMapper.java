/*
 * GPAReportingInvoicDetailsMapper.java created on Nov 21, 2018
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8464
 *
 */
public class ClaimDetailsMapper implements Mapper<ClaimDetailsVO>{
	
	
	private Log log = LogFactory.getLogger("MRA:GPAREPORTING ClaimDetailsMapper");
	
	/**
	 * maps the resultset row to a VO
	 * @param rs
	 * @return invoicDetailsVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public ClaimDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("ClaimDetailsMapper", "map method");
		ClaimDetailsVO claimdtlsVO = new ClaimDetailsVO();
		//From result of list query to the details needed for displaying in screen invoic Enquiry
		claimdtlsVO.setCmpcod(rs.getString("CMPCOD"));
		claimdtlsVO.setGpaCode(rs.getString("POACOD"));
		claimdtlsVO.setSernum(rs.getInt("SERNUM"));
		claimdtlsVO.setMailSeqNumber(rs.getLong("MALSEQNUM"));
		claimdtlsVO.setSeqNumber(rs.getInt("SEQNUM"));
		claimdtlsVO.setMailBagId(rs.getString("MALIDR"));
		claimdtlsVO.setReceivedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("RCVDAT")));
		if(rs.getDate("RPTPRDFRM")!=null)
		claimdtlsVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDFRM")));
		if(rs.getDate("RPTPRDTOO")!=null)
		claimdtlsVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDTOO")));
		claimdtlsVO.setClaimType(rs.getString("CLMTYP"));
		claimdtlsVO.setClaimAmount(rs.getDouble("CLMAMT"));
		claimdtlsVO.setClaimStatus(rs.getString("CLMSTA"));		
		
		claimdtlsVO.setCurrency(rs.getString("CURCOD"));
		if(rs.getDate("CLMSUBDAT")!=null)
		claimdtlsVO.setClaimSubDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("CLMSUBDAT")));
		claimdtlsVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		
		Timestamp upTime =rs.getTimestamp("LSTUPDTIM");
		if(upTime!=null){
			LocalDate date=new LocalDate(LocalDate.NO_STATION,Location.NONE,upTime);
			claimdtlsVO.setLastUpdatedTime(date);
		}
		claimdtlsVO.setClaimFilename(rs.getString("CLMFILNAM"));
		log.exiting("ClaimDetailsMapper", "map method");
		return claimdtlsVO;
	}

}
