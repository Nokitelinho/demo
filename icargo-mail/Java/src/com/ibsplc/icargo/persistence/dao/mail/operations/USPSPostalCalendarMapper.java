package com.ibsplc.icargo.persistence.dao.mail.operations;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.USPSPostalCalendarMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
public class USPSPostalCalendarMapper implements Mapper<USPSPostalCalendarVO>{
	private Log log = LogFactory.getLogger("USPSPostalCalendarMapper");
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-8164 on 04-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param rs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException
	 */
	public USPSPostalCalendarVO map(ResultSet rs) throws SQLException {
		USPSPostalCalendarVO uSPSPostalCalendarVO=new USPSPostalCalendarVO();
		uSPSPostalCalendarVO.setCompanyCode(rs.getString("CMPCOD"));
		uSPSPostalCalendarVO.setGpacod(rs.getString("GPACOD"));
		uSPSPostalCalendarVO.setFilterCalender(rs.getString("CALTYP"));
		uSPSPostalCalendarVO.setPeriods(rs.getString("CALPRD"));
		if(null!=rs.getDate("PRDFRM")){
			uSPSPostalCalendarVO.setFromDates((new LocalDate(LocalDate.NO_STATION,Location.NONE,  
				rs.getDate("PRDFRM"))).toDisplayDateOnlyFormat()); 
		}
		if(null!=rs.getDate("PRDTOO")){
			uSPSPostalCalendarVO.setToDates((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("PRDTOO"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("CGRSUBDAT")) { 
			uSPSPostalCalendarVO.setCgrSubmissions((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CGRSUBDAT"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("CGRDELCUTOFFDAT")) {
			uSPSPostalCalendarVO.setCgrDeleteCutOffs((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CGRDELCUTOFFDAT"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("CUTOFFWEKFIR")) {
			uSPSPostalCalendarVO.setCutWeek1s((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CUTOFFWEKFIR"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("CUTOFFWEKSEC")){
			uSPSPostalCalendarVO.setCutWeek2s((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CUTOFFWEKSEC"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("CUTOFFWEKTHR"))  {
			uSPSPostalCalendarVO.setCutWeek3s((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CUTOFFWEKTHR"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("CUTOFFWEKFOU")){
			uSPSPostalCalendarVO.setCutWeek4s((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CUTOFFWEKFOU"))).toDisplayDateOnlyFormat()); 
		}
		if(null!=rs.getDate("CUTOFFWEKFIV")){ 
			uSPSPostalCalendarVO.setCutWeek5s((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("CUTOFFWEKFIV"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("PAYEFTDAT"))  {
			uSPSPostalCalendarVO.setPaymEffectiveDates((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("PAYEFTDAT"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("INCCALDAT")) {
			uSPSPostalCalendarVO.setIncCalcDates((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("INCCALDAT"))).toDisplayDateOnlyFormat()); 
		}
		if(null!=rs.getDate("INCEFTDAT")) {
			uSPSPostalCalendarVO.setIncEffectiveDates((new LocalDate(LocalDate.NO_STATION,Location.NONE, 
				rs.getDate("INCEFTDAT"))).toDisplayDateOnlyFormat());
		}
		if(null!=rs.getDate("INCRCVDAT")) {
			uSPSPostalCalendarVO.setIncRecvDates((new LocalDate(LocalDate.NO_STATION,Location.NONE,   
				rs.getDate("INCRCVDAT"))).toDisplayDateOnlyFormat());
		}
		//Added by A-8527 for ICRD-262451 starts
		if(null!=rs.getDate("CLMGENDAT")) {
			uSPSPostalCalendarVO.setClmGenDate((new LocalDate(LocalDate.NO_STATION,Location.NONE,   
				rs.getDate("CLMGENDAT"))).toDisplayDateOnlyFormat());
		}
		uSPSPostalCalendarVO.setCalSeqnum(rs.getLong("SERNUM"));               
		return uSPSPostalCalendarVO;
	}
}