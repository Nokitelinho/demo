/*
 * ReportingPeriodMapper.java Created on May 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2518
 * 
 */
public class ReportingPeriodMapper implements Mapper<ReportingPeriodVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");

	private static final String CLASS_NAME = "ReportingPeriodMapper";

	private ReportingPeriodFilterVO reportingPeriodFilterVo;

	/**
	 * 
	 * @param reportingPeriodFilterVo
	 */
	public ReportingPeriodMapper(ReportingPeriodFilterVO reportingPeriodFilterVo) {
		this.reportingPeriodFilterVo = reportingPeriodFilterVo;
	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ReportingPeriodVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		ReportingPeriodVO reportingPeriodVo = new ReportingPeriodVO();
		log.log(Log.FINE, reportingPeriodFilterVo);
		
		
		
		/*LocalDate localDateFrom = new LocalDate(NO_STATION, NONE, false);
		localDateFrom.set(LocalDate.MONTH, reportingPeriodFilterVo
				.getFromDate().get(LocalDate.MONTH));
		localDateFrom.set(LocalDate.YEAR, reportingPeriodFilterVo.getFromDate()
				.get(LocalDate.YEAR));
		localDateFrom.set(LocalDate.DAY_OF_MONTH, 1);
		LocalDate localDateTo = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		localDateTo.set(LocalDate.MONTH, reportingPeriodFilterVo.getToDate()
				.get(LocalDate.MONTH));
		localDateTo.set(LocalDate.YEAR, reportingPeriodFilterVo.getToDate()
				.get(LocalDate.YEAR));
		localDateTo.set(LocalDate.DAY_OF_MONTH, localDateTo
				.getActualMaximum(LocalDate.DAY_OF_MONTH));
		for (; localDateFrom.isLesserThan(localDateTo); localDateFrom.set(
				LocalDate.MONTH, localDateFrom.get(LocalDate.MONTH) + 1)) {
			int fromDate = Integer.parseInt(rs.getString("BILFRMPRD")
					.substring(1));
			// Assumes that if it is not starting with 'D', it is 'EOM'
			int toDate = (rs.getString("BILENDPRD").toUpperCase()
					.startsWith("D")) ? Integer.parseInt(rs.getString(
					"BILENDPRD").substring(1)) : localDateFrom
					.getActualMaximum(LocalDate.DAY_OF_MONTH);
			LocalDate fromDateTemp = new LocalDate(localDateFrom, false);
			fromDateTemp.set(LocalDate.DAY_OF_MONTH, fromDate);
			LocalDate toDateTemp = new LocalDate(localDateFrom, false);
			toDateTemp.set(LocalDate.DAY_OF_MONTH, toDate);
			if ((reportingPeriodFilterVo.getFromDate().isLesserThan(
					fromDateTemp) || reportingPeriodFilterVo.getFromDate()
					.equals(fromDateTemp))
					&& (reportingPeriodFilterVo.getToDate().isGreaterThan(
							toDateTemp) || reportingPeriodFilterVo.getToDate()
							.equals(toDateTemp))) {
				reportingPeriodVo.setFromDate(fromDateTemp);
				repo
				
				rtingPeriodVo.setToDate(toDateTemp);
			}
		}*/
		
		if(rs.getString("BILFRQ")!=null){
		reportingPeriodVo.setBillingFrequency(rs.getString("BILFRQ"));
		}
		
		return reportingPeriodVo;
	}
}
