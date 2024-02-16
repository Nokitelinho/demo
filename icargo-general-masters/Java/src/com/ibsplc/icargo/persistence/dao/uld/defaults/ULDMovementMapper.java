/*
 * ULDDetailsMapper.java Created on Oct 14, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class ULDMovementMapper implements MultiMapper<ULDFlightMessageReconcileVO>{

	private Log log= LogFactory.getLogger("ULD_DEFAULTS");

	private static final String KEY_SEP = "-";
	
	private static final String CARGO_CONTENT="C";

	private static final String CLASS_NAME = "ULDMovementMapper";


	public List<ULDFlightMessageReconcileVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "ULDMovementMapper");
		Map<String, ULDFlightMessageReconcileVO> flightReconcileVOMap = new HashMap<String, ULDFlightMessageReconcileVO>();
		String flightReconcileKey = null;
		ULDFlightMessageReconcileVO flightReconcileVO = null;
		ULDFlightMessageReconcileDetailsVO uldReconcileVO = null;

		while (rs.next()){	
			flightReconcileKey = new StringBuilder(rs.getString("CMPCOD"))
			.append(KEY_SEP).append(rs.getString("FLTCARIDR"))
			.append(KEY_SEP).append(rs.getString("FLTNUM"))
			.append(KEY_SEP).append(rs.getString("FLTSEQNUM"))
			.append(KEY_SEP).append(rs.getString("SEGORG")).toString();
			if (!flightReconcileVOMap.containsKey(flightReconcileKey)) {
				flightReconcileVO = populateflightReconcileVO(rs);
				flightReconcileVOMap.put(flightReconcileKey, flightReconcileVO);
			} else {
				flightReconcileVO = flightReconcileVOMap.get(flightReconcileKey);
				uldReconcileVO = populateUldReconcileVO(rs);
				flightReconcileVO.getReconcileDetailsVOs().add(uldReconcileVO);
			}
		}
		List list = new ArrayList<>(flightReconcileVOMap.values());
		return list;
	}
	private ULDFlightMessageReconcileVO populateflightReconcileVO( ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "populateflightReconcileVO");
		ULDFlightMessageReconcileDetailsVO uldReconcileVO = null;
		ULDFlightMessageReconcileVO flightReconcileVO = new ULDFlightMessageReconcileVO();
		flightReconcileVO.setCompanyCode(rs.getString("CMPCOD"));
		flightReconcileVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
		flightReconcileVO.setFlightNumber(rs.getString("FLTNUM"));
		flightReconcileVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		flightReconcileVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,false));
		flightReconcileVO.setAirportCode(rs.getString("SEGORG"));
		flightReconcileVO.setMessageType("OUT");
		flightReconcileVO.setMessageSource("ULDMVTJOB");
		uldReconcileVO= populateUldReconcileVO(rs);
		flightReconcileVO.setReconcileDetailsVOs (new ArrayList<ULDFlightMessageReconcileDetailsVO>());
		flightReconcileVO.getReconcileDetailsVOs().add(uldReconcileVO);
		log.exiting(CLASS_NAME, "populateflightReconcileVO");
		return flightReconcileVO;
		
	}
	
	private ULDFlightMessageReconcileDetailsVO populateUldReconcileVO( ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "populateflightReconcileVO");
		ULDFlightMessageReconcileDetailsVO uldReconcileVO = new ULDFlightMessageReconcileDetailsVO();
		uldReconcileVO.setCompanyCode(rs.getString("CMPCOD"));
		uldReconcileVO.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
		uldReconcileVO.setFlightNumber(rs.getString("FLTNUM"));
		uldReconcileVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		uldReconcileVO.setPou(rs.getString("SEGDST"));
		uldReconcileVO.setUldNumber(rs.getString("ULDNUM"));
		uldReconcileVO.setContent(CARGO_CONTENT);
		uldReconcileVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,false));
		log.exiting(CLASS_NAME, "populateflightReconcileVO");
		return uldReconcileVO;
		
	}
}
