/*
 * ListRateAuditDtlsMapper.java Created on Jul 16, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

/*
 * ListRateAuditDtlsMapper.java created on Jul 17,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * 
 * @author A-2391
 *
 */
public class ListRateAuditDtlsMapper implements Mapper<RateAuditVO> {

	/**
	 *
	 * Jul 17, 2008
	 * @param rs
	 * @return RateLineErrorVO
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *
	 */
    public RateAuditVO map(ResultSet rs) throws SQLException {
    	RateAuditVO rateAuditVO =
    		                        new RateAuditVO();
    	rateAuditVO.setCompanyCode(rs.getString("CMPCOD"));
    	rateAuditVO.setBillingBasis(rs.getString("BLGBAS"));
    	rateAuditVO.setSerialNumber(rs.getInt("SERNUM"));
    	rateAuditVO.setOrigin(rs.getString("ORGEXGOFC"));
    	rateAuditVO.setDestination(rs.getString("DSTEXGOFC"));
    	rateAuditVO.setGpaCode(rs.getString("POACOD"));
    	rateAuditVO.setConDocNum(rs.getString("CSGDOCNUM"));
    	rateAuditVO.setConSerNum(rs.getInt("CSGSEQNUM"));
    	rateAuditVO.setRoute(rs.getString("ROU"));
    	rateAuditVO.setPcs(rs.getString("PCS"));
    	rateAuditVO.setCategory(rs.getString("UPDMALCTGCOD"));
    	rateAuditVO.setSubClass(rs.getString("UPDMALSUBCLS"));
    	rateAuditVO.setUld(rs.getString("ULDNUM"));
    	rateAuditVO.setFlightCarCod(rs.getString("FLTCARCOD"));
    	rateAuditVO.setFlightNumber(rs.getString("FLTNUM"));
    	rateAuditVO.setRate(rs.getDouble("APLRAT"));
    	rateAuditVO.setUpdWt(rs.getString("UPDWGT"));
    	//rateAuditVO.setPresentWtCharge(rs.getString("WGTCHG"));
    	//rateAuditVO.setAuditedWtCharge(rs.getString("UPDWGTCHG"));
    	rateAuditVO.setDiscrepancy(rs.getString("DISPCY"));
    	rateAuditVO.setBillTo(rs.getString("BILTOO"));
    	rateAuditVO.setApplyAutd(rs.getString("APPAUD"));
    	
    	rateAuditVO.setDsnStatus(rs.getString("RATSTA"));
    	rateAuditVO.setLastUpdateTime(new LocalDate(NO_STATION, NONE, rs
				.getTimestamp("LSTUPDTIM")));
    	
    return rateAuditVO;
    }

}










