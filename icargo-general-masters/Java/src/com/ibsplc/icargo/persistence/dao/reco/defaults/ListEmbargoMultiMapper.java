/*
 * ListEmbargoMultiMapper.java Created on Aug 02, 2013
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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5186
 * 
 */
public class ListEmbargoMultiMapper implements MultiMapper<EmbargoDetailsVO> {
	private Log log = LogFactory.getLogger("SHARED EMBARGO LIST EMBARGO ");

	public List<EmbargoDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("ListEmbargoMultiMapper", "PageAwareMultiMapper");
		LinkedHashMap<String, EmbargoDetailsVO> embargoDetailsMap = new LinkedHashMap<String, EmbargoDetailsVO>();
		String embargoPk = "";
		Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
		EmbargoDetailsVO embargoDetailsVO = null;
		EmbargoGeographicLevelVO embargoGeographicLevelVO = null;

		while (rs.next()) {

			embargoPk = new StringBuilder().append(rs.getString("CMPCOD"))
					.append(rs.getString("REFNUM")).append(rs.getInt("VERNUM")).toString();

			if (!embargoDetailsMap.containsKey(embargoPk)) {

				embargoDetailsVO = new EmbargoDetailsVO();
				embargoDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				embargoDetailsVO.setEmbargoVersion(rs.getInt("VERNUM"));
				
				embargoDetailsVO.setEmbargoDescription(rs.getString("RECDES"));
				embargoDetailsVO.setEmbargoReferenceNumber(rs
						.getString("REFNUM"));

				embargoDetailsVO.setAirportCode(rs.getString("USRSTNCOD"));
				if (rs.getDate("ENDDAT") != null) {
					embargoDetailsVO.setEndDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs
									.getDate("ENDDAT")));
				}

				
				embargoDetailsVO.setRemarks(rs.getString("RECRMK"));
				embargoDetailsVO.setCategory(rs.getString("CATTYP"));
				embargoDetailsVO.setComplianceType(rs.getString("CMPTYP"));
				embargoDetailsVO.setApplicableTransactions(rs.getString("APPTXN"));
				embargoDetailsVO.setRuleType(rs.getString("RULTYP"));

				embargoDetailsVO.setStartDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs
								.getDate("STRDAT")));

				embargoDetailsVO.setStatus(rs.getString("RECSTA"));
				if("Y".equals(rs.getString("SUSFLG"))){
					embargoDetailsVO.setIsSuspended(true);
				}
				else{
					embargoDetailsVO.setIsSuspended(false);
				}
				embargoDetailsVO.setEmbargoLevel(rs.getString("RECTYP"));

				Timestamp time = rs.getTimestamp("LSTUPDTIM");
				if (time != null) {
					embargoDetailsVO.setLastUpdateTime(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, time));
				}
				embargoDetailsVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				
				/*embargoDetailsVO.setDaysOfOperation(convertDayNumToDayName(rs
						.getString("DAYOPR")));
				embargoDetailsVO.setDaysOfOperationApplicableOn(rs
						.getString("DAYOPRAPP"));*/

				embargoDetailsMap.put(embargoPk, embargoDetailsVO);
			}
			if(!"P".equals(rs.getString("PARTYP")) && !"-".equals(rs.getString("PARVAL"))){
				embargoGeographicLevelVO = new EmbargoGeographicLevelVO();
				embargoGeographicLevelVO.setGeographicLevel(rs.getString("PARTYP"));
				embargoGeographicLevelVO.setGeographicLevelType(rs.getString("PARCOD"));
				embargoGeographicLevelVO.setGeographicLevelApplicableOn(rs.getString("PARCND"));
				embargoGeographicLevelVO.setGeographicLevelValues(rs.getString("PARVAL"));
				if (embargoDetailsVO.getGeographicLevels() == null) {
					embargoDetailsVO
							.setGeographicLevels(new HashSet<EmbargoGeographicLevelVO>());
				}
				embargoDetailsVO.getGeographicLevels().add(embargoGeographicLevelVO);
			}

			if ("P".equals(rs.getString("PARTYP")) && !"-".equals(rs.getString("PARVAL"))) {
				if("DOW".equals(rs.getString("PARCOD"))){
					embargoDetailsVO.setDaysOfOperation(convertDayNumToDayName(rs
							.getString("PARVAL")));
					embargoDetailsVO.setDaysOfOperationApplicableOn(rs
							.getString("APPONN"));
				}
				else{
					EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
					embargoParameterVO.setCompanyCode(rs.getString("CMPCOD"));
					embargoParameterVO.setEmbargoReferenceNumber(rs
							.getString("REFNUM"));
					embargoParameterVO.setParameterCode(rs.getString("PARCOD"));
					embargoParameterVO.setApplicable(rs.getString("PARCND"));
					embargoParameterVO.setParameterValues(rs.getString("PARVAL"));
					embargoParameterVO.setApplicableLevel(rs.getString("APPONN")); //Added by A-7924 as part of CR ICRD-299901
					if ("FLTNUM".equals(embargoParameterVO.getParameterCode())
							&& embargoParameterVO.getParameterValues() != null) {
						String[] values = embargoParameterVO.getParameterValues()
								.split("~");
						if (values.length > 1) {
							embargoParameterVO.setCarrierCode(values[0]);
							embargoParameterVO.setFlightNumber(values[1]);
							StringBuffer paramValue = new StringBuffer(embargoParameterVO.getCarrierCode())
							.append("~").append(embargoParameterVO.getFlightNumber());
							embargoParameterVO.setParameterValues(paramValue.toString());
						}
					}
					if (embargoDetailsVO.getParams() == null) {
						embargoDetailsVO
								.setParams(new HashSet<EmbargoParameterVO>());
					}
					embargoDetailsVO.getParams().add(embargoParameterVO);
				}
			}
		}
		embargoDetailsVOs.addAll(new ArrayList<EmbargoDetailsVO>(
				embargoDetailsMap.values()));
		log.log(Log.FINE, "embargoDetailsVOs Size-----> ", embargoDetailsVOs.size());
		log.exiting("ListEmbargoMultiMapper", "PageAwareMultiMapper");
		return (List<EmbargoDetailsVO>) embargoDetailsVOs;
	}

	private String convertDayNumToDayName(String dayNum) {
		if (dayNum != null) {
			if ("1234567".equals(dayNum)) {
				return "All";
			}
			String[] dayNumSplit = dayNum.split("(?<=\\G.{1})");
			StringBuffer dayNames = new StringBuffer("");
			if (dayNumSplit != null) {
				for (String dayNo : dayNumSplit) {
					if ("1".equals(dayNo)) {
						dayNames.append("Mon,");
					} else if ("2".equals(dayNo)) {
						dayNames.append("Tue,");
					} else if ("3".equals(dayNo)) {
						dayNames.append("Wed,");
					} else if ("4".equals(dayNo)) {
						dayNames.append("Thu,");
					} else if ("5".equals(dayNo)) {
						dayNames.append("Fri,");
					} else if ("6".equals(dayNo)) {
						dayNames.append("Sat,");
					} else if ("7".equals(dayNo)) {
						dayNames.append("Sun,");
					}
				}
				if (dayNames.length() > 1) {
					return dayNames.substring(0, dayNames.length() - 1);
				}
			}

		}

		return "";

	}

}
