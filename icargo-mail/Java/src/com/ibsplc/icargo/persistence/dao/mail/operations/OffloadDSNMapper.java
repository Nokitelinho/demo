/*
 * OffloadDSNMapper.java Created on Jun 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-1936
 * 
 */
public class OffloadDSNMapper implements Mapper<DespatchDetailsVO> {
	/**
	 * This method is used to map the ResultSet in to the DespatchDetailsVOs
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	//private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	public DespatchDetailsVO map(ResultSet rs) throws SQLException {
		DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
		String airport = rs.getString("ASGPRT");
		despatchDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		despatchDetailsVO.setDsn(rs.getString("DSN"));
		despatchDetailsVO.setMailClass(rs.getString("MALCLS")); 
		//Added to include  teh DSN PK 
		despatchDetailsVO.setMailSubclass(rs.getString("MALSUBCLS"));
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setOriginOfficeOfExchange(rs.getString("ORGEXGOFC"));
		despatchDetailsVO.setDestinationOfficeOfExchange(rs
				.getString("DSTEXGOFC"));
		despatchDetailsVO.setYear(rs.getInt("YER"));
		despatchDetailsVO.setAcceptedBags(rs.getInt("BAGS"));
		//despatchDetailsVO.setAcceptedWeight(rs.getDouble("WGT"));
		despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
		despatchDetailsVO.setContainerType(rs.getString("CONTYP"));
		despatchDetailsVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		despatchDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		despatchDetailsVO.setUldNumber(rs.getString("ULDNUM"));
		despatchDetailsVO.setDestination(rs.getString("DSTCOD"));
		despatchDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		despatchDetailsVO.setPou(rs.getString("POU"));
		despatchDetailsVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		despatchDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		despatchDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		despatchDetailsVO.setAirportCode(airport);
		despatchDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		despatchDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		despatchDetailsVO.setStatedBags(rs.getInt("STDBAG"));
		//despatchDetailsVO.setStatedWeight(rs.getDouble("STDWGT"));
		despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("STDWGT")));//added by A-7371
		despatchDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		if (rs.getDate("ACPDAT") != null) {
			despatchDetailsVO.setAcceptedDate(new LocalDate(airport,
					Location.ARP, rs.getDate("ACPDAT")));
		}
		if (rs.getDate("CSGDAT") != null) {
			despatchDetailsVO.setConsignmentDate(new LocalDate(airport,
					Location.ARP, rs.getDate("CSGDAT")));
		}
		despatchDetailsVO.setAcceptedUser(rs.getString("ACPUSR"));
		//despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		despatchDetailsVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
		despatchDetailsVO.setPaCode(rs.getString("POACOD"));
		
		//for cardit enquiry 
		
		if(rs.getString("SDRIDR")!=null){
		 despatchDetailsVO.setPaCode(rs.getString("SDRIDR"));
		}
		Date csgCmpDat = rs.getDate("CSGCMPDAT");
		if(csgCmpDat != null) {
			despatchDetailsVO.setConsignmentDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,csgCmpDat));
		} 
		/*
		 * Added By Karthick V 
		 * A kind of Work around need to be removed once cahnges in the corresponding Queries being done 
		 * 
		 */
		if(despatchDetailsVO.getMailCategoryCode()==null && 
				rs.getString("MALCTG")!=null){
		despatchDetailsVO.setMailCategoryCode(rs.getString("MALCTG"));
		}
		return despatchDetailsVO;
	}

}
