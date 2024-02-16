package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mailmasters.vo.USPSPostalCalendarVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import java.time.format.DateTimeFormatter;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.USPSPostalCalendarMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
public class USPSPostalCalendarMapper implements Mapper<USPSPostalCalendarVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param rs Parameters	:	@return Parameters	:	@throws SQLException
	*/
	public USPSPostalCalendarVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		USPSPostalCalendarVO uSPSPostalCalendarVO = new USPSPostalCalendarVO();
		uSPSPostalCalendarVO.setCompanyCode(rs.getString("CMPCOD"));
		uSPSPostalCalendarVO.setGpacod(rs.getString("GPACOD"));
		uSPSPostalCalendarVO.setFilterCalender(rs.getString("CALTYP"));
		uSPSPostalCalendarVO.setPeriods(rs.getString("CALPRD"));
		if (null != rs.getDate("PRDFRM")) {
			uSPSPostalCalendarVO.setFromDates((localDateUtil.getLocalDate(null, rs.getDate("PRDFRM")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("PRDTOO")) {
			uSPSPostalCalendarVO.setToDates((localDateUtil.getLocalDate(null, rs.getDate("PRDTOO")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CGRSUBDAT")) {
			uSPSPostalCalendarVO.setCgrSubmissions((localDateUtil.getLocalDate(null, rs.getDate("CGRSUBDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CGRDELCUTOFFDAT")) {
			uSPSPostalCalendarVO.setCgrDeleteCutOffs((localDateUtil.getLocalDate(null, rs.getDate("CGRDELCUTOFFDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CUTOFFWEKFIR")) {
			uSPSPostalCalendarVO.setCutWeek1s((localDateUtil.getLocalDate(null, rs.getDate("CUTOFFWEKFIR")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CUTOFFWEKSEC")) {
			uSPSPostalCalendarVO.setCutWeek2s((localDateUtil.getLocalDate(null, rs.getDate("CUTOFFWEKSEC")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CUTOFFWEKTHR")) {
			uSPSPostalCalendarVO.setCutWeek3s((localDateUtil.getLocalDate(null, rs.getDate("CUTOFFWEKTHR")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CUTOFFWEKFOU")) {
			uSPSPostalCalendarVO.setCutWeek4s((localDateUtil.getLocalDate(null, rs.getDate("CUTOFFWEKFOU")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CUTOFFWEKFIV")) {
			uSPSPostalCalendarVO.setCutWeek5s((localDateUtil.getLocalDate(null, rs.getDate("CUTOFFWEKFIV")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("PAYEFTDAT")) {
			uSPSPostalCalendarVO.setPaymEffectiveDates((localDateUtil.getLocalDate(null, rs.getDate("PAYEFTDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("INCCALDAT")) {
			uSPSPostalCalendarVO.setIncCalcDates((localDateUtil.getLocalDate(null, rs.getDate("INCCALDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("INCEFTDAT")) {
			uSPSPostalCalendarVO.setIncEffectiveDates((localDateUtil.getLocalDate(null, rs.getDate("INCEFTDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("INCRCVDAT")) {
			uSPSPostalCalendarVO.setIncRecvDates((localDateUtil.getLocalDate(null, rs.getDate("INCRCVDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		if (null != rs.getDate("CLMGENDAT")) {
			uSPSPostalCalendarVO.setClmGenDate((localDateUtil.getLocalDate(null, rs.getDate("CLMGENDAT")))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		uSPSPostalCalendarVO.setCalSeqnum(rs.getLong("SERNUM"));
		return uSPSPostalCalendarVO;
	}
}
