/*
 * ULDAuditEnquiryMapper.java Created on Apr 03,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2667
 * 
 */
public class ULDAuditEnquiryMapper implements Mapper<AuditDetailsVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	
	private static final String  ULDMSTCPT = "ULDMSTCPT";
	private static final String  ULDMSTUPD = "ULDMSTUPD";
	private static final String  ULDMSTDEL = "ULDMSTDEL";
	private static final String  ULDDMGCPT = "ULDDMGCPT";
	private static final String  ULDDMGUPD = "ULDDMGUPD";
	private static final String  ULDDMGDEL = "ULDDMGDEL";
	private static final String  ULDRPRCPT = "ULDRPRCPT";
	private static final String  ULDLONTXNCPT = "ULDLONTXNCPT";
	private static final String  ULDLONTXNDEL = "ULDLONTXNDEL";
	private static final String  ULDLONTXNUPD = "ULDLONTXNUPD";
	private static final String  ULDBRWTXNCPT = "ULDBRWTXNCPT";
	private static final String  ULDBRWTXNDEL = "ULDBRWTXNDEL";
	private static final String  ULDBRWTXNUPD = "ULDBRWTXNUPD";
	private static final String  ULDBRWRTNCPT = "ULDBRWRTNCPT";
	private static final String  ULDLONRTNCPT = "ULDLONRTNCPT";
	private static final String  ULDDISCPT = "ULDDISCPT";
	private static final String  ULDDISDEL = "ULDDISDEL";
	private static final String  ULDDISUPD = "ULDDISUPD";
	private static final String  ULDMVTCPT = "ULDMVTCPT";
	private static final String  ULDTXNINVGNT = "ULDTXNINVGNT";
	private static final String  ULDSTKCPT = "ULDSTKCPT";
	private static final String  ULDSTKDEL = "ULDSTKDEL";
	private static final String  ULDSTKUPD = "ULDSTKUPD";
	private static final String  ULDAGRCPT = "ULDAGRCPT";
	private static final String  ULDAGRUPD = "ULDAGRUPD";
	private static final String  ULDFACLOCCRT = "ULDFACLOCCRT";
	private static final String  ULDAGRDEL = "ULDAGRDEL";
	private static final String  ULDFACLOCUPD = "ULDFACLOCUPD";
	private static final String  ULDFACLOCDEL = "ULDFACLOCDEL";
	private static final String  ULDSCMSNT = "ULDSCMSNT";
	private static final String  ULDMUCGEN = "ULDMUCGEN";
	private static final String  ULDINTMVTCPT = "ULDINTMVTCPT";
	private static final String  ULDCPMPRC = "ULDCPMPRC";
	private static final String  ULDLUCPRC = "ULDLUCPRC";
	private static final String  ULDSCMPRC = "ULDSCMPRC";
	 /**
     * Method for getting the map
     * @param rs
     * @return AuditDetailsVO
     * @throws SQLException
     */
	public AuditDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("ULDAuditEnquiryMapper", "map");
		AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
		auditDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		auditDetailsVO.setAction(rs.getString("ACTCOD"));
		if(ULDMSTCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Create");
		}else if(ULDMSTUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Master Update");
		}else if(ULDMSTDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Delete");
		}else if(ULDDMGCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Damage Capture");
		}else if(ULDDMGUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Damage Updation");
		}else if(ULDDMGDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Damage Deletion");
		}else if(ULDRPRCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Repair capture");
		}else if(ULDLONTXNCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Loan Capture");
		}else if(ULDLONTXNDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Loan Delete");
		}else if(ULDLONTXNUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Loan Update");
		}else if(ULDBRWTXNCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Borrow Create");
		}else if(ULDBRWTXNDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Borrow Delete");
		}else if(ULDBRWTXNUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Borrow Update");
		}else if(ULDBRWRTNCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Return Borrow");
		}else if(ULDLONRTNCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Return Loan");
		}else if(ULDDISCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Discrepancy");
		}else if(ULDDISDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Discrepancy Delete");
		}else if(ULDDISUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Discrepancy Update");
		}else if(ULDMVTCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD External Movement ");
		}else if(ULDTXNINVGNT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Invoice Generation");
		}else if(ULDSTKCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Stock Create");
		}else if(ULDSTKDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Stock Delete");
		}else if(ULDSTKUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Stock Update");
		}else if(ULDAGRCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Agreement Capture");
		}else if(ULDAGRUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Agreement Update");
		}else if(ULDFACLOCCRT.equals(rs.getString("ACTCOD"))){
				auditDetailsVO.setAction("Location Create");
		}else if(ULDAGRDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Agreement Delete");
		}else if(ULDFACLOCUPD.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Location Update");
		}else if(ULDFACLOCDEL.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("Location Delete");
		}else if(ULDSCMSNT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("SCM Sent");
		}else if(ULDMUCGEN.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("MUC Sent");
		}else if(ULDINTMVTCPT.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD Internal Movement");
		}else if(ULDCPMPRC.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD CPM Processing");
		}else if(ULDLUCPRC.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD LUC Processing");
		}else if(ULDSCMPRC.equals(rs.getString("ACTCOD"))){
			auditDetailsVO.setAction("ULD SCM Processing");
		}
		
		auditDetailsVO.setRemarks(rs.getString("AUDRMK"));
		auditDetailsVO.setAdditionalInformation(rs.getString("ADLINF"));
		auditDetailsVO.setStationCode(rs.getString("STNCOD"));
		auditDetailsVO.setLastUpdateUser(rs.getString("UPDUSR"));
		if(rs.getTimestamp("UPDTXNTIM") != null){
			auditDetailsVO.setLastUpdateTime(new LocalDate(rs.getString("STNCOD"),Location.ARP ,rs.getTimestamp("UPDTXNTIM")));
		}else
		if(rs.getTimestamp("UPDTXNTIMUTC") != null){
			auditDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE ,rs.getTimestamp("UPDTXNTIMUTC")));
		}
		log.exiting("ULDAuditEnquiryMapper", "map");
		return auditDetailsVO;
	}
}
