/*
 * EmbargoRulesMapperForPortal.java Created on Sep 29, 2016
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.reco.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-4823
 *
 */
public class ParameterSelectiveSearchMapper implements MultiMapper<EmbargoDetailsVO> {
	private Log log = LogFactory.getLogger("RECO DEFAULTS");
	@Override
	public List<EmbargoDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("ParameterSelectiveSearchMapper", "map");
		List<EmbargoDetailsVO>  embargoDetailsVOs= new ArrayList<EmbargoDetailsVO>();
		EmbargoDetailsVO embargoDetailsVO=null;
		while (rs.next()) {
			embargoDetailsVO = new EmbargoDetailsVO();
			embargoDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			embargoDetailsVO.setEmbargoDescription(rs.getString("RECDES"));			
			embargoDetailsVO.setEmbargoReferenceNumber(rs.getString("REFNUM"));
			embargoDetailsVO.setComplianceType(rs.getString("CMPTYP"));
			embargoDetailsVO.setComplianceTypeDescription(rs.getString("CMPTYP"));
			embargoDetailsVO.setCategory(rs.getString("CATTYP"));
			embargoDetailsVO.setCategoryDescription(rs.getString("CATTYP"));
			if (rs.getDate("ENDDAT") != null) {
				embargoDetailsVO.setEndDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs.getDate("ENDDAT")));
			}
			if (rs.getDate("STRDAT") != null) {
			embargoDetailsVO.setStartDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs.getDate("STRDAT")));
			}
			if (rs.getString("ORGDAYOPR") != null ) {
				embargoDetailsVO.setOriginDayOfOperation(rs.getString("ORGDAYOPR"));
				
			}
			if (rs.getString("VIADAYOPR") != null ) {
				embargoDetailsVO.setViaPointDayOfOperation(rs.getString("ORGDAYOPR"));
			}
			if (rs.getString("DSTDAYOPR") != null ) {
				embargoDetailsVO.setDestinationDayOfOperation(rs.getString("ORGDAYOPR"));
			}								
			embargoDetailsVO.setStatus(rs.getString("RECSTA"));
			embargoDetailsVO
			.setEmbargoLevel(rs.getString("RECTYP"));
			embargoDetailsVO.setOriginAirportCodeInc(rs.getString("ORGARPCODINC"));
			embargoDetailsVO.setOriginAirportCodeExc(rs.getString("ORGARPCODEXC"));
			embargoDetailsVO.setOriginAirportGroupInc(rs.getString("ORGARPGRPINC"));
			embargoDetailsVO.setOriginAirportGroupExc(rs.getString("ORGARPGRPEXC"));
			embargoDetailsVO.setOriginCountryCodeInc(rs.getString("ORGCNTCODINC"));
			embargoDetailsVO.setOriginCountryCodeExc(rs.getString("ORGCNTCODEXC"));
			embargoDetailsVO.setOriginCountryGroupInc(rs.getString("ORGCNTGRPINC"));
			embargoDetailsVO.setOriginCountryGroupExc(rs.getString("ORGCNTGRPEXC"));
			embargoDetailsVO.setViaPointAirportCodeInc(rs.getString("VIAARPCODINC"));
			embargoDetailsVO.setViaPointAirportCodeExc(rs.getString("VIAARPCODEXC"));
			embargoDetailsVO.setViaPointAirportGroupInc(rs.getString("VIAARPGRPINC"));
			embargoDetailsVO.setViaPointAirportGroupExc(rs.getString("VIAARPGRPEXC"));
			embargoDetailsVO.setViaPointCountryCodeInc(rs.getString("VIACNTCODINC"));
			embargoDetailsVO.setViaPointCountryCodeExc(rs.getString("VIACNTCODEXC"));
			embargoDetailsVO.setViaPointCountryGroupInc(rs.getString("VIACNTGRPINC"));
			embargoDetailsVO.setViaPointCountryGroupExc(rs.getString("VIACNTGRPEXC"));
			embargoDetailsVO.setDestinationAirportCodeInc(rs.getString("DSTARPCODINC"));
			embargoDetailsVO.setDestinationAirportCodeExc(rs.getString("DSTARPCODEXC"));
			embargoDetailsVO.setDestinationAirportGroupInc(rs.getString("DSTARPGRPINC"));
			embargoDetailsVO.setDestinationAirportGroupExc(rs.getString("DSTARPGRPEXC"));
			embargoDetailsVO.setDestinationCountryCodeInc(rs.getString("DSTCNTCODINC"));
			embargoDetailsVO.setDestinationCountryCodeExc(rs.getString("DSTCNTCODEXC"));
			embargoDetailsVO.setDestinationCountryGroupInc(rs.getString("DSTCNTGRPINC"));
			embargoDetailsVO.setDestinationCountryGroupExc(rs.getString("DSTCNTGRPEXC"));		 		
			embargoDetailsVO.setProductInc(rs.getString("PRDINC"));
			embargoDetailsVO.setProductExc(rs.getString("PRDEXC"));
			embargoDetailsVO.setCommodityInc(rs.getString("COMINC"));
			embargoDetailsVO.setCommodityExc(rs.getString("COMEXC"));
			/**
			 * All the other parameters need to be set on need basis 
			 */
			Timestamp time = rs.getTimestamp("LSTUPDTIM");
			if(time != null){
				embargoDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,time));
			}
			if("Y".equals(rs.getString("SUSFLG"))){
				embargoDetailsVO.setIsSuspended(true);
			}
			embargoDetailsVOs.add(embargoDetailsVO);
		}

		log.exiting("ParameterSelectiveSearchMapper", "map");
		return embargoDetailsVOs;
	}

}
